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
import com.sinosoft.lis.db.PD_LMRiskAppDB;

/*
 * <p>ClassName: PD_LMRiskAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PD_LMRiskApp
 */
public class PD_LMRiskAppSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 险类编码 */
	private String KindCode;
	/** 险种分类 */
	private String RiskType;
	/** 险种分类1 */
	private String RiskType1;
	/** 险种分类2 */
	private String RiskType2;
	/** 险种性质 */
	private String RiskProp;
	/** 险种类别 */
	private String RiskPeriod;
	/** 险种细分 */
	private String RiskTypeDetail;
	/** 险种标记 */
	private String RiskFlag;
	/** 保单类型 */
	private String PolType;
	/** 投资标记 */
	private String InvestFlag;
	/** 分红标记 */
	private String BonusFlag;
	/** 红利领取方式 */
	private String BonusMode;
	/** 有无名单标记 */
	private String ListFlag;
	/** 主附险标记 */
	private String SubRiskFlag;
	/** 计算精确位 */
	private int CalDigital;
	/** 计算取舍方法 */
	private String CalChoMode;
	/** 风险保额倍数 */
	private int RiskAmntMult;
	/** 保险期限标志 */
	private String InsuPeriodFlag;
	/** 保险期间上限 */
	private int MaxEndPeriod;
	/** 满期截至年龄 */
	private int AgeLmt;
	/** 签单日算法 */
	private int SignDateCalMode;
	/** 协议险标记 */
	private String ProtocolFlag;
	/** 协议险给付金改变标记 */
	private String GetChgFlag;
	/** 协议缴费标记 */
	private String ProtocolPayFlag;
	/** 保障计划标记 */
	private String EnsuPlanFlag;
	/** 保障计划调整标记 */
	private String EnsuPlanAdjFlag;
	/** 开办日期 */
	private Date StartDate;
	/** 停办日期 */
	private Date EndDate;
	/** 最小投保人年龄 */
	private int MinAppntAge;
	/** 最大投保人年龄 */
	private int MaxAppntAge;
	/** 最大被保人年龄 */
	private int MaxInsuredAge;
	/** 最小被保人年龄 */
	private int MinInsuredAge;
	/** 投保使用利率 */
	private double AppInterest;
	/** 投保使用费率 */
	private double AppPremRate;
	/** 多被保人标记 */
	private String InsuredFlag;
	/** 共保标记 */
	private String ShareFlag;
	/** 受益人标记 */
	private String BnfFlag;
	/** 暂缴费标记 */
	private String TempPayFlag;
	/** 录入缴费项标记 */
	private String InpPayPlan;
	/** 告知标记 */
	private String ImpartFlag;
	/** 保险经历标记 */
	private String InsuExpeFlag;
	/** 提供借款标记 */
	private String LoanFlag;
	/** 抵押标记 */
	private String MortagageFlag;
	/** 备注 */
	private String IDifReturnFlag;
	/** 减额缴清标记 */
	private String CutAmntStopPay;
	/** 分保率 */
	private double RinsRate;
	/** 销售标记 */
	private String SaleFlag;
	/** 磁盘文件投保标记 */
	private String FileAppFlag;
	/** 管理部门 */
	private String MngCom;
	/** 自动垫缴标志 */
	private String AutoPayFlag;
	/** 是否打印医院列表标记 */
	private String NeedPrintHospital;
	/** 是否打印伤残给付表标记 */
	private String NeedPrintGet;
	/** 险种分类3 */
	private String RiskType3;
	/** 险种分类4 */
	private String RiskType4;
	/** 险种分类5 */
	private String RiskType5;
	/** 签单后不需要打印 */
	private String NotPrintPol;
	/** 录单时是否需要设置保单送达日期 */
	private String NeedGetPolDate;
	/** 是否从暂缴费表中读取银行的账号和户名 */
	private String NeedReReadBank;
	/** 特殊险种标记 */
	private String SpecFlag;
	/** 利差返还标记 */
	private String InterestDifFlag;
	/** 险种分类6 */
	private String RiskType6;
	/** 险种分类7 */
	private String RiskType7;
	/** 险种分类8 */
	private String RiskType8;
	/** 险种分类9 */
	private String RiskType9;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;
	/** 取消提前给付特约标志 */
	private String CancleForeGetSpecFlag;
	/** 签单日算法2 */
	private int SignDateCalMode2;
	/** 签单日算法3 */
	private int SignDateCalMode3;
	/** 健康委托产品分类 */
	private String HealthType;
	/** 财务险种分类 */
	private String RiskTypeAcc;
	/** 财务险种类别 */
	private String RiskPeriodAcc;
	/** 自动垫缴类型 */
	private String AutoPayType;
	/** 能否自动展期标记 */
	private String AutoETIFlag;
	/** 自動展期類型 */
	private String AutoETIType;
	/** 能否自动退保标记 */
	private String AutoCTFlag;
	/** 清繳不分紅標誌 */
	private String NonParFlag;
	/** 產品是否允許回溯 */
	private String BackDateFlag;
	/** Honeymoon标记 */
	private String HoneymoonFlag;
	/** Nlg标记 */
	private String NLGFlag;

	public static final int FIELDNUM = 92;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		PD_LMRiskAppSchema cloned = (PD_LMRiskAppSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/**
	* 人为的分类 DX-短险 DB-确定给付型 DC-确定缴费型
	*/
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	/**
	* L--寿险(Life)、 A--意外险(Accident)、 H--健康险(Health)
	*/
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	/**
	* 1--帐户类年金; 2--传统一年期, 3--传统年金险, 4--利差返还年金, 5--综合意外伤害, 6--世纪泰康团体住院医疗, 7--世纪泰康团体综合医疗
	*/
	public String getRiskType1()
	{
		return RiskType1;
	}
	public void setRiskType1(String aRiskType1)
	{
		RiskType1 = aRiskType1;
	}
	/**
	* Y--打印年金领取年龄 N--不打印年金领取年龄
	*/
	public String getRiskType2()
	{
		return RiskType2;
	}
	public void setRiskType2(String aRiskType2)
	{
		RiskType2 = aRiskType2;
	}
	/**
	* G--团体险、I--个人险、A--两者都是 表PD_LMRiskApp中RiskProp字段(险种性质)的值为： G--团体险、 I--个人险、 Y--银代险 B--G+Y C--I+Y D--G+I+Y A--G+I两者都是 (1) 险种编码的代码选择事件中如果填写RiskCode 则显示表PD_LMRiskApp中RiskProp字段值为I,G和A的所有险种编码。 (2) 险种编码的代码选择事件中如果填写RiskInd 则显示表PD_LMRiskApp中RiskProp字段值为I和A的所有险种编码。 (3) 险种编码的代码选择事件中如果填写RiskGrp 则显示表PD_LMRiskApp中RiskProp字段值为G和A的所有险种编码。
	*/
	public String getRiskProp()
	{
		return RiskProp;
	}
	public void setRiskProp(String aRiskProp)
	{
		RiskProp = aRiskProp;
	}
	/**
	* L--长险(Long)、M--一年期险(Middle)、S--极短期险(Short)
	*/
	public String getRiskPeriod()
	{
		return RiskPeriod;
	}
	public void setRiskPeriod(String aRiskPeriod)
	{
		RiskPeriod = aRiskPeriod;
	}
	/**
	* 定期寿险、两全寿险、终身寿险、年金保险、意外险、住院医疗、手术医疗、普通医疗、疾病医疗、意外伤害医疗、综合医疗
	*/
	public String getRiskTypeDetail()
	{
		return RiskTypeDetail;
	}
	public void setRiskTypeDetail(String aRiskTypeDetail)
	{
		RiskTypeDetail = aRiskTypeDetail;
	}
	/**
	* 4 :定额单、0 基金类险种、null 其他
	*/
	public String getRiskFlag()
	{
		return RiskFlag;
	}
	public void setRiskFlag(String aRiskFlag)
	{
		RiskFlag = aRiskFlag;
	}
	/**
	* P--保单(Policy)、C--卡单(Card)
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
	* Y--是、N--否
	*/
	public String getInvestFlag()
	{
		return InvestFlag;
	}
	public void setInvestFlag(String aInvestFlag)
	{
		InvestFlag = aInvestFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getBonusFlag()
	{
		return BonusFlag;
	}
	public void setBonusFlag(String aBonusFlag)
	{
		BonusFlag = aBonusFlag;
	}
	/**
	* 1--领取现金 2--抵缴保费 3--购买缴清增额保险 4--累积生息 9--其他
	*/
	public String getBonusMode()
	{
		return BonusMode;
	}
	public void setBonusMode(String aBonusMode)
	{
		BonusMode = aBonusMode;
	}
	/**
	* A--都可 Y--有名单 N--无名单
	*/
	public String getListFlag()
	{
		return ListFlag;
	}
	public void setListFlag(String aListFlag)
	{
		ListFlag = aListFlag;
	}
	/**
	* M--主险(Main) S--附险(Sub) A--两者都可以。
	*/
	public String getSubRiskFlag()
	{
		return SubRiskFlag;
	}
	public void setSubRiskFlag(String aSubRiskFlag)
	{
		SubRiskFlag = aSubRiskFlag;
	}
	public int getCalDigital()
	{
		return CalDigital;
	}
	public void setCalDigital(int aCalDigital)
	{
		CalDigital = aCalDigital;
	}
	public void setCalDigital(String aCalDigital)
	{
		if (aCalDigital != null && !aCalDigital.equals(""))
		{
			Integer tInteger = new Integer(aCalDigital);
			int i = tInteger.intValue();
			CalDigital = i;
		}
	}

	/**
	* 1--四舍五入 2--截断 3--直接进位
	*/
	public String getCalChoMode()
	{
		return CalChoMode;
	}
	public void setCalChoMode(String aCalChoMode)
	{
		CalChoMode = aCalChoMode;
	}
	public int getRiskAmntMult()
	{
		return RiskAmntMult;
	}
	public void setRiskAmntMult(int aRiskAmntMult)
	{
		RiskAmntMult = aRiskAmntMult;
	}
	public void setRiskAmntMult(String aRiskAmntMult)
	{
		if (aRiskAmntMult != null && !aRiskAmntMult.equals(""))
		{
			Integer tInteger = new Integer(aRiskAmntMult);
			int i = tInteger.intValue();
			RiskAmntMult = i;
		}
	}

	/**
	* 1:有固定保险期限 0:保险期限为终身
	*/
	public String getInsuPeriodFlag()
	{
		return InsuPeriodFlag;
	}
	public void setInsuPeriodFlag(String aInsuPeriodFlag)
	{
		InsuPeriodFlag = aInsuPeriodFlag;
	}
	public int getMaxEndPeriod()
	{
		return MaxEndPeriod;
	}
	public void setMaxEndPeriod(int aMaxEndPeriod)
	{
		MaxEndPeriod = aMaxEndPeriod;
	}
	public void setMaxEndPeriod(String aMaxEndPeriod)
	{
		if (aMaxEndPeriod != null && !aMaxEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aMaxEndPeriod);
			int i = tInteger.intValue();
			MaxEndPeriod = i;
		}
	}

	public int getAgeLmt()
	{
		return AgeLmt;
	}
	public void setAgeLmt(int aAgeLmt)
	{
		AgeLmt = aAgeLmt;
	}
	public void setAgeLmt(String aAgeLmt)
	{
		if (aAgeLmt != null && !aAgeLmt.equals(""))
		{
			Integer tInteger = new Integer(aAgeLmt);
			int i = tInteger.intValue();
			AgeLmt = i;
		}
	}

	/**
	* 签单到生效日期之间的天数: 0--表示签单当天生效， 1--表示签单次日生效 2--表示首期交费次日 999--表示可选择，弹出生效日期录入框，但限制录入的生效日期在首交日期与登录日期之间
	*/
	public int getSignDateCalMode()
	{
		return SignDateCalMode;
	}
	public void setSignDateCalMode(int aSignDateCalMode)
	{
		SignDateCalMode = aSignDateCalMode;
	}
	public void setSignDateCalMode(String aSignDateCalMode)
	{
		if (aSignDateCalMode != null && !aSignDateCalMode.equals(""))
		{
			Integer tInteger = new Integer(aSignDateCalMode);
			int i = tInteger.intValue();
			SignDateCalMode = i;
		}
	}

	/**
	* 0 --协议险可做协议领取的全部功能、1 --协议险可做协议预定领取的给付金;而不能在给付时增加给付金类型并领取、2 --不能做协议领取
	*/
	public String getProtocolFlag()
	{
		return ProtocolFlag;
	}
	public void setProtocolFlag(String aProtocolFlag)
	{
		ProtocolFlag = aProtocolFlag;
	}
	/**
	* Y --承保时协议险给付金可改 N--不可改
	*/
	public String getGetChgFlag()
	{
		return GetChgFlag;
	}
	public void setGetChgFlag(String aGetChgFlag)
	{
		GetChgFlag = aGetChgFlag;
	}
	/**
	* N --协议正常交费均不能、C --只能做协议交费(Changeable)、S --只能做正常交费(Standard)、A --可做正常交费或协议交费
	*/
	public String getProtocolPayFlag()
	{
		return ProtocolPayFlag;
	}
	public void setProtocolPayFlag(String aProtocolPayFlag)
	{
		ProtocolPayFlag = aProtocolPayFlag;
	}
	/**
	* N-非固定档次，由操作员确定给付金的金额比例、Y-固定档次，险种有固定档次可供用户选择
	*/
	public String getEnsuPlanFlag()
	{
		return EnsuPlanFlag;
	}
	public void setEnsuPlanFlag(String aEnsuPlanFlag)
	{
		EnsuPlanFlag = aEnsuPlanFlag;
	}
	/**
	* N--不需要保障计划、R--比率变动(Rate);V--数值变动(Value)
	*/
	public String getEnsuPlanAdjFlag()
	{
		return EnsuPlanAdjFlag;
	}
	public void setEnsuPlanAdjFlag(String aEnsuPlanAdjFlag)
	{
		EnsuPlanAdjFlag = aEnsuPlanAdjFlag;
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

	public int getMinAppntAge()
	{
		return MinAppntAge;
	}
	public void setMinAppntAge(int aMinAppntAge)
	{
		MinAppntAge = aMinAppntAge;
	}
	public void setMinAppntAge(String aMinAppntAge)
	{
		if (aMinAppntAge != null && !aMinAppntAge.equals(""))
		{
			Integer tInteger = new Integer(aMinAppntAge);
			int i = tInteger.intValue();
			MinAppntAge = i;
		}
	}

	public int getMaxAppntAge()
	{
		return MaxAppntAge;
	}
	public void setMaxAppntAge(int aMaxAppntAge)
	{
		MaxAppntAge = aMaxAppntAge;
	}
	public void setMaxAppntAge(String aMaxAppntAge)
	{
		if (aMaxAppntAge != null && !aMaxAppntAge.equals(""))
		{
			Integer tInteger = new Integer(aMaxAppntAge);
			int i = tInteger.intValue();
			MaxAppntAge = i;
		}
	}

	public int getMaxInsuredAge()
	{
		return MaxInsuredAge;
	}
	public void setMaxInsuredAge(int aMaxInsuredAge)
	{
		MaxInsuredAge = aMaxInsuredAge;
	}
	public void setMaxInsuredAge(String aMaxInsuredAge)
	{
		if (aMaxInsuredAge != null && !aMaxInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aMaxInsuredAge);
			int i = tInteger.intValue();
			MaxInsuredAge = i;
		}
	}

	public int getMinInsuredAge()
	{
		return MinInsuredAge;
	}
	public void setMinInsuredAge(int aMinInsuredAge)
	{
		MinInsuredAge = aMinInsuredAge;
	}
	public void setMinInsuredAge(String aMinInsuredAge)
	{
		if (aMinInsuredAge != null && !aMinInsuredAge.equals(""))
		{
			Integer tInteger = new Integer(aMinInsuredAge);
			int i = tInteger.intValue();
			MinInsuredAge = i;
		}
	}

	public double getAppInterest()
	{
		return AppInterest;
	}
	public void setAppInterest(double aAppInterest)
	{
		AppInterest = aAppInterest;
	}
	public void setAppInterest(String aAppInterest)
	{
		if (aAppInterest != null && !aAppInterest.equals(""))
		{
			Double tDouble = new Double(aAppInterest);
			double d = tDouble.doubleValue();
			AppInterest = d;
		}
	}

	public double getAppPremRate()
	{
		return AppPremRate;
	}
	public void setAppPremRate(double aAppPremRate)
	{
		AppPremRate = aAppPremRate;
	}
	public void setAppPremRate(String aAppPremRate)
	{
		if (aAppPremRate != null && !aAppPremRate.equals(""))
		{
			Double tDouble = new Double(aAppPremRate);
			double d = tDouble.doubleValue();
			AppPremRate = d;
		}
	}

	/**
	* M--多被保人(Multiple)、S--单一被保人(Single)
	*/
	public String getInsuredFlag()
	{
		return InsuredFlag;
	}
	public void setInsuredFlag(String aInsuredFlag)
	{
		InsuredFlag = aInsuredFlag;
	}
	/**
	* Y--可共保、N--不可共保
	*/
	public String getShareFlag()
	{
		return ShareFlag;
	}
	public void setShareFlag(String aShareFlag)
	{
		ShareFlag = aShareFlag;
	}
	/**
	* I--受益人是被保险人(Insured)、A--是投保人(Appnt)、N--无限制
	*/
	public String getBnfFlag()
	{
		return BnfFlag;
	}
	public void setBnfFlag(String aBnfFlag)
	{
		BnfFlag = aBnfFlag;
	}
	/**
	* Y--需要、N--不需要
	*/
	public String getTempPayFlag()
	{
		return TempPayFlag;
	}
	public void setTempPayFlag(String aTempPayFlag)
	{
		TempPayFlag = aTempPayFlag;
	}
	/**
	* N - 不要求、Y - 要求录入缴费项
	*/
	public String getInpPayPlan()
	{
		return InpPayPlan;
	}
	public void setInpPayPlan(String aInpPayPlan)
	{
		InpPayPlan = aInpPayPlan;
	}
	/**
	* Y--是 N--否
	*/
	public String getImpartFlag()
	{
		return ImpartFlag;
	}
	public void setImpartFlag(String aImpartFlag)
	{
		ImpartFlag = aImpartFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getInsuExpeFlag()
	{
		return InsuExpeFlag;
	}
	public void setInsuExpeFlag(String aInsuExpeFlag)
	{
		InsuExpeFlag = aInsuExpeFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getLoanFlag()
	{
		return LoanFlag;
	}
	public void setLoanFlag(String aLoanFlag)
	{
		LoanFlag = aLoanFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getMortagageFlag()
	{
		return MortagageFlag;
	}
	public void setMortagageFlag(String aMortagageFlag)
	{
		MortagageFlag = aMortagageFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getIDifReturnFlag()
	{
		return IDifReturnFlag;
	}
	public void setIDifReturnFlag(String aIDifReturnFlag)
	{
		IDifReturnFlag = aIDifReturnFlag;
	}
	/**
	* Y--能、N--不能
	*/
	public String getCutAmntStopPay()
	{
		return CutAmntStopPay;
	}
	public void setCutAmntStopPay(String aCutAmntStopPay)
	{
		CutAmntStopPay = aCutAmntStopPay;
	}
	public double getRinsRate()
	{
		return RinsRate;
	}
	public void setRinsRate(double aRinsRate)
	{
		RinsRate = aRinsRate;
	}
	public void setRinsRate(String aRinsRate)
	{
		if (aRinsRate != null && !aRinsRate.equals(""))
		{
			Double tDouble = new Double(aRinsRate);
			double d = tDouble.doubleValue();
			RinsRate = d;
		}
	}

	/**
	* 1:直销长险2:营销 3.直销短险 4 长短险共有 0:直营销全有
	*/
	public String getSaleFlag()
	{
		return SaleFlag;
	}
	public void setSaleFlag(String aSaleFlag)
	{
		SaleFlag = aSaleFlag;
	}
	/**
	* Y--允许、N--不允许
	*/
	public String getFileAppFlag()
	{
		return FileAppFlag;
	}
	public void setFileAppFlag(String aFileAppFlag)
	{
		FileAppFlag = aFileAppFlag;
	}
	/**
	* G-团险部(Group),H-健康险部(Health),B-银行保险部(Bank)
	*/
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
	}
	/**
	* 0 --正常 1 --垫交
	*/
	public String getAutoPayFlag()
	{
		return AutoPayFlag;
	}
	public void setAutoPayFlag(String aAutoPayFlag)
	{
		AutoPayFlag = aAutoPayFlag;
	}
	/**
	* 0 －－ 不用打印 1 －－ 需要打印
	*/
	public String getNeedPrintHospital()
	{
		return NeedPrintHospital;
	}
	public void setNeedPrintHospital(String aNeedPrintHospital)
	{
		NeedPrintHospital = aNeedPrintHospital;
	}
	/**
	* 0 －－ 不用打印 1 －－ 需要打印
	*/
	public String getNeedPrintGet()
	{
		return NeedPrintGet;
	}
	public void setNeedPrintGet(String aNeedPrintGet)
	{
		NeedPrintGet = aNeedPrintGet;
	}
	/**
	* 该字段对应险种编码的第三位 1 传统险 2 分红 3 投连 4 万能 5 其他
	*/
	public String getRiskType3()
	{
		return RiskType3;
	}
	public void setRiskType3(String aRiskType3)
	{
		RiskType3 = aRiskType3;
	}
	/**
	* 该字段对应险种编码的第4位： 1 终身 2 两全及生存 3 定期 4 年金 5 重大疾病 6 意外 7 健康 8 短期综合（含意外和医疗）， 9 其他
	*/
	public String getRiskType4()
	{
		return RiskType4;
	}
	public void setRiskType4(String aRiskType4)
	{
		RiskType4 = aRiskType4;
	}
	/**
	* 1 －－ 表示一年期及一年期以内 2 －－ 表示一年期以上。
	*/
	public String getRiskType5()
	{
		return RiskType5;
	}
	public void setRiskType5(String aRiskType5)
	{
		RiskType5 = aRiskType5;
	}
	/**
	* 0 －－ 表示签单后需要打印 1 －－ 表示不需要打印 该字段必须描述。
	*/
	public String getNotPrintPol()
	{
		return NotPrintPol;
	}
	public void setNotPrintPol(String aNotPrintPol)
	{
		NotPrintPol = aNotPrintPol;
	}
	/**
	* 0 or null －－ 不用设置，在保单回执时设置 1 －－ 令回执日期为投保单录入日期 2 --令回执日期为签单日期
	*/
	public String getNeedGetPolDate()
	{
		return NeedGetPolDate;
	}
	public void setNeedGetPolDate(String aNeedGetPolDate)
	{
		NeedGetPolDate = aNeedGetPolDate;
	}
	/**
	* 0 －－ 表示不读取 1 －－ 表示需要读取
	*/
	public String getNeedReReadBank()
	{
		return NeedReReadBank;
	}
	public void setNeedReReadBank(String aNeedReReadBank)
	{
		NeedReReadBank = aNeedReReadBank;
	}
	/**
	* 0-普通险种 1-特殊险种 特殊险种在销售时需要授权
	*/
	public String getSpecFlag()
	{
		return SpecFlag;
	}
	public void setSpecFlag(String aSpecFlag)
	{
		SpecFlag = aSpecFlag;
	}
	/**
	* 0-没有利差返还 1-有利差返还 注：利差返还类型的可选项在PD_LMRiskParamsDef中定义
	*/
	public String getInterestDifFlag()
	{
		return InterestDifFlag;
	}
	public void setInterestDifFlag(String aInterestDifFlag)
	{
		InterestDifFlag = aInterestDifFlag;
	}
	/**
	* 1 －－ 表示一年期及一年期以内 2 －－ 表示一年期以上。
	*/
	public String getRiskType6()
	{
		return RiskType6;
	}
	public void setRiskType6(String aRiskType6)
	{
		RiskType6 = aRiskType6;
	}
	/**
	* 该字段对应险种编码的第4位： 1 终身 2 两全及生存 3 定期 4 年金 5 重大疾病 6 意外 7 健康 8 短期综合（含意外和医疗）， 9 其他
	*/
	public String getRiskType7()
	{
		return RiskType7;
	}
	public void setRiskType7(String aRiskType7)
	{
		RiskType7 = aRiskType7;
	}
	/**
	* 该字段对应险种编码的第三位 1 传统险 2 分红 3 投连 4 万能 5 其他
	*/
	public String getRiskType8()
	{
		return RiskType8;
	}
	public void setRiskType8(String aRiskType8)
	{
		RiskType8 = aRiskType8;
	}
	/**
	* 1 －－ 表示一年期及一年期以内 2 －－ 表示一年期以上。
	*/
	public String getRiskType9()
	{
		return RiskType9;
	}
	public void setRiskType9(String aRiskType9)
	{
		RiskType9 = aRiskType9;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}

	public String getCancleForeGetSpecFlag()
	{
		return CancleForeGetSpecFlag;
	}
	public void setCancleForeGetSpecFlag(String aCancleForeGetSpecFlag)
	{
		CancleForeGetSpecFlag = aCancleForeGetSpecFlag;
	}
	/**
	* 银邮保通外的保单签单到生效日期之间的天数: 0--表示签单当天生效， 1--表示签单次日生效 2--表示首期交费次日 999--表示可选择，弹出生效日期录入框，但限制录入的生效日期在首交日期与登录日期之间
	*/
	public int getSignDateCalMode2()
	{
		return SignDateCalMode2;
	}
	public void setSignDateCalMode2(int aSignDateCalMode2)
	{
		SignDateCalMode2 = aSignDateCalMode2;
	}
	public void setSignDateCalMode2(String aSignDateCalMode2)
	{
		if (aSignDateCalMode2 != null && !aSignDateCalMode2.equals(""))
		{
			Integer tInteger = new Integer(aSignDateCalMode2);
			int i = tInteger.intValue();
			SignDateCalMode2 = i;
		}
	}

	/**
	* 除银邮保通保单签单到生效日期之间的天数: 0--表示签单当天生效， 1--表示签单次日生效 2--表示首期交费次日 999--表示可选择，弹出生效日期录入框，但限制录入的生效日期在首交日期与登录日期之间
	*/
	public int getSignDateCalMode3()
	{
		return SignDateCalMode3;
	}
	public void setSignDateCalMode3(int aSignDateCalMode3)
	{
		SignDateCalMode3 = aSignDateCalMode3;
	}
	public void setSignDateCalMode3(String aSignDateCalMode3)
	{
		if (aSignDateCalMode3 != null && !aSignDateCalMode3.equals(""))
		{
			Integer tInteger = new Integer(aSignDateCalMode3);
			int i = tInteger.intValue();
			SignDateCalMode3 = i;
		}
	}

	/**
	* 健康委托产品 特指康福 0 非康福类产品 1 康福类产品
	*/
	public String getHealthType()
	{
		return HealthType;
	}
	public void setHealthType(String aHealthType)
	{
		HealthType = aHealthType;
	}
	/**
	* TK财务系统对应的险种类型 L -- 寿险 A -- 意外险 H -- 健康险
	*/
	public String getRiskTypeAcc()
	{
		return RiskTypeAcc;
	}
	public void setRiskTypeAcc(String aRiskTypeAcc)
	{
		RiskTypeAcc = aRiskTypeAcc;
	}
	/**
	* TK财务对应的险种期限 L -- 长险 S -- 短险
	*/
	public String getRiskPeriodAcc()
	{
		return RiskPeriodAcc;
	}
	public void setRiskPeriodAcc(String aRiskPeriodAcc)
	{
		RiskPeriodAcc = aRiskPeriodAcc;
	}
	/**
	* 0-按天<p>
	* 1-按期
	*/
	public String getAutoPayType()
	{
		return AutoPayType;
	}
	public void setAutoPayType(String aAutoPayType)
	{
		AutoPayType = aAutoPayType;
	}
	/**
	* Y--能、N--不能
	*/
	public String getAutoETIFlag()
	{
		return AutoETIFlag;
	}
	public void setAutoETIFlag(String aAutoETIFlag)
	{
		AutoETIFlag = aAutoETIFlag;
	}
	/**
	* A--APL<p>
	* P--PAPL
	*/
	public String getAutoETIType()
	{
		return AutoETIType;
	}
	public void setAutoETIType(String aAutoETIType)
	{
		AutoETIType = aAutoETIType;
	}
	/**
	* Y--能、N--不能
	*/
	public String getAutoCTFlag()
	{
		return AutoCTFlag;
	}
	public void setAutoCTFlag(String aAutoCTFlag)
	{
		AutoCTFlag = aAutoCTFlag;
	}
	/**
	* 產品在失效處理中如果不繳費，將會自動進行清繳不分紅
	*/
	public String getNonParFlag()
	{
		return NonParFlag;
	}
	public void setNonParFlag(String aNonParFlag)
	{
		NonParFlag = aNonParFlag;
	}
	/**
	* N-不允許；Y-允許
	*/
	public String getBackDateFlag()
	{
		return BackDateFlag;
	}
	public void setBackDateFlag(String aBackDateFlag)
	{
		BackDateFlag = aBackDateFlag;
	}
	public String getHoneymoonFlag()
	{
		return HoneymoonFlag;
	}
	public void setHoneymoonFlag(String aHoneymoonFlag)
	{
		HoneymoonFlag = aHoneymoonFlag;
	}
	public String getNLGFlag()
	{
		return NLGFlag;
	}
	public void setNLGFlag(String aNLGFlag)
	{
		NLGFlag = aNLGFlag;
	}

	/**
	* 使用另外一个 PD_LMRiskAppSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskAppSchema PD_LMRiskAppSchema
	**/
	public void setSchema(PD_LMRiskAppSchema aPD_LMRiskAppSchema)
	{
		this.RiskCode = aPD_LMRiskAppSchema.getRiskCode();
		this.RiskVer = aPD_LMRiskAppSchema.getRiskVer();
		this.RiskName = aPD_LMRiskAppSchema.getRiskName();
		this.KindCode = aPD_LMRiskAppSchema.getKindCode();
		this.RiskType = aPD_LMRiskAppSchema.getRiskType();
		this.RiskType1 = aPD_LMRiskAppSchema.getRiskType1();
		this.RiskType2 = aPD_LMRiskAppSchema.getRiskType2();
		this.RiskProp = aPD_LMRiskAppSchema.getRiskProp();
		this.RiskPeriod = aPD_LMRiskAppSchema.getRiskPeriod();
		this.RiskTypeDetail = aPD_LMRiskAppSchema.getRiskTypeDetail();
		this.RiskFlag = aPD_LMRiskAppSchema.getRiskFlag();
		this.PolType = aPD_LMRiskAppSchema.getPolType();
		this.InvestFlag = aPD_LMRiskAppSchema.getInvestFlag();
		this.BonusFlag = aPD_LMRiskAppSchema.getBonusFlag();
		this.BonusMode = aPD_LMRiskAppSchema.getBonusMode();
		this.ListFlag = aPD_LMRiskAppSchema.getListFlag();
		this.SubRiskFlag = aPD_LMRiskAppSchema.getSubRiskFlag();
		this.CalDigital = aPD_LMRiskAppSchema.getCalDigital();
		this.CalChoMode = aPD_LMRiskAppSchema.getCalChoMode();
		this.RiskAmntMult = aPD_LMRiskAppSchema.getRiskAmntMult();
		this.InsuPeriodFlag = aPD_LMRiskAppSchema.getInsuPeriodFlag();
		this.MaxEndPeriod = aPD_LMRiskAppSchema.getMaxEndPeriod();
		this.AgeLmt = aPD_LMRiskAppSchema.getAgeLmt();
		this.SignDateCalMode = aPD_LMRiskAppSchema.getSignDateCalMode();
		this.ProtocolFlag = aPD_LMRiskAppSchema.getProtocolFlag();
		this.GetChgFlag = aPD_LMRiskAppSchema.getGetChgFlag();
		this.ProtocolPayFlag = aPD_LMRiskAppSchema.getProtocolPayFlag();
		this.EnsuPlanFlag = aPD_LMRiskAppSchema.getEnsuPlanFlag();
		this.EnsuPlanAdjFlag = aPD_LMRiskAppSchema.getEnsuPlanAdjFlag();
		this.StartDate = fDate.getDate( aPD_LMRiskAppSchema.getStartDate());
		this.EndDate = fDate.getDate( aPD_LMRiskAppSchema.getEndDate());
		this.MinAppntAge = aPD_LMRiskAppSchema.getMinAppntAge();
		this.MaxAppntAge = aPD_LMRiskAppSchema.getMaxAppntAge();
		this.MaxInsuredAge = aPD_LMRiskAppSchema.getMaxInsuredAge();
		this.MinInsuredAge = aPD_LMRiskAppSchema.getMinInsuredAge();
		this.AppInterest = aPD_LMRiskAppSchema.getAppInterest();
		this.AppPremRate = aPD_LMRiskAppSchema.getAppPremRate();
		this.InsuredFlag = aPD_LMRiskAppSchema.getInsuredFlag();
		this.ShareFlag = aPD_LMRiskAppSchema.getShareFlag();
		this.BnfFlag = aPD_LMRiskAppSchema.getBnfFlag();
		this.TempPayFlag = aPD_LMRiskAppSchema.getTempPayFlag();
		this.InpPayPlan = aPD_LMRiskAppSchema.getInpPayPlan();
		this.ImpartFlag = aPD_LMRiskAppSchema.getImpartFlag();
		this.InsuExpeFlag = aPD_LMRiskAppSchema.getInsuExpeFlag();
		this.LoanFlag = aPD_LMRiskAppSchema.getLoanFlag();
		this.MortagageFlag = aPD_LMRiskAppSchema.getMortagageFlag();
		this.IDifReturnFlag = aPD_LMRiskAppSchema.getIDifReturnFlag();
		this.CutAmntStopPay = aPD_LMRiskAppSchema.getCutAmntStopPay();
		this.RinsRate = aPD_LMRiskAppSchema.getRinsRate();
		this.SaleFlag = aPD_LMRiskAppSchema.getSaleFlag();
		this.FileAppFlag = aPD_LMRiskAppSchema.getFileAppFlag();
		this.MngCom = aPD_LMRiskAppSchema.getMngCom();
		this.AutoPayFlag = aPD_LMRiskAppSchema.getAutoPayFlag();
		this.NeedPrintHospital = aPD_LMRiskAppSchema.getNeedPrintHospital();
		this.NeedPrintGet = aPD_LMRiskAppSchema.getNeedPrintGet();
		this.RiskType3 = aPD_LMRiskAppSchema.getRiskType3();
		this.RiskType4 = aPD_LMRiskAppSchema.getRiskType4();
		this.RiskType5 = aPD_LMRiskAppSchema.getRiskType5();
		this.NotPrintPol = aPD_LMRiskAppSchema.getNotPrintPol();
		this.NeedGetPolDate = aPD_LMRiskAppSchema.getNeedGetPolDate();
		this.NeedReReadBank = aPD_LMRiskAppSchema.getNeedReReadBank();
		this.SpecFlag = aPD_LMRiskAppSchema.getSpecFlag();
		this.InterestDifFlag = aPD_LMRiskAppSchema.getInterestDifFlag();
		this.RiskType6 = aPD_LMRiskAppSchema.getRiskType6();
		this.RiskType7 = aPD_LMRiskAppSchema.getRiskType7();
		this.RiskType8 = aPD_LMRiskAppSchema.getRiskType8();
		this.RiskType9 = aPD_LMRiskAppSchema.getRiskType9();
		this.Operator = aPD_LMRiskAppSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMRiskAppSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskAppSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskAppSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskAppSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMRiskAppSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMRiskAppSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMRiskAppSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMRiskAppSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMRiskAppSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMRiskAppSchema.getStandbyflag6();
		this.CancleForeGetSpecFlag = aPD_LMRiskAppSchema.getCancleForeGetSpecFlag();
		this.SignDateCalMode2 = aPD_LMRiskAppSchema.getSignDateCalMode2();
		this.SignDateCalMode3 = aPD_LMRiskAppSchema.getSignDateCalMode3();
		this.HealthType = aPD_LMRiskAppSchema.getHealthType();
		this.RiskTypeAcc = aPD_LMRiskAppSchema.getRiskTypeAcc();
		this.RiskPeriodAcc = aPD_LMRiskAppSchema.getRiskPeriodAcc();
		this.AutoPayType = aPD_LMRiskAppSchema.getAutoPayType();
		this.AutoETIFlag = aPD_LMRiskAppSchema.getAutoETIFlag();
		this.AutoETIType = aPD_LMRiskAppSchema.getAutoETIType();
		this.AutoCTFlag = aPD_LMRiskAppSchema.getAutoCTFlag();
		this.NonParFlag = aPD_LMRiskAppSchema.getNonParFlag();
		this.BackDateFlag = aPD_LMRiskAppSchema.getBackDateFlag();
		this.HoneymoonFlag = aPD_LMRiskAppSchema.getHoneymoonFlag();
		this.NLGFlag = aPD_LMRiskAppSchema.getNLGFlag();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("RiskType1") == null )
				this.RiskType1 = null;
			else
				this.RiskType1 = rs.getString("RiskType1").trim();

			if( rs.getString("RiskType2") == null )
				this.RiskType2 = null;
			else
				this.RiskType2 = rs.getString("RiskType2").trim();

			if( rs.getString("RiskProp") == null )
				this.RiskProp = null;
			else
				this.RiskProp = rs.getString("RiskProp").trim();

			if( rs.getString("RiskPeriod") == null )
				this.RiskPeriod = null;
			else
				this.RiskPeriod = rs.getString("RiskPeriod").trim();

			if( rs.getString("RiskTypeDetail") == null )
				this.RiskTypeDetail = null;
			else
				this.RiskTypeDetail = rs.getString("RiskTypeDetail").trim();

			if( rs.getString("RiskFlag") == null )
				this.RiskFlag = null;
			else
				this.RiskFlag = rs.getString("RiskFlag").trim();

			if( rs.getString("PolType") == null )
				this.PolType = null;
			else
				this.PolType = rs.getString("PolType").trim();

			if( rs.getString("InvestFlag") == null )
				this.InvestFlag = null;
			else
				this.InvestFlag = rs.getString("InvestFlag").trim();

			if( rs.getString("BonusFlag") == null )
				this.BonusFlag = null;
			else
				this.BonusFlag = rs.getString("BonusFlag").trim();

			if( rs.getString("BonusMode") == null )
				this.BonusMode = null;
			else
				this.BonusMode = rs.getString("BonusMode").trim();

			if( rs.getString("ListFlag") == null )
				this.ListFlag = null;
			else
				this.ListFlag = rs.getString("ListFlag").trim();

			if( rs.getString("SubRiskFlag") == null )
				this.SubRiskFlag = null;
			else
				this.SubRiskFlag = rs.getString("SubRiskFlag").trim();

			this.CalDigital = rs.getInt("CalDigital");
			if( rs.getString("CalChoMode") == null )
				this.CalChoMode = null;
			else
				this.CalChoMode = rs.getString("CalChoMode").trim();

			this.RiskAmntMult = rs.getInt("RiskAmntMult");
			if( rs.getString("InsuPeriodFlag") == null )
				this.InsuPeriodFlag = null;
			else
				this.InsuPeriodFlag = rs.getString("InsuPeriodFlag").trim();

			this.MaxEndPeriod = rs.getInt("MaxEndPeriod");
			this.AgeLmt = rs.getInt("AgeLmt");
			this.SignDateCalMode = rs.getInt("SignDateCalMode");
			if( rs.getString("ProtocolFlag") == null )
				this.ProtocolFlag = null;
			else
				this.ProtocolFlag = rs.getString("ProtocolFlag").trim();

			if( rs.getString("GetChgFlag") == null )
				this.GetChgFlag = null;
			else
				this.GetChgFlag = rs.getString("GetChgFlag").trim();

			if( rs.getString("ProtocolPayFlag") == null )
				this.ProtocolPayFlag = null;
			else
				this.ProtocolPayFlag = rs.getString("ProtocolPayFlag").trim();

			if( rs.getString("EnsuPlanFlag") == null )
				this.EnsuPlanFlag = null;
			else
				this.EnsuPlanFlag = rs.getString("EnsuPlanFlag").trim();

			if( rs.getString("EnsuPlanAdjFlag") == null )
				this.EnsuPlanAdjFlag = null;
			else
				this.EnsuPlanAdjFlag = rs.getString("EnsuPlanAdjFlag").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.MinAppntAge = rs.getInt("MinAppntAge");
			this.MaxAppntAge = rs.getInt("MaxAppntAge");
			this.MaxInsuredAge = rs.getInt("MaxInsuredAge");
			this.MinInsuredAge = rs.getInt("MinInsuredAge");
			this.AppInterest = rs.getDouble("AppInterest");
			this.AppPremRate = rs.getDouble("AppPremRate");
			if( rs.getString("InsuredFlag") == null )
				this.InsuredFlag = null;
			else
				this.InsuredFlag = rs.getString("InsuredFlag").trim();

			if( rs.getString("ShareFlag") == null )
				this.ShareFlag = null;
			else
				this.ShareFlag = rs.getString("ShareFlag").trim();

			if( rs.getString("BnfFlag") == null )
				this.BnfFlag = null;
			else
				this.BnfFlag = rs.getString("BnfFlag").trim();

			if( rs.getString("TempPayFlag") == null )
				this.TempPayFlag = null;
			else
				this.TempPayFlag = rs.getString("TempPayFlag").trim();

			if( rs.getString("InpPayPlan") == null )
				this.InpPayPlan = null;
			else
				this.InpPayPlan = rs.getString("InpPayPlan").trim();

			if( rs.getString("ImpartFlag") == null )
				this.ImpartFlag = null;
			else
				this.ImpartFlag = rs.getString("ImpartFlag").trim();

			if( rs.getString("InsuExpeFlag") == null )
				this.InsuExpeFlag = null;
			else
				this.InsuExpeFlag = rs.getString("InsuExpeFlag").trim();

			if( rs.getString("LoanFlag") == null )
				this.LoanFlag = null;
			else
				this.LoanFlag = rs.getString("LoanFlag").trim();

			if( rs.getString("MortagageFlag") == null )
				this.MortagageFlag = null;
			else
				this.MortagageFlag = rs.getString("MortagageFlag").trim();

			if( rs.getString("IDifReturnFlag") == null )
				this.IDifReturnFlag = null;
			else
				this.IDifReturnFlag = rs.getString("IDifReturnFlag").trim();

			if( rs.getString("CutAmntStopPay") == null )
				this.CutAmntStopPay = null;
			else
				this.CutAmntStopPay = rs.getString("CutAmntStopPay").trim();

			this.RinsRate = rs.getDouble("RinsRate");
			if( rs.getString("SaleFlag") == null )
				this.SaleFlag = null;
			else
				this.SaleFlag = rs.getString("SaleFlag").trim();

			if( rs.getString("FileAppFlag") == null )
				this.FileAppFlag = null;
			else
				this.FileAppFlag = rs.getString("FileAppFlag").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("AutoPayFlag") == null )
				this.AutoPayFlag = null;
			else
				this.AutoPayFlag = rs.getString("AutoPayFlag").trim();

			if( rs.getString("NeedPrintHospital") == null )
				this.NeedPrintHospital = null;
			else
				this.NeedPrintHospital = rs.getString("NeedPrintHospital").trim();

			if( rs.getString("NeedPrintGet") == null )
				this.NeedPrintGet = null;
			else
				this.NeedPrintGet = rs.getString("NeedPrintGet").trim();

			if( rs.getString("RiskType3") == null )
				this.RiskType3 = null;
			else
				this.RiskType3 = rs.getString("RiskType3").trim();

			if( rs.getString("RiskType4") == null )
				this.RiskType4 = null;
			else
				this.RiskType4 = rs.getString("RiskType4").trim();

			if( rs.getString("RiskType5") == null )
				this.RiskType5 = null;
			else
				this.RiskType5 = rs.getString("RiskType5").trim();

			if( rs.getString("NotPrintPol") == null )
				this.NotPrintPol = null;
			else
				this.NotPrintPol = rs.getString("NotPrintPol").trim();

			if( rs.getString("NeedGetPolDate") == null )
				this.NeedGetPolDate = null;
			else
				this.NeedGetPolDate = rs.getString("NeedGetPolDate").trim();

			if( rs.getString("NeedReReadBank") == null )
				this.NeedReReadBank = null;
			else
				this.NeedReReadBank = rs.getString("NeedReReadBank").trim();

			if( rs.getString("SpecFlag") == null )
				this.SpecFlag = null;
			else
				this.SpecFlag = rs.getString("SpecFlag").trim();

			if( rs.getString("InterestDifFlag") == null )
				this.InterestDifFlag = null;
			else
				this.InterestDifFlag = rs.getString("InterestDifFlag").trim();

			if( rs.getString("RiskType6") == null )
				this.RiskType6 = null;
			else
				this.RiskType6 = rs.getString("RiskType6").trim();

			if( rs.getString("RiskType7") == null )
				this.RiskType7 = null;
			else
				this.RiskType7 = rs.getString("RiskType7").trim();

			if( rs.getString("RiskType8") == null )
				this.RiskType8 = null;
			else
				this.RiskType8 = rs.getString("RiskType8").trim();

			if( rs.getString("RiskType9") == null )
				this.RiskType9 = null;
			else
				this.RiskType9 = rs.getString("RiskType9").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
			if( rs.getString("CancleForeGetSpecFlag") == null )
				this.CancleForeGetSpecFlag = null;
			else
				this.CancleForeGetSpecFlag = rs.getString("CancleForeGetSpecFlag").trim();

			this.SignDateCalMode2 = rs.getInt("SignDateCalMode2");
			this.SignDateCalMode3 = rs.getInt("SignDateCalMode3");
			if( rs.getString("HealthType") == null )
				this.HealthType = null;
			else
				this.HealthType = rs.getString("HealthType").trim();

			if( rs.getString("RiskTypeAcc") == null )
				this.RiskTypeAcc = null;
			else
				this.RiskTypeAcc = rs.getString("RiskTypeAcc").trim();

			if( rs.getString("RiskPeriodAcc") == null )
				this.RiskPeriodAcc = null;
			else
				this.RiskPeriodAcc = rs.getString("RiskPeriodAcc").trim();

			if( rs.getString("AutoPayType") == null )
				this.AutoPayType = null;
			else
				this.AutoPayType = rs.getString("AutoPayType").trim();

			if( rs.getString("AutoETIFlag") == null )
				this.AutoETIFlag = null;
			else
				this.AutoETIFlag = rs.getString("AutoETIFlag").trim();

			if( rs.getString("AutoETIType") == null )
				this.AutoETIType = null;
			else
				this.AutoETIType = rs.getString("AutoETIType").trim();

			if( rs.getString("AutoCTFlag") == null )
				this.AutoCTFlag = null;
			else
				this.AutoCTFlag = rs.getString("AutoCTFlag").trim();

			if( rs.getString("NonParFlag") == null )
				this.NonParFlag = null;
			else
				this.NonParFlag = rs.getString("NonParFlag").trim();

			if( rs.getString("BackDateFlag") == null )
				this.BackDateFlag = null;
			else
				this.BackDateFlag = rs.getString("BackDateFlag").trim();

			if( rs.getString("HoneymoonFlag") == null )
				this.HoneymoonFlag = null;
			else
				this.HoneymoonFlag = rs.getString("HoneymoonFlag").trim();

			if( rs.getString("NLGFlag") == null )
				this.NLGFlag = null;
			else
				this.NLGFlag = rs.getString("NLGFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMRiskApp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskAppSchema getSchema()
	{
		PD_LMRiskAppSchema aPD_LMRiskAppSchema = new PD_LMRiskAppSchema();
		aPD_LMRiskAppSchema.setSchema(this);
		return aPD_LMRiskAppSchema;
	}

	public PD_LMRiskAppDB getDB()
	{
		PD_LMRiskAppDB aDBOper = new PD_LMRiskAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskApp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskTypeDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InvestFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ListFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubRiskFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalDigital));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalChoMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiskAmntMult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AgeLmt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SignDateCalMode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtocolFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetChgFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProtocolPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnsuPlanFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnsuPlanAdjFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinAppntAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAppntAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxInsuredAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinInsuredAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppPremRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShareFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InpPayPlan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuExpeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoanFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MortagageFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDifReturnFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CutAmntStopPay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RinsRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileAppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedPrintHospital)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedPrintGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NotPrintPol)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedGetPolDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedReReadBank)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterestDifFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CancleForeGetSpecFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SignDateCalMode2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SignDateCalMode3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskTypeAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskPeriodAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoPayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoETIFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoETIType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AutoCTFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NonParFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackDateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HoneymoonFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NLGFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskType1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskTypeDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InvestFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BonusMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ListFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SubRiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CalDigital= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			CalChoMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RiskAmntMult= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			InsuPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MaxEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			AgeLmt= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			SignDateCalMode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			ProtocolFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			GetChgFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ProtocolPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			EnsuPlanFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			EnsuPlanAdjFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MinAppntAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,32,SysConst.PACKAGESPILTER))).intValue();
			MaxAppntAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).intValue();
			MaxInsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).intValue();
			MinInsuredAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).intValue();
			AppInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			AppPremRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			ShareFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			BnfFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			TempPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			InpPayPlan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			ImpartFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			InsuExpeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			LoanFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			MortagageFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			IDifReturnFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
			CutAmntStopPay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 48, SysConst.PACKAGESPILTER );
			RinsRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,49,SysConst.PACKAGESPILTER))).doubleValue();
			SaleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 50, SysConst.PACKAGESPILTER );
			FileAppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 51, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 52, SysConst.PACKAGESPILTER );
			AutoPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 53, SysConst.PACKAGESPILTER );
			NeedPrintHospital = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 54, SysConst.PACKAGESPILTER );
			NeedPrintGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 55, SysConst.PACKAGESPILTER );
			RiskType3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 56, SysConst.PACKAGESPILTER );
			RiskType4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 57, SysConst.PACKAGESPILTER );
			RiskType5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 58, SysConst.PACKAGESPILTER );
			NotPrintPol = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 59, SysConst.PACKAGESPILTER );
			NeedGetPolDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 60, SysConst.PACKAGESPILTER );
			NeedReReadBank = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 61, SysConst.PACKAGESPILTER );
			SpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 62, SysConst.PACKAGESPILTER );
			InterestDifFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 63, SysConst.PACKAGESPILTER );
			RiskType6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 64, SysConst.PACKAGESPILTER );
			RiskType7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 65, SysConst.PACKAGESPILTER );
			RiskType8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 66, SysConst.PACKAGESPILTER );
			RiskType9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 67, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 68, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 69,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 70, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 71,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 72, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 73, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 74, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,75,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,76,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,77,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,78,SysConst.PACKAGESPILTER))).doubleValue();
			CancleForeGetSpecFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 79, SysConst.PACKAGESPILTER );
			SignDateCalMode2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,80,SysConst.PACKAGESPILTER))).intValue();
			SignDateCalMode3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,81,SysConst.PACKAGESPILTER))).intValue();
			HealthType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 82, SysConst.PACKAGESPILTER );
			RiskTypeAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 83, SysConst.PACKAGESPILTER );
			RiskPeriodAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 84, SysConst.PACKAGESPILTER );
			AutoPayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 85, SysConst.PACKAGESPILTER );
			AutoETIFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 86, SysConst.PACKAGESPILTER );
			AutoETIType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 87, SysConst.PACKAGESPILTER );
			AutoCTFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 88, SysConst.PACKAGESPILTER );
			NonParFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 89, SysConst.PACKAGESPILTER );
			BackDateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 90, SysConst.PACKAGESPILTER );
			HoneymoonFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 91, SysConst.PACKAGESPILTER );
			NLGFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 92, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskAppSchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("RiskType1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType1));
		}
		if (FCode.equalsIgnoreCase("RiskType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType2));
		}
		if (FCode.equalsIgnoreCase("RiskProp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskProp));
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriod));
		}
		if (FCode.equalsIgnoreCase("RiskTypeDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskTypeDetail));
		}
		if (FCode.equalsIgnoreCase("RiskFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskFlag));
		}
		if (FCode.equalsIgnoreCase("PolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolType));
		}
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InvestFlag));
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusFlag));
		}
		if (FCode.equalsIgnoreCase("BonusMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMode));
		}
		if (FCode.equalsIgnoreCase("ListFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ListFlag));
		}
		if (FCode.equalsIgnoreCase("SubRiskFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskFlag));
		}
		if (FCode.equalsIgnoreCase("CalDigital"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalDigital));
		}
		if (FCode.equalsIgnoreCase("CalChoMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalChoMode));
		}
		if (FCode.equalsIgnoreCase("RiskAmntMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmntMult));
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("MaxEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxEndPeriod));
		}
		if (FCode.equalsIgnoreCase("AgeLmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgeLmt));
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDateCalMode));
		}
		if (FCode.equalsIgnoreCase("ProtocolFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolFlag));
		}
		if (FCode.equalsIgnoreCase("GetChgFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetChgFlag));
		}
		if (FCode.equalsIgnoreCase("ProtocolPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProtocolPayFlag));
		}
		if (FCode.equalsIgnoreCase("EnsuPlanFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnsuPlanFlag));
		}
		if (FCode.equalsIgnoreCase("EnsuPlanAdjFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnsuPlanAdjFlag));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("MinAppntAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinAppntAge));
		}
		if (FCode.equalsIgnoreCase("MaxAppntAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAppntAge));
		}
		if (FCode.equalsIgnoreCase("MaxInsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxInsuredAge));
		}
		if (FCode.equalsIgnoreCase("MinInsuredAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinInsuredAge));
		}
		if (FCode.equalsIgnoreCase("AppInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppInterest));
		}
		if (FCode.equalsIgnoreCase("AppPremRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppPremRate));
		}
		if (FCode.equalsIgnoreCase("InsuredFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredFlag));
		}
		if (FCode.equalsIgnoreCase("ShareFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShareFlag));
		}
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfFlag));
		}
		if (FCode.equalsIgnoreCase("TempPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempPayFlag));
		}
		if (FCode.equalsIgnoreCase("InpPayPlan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InpPayPlan));
		}
		if (FCode.equalsIgnoreCase("ImpartFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartFlag));
		}
		if (FCode.equalsIgnoreCase("InsuExpeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuExpeFlag));
		}
		if (FCode.equalsIgnoreCase("LoanFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoanFlag));
		}
		if (FCode.equalsIgnoreCase("MortagageFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MortagageFlag));
		}
		if (FCode.equalsIgnoreCase("IDifReturnFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDifReturnFlag));
		}
		if (FCode.equalsIgnoreCase("CutAmntStopPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutAmntStopPay));
		}
		if (FCode.equalsIgnoreCase("RinsRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RinsRate));
		}
		if (FCode.equalsIgnoreCase("SaleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleFlag));
		}
		if (FCode.equalsIgnoreCase("FileAppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileAppFlag));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayFlag));
		}
		if (FCode.equalsIgnoreCase("NeedPrintHospital"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedPrintHospital));
		}
		if (FCode.equalsIgnoreCase("NeedPrintGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedPrintGet));
		}
		if (FCode.equalsIgnoreCase("RiskType3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType3));
		}
		if (FCode.equalsIgnoreCase("RiskType4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType4));
		}
		if (FCode.equalsIgnoreCase("RiskType5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType5));
		}
		if (FCode.equalsIgnoreCase("NotPrintPol"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NotPrintPol));
		}
		if (FCode.equalsIgnoreCase("NeedGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedGetPolDate));
		}
		if (FCode.equalsIgnoreCase("NeedReReadBank"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedReReadBank));
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecFlag));
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterestDifFlag));
		}
		if (FCode.equalsIgnoreCase("RiskType6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType6));
		}
		if (FCode.equalsIgnoreCase("RiskType7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType7));
		}
		if (FCode.equalsIgnoreCase("RiskType8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType8));
		}
		if (FCode.equalsIgnoreCase("RiskType9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType9));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
		}
		if (FCode.equalsIgnoreCase("CancleForeGetSpecFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CancleForeGetSpecFlag));
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDateCalMode2));
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignDateCalMode3));
		}
		if (FCode.equalsIgnoreCase("HealthType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthType));
		}
		if (FCode.equalsIgnoreCase("RiskTypeAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskTypeAcc));
		}
		if (FCode.equalsIgnoreCase("RiskPeriodAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriodAcc));
		}
		if (FCode.equalsIgnoreCase("AutoPayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoPayType));
		}
		if (FCode.equalsIgnoreCase("AutoETIFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoETIFlag));
		}
		if (FCode.equalsIgnoreCase("AutoETIType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoETIType));
		}
		if (FCode.equalsIgnoreCase("AutoCTFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AutoCTFlag));
		}
		if (FCode.equalsIgnoreCase("NonParFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NonParFlag));
		}
		if (FCode.equalsIgnoreCase("BackDateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackDateFlag));
		}
		if (FCode.equalsIgnoreCase("HoneymoonFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HoneymoonFlag));
		}
		if (FCode.equalsIgnoreCase("NLGFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NLGFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskType1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskType2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskProp);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskPeriod);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskTypeDetail);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RiskFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PolType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InvestFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BonusFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BonusMode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ListFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SubRiskFlag);
				break;
			case 17:
				strFieldValue = String.valueOf(CalDigital);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(CalChoMode);
				break;
			case 19:
				strFieldValue = String.valueOf(RiskAmntMult);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(InsuPeriodFlag);
				break;
			case 21:
				strFieldValue = String.valueOf(MaxEndPeriod);
				break;
			case 22:
				strFieldValue = String.valueOf(AgeLmt);
				break;
			case 23:
				strFieldValue = String.valueOf(SignDateCalMode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ProtocolFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(GetChgFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ProtocolPayFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(EnsuPlanFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(EnsuPlanAdjFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 31:
				strFieldValue = String.valueOf(MinAppntAge);
				break;
			case 32:
				strFieldValue = String.valueOf(MaxAppntAge);
				break;
			case 33:
				strFieldValue = String.valueOf(MaxInsuredAge);
				break;
			case 34:
				strFieldValue = String.valueOf(MinInsuredAge);
				break;
			case 35:
				strFieldValue = String.valueOf(AppInterest);
				break;
			case 36:
				strFieldValue = String.valueOf(AppPremRate);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(InsuredFlag);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ShareFlag);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(BnfFlag);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(TempPayFlag);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(InpPayPlan);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ImpartFlag);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(InsuExpeFlag);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(LoanFlag);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(MortagageFlag);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(IDifReturnFlag);
				break;
			case 47:
				strFieldValue = StrTool.GBKToUnicode(CutAmntStopPay);
				break;
			case 48:
				strFieldValue = String.valueOf(RinsRate);
				break;
			case 49:
				strFieldValue = StrTool.GBKToUnicode(SaleFlag);
				break;
			case 50:
				strFieldValue = StrTool.GBKToUnicode(FileAppFlag);
				break;
			case 51:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 52:
				strFieldValue = StrTool.GBKToUnicode(AutoPayFlag);
				break;
			case 53:
				strFieldValue = StrTool.GBKToUnicode(NeedPrintHospital);
				break;
			case 54:
				strFieldValue = StrTool.GBKToUnicode(NeedPrintGet);
				break;
			case 55:
				strFieldValue = StrTool.GBKToUnicode(RiskType3);
				break;
			case 56:
				strFieldValue = StrTool.GBKToUnicode(RiskType4);
				break;
			case 57:
				strFieldValue = StrTool.GBKToUnicode(RiskType5);
				break;
			case 58:
				strFieldValue = StrTool.GBKToUnicode(NotPrintPol);
				break;
			case 59:
				strFieldValue = StrTool.GBKToUnicode(NeedGetPolDate);
				break;
			case 60:
				strFieldValue = StrTool.GBKToUnicode(NeedReReadBank);
				break;
			case 61:
				strFieldValue = StrTool.GBKToUnicode(SpecFlag);
				break;
			case 62:
				strFieldValue = StrTool.GBKToUnicode(InterestDifFlag);
				break;
			case 63:
				strFieldValue = StrTool.GBKToUnicode(RiskType6);
				break;
			case 64:
				strFieldValue = StrTool.GBKToUnicode(RiskType7);
				break;
			case 65:
				strFieldValue = StrTool.GBKToUnicode(RiskType8);
				break;
			case 66:
				strFieldValue = StrTool.GBKToUnicode(RiskType9);
				break;
			case 67:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 68:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 69:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 70:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 71:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 72:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 73:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 74:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 75:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 76:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 77:
				strFieldValue = String.valueOf(Standbyflag6);
				break;
			case 78:
				strFieldValue = StrTool.GBKToUnicode(CancleForeGetSpecFlag);
				break;
			case 79:
				strFieldValue = String.valueOf(SignDateCalMode2);
				break;
			case 80:
				strFieldValue = String.valueOf(SignDateCalMode3);
				break;
			case 81:
				strFieldValue = StrTool.GBKToUnicode(HealthType);
				break;
			case 82:
				strFieldValue = StrTool.GBKToUnicode(RiskTypeAcc);
				break;
			case 83:
				strFieldValue = StrTool.GBKToUnicode(RiskPeriodAcc);
				break;
			case 84:
				strFieldValue = StrTool.GBKToUnicode(AutoPayType);
				break;
			case 85:
				strFieldValue = StrTool.GBKToUnicode(AutoETIFlag);
				break;
			case 86:
				strFieldValue = StrTool.GBKToUnicode(AutoETIType);
				break;
			case 87:
				strFieldValue = StrTool.GBKToUnicode(AutoCTFlag);
				break;
			case 88:
				strFieldValue = StrTool.GBKToUnicode(NonParFlag);
				break;
			case 89:
				strFieldValue = StrTool.GBKToUnicode(BackDateFlag);
				break;
			case 90:
				strFieldValue = StrTool.GBKToUnicode(HoneymoonFlag);
				break;
			case 91:
				strFieldValue = StrTool.GBKToUnicode(NLGFlag);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("RiskType1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType1 = FValue.trim();
			}
			else
				RiskType1 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType2 = FValue.trim();
			}
			else
				RiskType2 = null;
		}
		if (FCode.equalsIgnoreCase("RiskProp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskProp = FValue.trim();
			}
			else
				RiskProp = null;
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskPeriod = FValue.trim();
			}
			else
				RiskPeriod = null;
		}
		if (FCode.equalsIgnoreCase("RiskTypeDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskTypeDetail = FValue.trim();
			}
			else
				RiskTypeDetail = null;
		}
		if (FCode.equalsIgnoreCase("RiskFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskFlag = FValue.trim();
			}
			else
				RiskFlag = null;
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
		if (FCode.equalsIgnoreCase("InvestFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InvestFlag = FValue.trim();
			}
			else
				InvestFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusFlag = FValue.trim();
			}
			else
				BonusFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusMode = FValue.trim();
			}
			else
				BonusMode = null;
		}
		if (FCode.equalsIgnoreCase("ListFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ListFlag = FValue.trim();
			}
			else
				ListFlag = null;
		}
		if (FCode.equalsIgnoreCase("SubRiskFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskFlag = FValue.trim();
			}
			else
				SubRiskFlag = null;
		}
		if (FCode.equalsIgnoreCase("CalDigital"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalDigital = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalChoMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalChoMode = FValue.trim();
			}
			else
				CalChoMode = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmntMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RiskAmntMult = i;
			}
		}
		if (FCode.equalsIgnoreCase("InsuPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuPeriodFlag = FValue.trim();
			}
			else
				InsuPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("MaxEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("AgeLmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AgeLmt = i;
			}
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SignDateCalMode = i;
			}
		}
		if (FCode.equalsIgnoreCase("ProtocolFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolFlag = FValue.trim();
			}
			else
				ProtocolFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetChgFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetChgFlag = FValue.trim();
			}
			else
				GetChgFlag = null;
		}
		if (FCode.equalsIgnoreCase("ProtocolPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProtocolPayFlag = FValue.trim();
			}
			else
				ProtocolPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("EnsuPlanFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnsuPlanFlag = FValue.trim();
			}
			else
				EnsuPlanFlag = null;
		}
		if (FCode.equalsIgnoreCase("EnsuPlanAdjFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnsuPlanAdjFlag = FValue.trim();
			}
			else
				EnsuPlanAdjFlag = null;
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
		if (FCode.equalsIgnoreCase("MinAppntAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinAppntAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxAppntAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxAppntAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxInsuredAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxInsuredAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MinInsuredAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinInsuredAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("AppInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("AppPremRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AppPremRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredFlag = FValue.trim();
			}
			else
				InsuredFlag = null;
		}
		if (FCode.equalsIgnoreCase("ShareFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShareFlag = FValue.trim();
			}
			else
				ShareFlag = null;
		}
		if (FCode.equalsIgnoreCase("BnfFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfFlag = FValue.trim();
			}
			else
				BnfFlag = null;
		}
		if (FCode.equalsIgnoreCase("TempPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempPayFlag = FValue.trim();
			}
			else
				TempPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("InpPayPlan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InpPayPlan = FValue.trim();
			}
			else
				InpPayPlan = null;
		}
		if (FCode.equalsIgnoreCase("ImpartFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartFlag = FValue.trim();
			}
			else
				ImpartFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuExpeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuExpeFlag = FValue.trim();
			}
			else
				InsuExpeFlag = null;
		}
		if (FCode.equalsIgnoreCase("LoanFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoanFlag = FValue.trim();
			}
			else
				LoanFlag = null;
		}
		if (FCode.equalsIgnoreCase("MortagageFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MortagageFlag = FValue.trim();
			}
			else
				MortagageFlag = null;
		}
		if (FCode.equalsIgnoreCase("IDifReturnFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDifReturnFlag = FValue.trim();
			}
			else
				IDifReturnFlag = null;
		}
		if (FCode.equalsIgnoreCase("CutAmntStopPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CutAmntStopPay = FValue.trim();
			}
			else
				CutAmntStopPay = null;
		}
		if (FCode.equalsIgnoreCase("RinsRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RinsRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("SaleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleFlag = FValue.trim();
			}
			else
				SaleFlag = null;
		}
		if (FCode.equalsIgnoreCase("FileAppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileAppFlag = FValue.trim();
			}
			else
				FileAppFlag = null;
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
		if (FCode.equalsIgnoreCase("AutoPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayFlag = FValue.trim();
			}
			else
				AutoPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("NeedPrintHospital"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedPrintHospital = FValue.trim();
			}
			else
				NeedPrintHospital = null;
		}
		if (FCode.equalsIgnoreCase("NeedPrintGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedPrintGet = FValue.trim();
			}
			else
				NeedPrintGet = null;
		}
		if (FCode.equalsIgnoreCase("RiskType3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType3 = FValue.trim();
			}
			else
				RiskType3 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType4 = FValue.trim();
			}
			else
				RiskType4 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType5 = FValue.trim();
			}
			else
				RiskType5 = null;
		}
		if (FCode.equalsIgnoreCase("NotPrintPol"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NotPrintPol = FValue.trim();
			}
			else
				NotPrintPol = null;
		}
		if (FCode.equalsIgnoreCase("NeedGetPolDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedGetPolDate = FValue.trim();
			}
			else
				NeedGetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("NeedReReadBank"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedReReadBank = FValue.trim();
			}
			else
				NeedReReadBank = null;
		}
		if (FCode.equalsIgnoreCase("SpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecFlag = FValue.trim();
			}
			else
				SpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("InterestDifFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterestDifFlag = FValue.trim();
			}
			else
				InterestDifFlag = null;
		}
		if (FCode.equalsIgnoreCase("RiskType6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType6 = FValue.trim();
			}
			else
				RiskType6 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType7 = FValue.trim();
			}
			else
				RiskType7 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType8 = FValue.trim();
			}
			else
				RiskType8 = null;
		}
		if (FCode.equalsIgnoreCase("RiskType9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType9 = FValue.trim();
			}
			else
				RiskType9 = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("CancleForeGetSpecFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CancleForeGetSpecFlag = FValue.trim();
			}
			else
				CancleForeGetSpecFlag = null;
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SignDateCalMode2 = i;
			}
		}
		if (FCode.equalsIgnoreCase("SignDateCalMode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SignDateCalMode3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("HealthType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthType = FValue.trim();
			}
			else
				HealthType = null;
		}
		if (FCode.equalsIgnoreCase("RiskTypeAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskTypeAcc = FValue.trim();
			}
			else
				RiskTypeAcc = null;
		}
		if (FCode.equalsIgnoreCase("RiskPeriodAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskPeriodAcc = FValue.trim();
			}
			else
				RiskPeriodAcc = null;
		}
		if (FCode.equalsIgnoreCase("AutoPayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoPayType = FValue.trim();
			}
			else
				AutoPayType = null;
		}
		if (FCode.equalsIgnoreCase("AutoETIFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoETIFlag = FValue.trim();
			}
			else
				AutoETIFlag = null;
		}
		if (FCode.equalsIgnoreCase("AutoETIType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoETIType = FValue.trim();
			}
			else
				AutoETIType = null;
		}
		if (FCode.equalsIgnoreCase("AutoCTFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AutoCTFlag = FValue.trim();
			}
			else
				AutoCTFlag = null;
		}
		if (FCode.equalsIgnoreCase("NonParFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NonParFlag = FValue.trim();
			}
			else
				NonParFlag = null;
		}
		if (FCode.equalsIgnoreCase("BackDateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackDateFlag = FValue.trim();
			}
			else
				BackDateFlag = null;
		}
		if (FCode.equalsIgnoreCase("HoneymoonFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HoneymoonFlag = FValue.trim();
			}
			else
				HoneymoonFlag = null;
		}
		if (FCode.equalsIgnoreCase("NLGFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NLGFlag = FValue.trim();
			}
			else
				NLGFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMRiskAppSchema other = (PD_LMRiskAppSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& KindCode.equals(other.getKindCode())
			&& RiskType.equals(other.getRiskType())
			&& RiskType1.equals(other.getRiskType1())
			&& RiskType2.equals(other.getRiskType2())
			&& RiskProp.equals(other.getRiskProp())
			&& RiskPeriod.equals(other.getRiskPeriod())
			&& RiskTypeDetail.equals(other.getRiskTypeDetail())
			&& RiskFlag.equals(other.getRiskFlag())
			&& PolType.equals(other.getPolType())
			&& InvestFlag.equals(other.getInvestFlag())
			&& BonusFlag.equals(other.getBonusFlag())
			&& BonusMode.equals(other.getBonusMode())
			&& ListFlag.equals(other.getListFlag())
			&& SubRiskFlag.equals(other.getSubRiskFlag())
			&& CalDigital == other.getCalDigital()
			&& CalChoMode.equals(other.getCalChoMode())
			&& RiskAmntMult == other.getRiskAmntMult()
			&& InsuPeriodFlag.equals(other.getInsuPeriodFlag())
			&& MaxEndPeriod == other.getMaxEndPeriod()
			&& AgeLmt == other.getAgeLmt()
			&& SignDateCalMode == other.getSignDateCalMode()
			&& ProtocolFlag.equals(other.getProtocolFlag())
			&& GetChgFlag.equals(other.getGetChgFlag())
			&& ProtocolPayFlag.equals(other.getProtocolPayFlag())
			&& EnsuPlanFlag.equals(other.getEnsuPlanFlag())
			&& EnsuPlanAdjFlag.equals(other.getEnsuPlanAdjFlag())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& MinAppntAge == other.getMinAppntAge()
			&& MaxAppntAge == other.getMaxAppntAge()
			&& MaxInsuredAge == other.getMaxInsuredAge()
			&& MinInsuredAge == other.getMinInsuredAge()
			&& AppInterest == other.getAppInterest()
			&& AppPremRate == other.getAppPremRate()
			&& InsuredFlag.equals(other.getInsuredFlag())
			&& ShareFlag.equals(other.getShareFlag())
			&& BnfFlag.equals(other.getBnfFlag())
			&& TempPayFlag.equals(other.getTempPayFlag())
			&& InpPayPlan.equals(other.getInpPayPlan())
			&& ImpartFlag.equals(other.getImpartFlag())
			&& InsuExpeFlag.equals(other.getInsuExpeFlag())
			&& LoanFlag.equals(other.getLoanFlag())
			&& MortagageFlag.equals(other.getMortagageFlag())
			&& IDifReturnFlag.equals(other.getIDifReturnFlag())
			&& CutAmntStopPay.equals(other.getCutAmntStopPay())
			&& RinsRate == other.getRinsRate()
			&& SaleFlag.equals(other.getSaleFlag())
			&& FileAppFlag.equals(other.getFileAppFlag())
			&& MngCom.equals(other.getMngCom())
			&& AutoPayFlag.equals(other.getAutoPayFlag())
			&& NeedPrintHospital.equals(other.getNeedPrintHospital())
			&& NeedPrintGet.equals(other.getNeedPrintGet())
			&& RiskType3.equals(other.getRiskType3())
			&& RiskType4.equals(other.getRiskType4())
			&& RiskType5.equals(other.getRiskType5())
			&& NotPrintPol.equals(other.getNotPrintPol())
			&& NeedGetPolDate.equals(other.getNeedGetPolDate())
			&& NeedReReadBank.equals(other.getNeedReReadBank())
			&& SpecFlag.equals(other.getSpecFlag())
			&& InterestDifFlag.equals(other.getInterestDifFlag())
			&& RiskType6.equals(other.getRiskType6())
			&& RiskType7.equals(other.getRiskType7())
			&& RiskType8.equals(other.getRiskType8())
			&& RiskType9.equals(other.getRiskType9())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6()
			&& CancleForeGetSpecFlag.equals(other.getCancleForeGetSpecFlag())
			&& SignDateCalMode2 == other.getSignDateCalMode2()
			&& SignDateCalMode3 == other.getSignDateCalMode3()
			&& HealthType.equals(other.getHealthType())
			&& RiskTypeAcc.equals(other.getRiskTypeAcc())
			&& RiskPeriodAcc.equals(other.getRiskPeriodAcc())
			&& AutoPayType.equals(other.getAutoPayType())
			&& AutoETIFlag.equals(other.getAutoETIFlag())
			&& AutoETIType.equals(other.getAutoETIType())
			&& AutoCTFlag.equals(other.getAutoCTFlag())
			&& NonParFlag.equals(other.getNonParFlag())
			&& BackDateFlag.equals(other.getBackDateFlag())
			&& HoneymoonFlag.equals(other.getHoneymoonFlag())
			&& NLGFlag.equals(other.getNLGFlag());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("KindCode") ) {
			return 3;
		}
		if( strFieldName.equals("RiskType") ) {
			return 4;
		}
		if( strFieldName.equals("RiskType1") ) {
			return 5;
		}
		if( strFieldName.equals("RiskType2") ) {
			return 6;
		}
		if( strFieldName.equals("RiskProp") ) {
			return 7;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return 8;
		}
		if( strFieldName.equals("RiskTypeDetail") ) {
			return 9;
		}
		if( strFieldName.equals("RiskFlag") ) {
			return 10;
		}
		if( strFieldName.equals("PolType") ) {
			return 11;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return 12;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return 13;
		}
		if( strFieldName.equals("BonusMode") ) {
			return 14;
		}
		if( strFieldName.equals("ListFlag") ) {
			return 15;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return 16;
		}
		if( strFieldName.equals("CalDigital") ) {
			return 17;
		}
		if( strFieldName.equals("CalChoMode") ) {
			return 18;
		}
		if( strFieldName.equals("RiskAmntMult") ) {
			return 19;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return 20;
		}
		if( strFieldName.equals("MaxEndPeriod") ) {
			return 21;
		}
		if( strFieldName.equals("AgeLmt") ) {
			return 22;
		}
		if( strFieldName.equals("SignDateCalMode") ) {
			return 23;
		}
		if( strFieldName.equals("ProtocolFlag") ) {
			return 24;
		}
		if( strFieldName.equals("GetChgFlag") ) {
			return 25;
		}
		if( strFieldName.equals("ProtocolPayFlag") ) {
			return 26;
		}
		if( strFieldName.equals("EnsuPlanFlag") ) {
			return 27;
		}
		if( strFieldName.equals("EnsuPlanAdjFlag") ) {
			return 28;
		}
		if( strFieldName.equals("StartDate") ) {
			return 29;
		}
		if( strFieldName.equals("EndDate") ) {
			return 30;
		}
		if( strFieldName.equals("MinAppntAge") ) {
			return 31;
		}
		if( strFieldName.equals("MaxAppntAge") ) {
			return 32;
		}
		if( strFieldName.equals("MaxInsuredAge") ) {
			return 33;
		}
		if( strFieldName.equals("MinInsuredAge") ) {
			return 34;
		}
		if( strFieldName.equals("AppInterest") ) {
			return 35;
		}
		if( strFieldName.equals("AppPremRate") ) {
			return 36;
		}
		if( strFieldName.equals("InsuredFlag") ) {
			return 37;
		}
		if( strFieldName.equals("ShareFlag") ) {
			return 38;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return 39;
		}
		if( strFieldName.equals("TempPayFlag") ) {
			return 40;
		}
		if( strFieldName.equals("InpPayPlan") ) {
			return 41;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return 42;
		}
		if( strFieldName.equals("InsuExpeFlag") ) {
			return 43;
		}
		if( strFieldName.equals("LoanFlag") ) {
			return 44;
		}
		if( strFieldName.equals("MortagageFlag") ) {
			return 45;
		}
		if( strFieldName.equals("IDifReturnFlag") ) {
			return 46;
		}
		if( strFieldName.equals("CutAmntStopPay") ) {
			return 47;
		}
		if( strFieldName.equals("RinsRate") ) {
			return 48;
		}
		if( strFieldName.equals("SaleFlag") ) {
			return 49;
		}
		if( strFieldName.equals("FileAppFlag") ) {
			return 50;
		}
		if( strFieldName.equals("MngCom") ) {
			return 51;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return 52;
		}
		if( strFieldName.equals("NeedPrintHospital") ) {
			return 53;
		}
		if( strFieldName.equals("NeedPrintGet") ) {
			return 54;
		}
		if( strFieldName.equals("RiskType3") ) {
			return 55;
		}
		if( strFieldName.equals("RiskType4") ) {
			return 56;
		}
		if( strFieldName.equals("RiskType5") ) {
			return 57;
		}
		if( strFieldName.equals("NotPrintPol") ) {
			return 58;
		}
		if( strFieldName.equals("NeedGetPolDate") ) {
			return 59;
		}
		if( strFieldName.equals("NeedReReadBank") ) {
			return 60;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return 61;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return 62;
		}
		if( strFieldName.equals("RiskType6") ) {
			return 63;
		}
		if( strFieldName.equals("RiskType7") ) {
			return 64;
		}
		if( strFieldName.equals("RiskType8") ) {
			return 65;
		}
		if( strFieldName.equals("RiskType9") ) {
			return 66;
		}
		if( strFieldName.equals("Operator") ) {
			return 67;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 68;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 69;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 70;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 71;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 72;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 73;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 74;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 75;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 76;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 77;
		}
		if( strFieldName.equals("CancleForeGetSpecFlag") ) {
			return 78;
		}
		if( strFieldName.equals("SignDateCalMode2") ) {
			return 79;
		}
		if( strFieldName.equals("SignDateCalMode3") ) {
			return 80;
		}
		if( strFieldName.equals("HealthType") ) {
			return 81;
		}
		if( strFieldName.equals("RiskTypeAcc") ) {
			return 82;
		}
		if( strFieldName.equals("RiskPeriodAcc") ) {
			return 83;
		}
		if( strFieldName.equals("AutoPayType") ) {
			return 84;
		}
		if( strFieldName.equals("AutoETIFlag") ) {
			return 85;
		}
		if( strFieldName.equals("AutoETIType") ) {
			return 86;
		}
		if( strFieldName.equals("AutoCTFlag") ) {
			return 87;
		}
		if( strFieldName.equals("NonParFlag") ) {
			return 88;
		}
		if( strFieldName.equals("BackDateFlag") ) {
			return 89;
		}
		if( strFieldName.equals("HoneymoonFlag") ) {
			return 90;
		}
		if( strFieldName.equals("NLGFlag") ) {
			return 91;
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
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "KindCode";
				break;
			case 4:
				strFieldName = "RiskType";
				break;
			case 5:
				strFieldName = "RiskType1";
				break;
			case 6:
				strFieldName = "RiskType2";
				break;
			case 7:
				strFieldName = "RiskProp";
				break;
			case 8:
				strFieldName = "RiskPeriod";
				break;
			case 9:
				strFieldName = "RiskTypeDetail";
				break;
			case 10:
				strFieldName = "RiskFlag";
				break;
			case 11:
				strFieldName = "PolType";
				break;
			case 12:
				strFieldName = "InvestFlag";
				break;
			case 13:
				strFieldName = "BonusFlag";
				break;
			case 14:
				strFieldName = "BonusMode";
				break;
			case 15:
				strFieldName = "ListFlag";
				break;
			case 16:
				strFieldName = "SubRiskFlag";
				break;
			case 17:
				strFieldName = "CalDigital";
				break;
			case 18:
				strFieldName = "CalChoMode";
				break;
			case 19:
				strFieldName = "RiskAmntMult";
				break;
			case 20:
				strFieldName = "InsuPeriodFlag";
				break;
			case 21:
				strFieldName = "MaxEndPeriod";
				break;
			case 22:
				strFieldName = "AgeLmt";
				break;
			case 23:
				strFieldName = "SignDateCalMode";
				break;
			case 24:
				strFieldName = "ProtocolFlag";
				break;
			case 25:
				strFieldName = "GetChgFlag";
				break;
			case 26:
				strFieldName = "ProtocolPayFlag";
				break;
			case 27:
				strFieldName = "EnsuPlanFlag";
				break;
			case 28:
				strFieldName = "EnsuPlanAdjFlag";
				break;
			case 29:
				strFieldName = "StartDate";
				break;
			case 30:
				strFieldName = "EndDate";
				break;
			case 31:
				strFieldName = "MinAppntAge";
				break;
			case 32:
				strFieldName = "MaxAppntAge";
				break;
			case 33:
				strFieldName = "MaxInsuredAge";
				break;
			case 34:
				strFieldName = "MinInsuredAge";
				break;
			case 35:
				strFieldName = "AppInterest";
				break;
			case 36:
				strFieldName = "AppPremRate";
				break;
			case 37:
				strFieldName = "InsuredFlag";
				break;
			case 38:
				strFieldName = "ShareFlag";
				break;
			case 39:
				strFieldName = "BnfFlag";
				break;
			case 40:
				strFieldName = "TempPayFlag";
				break;
			case 41:
				strFieldName = "InpPayPlan";
				break;
			case 42:
				strFieldName = "ImpartFlag";
				break;
			case 43:
				strFieldName = "InsuExpeFlag";
				break;
			case 44:
				strFieldName = "LoanFlag";
				break;
			case 45:
				strFieldName = "MortagageFlag";
				break;
			case 46:
				strFieldName = "IDifReturnFlag";
				break;
			case 47:
				strFieldName = "CutAmntStopPay";
				break;
			case 48:
				strFieldName = "RinsRate";
				break;
			case 49:
				strFieldName = "SaleFlag";
				break;
			case 50:
				strFieldName = "FileAppFlag";
				break;
			case 51:
				strFieldName = "MngCom";
				break;
			case 52:
				strFieldName = "AutoPayFlag";
				break;
			case 53:
				strFieldName = "NeedPrintHospital";
				break;
			case 54:
				strFieldName = "NeedPrintGet";
				break;
			case 55:
				strFieldName = "RiskType3";
				break;
			case 56:
				strFieldName = "RiskType4";
				break;
			case 57:
				strFieldName = "RiskType5";
				break;
			case 58:
				strFieldName = "NotPrintPol";
				break;
			case 59:
				strFieldName = "NeedGetPolDate";
				break;
			case 60:
				strFieldName = "NeedReReadBank";
				break;
			case 61:
				strFieldName = "SpecFlag";
				break;
			case 62:
				strFieldName = "InterestDifFlag";
				break;
			case 63:
				strFieldName = "RiskType6";
				break;
			case 64:
				strFieldName = "RiskType7";
				break;
			case 65:
				strFieldName = "RiskType8";
				break;
			case 66:
				strFieldName = "RiskType9";
				break;
			case 67:
				strFieldName = "Operator";
				break;
			case 68:
				strFieldName = "MakeDate";
				break;
			case 69:
				strFieldName = "MakeTime";
				break;
			case 70:
				strFieldName = "ModifyDate";
				break;
			case 71:
				strFieldName = "ModifyTime";
				break;
			case 72:
				strFieldName = "Standbyflag1";
				break;
			case 73:
				strFieldName = "Standbyflag2";
				break;
			case 74:
				strFieldName = "Standbyflag3";
				break;
			case 75:
				strFieldName = "Standbyflag4";
				break;
			case 76:
				strFieldName = "Standbyflag5";
				break;
			case 77:
				strFieldName = "Standbyflag6";
				break;
			case 78:
				strFieldName = "CancleForeGetSpecFlag";
				break;
			case 79:
				strFieldName = "SignDateCalMode2";
				break;
			case 80:
				strFieldName = "SignDateCalMode3";
				break;
			case 81:
				strFieldName = "HealthType";
				break;
			case 82:
				strFieldName = "RiskTypeAcc";
				break;
			case 83:
				strFieldName = "RiskPeriodAcc";
				break;
			case 84:
				strFieldName = "AutoPayType";
				break;
			case 85:
				strFieldName = "AutoETIFlag";
				break;
			case 86:
				strFieldName = "AutoETIType";
				break;
			case 87:
				strFieldName = "AutoCTFlag";
				break;
			case 88:
				strFieldName = "NonParFlag";
				break;
			case 89:
				strFieldName = "BackDateFlag";
				break;
			case 90:
				strFieldName = "HoneymoonFlag";
				break;
			case 91:
				strFieldName = "NLGFlag";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskProp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskTypeDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InvestFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ListFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalDigital") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalChoMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmntMult") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InsuPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AgeLmt") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SignDateCalMode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ProtocolFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetChgFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProtocolPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnsuPlanFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnsuPlanAdjFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MinAppntAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxAppntAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxInsuredAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MinInsuredAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AppPremRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShareFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InpPayPlan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuExpeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoanFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MortagageFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDifReturnFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutAmntStopPay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RinsRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SaleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileAppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedPrintHospital") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedPrintGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NotPrintPol") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedGetPolDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedReReadBank") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterestDifFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType9") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CancleForeGetSpecFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDateCalMode2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SignDateCalMode3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HealthType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskTypeAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskPeriodAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoPayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoETIFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoETIType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AutoCTFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NonParFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HoneymoonFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NLGFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_INT;
				break;
			case 32:
				nFieldType = Schema.TYPE_INT;
				break;
			case 33:
				nFieldType = Schema.TYPE_INT;
				break;
			case 34:
				nFieldType = Schema.TYPE_INT;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 48:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 62:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 63:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 64:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 65:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 66:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 67:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 68:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 69:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 70:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 71:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 72:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 73:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 74:
				nFieldType = Schema.TYPE_INT;
				break;
			case 75:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 80:
				nFieldType = Schema.TYPE_INT;
				break;
			case 81:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 82:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 83:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 84:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 85:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 86:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 87:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 88:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
