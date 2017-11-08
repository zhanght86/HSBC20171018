package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FICustomerAccTraceDB;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ChgData;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;

/**
 * <p>
 * ClassName: FICustomerAccTraceSchema
 * </p>
 * <p>
 * Description: 客户账户履历表（FICustomerAccTrace）的Schema文件
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @CreateDate：2009-12-30
 */
public class FICustomerAccTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICustomerAccTraceSchema.class);

	// @Field
	/** 流水号 */
	private String Sequence;

	/** 客户账户号码 */
	private String InsuAccNo;

	/** 客户号码 */
	private String CustomerNo;

	/** 客户类型 */
	private String CustomerType;

	/** 合同号码 */
	private String ContNo;

	/** 业务号码 */
	private String OperationNo;

	/** 业务类型 */
	private String OperationType;

	/** 交费方式 */
	private String PayMode;

	/** 操作类型 */
	private String OperType;

	/** 其它号码 */
	private String OtherNo;

	/** 借贷标记 */
	private String DCFlag;

	/** 币别 */
	private String Currency;

	/** 本次发生额 */
	private double Money;

	/** 状态 */
	private String State;

	/** 核销日期 */
	private Date ConfDate;

	/** 核销时间 */
	private String ConfTime;

	/** 操作员 */
	private String Operator;

	/** 入机日期 */
	private Date MakeDate;

	/** 入机时间 */
	private String MakeTime;

	/** 最后一次修改日期 */
	private Date ModifyDate;

	/** 最后一次修改时间 */
	private String ModifyTime;

	/** 入帐日期 */
	private Date EnterAccDate;

	/** 入帐时间 */
	private String EnterAccTime;

	public static final int FIELDNUM = 23; // 数据库表的字段个数

	private static String[] PK; // 主键

	private FDate fDate = new FDate(); // 处理日期

	public CErrors mErrors; // 错误信息

	// @Constructor
	public FICustomerAccTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Sequence";

		PK = pk;
	}

	/**
	 * Schema克隆
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException
	{
		FICustomerAccTraceSchema cloned = (FICustomerAccTraceSchema) super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSequence()
	{
		return Sequence;
	}

	public void setSequence(String aSequence)
	{
		Sequence = StrTool.cTrim(aSequence);
	}

	public String getInsuAccNo()
	{
		return InsuAccNo;
	}

	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = StrTool.cTrim(aInsuAccNo);
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}

	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = StrTool.cTrim(aCustomerNo);
	}

	public String getCustomerType()
	{
		return CustomerType;
	}

	public void setCustomerType(String aCustomerType)
	{
		CustomerType = StrTool.cTrim(aCustomerType);
	}

	public String getContNo()
	{
		return ContNo;
	}

	public void setContNo(String aContNo)
	{
		ContNo = StrTool.cTrim(aContNo);
	}

	public String getOperationNo()
	{
		return OperationNo;
	}

	public void setOperationNo(String aOperationNo)
	{
		OperationNo = StrTool.cTrim(aOperationNo);
	}

	public String getOperationType()
	{
		return OperationType;
	}

	public void setOperationType(String aOperationType)
	{
		OperationType = StrTool.cTrim(aOperationType);
	}

	public String getPayMode()
	{
		return PayMode;
	}

	public void setPayMode(String aPayMode)
	{
		PayMode = StrTool.cTrim(aPayMode);
	}

	public String getOperType()
	{
		return OperType;
	}

	public void setOperType(String aOperType)
	{
		OperType = StrTool.cTrim(aOperType);
	}

	public String getOtherNo()
	{
		return OtherNo;
	}

	public void setOtherNo(String aOtherNo)
	{
		OtherNo = StrTool.cTrim(aOtherNo);
	}

	public String getDCFlag()
	{
		return DCFlag;
	}

	public void setDCFlag(String aDCFlag)
	{
		DCFlag = StrTool.cTrim(aDCFlag);
	}

	public String getCurrency()
	{
		return Currency;
	}

	public void setCurrency(String aCurrency)
	{
		Currency = StrTool.cTrim(aCurrency);
	}

	public double getMoney()
	{
		return Money;
	}

	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}

	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = Arith.round(tDouble.doubleValue(), 2);
			Money = d;
		}
	}

	public String getState()
	{
		return State;
	}

	public void setState(String aState)
	{
		State = StrTool.cTrim(aState);
	}

	public String getConfDate()
	{
		if (ConfDate != null)
			return fDate.getString(ConfDate);
		else
			return null;
	}

	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}

	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals(""))
		{
			ConfDate = fDate.getDate(aConfDate);
		}
		else
			ConfDate = null;
	}

	public String getConfTime()
	{
		return ConfTime;
	}

	public void setConfTime(String aConfTime)
	{
		ConfTime = StrTool.cTrim(aConfTime);
	}

	public String getOperator()
	{
		return Operator;
	}

	public void setOperator(String aOperator)
	{
		Operator = StrTool.cTrim(aOperator);
	}

	public String getMakeDate()
	{
		if (MakeDate != null)
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
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			MakeDate = fDate.getDate(aMakeDate);
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
		MakeTime = StrTool.cTrim(aMakeTime);
	}

	public String getModifyDate()
	{
		if (ModifyDate != null)
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
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			ModifyDate = fDate.getDate(aModifyDate);
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
		ModifyTime = StrTool.cTrim(aModifyTime);
	}

	public String getEnterAccDate()
	{
		if (EnterAccDate != null)
			return fDate.getString(EnterAccDate);
		else
			return null;
	}

	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}

	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals(""))
		{
			EnterAccDate = fDate.getDate(aEnterAccDate);
		}
		else
			EnterAccDate = null;
	}

	public String getEnterAccTime()
	{
		return EnterAccTime;
	}

	public void setEnterAccTime(String aEnterAccTime)
	{
		EnterAccTime = StrTool.cTrim(aEnterAccTime);
	}

	/**
	 * 使用另外一个 FICustomerAccTraceSchema 对象给 Schema 赋值
	 * @param: aFICustomerAccTraceSchema FICustomerAccTraceSchema
	 **/
	public void setSchema(FICustomerAccTraceSchema aFICustomerAccTraceSchema)
	{
		this.Sequence = aFICustomerAccTraceSchema.getSequence();
		this.InsuAccNo = aFICustomerAccTraceSchema.getInsuAccNo();
		this.CustomerNo = aFICustomerAccTraceSchema.getCustomerNo();
		this.CustomerType = aFICustomerAccTraceSchema.getCustomerType();
		this.ContNo = aFICustomerAccTraceSchema.getContNo();
		this.OperationNo = aFICustomerAccTraceSchema.getOperationNo();
		this.OperationType = aFICustomerAccTraceSchema.getOperationType();
		this.PayMode = aFICustomerAccTraceSchema.getPayMode();
		this.OperType = aFICustomerAccTraceSchema.getOperType();
		this.OtherNo = aFICustomerAccTraceSchema.getOtherNo();
		this.DCFlag = aFICustomerAccTraceSchema.getDCFlag();
		this.Currency = aFICustomerAccTraceSchema.getCurrency();
		this.Money = aFICustomerAccTraceSchema.getMoney();
		this.State = aFICustomerAccTraceSchema.getState();
		this.ConfDate = fDate.getDate(aFICustomerAccTraceSchema.getConfDate());
		this.ConfTime = aFICustomerAccTraceSchema.getConfTime();
		this.Operator = aFICustomerAccTraceSchema.getOperator();
		this.MakeDate = fDate.getDate(aFICustomerAccTraceSchema.getMakeDate());
		this.MakeTime = aFICustomerAccTraceSchema.getMakeTime();
		this.ModifyDate = fDate.getDate(aFICustomerAccTraceSchema.getModifyDate());
		this.ModifyTime = aFICustomerAccTraceSchema.getModifyTime();
		this.EnterAccDate = fDate.getDate(aFICustomerAccTraceSchema.getEnterAccDate());
		this.EnterAccTime = aFICustomerAccTraceSchema.getEnterAccTime();
	}

	/**
	 * 使用 ResultSet 中的第 i 行给 Schema 赋值
	 * @param: rs ResultSet
	 * @param: i int
	 * @return: boolean
	 **/
	public boolean setSchema(ResultSet rs, int i)
	{
		try
		{
			// rs.absolute(i); // 非滚动游标
			if (rs.getString(1) == null)
				this.Sequence = null;
			else
				this.Sequence = rs.getString(1);

			if (rs.getString(2) == null)
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString(2);

			if (rs.getString(3) == null)
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString(3);

			if (rs.getString(4) == null)
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString(4);

			if (rs.getString(5) == null)
				this.ContNo = null;
			else
				this.ContNo = rs.getString(5);

			if (rs.getString(6) == null)
				this.OperationNo = null;
			else
				this.OperationNo = rs.getString(6);

			if (rs.getString(7) == null)
				this.OperationType = null;
			else
				this.OperationType = rs.getString(7);

			if (rs.getString(8) == null)
				this.PayMode = null;
			else
				this.PayMode = rs.getString(8);

			if (rs.getString(9) == null)
				this.OperType = null;
			else
				this.OperType = rs.getString(9);

			if (rs.getString(10) == null)
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString(10);

			if (rs.getString(11) == null)
				this.DCFlag = null;
			else
				this.DCFlag = rs.getString(11);

			if (rs.getString(12) == null)
				this.Currency = null;
			else
				this.Currency = rs.getString(12);

			this.Money = rs.getDouble(13);
			if (rs.getString(14) == null)
				this.State = null;
			else
				this.State = rs.getString(14);

			this.ConfDate = rs.getDate(15);
			if (rs.getString(16) == null)
				this.ConfTime = null;
			else
				this.ConfTime = rs.getString(16);

			if (rs.getString(17) == null)
				this.Operator = null;
			else
				this.Operator = rs.getString(17);

			this.MakeDate = rs.getDate(18);
			if (rs.getString(19) == null)
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString(19);

			this.ModifyDate = rs.getDate(20);
			if (rs.getString(21) == null)
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString(21);

			this.EnterAccDate = rs.getDate(22);
			if (rs.getString(23) == null)
				this.EnterAccTime = null;
			else
				this.EnterAccTime = rs.getString(23);

		}
		catch (SQLException sqle)
		{
			logger.debug("数据库中的FICustomerAccTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public FICustomerAccTraceSchema getSchema()
	{
		FICustomerAccTraceSchema aFICustomerAccTraceSchema = new FICustomerAccTraceSchema();
		aFICustomerAccTraceSchema.setSchema(this);
		return aFICustomerAccTraceSchema;
	}

	public FICustomerAccTraceDB getDB()
	{
		FICustomerAccTraceDB aDBOper = new FICustomerAccTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}

	/**
	 * 数据打包，按 XML 格式打包
	 * @return: String 返回打包后字符串
	 **/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Sequence));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperType));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DCFlag));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(ChgData.chgData(Money));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(ConfDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfTime));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(MakeDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(ModifyDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString(EnterAccDate)));
		strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnterAccTime));
		return strReturn.toString();
	}

	/**
	 * 数据解包，解包顺序参见<A href ={@docRoot}
	 * /dataStructure/tb.html#PrpFICustomerAccTrace>历史记账凭证主表信息</A>表字段
	 * @param: strMessage String 包含一条纪录数据的字符串
	 * @return: boolean
	 **/
	public boolean decode(String strMessage)
	{
		try
		{
			strMessage = StrTool.cTrim(strMessage);
			Sequence = StrTool.getStr(strMessage, 1, SysConst.PACKAGESPILTER);
			InsuAccNo = StrTool.getStr(strMessage, 2, SysConst.PACKAGESPILTER);
			CustomerNo = StrTool.getStr(strMessage, 3, SysConst.PACKAGESPILTER);
			CustomerType = StrTool.getStr(strMessage, 4, SysConst.PACKAGESPILTER);
			ContNo = StrTool.getStr(strMessage, 5, SysConst.PACKAGESPILTER);
			OperationNo = StrTool.getStr(strMessage, 6, SysConst.PACKAGESPILTER);
			OperationType = StrTool.getStr(strMessage, 7, SysConst.PACKAGESPILTER);
			PayMode = StrTool.getStr(strMessage, 8, SysConst.PACKAGESPILTER);
			OperType = StrTool.getStr(strMessage, 9, SysConst.PACKAGESPILTER);
			OtherNo = StrTool.getStr(strMessage, 10, SysConst.PACKAGESPILTER);
			DCFlag = StrTool.getStr(strMessage, 11, SysConst.PACKAGESPILTER);
			Currency = StrTool.getStr(strMessage, 12, SysConst.PACKAGESPILTER);
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage, 13, SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(strMessage, 14, SysConst.PACKAGESPILTER);
			ConfDate = fDate.getDate(StrTool.getStr(strMessage, 15, SysConst.PACKAGESPILTER));
			ConfTime = StrTool.getStr(strMessage, 16, SysConst.PACKAGESPILTER);
			Operator = StrTool.getStr(strMessage, 17, SysConst.PACKAGESPILTER);
			MakeDate = fDate.getDate(StrTool.getStr(strMessage, 18, SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(strMessage, 19, SysConst.PACKAGESPILTER);
			ModifyDate = fDate.getDate(StrTool.getStr(strMessage, 20, SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(strMessage, 21, SysConst.PACKAGESPILTER);
			EnterAccDate = fDate.getDate(StrTool.getStr(strMessage, 22, SysConst.PACKAGESPILTER));
			EnterAccTime = StrTool.getStr(strMessage, 23, SysConst.PACKAGESPILTER);
		}
		catch (NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICustomerAccTraceSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	 * 取得对应传入参数的String形式的字段值
	 * @param: FCode String 希望取得的字段名
	 * @return: String 如果没有对应的字段，返回"" 如果字段值为空，返回"null"
	 **/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equalsIgnoreCase("Sequence"))
		{
			strReturn = StrTool.cTrim(Sequence);
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.cTrim(InsuAccNo);
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.cTrim(CustomerNo);
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.cTrim(CustomerType);
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.cTrim(ContNo);
		}
		if (FCode.equalsIgnoreCase("OperationNo"))
		{
			strReturn = StrTool.cTrim(OperationNo);
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			strReturn = StrTool.cTrim(OperationType);
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.cTrim(PayMode);
		}
		if (FCode.equalsIgnoreCase("OperType"))
		{
			strReturn = StrTool.cTrim(OperType);
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.cTrim(OtherNo);
		}
		if (FCode.equalsIgnoreCase("DCFlag"))
		{
			strReturn = StrTool.cTrim(DCFlag);
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.cTrim(Currency);
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.cTrim(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.cTrim(State);
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.cTrim(this.getConfDate());
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			strReturn = StrTool.cTrim(ConfTime);
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.cTrim(Operator);
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.cTrim(this.getMakeDate());
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.cTrim(MakeTime);
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.cTrim(this.getModifyDate());
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.cTrim(ModifyTime);
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.cTrim(this.getEnterAccDate());
		}
		if (FCode.equalsIgnoreCase("EnterAccTime"))
		{
			strReturn = StrTool.cTrim(EnterAccTime);
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
	 * @return: String 如果没有对应的字段，返回"" 如果字段值为空，返回"null"
	 **/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch (nFieldIndex)
		{
			case 0:
				strFieldValue = StrTool.cTrim(Sequence);
				break;
			case 1:
				strFieldValue = StrTool.cTrim(InsuAccNo);
				break;
			case 2:
				strFieldValue = StrTool.cTrim(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.cTrim(CustomerType);
				break;
			case 4:
				strFieldValue = StrTool.cTrim(ContNo);
				break;
			case 5:
				strFieldValue = StrTool.cTrim(OperationNo);
				break;
			case 6:
				strFieldValue = StrTool.cTrim(OperationType);
				break;
			case 7:
				strFieldValue = StrTool.cTrim(PayMode);
				break;
			case 8:
				strFieldValue = StrTool.cTrim(OperType);
				break;
			case 9:
				strFieldValue = StrTool.cTrim(OtherNo);
				break;
			case 10:
				strFieldValue = StrTool.cTrim(DCFlag);
				break;
			case 11:
				strFieldValue = StrTool.cTrim(Currency);
				break;
			case 12:
				strFieldValue = String.valueOf(Money);
				break;
			case 13:
				strFieldValue = StrTool.cTrim(State);
				break;
			case 14:
				strFieldValue = StrTool.cTrim(this.getConfDate());
				break;
			case 15:
				strFieldValue = StrTool.cTrim(ConfTime);
				break;
			case 16:
				strFieldValue = StrTool.cTrim(Operator);
				break;
			case 17:
				strFieldValue = StrTool.cTrim(this.getMakeDate());
				break;
			case 18:
				strFieldValue = StrTool.cTrim(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.cTrim(this.getModifyDate());
				break;
			case 20:
				strFieldValue = StrTool.cTrim(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.cTrim(this.getEnterAccDate());
				break;
			case 22:
				strFieldValue = StrTool.cTrim(EnterAccTime);
				break;
			default:
				strFieldValue = "";
		};
		if (strFieldValue.equals(""))
		{
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
	public boolean setV(String FCode, String FValue)
	{
		if (StrTool.cTrim(FCode).equals(""))
			return false;

		if (FCode.equalsIgnoreCase("Sequence"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Sequence = FValue.trim();
			}
			else
				Sequence = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				CustomerType = FValue.trim();
			}
			else
				CustomerType = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("OperationNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperationNo = FValue.trim();
			}
			else
				OperationNo = null;
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperationType = FValue.trim();
			}
			else
				OperationType = null;
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("OperType"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OperType = FValue.trim();
			}
			else
				OperType = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("DCFlag"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				DCFlag = FValue.trim();
			}
			else
				DCFlag = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double(FValue);
				double d = tDouble.doubleValue();
				Money = d;
			}
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ConfDate = fDate.getDate(FValue);
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ConfTime = FValue.trim();
			}
			else
				ConfTime = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				MakeDate = fDate.getDate(FValue);
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ModifyDate = fDate.getDate(FValue);
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				EnterAccDate = fDate.getDate(FValue);
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccTime"))
		{
			if (FValue != null && !FValue.equals(""))
			{
				EnterAccTime = FValue.trim();
			}
			else
				EnterAccTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject)
			return true;
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		FICustomerAccTraceSchema other = (FICustomerAccTraceSchema) otherObject;
		return Sequence.equals(other.getSequence()) && InsuAccNo.equals(other.getInsuAccNo())
				&& CustomerNo.equals(other.getCustomerNo()) && CustomerType.equals(other.getCustomerType())
				&& ContNo.equals(other.getContNo()) && OperationNo.equals(other.getOperationNo())
				&& OperationType.equals(other.getOperationType()) && PayMode.equals(other.getPayMode())
				&& OperType.equals(other.getOperType()) && OtherNo.equals(other.getOtherNo())
				&& DCFlag.equals(other.getDCFlag()) && Currency.equals(other.getCurrency())
				&& Money == other.getMoney() && State.equals(other.getState())
				&& fDate.getString(ConfDate).equals(other.getConfDate()) && ConfTime.equals(other.getConfTime())
				&& Operator.equals(other.getOperator()) && fDate.getString(MakeDate).equals(other.getMakeDate())
				&& MakeTime.equals(other.getMakeTime()) && fDate.getString(ModifyDate).equals(other.getModifyDate())
				&& ModifyTime.equals(other.getModifyTime())
				&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
				&& EnterAccTime.equals(other.getEnterAccTime());
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
	 * 取得Schema中指定字段名所对应的索引值 如果没有对应的字段，返回-1
	 * @param: strFieldName String
	 * @return: int
	 **/
	public int getFieldIndex(String strFieldName)
	{
		if (strFieldName.equals("Sequence"))
		{
			return 0;
		}
		if (strFieldName.equals("InsuAccNo"))
		{
			return 1;
		}
		if (strFieldName.equals("CustomerNo"))
		{
			return 2;
		}
		if (strFieldName.equals("CustomerType"))
		{
			return 3;
		}
		if (strFieldName.equals("ContNo"))
		{
			return 4;
		}
		if (strFieldName.equals("OperationNo"))
		{
			return 5;
		}
		if (strFieldName.equals("OperationType"))
		{
			return 6;
		}
		if (strFieldName.equals("PayMode"))
		{
			return 7;
		}
		if (strFieldName.equals("OperType"))
		{
			return 8;
		}
		if (strFieldName.equals("OtherNo"))
		{
			return 9;
		}
		if (strFieldName.equals("DCFlag"))
		{
			return 10;
		}
		if (strFieldName.equals("Currency"))
		{
			return 11;
		}
		if (strFieldName.equals("Money"))
		{
			return 12;
		}
		if (strFieldName.equals("State"))
		{
			return 13;
		}
		if (strFieldName.equals("ConfDate"))
		{
			return 14;
		}
		if (strFieldName.equals("ConfTime"))
		{
			return 15;
		}
		if (strFieldName.equals("Operator"))
		{
			return 16;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return 17;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return 18;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return 19;
		}
		if (strFieldName.equals("ModifyTime"))
		{
			return 20;
		}
		if (strFieldName.equals("EnterAccDate"))
		{
			return 21;
		}
		if (strFieldName.equals("EnterAccTime"))
		{
			return 22;
		}
		return -1;
	}

	/**
	 * 取得Schema中指定索引值所对应的字段名 如果没有对应的字段，返回""
	 * @param: nFieldIndex int
	 * @return: String
	 **/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch (nFieldIndex)
		{
			case 0:
				strFieldName = "Sequence";
				break;
			case 1:
				strFieldName = "InsuAccNo";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "CustomerType";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "OperationNo";
				break;
			case 6:
				strFieldName = "OperationType";
				break;
			case 7:
				strFieldName = "PayMode";
				break;
			case 8:
				strFieldName = "OperType";
				break;
			case 9:
				strFieldName = "OtherNo";
				break;
			case 10:
				strFieldName = "DCFlag";
				break;
			case 11:
				strFieldName = "Currency";
				break;
			case 12:
				strFieldName = "Money";
				break;
			case 13:
				strFieldName = "State";
				break;
			case 14:
				strFieldName = "ConfDate";
				break;
			case 15:
				strFieldName = "ConfTime";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "EnterAccDate";
				break;
			case 22:
				strFieldName = "EnterAccTime";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	 * 取得Schema中指定字段名所对应的字段类型 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	 * @param: strFieldName String
	 * @return: int
	 **/
	public int getFieldType(String strFieldName)
	{
		if (strFieldName.equals("Sequence"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("InsuAccNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CustomerNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("CustomerType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("ContNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OperationNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OperationType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("PayMode"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OperType"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("OtherNo"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("DCFlag"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Currency"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Money"))
		{
			return Schema.TYPE_DOUBLE;
		}
		if (strFieldName.equals("State"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("ConfDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("ConfTime"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("Operator"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("MakeDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("MakeTime"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("ModifyDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("ModifyTime"))
		{
			return Schema.TYPE_STRING;
		}
		if (strFieldName.equals("EnterAccDate"))
		{
			return Schema.TYPE_DATE;
		}
		if (strFieldName.equals("EnterAccTime"))
		{
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	 * 取得Schema中指定索引值所对应的字段类型 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	 * @param: nFieldIndex int
	 * @return: int
	 **/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch (nFieldIndex)
		{
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
