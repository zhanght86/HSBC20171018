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
import com.sinosoft.lis.db.LCGrpContProcessDB;

/*
 * <p>ClassName: LCGrpContProcessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCGrpContProcessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpContProcessSchema.class);
	// @Field
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 扫描人 */
	private String ScanOperator;
	/** 扫描日期 */
	private Date ScanDate;
	/** 扫描时间 */
	private String ScanTime;
	/** 投保方案录入人 */
	private String Input1Operator;
	/** 投保方案日期 */
	private Date Input1Date;
	/** 投保方案时间 */
	private String Input1Time;
	/** 投保方案确认日期 */
	private Date Out1Date;
	/** 投保方案确认时间 */
	private String Out1Time;
	/** 人员清单维护人 */
	private String Input2Operator;
	/** 人员清单日期 */
	private Date Input2Date;
	/** 人员清单时间 */
	private String Input2Time;
	/** 人员清单确认日期 */
	private Date Out2Date;
	/** 人员清单确认时间 */
	private String Out2Time;
	/** 录入人 */
	private String Input3Operator;
	/** 录入日期 */
	private Date Input3Date;
	/** 录入时间 */
	private String Input3Time;
	/** 录入完毕日期 */
	private Date Out3Date;
	/** 录入完毕时间 */
	private String Out3Time;
	/** 复核人 */
	private String ApproveOperator;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 人工核保人 */
	private String UWOperator;
	/** 人工核保日期 */
	private Date UWDate;
	/** 人工核保时间 */
	private String UWTime;
	/** 核保结论 */
	private String UWFlag;
	/** 核保订正人 */
	private String BackUWOperator;
	/** 核保订正日期 */
	private Date BackUWDate;
	/** 核保订正时间 */
	private String BackUWTime;
	/** 保单回销日期 */
	private Date GetPolDate;
	/** 保单回销时间 */
	private String GetPolTime;
	/** 保单回执客户签收日期 */
	private Date CustomGetPolDate;
	/** 保单回执客户签收时间 */
	private String CustomGetPolTime;
	/** 财务确认日期 */
	private Date ConfDate;
	/** 财务确认人 */
	private String ConfOperator;
	/** 保单签发人员 */
	private String SignOperator;
	/** 保单签发日期 */
	private Date SignDate;
	/** 保单签发时间 */
	private String SignTime;
	/** 时效天数(工作日) */
	private int AcceptWorkdays;

	public static final int FIELDNUM = 40;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpContProcessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpPropNo";
		pk[1] = "GrpContNo";

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
		LCGrpContProcessSchema cloned = (LCGrpContProcessSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
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
	public String getScanOperator()
	{
		return ScanOperator;
	}
	public void setScanOperator(String aScanOperator)
	{
		if(aScanOperator!=null && aScanOperator.length()>30)
			throw new IllegalArgumentException("扫描人ScanOperator值"+aScanOperator+"的长度"+aScanOperator.length()+"大于最大值30");
		ScanOperator = aScanOperator;
	}
	public String getScanDate()
	{
		if( ScanDate != null )
			return fDate.getString(ScanDate);
		else
			return null;
	}
	public void setScanDate(Date aScanDate)
	{
		ScanDate = aScanDate;
	}
	public void setScanDate(String aScanDate)
	{
		if (aScanDate != null && !aScanDate.equals("") )
		{
			ScanDate = fDate.getDate( aScanDate );
		}
		else
			ScanDate = null;
	}

	public String getScanTime()
	{
		return ScanTime;
	}
	public void setScanTime(String aScanTime)
	{
		if(aScanTime!=null && aScanTime.length()>8)
			throw new IllegalArgumentException("扫描时间ScanTime值"+aScanTime+"的长度"+aScanTime.length()+"大于最大值8");
		ScanTime = aScanTime;
	}
	public String getInput1Operator()
	{
		return Input1Operator;
	}
	public void setInput1Operator(String aInput1Operator)
	{
		if(aInput1Operator!=null && aInput1Operator.length()>30)
			throw new IllegalArgumentException("投保方案录入人Input1Operator值"+aInput1Operator+"的长度"+aInput1Operator.length()+"大于最大值30");
		Input1Operator = aInput1Operator;
	}
	public String getInput1Date()
	{
		if( Input1Date != null )
			return fDate.getString(Input1Date);
		else
			return null;
	}
	public void setInput1Date(Date aInput1Date)
	{
		Input1Date = aInput1Date;
	}
	public void setInput1Date(String aInput1Date)
	{
		if (aInput1Date != null && !aInput1Date.equals("") )
		{
			Input1Date = fDate.getDate( aInput1Date );
		}
		else
			Input1Date = null;
	}

	public String getInput1Time()
	{
		return Input1Time;
	}
	public void setInput1Time(String aInput1Time)
	{
		if(aInput1Time!=null && aInput1Time.length()>8)
			throw new IllegalArgumentException("投保方案时间Input1Time值"+aInput1Time+"的长度"+aInput1Time.length()+"大于最大值8");
		Input1Time = aInput1Time;
	}
	public String getOut1Date()
	{
		if( Out1Date != null )
			return fDate.getString(Out1Date);
		else
			return null;
	}
	public void setOut1Date(Date aOut1Date)
	{
		Out1Date = aOut1Date;
	}
	public void setOut1Date(String aOut1Date)
	{
		if (aOut1Date != null && !aOut1Date.equals("") )
		{
			Out1Date = fDate.getDate( aOut1Date );
		}
		else
			Out1Date = null;
	}

	public String getOut1Time()
	{
		return Out1Time;
	}
	public void setOut1Time(String aOut1Time)
	{
		if(aOut1Time!=null && aOut1Time.length()>8)
			throw new IllegalArgumentException("投保方案确认时间Out1Time值"+aOut1Time+"的长度"+aOut1Time.length()+"大于最大值8");
		Out1Time = aOut1Time;
	}
	public String getInput2Operator()
	{
		return Input2Operator;
	}
	public void setInput2Operator(String aInput2Operator)
	{
		if(aInput2Operator!=null && aInput2Operator.length()>30)
			throw new IllegalArgumentException("人员清单维护人Input2Operator值"+aInput2Operator+"的长度"+aInput2Operator.length()+"大于最大值30");
		Input2Operator = aInput2Operator;
	}
	public String getInput2Date()
	{
		if( Input2Date != null )
			return fDate.getString(Input2Date);
		else
			return null;
	}
	public void setInput2Date(Date aInput2Date)
	{
		Input2Date = aInput2Date;
	}
	public void setInput2Date(String aInput2Date)
	{
		if (aInput2Date != null && !aInput2Date.equals("") )
		{
			Input2Date = fDate.getDate( aInput2Date );
		}
		else
			Input2Date = null;
	}

	public String getInput2Time()
	{
		return Input2Time;
	}
	public void setInput2Time(String aInput2Time)
	{
		if(aInput2Time!=null && aInput2Time.length()>8)
			throw new IllegalArgumentException("人员清单时间Input2Time值"+aInput2Time+"的长度"+aInput2Time.length()+"大于最大值8");
		Input2Time = aInput2Time;
	}
	public String getOut2Date()
	{
		if( Out2Date != null )
			return fDate.getString(Out2Date);
		else
			return null;
	}
	public void setOut2Date(Date aOut2Date)
	{
		Out2Date = aOut2Date;
	}
	public void setOut2Date(String aOut2Date)
	{
		if (aOut2Date != null && !aOut2Date.equals("") )
		{
			Out2Date = fDate.getDate( aOut2Date );
		}
		else
			Out2Date = null;
	}

	public String getOut2Time()
	{
		return Out2Time;
	}
	public void setOut2Time(String aOut2Time)
	{
		if(aOut2Time!=null && aOut2Time.length()>8)
			throw new IllegalArgumentException("人员清单确认时间Out2Time值"+aOut2Time+"的长度"+aOut2Time.length()+"大于最大值8");
		Out2Time = aOut2Time;
	}
	public String getInput3Operator()
	{
		return Input3Operator;
	}
	public void setInput3Operator(String aInput3Operator)
	{
		if(aInput3Operator!=null && aInput3Operator.length()>30)
			throw new IllegalArgumentException("录入人Input3Operator值"+aInput3Operator+"的长度"+aInput3Operator.length()+"大于最大值30");
		Input3Operator = aInput3Operator;
	}
	public String getInput3Date()
	{
		if( Input3Date != null )
			return fDate.getString(Input3Date);
		else
			return null;
	}
	public void setInput3Date(Date aInput3Date)
	{
		Input3Date = aInput3Date;
	}
	public void setInput3Date(String aInput3Date)
	{
		if (aInput3Date != null && !aInput3Date.equals("") )
		{
			Input3Date = fDate.getDate( aInput3Date );
		}
		else
			Input3Date = null;
	}

	public String getInput3Time()
	{
		return Input3Time;
	}
	public void setInput3Time(String aInput3Time)
	{
		if(aInput3Time!=null && aInput3Time.length()>8)
			throw new IllegalArgumentException("录入时间Input3Time值"+aInput3Time+"的长度"+aInput3Time.length()+"大于最大值8");
		Input3Time = aInput3Time;
	}
	public String getOut3Date()
	{
		if( Out3Date != null )
			return fDate.getString(Out3Date);
		else
			return null;
	}
	public void setOut3Date(Date aOut3Date)
	{
		Out3Date = aOut3Date;
	}
	public void setOut3Date(String aOut3Date)
	{
		if (aOut3Date != null && !aOut3Date.equals("") )
		{
			Out3Date = fDate.getDate( aOut3Date );
		}
		else
			Out3Date = null;
	}

	public String getOut3Time()
	{
		return Out3Time;
	}
	public void setOut3Time(String aOut3Time)
	{
		if(aOut3Time!=null && aOut3Time.length()>8)
			throw new IllegalArgumentException("录入完毕时间Out3Time值"+aOut3Time+"的长度"+aOut3Time.length()+"大于最大值8");
		Out3Time = aOut3Time;
	}
	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		if(aApproveOperator!=null && aApproveOperator.length()>30)
			throw new IllegalArgumentException("复核人ApproveOperator值"+aApproveOperator+"的长度"+aApproveOperator.length()+"大于最大值30");
		ApproveOperator = aApproveOperator;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("复核时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
		ApproveTime = aApproveTime;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>30)
			throw new IllegalArgumentException("人工核保人UWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值30");
		UWOperator = aUWOperator;
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

	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		if(aUWTime!=null && aUWTime.length()>8)
			throw new IllegalArgumentException("人工核保时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
		UWTime = aUWTime;
	}
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>2)
			throw new IllegalArgumentException("核保结论UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值2");
		UWFlag = aUWFlag;
	}
	public String getBackUWOperator()
	{
		return BackUWOperator;
	}
	public void setBackUWOperator(String aBackUWOperator)
	{
		if(aBackUWOperator!=null && aBackUWOperator.length()>30)
			throw new IllegalArgumentException("核保订正人BackUWOperator值"+aBackUWOperator+"的长度"+aBackUWOperator.length()+"大于最大值30");
		BackUWOperator = aBackUWOperator;
	}
	public String getBackUWDate()
	{
		if( BackUWDate != null )
			return fDate.getString(BackUWDate);
		else
			return null;
	}
	public void setBackUWDate(Date aBackUWDate)
	{
		BackUWDate = aBackUWDate;
	}
	public void setBackUWDate(String aBackUWDate)
	{
		if (aBackUWDate != null && !aBackUWDate.equals("") )
		{
			BackUWDate = fDate.getDate( aBackUWDate );
		}
		else
			BackUWDate = null;
	}

	public String getBackUWTime()
	{
		return BackUWTime;
	}
	public void setBackUWTime(String aBackUWTime)
	{
		if(aBackUWTime!=null && aBackUWTime.length()>8)
			throw new IllegalArgumentException("核保订正时间BackUWTime值"+aBackUWTime+"的长度"+aBackUWTime.length()+"大于最大值8");
		BackUWTime = aBackUWTime;
	}
	/**
	* 回销操作日期
	*/
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

	public String getGetPolTime()
	{
		return GetPolTime;
	}
	public void setGetPolTime(String aGetPolTime)
	{
		if(aGetPolTime!=null && aGetPolTime.length()>8)
			throw new IllegalArgumentException("保单回销时间GetPolTime值"+aGetPolTime+"的长度"+aGetPolTime.length()+"大于最大值8");
		GetPolTime = aGetPolTime;
	}
	/**
	* 客户签字日期
	*/
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

	public String getCustomGetPolTime()
	{
		return CustomGetPolTime;
	}
	public void setCustomGetPolTime(String aCustomGetPolTime)
	{
		if(aCustomGetPolTime!=null && aCustomGetPolTime.length()>8)
			throw new IllegalArgumentException("保单回执客户签收时间CustomGetPolTime值"+aCustomGetPolTime+"的长度"+aCustomGetPolTime.length()+"大于最大值8");
		CustomGetPolTime = aCustomGetPolTime;
	}
	public String getConfDate()
	{
		if( ConfDate != null )
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
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	public String getConfOperator()
	{
		return ConfOperator;
	}
	public void setConfOperator(String aConfOperator)
	{
		if(aConfOperator!=null && aConfOperator.length()>30)
			throw new IllegalArgumentException("财务确认人ConfOperator值"+aConfOperator+"的长度"+aConfOperator.length()+"大于最大值30");
		ConfOperator = aConfOperator;
	}
	public String getSignOperator()
	{
		return SignOperator;
	}
	public void setSignOperator(String aSignOperator)
	{
		if(aSignOperator!=null && aSignOperator.length()>30)
			throw new IllegalArgumentException("保单签发人员SignOperator值"+aSignOperator+"的长度"+aSignOperator.length()+"大于最大值30");
		SignOperator = aSignOperator;
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

	public String getSignTime()
	{
		return SignTime;
	}
	public void setSignTime(String aSignTime)
	{
		if(aSignTime!=null && aSignTime.length()>8)
			throw new IllegalArgumentException("保单签发时间SignTime值"+aSignTime+"的长度"+aSignTime.length()+"大于最大值8");
		SignTime = aSignTime;
	}
	public int getAcceptWorkdays()
	{
		return AcceptWorkdays;
	}
	public void setAcceptWorkdays(int aAcceptWorkdays)
	{
		AcceptWorkdays = aAcceptWorkdays;
	}
	public void setAcceptWorkdays(String aAcceptWorkdays)
	{
		if (aAcceptWorkdays != null && !aAcceptWorkdays.equals(""))
		{
			Integer tInteger = new Integer(aAcceptWorkdays);
			int i = tInteger.intValue();
			AcceptWorkdays = i;
		}
	}


	/**
	* 使用另外一个 LCGrpContProcessSchema 对象给 Schema 赋值
	* @param: aLCGrpContProcessSchema LCGrpContProcessSchema
	**/
	public void setSchema(LCGrpContProcessSchema aLCGrpContProcessSchema)
	{
		this.GrpPropNo = aLCGrpContProcessSchema.getGrpPropNo();
		this.GrpContNo = aLCGrpContProcessSchema.getGrpContNo();
		this.ScanOperator = aLCGrpContProcessSchema.getScanOperator();
		this.ScanDate = fDate.getDate( aLCGrpContProcessSchema.getScanDate());
		this.ScanTime = aLCGrpContProcessSchema.getScanTime();
		this.Input1Operator = aLCGrpContProcessSchema.getInput1Operator();
		this.Input1Date = fDate.getDate( aLCGrpContProcessSchema.getInput1Date());
		this.Input1Time = aLCGrpContProcessSchema.getInput1Time();
		this.Out1Date = fDate.getDate( aLCGrpContProcessSchema.getOut1Date());
		this.Out1Time = aLCGrpContProcessSchema.getOut1Time();
		this.Input2Operator = aLCGrpContProcessSchema.getInput2Operator();
		this.Input2Date = fDate.getDate( aLCGrpContProcessSchema.getInput2Date());
		this.Input2Time = aLCGrpContProcessSchema.getInput2Time();
		this.Out2Date = fDate.getDate( aLCGrpContProcessSchema.getOut2Date());
		this.Out2Time = aLCGrpContProcessSchema.getOut2Time();
		this.Input3Operator = aLCGrpContProcessSchema.getInput3Operator();
		this.Input3Date = fDate.getDate( aLCGrpContProcessSchema.getInput3Date());
		this.Input3Time = aLCGrpContProcessSchema.getInput3Time();
		this.Out3Date = fDate.getDate( aLCGrpContProcessSchema.getOut3Date());
		this.Out3Time = aLCGrpContProcessSchema.getOut3Time();
		this.ApproveOperator = aLCGrpContProcessSchema.getApproveOperator();
		this.ApproveDate = fDate.getDate( aLCGrpContProcessSchema.getApproveDate());
		this.ApproveTime = aLCGrpContProcessSchema.getApproveTime();
		this.UWOperator = aLCGrpContProcessSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLCGrpContProcessSchema.getUWDate());
		this.UWTime = aLCGrpContProcessSchema.getUWTime();
		this.UWFlag = aLCGrpContProcessSchema.getUWFlag();
		this.BackUWOperator = aLCGrpContProcessSchema.getBackUWOperator();
		this.BackUWDate = fDate.getDate( aLCGrpContProcessSchema.getBackUWDate());
		this.BackUWTime = aLCGrpContProcessSchema.getBackUWTime();
		this.GetPolDate = fDate.getDate( aLCGrpContProcessSchema.getGetPolDate());
		this.GetPolTime = aLCGrpContProcessSchema.getGetPolTime();
		this.CustomGetPolDate = fDate.getDate( aLCGrpContProcessSchema.getCustomGetPolDate());
		this.CustomGetPolTime = aLCGrpContProcessSchema.getCustomGetPolTime();
		this.ConfDate = fDate.getDate( aLCGrpContProcessSchema.getConfDate());
		this.ConfOperator = aLCGrpContProcessSchema.getConfOperator();
		this.SignOperator = aLCGrpContProcessSchema.getSignOperator();
		this.SignDate = fDate.getDate( aLCGrpContProcessSchema.getSignDate());
		this.SignTime = aLCGrpContProcessSchema.getSignTime();
		this.AcceptWorkdays = aLCGrpContProcessSchema.getAcceptWorkdays();
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
			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ScanOperator") == null )
				this.ScanOperator = null;
			else
				this.ScanOperator = rs.getString("ScanOperator").trim();

			this.ScanDate = rs.getDate("ScanDate");
			if( rs.getString("ScanTime") == null )
				this.ScanTime = null;
			else
				this.ScanTime = rs.getString("ScanTime").trim();

			if( rs.getString("Input1Operator") == null )
				this.Input1Operator = null;
			else
				this.Input1Operator = rs.getString("Input1Operator").trim();

			this.Input1Date = rs.getDate("Input1Date");
			if( rs.getString("Input1Time") == null )
				this.Input1Time = null;
			else
				this.Input1Time = rs.getString("Input1Time").trim();

			this.Out1Date = rs.getDate("Out1Date");
			if( rs.getString("Out1Time") == null )
				this.Out1Time = null;
			else
				this.Out1Time = rs.getString("Out1Time").trim();

			if( rs.getString("Input2Operator") == null )
				this.Input2Operator = null;
			else
				this.Input2Operator = rs.getString("Input2Operator").trim();

			this.Input2Date = rs.getDate("Input2Date");
			if( rs.getString("Input2Time") == null )
				this.Input2Time = null;
			else
				this.Input2Time = rs.getString("Input2Time").trim();

			this.Out2Date = rs.getDate("Out2Date");
			if( rs.getString("Out2Time") == null )
				this.Out2Time = null;
			else
				this.Out2Time = rs.getString("Out2Time").trim();

			if( rs.getString("Input3Operator") == null )
				this.Input3Operator = null;
			else
				this.Input3Operator = rs.getString("Input3Operator").trim();

			this.Input3Date = rs.getDate("Input3Date");
			if( rs.getString("Input3Time") == null )
				this.Input3Time = null;
			else
				this.Input3Time = rs.getString("Input3Time").trim();

			this.Out3Date = rs.getDate("Out3Date");
			if( rs.getString("Out3Time") == null )
				this.Out3Time = null;
			else
				this.Out3Time = rs.getString("Out3Time").trim();

			if( rs.getString("ApproveOperator") == null )
				this.ApproveOperator = null;
			else
				this.ApproveOperator = rs.getString("ApproveOperator").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("BackUWOperator") == null )
				this.BackUWOperator = null;
			else
				this.BackUWOperator = rs.getString("BackUWOperator").trim();

			this.BackUWDate = rs.getDate("BackUWDate");
			if( rs.getString("BackUWTime") == null )
				this.BackUWTime = null;
			else
				this.BackUWTime = rs.getString("BackUWTime").trim();

			this.GetPolDate = rs.getDate("GetPolDate");
			if( rs.getString("GetPolTime") == null )
				this.GetPolTime = null;
			else
				this.GetPolTime = rs.getString("GetPolTime").trim();

			this.CustomGetPolDate = rs.getDate("CustomGetPolDate");
			if( rs.getString("CustomGetPolTime") == null )
				this.CustomGetPolTime = null;
			else
				this.CustomGetPolTime = rs.getString("CustomGetPolTime").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfOperator") == null )
				this.ConfOperator = null;
			else
				this.ConfOperator = rs.getString("ConfOperator").trim();

			if( rs.getString("SignOperator") == null )
				this.SignOperator = null;
			else
				this.SignOperator = rs.getString("SignOperator").trim();

			this.SignDate = rs.getDate("SignDate");
			if( rs.getString("SignTime") == null )
				this.SignTime = null;
			else
				this.SignTime = rs.getString("SignTime").trim();

			this.AcceptWorkdays = rs.getInt("AcceptWorkdays");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpContProcess表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContProcessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpContProcessSchema getSchema()
	{
		LCGrpContProcessSchema aLCGrpContProcessSchema = new LCGrpContProcessSchema();
		aLCGrpContProcessSchema.setSchema(this);
		return aLCGrpContProcessSchema;
	}

	public LCGrpContProcessDB getDB()
	{
		LCGrpContProcessDB aDBOper = new LCGrpContProcessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContProcess描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ScanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input1Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Input1Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input1Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Out1Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Out1Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input2Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Input2Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input2Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Out2Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Out2Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input3Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Input3Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Input3Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Out3Date ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Out3Time)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackUWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BackUWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackUWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomGetPolTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SignTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AcceptWorkdays));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContProcess>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ScanOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ScanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ScanTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Input1Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Input1Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			Input1Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Out1Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Out1Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Input2Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Input2Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			Input2Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Out2Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			Out2Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Input3Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Input3Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			Input3Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Out3Date = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			Out3Time = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BackUWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BackUWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			BackUWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			GetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			CustomGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			CustomGetPolTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			ConfOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			SignOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			SignTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			AcceptWorkdays= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,40,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContProcessSchema";
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
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanOperator));
		}
		if (FCode.equalsIgnoreCase("ScanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getScanDate()));
		}
		if (FCode.equalsIgnoreCase("ScanTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanTime));
		}
		if (FCode.equalsIgnoreCase("Input1Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input1Operator));
		}
		if (FCode.equalsIgnoreCase("Input1Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInput1Date()));
		}
		if (FCode.equalsIgnoreCase("Input1Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input1Time));
		}
		if (FCode.equalsIgnoreCase("Out1Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOut1Date()));
		}
		if (FCode.equalsIgnoreCase("Out1Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Out1Time));
		}
		if (FCode.equalsIgnoreCase("Input2Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input2Operator));
		}
		if (FCode.equalsIgnoreCase("Input2Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInput2Date()));
		}
		if (FCode.equalsIgnoreCase("Input2Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input2Time));
		}
		if (FCode.equalsIgnoreCase("Out2Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOut2Date()));
		}
		if (FCode.equalsIgnoreCase("Out2Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Out2Time));
		}
		if (FCode.equalsIgnoreCase("Input3Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input3Operator));
		}
		if (FCode.equalsIgnoreCase("Input3Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInput3Date()));
		}
		if (FCode.equalsIgnoreCase("Input3Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Input3Time));
		}
		if (FCode.equalsIgnoreCase("Out3Date"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOut3Date()));
		}
		if (FCode.equalsIgnoreCase("Out3Time"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Out3Time));
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveOperator));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("BackUWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUWOperator));
		}
		if (FCode.equalsIgnoreCase("BackUWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBackUWDate()));
		}
		if (FCode.equalsIgnoreCase("BackUWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackUWTime));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetPolTime));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("CustomGetPolTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomGetPolTime));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfOperator));
		}
		if (FCode.equalsIgnoreCase("SignOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignOperator));
		}
		if (FCode.equalsIgnoreCase("SignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
		}
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SignTime));
		}
		if (FCode.equalsIgnoreCase("AcceptWorkdays"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcceptWorkdays));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ScanOperator);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getScanDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ScanTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Input1Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInput1Date()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Input1Time);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOut1Date()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Out1Time);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Input2Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInput2Date()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Input2Time);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOut2Date()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Out2Time);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Input3Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInput3Date()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Input3Time);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOut3Date()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Out3Time);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BackUWOperator);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBackUWDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(BackUWTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(GetPolTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomGetPolDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(CustomGetPolTime);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(ConfOperator);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SignOperator);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSignDate()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(SignTime);
				break;
			case 39:
				strFieldValue = String.valueOf(AcceptWorkdays);
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

		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
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
		if (FCode.equalsIgnoreCase("ScanOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanOperator = FValue.trim();
			}
			else
				ScanOperator = null;
		}
		if (FCode.equalsIgnoreCase("ScanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ScanDate = fDate.getDate( FValue );
			}
			else
				ScanDate = null;
		}
		if (FCode.equalsIgnoreCase("ScanTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanTime = FValue.trim();
			}
			else
				ScanTime = null;
		}
		if (FCode.equalsIgnoreCase("Input1Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input1Operator = FValue.trim();
			}
			else
				Input1Operator = null;
		}
		if (FCode.equalsIgnoreCase("Input1Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Input1Date = fDate.getDate( FValue );
			}
			else
				Input1Date = null;
		}
		if (FCode.equalsIgnoreCase("Input1Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input1Time = FValue.trim();
			}
			else
				Input1Time = null;
		}
		if (FCode.equalsIgnoreCase("Out1Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Out1Date = fDate.getDate( FValue );
			}
			else
				Out1Date = null;
		}
		if (FCode.equalsIgnoreCase("Out1Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Out1Time = FValue.trim();
			}
			else
				Out1Time = null;
		}
		if (FCode.equalsIgnoreCase("Input2Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input2Operator = FValue.trim();
			}
			else
				Input2Operator = null;
		}
		if (FCode.equalsIgnoreCase("Input2Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Input2Date = fDate.getDate( FValue );
			}
			else
				Input2Date = null;
		}
		if (FCode.equalsIgnoreCase("Input2Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input2Time = FValue.trim();
			}
			else
				Input2Time = null;
		}
		if (FCode.equalsIgnoreCase("Out2Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Out2Date = fDate.getDate( FValue );
			}
			else
				Out2Date = null;
		}
		if (FCode.equalsIgnoreCase("Out2Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Out2Time = FValue.trim();
			}
			else
				Out2Time = null;
		}
		if (FCode.equalsIgnoreCase("Input3Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input3Operator = FValue.trim();
			}
			else
				Input3Operator = null;
		}
		if (FCode.equalsIgnoreCase("Input3Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Input3Date = fDate.getDate( FValue );
			}
			else
				Input3Date = null;
		}
		if (FCode.equalsIgnoreCase("Input3Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Input3Time = FValue.trim();
			}
			else
				Input3Time = null;
		}
		if (FCode.equalsIgnoreCase("Out3Date"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Out3Date = fDate.getDate( FValue );
			}
			else
				Out3Date = null;
		}
		if (FCode.equalsIgnoreCase("Out3Time"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Out3Time = FValue.trim();
			}
			else
				Out3Time = null;
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveOperator = FValue.trim();
			}
			else
				ApproveOperator = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
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
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
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
		if (FCode.equalsIgnoreCase("BackUWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUWOperator = FValue.trim();
			}
			else
				BackUWOperator = null;
		}
		if (FCode.equalsIgnoreCase("BackUWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BackUWDate = fDate.getDate( FValue );
			}
			else
				BackUWDate = null;
		}
		if (FCode.equalsIgnoreCase("BackUWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackUWTime = FValue.trim();
			}
			else
				BackUWTime = null;
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
		if (FCode.equalsIgnoreCase("GetPolTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetPolTime = FValue.trim();
			}
			else
				GetPolTime = null;
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
		if (FCode.equalsIgnoreCase("CustomGetPolTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomGetPolTime = FValue.trim();
			}
			else
				CustomGetPolTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfOperator = FValue.trim();
			}
			else
				ConfOperator = null;
		}
		if (FCode.equalsIgnoreCase("SignOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignOperator = FValue.trim();
			}
			else
				SignOperator = null;
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
		if (FCode.equalsIgnoreCase("SignTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SignTime = FValue.trim();
			}
			else
				SignTime = null;
		}
		if (FCode.equalsIgnoreCase("AcceptWorkdays"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AcceptWorkdays = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpContProcessSchema other = (LCGrpContProcessSchema)otherObject;
		return
			GrpPropNo.equals(other.getGrpPropNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ScanOperator.equals(other.getScanOperator())
			&& fDate.getString(ScanDate).equals(other.getScanDate())
			&& ScanTime.equals(other.getScanTime())
			&& Input1Operator.equals(other.getInput1Operator())
			&& fDate.getString(Input1Date).equals(other.getInput1Date())
			&& Input1Time.equals(other.getInput1Time())
			&& fDate.getString(Out1Date).equals(other.getOut1Date())
			&& Out1Time.equals(other.getOut1Time())
			&& Input2Operator.equals(other.getInput2Operator())
			&& fDate.getString(Input2Date).equals(other.getInput2Date())
			&& Input2Time.equals(other.getInput2Time())
			&& fDate.getString(Out2Date).equals(other.getOut2Date())
			&& Out2Time.equals(other.getOut2Time())
			&& Input3Operator.equals(other.getInput3Operator())
			&& fDate.getString(Input3Date).equals(other.getInput3Date())
			&& Input3Time.equals(other.getInput3Time())
			&& fDate.getString(Out3Date).equals(other.getOut3Date())
			&& Out3Time.equals(other.getOut3Time())
			&& ApproveOperator.equals(other.getApproveOperator())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& UWFlag.equals(other.getUWFlag())
			&& BackUWOperator.equals(other.getBackUWOperator())
			&& fDate.getString(BackUWDate).equals(other.getBackUWDate())
			&& BackUWTime.equals(other.getBackUWTime())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& GetPolTime.equals(other.getGetPolTime())
			&& fDate.getString(CustomGetPolDate).equals(other.getCustomGetPolDate())
			&& CustomGetPolTime.equals(other.getCustomGetPolTime())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfOperator.equals(other.getConfOperator())
			&& SignOperator.equals(other.getSignOperator())
			&& fDate.getString(SignDate).equals(other.getSignDate())
			&& SignTime.equals(other.getSignTime())
			&& AcceptWorkdays == other.getAcceptWorkdays();
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
		if( strFieldName.equals("GrpPropNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return 2;
		}
		if( strFieldName.equals("ScanDate") ) {
			return 3;
		}
		if( strFieldName.equals("ScanTime") ) {
			return 4;
		}
		if( strFieldName.equals("Input1Operator") ) {
			return 5;
		}
		if( strFieldName.equals("Input1Date") ) {
			return 6;
		}
		if( strFieldName.equals("Input1Time") ) {
			return 7;
		}
		if( strFieldName.equals("Out1Date") ) {
			return 8;
		}
		if( strFieldName.equals("Out1Time") ) {
			return 9;
		}
		if( strFieldName.equals("Input2Operator") ) {
			return 10;
		}
		if( strFieldName.equals("Input2Date") ) {
			return 11;
		}
		if( strFieldName.equals("Input2Time") ) {
			return 12;
		}
		if( strFieldName.equals("Out2Date") ) {
			return 13;
		}
		if( strFieldName.equals("Out2Time") ) {
			return 14;
		}
		if( strFieldName.equals("Input3Operator") ) {
			return 15;
		}
		if( strFieldName.equals("Input3Date") ) {
			return 16;
		}
		if( strFieldName.equals("Input3Time") ) {
			return 17;
		}
		if( strFieldName.equals("Out3Date") ) {
			return 18;
		}
		if( strFieldName.equals("Out3Time") ) {
			return 19;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return 20;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 21;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 22;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 23;
		}
		if( strFieldName.equals("UWDate") ) {
			return 24;
		}
		if( strFieldName.equals("UWTime") ) {
			return 25;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 26;
		}
		if( strFieldName.equals("BackUWOperator") ) {
			return 27;
		}
		if( strFieldName.equals("BackUWDate") ) {
			return 28;
		}
		if( strFieldName.equals("BackUWTime") ) {
			return 29;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 30;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return 31;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return 32;
		}
		if( strFieldName.equals("CustomGetPolTime") ) {
			return 33;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 34;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return 35;
		}
		if( strFieldName.equals("SignOperator") ) {
			return 36;
		}
		if( strFieldName.equals("SignDate") ) {
			return 37;
		}
		if( strFieldName.equals("SignTime") ) {
			return 38;
		}
		if( strFieldName.equals("AcceptWorkdays") ) {
			return 39;
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
				strFieldName = "GrpPropNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ScanOperator";
				break;
			case 3:
				strFieldName = "ScanDate";
				break;
			case 4:
				strFieldName = "ScanTime";
				break;
			case 5:
				strFieldName = "Input1Operator";
				break;
			case 6:
				strFieldName = "Input1Date";
				break;
			case 7:
				strFieldName = "Input1Time";
				break;
			case 8:
				strFieldName = "Out1Date";
				break;
			case 9:
				strFieldName = "Out1Time";
				break;
			case 10:
				strFieldName = "Input2Operator";
				break;
			case 11:
				strFieldName = "Input2Date";
				break;
			case 12:
				strFieldName = "Input2Time";
				break;
			case 13:
				strFieldName = "Out2Date";
				break;
			case 14:
				strFieldName = "Out2Time";
				break;
			case 15:
				strFieldName = "Input3Operator";
				break;
			case 16:
				strFieldName = "Input3Date";
				break;
			case 17:
				strFieldName = "Input3Time";
				break;
			case 18:
				strFieldName = "Out3Date";
				break;
			case 19:
				strFieldName = "Out3Time";
				break;
			case 20:
				strFieldName = "ApproveOperator";
				break;
			case 21:
				strFieldName = "ApproveDate";
				break;
			case 22:
				strFieldName = "ApproveTime";
				break;
			case 23:
				strFieldName = "UWOperator";
				break;
			case 24:
				strFieldName = "UWDate";
				break;
			case 25:
				strFieldName = "UWTime";
				break;
			case 26:
				strFieldName = "UWFlag";
				break;
			case 27:
				strFieldName = "BackUWOperator";
				break;
			case 28:
				strFieldName = "BackUWDate";
				break;
			case 29:
				strFieldName = "BackUWTime";
				break;
			case 30:
				strFieldName = "GetPolDate";
				break;
			case 31:
				strFieldName = "GetPolTime";
				break;
			case 32:
				strFieldName = "CustomGetPolDate";
				break;
			case 33:
				strFieldName = "CustomGetPolTime";
				break;
			case 34:
				strFieldName = "ConfDate";
				break;
			case 35:
				strFieldName = "ConfOperator";
				break;
			case 36:
				strFieldName = "SignOperator";
				break;
			case 37:
				strFieldName = "SignDate";
				break;
			case 38:
				strFieldName = "SignTime";
				break;
			case 39:
				strFieldName = "AcceptWorkdays";
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
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ScanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ScanTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input1Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input1Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Input1Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Out1Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Out1Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input2Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input2Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Input2Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Out2Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Out2Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input3Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Input3Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Input3Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Out3Date") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Out3Time") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackUWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackUWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BackUWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomGetPolTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SignTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcceptWorkdays") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
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
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
