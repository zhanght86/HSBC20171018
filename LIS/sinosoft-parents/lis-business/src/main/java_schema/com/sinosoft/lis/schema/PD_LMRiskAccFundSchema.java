/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_LMRiskAccFundDB;

/*
 * <p>ClassName: PD_LMRiskAccFundSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PD_LMRiskAccFundSchema implements Schema, Cloneable
{
	// @Field
	/** 产品编码 */
	private String RiskCode;
	/** 产品缴费编码 */
	private String PayPlanCode;
	/** 账户缴费编码 */
	private String AccountCode;
	/** 账户缴费名称 */
	private String AccountName;
	/** 账户缴费类型 */
	private String AccountType;
	/** Operator */
	private String Operator;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Modifydate */
	private Date ModifyDate;
	/** Modifytime */
	private String ModifyTime;
	/** 账户缴费英文名称 */
	private String AccountNameEn;
	/** 账户缴费名称繁体 */
	private String AccountNameTr;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskAccFundSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "PayPlanCode";
		pk[2] = "AccountCode";

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
		PD_LMRiskAccFundSchema cloned = (PD_LMRiskAccFundSchema)super.clone();
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
	* 1-3位：险种号码<p>
	* 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3）<p>
	* 1 承保<p>
	* 2 领取<p>
	* 3 赔付<p>
	* 4 现金价值<p>
	* 5 单证描述<p>
	* 5-6位 顺序号
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getAccountCode()
	{
		return AccountCode;
	}
	public void setAccountCode(String aAccountCode)
	{
		AccountCode = aAccountCode;
	}
	/**
	* A--计算责任给付(Amount)、<p>
	* P--计算缴费(Premium)、<p>
	* C--计算赔付(Claim)、<p>
	* G--计算领取(Get)<p>
	* X--描述现金价值。<p>
	* Z--单证描述<p>
	* H 为计算体检额度算法类型<p>
	* M 为查询投保单交费状态算法类型<p>
	* S--查询保单状态算法<p>
	* T--代理人佣金计算算法<p>
	* U--个险核保规则<p>
	* V--承保时CheckField中的描述<p>
	* W--佣金计算的编码
	*/
	public String getAccountName()
	{
		return AccountName;
	}
	public void setAccountName(String aAccountName)
	{
		AccountName = aAccountName;
	}
	public String getAccountType()
	{
		return AccountType;
	}
	public void setAccountType(String aAccountType)
	{
		AccountType = aAccountType;
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
	public String getAccountNameEn()
	{
		return AccountNameEn;
	}
	public void setAccountNameEn(String aAccountNameEn)
	{
		AccountNameEn = aAccountNameEn;
	}
	public String getAccountNameTr()
	{
		return AccountNameTr;
	}
	public void setAccountNameTr(String aAccountNameTr)
	{
		AccountNameTr = aAccountNameTr;
	}

	/**
	* 使用另外一个 PD_LMRiskAccFundSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskAccFundSchema PD_LMRiskAccFundSchema
	**/
	public void setSchema(PD_LMRiskAccFundSchema aPD_LMRiskAccFundSchema)
	{
		this.RiskCode = aPD_LMRiskAccFundSchema.getRiskCode();
		this.PayPlanCode = aPD_LMRiskAccFundSchema.getPayPlanCode();
		this.AccountCode = aPD_LMRiskAccFundSchema.getAccountCode();
		this.AccountName = aPD_LMRiskAccFundSchema.getAccountName();
		this.AccountType = aPD_LMRiskAccFundSchema.getAccountType();
		this.Operator = aPD_LMRiskAccFundSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskAccFundSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskAccFundSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskAccFundSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskAccFundSchema.getModifyTime();
		this.AccountNameEn = aPD_LMRiskAccFundSchema.getAccountNameEn();
		this.AccountNameTr = aPD_LMRiskAccFundSchema.getAccountNameTr();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("AccountCode") == null )
				this.AccountCode = null;
			else
				this.AccountCode = rs.getString("AccountCode").trim();

			if( rs.getString("AccountName") == null )
				this.AccountName = null;
			else
				this.AccountName = rs.getString("AccountName").trim();

			if( rs.getString("AccountType") == null )
				this.AccountType = null;
			else
				this.AccountType = rs.getString("AccountType").trim();

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

			if( rs.getString("AccountNameEn") == null )
				this.AccountNameEn = null;
			else
				this.AccountNameEn = rs.getString("AccountNameEn").trim();

			if( rs.getString("AccountNameTr") == null )
				this.AccountNameTr = null;
			else
				this.AccountNameTr = rs.getString("AccountNameTr").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMRiskAccFund表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAccFundSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskAccFundSchema getSchema()
	{
		PD_LMRiskAccFundSchema aPD_LMRiskAccFundSchema = new PD_LMRiskAccFundSchema();
		aPD_LMRiskAccFundSchema.setSchema(this);
		return aPD_LMRiskAccFundSchema;
	}

	public PD_LMRiskAccFundDB getDB()
	{
		PD_LMRiskAccFundDB aDBOper = new PD_LMRiskAccFundDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskAccFund描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountNameEn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccountNameTr));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskAccFund>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccountCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccountName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccountType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccountNameEn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AccountNameTr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAccFundSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("AccountCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountCode));
		}
		if (FCode.equalsIgnoreCase("AccountName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountName));
		}
		if (FCode.equalsIgnoreCase("AccountType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountType));
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
		if (FCode.equalsIgnoreCase("AccountNameEn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountNameEn));
		}
		if (FCode.equalsIgnoreCase("AccountNameTr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountNameTr));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccountCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccountName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccountType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccountNameEn);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AccountNameTr);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("AccountName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountName = FValue.trim();
			}
			else
				AccountName = null;
		}
		if (FCode.equalsIgnoreCase("AccountType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountType = FValue.trim();
			}
			else
				AccountType = null;
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
		if (FCode.equalsIgnoreCase("AccountNameEn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountNameEn = FValue.trim();
			}
			else
				AccountNameEn = null;
		}
		if (FCode.equalsIgnoreCase("AccountNameTr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountNameTr = FValue.trim();
			}
			else
				AccountNameTr = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskAccFundSchema other = (PD_LMRiskAccFundSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& AccountCode.equals(other.getAccountCode())
			&& AccountName.equals(other.getAccountName())
			&& AccountType.equals(other.getAccountType())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AccountNameEn.equals(other.getAccountNameEn())
			&& AccountNameTr.equals(other.getAccountNameTr());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("AccountCode") ) {
			return 2;
		}
		if( strFieldName.equals("AccountName") ) {
			return 3;
		}
		if( strFieldName.equals("AccountType") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 9;
		}
		if( strFieldName.equals("AccountNameEn") ) {
			return 10;
		}
		if( strFieldName.equals("AccountNameTr") ) {
			return 11;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "PayPlanCode";
				break;
			case 2:
				strFieldName = "AccountCode";
				break;
			case 3:
				strFieldName = "AccountName";
				break;
			case 4:
				strFieldName = "AccountType";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
				break;
			case 8:
				strFieldName = "ModifyDate";
				break;
			case 9:
				strFieldName = "ModifyTime";
				break;
			case 10:
				strFieldName = "AccountNameEn";
				break;
			case 11:
				strFieldName = "AccountNameTr";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountType") ) {
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
		if( strFieldName.equals("AccountNameEn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountNameTr") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
