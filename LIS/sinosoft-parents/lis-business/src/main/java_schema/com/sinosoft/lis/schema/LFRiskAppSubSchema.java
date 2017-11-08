/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

// schema 与数据库不一致，暂不处理

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LFRiskAppSubDB;
import org.apache.log4j.Logger;

/*
 * 与数据库结构不一致，暂不处理
 * <p>ClassName: LFRiskAppSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: msreport
 */
public class LFRiskAppSubSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(LFRiskAppSubSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 管理机构 */
	private String ManageCom;
	/** 缴费间隔 */
	private int PayIntv;
	/** 销售渠道 */
	private String SaleChnl;
	/** 首期续期标志 */
	private String FirstPayFlag;
	/** 团单个单标志 */
	private String PersonPolFlag;
	/** 报表日期 */
	private Date ReportDate;
	/** 度量类型 */
	private String MeasurementType;
	/** 值_新增 */
	private double CurYearValue;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 专项标记 */
	private String EspecialType;
	/** 重大疾病标记 */
	private String SickType;
	/** 费用标记 */
	private String FeeType;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFRiskAppSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[11];
		pk[0] = "RiskCode";
		pk[1] = "ManageCom";
		pk[2] = "PayIntv";
		pk[3] = "SaleChnl";
		pk[4] = "FirstPayFlag";
		pk[5] = "PersonPolFlag";
		pk[6] = "ReportDate";
		pk[7] = "MeasurementType";
		pk[8] = "EspecialType";
		pk[9] = "SickType";
		pk[10] = "FeeType";

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
		LFRiskAppSubSchema cloned = (LFRiskAppSubSchema)super.clone();
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
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
		ManageCom = aManageCom;
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
	* 01--个人代理<p>
	* 02--公司直销<p>
	* 03--保险专业代理<p>
	* 04--银行邮政代理<p>
	* 05--其他兼业代理<p>
	* 06--保险经纪业务
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
	}
	/**
	* 1--首期<p>
	* 2--续期
	*/
	public String getFirstPayFlag()
	{
		return FirstPayFlag;
	}
	public void setFirstPayFlag(String aFirstPayFlag)
	{
		if(aFirstPayFlag!=null && aFirstPayFlag.length()>1)
			throw new IllegalArgumentException("首期续期标志FirstPayFlag值"+aFirstPayFlag+"的长度"+aFirstPayFlag.length()+"大于最大值1");
		FirstPayFlag = aFirstPayFlag;
	}
	public String getPersonPolFlag()
	{
		return PersonPolFlag;
	}
	public void setPersonPolFlag(String aPersonPolFlag)
	{
		if(aPersonPolFlag!=null && aPersonPolFlag.length()>1)
			throw new IllegalArgumentException("团单个单标志PersonPolFlag值"+aPersonPolFlag+"的长度"+aPersonPolFlag.length()+"大于最大值1");
		PersonPolFlag = aPersonPolFlag;
	}
	public String getReportDate()
	{
		if( ReportDate != null )
			return fDate.getString(ReportDate);
		else
			return null;
	}
	public void setReportDate(Date aReportDate)
	{
		ReportDate = aReportDate;
	}
	public void setReportDate(String aReportDate)
	{
		if (aReportDate != null && !aReportDate.equals("") )
		{
			ReportDate = fDate.getDate( aReportDate );
		}
		else
			ReportDate = null;
	}

	/**
	* 11 －－ 本年累计保费新增<p>
	* 12 －－ 期末有效保费新增<p>
	* 21 －－ 本年累计保额新增<p>
	* 22 －－ 期末有效保额新增<p>
	* 31 －－ 本年累计人次新增<p>
	* 32 －－ 期末有效人次新增<p>
	* 41 －－ 本年累计件次新增<p>
	* 42 －－ 期末有效件次新增
	*/
	public String getMeasurementType()
	{
		return MeasurementType;
	}
	public void setMeasurementType(String aMeasurementType)
	{
		if(aMeasurementType!=null && aMeasurementType.length()>2)
			throw new IllegalArgumentException("度量类型MeasurementType值"+aMeasurementType+"的长度"+aMeasurementType.length()+"大于最大值2");
		MeasurementType = aMeasurementType;
	}
	public double getCurYearValue()
	{
		return CurYearValue;
	}
	public void setCurYearValue(double aCurYearValue)
	{
		CurYearValue = aCurYearValue;
	}
	public void setCurYearValue(String aCurYearValue)
	{
		if (aCurYearValue != null && !aCurYearValue.equals(""))
		{
			Double tDouble = new Double(aCurYearValue);
			double d = tDouble.doubleValue();
			CurYearValue = d;
		}
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
	/**
	* especialtype---专项标记<p>
	* 1    新农合             <p>
	* 2    新农合补充         <p>
	* 3    城镇职工基本医疗<p>
	* 4    城镇职工补充医疗<p>
	* 5    城镇居民基本医疗<p>
	* 6    城镇居民补充医疗<p>
	* 7    医疗救助<p>
	* 8    企事业团体补充医疗<p>
	* 9    其他委托管理业务
	*/
	public String getEspecialType()
	{
		return EspecialType;
	}
	public void setEspecialType(String aEspecialType)
	{
		if(aEspecialType!=null && aEspecialType.length()>1)
			throw new IllegalArgumentException("专项标记EspecialType值"+aEspecialType+"的长度"+aEspecialType.length()+"大于最大值1");
		EspecialType = aEspecialType;
	}
	/**
	* sicktype---重大疾病标记<p>
	*  01  恶性肿瘤<p>
	*  02  急性心肌梗塞<p>
	*  03  脑中风后遗症<p>
	*  04  重大器官移植术或造血干细胞移植术<p>
	*  05  冠状动脉搭桥术（或称冠状动脉旁路移植术）<p>
	*  06  终末期肾病（或称慢性肾功能衰竭尿毒症期）<p>
	*  07  其他重大疾病<p>
	*  08  其他险因
	*/
	public String getSickType()
	{
		return SickType;
	}
	public void setSickType(String aSickType)
	{
		if(aSickType!=null && aSickType.length()>2)
			throw new IllegalArgumentException("重大疾病标记SickType值"+aSickType+"的长度"+aSickType.length()+"大于最大值2");
		SickType = aSickType;
	}
	/**
	* feetype ---费用标记 住院/门诊/其他<p>
	* 1  住院<p>
	* 2  门诊 <p>
	* 3  其他
	*/
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		if(aFeeType!=null && aFeeType.length()>1)
			throw new IllegalArgumentException("费用标记FeeType值"+aFeeType+"的长度"+aFeeType.length()+"大于最大值1");
		FeeType = aFeeType;
	}

	/**
	* 使用另外一个 LFRiskAppSubSchema 对象给 Schema 赋值
	* @param: aLFRiskAppSubSchema LFRiskAppSubSchema
	**/
	public void setSchema(LFRiskAppSubSchema aLFRiskAppSubSchema)
	{
		this.RiskCode = aLFRiskAppSubSchema.getRiskCode();
		this.ManageCom = aLFRiskAppSubSchema.getManageCom();
		this.PayIntv = aLFRiskAppSubSchema.getPayIntv();
		this.SaleChnl = aLFRiskAppSubSchema.getSaleChnl();
		this.FirstPayFlag = aLFRiskAppSubSchema.getFirstPayFlag();
		this.PersonPolFlag = aLFRiskAppSubSchema.getPersonPolFlag();
		this.ReportDate = fDate.getDate( aLFRiskAppSubSchema.getReportDate());
		this.MeasurementType = aLFRiskAppSubSchema.getMeasurementType();
		this.CurYearValue = aLFRiskAppSubSchema.getCurYearValue();
		this.MakeDate = fDate.getDate( aLFRiskAppSubSchema.getMakeDate());
		this.MakeTime = aLFRiskAppSubSchema.getMakeTime();
		this.EspecialType = aLFRiskAppSubSchema.getEspecialType();
		this.SickType = aLFRiskAppSubSchema.getSickType();
		this.FeeType = aLFRiskAppSubSchema.getFeeType();
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

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.PayIntv = rs.getInt("PayIntv");
			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("FirstPayFlag") == null )
				this.FirstPayFlag = null;
			else
				this.FirstPayFlag = rs.getString("FirstPayFlag").trim();

			if( rs.getString("PersonPolFlag") == null )
				this.PersonPolFlag = null;
			else
				this.PersonPolFlag = rs.getString("PersonPolFlag").trim();

			this.ReportDate = rs.getDate("ReportDate");
			if( rs.getString("MeasurementType") == null )
				this.MeasurementType = null;
			else
				this.MeasurementType = rs.getString("MeasurementType").trim();

			this.CurYearValue = rs.getDouble("CurYearValue");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("EspecialType") == null )
				this.EspecialType = null;
			else
				this.EspecialType = rs.getString("EspecialType").trim();

			if( rs.getString("SickType") == null )
				this.SickType = null;
			else
				this.SickType = rs.getString("SickType").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFRiskAppSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFRiskAppSubSchema getSchema()
	{
		LFRiskAppSubSchema aLFRiskAppSubSchema = new LFRiskAppSubSchema();
		aLFRiskAppSubSchema.setSchema(this);
		return aLFRiskAppSubSchema;
	}

	public LFRiskAppSubDB getDB()
	{
		LFRiskAppSubDB aDBOper = new LFRiskAppSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFRiskAppSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayIntv));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PersonPolFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReportDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MeasurementType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurYearValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EspecialType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SickType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFRiskAppSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayIntv= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FirstPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PersonPolFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReportDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MeasurementType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CurYearValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			EspecialType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SickType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFRiskAppSubSchema";
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("PayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayIntv));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("FirstPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstPayFlag));
		}
		if (FCode.equalsIgnoreCase("PersonPolFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PersonPolFlag));
		}
		if (FCode.equalsIgnoreCase("ReportDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReportDate()));
		}
		if (FCode.equalsIgnoreCase("MeasurementType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MeasurementType));
		}
		if (FCode.equalsIgnoreCase("CurYearValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurYearValue));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("EspecialType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EspecialType));
		}
		if (FCode.equalsIgnoreCase("SickType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SickType));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
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
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 2:
				strFieldValue = String.valueOf(PayIntv);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FirstPayFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PersonPolFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReportDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MeasurementType);
				break;
			case 8:
				strFieldValue = String.valueOf(CurYearValue);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(EspecialType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SickType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("FirstPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstPayFlag = FValue.trim();
			}
			else
				FirstPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("PersonPolFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PersonPolFlag = FValue.trim();
			}
			else
				PersonPolFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReportDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReportDate = fDate.getDate( FValue );
			}
			else
				ReportDate = null;
		}
		if (FCode.equalsIgnoreCase("MeasurementType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MeasurementType = FValue.trim();
			}
			else
				MeasurementType = null;
		}
		if (FCode.equalsIgnoreCase("CurYearValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurYearValue = d;
			}
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
		if (FCode.equalsIgnoreCase("EspecialType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EspecialType = FValue.trim();
			}
			else
				EspecialType = null;
		}
		if (FCode.equalsIgnoreCase("SickType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SickType = FValue.trim();
			}
			else
				SickType = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFRiskAppSubSchema other = (LFRiskAppSubSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ManageCom.equals(other.getManageCom())
			&& PayIntv == other.getPayIntv()
			&& SaleChnl.equals(other.getSaleChnl())
			&& FirstPayFlag.equals(other.getFirstPayFlag())
			&& PersonPolFlag.equals(other.getPersonPolFlag())
			&& fDate.getString(ReportDate).equals(other.getReportDate())
			&& MeasurementType.equals(other.getMeasurementType())
			&& CurYearValue == other.getCurYearValue()
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& EspecialType.equals(other.getEspecialType())
			&& SickType.equals(other.getSickType())
			&& FeeType.equals(other.getFeeType());
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
		if( strFieldName.equals("ManageCom") ) {
			return 1;
		}
		if( strFieldName.equals("PayIntv") ) {
			return 2;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 3;
		}
		if( strFieldName.equals("FirstPayFlag") ) {
			return 4;
		}
		if( strFieldName.equals("PersonPolFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ReportDate") ) {
			return 6;
		}
		if( strFieldName.equals("MeasurementType") ) {
			return 7;
		}
		if( strFieldName.equals("CurYearValue") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("EspecialType") ) {
			return 11;
		}
		if( strFieldName.equals("SickType") ) {
			return 12;
		}
		if( strFieldName.equals("FeeType") ) {
			return 13;
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
				strFieldName = "ManageCom";
				break;
			case 2:
				strFieldName = "PayIntv";
				break;
			case 3:
				strFieldName = "SaleChnl";
				break;
			case 4:
				strFieldName = "FirstPayFlag";
				break;
			case 5:
				strFieldName = "PersonPolFlag";
				break;
			case 6:
				strFieldName = "ReportDate";
				break;
			case 7:
				strFieldName = "MeasurementType";
				break;
			case 8:
				strFieldName = "CurYearValue";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "EspecialType";
				break;
			case 12:
				strFieldName = "SickType";
				break;
			case 13:
				strFieldName = "FeeType";
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayIntv") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PersonPolFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MeasurementType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurYearValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EspecialType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SickType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
