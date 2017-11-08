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
import com.sinosoft.lis.db.LYBankLogDB;

/*
 * <p>ClassName: LYBankLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYBankLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LYBankLogSchema.class);

	// @Field
	/** 批次号 */
	private String SerialNo;
	/** 银行代码 */
	private String BankCode;
	/** 逻辑日志类型 */
	private String LogType;
	/** 起始日期 */
	private Date StartDate;
	/** 记录日期 */
	private Date MakeDate;
	/** 转出文件名 */
	private String OutFile;
	/** 转入文件名 */
	private String InFile;
	/** 发送日期 */
	private Date SendDate;
	/** 发送操作员 */
	private String SendOperator;
	/** 返回日期 */
	private Date ReturnDate;
	/** 返回操作员 */
	private String ReturnOperator;
	/** 转换日期 */
	private Date TransDate;
	/** 转换操作员 */
	private String TransOperator;
	/** 总金额 */
	private double TotalMoney;
	/** 总数量 */
	private int TotalNum;
	/** 财务确认总金额 */
	private double AccTotalMoney;
	/** 银行成功总金额 */
	private double BankSuccMoney;
	/** 银行成功总数量 */
	private int BankSuccNum;
	/** 转换成功金额 */
	private double SuccMoney;
	/** 成功数量 */
	private int SuccNum;
	/** 处理状态 */
	private String DealState;
	/** 其他标志 */
	private String OthFlag;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 机构编码 */
	private String ComCode;
	/** 授权文件 */
	private String OutFileB;
	/** 渠道 */
	private String SaleChnl;
	/** 业务类型 */
	private String OperationType;
	/** 发送银行文件名 */
	private String SendBankFileName;
	/** 发盘文件处理状态 */
	private String SendBankFileState;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LYBankLogSchema()
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
		LYBankLogSchema cloned = (LYBankLogSchema)super.clone();
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
	public String getLogType()
	{
		return LogType;
	}
	public void setLogType(String aLogType)
	{
		LogType = aLogType;
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

	public String getOutFile()
	{
		return OutFile;
	}
	public void setOutFile(String aOutFile)
	{
		OutFile = aOutFile;
	}
	public String getInFile()
	{
		return InFile;
	}
	public void setInFile(String aInFile)
	{
		InFile = aInFile;
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

	public String getSendOperator()
	{
		return SendOperator;
	}
	public void setSendOperator(String aSendOperator)
	{
		SendOperator = aSendOperator;
	}
	public String getReturnDate()
	{
		if( ReturnDate != null )
			return fDate.getString(ReturnDate);
		else
			return null;
	}
	public void setReturnDate(Date aReturnDate)
	{
		ReturnDate = aReturnDate;
	}
	public void setReturnDate(String aReturnDate)
	{
		if (aReturnDate != null && !aReturnDate.equals("") )
		{
			ReturnDate = fDate.getDate( aReturnDate );
		}
		else
			ReturnDate = null;
	}

	public String getReturnOperator()
	{
		return ReturnOperator;
	}
	public void setReturnOperator(String aReturnOperator)
	{
		ReturnOperator = aReturnOperator;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getTransOperator()
	{
		return TransOperator;
	}
	public void setTransOperator(String aTransOperator)
	{
		TransOperator = aTransOperator;
	}
	public double getTotalMoney()
	{
		return TotalMoney;
	}
	public void setTotalMoney(double aTotalMoney)
	{
		TotalMoney = aTotalMoney;
	}
	public void setTotalMoney(String aTotalMoney)
	{
		if (aTotalMoney != null && !aTotalMoney.equals(""))
		{
			Double tDouble = new Double(aTotalMoney);
			double d = tDouble.doubleValue();
			TotalMoney = d;
		}
	}

	public int getTotalNum()
	{
		return TotalNum;
	}
	public void setTotalNum(int aTotalNum)
	{
		TotalNum = aTotalNum;
	}
	public void setTotalNum(String aTotalNum)
	{
		if (aTotalNum != null && !aTotalNum.equals(""))
		{
			Integer tInteger = new Integer(aTotalNum);
			int i = tInteger.intValue();
			TotalNum = i;
		}
	}

	public double getAccTotalMoney()
	{
		return AccTotalMoney;
	}
	public void setAccTotalMoney(double aAccTotalMoney)
	{
		AccTotalMoney = aAccTotalMoney;
	}
	public void setAccTotalMoney(String aAccTotalMoney)
	{
		if (aAccTotalMoney != null && !aAccTotalMoney.equals(""))
		{
			Double tDouble = new Double(aAccTotalMoney);
			double d = tDouble.doubleValue();
			AccTotalMoney = d;
		}
	}

	public double getBankSuccMoney()
	{
		return BankSuccMoney;
	}
	public void setBankSuccMoney(double aBankSuccMoney)
	{
		BankSuccMoney = aBankSuccMoney;
	}
	public void setBankSuccMoney(String aBankSuccMoney)
	{
		if (aBankSuccMoney != null && !aBankSuccMoney.equals(""))
		{
			Double tDouble = new Double(aBankSuccMoney);
			double d = tDouble.doubleValue();
			BankSuccMoney = d;
		}
	}

	public int getBankSuccNum()
	{
		return BankSuccNum;
	}
	public void setBankSuccNum(int aBankSuccNum)
	{
		BankSuccNum = aBankSuccNum;
	}
	public void setBankSuccNum(String aBankSuccNum)
	{
		if (aBankSuccNum != null && !aBankSuccNum.equals(""))
		{
			Integer tInteger = new Integer(aBankSuccNum);
			int i = tInteger.intValue();
			BankSuccNum = i;
		}
	}

	public double getSuccMoney()
	{
		return SuccMoney;
	}
	public void setSuccMoney(double aSuccMoney)
	{
		SuccMoney = aSuccMoney;
	}
	public void setSuccMoney(String aSuccMoney)
	{
		if (aSuccMoney != null && !aSuccMoney.equals(""))
		{
			Double tDouble = new Double(aSuccMoney);
			double d = tDouble.doubleValue();
			SuccMoney = d;
		}
	}

	public int getSuccNum()
	{
		return SuccNum;
	}
	public void setSuccNum(int aSuccNum)
	{
		SuccNum = aSuccNum;
	}
	public void setSuccNum(String aSuccNum)
	{
		if (aSuccNum != null && !aSuccNum.equals(""))
		{
			Integer tInteger = new Integer(aSuccNum);
			int i = tInteger.intValue();
			SuccNum = i;
		}
	}

	public String getDealState()
	{
		return DealState;
	}
	public void setDealState(String aDealState)
	{
		DealState = aDealState;
	}
	public String getOthFlag()
	{
		return OthFlag;
	}
	public void setOthFlag(String aOthFlag)
	{
		OthFlag = aOthFlag;
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
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getOutFileB()
	{
		return OutFileB;
	}
	public void setOutFileB(String aOutFileB)
	{
		OutFileB = aOutFileB;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getOperationType()
	{
		return OperationType;
	}
	public void setOperationType(String aOperationType)
	{
		OperationType = aOperationType;
	}
	public String getSendBankFileName()
	{
		return SendBankFileName;
	}
	public void setSendBankFileName(String aSendBankFileName)
	{
		SendBankFileName = aSendBankFileName;
	}
	/**
	* 0  发送在途<p>
	* 1  发送成功（银行也反馈成功）<p>
	* 2  发送失败<p>
	* 3  银行接受成功但反馈异常<p>
	* 4  银行回盘成功<p>
	* 5  银行回盘失败
	*/
	public String getSendBankFileState()
	{
		return SendBankFileState;
	}
	public void setSendBankFileState(String aSendBankFileState)
	{
		SendBankFileState = aSendBankFileState;
	}

	/**
	* 使用另外一个 LYBankLogSchema 对象给 Schema 赋值
	* @param: aLYBankLogSchema LYBankLogSchema
	**/
	public void setSchema(LYBankLogSchema aLYBankLogSchema)
	{
		this.SerialNo = aLYBankLogSchema.getSerialNo();
		this.BankCode = aLYBankLogSchema.getBankCode();
		this.LogType = aLYBankLogSchema.getLogType();
		this.StartDate = fDate.getDate( aLYBankLogSchema.getStartDate());
		this.MakeDate = fDate.getDate( aLYBankLogSchema.getMakeDate());
		this.OutFile = aLYBankLogSchema.getOutFile();
		this.InFile = aLYBankLogSchema.getInFile();
		this.SendDate = fDate.getDate( aLYBankLogSchema.getSendDate());
		this.SendOperator = aLYBankLogSchema.getSendOperator();
		this.ReturnDate = fDate.getDate( aLYBankLogSchema.getReturnDate());
		this.ReturnOperator = aLYBankLogSchema.getReturnOperator();
		this.TransDate = fDate.getDate( aLYBankLogSchema.getTransDate());
		this.TransOperator = aLYBankLogSchema.getTransOperator();
		this.TotalMoney = aLYBankLogSchema.getTotalMoney();
		this.TotalNum = aLYBankLogSchema.getTotalNum();
		this.AccTotalMoney = aLYBankLogSchema.getAccTotalMoney();
		this.BankSuccMoney = aLYBankLogSchema.getBankSuccMoney();
		this.BankSuccNum = aLYBankLogSchema.getBankSuccNum();
		this.SuccMoney = aLYBankLogSchema.getSuccMoney();
		this.SuccNum = aLYBankLogSchema.getSuccNum();
		this.DealState = aLYBankLogSchema.getDealState();
		this.OthFlag = aLYBankLogSchema.getOthFlag();
		this.ModifyDate = fDate.getDate( aLYBankLogSchema.getModifyDate());
		this.ModifyTime = aLYBankLogSchema.getModifyTime();
		this.ComCode = aLYBankLogSchema.getComCode();
		this.OutFileB = aLYBankLogSchema.getOutFileB();
		this.SaleChnl = aLYBankLogSchema.getSaleChnl();
		this.OperationType = aLYBankLogSchema.getOperationType();
		this.SendBankFileName = aLYBankLogSchema.getSendBankFileName();
		this.SendBankFileState = aLYBankLogSchema.getSendBankFileState();
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

			if( rs.getString("LogType") == null )
				this.LogType = null;
			else
				this.LogType = rs.getString("LogType").trim();

			this.StartDate = rs.getDate("StartDate");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("OutFile") == null )
				this.OutFile = null;
			else
				this.OutFile = rs.getString("OutFile").trim();

			if( rs.getString("InFile") == null )
				this.InFile = null;
			else
				this.InFile = rs.getString("InFile").trim();

			this.SendDate = rs.getDate("SendDate");
			if( rs.getString("SendOperator") == null )
				this.SendOperator = null;
			else
				this.SendOperator = rs.getString("SendOperator").trim();

			this.ReturnDate = rs.getDate("ReturnDate");
			if( rs.getString("ReturnOperator") == null )
				this.ReturnOperator = null;
			else
				this.ReturnOperator = rs.getString("ReturnOperator").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("TransOperator") == null )
				this.TransOperator = null;
			else
				this.TransOperator = rs.getString("TransOperator").trim();

			this.TotalMoney = rs.getDouble("TotalMoney");
			this.TotalNum = rs.getInt("TotalNum");
			this.AccTotalMoney = rs.getDouble("AccTotalMoney");
			this.BankSuccMoney = rs.getDouble("BankSuccMoney");
			this.BankSuccNum = rs.getInt("BankSuccNum");
			this.SuccMoney = rs.getDouble("SuccMoney");
			this.SuccNum = rs.getInt("SuccNum");
			if( rs.getString("DealState") == null )
				this.DealState = null;
			else
				this.DealState = rs.getString("DealState").trim();

			if( rs.getString("OthFlag") == null )
				this.OthFlag = null;
			else
				this.OthFlag = rs.getString("OthFlag").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OutFileB") == null )
				this.OutFileB = null;
			else
				this.OutFileB = rs.getString("OutFileB").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("OperationType") == null )
				this.OperationType = null;
			else
				this.OperationType = rs.getString("OperationType").trim();

			if( rs.getString("SendBankFileName") == null )
				this.SendBankFileName = null;
			else
				this.SendBankFileName = rs.getString("SendBankFileName").trim();

			if( rs.getString("SendBankFileState") == null )
				this.SendBankFileState = null;
			else
				this.SendBankFileState = rs.getString("SendBankFileState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LYBankLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LYBankLogSchema getSchema()
	{
		LYBankLogSchema aLYBankLogSchema = new LYBankLogSchema();
		aLYBankLogSchema.setSchema(this);
		return aLYBankLogSchema;
	}

	public LYBankLogDB getDB()
	{
		LYBankLogDB aDBOper = new LYBankLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutFile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InFile)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReturnDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccTotalMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BankSuccMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BankSuccNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SuccMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SuccNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutFileB)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendBankFileName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendBankFileState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LogType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			OutFile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InFile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			SendOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ReturnDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ReturnOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			TransOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			TotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			TotalNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			AccTotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			BankSuccMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			BankSuccNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			SuccMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			SuccNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			DealState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			OthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			OutFileB = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			OperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			SendBankFileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SendBankFileState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankLogSchema";
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
		if (FCode.equalsIgnoreCase("LogType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogType));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("OutFile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutFile));
		}
		if (FCode.equalsIgnoreCase("InFile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InFile));
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
		}
		if (FCode.equalsIgnoreCase("SendOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendOperator));
		}
		if (FCode.equalsIgnoreCase("ReturnDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReturnDate()));
		}
		if (FCode.equalsIgnoreCase("ReturnOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnOperator));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransOperator));
		}
		if (FCode.equalsIgnoreCase("TotalMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalMoney));
		}
		if (FCode.equalsIgnoreCase("TotalNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalNum));
		}
		if (FCode.equalsIgnoreCase("AccTotalMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccTotalMoney));
		}
		if (FCode.equalsIgnoreCase("BankSuccMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccMoney));
		}
		if (FCode.equalsIgnoreCase("BankSuccNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccNum));
		}
		if (FCode.equalsIgnoreCase("SuccMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuccMoney));
		}
		if (FCode.equalsIgnoreCase("SuccNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuccNum));
		}
		if (FCode.equalsIgnoreCase("DealState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealState));
		}
		if (FCode.equalsIgnoreCase("OthFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthFlag));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("OutFileB"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutFileB));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperationType));
		}
		if (FCode.equalsIgnoreCase("SendBankFileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendBankFileName));
		}
		if (FCode.equalsIgnoreCase("SendBankFileState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendBankFileState));
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
				strFieldValue = StrTool.GBKToUnicode(LogType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OutFile);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InFile);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SendOperator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReturnDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ReturnOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(TransOperator);
				break;
			case 13:
				strFieldValue = String.valueOf(TotalMoney);
				break;
			case 14:
				strFieldValue = String.valueOf(TotalNum);
				break;
			case 15:
				strFieldValue = String.valueOf(AccTotalMoney);
				break;
			case 16:
				strFieldValue = String.valueOf(BankSuccMoney);
				break;
			case 17:
				strFieldValue = String.valueOf(BankSuccNum);
				break;
			case 18:
				strFieldValue = String.valueOf(SuccMoney);
				break;
			case 19:
				strFieldValue = String.valueOf(SuccNum);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(DealState);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(OthFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OutFileB);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(OperationType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SendBankFileName);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SendBankFileState);
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
		if (FCode.equalsIgnoreCase("LogType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogType = FValue.trim();
			}
			else
				LogType = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("OutFile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutFile = FValue.trim();
			}
			else
				OutFile = null;
		}
		if (FCode.equalsIgnoreCase("InFile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InFile = FValue.trim();
			}
			else
				InFile = null;
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
		if (FCode.equalsIgnoreCase("SendOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendOperator = FValue.trim();
			}
			else
				SendOperator = null;
		}
		if (FCode.equalsIgnoreCase("ReturnDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReturnDate = fDate.getDate( FValue );
			}
			else
				ReturnDate = null;
		}
		if (FCode.equalsIgnoreCase("ReturnOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnOperator = FValue.trim();
			}
			else
				ReturnOperator = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equalsIgnoreCase("TransOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransOperator = FValue.trim();
			}
			else
				TransOperator = null;
		}
		if (FCode.equalsIgnoreCase("TotalMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("TotalNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("AccTotalMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccTotalMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("BankSuccMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BankSuccMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("BankSuccNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BankSuccNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("SuccMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SuccMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("SuccNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SuccNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("DealState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealState = FValue.trim();
			}
			else
				DealState = null;
		}
		if (FCode.equalsIgnoreCase("OthFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthFlag = FValue.trim();
			}
			else
				OthFlag = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("OutFileB"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutFileB = FValue.trim();
			}
			else
				OutFileB = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperationType = FValue.trim();
			}
			else
				OperationType = null;
		}
		if (FCode.equalsIgnoreCase("SendBankFileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendBankFileName = FValue.trim();
			}
			else
				SendBankFileName = null;
		}
		if (FCode.equalsIgnoreCase("SendBankFileState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendBankFileState = FValue.trim();
			}
			else
				SendBankFileState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LYBankLogSchema other = (LYBankLogSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& BankCode.equals(other.getBankCode())
			&& LogType.equals(other.getLogType())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& OutFile.equals(other.getOutFile())
			&& InFile.equals(other.getInFile())
			&& fDate.getString(SendDate).equals(other.getSendDate())
			&& SendOperator.equals(other.getSendOperator())
			&& fDate.getString(ReturnDate).equals(other.getReturnDate())
			&& ReturnOperator.equals(other.getReturnOperator())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& TransOperator.equals(other.getTransOperator())
			&& TotalMoney == other.getTotalMoney()
			&& TotalNum == other.getTotalNum()
			&& AccTotalMoney == other.getAccTotalMoney()
			&& BankSuccMoney == other.getBankSuccMoney()
			&& BankSuccNum == other.getBankSuccNum()
			&& SuccMoney == other.getSuccMoney()
			&& SuccNum == other.getSuccNum()
			&& DealState.equals(other.getDealState())
			&& OthFlag.equals(other.getOthFlag())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ComCode.equals(other.getComCode())
			&& OutFileB.equals(other.getOutFileB())
			&& SaleChnl.equals(other.getSaleChnl())
			&& OperationType.equals(other.getOperationType())
			&& SendBankFileName.equals(other.getSendBankFileName())
			&& SendBankFileState.equals(other.getSendBankFileState());
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
		if( strFieldName.equals("LogType") ) {
			return 2;
		}
		if( strFieldName.equals("StartDate") ) {
			return 3;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 4;
		}
		if( strFieldName.equals("OutFile") ) {
			return 5;
		}
		if( strFieldName.equals("InFile") ) {
			return 6;
		}
		if( strFieldName.equals("SendDate") ) {
			return 7;
		}
		if( strFieldName.equals("SendOperator") ) {
			return 8;
		}
		if( strFieldName.equals("ReturnDate") ) {
			return 9;
		}
		if( strFieldName.equals("ReturnOperator") ) {
			return 10;
		}
		if( strFieldName.equals("TransDate") ) {
			return 11;
		}
		if( strFieldName.equals("TransOperator") ) {
			return 12;
		}
		if( strFieldName.equals("TotalMoney") ) {
			return 13;
		}
		if( strFieldName.equals("TotalNum") ) {
			return 14;
		}
		if( strFieldName.equals("AccTotalMoney") ) {
			return 15;
		}
		if( strFieldName.equals("BankSuccMoney") ) {
			return 16;
		}
		if( strFieldName.equals("BankSuccNum") ) {
			return 17;
		}
		if( strFieldName.equals("SuccMoney") ) {
			return 18;
		}
		if( strFieldName.equals("SuccNum") ) {
			return 19;
		}
		if( strFieldName.equals("DealState") ) {
			return 20;
		}
		if( strFieldName.equals("OthFlag") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("ComCode") ) {
			return 24;
		}
		if( strFieldName.equals("OutFileB") ) {
			return 25;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 26;
		}
		if( strFieldName.equals("OperationType") ) {
			return 27;
		}
		if( strFieldName.equals("SendBankFileName") ) {
			return 28;
		}
		if( strFieldName.equals("SendBankFileState") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "LogType";
				break;
			case 3:
				strFieldName = "StartDate";
				break;
			case 4:
				strFieldName = "MakeDate";
				break;
			case 5:
				strFieldName = "OutFile";
				break;
			case 6:
				strFieldName = "InFile";
				break;
			case 7:
				strFieldName = "SendDate";
				break;
			case 8:
				strFieldName = "SendOperator";
				break;
			case 9:
				strFieldName = "ReturnDate";
				break;
			case 10:
				strFieldName = "ReturnOperator";
				break;
			case 11:
				strFieldName = "TransDate";
				break;
			case 12:
				strFieldName = "TransOperator";
				break;
			case 13:
				strFieldName = "TotalMoney";
				break;
			case 14:
				strFieldName = "TotalNum";
				break;
			case 15:
				strFieldName = "AccTotalMoney";
				break;
			case 16:
				strFieldName = "BankSuccMoney";
				break;
			case 17:
				strFieldName = "BankSuccNum";
				break;
			case 18:
				strFieldName = "SuccMoney";
				break;
			case 19:
				strFieldName = "SuccNum";
				break;
			case 20:
				strFieldName = "DealState";
				break;
			case 21:
				strFieldName = "OthFlag";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "ComCode";
				break;
			case 25:
				strFieldName = "OutFileB";
				break;
			case 26:
				strFieldName = "SaleChnl";
				break;
			case 27:
				strFieldName = "OperationType";
				break;
			case 28:
				strFieldName = "SendBankFileName";
				break;
			case 29:
				strFieldName = "SendBankFileState";
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
		if( strFieldName.equals("LogType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutFile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InFile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SendOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReturnOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TotalNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AccTotalMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankSuccMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankSuccNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SuccMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SuccNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DealState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutFileB") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendBankFileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendBankFileState") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
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
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
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
