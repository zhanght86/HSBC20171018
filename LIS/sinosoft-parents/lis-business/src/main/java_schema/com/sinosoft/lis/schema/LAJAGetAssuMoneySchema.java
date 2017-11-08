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
import com.sinosoft.lis.db.LAJAGetAssuMoneyDB;

/*
 * <p>ClassName: LAJAGetAssuMoneySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAJAGetAssuMoneySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAJAGetAssuMoneySchema.class);

	// @Field
	/** 给付通知书号 */
	private String GetNoticeNo;
	/** 序号 */
	private String SerialNo;
	/** 管理机构 */
	private String ManageCom;
	/** 付费管理机构 */
	private String FManageCom;
	/** 收费收据号 */
	private String ReceiptNo;
	/** 展业类型 */
	private String BranchType;
	/** 代理人姓名 */
	private String AgentName;
	/** 代理人编码 */
	private String AgentCode;
	/** 离职操作日期 */
	private Date DoLiZhiDate;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 押金付费金额 */
	private double AssuGetMoney;
	/** 付费类型 */
	private String GetType;
	/** 付费确认日期 */
	private Date GetConfirmDate;
	/** 付费方式 */
	private String GetMode;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccCode;
	/** 银行在途标志 */
	private String BankOnTheWayFlag;
	/** 银行转帐成功标志 */
	private String BankSuccFlag;
	/** 付费支票号 */
	private String ChequeNo;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAJAGetAssuMoneySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GetNoticeNo";

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
		LAJAGetAssuMoneySchema cloned = (LAJAGetAssuMoneySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		GetNoticeNo = aGetNoticeNo;
	}
	/**
	* 采用序列方式存储与取数
	*/
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 登录的stationcode<p>
	* 2-8位<p>
	* <p>
	* 录错退费置<p>
	* 付费确认置<p>
	* <p>
	* 业务离职或清退不置
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 方便财务统计<p>
	* 收费管理机构与付费管理机构可能不同<p>
	* <p>
	* <p>
	* 财务录错退费置<p>
	* 财务付费确认置<p>
	* <p>
	* 业务离职或清退不置
	*/
	public String getFManageCom()
	{
		return FManageCom;
	}
	public void setFManageCom(String aFManageCom)
	{
		FManageCom = aFManageCom;
	}
	/**
	* 14位<p>
	* <p>
	* 收据号不是pk，是因为可能有重复
	*/
	public String getReceiptNo()
	{
		return ReceiptNo;
	}
	public void setReceiptNo(String aReceiptNo)
	{
		ReceiptNo = aReceiptNo;
	}
	/**
	* 冗余字段 目前只有这几个渠道支持押金管理<p>
	* <p>
	* 1-个人营销员<p>
	* <p>
	* 4-续收外勤<p>
	* 99-收展
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	public String getAgentName()
	{
		return AgentName;
	}
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	/**
	* 增员后回写代理人编码<p>
	* 增员之前为空<p>
	* <p>
	* 录错退费也为空
	*/
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/**
	* 业务置，录错退费不置这个字段<p>
	* <p>
	* <p>
	* 此字段和agentcode可以判断出代理人是否在职<p>
	* <p>
	* agentcode is not null and dolizhidate is null 在职<p>
	* agentcode is not null and dolizhidate is not null 离职<p>
	* agentcode is null 表录错退费 显示为空
	*/
	public String getDoLiZhiDate()
	{
		if( DoLiZhiDate != null )
			return fDate.getString(DoLiZhiDate);
		else
			return null;
	}
	public void setDoLiZhiDate(Date aDoLiZhiDate)
	{
		DoLiZhiDate = aDoLiZhiDate;
	}
	public void setDoLiZhiDate(String aDoLiZhiDate)
	{
		if (aDoLiZhiDate != null && !aDoLiZhiDate.equals("") )
		{
			DoLiZhiDate = fDate.getDate( aDoLiZhiDate );
		}
		else
			DoLiZhiDate = null;
	}

	/**
	* I-身份证<p>
	* <p>
	* O-其他如军官证侨胞证
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	/**
	* 长度为最长的身份证18位
	*/
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	/**
	* =LAAssuMoney.AssuMoney
	*/
	public double getAssuGetMoney()
	{
		return AssuGetMoney;
	}
	public void setAssuGetMoney(double aAssuGetMoney)
	{
		AssuGetMoney = aAssuGetMoney;
	}
	public void setAssuGetMoney(String aAssuGetMoney)
	{
		if (aAssuGetMoney != null && !aAssuGetMoney.equals(""))
		{
			Double tDouble = new Double(aAssuGetMoney);
			double d = tDouble.doubleValue();
			AssuGetMoney = d;
		}
	}

	/**
	* 0-录错退费<p>
	* <p>
	* 1-正常离职付费（包括考核清退）同时置上离职操作日期
	*/
	public String getGetType()
	{
		return GetType;
	}
	public void setGetType(String aGetType)
	{
		GetType = aGetType;
	}
	/**
	* 财务付费确认日期置<p>
	* <p>
	* 以这个日期作为保证金已未退的标志
	*/
	public String getGetConfirmDate()
	{
		if( GetConfirmDate != null )
			return fDate.getString(GetConfirmDate);
		else
			return null;
	}
	public void setGetConfirmDate(Date aGetConfirmDate)
	{
		GetConfirmDate = aGetConfirmDate;
	}
	public void setGetConfirmDate(String aGetConfirmDate)
	{
		if (aGetConfirmDate != null && !aGetConfirmDate.equals("") )
		{
			GetConfirmDate = fDate.getDate( aGetConfirmDate );
		}
		else
			GetConfirmDate = null;
	}

	/**
	* 付费方式1为现金，2为现金支票，3为支票，4为银行转帐
	*/
	public String getGetMode()
	{
		return GetMode;
	}
	public void setGetMode(String aGetMode)
	{
		GetMode = aGetMode;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankAccCode()
	{
		return BankAccCode;
	}
	public void setBankAccCode(String aBankAccCode)
	{
		BankAccCode = aBankAccCode;
	}
	public String getBankOnTheWayFlag()
	{
		return BankOnTheWayFlag;
	}
	public void setBankOnTheWayFlag(String aBankOnTheWayFlag)
	{
		BankOnTheWayFlag = aBankOnTheWayFlag;
	}
	public String getBankSuccFlag()
	{
		return BankSuccFlag;
	}
	public void setBankSuccFlag(String aBankSuccFlag)
	{
		BankSuccFlag = aBankSuccFlag;
	}
	public String getChequeNo()
	{
		return ChequeNo;
	}
	public void setChequeNo(String aChequeNo)
	{
		ChequeNo = aChequeNo;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
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
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LAJAGetAssuMoneySchema 对象给 Schema 赋值
	* @param: aLAJAGetAssuMoneySchema LAJAGetAssuMoneySchema
	**/
	public void setSchema(LAJAGetAssuMoneySchema aLAJAGetAssuMoneySchema)
	{
		this.GetNoticeNo = aLAJAGetAssuMoneySchema.getGetNoticeNo();
		this.SerialNo = aLAJAGetAssuMoneySchema.getSerialNo();
		this.ManageCom = aLAJAGetAssuMoneySchema.getManageCom();
		this.FManageCom = aLAJAGetAssuMoneySchema.getFManageCom();
		this.ReceiptNo = aLAJAGetAssuMoneySchema.getReceiptNo();
		this.BranchType = aLAJAGetAssuMoneySchema.getBranchType();
		this.AgentName = aLAJAGetAssuMoneySchema.getAgentName();
		this.AgentCode = aLAJAGetAssuMoneySchema.getAgentCode();
		this.DoLiZhiDate = fDate.getDate( aLAJAGetAssuMoneySchema.getDoLiZhiDate());
		this.IDType = aLAJAGetAssuMoneySchema.getIDType();
		this.IDNo = aLAJAGetAssuMoneySchema.getIDNo();
		this.AssuGetMoney = aLAJAGetAssuMoneySchema.getAssuGetMoney();
		this.GetType = aLAJAGetAssuMoneySchema.getGetType();
		this.GetConfirmDate = fDate.getDate( aLAJAGetAssuMoneySchema.getGetConfirmDate());
		this.GetMode = aLAJAGetAssuMoneySchema.getGetMode();
		this.BankCode = aLAJAGetAssuMoneySchema.getBankCode();
		this.BankAccCode = aLAJAGetAssuMoneySchema.getBankAccCode();
		this.BankOnTheWayFlag = aLAJAGetAssuMoneySchema.getBankOnTheWayFlag();
		this.BankSuccFlag = aLAJAGetAssuMoneySchema.getBankSuccFlag();
		this.ChequeNo = aLAJAGetAssuMoneySchema.getChequeNo();
		this.Operator = aLAJAGetAssuMoneySchema.getOperator();
		this.MakeDate = fDate.getDate( aLAJAGetAssuMoneySchema.getMakeDate());
		this.MakeTime = aLAJAGetAssuMoneySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAJAGetAssuMoneySchema.getModifyDate());
		this.ModifyTime = aLAJAGetAssuMoneySchema.getModifyTime();
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
			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("FManageCom") == null )
				this.FManageCom = null;
			else
				this.FManageCom = rs.getString("FManageCom").trim();

			if( rs.getString("ReceiptNo") == null )
				this.ReceiptNo = null;
			else
				this.ReceiptNo = rs.getString("ReceiptNo").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.DoLiZhiDate = rs.getDate("DoLiZhiDate");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.AssuGetMoney = rs.getDouble("AssuGetMoney");
			if( rs.getString("GetType") == null )
				this.GetType = null;
			else
				this.GetType = rs.getString("GetType").trim();

			this.GetConfirmDate = rs.getDate("GetConfirmDate");
			if( rs.getString("GetMode") == null )
				this.GetMode = null;
			else
				this.GetMode = rs.getString("GetMode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccCode") == null )
				this.BankAccCode = null;
			else
				this.BankAccCode = rs.getString("BankAccCode").trim();

			if( rs.getString("BankOnTheWayFlag") == null )
				this.BankOnTheWayFlag = null;
			else
				this.BankOnTheWayFlag = rs.getString("BankOnTheWayFlag").trim();

			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			if( rs.getString("ChequeNo") == null )
				this.ChequeNo = null;
			else
				this.ChequeNo = rs.getString("ChequeNo").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAJAGetAssuMoney表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAJAGetAssuMoneySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAJAGetAssuMoneySchema getSchema()
	{
		LAJAGetAssuMoneySchema aLAJAGetAssuMoneySchema = new LAJAGetAssuMoneySchema();
		aLAJAGetAssuMoneySchema.setSchema(this);
		return aLAJAGetAssuMoneySchema;
	}

	public LAJAGetAssuMoneyDB getDB()
	{
		LAJAGetAssuMoneyDB aDBOper = new LAJAGetAssuMoneyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAJAGetAssuMoney描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DoLiZhiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssuGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOnTheWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChequeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAJAGetAssuMoney>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DoLiZhiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AssuGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			GetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GetConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			GetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BankAccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BankOnTheWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ChequeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAJAGetAssuMoneySchema";
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
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("FManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FManageCom));
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptNo));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("DoLiZhiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDoLiZhiDate()));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("AssuGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuGetMoney));
		}
		if (FCode.equalsIgnoreCase("GetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType));
		}
		if (FCode.equalsIgnoreCase("GetConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMode));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccCode));
		}
		if (FCode.equalsIgnoreCase("BankOnTheWayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankOnTheWayFlag));
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccFlag));
		}
		if (FCode.equalsIgnoreCase("ChequeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChequeNo));
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
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDoLiZhiDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 11:
				strFieldValue = String.valueOf(AssuGetMoney);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GetType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetConfirmDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GetMode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BankAccCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BankOnTheWayFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ChequeNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("FManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FManageCom = FValue.trim();
			}
			else
				FManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptNo = FValue.trim();
			}
			else
				ReceiptNo = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("DoLiZhiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DoLiZhiDate = fDate.getDate( FValue );
			}
			else
				DoLiZhiDate = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("AssuGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssuGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType = FValue.trim();
			}
			else
				GetType = null;
		}
		if (FCode.equalsIgnoreCase("GetConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetConfirmDate = fDate.getDate( FValue );
			}
			else
				GetConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("GetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetMode = FValue.trim();
			}
			else
				GetMode = null;
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
		if (FCode.equalsIgnoreCase("BankAccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccCode = FValue.trim();
			}
			else
				BankAccCode = null;
		}
		if (FCode.equalsIgnoreCase("BankOnTheWayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankOnTheWayFlag = FValue.trim();
			}
			else
				BankOnTheWayFlag = null;
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
		if (FCode.equalsIgnoreCase("ChequeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChequeNo = FValue.trim();
			}
			else
				ChequeNo = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAJAGetAssuMoneySchema other = (LAJAGetAssuMoneySchema)otherObject;
		return
			GetNoticeNo.equals(other.getGetNoticeNo())
			&& SerialNo.equals(other.getSerialNo())
			&& ManageCom.equals(other.getManageCom())
			&& FManageCom.equals(other.getFManageCom())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& BranchType.equals(other.getBranchType())
			&& AgentName.equals(other.getAgentName())
			&& AgentCode.equals(other.getAgentCode())
			&& fDate.getString(DoLiZhiDate).equals(other.getDoLiZhiDate())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& AssuGetMoney == other.getAssuGetMoney()
			&& GetType.equals(other.getGetType())
			&& fDate.getString(GetConfirmDate).equals(other.getGetConfirmDate())
			&& GetMode.equals(other.getGetMode())
			&& BankCode.equals(other.getBankCode())
			&& BankAccCode.equals(other.getBankAccCode())
			&& BankOnTheWayFlag.equals(other.getBankOnTheWayFlag())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& ChequeNo.equals(other.getChequeNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("FManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 4;
		}
		if( strFieldName.equals("BranchType") ) {
			return 5;
		}
		if( strFieldName.equals("AgentName") ) {
			return 6;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 7;
		}
		if( strFieldName.equals("DoLiZhiDate") ) {
			return 8;
		}
		if( strFieldName.equals("IDType") ) {
			return 9;
		}
		if( strFieldName.equals("IDNo") ) {
			return 10;
		}
		if( strFieldName.equals("AssuGetMoney") ) {
			return 11;
		}
		if( strFieldName.equals("GetType") ) {
			return 12;
		}
		if( strFieldName.equals("GetConfirmDate") ) {
			return 13;
		}
		if( strFieldName.equals("GetMode") ) {
			return 14;
		}
		if( strFieldName.equals("BankCode") ) {
			return 15;
		}
		if( strFieldName.equals("BankAccCode") ) {
			return 16;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return 17;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 18;
		}
		if( strFieldName.equals("ChequeNo") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "GetNoticeNo";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "FManageCom";
				break;
			case 4:
				strFieldName = "ReceiptNo";
				break;
			case 5:
				strFieldName = "BranchType";
				break;
			case 6:
				strFieldName = "AgentName";
				break;
			case 7:
				strFieldName = "AgentCode";
				break;
			case 8:
				strFieldName = "DoLiZhiDate";
				break;
			case 9:
				strFieldName = "IDType";
				break;
			case 10:
				strFieldName = "IDNo";
				break;
			case 11:
				strFieldName = "AssuGetMoney";
				break;
			case 12:
				strFieldName = "GetType";
				break;
			case 13:
				strFieldName = "GetConfirmDate";
				break;
			case 14:
				strFieldName = "GetMode";
				break;
			case 15:
				strFieldName = "BankCode";
				break;
			case 16:
				strFieldName = "BankAccCode";
				break;
			case 17:
				strFieldName = "BankOnTheWayFlag";
				break;
			case 18:
				strFieldName = "BankSuccFlag";
				break;
			case 19:
				strFieldName = "ChequeNo";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoLiZhiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssuGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankOnTheWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChequeNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
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
