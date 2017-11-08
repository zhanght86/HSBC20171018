

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
import com.sinosoft.lis.db.RICalParamBDB;

/*
 * <p>ClassName: RICalParamBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RICalParamBSchema implements Schema, Cloneable
{
	// @Field
	/** 备份序列号 */
	private String BakeSerialNo;
	/** 序列号 */
	private String SerialNo;
	/** 事件编号 */
	private String EventNo;
	/** 批次号 */
	private String BatchNo;
	/** 其他号码 */
	private String OtherNo;
	/** 合同号码 */
	private String ContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 区域编号 */
	private String AreaID;
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 数字型计算参数1 */
	private double ParamDouble1;
	/** 数字型计算参数2 */
	private double ParamDouble2;
	/** 数字型计算参数3 */
	private double ParamDouble3;
	/** 数字型计算参数4 */
	private double ParamDouble4;
	/** 数字型计算参数5 */
	private double ParamDouble5;
	/** 数字型计算参数6 */
	private double ParamDouble6;
	/** 数字型计算参数7 */
	private double ParamDouble7;
	/** 数字型计算参数8 */
	private double ParamDouble8;
	/** 数字型计算参数9 */
	private double ParamDouble9;
	/** 数字型计算参数10 */
	private double ParamDouble10;
	/** 字符型计算参数1 */
	private String ParamString1;
	/** 字符型计算参数2 */
	private String ParamString2;
	/** 字符型计算参数3 */
	private String ParamString3;
	/** 字符型计算参数4 */
	private String ParamString4;
	/** 字符型计算参数5 */
	private String ParamString5;
	/** 字符型计算参数6 */
	private String ParamString6;
	/** 日期型计算参数1 */
	private Date ParamDate1;
	/** 日期型计算参数2 */
	private Date ParamDate2;
	/** 日期型计算参数3 */
	private Date ParamDate3;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RICalParamBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "BakeSerialNo";
		pk[1] = "SerialNo";

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
		RICalParamBSchema cloned = (RICalParamBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBakeSerialNo()
	{
		return BakeSerialNo;
	}
	public void setBakeSerialNo(String aBakeSerialNo)
	{
		BakeSerialNo = aBakeSerialNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getEventNo()
	{
		return EventNo;
	}
	public void setEventNo(String aEventNo)
	{
		EventNo = aEventNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	/**
	* AccumulateMode = 01-个人单合同累计  OtherNo=contno 个人合同号码<p>
	* AccumulateMode = 02-个人多合同累计  OtherNo=contno 个人客户号码<p>
	* AccumulateMode = 03-多人多合同累计  OtherNo=“000000”
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 团单为contno 个单为contno + ',' + insuredno
	*/
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getAreaID()
	{
		return AreaID;
	}
	public void setAreaID(String aAreaID)
	{
		AreaID = aAreaID;
	}
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public double getParamDouble1()
	{
		return ParamDouble1;
	}
	public void setParamDouble1(double aParamDouble1)
	{
		ParamDouble1 = aParamDouble1;
	}
	public void setParamDouble1(String aParamDouble1)
	{
		if (aParamDouble1 != null && !aParamDouble1.equals(""))
		{
			Double tDouble = new Double(aParamDouble1);
			double d = tDouble.doubleValue();
			ParamDouble1 = d;
		}
	}

	public double getParamDouble2()
	{
		return ParamDouble2;
	}
	public void setParamDouble2(double aParamDouble2)
	{
		ParamDouble2 = aParamDouble2;
	}
	public void setParamDouble2(String aParamDouble2)
	{
		if (aParamDouble2 != null && !aParamDouble2.equals(""))
		{
			Double tDouble = new Double(aParamDouble2);
			double d = tDouble.doubleValue();
			ParamDouble2 = d;
		}
	}

	public double getParamDouble3()
	{
		return ParamDouble3;
	}
	public void setParamDouble3(double aParamDouble3)
	{
		ParamDouble3 = aParamDouble3;
	}
	public void setParamDouble3(String aParamDouble3)
	{
		if (aParamDouble3 != null && !aParamDouble3.equals(""))
		{
			Double tDouble = new Double(aParamDouble3);
			double d = tDouble.doubleValue();
			ParamDouble3 = d;
		}
	}

	public double getParamDouble4()
	{
		return ParamDouble4;
	}
	public void setParamDouble4(double aParamDouble4)
	{
		ParamDouble4 = aParamDouble4;
	}
	public void setParamDouble4(String aParamDouble4)
	{
		if (aParamDouble4 != null && !aParamDouble4.equals(""))
		{
			Double tDouble = new Double(aParamDouble4);
			double d = tDouble.doubleValue();
			ParamDouble4 = d;
		}
	}

	public double getParamDouble5()
	{
		return ParamDouble5;
	}
	public void setParamDouble5(double aParamDouble5)
	{
		ParamDouble5 = aParamDouble5;
	}
	public void setParamDouble5(String aParamDouble5)
	{
		if (aParamDouble5 != null && !aParamDouble5.equals(""))
		{
			Double tDouble = new Double(aParamDouble5);
			double d = tDouble.doubleValue();
			ParamDouble5 = d;
		}
	}

	public double getParamDouble6()
	{
		return ParamDouble6;
	}
	public void setParamDouble6(double aParamDouble6)
	{
		ParamDouble6 = aParamDouble6;
	}
	public void setParamDouble6(String aParamDouble6)
	{
		if (aParamDouble6 != null && !aParamDouble6.equals(""))
		{
			Double tDouble = new Double(aParamDouble6);
			double d = tDouble.doubleValue();
			ParamDouble6 = d;
		}
	}

	public double getParamDouble7()
	{
		return ParamDouble7;
	}
	public void setParamDouble7(double aParamDouble7)
	{
		ParamDouble7 = aParamDouble7;
	}
	public void setParamDouble7(String aParamDouble7)
	{
		if (aParamDouble7 != null && !aParamDouble7.equals(""))
		{
			Double tDouble = new Double(aParamDouble7);
			double d = tDouble.doubleValue();
			ParamDouble7 = d;
		}
	}

	public double getParamDouble8()
	{
		return ParamDouble8;
	}
	public void setParamDouble8(double aParamDouble8)
	{
		ParamDouble8 = aParamDouble8;
	}
	public void setParamDouble8(String aParamDouble8)
	{
		if (aParamDouble8 != null && !aParamDouble8.equals(""))
		{
			Double tDouble = new Double(aParamDouble8);
			double d = tDouble.doubleValue();
			ParamDouble8 = d;
		}
	}

	public double getParamDouble9()
	{
		return ParamDouble9;
	}
	public void setParamDouble9(double aParamDouble9)
	{
		ParamDouble9 = aParamDouble9;
	}
	public void setParamDouble9(String aParamDouble9)
	{
		if (aParamDouble9 != null && !aParamDouble9.equals(""))
		{
			Double tDouble = new Double(aParamDouble9);
			double d = tDouble.doubleValue();
			ParamDouble9 = d;
		}
	}

	public double getParamDouble10()
	{
		return ParamDouble10;
	}
	public void setParamDouble10(double aParamDouble10)
	{
		ParamDouble10 = aParamDouble10;
	}
	public void setParamDouble10(String aParamDouble10)
	{
		if (aParamDouble10 != null && !aParamDouble10.equals(""))
		{
			Double tDouble = new Double(aParamDouble10);
			double d = tDouble.doubleValue();
			ParamDouble10 = d;
		}
	}

	/**
	* caseno
	*/
	public String getParamString1()
	{
		return ParamString1;
	}
	public void setParamString1(String aParamString1)
	{
		ParamString1 = aParamString1;
	}
	public String getParamString2()
	{
		return ParamString2;
	}
	public void setParamString2(String aParamString2)
	{
		ParamString2 = aParamString2;
	}
	public String getParamString3()
	{
		return ParamString3;
	}
	public void setParamString3(String aParamString3)
	{
		ParamString3 = aParamString3;
	}
	/**
	* caseno
	*/
	public String getParamString4()
	{
		return ParamString4;
	}
	public void setParamString4(String aParamString4)
	{
		ParamString4 = aParamString4;
	}
	public String getParamString5()
	{
		return ParamString5;
	}
	public void setParamString5(String aParamString5)
	{
		ParamString5 = aParamString5;
	}
	public String getParamString6()
	{
		return ParamString6;
	}
	public void setParamString6(String aParamString6)
	{
		ParamString6 = aParamString6;
	}
	public String getParamDate1()
	{
		if( ParamDate1 != null )
			return fDate.getString(ParamDate1);
		else
			return null;
	}
	public void setParamDate1(Date aParamDate1)
	{
		ParamDate1 = aParamDate1;
	}
	public void setParamDate1(String aParamDate1)
	{
		if (aParamDate1 != null && !aParamDate1.equals("") )
		{
			ParamDate1 = fDate.getDate( aParamDate1 );
		}
		else
			ParamDate1 = null;
	}

	public String getParamDate2()
	{
		if( ParamDate2 != null )
			return fDate.getString(ParamDate2);
		else
			return null;
	}
	public void setParamDate2(Date aParamDate2)
	{
		ParamDate2 = aParamDate2;
	}
	public void setParamDate2(String aParamDate2)
	{
		if (aParamDate2 != null && !aParamDate2.equals("") )
		{
			ParamDate2 = fDate.getDate( aParamDate2 );
		}
		else
			ParamDate2 = null;
	}

	public String getParamDate3()
	{
		if( ParamDate3 != null )
			return fDate.getString(ParamDate3);
		else
			return null;
	}
	public void setParamDate3(Date aParamDate3)
	{
		ParamDate3 = aParamDate3;
	}
	public void setParamDate3(String aParamDate3)
	{
		if (aParamDate3 != null && !aParamDate3.equals("") )
		{
			ParamDate3 = fDate.getDate( aParamDate3 );
		}
		else
			ParamDate3 = null;
	}


	/**
	* 使用另外一个 RICalParamBSchema 对象给 Schema 赋值
	* @param: aRICalParamBSchema RICalParamBSchema
	**/
	public void setSchema(RICalParamBSchema aRICalParamBSchema)
	{
		this.BakeSerialNo = aRICalParamBSchema.getBakeSerialNo();
		this.SerialNo = aRICalParamBSchema.getSerialNo();
		this.EventNo = aRICalParamBSchema.getEventNo();
		this.BatchNo = aRICalParamBSchema.getBatchNo();
		this.OtherNo = aRICalParamBSchema.getOtherNo();
		this.ContNo = aRICalParamBSchema.getContNo();
		this.RiskCode = aRICalParamBSchema.getRiskCode();
		this.DutyCode = aRICalParamBSchema.getDutyCode();
		this.AreaID = aRICalParamBSchema.getAreaID();
		this.AccumulateDefNO = aRICalParamBSchema.getAccumulateDefNO();
		this.RIPreceptNo = aRICalParamBSchema.getRIPreceptNo();
		this.ParamDouble1 = aRICalParamBSchema.getParamDouble1();
		this.ParamDouble2 = aRICalParamBSchema.getParamDouble2();
		this.ParamDouble3 = aRICalParamBSchema.getParamDouble3();
		this.ParamDouble4 = aRICalParamBSchema.getParamDouble4();
		this.ParamDouble5 = aRICalParamBSchema.getParamDouble5();
		this.ParamDouble6 = aRICalParamBSchema.getParamDouble6();
		this.ParamDouble7 = aRICalParamBSchema.getParamDouble7();
		this.ParamDouble8 = aRICalParamBSchema.getParamDouble8();
		this.ParamDouble9 = aRICalParamBSchema.getParamDouble9();
		this.ParamDouble10 = aRICalParamBSchema.getParamDouble10();
		this.ParamString1 = aRICalParamBSchema.getParamString1();
		this.ParamString2 = aRICalParamBSchema.getParamString2();
		this.ParamString3 = aRICalParamBSchema.getParamString3();
		this.ParamString4 = aRICalParamBSchema.getParamString4();
		this.ParamString5 = aRICalParamBSchema.getParamString5();
		this.ParamString6 = aRICalParamBSchema.getParamString6();
		this.ParamDate1 = fDate.getDate( aRICalParamBSchema.getParamDate1());
		this.ParamDate2 = fDate.getDate( aRICalParamBSchema.getParamDate2());
		this.ParamDate3 = fDate.getDate( aRICalParamBSchema.getParamDate3());
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
			if( rs.getString("BakeSerialNo") == null )
				this.BakeSerialNo = null;
			else
				this.BakeSerialNo = rs.getString("BakeSerialNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("EventNo") == null )
				this.EventNo = null;
			else
				this.EventNo = rs.getString("EventNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("AreaID") == null )
				this.AreaID = null;
			else
				this.AreaID = rs.getString("AreaID").trim();

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			this.ParamDouble1 = rs.getDouble("ParamDouble1");
			this.ParamDouble2 = rs.getDouble("ParamDouble2");
			this.ParamDouble3 = rs.getDouble("ParamDouble3");
			this.ParamDouble4 = rs.getDouble("ParamDouble4");
			this.ParamDouble5 = rs.getDouble("ParamDouble5");
			this.ParamDouble6 = rs.getDouble("ParamDouble6");
			this.ParamDouble7 = rs.getDouble("ParamDouble7");
			this.ParamDouble8 = rs.getDouble("ParamDouble8");
			this.ParamDouble9 = rs.getDouble("ParamDouble9");
			this.ParamDouble10 = rs.getDouble("ParamDouble10");
			if( rs.getString("ParamString1") == null )
				this.ParamString1 = null;
			else
				this.ParamString1 = rs.getString("ParamString1").trim();

			if( rs.getString("ParamString2") == null )
				this.ParamString2 = null;
			else
				this.ParamString2 = rs.getString("ParamString2").trim();

			if( rs.getString("ParamString3") == null )
				this.ParamString3 = null;
			else
				this.ParamString3 = rs.getString("ParamString3").trim();

			if( rs.getString("ParamString4") == null )
				this.ParamString4 = null;
			else
				this.ParamString4 = rs.getString("ParamString4").trim();

			if( rs.getString("ParamString5") == null )
				this.ParamString5 = null;
			else
				this.ParamString5 = rs.getString("ParamString5").trim();

			if( rs.getString("ParamString6") == null )
				this.ParamString6 = null;
			else
				this.ParamString6 = rs.getString("ParamString6").trim();

			this.ParamDate1 = rs.getDate("ParamDate1");
			this.ParamDate2 = rs.getDate("ParamDate2");
			this.ParamDate3 = rs.getDate("ParamDate3");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RICalParamB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICalParamBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RICalParamBSchema getSchema()
	{
		RICalParamBSchema aRICalParamBSchema = new RICalParamBSchema();
		aRICalParamBSchema.setSchema(this);
		return aRICalParamBSchema;
	}

	public RICalParamBDB getDB()
	{
		RICalParamBDB aDBOper = new RICalParamBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICalParamB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BakeSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EventNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble6));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble7));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble8));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble9));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ParamDouble10));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamString6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ParamDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ParamDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ParamDate3 )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICalParamB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BakeSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EventNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AreaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ParamDouble1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble4 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble7 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble8 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble9 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			ParamDouble10 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			ParamString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ParamString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ParamString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ParamString4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ParamString5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ParamString6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ParamDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ParamDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ParamDate3 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICalParamBSchema";
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
		if (FCode.equalsIgnoreCase("BakeSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BakeSerialNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaID));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("ParamDouble1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble1));
		}
		if (FCode.equalsIgnoreCase("ParamDouble2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble2));
		}
		if (FCode.equalsIgnoreCase("ParamDouble3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble3));
		}
		if (FCode.equalsIgnoreCase("ParamDouble4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble4));
		}
		if (FCode.equalsIgnoreCase("ParamDouble5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble5));
		}
		if (FCode.equalsIgnoreCase("ParamDouble6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble6));
		}
		if (FCode.equalsIgnoreCase("ParamDouble7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble7));
		}
		if (FCode.equalsIgnoreCase("ParamDouble8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble8));
		}
		if (FCode.equalsIgnoreCase("ParamDouble9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble9));
		}
		if (FCode.equalsIgnoreCase("ParamDouble10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamDouble10));
		}
		if (FCode.equalsIgnoreCase("ParamString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString1));
		}
		if (FCode.equalsIgnoreCase("ParamString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString2));
		}
		if (FCode.equalsIgnoreCase("ParamString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString3));
		}
		if (FCode.equalsIgnoreCase("ParamString4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString4));
		}
		if (FCode.equalsIgnoreCase("ParamString5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString5));
		}
		if (FCode.equalsIgnoreCase("ParamString6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamString6));
		}
		if (FCode.equalsIgnoreCase("ParamDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getParamDate1()));
		}
		if (FCode.equalsIgnoreCase("ParamDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getParamDate2()));
		}
		if (FCode.equalsIgnoreCase("ParamDate3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getParamDate3()));
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
				strFieldValue = StrTool.GBKToUnicode(BakeSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EventNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AreaID);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 11:
				strFieldValue = String.valueOf(ParamDouble1);
				break;
			case 12:
				strFieldValue = String.valueOf(ParamDouble2);
				break;
			case 13:
				strFieldValue = String.valueOf(ParamDouble3);
				break;
			case 14:
				strFieldValue = String.valueOf(ParamDouble4);
				break;
			case 15:
				strFieldValue = String.valueOf(ParamDouble5);
				break;
			case 16:
				strFieldValue = String.valueOf(ParamDouble6);
				break;
			case 17:
				strFieldValue = String.valueOf(ParamDouble7);
				break;
			case 18:
				strFieldValue = String.valueOf(ParamDouble8);
				break;
			case 19:
				strFieldValue = String.valueOf(ParamDouble9);
				break;
			case 20:
				strFieldValue = String.valueOf(ParamDouble10);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ParamString1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ParamString2);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ParamString3);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ParamString4);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ParamString5);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ParamString6);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getParamDate1()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getParamDate2()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getParamDate3()));
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

		if (FCode.equalsIgnoreCase("BakeSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BakeSerialNo = FValue.trim();
			}
			else
				BakeSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventNo = FValue.trim();
			}
			else
				EventNo = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaID = FValue.trim();
			}
			else
				AreaID = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("ParamDouble1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble4 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble6 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble7 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble8 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble9 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamDouble10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ParamDouble10 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ParamString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString1 = FValue.trim();
			}
			else
				ParamString1 = null;
		}
		if (FCode.equalsIgnoreCase("ParamString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString2 = FValue.trim();
			}
			else
				ParamString2 = null;
		}
		if (FCode.equalsIgnoreCase("ParamString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString3 = FValue.trim();
			}
			else
				ParamString3 = null;
		}
		if (FCode.equalsIgnoreCase("ParamString4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString4 = FValue.trim();
			}
			else
				ParamString4 = null;
		}
		if (FCode.equalsIgnoreCase("ParamString5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString5 = FValue.trim();
			}
			else
				ParamString5 = null;
		}
		if (FCode.equalsIgnoreCase("ParamString6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamString6 = FValue.trim();
			}
			else
				ParamString6 = null;
		}
		if (FCode.equalsIgnoreCase("ParamDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ParamDate1 = fDate.getDate( FValue );
			}
			else
				ParamDate1 = null;
		}
		if (FCode.equalsIgnoreCase("ParamDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ParamDate2 = fDate.getDate( FValue );
			}
			else
				ParamDate2 = null;
		}
		if (FCode.equalsIgnoreCase("ParamDate3"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ParamDate3 = fDate.getDate( FValue );
			}
			else
				ParamDate3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RICalParamBSchema other = (RICalParamBSchema)otherObject;
		return
			BakeSerialNo.equals(other.getBakeSerialNo())
			&& SerialNo.equals(other.getSerialNo())
			&& EventNo.equals(other.getEventNo())
			&& BatchNo.equals(other.getBatchNo())
			&& OtherNo.equals(other.getOtherNo())
			&& ContNo.equals(other.getContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AreaID.equals(other.getAreaID())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& ParamDouble1 == other.getParamDouble1()
			&& ParamDouble2 == other.getParamDouble2()
			&& ParamDouble3 == other.getParamDouble3()
			&& ParamDouble4 == other.getParamDouble4()
			&& ParamDouble5 == other.getParamDouble5()
			&& ParamDouble6 == other.getParamDouble6()
			&& ParamDouble7 == other.getParamDouble7()
			&& ParamDouble8 == other.getParamDouble8()
			&& ParamDouble9 == other.getParamDouble9()
			&& ParamDouble10 == other.getParamDouble10()
			&& ParamString1.equals(other.getParamString1())
			&& ParamString2.equals(other.getParamString2())
			&& ParamString3.equals(other.getParamString3())
			&& ParamString4.equals(other.getParamString4())
			&& ParamString5.equals(other.getParamString5())
			&& ParamString6.equals(other.getParamString6())
			&& fDate.getString(ParamDate1).equals(other.getParamDate1())
			&& fDate.getString(ParamDate2).equals(other.getParamDate2())
			&& fDate.getString(ParamDate3).equals(other.getParamDate3());
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
		if( strFieldName.equals("BakeSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("EventNo") ) {
			return 2;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 4;
		}
		if( strFieldName.equals("ContNo") ) {
			return 5;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 6;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 7;
		}
		if( strFieldName.equals("AreaID") ) {
			return 8;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 9;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 10;
		}
		if( strFieldName.equals("ParamDouble1") ) {
			return 11;
		}
		if( strFieldName.equals("ParamDouble2") ) {
			return 12;
		}
		if( strFieldName.equals("ParamDouble3") ) {
			return 13;
		}
		if( strFieldName.equals("ParamDouble4") ) {
			return 14;
		}
		if( strFieldName.equals("ParamDouble5") ) {
			return 15;
		}
		if( strFieldName.equals("ParamDouble6") ) {
			return 16;
		}
		if( strFieldName.equals("ParamDouble7") ) {
			return 17;
		}
		if( strFieldName.equals("ParamDouble8") ) {
			return 18;
		}
		if( strFieldName.equals("ParamDouble9") ) {
			return 19;
		}
		if( strFieldName.equals("ParamDouble10") ) {
			return 20;
		}
		if( strFieldName.equals("ParamString1") ) {
			return 21;
		}
		if( strFieldName.equals("ParamString2") ) {
			return 22;
		}
		if( strFieldName.equals("ParamString3") ) {
			return 23;
		}
		if( strFieldName.equals("ParamString4") ) {
			return 24;
		}
		if( strFieldName.equals("ParamString5") ) {
			return 25;
		}
		if( strFieldName.equals("ParamString6") ) {
			return 26;
		}
		if( strFieldName.equals("ParamDate1") ) {
			return 27;
		}
		if( strFieldName.equals("ParamDate2") ) {
			return 28;
		}
		if( strFieldName.equals("ParamDate3") ) {
			return 29;
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
				strFieldName = "BakeSerialNo";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "EventNo";
				break;
			case 3:
				strFieldName = "BatchNo";
				break;
			case 4:
				strFieldName = "OtherNo";
				break;
			case 5:
				strFieldName = "ContNo";
				break;
			case 6:
				strFieldName = "RiskCode";
				break;
			case 7:
				strFieldName = "DutyCode";
				break;
			case 8:
				strFieldName = "AreaID";
				break;
			case 9:
				strFieldName = "AccumulateDefNO";
				break;
			case 10:
				strFieldName = "RIPreceptNo";
				break;
			case 11:
				strFieldName = "ParamDouble1";
				break;
			case 12:
				strFieldName = "ParamDouble2";
				break;
			case 13:
				strFieldName = "ParamDouble3";
				break;
			case 14:
				strFieldName = "ParamDouble4";
				break;
			case 15:
				strFieldName = "ParamDouble5";
				break;
			case 16:
				strFieldName = "ParamDouble6";
				break;
			case 17:
				strFieldName = "ParamDouble7";
				break;
			case 18:
				strFieldName = "ParamDouble8";
				break;
			case 19:
				strFieldName = "ParamDouble9";
				break;
			case 20:
				strFieldName = "ParamDouble10";
				break;
			case 21:
				strFieldName = "ParamString1";
				break;
			case 22:
				strFieldName = "ParamString2";
				break;
			case 23:
				strFieldName = "ParamString3";
				break;
			case 24:
				strFieldName = "ParamString4";
				break;
			case 25:
				strFieldName = "ParamString5";
				break;
			case 26:
				strFieldName = "ParamString6";
				break;
			case 27:
				strFieldName = "ParamDate1";
				break;
			case 28:
				strFieldName = "ParamDate2";
				break;
			case 29:
				strFieldName = "ParamDate3";
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
		if( strFieldName.equals("BakeSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamDouble1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble4") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble6") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble7") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble8") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble9") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamDouble10") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ParamString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamString4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamString5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamString6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ParamDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ParamDate3") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
