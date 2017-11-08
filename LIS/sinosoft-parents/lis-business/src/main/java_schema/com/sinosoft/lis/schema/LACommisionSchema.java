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
import com.sinosoft.lis.db.LACommisionDB;

/*
 * <p>ClassName: LACommisionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LACommisionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LACommisionSchema.class);

	// @Field
	/** 系列号 */
	private String CommisionSN;
	/** 佣金计算年月代码 */
	private String WageNo;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 主险保单号码 */
	private String MainPolNo;
	/** 保单号码 */
	private String PolNo;
	/** 管理机构 */
	private String ManageCom;
	/** 投保人客户号码 */
	private String AppntNo;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 责任编码 */
	private String DutyCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 保单生效日期 */
	private Date CValiDate;
	/** 交费间隔 */
	private int PayIntv;
	/** 交费方式 */
	private String PayMode;
	/** 交易号 */
	private String ReceiptNo;
	/** 交易交费日期 */
	private Date TPayDate;
	/** 交易到帐日期 */
	private Date TEnterAccDate;
	/** 交易确认日期 */
	private Date TConfDate;
	/** 交易入机日期 */
	private Date TMakeDate;
	/** 复效日期/还垫日期 */
	private Date CalcDate;
	/** 生成扎账信息日期 */
	private Date CommDate;
	/** 交易金额 */
	private double TransMoney;
	/** 交易标准金额 */
	private double TransStandMoney;
	/** 原交至日期 */
	private Date LastPayToDate;
	/** 现交至日期 */
	private Date CurPayToDate;
	/** 交易类别 */
	private String TransType;
	/** 交易处理状态 */
	private String TransState;
	/** 佣金计算特征 */
	private String CommDire;
	/** 直接佣金 */
	private double DirectWage;
	/** 附加佣金 */
	private double AppendWage;
	/** 交易业务属性1 */
	private String F1;
	/** 交易业务属性2 */
	private String F2;
	/** 交易业务属性3 */
	private String F3;
	/** 交易业务属性4 */
	private String F4;
	/** 交易业务属性5 */
	private String F5;
	/** 业务费用1 */
	private double K1;
	/** 业务费用2 */
	private double K2;
	/** 业务费用3 */
	private double K3;
	/** 业务费用4 */
	private double K4;
	/** 业务费用5 */
	private double K5;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 联合代理人代理人编码 */
	private String AgentCode1;
	/** 联合代理人组别 */
	private String AgentGroup1;
	/** 标志位 */
	private String Flag;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 交费年度 */
	private int PayYear;
	/** 交费年期 */
	private int PayYears;
	/** 保险年期 */
	private int Years;
	/** 第几次交费 */
	private int PayCount;
	/** 签单日期 */
	private Date SignDate;
	/** 保单送达日期 */
	private Date GetPolDate;
	/** 展业类型 */
	private String BranchType;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构内部分类 */
	private String AgentType;
	/** 代理人组别 */
	private String BranchCode;
	/** 统计日期 */
	private Date CalDate;
	/** 统计件数 */
	private double CalCount;
	/** 展业机构外部编码 */
	private String BranchAttr;
	/** 标准提佣比例 */
	private double StandFYCRate;
	/** 实际提佣比例 */
	private double FYCRate;
	/** 实际提佣金额 */
	private double FYC;
	/** 组提佣金额 */
	private double GrpFYC;
	/** 部提佣金额 */
	private double DepFYC;
	/** 折算标保 */
	private double StandPrem;
	/** 标准网点手续费 */
	private double CommCharge;
	/** 标准分理处手续费 */
	private double CommCharge1;
	/** 标准支行手续费 */
	private double CommCharge2;
	/** 标准分行手续费 */
	private double CommCharge3;
	/** 标准总行手续费 */
	private double CommCharge4;
	/** 组提奖比例 */
	private double GrpFYCRate;
	/** 部提奖比例 */
	private double DepFYCRate;
	/** 折标比例 */
	private double StandPremRate;
	/** 保单类型 */
	private String PolType;
	/** P1 */
	private double P1;
	/** P2 */
	private double P2;
	/** P3 */
	private double P3;
	/** P4 */
	private double P4;
	/** 自动垫交标记 */
	private double P5;
	/** 保全标记 */
	private double P6;
	/** P7 */
	private double P7;
	/** P8 */
	private double P8;
	/** P9 */
	private double P9;
	/** P10 */
	private double P10;
	/** 投保人名称 */
	private String P11;
	/** 被保人号码 */
	private String P12;
	/** 被保人姓名 */
	private String P13;
	/** 印刷号 */
	private String P14;
	/** 投保单号 */
	private String P15;
	/** 交单日期 */
	private Date MakePolDate;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 展业机构号码1 */
	private String branchattr1;
	/** 代理人组别1 */
	private String branchcode1;
	/** 主附险标志 */
	private String riskmark;
	/** 扫描日期 */
	private Date scanDate;
	/** 展业机构部编码 */
	private String AgentGroup2;
	/** 展业机构区编码 */
	private String AgentGroup3;
	/** 展业机构督导区编码 */
	private String AgentGroup4;
	/** 保险年龄年期标志 */
	private String InsuYearFlag;
	/** 保险年龄年期 */
	private int InsuYear;
	/** 团单合同号 */
	private String GrpContNo;

	public static final int FIELDNUM = 106;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LACommisionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CommisionSN";

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
		LACommisionSchema cloned = (LACommisionSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCommisionSN()
	{
		return CommisionSN;
	}
	public void setCommisionSN(String aCommisionSN)
	{
		CommisionSN = aCommisionSN;
	}
	public String getWageNo()
	{
		return WageNo;
	}
	public void setWageNo(String aWageNo)
	{
		WageNo = aWageNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getMainPolNo()
	{
		return MainPolNo;
	}
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public int getPayIntv()
	{
		return PayIntv;
	}
	public void setPayIntv(int aPayIntv)
	{
		PayIntv = aPayIntv;
	}
	public void setPayIntv(String aPayIntv)
	{
		if (aPayIntv != null && !aPayIntv.equals(""))
		{
			Integer tInteger = new Integer(aPayIntv);
			int i = tInteger.intValue();
			PayIntv = i;
		}
	}

	/**
	* 1 -- 现金<p>
	* 2 -- 支票<p>
	* 3 -- 代理人信用卡<p>
	* 4 -- 银行代理<p>
	* 5 -- 内部转帐
	*/
	public String getPayMode()
	{
		return PayMode;
	}
	public void setPayMode(String aPayMode)
	{
		PayMode = aPayMode;
	}
	public String getReceiptNo()
	{
		return ReceiptNo;
	}
	public void setReceiptNo(String aReceiptNo)
	{
		ReceiptNo = aReceiptNo;
	}
	public String getTPayDate()
	{
		if( TPayDate != null )
			return fDate.getString(TPayDate);
		else
			return null;
	}
	public void setTPayDate(Date aTPayDate)
	{
		TPayDate = aTPayDate;
	}
	public void setTPayDate(String aTPayDate)
	{
		if (aTPayDate != null && !aTPayDate.equals("") )
		{
			TPayDate = fDate.getDate( aTPayDate );
		}
		else
			TPayDate = null;
	}

	public String getTEnterAccDate()
	{
		if( TEnterAccDate != null )
			return fDate.getString(TEnterAccDate);
		else
			return null;
	}
	public void setTEnterAccDate(Date aTEnterAccDate)
	{
		TEnterAccDate = aTEnterAccDate;
	}
	public void setTEnterAccDate(String aTEnterAccDate)
	{
		if (aTEnterAccDate != null && !aTEnterAccDate.equals("") )
		{
			TEnterAccDate = fDate.getDate( aTEnterAccDate );
		}
		else
			TEnterAccDate = null;
	}

	public String getTConfDate()
	{
		if( TConfDate != null )
			return fDate.getString(TConfDate);
		else
			return null;
	}
	public void setTConfDate(Date aTConfDate)
	{
		TConfDate = aTConfDate;
	}
	public void setTConfDate(String aTConfDate)
	{
		if (aTConfDate != null && !aTConfDate.equals("") )
		{
			TConfDate = fDate.getDate( aTConfDate );
		}
		else
			TConfDate = null;
	}

	public String getTMakeDate()
	{
		if( TMakeDate != null )
			return fDate.getString(TMakeDate);
		else
			return null;
	}
	public void setTMakeDate(Date aTMakeDate)
	{
		TMakeDate = aTMakeDate;
	}
	public void setTMakeDate(String aTMakeDate)
	{
		if (aTMakeDate != null && !aTMakeDate.equals("") )
		{
			TMakeDate = fDate.getDate( aTMakeDate );
		}
		else
			TMakeDate = null;
	}

	public String getCalcDate()
	{
		if( CalcDate != null )
			return fDate.getString(CalcDate);
		else
			return null;
	}
	public void setCalcDate(Date aCalcDate)
	{
		CalcDate = aCalcDate;
	}
	public void setCalcDate(String aCalcDate)
	{
		if (aCalcDate != null && !aCalcDate.equals("") )
		{
			CalcDate = fDate.getDate( aCalcDate );
		}
		else
			CalcDate = null;
	}

	public String getCommDate()
	{
		if( CommDate != null )
			return fDate.getString(CommDate);
		else
			return null;
	}
	public void setCommDate(Date aCommDate)
	{
		CommDate = aCommDate;
	}
	public void setCommDate(String aCommDate)
	{
		if (aCommDate != null && !aCommDate.equals("") )
		{
			CommDate = fDate.getDate( aCommDate );
		}
		else
			CommDate = null;
	}

	public double getTransMoney()
	{
		return TransMoney;
	}
	public void setTransMoney(double aTransMoney)
	{
		TransMoney = aTransMoney;
	}
	public void setTransMoney(String aTransMoney)
	{
		if (aTransMoney != null && !aTransMoney.equals(""))
		{
			Double tDouble = new Double(aTransMoney);
			double d = tDouble.doubleValue();
			TransMoney = d;
		}
	}

	public double getTransStandMoney()
	{
		return TransStandMoney;
	}
	public void setTransStandMoney(double aTransStandMoney)
	{
		TransStandMoney = aTransStandMoney;
	}
	public void setTransStandMoney(String aTransStandMoney)
	{
		if (aTransStandMoney != null && !aTransStandMoney.equals(""))
		{
			Double tDouble = new Double(aTransStandMoney);
			double d = tDouble.doubleValue();
			TransStandMoney = d;
		}
	}

	public String getLastPayToDate()
	{
		if( LastPayToDate != null )
			return fDate.getString(LastPayToDate);
		else
			return null;
	}
	public void setLastPayToDate(Date aLastPayToDate)
	{
		LastPayToDate = aLastPayToDate;
	}
	public void setLastPayToDate(String aLastPayToDate)
	{
		if (aLastPayToDate != null && !aLastPayToDate.equals("") )
		{
			LastPayToDate = fDate.getDate( aLastPayToDate );
		}
		else
			LastPayToDate = null;
	}

	public String getCurPayToDate()
	{
		if( CurPayToDate != null )
			return fDate.getString(CurPayToDate);
		else
			return null;
	}
	public void setCurPayToDate(Date aCurPayToDate)
	{
		CurPayToDate = aCurPayToDate;
	}
	public void setCurPayToDate(String aCurPayToDate)
	{
		if (aCurPayToDate != null && !aCurPayToDate.equals("") )
		{
			CurPayToDate = fDate.getDate( aCurPayToDate );
		}
		else
			CurPayToDate = null;
	}

	public String getTransType()
	{
		return TransType;
	}
	public void setTransType(String aTransType)
	{
		TransType = aTransType;
	}
	/**
	* 00:null  正常<p>
	* 01-特殊业务<p>
	* 02-回单日期错误<p>
	* 03-销售渠道错误
	*/
	public String getTransState()
	{
		return TransState;
	}
	public void setTransState(String aTransState)
	{
		TransState = aTransState;
	}
	/**
	* 1 ： 本次应发（参与本月汇总）<p>
	* 2 ： 已退费标记（不参与本月汇总）
	*/
	public String getCommDire()
	{
		return CommDire;
	}
	public void setCommDire(String aCommDire)
	{
		CommDire = aCommDire;
	}
	public double getDirectWage()
	{
		return DirectWage;
	}
	public void setDirectWage(double aDirectWage)
	{
		DirectWage = aDirectWage;
	}
	public void setDirectWage(String aDirectWage)
	{
		if (aDirectWage != null && !aDirectWage.equals(""))
		{
			Double tDouble = new Double(aDirectWage);
			double d = tDouble.doubleValue();
			DirectWage = d;
		}
	}

	public double getAppendWage()
	{
		return AppendWage;
	}
	public void setAppendWage(double aAppendWage)
	{
		AppendWage = aAppendWage;
	}
	public void setAppendWage(String aAppendWage)
	{
		if (aAppendWage != null && !aAppendWage.equals(""))
		{
			Double tDouble = new Double(aAppendWage);
			double d = tDouble.doubleValue();
			AppendWage = d;
		}
	}

	public String getF1()
	{
		return F1;
	}
	public void setF1(String aF1)
	{
		F1 = aF1;
	}
	public String getF2()
	{
		return F2;
	}
	public void setF2(String aF2)
	{
		F2 = aF2;
	}
	public String getF3()
	{
		return F3;
	}
	public void setF3(String aF3)
	{
		F3 = aF3;
	}
	public String getF4()
	{
		return F4;
	}
	public void setF4(String aF4)
	{
		F4 = aF4;
	}
	/**
	* 优惠业务标志:
	*/
	public String getF5()
	{
		return F5;
	}
	public void setF5(String aF5)
	{
		F5 = aF5;
	}
	public double getK1()
	{
		return K1;
	}
	public void setK1(double aK1)
	{
		K1 = aK1;
	}
	public void setK1(String aK1)
	{
		if (aK1 != null && !aK1.equals(""))
		{
			Double tDouble = new Double(aK1);
			double d = tDouble.doubleValue();
			K1 = d;
		}
	}

	public double getK2()
	{
		return K2;
	}
	public void setK2(double aK2)
	{
		K2 = aK2;
	}
	public void setK2(String aK2)
	{
		if (aK2 != null && !aK2.equals(""))
		{
			Double tDouble = new Double(aK2);
			double d = tDouble.doubleValue();
			K2 = d;
		}
	}

	public double getK3()
	{
		return K3;
	}
	public void setK3(double aK3)
	{
		K3 = aK3;
	}
	public void setK3(String aK3)
	{
		if (aK3 != null && !aK3.equals(""))
		{
			Double tDouble = new Double(aK3);
			double d = tDouble.doubleValue();
			K3 = d;
		}
	}

	public double getK4()
	{
		return K4;
	}
	public void setK4(double aK4)
	{
		K4 = aK4;
	}
	public void setK4(String aK4)
	{
		if (aK4 != null && !aK4.equals(""))
		{
			Double tDouble = new Double(aK4);
			double d = tDouble.doubleValue();
			K4 = d;
		}
	}

	public double getK5()
	{
		return K5;
	}
	public void setK5(double aK5)
	{
		K5 = aK5;
	}
	public void setK5(String aK5)
	{
		if (aK5 != null && !aK5.equals(""))
		{
			Double tDouble = new Double(aK5);
			double d = tDouble.doubleValue();
			K5 = d;
		}
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		AgentCode1 = aAgentCode1;
	}
	public String getAgentGroup1()
	{
		return AgentGroup1;
	}
	public void setAgentGroup1(String aAgentGroup1)
	{
		AgentGroup1 = aAgentGroup1;
	}
	/**
	* 0-正常<p>
	* 1-犹豫期撤单件<p>
	* 2-理赔件
	*/
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
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
	/**
	* 0-首年度<p>
	* <p>
	* 1-第一年度<p>
	* ...
	*/
	public int getPayYear()
	{
		return PayYear;
	}
	public void setPayYear(int aPayYear)
	{
		PayYear = aPayYear;
	}
	public void setPayYear(String aPayYear)
	{
		if (aPayYear != null && !aPayYear.equals(""))
		{
			Integer tInteger = new Integer(aPayYear);
			int i = tInteger.intValue();
			PayYear = i;
		}
	}

	public int getPayYears()
	{
		return PayYears;
	}
	public void setPayYears(int aPayYears)
	{
		PayYears = aPayYears;
	}
	public void setPayYears(String aPayYears)
	{
		if (aPayYears != null && !aPayYears.equals(""))
		{
			Integer tInteger = new Integer(aPayYears);
			int i = tInteger.intValue();
			PayYears = i;
		}
	}

	public int getYears()
	{
		return Years;
	}
	public void setYears(int aYears)
	{
		Years = aYears;
	}
	public void setYears(String aYears)
	{
		if (aYears != null && !aYears.equals(""))
		{
			Integer tInteger = new Integer(aYears);
			int i = tInteger.intValue();
			Years = i;
		}
	}

	/**
	* 1-首次交费<p>
	* 2-第二次交费
	*/
	public int getPayCount()
	{
		return PayCount;
	}
	public void setPayCount(int aPayCount)
	{
		PayCount = aPayCount;
	}
	public void setPayCount(String aPayCount)
	{
		if (aPayCount != null && !aPayCount.equals(""))
		{
			Integer tInteger = new Integer(aPayCount);
			int i = tInteger.intValue();
			PayCount = i;
		}
	}

	public String getSignDate()
	{
		if( SignDate != null )
			return fDate.getString(SignDate);
		else
			return null;
	}
	public void setSignDate(Date aSignDate)
	{
		SignDate = aSignDate;
	}
	public void setSignDate(String aSignDate)
	{
		if (aSignDate != null && !aSignDate.equals("") )
		{
			SignDate = fDate.getDate( aSignDate );
		}
		else
			SignDate = null;
	}

	public String getGetPolDate()
	{
		if( GetPolDate != null )
			return fDate.getString(GetPolDate);
		else
			return null;
	}
	public void setGetPolDate(Date aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	public void setGetPolDate(String aGetPolDate)
	{
		if (aGetPolDate != null && !aGetPolDate.equals("") )
		{
			GetPolDate = fDate.getDate( aGetPolDate );
		}
		else
			GetPolDate = null;
	}

	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	/**
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/**
	* 对不同的代理机构号进行内部的分类。
	*/
	public String getAgentType()
	{
		return AgentType;
	}
	public void setAgentType(String aAgentType)
	{
		AgentType = aAgentType;
	}
	public String getBranchCode()
	{
		return BranchCode;
	}
	public void setBranchCode(String aBranchCode)
	{
		BranchCode = aBranchCode;
	}
	public String getCalDate()
	{
		if( CalDate != null )
			return fDate.getString(CalDate);
		else
			return null;
	}
	public void setCalDate(Date aCalDate)
	{
		CalDate = aCalDate;
	}
	public void setCalDate(String aCalDate)
	{
		if (aCalDate != null && !aCalDate.equals("") )
		{
			CalDate = fDate.getDate( aCalDate );
		}
		else
			CalDate = null;
	}

	public double getCalCount()
	{
		return CalCount;
	}
	public void setCalCount(double aCalCount)
	{
		CalCount = aCalCount;
	}
	public void setCalCount(String aCalCount)
	{
		if (aCalCount != null && !aCalCount.equals(""))
		{
			Double tDouble = new Double(aCalCount);
			double d = tDouble.doubleValue();
			CalCount = d;
		}
	}

	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}
	public double getStandFYCRate()
	{
		return StandFYCRate;
	}
	public void setStandFYCRate(double aStandFYCRate)
	{
		StandFYCRate = aStandFYCRate;
	}
	public void setStandFYCRate(String aStandFYCRate)
	{
		if (aStandFYCRate != null && !aStandFYCRate.equals(""))
		{
			Double tDouble = new Double(aStandFYCRate);
			double d = tDouble.doubleValue();
			StandFYCRate = d;
		}
	}

	public double getFYCRate()
	{
		return FYCRate;
	}
	public void setFYCRate(double aFYCRate)
	{
		FYCRate = aFYCRate;
	}
	public void setFYCRate(String aFYCRate)
	{
		if (aFYCRate != null && !aFYCRate.equals(""))
		{
			Double tDouble = new Double(aFYCRate);
			double d = tDouble.doubleValue();
			FYCRate = d;
		}
	}

	public double getFYC()
	{
		return FYC;
	}
	public void setFYC(double aFYC)
	{
		FYC = aFYC;
	}
	public void setFYC(String aFYC)
	{
		if (aFYC != null && !aFYC.equals(""))
		{
			Double tDouble = new Double(aFYC);
			double d = tDouble.doubleValue();
			FYC = d;
		}
	}

	public double getGrpFYC()
	{
		return GrpFYC;
	}
	public void setGrpFYC(double aGrpFYC)
	{
		GrpFYC = aGrpFYC;
	}
	public void setGrpFYC(String aGrpFYC)
	{
		if (aGrpFYC != null && !aGrpFYC.equals(""))
		{
			Double tDouble = new Double(aGrpFYC);
			double d = tDouble.doubleValue();
			GrpFYC = d;
		}
	}

	/**
	* --新版续期用到新契约业绩
	*/
	public double getDepFYC()
	{
		return DepFYC;
	}
	public void setDepFYC(double aDepFYC)
	{
		DepFYC = aDepFYC;
	}
	public void setDepFYC(String aDepFYC)
	{
		if (aDepFYC != null && !aDepFYC.equals(""))
		{
			Double tDouble = new Double(aDepFYC);
			double d = tDouble.doubleValue();
			DepFYC = d;
		}
	}

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public double getCommCharge()
	{
		return CommCharge;
	}
	public void setCommCharge(double aCommCharge)
	{
		CommCharge = aCommCharge;
	}
	public void setCommCharge(String aCommCharge)
	{
		if (aCommCharge != null && !aCommCharge.equals(""))
		{
			Double tDouble = new Double(aCommCharge);
			double d = tDouble.doubleValue();
			CommCharge = d;
		}
	}

	public double getCommCharge1()
	{
		return CommCharge1;
	}
	public void setCommCharge1(double aCommCharge1)
	{
		CommCharge1 = aCommCharge1;
	}
	public void setCommCharge1(String aCommCharge1)
	{
		if (aCommCharge1 != null && !aCommCharge1.equals(""))
		{
			Double tDouble = new Double(aCommCharge1);
			double d = tDouble.doubleValue();
			CommCharge1 = d;
		}
	}

	public double getCommCharge2()
	{
		return CommCharge2;
	}
	public void setCommCharge2(double aCommCharge2)
	{
		CommCharge2 = aCommCharge2;
	}
	public void setCommCharge2(String aCommCharge2)
	{
		if (aCommCharge2 != null && !aCommCharge2.equals(""))
		{
			Double tDouble = new Double(aCommCharge2);
			double d = tDouble.doubleValue();
			CommCharge2 = d;
		}
	}

	public double getCommCharge3()
	{
		return CommCharge3;
	}
	public void setCommCharge3(double aCommCharge3)
	{
		CommCharge3 = aCommCharge3;
	}
	public void setCommCharge3(String aCommCharge3)
	{
		if (aCommCharge3 != null && !aCommCharge3.equals(""))
		{
			Double tDouble = new Double(aCommCharge3);
			double d = tDouble.doubleValue();
			CommCharge3 = d;
		}
	}

	public double getCommCharge4()
	{
		return CommCharge4;
	}
	public void setCommCharge4(double aCommCharge4)
	{
		CommCharge4 = aCommCharge4;
	}
	public void setCommCharge4(String aCommCharge4)
	{
		if (aCommCharge4 != null && !aCommCharge4.equals(""))
		{
			Double tDouble = new Double(aCommCharge4);
			double d = tDouble.doubleValue();
			CommCharge4 = d;
		}
	}

	public double getGrpFYCRate()
	{
		return GrpFYCRate;
	}
	public void setGrpFYCRate(double aGrpFYCRate)
	{
		GrpFYCRate = aGrpFYCRate;
	}
	public void setGrpFYCRate(String aGrpFYCRate)
	{
		if (aGrpFYCRate != null && !aGrpFYCRate.equals(""))
		{
			Double tDouble = new Double(aGrpFYCRate);
			double d = tDouble.doubleValue();
			GrpFYCRate = d;
		}
	}

	public double getDepFYCRate()
	{
		return DepFYCRate;
	}
	public void setDepFYCRate(double aDepFYCRate)
	{
		DepFYCRate = aDepFYCRate;
	}
	public void setDepFYCRate(String aDepFYCRate)
	{
		if (aDepFYCRate != null && !aDepFYCRate.equals(""))
		{
			Double tDouble = new Double(aDepFYCRate);
			double d = tDouble.doubleValue();
			DepFYCRate = d;
		}
	}

	public double getStandPremRate()
	{
		return StandPremRate;
	}
	public void setStandPremRate(double aStandPremRate)
	{
		StandPremRate = aStandPremRate;
	}
	public void setStandPremRate(String aStandPremRate)
	{
		if (aStandPremRate != null && !aStandPremRate.equals(""))
		{
			Double tDouble = new Double(aStandPremRate);
			double d = tDouble.doubleValue();
			StandPremRate = d;
		}
	}

	/**
	* 保单类型<p>
	* 0-优惠<p>
	* 1-正常
	*/
	public String getPolType()
	{
		return PolType;
	}
	public void setPolType(String aPolType)
	{
		PolType = aPolType;
	}
	/**
	* 管理费比例
	*/
	public double getP1()
	{
		return P1;
	}
	public void setP1(double aP1)
	{
		P1 = aP1;
	}
	public void setP1(String aP1)
	{
		if (aP1 != null && !aP1.equals(""))
		{
			Double tDouble = new Double(aP1);
			double d = tDouble.doubleValue();
			P1 = d;
		}
	}

	/**
	* 分红比例
	*/
	public double getP2()
	{
		return P2;
	}
	public void setP2(double aP2)
	{
		P2 = aP2;
	}
	public void setP2(String aP2)
	{
		if (aP2 != null && !aP2.equals(""))
		{
			Double tDouble = new Double(aP2);
			double d = tDouble.doubleValue();
			P2 = d;
		}
	}

	/**
	* 被保人的投保年龄
	*/
	public double getP3()
	{
		return P3;
	}
	public void setP3(double aP3)
	{
		P3 = aP3;
	}
	public void setP3(String aP3)
	{
		if (aP3 != null && !aP3.equals(""))
		{
			Double tDouble = new Double(aP3);
			double d = tDouble.doubleValue();
			P3 = d;
		}
	}

	/**
	* NAFYC每月增量
	*/
	public double getP4()
	{
		return P4;
	}
	public void setP4(double aP4)
	{
		P4 = aP4;
	}
	public void setP4(String aP4)
	{
		if (aP4 != null && !aP4.equals(""))
		{
			Double tDouble = new Double(aP4);
			double d = tDouble.doubleValue();
			P4 = d;
		}
	}

	/**
	* null或者0表示非自动垫交标记<p>
	* <p>
	* 1垫交保单
	*/
	public double getP5()
	{
		return P5;
	}
	public void setP5(double aP5)
	{
		P5 = aP5;
	}
	public void setP5(String aP5)
	{
		if (aP5 != null && !aP5.equals(""))
		{
			Double tDouble = new Double(aP5);
			double d = tDouble.doubleValue();
			P5 = d;
		}
	}

	/**
	* 保全标记<p>
	* null或者0为正常<p>
	* <p>
	* 1为保全加费
	*/
	public double getP6()
	{
		return P6;
	}
	public void setP6(double aP6)
	{
		P6 = aP6;
	}
	public void setP6(String aP6)
	{
		if (aP6 != null && !aP6.equals(""))
		{
			Double tDouble = new Double(aP6);
			double d = tDouble.doubleValue();
			P6 = d;
		}
	}

	public double getP7()
	{
		return P7;
	}
	public void setP7(double aP7)
	{
		P7 = aP7;
	}
	public void setP7(String aP7)
	{
		if (aP7 != null && !aP7.equals(""))
		{
			Double tDouble = new Double(aP7);
			double d = tDouble.doubleValue();
			P7 = d;
		}
	}

	public double getP8()
	{
		return P8;
	}
	public void setP8(double aP8)
	{
		P8 = aP8;
	}
	public void setP8(String aP8)
	{
		if (aP8 != null && !aP8.equals(""))
		{
			Double tDouble = new Double(aP8);
			double d = tDouble.doubleValue();
			P8 = d;
		}
	}

	public double getP9()
	{
		return P9;
	}
	public void setP9(double aP9)
	{
		P9 = aP9;
	}
	public void setP9(String aP9)
	{
		if (aP9 != null && !aP9.equals(""))
		{
			Double tDouble = new Double(aP9);
			double d = tDouble.doubleValue();
			P9 = d;
		}
	}

	public double getP10()
	{
		return P10;
	}
	public void setP10(double aP10)
	{
		P10 = aP10;
	}
	public void setP10(String aP10)
	{
		if (aP10 != null && !aP10.equals(""))
		{
			Double tDouble = new Double(aP10);
			double d = tDouble.doubleValue();
			P10 = d;
		}
	}

	/**
	* 投保人姓名
	*/
	public String getP11()
	{
		return P11;
	}
	public void setP11(String aP11)
	{
		P11 = aP11;
	}
	/**
	* 被保人代码
	*/
	public String getP12()
	{
		return P12;
	}
	public void setP12(String aP12)
	{
		P12 = aP12;
	}
	/**
	* 被保人代码
	*/
	public String getP13()
	{
		return P13;
	}
	public void setP13(String aP13)
	{
		P13 = aP13;
	}
	public String getP14()
	{
		return P14;
	}
	public void setP14(String aP14)
	{
		P14 = aP14;
	}
	public String getP15()
	{
		return P15;
	}
	public void setP15(String aP15)
	{
		P15 = aP15;
	}
	public String getMakePolDate()
	{
		if( MakePolDate != null )
			return fDate.getString(MakePolDate);
		else
			return null;
	}
	public void setMakePolDate(Date aMakePolDate)
	{
		MakePolDate = aMakePolDate;
	}
	public void setMakePolDate(String aMakePolDate)
	{
		if (aMakePolDate != null && !aMakePolDate.equals("") )
		{
			MakePolDate = fDate.getDate( aMakePolDate );
		}
		else
			MakePolDate = null;
	}

	public String getCustomGetPolDate()
	{
		if( CustomGetPolDate != null )
			return fDate.getString(CustomGetPolDate);
		else
			return null;
	}
	public void setCustomGetPolDate(Date aCustomGetPolDate)
	{
		CustomGetPolDate = aCustomGetPolDate;
	}
	public void setCustomGetPolDate(String aCustomGetPolDate)
	{
		if (aCustomGetPolDate != null && !aCustomGetPolDate.equals("") )
		{
			CustomGetPolDate = fDate.getDate( aCustomGetPolDate );
		}
		else
			CustomGetPolDate = null;
	}

	public String getbranchattr1()
	{
		return branchattr1;
	}
	public void setbranchattr1(String abranchattr1)
	{
		branchattr1 = abranchattr1;
	}
	public String getbranchcode1()
	{
		return branchcode1;
	}
	public void setbranchcode1(String abranchcode1)
	{
		branchcode1 = abranchcode1;
	}
	/**
	* Y:主险<p>
	* N:附险<p>
	* <p>
	* <p>
	* --个险借用了这个字段，表客户服务奖发给谁<p>
	* <p>
	* 0-原代理人<p>
	* 1-继承代理人<p>
	* 2-两者都不是
	*/
	public String getriskmark()
	{
		return riskmark;
	}
	public void setriskmark(String ariskmark)
	{
		riskmark = ariskmark;
	}
	public String getscanDate()
	{
		if( scanDate != null )
			return fDate.getString(scanDate);
		else
			return null;
	}
	public void setscanDate(Date ascanDate)
	{
		scanDate = ascanDate;
	}
	public void setscanDate(String ascanDate)
	{
		if (ascanDate != null && !ascanDate.equals("") )
		{
			scanDate = fDate.getDate( ascanDate );
		}
		else
			scanDate = null;
	}

	public String getAgentGroup2()
	{
		return AgentGroup2;
	}
	public void setAgentGroup2(String aAgentGroup2)
	{
		AgentGroup2 = aAgentGroup2;
	}
	public String getAgentGroup3()
	{
		return AgentGroup3;
	}
	public void setAgentGroup3(String aAgentGroup3)
	{
		AgentGroup3 = aAgentGroup3;
	}
	public String getAgentGroup4()
	{
		return AgentGroup4;
	}
	public void setAgentGroup4(String aAgentGroup4)
	{
		AgentGroup4 = aAgentGroup4;
	}
	/**
	* Y －－ 年<p>
	* M －－ 月<p>
	* <p>
	* D －－ 日<p>
	* <p>
	* A －－ 岁
	*/
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public int getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}

	/**
	* 使用另外一个 LACommisionSchema 对象给 Schema 赋值
	* @param: aLACommisionSchema LACommisionSchema
	**/
	public void setSchema(LACommisionSchema aLACommisionSchema)
	{
		this.CommisionSN = aLACommisionSchema.getCommisionSN();
		this.WageNo = aLACommisionSchema.getWageNo();
		this.ContNo = aLACommisionSchema.getContNo();
		this.GrpPolNo = aLACommisionSchema.getGrpPolNo();
		this.MainPolNo = aLACommisionSchema.getMainPolNo();
		this.PolNo = aLACommisionSchema.getPolNo();
		this.ManageCom = aLACommisionSchema.getManageCom();
		this.AppntNo = aLACommisionSchema.getAppntNo();
		this.RiskCode = aLACommisionSchema.getRiskCode();
		this.RiskVersion = aLACommisionSchema.getRiskVersion();
		this.DutyCode = aLACommisionSchema.getDutyCode();
		this.PayPlanCode = aLACommisionSchema.getPayPlanCode();
		this.CValiDate = fDate.getDate( aLACommisionSchema.getCValiDate());
		this.PayIntv = aLACommisionSchema.getPayIntv();
		this.PayMode = aLACommisionSchema.getPayMode();
		this.ReceiptNo = aLACommisionSchema.getReceiptNo();
		this.TPayDate = fDate.getDate( aLACommisionSchema.getTPayDate());
		this.TEnterAccDate = fDate.getDate( aLACommisionSchema.getTEnterAccDate());
		this.TConfDate = fDate.getDate( aLACommisionSchema.getTConfDate());
		this.TMakeDate = fDate.getDate( aLACommisionSchema.getTMakeDate());
		this.CalcDate = fDate.getDate( aLACommisionSchema.getCalcDate());
		this.CommDate = fDate.getDate( aLACommisionSchema.getCommDate());
		this.TransMoney = aLACommisionSchema.getTransMoney();
		this.TransStandMoney = aLACommisionSchema.getTransStandMoney();
		this.LastPayToDate = fDate.getDate( aLACommisionSchema.getLastPayToDate());
		this.CurPayToDate = fDate.getDate( aLACommisionSchema.getCurPayToDate());
		this.TransType = aLACommisionSchema.getTransType();
		this.TransState = aLACommisionSchema.getTransState();
		this.CommDire = aLACommisionSchema.getCommDire();
		this.DirectWage = aLACommisionSchema.getDirectWage();
		this.AppendWage = aLACommisionSchema.getAppendWage();
		this.F1 = aLACommisionSchema.getF1();
		this.F2 = aLACommisionSchema.getF2();
		this.F3 = aLACommisionSchema.getF3();
		this.F4 = aLACommisionSchema.getF4();
		this.F5 = aLACommisionSchema.getF5();
		this.K1 = aLACommisionSchema.getK1();
		this.K2 = aLACommisionSchema.getK2();
		this.K3 = aLACommisionSchema.getK3();
		this.K4 = aLACommisionSchema.getK4();
		this.K5 = aLACommisionSchema.getK5();
		this.AgentCode = aLACommisionSchema.getAgentCode();
		this.AgentGroup = aLACommisionSchema.getAgentGroup();
		this.AgentCode1 = aLACommisionSchema.getAgentCode1();
		this.AgentGroup1 = aLACommisionSchema.getAgentGroup1();
		this.Flag = aLACommisionSchema.getFlag();
		this.Operator = aLACommisionSchema.getOperator();
		this.MakeDate = fDate.getDate( aLACommisionSchema.getMakeDate());
		this.MakeTime = aLACommisionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLACommisionSchema.getModifyDate());
		this.ModifyTime = aLACommisionSchema.getModifyTime();
		this.PayYear = aLACommisionSchema.getPayYear();
		this.PayYears = aLACommisionSchema.getPayYears();
		this.Years = aLACommisionSchema.getYears();
		this.PayCount = aLACommisionSchema.getPayCount();
		this.SignDate = fDate.getDate( aLACommisionSchema.getSignDate());
		this.GetPolDate = fDate.getDate( aLACommisionSchema.getGetPolDate());
		this.BranchType = aLACommisionSchema.getBranchType();
		this.AgentCom = aLACommisionSchema.getAgentCom();
		this.AgentType = aLACommisionSchema.getAgentType();
		this.BranchCode = aLACommisionSchema.getBranchCode();
		this.CalDate = fDate.getDate( aLACommisionSchema.getCalDate());
		this.CalCount = aLACommisionSchema.getCalCount();
		this.BranchAttr = aLACommisionSchema.getBranchAttr();
		this.StandFYCRate = aLACommisionSchema.getStandFYCRate();
		this.FYCRate = aLACommisionSchema.getFYCRate();
		this.FYC = aLACommisionSchema.getFYC();
		this.GrpFYC = aLACommisionSchema.getGrpFYC();
		this.DepFYC = aLACommisionSchema.getDepFYC();
		this.StandPrem = aLACommisionSchema.getStandPrem();
		this.CommCharge = aLACommisionSchema.getCommCharge();
		this.CommCharge1 = aLACommisionSchema.getCommCharge1();
		this.CommCharge2 = aLACommisionSchema.getCommCharge2();
		this.CommCharge3 = aLACommisionSchema.getCommCharge3();
		this.CommCharge4 = aLACommisionSchema.getCommCharge4();
		this.GrpFYCRate = aLACommisionSchema.getGrpFYCRate();
		this.DepFYCRate = aLACommisionSchema.getDepFYCRate();
		this.StandPremRate = aLACommisionSchema.getStandPremRate();
		this.PolType = aLACommisionSchema.getPolType();
		this.P1 = aLACommisionSchema.getP1();
		this.P2 = aLACommisionSchema.getP2();
		this.P3 = aLACommisionSchema.getP3();
		this.P4 = aLACommisionSchema.getP4();
		this.P5 = aLACommisionSchema.getP5();
		this.P6 = aLACommisionSchema.getP6();
		this.P7 = aLACommisionSchema.getP7();
		this.P8 = aLACommisionSchema.getP8();
		this.P9 = aLACommisionSchema.getP9();
		this.P10 = aLACommisionSchema.getP10();
		this.P11 = aLACommisionSchema.getP11();
		this.P12 = aLACommisionSchema.getP12();
		this.P13 = aLACommisionSchema.getP13();
		this.P14 = aLACommisionSchema.getP14();
		this.P15 = aLACommisionSchema.getP15();
		this.MakePolDate = fDate.getDate( aLACommisionSchema.getMakePolDate());
		this.CustomGetPolDate = fDate.getDate( aLACommisionSchema.getCustomGetPolDate());
		this.branchattr1 = aLACommisionSchema.getbranchattr1();
		this.branchcode1 = aLACommisionSchema.getbranchcode1();
		this.riskmark = aLACommisionSchema.getriskmark();
		this.scanDate = fDate.getDate( aLACommisionSchema.getscanDate());
		this.AgentGroup2 = aLACommisionSchema.getAgentGroup2();
		this.AgentGroup3 = aLACommisionSchema.getAgentGroup3();
		this.AgentGroup4 = aLACommisionSchema.getAgentGroup4();
		this.InsuYearFlag = aLACommisionSchema.getInsuYearFlag();
		this.InsuYear = aLACommisionSchema.getInsuYear();
		this.GrpContNo = aLACommisionSchema.getGrpContNo();
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
			if( rs.getString("CommisionSN") == null )
				this.CommisionSN = null;
			else
				this.CommisionSN = rs.getString("CommisionSN").trim();

			if( rs.getString("WageNo") == null )
				this.WageNo = null;
			else
				this.WageNo = rs.getString("WageNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("PayMode") == null )
				this.PayMode = null;
			else
				this.PayMode = rs.getString("PayMode").trim();

			if( rs.getString("ReceiptNo") == null )
				this.ReceiptNo = null;
			else
				this.ReceiptNo = rs.getString("ReceiptNo").trim();

			this.TPayDate = rs.getDate("TPayDate");
			this.TEnterAccDate = rs.getDate("TEnterAccDate");
			this.TConfDate = rs.getDate("TConfDate");
			this.TMakeDate = rs.getDate("TMakeDate");
			this.CalcDate = rs.getDate("CalcDate");
			this.CommDate = rs.getDate("CommDate");
			this.TransMoney = rs.getDouble("TransMoney");
			this.TransStandMoney = rs.getDouble("TransStandMoney");
			this.LastPayToDate = rs.getDate("LastPayToDate");
			this.CurPayToDate = rs.getDate("CurPayToDate");
			if( rs.getString("TransType") == null )
				this.TransType = null;
			else
				this.TransType = rs.getString("TransType").trim();

			if( rs.getString("TransState") == null )
				this.TransState = null;
			else
				this.TransState = rs.getString("TransState").trim();

			if( rs.getString("CommDire") == null )
				this.CommDire = null;
			else
				this.CommDire = rs.getString("CommDire").trim();

			this.DirectWage = rs.getDouble("DirectWage");
			this.AppendWage = rs.getDouble("AppendWage");
			if( rs.getString("F1") == null )
				this.F1 = null;
			else
				this.F1 = rs.getString("F1").trim();

			if( rs.getString("F2") == null )
				this.F2 = null;
			else
				this.F2 = rs.getString("F2").trim();

			if( rs.getString("F3") == null )
				this.F3 = null;
			else
				this.F3 = rs.getString("F3").trim();

			if( rs.getString("F4") == null )
				this.F4 = null;
			else
				this.F4 = rs.getString("F4").trim();

			if( rs.getString("F5") == null )
				this.F5 = null;
			else
				this.F5 = rs.getString("F5").trim();

			this.K1 = rs.getDouble("K1");
			this.K2 = rs.getDouble("K2");
			this.K3 = rs.getDouble("K3");
			this.K4 = rs.getDouble("K4");
			this.K5 = rs.getDouble("K5");
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

			if( rs.getString("AgentGroup1") == null )
				this.AgentGroup1 = null;
			else
				this.AgentGroup1 = rs.getString("AgentGroup1").trim();

			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

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

			this.PayYear = rs.getInt("PayYear");
			this.PayYears = rs.getInt("PayYears");
			this.Years = rs.getInt("Years");
			this.PayCount = rs.getInt("PayCount");
			this.SignDate = rs.getDate("SignDate");
			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentType") == null )
				this.AgentType = null;
			else
				this.AgentType = rs.getString("AgentType").trim();

			if( rs.getString("BranchCode") == null )
				this.BranchCode = null;
			else
				this.BranchCode = rs.getString("BranchCode").trim();

			this.CalDate = rs.getDate("CalDate");
			this.CalCount = rs.getDouble("CalCount");
			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

			this.StandFYCRate = rs.getDouble("StandFYCRate");
			this.FYCRate = rs.getDouble("FYCRate");
			this.FYC = rs.getDouble("FYC");
			this.GrpFYC = rs.getDouble("GrpFYC");
			this.DepFYC = rs.getDouble("DepFYC");
			this.StandPrem = rs.getDouble("StandPrem");
			this.CommCharge = rs.getDouble("CommCharge");
			this.CommCharge1 = rs.getDouble("CommCharge1");
			this.CommCharge2 = rs.getDouble("CommCharge2");
			this.CommCharge3 = rs.getDouble("CommCharge3");
			this.CommCharge4 = rs.getDouble("CommCharge4");
			this.GrpFYCRate = rs.getDouble("GrpFYCRate");
			this.DepFYCRate = rs.getDouble("DepFYCRate");
			this.StandPremRate = rs.getDouble("StandPremRate");
			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			this.P1 = rs.getDouble("P1");
			this.P2 = rs.getDouble("P2");
			this.P3 = rs.getDouble("P3");
			this.P4 = rs.getDouble("P4");
			this.P5 = rs.getDouble("P5");
			this.P6 = rs.getDouble("P6");
			this.P7 = rs.getDouble("P7");
			this.P8 = rs.getDouble("P8");
			this.P9 = rs.getDouble("P9");
			this.P10 = rs.getDouble("P10");
			if( rs.getString("P11") == null )
				this.P11 = null;
			else
				this.P11 = rs.getString("P11").trim();

			if( rs.getString("P12") == null )
				this.P12 = null;
			else
				this.P12 = rs.getString("P12").trim();

			if( rs.getString("P13") == null )
				this.P13 = null;
			else
				this.P13 = rs.getString("P13").trim();

			if( rs.getString("P14") == null )
				this.P14 = null;
			else
				this.P14 = rs.getString("P14").trim();

			if( rs.getString("P15") == null )
				this.P15 = null;
			else
				this.P15 = rs.getString("P15").trim();

			this.MakePolDate = rs.getDate("MakePolDate");
			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			if( rs.getString("branchattr1") == null )
				this.branchattr1 = null;
			else
				this.branchattr1 = rs.getString("branchattr1").trim();

			if( rs.getString("branchcode1") == null )
				this.branchcode1 = null;
			else
				this.branchcode1 = rs.getString("branchcode1").trim();

			if( rs.getString("riskmark") == null )
				this.riskmark = null;
			else
				this.riskmark = rs.getString("riskmark").trim();

			this.scanDate = rs.getDate("scanDate");
			if( rs.getString("AgentGroup2") == null )
				this.AgentGroup2 = null;
			else
				this.AgentGroup2 = rs.getString("AgentGroup2").trim();

			if( rs.getString("AgentGroup3") == null )
				this.AgentGroup3 = null;
			else
				this.AgentGroup3 = rs.getString("AgentGroup3").trim();

			if( rs.getString("AgentGroup4") == null )
				this.AgentGroup4 = null;
			else
				this.AgentGroup4 = rs.getString("AgentGroup4").trim();

			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LACommision表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LACommisionSchema getSchema()
	{
		LACommisionSchema aLACommisionSchema = new LACommisionSchema();
		aLACommisionSchema.setSchema(this);
		return aLACommisionSchema;
	}

	public LACommisionDB getDB()
	{
		LACommisionDB aDBOper = new LACommisionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACommision描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CommisionSN)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WageNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TPayDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TEnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CalcDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CommDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TransMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TransStandMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CurPayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommDire)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DirectWage));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppendWage));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(K5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYears));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Years));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandFYCRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FYCRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FYC));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GrpFYC));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DepFYC));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommCharge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommCharge1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommCharge2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommCharge3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommCharge4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GrpFYCRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DepFYCRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPremRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(P10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakePolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(branchattr1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(branchcode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(riskmark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( scanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLACommision>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CommisionSN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			WageNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			PayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReceiptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			TPayDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			TEnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			TConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			TMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			CalcDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			CommDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			TransMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
			TransStandMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).doubleValue();
			LastPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			CurPayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			TransType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			TransState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			CommDire = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			DirectWage = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,30,SysConst.PACKAGESPILTER))).doubleValue();
			AppendWage = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,31,SysConst.PACKAGESPILTER))).doubleValue();
			F1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			F2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			F3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			F4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			F5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			K1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			K2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			K3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,39,SysConst.PACKAGESPILTER))).doubleValue();
			K4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).doubleValue();
			K5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,41,SysConst.PACKAGESPILTER))).doubleValue();
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			AgentGroup1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 49, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			PayYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,52,SysConst.PACKAGESPILTER))).intValue();
			PayYears= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,53,SysConst.PACKAGESPILTER))).intValue();
			Years= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,54,SysConst.PACKAGESPILTER))).intValue();
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,55,SysConst.PACKAGESPILTER))).intValue();
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57,SysConst.PACKAGESPILTER));
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			AgentType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			BranchCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			CalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62,SysConst.PACKAGESPILTER));
			CalCount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,63,SysConst.PACKAGESPILTER))).doubleValue();
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			StandFYCRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,65,SysConst.PACKAGESPILTER))).doubleValue();
			FYCRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,66,SysConst.PACKAGESPILTER))).doubleValue();
			FYC = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,67,SysConst.PACKAGESPILTER))).doubleValue();
			GrpFYC = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,68,SysConst.PACKAGESPILTER))).doubleValue();
			DepFYC = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,69,SysConst.PACKAGESPILTER))).doubleValue();
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,70,SysConst.PACKAGESPILTER))).doubleValue();
			CommCharge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,71,SysConst.PACKAGESPILTER))).doubleValue();
			CommCharge1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,72,SysConst.PACKAGESPILTER))).doubleValue();
			CommCharge2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,73,SysConst.PACKAGESPILTER))).doubleValue();
			CommCharge3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,74,SysConst.PACKAGESPILTER))).doubleValue();
			CommCharge4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).doubleValue();
			GrpFYCRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).doubleValue();
			DepFYCRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
			StandPremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,78,SysConst.PACKAGESPILTER))).doubleValue();
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			P1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).doubleValue();
			P2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).doubleValue();
			P3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,82,SysConst.PACKAGESPILTER))).doubleValue();
			P4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,83,SysConst.PACKAGESPILTER))).doubleValue();
			P5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,84,SysConst.PACKAGESPILTER))).doubleValue();
			P6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,85,SysConst.PACKAGESPILTER))).doubleValue();
			P7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,86,SysConst.PACKAGESPILTER))).doubleValue();
			P8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,87,SysConst.PACKAGESPILTER))).doubleValue();
			P9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,88,SysConst.PACKAGESPILTER))).doubleValue();
			P10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,89,SysConst.PACKAGESPILTER))).doubleValue();
			P11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			P12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			P13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
			P14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 93, SysConst.PACKAGESPILTER );
			P15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 94, SysConst.PACKAGESPILTER );
			MakePolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 95,SysConst.PACKAGESPILTER));
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 96,SysConst.PACKAGESPILTER));
			branchattr1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 97, SysConst.PACKAGESPILTER );
			branchcode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 98, SysConst.PACKAGESPILTER );
			riskmark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 99, SysConst.PACKAGESPILTER );
			scanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 100,SysConst.PACKAGESPILTER));
			AgentGroup2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 101, SysConst.PACKAGESPILTER );
			AgentGroup3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 102, SysConst.PACKAGESPILTER );
			AgentGroup4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 103, SysConst.PACKAGESPILTER );
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 104, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,105,SysConst.PACKAGESPILTER))).intValue();
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 106, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LACommisionSchema";
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
		if (FCode.equalsIgnoreCase("CommisionSN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommisionSN));
		}
		if (FCode.equalsIgnoreCase("WageNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WageNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMode));
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiptNo));
		}
		if (FCode.equalsIgnoreCase("TPayDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTPayDate()));
		}
		if (FCode.equalsIgnoreCase("TEnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("TConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTConfDate()));
		}
		if (FCode.equalsIgnoreCase("TMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTMakeDate()));
		}
		if (FCode.equalsIgnoreCase("CalcDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCalcDate()));
		}
		if (FCode.equalsIgnoreCase("CommDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCommDate()));
		}
		if (FCode.equalsIgnoreCase("TransMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransMoney));
		}
		if (FCode.equalsIgnoreCase("TransStandMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransStandMoney));
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
		}
		if (FCode.equalsIgnoreCase("TransType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransType));
		}
		if (FCode.equalsIgnoreCase("TransState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransState));
		}
		if (FCode.equalsIgnoreCase("CommDire"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommDire));
		}
		if (FCode.equalsIgnoreCase("DirectWage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DirectWage));
		}
		if (FCode.equalsIgnoreCase("AppendWage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppendWage));
		}
		if (FCode.equalsIgnoreCase("F1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F1));
		}
		if (FCode.equalsIgnoreCase("F2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F2));
		}
		if (FCode.equalsIgnoreCase("F3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F3));
		}
		if (FCode.equalsIgnoreCase("F4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F4));
		}
		if (FCode.equalsIgnoreCase("F5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F5));
		}
		if (FCode.equalsIgnoreCase("K1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K1));
		}
		if (FCode.equalsIgnoreCase("K2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K2));
		}
		if (FCode.equalsIgnoreCase("K3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K3));
		}
		if (FCode.equalsIgnoreCase("K4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K4));
		}
		if (FCode.equalsIgnoreCase("K5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(K5));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
		}
		if (FCode.equalsIgnoreCase("AgentGroup1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup1));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
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
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYear));
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYears));
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Years));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentType));
		}
		if (FCode.equalsIgnoreCase("BranchCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchCode));
		}
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
		}
		if (FCode.equalsIgnoreCase("CalCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCount));
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
		}
		if (FCode.equalsIgnoreCase("StandFYCRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFYCRate));
		}
		if (FCode.equalsIgnoreCase("FYCRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FYCRate));
		}
		if (FCode.equalsIgnoreCase("FYC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FYC));
		}
		if (FCode.equalsIgnoreCase("GrpFYC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpFYC));
		}
		if (FCode.equalsIgnoreCase("DepFYC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DepFYC));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("CommCharge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommCharge));
		}
		if (FCode.equalsIgnoreCase("CommCharge1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommCharge1));
		}
		if (FCode.equalsIgnoreCase("CommCharge2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommCharge2));
		}
		if (FCode.equalsIgnoreCase("CommCharge3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommCharge3));
		}
		if (FCode.equalsIgnoreCase("CommCharge4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommCharge4));
		}
		if (FCode.equalsIgnoreCase("GrpFYCRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpFYCRate));
		}
		if (FCode.equalsIgnoreCase("DepFYCRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DepFYCRate));
		}
		if (FCode.equalsIgnoreCase("StandPremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPremRate));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P1));
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P2));
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P3));
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P4));
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P5));
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P6));
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P7));
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P8));
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P9));
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P10));
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P11));
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P12));
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P13));
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P14));
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P15));
		}
		if (FCode.equalsIgnoreCase("MakePolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakePolDate()));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("branchattr1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(branchattr1));
		}
		if (FCode.equalsIgnoreCase("branchcode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(branchcode1));
		}
		if (FCode.equalsIgnoreCase("riskmark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(riskmark));
		}
		if (FCode.equalsIgnoreCase("scanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getscanDate()));
		}
		if (FCode.equalsIgnoreCase("AgentGroup2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup2));
		}
		if (FCode.equalsIgnoreCase("AgentGroup3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup3));
		}
		if (FCode.equalsIgnoreCase("AgentGroup4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup4));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
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
				strFieldValue = StrTool.GBKToUnicode(CommisionSN);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(WageNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 13:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PayMode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReceiptNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTPayDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTEnterAccDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTConfDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCalcDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCommDate()));
				break;
			case 22:
				strFieldValue = String.valueOf(TransMoney);
				break;
			case 23:
				strFieldValue = String.valueOf(TransStandMoney);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastPayToDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCurPayToDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(TransType);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(TransState);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(CommDire);
				break;
			case 29:
				strFieldValue = String.valueOf(DirectWage);
				break;
			case 30:
				strFieldValue = String.valueOf(AppendWage);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(F1);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(F2);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(F3);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(F4);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(F5);
				break;
			case 36:
				strFieldValue = String.valueOf(K1);
				break;
			case 37:
				strFieldValue = String.valueOf(K2);
				break;
			case 38:
				strFieldValue = String.valueOf(K3);
				break;
			case 39:
				strFieldValue = String.valueOf(K4);
				break;
			case 40:
				strFieldValue = String.valueOf(K5);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup1);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 48:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 51:
				strFieldValue = String.valueOf(PayYear);
				break;
			case 52:
				strFieldValue = String.valueOf(PayYears);
				break;
			case 53:
				strFieldValue = String.valueOf(Years);
				break;
			case 54:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(AgentType);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(BranchCode);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCalDate()));
				break;
			case 62:
				strFieldValue = String.valueOf(CalCount);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
				break;
			case 64:
				strFieldValue = String.valueOf(StandFYCRate);
				break;
			case 65:
				strFieldValue = String.valueOf(FYCRate);
				break;
			case 66:
				strFieldValue = String.valueOf(FYC);
				break;
			case 67:
				strFieldValue = String.valueOf(GrpFYC);
				break;
			case 68:
				strFieldValue = String.valueOf(DepFYC);
				break;
			case 69:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 70:
				strFieldValue = String.valueOf(CommCharge);
				break;
			case 71:
				strFieldValue = String.valueOf(CommCharge1);
				break;
			case 72:
				strFieldValue = String.valueOf(CommCharge2);
				break;
			case 73:
				strFieldValue = String.valueOf(CommCharge3);
				break;
			case 74:
				strFieldValue = String.valueOf(CommCharge4);
				break;
			case 75:
				strFieldValue = String.valueOf(GrpFYCRate);
				break;
			case 76:
				strFieldValue = String.valueOf(DepFYCRate);
				break;
			case 77:
				strFieldValue = String.valueOf(StandPremRate);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 79:
				strFieldValue = String.valueOf(P1);
				break;
			case 80:
				strFieldValue = String.valueOf(P2);
				break;
			case 81:
				strFieldValue = String.valueOf(P3);
				break;
			case 82:
				strFieldValue = String.valueOf(P4);
				break;
			case 83:
				strFieldValue = String.valueOf(P5);
				break;
			case 84:
				strFieldValue = String.valueOf(P6);
				break;
			case 85:
				strFieldValue = String.valueOf(P7);
				break;
			case 86:
				strFieldValue = String.valueOf(P8);
				break;
			case 87:
				strFieldValue = String.valueOf(P9);
				break;
			case 88:
				strFieldValue = String.valueOf(P10);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(P11);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(P12);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(P13);
				break;
			case 92:
				strFieldValue = StrTool.GBKToUnicode(P14);
				break;
			case 93:
				strFieldValue = StrTool.GBKToUnicode(P15);
				break;
			case 94:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakePolDate()));
				break;
			case 95:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 96:
				strFieldValue = StrTool.GBKToUnicode(branchattr1);
				break;
			case 97:
				strFieldValue = StrTool.GBKToUnicode(branchcode1);
				break;
			case 98:
				strFieldValue = StrTool.GBKToUnicode(riskmark);
				break;
			case 99:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getscanDate()));
				break;
			case 100:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup2);
				break;
			case 101:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup3);
				break;
			case 102:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup4);
				break;
			case 103:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 104:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 105:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
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

		if (FCode.equalsIgnoreCase("CommisionSN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommisionSN = FValue.trim();
			}
			else
				CommisionSN = null;
		}
		if (FCode.equalsIgnoreCase("WageNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WageNo = FValue.trim();
			}
			else
				WageNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMode = FValue.trim();
			}
			else
				PayMode = null;
		}
		if (FCode.equalsIgnoreCase("ReceiptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiptNo = FValue.trim();
			}
			else
				ReceiptNo = null;
		}
		if (FCode.equalsIgnoreCase("TPayDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TPayDate = fDate.getDate( FValue );
			}
			else
				TPayDate = null;
		}
		if (FCode.equalsIgnoreCase("TEnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TEnterAccDate = fDate.getDate( FValue );
			}
			else
				TEnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("TConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TConfDate = fDate.getDate( FValue );
			}
			else
				TConfDate = null;
		}
		if (FCode.equalsIgnoreCase("TMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TMakeDate = fDate.getDate( FValue );
			}
			else
				TMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("CalcDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CalcDate = fDate.getDate( FValue );
			}
			else
				CalcDate = null;
		}
		if (FCode.equalsIgnoreCase("CommDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CommDate = fDate.getDate( FValue );
			}
			else
				CommDate = null;
		}
		if (FCode.equalsIgnoreCase("TransMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("TransStandMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransStandMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastPayToDate = fDate.getDate( FValue );
			}
			else
				LastPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("CurPayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CurPayToDate = fDate.getDate( FValue );
			}
			else
				CurPayToDate = null;
		}
		if (FCode.equalsIgnoreCase("TransType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransType = FValue.trim();
			}
			else
				TransType = null;
		}
		if (FCode.equalsIgnoreCase("TransState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransState = FValue.trim();
			}
			else
				TransState = null;
		}
		if (FCode.equalsIgnoreCase("CommDire"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommDire = FValue.trim();
			}
			else
				CommDire = null;
		}
		if (FCode.equalsIgnoreCase("DirectWage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DirectWage = d;
			}
		}
		if (FCode.equalsIgnoreCase("AppendWage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppendWage = d;
			}
		}
		if (FCode.equalsIgnoreCase("F1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F1 = FValue.trim();
			}
			else
				F1 = null;
		}
		if (FCode.equalsIgnoreCase("F2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F2 = FValue.trim();
			}
			else
				F2 = null;
		}
		if (FCode.equalsIgnoreCase("F3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F3 = FValue.trim();
			}
			else
				F3 = null;
		}
		if (FCode.equalsIgnoreCase("F4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F4 = FValue.trim();
			}
			else
				F4 = null;
		}
		if (FCode.equalsIgnoreCase("F5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F5 = FValue.trim();
			}
			else
				F5 = null;
		}
		if (FCode.equalsIgnoreCase("K1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("K5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				K5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode1 = FValue.trim();
			}
			else
				AgentCode1 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup1 = FValue.trim();
			}
			else
				AgentGroup1 = null;
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
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
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayYears"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYears = i;
			}
		}
		if (FCode.equalsIgnoreCase("Years"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Years = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SignDate = fDate.getDate( FValue );
			}
			else
				SignDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("AgentType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentType = FValue.trim();
			}
			else
				AgentType = null;
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
		if (FCode.equalsIgnoreCase("CalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CalDate = fDate.getDate( FValue );
			}
			else
				CalDate = null;
		}
		if (FCode.equalsIgnoreCase("CalCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CalCount = d;
			}
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAttr = FValue.trim();
			}
			else
				BranchAttr = null;
		}
		if (FCode.equalsIgnoreCase("StandFYCRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandFYCRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FYCRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FYCRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("FYC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FYC = d;
			}
		}
		if (FCode.equalsIgnoreCase("GrpFYC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GrpFYC = d;
			}
		}
		if (FCode.equalsIgnoreCase("DepFYC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DepFYC = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommCharge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommCharge = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommCharge1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommCharge1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommCharge2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommCharge2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommCharge3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommCharge3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommCharge4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommCharge4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("GrpFYCRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GrpFYCRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("DepFYCRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DepFYCRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPremRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolType = FValue.trim();
			}
			else
				PolType = null;
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				P10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P11 = FValue.trim();
			}
			else
				P11 = null;
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P12 = FValue.trim();
			}
			else
				P12 = null;
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P13 = FValue.trim();
			}
			else
				P13 = null;
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P14 = FValue.trim();
			}
			else
				P14 = null;
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P15 = FValue.trim();
			}
			else
				P15 = null;
		}
		if (FCode.equalsIgnoreCase("MakePolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakePolDate = fDate.getDate( FValue );
			}
			else
				MakePolDate = null;
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomGetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("branchattr1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				branchattr1 = FValue.trim();
			}
			else
				branchattr1 = null;
		}
		if (FCode.equalsIgnoreCase("branchcode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				branchcode1 = FValue.trim();
			}
			else
				branchcode1 = null;
		}
		if (FCode.equalsIgnoreCase("riskmark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				riskmark = FValue.trim();
			}
			else
				riskmark = null;
		}
		if (FCode.equalsIgnoreCase("scanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				scanDate = fDate.getDate( FValue );
			}
			else
				scanDate = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup2 = FValue.trim();
			}
			else
				AgentGroup2 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup3 = FValue.trim();
			}
			else
				AgentGroup3 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup4 = FValue.trim();
			}
			else
				AgentGroup4 = null;
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LACommisionSchema other = (LACommisionSchema)otherObject;
		return
			CommisionSN.equals(other.getCommisionSN())
			&& WageNo.equals(other.getWageNo())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& MainPolNo.equals(other.getMainPolNo())
			&& PolNo.equals(other.getPolNo())
			&& ManageCom.equals(other.getManageCom())
			&& AppntNo.equals(other.getAppntNo())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& DutyCode.equals(other.getDutyCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& PayIntv == other.getPayIntv()
			&& PayMode.equals(other.getPayMode())
			&& ReceiptNo.equals(other.getReceiptNo())
			&& fDate.getString(TPayDate).equals(other.getTPayDate())
			&& fDate.getString(TEnterAccDate).equals(other.getTEnterAccDate())
			&& fDate.getString(TConfDate).equals(other.getTConfDate())
			&& fDate.getString(TMakeDate).equals(other.getTMakeDate())
			&& fDate.getString(CalcDate).equals(other.getCalcDate())
			&& fDate.getString(CommDate).equals(other.getCommDate())
			&& TransMoney == other.getTransMoney()
			&& TransStandMoney == other.getTransStandMoney()
			&& fDate.getString(LastPayToDate).equals(other.getLastPayToDate())
			&& fDate.getString(CurPayToDate).equals(other.getCurPayToDate())
			&& TransType.equals(other.getTransType())
			&& TransState.equals(other.getTransState())
			&& CommDire.equals(other.getCommDire())
			&& DirectWage == other.getDirectWage()
			&& AppendWage == other.getAppendWage()
			&& F1.equals(other.getF1())
			&& F2.equals(other.getF2())
			&& F3.equals(other.getF3())
			&& F4.equals(other.getF4())
			&& F5.equals(other.getF5())
			&& K1 == other.getK1()
			&& K2 == other.getK2()
			&& K3 == other.getK3()
			&& K4 == other.getK4()
			&& K5 == other.getK5()
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& AgentCode1.equals(other.getAgentCode1())
			&& AgentGroup1.equals(other.getAgentGroup1())
			&& Flag.equals(other.getFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PayYear == other.getPayYear()
			&& PayYears == other.getPayYears()
			&& Years == other.getYears()
			&& PayCount == other.getPayCount()
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& BranchType.equals(other.getBranchType())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentType.equals(other.getAgentType())
			&& BranchCode.equals(other.getBranchCode())
			&& fDate.getString(CalDate).equals(other.getCalDate())
			&& CalCount == other.getCalCount()
			&& BranchAttr.equals(other.getBranchAttr())
			&& StandFYCRate == other.getStandFYCRate()
			&& FYCRate == other.getFYCRate()
			&& FYC == other.getFYC()
			&& GrpFYC == other.getGrpFYC()
			&& DepFYC == other.getDepFYC()
			&& StandPrem == other.getStandPrem()
			&& CommCharge == other.getCommCharge()
			&& CommCharge1 == other.getCommCharge1()
			&& CommCharge2 == other.getCommCharge2()
			&& CommCharge3 == other.getCommCharge3()
			&& CommCharge4 == other.getCommCharge4()
			&& GrpFYCRate == other.getGrpFYCRate()
			&& DepFYCRate == other.getDepFYCRate()
			&& StandPremRate == other.getStandPremRate()
			&& PolType.equals(other.getPolType())
			&& P1 == other.getP1()
			&& P2 == other.getP2()
			&& P3 == other.getP3()
			&& P4 == other.getP4()
			&& P5 == other.getP5()
			&& P6 == other.getP6()
			&& P7 == other.getP7()
			&& P8 == other.getP8()
			&& P9 == other.getP9()
			&& P10 == other.getP10()
			&& P11.equals(other.getP11())
			&& P12.equals(other.getP12())
			&& P13.equals(other.getP13())
			&& P14.equals(other.getP14())
			&& P15.equals(other.getP15())
			&& fDate.getString(MakePolDate).equals(other.getMakePolDate())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& branchattr1.equals(other.getbranchattr1())
			&& branchcode1.equals(other.getbranchcode1())
			&& riskmark.equals(other.getriskmark())
			&& fDate.getString(scanDate).equals(other.getscanDate())
			&& AgentGroup2.equals(other.getAgentGroup2())
			&& AgentGroup3.equals(other.getAgentGroup3())
			&& AgentGroup4.equals(other.getAgentGroup4())
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& GrpContNo.equals(other.getGrpContNo());
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
		if( strFieldName.equals("CommisionSN") ) {
			return 0;
		}
		if( strFieldName.equals("WageNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 3;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolNo") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 7;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 8;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 9;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 11;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 12;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 13;
		}
		if( strFieldName.equals("PayMode") ) {
			return 14;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return 15;
		}
		if( strFieldName.equals("TPayDate") ) {
			return 16;
		}
		if( strFieldName.equals("TEnterAccDate") ) {
			return 17;
		}
		if( strFieldName.equals("TConfDate") ) {
			return 18;
		}
		if( strFieldName.equals("TMakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("CalcDate") ) {
			return 20;
		}
		if( strFieldName.equals("CommDate") ) {
			return 21;
		}
		if( strFieldName.equals("TransMoney") ) {
			return 22;
		}
		if( strFieldName.equals("TransStandMoney") ) {
			return 23;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return 24;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return 25;
		}
		if( strFieldName.equals("TransType") ) {
			return 26;
		}
		if( strFieldName.equals("TransState") ) {
			return 27;
		}
		if( strFieldName.equals("CommDire") ) {
			return 28;
		}
		if( strFieldName.equals("DirectWage") ) {
			return 29;
		}
		if( strFieldName.equals("AppendWage") ) {
			return 30;
		}
		if( strFieldName.equals("F1") ) {
			return 31;
		}
		if( strFieldName.equals("F2") ) {
			return 32;
		}
		if( strFieldName.equals("F3") ) {
			return 33;
		}
		if( strFieldName.equals("F4") ) {
			return 34;
		}
		if( strFieldName.equals("F5") ) {
			return 35;
		}
		if( strFieldName.equals("K1") ) {
			return 36;
		}
		if( strFieldName.equals("K2") ) {
			return 37;
		}
		if( strFieldName.equals("K3") ) {
			return 38;
		}
		if( strFieldName.equals("K4") ) {
			return 39;
		}
		if( strFieldName.equals("K5") ) {
			return 40;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 41;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 42;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 43;
		}
		if( strFieldName.equals("AgentGroup1") ) {
			return 44;
		}
		if( strFieldName.equals("Flag") ) {
			return 45;
		}
		if( strFieldName.equals("Operator") ) {
			return 46;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 47;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 48;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 49;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 50;
		}
		if( strFieldName.equals("PayYear") ) {
			return 51;
		}
		if( strFieldName.equals("PayYears") ) {
			return 52;
		}
		if( strFieldName.equals("Years") ) {
			return 53;
		}
		if( strFieldName.equals("PayCount") ) {
			return 54;
		}
		if( strFieldName.equals("SignDate") ) {
			return 55;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 56;
		}
		if( strFieldName.equals("BranchType") ) {
			return 57;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 58;
		}
		if( strFieldName.equals("AgentType") ) {
			return 59;
		}
		if( strFieldName.equals("BranchCode") ) {
			return 60;
		}
		if( strFieldName.equals("CalDate") ) {
			return 61;
		}
		if( strFieldName.equals("CalCount") ) {
			return 62;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 63;
		}
		if( strFieldName.equals("StandFYCRate") ) {
			return 64;
		}
		if( strFieldName.equals("FYCRate") ) {
			return 65;
		}
		if( strFieldName.equals("FYC") ) {
			return 66;
		}
		if( strFieldName.equals("GrpFYC") ) {
			return 67;
		}
		if( strFieldName.equals("DepFYC") ) {
			return 68;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 69;
		}
		if( strFieldName.equals("CommCharge") ) {
			return 70;
		}
		if( strFieldName.equals("CommCharge1") ) {
			return 71;
		}
		if( strFieldName.equals("CommCharge2") ) {
			return 72;
		}
		if( strFieldName.equals("CommCharge3") ) {
			return 73;
		}
		if( strFieldName.equals("CommCharge4") ) {
			return 74;
		}
		if( strFieldName.equals("GrpFYCRate") ) {
			return 75;
		}
		if( strFieldName.equals("DepFYCRate") ) {
			return 76;
		}
		if( strFieldName.equals("StandPremRate") ) {
			return 77;
		}
		if( strFieldName.equals("PolType") ) {
			return 78;
		}
		if( strFieldName.equals("P1") ) {
			return 79;
		}
		if( strFieldName.equals("P2") ) {
			return 80;
		}
		if( strFieldName.equals("P3") ) {
			return 81;
		}
		if( strFieldName.equals("P4") ) {
			return 82;
		}
		if( strFieldName.equals("P5") ) {
			return 83;
		}
		if( strFieldName.equals("P6") ) {
			return 84;
		}
		if( strFieldName.equals("P7") ) {
			return 85;
		}
		if( strFieldName.equals("P8") ) {
			return 86;
		}
		if( strFieldName.equals("P9") ) {
			return 87;
		}
		if( strFieldName.equals("P10") ) {
			return 88;
		}
		if( strFieldName.equals("P11") ) {
			return 89;
		}
		if( strFieldName.equals("P12") ) {
			return 90;
		}
		if( strFieldName.equals("P13") ) {
			return 91;
		}
		if( strFieldName.equals("P14") ) {
			return 92;
		}
		if( strFieldName.equals("P15") ) {
			return 93;
		}
		if( strFieldName.equals("MakePolDate") ) {
			return 94;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 95;
		}
		if( strFieldName.equals("branchattr1") ) {
			return 96;
		}
		if( strFieldName.equals("branchcode1") ) {
			return 97;
		}
		if( strFieldName.equals("riskmark") ) {
			return 98;
		}
		if( strFieldName.equals("scanDate") ) {
			return 99;
		}
		if( strFieldName.equals("AgentGroup2") ) {
			return 100;
		}
		if( strFieldName.equals("AgentGroup3") ) {
			return 101;
		}
		if( strFieldName.equals("AgentGroup4") ) {
			return 102;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 103;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 104;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 105;
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
				strFieldName = "CommisionSN";
				break;
			case 1:
				strFieldName = "WageNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "GrpPolNo";
				break;
			case 4:
				strFieldName = "MainPolNo";
				break;
			case 5:
				strFieldName = "PolNo";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "AppntNo";
				break;
			case 8:
				strFieldName = "RiskCode";
				break;
			case 9:
				strFieldName = "RiskVersion";
				break;
			case 10:
				strFieldName = "DutyCode";
				break;
			case 11:
				strFieldName = "PayPlanCode";
				break;
			case 12:
				strFieldName = "CValiDate";
				break;
			case 13:
				strFieldName = "PayIntv";
				break;
			case 14:
				strFieldName = "PayMode";
				break;
			case 15:
				strFieldName = "ReceiptNo";
				break;
			case 16:
				strFieldName = "TPayDate";
				break;
			case 17:
				strFieldName = "TEnterAccDate";
				break;
			case 18:
				strFieldName = "TConfDate";
				break;
			case 19:
				strFieldName = "TMakeDate";
				break;
			case 20:
				strFieldName = "CalcDate";
				break;
			case 21:
				strFieldName = "CommDate";
				break;
			case 22:
				strFieldName = "TransMoney";
				break;
			case 23:
				strFieldName = "TransStandMoney";
				break;
			case 24:
				strFieldName = "LastPayToDate";
				break;
			case 25:
				strFieldName = "CurPayToDate";
				break;
			case 26:
				strFieldName = "TransType";
				break;
			case 27:
				strFieldName = "TransState";
				break;
			case 28:
				strFieldName = "CommDire";
				break;
			case 29:
				strFieldName = "DirectWage";
				break;
			case 30:
				strFieldName = "AppendWage";
				break;
			case 31:
				strFieldName = "F1";
				break;
			case 32:
				strFieldName = "F2";
				break;
			case 33:
				strFieldName = "F3";
				break;
			case 34:
				strFieldName = "F4";
				break;
			case 35:
				strFieldName = "F5";
				break;
			case 36:
				strFieldName = "K1";
				break;
			case 37:
				strFieldName = "K2";
				break;
			case 38:
				strFieldName = "K3";
				break;
			case 39:
				strFieldName = "K4";
				break;
			case 40:
				strFieldName = "K5";
				break;
			case 41:
				strFieldName = "AgentCode";
				break;
			case 42:
				strFieldName = "AgentGroup";
				break;
			case 43:
				strFieldName = "AgentCode1";
				break;
			case 44:
				strFieldName = "AgentGroup1";
				break;
			case 45:
				strFieldName = "Flag";
				break;
			case 46:
				strFieldName = "Operator";
				break;
			case 47:
				strFieldName = "MakeDate";
				break;
			case 48:
				strFieldName = "MakeTime";
				break;
			case 49:
				strFieldName = "ModifyDate";
				break;
			case 50:
				strFieldName = "ModifyTime";
				break;
			case 51:
				strFieldName = "PayYear";
				break;
			case 52:
				strFieldName = "PayYears";
				break;
			case 53:
				strFieldName = "Years";
				break;
			case 54:
				strFieldName = "PayCount";
				break;
			case 55:
				strFieldName = "SignDate";
				break;
			case 56:
				strFieldName = "GetPolDate";
				break;
			case 57:
				strFieldName = "BranchType";
				break;
			case 58:
				strFieldName = "AgentCom";
				break;
			case 59:
				strFieldName = "AgentType";
				break;
			case 60:
				strFieldName = "BranchCode";
				break;
			case 61:
				strFieldName = "CalDate";
				break;
			case 62:
				strFieldName = "CalCount";
				break;
			case 63:
				strFieldName = "BranchAttr";
				break;
			case 64:
				strFieldName = "StandFYCRate";
				break;
			case 65:
				strFieldName = "FYCRate";
				break;
			case 66:
				strFieldName = "FYC";
				break;
			case 67:
				strFieldName = "GrpFYC";
				break;
			case 68:
				strFieldName = "DepFYC";
				break;
			case 69:
				strFieldName = "StandPrem";
				break;
			case 70:
				strFieldName = "CommCharge";
				break;
			case 71:
				strFieldName = "CommCharge1";
				break;
			case 72:
				strFieldName = "CommCharge2";
				break;
			case 73:
				strFieldName = "CommCharge3";
				break;
			case 74:
				strFieldName = "CommCharge4";
				break;
			case 75:
				strFieldName = "GrpFYCRate";
				break;
			case 76:
				strFieldName = "DepFYCRate";
				break;
			case 77:
				strFieldName = "StandPremRate";
				break;
			case 78:
				strFieldName = "PolType";
				break;
			case 79:
				strFieldName = "P1";
				break;
			case 80:
				strFieldName = "P2";
				break;
			case 81:
				strFieldName = "P3";
				break;
			case 82:
				strFieldName = "P4";
				break;
			case 83:
				strFieldName = "P5";
				break;
			case 84:
				strFieldName = "P6";
				break;
			case 85:
				strFieldName = "P7";
				break;
			case 86:
				strFieldName = "P8";
				break;
			case 87:
				strFieldName = "P9";
				break;
			case 88:
				strFieldName = "P10";
				break;
			case 89:
				strFieldName = "P11";
				break;
			case 90:
				strFieldName = "P12";
				break;
			case 91:
				strFieldName = "P13";
				break;
			case 92:
				strFieldName = "P14";
				break;
			case 93:
				strFieldName = "P15";
				break;
			case 94:
				strFieldName = "MakePolDate";
				break;
			case 95:
				strFieldName = "CustomGetPolDate";
				break;
			case 96:
				strFieldName = "branchattr1";
				break;
			case 97:
				strFieldName = "branchcode1";
				break;
			case 98:
				strFieldName = "riskmark";
				break;
			case 99:
				strFieldName = "scanDate";
				break;
			case 100:
				strFieldName = "AgentGroup2";
				break;
			case 101:
				strFieldName = "AgentGroup3";
				break;
			case 102:
				strFieldName = "AgentGroup4";
				break;
			case 103:
				strFieldName = "InsuYearFlag";
				break;
			case 104:
				strFieldName = "InsuYear";
				break;
			case 105:
				strFieldName = "GrpContNo";
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
		if( strFieldName.equals("CommisionSN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WageNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TPayDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TEnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CalcDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CommDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TransStandMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CurPayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommDire") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DirectWage") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppendWage") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("F1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("K1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("K5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag") ) {
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
		if( strFieldName.equals("PayYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayYears") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Years") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CalCount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFYCRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FYCRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("FYC") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GrpFYC") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DepFYC") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommCharge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommCharge1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommCharge2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommCharge3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommCharge4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GrpFYCRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DepFYCRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("P11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakePolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("branchattr1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("branchcode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("riskmark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("scanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AgentGroup2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpContNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 23:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 38:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 39:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 40:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 48:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 49:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 50:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 51:
				nFieldType = Schema.TYPE_INT;
				break;
			case 52:
				nFieldType = Schema.TYPE_INT;
				break;
			case 53:
				nFieldType = Schema.TYPE_INT;
				break;
			case 54:
				nFieldType = Schema.TYPE_INT;
				break;
			case 55:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 56:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 57:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 58:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 65:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 66:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 67:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 68:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 69:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 70:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 71:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 72:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 73:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 74:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 75:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 76:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 77:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 78:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 79:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 80:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 81:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 82:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 83:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 84:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 85:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 86:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 87:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 88:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 89:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 90:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 91:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 92:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 93:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 94:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 95:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 96:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 97:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 98:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 99:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 100:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 101:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 102:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 103:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 104:
				nFieldType = Schema.TYPE_INT;
				break;
			case 105:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
