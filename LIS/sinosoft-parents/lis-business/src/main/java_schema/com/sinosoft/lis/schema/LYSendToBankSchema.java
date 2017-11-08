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
import com.sinosoft.lis.db.LYSendToBankDB;

/*
 * <p>ClassName: LYSendToBankSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYSendToBankSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LYSendToBankSchema.class);

	// @Field
	/** 批次号 */
	private String SerialNo;
	/** 处理代收/代付状态 */
	private String DealType;
	/** 数据类型 */
	private String DataType;
	/** 交退费编码 */
	private String PayCode;
	/** 交退费类型 */
	private String PayType;
	/** 姓名 */
	private String Name;
	/** 银行代码 */
	private String BankCode;
	/** 帐户类型 */
	private String AccType;
	/** 帐户名 */
	private String AccName;
	/** 帐号 */
	private String AccNo;
	/** 保单号码 */
	private String PolNo;
	/** 号码类型 */
	private String NoType;
	/** 机构编码 */
	private String ComCode;
	/** 代理人编码 */
	private String AgentCode;
	/** 缴费退费金额 */
	private double PayMoney;
	/** 发送日期 */
	private Date SendDate;
	/** 银行扣款日期 */
	private Date BankDealDate;
	/** 银行扣款时间 */
	private Date BankDealTime;
	/** 银行成功标记 */
	private String BankSuccFlag;
	/** 核销标记 */
	private String VertifyFlag;
	/** 备注 */
	private String Remark;
	/** 数据转换标志 */
	private String ConvertFlag;
	/** 扣款类型 */
	private String DoType;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 险种编码 */
	private String RiskCode;
	/** 顺序号 */
	private int SerNo;
	/** 银行名称 */
	private String BankName;
	/** 收款银行代码 */
	private String InBankCode;
	/** 收款银行名称 */
	private String InBankName;
	/** 收款帐户类型 */
	private String InAccType;
	/** 收款帐户名 */
	private String InAccName;
	/** 收款帐号 */
	private String InAccNo;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 备用字段1 */
	private String StandByFlag1;
	/** 备用字段2 */
	private String StandByFlag2;
	/** 备用字段3 */
	private String StandByFlag3;
	/** 备用字段4 */
	private String StandByFlag4;
	/** 备用字段5 */
	private String StandByFlag5;

	public static final int FIELDNUM = 40;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LYSendToBankSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SerialNo";
		pk[1] = "DealType";
		pk[2] = "PayCode";
		pk[3] = "PolNo";

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
		LYSendToBankSchema cloned = (LYSendToBankSchema)super.clone();
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
	* 流水号
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
	* 代收－S<p>
	* 代付－F
	*/
	public String getDealType()
	{
		return DealType;
	}
	public void setDealType(String aDealType)
	{
		DealType = aDealType;
	}
	/**
	* S －－ 收费<p>
	* F －－ 付费
	*/
	public String getDataType()
	{
		return DataType;
	}
	public void setDataType(String aDataType)
	{
		DataType = aDataType;
	}
	/**
	* 代收－LJSPay的GetNoticeNo<p>
	* 代付－LJAGet的ActuGetNo
	*/
	public String getPayCode()
	{
		return PayCode;
	}
	public void setPayCode(String aPayCode)
	{
		PayCode = aPayCode;
	}
	public String getPayType()
	{
		return PayType;
	}
	public void setPayType(String aPayType)
	{
		PayType = aPayType;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public String getAccNo()
	{
		return AccNo;
	}
	public void setAccNo(String aAccNo)
	{
		AccNo = aAccNo;
	}
	/**
	* 新契约--投保单号<p>
	* 续期  --保单号<p>
	* 保全  --保全受理号
	*/
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/**
	* 对应应收表中的其他号码类型。
	*/
	public String getNoType()
	{
		return NoType;
	}
	public void setNoType(String aNoType)
	{
		NoType = aNoType;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
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

	public String getBankDealDate()
	{
		if( BankDealDate != null )
			return fDate.getString(BankDealDate);
		else
			return null;
	}
	public void setBankDealDate(Date aBankDealDate)
	{
		BankDealDate = aBankDealDate;
	}
	public void setBankDealDate(String aBankDealDate)
	{
		if (aBankDealDate != null && !aBankDealDate.equals("") )
		{
			BankDealDate = fDate.getDate( aBankDealDate );
		}
		else
			BankDealDate = null;
	}

	public String getBankDealTime()
	{
		if( BankDealTime != null )
			return fDate.getString(BankDealTime);
		else
			return null;
	}
	public void setBankDealTime(Date aBankDealTime)
	{
		BankDealTime = aBankDealTime;
	}
	public void setBankDealTime(String aBankDealTime)
	{
		if (aBankDealTime != null && !aBankDealTime.equals("") )
		{
			BankDealTime = fDate.getDate( aBankDealTime );
		}
		else
			BankDealTime = null;
	}

	/**
	* 在LdCode1表中有详细
	*/
	public String getBankSuccFlag()
	{
		return BankSuccFlag;
	}
	public void setBankSuccFlag(String aBankSuccFlag)
	{
		BankSuccFlag = aBankSuccFlag;
	}
	public String getVertifyFlag()
	{
		return VertifyFlag;
	}
	public void setVertifyFlag(String aVertifyFlag)
	{
		VertifyFlag = aVertifyFlag;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getConvertFlag()
	{
		return ConvertFlag;
	}
	public void setConvertFlag(String aConvertFlag)
	{
		ConvertFlag = aConvertFlag;
	}
	/**
	* 1 －－ 根据保险公司催收（付）扣款。<p>
	* 2 －－ 根据首期投保单应交直接在银行缴纳，不用保险公司给数据。
	*/
	public String getDoType()
	{
		return DoType;
	}
	public void setDoType(String aDoType)
	{
		DoType = aDoType;
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
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 用于此批次号下的每笔数据
	*/
	public int getSerNo()
	{
		return SerNo;
	}
	public void setSerNo(int aSerNo)
	{
		SerNo = aSerNo;
	}
	public void setSerNo(String aSerNo)
	{
		if (aSerNo != null && !aSerNo.equals(""))
		{
			Integer tInteger = new Integer(aSerNo);
			int i = tInteger.intValue();
			SerNo = i;
		}
	}

	public String getBankName()
	{
		return BankName;
	}
	public void setBankName(String aBankName)
	{
		BankName = aBankName;
	}
	public String getInBankCode()
	{
		return InBankCode;
	}
	public void setInBankCode(String aInBankCode)
	{
		InBankCode = aInBankCode;
	}
	public String getInBankName()
	{
		return InBankName;
	}
	public void setInBankName(String aInBankName)
	{
		InBankName = aInBankName;
	}
	public String getInAccType()
	{
		return InAccType;
	}
	public void setInAccType(String aInAccType)
	{
		InAccType = aInAccType;
	}
	public String getInAccName()
	{
		return InAccName;
	}
	public void setInAccName(String aInAccName)
	{
		InAccName = aInAccName;
	}
	public String getInAccNo()
	{
		return InAccNo;
	}
	public void setInAccNo(String aInAccNo)
	{
		InAccNo = aInAccNo;
	}
	/**
	* 0  --  身份证<p>
	* 1  --  护照<p>
	* 2  --  军官证<p>
	* 3  --  驾照<p>
	* 4  --  户口本<p>
	* 5  --  学生证<p>
	* 6  --  工作证<p>
	* 9  --  无证件
	*/
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}
	public String getStandByFlag3()
	{
		return StandByFlag3;
	}
	public void setStandByFlag3(String aStandByFlag3)
	{
		StandByFlag3 = aStandByFlag3;
	}
	public String getStandByFlag4()
	{
		return StandByFlag4;
	}
	public void setStandByFlag4(String aStandByFlag4)
	{
		StandByFlag4 = aStandByFlag4;
	}
	public String getStandByFlag5()
	{
		return StandByFlag5;
	}
	public void setStandByFlag5(String aStandByFlag5)
	{
		StandByFlag5 = aStandByFlag5;
	}

	/**
	* 使用另外一个 LYSendToBankSchema 对象给 Schema 赋值
	* @param: aLYSendToBankSchema LYSendToBankSchema
	**/
	public void setSchema(LYSendToBankSchema aLYSendToBankSchema)
	{
		this.SerialNo = aLYSendToBankSchema.getSerialNo();
		this.DealType = aLYSendToBankSchema.getDealType();
		this.DataType = aLYSendToBankSchema.getDataType();
		this.PayCode = aLYSendToBankSchema.getPayCode();
		this.PayType = aLYSendToBankSchema.getPayType();
		this.Name = aLYSendToBankSchema.getName();
		this.BankCode = aLYSendToBankSchema.getBankCode();
		this.AccType = aLYSendToBankSchema.getAccType();
		this.AccName = aLYSendToBankSchema.getAccName();
		this.AccNo = aLYSendToBankSchema.getAccNo();
		this.PolNo = aLYSendToBankSchema.getPolNo();
		this.NoType = aLYSendToBankSchema.getNoType();
		this.ComCode = aLYSendToBankSchema.getComCode();
		this.AgentCode = aLYSendToBankSchema.getAgentCode();
		this.PayMoney = aLYSendToBankSchema.getPayMoney();
		this.SendDate = fDate.getDate( aLYSendToBankSchema.getSendDate());
		this.BankDealDate = fDate.getDate( aLYSendToBankSchema.getBankDealDate());
		this.BankDealTime = fDate.getDate( aLYSendToBankSchema.getBankDealTime());
		this.BankSuccFlag = aLYSendToBankSchema.getBankSuccFlag();
		this.VertifyFlag = aLYSendToBankSchema.getVertifyFlag();
		this.Remark = aLYSendToBankSchema.getRemark();
		this.ConvertFlag = aLYSendToBankSchema.getConvertFlag();
		this.DoType = aLYSendToBankSchema.getDoType();
		this.ModifyDate = fDate.getDate( aLYSendToBankSchema.getModifyDate());
		this.ModifyTime = aLYSendToBankSchema.getModifyTime();
		this.RiskCode = aLYSendToBankSchema.getRiskCode();
		this.SerNo = aLYSendToBankSchema.getSerNo();
		this.BankName = aLYSendToBankSchema.getBankName();
		this.InBankCode = aLYSendToBankSchema.getInBankCode();
		this.InBankName = aLYSendToBankSchema.getInBankName();
		this.InAccType = aLYSendToBankSchema.getInAccType();
		this.InAccName = aLYSendToBankSchema.getInAccName();
		this.InAccNo = aLYSendToBankSchema.getInAccNo();
		this.IDType = aLYSendToBankSchema.getIDType();
		this.IDNo = aLYSendToBankSchema.getIDNo();
		this.StandByFlag1 = aLYSendToBankSchema.getStandByFlag1();
		this.StandByFlag2 = aLYSendToBankSchema.getStandByFlag2();
		this.StandByFlag3 = aLYSendToBankSchema.getStandByFlag3();
		this.StandByFlag4 = aLYSendToBankSchema.getStandByFlag4();
		this.StandByFlag5 = aLYSendToBankSchema.getStandByFlag5();
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

			if( rs.getString("DealType") == null )
				this.DealType = null;
			else
				this.DealType = rs.getString("DealType").trim();

			if( rs.getString("DataType") == null )
				this.DataType = null;
			else
				this.DataType = rs.getString("DataType").trim();

			if( rs.getString("PayCode") == null )
				this.PayCode = null;
			else
				this.PayCode = rs.getString("PayCode").trim();

			if( rs.getString("PayType") == null )
				this.PayType = null;
			else
				this.PayType = rs.getString("PayType").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("AccNo") == null )
				this.AccNo = null;
			else
				this.AccNo = rs.getString("AccNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("NoType") == null )
				this.NoType = null;
			else
				this.NoType = rs.getString("NoType").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			this.SendDate = rs.getDate("SendDate");
			this.BankDealDate = rs.getDate("BankDealDate");
			this.BankDealTime = rs.getDate("BankDealTime");
			if( rs.getString("BankSuccFlag") == null )
				this.BankSuccFlag = null;
			else
				this.BankSuccFlag = rs.getString("BankSuccFlag").trim();

			if( rs.getString("VertifyFlag") == null )
				this.VertifyFlag = null;
			else
				this.VertifyFlag = rs.getString("VertifyFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ConvertFlag") == null )
				this.ConvertFlag = null;
			else
				this.ConvertFlag = rs.getString("ConvertFlag").trim();

			if( rs.getString("DoType") == null )
				this.DoType = null;
			else
				this.DoType = rs.getString("DoType").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.SerNo = rs.getInt("SerNo");
			if( rs.getString("BankName") == null )
				this.BankName = null;
			else
				this.BankName = rs.getString("BankName").trim();

			if( rs.getString("InBankCode") == null )
				this.InBankCode = null;
			else
				this.InBankCode = rs.getString("InBankCode").trim();

			if( rs.getString("InBankName") == null )
				this.InBankName = null;
			else
				this.InBankName = rs.getString("InBankName").trim();

			if( rs.getString("InAccType") == null )
				this.InAccType = null;
			else
				this.InAccType = rs.getString("InAccType").trim();

			if( rs.getString("InAccName") == null )
				this.InAccName = null;
			else
				this.InAccName = rs.getString("InAccName").trim();

			if( rs.getString("InAccNo") == null )
				this.InAccNo = null;
			else
				this.InAccNo = rs.getString("InAccNo").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("StandByFlag3") == null )
				this.StandByFlag3 = null;
			else
				this.StandByFlag3 = rs.getString("StandByFlag3").trim();

			if( rs.getString("StandByFlag4") == null )
				this.StandByFlag4 = null;
			else
				this.StandByFlag4 = rs.getString("StandByFlag4").trim();

			if( rs.getString("StandByFlag5") == null )
				this.StandByFlag5 = null;
			else
				this.StandByFlag5 = rs.getString("StandByFlag5").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LYSendToBank表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYSendToBankSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LYSendToBankSchema getSchema()
	{
		LYSendToBankSchema aLYSendToBankSchema = new LYSendToBankSchema();
		aLYSendToBankSchema.setSchema(this);
		return aLYSendToBankSchema;
	}

	public LYSendToBankDB getDB()
	{
		LYSendToBankDB aDBOper = new LYSendToBankDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYSendToBank描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DataType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BankDealDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BankDealTime ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankSuccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VertifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConvertFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag5));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYSendToBank>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DealType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DataType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			NoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			BankDealDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			BankDealTime = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			BankSuccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			VertifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ConvertFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			DoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			SerNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			BankName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			InBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			InBankName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			InAccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			InAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			StandByFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			StandByFlag5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYSendToBankSchema";
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
		if (FCode.equalsIgnoreCase("DealType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealType));
		}
		if (FCode.equalsIgnoreCase("DataType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataType));
		}
		if (FCode.equalsIgnoreCase("PayCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCode));
		}
		if (FCode.equalsIgnoreCase("PayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayType));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("NoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoType));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
		}
		if (FCode.equalsIgnoreCase("BankDealDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBankDealDate()));
		}
		if (FCode.equalsIgnoreCase("BankDealTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBankDealTime()));
		}
		if (FCode.equalsIgnoreCase("BankSuccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankSuccFlag));
		}
		if (FCode.equalsIgnoreCase("VertifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VertifyFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ConvertFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConvertFlag));
		}
		if (FCode.equalsIgnoreCase("DoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoType));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("SerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerNo));
		}
		if (FCode.equalsIgnoreCase("BankName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankName));
		}
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankCode));
		}
		if (FCode.equalsIgnoreCase("InBankName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankName));
		}
		if (FCode.equalsIgnoreCase("InAccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAccType));
		}
		if (FCode.equalsIgnoreCase("InAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAccName));
		}
		if (FCode.equalsIgnoreCase("InAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAccNo));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
		}
		if (FCode.equalsIgnoreCase("StandByFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag4));
		}
		if (FCode.equalsIgnoreCase("StandByFlag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag5));
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
				strFieldValue = StrTool.GBKToUnicode(DealType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DataType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(NoType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 14:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBankDealDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBankDealTime()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BankSuccFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(VertifyFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ConvertFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(DoType);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 26:
				strFieldValue = String.valueOf(SerNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BankName);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(InBankCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(InBankName);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(InAccType);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(InAccName);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InAccNo);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag4);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag5);
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
		if (FCode.equalsIgnoreCase("DealType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealType = FValue.trim();
			}
			else
				DealType = null;
		}
		if (FCode.equalsIgnoreCase("DataType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataType = FValue.trim();
			}
			else
				DataType = null;
		}
		if (FCode.equalsIgnoreCase("PayCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayCode = FValue.trim();
			}
			else
				PayCode = null;
		}
		if (FCode.equalsIgnoreCase("PayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayType = FValue.trim();
			}
			else
				PayType = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
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
		if (FCode.equalsIgnoreCase("AccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccNo = FValue.trim();
			}
			else
				AccNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("NoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoType = FValue.trim();
			}
			else
				NoType = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SendDate = fDate.getDate( FValue );
			}
			else
				SendDate = null;
		}
		if (FCode.equalsIgnoreCase("BankDealDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BankDealDate = fDate.getDate( FValue );
			}
			else
				BankDealDate = null;
		}
		if (FCode.equalsIgnoreCase("BankDealTime"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BankDealTime = fDate.getDate( FValue );
			}
			else
				BankDealTime = null;
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
		if (FCode.equalsIgnoreCase("VertifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VertifyFlag = FValue.trim();
			}
			else
				VertifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("ConvertFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConvertFlag = FValue.trim();
			}
			else
				ConvertFlag = null;
		}
		if (FCode.equalsIgnoreCase("DoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoType = FValue.trim();
			}
			else
				DoType = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("SerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("BankName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankName = FValue.trim();
			}
			else
				BankName = null;
		}
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankCode = FValue.trim();
			}
			else
				InBankCode = null;
		}
		if (FCode.equalsIgnoreCase("InBankName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankName = FValue.trim();
			}
			else
				InBankName = null;
		}
		if (FCode.equalsIgnoreCase("InAccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAccType = FValue.trim();
			}
			else
				InAccType = null;
		}
		if (FCode.equalsIgnoreCase("InAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAccName = FValue.trim();
			}
			else
				InAccName = null;
		}
		if (FCode.equalsIgnoreCase("InAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAccNo = FValue.trim();
			}
			else
				InAccNo = null;
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
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag3 = FValue.trim();
			}
			else
				StandByFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag4 = FValue.trim();
			}
			else
				StandByFlag4 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag5 = FValue.trim();
			}
			else
				StandByFlag5 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LYSendToBankSchema other = (LYSendToBankSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& DealType.equals(other.getDealType())
			&& DataType.equals(other.getDataType())
			&& PayCode.equals(other.getPayCode())
			&& PayType.equals(other.getPayType())
			&& Name.equals(other.getName())
			&& BankCode.equals(other.getBankCode())
			&& AccType.equals(other.getAccType())
			&& AccName.equals(other.getAccName())
			&& AccNo.equals(other.getAccNo())
			&& PolNo.equals(other.getPolNo())
			&& NoType.equals(other.getNoType())
			&& ComCode.equals(other.getComCode())
			&& AgentCode.equals(other.getAgentCode())
			&& PayMoney == other.getPayMoney()
			&& fDate.getString(SendDate).equals(other.getSendDate())
			&& fDate.getString(BankDealDate).equals(other.getBankDealDate())
			&& fDate.getString(BankDealTime).equals(other.getBankDealTime())
			&& BankSuccFlag.equals(other.getBankSuccFlag())
			&& VertifyFlag.equals(other.getVertifyFlag())
			&& Remark.equals(other.getRemark())
			&& ConvertFlag.equals(other.getConvertFlag())
			&& DoType.equals(other.getDoType())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RiskCode.equals(other.getRiskCode())
			&& SerNo == other.getSerNo()
			&& BankName.equals(other.getBankName())
			&& InBankCode.equals(other.getInBankCode())
			&& InBankName.equals(other.getInBankName())
			&& InAccType.equals(other.getInAccType())
			&& InAccName.equals(other.getInAccName())
			&& InAccNo.equals(other.getInAccNo())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& StandByFlag3.equals(other.getStandByFlag3())
			&& StandByFlag4.equals(other.getStandByFlag4())
			&& StandByFlag5.equals(other.getStandByFlag5());
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
		if( strFieldName.equals("DealType") ) {
			return 1;
		}
		if( strFieldName.equals("DataType") ) {
			return 2;
		}
		if( strFieldName.equals("PayCode") ) {
			return 3;
		}
		if( strFieldName.equals("PayType") ) {
			return 4;
		}
		if( strFieldName.equals("Name") ) {
			return 5;
		}
		if( strFieldName.equals("BankCode") ) {
			return 6;
		}
		if( strFieldName.equals("AccType") ) {
			return 7;
		}
		if( strFieldName.equals("AccName") ) {
			return 8;
		}
		if( strFieldName.equals("AccNo") ) {
			return 9;
		}
		if( strFieldName.equals("PolNo") ) {
			return 10;
		}
		if( strFieldName.equals("NoType") ) {
			return 11;
		}
		if( strFieldName.equals("ComCode") ) {
			return 12;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 13;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 14;
		}
		if( strFieldName.equals("SendDate") ) {
			return 15;
		}
		if( strFieldName.equals("BankDealDate") ) {
			return 16;
		}
		if( strFieldName.equals("BankDealTime") ) {
			return 17;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return 18;
		}
		if( strFieldName.equals("VertifyFlag") ) {
			return 19;
		}
		if( strFieldName.equals("Remark") ) {
			return 20;
		}
		if( strFieldName.equals("ConvertFlag") ) {
			return 21;
		}
		if( strFieldName.equals("DoType") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 25;
		}
		if( strFieldName.equals("SerNo") ) {
			return 26;
		}
		if( strFieldName.equals("BankName") ) {
			return 27;
		}
		if( strFieldName.equals("InBankCode") ) {
			return 28;
		}
		if( strFieldName.equals("InBankName") ) {
			return 29;
		}
		if( strFieldName.equals("InAccType") ) {
			return 30;
		}
		if( strFieldName.equals("InAccName") ) {
			return 31;
		}
		if( strFieldName.equals("InAccNo") ) {
			return 32;
		}
		if( strFieldName.equals("IDType") ) {
			return 33;
		}
		if( strFieldName.equals("IDNo") ) {
			return 34;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 35;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 36;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return 37;
		}
		if( strFieldName.equals("StandByFlag4") ) {
			return 38;
		}
		if( strFieldName.equals("StandByFlag5") ) {
			return 39;
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
				strFieldName = "DealType";
				break;
			case 2:
				strFieldName = "DataType";
				break;
			case 3:
				strFieldName = "PayCode";
				break;
			case 4:
				strFieldName = "PayType";
				break;
			case 5:
				strFieldName = "Name";
				break;
			case 6:
				strFieldName = "BankCode";
				break;
			case 7:
				strFieldName = "AccType";
				break;
			case 8:
				strFieldName = "AccName";
				break;
			case 9:
				strFieldName = "AccNo";
				break;
			case 10:
				strFieldName = "PolNo";
				break;
			case 11:
				strFieldName = "NoType";
				break;
			case 12:
				strFieldName = "ComCode";
				break;
			case 13:
				strFieldName = "AgentCode";
				break;
			case 14:
				strFieldName = "PayMoney";
				break;
			case 15:
				strFieldName = "SendDate";
				break;
			case 16:
				strFieldName = "BankDealDate";
				break;
			case 17:
				strFieldName = "BankDealTime";
				break;
			case 18:
				strFieldName = "BankSuccFlag";
				break;
			case 19:
				strFieldName = "VertifyFlag";
				break;
			case 20:
				strFieldName = "Remark";
				break;
			case 21:
				strFieldName = "ConvertFlag";
				break;
			case 22:
				strFieldName = "DoType";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
				strFieldName = "RiskCode";
				break;
			case 26:
				strFieldName = "SerNo";
				break;
			case 27:
				strFieldName = "BankName";
				break;
			case 28:
				strFieldName = "InBankCode";
				break;
			case 29:
				strFieldName = "InBankName";
				break;
			case 30:
				strFieldName = "InAccType";
				break;
			case 31:
				strFieldName = "InAccName";
				break;
			case 32:
				strFieldName = "InAccNo";
				break;
			case 33:
				strFieldName = "IDType";
				break;
			case 34:
				strFieldName = "IDNo";
				break;
			case 35:
				strFieldName = "StandByFlag1";
				break;
			case 36:
				strFieldName = "StandByFlag2";
				break;
			case 37:
				strFieldName = "StandByFlag3";
				break;
			case 38:
				strFieldName = "StandByFlag4";
				break;
			case 39:
				strFieldName = "StandByFlag5";
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
		if( strFieldName.equals("DealType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankDealDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankDealTime") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BankSuccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VertifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConvertFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BankName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InBankName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag5") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
