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
import com.sinosoft.lis.db.LLBnfDB;

/*
 * <p>ClassName: LLBnfSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBnfSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLBnfSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 批次号 */
	private String BatNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 受益性质 */
	private String BnfKind;
	/** 保单号码 */
	private String PolNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 受益人序号 */
	private String BnfNo;
	/** 受益人类别 */
	private String BnfType;
	/** 受益人级别 */
	private String BnfGrade;
	/** 受益人与被保人关系 */
	private String RelationToInsured;
	/** 受益人号码 */
	private String CustomerNo;
	/** 受益人姓名 */
	private String Name;
	/** 受益人性别 */
	private String Sex;
	/** 受益人出生日期 */
	private Date Birthday;
	/** 受益人证件类型 */
	private String IDType;
	/** 受益人证件号码 */
	private String IDNo;
	/** 领款人与被保人关系 */
	private String RelationToPayee;
	/** 领款人号码 */
	private String PayeeNo;
	/** 领款人姓名 */
	private String PayeeName;
	/** 领款人性别 */
	private String PayeeSex;
	/** 领款人出生日期 */
	private Date PayeeBirthday;
	/** 领款人证件类型 */
	private String PayeeIDType;
	/** 承保时保单号 */
	private String NBPolNo;
	/** 领款人证件号码 */
	private String PayeeIDNo;
	/** 受益比例 */
	private double BnfLot;
	/** 受益金额 */
	private double GetMoney;
	/** 保险金领取方式 */
	private String CaseGetMode;
	/** 保险金支付方式 */
	private String CasePayMode;
	/** 保险金支付标志 */
	private String CasePayFlag;
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
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 分配项目 */
	private String FeeOperationType;
	/** 币别 */
	private String Currency;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 52;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLBnfSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "BatNo";
		pk[3] = "BnfKind";
		pk[4] = "PolNo";
		pk[5] = "InsuredNo";
		pk[6] = "BnfNo";
		pk[7] = "FeeOperationType";
		pk[8] = "Currency";

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
		LLBnfSchema cloned = (LLBnfSchema)super.clone();
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
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	/**
	* 对于非预付业务，该字段值为0，<p>
	* 对于预付业务，为预付的批次号
	*/
	public String getBatNo()
	{
		return BatNo;
	}
	public void setBatNo(String aBatNo)
	{
		if(aBatNo!=null && aBatNo.length()>20)
			throw new IllegalArgumentException("批次号BatNo值"+aBatNo+"的长度"+aBatNo.length()+"大于最大值20");
		BatNo = aBatNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	/**
	* A赔案金额受益<p>
	* B预付金额受益
	*/
	public String getBnfKind()
	{
		return BnfKind;
	}
	public void setBnfKind(String aBnfKind)
	{
		if(aBnfKind!=null && aBnfKind.length()>10)
			throw new IllegalArgumentException("受益性质BnfKind值"+aBnfKind+"的长度"+aBnfKind.length()+"大于最大值10");
		BnfKind = aBnfKind;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单号码PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	/**
	* 做为出险人的号码
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号码InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 对受益人进行唯一编码。
	*/
	public String getBnfNo()
	{
		return BnfNo;
	}
	public void setBnfNo(String aBnfNo)
	{
		if(aBnfNo!=null && aBnfNo.length()>24)
			throw new IllegalArgumentException("受益人序号BnfNo值"+aBnfNo+"的长度"+aBnfNo.length()+"大于最大值24");
		BnfNo = aBnfNo;
	}
	/**
	* 0 -- 生存受益人<p>
	* 1 -- 死亡受益人<p>
	* 2 -- 红利受益人
	*/
	public String getBnfType()
	{
		return BnfType;
	}
	public void setBnfType(String aBnfType)
	{
		if(aBnfType!=null && aBnfType.length()>6)
			throw new IllegalArgumentException("受益人类别BnfType值"+aBnfType+"的长度"+aBnfType.length()+"大于最大值6");
		BnfType = aBnfType;
	}
	/**
	* 指对受益人进行分组。<p>
	* 如第一受益人，第二受益人。
	*/
	public String getBnfGrade()
	{
		return BnfGrade;
	}
	public void setBnfGrade(String aBnfGrade)
	{
		if(aBnfGrade!=null && aBnfGrade.length()>6)
			throw new IllegalArgumentException("受益人级别BnfGrade值"+aBnfGrade+"的长度"+aBnfGrade.length()+"大于最大值6");
		BnfGrade = aBnfGrade;
	}
	/**
	* 00  	本人<p>
	* 01  	父子<p>
	* 02  	父女<p>
	* 03  	母子<p>
	* 04  	母女<p>
	* 05  	祖孙<p>
	* 07  	夫妻<p>
	* 08  	兄弟<p>
	* 09  	兄妹<p>
	* 10  	姐弟<p>
	* 11  	姐妹<p>
	* 12  	叔侄<p>
	* 13  	姑???<p>
	* 14  	外甥<p>
	* 15  	媳<p>
	* 16  	婿<p>
	* 17  	姐夫<p>
	* 18  	朋友<p>
	* 19  	同事<p>
	* 20  	师生<p>
	* 21  	雇佣<p>
	* 22  	其他<p>
	* 23  	法定
	*/
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		if(aRelationToInsured!=null && aRelationToInsured.length()>6)
			throw new IllegalArgumentException("受益人与被保人关系RelationToInsured值"+aRelationToInsured+"的长度"+aRelationToInsured.length()+"大于最大值6");
		RelationToInsured = aRelationToInsured;
	}
	/**
	* 作为受益人的号码
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("受益人号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>20)
			throw new IllegalArgumentException("受益人姓名Name值"+aName+"的长度"+aName.length()+"大于最大值20");
		Name = aName;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		if(aSex!=null && aSex.length()>6)
			throw new IllegalArgumentException("受益人性别Sex值"+aSex+"的长度"+aSex.length()+"大于最大值6");
		Sex = aSex;
	}
	public String getBirthday()
	{
		if( Birthday != null )
			return fDate.getString(Birthday);
		else
			return null;
	}
	public void setBirthday(Date aBirthday)
	{
		Birthday = aBirthday;
	}
	public void setBirthday(String aBirthday)
	{
		if (aBirthday != null && !aBirthday.equals("") )
		{
			Birthday = fDate.getDate( aBirthday );
		}
		else
			Birthday = null;
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
		if(aIDType!=null && aIDType.length()>6)
			throw new IllegalArgumentException("受益人证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值6");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>20)
			throw new IllegalArgumentException("受益人证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值20");
		IDNo = aIDNo;
	}
	/**
	* 00   本人<p>
	* 01   父子<p>
	* 02   父女<p>
	* 03   母子<p>
	* 04   母女<p>
	* 05   祖孙<p>
	* 07   夫妻<p>
	* 08   兄弟<p>
	* 09   兄妹<p>
	* 10   姐弟<p>
	* 11   姐妹<p>
	* 12   叔侄<p>
	* 13   姑侄<p>
	* 14   外甥<p>
	* 15   媳<p>
	* 16   婿<p>
	* 17   姐夫<p>
	* 18   朋友<p>
	* 19   同事<p>
	* 20   师生<p>
	* 21   雇佣<p>
	* 22   其他<p>
	* 23   法定
	*/
	public String getRelationToPayee()
	{
		return RelationToPayee;
	}
	public void setRelationToPayee(String aRelationToPayee)
	{
		if(aRelationToPayee!=null && aRelationToPayee.length()>6)
			throw new IllegalArgumentException("领款人与被保人关系RelationToPayee值"+aRelationToPayee+"的长度"+aRelationToPayee.length()+"大于最大值6");
		RelationToPayee = aRelationToPayee;
	}
	public String getPayeeNo()
	{
		return PayeeNo;
	}
	public void setPayeeNo(String aPayeeNo)
	{
		if(aPayeeNo!=null && aPayeeNo.length()>24)
			throw new IllegalArgumentException("领款人号码PayeeNo值"+aPayeeNo+"的长度"+aPayeeNo.length()+"大于最大值24");
		PayeeNo = aPayeeNo;
	}
	public String getPayeeName()
	{
		return PayeeName;
	}
	public void setPayeeName(String aPayeeName)
	{
		if(aPayeeName!=null && aPayeeName.length()>200)
			throw new IllegalArgumentException("领款人姓名PayeeName值"+aPayeeName+"的长度"+aPayeeName.length()+"大于最大值200");
		PayeeName = aPayeeName;
	}
	public String getPayeeSex()
	{
		return PayeeSex;
	}
	public void setPayeeSex(String aPayeeSex)
	{
		if(aPayeeSex!=null && aPayeeSex.length()>6)
			throw new IllegalArgumentException("领款人性别PayeeSex值"+aPayeeSex+"的长度"+aPayeeSex.length()+"大于最大值6");
		PayeeSex = aPayeeSex;
	}
	public String getPayeeBirthday()
	{
		if( PayeeBirthday != null )
			return fDate.getString(PayeeBirthday);
		else
			return null;
	}
	public void setPayeeBirthday(Date aPayeeBirthday)
	{
		PayeeBirthday = aPayeeBirthday;
	}
	public void setPayeeBirthday(String aPayeeBirthday)
	{
		if (aPayeeBirthday != null && !aPayeeBirthday.equals("") )
		{
			PayeeBirthday = fDate.getDate( aPayeeBirthday );
		}
		else
			PayeeBirthday = null;
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
	public String getPayeeIDType()
	{
		return PayeeIDType;
	}
	public void setPayeeIDType(String aPayeeIDType)
	{
		if(aPayeeIDType!=null && aPayeeIDType.length()>6)
			throw new IllegalArgumentException("领款人证件类型PayeeIDType值"+aPayeeIDType+"的长度"+aPayeeIDType.length()+"大于最大值6");
		PayeeIDType = aPayeeIDType;
	}
	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		if(aNBPolNo!=null && aNBPolNo.length()>20)
			throw new IllegalArgumentException("承保时保单号NBPolNo值"+aNBPolNo+"的长度"+aNBPolNo.length()+"大于最大值20");
		NBPolNo = aNBPolNo;
	}
	public String getPayeeIDNo()
	{
		return PayeeIDNo;
	}
	public void setPayeeIDNo(String aPayeeIDNo)
	{
		if(aPayeeIDNo!=null && aPayeeIDNo.length()>20)
			throw new IllegalArgumentException("领款人证件号码PayeeIDNo值"+aPayeeIDNo+"的长度"+aPayeeIDNo.length()+"大于最大值20");
		PayeeIDNo = aPayeeIDNo;
	}
	public double getBnfLot()
	{
		return BnfLot;
	}
	public void setBnfLot(double aBnfLot)
	{
		BnfLot = aBnfLot;
	}
	public void setBnfLot(String aBnfLot)
	{
		if (aBnfLot != null && !aBnfLot.equals(""))
		{
			Double tDouble = new Double(aBnfLot);
			double d = tDouble.doubleValue();
			BnfLot = d;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	/**
	* 1 一次统一给付<p>
	* 2 按年金方式领取<p>
	* 3 分期支付
	*/
	public String getCaseGetMode()
	{
		return CaseGetMode;
	}
	public void setCaseGetMode(String aCaseGetMode)
	{
		if(aCaseGetMode!=null && aCaseGetMode.length()>6)
			throw new IllegalArgumentException("保险金领取方式CaseGetMode值"+aCaseGetMode+"的长度"+aCaseGetMode.length()+"大于最大值6");
		CaseGetMode = aCaseGetMode;
	}
	/**
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	银行托收<p>
	* 7	其他<p>
	* 8 new 统一转账(团单，家庭单按一个申请统一转账)
	*/
	public String getCasePayMode()
	{
		return CasePayMode;
	}
	public void setCasePayMode(String aCasePayMode)
	{
		if(aCasePayMode!=null && aCasePayMode.length()>6)
			throw new IllegalArgumentException("保险金支付方式CasePayMode值"+aCasePayMode+"的长度"+aCasePayMode.length()+"大于最大值6");
		CasePayMode = aCasePayMode;
	}
	/**
	* 0未支付<p>
	* 1已支付
	*/
	public String getCasePayFlag()
	{
		return CasePayFlag;
	}
	public void setCasePayFlag(String aCasePayFlag)
	{
		if(aCasePayFlag!=null && aCasePayFlag.length()>6)
			throw new IllegalArgumentException("保险金支付标志CasePayFlag值"+aCasePayFlag+"的长度"+aCasePayFlag.length()+"大于最大值6");
		CasePayFlag = aCasePayFlag;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>30)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值30");
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
		if(aAccName!=null && aAccName.length()>200)
			throw new IllegalArgumentException("银行帐户名AccName值"+aAccName+"的长度"+aAccName.length()+"大于最大值200");
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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
	/**
	* 将给应付表中的给付通知书号赋值给该字段<p>
	* 审批通过后,将实付表中的实付号赋值给该字段
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>2)
			throw new IllegalArgumentException("其它号码类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值2");
		OtherNoType = aOtherNoType;
	}
	/**
	* A       赔付金额<p>
	* B       预付<p>
	* C01  	保单结算退还保费<p>
	* C02  	保单结算补交保费<p>
	* C03  	保单结算保单质押贷款<p>
	* C04  	保单结算生存金、养老金<p>
	* C05  	保单结算红利<p>
	* C06  	保单利差返还<p>
	* C07  	保单自动垫缴<p>
	* C08  	保单保费豁免<p>
	* C09  	退未满期保费<p>
	* C30  	二核加费补费
	*/
	public String getFeeOperationType()
	{
		return FeeOperationType;
	}
	public void setFeeOperationType(String aFeeOperationType)
	{
		if(aFeeOperationType!=null && aFeeOperationType.length()>10)
			throw new IllegalArgumentException("分配项目FeeOperationType值"+aFeeOperationType+"的长度"+aFeeOperationType.length()+"大于最大值10");
		FeeOperationType = aFeeOperationType;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LLBnfSchema 对象给 Schema 赋值
	* @param: aLLBnfSchema LLBnfSchema
	**/
	public void setSchema(LLBnfSchema aLLBnfSchema)
	{
		this.ClmNo = aLLBnfSchema.getClmNo();
		this.CaseNo = aLLBnfSchema.getCaseNo();
		this.BatNo = aLLBnfSchema.getBatNo();
		this.GrpContNo = aLLBnfSchema.getGrpContNo();
		this.GrpPolNo = aLLBnfSchema.getGrpPolNo();
		this.ContNo = aLLBnfSchema.getContNo();
		this.BnfKind = aLLBnfSchema.getBnfKind();
		this.PolNo = aLLBnfSchema.getPolNo();
		this.InsuredNo = aLLBnfSchema.getInsuredNo();
		this.BnfNo = aLLBnfSchema.getBnfNo();
		this.BnfType = aLLBnfSchema.getBnfType();
		this.BnfGrade = aLLBnfSchema.getBnfGrade();
		this.RelationToInsured = aLLBnfSchema.getRelationToInsured();
		this.CustomerNo = aLLBnfSchema.getCustomerNo();
		this.Name = aLLBnfSchema.getName();
		this.Sex = aLLBnfSchema.getSex();
		this.Birthday = fDate.getDate( aLLBnfSchema.getBirthday());
		this.IDType = aLLBnfSchema.getIDType();
		this.IDNo = aLLBnfSchema.getIDNo();
		this.RelationToPayee = aLLBnfSchema.getRelationToPayee();
		this.PayeeNo = aLLBnfSchema.getPayeeNo();
		this.PayeeName = aLLBnfSchema.getPayeeName();
		this.PayeeSex = aLLBnfSchema.getPayeeSex();
		this.PayeeBirthday = fDate.getDate( aLLBnfSchema.getPayeeBirthday());
		this.PayeeIDType = aLLBnfSchema.getPayeeIDType();
		this.NBPolNo = aLLBnfSchema.getNBPolNo();
		this.PayeeIDNo = aLLBnfSchema.getPayeeIDNo();
		this.BnfLot = aLLBnfSchema.getBnfLot();
		this.GetMoney = aLLBnfSchema.getGetMoney();
		this.CaseGetMode = aLLBnfSchema.getCaseGetMode();
		this.CasePayMode = aLLBnfSchema.getCasePayMode();
		this.CasePayFlag = aLLBnfSchema.getCasePayFlag();
		this.BankCode = aLLBnfSchema.getBankCode();
		this.BankAccNo = aLLBnfSchema.getBankAccNo();
		this.AccName = aLLBnfSchema.getAccName();
		this.ModiReasonCode = aLLBnfSchema.getModiReasonCode();
		this.ModiReasonDesc = aLLBnfSchema.getModiReasonDesc();
		this.OBankCode = aLLBnfSchema.getOBankCode();
		this.OBankAccNo = aLLBnfSchema.getOBankAccNo();
		this.OAccName = aLLBnfSchema.getOAccName();
		this.Operator = aLLBnfSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLBnfSchema.getMakeDate());
		this.MakeTime = aLLBnfSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLBnfSchema.getModifyDate());
		this.ModifyTime = aLLBnfSchema.getModifyTime();
		this.OtherNo = aLLBnfSchema.getOtherNo();
		this.OtherNoType = aLLBnfSchema.getOtherNoType();
		this.FeeOperationType = aLLBnfSchema.getFeeOperationType();
		this.Currency = aLLBnfSchema.getCurrency();
		this.ManageCom = aLLBnfSchema.getManageCom();
		this.ComCode = aLLBnfSchema.getComCode();
		this.ModifyOperator = aLLBnfSchema.getModifyOperator();
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

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("BatNo") == null )
				this.BatNo = null;
			else
				this.BatNo = rs.getString("BatNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("BnfKind") == null )
				this.BnfKind = null;
			else
				this.BnfKind = rs.getString("BnfKind").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("BnfNo") == null )
				this.BnfNo = null;
			else
				this.BnfNo = rs.getString("BnfNo").trim();

			if( rs.getString("BnfType") == null )
				this.BnfType = null;
			else
				this.BnfType = rs.getString("BnfType").trim();

			if( rs.getString("BnfGrade") == null )
				this.BnfGrade = null;
			else
				this.BnfGrade = rs.getString("BnfGrade").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Birthday = rs.getDate("Birthday");
			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("RelationToPayee") == null )
				this.RelationToPayee = null;
			else
				this.RelationToPayee = rs.getString("RelationToPayee").trim();

			if( rs.getString("PayeeNo") == null )
				this.PayeeNo = null;
			else
				this.PayeeNo = rs.getString("PayeeNo").trim();

			if( rs.getString("PayeeName") == null )
				this.PayeeName = null;
			else
				this.PayeeName = rs.getString("PayeeName").trim();

			if( rs.getString("PayeeSex") == null )
				this.PayeeSex = null;
			else
				this.PayeeSex = rs.getString("PayeeSex").trim();

			this.PayeeBirthday = rs.getDate("PayeeBirthday");
			if( rs.getString("PayeeIDType") == null )
				this.PayeeIDType = null;
			else
				this.PayeeIDType = rs.getString("PayeeIDType").trim();

			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("PayeeIDNo") == null )
				this.PayeeIDNo = null;
			else
				this.PayeeIDNo = rs.getString("PayeeIDNo").trim();

			this.BnfLot = rs.getDouble("BnfLot");
			this.GetMoney = rs.getDouble("GetMoney");
			if( rs.getString("CaseGetMode") == null )
				this.CaseGetMode = null;
			else
				this.CaseGetMode = rs.getString("CaseGetMode").trim();

			if( rs.getString("CasePayMode") == null )
				this.CasePayMode = null;
			else
				this.CasePayMode = rs.getString("CasePayMode").trim();

			if( rs.getString("CasePayFlag") == null )
				this.CasePayFlag = null;
			else
				this.CasePayFlag = rs.getString("CasePayFlag").trim();

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

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLBnf表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBnfSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLBnfSchema getSchema()
	{
		LLBnfSchema aLLBnfSchema = new LLBnfSchema();
		aLLBnfSchema.setSchema(this);
		return aLLBnfSchema;
	}

	public LLBnfDB getDB()
	{
		LLBnfDB aDBOper = new LLBnfDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBnf描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Birthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToPayee)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayeeBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayeeIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BnfLot));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModiReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModiReasonDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBnf>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BnfKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BnfNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BnfType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BnfGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Birthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RelationToPayee = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PayeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PayeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			PayeeSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PayeeBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			PayeeIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			PayeeIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BnfLot = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,29,SysConst.PACKAGESPILTER))).doubleValue();
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CasePayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			CasePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			ModiReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			ModiReasonDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			OBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			OBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			OAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBnfSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("BnfKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfKind));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("BnfNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfNo));
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfType));
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfGrade));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("RelationToPayee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToPayee));
		}
		if (FCode.equalsIgnoreCase("PayeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeNo));
		}
		if (FCode.equalsIgnoreCase("PayeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeName));
		}
		if (FCode.equalsIgnoreCase("PayeeSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeSex));
		}
		if (FCode.equalsIgnoreCase("PayeeBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayeeBirthday()));
		}
		if (FCode.equalsIgnoreCase("PayeeIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeIDType));
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("PayeeIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayeeIDNo));
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfLot));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseGetMode));
		}
		if (FCode.equalsIgnoreCase("CasePayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayMode));
		}
		if (FCode.equalsIgnoreCase("CasePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayFlag));
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BnfKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BnfNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BnfType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BnfGrade);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBirthday()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RelationToPayee);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PayeeNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PayeeName);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(PayeeSex);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayeeBirthday()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(PayeeIDType);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(PayeeIDNo);
				break;
			case 27:
				strFieldValue = String.valueOf(BnfLot);
				break;
			case 28:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CasePayMode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(CasePayFlag);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ModiReasonCode);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(ModiReasonDesc);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(OBankCode);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(OBankAccNo);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(OAccName);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatNo = FValue.trim();
			}
			else
				BatNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("BnfKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfKind = FValue.trim();
			}
			else
				BnfKind = null;
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
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfNo = FValue.trim();
			}
			else
				BnfNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfType = FValue.trim();
			}
			else
				BnfType = null;
		}
		if (FCode.equalsIgnoreCase("BnfGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfGrade = FValue.trim();
			}
			else
				BnfGrade = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
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
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Birthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Birthday = fDate.getDate( FValue );
			}
			else
				Birthday = null;
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
		if (FCode.equalsIgnoreCase("RelationToPayee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToPayee = FValue.trim();
			}
			else
				RelationToPayee = null;
		}
		if (FCode.equalsIgnoreCase("PayeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeNo = FValue.trim();
			}
			else
				PayeeNo = null;
		}
		if (FCode.equalsIgnoreCase("PayeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeName = FValue.trim();
			}
			else
				PayeeName = null;
		}
		if (FCode.equalsIgnoreCase("PayeeSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeSex = FValue.trim();
			}
			else
				PayeeSex = null;
		}
		if (FCode.equalsIgnoreCase("PayeeBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayeeBirthday = fDate.getDate( FValue );
			}
			else
				PayeeBirthday = null;
		}
		if (FCode.equalsIgnoreCase("PayeeIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeIDType = FValue.trim();
			}
			else
				PayeeIDType = null;
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("PayeeIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayeeIDNo = FValue.trim();
			}
			else
				PayeeIDNo = null;
		}
		if (FCode.equalsIgnoreCase("BnfLot"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BnfLot = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseGetMode = FValue.trim();
			}
			else
				CaseGetMode = null;
		}
		if (FCode.equalsIgnoreCase("CasePayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePayMode = FValue.trim();
			}
			else
				CasePayMode = null;
		}
		if (FCode.equalsIgnoreCase("CasePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePayFlag = FValue.trim();
			}
			else
				CasePayFlag = null;
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeOperationType = FValue.trim();
			}
			else
				FeeOperationType = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLBnfSchema other = (LLBnfSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& BatNo.equals(other.getBatNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& BnfKind.equals(other.getBnfKind())
			&& PolNo.equals(other.getPolNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& BnfNo.equals(other.getBnfNo())
			&& BnfType.equals(other.getBnfType())
			&& BnfGrade.equals(other.getBnfGrade())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& CustomerNo.equals(other.getCustomerNo())
			&& Name.equals(other.getName())
			&& Sex.equals(other.getSex())
			&& fDate.getString(Birthday).equals(other.getBirthday())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& RelationToPayee.equals(other.getRelationToPayee())
			&& PayeeNo.equals(other.getPayeeNo())
			&& PayeeName.equals(other.getPayeeName())
			&& PayeeSex.equals(other.getPayeeSex())
			&& fDate.getString(PayeeBirthday).equals(other.getPayeeBirthday())
			&& PayeeIDType.equals(other.getPayeeIDType())
			&& NBPolNo.equals(other.getNBPolNo())
			&& PayeeIDNo.equals(other.getPayeeIDNo())
			&& BnfLot == other.getBnfLot()
			&& GetMoney == other.getGetMoney()
			&& CaseGetMode.equals(other.getCaseGetMode())
			&& CasePayMode.equals(other.getCasePayMode())
			&& CasePayFlag.equals(other.getCasePayFlag())
			&& BankCode.equals(other.getBankCode())
			&& BankAccNo.equals(other.getBankAccNo())
			&& AccName.equals(other.getAccName())
			&& ModiReasonCode.equals(other.getModiReasonCode())
			&& ModiReasonDesc.equals(other.getModiReasonDesc())
			&& OBankCode.equals(other.getOBankCode())
			&& OBankAccNo.equals(other.getOBankAccNo())
			&& OAccName.equals(other.getOAccName())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& FeeOperationType.equals(other.getFeeOperationType())
			&& Currency.equals(other.getCurrency())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("ContNo") ) {
			return 5;
		}
		if( strFieldName.equals("BnfKind") ) {
			return 6;
		}
		if( strFieldName.equals("PolNo") ) {
			return 7;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 8;
		}
		if( strFieldName.equals("BnfNo") ) {
			return 9;
		}
		if( strFieldName.equals("BnfType") ) {
			return 10;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return 11;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 12;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 13;
		}
		if( strFieldName.equals("Name") ) {
			return 14;
		}
		if( strFieldName.equals("Sex") ) {
			return 15;
		}
		if( strFieldName.equals("Birthday") ) {
			return 16;
		}
		if( strFieldName.equals("IDType") ) {
			return 17;
		}
		if( strFieldName.equals("IDNo") ) {
			return 18;
		}
		if( strFieldName.equals("RelationToPayee") ) {
			return 19;
		}
		if( strFieldName.equals("PayeeNo") ) {
			return 20;
		}
		if( strFieldName.equals("PayeeName") ) {
			return 21;
		}
		if( strFieldName.equals("PayeeSex") ) {
			return 22;
		}
		if( strFieldName.equals("PayeeBirthday") ) {
			return 23;
		}
		if( strFieldName.equals("PayeeIDType") ) {
			return 24;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 25;
		}
		if( strFieldName.equals("PayeeIDNo") ) {
			return 26;
		}
		if( strFieldName.equals("BnfLot") ) {
			return 27;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 28;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 29;
		}
		if( strFieldName.equals("CasePayMode") ) {
			return 30;
		}
		if( strFieldName.equals("CasePayFlag") ) {
			return 31;
		}
		if( strFieldName.equals("BankCode") ) {
			return 32;
		}
		if( strFieldName.equals("BankAccNo") ) {
			return 33;
		}
		if( strFieldName.equals("AccName") ) {
			return 34;
		}
		if( strFieldName.equals("ModiReasonCode") ) {
			return 35;
		}
		if( strFieldName.equals("ModiReasonDesc") ) {
			return 36;
		}
		if( strFieldName.equals("OBankCode") ) {
			return 37;
		}
		if( strFieldName.equals("OBankAccNo") ) {
			return 38;
		}
		if( strFieldName.equals("OAccName") ) {
			return 39;
		}
		if( strFieldName.equals("Operator") ) {
			return 40;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 41;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 42;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 44;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 45;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 46;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return 47;
		}
		if( strFieldName.equals("Currency") ) {
			return 48;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 49;
		}
		if( strFieldName.equals("ComCode") ) {
			return 50;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 51;
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
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "BatNo";
				break;
			case 3:
				strFieldName = "GrpContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "ContNo";
				break;
			case 6:
				strFieldName = "BnfKind";
				break;
			case 7:
				strFieldName = "PolNo";
				break;
			case 8:
				strFieldName = "InsuredNo";
				break;
			case 9:
				strFieldName = "BnfNo";
				break;
			case 10:
				strFieldName = "BnfType";
				break;
			case 11:
				strFieldName = "BnfGrade";
				break;
			case 12:
				strFieldName = "RelationToInsured";
				break;
			case 13:
				strFieldName = "CustomerNo";
				break;
			case 14:
				strFieldName = "Name";
				break;
			case 15:
				strFieldName = "Sex";
				break;
			case 16:
				strFieldName = "Birthday";
				break;
			case 17:
				strFieldName = "IDType";
				break;
			case 18:
				strFieldName = "IDNo";
				break;
			case 19:
				strFieldName = "RelationToPayee";
				break;
			case 20:
				strFieldName = "PayeeNo";
				break;
			case 21:
				strFieldName = "PayeeName";
				break;
			case 22:
				strFieldName = "PayeeSex";
				break;
			case 23:
				strFieldName = "PayeeBirthday";
				break;
			case 24:
				strFieldName = "PayeeIDType";
				break;
			case 25:
				strFieldName = "NBPolNo";
				break;
			case 26:
				strFieldName = "PayeeIDNo";
				break;
			case 27:
				strFieldName = "BnfLot";
				break;
			case 28:
				strFieldName = "GetMoney";
				break;
			case 29:
				strFieldName = "CaseGetMode";
				break;
			case 30:
				strFieldName = "CasePayMode";
				break;
			case 31:
				strFieldName = "CasePayFlag";
				break;
			case 32:
				strFieldName = "BankCode";
				break;
			case 33:
				strFieldName = "BankAccNo";
				break;
			case 34:
				strFieldName = "AccName";
				break;
			case 35:
				strFieldName = "ModiReasonCode";
				break;
			case 36:
				strFieldName = "ModiReasonDesc";
				break;
			case 37:
				strFieldName = "OBankCode";
				break;
			case 38:
				strFieldName = "OBankAccNo";
				break;
			case 39:
				strFieldName = "OAccName";
				break;
			case 40:
				strFieldName = "Operator";
				break;
			case 41:
				strFieldName = "MakeDate";
				break;
			case 42:
				strFieldName = "MakeTime";
				break;
			case 43:
				strFieldName = "ModifyDate";
				break;
			case 44:
				strFieldName = "ModifyTime";
				break;
			case 45:
				strFieldName = "OtherNo";
				break;
			case 46:
				strFieldName = "OtherNoType";
				break;
			case 47:
				strFieldName = "FeeOperationType";
				break;
			case 48:
				strFieldName = "Currency";
				break;
			case 49:
				strFieldName = "ManageCom";
				break;
			case 50:
				strFieldName = "ComCode";
				break;
			case 51:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Birthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToPayee") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayeeIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayeeIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfLot") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePayFlag") ) {
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
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 47:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
