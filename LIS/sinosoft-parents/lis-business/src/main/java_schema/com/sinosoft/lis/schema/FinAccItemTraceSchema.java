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
import com.sinosoft.lis.db.FinAccItemTraceDB;

/*
 * <p>ClassName: FinAccItemTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinAccItemTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FinAccItemTraceSchema.class);
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 交易流水号 */
	private String TransSerialNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 其它号码 */
	private String OtherNo;
	/** 交易类型编码 */
	private String TransTypeCode;
	/** 交易日期 */
	private Date TransDate;
	/** 分支科目编码 */
	private String AccItemCode;
	/** 分支科目编码1 */
	private String AccItemCode1;
	/** 分支科目编码2 */
	private String AccItemCode2;
	/** 分支科目编码3 */
	private String AccItemCode3;
	/** 分支科目编码4 */
	private String AccItemCode4;
	/** 分支科目编码5 */
	private String AccItemCode5;
	/** 分支科目编码6 */
	private String AccItemCode6;
	/** 分支科目名称 */
	private String AccItemName;
	/** 会计科目编码 */
	private String FinCode;
	/** 会计科目名称 */
	private String FinName;
	/** 借/贷 */
	private String D_C;
	/** 金额 */
	private double Amount;
	/** 属性字段1 */
	private String Segment1;
	/** 属性字段2 */
	private String Segment2;
	/** 属性字段3 */
	private String Segment3;
	/** 属性字段4 */
	private String Segment4;
	/** 属性字段5 */
	private String Segment5;
	/** 属性字段6 */
	private String Segment6;
	/** 属性字段7 */
	private String Segment7;
	/** 属性字段8 */
	private String Segment8;
	/** 属性字段9 */
	private String Segment9;
	/** 属性字段10 */
	private String Segment10;
	/** 属性字段11 */
	private String Segment11;
	/** 属性字段12 */
	private String Segment12;
	/** 属性字段13 */
	private String Segment13;
	/** 属性字段14 */
	private String Segment14;
	/** 属性字段15 */
	private String Segment15;
	/** 属性字段16 */
	private String Segment16;
	/** 属性字段17 */
	private String Segment17;
	/** 属性字段18 */
	private String Segment18;
	/** 属性字段19 */
	private String Segment19;
	/** 属性字段20 */
	private String Segment20;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 46;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FinAccItemTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		FinAccItemTraceSchema cloned = (FinAccItemTraceSchema)super.clone();
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
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getTransSerialNo()
	{
		return TransSerialNo;
	}
	public void setTransSerialNo(String aTransSerialNo)
	{
		if(aTransSerialNo!=null && aTransSerialNo.length()>20)
			throw new IllegalArgumentException("交易流水号TransSerialNo值"+aTransSerialNo+"的长度"+aTransSerialNo.length()+"大于最大值20");
		TransSerialNo = aTransSerialNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
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
	public String getTransTypeCode()
	{
		return TransTypeCode;
	}
	public void setTransTypeCode(String aTransTypeCode)
	{
		if(aTransTypeCode!=null && aTransTypeCode.length()>10)
			throw new IllegalArgumentException("交易类型编码TransTypeCode值"+aTransTypeCode+"的长度"+aTransTypeCode.length()+"大于最大值10");
		TransTypeCode = aTransTypeCode;
	}
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

	public String getAccItemCode()
	{
		return AccItemCode;
	}
	public void setAccItemCode(String aAccItemCode)
	{
		if(aAccItemCode!=null && aAccItemCode.length()>30)
			throw new IllegalArgumentException("分支科目编码AccItemCode值"+aAccItemCode+"的长度"+aAccItemCode.length()+"大于最大值30");
		AccItemCode = aAccItemCode;
	}
	public String getAccItemCode1()
	{
		return AccItemCode1;
	}
	public void setAccItemCode1(String aAccItemCode1)
	{
		if(aAccItemCode1!=null && aAccItemCode1.length()>30)
			throw new IllegalArgumentException("分支科目编码1AccItemCode1值"+aAccItemCode1+"的长度"+aAccItemCode1.length()+"大于最大值30");
		AccItemCode1 = aAccItemCode1;
	}
	public String getAccItemCode2()
	{
		return AccItemCode2;
	}
	public void setAccItemCode2(String aAccItemCode2)
	{
		if(aAccItemCode2!=null && aAccItemCode2.length()>30)
			throw new IllegalArgumentException("分支科目编码2AccItemCode2值"+aAccItemCode2+"的长度"+aAccItemCode2.length()+"大于最大值30");
		AccItemCode2 = aAccItemCode2;
	}
	public String getAccItemCode3()
	{
		return AccItemCode3;
	}
	public void setAccItemCode3(String aAccItemCode3)
	{
		if(aAccItemCode3!=null && aAccItemCode3.length()>30)
			throw new IllegalArgumentException("分支科目编码3AccItemCode3值"+aAccItemCode3+"的长度"+aAccItemCode3.length()+"大于最大值30");
		AccItemCode3 = aAccItemCode3;
	}
	public String getAccItemCode4()
	{
		return AccItemCode4;
	}
	public void setAccItemCode4(String aAccItemCode4)
	{
		if(aAccItemCode4!=null && aAccItemCode4.length()>30)
			throw new IllegalArgumentException("分支科目编码4AccItemCode4值"+aAccItemCode4+"的长度"+aAccItemCode4.length()+"大于最大值30");
		AccItemCode4 = aAccItemCode4;
	}
	public String getAccItemCode5()
	{
		return AccItemCode5;
	}
	public void setAccItemCode5(String aAccItemCode5)
	{
		if(aAccItemCode5!=null && aAccItemCode5.length()>30)
			throw new IllegalArgumentException("分支科目编码5AccItemCode5值"+aAccItemCode5+"的长度"+aAccItemCode5.length()+"大于最大值30");
		AccItemCode5 = aAccItemCode5;
	}
	public String getAccItemCode6()
	{
		return AccItemCode6;
	}
	public void setAccItemCode6(String aAccItemCode6)
	{
		if(aAccItemCode6!=null && aAccItemCode6.length()>30)
			throw new IllegalArgumentException("分支科目编码6AccItemCode6值"+aAccItemCode6+"的长度"+aAccItemCode6.length()+"大于最大值30");
		AccItemCode6 = aAccItemCode6;
	}
	public String getAccItemName()
	{
		return AccItemName;
	}
	public void setAccItemName(String aAccItemName)
	{
		if(aAccItemName!=null && aAccItemName.length()>200)
			throw new IllegalArgumentException("分支科目名称AccItemName值"+aAccItemName+"的长度"+aAccItemName.length()+"大于最大值200");
		AccItemName = aAccItemName;
	}
	public String getFinCode()
	{
		return FinCode;
	}
	public void setFinCode(String aFinCode)
	{
		if(aFinCode!=null && aFinCode.length()>30)
			throw new IllegalArgumentException("会计科目编码FinCode值"+aFinCode+"的长度"+aFinCode.length()+"大于最大值30");
		FinCode = aFinCode;
	}
	public String getFinName()
	{
		return FinName;
	}
	public void setFinName(String aFinName)
	{
		if(aFinName!=null && aFinName.length()>200)
			throw new IllegalArgumentException("会计科目名称FinName值"+aFinName+"的长度"+aFinName.length()+"大于最大值200");
		FinName = aFinName;
	}
	public String getD_C()
	{
		return D_C;
	}
	public void setD_C(String aD_C)
	{
		if(aD_C!=null && aD_C.length()>1)
			throw new IllegalArgumentException("借/贷D_C值"+aD_C+"的长度"+aD_C.length()+"大于最大值1");
		D_C = aD_C;
	}
	public double getAmount()
	{
		return Amount;
	}
	public void setAmount(double aAmount)
	{
		Amount = aAmount;
	}
	public void setAmount(String aAmount)
	{
		if (aAmount != null && !aAmount.equals(""))
		{
			Double tDouble = new Double(aAmount);
			double d = tDouble.doubleValue();
			Amount = d;
		}
	}

	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>30)
			throw new IllegalArgumentException("属性字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值30");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>30)
			throw new IllegalArgumentException("属性字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值30");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>30)
			throw new IllegalArgumentException("属性字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值30");
		Segment3 = aSegment3;
	}
	public String getSegment4()
	{
		return Segment4;
	}
	public void setSegment4(String aSegment4)
	{
		if(aSegment4!=null && aSegment4.length()>30)
			throw new IllegalArgumentException("属性字段4Segment4值"+aSegment4+"的长度"+aSegment4.length()+"大于最大值30");
		Segment4 = aSegment4;
	}
	public String getSegment5()
	{
		return Segment5;
	}
	public void setSegment5(String aSegment5)
	{
		if(aSegment5!=null && aSegment5.length()>30)
			throw new IllegalArgumentException("属性字段5Segment5值"+aSegment5+"的长度"+aSegment5.length()+"大于最大值30");
		Segment5 = aSegment5;
	}
	public String getSegment6()
	{
		return Segment6;
	}
	public void setSegment6(String aSegment6)
	{
		if(aSegment6!=null && aSegment6.length()>30)
			throw new IllegalArgumentException("属性字段6Segment6值"+aSegment6+"的长度"+aSegment6.length()+"大于最大值30");
		Segment6 = aSegment6;
	}
	public String getSegment7()
	{
		return Segment7;
	}
	public void setSegment7(String aSegment7)
	{
		if(aSegment7!=null && aSegment7.length()>30)
			throw new IllegalArgumentException("属性字段7Segment7值"+aSegment7+"的长度"+aSegment7.length()+"大于最大值30");
		Segment7 = aSegment7;
	}
	public String getSegment8()
	{
		return Segment8;
	}
	public void setSegment8(String aSegment8)
	{
		if(aSegment8!=null && aSegment8.length()>30)
			throw new IllegalArgumentException("属性字段8Segment8值"+aSegment8+"的长度"+aSegment8.length()+"大于最大值30");
		Segment8 = aSegment8;
	}
	public String getSegment9()
	{
		return Segment9;
	}
	public void setSegment9(String aSegment9)
	{
		if(aSegment9!=null && aSegment9.length()>30)
			throw new IllegalArgumentException("属性字段9Segment9值"+aSegment9+"的长度"+aSegment9.length()+"大于最大值30");
		Segment9 = aSegment9;
	}
	public String getSegment10()
	{
		return Segment10;
	}
	public void setSegment10(String aSegment10)
	{
		if(aSegment10!=null && aSegment10.length()>30)
			throw new IllegalArgumentException("属性字段10Segment10值"+aSegment10+"的长度"+aSegment10.length()+"大于最大值30");
		Segment10 = aSegment10;
	}
	public String getSegment11()
	{
		return Segment11;
	}
	public void setSegment11(String aSegment11)
	{
		if(aSegment11!=null && aSegment11.length()>30)
			throw new IllegalArgumentException("属性字段11Segment11值"+aSegment11+"的长度"+aSegment11.length()+"大于最大值30");
		Segment11 = aSegment11;
	}
	public String getSegment12()
	{
		return Segment12;
	}
	public void setSegment12(String aSegment12)
	{
		if(aSegment12!=null && aSegment12.length()>30)
			throw new IllegalArgumentException("属性字段12Segment12值"+aSegment12+"的长度"+aSegment12.length()+"大于最大值30");
		Segment12 = aSegment12;
	}
	public String getSegment13()
	{
		return Segment13;
	}
	public void setSegment13(String aSegment13)
	{
		if(aSegment13!=null && aSegment13.length()>100)
			throw new IllegalArgumentException("属性字段13Segment13值"+aSegment13+"的长度"+aSegment13.length()+"大于最大值100");
		Segment13 = aSegment13;
	}
	public String getSegment14()
	{
		return Segment14;
	}
	public void setSegment14(String aSegment14)
	{
		if(aSegment14!=null && aSegment14.length()>30)
			throw new IllegalArgumentException("属性字段14Segment14值"+aSegment14+"的长度"+aSegment14.length()+"大于最大值30");
		Segment14 = aSegment14;
	}
	public String getSegment15()
	{
		return Segment15;
	}
	public void setSegment15(String aSegment15)
	{
		if(aSegment15!=null && aSegment15.length()>100)
			throw new IllegalArgumentException("属性字段15Segment15值"+aSegment15+"的长度"+aSegment15.length()+"大于最大值100");
		Segment15 = aSegment15;
	}
	public String getSegment16()
	{
		return Segment16;
	}
	public void setSegment16(String aSegment16)
	{
		if(aSegment16!=null && aSegment16.length()>30)
			throw new IllegalArgumentException("属性字段16Segment16值"+aSegment16+"的长度"+aSegment16.length()+"大于最大值30");
		Segment16 = aSegment16;
	}
	public String getSegment17()
	{
		return Segment17;
	}
	public void setSegment17(String aSegment17)
	{
		if(aSegment17!=null && aSegment17.length()>30)
			throw new IllegalArgumentException("属性字段17Segment17值"+aSegment17+"的长度"+aSegment17.length()+"大于最大值30");
		Segment17 = aSegment17;
	}
	public String getSegment18()
	{
		return Segment18;
	}
	public void setSegment18(String aSegment18)
	{
		if(aSegment18!=null && aSegment18.length()>30)
			throw new IllegalArgumentException("属性字段18Segment18值"+aSegment18+"的长度"+aSegment18.length()+"大于最大值30");
		Segment18 = aSegment18;
	}
	public String getSegment19()
	{
		return Segment19;
	}
	public void setSegment19(String aSegment19)
	{
		if(aSegment19!=null && aSegment19.length()>30)
			throw new IllegalArgumentException("属性字段19Segment19值"+aSegment19+"的长度"+aSegment19.length()+"大于最大值30");
		Segment19 = aSegment19;
	}
	public String getSegment20()
	{
		return Segment20;
	}
	public void setSegment20(String aSegment20)
	{
		if(aSegment20!=null && aSegment20.length()>30)
			throw new IllegalArgumentException("属性字段20Segment20值"+aSegment20+"的长度"+aSegment20.length()+"大于最大值30");
		Segment20 = aSegment20;
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
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	* 使用另外一个 FinAccItemTraceSchema 对象给 Schema 赋值
	* @param: aFinAccItemTraceSchema FinAccItemTraceSchema
	**/
	public void setSchema(FinAccItemTraceSchema aFinAccItemTraceSchema)
	{
		this.SerialNo = aFinAccItemTraceSchema.getSerialNo();
		this.TransSerialNo = aFinAccItemTraceSchema.getTransSerialNo();
		this.GrpContNo = aFinAccItemTraceSchema.getGrpContNo();
		this.OtherNo = aFinAccItemTraceSchema.getOtherNo();
		this.TransTypeCode = aFinAccItemTraceSchema.getTransTypeCode();
		this.TransDate = fDate.getDate( aFinAccItemTraceSchema.getTransDate());
		this.AccItemCode = aFinAccItemTraceSchema.getAccItemCode();
		this.AccItemCode1 = aFinAccItemTraceSchema.getAccItemCode1();
		this.AccItemCode2 = aFinAccItemTraceSchema.getAccItemCode2();
		this.AccItemCode3 = aFinAccItemTraceSchema.getAccItemCode3();
		this.AccItemCode4 = aFinAccItemTraceSchema.getAccItemCode4();
		this.AccItemCode5 = aFinAccItemTraceSchema.getAccItemCode5();
		this.AccItemCode6 = aFinAccItemTraceSchema.getAccItemCode6();
		this.AccItemName = aFinAccItemTraceSchema.getAccItemName();
		this.FinCode = aFinAccItemTraceSchema.getFinCode();
		this.FinName = aFinAccItemTraceSchema.getFinName();
		this.D_C = aFinAccItemTraceSchema.getD_C();
		this.Amount = aFinAccItemTraceSchema.getAmount();
		this.Segment1 = aFinAccItemTraceSchema.getSegment1();
		this.Segment2 = aFinAccItemTraceSchema.getSegment2();
		this.Segment3 = aFinAccItemTraceSchema.getSegment3();
		this.Segment4 = aFinAccItemTraceSchema.getSegment4();
		this.Segment5 = aFinAccItemTraceSchema.getSegment5();
		this.Segment6 = aFinAccItemTraceSchema.getSegment6();
		this.Segment7 = aFinAccItemTraceSchema.getSegment7();
		this.Segment8 = aFinAccItemTraceSchema.getSegment8();
		this.Segment9 = aFinAccItemTraceSchema.getSegment9();
		this.Segment10 = aFinAccItemTraceSchema.getSegment10();
		this.Segment11 = aFinAccItemTraceSchema.getSegment11();
		this.Segment12 = aFinAccItemTraceSchema.getSegment12();
		this.Segment13 = aFinAccItemTraceSchema.getSegment13();
		this.Segment14 = aFinAccItemTraceSchema.getSegment14();
		this.Segment15 = aFinAccItemTraceSchema.getSegment15();
		this.Segment16 = aFinAccItemTraceSchema.getSegment16();
		this.Segment17 = aFinAccItemTraceSchema.getSegment17();
		this.Segment18 = aFinAccItemTraceSchema.getSegment18();
		this.Segment19 = aFinAccItemTraceSchema.getSegment19();
		this.Segment20 = aFinAccItemTraceSchema.getSegment20();
		this.ManageCom = aFinAccItemTraceSchema.getManageCom();
		this.ComCode = aFinAccItemTraceSchema.getComCode();
		this.MakeOperator = aFinAccItemTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aFinAccItemTraceSchema.getMakeDate());
		this.MakeTime = aFinAccItemTraceSchema.getMakeTime();
		this.ModifyOperator = aFinAccItemTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aFinAccItemTraceSchema.getModifyDate());
		this.ModifyTime = aFinAccItemTraceSchema.getModifyTime();
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

			if( rs.getString("TransSerialNo") == null )
				this.TransSerialNo = null;
			else
				this.TransSerialNo = rs.getString("TransSerialNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("TransTypeCode") == null )
				this.TransTypeCode = null;
			else
				this.TransTypeCode = rs.getString("TransTypeCode").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("AccItemCode") == null )
				this.AccItemCode = null;
			else
				this.AccItemCode = rs.getString("AccItemCode").trim();

			if( rs.getString("AccItemCode1") == null )
				this.AccItemCode1 = null;
			else
				this.AccItemCode1 = rs.getString("AccItemCode1").trim();

			if( rs.getString("AccItemCode2") == null )
				this.AccItemCode2 = null;
			else
				this.AccItemCode2 = rs.getString("AccItemCode2").trim();

			if( rs.getString("AccItemCode3") == null )
				this.AccItemCode3 = null;
			else
				this.AccItemCode3 = rs.getString("AccItemCode3").trim();

			if( rs.getString("AccItemCode4") == null )
				this.AccItemCode4 = null;
			else
				this.AccItemCode4 = rs.getString("AccItemCode4").trim();

			if( rs.getString("AccItemCode5") == null )
				this.AccItemCode5 = null;
			else
				this.AccItemCode5 = rs.getString("AccItemCode5").trim();

			if( rs.getString("AccItemCode6") == null )
				this.AccItemCode6 = null;
			else
				this.AccItemCode6 = rs.getString("AccItemCode6").trim();

			if( rs.getString("AccItemName") == null )
				this.AccItemName = null;
			else
				this.AccItemName = rs.getString("AccItemName").trim();

			if( rs.getString("FinCode") == null )
				this.FinCode = null;
			else
				this.FinCode = rs.getString("FinCode").trim();

			if( rs.getString("FinName") == null )
				this.FinName = null;
			else
				this.FinName = rs.getString("FinName").trim();

			if( rs.getString("D_C") == null )
				this.D_C = null;
			else
				this.D_C = rs.getString("D_C").trim();

			this.Amount = rs.getDouble("Amount");
			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("Segment4") == null )
				this.Segment4 = null;
			else
				this.Segment4 = rs.getString("Segment4").trim();

			if( rs.getString("Segment5") == null )
				this.Segment5 = null;
			else
				this.Segment5 = rs.getString("Segment5").trim();

			if( rs.getString("Segment6") == null )
				this.Segment6 = null;
			else
				this.Segment6 = rs.getString("Segment6").trim();

			if( rs.getString("Segment7") == null )
				this.Segment7 = null;
			else
				this.Segment7 = rs.getString("Segment7").trim();

			if( rs.getString("Segment8") == null )
				this.Segment8 = null;
			else
				this.Segment8 = rs.getString("Segment8").trim();

			if( rs.getString("Segment9") == null )
				this.Segment9 = null;
			else
				this.Segment9 = rs.getString("Segment9").trim();

			if( rs.getString("Segment10") == null )
				this.Segment10 = null;
			else
				this.Segment10 = rs.getString("Segment10").trim();

			if( rs.getString("Segment11") == null )
				this.Segment11 = null;
			else
				this.Segment11 = rs.getString("Segment11").trim();

			if( rs.getString("Segment12") == null )
				this.Segment12 = null;
			else
				this.Segment12 = rs.getString("Segment12").trim();

			if( rs.getString("Segment13") == null )
				this.Segment13 = null;
			else
				this.Segment13 = rs.getString("Segment13").trim();

			if( rs.getString("Segment14") == null )
				this.Segment14 = null;
			else
				this.Segment14 = rs.getString("Segment14").trim();

			if( rs.getString("Segment15") == null )
				this.Segment15 = null;
			else
				this.Segment15 = rs.getString("Segment15").trim();

			if( rs.getString("Segment16") == null )
				this.Segment16 = null;
			else
				this.Segment16 = rs.getString("Segment16").trim();

			if( rs.getString("Segment17") == null )
				this.Segment17 = null;
			else
				this.Segment17 = rs.getString("Segment17").trim();

			if( rs.getString("Segment18") == null )
				this.Segment18 = null;
			else
				this.Segment18 = rs.getString("Segment18").trim();

			if( rs.getString("Segment19") == null )
				this.Segment19 = null;
			else
				this.Segment19 = rs.getString("Segment19").trim();

			if( rs.getString("Segment20") == null )
				this.Segment20 = null;
			else
				this.Segment20 = rs.getString("Segment20").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FinAccItemTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinAccItemTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FinAccItemTraceSchema getSchema()
	{
		FinAccItemTraceSchema aFinAccItemTraceSchema = new FinAccItemTraceSchema();
		aFinAccItemTraceSchema.setSchema(this);
		return aFinAccItemTraceSchema;
	}

	public FinAccItemTraceDB getDB()
	{
		FinAccItemTraceDB aDBOper = new FinAccItemTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinAccItemTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemCode6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItemName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(D_C)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinAccItemTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TransSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TransTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			AccItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AccItemCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccItemCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccItemCode3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccItemCode4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AccItemCode5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccItemCode6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AccItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			FinCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			FinName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			D_C = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Amount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Segment4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Segment5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Segment6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Segment7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Segment8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Segment9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Segment10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Segment11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Segment12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			Segment13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Segment14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			Segment15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Segment16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Segment17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Segment18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			Segment19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			Segment20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinAccItemTraceSchema";
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
		if (FCode.equalsIgnoreCase("TransSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSerialNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("TransTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTypeCode));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("AccItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode));
		}
		if (FCode.equalsIgnoreCase("AccItemCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode1));
		}
		if (FCode.equalsIgnoreCase("AccItemCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode2));
		}
		if (FCode.equalsIgnoreCase("AccItemCode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode3));
		}
		if (FCode.equalsIgnoreCase("AccItemCode4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode4));
		}
		if (FCode.equalsIgnoreCase("AccItemCode5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode5));
		}
		if (FCode.equalsIgnoreCase("AccItemCode6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemCode6));
		}
		if (FCode.equalsIgnoreCase("AccItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItemName));
		}
		if (FCode.equalsIgnoreCase("FinCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinCode));
		}
		if (FCode.equalsIgnoreCase("FinName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinName));
		}
		if (FCode.equalsIgnoreCase("D_C"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(D_C));
		}
		if (FCode.equalsIgnoreCase("Amount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amount));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment4));
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment5));
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment6));
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment7));
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment8));
		}
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment9));
		}
		if (FCode.equalsIgnoreCase("Segment10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment10));
		}
		if (FCode.equalsIgnoreCase("Segment11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment11));
		}
		if (FCode.equalsIgnoreCase("Segment12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment12));
		}
		if (FCode.equalsIgnoreCase("Segment13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment13));
		}
		if (FCode.equalsIgnoreCase("Segment14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment14));
		}
		if (FCode.equalsIgnoreCase("Segment15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment15));
		}
		if (FCode.equalsIgnoreCase("Segment16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment16));
		}
		if (FCode.equalsIgnoreCase("Segment17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment17));
		}
		if (FCode.equalsIgnoreCase("Segment18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment18));
		}
		if (FCode.equalsIgnoreCase("Segment19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment19));
		}
		if (FCode.equalsIgnoreCase("Segment20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment20));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(TransSerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TransTypeCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode1);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode2);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode3);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode4);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode5);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccItemCode6);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AccItemName);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FinCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FinName);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(D_C);
				break;
			case 17:
				strFieldValue = String.valueOf(Amount);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Segment4);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Segment5);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Segment6);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Segment7);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Segment8);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Segment9);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Segment10);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Segment11);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Segment12);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Segment13);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Segment14);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(Segment15);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Segment16);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Segment17);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Segment18);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(Segment19);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Segment20);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
		if (FCode.equalsIgnoreCase("TransSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSerialNo = FValue.trim();
			}
			else
				TransSerialNo = null;
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("TransTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTypeCode = FValue.trim();
			}
			else
				TransTypeCode = null;
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
		if (FCode.equalsIgnoreCase("AccItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode = FValue.trim();
			}
			else
				AccItemCode = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode1 = FValue.trim();
			}
			else
				AccItemCode1 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode2 = FValue.trim();
			}
			else
				AccItemCode2 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode3 = FValue.trim();
			}
			else
				AccItemCode3 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode4 = FValue.trim();
			}
			else
				AccItemCode4 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode5 = FValue.trim();
			}
			else
				AccItemCode5 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemCode6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemCode6 = FValue.trim();
			}
			else
				AccItemCode6 = null;
		}
		if (FCode.equalsIgnoreCase("AccItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItemName = FValue.trim();
			}
			else
				AccItemName = null;
		}
		if (FCode.equalsIgnoreCase("FinCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinCode = FValue.trim();
			}
			else
				FinCode = null;
		}
		if (FCode.equalsIgnoreCase("FinName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinName = FValue.trim();
			}
			else
				FinName = null;
		}
		if (FCode.equalsIgnoreCase("D_C"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				D_C = FValue.trim();
			}
			else
				D_C = null;
		}
		if (FCode.equalsIgnoreCase("Amount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amount = d;
			}
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
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
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment4 = FValue.trim();
			}
			else
				Segment4 = null;
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment5 = FValue.trim();
			}
			else
				Segment5 = null;
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment6 = FValue.trim();
			}
			else
				Segment6 = null;
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
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment9 = FValue.trim();
			}
			else
				Segment9 = null;
		}
		if (FCode.equalsIgnoreCase("Segment10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment10 = FValue.trim();
			}
			else
				Segment10 = null;
		}
		if (FCode.equalsIgnoreCase("Segment11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment11 = FValue.trim();
			}
			else
				Segment11 = null;
		}
		if (FCode.equalsIgnoreCase("Segment12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment12 = FValue.trim();
			}
			else
				Segment12 = null;
		}
		if (FCode.equalsIgnoreCase("Segment13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment13 = FValue.trim();
			}
			else
				Segment13 = null;
		}
		if (FCode.equalsIgnoreCase("Segment14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment14 = FValue.trim();
			}
			else
				Segment14 = null;
		}
		if (FCode.equalsIgnoreCase("Segment15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment15 = FValue.trim();
			}
			else
				Segment15 = null;
		}
		if (FCode.equalsIgnoreCase("Segment16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment16 = FValue.trim();
			}
			else
				Segment16 = null;
		}
		if (FCode.equalsIgnoreCase("Segment17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment17 = FValue.trim();
			}
			else
				Segment17 = null;
		}
		if (FCode.equalsIgnoreCase("Segment18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment18 = FValue.trim();
			}
			else
				Segment18 = null;
		}
		if (FCode.equalsIgnoreCase("Segment19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment19 = FValue.trim();
			}
			else
				Segment19 = null;
		}
		if (FCode.equalsIgnoreCase("Segment20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment20 = FValue.trim();
			}
			else
				Segment20 = null;
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
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FinAccItemTraceSchema other = (FinAccItemTraceSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& TransSerialNo.equals(other.getTransSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& OtherNo.equals(other.getOtherNo())
			&& TransTypeCode.equals(other.getTransTypeCode())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& AccItemCode.equals(other.getAccItemCode())
			&& AccItemCode1.equals(other.getAccItemCode1())
			&& AccItemCode2.equals(other.getAccItemCode2())
			&& AccItemCode3.equals(other.getAccItemCode3())
			&& AccItemCode4.equals(other.getAccItemCode4())
			&& AccItemCode5.equals(other.getAccItemCode5())
			&& AccItemCode6.equals(other.getAccItemCode6())
			&& AccItemName.equals(other.getAccItemName())
			&& FinCode.equals(other.getFinCode())
			&& FinName.equals(other.getFinName())
			&& D_C.equals(other.getD_C())
			&& Amount == other.getAmount()
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& Segment4.equals(other.getSegment4())
			&& Segment5.equals(other.getSegment5())
			&& Segment6.equals(other.getSegment6())
			&& Segment7.equals(other.getSegment7())
			&& Segment8.equals(other.getSegment8())
			&& Segment9.equals(other.getSegment9())
			&& Segment10.equals(other.getSegment10())
			&& Segment11.equals(other.getSegment11())
			&& Segment12.equals(other.getSegment12())
			&& Segment13.equals(other.getSegment13())
			&& Segment14.equals(other.getSegment14())
			&& Segment15.equals(other.getSegment15())
			&& Segment16.equals(other.getSegment16())
			&& Segment17.equals(other.getSegment17())
			&& Segment18.equals(other.getSegment18())
			&& Segment19.equals(other.getSegment19())
			&& Segment20.equals(other.getSegment20())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("TransSerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("TransTypeCode") ) {
			return 4;
		}
		if( strFieldName.equals("TransDate") ) {
			return 5;
		}
		if( strFieldName.equals("AccItemCode") ) {
			return 6;
		}
		if( strFieldName.equals("AccItemCode1") ) {
			return 7;
		}
		if( strFieldName.equals("AccItemCode2") ) {
			return 8;
		}
		if( strFieldName.equals("AccItemCode3") ) {
			return 9;
		}
		if( strFieldName.equals("AccItemCode4") ) {
			return 10;
		}
		if( strFieldName.equals("AccItemCode5") ) {
			return 11;
		}
		if( strFieldName.equals("AccItemCode6") ) {
			return 12;
		}
		if( strFieldName.equals("AccItemName") ) {
			return 13;
		}
		if( strFieldName.equals("FinCode") ) {
			return 14;
		}
		if( strFieldName.equals("FinName") ) {
			return 15;
		}
		if( strFieldName.equals("D_C") ) {
			return 16;
		}
		if( strFieldName.equals("Amount") ) {
			return 17;
		}
		if( strFieldName.equals("Segment1") ) {
			return 18;
		}
		if( strFieldName.equals("Segment2") ) {
			return 19;
		}
		if( strFieldName.equals("Segment3") ) {
			return 20;
		}
		if( strFieldName.equals("Segment4") ) {
			return 21;
		}
		if( strFieldName.equals("Segment5") ) {
			return 22;
		}
		if( strFieldName.equals("Segment6") ) {
			return 23;
		}
		if( strFieldName.equals("Segment7") ) {
			return 24;
		}
		if( strFieldName.equals("Segment8") ) {
			return 25;
		}
		if( strFieldName.equals("Segment9") ) {
			return 26;
		}
		if( strFieldName.equals("Segment10") ) {
			return 27;
		}
		if( strFieldName.equals("Segment11") ) {
			return 28;
		}
		if( strFieldName.equals("Segment12") ) {
			return 29;
		}
		if( strFieldName.equals("Segment13") ) {
			return 30;
		}
		if( strFieldName.equals("Segment14") ) {
			return 31;
		}
		if( strFieldName.equals("Segment15") ) {
			return 32;
		}
		if( strFieldName.equals("Segment16") ) {
			return 33;
		}
		if( strFieldName.equals("Segment17") ) {
			return 34;
		}
		if( strFieldName.equals("Segment18") ) {
			return 35;
		}
		if( strFieldName.equals("Segment19") ) {
			return 36;
		}
		if( strFieldName.equals("Segment20") ) {
			return 37;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 38;
		}
		if( strFieldName.equals("ComCode") ) {
			return 39;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 40;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 41;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 42;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 45;
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
				strFieldName = "TransSerialNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "TransTypeCode";
				break;
			case 5:
				strFieldName = "TransDate";
				break;
			case 6:
				strFieldName = "AccItemCode";
				break;
			case 7:
				strFieldName = "AccItemCode1";
				break;
			case 8:
				strFieldName = "AccItemCode2";
				break;
			case 9:
				strFieldName = "AccItemCode3";
				break;
			case 10:
				strFieldName = "AccItemCode4";
				break;
			case 11:
				strFieldName = "AccItemCode5";
				break;
			case 12:
				strFieldName = "AccItemCode6";
				break;
			case 13:
				strFieldName = "AccItemName";
				break;
			case 14:
				strFieldName = "FinCode";
				break;
			case 15:
				strFieldName = "FinName";
				break;
			case 16:
				strFieldName = "D_C";
				break;
			case 17:
				strFieldName = "Amount";
				break;
			case 18:
				strFieldName = "Segment1";
				break;
			case 19:
				strFieldName = "Segment2";
				break;
			case 20:
				strFieldName = "Segment3";
				break;
			case 21:
				strFieldName = "Segment4";
				break;
			case 22:
				strFieldName = "Segment5";
				break;
			case 23:
				strFieldName = "Segment6";
				break;
			case 24:
				strFieldName = "Segment7";
				break;
			case 25:
				strFieldName = "Segment8";
				break;
			case 26:
				strFieldName = "Segment9";
				break;
			case 27:
				strFieldName = "Segment10";
				break;
			case 28:
				strFieldName = "Segment11";
				break;
			case 29:
				strFieldName = "Segment12";
				break;
			case 30:
				strFieldName = "Segment13";
				break;
			case 31:
				strFieldName = "Segment14";
				break;
			case 32:
				strFieldName = "Segment15";
				break;
			case 33:
				strFieldName = "Segment16";
				break;
			case 34:
				strFieldName = "Segment17";
				break;
			case 35:
				strFieldName = "Segment18";
				break;
			case 36:
				strFieldName = "Segment19";
				break;
			case 37:
				strFieldName = "Segment20";
				break;
			case 38:
				strFieldName = "ManageCom";
				break;
			case 39:
				strFieldName = "ComCode";
				break;
			case 40:
				strFieldName = "MakeOperator";
				break;
			case 41:
				strFieldName = "MakeDate";
				break;
			case 42:
				strFieldName = "MakeTime";
				break;
			case 43:
				strFieldName = "ModifyOperator";
				break;
			case 44:
				strFieldName = "ModifyDate";
				break;
			case 45:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("TransSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AccItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemCode6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("D_C") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
