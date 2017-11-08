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
import com.sinosoft.lis.db.LSQuotationProcessDB;

/*
 * <p>ClassName: LSQuotationProcessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotationProcessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotationProcessSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 申请人员 */
	private String AppOper;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 录入人员 */
	private String InputOper;
	/** 录入完毕日期 */
	private Date InputDate;
	/** 录入完毕时间 */
	private String InputTime;
	/** 中支公司综合意见 */
	private String SubUWOpinion;
	/** 中支公司核保结论 */
	private String SubUWConclu;
	/** 中支公司核保师 */
	private String SubUWOper;
	/** 中支公司核保日期 */
	private Date SubUWDate;
	/** 中支公司核保时间 */
	private String SubUWTime;
	/** 分公司综合意见 */
	private String BranchUWOpinion;
	/** 分公司核保结论 */
	private String BranchUWConclu;
	/** 分公司加急标记 */
	private String BranchUrgentFlag;
	/** 分公司核保师 */
	private String BranchUWOper;
	/** 分公司核保日期 */
	private Date BranchUWDate;
	/** 分公司核保时间 */
	private String BranchUWTime;
	/** 系统判断核保级别 */
	private String SysUWLevel;
	/** 再保安排 */
	private String ReinsArrange;
	/** 总公司综合意见 */
	private String UWOpinion;
	/** 总公司核保结论 */
	private String UWConclu;
	/** 总公司核保师 */
	private String UWOper;
	/** 总公司核保日期 */
	private Date UWDate;
	/** 总公司核保时间 */
	private String UWTime;
	/** 核保经理意见 */
	private String UWManagerOpinion;
	/** 核保经理结论 */
	private String UWManagerConclu;
	/** 核保经理 */
	private String UWManager;
	/** 核保经理核保日期 */
	private Date UWManagerDate;
	/** 核保经理核保时间 */
	private String UWManagerTime;
	/** 分公司最终意见 */
	private String BranchFinalOpinion;
	/** 分公司最终结论 */
	private String BranchFinalConclu;
	/** 分公司最终处理人员 */
	private String BranchFinalOper;
	/** 分公司最终处理日期 */
	private Date BranchFinalDate;
	/** 分公司最终处理时间 */
	private String BranchFinalTime;
	/** 中支公司最终意见 */
	private String SubFinalOpinion;
	/** 中支公司最终结论 */
	private String SubFinalConclu;
	/** 中支公司最终处理人员 */
	private String SubFinalOper;
	/** 中支公司最终处理日期 */
	private Date SubFinalDate;
	/** 中支公司最终处理时间 */
	private String SubFinalTime;
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

	public static final int FIELDNUM = 47;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotationProcessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";

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
		LSQuotationProcessSchema cloned = (LSQuotationProcessSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			throw new IllegalArgumentException("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
	}

	public String getAppOper()
	{
		return AppOper;
	}
	public void setAppOper(String aAppOper)
	{
		if(aAppOper!=null && aAppOper.length()>30)
			throw new IllegalArgumentException("申请人员AppOper值"+aAppOper+"的长度"+aAppOper.length()+"大于最大值30");
		AppOper = aAppOper;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		if(aAppTime!=null && aAppTime.length()>8)
			throw new IllegalArgumentException("申请时间AppTime值"+aAppTime+"的长度"+aAppTime.length()+"大于最大值8");
		AppTime = aAppTime;
	}
	public String getInputOper()
	{
		return InputOper;
	}
	public void setInputOper(String aInputOper)
	{
		if(aInputOper!=null && aInputOper.length()>30)
			throw new IllegalArgumentException("录入人员InputOper值"+aInputOper+"的长度"+aInputOper.length()+"大于最大值30");
		InputOper = aInputOper;
	}
	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	public String getInputTime()
	{
		return InputTime;
	}
	public void setInputTime(String aInputTime)
	{
		if(aInputTime!=null && aInputTime.length()>8)
			throw new IllegalArgumentException("录入完毕时间InputTime值"+aInputTime+"的长度"+aInputTime.length()+"大于最大值8");
		InputTime = aInputTime;
	}
	public String getSubUWOpinion()
	{
		return SubUWOpinion;
	}
	public void setSubUWOpinion(String aSubUWOpinion)
	{
		if(aSubUWOpinion!=null && aSubUWOpinion.length()>3000)
			throw new IllegalArgumentException("中支公司综合意见SubUWOpinion值"+aSubUWOpinion+"的长度"+aSubUWOpinion.length()+"大于最大值3000");
		SubUWOpinion = aSubUWOpinion;
	}
	public String getSubUWConclu()
	{
		return SubUWConclu;
	}
	public void setSubUWConclu(String aSubUWConclu)
	{
		if(aSubUWConclu!=null && aSubUWConclu.length()>2)
			throw new IllegalArgumentException("中支公司核保结论SubUWConclu值"+aSubUWConclu+"的长度"+aSubUWConclu.length()+"大于最大值2");
		SubUWConclu = aSubUWConclu;
	}
	public String getSubUWOper()
	{
		return SubUWOper;
	}
	public void setSubUWOper(String aSubUWOper)
	{
		if(aSubUWOper!=null && aSubUWOper.length()>30)
			throw new IllegalArgumentException("中支公司核保师SubUWOper值"+aSubUWOper+"的长度"+aSubUWOper.length()+"大于最大值30");
		SubUWOper = aSubUWOper;
	}
	public String getSubUWDate()
	{
		if( SubUWDate != null )
			return fDate.getString(SubUWDate);
		else
			return null;
	}
	public void setSubUWDate(Date aSubUWDate)
	{
		SubUWDate = aSubUWDate;
	}
	public void setSubUWDate(String aSubUWDate)
	{
		if (aSubUWDate != null && !aSubUWDate.equals("") )
		{
			SubUWDate = fDate.getDate( aSubUWDate );
		}
		else
			SubUWDate = null;
	}

	public String getSubUWTime()
	{
		return SubUWTime;
	}
	public void setSubUWTime(String aSubUWTime)
	{
		if(aSubUWTime!=null && aSubUWTime.length()>8)
			throw new IllegalArgumentException("中支公司核保时间SubUWTime值"+aSubUWTime+"的长度"+aSubUWTime.length()+"大于最大值8");
		SubUWTime = aSubUWTime;
	}
	public String getBranchUWOpinion()
	{
		return BranchUWOpinion;
	}
	public void setBranchUWOpinion(String aBranchUWOpinion)
	{
		if(aBranchUWOpinion!=null && aBranchUWOpinion.length()>3000)
			throw new IllegalArgumentException("分公司综合意见BranchUWOpinion值"+aBranchUWOpinion+"的长度"+aBranchUWOpinion.length()+"大于最大值3000");
		BranchUWOpinion = aBranchUWOpinion;
	}
	public String getBranchUWConclu()
	{
		return BranchUWConclu;
	}
	public void setBranchUWConclu(String aBranchUWConclu)
	{
		if(aBranchUWConclu!=null && aBranchUWConclu.length()>2)
			throw new IllegalArgumentException("分公司核保结论BranchUWConclu值"+aBranchUWConclu+"的长度"+aBranchUWConclu.length()+"大于最大值2");
		BranchUWConclu = aBranchUWConclu;
	}
	public String getBranchUrgentFlag()
	{
		return BranchUrgentFlag;
	}
	public void setBranchUrgentFlag(String aBranchUrgentFlag)
	{
		if(aBranchUrgentFlag!=null && aBranchUrgentFlag.length()>1)
			throw new IllegalArgumentException("分公司加急标记BranchUrgentFlag值"+aBranchUrgentFlag+"的长度"+aBranchUrgentFlag.length()+"大于最大值1");
		BranchUrgentFlag = aBranchUrgentFlag;
	}
	public String getBranchUWOper()
	{
		return BranchUWOper;
	}
	public void setBranchUWOper(String aBranchUWOper)
	{
		if(aBranchUWOper!=null && aBranchUWOper.length()>30)
			throw new IllegalArgumentException("分公司核保师BranchUWOper值"+aBranchUWOper+"的长度"+aBranchUWOper.length()+"大于最大值30");
		BranchUWOper = aBranchUWOper;
	}
	public String getBranchUWDate()
	{
		if( BranchUWDate != null )
			return fDate.getString(BranchUWDate);
		else
			return null;
	}
	public void setBranchUWDate(Date aBranchUWDate)
	{
		BranchUWDate = aBranchUWDate;
	}
	public void setBranchUWDate(String aBranchUWDate)
	{
		if (aBranchUWDate != null && !aBranchUWDate.equals("") )
		{
			BranchUWDate = fDate.getDate( aBranchUWDate );
		}
		else
			BranchUWDate = null;
	}

	public String getBranchUWTime()
	{
		return BranchUWTime;
	}
	public void setBranchUWTime(String aBranchUWTime)
	{
		if(aBranchUWTime!=null && aBranchUWTime.length()>8)
			throw new IllegalArgumentException("分公司核保时间BranchUWTime值"+aBranchUWTime+"的长度"+aBranchUWTime.length()+"大于最大值8");
		BranchUWTime = aBranchUWTime;
	}
	public String getSysUWLevel()
	{
		return SysUWLevel;
	}
	public void setSysUWLevel(String aSysUWLevel)
	{
		if(aSysUWLevel!=null && aSysUWLevel.length()>10)
			throw new IllegalArgumentException("系统判断核保级别SysUWLevel值"+aSysUWLevel+"的长度"+aSysUWLevel.length()+"大于最大值10");
		SysUWLevel = aSysUWLevel;
	}
	public String getReinsArrange()
	{
		return ReinsArrange;
	}
	public void setReinsArrange(String aReinsArrange)
	{
		if(aReinsArrange!=null && aReinsArrange.length()>2)
			throw new IllegalArgumentException("再保安排ReinsArrange值"+aReinsArrange+"的长度"+aReinsArrange.length()+"大于最大值2");
		ReinsArrange = aReinsArrange;
	}
	public String getUWOpinion()
	{
		return UWOpinion;
	}
	public void setUWOpinion(String aUWOpinion)
	{
		if(aUWOpinion!=null && aUWOpinion.length()>3000)
			throw new IllegalArgumentException("总公司综合意见UWOpinion值"+aUWOpinion+"的长度"+aUWOpinion.length()+"大于最大值3000");
		UWOpinion = aUWOpinion;
	}
	public String getUWConclu()
	{
		return UWConclu;
	}
	public void setUWConclu(String aUWConclu)
	{
		if(aUWConclu!=null && aUWConclu.length()>2)
			throw new IllegalArgumentException("总公司核保结论UWConclu值"+aUWConclu+"的长度"+aUWConclu.length()+"大于最大值2");
		UWConclu = aUWConclu;
	}
	public String getUWOper()
	{
		return UWOper;
	}
	public void setUWOper(String aUWOper)
	{
		if(aUWOper!=null && aUWOper.length()>30)
			throw new IllegalArgumentException("总公司核保师UWOper值"+aUWOper+"的长度"+aUWOper.length()+"大于最大值30");
		UWOper = aUWOper;
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
			throw new IllegalArgumentException("总公司核保时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
		UWTime = aUWTime;
	}
	public String getUWManagerOpinion()
	{
		return UWManagerOpinion;
	}
	public void setUWManagerOpinion(String aUWManagerOpinion)
	{
		if(aUWManagerOpinion!=null && aUWManagerOpinion.length()>3000)
			throw new IllegalArgumentException("核保经理意见UWManagerOpinion值"+aUWManagerOpinion+"的长度"+aUWManagerOpinion.length()+"大于最大值3000");
		UWManagerOpinion = aUWManagerOpinion;
	}
	public String getUWManagerConclu()
	{
		return UWManagerConclu;
	}
	public void setUWManagerConclu(String aUWManagerConclu)
	{
		if(aUWManagerConclu!=null && aUWManagerConclu.length()>2)
			throw new IllegalArgumentException("核保经理结论UWManagerConclu值"+aUWManagerConclu+"的长度"+aUWManagerConclu.length()+"大于最大值2");
		UWManagerConclu = aUWManagerConclu;
	}
	public String getUWManager()
	{
		return UWManager;
	}
	public void setUWManager(String aUWManager)
	{
		if(aUWManager!=null && aUWManager.length()>30)
			throw new IllegalArgumentException("核保经理UWManager值"+aUWManager+"的长度"+aUWManager.length()+"大于最大值30");
		UWManager = aUWManager;
	}
	public String getUWManagerDate()
	{
		if( UWManagerDate != null )
			return fDate.getString(UWManagerDate);
		else
			return null;
	}
	public void setUWManagerDate(Date aUWManagerDate)
	{
		UWManagerDate = aUWManagerDate;
	}
	public void setUWManagerDate(String aUWManagerDate)
	{
		if (aUWManagerDate != null && !aUWManagerDate.equals("") )
		{
			UWManagerDate = fDate.getDate( aUWManagerDate );
		}
		else
			UWManagerDate = null;
	}

	public String getUWManagerTime()
	{
		return UWManagerTime;
	}
	public void setUWManagerTime(String aUWManagerTime)
	{
		if(aUWManagerTime!=null && aUWManagerTime.length()>8)
			throw new IllegalArgumentException("核保经理核保时间UWManagerTime值"+aUWManagerTime+"的长度"+aUWManagerTime.length()+"大于最大值8");
		UWManagerTime = aUWManagerTime;
	}
	public String getBranchFinalOpinion()
	{
		return BranchFinalOpinion;
	}
	public void setBranchFinalOpinion(String aBranchFinalOpinion)
	{
		if(aBranchFinalOpinion!=null && aBranchFinalOpinion.length()>3000)
			throw new IllegalArgumentException("分公司最终意见BranchFinalOpinion值"+aBranchFinalOpinion+"的长度"+aBranchFinalOpinion.length()+"大于最大值3000");
		BranchFinalOpinion = aBranchFinalOpinion;
	}
	public String getBranchFinalConclu()
	{
		return BranchFinalConclu;
	}
	public void setBranchFinalConclu(String aBranchFinalConclu)
	{
		if(aBranchFinalConclu!=null && aBranchFinalConclu.length()>2)
			throw new IllegalArgumentException("分公司最终结论BranchFinalConclu值"+aBranchFinalConclu+"的长度"+aBranchFinalConclu.length()+"大于最大值2");
		BranchFinalConclu = aBranchFinalConclu;
	}
	public String getBranchFinalOper()
	{
		return BranchFinalOper;
	}
	public void setBranchFinalOper(String aBranchFinalOper)
	{
		if(aBranchFinalOper!=null && aBranchFinalOper.length()>30)
			throw new IllegalArgumentException("分公司最终处理人员BranchFinalOper值"+aBranchFinalOper+"的长度"+aBranchFinalOper.length()+"大于最大值30");
		BranchFinalOper = aBranchFinalOper;
	}
	public String getBranchFinalDate()
	{
		if( BranchFinalDate != null )
			return fDate.getString(BranchFinalDate);
		else
			return null;
	}
	public void setBranchFinalDate(Date aBranchFinalDate)
	{
		BranchFinalDate = aBranchFinalDate;
	}
	public void setBranchFinalDate(String aBranchFinalDate)
	{
		if (aBranchFinalDate != null && !aBranchFinalDate.equals("") )
		{
			BranchFinalDate = fDate.getDate( aBranchFinalDate );
		}
		else
			BranchFinalDate = null;
	}

	public String getBranchFinalTime()
	{
		return BranchFinalTime;
	}
	public void setBranchFinalTime(String aBranchFinalTime)
	{
		if(aBranchFinalTime!=null && aBranchFinalTime.length()>8)
			throw new IllegalArgumentException("分公司最终处理时间BranchFinalTime值"+aBranchFinalTime+"的长度"+aBranchFinalTime.length()+"大于最大值8");
		BranchFinalTime = aBranchFinalTime;
	}
	public String getSubFinalOpinion()
	{
		return SubFinalOpinion;
	}
	public void setSubFinalOpinion(String aSubFinalOpinion)
	{
		if(aSubFinalOpinion!=null && aSubFinalOpinion.length()>3000)
			throw new IllegalArgumentException("中支公司最终意见SubFinalOpinion值"+aSubFinalOpinion+"的长度"+aSubFinalOpinion.length()+"大于最大值3000");
		SubFinalOpinion = aSubFinalOpinion;
	}
	public String getSubFinalConclu()
	{
		return SubFinalConclu;
	}
	public void setSubFinalConclu(String aSubFinalConclu)
	{
		if(aSubFinalConclu!=null && aSubFinalConclu.length()>2)
			throw new IllegalArgumentException("中支公司最终结论SubFinalConclu值"+aSubFinalConclu+"的长度"+aSubFinalConclu.length()+"大于最大值2");
		SubFinalConclu = aSubFinalConclu;
	}
	public String getSubFinalOper()
	{
		return SubFinalOper;
	}
	public void setSubFinalOper(String aSubFinalOper)
	{
		if(aSubFinalOper!=null && aSubFinalOper.length()>30)
			throw new IllegalArgumentException("中支公司最终处理人员SubFinalOper值"+aSubFinalOper+"的长度"+aSubFinalOper.length()+"大于最大值30");
		SubFinalOper = aSubFinalOper;
	}
	public String getSubFinalDate()
	{
		if( SubFinalDate != null )
			return fDate.getString(SubFinalDate);
		else
			return null;
	}
	public void setSubFinalDate(Date aSubFinalDate)
	{
		SubFinalDate = aSubFinalDate;
	}
	public void setSubFinalDate(String aSubFinalDate)
	{
		if (aSubFinalDate != null && !aSubFinalDate.equals("") )
		{
			SubFinalDate = fDate.getDate( aSubFinalDate );
		}
		else
			SubFinalDate = null;
	}

	public String getSubFinalTime()
	{
		return SubFinalTime;
	}
	public void setSubFinalTime(String aSubFinalTime)
	{
		if(aSubFinalTime!=null && aSubFinalTime.length()>8)
			throw new IllegalArgumentException("中支公司最终处理时间SubFinalTime值"+aSubFinalTime+"的长度"+aSubFinalTime.length()+"大于最大值8");
		SubFinalTime = aSubFinalTime;
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
	* 使用另外一个 LSQuotationProcessSchema 对象给 Schema 赋值
	* @param: aLSQuotationProcessSchema LSQuotationProcessSchema
	**/
	public void setSchema(LSQuotationProcessSchema aLSQuotationProcessSchema)
	{
		this.QuotNo = aLSQuotationProcessSchema.getQuotNo();
		this.QuotBatNo = aLSQuotationProcessSchema.getQuotBatNo();
		this.AppOper = aLSQuotationProcessSchema.getAppOper();
		this.AppDate = fDate.getDate( aLSQuotationProcessSchema.getAppDate());
		this.AppTime = aLSQuotationProcessSchema.getAppTime();
		this.InputOper = aLSQuotationProcessSchema.getInputOper();
		this.InputDate = fDate.getDate( aLSQuotationProcessSchema.getInputDate());
		this.InputTime = aLSQuotationProcessSchema.getInputTime();
		this.SubUWOpinion = aLSQuotationProcessSchema.getSubUWOpinion();
		this.SubUWConclu = aLSQuotationProcessSchema.getSubUWConclu();
		this.SubUWOper = aLSQuotationProcessSchema.getSubUWOper();
		this.SubUWDate = fDate.getDate( aLSQuotationProcessSchema.getSubUWDate());
		this.SubUWTime = aLSQuotationProcessSchema.getSubUWTime();
		this.BranchUWOpinion = aLSQuotationProcessSchema.getBranchUWOpinion();
		this.BranchUWConclu = aLSQuotationProcessSchema.getBranchUWConclu();
		this.BranchUrgentFlag = aLSQuotationProcessSchema.getBranchUrgentFlag();
		this.BranchUWOper = aLSQuotationProcessSchema.getBranchUWOper();
		this.BranchUWDate = fDate.getDate( aLSQuotationProcessSchema.getBranchUWDate());
		this.BranchUWTime = aLSQuotationProcessSchema.getBranchUWTime();
		this.SysUWLevel = aLSQuotationProcessSchema.getSysUWLevel();
		this.ReinsArrange = aLSQuotationProcessSchema.getReinsArrange();
		this.UWOpinion = aLSQuotationProcessSchema.getUWOpinion();
		this.UWConclu = aLSQuotationProcessSchema.getUWConclu();
		this.UWOper = aLSQuotationProcessSchema.getUWOper();
		this.UWDate = fDate.getDate( aLSQuotationProcessSchema.getUWDate());
		this.UWTime = aLSQuotationProcessSchema.getUWTime();
		this.UWManagerOpinion = aLSQuotationProcessSchema.getUWManagerOpinion();
		this.UWManagerConclu = aLSQuotationProcessSchema.getUWManagerConclu();
		this.UWManager = aLSQuotationProcessSchema.getUWManager();
		this.UWManagerDate = fDate.getDate( aLSQuotationProcessSchema.getUWManagerDate());
		this.UWManagerTime = aLSQuotationProcessSchema.getUWManagerTime();
		this.BranchFinalOpinion = aLSQuotationProcessSchema.getBranchFinalOpinion();
		this.BranchFinalConclu = aLSQuotationProcessSchema.getBranchFinalConclu();
		this.BranchFinalOper = aLSQuotationProcessSchema.getBranchFinalOper();
		this.BranchFinalDate = fDate.getDate( aLSQuotationProcessSchema.getBranchFinalDate());
		this.BranchFinalTime = aLSQuotationProcessSchema.getBranchFinalTime();
		this.SubFinalOpinion = aLSQuotationProcessSchema.getSubFinalOpinion();
		this.SubFinalConclu = aLSQuotationProcessSchema.getSubFinalConclu();
		this.SubFinalOper = aLSQuotationProcessSchema.getSubFinalOper();
		this.SubFinalDate = fDate.getDate( aLSQuotationProcessSchema.getSubFinalDate());
		this.SubFinalTime = aLSQuotationProcessSchema.getSubFinalTime();
		this.MakeOperator = aLSQuotationProcessSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotationProcessSchema.getMakeDate());
		this.MakeTime = aLSQuotationProcessSchema.getMakeTime();
		this.ModifyOperator = aLSQuotationProcessSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotationProcessSchema.getModifyDate());
		this.ModifyTime = aLSQuotationProcessSchema.getModifyTime();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("AppOper") == null )
				this.AppOper = null;
			else
				this.AppOper = rs.getString("AppOper").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("InputOper") == null )
				this.InputOper = null;
			else
				this.InputOper = rs.getString("InputOper").trim();

			this.InputDate = rs.getDate("InputDate");
			if( rs.getString("InputTime") == null )
				this.InputTime = null;
			else
				this.InputTime = rs.getString("InputTime").trim();

			if( rs.getString("SubUWOpinion") == null )
				this.SubUWOpinion = null;
			else
				this.SubUWOpinion = rs.getString("SubUWOpinion").trim();

			if( rs.getString("SubUWConclu") == null )
				this.SubUWConclu = null;
			else
				this.SubUWConclu = rs.getString("SubUWConclu").trim();

			if( rs.getString("SubUWOper") == null )
				this.SubUWOper = null;
			else
				this.SubUWOper = rs.getString("SubUWOper").trim();

			this.SubUWDate = rs.getDate("SubUWDate");
			if( rs.getString("SubUWTime") == null )
				this.SubUWTime = null;
			else
				this.SubUWTime = rs.getString("SubUWTime").trim();

			if( rs.getString("BranchUWOpinion") == null )
				this.BranchUWOpinion = null;
			else
				this.BranchUWOpinion = rs.getString("BranchUWOpinion").trim();

			if( rs.getString("BranchUWConclu") == null )
				this.BranchUWConclu = null;
			else
				this.BranchUWConclu = rs.getString("BranchUWConclu").trim();

			if( rs.getString("BranchUrgentFlag") == null )
				this.BranchUrgentFlag = null;
			else
				this.BranchUrgentFlag = rs.getString("BranchUrgentFlag").trim();

			if( rs.getString("BranchUWOper") == null )
				this.BranchUWOper = null;
			else
				this.BranchUWOper = rs.getString("BranchUWOper").trim();

			this.BranchUWDate = rs.getDate("BranchUWDate");
			if( rs.getString("BranchUWTime") == null )
				this.BranchUWTime = null;
			else
				this.BranchUWTime = rs.getString("BranchUWTime").trim();

			if( rs.getString("SysUWLevel") == null )
				this.SysUWLevel = null;
			else
				this.SysUWLevel = rs.getString("SysUWLevel").trim();

			if( rs.getString("ReinsArrange") == null )
				this.ReinsArrange = null;
			else
				this.ReinsArrange = rs.getString("ReinsArrange").trim();

			if( rs.getString("UWOpinion") == null )
				this.UWOpinion = null;
			else
				this.UWOpinion = rs.getString("UWOpinion").trim();

			if( rs.getString("UWConclu") == null )
				this.UWConclu = null;
			else
				this.UWConclu = rs.getString("UWConclu").trim();

			if( rs.getString("UWOper") == null )
				this.UWOper = null;
			else
				this.UWOper = rs.getString("UWOper").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			if( rs.getString("UWManagerOpinion") == null )
				this.UWManagerOpinion = null;
			else
				this.UWManagerOpinion = rs.getString("UWManagerOpinion").trim();

			if( rs.getString("UWManagerConclu") == null )
				this.UWManagerConclu = null;
			else
				this.UWManagerConclu = rs.getString("UWManagerConclu").trim();

			if( rs.getString("UWManager") == null )
				this.UWManager = null;
			else
				this.UWManager = rs.getString("UWManager").trim();

			this.UWManagerDate = rs.getDate("UWManagerDate");
			if( rs.getString("UWManagerTime") == null )
				this.UWManagerTime = null;
			else
				this.UWManagerTime = rs.getString("UWManagerTime").trim();

			if( rs.getString("BranchFinalOpinion") == null )
				this.BranchFinalOpinion = null;
			else
				this.BranchFinalOpinion = rs.getString("BranchFinalOpinion").trim();

			if( rs.getString("BranchFinalConclu") == null )
				this.BranchFinalConclu = null;
			else
				this.BranchFinalConclu = rs.getString("BranchFinalConclu").trim();

			if( rs.getString("BranchFinalOper") == null )
				this.BranchFinalOper = null;
			else
				this.BranchFinalOper = rs.getString("BranchFinalOper").trim();

			this.BranchFinalDate = rs.getDate("BranchFinalDate");
			if( rs.getString("BranchFinalTime") == null )
				this.BranchFinalTime = null;
			else
				this.BranchFinalTime = rs.getString("BranchFinalTime").trim();

			if( rs.getString("SubFinalOpinion") == null )
				this.SubFinalOpinion = null;
			else
				this.SubFinalOpinion = rs.getString("SubFinalOpinion").trim();

			if( rs.getString("SubFinalConclu") == null )
				this.SubFinalConclu = null;
			else
				this.SubFinalConclu = rs.getString("SubFinalConclu").trim();

			if( rs.getString("SubFinalOper") == null )
				this.SubFinalOper = null;
			else
				this.SubFinalOper = rs.getString("SubFinalOper").trim();

			this.SubFinalDate = rs.getDate("SubFinalDate");
			if( rs.getString("SubFinalTime") == null )
				this.SubFinalTime = null;
			else
				this.SubFinalTime = rs.getString("SubFinalTime").trim();

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
			logger.debug("数据库中的LSQuotationProcess表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotationProcessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotationProcessSchema getSchema()
	{
		LSQuotationProcessSchema aLSQuotationProcessSchema = new LSQuotationProcessSchema();
		aLSQuotationProcessSchema.setSchema(this);
		return aLSQuotationProcessSchema;
	}

	public LSQuotationProcessDB getDB()
	{
		LSQuotationProcessDB aDBOper = new LSQuotationProcessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotationProcess描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SubUWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUrgentFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BranchUWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysUWLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsArrange)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWManagerOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWManagerConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWManager)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWManagerDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWManagerTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchFinalOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchFinalConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchFinalOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BranchFinalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchFinalTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFinalOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFinalConclu)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFinalOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SubFinalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFinalTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotationProcess>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			AppOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InputOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			InputTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SubUWOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SubUWConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SubUWOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SubUWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			SubUWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BranchUWOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BranchUWConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BranchUrgentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BranchUWOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BranchUWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			BranchUWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			SysUWLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ReinsArrange = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			UWOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			UWConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			UWOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			UWManagerOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			UWManagerConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			UWManager = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			UWManagerDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			UWManagerTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BranchFinalOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BranchFinalConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BranchFinalOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BranchFinalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35,SysConst.PACKAGESPILTER));
			BranchFinalTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			SubFinalOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			SubFinalConclu = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			SubFinalOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			SubFinalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40,SysConst.PACKAGESPILTER));
			SubFinalTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotationProcessSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("AppOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOper));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("InputOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputOper));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputTime));
		}
		if (FCode.equalsIgnoreCase("SubUWOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWOpinion));
		}
		if (FCode.equalsIgnoreCase("SubUWConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWConclu));
		}
		if (FCode.equalsIgnoreCase("SubUWOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWOper));
		}
		if (FCode.equalsIgnoreCase("SubUWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSubUWDate()));
		}
		if (FCode.equalsIgnoreCase("SubUWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWTime));
		}
		if (FCode.equalsIgnoreCase("BranchUWOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWOpinion));
		}
		if (FCode.equalsIgnoreCase("BranchUWConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWConclu));
		}
		if (FCode.equalsIgnoreCase("BranchUrgentFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUrgentFlag));
		}
		if (FCode.equalsIgnoreCase("BranchUWOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWOper));
		}
		if (FCode.equalsIgnoreCase("BranchUWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBranchUWDate()));
		}
		if (FCode.equalsIgnoreCase("BranchUWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWTime));
		}
		if (FCode.equalsIgnoreCase("SysUWLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysUWLevel));
		}
		if (FCode.equalsIgnoreCase("ReinsArrange"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsArrange));
		}
		if (FCode.equalsIgnoreCase("UWOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOpinion));
		}
		if (FCode.equalsIgnoreCase("UWConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWConclu));
		}
		if (FCode.equalsIgnoreCase("UWOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOper));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("UWManagerOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWManagerOpinion));
		}
		if (FCode.equalsIgnoreCase("UWManagerConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWManagerConclu));
		}
		if (FCode.equalsIgnoreCase("UWManager"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWManager));
		}
		if (FCode.equalsIgnoreCase("UWManagerDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWManagerDate()));
		}
		if (FCode.equalsIgnoreCase("UWManagerTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWManagerTime));
		}
		if (FCode.equalsIgnoreCase("BranchFinalOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchFinalOpinion));
		}
		if (FCode.equalsIgnoreCase("BranchFinalConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchFinalConclu));
		}
		if (FCode.equalsIgnoreCase("BranchFinalOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchFinalOper));
		}
		if (FCode.equalsIgnoreCase("BranchFinalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBranchFinalDate()));
		}
		if (FCode.equalsIgnoreCase("BranchFinalTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchFinalTime));
		}
		if (FCode.equalsIgnoreCase("SubFinalOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFinalOpinion));
		}
		if (FCode.equalsIgnoreCase("SubFinalConclu"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFinalConclu));
		}
		if (FCode.equalsIgnoreCase("SubFinalOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFinalOper));
		}
		if (FCode.equalsIgnoreCase("SubFinalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSubFinalDate()));
		}
		if (FCode.equalsIgnoreCase("SubFinalTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFinalTime));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AppOper);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InputOper);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InputTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SubUWOpinion);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SubUWConclu);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SubUWOper);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSubUWDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SubUWTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BranchUWOpinion);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BranchUWConclu);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BranchUrgentFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BranchUWOper);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBranchUWDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BranchUWTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(SysUWLevel);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ReinsArrange);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(UWOpinion);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(UWConclu);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(UWOper);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(UWManagerOpinion);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(UWManagerConclu);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(UWManager);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWManagerDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(UWManagerTime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BranchFinalOpinion);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BranchFinalConclu);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BranchFinalOper);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBranchFinalDate()));
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(BranchFinalTime);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(SubFinalOpinion);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(SubFinalConclu);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(SubFinalOper);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSubFinalDate()));
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SubFinalTime);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 46:
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("AppOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOper = FValue.trim();
			}
			else
				AppOper = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("InputOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputOper = FValue.trim();
			}
			else
				InputOper = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
		}
		if (FCode.equalsIgnoreCase("InputTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputTime = FValue.trim();
			}
			else
				InputTime = null;
		}
		if (FCode.equalsIgnoreCase("SubUWOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWOpinion = FValue.trim();
			}
			else
				SubUWOpinion = null;
		}
		if (FCode.equalsIgnoreCase("SubUWConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWConclu = FValue.trim();
			}
			else
				SubUWConclu = null;
		}
		if (FCode.equalsIgnoreCase("SubUWOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWOper = FValue.trim();
			}
			else
				SubUWOper = null;
		}
		if (FCode.equalsIgnoreCase("SubUWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SubUWDate = fDate.getDate( FValue );
			}
			else
				SubUWDate = null;
		}
		if (FCode.equalsIgnoreCase("SubUWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWTime = FValue.trim();
			}
			else
				SubUWTime = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWOpinion = FValue.trim();
			}
			else
				BranchUWOpinion = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWConclu = FValue.trim();
			}
			else
				BranchUWConclu = null;
		}
		if (FCode.equalsIgnoreCase("BranchUrgentFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUrgentFlag = FValue.trim();
			}
			else
				BranchUrgentFlag = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWOper = FValue.trim();
			}
			else
				BranchUWOper = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BranchUWDate = fDate.getDate( FValue );
			}
			else
				BranchUWDate = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWTime = FValue.trim();
			}
			else
				BranchUWTime = null;
		}
		if (FCode.equalsIgnoreCase("SysUWLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysUWLevel = FValue.trim();
			}
			else
				SysUWLevel = null;
		}
		if (FCode.equalsIgnoreCase("ReinsArrange"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsArrange = FValue.trim();
			}
			else
				ReinsArrange = null;
		}
		if (FCode.equalsIgnoreCase("UWOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOpinion = FValue.trim();
			}
			else
				UWOpinion = null;
		}
		if (FCode.equalsIgnoreCase("UWConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWConclu = FValue.trim();
			}
			else
				UWConclu = null;
		}
		if (FCode.equalsIgnoreCase("UWOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOper = FValue.trim();
			}
			else
				UWOper = null;
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
		if (FCode.equalsIgnoreCase("UWManagerOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWManagerOpinion = FValue.trim();
			}
			else
				UWManagerOpinion = null;
		}
		if (FCode.equalsIgnoreCase("UWManagerConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWManagerConclu = FValue.trim();
			}
			else
				UWManagerConclu = null;
		}
		if (FCode.equalsIgnoreCase("UWManager"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWManager = FValue.trim();
			}
			else
				UWManager = null;
		}
		if (FCode.equalsIgnoreCase("UWManagerDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWManagerDate = fDate.getDate( FValue );
			}
			else
				UWManagerDate = null;
		}
		if (FCode.equalsIgnoreCase("UWManagerTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWManagerTime = FValue.trim();
			}
			else
				UWManagerTime = null;
		}
		if (FCode.equalsIgnoreCase("BranchFinalOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchFinalOpinion = FValue.trim();
			}
			else
				BranchFinalOpinion = null;
		}
		if (FCode.equalsIgnoreCase("BranchFinalConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchFinalConclu = FValue.trim();
			}
			else
				BranchFinalConclu = null;
		}
		if (FCode.equalsIgnoreCase("BranchFinalOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchFinalOper = FValue.trim();
			}
			else
				BranchFinalOper = null;
		}
		if (FCode.equalsIgnoreCase("BranchFinalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BranchFinalDate = fDate.getDate( FValue );
			}
			else
				BranchFinalDate = null;
		}
		if (FCode.equalsIgnoreCase("BranchFinalTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchFinalTime = FValue.trim();
			}
			else
				BranchFinalTime = null;
		}
		if (FCode.equalsIgnoreCase("SubFinalOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFinalOpinion = FValue.trim();
			}
			else
				SubFinalOpinion = null;
		}
		if (FCode.equalsIgnoreCase("SubFinalConclu"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFinalConclu = FValue.trim();
			}
			else
				SubFinalConclu = null;
		}
		if (FCode.equalsIgnoreCase("SubFinalOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFinalOper = FValue.trim();
			}
			else
				SubFinalOper = null;
		}
		if (FCode.equalsIgnoreCase("SubFinalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SubFinalDate = fDate.getDate( FValue );
			}
			else
				SubFinalDate = null;
		}
		if (FCode.equalsIgnoreCase("SubFinalTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFinalTime = FValue.trim();
			}
			else
				SubFinalTime = null;
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
		LSQuotationProcessSchema other = (LSQuotationProcessSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& AppOper.equals(other.getAppOper())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& InputOper.equals(other.getInputOper())
			&& fDate.getString(InputDate).equals(other.getInputDate())
			&& InputTime.equals(other.getInputTime())
			&& SubUWOpinion.equals(other.getSubUWOpinion())
			&& SubUWConclu.equals(other.getSubUWConclu())
			&& SubUWOper.equals(other.getSubUWOper())
			&& fDate.getString(SubUWDate).equals(other.getSubUWDate())
			&& SubUWTime.equals(other.getSubUWTime())
			&& BranchUWOpinion.equals(other.getBranchUWOpinion())
			&& BranchUWConclu.equals(other.getBranchUWConclu())
			&& BranchUrgentFlag.equals(other.getBranchUrgentFlag())
			&& BranchUWOper.equals(other.getBranchUWOper())
			&& fDate.getString(BranchUWDate).equals(other.getBranchUWDate())
			&& BranchUWTime.equals(other.getBranchUWTime())
			&& SysUWLevel.equals(other.getSysUWLevel())
			&& ReinsArrange.equals(other.getReinsArrange())
			&& UWOpinion.equals(other.getUWOpinion())
			&& UWConclu.equals(other.getUWConclu())
			&& UWOper.equals(other.getUWOper())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& UWManagerOpinion.equals(other.getUWManagerOpinion())
			&& UWManagerConclu.equals(other.getUWManagerConclu())
			&& UWManager.equals(other.getUWManager())
			&& fDate.getString(UWManagerDate).equals(other.getUWManagerDate())
			&& UWManagerTime.equals(other.getUWManagerTime())
			&& BranchFinalOpinion.equals(other.getBranchFinalOpinion())
			&& BranchFinalConclu.equals(other.getBranchFinalConclu())
			&& BranchFinalOper.equals(other.getBranchFinalOper())
			&& fDate.getString(BranchFinalDate).equals(other.getBranchFinalDate())
			&& BranchFinalTime.equals(other.getBranchFinalTime())
			&& SubFinalOpinion.equals(other.getSubFinalOpinion())
			&& SubFinalConclu.equals(other.getSubFinalConclu())
			&& SubFinalOper.equals(other.getSubFinalOper())
			&& fDate.getString(SubFinalDate).equals(other.getSubFinalDate())
			&& SubFinalTime.equals(other.getSubFinalTime())
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("AppOper") ) {
			return 2;
		}
		if( strFieldName.equals("AppDate") ) {
			return 3;
		}
		if( strFieldName.equals("AppTime") ) {
			return 4;
		}
		if( strFieldName.equals("InputOper") ) {
			return 5;
		}
		if( strFieldName.equals("InputDate") ) {
			return 6;
		}
		if( strFieldName.equals("InputTime") ) {
			return 7;
		}
		if( strFieldName.equals("SubUWOpinion") ) {
			return 8;
		}
		if( strFieldName.equals("SubUWConclu") ) {
			return 9;
		}
		if( strFieldName.equals("SubUWOper") ) {
			return 10;
		}
		if( strFieldName.equals("SubUWDate") ) {
			return 11;
		}
		if( strFieldName.equals("SubUWTime") ) {
			return 12;
		}
		if( strFieldName.equals("BranchUWOpinion") ) {
			return 13;
		}
		if( strFieldName.equals("BranchUWConclu") ) {
			return 14;
		}
		if( strFieldName.equals("BranchUrgentFlag") ) {
			return 15;
		}
		if( strFieldName.equals("BranchUWOper") ) {
			return 16;
		}
		if( strFieldName.equals("BranchUWDate") ) {
			return 17;
		}
		if( strFieldName.equals("BranchUWTime") ) {
			return 18;
		}
		if( strFieldName.equals("SysUWLevel") ) {
			return 19;
		}
		if( strFieldName.equals("ReinsArrange") ) {
			return 20;
		}
		if( strFieldName.equals("UWOpinion") ) {
			return 21;
		}
		if( strFieldName.equals("UWConclu") ) {
			return 22;
		}
		if( strFieldName.equals("UWOper") ) {
			return 23;
		}
		if( strFieldName.equals("UWDate") ) {
			return 24;
		}
		if( strFieldName.equals("UWTime") ) {
			return 25;
		}
		if( strFieldName.equals("UWManagerOpinion") ) {
			return 26;
		}
		if( strFieldName.equals("UWManagerConclu") ) {
			return 27;
		}
		if( strFieldName.equals("UWManager") ) {
			return 28;
		}
		if( strFieldName.equals("UWManagerDate") ) {
			return 29;
		}
		if( strFieldName.equals("UWManagerTime") ) {
			return 30;
		}
		if( strFieldName.equals("BranchFinalOpinion") ) {
			return 31;
		}
		if( strFieldName.equals("BranchFinalConclu") ) {
			return 32;
		}
		if( strFieldName.equals("BranchFinalOper") ) {
			return 33;
		}
		if( strFieldName.equals("BranchFinalDate") ) {
			return 34;
		}
		if( strFieldName.equals("BranchFinalTime") ) {
			return 35;
		}
		if( strFieldName.equals("SubFinalOpinion") ) {
			return 36;
		}
		if( strFieldName.equals("SubFinalConclu") ) {
			return 37;
		}
		if( strFieldName.equals("SubFinalOper") ) {
			return 38;
		}
		if( strFieldName.equals("SubFinalDate") ) {
			return 39;
		}
		if( strFieldName.equals("SubFinalTime") ) {
			return 40;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 41;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 42;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 43;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 44;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 45;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 46;
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "AppOper";
				break;
			case 3:
				strFieldName = "AppDate";
				break;
			case 4:
				strFieldName = "AppTime";
				break;
			case 5:
				strFieldName = "InputOper";
				break;
			case 6:
				strFieldName = "InputDate";
				break;
			case 7:
				strFieldName = "InputTime";
				break;
			case 8:
				strFieldName = "SubUWOpinion";
				break;
			case 9:
				strFieldName = "SubUWConclu";
				break;
			case 10:
				strFieldName = "SubUWOper";
				break;
			case 11:
				strFieldName = "SubUWDate";
				break;
			case 12:
				strFieldName = "SubUWTime";
				break;
			case 13:
				strFieldName = "BranchUWOpinion";
				break;
			case 14:
				strFieldName = "BranchUWConclu";
				break;
			case 15:
				strFieldName = "BranchUrgentFlag";
				break;
			case 16:
				strFieldName = "BranchUWOper";
				break;
			case 17:
				strFieldName = "BranchUWDate";
				break;
			case 18:
				strFieldName = "BranchUWTime";
				break;
			case 19:
				strFieldName = "SysUWLevel";
				break;
			case 20:
				strFieldName = "ReinsArrange";
				break;
			case 21:
				strFieldName = "UWOpinion";
				break;
			case 22:
				strFieldName = "UWConclu";
				break;
			case 23:
				strFieldName = "UWOper";
				break;
			case 24:
				strFieldName = "UWDate";
				break;
			case 25:
				strFieldName = "UWTime";
				break;
			case 26:
				strFieldName = "UWManagerOpinion";
				break;
			case 27:
				strFieldName = "UWManagerConclu";
				break;
			case 28:
				strFieldName = "UWManager";
				break;
			case 29:
				strFieldName = "UWManagerDate";
				break;
			case 30:
				strFieldName = "UWManagerTime";
				break;
			case 31:
				strFieldName = "BranchFinalOpinion";
				break;
			case 32:
				strFieldName = "BranchFinalConclu";
				break;
			case 33:
				strFieldName = "BranchFinalOper";
				break;
			case 34:
				strFieldName = "BranchFinalDate";
				break;
			case 35:
				strFieldName = "BranchFinalTime";
				break;
			case 36:
				strFieldName = "SubFinalOpinion";
				break;
			case 37:
				strFieldName = "SubFinalConclu";
				break;
			case 38:
				strFieldName = "SubFinalOper";
				break;
			case 39:
				strFieldName = "SubFinalDate";
				break;
			case 40:
				strFieldName = "SubFinalTime";
				break;
			case 41:
				strFieldName = "MakeOperator";
				break;
			case 42:
				strFieldName = "MakeDate";
				break;
			case 43:
				strFieldName = "MakeTime";
				break;
			case 44:
				strFieldName = "ModifyOperator";
				break;
			case 45:
				strFieldName = "ModifyDate";
				break;
			case 46:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SubUWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUrgentFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BranchUWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysUWLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsArrange") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWManagerOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWManagerConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWManager") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWManagerDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWManagerTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchFinalOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchFinalConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchFinalOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchFinalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BranchFinalTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFinalOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFinalConclu") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFinalOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFinalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SubFinalTime") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
