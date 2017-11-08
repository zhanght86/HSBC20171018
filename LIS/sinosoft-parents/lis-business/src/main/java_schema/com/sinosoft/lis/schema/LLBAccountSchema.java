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
import com.sinosoft.lis.db.LLBAccountDB;

/*
 * <p>ClassName: LLBAccountSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLBAccountSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLBAccountSchema.class);
	// @Field
	/** Serialno */
	private String SerialNo;
	/** Grpcontno */
	private String GrpContNo;
	/** Insuredno */
	private String InsuredNo;
	/** Contno */
	private String ContNo;
	/** Name */
	private String Name;
	/** Idno */
	private String IdNo;
	/** Bnfrate */
	private double BnfRate;
	/** Bankcode */
	private String BankCode;
	/** Branchcode */
	private String BranchCode;
	/** Branchname */
	private String BranchName;
	/** Account */
	private String Account;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Modifydate */
	private Date ModifyDate;
	/** Modefytime */
	private String ModefyTime;
	/** Operator */
	private String Operator;
	/** Insuredname */
	private String InsuredName;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLBAccountSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "Name";

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
		LLBAccountSchema cloned = (LLBAccountSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>10)
			throw new IllegalArgumentException("SerialnoSerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值10");
		SerialNo = aSerialNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("GrpcontnoGrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>20)
			throw new IllegalArgumentException("InsurednoInsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值20");
		InsuredNo = aInsuredNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("ContnoContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>20)
			throw new IllegalArgumentException("NameName值"+aName+"的长度"+aName.length()+"大于最大值20");
		Name = aName;
	}
	public String getIdNo()
	{
		return IdNo;
	}
	public void setIdNo(String aIdNo)
	{
		if(aIdNo!=null && aIdNo.length()>20)
			throw new IllegalArgumentException("IdnoIdNo值"+aIdNo+"的长度"+aIdNo.length()+"大于最大值20");
		IdNo = aIdNo;
	}
	public double getBnfRate()
	{
		return BnfRate;
	}
	public void setBnfRate(double aBnfRate)
	{
		BnfRate = aBnfRate;
	}
	public void setBnfRate(String aBnfRate)
	{
		if (aBnfRate != null && !aBnfRate.equals(""))
		{
			Double tDouble = new Double(aBnfRate);
			double d = tDouble.doubleValue();
			BnfRate = d;
		}
	}

	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>20)
			throw new IllegalArgumentException("BankcodeBankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值20");
		BankCode = aBankCode;
	}
	public String getBranchCode()
	{
		return BranchCode;
	}
	public void setBranchCode(String aBranchCode)
	{
		if(aBranchCode!=null && aBranchCode.length()>20)
			throw new IllegalArgumentException("BranchcodeBranchCode值"+aBranchCode+"的长度"+aBranchCode.length()+"大于最大值20");
		BranchCode = aBranchCode;
	}
	public String getBranchName()
	{
		return BranchName;
	}
	public void setBranchName(String aBranchName)
	{
		if(aBranchName!=null && aBranchName.length()>40)
			throw new IllegalArgumentException("BranchnameBranchName值"+aBranchName+"的长度"+aBranchName.length()+"大于最大值40");
		BranchName = aBranchName;
	}
	public String getAccount()
	{
		return Account;
	}
	public void setAccount(String aAccount)
	{
		if(aAccount!=null && aAccount.length()>20)
			throw new IllegalArgumentException("AccountAccount值"+aAccount+"的长度"+aAccount.length()+"大于最大值20");
		Account = aAccount;
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
		if(aMakeTime!=null && aMakeTime.length()>10)
			throw new IllegalArgumentException("MaketimeMakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值10");
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

	public String getModefyTime()
	{
		return ModefyTime;
	}
	public void setModefyTime(String aModefyTime)
	{
		if(aModefyTime!=null && aModefyTime.length()>10)
			throw new IllegalArgumentException("ModefytimeModefyTime值"+aModefyTime+"的长度"+aModefyTime.length()+"大于最大值10");
		ModefyTime = aModefyTime;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("OperatorOperator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>20)
			throw new IllegalArgumentException("InsurednameInsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值20");
		InsuredName = aInsuredName;
	}

	/**
	* 使用另外一个 LLBAccountSchema 对象给 Schema 赋值
	* @param: aLLBAccountSchema LLBAccountSchema
	**/
	public void setSchema(LLBAccountSchema aLLBAccountSchema)
	{
		this.SerialNo = aLLBAccountSchema.getSerialNo();
		this.GrpContNo = aLLBAccountSchema.getGrpContNo();
		this.InsuredNo = aLLBAccountSchema.getInsuredNo();
		this.ContNo = aLLBAccountSchema.getContNo();
		this.Name = aLLBAccountSchema.getName();
		this.IdNo = aLLBAccountSchema.getIdNo();
		this.BnfRate = aLLBAccountSchema.getBnfRate();
		this.BankCode = aLLBAccountSchema.getBankCode();
		this.BranchCode = aLLBAccountSchema.getBranchCode();
		this.BranchName = aLLBAccountSchema.getBranchName();
		this.Account = aLLBAccountSchema.getAccount();
		this.MakeDate = fDate.getDate( aLLBAccountSchema.getMakeDate());
		this.MakeTime = aLLBAccountSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLBAccountSchema.getModifyDate());
		this.ModefyTime = aLLBAccountSchema.getModefyTime();
		this.Operator = aLLBAccountSchema.getOperator();
		this.InsuredName = aLLBAccountSchema.getInsuredName();
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

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("IdNo") == null )
				this.IdNo = null;
			else
				this.IdNo = rs.getString("IdNo").trim();

			this.BnfRate = rs.getDouble("BnfRate");
			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BranchCode") == null )
				this.BranchCode = null;
			else
				this.BranchCode = rs.getString("BranchCode").trim();

			if( rs.getString("BranchName") == null )
				this.BranchName = null;
			else
				this.BranchName = rs.getString("BranchName").trim();

			if( rs.getString("Account") == null )
				this.Account = null;
			else
				this.Account = rs.getString("Account").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModefyTime") == null )
				this.ModefyTime = null;
			else
				this.ModefyTime = rs.getString("ModefyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLBAccount表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBAccountSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLBAccountSchema getSchema()
	{
		LLBAccountSchema aLLBAccountSchema = new LLBAccountSchema();
		aLLBAccountSchema.setSchema(this);
		return aLLBAccountSchema;
	}

	public LLBAccountDB getDB()
	{
		LLBAccountDB aDBOper = new LLBAccountDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBAccount描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IdNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BnfRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Account)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModefyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBAccount>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IdNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BnfRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BranchName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Account = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModefyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBAccountSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("IdNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IdNo));
		}
		if (FCode.equalsIgnoreCase("BnfRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfRate));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchCode));
		}
		if (FCode.equalsIgnoreCase("BranchName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchName));
		}
		if (FCode.equalsIgnoreCase("Account"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Account));
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
		if (FCode.equalsIgnoreCase("ModefyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModefyTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IdNo);
				break;
			case 6:
				strFieldValue = String.valueOf(BnfRate);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BranchCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BranchName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Account);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModefyTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("IdNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IdNo = FValue.trim();
			}
			else
				IdNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BnfRate = d;
			}
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
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchCode = FValue.trim();
			}
			else
				BranchCode = null;
		}
		if (FCode.equalsIgnoreCase("BranchName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchName = FValue.trim();
			}
			else
				BranchName = null;
		}
		if (FCode.equalsIgnoreCase("Account"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Account = FValue.trim();
			}
			else
				Account = null;
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
		if (FCode.equalsIgnoreCase("ModefyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModefyTime = FValue.trim();
			}
			else
				ModefyTime = null;
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
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLBAccountSchema other = (LLBAccountSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& ContNo.equals(other.getContNo())
			&& Name.equals(other.getName())
			&& IdNo.equals(other.getIdNo())
			&& BnfRate == other.getBnfRate()
			&& BankCode.equals(other.getBankCode())
			&& BranchCode.equals(other.getBranchCode())
			&& BranchName.equals(other.getBranchName())
			&& Account.equals(other.getAccount())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModefyTime.equals(other.getModefyTime())
			&& Operator.equals(other.getOperator())
			&& InsuredName.equals(other.getInsuredName());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("Name") ) {
			return 4;
		}
		if( strFieldName.equals("IdNo") ) {
			return 5;
		}
		if( strFieldName.equals("BnfRate") ) {
			return 6;
		}
		if( strFieldName.equals("BankCode") ) {
			return 7;
		}
		if( strFieldName.equals("BranchCode") ) {
			return 8;
		}
		if( strFieldName.equals("BranchName") ) {
			return 9;
		}
		if( strFieldName.equals("Account") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModefyTime") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 16;
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
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "InsuredNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "Name";
				break;
			case 5:
				strFieldName = "IdNo";
				break;
			case 6:
				strFieldName = "BnfRate";
				break;
			case 7:
				strFieldName = "BankCode";
				break;
			case 8:
				strFieldName = "BranchCode";
				break;
			case 9:
				strFieldName = "BranchName";
				break;
			case 10:
				strFieldName = "Account";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModefyTime";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "InsuredName";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IdNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Account") ) {
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
		if( strFieldName.equals("ModefyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
