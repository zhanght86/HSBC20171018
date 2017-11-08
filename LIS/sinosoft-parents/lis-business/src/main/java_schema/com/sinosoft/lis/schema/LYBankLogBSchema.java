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
import com.sinosoft.lis.db.LYBankLogBDB;

/*
 * <p>ClassName: LYBankLogBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYBankLogBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LYBankLogBSchema.class);

	// @Field
	/** 回滚流水号 */
	private String BackUpNo;
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
	/** 机构编码 */
	private String ComCode;
	/** 授权文件 */
	private String OutFileB;
	/** 渠道 */
	private String SaleChnl;
	/** 业务类型 */
	private String OperationType;
	/** 文件导入日期 */
	private Date FileOperateDate;
	/** 文件导入时间 */
	private String FileOperateTime;
	/** 回滚原因 */
	private String BackReason;
	/** 回滚操作员 */
	private String BackOperator;
	/** 回滚操作结构 */
	private String BackCom;
	/** 回滚日期 */
	private Date ModifyDate;
	/** 回滚时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LYBankLogBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BackUpNo";

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
		LYBankLogBSchema cloned = (LYBankLogBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBackUpNo()
	{
		return BackUpNo;
	}
	public void setBackUpNo(String aBackUpNo)
	{
		BackUpNo = aBackUpNo;
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
	public String getFileOperateDate()
	{
		if( FileOperateDate != null )
			return fDate.getString(FileOperateDate);
		else
			return null;
	}
	public void setFileOperateDate(Date aFileOperateDate)
	{
		FileOperateDate = aFileOperateDate;
	}
	public void setFileOperateDate(String aFileOperateDate)
	{
		if (aFileOperateDate != null && !aFileOperateDate.equals("") )
		{
			FileOperateDate = fDate.getDate( aFileOperateDate );
		}
		else
			FileOperateDate = null;
	}

	public String getFileOperateTime()
	{
		return FileOperateTime;
	}
	public void setFileOperateTime(String aFileOperateTime)
	{
		FileOperateTime = aFileOperateTime;
	}
	public String getBackReason()
	{
		return BackReason;
	}
	public void setBackReason(String aBackReason)
	{
		BackReason = aBackReason;
	}
	public String getBackOperator()
	{
		return BackOperator;
	}
	public void setBackOperator(String aBackOperator)
	{
		BackOperator = aBackOperator;
	}
	public String getBackCom()
	{
		return BackCom;
	}
	public void setBackCom(String aBackCom)
	{
		BackCom = aBackCom;
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
	* 使用另外一个 LYBankLogBSchema 对象给 Schema 赋值
	* @param: aLYBankLogBSchema LYBankLogBSchema
	**/
	public void setSchema(LYBankLogBSchema aLYBankLogBSchema)
	{
		this.BackUpNo = aLYBankLogBSchema.getBackUpNo();
		this.SerialNo = aLYBankLogBSchema.getSerialNo();
		this.BankCode = aLYBankLogBSchema.getBankCode();
		this.LogType = aLYBankLogBSchema.getLogType();
		this.StartDate = fDate.getDate( aLYBankLogBSchema.getStartDate());
		this.MakeDate = fDate.getDate( aLYBankLogBSchema.getMakeDate());
		this.OutFile = aLYBankLogBSchema.getOutFile();
		this.InFile = aLYBankLogBSchema.getInFile();
		this.SendDate = fDate.getDate( aLYBankLogBSchema.getSendDate());
		this.SendOperator = aLYBankLogBSchema.getSendOperator();
		this.ReturnDate = fDate.getDate( aLYBankLogBSchema.getReturnDate());
		this.ReturnOperator = aLYBankLogBSchema.getReturnOperator();
		this.TransDate = fDate.getDate( aLYBankLogBSchema.getTransDate());
		this.TransOperator = aLYBankLogBSchema.getTransOperator();
		this.TotalMoney = aLYBankLogBSchema.getTotalMoney();
		this.TotalNum = aLYBankLogBSchema.getTotalNum();
		this.AccTotalMoney = aLYBankLogBSchema.getAccTotalMoney();
		this.BankSuccMoney = aLYBankLogBSchema.getBankSuccMoney();
		this.BankSuccNum = aLYBankLogBSchema.getBankSuccNum();
		this.SuccMoney = aLYBankLogBSchema.getSuccMoney();
		this.SuccNum = aLYBankLogBSchema.getSuccNum();
		this.DealState = aLYBankLogBSchema.getDealState();
		this.OthFlag = aLYBankLogBSchema.getOthFlag();
		this.ComCode = aLYBankLogBSchema.getComCode();
		this.OutFileB = aLYBankLogBSchema.getOutFileB();
		this.SaleChnl = aLYBankLogBSchema.getSaleChnl();
		this.OperationType = aLYBankLogBSchema.getOperationType();
		this.FileOperateDate = fDate.getDate( aLYBankLogBSchema.getFileOperateDate());
		this.FileOperateTime = aLYBankLogBSchema.getFileOperateTime();
		this.BackReason = aLYBankLogBSchema.getBackReason();
		this.BackOperator = aLYBankLogBSchema.getBackOperator();
		this.BackCom = aLYBankLogBSchema.getBackCom();
		this.ModifyDate = fDate.getDate( aLYBankLogBSchema.getModifyDate());
		this.ModifyTime = aLYBankLogBSchema.getModifyTime();
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
			if( rs.getString("BackUpNo") == null )
				this.BackUpNo = null;
			else
				this.BackUpNo = rs.getString("BackUpNo").trim();

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

			this.FileOperateDate = rs.getDate("FileOperateDate");
			if( rs.getString("FileOperateTime") == null )
				this.FileOperateTime = null;
			else
				this.FileOperateTime = rs.getString("FileOperateTime").trim();

			if( rs.getString("BackReason") == null )
				this.BackReason = null;
			else
				this.BackReason = rs.getString("BackReason").trim();

			if( rs.getString("BackOperator") == null )
				this.BackOperator = null;
			else
				this.BackOperator = rs.getString("BackOperator").trim();

			if( rs.getString("BackCom") == null )
				this.BackCom = null;
			else
				this.BackCom = rs.getString("BackCom").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LYBankLogB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankLogBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LYBankLogBSchema getSchema()
	{
		LYBankLogBSchema aLYBankLogBSchema = new LYBankLogBSchema();
		aLYBankLogBSchema.setSchema(this);
		return aLYBankLogBSchema;
	}

	public LYBankLogBDB getDB()
	{
		LYBankLogBDB aDBOper = new LYBankLogBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankLogB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BackUpNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutFileB)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FileOperateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileOperateTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankLogB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BackUpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LogType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			OutFile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InFile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			SendOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ReturnDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ReturnOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			TransOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			TotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			TotalNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			AccTotalMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			BankSuccMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			BankSuccNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			SuccMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			SuccNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			DealState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			OthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			OutFileB = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			FileOperateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			FileOperateTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			BackReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BackOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BackCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankLogBSchema";
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
		if (FCode.equalsIgnoreCase("BackUpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUpNo));
		}
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
		if (FCode.equalsIgnoreCase("FileOperateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFileOperateDate()));
		}
		if (FCode.equalsIgnoreCase("FileOperateTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileOperateTime));
		}
		if (FCode.equalsIgnoreCase("BackReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackReason));
		}
		if (FCode.equalsIgnoreCase("BackOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackOperator));
		}
		if (FCode.equalsIgnoreCase("BackCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackCom));
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
				strFieldValue = StrTool.GBKToUnicode(BackUpNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LogType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OutFile);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InFile);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SendOperator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReturnDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReturnOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(TransOperator);
				break;
			case 14:
				strFieldValue = String.valueOf(TotalMoney);
				break;
			case 15:
				strFieldValue = String.valueOf(TotalNum);
				break;
			case 16:
				strFieldValue = String.valueOf(AccTotalMoney);
				break;
			case 17:
				strFieldValue = String.valueOf(BankSuccMoney);
				break;
			case 18:
				strFieldValue = String.valueOf(BankSuccNum);
				break;
			case 19:
				strFieldValue = String.valueOf(SuccMoney);
				break;
			case 20:
				strFieldValue = String.valueOf(SuccNum);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(DealState);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(OthFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(OutFileB);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OperationType);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileOperateDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(FileOperateTime);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(BackReason);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(BackOperator);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BackCom);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 33:
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

		if (FCode.equalsIgnoreCase("BackUpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUpNo = FValue.trim();
			}
			else
				BackUpNo = null;
		}
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
		if (FCode.equalsIgnoreCase("FileOperateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FileOperateDate = fDate.getDate( FValue );
			}
			else
				FileOperateDate = null;
		}
		if (FCode.equalsIgnoreCase("FileOperateTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileOperateTime = FValue.trim();
			}
			else
				FileOperateTime = null;
		}
		if (FCode.equalsIgnoreCase("BackReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackReason = FValue.trim();
			}
			else
				BackReason = null;
		}
		if (FCode.equalsIgnoreCase("BackOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackOperator = FValue.trim();
			}
			else
				BackOperator = null;
		}
		if (FCode.equalsIgnoreCase("BackCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackCom = FValue.trim();
			}
			else
				BackCom = null;
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
		LYBankLogBSchema other = (LYBankLogBSchema)otherObject;
		return
			BackUpNo.equals(other.getBackUpNo())
			&& SerialNo.equals(other.getSerialNo())
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
			&& ComCode.equals(other.getComCode())
			&& OutFileB.equals(other.getOutFileB())
			&& SaleChnl.equals(other.getSaleChnl())
			&& OperationType.equals(other.getOperationType())
			&& fDate.getString(FileOperateDate).equals(other.getFileOperateDate())
			&& FileOperateTime.equals(other.getFileOperateTime())
			&& BackReason.equals(other.getBackReason())
			&& BackOperator.equals(other.getBackOperator())
			&& BackCom.equals(other.getBackCom())
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
		if( strFieldName.equals("BackUpNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("BankCode") ) {
			return 2;
		}
		if( strFieldName.equals("LogType") ) {
			return 3;
		}
		if( strFieldName.equals("StartDate") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("OutFile") ) {
			return 6;
		}
		if( strFieldName.equals("InFile") ) {
			return 7;
		}
		if( strFieldName.equals("SendDate") ) {
			return 8;
		}
		if( strFieldName.equals("SendOperator") ) {
			return 9;
		}
		if( strFieldName.equals("ReturnDate") ) {
			return 10;
		}
		if( strFieldName.equals("ReturnOperator") ) {
			return 11;
		}
		if( strFieldName.equals("TransDate") ) {
			return 12;
		}
		if( strFieldName.equals("TransOperator") ) {
			return 13;
		}
		if( strFieldName.equals("TotalMoney") ) {
			return 14;
		}
		if( strFieldName.equals("TotalNum") ) {
			return 15;
		}
		if( strFieldName.equals("AccTotalMoney") ) {
			return 16;
		}
		if( strFieldName.equals("BankSuccMoney") ) {
			return 17;
		}
		if( strFieldName.equals("BankSuccNum") ) {
			return 18;
		}
		if( strFieldName.equals("SuccMoney") ) {
			return 19;
		}
		if( strFieldName.equals("SuccNum") ) {
			return 20;
		}
		if( strFieldName.equals("DealState") ) {
			return 21;
		}
		if( strFieldName.equals("OthFlag") ) {
			return 22;
		}
		if( strFieldName.equals("ComCode") ) {
			return 23;
		}
		if( strFieldName.equals("OutFileB") ) {
			return 24;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 25;
		}
		if( strFieldName.equals("OperationType") ) {
			return 26;
		}
		if( strFieldName.equals("FileOperateDate") ) {
			return 27;
		}
		if( strFieldName.equals("FileOperateTime") ) {
			return 28;
		}
		if( strFieldName.equals("BackReason") ) {
			return 29;
		}
		if( strFieldName.equals("BackOperator") ) {
			return 30;
		}
		if( strFieldName.equals("BackCom") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 33;
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
				strFieldName = "BackUpNo";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "BankCode";
				break;
			case 3:
				strFieldName = "LogType";
				break;
			case 4:
				strFieldName = "StartDate";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "OutFile";
				break;
			case 7:
				strFieldName = "InFile";
				break;
			case 8:
				strFieldName = "SendDate";
				break;
			case 9:
				strFieldName = "SendOperator";
				break;
			case 10:
				strFieldName = "ReturnDate";
				break;
			case 11:
				strFieldName = "ReturnOperator";
				break;
			case 12:
				strFieldName = "TransDate";
				break;
			case 13:
				strFieldName = "TransOperator";
				break;
			case 14:
				strFieldName = "TotalMoney";
				break;
			case 15:
				strFieldName = "TotalNum";
				break;
			case 16:
				strFieldName = "AccTotalMoney";
				break;
			case 17:
				strFieldName = "BankSuccMoney";
				break;
			case 18:
				strFieldName = "BankSuccNum";
				break;
			case 19:
				strFieldName = "SuccMoney";
				break;
			case 20:
				strFieldName = "SuccNum";
				break;
			case 21:
				strFieldName = "DealState";
				break;
			case 22:
				strFieldName = "OthFlag";
				break;
			case 23:
				strFieldName = "ComCode";
				break;
			case 24:
				strFieldName = "OutFileB";
				break;
			case 25:
				strFieldName = "SaleChnl";
				break;
			case 26:
				strFieldName = "OperationType";
				break;
			case 27:
				strFieldName = "FileOperateDate";
				break;
			case 28:
				strFieldName = "FileOperateTime";
				break;
			case 29:
				strFieldName = "BackReason";
				break;
			case 30:
				strFieldName = "BackOperator";
				break;
			case 31:
				strFieldName = "BackCom";
				break;
			case 32:
				strFieldName = "ModifyDate";
				break;
			case 33:
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
		if( strFieldName.equals("BackUpNo") ) {
			return Schema.TYPE_STRING;
		}
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
		if( strFieldName.equals("FileOperateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FileOperateTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
