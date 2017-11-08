/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIDataTransResultDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataTransResultSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataTransResultSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataTransResultSchema.class);

	// @Field
	/** 分录数据流水号 */
	private String FSerialNo;
	/** 业务数据流水号 */
	private String ASerialNo;
	/** 数据采集批次号 */
	private String BatchNo;
	/** 凭证类型编号 */
	private String CertificateID;
	/** 费用类型编码 */
	private String CostID;
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
	/** 凭证大类 */
	private String UpCertificate;
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
	/** 备用数字一 */
	private double StandByNum1;
	/** 备用数字二 */
	private double StandByNum2;
	/** 备用日期一 */
	private Date StandByDate1;
	/** 备用日期二 */
	private Date StandByDate2;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataTransResultSchema()
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
                FIDataTransResultSchema cloned = (FIDataTransResultSchema)super.clone();
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
	public String getASerialNo()
	{
		return ASerialNo;
	}
	public void setASerialNo(String aASerialNo)
	{
		ASerialNo = aASerialNo;
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
	public String getCostID()
	{
		return CostID;
	}
	public void setCostID(String aCostID)
	{
		CostID = aCostID;
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
	public String getUpCertificate()
	{
		return UpCertificate;
	}
	public void setUpCertificate(String aUpCertificate)
	{
		UpCertificate = aUpCertificate;
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


	/**
	* 使用另外一个 FIDataTransResultSchema 对象给 Schema 赋值
	* @param: aFIDataTransResultSchema FIDataTransResultSchema
	**/
	public void setSchema(FIDataTransResultSchema aFIDataTransResultSchema)
	{
		this.FSerialNo = aFIDataTransResultSchema.getFSerialNo();
		this.ASerialNo = aFIDataTransResultSchema.getASerialNo();
		this.BatchNo = aFIDataTransResultSchema.getBatchNo();
		this.CertificateID = aFIDataTransResultSchema.getCertificateID();
		this.CostID = aFIDataTransResultSchema.getCostID();
		this.AccountCode = aFIDataTransResultSchema.getAccountCode();
		this.FinItemType = aFIDataTransResultSchema.getFinItemType();
		this.SumMoney = aFIDataTransResultSchema.getSumMoney();
		this.AccountDate = fDate.getDate( aFIDataTransResultSchema.getAccountDate());
		this.SaleChnl = aFIDataTransResultSchema.getSaleChnl();
		this.ManageCom = aFIDataTransResultSchema.getManageCom();
		this.ExecuteCom = aFIDataTransResultSchema.getExecuteCom();
		this.RiskCode = aFIDataTransResultSchema.getRiskCode();
		this.CostCenter = aFIDataTransResultSchema.getCostCenter();
		this.NotesNo = aFIDataTransResultSchema.getNotesNo();
		this.CustomerID = aFIDataTransResultSchema.getCustomerID();
		this.Budget = aFIDataTransResultSchema.getBudget();
		this.CashFlow = aFIDataTransResultSchema.getCashFlow();
		this.Currency = aFIDataTransResultSchema.getCurrency();
		this.UpCertificate = aFIDataTransResultSchema.getUpCertificate();
		this.StandByString1 = aFIDataTransResultSchema.getStandByString1();
		this.StandByString2 = aFIDataTransResultSchema.getStandByString2();
		this.StandByString3 = aFIDataTransResultSchema.getStandByString3();
		this.StandByString4 = aFIDataTransResultSchema.getStandByString4();
		this.StandByString5 = aFIDataTransResultSchema.getStandByString5();
		this.StandByNum1 = aFIDataTransResultSchema.getStandByNum1();
		this.StandByNum2 = aFIDataTransResultSchema.getStandByNum2();
		this.StandByDate1 = fDate.getDate( aFIDataTransResultSchema.getStandByDate1());
		this.StandByDate2 = fDate.getDate( aFIDataTransResultSchema.getStandByDate2());
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

			if( rs.getString("ASerialNo") == null )
				this.ASerialNo = null;
			else
				this.ASerialNo = rs.getString("ASerialNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("CertificateID") == null )
				this.CertificateID = null;
			else
				this.CertificateID = rs.getString("CertificateID").trim();

			if( rs.getString("CostID") == null )
				this.CostID = null;
			else
				this.CostID = rs.getString("CostID").trim();

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

			if( rs.getString("UpCertificate") == null )
				this.UpCertificate = null;
			else
				this.UpCertificate = rs.getString("UpCertificate").trim();

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

			this.StandByNum1 = rs.getDouble("StandByNum1");
			this.StandByNum2 = rs.getDouble("StandByNum2");
			this.StandByDate1 = rs.getDate("StandByDate1");
			this.StandByDate2 = rs.getDate("StandByDate2");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataTransResult表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransResultSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataTransResultSchema getSchema()
	{
		FIDataTransResultSchema aFIDataTransResultSchema = new FIDataTransResultSchema();
		aFIDataTransResultSchema.setSchema(this);
		return aFIDataTransResultSchema;
	}

	public FIDataTransResultDB getDB()
	{
		FIDataTransResultDB aDBOper = new FIDataTransResultDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataTransResult描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(FSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ASerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CostID)); strReturn.append(SysConst.PACKAGESPILTER);
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
strReturn.append(StrTool.cTrim(UpCertificate)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(StandByString5)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(StandByNum1));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(StandByNum2));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StandByDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StandByDate2 )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataTransResult>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ASerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CostID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AccountCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FinItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			AccountDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ExecuteCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CostCenter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			NotesNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CustomerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Budget = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CashFlow = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			UpCertificate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandByString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			StandByString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StandByString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			StandByString4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StandByString5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StandByNum1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			StandByNum2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			StandByDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			StandByDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataTransResultSchema";
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
		if (FCode.equalsIgnoreCase("ASerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ASerialNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateID));
		}
		if (FCode.equalsIgnoreCase("CostID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostID));
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
		if (FCode.equalsIgnoreCase("UpCertificate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpCertificate));
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
				strFieldValue = StrTool.GBKToUnicode(ASerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CertificateID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CostID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AccountCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FinItemType);
				break;
			case 7:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccountDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ExecuteCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CostCenter);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(NotesNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CustomerID);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Budget);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CashFlow);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(UpCertificate);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandByString1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(StandByString2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StandByString3);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(StandByString4);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StandByString5);
				break;
			case 25:
				strFieldValue = String.valueOf(StandByNum1);
				break;
			case 26:
				strFieldValue = String.valueOf(StandByNum2);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate1()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStandByDate2()));
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
		if (FCode.equalsIgnoreCase("ASerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ASerialNo = FValue.trim();
			}
			else
				ASerialNo = null;
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
		if (FCode.equalsIgnoreCase("CostID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CostID = FValue.trim();
			}
			else
				CostID = null;
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
		if (FCode.equalsIgnoreCase("UpCertificate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpCertificate = FValue.trim();
			}
			else
				UpCertificate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataTransResultSchema other = (FIDataTransResultSchema)otherObject;
		return
			FSerialNo.equals(other.getFSerialNo())
			&& ASerialNo.equals(other.getASerialNo())
			&& BatchNo.equals(other.getBatchNo())
			&& CertificateID.equals(other.getCertificateID())
			&& CostID.equals(other.getCostID())
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
			&& UpCertificate.equals(other.getUpCertificate())
			&& StandByString1.equals(other.getStandByString1())
			&& StandByString2.equals(other.getStandByString2())
			&& StandByString3.equals(other.getStandByString3())
			&& StandByString4.equals(other.getStandByString4())
			&& StandByString5.equals(other.getStandByString5())
			&& StandByNum1 == other.getStandByNum1()
			&& StandByNum2 == other.getStandByNum2()
			&& fDate.getString(StandByDate1).equals(other.getStandByDate1())
			&& fDate.getString(StandByDate2).equals(other.getStandByDate2());
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
		if( strFieldName.equals("ASerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 2;
		}
		if( strFieldName.equals("CertificateID") ) {
			return 3;
		}
		if( strFieldName.equals("CostID") ) {
			return 4;
		}
		if( strFieldName.equals("AccountCode") ) {
			return 5;
		}
		if( strFieldName.equals("FinItemType") ) {
			return 6;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 7;
		}
		if( strFieldName.equals("AccountDate") ) {
			return 8;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("ExecuteCom") ) {
			return 11;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 12;
		}
		if( strFieldName.equals("CostCenter") ) {
			return 13;
		}
		if( strFieldName.equals("NotesNo") ) {
			return 14;
		}
		if( strFieldName.equals("CustomerID") ) {
			return 15;
		}
		if( strFieldName.equals("Budget") ) {
			return 16;
		}
		if( strFieldName.equals("CashFlow") ) {
			return 17;
		}
		if( strFieldName.equals("Currency") ) {
			return 18;
		}
		if( strFieldName.equals("UpCertificate") ) {
			return 19;
		}
		if( strFieldName.equals("StandByString1") ) {
			return 20;
		}
		if( strFieldName.equals("StandByString2") ) {
			return 21;
		}
		if( strFieldName.equals("StandByString3") ) {
			return 22;
		}
		if( strFieldName.equals("StandByString4") ) {
			return 23;
		}
		if( strFieldName.equals("StandByString5") ) {
			return 24;
		}
		if( strFieldName.equals("StandByNum1") ) {
			return 25;
		}
		if( strFieldName.equals("StandByNum2") ) {
			return 26;
		}
		if( strFieldName.equals("StandByDate1") ) {
			return 27;
		}
		if( strFieldName.equals("StandByDate2") ) {
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
				strFieldName = "FSerialNo";
				break;
			case 1:
				strFieldName = "ASerialNo";
				break;
			case 2:
				strFieldName = "BatchNo";
				break;
			case 3:
				strFieldName = "CertificateID";
				break;
			case 4:
				strFieldName = "CostID";
				break;
			case 5:
				strFieldName = "AccountCode";
				break;
			case 6:
				strFieldName = "FinItemType";
				break;
			case 7:
				strFieldName = "SumMoney";
				break;
			case 8:
				strFieldName = "AccountDate";
				break;
			case 9:
				strFieldName = "SaleChnl";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "ExecuteCom";
				break;
			case 12:
				strFieldName = "RiskCode";
				break;
			case 13:
				strFieldName = "CostCenter";
				break;
			case 14:
				strFieldName = "NotesNo";
				break;
			case 15:
				strFieldName = "CustomerID";
				break;
			case 16:
				strFieldName = "Budget";
				break;
			case 17:
				strFieldName = "CashFlow";
				break;
			case 18:
				strFieldName = "Currency";
				break;
			case 19:
				strFieldName = "UpCertificate";
				break;
			case 20:
				strFieldName = "StandByString1";
				break;
			case 21:
				strFieldName = "StandByString2";
				break;
			case 22:
				strFieldName = "StandByString3";
				break;
			case 23:
				strFieldName = "StandByString4";
				break;
			case 24:
				strFieldName = "StandByString5";
				break;
			case 25:
				strFieldName = "StandByNum1";
				break;
			case 26:
				strFieldName = "StandByNum2";
				break;
			case 27:
				strFieldName = "StandByDate1";
				break;
			case 28:
				strFieldName = "StandByDate2";
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
		if( strFieldName.equals("ASerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertificateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CostID") ) {
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
		if( strFieldName.equals("UpCertificate") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
