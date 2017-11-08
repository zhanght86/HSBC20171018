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
import com.sinosoft.lis.db.PD_LMDutyGetAliveDB;

/*
 * <p>ClassName: PD_LMDutyGetAliveSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PD_LMDutyGetAliveSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMDutyGetAliveSchema.class);

	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付名称 */
	private String GetDutyName;
	/** 算法编码 */
	private String CalCode;
	/** 给付间隔 */
	private int GetIntv;
	/** 默认值 */
	private double DefaultVal;
	/** 反算算法 */
	private String CnterCalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 起领期间 */
	private int GetStartPeriod;
	/** 起领期间单位 */
	private String GetStartUnit;
	/** 起领日期计算参照 */
	private String StartDateCalRef;
	/** 起领日期计算方式 */
	private String StartDateCalMode;
	/** 起领期间上限 */
	private int MinGetStartPeriod;
	/** 止领期间 */
	private int GetEndPeriod;
	/** 止领期间单位 */
	private String GetEndUnit;
	/** 止领日期计算参照 */
	private String EndDateCalRef;
	/** 止领日期计算方式 */
	private String EndDateCalMode;
	/** 止领期间下限 */
	private int MaxGetEndPeriod;
	/** 递增标记 */
	private String AddFlag;
	/** 递增间隔 */
	private int AddIntv;
	/** 递增开始期间 */
	private int AddStartPeriod;
	/** 递增开始期间单位 */
	private String AddStartUnit;
	/** 递增终止期间 */
	private int AddEndPeriod;
	/** 递增终止期间单位 */
	private String AddEndUnit;
	/** 递增类型 */
	private String AddType;
	/** 递增值 */
	private double AddValue;
	/** 给付最大次数 */
	private int MaxGetCount;
	/** 给付后动作 */
	private String AfterGet;
	/** 领取动作类型 */
	private String GetActionType;
	/** 催付标记 */
	private String UrgeGetFlag;
	/** 现金领取标记 */
	private String DiscntFlag;
	/** 领取条件 */
	private String GetCond;
	/** 给付最大次数类型 */
	private String MaxGetCountType;
	/** 领取时是否需要重新计算 */
	private String NeedReCompute;
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

	public static final int FIELDNUM = 45;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyGetAliveSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";

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
		PD_LMDutyGetAliveSchema cloned = (PD_LMDutyGetAliveSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>6)
			throw new IllegalArgumentException("给付代码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值6");
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 设置不同的领取生存领取方式。 编码规则：按流水号排序 如： 000001 000002 。。。
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		if(aGetDutyName!=null && aGetDutyName.length()>120)
			throw new IllegalArgumentException("给付名称GetDutyName值"+aGetDutyName+"的长度"+aGetDutyName.length()+"大于最大值120");
		GetDutyName = aGetDutyName;
	}
	/**
	* 1-3位：险种号码 4位：业务类型（该位不一定按照此规则来编写，现在赔付都是3） 1 承保 2 领取 3 赔付 4 现金价值 5 单证描述 5-6位 顺序号
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		if(aCalCode!=null && aCalCode.length()>10)
			throw new IllegalArgumentException("算法编码CalCode值"+aCalCode+"的长度"+aCalCode.length()+"大于最大值10");
		CalCode = aCalCode;
	}
	public int getGetIntv()
	{
		return GetIntv;
	}
	public void setGetIntv(int aGetIntv)
	{
		GetIntv = aGetIntv;
	}
	public void setGetIntv(String aGetIntv)
	{
		if (aGetIntv != null && !aGetIntv.equals(""))
		{
			Integer tInteger = new Integer(aGetIntv);
			int i = tInteger.intValue();
			GetIntv = i;
		}
	}

	public double getDefaultVal()
	{
		return DefaultVal;
	}
	public void setDefaultVal(double aDefaultVal)
	{
		DefaultVal = aDefaultVal;
	}
	public void setDefaultVal(String aDefaultVal)
	{
		if (aDefaultVal != null && !aDefaultVal.equals(""))
		{
			Double tDouble = new Double(aDefaultVal);
			double d = tDouble.doubleValue();
			DefaultVal = d;
		}
	}

	public String getCnterCalCode()
	{
		return CnterCalCode;
	}
	public void setCnterCalCode(String aCnterCalCode)
	{
		if(aCnterCalCode!=null && aCnterCalCode.length()>10)
			throw new IllegalArgumentException("反算算法CnterCalCode值"+aCnterCalCode+"的长度"+aCnterCalCode.length()+"大于最大值10");
		CnterCalCode = aCnterCalCode;
	}
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		if(aOthCalCode!=null && aOthCalCode.length()>10)
			throw new IllegalArgumentException("其他算法OthCalCode值"+aOthCalCode+"的长度"+aOthCalCode.length()+"大于最大值10");
		OthCalCode = aOthCalCode;
	}
	public int getGetStartPeriod()
	{
		return GetStartPeriod;
	}
	public void setGetStartPeriod(int aGetStartPeriod)
	{
		GetStartPeriod = aGetStartPeriod;
	}
	public void setGetStartPeriod(String aGetStartPeriod)
	{
		if (aGetStartPeriod != null && !aGetStartPeriod.equals(""))
		{
			Integer tInteger = new Integer(aGetStartPeriod);
			int i = tInteger.intValue();
			GetStartPeriod = i;
		}
	}

	/**
	* M--年、Y--月、D--日、A--年龄
	*/
	public String getGetStartUnit()
	{
		return GetStartUnit;
	}
	public void setGetStartUnit(String aGetStartUnit)
	{
		if(aGetStartUnit!=null && aGetStartUnit.length()>1)
			throw new IllegalArgumentException("起领期间单位GetStartUnit值"+aGetStartUnit+"的长度"+aGetStartUnit.length()+"大于最大值1");
		GetStartUnit = aGetStartUnit;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getStartDateCalRef()
	{
		return StartDateCalRef;
	}
	public void setStartDateCalRef(String aStartDateCalRef)
	{
		if(aStartDateCalRef!=null && aStartDateCalRef.length()>1)
			throw new IllegalArgumentException("起领日期计算参照StartDateCalRef值"+aStartDateCalRef+"的长度"+aStartDateCalRef.length()+"大于最大值1");
		StartDateCalRef = aStartDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号、3 - 取缴费终止日期
	*/
	public String getStartDateCalMode()
	{
		return StartDateCalMode;
	}
	public void setStartDateCalMode(String aStartDateCalMode)
	{
		if(aStartDateCalMode!=null && aStartDateCalMode.length()>1)
			throw new IllegalArgumentException("起领日期计算方式StartDateCalMode值"+aStartDateCalMode+"的长度"+aStartDateCalMode.length()+"大于最大值1");
		StartDateCalMode = aStartDateCalMode;
	}
	public int getMinGetStartPeriod()
	{
		return MinGetStartPeriod;
	}
	public void setMinGetStartPeriod(int aMinGetStartPeriod)
	{
		MinGetStartPeriod = aMinGetStartPeriod;
	}
	public void setMinGetStartPeriod(String aMinGetStartPeriod)
	{
		if (aMinGetStartPeriod != null && !aMinGetStartPeriod.equals(""))
		{
			Integer tInteger = new Integer(aMinGetStartPeriod);
			int i = tInteger.intValue();
			MinGetStartPeriod = i;
		}
	}

	public int getGetEndPeriod()
	{
		return GetEndPeriod;
	}
	public void setGetEndPeriod(int aGetEndPeriod)
	{
		GetEndPeriod = aGetEndPeriod;
	}
	public void setGetEndPeriod(String aGetEndPeriod)
	{
		if (aGetEndPeriod != null && !aGetEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aGetEndPeriod);
			int i = tInteger.intValue();
			GetEndPeriod = i;
		}
	}

	/**
	* M--年、Y--月、D--日、A--年龄
	*/
	public String getGetEndUnit()
	{
		return GetEndUnit;
	}
	public void setGetEndUnit(String aGetEndUnit)
	{
		if(aGetEndUnit!=null && aGetEndUnit.length()>1)
			throw new IllegalArgumentException("止领期间单位GetEndUnit值"+aGetEndUnit+"的长度"+aGetEndUnit.length()+"大于最大值1");
		GetEndUnit = aGetEndUnit;
	}
	/**
	* S - 起保日期对应日(StartInsuDate)、B - 出生日期对应日(Birthday)、C - 参考保单选择(Choose)
	*/
	public String getEndDateCalRef()
	{
		return EndDateCalRef;
	}
	public void setEndDateCalRef(String aEndDateCalRef)
	{
		if(aEndDateCalRef!=null && aEndDateCalRef.length()>1)
			throw new IllegalArgumentException("止领日期计算参照EndDateCalRef值"+aEndDateCalRef+"的长度"+aEndDateCalRef.length()+"大于最大值1");
		EndDateCalRef = aEndDateCalRef;
	}
	/**
	* 0 - 以计算为准、1 - 取计算后当月一号、2 - 取计算后当年一号
	*/
	public String getEndDateCalMode()
	{
		return EndDateCalMode;
	}
	public void setEndDateCalMode(String aEndDateCalMode)
	{
		if(aEndDateCalMode!=null && aEndDateCalMode.length()>1)
			throw new IllegalArgumentException("止领日期计算方式EndDateCalMode值"+aEndDateCalMode+"的长度"+aEndDateCalMode.length()+"大于最大值1");
		EndDateCalMode = aEndDateCalMode;
	}
	public int getMaxGetEndPeriod()
	{
		return MaxGetEndPeriod;
	}
	public void setMaxGetEndPeriod(int aMaxGetEndPeriod)
	{
		MaxGetEndPeriod = aMaxGetEndPeriod;
	}
	public void setMaxGetEndPeriod(String aMaxGetEndPeriod)
	{
		if (aMaxGetEndPeriod != null && !aMaxGetEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aMaxGetEndPeriod);
			int i = tInteger.intValue();
			MaxGetEndPeriod = i;
		}
	}

	/**
	* Y--是、N--否
	*/
	public String getAddFlag()
	{
		return AddFlag;
	}
	public void setAddFlag(String aAddFlag)
	{
		if(aAddFlag!=null && aAddFlag.length()>1)
			throw new IllegalArgumentException("递增标记AddFlag值"+aAddFlag+"的长度"+aAddFlag.length()+"大于最大值1");
		AddFlag = aAddFlag;
	}
	public int getAddIntv()
	{
		return AddIntv;
	}
	public void setAddIntv(int aAddIntv)
	{
		AddIntv = aAddIntv;
	}
	public void setAddIntv(String aAddIntv)
	{
		if (aAddIntv != null && !aAddIntv.equals(""))
		{
			Integer tInteger = new Integer(aAddIntv);
			int i = tInteger.intValue();
			AddIntv = i;
		}
	}

	public int getAddStartPeriod()
	{
		return AddStartPeriod;
	}
	public void setAddStartPeriod(int aAddStartPeriod)
	{
		AddStartPeriod = aAddStartPeriod;
	}
	public void setAddStartPeriod(String aAddStartPeriod)
	{
		if (aAddStartPeriod != null && !aAddStartPeriod.equals(""))
		{
			Integer tInteger = new Integer(aAddStartPeriod);
			int i = tInteger.intValue();
			AddStartPeriod = i;
		}
	}

	/**
	* 0:递增开始年龄； 1:领取递增开始年期,领取后多少年开始递增； 2:投保递增开始年期,投保后多少年递增
	*/
	public String getAddStartUnit()
	{
		return AddStartUnit;
	}
	public void setAddStartUnit(String aAddStartUnit)
	{
		if(aAddStartUnit!=null && aAddStartUnit.length()>1)
			throw new IllegalArgumentException("递增开始期间单位AddStartUnit值"+aAddStartUnit+"的长度"+aAddStartUnit.length()+"大于最大值1");
		AddStartUnit = aAddStartUnit;
	}
	public int getAddEndPeriod()
	{
		return AddEndPeriod;
	}
	public void setAddEndPeriod(int aAddEndPeriod)
	{
		AddEndPeriod = aAddEndPeriod;
	}
	public void setAddEndPeriod(String aAddEndPeriod)
	{
		if (aAddEndPeriod != null && !aAddEndPeriod.equals(""))
		{
			Integer tInteger = new Integer(aAddEndPeriod);
			int i = tInteger.intValue();
			AddEndPeriod = i;
		}
	}

	/**
	* Y--年、M--月、D--日、A--年龄
	*/
	public String getAddEndUnit()
	{
		return AddEndUnit;
	}
	public void setAddEndUnit(String aAddEndUnit)
	{
		if(aAddEndUnit!=null && aAddEndUnit.length()>1)
			throw new IllegalArgumentException("递增终止期间单位AddEndUnit值"+aAddEndUnit+"的长度"+aAddEndUnit.length()+"大于最大值1");
		AddEndUnit = aAddEndUnit;
	}
	/**
	* R--按百分比算术递增(Rate) V--按金额算术递增(Value) G--几何递增
	*/
	public String getAddType()
	{
		return AddType;
	}
	public void setAddType(String aAddType)
	{
		if(aAddType!=null && aAddType.length()>1)
			throw new IllegalArgumentException("递增类型AddType值"+aAddType+"的长度"+aAddType.length()+"大于最大值1");
		AddType = aAddType;
	}
	public double getAddValue()
	{
		return AddValue;
	}
	public void setAddValue(double aAddValue)
	{
		AddValue = aAddValue;
	}
	public void setAddValue(String aAddValue)
	{
		if (aAddValue != null && !aAddValue.equals(""))
		{
			Double tDouble = new Double(aAddValue);
			double d = tDouble.doubleValue();
			AddValue = d;
		}
	}

	public int getMaxGetCount()
	{
		return MaxGetCount;
	}
	public void setMaxGetCount(int aMaxGetCount)
	{
		MaxGetCount = aMaxGetCount;
	}
	public void setMaxGetCount(String aMaxGetCount)
	{
		if (aMaxGetCount != null && !aMaxGetCount.equals(""))
		{
			Integer tInteger = new Integer(aMaxGetCount);
			int i = tInteger.intValue();
			MaxGetCount = i;
		}
	}

	/**
	* #"000" 无动作# "001" 保额递减（暂不用）#"002" 保额递增（暂不用）# "003" 无条件销户 # "004" 最后一次给付销户
	*/
	public String getAfterGet()
	{
		return AfterGet;
	}
	public void setAfterGet(String aAfterGet)
	{
		if(aAfterGet!=null && aAfterGet.length()>3)
			throw new IllegalArgumentException("给付后动作AfterGet值"+aAfterGet+"的长度"+aAfterGet.length()+"大于最大值3");
		AfterGet = aAfterGet;
	}
	/**
	* 0--可领可不领、1--必须领
	*/
	public String getGetActionType()
	{
		return GetActionType;
	}
	public void setGetActionType(String aGetActionType)
	{
		if(aGetActionType!=null && aGetActionType.length()>1)
			throw new IllegalArgumentException("领取动作类型GetActionType值"+aGetActionType+"的长度"+aGetActionType.length()+"大于最大值1");
		GetActionType = aGetActionType;
	}
	/**
	* Y--发催收、N--不发催收
	*/
	public String getUrgeGetFlag()
	{
		return UrgeGetFlag;
	}
	public void setUrgeGetFlag(String aUrgeGetFlag)
	{
		if(aUrgeGetFlag!=null && aUrgeGetFlag.length()>1)
			throw new IllegalArgumentException("催付标记UrgeGetFlag值"+aUrgeGetFlag+"的长度"+aUrgeGetFlag.length()+"大于最大值1");
		UrgeGetFlag = aUrgeGetFlag;
	}
	/**
	* 1 只能现金领取 0 可以选择等 Y--允许、N--不允许
	*/
	public String getDiscntFlag()
	{
		return DiscntFlag;
	}
	public void setDiscntFlag(String aDiscntFlag)
	{
		if(aDiscntFlag!=null && aDiscntFlag.length()>1)
			throw new IllegalArgumentException("现金领取标记DiscntFlag值"+aDiscntFlag+"的长度"+aDiscntFlag.length()+"大于最大值1");
		DiscntFlag = aDiscntFlag;
	}
	/**
	* 1 交费未交至起领日期可以领取(宽限期内) 0 交费未交至起领日期不能领取
	*/
	public String getGetCond()
	{
		return GetCond;
	}
	public void setGetCond(String aGetCond)
	{
		if(aGetCond!=null && aGetCond.length()>1)
			throw new IllegalArgumentException("领取条件GetCond值"+aGetCond+"的长度"+aGetCond.length()+"大于最大值1");
		GetCond = aGetCond;
	}
	/**
	* 0 无条件给付最大次数 1 无条件给付最大年龄数 2 被保人死亡给付最大次数 3 被保人死亡给付最大年龄数 4 投保人死亡给付最大次数 5 投保人死亡给付最大年龄数
	*/
	public String getMaxGetCountType()
	{
		return MaxGetCountType;
	}
	public void setMaxGetCountType(String aMaxGetCountType)
	{
		if(aMaxGetCountType!=null && aMaxGetCountType.length()>1)
			throw new IllegalArgumentException("给付最大次数类型MaxGetCountType值"+aMaxGetCountType+"的长度"+aMaxGetCountType.length()+"大于最大值1");
		MaxGetCountType = aMaxGetCountType;
	}
	/**
	* 0 －－ 领取时不需要重新计算 1 －－ 领取时需要重新计算
	*/
	public String getNeedReCompute()
	{
		return NeedReCompute;
	}
	public void setNeedReCompute(String aNeedReCompute)
	{
		if(aNeedReCompute!=null && aNeedReCompute.length()>1)
			throw new IllegalArgumentException("领取时是否需要重新计算NeedReCompute值"+aNeedReCompute+"的长度"+aNeedReCompute.length()+"大于最大值1");
		NeedReCompute = aNeedReCompute;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		if(aStandbyflag1!=null && aStandbyflag1.length()>20)
			throw new IllegalArgumentException("Standbyflag1Standbyflag1值"+aStandbyflag1+"的长度"+aStandbyflag1.length()+"大于最大值20");
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		if(aStandbyflag2!=null && aStandbyflag2.length()>20)
			throw new IllegalArgumentException("Standbyflag2Standbyflag2值"+aStandbyflag2+"的长度"+aStandbyflag2.length()+"大于最大值20");
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


	/**
	* 使用另外一个 PD_LMDutyGetAliveSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyGetAliveSchema PD_LMDutyGetAliveSchema
	**/
	public void setSchema(PD_LMDutyGetAliveSchema aPD_LMDutyGetAliveSchema)
	{
		this.GetDutyCode = aPD_LMDutyGetAliveSchema.getGetDutyCode();
		this.GetDutyKind = aPD_LMDutyGetAliveSchema.getGetDutyKind();
		this.GetDutyName = aPD_LMDutyGetAliveSchema.getGetDutyName();
		this.CalCode = aPD_LMDutyGetAliveSchema.getCalCode();
		this.GetIntv = aPD_LMDutyGetAliveSchema.getGetIntv();
		this.DefaultVal = aPD_LMDutyGetAliveSchema.getDefaultVal();
		this.CnterCalCode = aPD_LMDutyGetAliveSchema.getCnterCalCode();
		this.OthCalCode = aPD_LMDutyGetAliveSchema.getOthCalCode();
		this.GetStartPeriod = aPD_LMDutyGetAliveSchema.getGetStartPeriod();
		this.GetStartUnit = aPD_LMDutyGetAliveSchema.getGetStartUnit();
		this.StartDateCalRef = aPD_LMDutyGetAliveSchema.getStartDateCalRef();
		this.StartDateCalMode = aPD_LMDutyGetAliveSchema.getStartDateCalMode();
		this.MinGetStartPeriod = aPD_LMDutyGetAliveSchema.getMinGetStartPeriod();
		this.GetEndPeriod = aPD_LMDutyGetAliveSchema.getGetEndPeriod();
		this.GetEndUnit = aPD_LMDutyGetAliveSchema.getGetEndUnit();
		this.EndDateCalRef = aPD_LMDutyGetAliveSchema.getEndDateCalRef();
		this.EndDateCalMode = aPD_LMDutyGetAliveSchema.getEndDateCalMode();
		this.MaxGetEndPeriod = aPD_LMDutyGetAliveSchema.getMaxGetEndPeriod();
		this.AddFlag = aPD_LMDutyGetAliveSchema.getAddFlag();
		this.AddIntv = aPD_LMDutyGetAliveSchema.getAddIntv();
		this.AddStartPeriod = aPD_LMDutyGetAliveSchema.getAddStartPeriod();
		this.AddStartUnit = aPD_LMDutyGetAliveSchema.getAddStartUnit();
		this.AddEndPeriod = aPD_LMDutyGetAliveSchema.getAddEndPeriod();
		this.AddEndUnit = aPD_LMDutyGetAliveSchema.getAddEndUnit();
		this.AddType = aPD_LMDutyGetAliveSchema.getAddType();
		this.AddValue = aPD_LMDutyGetAliveSchema.getAddValue();
		this.MaxGetCount = aPD_LMDutyGetAliveSchema.getMaxGetCount();
		this.AfterGet = aPD_LMDutyGetAliveSchema.getAfterGet();
		this.GetActionType = aPD_LMDutyGetAliveSchema.getGetActionType();
		this.UrgeGetFlag = aPD_LMDutyGetAliveSchema.getUrgeGetFlag();
		this.DiscntFlag = aPD_LMDutyGetAliveSchema.getDiscntFlag();
		this.GetCond = aPD_LMDutyGetAliveSchema.getGetCond();
		this.MaxGetCountType = aPD_LMDutyGetAliveSchema.getMaxGetCountType();
		this.NeedReCompute = aPD_LMDutyGetAliveSchema.getNeedReCompute();
		this.Operator = aPD_LMDutyGetAliveSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyGetAliveSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyGetAliveSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyGetAliveSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyGetAliveSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyGetAliveSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyGetAliveSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyGetAliveSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyGetAliveSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyGetAliveSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyGetAliveSchema.getStandbyflag6();
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
			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			this.GetIntv = rs.getInt("GetIntv");
			this.DefaultVal = rs.getDouble("DefaultVal");
			if( rs.getString("CnterCalCode") == null )
				this.CnterCalCode = null;
			else
				this.CnterCalCode = rs.getString("CnterCalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			this.GetStartPeriod = rs.getInt("GetStartPeriod");
			if( rs.getString("GetStartUnit") == null )
				this.GetStartUnit = null;
			else
				this.GetStartUnit = rs.getString("GetStartUnit").trim();

			if( rs.getString("StartDateCalRef") == null )
				this.StartDateCalRef = null;
			else
				this.StartDateCalRef = rs.getString("StartDateCalRef").trim();

			if( rs.getString("StartDateCalMode") == null )
				this.StartDateCalMode = null;
			else
				this.StartDateCalMode = rs.getString("StartDateCalMode").trim();

			this.MinGetStartPeriod = rs.getInt("MinGetStartPeriod");
			this.GetEndPeriod = rs.getInt("GetEndPeriod");
			if( rs.getString("GetEndUnit") == null )
				this.GetEndUnit = null;
			else
				this.GetEndUnit = rs.getString("GetEndUnit").trim();

			if( rs.getString("EndDateCalRef") == null )
				this.EndDateCalRef = null;
			else
				this.EndDateCalRef = rs.getString("EndDateCalRef").trim();

			if( rs.getString("EndDateCalMode") == null )
				this.EndDateCalMode = null;
			else
				this.EndDateCalMode = rs.getString("EndDateCalMode").trim();

			this.MaxGetEndPeriod = rs.getInt("MaxGetEndPeriod");
			if( rs.getString("AddFlag") == null )
				this.AddFlag = null;
			else
				this.AddFlag = rs.getString("AddFlag").trim();

			this.AddIntv = rs.getInt("AddIntv");
			this.AddStartPeriod = rs.getInt("AddStartPeriod");
			if( rs.getString("AddStartUnit") == null )
				this.AddStartUnit = null;
			else
				this.AddStartUnit = rs.getString("AddStartUnit").trim();

			this.AddEndPeriod = rs.getInt("AddEndPeriod");
			if( rs.getString("AddEndUnit") == null )
				this.AddEndUnit = null;
			else
				this.AddEndUnit = rs.getString("AddEndUnit").trim();

			if( rs.getString("AddType") == null )
				this.AddType = null;
			else
				this.AddType = rs.getString("AddType").trim();

			this.AddValue = rs.getDouble("AddValue");
			this.MaxGetCount = rs.getInt("MaxGetCount");
			if( rs.getString("AfterGet") == null )
				this.AfterGet = null;
			else
				this.AfterGet = rs.getString("AfterGet").trim();

			if( rs.getString("GetActionType") == null )
				this.GetActionType = null;
			else
				this.GetActionType = rs.getString("GetActionType").trim();

			if( rs.getString("UrgeGetFlag") == null )
				this.UrgeGetFlag = null;
			else
				this.UrgeGetFlag = rs.getString("UrgeGetFlag").trim();

			if( rs.getString("DiscntFlag") == null )
				this.DiscntFlag = null;
			else
				this.DiscntFlag = rs.getString("DiscntFlag").trim();

			if( rs.getString("GetCond") == null )
				this.GetCond = null;
			else
				this.GetCond = rs.getString("GetCond").trim();

			if( rs.getString("MaxGetCountType") == null )
				this.MaxGetCountType = null;
			else
				this.MaxGetCountType = rs.getString("MaxGetCountType").trim();

			if( rs.getString("NeedReCompute") == null )
				this.NeedReCompute = null;
			else
				this.NeedReCompute = rs.getString("NeedReCompute").trim();

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
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMDutyGetAlive表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetAliveSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyGetAliveSchema getSchema()
	{
		PD_LMDutyGetAliveSchema aPD_LMDutyGetAliveSchema = new PD_LMDutyGetAliveSchema();
		aPD_LMDutyGetAliveSchema.setSchema(this);
		return aPD_LMDutyGetAliveSchema;
	}

	public PD_LMDutyGetAliveDB getDB()
	{
		PD_LMDutyGetAliveDB aDBOper = new PD_LMDutyGetAliveDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetAlive描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultVal));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CnterCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetStartPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetStartUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinGetStartPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetEndUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDateCalRef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxGetEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddStartPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddStartUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddEndPeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddEndUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxGetCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AfterGet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetActionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgeGetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiscntFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MaxGetCountType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedReCompute)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyGetAlive>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			DefaultVal = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			CnterCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetStartPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			GetStartUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StartDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StartDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MinGetStartPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			GetEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			GetEndUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EndDateCalRef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			EndDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MaxGetEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			AddFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AddIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			AddStartPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			AddStartUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AddEndPeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			AddEndUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AddType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AddValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			MaxGetCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			AfterGet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			GetActionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			UrgeGetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			DiscntFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			GetCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			MaxGetCountType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			NeedReCompute = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,42,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,43,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,44,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,45,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyGetAliveSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetIntv));
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultVal));
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CnterCalCode));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("GetStartPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartPeriod));
		}
		if (FCode.equalsIgnoreCase("GetStartUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetStartUnit));
		}
		if (FCode.equalsIgnoreCase("StartDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDateCalRef));
		}
		if (FCode.equalsIgnoreCase("StartDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDateCalMode));
		}
		if (FCode.equalsIgnoreCase("MinGetStartPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinGetStartPeriod));
		}
		if (FCode.equalsIgnoreCase("GetEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndPeriod));
		}
		if (FCode.equalsIgnoreCase("GetEndUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetEndUnit));
		}
		if (FCode.equalsIgnoreCase("EndDateCalRef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalRef));
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("MaxGetEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGetEndPeriod));
		}
		if (FCode.equalsIgnoreCase("AddFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFlag));
		}
		if (FCode.equalsIgnoreCase("AddIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddIntv));
		}
		if (FCode.equalsIgnoreCase("AddStartPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddStartPeriod));
		}
		if (FCode.equalsIgnoreCase("AddStartUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddStartUnit));
		}
		if (FCode.equalsIgnoreCase("AddEndPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddEndPeriod));
		}
		if (FCode.equalsIgnoreCase("AddEndUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddEndUnit));
		}
		if (FCode.equalsIgnoreCase("AddType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddType));
		}
		if (FCode.equalsIgnoreCase("AddValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddValue));
		}
		if (FCode.equalsIgnoreCase("MaxGetCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGetCount));
		}
		if (FCode.equalsIgnoreCase("AfterGet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AfterGet));
		}
		if (FCode.equalsIgnoreCase("GetActionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetActionType));
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgeGetFlag));
		}
		if (FCode.equalsIgnoreCase("DiscntFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiscntFlag));
		}
		if (FCode.equalsIgnoreCase("GetCond"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetCond));
		}
		if (FCode.equalsIgnoreCase("MaxGetCountType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxGetCountType));
		}
		if (FCode.equalsIgnoreCase("NeedReCompute"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedReCompute));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 4:
				strFieldValue = String.valueOf(GetIntv);
				break;
			case 5:
				strFieldValue = String.valueOf(DefaultVal);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CnterCalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 8:
				strFieldValue = String.valueOf(GetStartPeriod);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetStartUnit);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StartDateCalRef);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StartDateCalMode);
				break;
			case 12:
				strFieldValue = String.valueOf(MinGetStartPeriod);
				break;
			case 13:
				strFieldValue = String.valueOf(GetEndPeriod);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GetEndUnit);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EndDateCalRef);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(EndDateCalMode);
				break;
			case 17:
				strFieldValue = String.valueOf(MaxGetEndPeriod);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AddFlag);
				break;
			case 19:
				strFieldValue = String.valueOf(AddIntv);
				break;
			case 20:
				strFieldValue = String.valueOf(AddStartPeriod);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AddStartUnit);
				break;
			case 22:
				strFieldValue = String.valueOf(AddEndPeriod);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(AddEndUnit);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AddType);
				break;
			case 25:
				strFieldValue = String.valueOf(AddValue);
				break;
			case 26:
				strFieldValue = String.valueOf(MaxGetCount);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AfterGet);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(GetActionType);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(UrgeGetFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(DiscntFlag);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(GetCond);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(MaxGetCountType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(NeedReCompute);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 41:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 42:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 43:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 44:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("GetIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("DefaultVal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultVal = d;
			}
		}
		if (FCode.equalsIgnoreCase("CnterCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CnterCalCode = FValue.trim();
			}
			else
				CnterCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthCalCode = FValue.trim();
			}
			else
				OthCalCode = null;
		}
		if (FCode.equalsIgnoreCase("GetStartPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetStartPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetStartUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetStartUnit = FValue.trim();
			}
			else
				GetStartUnit = null;
		}
		if (FCode.equalsIgnoreCase("StartDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDateCalRef = FValue.trim();
			}
			else
				StartDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("StartDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDateCalMode = FValue.trim();
			}
			else
				StartDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("MinGetStartPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinGetStartPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GetEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GetEndUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetEndUnit = FValue.trim();
			}
			else
				GetEndUnit = null;
		}
		if (FCode.equalsIgnoreCase("EndDateCalRef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDateCalRef = FValue.trim();
			}
			else
				EndDateCalRef = null;
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDateCalMode = FValue.trim();
			}
			else
				EndDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("MaxGetEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxGetEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("AddFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFlag = FValue.trim();
			}
			else
				AddFlag = null;
		}
		if (FCode.equalsIgnoreCase("AddIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AddIntv = i;
			}
		}
		if (FCode.equalsIgnoreCase("AddStartPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AddStartPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("AddStartUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddStartUnit = FValue.trim();
			}
			else
				AddStartUnit = null;
		}
		if (FCode.equalsIgnoreCase("AddEndPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AddEndPeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("AddEndUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddEndUnit = FValue.trim();
			}
			else
				AddEndUnit = null;
		}
		if (FCode.equalsIgnoreCase("AddType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddType = FValue.trim();
			}
			else
				AddType = null;
		}
		if (FCode.equalsIgnoreCase("AddValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxGetCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxGetCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("AfterGet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AfterGet = FValue.trim();
			}
			else
				AfterGet = null;
		}
		if (FCode.equalsIgnoreCase("GetActionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetActionType = FValue.trim();
			}
			else
				GetActionType = null;
		}
		if (FCode.equalsIgnoreCase("UrgeGetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgeGetFlag = FValue.trim();
			}
			else
				UrgeGetFlag = null;
		}
		if (FCode.equalsIgnoreCase("DiscntFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiscntFlag = FValue.trim();
			}
			else
				DiscntFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetCond"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetCond = FValue.trim();
			}
			else
				GetCond = null;
		}
		if (FCode.equalsIgnoreCase("MaxGetCountType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaxGetCountType = FValue.trim();
			}
			else
				MaxGetCountType = null;
		}
		if (FCode.equalsIgnoreCase("NeedReCompute"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedReCompute = FValue.trim();
			}
			else
				NeedReCompute = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutyGetAliveSchema other = (PD_LMDutyGetAliveSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyName.equals(other.getGetDutyName())
			&& CalCode.equals(other.getCalCode())
			&& GetIntv == other.getGetIntv()
			&& DefaultVal == other.getDefaultVal()
			&& CnterCalCode.equals(other.getCnterCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& GetStartPeriod == other.getGetStartPeriod()
			&& GetStartUnit.equals(other.getGetStartUnit())
			&& StartDateCalRef.equals(other.getStartDateCalRef())
			&& StartDateCalMode.equals(other.getStartDateCalMode())
			&& MinGetStartPeriod == other.getMinGetStartPeriod()
			&& GetEndPeriod == other.getGetEndPeriod()
			&& GetEndUnit.equals(other.getGetEndUnit())
			&& EndDateCalRef.equals(other.getEndDateCalRef())
			&& EndDateCalMode.equals(other.getEndDateCalMode())
			&& MaxGetEndPeriod == other.getMaxGetEndPeriod()
			&& AddFlag.equals(other.getAddFlag())
			&& AddIntv == other.getAddIntv()
			&& AddStartPeriod == other.getAddStartPeriod()
			&& AddStartUnit.equals(other.getAddStartUnit())
			&& AddEndPeriod == other.getAddEndPeriod()
			&& AddEndUnit.equals(other.getAddEndUnit())
			&& AddType.equals(other.getAddType())
			&& AddValue == other.getAddValue()
			&& MaxGetCount == other.getMaxGetCount()
			&& AfterGet.equals(other.getAfterGet())
			&& GetActionType.equals(other.getGetActionType())
			&& UrgeGetFlag.equals(other.getUrgeGetFlag())
			&& DiscntFlag.equals(other.getDiscntFlag())
			&& GetCond.equals(other.getGetCond())
			&& MaxGetCountType.equals(other.getMaxGetCountType())
			&& NeedReCompute.equals(other.getNeedReCompute())
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
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("GetDutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 2;
		}
		if( strFieldName.equals("CalCode") ) {
			return 3;
		}
		if( strFieldName.equals("GetIntv") ) {
			return 4;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return 5;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return 6;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 7;
		}
		if( strFieldName.equals("GetStartPeriod") ) {
			return 8;
		}
		if( strFieldName.equals("GetStartUnit") ) {
			return 9;
		}
		if( strFieldName.equals("StartDateCalRef") ) {
			return 10;
		}
		if( strFieldName.equals("StartDateCalMode") ) {
			return 11;
		}
		if( strFieldName.equals("MinGetStartPeriod") ) {
			return 12;
		}
		if( strFieldName.equals("GetEndPeriod") ) {
			return 13;
		}
		if( strFieldName.equals("GetEndUnit") ) {
			return 14;
		}
		if( strFieldName.equals("EndDateCalRef") ) {
			return 15;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return 16;
		}
		if( strFieldName.equals("MaxGetEndPeriod") ) {
			return 17;
		}
		if( strFieldName.equals("AddFlag") ) {
			return 18;
		}
		if( strFieldName.equals("AddIntv") ) {
			return 19;
		}
		if( strFieldName.equals("AddStartPeriod") ) {
			return 20;
		}
		if( strFieldName.equals("AddStartUnit") ) {
			return 21;
		}
		if( strFieldName.equals("AddEndPeriod") ) {
			return 22;
		}
		if( strFieldName.equals("AddEndUnit") ) {
			return 23;
		}
		if( strFieldName.equals("AddType") ) {
			return 24;
		}
		if( strFieldName.equals("AddValue") ) {
			return 25;
		}
		if( strFieldName.equals("MaxGetCount") ) {
			return 26;
		}
		if( strFieldName.equals("AfterGet") ) {
			return 27;
		}
		if( strFieldName.equals("GetActionType") ) {
			return 28;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return 29;
		}
		if( strFieldName.equals("DiscntFlag") ) {
			return 30;
		}
		if( strFieldName.equals("GetCond") ) {
			return 31;
		}
		if( strFieldName.equals("MaxGetCountType") ) {
			return 32;
		}
		if( strFieldName.equals("NeedReCompute") ) {
			return 33;
		}
		if( strFieldName.equals("Operator") ) {
			return 34;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 35;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 37;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 38;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 39;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 40;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 41;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 42;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 43;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 44;
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
				strFieldName = "GetDutyCode";
				break;
			case 1:
				strFieldName = "GetDutyKind";
				break;
			case 2:
				strFieldName = "GetDutyName";
				break;
			case 3:
				strFieldName = "CalCode";
				break;
			case 4:
				strFieldName = "GetIntv";
				break;
			case 5:
				strFieldName = "DefaultVal";
				break;
			case 6:
				strFieldName = "CnterCalCode";
				break;
			case 7:
				strFieldName = "OthCalCode";
				break;
			case 8:
				strFieldName = "GetStartPeriod";
				break;
			case 9:
				strFieldName = "GetStartUnit";
				break;
			case 10:
				strFieldName = "StartDateCalRef";
				break;
			case 11:
				strFieldName = "StartDateCalMode";
				break;
			case 12:
				strFieldName = "MinGetStartPeriod";
				break;
			case 13:
				strFieldName = "GetEndPeriod";
				break;
			case 14:
				strFieldName = "GetEndUnit";
				break;
			case 15:
				strFieldName = "EndDateCalRef";
				break;
			case 16:
				strFieldName = "EndDateCalMode";
				break;
			case 17:
				strFieldName = "MaxGetEndPeriod";
				break;
			case 18:
				strFieldName = "AddFlag";
				break;
			case 19:
				strFieldName = "AddIntv";
				break;
			case 20:
				strFieldName = "AddStartPeriod";
				break;
			case 21:
				strFieldName = "AddStartUnit";
				break;
			case 22:
				strFieldName = "AddEndPeriod";
				break;
			case 23:
				strFieldName = "AddEndUnit";
				break;
			case 24:
				strFieldName = "AddType";
				break;
			case 25:
				strFieldName = "AddValue";
				break;
			case 26:
				strFieldName = "MaxGetCount";
				break;
			case 27:
				strFieldName = "AfterGet";
				break;
			case 28:
				strFieldName = "GetActionType";
				break;
			case 29:
				strFieldName = "UrgeGetFlag";
				break;
			case 30:
				strFieldName = "DiscntFlag";
				break;
			case 31:
				strFieldName = "GetCond";
				break;
			case 32:
				strFieldName = "MaxGetCountType";
				break;
			case 33:
				strFieldName = "NeedReCompute";
				break;
			case 34:
				strFieldName = "Operator";
				break;
			case 35:
				strFieldName = "MakeDate";
				break;
			case 36:
				strFieldName = "MakeTime";
				break;
			case 37:
				strFieldName = "ModifyDate";
				break;
			case 38:
				strFieldName = "ModifyTime";
				break;
			case 39:
				strFieldName = "Standbyflag1";
				break;
			case 40:
				strFieldName = "Standbyflag2";
				break;
			case 41:
				strFieldName = "Standbyflag3";
				break;
			case 42:
				strFieldName = "Standbyflag4";
				break;
			case 43:
				strFieldName = "Standbyflag5";
				break;
			case 44:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DefaultVal") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CnterCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetStartPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetStartUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MinGetStartPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GetEndUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalRef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxGetEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AddFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AddStartPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AddStartUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddEndPeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AddEndUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxGetCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AfterGet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetActionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgeGetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiscntFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetCond") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxGetCountType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedReCompute") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 42:
				nFieldType = Schema.TYPE_INT;
				break;
			case 43:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 44:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
