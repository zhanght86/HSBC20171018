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
import com.sinosoft.lis.db.LSQuotGetCalModeDB;

/*
 * <p>ClassName: LSQuotGetCalModeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotGetCalModeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotGetCalModeSchema.class);
	// @Field
	/** 序号 */
	private String SerialNo;
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 退费类型 */
	private String GetType;
	/** 费用比例值 */
	private String FeeValue;
	/** 计算公式 */
	private String FeeCalCode;
	/** 算法编码 */
	private String CalCode;
	/** 算法编码2 */
	private String CalCode2;
	/** 有效区间单位 */
	private String ValPeriod;
	/** 有效起期 */
	private String ValStartDate;
	/** 有效止期 */
	private String ValEndDate;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotGetCalModeSchema()
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
		LSQuotGetCalModeSchema cloned = (LSQuotGetCalModeSchema)super.clone();
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
			throw new IllegalArgumentException("序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值10");
		SerialNo = aSerialNo;
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

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	/**
	* 预留
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>10)
			throw new IllegalArgumentException("账户类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值10");
		AccType = aAccType;
	}
	/**
	* ZT-减人退费<p>
	* CT-退保退费
	*/
	public String getGetType()
	{
		return GetType;
	}
	public void setGetType(String aGetType)
	{
		if(aGetType!=null && aGetType.length()>6)
			throw new IllegalArgumentException("退费类型GetType值"+aGetType+"的长度"+aGetType.length()+"大于最大值6");
		GetType = aGetType;
	}
	public String getFeeValue()
	{
		return FeeValue;
	}
	public void setFeeValue(String aFeeValue)
	{
		if(aFeeValue!=null && aFeeValue.length()>20)
			throw new IllegalArgumentException("费用比例值FeeValue值"+aFeeValue+"的长度"+aFeeValue.length()+"大于最大值20");
		FeeValue = aFeeValue;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
		if(aFeeCalCode!=null && aFeeCalCode.length()>6)
			throw new IllegalArgumentException("计算公式FeeCalCode值"+aFeeCalCode+"的长度"+aFeeCalCode.length()+"大于最大值6");
		FeeCalCode = aFeeCalCode;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		if(aCalCode!=null && aCalCode.length()>20)
			throw new IllegalArgumentException("算法编码CalCode值"+aCalCode+"的长度"+aCalCode.length()+"大于最大值20");
		CalCode = aCalCode;
	}
	/**
	* 预留
	*/
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		if(aCalCode2!=null && aCalCode2.length()>20)
			throw new IllegalArgumentException("算法编码2CalCode2值"+aCalCode2+"的长度"+aCalCode2.length()+"大于最大值20");
		CalCode2 = aCalCode2;
	}
	/**
	* Y-年<p>
	* D-日
	*/
	public String getValPeriod()
	{
		return ValPeriod;
	}
	public void setValPeriod(String aValPeriod)
	{
		if(aValPeriod!=null && aValPeriod.length()>6)
			throw new IllegalArgumentException("有效区间单位ValPeriod值"+aValPeriod+"的长度"+aValPeriod.length()+"大于最大值6");
		ValPeriod = aValPeriod;
	}
	public String getValStartDate()
	{
		return ValStartDate;
	}
	public void setValStartDate(String aValStartDate)
	{
		if(aValStartDate!=null && aValStartDate.length()>6)
			throw new IllegalArgumentException("有效起期ValStartDate值"+aValStartDate+"的长度"+aValStartDate.length()+"大于最大值6");
		ValStartDate = aValStartDate;
	}
	public String getValEndDate()
	{
		return ValEndDate;
	}
	public void setValEndDate(String aValEndDate)
	{
		if(aValEndDate!=null && aValEndDate.length()>6)
			throw new IllegalArgumentException("有效止期ValEndDate值"+aValEndDate+"的长度"+aValEndDate.length()+"大于最大值6");
		ValEndDate = aValEndDate;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>200)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值200");
		Segment3 = aSegment3;
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
	* 使用另外一个 LSQuotGetCalModeSchema 对象给 Schema 赋值
	* @param: aLSQuotGetCalModeSchema LSQuotGetCalModeSchema
	**/
	public void setSchema(LSQuotGetCalModeSchema aLSQuotGetCalModeSchema)
	{
		this.SerialNo = aLSQuotGetCalModeSchema.getSerialNo();
		this.QuotNo = aLSQuotGetCalModeSchema.getQuotNo();
		this.QuotBatNo = aLSQuotGetCalModeSchema.getQuotBatNo();
		this.RiskCode = aLSQuotGetCalModeSchema.getRiskCode();
		this.AccType = aLSQuotGetCalModeSchema.getAccType();
		this.GetType = aLSQuotGetCalModeSchema.getGetType();
		this.FeeValue = aLSQuotGetCalModeSchema.getFeeValue();
		this.FeeCalCode = aLSQuotGetCalModeSchema.getFeeCalCode();
		this.CalCode = aLSQuotGetCalModeSchema.getCalCode();
		this.CalCode2 = aLSQuotGetCalModeSchema.getCalCode2();
		this.ValPeriod = aLSQuotGetCalModeSchema.getValPeriod();
		this.ValStartDate = aLSQuotGetCalModeSchema.getValStartDate();
		this.ValEndDate = aLSQuotGetCalModeSchema.getValEndDate();
		this.Segment1 = aLSQuotGetCalModeSchema.getSegment1();
		this.Segment2 = aLSQuotGetCalModeSchema.getSegment2();
		this.Segment3 = aLSQuotGetCalModeSchema.getSegment3();
		this.ManageCom = aLSQuotGetCalModeSchema.getManageCom();
		this.ComCode = aLSQuotGetCalModeSchema.getComCode();
		this.MakeOperator = aLSQuotGetCalModeSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotGetCalModeSchema.getMakeDate());
		this.MakeTime = aLSQuotGetCalModeSchema.getMakeTime();
		this.ModifyOperator = aLSQuotGetCalModeSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotGetCalModeSchema.getModifyDate());
		this.ModifyTime = aLSQuotGetCalModeSchema.getModifyTime();
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

			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("GetType") == null )
				this.GetType = null;
			else
				this.GetType = rs.getString("GetType").trim();

			if( rs.getString("FeeValue") == null )
				this.FeeValue = null;
			else
				this.FeeValue = rs.getString("FeeValue").trim();

			if( rs.getString("FeeCalCode") == null )
				this.FeeCalCode = null;
			else
				this.FeeCalCode = rs.getString("FeeCalCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

			if( rs.getString("ValPeriod") == null )
				this.ValPeriod = null;
			else
				this.ValPeriod = rs.getString("ValPeriod").trim();

			if( rs.getString("ValStartDate") == null )
				this.ValStartDate = null;
			else
				this.ValStartDate = rs.getString("ValStartDate").trim();

			if( rs.getString("ValEndDate") == null )
				this.ValEndDate = null;
			else
				this.ValEndDate = rs.getString("ValEndDate").trim();

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
			logger.debug("数据库中的LSQuotGetCalMode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotGetCalModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotGetCalModeSchema getSchema()
	{
		LSQuotGetCalModeSchema aLSQuotGetCalModeSchema = new LSQuotGetCalModeSchema();
		aLSQuotGetCalModeSchema.setSchema(this);
		return aLSQuotGetCalModeSchema;
	}

	public LSQuotGetCalModeDB getDB()
	{
		LSQuotGetCalModeDB aDBOper = new LSQuotGetCalModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotGetCalMode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValStartDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValEndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotGetCalMode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ValPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ValStartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ValEndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotGetCalModeSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("GetType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetType));
		}
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeValue));
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalCode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
		}
		if (FCode.equalsIgnoreCase("ValPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValPeriod));
		}
		if (FCode.equalsIgnoreCase("ValStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValStartDate));
		}
		if (FCode.equalsIgnoreCase("ValEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValEndDate));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 2:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FeeValue);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ValPeriod);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ValStartDate);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ValEndDate);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("GetType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetType = FValue.trim();
			}
			else
				GetType = null;
		}
		if (FCode.equalsIgnoreCase("FeeValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeValue = FValue.trim();
			}
			else
				FeeValue = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalCode = FValue.trim();
			}
			else
				FeeCalCode = null;
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
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode2 = FValue.trim();
			}
			else
				CalCode2 = null;
		}
		if (FCode.equalsIgnoreCase("ValPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValPeriod = FValue.trim();
			}
			else
				ValPeriod = null;
		}
		if (FCode.equalsIgnoreCase("ValStartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValStartDate = FValue.trim();
			}
			else
				ValStartDate = null;
		}
		if (FCode.equalsIgnoreCase("ValEndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValEndDate = FValue.trim();
			}
			else
				ValEndDate = null;
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
		LSQuotGetCalModeSchema other = (LSQuotGetCalModeSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& GetType.equals(other.getGetType())
			&& FeeValue.equals(other.getFeeValue())
			&& FeeCalCode.equals(other.getFeeCalCode())
			&& CalCode.equals(other.getCalCode())
			&& CalCode2.equals(other.getCalCode2())
			&& ValPeriod.equals(other.getValPeriod())
			&& ValStartDate.equals(other.getValStartDate())
			&& ValEndDate.equals(other.getValEndDate())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
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
		if( strFieldName.equals("QuotNo") ) {
			return 1;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("AccType") ) {
			return 4;
		}
		if( strFieldName.equals("GetType") ) {
			return 5;
		}
		if( strFieldName.equals("FeeValue") ) {
			return 6;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return 7;
		}
		if( strFieldName.equals("CalCode") ) {
			return 8;
		}
		if( strFieldName.equals("CalCode2") ) {
			return 9;
		}
		if( strFieldName.equals("ValPeriod") ) {
			return 10;
		}
		if( strFieldName.equals("ValStartDate") ) {
			return 11;
		}
		if( strFieldName.equals("ValEndDate") ) {
			return 12;
		}
		if( strFieldName.equals("Segment1") ) {
			return 13;
		}
		if( strFieldName.equals("Segment2") ) {
			return 14;
		}
		if( strFieldName.equals("Segment3") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("ComCode") ) {
			return 17;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
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
				strFieldName = "QuotNo";
				break;
			case 2:
				strFieldName = "QuotBatNo";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "AccType";
				break;
			case 5:
				strFieldName = "GetType";
				break;
			case 6:
				strFieldName = "FeeValue";
				break;
			case 7:
				strFieldName = "FeeCalCode";
				break;
			case 8:
				strFieldName = "CalCode";
				break;
			case 9:
				strFieldName = "CalCode2";
				break;
			case 10:
				strFieldName = "ValPeriod";
				break;
			case 11:
				strFieldName = "ValStartDate";
				break;
			case 12:
				strFieldName = "ValEndDate";
				break;
			case 13:
				strFieldName = "Segment1";
				break;
			case 14:
				strFieldName = "Segment2";
				break;
			case 15:
				strFieldName = "Segment3";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "ComCode";
				break;
			case 18:
				strFieldName = "MakeOperator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyOperator";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValStartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValEndDate") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
