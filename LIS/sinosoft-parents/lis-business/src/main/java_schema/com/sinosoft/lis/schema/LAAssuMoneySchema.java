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
import com.sinosoft.lis.db.LAAssuMoneyDB;

/*
 * <p>ClassName: LAAssuMoneySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAssuMoneySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAAssuMoneySchema.class);

	// @Field
	/** 序号 */
	private String SerialNo;
	/** 管理机构 */
	private String ManageCom;
	/** 收费管理机构 */
	private String SManageCom;
	/** 收费收据号 */
	private String ReceiptNo;
	/** 展业类型 */
	private String BranchType;
	/** 代理人姓名 */
	private String AgentName;
	/** 代理人编码 */
	private String AgentCode;
	/** 押金核销状态 */
	private String AssuCheckState;
	/** 押金核销日期 */
	private Date AssuCheckDate;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 押金金额 */
	private double AssuMoney;
	/** 缴费方式 */
	private String AssuPayMode;
	/** 缴费日期 */
	private Date PayDate;
	/** 到帐日期 */
	private Date ConfMakeDate;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccCode;
	/** 银行帐户名 */
	private String AccName;
	/** 收费支票号 */
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAAssuMoneySchema()
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
		LAAssuMoneySchema cloned = (LAAssuMoneySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	* 收费必须是8位<p>
	* <p>
	* 所以ManageCom=SManageCom
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
	* 财务的登录机构
	*/
	public String getSManageCom()
	{
		return SManageCom;
	}
	public void setSManageCom(String aSManageCom)
	{
		SManageCom = aSManageCom;
	}
	/**
	* 财务岗录入<p>
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
	* 增员之前为空
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
	* 0-未核销 收费时置0<p>
	* 1-核销 增员时置同时置上押金核销日期
	*/
	public String getAssuCheckState()
	{
		return AssuCheckState;
	}
	public void setAssuCheckState(String aAssuCheckState)
	{
		AssuCheckState = aAssuCheckState;
	}
	/**
	* 人管岗增员代理人的操作日期<p>
	* <p>
	* 也即是押金核销日期<p>
	* 一笔押金只能用作一个人的增员<p>
	* <p>
	* 需要回填：收押金与增员存在时间差
	*/
	public String getAssuCheckDate()
	{
		if( AssuCheckDate != null )
			return fDate.getString(AssuCheckDate);
		else
			return null;
	}
	public void setAssuCheckDate(Date aAssuCheckDate)
	{
		AssuCheckDate = aAssuCheckDate;
	}
	public void setAssuCheckDate(String aAssuCheckDate)
	{
		if (aAssuCheckDate != null && !aAssuCheckDate.equals("") )
		{
			AssuCheckDate = fDate.getDate( aAssuCheckDate );
		}
		else
			AssuCheckDate = null;
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
	public double getAssuMoney()
	{
		return AssuMoney;
	}
	public void setAssuMoney(double aAssuMoney)
	{
		AssuMoney = aAssuMoney;
	}
	public void setAssuMoney(String aAssuMoney)
	{
		if (aAssuMoney != null && !aAssuMoney.equals(""))
		{
			Double tDouble = new Double(aAssuMoney);
			double d = tDouble.doubleValue();
			AssuMoney = d;
		}
	}

	/**
	* 1-cash<p>
	* 8-pos
	*/
	public String getAssuPayMode()
	{
		return AssuPayMode;
	}
	public void setAssuPayMode(String aAssuPayMode)
	{
		AssuPayMode = aAssuPayMode;
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

	public String getConfMakeDate()
	{
		if( ConfMakeDate != null )
			return fDate.getString(ConfMakeDate);
		else
			return null;
	}
	public void setConfMakeDate(Date aConfMakeDate)
	{
		ConfMakeDate = aConfMakeDate;
	}
	public void setConfMakeDate(String aConfMakeDate)
	{
		if (aConfMakeDate != null && !aConfMakeDate.equals("") )
		{
			ConfMakeDate = fDate.getDate( aConfMakeDate );
		}
		else
			ConfMakeDate = null;
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
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
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
	* 使用另外一个 LAAssuMoneySchema 对象给 Schema 赋值
	* @param: aLAAssuMoneySchema LAAssuMoneySchema
	**/
	public void setSchema(LAAssuMoneySchema aLAAssuMoneySchema)
	{
		this.SerialNo = aLAAssuMoneySchema.getSerialNo();
		this.ManageCom = aLAAssuMoneySchema.getManageCom();
		this.SManageCom = aLAAssuMoneySchema.getSManageCom();
		this.ReceiptNo = aLAAssuMoneySchema.getReceiptNo();
		this.BranchType = aLAAssuMoneySchema.getBranchType();
		this.AgentName = aLAAssuMoneySchema.getAgentName();
		this.AgentCode = aLAAssuMoneySchema.getAgentCode();
		this.AssuCheckState = aLAAssuMoneySchema.getAssuCheckState();
		this.AssuCheckDate = fDate.getDate( aLAAssuMoneySchema.getAssuCheckDate());
		this.IDType = aLAAssuMoneySchema.getIDType();
		this.IDNo = aLAAssuMoneySchema.getIDNo();
		this.AssuMoney = aLAAssuMoneySchema.getAssuMoney();
		this.AssuPayMode = aLAAssuMoneySchema.getAssuPayMode();
		this.PayDate = fDate.getDate( aLAAssuMoneySchema.getPayDate());
		this.ConfMakeDate = fDate.getDate( aLAAssuMoneySchema.getConfMakeDate());
		this.BankCode = aLAAssuMoneySchema.getBankCode();
		this.BankAccCode = aLAAssuMoneySchema.getBankAccCode();
		this.AccName = aLAAssuMoneySchema.getAccName();
		this.ChequeNo = aLAAssuMoneySchema.getChequeNo();
		this.Operator = aLAAssuMoneySchema.getOperator();
		this.MakeDate = fDate.getDate( aLAAssuMoneySchema.getMakeDate());
		this.MakeTime = aLAAssuMoneySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAAssuMoneySchema.getModifyDate());
		this.ModifyTime = aLAAssuMoneySchema.getModifyTime();
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

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("SManageCom") == null )
				this.SManageCom = null;
			else
				this.SManageCom = rs.getString("SManageCom").trim();

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

			if( rs.getString("AssuCheckState") == null )
				this.AssuCheckState = null;
			else
				this.AssuCheckState = rs.getString("AssuCheckState").trim();

			this.AssuCheckDate = rs.getDate("AssuCheckDate");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.AssuMoney = rs.getDouble("AssuMoney");
			if( rs.getString("AssuPayMode") == null )
				this.AssuPayMode = null;
			else
				this.AssuPayMode = rs.getString("AssuPayMode").trim();

			this.PayDate = rs.getDate("PayDate");
			this.ConfMakeDate = rs.getDate("ConfMakeDate");
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccCode") == null )
				this.BankAccCode = null;
			else
				this.BankAccCode = rs.getString("BankAccCode").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

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
			logger.debug("数据库中的LAAssuMoney表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAssuMoneySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAAssuMoneySchema getSchema()
	{
		LAAssuMoneySchema aLAAssuMoneySchema = new LAAssuMoneySchema();
		aLAAssuMoneySchema.setSchema(this);
		return aLAAssuMoneySchema;
	}

	public LAAssuMoneyDB getDB()
	{
		LAAssuMoneyDB aDBOper = new LAAssuMoneyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAssuMoney描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssuCheckState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AssuCheckDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssuMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssuPayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChequeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAssuMoney>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AssuCheckState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AssuCheckDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AssuMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			AssuPayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ConfMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BankAccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ChequeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAssuMoneySchema";
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SManageCom));
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
		if (FCode.equalsIgnoreCase("AssuCheckState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuCheckState));
		}
		if (FCode.equalsIgnoreCase("AssuCheckDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAssuCheckDate()));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("AssuMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuMoney));
		}
		if (FCode.equalsIgnoreCase("AssuPayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuPayMode));
		}
		if (FCode.equalsIgnoreCase("PayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
		}
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccCode));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AssuCheckState);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAssuCheckDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 11:
				strFieldValue = String.valueOf(AssuMoney);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AssuPayMode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BankAccCode);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ChequeNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("SManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SManageCom = FValue.trim();
			}
			else
				SManageCom = null;
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
		if (FCode.equalsIgnoreCase("AssuCheckState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssuCheckState = FValue.trim();
			}
			else
				AssuCheckState = null;
		}
		if (FCode.equalsIgnoreCase("AssuCheckDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AssuCheckDate = fDate.getDate( FValue );
			}
			else
				AssuCheckDate = null;
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
		if (FCode.equalsIgnoreCase("AssuMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AssuMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("AssuPayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssuPayMode = FValue.trim();
			}
			else
				AssuPayMode = null;
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
		if (FCode.equalsIgnoreCase("ConfMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfMakeDate = fDate.getDate( FValue );
			}
			else
				ConfMakeDate = null;
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
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
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
		LAAssuMoneySchema other = (LAAssuMoneySchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ManageCom.equals(other.getManageCom())
			&& SManageCom.equals(other.getSManageCom())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& BranchType.equals(other.getBranchType())
			&& AgentName.equals(other.getAgentName())
			&& AgentCode.equals(other.getAgentCode())
			&& AssuCheckState.equals(other.getAssuCheckState())
			&& fDate.getString(AssuCheckDate).equals(other.getAssuCheckDate())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& AssuMoney == other.getAssuMoney()
			&& AssuPayMode.equals(other.getAssuPayMode())
			&& fDate.getString(PayDate).equals(other.getPayDate())
			&& fDate.getString(ConfMakeDate).equals(other.getConfMakeDate())
			&& BankCode.equals(other.getBankCode())
			&& BankAccCode.equals(other.getBankAccCode())
			&& AccName.equals(other.getAccName())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("SManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 3;
		}
		if( strFieldName.equals("BranchType") ) {
			return 4;
		}
		if( strFieldName.equals("AgentName") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("AssuCheckState") ) {
			return 7;
		}
		if( strFieldName.equals("AssuCheckDate") ) {
			return 8;
		}
		if( strFieldName.equals("IDType") ) {
			return 9;
		}
		if( strFieldName.equals("IDNo") ) {
			return 10;
		}
		if( strFieldName.equals("AssuMoney") ) {
			return 11;
		}
		if( strFieldName.equals("AssuPayMode") ) {
			return 12;
		}
		if( strFieldName.equals("PayDate") ) {
			return 13;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("BankCode") ) {
			return 15;
		}
		if( strFieldName.equals("BankAccCode") ) {
			return 16;
		}
		if( strFieldName.equals("AccName") ) {
			return 17;
		}
		if( strFieldName.equals("ChequeNo") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "SManageCom";
				break;
			case 3:
				strFieldName = "ReceiptNo";
				break;
			case 4:
				strFieldName = "BranchType";
				break;
			case 5:
				strFieldName = "AgentName";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "AssuCheckState";
				break;
			case 8:
				strFieldName = "AssuCheckDate";
				break;
			case 9:
				strFieldName = "IDType";
				break;
			case 10:
				strFieldName = "IDNo";
				break;
			case 11:
				strFieldName = "AssuMoney";
				break;
			case 12:
				strFieldName = "AssuPayMode";
				break;
			case 13:
				strFieldName = "PayDate";
				break;
			case 14:
				strFieldName = "ConfMakeDate";
				break;
			case 15:
				strFieldName = "BankCode";
				break;
			case 16:
				strFieldName = "BankAccCode";
				break;
			case 17:
				strFieldName = "AccName";
				break;
			case 18:
				strFieldName = "ChequeNo";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SManageCom") ) {
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
		if( strFieldName.equals("AssuCheckState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssuCheckDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssuMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AssuPayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
