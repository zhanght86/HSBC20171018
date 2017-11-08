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
import com.sinosoft.lis.db.OFInterfaceDB;

/*
 * <p>ClassName: OFInterfaceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口
 */
public class OFInterfaceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(OFInterfaceSchema.class);

	// @Field
	/** 记录行id */
	private int RecordID;
	/** 冲消状态 */
	private String ReversedStatus;
	/** 被冲消的行 */
	private int OrigRowID;
	/** 冲消生成的行 */
	private int ReversedRowID;
	/** 币别 */
	private String CurrencyCode;
	/** 凭证类别 */
	private String VoucherType;
	/** 核算单位代码 */
	private String ManageCom;
	/** 成本中心 */
	private String Segment2;
	/** 科目代码 */
	private String AccountCode;
	/** 科目明细代码 */
	private String AccountSubCode;
	/** 渠道代码 */
	private String SaleChnl;
	/** 保险产品代码 */
	private String RiskCode;
	/** 备用段1 */
	private String Segment7;
	/** 备用段2 */
	private String Segment8;
	/** 事务日期 */
	private Date TransDate;
	/** 记帐日期 */
	private Date AccountingDate;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 最后更新日期 */
	private Date ModifyDate;
	/** 最后更新时间 */
	private String ModifyTime;
	/** 借贷关系key值 */
	private int MatchID;
	/** 批次号 */
	private String BatchNo;
	/** 事务借计金额 */
	private double EnteredDR;
	/** 事务贷计金额 */
	private double EnteredCR;
	/** 日记帐摘要 */
	private String HeadDescription;
	/** 行记帐摘要 */
	private String LineDescription;
	/** 汇率日期 */
	private Date CurrencyConversionDate;
	/** 汇率 */
	private int CurrencyConversionRate;
	/** 记帐借计金额 */
	private double AccountedDR;
	/** 记帐贷计金额 */
	private double AccountedCR;
	/** 空闲属性1 */
	private String Attribute1;
	/** 承保单号码 */
	private String PolNo;
	/** 被保险人姓名 */
	private String InsuredName;
	/** 收付款单据号 */
	private String BussNo;
	/** 行为明细类别 */
	private String Attribute5;
	/** 空闲属性6 */
	private String Attribute6;
	/** 空闲属性7 */
	private String Attribute7;
	/** 空闲属性8 */
	private String Attribute8;
	/** 总帐凭证号回写 */
	private String VoucherID;
	/** 回写标志 */
	private String VoucherFlag;
	/** 回写导入日期 */
	private String VoucherDate;
	/** 空闲属性9 */
	private String Attribute9;

	public static final int FIELDNUM = 42;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public OFInterfaceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RecordID";

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
		OFInterfaceSchema cloned = (OFInterfaceSchema)super.clone();
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
	* 接口表中每一行的唯一ID号,导入错误时会使用改段内容
	*/
	public int getRecordID()
	{
		return RecordID;
	}
	public void setRecordID(int aRecordID)
	{
		RecordID = aRecordID;
	}
	public void setRecordID(String aRecordID)
	{
		if (aRecordID != null && !aRecordID.equals(""))
		{
			Integer tInteger = new Integer(aRecordID);
			int i = tInteger.intValue();
			RecordID = i;
		}
	}

	/**
	* 有效值：0/正常;1/被冲消
	*/
	public String getReversedStatus()
	{
		return ReversedStatus;
	}
	public void setReversedStatus(String aReversedStatus)
	{
		ReversedStatus = aReversedStatus;
	}
	/**
	* 仅适用于冲消行，也就是负借，负贷的那些行
	*/
	public int getOrigRowID()
	{
		return OrigRowID;
	}
	public void setOrigRowID(int aOrigRowID)
	{
		OrigRowID = aOrigRowID;
	}
	public void setOrigRowID(String aOrigRowID)
	{
		if (aOrigRowID != null && !aOrigRowID.equals(""))
		{
			Integer tInteger = new Integer(aOrigRowID);
			int i = tInteger.intValue();
			OrigRowID = i;
		}
	}

	/**
	* 用来记录冲消的RowID
	*/
	public int getReversedRowID()
	{
		return ReversedRowID;
	}
	public void setReversedRowID(int aReversedRowID)
	{
		ReversedRowID = aReversedRowID;
	}
	public void setReversedRowID(String aReversedRowID)
	{
		if (aReversedRowID != null && !aReversedRowID.equals(""))
		{
			Integer tInteger = new Integer(aReversedRowID);
			int i = tInteger.intValue();
			ReversedRowID = i;
		}
	}

	/**
	* “CNY”表示人民币，目前没有外币业务
	*/
	public String getCurrencyCode()
	{
		return CurrencyCode;
	}
	public void setCurrencyCode(String aCurrencyCode)
	{
		CurrencyCode = aCurrencyCode;
	}
	/**
	* 业务类别名称，有效值：预收/暂收/..
	*/
	public String getVoucherType()
	{
		return VoucherType;
	}
	public void setVoucherType(String aVoucherType)
	{
		VoucherType = aVoucherType;
	}
	/**
	* 发生事务的核算单位
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
	* 营销服务部
	*/
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		Segment2 = aSegment2;
	}
	/**
	* 发生事务的科目，与EBS科目段一致，不用数据字典对照
	*/
	public String getAccountCode()
	{
		return AccountCode;
	}
	public void setAccountCode(String aAccountCode)
	{
		AccountCode = aAccountCode;
	}
	/**
	* 如科目段为银行存款，则该段取唯一银行帐户编码。具体详见数据字典
	*/
	public String getAccountSubCode()
	{
		return AccountSubCode;
	}
	public void setAccountSubCode(String aAccountSubCode)
	{
		AccountSubCode = aAccountSubCode;
	}
	/**
	* 发生事务的渠道代码，比如个险渠道、团险渠道等 ，需要字段对照
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	/**
	* 发生事务的保险产品代码，需要字典对照
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 目前可以不用，为今后可能的扩展备用，有效值：NA
	*/
	public String getSegment7()
	{
		return Segment7;
	}
	public void setSegment7(String aSegment7)
	{
		Segment7 = aSegment7;
	}
	/**
	* 目前可以不用，为今后可能的扩展备用，有效值：NA
	*/
	public String getSegment8()
	{
		return Segment8;
	}
	public void setSegment8(String aSegment8)
	{
		Segment8 = aSegment8;
	}
	/**
	* 事务发生日期
	*/
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

	/**
	* 财务入帐日期，也是导入Oracle EBS的总帐凭证的GL_DATE，等于事务日期
	*/
	public String getAccountingDate()
	{
		if( AccountingDate != null )
			return fDate.getString(AccountingDate);
		else
			return null;
	}
	public void setAccountingDate(Date aAccountingDate)
	{
		AccountingDate = aAccountingDate;
	}
	public void setAccountingDate(String aAccountingDate)
	{
		if (aAccountingDate != null && !aAccountingDate.equals("") )
		{
			AccountingDate = fDate.getDate( aAccountingDate );
		}
		else
			AccountingDate = null;
	}

	/**
	* Turnc(SYSDATE) ,yyyy-mm-dd
	*/
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

	/**
	* Hh24:mi:ss
	*/
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
	* 标示借贷关系
	*/
	public int getMatchID()
	{
		return MatchID;
	}
	public void setMatchID(int aMatchID)
	{
		MatchID = aMatchID;
	}
	public void setMatchID(String aMatchID)
	{
		if (aMatchID != null && !aMatchID.equals(""))
		{
			Integer tInteger = new Integer(aMatchID);
			int i = tInteger.intValue();
			MatchID = i;
		}
	}

	/**
	* 每一批传送至结构表记录的KEY_ID,作为将来两两个instance关联对账的依据
	*/
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	/**
	* 借方金额(原币)，在一条记录上此字段同enteredcr 互斥，不能同时有值，也不能同时为空。如果是零用空表示
	*/
	public double getEnteredDR()
	{
		return EnteredDR;
	}
	public void setEnteredDR(double aEnteredDR)
	{
		EnteredDR = aEnteredDR;
	}
	public void setEnteredDR(String aEnteredDR)
	{
		if (aEnteredDR != null && !aEnteredDR.equals(""))
		{
			Double tDouble = new Double(aEnteredDR);
			double d = tDouble.doubleValue();
			EnteredDR = d;
		}
	}

	/**
	* 贷方金额(原币)，原则如上 。
	*/
	public double getEnteredCR()
	{
		return EnteredCR;
	}
	public void setEnteredCR(double aEnteredCR)
	{
		EnteredCR = aEnteredCR;
	}
	public void setEnteredCR(String aEnteredCR)
	{
		if (aEnteredCR != null && !aEnteredCR.equals(""))
		{
			Double tDouble = new Double(aEnteredCR);
			double d = tDouble.doubleValue();
			EnteredCR = d;
		}
	}

	/**
	* 为空，由EBS拼
	*/
	public String getHeadDescription()
	{
		return HeadDescription;
	}
	public void setHeadDescription(String aHeadDescription)
	{
		HeadDescription = aHeadDescription;
	}
	/**
	* 为空，由EBS拼
	*/
	public String getLineDescription()
	{
		return LineDescription;
	}
	public void setLineDescription(String aLineDescription)
	{
		LineDescription = aLineDescription;
	}
	/**
	* 体现将来的外币业务，目前为空
	*/
	public String getCurrencyConversionDate()
	{
		if( CurrencyConversionDate != null )
			return fDate.getString(CurrencyConversionDate);
		else
			return null;
	}
	public void setCurrencyConversionDate(Date aCurrencyConversionDate)
	{
		CurrencyConversionDate = aCurrencyConversionDate;
	}
	public void setCurrencyConversionDate(String aCurrencyConversionDate)
	{
		if (aCurrencyConversionDate != null && !aCurrencyConversionDate.equals("") )
		{
			CurrencyConversionDate = fDate.getDate( aCurrencyConversionDate );
		}
		else
			CurrencyConversionDate = null;
	}

	/**
	* 体现将来的外币业务，目前为空
	*/
	public int getCurrencyConversionRate()
	{
		return CurrencyConversionRate;
	}
	public void setCurrencyConversionRate(int aCurrencyConversionRate)
	{
		CurrencyConversionRate = aCurrencyConversionRate;
	}
	public void setCurrencyConversionRate(String aCurrencyConversionRate)
	{
		if (aCurrencyConversionRate != null && !aCurrencyConversionRate.equals(""))
		{
			Integer tInteger = new Integer(aCurrencyConversionRate);
			int i = tInteger.intValue();
			CurrencyConversionRate = i;
		}
	}

	/**
	* 体现将来的外币业务，目前为空
	*/
	public double getAccountedDR()
	{
		return AccountedDR;
	}
	public void setAccountedDR(double aAccountedDR)
	{
		AccountedDR = aAccountedDR;
	}
	public void setAccountedDR(String aAccountedDR)
	{
		if (aAccountedDR != null && !aAccountedDR.equals(""))
		{
			Double tDouble = new Double(aAccountedDR);
			double d = tDouble.doubleValue();
			AccountedDR = d;
		}
	}

	/**
	* 体现将来的外币业务，目前为空
	*/
	public double getAccountedCR()
	{
		return AccountedCR;
	}
	public void setAccountedCR(double aAccountedCR)
	{
		AccountedCR = aAccountedCR;
	}
	public void setAccountedCR(String aAccountedCR)
	{
		if (aAccountedCR != null && !aAccountedCR.equals(""))
		{
			Double tDouble = new Double(aAccountedCR);
			double d = tDouble.doubleValue();
			AccountedCR = d;
		}
	}

	/**
	* 目前为空
	*/
	public String getAttribute1()
	{
		return Attribute1;
	}
	public void setAttribute1(String aAttribute1)
	{
		Attribute1 = aAttribute1;
	}
	/**
	* 承保单号码，来自核心系统的业务线索信息
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
	* 团单或者与被保人没有关系的时候，为空
	*/
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		BussNo = aBussNo;
	}
	public String getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		Attribute5 = aAttribute5;
	}
	/**
	* 目前为空
	*/
	public String getAttribute6()
	{
		return Attribute6;
	}
	public void setAttribute6(String aAttribute6)
	{
		Attribute6 = aAttribute6;
	}
	/**
	* 目前为空
	*/
	public String getAttribute7()
	{
		return Attribute7;
	}
	public void setAttribute7(String aAttribute7)
	{
		Attribute7 = aAttribute7;
	}
	/**
	* 目前为空
	*/
	public String getAttribute8()
	{
		return Attribute8;
	}
	public void setAttribute8(String aAttribute8)
	{
		Attribute8 = aAttribute8;
	}
	/**
	* 回写总帐凭证号码
	*/
	public String getVoucherID()
	{
		return VoucherID;
	}
	public void setVoucherID(String aVoucherID)
	{
		VoucherID = aVoucherID;
	}
	/**
	* Oracle GL导入读取成功后，回写此标识”请求号”
	*/
	public String getVoucherFlag()
	{
		return VoucherFlag;
	}
	public void setVoucherFlag(String aVoucherFlag)
	{
		VoucherFlag = aVoucherFlag;
	}
	/**
	* Oracle GL导入读取成功后，将成功到入日期写入（时分秒）
	*/
	public String getVoucherDate()
	{
		return VoucherDate;
	}
	public void setVoucherDate(String aVoucherDate)
	{
		VoucherDate = aVoucherDate;
	}
	public String getAttribute9()
	{
		return Attribute9;
	}
	public void setAttribute9(String aAttribute9)
	{
		Attribute9 = aAttribute9;
	}

	/**
	* 使用另外一个 OFInterfaceSchema 对象给 Schema 赋值
	* @param: aOFInterfaceSchema OFInterfaceSchema
	**/
	public void setSchema(OFInterfaceSchema aOFInterfaceSchema)
	{
		this.RecordID = aOFInterfaceSchema.getRecordID();
		this.ReversedStatus = aOFInterfaceSchema.getReversedStatus();
		this.OrigRowID = aOFInterfaceSchema.getOrigRowID();
		this.ReversedRowID = aOFInterfaceSchema.getReversedRowID();
		this.CurrencyCode = aOFInterfaceSchema.getCurrencyCode();
		this.VoucherType = aOFInterfaceSchema.getVoucherType();
		this.ManageCom = aOFInterfaceSchema.getManageCom();
		this.Segment2 = aOFInterfaceSchema.getSegment2();
		this.AccountCode = aOFInterfaceSchema.getAccountCode();
		this.AccountSubCode = aOFInterfaceSchema.getAccountSubCode();
		this.SaleChnl = aOFInterfaceSchema.getSaleChnl();
		this.RiskCode = aOFInterfaceSchema.getRiskCode();
		this.Segment7 = aOFInterfaceSchema.getSegment7();
		this.Segment8 = aOFInterfaceSchema.getSegment8();
		this.TransDate = fDate.getDate( aOFInterfaceSchema.getTransDate());
		this.AccountingDate = fDate.getDate( aOFInterfaceSchema.getAccountingDate());
		this.MakeDate = fDate.getDate( aOFInterfaceSchema.getMakeDate());
		this.MakeTime = aOFInterfaceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aOFInterfaceSchema.getModifyDate());
		this.ModifyTime = aOFInterfaceSchema.getModifyTime();
		this.MatchID = aOFInterfaceSchema.getMatchID();
		this.BatchNo = aOFInterfaceSchema.getBatchNo();
		this.EnteredDR = aOFInterfaceSchema.getEnteredDR();
		this.EnteredCR = aOFInterfaceSchema.getEnteredCR();
		this.HeadDescription = aOFInterfaceSchema.getHeadDescription();
		this.LineDescription = aOFInterfaceSchema.getLineDescription();
		this.CurrencyConversionDate = fDate.getDate( aOFInterfaceSchema.getCurrencyConversionDate());
		this.CurrencyConversionRate = aOFInterfaceSchema.getCurrencyConversionRate();
		this.AccountedDR = aOFInterfaceSchema.getAccountedDR();
		this.AccountedCR = aOFInterfaceSchema.getAccountedCR();
		this.Attribute1 = aOFInterfaceSchema.getAttribute1();
		this.PolNo = aOFInterfaceSchema.getPolNo();
		this.InsuredName = aOFInterfaceSchema.getInsuredName();
		this.BussNo = aOFInterfaceSchema.getBussNo();
		this.Attribute5 = aOFInterfaceSchema.getAttribute5();
		this.Attribute6 = aOFInterfaceSchema.getAttribute6();
		this.Attribute7 = aOFInterfaceSchema.getAttribute7();
		this.Attribute8 = aOFInterfaceSchema.getAttribute8();
		this.VoucherID = aOFInterfaceSchema.getVoucherID();
		this.VoucherFlag = aOFInterfaceSchema.getVoucherFlag();
		this.VoucherDate = aOFInterfaceSchema.getVoucherDate();
		this.Attribute9 = aOFInterfaceSchema.getAttribute9();
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
			this.RecordID = rs.getInt("RecordID");
			if( rs.getString("ReversedStatus") == null )
				this.ReversedStatus = null;
			else
				this.ReversedStatus = rs.getString("ReversedStatus").trim();

			this.OrigRowID = rs.getInt("OrigRowID");
			this.ReversedRowID = rs.getInt("ReversedRowID");
			if( rs.getString("CurrencyCode") == null )
				this.CurrencyCode = null;
			else
				this.CurrencyCode = rs.getString("CurrencyCode").trim();

			if( rs.getString("VoucherType") == null )
				this.VoucherType = null;
			else
				this.VoucherType = rs.getString("VoucherType").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("AccountCode") == null )
				this.AccountCode = null;
			else
				this.AccountCode = rs.getString("AccountCode").trim();

			if( rs.getString("AccountSubCode") == null )
				this.AccountSubCode = null;
			else
				this.AccountSubCode = rs.getString("AccountSubCode").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("Segment7") == null )
				this.Segment7 = null;
			else
				this.Segment7 = rs.getString("Segment7").trim();

			if( rs.getString("Segment8") == null )
				this.Segment8 = null;
			else
				this.Segment8 = rs.getString("Segment8").trim();

			this.TransDate = rs.getDate("TransDate");
			this.AccountingDate = rs.getDate("AccountingDate");
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

			this.MatchID = rs.getInt("MatchID");
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			this.EnteredDR = rs.getDouble("EnteredDR");
			this.EnteredCR = rs.getDouble("EnteredCR");
			if( rs.getString("HeadDescription") == null )
				this.HeadDescription = null;
			else
				this.HeadDescription = rs.getString("HeadDescription").trim();

			if( rs.getString("LineDescription") == null )
				this.LineDescription = null;
			else
				this.LineDescription = rs.getString("LineDescription").trim();

			this.CurrencyConversionDate = rs.getDate("CurrencyConversionDate");
			this.CurrencyConversionRate = rs.getInt("CurrencyConversionRate");
			this.AccountedDR = rs.getDouble("AccountedDR");
			this.AccountedCR = rs.getDouble("AccountedCR");
			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("Attribute5") == null )
				this.Attribute5 = null;
			else
				this.Attribute5 = rs.getString("Attribute5").trim();

			if( rs.getString("Attribute6") == null )
				this.Attribute6 = null;
			else
				this.Attribute6 = rs.getString("Attribute6").trim();

			if( rs.getString("Attribute7") == null )
				this.Attribute7 = null;
			else
				this.Attribute7 = rs.getString("Attribute7").trim();

			if( rs.getString("Attribute8") == null )
				this.Attribute8 = null;
			else
				this.Attribute8 = rs.getString("Attribute8").trim();

			if( rs.getString("VoucherID") == null )
				this.VoucherID = null;
			else
				this.VoucherID = rs.getString("VoucherID").trim();

			if( rs.getString("VoucherFlag") == null )
				this.VoucherFlag = null;
			else
				this.VoucherFlag = rs.getString("VoucherFlag").trim();

			if( rs.getString("VoucherDate") == null )
				this.VoucherDate = null;
			else
				this.VoucherDate = rs.getString("VoucherDate").trim();

			if( rs.getString("Attribute9") == null )
				this.Attribute9 = null;
			else
				this.Attribute9 = rs.getString("Attribute9").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的OFInterface表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OFInterfaceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public OFInterfaceSchema getSchema()
	{
		OFInterfaceSchema aOFInterfaceSchema = new OFInterfaceSchema();
		aOFInterfaceSchema.setSchema(this);
		return aOFInterfaceSchema;
	}

	public OFInterfaceDB getDB()
	{
		OFInterfaceDB aDBOper = new OFInterfaceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpOFInterface描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(RecordID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReversedStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OrigRowID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReversedRowID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrencyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VoucherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountSubCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccountingDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MatchID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnteredDR));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnteredCR));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HeadDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LineDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurrencyConversionDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurrencyConversionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccountedDR));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccountedCR));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VoucherID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VoucherFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VoucherDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Attribute9));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpOFInterface>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RecordID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ReversedStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OrigRowID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ReversedRowID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			CurrencyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			VoucherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccountCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccountSubCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Segment7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			AccountingDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MatchID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			EnteredDR = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			EnteredCR = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			HeadDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			LineDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CurrencyConversionDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			CurrencyConversionRate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
			AccountedDR = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			AccountedCR = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Attribute5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Attribute6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Attribute7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Attribute8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			VoucherID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			VoucherFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			VoucherDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Attribute9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "OFInterfaceSchema";
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
		if (FCode.equalsIgnoreCase("RecordID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecordID));
		}
		if (FCode.equalsIgnoreCase("ReversedStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReversedStatus));
		}
		if (FCode.equalsIgnoreCase("OrigRowID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrigRowID));
		}
		if (FCode.equalsIgnoreCase("ReversedRowID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReversedRowID));
		}
		if (FCode.equalsIgnoreCase("CurrencyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyCode));
		}
		if (FCode.equalsIgnoreCase("VoucherType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VoucherType));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("AccountCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountCode));
		}
		if (FCode.equalsIgnoreCase("AccountSubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountSubCode));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment7));
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment8));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("AccountingDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccountingDate()));
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
		if (FCode.equalsIgnoreCase("MatchID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchID));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("EnteredDR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnteredDR));
		}
		if (FCode.equalsIgnoreCase("EnteredCR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnteredCR));
		}
		if (FCode.equalsIgnoreCase("HeadDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HeadDescription));
		}
		if (FCode.equalsIgnoreCase("LineDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LineDescription));
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurrencyConversionDate()));
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrencyConversionRate));
		}
		if (FCode.equalsIgnoreCase("AccountedDR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountedDR));
		}
		if (FCode.equalsIgnoreCase("AccountedCR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountedCR));
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute1));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute6));
		}
		if (FCode.equalsIgnoreCase("Attribute7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute7));
		}
		if (FCode.equalsIgnoreCase("Attribute8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute8));
		}
		if (FCode.equalsIgnoreCase("VoucherID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VoucherID));
		}
		if (FCode.equalsIgnoreCase("VoucherFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VoucherFlag));
		}
		if (FCode.equalsIgnoreCase("VoucherDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VoucherDate));
		}
		if (FCode.equalsIgnoreCase("Attribute9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute9));
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
				strFieldValue = String.valueOf(RecordID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ReversedStatus);
				break;
			case 2:
				strFieldValue = String.valueOf(OrigRowID);
				break;
			case 3:
				strFieldValue = String.valueOf(ReversedRowID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CurrencyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(VoucherType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccountCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccountSubCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Segment7);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment8);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccountingDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = String.valueOf(MatchID);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 22:
				strFieldValue = String.valueOf(EnteredDR);
				break;
			case 23:
				strFieldValue = String.valueOf(EnteredCR);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(HeadDescription);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(LineDescription);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurrencyConversionDate()));
				break;
			case 27:
				strFieldValue = String.valueOf(CurrencyConversionRate);
				break;
			case 28:
				strFieldValue = String.valueOf(AccountedDR);
				break;
			case 29:
				strFieldValue = String.valueOf(AccountedCR);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Attribute5);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Attribute6);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Attribute7);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Attribute8);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(VoucherID);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(VoucherFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(VoucherDate);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Attribute9);
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

		if (FCode.equalsIgnoreCase("RecordID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RecordID = i;
			}
		}
		if (FCode.equalsIgnoreCase("ReversedStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReversedStatus = FValue.trim();
			}
			else
				ReversedStatus = null;
		}
		if (FCode.equalsIgnoreCase("OrigRowID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrigRowID = i;
			}
		}
		if (FCode.equalsIgnoreCase("ReversedRowID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ReversedRowID = i;
			}
		}
		if (FCode.equalsIgnoreCase("CurrencyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrencyCode = FValue.trim();
			}
			else
				CurrencyCode = null;
		}
		if (FCode.equalsIgnoreCase("VoucherType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VoucherType = FValue.trim();
			}
			else
				VoucherType = null;
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
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("AccountCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountCode = FValue.trim();
			}
			else
				AccountCode = null;
		}
		if (FCode.equalsIgnoreCase("AccountSubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountSubCode = FValue.trim();
			}
			else
				AccountSubCode = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment7 = FValue.trim();
			}
			else
				Segment7 = null;
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment8 = FValue.trim();
			}
			else
				Segment8 = null;
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
		if (FCode.equalsIgnoreCase("AccountingDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccountingDate = fDate.getDate( FValue );
			}
			else
				AccountingDate = null;
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
		if (FCode.equalsIgnoreCase("MatchID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MatchID = i;
			}
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
		if (FCode.equalsIgnoreCase("EnteredDR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnteredDR = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnteredCR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnteredCR = d;
			}
		}
		if (FCode.equalsIgnoreCase("HeadDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HeadDescription = FValue.trim();
			}
			else
				HeadDescription = null;
		}
		if (FCode.equalsIgnoreCase("LineDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LineDescription = FValue.trim();
			}
			else
				LineDescription = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurrencyConversionDate = fDate.getDate( FValue );
			}
			else
				CurrencyConversionDate = null;
		}
		if (FCode.equalsIgnoreCase("CurrencyConversionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CurrencyConversionRate = i;
			}
		}
		if (FCode.equalsIgnoreCase("AccountedDR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccountedDR = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccountedCR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccountedCR = d;
			}
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute1 = FValue.trim();
			}
			else
				Attribute1 = null;
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
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
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
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute5 = FValue.trim();
			}
			else
				Attribute5 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute6 = FValue.trim();
			}
			else
				Attribute6 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute7 = FValue.trim();
			}
			else
				Attribute7 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute8 = FValue.trim();
			}
			else
				Attribute8 = null;
		}
		if (FCode.equalsIgnoreCase("VoucherID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VoucherID = FValue.trim();
			}
			else
				VoucherID = null;
		}
		if (FCode.equalsIgnoreCase("VoucherFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VoucherFlag = FValue.trim();
			}
			else
				VoucherFlag = null;
		}
		if (FCode.equalsIgnoreCase("VoucherDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VoucherDate = FValue.trim();
			}
			else
				VoucherDate = null;
		}
		if (FCode.equalsIgnoreCase("Attribute9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute9 = FValue.trim();
			}
			else
				Attribute9 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		OFInterfaceSchema other = (OFInterfaceSchema)otherObject;
		return
			RecordID == other.getRecordID()
			&& ReversedStatus.equals(other.getReversedStatus())
			&& OrigRowID == other.getOrigRowID()
			&& ReversedRowID == other.getReversedRowID()
			&& CurrencyCode.equals(other.getCurrencyCode())
			&& VoucherType.equals(other.getVoucherType())
			&& ManageCom.equals(other.getManageCom())
			&& Segment2.equals(other.getSegment2())
			&& AccountCode.equals(other.getAccountCode())
			&& AccountSubCode.equals(other.getAccountSubCode())
			&& SaleChnl.equals(other.getSaleChnl())
			&& RiskCode.equals(other.getRiskCode())
			&& Segment7.equals(other.getSegment7())
			&& Segment8.equals(other.getSegment8())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& fDate.getString(AccountingDate).equals(other.getAccountingDate())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& MatchID == other.getMatchID()
			&& BatchNo.equals(other.getBatchNo())
			&& EnteredDR == other.getEnteredDR()
			&& EnteredCR == other.getEnteredCR()
			&& HeadDescription.equals(other.getHeadDescription())
			&& LineDescription.equals(other.getLineDescription())
			&& fDate.getString(CurrencyConversionDate).equals(other.getCurrencyConversionDate())
			&& CurrencyConversionRate == other.getCurrencyConversionRate()
			&& AccountedDR == other.getAccountedDR()
			&& AccountedCR == other.getAccountedCR()
			&& Attribute1.equals(other.getAttribute1())
			&& PolNo.equals(other.getPolNo())
			&& InsuredName.equals(other.getInsuredName())
			&& BussNo.equals(other.getBussNo())
			&& Attribute5.equals(other.getAttribute5())
			&& Attribute6.equals(other.getAttribute6())
			&& Attribute7.equals(other.getAttribute7())
			&& Attribute8.equals(other.getAttribute8())
			&& VoucherID.equals(other.getVoucherID())
			&& VoucherFlag.equals(other.getVoucherFlag())
			&& VoucherDate.equals(other.getVoucherDate())
			&& Attribute9.equals(other.getAttribute9());
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
		if( strFieldName.equals("RecordID") ) {
			return 0;
		}
		if( strFieldName.equals("ReversedStatus") ) {
			return 1;
		}
		if( strFieldName.equals("OrigRowID") ) {
			return 2;
		}
		if( strFieldName.equals("ReversedRowID") ) {
			return 3;
		}
		if( strFieldName.equals("CurrencyCode") ) {
			return 4;
		}
		if( strFieldName.equals("VoucherType") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("Segment2") ) {
			return 7;
		}
		if( strFieldName.equals("AccountCode") ) {
			return 8;
		}
		if( strFieldName.equals("AccountSubCode") ) {
			return 9;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("Segment7") ) {
			return 12;
		}
		if( strFieldName.equals("Segment8") ) {
			return 13;
		}
		if( strFieldName.equals("TransDate") ) {
			return 14;
		}
		if( strFieldName.equals("AccountingDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("MatchID") ) {
			return 20;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 21;
		}
		if( strFieldName.equals("EnteredDR") ) {
			return 22;
		}
		if( strFieldName.equals("EnteredCR") ) {
			return 23;
		}
		if( strFieldName.equals("HeadDescription") ) {
			return 24;
		}
		if( strFieldName.equals("LineDescription") ) {
			return 25;
		}
		if( strFieldName.equals("CurrencyConversionDate") ) {
			return 26;
		}
		if( strFieldName.equals("CurrencyConversionRate") ) {
			return 27;
		}
		if( strFieldName.equals("AccountedDR") ) {
			return 28;
		}
		if( strFieldName.equals("AccountedCR") ) {
			return 29;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 30;
		}
		if( strFieldName.equals("PolNo") ) {
			return 31;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 32;
		}
		if( strFieldName.equals("BussNo") ) {
			return 33;
		}
		if( strFieldName.equals("Attribute5") ) {
			return 34;
		}
		if( strFieldName.equals("Attribute6") ) {
			return 35;
		}
		if( strFieldName.equals("Attribute7") ) {
			return 36;
		}
		if( strFieldName.equals("Attribute8") ) {
			return 37;
		}
		if( strFieldName.equals("VoucherID") ) {
			return 38;
		}
		if( strFieldName.equals("VoucherFlag") ) {
			return 39;
		}
		if( strFieldName.equals("VoucherDate") ) {
			return 40;
		}
		if( strFieldName.equals("Attribute9") ) {
			return 41;
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
				strFieldName = "RecordID";
				break;
			case 1:
				strFieldName = "ReversedStatus";
				break;
			case 2:
				strFieldName = "OrigRowID";
				break;
			case 3:
				strFieldName = "ReversedRowID";
				break;
			case 4:
				strFieldName = "CurrencyCode";
				break;
			case 5:
				strFieldName = "VoucherType";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "Segment2";
				break;
			case 8:
				strFieldName = "AccountCode";
				break;
			case 9:
				strFieldName = "AccountSubCode";
				break;
			case 10:
				strFieldName = "SaleChnl";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "Segment7";
				break;
			case 13:
				strFieldName = "Segment8";
				break;
			case 14:
				strFieldName = "TransDate";
				break;
			case 15:
				strFieldName = "AccountingDate";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "MatchID";
				break;
			case 21:
				strFieldName = "BatchNo";
				break;
			case 22:
				strFieldName = "EnteredDR";
				break;
			case 23:
				strFieldName = "EnteredCR";
				break;
			case 24:
				strFieldName = "HeadDescription";
				break;
			case 25:
				strFieldName = "LineDescription";
				break;
			case 26:
				strFieldName = "CurrencyConversionDate";
				break;
			case 27:
				strFieldName = "CurrencyConversionRate";
				break;
			case 28:
				strFieldName = "AccountedDR";
				break;
			case 29:
				strFieldName = "AccountedCR";
				break;
			case 30:
				strFieldName = "Attribute1";
				break;
			case 31:
				strFieldName = "PolNo";
				break;
			case 32:
				strFieldName = "InsuredName";
				break;
			case 33:
				strFieldName = "BussNo";
				break;
			case 34:
				strFieldName = "Attribute5";
				break;
			case 35:
				strFieldName = "Attribute6";
				break;
			case 36:
				strFieldName = "Attribute7";
				break;
			case 37:
				strFieldName = "Attribute8";
				break;
			case 38:
				strFieldName = "VoucherID";
				break;
			case 39:
				strFieldName = "VoucherFlag";
				break;
			case 40:
				strFieldName = "VoucherDate";
				break;
			case 41:
				strFieldName = "Attribute9";
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
		if( strFieldName.equals("RecordID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ReversedStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrigRowID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ReversedRowID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CurrencyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VoucherType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountSubCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccountingDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("MatchID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnteredDR") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnteredCR") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HeadDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LineDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrencyConversionDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurrencyConversionRate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AccountedDR") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccountedCR") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VoucherID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VoucherFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VoucherDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute9") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
