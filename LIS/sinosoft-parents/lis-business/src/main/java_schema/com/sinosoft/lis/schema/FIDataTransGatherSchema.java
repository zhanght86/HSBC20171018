/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIDataTransGatherDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataTransGatherSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2009-01-09
 */
public class FIDataTransGatherSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataTransGatherSchema.class);

	// @Field
	/** 汇总数据流水号 */
	private String FSerialNo;
	/** 数据采集批次号 */
	private String BatchNo;
	/** 凭证大类 */
	private String CertificateID;
	/** 科目代码 */
	private String AccountCode;
	/** 借贷标志 */
	private String FinItemType;
	/** 费用金额 */
	private double SumMoney;
	/** 账务日期 */
	private Date AccountDate;
	/** 销售渠道 */
	private String SaleChnl;
	/** 管理机构 */
	private String ManageCom;
	/** 对方机构 */
	private String ExecuteCom;
	/** 险种编码 */
	private String RiskCode;
	/** 成本中心 */
	private String CostCenter;
	/** 票据号码 */
	private String NotesNo;
	/** 客户编号 */
	private String CustomerID;
	/** 预算信息 */
	private String Budget;
	/** 现金流量 */
	private String CashFlow;
	/** 币别 */
	private String Currency;
	/** 摘要 */
	private String ReMark;
	/** 备用字符串一 */
	private String StandByString1;
	/** 备用字符串二 */
	private String StandByString2;
	/** 备用字符串三 */
	private String StandByString3;
	/** 备用字符串四 */
	private String StandByString4;
	/** 备用字符串五 */
	private String StandByString5;
	/** 备用字符串六 */
	private String StandByString6;
	/** 备用字符串七 */
	private String StandByString7;
	/** 备用字符串八 */
	private String StandByString8;
	/** 备用字符串九 */
	private String StandByString9;
	/** 备用字符串十 */
	private String StandByString10;
	/** 备用字符串十一 */
	private String StandByString11;
	/** 备用字符串十二 */
	private String StandByString12;
	/** 备用字符串十三 */
	private String StandByString13;
	/** 备用字符串十四 */
	private String StandByString14;
	/** 备用字符串十五 */
	private String StandByString15;
	/** 备用数字一 */
	private double StandByNum1;
	/** 备用数字二 */
	private double StandByNum2;
	/** 备用日期一 */
	private Date StandByDate1;
	/** 备用日期二 */
	private Date StandByDate2;
	/** 回写凭证是否生成 */
	private String ReturnFlag;
	/** 回写凭证号码 */
	private String VoucherNo;
	/** 回写会计月度 */
	private String YearMonth;
	/** 回写校验是否成功 */
	private String VerifyFlag;
	/** 回写校验信息 */
	private String ErrorInfo;
	/** 回改日期 */
	private Date ReturnDate;
	/** 回改时间 */
	private String ReturnTime;
	/** 回改操作员 */
	private String DealOperator;

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataTransGatherSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "FSerialNo";

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
                FIDataTransGatherSchema cloned = (FIDataTransGatherSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFSerialNo()
	{
		return FSerialNo;
	}
	public void setFSerialNo(String aFSerialNo)
	{
		FSerialNo = aFSerialNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getCertificateID()
	{
		return CertificateID;
	}
	public void setCertificateID(String aCertificateID)
	{
		CertificateID = aCertificateID;
	}
	public String getAccountCode()
	{
		return AccountCode;
	}
	public void setAccountCode(String aAccountCode)
	{
		AccountCode = aAccountCode;
	}
	public String getFinItemType()
	{
		return FinItemType;
	}
	public void setFinItemType(String aFinItemType)
	{
		FinItemType = aFinItemType;
	}
	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	public String getAccountDate()
	{
		if( AccountDate != null )
			return fDate.getString(AccountDate);
		else
			return null;
	}
	public void setAccountDate(Date aAccountDate)
	{
		AccountDate = aAccountDate;
	}
	public void setAccountDate(String aAccountDate)
	{
		if (aAccountDate != null && !aAccountDate.equals("") )
		{
			AccountDate = fDate.getDate( aAccountDate );
		}
		else
			AccountDate = null;
	}

	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getExecuteCom()
	{
		return ExecuteCom;
	}
	public void setExecuteCom(String aExecuteCom)
	{
		ExecuteCom = aExecuteCom;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getCostCenter()
	{
		return CostCenter;
	}
	public void setCostCenter(String aCostCenter)
	{
		CostCenter = aCostCenter;
	}
	public String getNotesNo()
	{
		return NotesNo;
	}
	public void setNotesNo(String aNotesNo)
	{
		NotesNo = aNotesNo;
	}
	public String getCustomerID()
	{
		return CustomerID;
	}
	public void setCustomerID(String aCustomerID)
	{
		CustomerID = aCustomerID;
	}
	public String getBudget()
	{
		return Budget;
	}
	public void setBudget(String aBudget)
	{
		Budget = aBudget;
	}
	public String getCashFlow()
	{
		return CashFlow;
	}
	public void setCashFlow(String aCashFlow)
	{
		CashFlow = aCashFlow;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}
	public String getStandByString1()
	{
		return StandByString1;
	}
	public void setStandByString1(String aStandByString1)
	{
		StandByString1 = aStandByString1;
	}
	public String getStandByString2()
	{
		return StandByString2;
	}
	public void setStandByString2(String aStandByString2)
	{
		StandByString2 = aStandByString2;
	}
	public String getStandByString3()
	{
		return StandByString3;
	}
	public void setStandByString3(String aStandByString3)
	{
		StandByString3 = aStandByString3;
	}
	public String getStandByString4()
	{
		return StandByString4;
	}
	public void setStandByString4(String aStandByString4)
	{
		StandByString4 = aStandByString4;
	}
	public String getStandByString5()
	{
		return StandByString5;
	}
	public void setStandByString5(String aStandByString5)
	{
		StandByString5 = aStandByString5;
	}
	public String getStandByString6()
	{
		return StandByString6;
	}
	public void setStandByString6(String aStandByString6)
	{
		StandByString6 = aStandByString6;
	}
	public String getStandByString7()
	{
		return StandByString7;
	}
	public void setStandByString7(String aStandByString7)
	{
		StandByString7 = aStandByString7;
	}
	public String getStandByString8()
	{
		return StandByString8;
	}
	public void setStandByString8(String aStandByString8)
	{
		StandByString8 = aStandByString8;
	}
	public String getStandByString9()
	{
		return StandByString9;
	}
	public void setStandByString9(String aStandByString9)
	{
		StandByString9 = aStandByString9;
	}
	public String getStandByString10()
	{
		return StandByString10;
	}
	public void setStandByString10(String aStandByString10)
	{
		StandByString10 = aStandByString10;
	}
	public String getStandByString11()
	{
		return StandByString11;
	}
	public void setStandByString11(String aStandByString11)
	{
		StandByString11 = aStandByString11;
	}
	public String getStandByString12()
	{
		return StandByString12;
	}
	public void setStandByString12(String aStandByString12)
	{
		StandByString12 = aStandByString12;
	}
	public String getStandByString13()
	{
		return StandByString13;
	}
	public void setStandByString13(String aStandByString13)
	{
		StandByString13 = aStandByString13;
	}
	public String getStandByString14()
	{
		return StandByString14;
	}
	public void setStandByString14(String aStandByString14)
	{
		StandByString14 = aStandByString14;
	}
	public String getStandByString15()
	{
		return StandByString15;
	}
	public void setStandByString15(String aStandByString15)
	{
		StandByString15 = aStandByString15;
	}
	public double getStandByNum1()
	{
		return StandByNum1;
	}
	public void setStandByNum1(double aStandByNum1)
	{
		StandByNum1 = aStandByNum1;
	}
	public void setStandByNum1(String aStandByNum1)
	{
		if (aStandByNum1 != null && !aStandByNum1.equals(""))
		{
			Double tDouble = new Double(aStandByNum1);
			double d = tDouble.doubleValue();
			StandByNum1 = d;
		}
	}

	public double getStandByNum2()
	{
		return StandByNum2;
	}
	public void setStandByNum2(double aStandByNum2)
	{
		StandByNum2 = aStandByNum2;
	}
	public void setStandByNum2(String aStandByNum2)
	{
		if (aStandByNum2 != null && !aStandByNum2.equals(""))
		{
			Double tDouble = new Double(aStandByNum2);
			double d = tDouble.doubleValue();
			StandByNum2 = d;
		}
	}

	public String getStandByDate1()
	{
		if( StandByDate1 != null )
			return fDate.getString(StandByDate1);
		else
			return null;
	}
	public void setStandByDate1(Date aStandByDate1)
	{
		StandByDate1 = aStandByDate1;
	}
	public void setStandByDate1(String aStandByDate1)
	{
		if (aStandByDate1 != null && !aStandByDate1.equals("") )
		{
			StandByDate1 = fDate.getDate( aStandByDate1 );
		}
		else
			StandByDate1 = null;
	}

	public String getStandByDate2()
	{
		if( StandByDate2 != null )
			return fDate.getString(StandByDate2);
		else
			return null;
	}
	public void setStandByDate2(Date aStandByDate2)
	{
		StandByDate2 = aStandByDate2;
	}
	public void setStandByDate2(String aStandByDate2)
	{
		if (aStandByDate2 != null && !aStandByDate2.equals("") )
		{
			StandByDate2 = fDate.getDate( aStandByDate2 );
		}
		else
			StandByDate2 = null;
	}

	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String aReturnFlag)
	{
		ReturnFlag = aReturnFlag;
	}
	public String getVoucherNo()
	{
		return VoucherNo;
	}
	public void setVoucherNo(String aVoucherNo)
	{
		VoucherNo = aVoucherNo;
	}
	public String getYearMonth()
	{
		return YearMonth;
	}
	public void setYearMonth(String aYearMonth)
	{
		YearMonth = aYearMonth;
	}
	public String getVerifyFlag()
	{
		return VerifyFlag;
	}
	public void setVerifyFlag(String aVerifyFlag)
	{
		VerifyFlag = aVerifyFlag;
	}
	public String getErrorInfo()
	{
		return ErrorInfo;
	}
	public void setErrorInfo(String aErrorInfo)
	{
		ErrorInfo = aErrorInfo;
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

	public String getReturnTime()
	{
		return ReturnTime;
	}
	public void setReturnTime(String aReturnTime)
	{
		ReturnTime = aReturnTime;
	}
	public String getDealOperator()
	{
		return DealOperator;
	}
	public void setDealOperator(String aDealOperator)
	{
		DealOperator = aDealOperator;
	}

	/**
	* 使用另外一个 FIDataTransGatherSchema 对象给 Schema 赋值
	* @param: aFIDataTransGatherSchema FIDataTransGatherSchema
	**/
	public void setSchema(FIDataTransGatherSchema aFIDataTransGatherSchema)
	{
		this.FSerialNo = aFIDataTransGatherSchema.getFSerialNo();
		this.BatchNo = aFIDataTransGatherSchema.getBatchNo();
		this.CertificateID = aFIDataTransGatherSchema.getCertificateID();
		this.AccountCode = aFIDataTransGatherSchema.getAccountCode();
		this.FinItemType = aFIDataTransGatherSchema.getFinItemType();
		this.SumMoney = aFIDataTransGatherSchema.getSumMoney();
		this.AccountDate = fDate.getDate( aFIDataTransGatherSchema.getAccountDate());
		this.SaleChnl = aFIDataTransGatherSchema.getSaleChnl();
		this.ManageCom = aFIDataTransGatherSchema.getManageCom();
		this.ExecuteCom = aFIDataTransGatherSchema.getExecuteCom();
		this.RiskCode = aFIDataTransGatherSchema.getRiskCode();
		this.CostCenter = aFIDataTransGatherSchema.getCostCenter();
		this.NotesNo = aFIDataTransGatherSchema.getNotesNo();
		this.CustomerID = aFIDataTransGatherSchema.getCustomerID();
		this.Budget = aFIDataTransGatherSchema.getBudget();
		this.CashFlow = aFIDataTransGatherSchema.getCashFlow();
		this.Currency = aFIDataTransGatherSchema.getCurrency();
		this.ReMark = aFIDataTransGatherSchema.getReMark();
		this.StandByString1 = aFIDataTransGatherSchema.getStandByString1();
		this.StandByString2 = aFIDataTransGatherSchema.getStandByString2();
		this.StandByString3 = aFIDataTransGatherSchema.getStandByString3();
		this.StandByString4 = aFIDataTransGatherSchema.getStandByString4();
		this.StandByString5 = aFIDataTransGatherSchema.getStandByString5();
		this.StandByString6 = aFIDataTransGatherSchema.getStandByString6();
		this.StandByString7 = aFIDataTransGatherSchema.getStandByString7();
		this.StandByString8 = aFIDataTransGatherSchema.getStandByString8();
		this.StandByString9 = aFIDataTransGatherSchema.getStandByString9();
		this.StandByString10 = aFIDataTransGatherSchema.getStandByString10();
		this.StandByString11 = aFIDataTransGatherSchema.getStandByString11();
		this.StandByString12 = aFIDataTransGatherSchema.getStandByString12();
		this.StandByString13 = aFIDataTransGatherSchema.getStandByString13();
		this.StandByString14 = aFIDataTransGatherSchema.getStandByString14();
		this.StandByString15 = aFIDataTransGatherSchema.getStandByString15();
		this.StandByNum1 = aFIDataTransGatherSchema.getStandByNum1();
		this.StandByNum2 = aFIDataTransGatherSchema.getStandByNum2();
		this.StandByDate1 = fDate.getDate( aFIDataTransGatherSchema.getStandByDate1());
		this.StandByDate2 = fDate.getDate( aFIDataTransGatherSchema.getStandByDate2());
		this.ReturnFlag = aFIDataTransGatherSchema.getReturnFlag();
		this.VoucherNo = aFIDataTransGatherSchema.getVoucherNo();
		this.YearMonth = aFIDataTransGatherSchema.getYearMonth();
		this.VerifyFlag = aFIDataTransGatherSchema.getVerifyFlag();
		this.ErrorInfo = aFIDataTransGatherSchema.getErrorInfo();
		this.ReturnDate = fDate.getDate( aFIDataTransGatherSchema.getReturnDate());
		this.ReturnTime = aFIDataTransGatherSchema.getReturnTime();
		this.DealOperator = aFIDataTransGatherSchema.getDealOperator();
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
			if( rs.getString("FSerialNo") == null )
				this.FSerialNo = null;
			else
				this.FSerialNo = rs.getString("FSerialNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("CertificateID") == null )
				this.CertificateID = null;
			else
				this.CertificateID = rs.getString("CertificateID").trim();

			if( rs.getString("AccountCode") == null )
				this.AccountCode = null;
			else
				this.AccountCode = rs.getString("AccountCode").trim();

			if( rs.getString("FinItemType") == null )
				this.FinItemType = null;
			else
				this.FinItemType = rs.getString("FinItemType").trim();

			this.SumMoney = rs.getDouble("SumMoney");
			this.AccountDate = rs.getDate("AccountDate");
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ExecuteCom") == null )
				this.ExecuteCom = null;
			else
				this.ExecuteCom = rs.getString("ExecuteCom").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("CostCenter") == null )
				this.CostCenter = null;
			else
				this.CostCenter = rs.getString("CostCenter").trim();

			if( rs.getString("NotesNo") == null )
				this.NotesNo = null;
			else
				this.NotesNo = rs.getString("NotesNo").trim();

			if( rs.getString("CustomerID") == null )
				this.CustomerID = null;
			else
				this.CustomerID = rs.getString("CustomerID").trim();

			if( rs.getString("Budget") == null )
				this.Budget = null;
			else
				this.Budget = rs.getString("Budget").trim();

			if( rs.getString("CashFlow") == null )
				this.CashFlow = null;
			else
				this.CashFlow = rs.getString("CashFlow").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

			if( rs.getString("StandByString1") == null )
				this.StandByString1 = null;
			else
				this.StandByString1 = rs.getString("StandByString1").trim();

			if( rs.getString("StandByString2") == null )
				this.StandByString2 = null;
			else
				this.StandByString2 = rs.getString("StandByString2").trim();

			if( rs.getString("StandByString3") == null )
				this.StandByString3 = null;
			else
				this.StandByString3 = rs.getString("StandByString3").trim();

			if( rs.getString("StandByString4") == null )
				this.StandByString4 = null;
			else
				this.StandByString4 = rs.getString("StandByString4").trim();

			if( rs.getString("StandByString5") == null )
				this.StandByString5 = null;
			else
				this.StandByString5 = rs.getString("StandByString5").trim();

			if( rs.getString("StandByString6") == null )
				this.StandByString6 = null;
			else
				this.StandByString6 = rs.getString("StandByString6").trim();

			if( rs.getString("StandByString7") == null )
				this.StandByString7 = null;
			else
				this.StandByString7 = rs.getString("StandByString7").trim();

			if( rs.getString("StandByString8") == null )
				this.StandByString8 = null;
			else
				this.StandByString8 = rs.getString("StandByString8").trim();

			if( rs.getString("StandByString9") == null )
				this.StandByString9 = null;
			else
				this.StandByString9 = rs.getString("StandByString9").trim();

			if( rs.getString("StandByString10") == null )
				this.StandByString10 = null;
			else
				this.StandByString10 = rs.getString("StandByString10").trim();

			if( rs.getString("StandByString11") == null )
				this.StandByString11 = null;
			else
				this.StandByString11 = rs.getString("StandByString11").trim();

			if( rs.getString("StandByString12") == null )
				this.StandByString12 = null;
			else
				this.StandByString12 = rs.getString("StandByString12").trim();

			if( rs.getString("StandByString13") == null )
				this.StandByString13 = null;
			else
				this.StandByString13 = rs.getString("StandByString13").trim();

			if( rs.getString("StandByString14") == null )
				this.StandByString14 = null;
			else
				this.StandByString14 = rs.getString("StandByString14").trim();

			if( rs.getString("StandByString15") == null )
				this.StandByString15 = null;
			else
				this.StandByString15 = rs.getString("StandByString15").trim();

			this.StandByNum1 = rs.getDouble("StandByNum1");
			this.StandByNum2 = rs.getDouble("StandByNum2");
			this.StandByDate1 = rs.getDate("StandByDate1");
			this.StandByDate2 = rs.getDate("StandByDate2");
			if( rs.getString("ReturnFlag") == null )
				this.ReturnFlag = null;
			else
				this.ReturnFlag = rs.getString("ReturnFlag").trim();

			if( rs.getString("VoucherNo") == null )
				this.VoucherNo = null;
			else
				this.VoucherNo = rs.getString("VoucherNo").trim();

			if( rs.getString("YearMonth") == null )
				this.YearMonth = null;
			else
				this.YearMonth = rs.getString("YearMonth").trim();

			if( rs.getString("VerifyFlag") == null )
				this.VerifyFlag = null;
			else
				this.VerifyFlag = rs.getString("VerifyFlag").trim();

			if( rs.getString("ErrorInfo") == null )
				this.ErrorInfo = null;
			else
				this.ErrorInfo = rs.getString("ErrorInfo").trim();

			this.ReturnDate = rs.getDate("ReturnDate");
			if( rs.getString("ReturnTime") == null )
				this.ReturnTime = null;
			else
				this.ReturnTime = rs.getString("ReturnTime").trim();

			if( rs.getString("DealOperator") == null )
				this.DealOperator = null;
			else
				this.DealOperator = rs.getString("DealOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataTransGather表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataTransGatherSchema getSchema()
	{
		FIDataTransGatherSchema aFIDataTransGatherSchema = new FIDataTransGatherSchema();
		aFIDataTransGatherSchema.setSchema(this);
		return aFIDataTransGatherSchema;
	}

	public FIDataTransGatherDB getDB()
	{
		FIDataTransGatherDB aDBOper = new FIDataTransGatherDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataTransGather描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(FSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AccountCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( AccountDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ExecuteCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CostCenter)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NotesNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CustomerID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Budget)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CashFlow)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString5)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString6)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString7)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString8)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString9)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString10)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString11)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString12)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString13)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString14)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString15)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(StandByNum1));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(StandByNum2));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StandByDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StandByDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReturnFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(VoucherNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(YearMonth)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(VerifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ErrorInfo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ReturnDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReturnTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataTransGather>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccountCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FinItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AccountDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CostCenter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			NotesNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CustomerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Budget = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CashFlow = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandByString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandByString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandByString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			StandByString4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StandByString5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			StandByString6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StandByString7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StandByString8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			StandByString9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			StandByString10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			StandByString11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandByString12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			StandByString13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			StandByString14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			StandByString15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			StandByNum1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			StandByNum2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			StandByDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			StandByDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			ReturnFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			VoucherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			YearMonth = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			VerifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			ErrorInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ReturnDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			ReturnTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			DealOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransGatherSchema";
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
		if (FCode.equalsIgnoreCase("FSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FSerialNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateID));
		}
		if (FCode.equalsIgnoreCase("AccountCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountCode));
		}
		if (FCode.equalsIgnoreCase("FinItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemType));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("AccountDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccountDate()));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExecuteCom));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("CostCenter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostCenter));
		}
		if (FCode.equalsIgnoreCase("NotesNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NotesNo));
		}
		if (FCode.equalsIgnoreCase("CustomerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerID));
		}
		if (FCode.equalsIgnoreCase("Budget"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Budget));
		}
		if (FCode.equalsIgnoreCase("CashFlow"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashFlow));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
		}
		if (FCode.equalsIgnoreCase("StandByString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString1));
		}
		if (FCode.equalsIgnoreCase("StandByString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString2));
		}
		if (FCode.equalsIgnoreCase("StandByString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString3));
		}
		if (FCode.equalsIgnoreCase("StandByString4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString4));
		}
		if (FCode.equalsIgnoreCase("StandByString5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString5));
		}
		if (FCode.equalsIgnoreCase("StandByString6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString6));
		}
		if (FCode.equalsIgnoreCase("StandByString7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString7));
		}
		if (FCode.equalsIgnoreCase("StandByString8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString8));
		}
		if (FCode.equalsIgnoreCase("StandByString9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString9));
		}
		if (FCode.equalsIgnoreCase("StandByString10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString10));
		}
		if (FCode.equalsIgnoreCase("StandByString11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString11));
		}
		if (FCode.equalsIgnoreCase("StandByString12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString12));
		}
		if (FCode.equalsIgnoreCase("StandByString13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString13));
		}
		if (FCode.equalsIgnoreCase("StandByString14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString14));
		}
		if (FCode.equalsIgnoreCase("StandByString15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByString15));
		}
		if (FCode.equalsIgnoreCase("StandByNum1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByNum1));
		}
		if (FCode.equalsIgnoreCase("StandByNum2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByNum2));
		}
		if (FCode.equalsIgnoreCase("StandByDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate1()));
		}
		if (FCode.equalsIgnoreCase("StandByDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate2()));
		}
		if (FCode.equalsIgnoreCase("ReturnFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnFlag));
		}
		if (FCode.equalsIgnoreCase("VoucherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VoucherNo));
		}
		if (FCode.equalsIgnoreCase("YearMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YearMonth));
		}
		if (FCode.equalsIgnoreCase("VerifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VerifyFlag));
		}
		if (FCode.equalsIgnoreCase("ErrorInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorInfo));
		}
		if (FCode.equalsIgnoreCase("ReturnDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReturnDate()));
		}
		if (FCode.equalsIgnoreCase("ReturnTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnTime));
		}
		if (FCode.equalsIgnoreCase("DealOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealOperator));
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
				strFieldValue = StrTool.GBKToUnicode(FSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CertificateID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccountCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FinItemType);
				break;
			case 5:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccountDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CostCenter);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(NotesNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CustomerID);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Budget);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CashFlow);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StandByString1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandByString2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandByString3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(StandByString4);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StandByString5);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(StandByString6);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StandByString7);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(StandByString8);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(StandByString9);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(StandByString10);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(StandByString11);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandByString12);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(StandByString13);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(StandByString14);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(StandByString15);
				break;
			case 33:
				strFieldValue = String.valueOf(StandByNum1);
				break;
			case 34:
				strFieldValue = String.valueOf(StandByNum2);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate1()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate2()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ReturnFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(VoucherNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(YearMonth);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(VerifyFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(ErrorInfo);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReturnDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ReturnTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(DealOperator);
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

		if (FCode.equalsIgnoreCase("FSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FSerialNo = FValue.trim();
			}
			else
				FSerialNo = null;
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
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertificateID = FValue.trim();
			}
			else
				CertificateID = null;
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
		if (FCode.equalsIgnoreCase("FinItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemType = FValue.trim();
			}
			else
				FinItemType = null;
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccountDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccountDate = fDate.getDate( FValue );
			}
			else
				AccountDate = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ExecuteCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExecuteCom = FValue.trim();
			}
			else
				ExecuteCom = null;
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
		if (FCode.equalsIgnoreCase("CostCenter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CostCenter = FValue.trim();
			}
			else
				CostCenter = null;
		}
		if (FCode.equalsIgnoreCase("NotesNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NotesNo = FValue.trim();
			}
			else
				NotesNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerID = FValue.trim();
			}
			else
				CustomerID = null;
		}
		if (FCode.equalsIgnoreCase("Budget"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Budget = FValue.trim();
			}
			else
				Budget = null;
		}
		if (FCode.equalsIgnoreCase("CashFlow"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CashFlow = FValue.trim();
			}
			else
				CashFlow = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		if (FCode.equalsIgnoreCase("StandByString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString1 = FValue.trim();
			}
			else
				StandByString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString2 = FValue.trim();
			}
			else
				StandByString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString3 = FValue.trim();
			}
			else
				StandByString3 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString4 = FValue.trim();
			}
			else
				StandByString4 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString5 = FValue.trim();
			}
			else
				StandByString5 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString6 = FValue.trim();
			}
			else
				StandByString6 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString7 = FValue.trim();
			}
			else
				StandByString7 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString8 = FValue.trim();
			}
			else
				StandByString8 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString9 = FValue.trim();
			}
			else
				StandByString9 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString10 = FValue.trim();
			}
			else
				StandByString10 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString11 = FValue.trim();
			}
			else
				StandByString11 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString12 = FValue.trim();
			}
			else
				StandByString12 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString13 = FValue.trim();
			}
			else
				StandByString13 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString14 = FValue.trim();
			}
			else
				StandByString14 = null;
		}
		if (FCode.equalsIgnoreCase("StandByString15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByString15 = FValue.trim();
			}
			else
				StandByString15 = null;
		}
		if (FCode.equalsIgnoreCase("StandByNum1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandByNum1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandByNum2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandByNum2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandByDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandByDate1 = fDate.getDate( FValue );
			}
			else
				StandByDate1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StandByDate2 = fDate.getDate( FValue );
			}
			else
				StandByDate2 = null;
		}
		if (FCode.equalsIgnoreCase("ReturnFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnFlag = FValue.trim();
			}
			else
				ReturnFlag = null;
		}
		if (FCode.equalsIgnoreCase("VoucherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VoucherNo = FValue.trim();
			}
			else
				VoucherNo = null;
		}
		if (FCode.equalsIgnoreCase("YearMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				YearMonth = FValue.trim();
			}
			else
				YearMonth = null;
		}
		if (FCode.equalsIgnoreCase("VerifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VerifyFlag = FValue.trim();
			}
			else
				VerifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("ErrorInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorInfo = FValue.trim();
			}
			else
				ErrorInfo = null;
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
		if (FCode.equalsIgnoreCase("ReturnTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnTime = FValue.trim();
			}
			else
				ReturnTime = null;
		}
		if (FCode.equalsIgnoreCase("DealOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealOperator = FValue.trim();
			}
			else
				DealOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataTransGatherSchema other = (FIDataTransGatherSchema)otherObject;
		return
			FSerialNo.equals(other.getFSerialNo())
			&& BatchNo.equals(other.getBatchNo())
			&& CertificateID.equals(other.getCertificateID())
			&& AccountCode.equals(other.getAccountCode())
			&& FinItemType.equals(other.getFinItemType())
			&& SumMoney == other.getSumMoney()
			&& fDate.getString(AccountDate).equals(other.getAccountDate())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ManageCom.equals(other.getManageCom())
			&& ExecuteCom.equals(other.getExecuteCom())
			&& RiskCode.equals(other.getRiskCode())
			&& CostCenter.equals(other.getCostCenter())
			&& NotesNo.equals(other.getNotesNo())
			&& CustomerID.equals(other.getCustomerID())
			&& Budget.equals(other.getBudget())
			&& CashFlow.equals(other.getCashFlow())
			&& Currency.equals(other.getCurrency())
			&& ReMark.equals(other.getReMark())
			&& StandByString1.equals(other.getStandByString1())
			&& StandByString2.equals(other.getStandByString2())
			&& StandByString3.equals(other.getStandByString3())
			&& StandByString4.equals(other.getStandByString4())
			&& StandByString5.equals(other.getStandByString5())
			&& StandByString6.equals(other.getStandByString6())
			&& StandByString7.equals(other.getStandByString7())
			&& StandByString8.equals(other.getStandByString8())
			&& StandByString9.equals(other.getStandByString9())
			&& StandByString10.equals(other.getStandByString10())
			&& StandByString11.equals(other.getStandByString11())
			&& StandByString12.equals(other.getStandByString12())
			&& StandByString13.equals(other.getStandByString13())
			&& StandByString14.equals(other.getStandByString14())
			&& StandByString15.equals(other.getStandByString15())
			&& StandByNum1 == other.getStandByNum1()
			&& StandByNum2 == other.getStandByNum2()
			&& fDate.getString(StandByDate1).equals(other.getStandByDate1())
			&& fDate.getString(StandByDate2).equals(other.getStandByDate2())
			&& ReturnFlag.equals(other.getReturnFlag())
			&& VoucherNo.equals(other.getVoucherNo())
			&& YearMonth.equals(other.getYearMonth())
			&& VerifyFlag.equals(other.getVerifyFlag())
			&& ErrorInfo.equals(other.getErrorInfo())
			&& fDate.getString(ReturnDate).equals(other.getReturnDate())
			&& ReturnTime.equals(other.getReturnTime())
			&& DealOperator.equals(other.getDealOperator());
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
		if( strFieldName.equals("FSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("CertificateID") ) {
			return 2;
		}
		if( strFieldName.equals("AccountCode") ) {
			return 3;
		}
		if( strFieldName.equals("FinItemType") ) {
			return 4;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 5;
		}
		if( strFieldName.equals("AccountDate") ) {
			return 6;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 9;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 10;
		}
		if( strFieldName.equals("CostCenter") ) {
			return 11;
		}
		if( strFieldName.equals("NotesNo") ) {
			return 12;
		}
		if( strFieldName.equals("CustomerID") ) {
			return 13;
		}
		if( strFieldName.equals("Budget") ) {
			return 14;
		}
		if( strFieldName.equals("CashFlow") ) {
			return 15;
		}
		if( strFieldName.equals("Currency") ) {
			return 16;
		}
		if( strFieldName.equals("ReMark") ) {
			return 17;
		}
		if( strFieldName.equals("StandByString1") ) {
			return 18;
		}
		if( strFieldName.equals("StandByString2") ) {
			return 19;
		}
		if( strFieldName.equals("StandByString3") ) {
			return 20;
		}
		if( strFieldName.equals("StandByString4") ) {
			return 21;
		}
		if( strFieldName.equals("StandByString5") ) {
			return 22;
		}
		if( strFieldName.equals("StandByString6") ) {
			return 23;
		}
		if( strFieldName.equals("StandByString7") ) {
			return 24;
		}
		if( strFieldName.equals("StandByString8") ) {
			return 25;
		}
		if( strFieldName.equals("StandByString9") ) {
			return 26;
		}
		if( strFieldName.equals("StandByString10") ) {
			return 27;
		}
		if( strFieldName.equals("StandByString11") ) {
			return 28;
		}
		if( strFieldName.equals("StandByString12") ) {
			return 29;
		}
		if( strFieldName.equals("StandByString13") ) {
			return 30;
		}
		if( strFieldName.equals("StandByString14") ) {
			return 31;
		}
		if( strFieldName.equals("StandByString15") ) {
			return 32;
		}
		if( strFieldName.equals("StandByNum1") ) {
			return 33;
		}
		if( strFieldName.equals("StandByNum2") ) {
			return 34;
		}
		if( strFieldName.equals("StandByDate1") ) {
			return 35;
		}
		if( strFieldName.equals("StandByDate2") ) {
			return 36;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return 37;
		}
		if( strFieldName.equals("VoucherNo") ) {
			return 38;
		}
		if( strFieldName.equals("YearMonth") ) {
			return 39;
		}
		if( strFieldName.equals("VerifyFlag") ) {
			return 40;
		}
		if( strFieldName.equals("ErrorInfo") ) {
			return 41;
		}
		if( strFieldName.equals("ReturnDate") ) {
			return 42;
		}
		if( strFieldName.equals("ReturnTime") ) {
			return 43;
		}
		if( strFieldName.equals("DealOperator") ) {
			return 44;
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
				strFieldName = "FSerialNo";
				break;
			case 1:
				strFieldName = "BatchNo";
				break;
			case 2:
				strFieldName = "CertificateID";
				break;
			case 3:
				strFieldName = "AccountCode";
				break;
			case 4:
				strFieldName = "FinItemType";
				break;
			case 5:
				strFieldName = "SumMoney";
				break;
			case 6:
				strFieldName = "AccountDate";
				break;
			case 7:
				strFieldName = "SaleChnl";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "ExecuteCom";
				break;
			case 10:
				strFieldName = "RiskCode";
				break;
			case 11:
				strFieldName = "CostCenter";
				break;
			case 12:
				strFieldName = "NotesNo";
				break;
			case 13:
				strFieldName = "CustomerID";
				break;
			case 14:
				strFieldName = "Budget";
				break;
			case 15:
				strFieldName = "CashFlow";
				break;
			case 16:
				strFieldName = "Currency";
				break;
			case 17:
				strFieldName = "ReMark";
				break;
			case 18:
				strFieldName = "StandByString1";
				break;
			case 19:
				strFieldName = "StandByString2";
				break;
			case 20:
				strFieldName = "StandByString3";
				break;
			case 21:
				strFieldName = "StandByString4";
				break;
			case 22:
				strFieldName = "StandByString5";
				break;
			case 23:
				strFieldName = "StandByString6";
				break;
			case 24:
				strFieldName = "StandByString7";
				break;
			case 25:
				strFieldName = "StandByString8";
				break;
			case 26:
				strFieldName = "StandByString9";
				break;
			case 27:
				strFieldName = "StandByString10";
				break;
			case 28:
				strFieldName = "StandByString11";
				break;
			case 29:
				strFieldName = "StandByString12";
				break;
			case 30:
				strFieldName = "StandByString13";
				break;
			case 31:
				strFieldName = "StandByString14";
				break;
			case 32:
				strFieldName = "StandByString15";
				break;
			case 33:
				strFieldName = "StandByNum1";
				break;
			case 34:
				strFieldName = "StandByNum2";
				break;
			case 35:
				strFieldName = "StandByDate1";
				break;
			case 36:
				strFieldName = "StandByDate2";
				break;
			case 37:
				strFieldName = "ReturnFlag";
				break;
			case 38:
				strFieldName = "VoucherNo";
				break;
			case 39:
				strFieldName = "YearMonth";
				break;
			case 40:
				strFieldName = "VerifyFlag";
				break;
			case 41:
				strFieldName = "ErrorInfo";
				break;
			case 42:
				strFieldName = "ReturnDate";
				break;
			case 43:
				strFieldName = "ReturnTime";
				break;
			case 44:
				strFieldName = "DealOperator";
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
		if( strFieldName.equals("FSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertificateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccountDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CostCenter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NotesNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Budget") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CashFlow") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByString15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByNum1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandByNum2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandByDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandByDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VoucherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("YearMonth") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VerifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReturnTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealOperator") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
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
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
