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
import com.sinosoft.lis.db.LLCaseReceiptClassDB;

/*
 * <p>ClassName: LLCaseReceiptClassSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseReceiptClassSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseReceiptClassSchema.class);
	// @Field
	/** Clmno */
	private String ClmNo;
	/** Mngcom */
	private String MngCom;
	/** Grpcontno */
	private String GrpContNo;
	/** Insuredno */
	private String InsuredNo;
	/** Name */
	private String Name;
	/** Id */
	private int ID;
	/** Billno */
	private String BillNo;
	/** Receiptdate */
	private Date ReceiptDate;
	/** Feeitemtype */
	private String FeeItemType;
	/** Totalfee */
	private double TotalFee;
	/** Startdate */
	private Date StartDate;
	/** Enddate */
	private Date EndDate;
	/** Validflag */
	private String ValidFlag;
	/** Col1 */
	private String Col1;
	/** Rate1 */
	private double Rate1;
	/** Adjfee1 */
	private double AdjFee1;
	/** Reason1 */
	private String Reason1;
	/** Col2 */
	private String Col2;
	/** Rate2 */
	private double Rate2;
	/** Adjfee2 */
	private double AdjFee2;
	/** Reason2 */
	private String Reason2;
	/** Col3 */
	private String Col3;
	/** Rate3 */
	private double Rate3;
	/** Adjfee3 */
	private double AdjFee3;
	/** Reason3 */
	private String Reason3;
	/** Col4 */
	private String Col4;
	/** Rate4 */
	private double Rate4;
	/** Adjfee4 */
	private double AdjFee4;
	/** Reason4 */
	private String Reason4;
	/** Col5 */
	private String Col5;
	/** Rate5 */
	private double Rate5;
	/** Adjfee5 */
	private double AdjFee5;
	/** Reason5 */
	private String Reason5;
	/** Col6 */
	private String Col6;
	/** Rate6 */
	private double Rate6;
	/** Adjfee6 */
	private double AdjFee6;
	/** Reason6 */
	private String Reason6;
	/** Col7 */
	private String Col7;
	/** Rate7 */
	private double Rate7;
	/** Adjfee7 */
	private double AdjFee7;
	/** Reason7 */
	private String Reason7;
	/** Col8 */
	private String Col8;
	/** Rate8 */
	private double Rate8;
	/** Adjfee8 */
	private double AdjFee8;
	/** Reason8 */
	private String Reason8;
	/** Col9 */
	private String Col9;
	/** Rate9 */
	private double Rate9;
	/** Adjfee9 */
	private double AdjFee9;
	/** Reason9 */
	private String Reason9;
	/** Col10 */
	private String Col10;
	/** Rate10 */
	private double Rate10;
	/** Adjfee10 */
	private double AdjFee10;
	/** Reason10 */
	private String Reason10;
	/** Reason */
	private String Reason;
	/** Hospitalcode */
	private String HospitalCode;
	/** Uwflag */
	private String UWFlag;
	/** Uwdate */
	private Date UWDate;
	/** Uwoperator */
	private String UWOperator;
	/** Totaladjfee */
	private double TotalAdjFee;
	/** Feeitemcode */
	private String FeeItemCode;
	/** Feeitemname */
	private String FeeItemName;
	/** Modifydate */
	private Date ModifyDate;
	/** Modifytime */
	private String ModifyTime;
	/** Lastoperator */
	private String LastOperator;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Operator */
	private String Operator;

	public static final int FIELDNUM = 67;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseReceiptClassSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ID";

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
		LLCaseReceiptClassSchema cloned = (LLCaseReceiptClassSchema)super.clone();
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
			throw new IllegalArgumentException("ClmnoClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("MngcomMngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
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
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("InsurednoInsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>120)
			throw new IllegalArgumentException("NameName值"+aName+"的长度"+aName.length()+"大于最大值120");
		Name = aName;
	}
	public int getID()
	{
		return ID;
	}
	public void setID(int aID)
	{
		ID = aID;
	}
	public void setID(String aID)
	{
		if (aID != null && !aID.equals(""))
		{
			Integer tInteger = new Integer(aID);
			int i = tInteger.intValue();
			ID = i;
		}
	}

	public String getBillNo()
	{
		return BillNo;
	}
	public void setBillNo(String aBillNo)
	{
		if(aBillNo!=null && aBillNo.length()>20)
			throw new IllegalArgumentException("BillnoBillNo值"+aBillNo+"的长度"+aBillNo.length()+"大于最大值20");
		BillNo = aBillNo;
	}
	public String getReceiptDate()
	{
		if( ReceiptDate != null )
			return fDate.getString(ReceiptDate);
		else
			return null;
	}
	public void setReceiptDate(Date aReceiptDate)
	{
		ReceiptDate = aReceiptDate;
	}
	public void setReceiptDate(String aReceiptDate)
	{
		if (aReceiptDate != null && !aReceiptDate.equals("") )
		{
			ReceiptDate = fDate.getDate( aReceiptDate );
		}
		else
			ReceiptDate = null;
	}

	public String getFeeItemType()
	{
		return FeeItemType;
	}
	public void setFeeItemType(String aFeeItemType)
	{
		if(aFeeItemType!=null && aFeeItemType.length()>6)
			throw new IllegalArgumentException("FeeitemtypeFeeItemType值"+aFeeItemType+"的长度"+aFeeItemType.length()+"大于最大值6");
		FeeItemType = aFeeItemType;
	}
	public double getTotalFee()
	{
		return TotalFee;
	}
	public void setTotalFee(double aTotalFee)
	{
		TotalFee = aTotalFee;
	}
	public void setTotalFee(String aTotalFee)
	{
		if (aTotalFee != null && !aTotalFee.equals(""))
		{
			Double tDouble = new Double(aTotalFee);
			double d = tDouble.doubleValue();
			TotalFee = d;
		}
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getValidFlag()
	{
		return ValidFlag;
	}
	public void setValidFlag(String aValidFlag)
	{
		if(aValidFlag!=null && aValidFlag.length()>2)
			throw new IllegalArgumentException("ValidflagValidFlag值"+aValidFlag+"的长度"+aValidFlag.length()+"大于最大值2");
		ValidFlag = aValidFlag;
	}
	public String getCol1()
	{
		return Col1;
	}
	public void setCol1(String aCol1)
	{
		if(aCol1!=null && aCol1.length()>30)
			throw new IllegalArgumentException("Col1Col1值"+aCol1+"的长度"+aCol1.length()+"大于最大值30");
		Col1 = aCol1;
	}
	public double getRate1()
	{
		return Rate1;
	}
	public void setRate1(double aRate1)
	{
		Rate1 = aRate1;
	}
	public void setRate1(String aRate1)
	{
		if (aRate1 != null && !aRate1.equals(""))
		{
			Double tDouble = new Double(aRate1);
			double d = tDouble.doubleValue();
			Rate1 = d;
		}
	}

	public double getAdjFee1()
	{
		return AdjFee1;
	}
	public void setAdjFee1(double aAdjFee1)
	{
		AdjFee1 = aAdjFee1;
	}
	public void setAdjFee1(String aAdjFee1)
	{
		if (aAdjFee1 != null && !aAdjFee1.equals(""))
		{
			Double tDouble = new Double(aAdjFee1);
			double d = tDouble.doubleValue();
			AdjFee1 = d;
		}
	}

	public String getReason1()
	{
		return Reason1;
	}
	public void setReason1(String aReason1)
	{
		if(aReason1!=null && aReason1.length()>100)
			throw new IllegalArgumentException("Reason1Reason1值"+aReason1+"的长度"+aReason1.length()+"大于最大值100");
		Reason1 = aReason1;
	}
	public String getCol2()
	{
		return Col2;
	}
	public void setCol2(String aCol2)
	{
		if(aCol2!=null && aCol2.length()>30)
			throw new IllegalArgumentException("Col2Col2值"+aCol2+"的长度"+aCol2.length()+"大于最大值30");
		Col2 = aCol2;
	}
	public double getRate2()
	{
		return Rate2;
	}
	public void setRate2(double aRate2)
	{
		Rate2 = aRate2;
	}
	public void setRate2(String aRate2)
	{
		if (aRate2 != null && !aRate2.equals(""))
		{
			Double tDouble = new Double(aRate2);
			double d = tDouble.doubleValue();
			Rate2 = d;
		}
	}

	public double getAdjFee2()
	{
		return AdjFee2;
	}
	public void setAdjFee2(double aAdjFee2)
	{
		AdjFee2 = aAdjFee2;
	}
	public void setAdjFee2(String aAdjFee2)
	{
		if (aAdjFee2 != null && !aAdjFee2.equals(""))
		{
			Double tDouble = new Double(aAdjFee2);
			double d = tDouble.doubleValue();
			AdjFee2 = d;
		}
	}

	public String getReason2()
	{
		return Reason2;
	}
	public void setReason2(String aReason2)
	{
		if(aReason2!=null && aReason2.length()>100)
			throw new IllegalArgumentException("Reason2Reason2值"+aReason2+"的长度"+aReason2.length()+"大于最大值100");
		Reason2 = aReason2;
	}
	public String getCol3()
	{
		return Col3;
	}
	public void setCol3(String aCol3)
	{
		if(aCol3!=null && aCol3.length()>30)
			throw new IllegalArgumentException("Col3Col3值"+aCol3+"的长度"+aCol3.length()+"大于最大值30");
		Col3 = aCol3;
	}
	public double getRate3()
	{
		return Rate3;
	}
	public void setRate3(double aRate3)
	{
		Rate3 = aRate3;
	}
	public void setRate3(String aRate3)
	{
		if (aRate3 != null && !aRate3.equals(""))
		{
			Double tDouble = new Double(aRate3);
			double d = tDouble.doubleValue();
			Rate3 = d;
		}
	}

	public double getAdjFee3()
	{
		return AdjFee3;
	}
	public void setAdjFee3(double aAdjFee3)
	{
		AdjFee3 = aAdjFee3;
	}
	public void setAdjFee3(String aAdjFee3)
	{
		if (aAdjFee3 != null && !aAdjFee3.equals(""))
		{
			Double tDouble = new Double(aAdjFee3);
			double d = tDouble.doubleValue();
			AdjFee3 = d;
		}
	}

	public String getReason3()
	{
		return Reason3;
	}
	public void setReason3(String aReason3)
	{
		if(aReason3!=null && aReason3.length()>100)
			throw new IllegalArgumentException("Reason3Reason3值"+aReason3+"的长度"+aReason3.length()+"大于最大值100");
		Reason3 = aReason3;
	}
	public String getCol4()
	{
		return Col4;
	}
	public void setCol4(String aCol4)
	{
		if(aCol4!=null && aCol4.length()>30)
			throw new IllegalArgumentException("Col4Col4值"+aCol4+"的长度"+aCol4.length()+"大于最大值30");
		Col4 = aCol4;
	}
	public double getRate4()
	{
		return Rate4;
	}
	public void setRate4(double aRate4)
	{
		Rate4 = aRate4;
	}
	public void setRate4(String aRate4)
	{
		if (aRate4 != null && !aRate4.equals(""))
		{
			Double tDouble = new Double(aRate4);
			double d = tDouble.doubleValue();
			Rate4 = d;
		}
	}

	public double getAdjFee4()
	{
		return AdjFee4;
	}
	public void setAdjFee4(double aAdjFee4)
	{
		AdjFee4 = aAdjFee4;
	}
	public void setAdjFee4(String aAdjFee4)
	{
		if (aAdjFee4 != null && !aAdjFee4.equals(""))
		{
			Double tDouble = new Double(aAdjFee4);
			double d = tDouble.doubleValue();
			AdjFee4 = d;
		}
	}

	public String getReason4()
	{
		return Reason4;
	}
	public void setReason4(String aReason4)
	{
		if(aReason4!=null && aReason4.length()>100)
			throw new IllegalArgumentException("Reason4Reason4值"+aReason4+"的长度"+aReason4.length()+"大于最大值100");
		Reason4 = aReason4;
	}
	public String getCol5()
	{
		return Col5;
	}
	public void setCol5(String aCol5)
	{
		if(aCol5!=null && aCol5.length()>30)
			throw new IllegalArgumentException("Col5Col5值"+aCol5+"的长度"+aCol5.length()+"大于最大值30");
		Col5 = aCol5;
	}
	public double getRate5()
	{
		return Rate5;
	}
	public void setRate5(double aRate5)
	{
		Rate5 = aRate5;
	}
	public void setRate5(String aRate5)
	{
		if (aRate5 != null && !aRate5.equals(""))
		{
			Double tDouble = new Double(aRate5);
			double d = tDouble.doubleValue();
			Rate5 = d;
		}
	}

	public double getAdjFee5()
	{
		return AdjFee5;
	}
	public void setAdjFee5(double aAdjFee5)
	{
		AdjFee5 = aAdjFee5;
	}
	public void setAdjFee5(String aAdjFee5)
	{
		if (aAdjFee5 != null && !aAdjFee5.equals(""))
		{
			Double tDouble = new Double(aAdjFee5);
			double d = tDouble.doubleValue();
			AdjFee5 = d;
		}
	}

	public String getReason5()
	{
		return Reason5;
	}
	public void setReason5(String aReason5)
	{
		if(aReason5!=null && aReason5.length()>100)
			throw new IllegalArgumentException("Reason5Reason5值"+aReason5+"的长度"+aReason5.length()+"大于最大值100");
		Reason5 = aReason5;
	}
	public String getCol6()
	{
		return Col6;
	}
	public void setCol6(String aCol6)
	{
		if(aCol6!=null && aCol6.length()>30)
			throw new IllegalArgumentException("Col6Col6值"+aCol6+"的长度"+aCol6.length()+"大于最大值30");
		Col6 = aCol6;
	}
	public double getRate6()
	{
		return Rate6;
	}
	public void setRate6(double aRate6)
	{
		Rate6 = aRate6;
	}
	public void setRate6(String aRate6)
	{
		if (aRate6 != null && !aRate6.equals(""))
		{
			Double tDouble = new Double(aRate6);
			double d = tDouble.doubleValue();
			Rate6 = d;
		}
	}

	public double getAdjFee6()
	{
		return AdjFee6;
	}
	public void setAdjFee6(double aAdjFee6)
	{
		AdjFee6 = aAdjFee6;
	}
	public void setAdjFee6(String aAdjFee6)
	{
		if (aAdjFee6 != null && !aAdjFee6.equals(""))
		{
			Double tDouble = new Double(aAdjFee6);
			double d = tDouble.doubleValue();
			AdjFee6 = d;
		}
	}

	public String getReason6()
	{
		return Reason6;
	}
	public void setReason6(String aReason6)
	{
		if(aReason6!=null && aReason6.length()>100)
			throw new IllegalArgumentException("Reason6Reason6值"+aReason6+"的长度"+aReason6.length()+"大于最大值100");
		Reason6 = aReason6;
	}
	public String getCol7()
	{
		return Col7;
	}
	public void setCol7(String aCol7)
	{
		if(aCol7!=null && aCol7.length()>30)
			throw new IllegalArgumentException("Col7Col7值"+aCol7+"的长度"+aCol7.length()+"大于最大值30");
		Col7 = aCol7;
	}
	public double getRate7()
	{
		return Rate7;
	}
	public void setRate7(double aRate7)
	{
		Rate7 = aRate7;
	}
	public void setRate7(String aRate7)
	{
		if (aRate7 != null && !aRate7.equals(""))
		{
			Double tDouble = new Double(aRate7);
			double d = tDouble.doubleValue();
			Rate7 = d;
		}
	}

	public double getAdjFee7()
	{
		return AdjFee7;
	}
	public void setAdjFee7(double aAdjFee7)
	{
		AdjFee7 = aAdjFee7;
	}
	public void setAdjFee7(String aAdjFee7)
	{
		if (aAdjFee7 != null && !aAdjFee7.equals(""))
		{
			Double tDouble = new Double(aAdjFee7);
			double d = tDouble.doubleValue();
			AdjFee7 = d;
		}
	}

	public String getReason7()
	{
		return Reason7;
	}
	public void setReason7(String aReason7)
	{
		if(aReason7!=null && aReason7.length()>100)
			throw new IllegalArgumentException("Reason7Reason7值"+aReason7+"的长度"+aReason7.length()+"大于最大值100");
		Reason7 = aReason7;
	}
	public String getCol8()
	{
		return Col8;
	}
	public void setCol8(String aCol8)
	{
		if(aCol8!=null && aCol8.length()>30)
			throw new IllegalArgumentException("Col8Col8值"+aCol8+"的长度"+aCol8.length()+"大于最大值30");
		Col8 = aCol8;
	}
	public double getRate8()
	{
		return Rate8;
	}
	public void setRate8(double aRate8)
	{
		Rate8 = aRate8;
	}
	public void setRate8(String aRate8)
	{
		if (aRate8 != null && !aRate8.equals(""))
		{
			Double tDouble = new Double(aRate8);
			double d = tDouble.doubleValue();
			Rate8 = d;
		}
	}

	public double getAdjFee8()
	{
		return AdjFee8;
	}
	public void setAdjFee8(double aAdjFee8)
	{
		AdjFee8 = aAdjFee8;
	}
	public void setAdjFee8(String aAdjFee8)
	{
		if (aAdjFee8 != null && !aAdjFee8.equals(""))
		{
			Double tDouble = new Double(aAdjFee8);
			double d = tDouble.doubleValue();
			AdjFee8 = d;
		}
	}

	public String getReason8()
	{
		return Reason8;
	}
	public void setReason8(String aReason8)
	{
		if(aReason8!=null && aReason8.length()>100)
			throw new IllegalArgumentException("Reason8Reason8值"+aReason8+"的长度"+aReason8.length()+"大于最大值100");
		Reason8 = aReason8;
	}
	public String getCol9()
	{
		return Col9;
	}
	public void setCol9(String aCol9)
	{
		if(aCol9!=null && aCol9.length()>30)
			throw new IllegalArgumentException("Col9Col9值"+aCol9+"的长度"+aCol9.length()+"大于最大值30");
		Col9 = aCol9;
	}
	public double getRate9()
	{
		return Rate9;
	}
	public void setRate9(double aRate9)
	{
		Rate9 = aRate9;
	}
	public void setRate9(String aRate9)
	{
		if (aRate9 != null && !aRate9.equals(""))
		{
			Double tDouble = new Double(aRate9);
			double d = tDouble.doubleValue();
			Rate9 = d;
		}
	}

	public double getAdjFee9()
	{
		return AdjFee9;
	}
	public void setAdjFee9(double aAdjFee9)
	{
		AdjFee9 = aAdjFee9;
	}
	public void setAdjFee9(String aAdjFee9)
	{
		if (aAdjFee9 != null && !aAdjFee9.equals(""))
		{
			Double tDouble = new Double(aAdjFee9);
			double d = tDouble.doubleValue();
			AdjFee9 = d;
		}
	}

	public String getReason9()
	{
		return Reason9;
	}
	public void setReason9(String aReason9)
	{
		if(aReason9!=null && aReason9.length()>100)
			throw new IllegalArgumentException("Reason9Reason9值"+aReason9+"的长度"+aReason9.length()+"大于最大值100");
		Reason9 = aReason9;
	}
	public String getCol10()
	{
		return Col10;
	}
	public void setCol10(String aCol10)
	{
		if(aCol10!=null && aCol10.length()>30)
			throw new IllegalArgumentException("Col10Col10值"+aCol10+"的长度"+aCol10.length()+"大于最大值30");
		Col10 = aCol10;
	}
	public double getRate10()
	{
		return Rate10;
	}
	public void setRate10(double aRate10)
	{
		Rate10 = aRate10;
	}
	public void setRate10(String aRate10)
	{
		if (aRate10 != null && !aRate10.equals(""))
		{
			Double tDouble = new Double(aRate10);
			double d = tDouble.doubleValue();
			Rate10 = d;
		}
	}

	public double getAdjFee10()
	{
		return AdjFee10;
	}
	public void setAdjFee10(double aAdjFee10)
	{
		AdjFee10 = aAdjFee10;
	}
	public void setAdjFee10(String aAdjFee10)
	{
		if (aAdjFee10 != null && !aAdjFee10.equals(""))
		{
			Double tDouble = new Double(aAdjFee10);
			double d = tDouble.doubleValue();
			AdjFee10 = d;
		}
	}

	public String getReason10()
	{
		return Reason10;
	}
	public void setReason10(String aReason10)
	{
		if(aReason10!=null && aReason10.length()>100)
			throw new IllegalArgumentException("Reason10Reason10值"+aReason10+"的长度"+aReason10.length()+"大于最大值100");
		Reason10 = aReason10;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>100)
			throw new IllegalArgumentException("ReasonReason值"+aReason+"的长度"+aReason.length()+"大于最大值100");
		Reason = aReason;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>10)
			throw new IllegalArgumentException("HospitalcodeHospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值10");
		HospitalCode = aHospitalCode;
	}
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("UwflagUWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>10)
			throw new IllegalArgumentException("UwoperatorUWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值10");
		UWOperator = aUWOperator;
	}
	public double getTotalAdjFee()
	{
		return TotalAdjFee;
	}
	public void setTotalAdjFee(double aTotalAdjFee)
	{
		TotalAdjFee = aTotalAdjFee;
	}
	public void setTotalAdjFee(String aTotalAdjFee)
	{
		if (aTotalAdjFee != null && !aTotalAdjFee.equals(""))
		{
			Double tDouble = new Double(aTotalAdjFee);
			double d = tDouble.doubleValue();
			TotalAdjFee = d;
		}
	}

	public String getFeeItemCode()
	{
		return FeeItemCode;
	}
	public void setFeeItemCode(String aFeeItemCode)
	{
		if(aFeeItemCode!=null && aFeeItemCode.length()>10)
			throw new IllegalArgumentException("FeeitemcodeFeeItemCode值"+aFeeItemCode+"的长度"+aFeeItemCode.length()+"大于最大值10");
		FeeItemCode = aFeeItemCode;
	}
	public String getFeeItemName()
	{
		return FeeItemName;
	}
	public void setFeeItemName(String aFeeItemName)
	{
		if(aFeeItemName!=null && aFeeItemName.length()>60)
			throw new IllegalArgumentException("FeeitemnameFeeItemName值"+aFeeItemName+"的长度"+aFeeItemName.length()+"大于最大值60");
		FeeItemName = aFeeItemName;
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
			throw new IllegalArgumentException("ModifytimeModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getLastOperator()
	{
		return LastOperator;
	}
	public void setLastOperator(String aLastOperator)
	{
		if(aLastOperator!=null && aLastOperator.length()>10)
			throw new IllegalArgumentException("LastoperatorLastOperator值"+aLastOperator+"的长度"+aLastOperator.length()+"大于最大值10");
		LastOperator = aLastOperator;
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
			throw new IllegalArgumentException("MaketimeMakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
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

	/**
	* 使用另外一个 LLCaseReceiptClassSchema 对象给 Schema 赋值
	* @param: aLLCaseReceiptClassSchema LLCaseReceiptClassSchema
	**/
	public void setSchema(LLCaseReceiptClassSchema aLLCaseReceiptClassSchema)
	{
		this.ClmNo = aLLCaseReceiptClassSchema.getClmNo();
		this.MngCom = aLLCaseReceiptClassSchema.getMngCom();
		this.GrpContNo = aLLCaseReceiptClassSchema.getGrpContNo();
		this.InsuredNo = aLLCaseReceiptClassSchema.getInsuredNo();
		this.Name = aLLCaseReceiptClassSchema.getName();
		this.ID = aLLCaseReceiptClassSchema.getID();
		this.BillNo = aLLCaseReceiptClassSchema.getBillNo();
		this.ReceiptDate = fDate.getDate( aLLCaseReceiptClassSchema.getReceiptDate());
		this.FeeItemType = aLLCaseReceiptClassSchema.getFeeItemType();
		this.TotalFee = aLLCaseReceiptClassSchema.getTotalFee();
		this.StartDate = fDate.getDate( aLLCaseReceiptClassSchema.getStartDate());
		this.EndDate = fDate.getDate( aLLCaseReceiptClassSchema.getEndDate());
		this.ValidFlag = aLLCaseReceiptClassSchema.getValidFlag();
		this.Col1 = aLLCaseReceiptClassSchema.getCol1();
		this.Rate1 = aLLCaseReceiptClassSchema.getRate1();
		this.AdjFee1 = aLLCaseReceiptClassSchema.getAdjFee1();
		this.Reason1 = aLLCaseReceiptClassSchema.getReason1();
		this.Col2 = aLLCaseReceiptClassSchema.getCol2();
		this.Rate2 = aLLCaseReceiptClassSchema.getRate2();
		this.AdjFee2 = aLLCaseReceiptClassSchema.getAdjFee2();
		this.Reason2 = aLLCaseReceiptClassSchema.getReason2();
		this.Col3 = aLLCaseReceiptClassSchema.getCol3();
		this.Rate3 = aLLCaseReceiptClassSchema.getRate3();
		this.AdjFee3 = aLLCaseReceiptClassSchema.getAdjFee3();
		this.Reason3 = aLLCaseReceiptClassSchema.getReason3();
		this.Col4 = aLLCaseReceiptClassSchema.getCol4();
		this.Rate4 = aLLCaseReceiptClassSchema.getRate4();
		this.AdjFee4 = aLLCaseReceiptClassSchema.getAdjFee4();
		this.Reason4 = aLLCaseReceiptClassSchema.getReason4();
		this.Col5 = aLLCaseReceiptClassSchema.getCol5();
		this.Rate5 = aLLCaseReceiptClassSchema.getRate5();
		this.AdjFee5 = aLLCaseReceiptClassSchema.getAdjFee5();
		this.Reason5 = aLLCaseReceiptClassSchema.getReason5();
		this.Col6 = aLLCaseReceiptClassSchema.getCol6();
		this.Rate6 = aLLCaseReceiptClassSchema.getRate6();
		this.AdjFee6 = aLLCaseReceiptClassSchema.getAdjFee6();
		this.Reason6 = aLLCaseReceiptClassSchema.getReason6();
		this.Col7 = aLLCaseReceiptClassSchema.getCol7();
		this.Rate7 = aLLCaseReceiptClassSchema.getRate7();
		this.AdjFee7 = aLLCaseReceiptClassSchema.getAdjFee7();
		this.Reason7 = aLLCaseReceiptClassSchema.getReason7();
		this.Col8 = aLLCaseReceiptClassSchema.getCol8();
		this.Rate8 = aLLCaseReceiptClassSchema.getRate8();
		this.AdjFee8 = aLLCaseReceiptClassSchema.getAdjFee8();
		this.Reason8 = aLLCaseReceiptClassSchema.getReason8();
		this.Col9 = aLLCaseReceiptClassSchema.getCol9();
		this.Rate9 = aLLCaseReceiptClassSchema.getRate9();
		this.AdjFee9 = aLLCaseReceiptClassSchema.getAdjFee9();
		this.Reason9 = aLLCaseReceiptClassSchema.getReason9();
		this.Col10 = aLLCaseReceiptClassSchema.getCol10();
		this.Rate10 = aLLCaseReceiptClassSchema.getRate10();
		this.AdjFee10 = aLLCaseReceiptClassSchema.getAdjFee10();
		this.Reason10 = aLLCaseReceiptClassSchema.getReason10();
		this.Reason = aLLCaseReceiptClassSchema.getReason();
		this.HospitalCode = aLLCaseReceiptClassSchema.getHospitalCode();
		this.UWFlag = aLLCaseReceiptClassSchema.getUWFlag();
		this.UWDate = fDate.getDate( aLLCaseReceiptClassSchema.getUWDate());
		this.UWOperator = aLLCaseReceiptClassSchema.getUWOperator();
		this.TotalAdjFee = aLLCaseReceiptClassSchema.getTotalAdjFee();
		this.FeeItemCode = aLLCaseReceiptClassSchema.getFeeItemCode();
		this.FeeItemName = aLLCaseReceiptClassSchema.getFeeItemName();
		this.ModifyDate = fDate.getDate( aLLCaseReceiptClassSchema.getModifyDate());
		this.ModifyTime = aLLCaseReceiptClassSchema.getModifyTime();
		this.LastOperator = aLLCaseReceiptClassSchema.getLastOperator();
		this.MakeDate = fDate.getDate( aLLCaseReceiptClassSchema.getMakeDate());
		this.MakeTime = aLLCaseReceiptClassSchema.getMakeTime();
		this.Operator = aLLCaseReceiptClassSchema.getOperator();
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

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			this.ID = rs.getInt("ID");
			if( rs.getString("BillNo") == null )
				this.BillNo = null;
			else
				this.BillNo = rs.getString("BillNo").trim();

			this.ReceiptDate = rs.getDate("ReceiptDate");
			if( rs.getString("FeeItemType") == null )
				this.FeeItemType = null;
			else
				this.FeeItemType = rs.getString("FeeItemType").trim();

			this.TotalFee = rs.getDouble("TotalFee");
			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

			if( rs.getString("Col1") == null )
				this.Col1 = null;
			else
				this.Col1 = rs.getString("Col1").trim();

			this.Rate1 = rs.getDouble("Rate1");
			this.AdjFee1 = rs.getDouble("AdjFee1");
			if( rs.getString("Reason1") == null )
				this.Reason1 = null;
			else
				this.Reason1 = rs.getString("Reason1").trim();

			if( rs.getString("Col2") == null )
				this.Col2 = null;
			else
				this.Col2 = rs.getString("Col2").trim();

			this.Rate2 = rs.getDouble("Rate2");
			this.AdjFee2 = rs.getDouble("AdjFee2");
			if( rs.getString("Reason2") == null )
				this.Reason2 = null;
			else
				this.Reason2 = rs.getString("Reason2").trim();

			if( rs.getString("Col3") == null )
				this.Col3 = null;
			else
				this.Col3 = rs.getString("Col3").trim();

			this.Rate3 = rs.getDouble("Rate3");
			this.AdjFee3 = rs.getDouble("AdjFee3");
			if( rs.getString("Reason3") == null )
				this.Reason3 = null;
			else
				this.Reason3 = rs.getString("Reason3").trim();

			if( rs.getString("Col4") == null )
				this.Col4 = null;
			else
				this.Col4 = rs.getString("Col4").trim();

			this.Rate4 = rs.getDouble("Rate4");
			this.AdjFee4 = rs.getDouble("AdjFee4");
			if( rs.getString("Reason4") == null )
				this.Reason4 = null;
			else
				this.Reason4 = rs.getString("Reason4").trim();

			if( rs.getString("Col5") == null )
				this.Col5 = null;
			else
				this.Col5 = rs.getString("Col5").trim();

			this.Rate5 = rs.getDouble("Rate5");
			this.AdjFee5 = rs.getDouble("AdjFee5");
			if( rs.getString("Reason5") == null )
				this.Reason5 = null;
			else
				this.Reason5 = rs.getString("Reason5").trim();

			if( rs.getString("Col6") == null )
				this.Col6 = null;
			else
				this.Col6 = rs.getString("Col6").trim();

			this.Rate6 = rs.getDouble("Rate6");
			this.AdjFee6 = rs.getDouble("AdjFee6");
			if( rs.getString("Reason6") == null )
				this.Reason6 = null;
			else
				this.Reason6 = rs.getString("Reason6").trim();

			if( rs.getString("Col7") == null )
				this.Col7 = null;
			else
				this.Col7 = rs.getString("Col7").trim();

			this.Rate7 = rs.getDouble("Rate7");
			this.AdjFee7 = rs.getDouble("AdjFee7");
			if( rs.getString("Reason7") == null )
				this.Reason7 = null;
			else
				this.Reason7 = rs.getString("Reason7").trim();

			if( rs.getString("Col8") == null )
				this.Col8 = null;
			else
				this.Col8 = rs.getString("Col8").trim();

			this.Rate8 = rs.getDouble("Rate8");
			this.AdjFee8 = rs.getDouble("AdjFee8");
			if( rs.getString("Reason8") == null )
				this.Reason8 = null;
			else
				this.Reason8 = rs.getString("Reason8").trim();

			if( rs.getString("Col9") == null )
				this.Col9 = null;
			else
				this.Col9 = rs.getString("Col9").trim();

			this.Rate9 = rs.getDouble("Rate9");
			this.AdjFee9 = rs.getDouble("AdjFee9");
			if( rs.getString("Reason9") == null )
				this.Reason9 = null;
			else
				this.Reason9 = rs.getString("Reason9").trim();

			if( rs.getString("Col10") == null )
				this.Col10 = null;
			else
				this.Col10 = rs.getString("Col10").trim();

			this.Rate10 = rs.getDouble("Rate10");
			this.AdjFee10 = rs.getDouble("AdjFee10");
			if( rs.getString("Reason10") == null )
				this.Reason10 = null;
			else
				this.Reason10 = rs.getString("Reason10").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.TotalAdjFee = rs.getDouble("TotalAdjFee");
			if( rs.getString("FeeItemCode") == null )
				this.FeeItemCode = null;
			else
				this.FeeItemCode = rs.getString("FeeItemCode").trim();

			if( rs.getString("FeeItemName") == null )
				this.FeeItemName = null;
			else
				this.FeeItemName = rs.getString("FeeItemName").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("LastOperator") == null )
				this.LastOperator = null;
			else
				this.LastOperator = rs.getString("LastOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCaseReceiptClass表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseReceiptClassSchema getSchema()
	{
		LLCaseReceiptClassSchema aLLCaseReceiptClassSchema = new LLCaseReceiptClassSchema();
		aLLCaseReceiptClassSchema.setSchema(this);
		return aLLCaseReceiptClassSchema;
	}

	public LLCaseReceiptClassDB getDB()
	{
		LLCaseReceiptClassDB aDBOper = new LLCaseReceiptClassDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseReceiptClass描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReceiptDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjFee10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalAdjFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseReceiptClass>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReceiptDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			FeeItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TotalFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Col1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Rate1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			Reason1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Col2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Rate2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			Reason2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Col3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Rate3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			Reason3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Col4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Rate4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).doubleValue();
			Reason4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Col5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Rate5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).doubleValue();
			Reason5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Col6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Rate6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			Reason6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Col7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			Rate7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			Reason7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Col8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			Rate8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			Reason8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			Col9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			Rate9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,47,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,48,SysConst.PACKAGESPILTER))).doubleValue();
			Reason9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			Col10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			Rate10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,51,SysConst.PACKAGESPILTER))).doubleValue();
			AdjFee10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).doubleValue();
			Reason10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			TotalAdjFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,59,SysConst.PACKAGESPILTER))).doubleValue();
			FeeItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			FeeItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			LastOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseReceiptClassSchema";
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ID));
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillNo));
		}
		if (FCode.equalsIgnoreCase("ReceiptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReceiptDate()));
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemType));
		}
		if (FCode.equalsIgnoreCase("TotalFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalFee));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
		}
		if (FCode.equalsIgnoreCase("Col1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col1));
		}
		if (FCode.equalsIgnoreCase("Rate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate1));
		}
		if (FCode.equalsIgnoreCase("AdjFee1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee1));
		}
		if (FCode.equalsIgnoreCase("Reason1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason1));
		}
		if (FCode.equalsIgnoreCase("Col2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col2));
		}
		if (FCode.equalsIgnoreCase("Rate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate2));
		}
		if (FCode.equalsIgnoreCase("AdjFee2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee2));
		}
		if (FCode.equalsIgnoreCase("Reason2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason2));
		}
		if (FCode.equalsIgnoreCase("Col3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col3));
		}
		if (FCode.equalsIgnoreCase("Rate3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate3));
		}
		if (FCode.equalsIgnoreCase("AdjFee3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee3));
		}
		if (FCode.equalsIgnoreCase("Reason3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason3));
		}
		if (FCode.equalsIgnoreCase("Col4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col4));
		}
		if (FCode.equalsIgnoreCase("Rate4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate4));
		}
		if (FCode.equalsIgnoreCase("AdjFee4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee4));
		}
		if (FCode.equalsIgnoreCase("Reason4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason4));
		}
		if (FCode.equalsIgnoreCase("Col5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col5));
		}
		if (FCode.equalsIgnoreCase("Rate5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate5));
		}
		if (FCode.equalsIgnoreCase("AdjFee5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee5));
		}
		if (FCode.equalsIgnoreCase("Reason5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason5));
		}
		if (FCode.equalsIgnoreCase("Col6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col6));
		}
		if (FCode.equalsIgnoreCase("Rate6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate6));
		}
		if (FCode.equalsIgnoreCase("AdjFee6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee6));
		}
		if (FCode.equalsIgnoreCase("Reason6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason6));
		}
		if (FCode.equalsIgnoreCase("Col7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col7));
		}
		if (FCode.equalsIgnoreCase("Rate7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate7));
		}
		if (FCode.equalsIgnoreCase("AdjFee7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee7));
		}
		if (FCode.equalsIgnoreCase("Reason7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason7));
		}
		if (FCode.equalsIgnoreCase("Col8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col8));
		}
		if (FCode.equalsIgnoreCase("Rate8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate8));
		}
		if (FCode.equalsIgnoreCase("AdjFee8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee8));
		}
		if (FCode.equalsIgnoreCase("Reason8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason8));
		}
		if (FCode.equalsIgnoreCase("Col9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col9));
		}
		if (FCode.equalsIgnoreCase("Rate9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate9));
		}
		if (FCode.equalsIgnoreCase("AdjFee9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee9));
		}
		if (FCode.equalsIgnoreCase("Reason9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason9));
		}
		if (FCode.equalsIgnoreCase("Col10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col10));
		}
		if (FCode.equalsIgnoreCase("Rate10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate10));
		}
		if (FCode.equalsIgnoreCase("AdjFee10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjFee10));
		}
		if (FCode.equalsIgnoreCase("Reason10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason10));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("TotalAdjFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalAdjFee));
		}
		if (FCode.equalsIgnoreCase("FeeItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemCode));
		}
		if (FCode.equalsIgnoreCase("FeeItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeItemName));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 5:
				strFieldValue = String.valueOf(ID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiptDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FeeItemType);
				break;
			case 9:
				strFieldValue = String.valueOf(TotalFee);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Col1);
				break;
			case 14:
				strFieldValue = String.valueOf(Rate1);
				break;
			case 15:
				strFieldValue = String.valueOf(AdjFee1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Reason1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Col2);
				break;
			case 18:
				strFieldValue = String.valueOf(Rate2);
				break;
			case 19:
				strFieldValue = String.valueOf(AdjFee2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Reason2);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Col3);
				break;
			case 22:
				strFieldValue = String.valueOf(Rate3);
				break;
			case 23:
				strFieldValue = String.valueOf(AdjFee3);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Reason3);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Col4);
				break;
			case 26:
				strFieldValue = String.valueOf(Rate4);
				break;
			case 27:
				strFieldValue = String.valueOf(AdjFee4);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Reason4);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Col5);
				break;
			case 30:
				strFieldValue = String.valueOf(Rate5);
				break;
			case 31:
				strFieldValue = String.valueOf(AdjFee5);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Reason5);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Col6);
				break;
			case 34:
				strFieldValue = String.valueOf(Rate6);
				break;
			case 35:
				strFieldValue = String.valueOf(AdjFee6);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Reason6);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Col7);
				break;
			case 38:
				strFieldValue = String.valueOf(Rate7);
				break;
			case 39:
				strFieldValue = String.valueOf(AdjFee7);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Reason7);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(Col8);
				break;
			case 42:
				strFieldValue = String.valueOf(Rate8);
				break;
			case 43:
				strFieldValue = String.valueOf(AdjFee8);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(Reason8);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(Col9);
				break;
			case 46:
				strFieldValue = String.valueOf(Rate9);
				break;
			case 47:
				strFieldValue = String.valueOf(AdjFee9);
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(Reason9);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(Col10);
				break;
			case 50:
				strFieldValue = String.valueOf(Rate10);
				break;
			case 51:
				strFieldValue = String.valueOf(AdjFee10);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(Reason10);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 58:
				strFieldValue = String.valueOf(TotalAdjFee);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(FeeItemCode);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(FeeItemName);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(LastOperator);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("ID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ID = i;
			}
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillNo = FValue.trim();
			}
			else
				BillNo = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReceiptDate = fDate.getDate( FValue );
			}
			else
				ReceiptDate = null;
		}
		if (FCode.equalsIgnoreCase("FeeItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemType = FValue.trim();
			}
			else
				FeeItemType = null;
		}
		if (FCode.equalsIgnoreCase("TotalFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidFlag = FValue.trim();
			}
			else
				ValidFlag = null;
		}
		if (FCode.equalsIgnoreCase("Col1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col1 = FValue.trim();
			}
			else
				Col1 = null;
		}
		if (FCode.equalsIgnoreCase("Rate1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason1 = FValue.trim();
			}
			else
				Reason1 = null;
		}
		if (FCode.equalsIgnoreCase("Col2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col2 = FValue.trim();
			}
			else
				Col2 = null;
		}
		if (FCode.equalsIgnoreCase("Rate2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason2 = FValue.trim();
			}
			else
				Reason2 = null;
		}
		if (FCode.equalsIgnoreCase("Col3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col3 = FValue.trim();
			}
			else
				Col3 = null;
		}
		if (FCode.equalsIgnoreCase("Rate3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason3 = FValue.trim();
			}
			else
				Reason3 = null;
		}
		if (FCode.equalsIgnoreCase("Col4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col4 = FValue.trim();
			}
			else
				Col4 = null;
		}
		if (FCode.equalsIgnoreCase("Rate4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason4 = FValue.trim();
			}
			else
				Reason4 = null;
		}
		if (FCode.equalsIgnoreCase("Col5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col5 = FValue.trim();
			}
			else
				Col5 = null;
		}
		if (FCode.equalsIgnoreCase("Rate5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason5 = FValue.trim();
			}
			else
				Reason5 = null;
		}
		if (FCode.equalsIgnoreCase("Col6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col6 = FValue.trim();
			}
			else
				Col6 = null;
		}
		if (FCode.equalsIgnoreCase("Rate6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason6 = FValue.trim();
			}
			else
				Reason6 = null;
		}
		if (FCode.equalsIgnoreCase("Col7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col7 = FValue.trim();
			}
			else
				Col7 = null;
		}
		if (FCode.equalsIgnoreCase("Rate7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason7 = FValue.trim();
			}
			else
				Reason7 = null;
		}
		if (FCode.equalsIgnoreCase("Col8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col8 = FValue.trim();
			}
			else
				Col8 = null;
		}
		if (FCode.equalsIgnoreCase("Rate8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason8 = FValue.trim();
			}
			else
				Reason8 = null;
		}
		if (FCode.equalsIgnoreCase("Col9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col9 = FValue.trim();
			}
			else
				Col9 = null;
		}
		if (FCode.equalsIgnoreCase("Rate9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason9 = FValue.trim();
			}
			else
				Reason9 = null;
		}
		if (FCode.equalsIgnoreCase("Col10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col10 = FValue.trim();
			}
			else
				Col10 = null;
		}
		if (FCode.equalsIgnoreCase("Rate10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjFee10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjFee10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Reason10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason10 = FValue.trim();
			}
			else
				Reason10 = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalCode = FValue.trim();
			}
			else
				HospitalCode = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
		}
		if (FCode.equalsIgnoreCase("TotalAdjFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TotalAdjFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("FeeItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemCode = FValue.trim();
			}
			else
				FeeItemCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeItemName = FValue.trim();
			}
			else
				FeeItemName = null;
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
		if (FCode.equalsIgnoreCase("LastOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastOperator = FValue.trim();
			}
			else
				LastOperator = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseReceiptClassSchema other = (LLCaseReceiptClassSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& MngCom.equals(other.getMngCom())
			&& GrpContNo.equals(other.getGrpContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& Name.equals(other.getName())
			&& ID == other.getID()
			&& BillNo.equals(other.getBillNo())
			&& fDate.getString(ReceiptDate).equals(other.getReceiptDate())
			&& FeeItemType.equals(other.getFeeItemType())
			&& TotalFee == other.getTotalFee()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& ValidFlag.equals(other.getValidFlag())
			&& Col1.equals(other.getCol1())
			&& Rate1 == other.getRate1()
			&& AdjFee1 == other.getAdjFee1()
			&& Reason1.equals(other.getReason1())
			&& Col2.equals(other.getCol2())
			&& Rate2 == other.getRate2()
			&& AdjFee2 == other.getAdjFee2()
			&& Reason2.equals(other.getReason2())
			&& Col3.equals(other.getCol3())
			&& Rate3 == other.getRate3()
			&& AdjFee3 == other.getAdjFee3()
			&& Reason3.equals(other.getReason3())
			&& Col4.equals(other.getCol4())
			&& Rate4 == other.getRate4()
			&& AdjFee4 == other.getAdjFee4()
			&& Reason4.equals(other.getReason4())
			&& Col5.equals(other.getCol5())
			&& Rate5 == other.getRate5()
			&& AdjFee5 == other.getAdjFee5()
			&& Reason5.equals(other.getReason5())
			&& Col6.equals(other.getCol6())
			&& Rate6 == other.getRate6()
			&& AdjFee6 == other.getAdjFee6()
			&& Reason6.equals(other.getReason6())
			&& Col7.equals(other.getCol7())
			&& Rate7 == other.getRate7()
			&& AdjFee7 == other.getAdjFee7()
			&& Reason7.equals(other.getReason7())
			&& Col8.equals(other.getCol8())
			&& Rate8 == other.getRate8()
			&& AdjFee8 == other.getAdjFee8()
			&& Reason8.equals(other.getReason8())
			&& Col9.equals(other.getCol9())
			&& Rate9 == other.getRate9()
			&& AdjFee9 == other.getAdjFee9()
			&& Reason9.equals(other.getReason9())
			&& Col10.equals(other.getCol10())
			&& Rate10 == other.getRate10()
			&& AdjFee10 == other.getAdjFee10()
			&& Reason10.equals(other.getReason10())
			&& Reason.equals(other.getReason())
			&& HospitalCode.equals(other.getHospitalCode())
			&& UWFlag.equals(other.getUWFlag())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWOperator.equals(other.getUWOperator())
			&& TotalAdjFee == other.getTotalAdjFee()
			&& FeeItemCode.equals(other.getFeeItemCode())
			&& FeeItemName.equals(other.getFeeItemName())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& LastOperator.equals(other.getLastOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("MngCom") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 3;
		}
		if( strFieldName.equals("Name") ) {
			return 4;
		}
		if( strFieldName.equals("ID") ) {
			return 5;
		}
		if( strFieldName.equals("BillNo") ) {
			return 6;
		}
		if( strFieldName.equals("ReceiptDate") ) {
			return 7;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return 8;
		}
		if( strFieldName.equals("TotalFee") ) {
			return 9;
		}
		if( strFieldName.equals("StartDate") ) {
			return 10;
		}
		if( strFieldName.equals("EndDate") ) {
			return 11;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return 12;
		}
		if( strFieldName.equals("Col1") ) {
			return 13;
		}
		if( strFieldName.equals("Rate1") ) {
			return 14;
		}
		if( strFieldName.equals("AdjFee1") ) {
			return 15;
		}
		if( strFieldName.equals("Reason1") ) {
			return 16;
		}
		if( strFieldName.equals("Col2") ) {
			return 17;
		}
		if( strFieldName.equals("Rate2") ) {
			return 18;
		}
		if( strFieldName.equals("AdjFee2") ) {
			return 19;
		}
		if( strFieldName.equals("Reason2") ) {
			return 20;
		}
		if( strFieldName.equals("Col3") ) {
			return 21;
		}
		if( strFieldName.equals("Rate3") ) {
			return 22;
		}
		if( strFieldName.equals("AdjFee3") ) {
			return 23;
		}
		if( strFieldName.equals("Reason3") ) {
			return 24;
		}
		if( strFieldName.equals("Col4") ) {
			return 25;
		}
		if( strFieldName.equals("Rate4") ) {
			return 26;
		}
		if( strFieldName.equals("AdjFee4") ) {
			return 27;
		}
		if( strFieldName.equals("Reason4") ) {
			return 28;
		}
		if( strFieldName.equals("Col5") ) {
			return 29;
		}
		if( strFieldName.equals("Rate5") ) {
			return 30;
		}
		if( strFieldName.equals("AdjFee5") ) {
			return 31;
		}
		if( strFieldName.equals("Reason5") ) {
			return 32;
		}
		if( strFieldName.equals("Col6") ) {
			return 33;
		}
		if( strFieldName.equals("Rate6") ) {
			return 34;
		}
		if( strFieldName.equals("AdjFee6") ) {
			return 35;
		}
		if( strFieldName.equals("Reason6") ) {
			return 36;
		}
		if( strFieldName.equals("Col7") ) {
			return 37;
		}
		if( strFieldName.equals("Rate7") ) {
			return 38;
		}
		if( strFieldName.equals("AdjFee7") ) {
			return 39;
		}
		if( strFieldName.equals("Reason7") ) {
			return 40;
		}
		if( strFieldName.equals("Col8") ) {
			return 41;
		}
		if( strFieldName.equals("Rate8") ) {
			return 42;
		}
		if( strFieldName.equals("AdjFee8") ) {
			return 43;
		}
		if( strFieldName.equals("Reason8") ) {
			return 44;
		}
		if( strFieldName.equals("Col9") ) {
			return 45;
		}
		if( strFieldName.equals("Rate9") ) {
			return 46;
		}
		if( strFieldName.equals("AdjFee9") ) {
			return 47;
		}
		if( strFieldName.equals("Reason9") ) {
			return 48;
		}
		if( strFieldName.equals("Col10") ) {
			return 49;
		}
		if( strFieldName.equals("Rate10") ) {
			return 50;
		}
		if( strFieldName.equals("AdjFee10") ) {
			return 51;
		}
		if( strFieldName.equals("Reason10") ) {
			return 52;
		}
		if( strFieldName.equals("Reason") ) {
			return 53;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 54;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 55;
		}
		if( strFieldName.equals("UWDate") ) {
			return 56;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 57;
		}
		if( strFieldName.equals("TotalAdjFee") ) {
			return 58;
		}
		if( strFieldName.equals("FeeItemCode") ) {
			return 59;
		}
		if( strFieldName.equals("FeeItemName") ) {
			return 60;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 61;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 62;
		}
		if( strFieldName.equals("LastOperator") ) {
			return 63;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 64;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 65;
		}
		if( strFieldName.equals("Operator") ) {
			return 66;
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
				strFieldName = "MngCom";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "InsuredNo";
				break;
			case 4:
				strFieldName = "Name";
				break;
			case 5:
				strFieldName = "ID";
				break;
			case 6:
				strFieldName = "BillNo";
				break;
			case 7:
				strFieldName = "ReceiptDate";
				break;
			case 8:
				strFieldName = "FeeItemType";
				break;
			case 9:
				strFieldName = "TotalFee";
				break;
			case 10:
				strFieldName = "StartDate";
				break;
			case 11:
				strFieldName = "EndDate";
				break;
			case 12:
				strFieldName = "ValidFlag";
				break;
			case 13:
				strFieldName = "Col1";
				break;
			case 14:
				strFieldName = "Rate1";
				break;
			case 15:
				strFieldName = "AdjFee1";
				break;
			case 16:
				strFieldName = "Reason1";
				break;
			case 17:
				strFieldName = "Col2";
				break;
			case 18:
				strFieldName = "Rate2";
				break;
			case 19:
				strFieldName = "AdjFee2";
				break;
			case 20:
				strFieldName = "Reason2";
				break;
			case 21:
				strFieldName = "Col3";
				break;
			case 22:
				strFieldName = "Rate3";
				break;
			case 23:
				strFieldName = "AdjFee3";
				break;
			case 24:
				strFieldName = "Reason3";
				break;
			case 25:
				strFieldName = "Col4";
				break;
			case 26:
				strFieldName = "Rate4";
				break;
			case 27:
				strFieldName = "AdjFee4";
				break;
			case 28:
				strFieldName = "Reason4";
				break;
			case 29:
				strFieldName = "Col5";
				break;
			case 30:
				strFieldName = "Rate5";
				break;
			case 31:
				strFieldName = "AdjFee5";
				break;
			case 32:
				strFieldName = "Reason5";
				break;
			case 33:
				strFieldName = "Col6";
				break;
			case 34:
				strFieldName = "Rate6";
				break;
			case 35:
				strFieldName = "AdjFee6";
				break;
			case 36:
				strFieldName = "Reason6";
				break;
			case 37:
				strFieldName = "Col7";
				break;
			case 38:
				strFieldName = "Rate7";
				break;
			case 39:
				strFieldName = "AdjFee7";
				break;
			case 40:
				strFieldName = "Reason7";
				break;
			case 41:
				strFieldName = "Col8";
				break;
			case 42:
				strFieldName = "Rate8";
				break;
			case 43:
				strFieldName = "AdjFee8";
				break;
			case 44:
				strFieldName = "Reason8";
				break;
			case 45:
				strFieldName = "Col9";
				break;
			case 46:
				strFieldName = "Rate9";
				break;
			case 47:
				strFieldName = "AdjFee9";
				break;
			case 48:
				strFieldName = "Reason9";
				break;
			case 49:
				strFieldName = "Col10";
				break;
			case 50:
				strFieldName = "Rate10";
				break;
			case 51:
				strFieldName = "AdjFee10";
				break;
			case 52:
				strFieldName = "Reason10";
				break;
			case 53:
				strFieldName = "Reason";
				break;
			case 54:
				strFieldName = "HospitalCode";
				break;
			case 55:
				strFieldName = "UWFlag";
				break;
			case 56:
				strFieldName = "UWDate";
				break;
			case 57:
				strFieldName = "UWOperator";
				break;
			case 58:
				strFieldName = "TotalAdjFee";
				break;
			case 59:
				strFieldName = "FeeItemCode";
				break;
			case 60:
				strFieldName = "FeeItemName";
				break;
			case 61:
				strFieldName = "ModifyDate";
				break;
			case 62:
				strFieldName = "ModifyTime";
				break;
			case 63:
				strFieldName = "LastOperator";
				break;
			case 64:
				strFieldName = "MakeDate";
				break;
			case 65:
				strFieldName = "MakeTime";
				break;
			case 66:
				strFieldName = "Operator";
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
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FeeItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rate10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjFee10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Reason10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TotalAdjFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FeeItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 27:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 31:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 43:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 47:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 50:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 51:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 52:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 53:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 54:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 55:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 56:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 59:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 60:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 61:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
