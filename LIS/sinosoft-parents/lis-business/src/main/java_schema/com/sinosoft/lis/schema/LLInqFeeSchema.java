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
import com.sinosoft.lis.db.LLInqFeeDB;

/*
 * <p>ClassName: LLInqFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLInqFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLInqFeeSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 调查序号 */
	private String InqNo;
	/** 调查机构 */
	private String InqDept;
	/** 费用类型 */
	private String FeeType;
	/** 发生时间 */
	private Date FeeDate;
	/** 金额 */
	private double FeeSum;
	/** 领款人 */
	private String Payee;
	/** 领款方式 */
	private String PayeeType;
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
	/** 备注 */
	private String Remark;
	/** 银行编码 */
	private String BankCode;
	/** 银行帐号 */
	private String BankAccNo;
	/** 银行帐户名 */
	private String AccName;
	/** 账户修改原因代码 */
	private String ModiReasonCode;
	/** 账户修改原因 */
	private String ModiReasonDesc;
	/** 原银行编码 */
	private String OBankCode;
	/** 原银行账号 */
	private String OBankAccNo;
	/** 原银行账户名 */
	private String OAccName;
	/** 确认标志 */
	private String ConFirmFlag;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLInqFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "InqNo";
		pk[2] = "InqDept";
		pk[3] = "FeeType";

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
		LLInqFeeSchema cloned = (LLInqFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getInqNo()
	{
		return InqNo;
	}
	public void setInqNo(String aInqNo)
	{
		if(aInqNo!=null && aInqNo.length()>20)
			throw new IllegalArgumentException("调查序号InqNo值"+aInqNo+"的长度"+aInqNo.length()+"大于最大值20");
		InqNo = aInqNo;
	}
	public String getInqDept()
	{
		return InqDept;
	}
	public void setInqDept(String aInqDept)
	{
		if(aInqDept!=null && aInqDept.length()>20)
			throw new IllegalArgumentException("调查机构InqDept值"+aInqDept+"的长度"+aInqDept.length()+"大于最大值20");
		InqDept = aInqDept;
	}
	/**
	* 01取证工料???（影音摄录、冲印、复印）<p>
	* 02取证劳务费（检???费、鉴定费、公证费、业务招待费、慰问费、交通费、通讯费、差旅费、误餐费、委托取证费
	*/
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		if(aFeeType!=null && aFeeType.length()>6)
			throw new IllegalArgumentException("费用类型FeeType值"+aFeeType+"的长度"+aFeeType.length()+"大于最大值6");
		FeeType = aFeeType;
	}
	public String getFeeDate()
	{
		if( FeeDate != null )
			return fDate.getString(FeeDate);
		else
			return null;
	}
	public void setFeeDate(Date aFeeDate)
	{
		FeeDate = aFeeDate;
	}
	public void setFeeDate(String aFeeDate)
	{
		if (aFeeDate != null && !aFeeDate.equals("") )
		{
			FeeDate = fDate.getDate( aFeeDate );
		}
		else
			FeeDate = null;
	}

	public double getFeeSum()
	{
		return FeeSum;
	}
	public void setFeeSum(double aFeeSum)
	{
		FeeSum = aFeeSum;
	}
	public void setFeeSum(String aFeeSum)
	{
		if (aFeeSum != null && !aFeeSum.equals(""))
		{
			Double tDouble = new Double(aFeeSum);
			double d = tDouble.doubleValue();
			FeeSum = d;
		}
	}

	public String getPayee()
	{
		return Payee;
	}
	public void setPayee(String aPayee)
	{
		if(aPayee!=null && aPayee.length()>20)
			throw new IllegalArgumentException("领款人Payee值"+aPayee+"的长度"+aPayee.length()+"大于最大值20");
		Payee = aPayee;
	}
	public String getPayeeType()
	{
		return PayeeType;
	}
	public void setPayeeType(String aPayeeType)
	{
		if(aPayeeType!=null && aPayeeType.length()>6)
			throw new IllegalArgumentException("领款方式PayeeType值"+aPayeeType+"的长度"+aPayeeType.length()+"大于最大值6");
		PayeeType = aPayeeType;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>30)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值30");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>10)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值10");
		BankCode = aBankCode;
	}
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		if(aBankAccNo!=null && aBankAccNo.length()>40)
			throw new IllegalArgumentException("银行帐号BankAccNo值"+aBankAccNo+"的长度"+aBankAccNo.length()+"大于最大值40");
		BankAccNo = aBankAccNo;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		if(aAccName!=null && aAccName.length()>60)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值60");
		AccName = aAccName;
	}
	public String getModiReasonCode()
	{
		return ModiReasonCode;
	}
	public void setModiReasonCode(String aModiReasonCode)
	{
		if(aModiReasonCode!=null && aModiReasonCode.length()>6)
			throw new IllegalArgumentException("账户修改原因代码ModiReasonCode值"+aModiReasonCode+"的长度"+aModiReasonCode.length()+"大于最大值6");
		ModiReasonCode = aModiReasonCode;
	}
	public String getModiReasonDesc()
	{
		return ModiReasonDesc;
	}
	public void setModiReasonDesc(String aModiReasonDesc)
	{
		if(aModiReasonDesc!=null && aModiReasonDesc.length()>20)
			throw new IllegalArgumentException("账户修改原因ModiReasonDesc值"+aModiReasonDesc+"的长度"+aModiReasonDesc.length()+"大于最大值20");
		ModiReasonDesc = aModiReasonDesc;
	}
	public String getOBankCode()
	{
		return OBankCode;
	}
	public void setOBankCode(String aOBankCode)
	{
		if(aOBankCode!=null && aOBankCode.length()>10)
			throw new IllegalArgumentException("原银行编码OBankCode值"+aOBankCode+"的长度"+aOBankCode.length()+"大于最大值10");
		OBankCode = aOBankCode;
	}
	public String getOBankAccNo()
	{
		return OBankAccNo;
	}
	public void setOBankAccNo(String aOBankAccNo)
	{
		if(aOBankAccNo!=null && aOBankAccNo.length()>40)
			throw new IllegalArgumentException("原银行账号OBankAccNo值"+aOBankAccNo+"的长度"+aOBankAccNo.length()+"大于最大值40");
		OBankAccNo = aOBankAccNo;
	}
	public String getOAccName()
	{
		return OAccName;
	}
	public void setOAccName(String aOAccName)
	{
		if(aOAccName!=null && aOAccName.length()>60)
			throw new IllegalArgumentException("原银行账户名OAccName值"+aOAccName+"的长度"+aOAccName.length()+"大于最大值60");
		OAccName = aOAccName;
	}
	public String getConFirmFlag()
	{
		return ConFirmFlag;
	}
	public void setConFirmFlag(String aConFirmFlag)
	{
		if(aConFirmFlag!=null && aConFirmFlag.length()>6)
			throw new IllegalArgumentException("确认标志ConFirmFlag值"+aConFirmFlag+"的长度"+aConFirmFlag.length()+"大于最大值6");
		ConFirmFlag = aConFirmFlag;
	}

	/**
	* 使用另外一个 LLInqFeeSchema 对象给 Schema 赋值
	* @param: aLLInqFeeSchema LLInqFeeSchema
	**/
	public void setSchema(LLInqFeeSchema aLLInqFeeSchema)
	{
		this.ClmNo = aLLInqFeeSchema.getClmNo();
		this.InqNo = aLLInqFeeSchema.getInqNo();
		this.InqDept = aLLInqFeeSchema.getInqDept();
		this.FeeType = aLLInqFeeSchema.getFeeType();
		this.FeeDate = fDate.getDate( aLLInqFeeSchema.getFeeDate());
		this.FeeSum = aLLInqFeeSchema.getFeeSum();
		this.Payee = aLLInqFeeSchema.getPayee();
		this.PayeeType = aLLInqFeeSchema.getPayeeType();
		this.Operator = aLLInqFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLInqFeeSchema.getMakeDate());
		this.MakeTime = aLLInqFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLInqFeeSchema.getModifyDate());
		this.ModifyTime = aLLInqFeeSchema.getModifyTime();
		this.Remark = aLLInqFeeSchema.getRemark();
		this.BankCode = aLLInqFeeSchema.getBankCode();
		this.BankAccNo = aLLInqFeeSchema.getBankAccNo();
		this.AccName = aLLInqFeeSchema.getAccName();
		this.ModiReasonCode = aLLInqFeeSchema.getModiReasonCode();
		this.ModiReasonDesc = aLLInqFeeSchema.getModiReasonDesc();
		this.OBankCode = aLLInqFeeSchema.getOBankCode();
		this.OBankAccNo = aLLInqFeeSchema.getOBankAccNo();
		this.OAccName = aLLInqFeeSchema.getOAccName();
		this.ConFirmFlag = aLLInqFeeSchema.getConFirmFlag();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("InqNo") == null )
				this.InqNo = null;
			else
				this.InqNo = rs.getString("InqNo").trim();

			if( rs.getString("InqDept") == null )
				this.InqDept = null;
			else
				this.InqDept = rs.getString("InqDept").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			this.FeeDate = rs.getDate("FeeDate");
			this.FeeSum = rs.getDouble("FeeSum");
			if( rs.getString("Payee") == null )
				this.Payee = null;
			else
				this.Payee = rs.getString("Payee").trim();

			if( rs.getString("PayeeType") == null )
				this.PayeeType = null;
			else
				this.PayeeType = rs.getString("PayeeType").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("ModiReasonCode") == null )
				this.ModiReasonCode = null;
			else
				this.ModiReasonCode = rs.getString("ModiReasonCode").trim();

			if( rs.getString("ModiReasonDesc") == null )
				this.ModiReasonDesc = null;
			else
				this.ModiReasonDesc = rs.getString("ModiReasonDesc").trim();

			if( rs.getString("OBankCode") == null )
				this.OBankCode = null;
			else
				this.OBankCode = rs.getString("OBankCode").trim();

			if( rs.getString("OBankAccNo") == null )
				this.OBankAccNo = null;
			else
				this.OBankAccNo = rs.getString("OBankAccNo").trim();

			if( rs.getString("OAccName") == null )
				this.OAccName = null;
			else
				this.OAccName = rs.getString("OAccName").trim();

			if( rs.getString("ConFirmFlag") == null )
				this.ConFirmFlag = null;
			else
				this.ConFirmFlag = rs.getString("ConFirmFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLInqFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLInqFeeSchema getSchema()
	{
		LLInqFeeSchema aLLInqFeeSchema = new LLInqFeeSchema();
		aLLInqFeeSchema.setSchema(this);
		return aLLInqFeeSchema;
	}

	public LLInqFeeDB getDB()
	{
		LLInqFeeDB aDBOper = new LLInqFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FeeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Payee)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModiReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModiReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConFirmFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InqDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			FeeSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			Payee = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModiReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModiReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			OBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			OBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			OAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ConFirmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqFeeSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqNo));
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqDept));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("FeeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFeeDate()));
		}
		if (FCode.equalsIgnoreCase("FeeSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeSum));
		}
		if (FCode.equalsIgnoreCase("Payee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Payee));
		}
		if (FCode.equalsIgnoreCase("PayeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeType));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("ModiReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModiReasonCode));
		}
		if (FCode.equalsIgnoreCase("ModiReasonDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModiReasonDesc));
		}
		if (FCode.equalsIgnoreCase("OBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OBankCode));
		}
		if (FCode.equalsIgnoreCase("OBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OBankAccNo));
		}
		if (FCode.equalsIgnoreCase("OAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OAccName));
		}
		if (FCode.equalsIgnoreCase("ConFirmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConFirmFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InqNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InqDept);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFeeDate()));
				break;
			case 5:
				strFieldValue = String.valueOf(FeeSum);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Payee);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PayeeType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModiReasonCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModiReasonDesc);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(OBankCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(OBankAccNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(OAccName);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ConFirmFlag);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqNo = FValue.trim();
			}
			else
				InqNo = null;
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqDept = FValue.trim();
			}
			else
				InqDept = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		if (FCode.equalsIgnoreCase("FeeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FeeDate = fDate.getDate( FValue );
			}
			else
				FeeDate = null;
		}
		if (FCode.equalsIgnoreCase("FeeSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("Payee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Payee = FValue.trim();
			}
			else
				Payee = null;
		}
		if (FCode.equalsIgnoreCase("PayeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeType = FValue.trim();
			}
			else
				PayeeType = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("ModiReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModiReasonCode = FValue.trim();
			}
			else
				ModiReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("ModiReasonDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModiReasonDesc = FValue.trim();
			}
			else
				ModiReasonDesc = null;
		}
		if (FCode.equalsIgnoreCase("OBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OBankCode = FValue.trim();
			}
			else
				OBankCode = null;
		}
		if (FCode.equalsIgnoreCase("OBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OBankAccNo = FValue.trim();
			}
			else
				OBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("OAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OAccName = FValue.trim();
			}
			else
				OAccName = null;
		}
		if (FCode.equalsIgnoreCase("ConFirmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConFirmFlag = FValue.trim();
			}
			else
				ConFirmFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLInqFeeSchema other = (LLInqFeeSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& InqNo.equals(other.getInqNo())
			&& InqDept.equals(other.getInqDept())
			&& FeeType.equals(other.getFeeType())
			&& fDate.getString(FeeDate).equals(other.getFeeDate())
			&& FeeSum == other.getFeeSum()
			&& Payee.equals(other.getPayee())
			&& PayeeType.equals(other.getPayeeType())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& ModiReasonCode.equals(other.getModiReasonCode())
			&& ModiReasonDesc.equals(other.getModiReasonDesc())
			&& OBankCode.equals(other.getOBankCode())
			&& OBankAccNo.equals(other.getOBankAccNo())
			&& OAccName.equals(other.getOAccName())
			&& ConFirmFlag.equals(other.getConFirmFlag());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("InqNo") ) {
			return 1;
		}
		if( strFieldName.equals("InqDept") ) {
			return 2;
		}
		if( strFieldName.equals("FeeType") ) {
			return 3;
		}
		if( strFieldName.equals("FeeDate") ) {
			return 4;
		}
		if( strFieldName.equals("FeeSum") ) {
			return 5;
		}
		if( strFieldName.equals("Payee") ) {
			return 6;
		}
		if( strFieldName.equals("PayeeType") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("Remark") ) {
			return 13;
		}
		if( strFieldName.equals("BankCode") ) {
			return 14;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 15;
		}
		if( strFieldName.equals("AccName") ) {
			return 16;
		}
		if( strFieldName.equals("ModiReasonCode") ) {
			return 17;
		}
		if( strFieldName.equals("ModiReasonDesc") ) {
			return 18;
		}
		if( strFieldName.equals("OBankCode") ) {
			return 19;
		}
		if( strFieldName.equals("OBankAccNo") ) {
			return 20;
		}
		if( strFieldName.equals("OAccName") ) {
			return 21;
		}
		if( strFieldName.equals("ConFirmFlag") ) {
			return 22;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "InqNo";
				break;
			case 2:
				strFieldName = "InqDept";
				break;
			case 3:
				strFieldName = "FeeType";
				break;
			case 4:
				strFieldName = "FeeDate";
				break;
			case 5:
				strFieldName = "FeeSum";
				break;
			case 6:
				strFieldName = "Payee";
				break;
			case 7:
				strFieldName = "PayeeType";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "Remark";
				break;
			case 14:
				strFieldName = "BankCode";
				break;
			case 15:
				strFieldName = "BankAccNo";
				break;
			case 16:
				strFieldName = "AccName";
				break;
			case 17:
				strFieldName = "ModiReasonCode";
				break;
			case 18:
				strFieldName = "ModiReasonDesc";
				break;
			case 19:
				strFieldName = "OBankCode";
				break;
			case 20:
				strFieldName = "OBankAccNo";
				break;
			case 21:
				strFieldName = "OAccName";
				break;
			case 22:
				strFieldName = "ConFirmFlag";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FeeSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Payee") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeType") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModiReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModiReasonDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConFirmFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
