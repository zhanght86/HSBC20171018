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
import com.sinosoft.lis.db.LPInsuAccOutDB;

/*
 * <p>ClassName: LPInsuAccOutSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LPInsuAccOutSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPInsuAccOutSchema.class);

	// @Field
	/** 转换流水号 */
	private String SerialNo;
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 转出被保人客户号码 */
	private String OutInsuredNo;
	/** 转出投保人客户号码 */
	private String OutAppntNo;
	/** 转出保单号码 */
	private String OutPolNo;
	/** 转出保险帐户号码 */
	private String OutInsuAccNo;
	/** 转出保险子帐户 */
	private String OutPayPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 账户转出类型 */
	private String AccOutType;
	/** 转出帐户金额 */
	private double AccOutBala;
	/** 转出帐户单位数 */
	private double AccOutUnit;
	/** 转出帐户比例 */
	private double AccOutRate;
	/** 状态 */
	private String State;
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

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPInsuAccOutSchema()
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
		LPInsuAccOutSchema cloned = (LPInsuAccOutSchema)super.clone();
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
			throw new IllegalArgumentException("转换流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>2)
			throw new IllegalArgumentException("批改类型EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值2");
		EdorType = aEdorType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getOutInsuredNo()
	{
		return OutInsuredNo;
	}
	public void setOutInsuredNo(String aOutInsuredNo)
	{
		if(aOutInsuredNo!=null && aOutInsuredNo.length()>24)
			throw new IllegalArgumentException("转出被保人客户号码OutInsuredNo值"+aOutInsuredNo+"的长度"+aOutInsuredNo.length()+"大于最大值24");
		OutInsuredNo = aOutInsuredNo;
	}
	public String getOutAppntNo()
	{
		return OutAppntNo;
	}
	public void setOutAppntNo(String aOutAppntNo)
	{
		if(aOutAppntNo!=null && aOutAppntNo.length()>24)
			throw new IllegalArgumentException("转出投保人客户号码OutAppntNo值"+aOutAppntNo+"的长度"+aOutAppntNo.length()+"大于最大值24");
		OutAppntNo = aOutAppntNo;
	}
	public String getOutPolNo()
	{
		return OutPolNo;
	}
	public void setOutPolNo(String aOutPolNo)
	{
		if(aOutPolNo!=null && aOutPolNo.length()>20)
			throw new IllegalArgumentException("转出保单号码OutPolNo值"+aOutPolNo+"的长度"+aOutPolNo.length()+"大于最大值20");
		OutPolNo = aOutPolNo;
	}
	public String getOutInsuAccNo()
	{
		return OutInsuAccNo;
	}
	public void setOutInsuAccNo(String aOutInsuAccNo)
	{
		if(aOutInsuAccNo!=null && aOutInsuAccNo.length()>20)
			throw new IllegalArgumentException("转出保险帐户号码OutInsuAccNo值"+aOutInsuAccNo+"的长度"+aOutInsuAccNo.length()+"大于最大值20");
		OutInsuAccNo = aOutInsuAccNo;
	}
	public String getOutPayPlanCode()
	{
		return OutPayPlanCode;
	}
	public void setOutPayPlanCode(String aOutPayPlanCode)
	{
		if(aOutPayPlanCode!=null && aOutPayPlanCode.length()>20)
			throw new IllegalArgumentException("转出保险子帐户OutPayPlanCode值"+aOutPayPlanCode+"的长度"+aOutPayPlanCode.length()+"大于最大值20");
		OutPayPlanCode = aOutPayPlanCode;
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
	/**
	* 001 -- 集体公共账户<p>
	* 002 -- 个人缴费账户<p>
	* 003 -- 个人累积生息账户<p>
	* 004 -- 个人红利账户
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		if(aAccType!=null && aAccType.length()>3)
			throw new IllegalArgumentException("账户类型AccType值"+aAccType+"的长度"+aAccType.length()+"大于最大值3");
		AccType = aAccType;
	}
	/**
	* 1-份额<p>
	* 2-金额<p>
	* 3-比例
	*/
	public String getAccOutType()
	{
		return AccOutType;
	}
	public void setAccOutType(String aAccOutType)
	{
		if(aAccOutType!=null && aAccOutType.length()>1)
			throw new IllegalArgumentException("账户转出类型AccOutType值"+aAccOutType+"的长度"+aAccOutType.length()+"大于最大值1");
		AccOutType = aAccOutType;
	}
	public double getAccOutBala()
	{
		return AccOutBala;
	}
	public void setAccOutBala(double aAccOutBala)
	{
		AccOutBala = aAccOutBala;
	}
	public void setAccOutBala(String aAccOutBala)
	{
		if (aAccOutBala != null && !aAccOutBala.equals(""))
		{
			Double tDouble = new Double(aAccOutBala);
			double d = tDouble.doubleValue();
			AccOutBala = d;
		}
	}

	public double getAccOutUnit()
	{
		return AccOutUnit;
	}
	public void setAccOutUnit(double aAccOutUnit)
	{
		AccOutUnit = aAccOutUnit;
	}
	public void setAccOutUnit(String aAccOutUnit)
	{
		if (aAccOutUnit != null && !aAccOutUnit.equals(""))
		{
			Double tDouble = new Double(aAccOutUnit);
			double d = tDouble.doubleValue();
			AccOutUnit = d;
		}
	}

	/**
	* 表示当前可以进行账户领取或者账户转出的金额。
	*/
	public double getAccOutRate()
	{
		return AccOutRate;
	}
	public void setAccOutRate(double aAccOutRate)
	{
		AccOutRate = aAccOutRate;
	}
	public void setAccOutRate(String aAccOutRate)
	{
		if (aAccOutRate != null && !aAccOutRate.equals(""))
		{
			Double tDouble = new Double(aAccOutRate);
			double d = tDouble.doubleValue();
			AccOutRate = d;
		}
	}

	/**
	* 0 - 计价完成<p>
	* 1 - 录入完成<p>
	* 2－保全复核确认
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>1)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
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

	/**
	* 使用另外一个 LPInsuAccOutSchema 对象给 Schema 赋值
	* @param: aLPInsuAccOutSchema LPInsuAccOutSchema
	**/
	public void setSchema(LPInsuAccOutSchema aLPInsuAccOutSchema)
	{
		this.SerialNo = aLPInsuAccOutSchema.getSerialNo();
		this.EdorNo = aLPInsuAccOutSchema.getEdorNo();
		this.EdorType = aLPInsuAccOutSchema.getEdorType();
		this.GrpContNo = aLPInsuAccOutSchema.getGrpContNo();
		this.GrpPolNo = aLPInsuAccOutSchema.getGrpPolNo();
		this.ContNo = aLPInsuAccOutSchema.getContNo();
		this.OutInsuredNo = aLPInsuAccOutSchema.getOutInsuredNo();
		this.OutAppntNo = aLPInsuAccOutSchema.getOutAppntNo();
		this.OutPolNo = aLPInsuAccOutSchema.getOutPolNo();
		this.OutInsuAccNo = aLPInsuAccOutSchema.getOutInsuAccNo();
		this.OutPayPlanCode = aLPInsuAccOutSchema.getOutPayPlanCode();
		this.RiskCode = aLPInsuAccOutSchema.getRiskCode();
		this.AccType = aLPInsuAccOutSchema.getAccType();
		this.AccOutType = aLPInsuAccOutSchema.getAccOutType();
		this.AccOutBala = aLPInsuAccOutSchema.getAccOutBala();
		this.AccOutUnit = aLPInsuAccOutSchema.getAccOutUnit();
		this.AccOutRate = aLPInsuAccOutSchema.getAccOutRate();
		this.State = aLPInsuAccOutSchema.getState();
		this.Operator = aLPInsuAccOutSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPInsuAccOutSchema.getMakeDate());
		this.MakeTime = aLPInsuAccOutSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPInsuAccOutSchema.getModifyDate());
		this.ModifyTime = aLPInsuAccOutSchema.getModifyTime();
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

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("OutInsuredNo") == null )
				this.OutInsuredNo = null;
			else
				this.OutInsuredNo = rs.getString("OutInsuredNo").trim();

			if( rs.getString("OutAppntNo") == null )
				this.OutAppntNo = null;
			else
				this.OutAppntNo = rs.getString("OutAppntNo").trim();

			if( rs.getString("OutPolNo") == null )
				this.OutPolNo = null;
			else
				this.OutPolNo = rs.getString("OutPolNo").trim();

			if( rs.getString("OutInsuAccNo") == null )
				this.OutInsuAccNo = null;
			else
				this.OutInsuAccNo = rs.getString("OutInsuAccNo").trim();

			if( rs.getString("OutPayPlanCode") == null )
				this.OutPayPlanCode = null;
			else
				this.OutPayPlanCode = rs.getString("OutPayPlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccOutType") == null )
				this.AccOutType = null;
			else
				this.AccOutType = rs.getString("AccOutType").trim();

			this.AccOutBala = rs.getDouble("AccOutBala");
			this.AccOutUnit = rs.getDouble("AccOutUnit");
			this.AccOutRate = rs.getDouble("AccOutRate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPInsuAccOut表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuAccOutSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPInsuAccOutSchema getSchema()
	{
		LPInsuAccOutSchema aLPInsuAccOutSchema = new LPInsuAccOutSchema();
		aLPInsuAccOutSchema.setSchema(this);
		return aLPInsuAccOutSchema;
	}

	public LPInsuAccOutDB getDB()
	{
		LPInsuAccOutDB aDBOper = new LPInsuAccOutDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuAccOut描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutAppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutInsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutPayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccOutType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccOutBala));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccOutUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccOutRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuAccOut>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OutInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OutAppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OutPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OutInsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			OutPayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AccOutType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AccOutBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			AccOutUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			AccOutRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuAccOutSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("OutInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutInsuredNo));
		}
		if (FCode.equalsIgnoreCase("OutAppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutAppntNo));
		}
		if (FCode.equalsIgnoreCase("OutPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPolNo));
		}
		if (FCode.equalsIgnoreCase("OutInsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutInsuAccNo));
		}
		if (FCode.equalsIgnoreCase("OutPayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayPlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccOutType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccOutType));
		}
		if (FCode.equalsIgnoreCase("AccOutBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccOutBala));
		}
		if (FCode.equalsIgnoreCase("AccOutUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccOutUnit));
		}
		if (FCode.equalsIgnoreCase("AccOutRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccOutRate));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OutInsuredNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OutAppntNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OutPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OutInsuAccNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(OutPayPlanCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AccOutType);
				break;
			case 14:
				strFieldValue = String.valueOf(AccOutBala);
				break;
			case 15:
				strFieldValue = String.valueOf(AccOutUnit);
				break;
			case 16:
				strFieldValue = String.valueOf(AccOutRate);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("OutInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutInsuredNo = FValue.trim();
			}
			else
				OutInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("OutAppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutAppntNo = FValue.trim();
			}
			else
				OutAppntNo = null;
		}
		if (FCode.equalsIgnoreCase("OutPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutPolNo = FValue.trim();
			}
			else
				OutPolNo = null;
		}
		if (FCode.equalsIgnoreCase("OutInsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutInsuAccNo = FValue.trim();
			}
			else
				OutInsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("OutPayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutPayPlanCode = FValue.trim();
			}
			else
				OutPayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("AccOutType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccOutType = FValue.trim();
			}
			else
				AccOutType = null;
		}
		if (FCode.equalsIgnoreCase("AccOutBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccOutBala = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccOutUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccOutUnit = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccOutRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccOutRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPInsuAccOutSchema other = (LPInsuAccOutSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& OutInsuredNo.equals(other.getOutInsuredNo())
			&& OutAppntNo.equals(other.getOutAppntNo())
			&& OutPolNo.equals(other.getOutPolNo())
			&& OutInsuAccNo.equals(other.getOutInsuAccNo())
			&& OutPayPlanCode.equals(other.getOutPayPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& AccOutType.equals(other.getAccOutType())
			&& AccOutBala == other.getAccOutBala()
			&& AccOutUnit == other.getAccOutUnit()
			&& AccOutRate == other.getAccOutRate()
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("EdorNo") ) {
			return 1;
		}
		if( strFieldName.equals("EdorType") ) {
			return 2;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("ContNo") ) {
			return 5;
		}
		if( strFieldName.equals("OutInsuredNo") ) {
			return 6;
		}
		if( strFieldName.equals("OutAppntNo") ) {
			return 7;
		}
		if( strFieldName.equals("OutPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("OutInsuAccNo") ) {
			return 9;
		}
		if( strFieldName.equals("OutPayPlanCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("AccType") ) {
			return 12;
		}
		if( strFieldName.equals("AccOutType") ) {
			return 13;
		}
		if( strFieldName.equals("AccOutBala") ) {
			return 14;
		}
		if( strFieldName.equals("AccOutUnit") ) {
			return 15;
		}
		if( strFieldName.equals("AccOutRate") ) {
			return 16;
		}
		if( strFieldName.equals("State") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
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
				strFieldName = "EdorNo";
				break;
			case 2:
				strFieldName = "EdorType";
				break;
			case 3:
				strFieldName = "GrpContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "ContNo";
				break;
			case 6:
				strFieldName = "OutInsuredNo";
				break;
			case 7:
				strFieldName = "OutAppntNo";
				break;
			case 8:
				strFieldName = "OutPolNo";
				break;
			case 9:
				strFieldName = "OutInsuAccNo";
				break;
			case 10:
				strFieldName = "OutPayPlanCode";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "AccType";
				break;
			case 13:
				strFieldName = "AccOutType";
				break;
			case 14:
				strFieldName = "AccOutBala";
				break;
			case 15:
				strFieldName = "AccOutUnit";
				break;
			case 16:
				strFieldName = "AccOutRate";
				break;
			case 17:
				strFieldName = "State";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutAppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutInsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutPayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccOutType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccOutBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccOutUnit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccOutRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
