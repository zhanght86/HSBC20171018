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
import com.sinosoft.lis.db.LYBankBillDB;

/*
 * <p>ClassName: LYBankBillSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYBankBillSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LYBankBillSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 银行编码 */
	private String BankCode;
	/** 对应其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 交费通知书号 */
	private String GetNoticeNo;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 银行帐号 */
	private String BankAccNo;
	/** 帐户名 */
	private String AccName;
	/** 金额 */
	private double PayMoney;
	/** 交费期次 */
	private int PayCount;
	/** 原交至日期 */
	private Date LastPayToDate;
	/** 现交至日期 */
	private Date CurPayToDate;
	/** 成功标志 */
	private String BankSuccFlag;
	/** 打印次数 */
	private int BankPrintCount;
	/** 划款成功日期 */
	private Date BankSuccDate;
	/** 送银行日期 */
	private Date SendDate;
	/** 银行返回日期 */
	private Date ReturnFromBankDate;
	/** 银行打印网点号 */
	private String BankNetworkCode;
	/** 银行业务流水号 */
	private String BankSerialNo;
	/** 操作员 */
	private String Operator;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LYBankBillSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "BankCode";

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
		LYBankBillSchema cloned = (LYBankBillSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 对于交费类型为首期交费tempfeetype=1<p>
	* <p>
	* 0---首期个单交费核销后对应个单保单号<p>
	* 1---首期集体交费核销后对应集体保单号<p>
	* 2---首期合同交费核销后对应合同号<p>
	* 3---保全交费对应保全的批单号<p>
	* 4---首期个单、团单交费对应的印刷号码。<p>
	* 6---首期银行险时候转账，复核通过核销产生的对应银行险投保单号。<p>
	* 7---首期团单合同交费核销后对应团单合同号<p>
	* <p>
	* <p>
	* 另外，对于新单还没有暂缴费关联前，其他号码字段为空。所以对于暂交费退费需要根据该表中的投保人，名称来退。<p>
	* <p>
	* 对于从银行代收的数据(2003-9-8 yt add)：<p>
	* 首期直接交费 TempFeeType=1 OtherNoType=0<p>
	* 首期事后交费 TempFeeType=1 OtherNoType=0<p>
	* <p>
	* 正常续期催收 TempFeeType=2 OtherNoType=0<p>
	* <p>
	* 对于交费类型为续期交费tempfeetype=2<p>
	* 0---续期交费对应个单保单号<p>
	* 1---续期交费对应集体保单号<p>
	* 2---续期交费对应合同号
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
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

	public String getLastPayToDate()
	{
		if( LastPayToDate != null )
			return fDate.getString(LastPayToDate);
		else
			return null;
	}
	public void setLastPayToDate(Date aLastPayToDate)
	{
		LastPayToDate = aLastPayToDate;
	}
	public void setLastPayToDate(String aLastPayToDate)
	{
		if (aLastPayToDate != null && !aLastPayToDate.equals("") )
		{
			LastPayToDate = fDate.getDate( aLastPayToDate );
		}
		else
			LastPayToDate = null;
	}

	public String getCurPayToDate()
	{
		if( CurPayToDate != null )
			return fDate.getString(CurPayToDate);
		else
			return null;
	}
	public void setCurPayToDate(Date aCurPayToDate)
	{
		CurPayToDate = aCurPayToDate;
	}
	public void setCurPayToDate(String aCurPayToDate)
	{
		if (aCurPayToDate != null && !aCurPayToDate.equals("") )
		{
			CurPayToDate = fDate.getDate( aCurPayToDate );
		}
		else
			CurPayToDate = null;
	}

	public String getBankSuccFlag()
	{
		return BankSuccFlag;
	}
	public void setBankSuccFlag(String aBankSuccFlag)
	{
		BankSuccFlag = aBankSuccFlag;
	}
	public int getBankPrintCount()
	{
		return BankPrintCount;
	}
	public void setBankPrintCount(int aBankPrintCount)
	{
		BankPrintCount = aBankPrintCount;
	}
	public void setBankPrintCount(String aBankPrintCount)
	{
		if (aBankPrintCount != null && !aBankPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aBankPrintCount);
			int i = tInteger.intValue();
			BankPrintCount = i;
		}
	}

	public String getBankSuccDate()
	{
		if( BankSuccDate != null )
			return fDate.getString(BankSuccDate);
		else
			return null;
	}
	public void setBankSuccDate(Date aBankSuccDate)
	{
		BankSuccDate = aBankSuccDate;
	}
	public void setBankSuccDate(String aBankSuccDate)
	{
		if (aBankSuccDate != null && !aBankSuccDate.equals("") )
		{
			BankSuccDate = fDate.getDate( aBankSuccDate );
		}
		else
			BankSuccDate = null;
	}

	public String getSendDate()
	{
		if( SendDate != null )
			return fDate.getString(SendDate);
		else
			return null;
	}
	public void setSendDate(Date aSendDate)
	{
		SendDate = aSendDate;
	}
	public void setSendDate(String aSendDate)
	{
		if (aSendDate != null && !aSendDate.equals("") )
		{
			SendDate = fDate.getDate( aSendDate );
		}
		else
			SendDate = null;
	}

	public String getReturnFromBankDate()
	{
		if( ReturnFromBankDate != null )
			return fDate.getString(ReturnFromBankDate);
		else
			return null;
	}
	public void setReturnFromBankDate(Date aReturnFromBankDate)
	{
		ReturnFromBankDate = aReturnFromBankDate;
	}
	public void setReturnFromBankDate(String aReturnFromBankDate)
	{
		if (aReturnFromBankDate != null && !aReturnFromBankDate.equals("") )
		{
			ReturnFromBankDate = fDate.getDate( aReturnFromBankDate );
		}
		else
			ReturnFromBankDate = null;
	}

	public String getBankNetworkCode()
	{
		return BankNetworkCode;
	}
	public void setBankNetworkCode(String aBankNetworkCode)
	{
		BankNetworkCode = aBankNetworkCode;
	}
	public String getBankSerialNo()
	{
		return BankSerialNo;
	}
	public void setBankSerialNo(String aBankSerialNo)
	{
		BankSerialNo = aBankSerialNo;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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

	/**
	* 使用另外一个 LYBankBillSchema 对象给 Schema 赋值
	* @param: aLYBankBillSchema LYBankBillSchema
	**/
	public void setSchema(LYBankBillSchema aLYBankBillSchema)
	{
		this.SerialNo = aLYBankBillSchema.getSerialNo();
		this.BankCode = aLYBankBillSchema.getBankCode();
		this.OtherNo = aLYBankBillSchema.getOtherNo();
		this.OtherNoType = aLYBankBillSchema.getOtherNoType();
		this.GetNoticeNo = aLYBankBillSchema.getGetNoticeNo();
		this.AppntNo = aLYBankBillSchema.getAppntNo();
		this.BankAccNo = aLYBankBillSchema.getBankAccNo();
		this.AccName = aLYBankBillSchema.getAccName();
		this.PayMoney = aLYBankBillSchema.getPayMoney();
		this.PayCount = aLYBankBillSchema.getPayCount();
		this.LastPayToDate = fDate.getDate( aLYBankBillSchema.getLastPayToDate());
		this.CurPayToDate = fDate.getDate( aLYBankBillSchema.getCurPayToDate());
		this.BankSuccFlag = aLYBankBillSchema.getBankSuccFlag();
		this.BankPrintCount = aLYBankBillSchema.getBankPrintCount();
		this.BankSuccDate = fDate.getDate( aLYBankBillSchema.getBankSuccDate());
		this.SendDate = fDate.getDate( aLYBankBillSchema.getSendDate());
		this.ReturnFromBankDate = fDate.getDate( aLYBankBillSchema.getReturnFromBankDate());
		this.BankNetworkCode = aLYBankBillSchema.getBankNetworkCode();
		this.BankSerialNo = aLYBankBillSchema.getBankSerialNo();
		this.Operator = aLYBankBillSchema.getOperator();
		this.MakeTime = aLYBankBillSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLYBankBillSchema.getMakeDate());
		this.ModifyDate = fDate.getDate( aLYBankBillSchema.getModifyDate());
		this.ModifyTime = aLYBankBillSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			this.PayCount = rs.getInt("PayCount");
			this.LastPayToDate = rs.getDate("LastPayToDate");
			this.CurPayToDate = rs.getDate("CurPayToDate");
			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			this.BankPrintCount = rs.getInt("BankPrintCount");
			this.BankSuccDate = rs.getDate("BankSuccDate");
			this.SendDate = rs.getDate("SendDate");
			this.ReturnFromBankDate = rs.getDate("ReturnFromBankDate");
			if( rs.getString("BankNetworkCode") == null )
				this.BankNetworkCode = null;
			else
				this.BankNetworkCode = rs.getString("BankNetworkCode").trim();

			if( rs.getString("BankSerialNo") == null )
				this.BankSerialNo = null;
			else
				this.BankSerialNo = rs.getString("BankSerialNo").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LYBankBill表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankBillSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LYBankBillSchema getSchema()
	{
		LYBankBillSchema aLYBankBillSchema = new LYBankBillSchema();
		aLYBankBillSchema.setSchema(this);
		return aLYBankBillSchema;
	}

	public LYBankBillDB getDB()
	{
		LYBankBillDB aDBOper = new LYBankBillDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankBill描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BankPrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BankSuccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReturnFromBankDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankNetworkCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankBill>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			LastPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			CurPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BankPrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			BankSuccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ReturnFromBankDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			BankNetworkCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			BankSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankBillSchema";
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccFlag));
		}
		if (FCode.equalsIgnoreCase("BankPrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankPrintCount));
		}
		if (FCode.equalsIgnoreCase("BankSuccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBankSuccDate()));
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
		}
		if (FCode.equalsIgnoreCase("ReturnFromBankDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReturnFromBankDate()));
		}
		if (FCode.equalsIgnoreCase("BankNetworkCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankNetworkCode));
		}
		if (FCode.equalsIgnoreCase("BankSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSerialNo));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 8:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 9:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 13:
				strFieldValue = String.valueOf(BankPrintCount);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBankSuccDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReturnFromBankDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BankNetworkCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BankSerialNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
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
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
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
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastPayToDate = fDate.getDate( FValue );
			}
			else
				LastPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurPayToDate = fDate.getDate( FValue );
			}
			else
				CurPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankSuccFlag = FValue.trim();
			}
			else
				BankSuccFlag = null;
		}
		if (FCode.equalsIgnoreCase("BankPrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BankPrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("BankSuccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BankSuccDate = fDate.getDate( FValue );
			}
			else
				BankSuccDate = null;
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SendDate = fDate.getDate( FValue );
			}
			else
				SendDate = null;
		}
		if (FCode.equalsIgnoreCase("ReturnFromBankDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReturnFromBankDate = fDate.getDate( FValue );
			}
			else
				ReturnFromBankDate = null;
		}
		if (FCode.equalsIgnoreCase("BankNetworkCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankNetworkCode = FValue.trim();
			}
			else
				BankNetworkCode = null;
		}
		if (FCode.equalsIgnoreCase("BankSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankSerialNo = FValue.trim();
			}
			else
				BankSerialNo = null;
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
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		LYBankBillSchema other = (LYBankBillSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& BankCode.equals(other.getBankCode())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& AppntNo.equals(other.getAppntNo())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& PayMoney == other.getPayMoney()
			&& PayCount == other.getPayCount()
			&& fDate.getString(LastPayToDate).equals(other.getLastPayToDate())
			&& fDate.getString(CurPayToDate).equals(other.getCurPayToDate())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& BankPrintCount == other.getBankPrintCount()
			&& fDate.getString(BankSuccDate).equals(other.getBankSuccDate())
			&& fDate.getString(SendDate).equals(other.getSendDate())
			&& fDate.getString(ReturnFromBankDate).equals(other.getReturnFromBankDate())
			&& BankNetworkCode.equals(other.getBankNetworkCode())
			&& BankSerialNo.equals(other.getBankSerialNo())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
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
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 3;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 4;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 5;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 6;
		}
		if( strFieldName.equals("AccName") ) {
			return 7;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 8;
		}
		if( strFieldName.equals("PayCount") ) {
			return 9;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return 10;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return 11;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 12;
		}
		if( strFieldName.equals("BankPrintCount") ) {
			return 13;
		}
		if( strFieldName.equals("BankSuccDate") ) {
			return 14;
		}
		if( strFieldName.equals("SendDate") ) {
			return 15;
		}
		if( strFieldName.equals("ReturnFromBankDate") ) {
			return 16;
		}
		if( strFieldName.equals("BankNetworkCode") ) {
			return 17;
		}
		if( strFieldName.equals("BankSerialNo") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "OtherNo";
				break;
			case 3:
				strFieldName = "OtherNoType";
				break;
			case 4:
				strFieldName = "GetNoticeNo";
				break;
			case 5:
				strFieldName = "AppntNo";
				break;
			case 6:
				strFieldName = "BankAccNo";
				break;
			case 7:
				strFieldName = "AccName";
				break;
			case 8:
				strFieldName = "PayMoney";
				break;
			case 9:
				strFieldName = "PayCount";
				break;
			case 10:
				strFieldName = "LastPayToDate";
				break;
			case 11:
				strFieldName = "CurPayToDate";
				break;
			case 12:
				strFieldName = "BankSuccFlag";
				break;
			case 13:
				strFieldName = "BankPrintCount";
				break;
			case 14:
				strFieldName = "BankSuccDate";
				break;
			case 15:
				strFieldName = "SendDate";
				break;
			case 16:
				strFieldName = "ReturnFromBankDate";
				break;
			case 17:
				strFieldName = "BankNetworkCode";
				break;
			case 18:
				strFieldName = "BankSerialNo";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankPrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BankSuccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReturnFromBankDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankNetworkCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
