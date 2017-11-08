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
import com.sinosoft.lis.db.LPInsuAccInDB;

/*
 * <p>ClassName: LPInsuAccInSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LPInsuAccInSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPInsuAccInSchema.class);

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
	/** 转入被保人客户号码 */
	private String InInsuredNo;
	/** 转入投保人客户号码 */
	private String InAppntNo;
	/** 转入保单号码 */
	private String InPolNo;
	/** 转入保险帐户号码 */
	private String InInsuAccNo;
	/** 转入保险子帐户 */
	private String InPayPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 账户转入类型 */
	private String AccInType;
	/** 转入帐户金额 */
	private double AccInBala;
	/** 转入帐户单位数 */
	private double AccInUnit;
	/** 转入帐户比例 */
	private double AccInRate;
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
	public LPInsuAccInSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SerialNo";
		pk[1] = "InPolNo";
		pk[2] = "InInsuAccNo";
		pk[3] = "InPayPlanCode";

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
		LPInsuAccInSchema cloned = (LPInsuAccInSchema)super.clone();
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
	public String getInInsuredNo()
	{
		return InInsuredNo;
	}
	public void setInInsuredNo(String aInInsuredNo)
	{
		if(aInInsuredNo!=null && aInInsuredNo.length()>24)
			throw new IllegalArgumentException("转入被保人客户号码InInsuredNo值"+aInInsuredNo+"的长度"+aInInsuredNo.length()+"大于最大值24");
		InInsuredNo = aInInsuredNo;
	}
	public String getInAppntNo()
	{
		return InAppntNo;
	}
	public void setInAppntNo(String aInAppntNo)
	{
		if(aInAppntNo!=null && aInAppntNo.length()>24)
			throw new IllegalArgumentException("转入投保人客户号码InAppntNo值"+aInAppntNo+"的长度"+aInAppntNo.length()+"大于最大值24");
		InAppntNo = aInAppntNo;
	}
	public String getInPolNo()
	{
		return InPolNo;
	}
	public void setInPolNo(String aInPolNo)
	{
		if(aInPolNo!=null && aInPolNo.length()>20)
			throw new IllegalArgumentException("转入保单号码InPolNo值"+aInPolNo+"的长度"+aInPolNo.length()+"大于最大值20");
		InPolNo = aInPolNo;
	}
	public String getInInsuAccNo()
	{
		return InInsuAccNo;
	}
	public void setInInsuAccNo(String aInInsuAccNo)
	{
		if(aInInsuAccNo!=null && aInInsuAccNo.length()>20)
			throw new IllegalArgumentException("转入保险帐户号码InInsuAccNo值"+aInInsuAccNo+"的长度"+aInInsuAccNo.length()+"大于最大值20");
		InInsuAccNo = aInInsuAccNo;
	}
	public String getInPayPlanCode()
	{
		return InPayPlanCode;
	}
	public void setInPayPlanCode(String aInPayPlanCode)
	{
		if(aInPayPlanCode!=null && aInPayPlanCode.length()>20)
			throw new IllegalArgumentException("转入保险子帐户InPayPlanCode值"+aInPayPlanCode+"的长度"+aInPayPlanCode.length()+"大于最大值20");
		InPayPlanCode = aInPayPlanCode;
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
	public String getAccInType()
	{
		return AccInType;
	}
	public void setAccInType(String aAccInType)
	{
		if(aAccInType!=null && aAccInType.length()>1)
			throw new IllegalArgumentException("账户转入类型AccInType值"+aAccInType+"的长度"+aAccInType.length()+"大于最大值1");
		AccInType = aAccInType;
	}
	public double getAccInBala()
	{
		return AccInBala;
	}
	public void setAccInBala(double aAccInBala)
	{
		AccInBala = aAccInBala;
	}
	public void setAccInBala(String aAccInBala)
	{
		if (aAccInBala != null && !aAccInBala.equals(""))
		{
			Double tDouble = new Double(aAccInBala);
			double d = tDouble.doubleValue();
			AccInBala = d;
		}
	}

	public double getAccInUnit()
	{
		return AccInUnit;
	}
	public void setAccInUnit(double aAccInUnit)
	{
		AccInUnit = aAccInUnit;
	}
	public void setAccInUnit(String aAccInUnit)
	{
		if (aAccInUnit != null && !aAccInUnit.equals(""))
		{
			Double tDouble = new Double(aAccInUnit);
			double d = tDouble.doubleValue();
			AccInUnit = d;
		}
	}

	/**
	* 表示当前可以进行账户领取或者账户转出的金额。
	*/
	public double getAccInRate()
	{
		return AccInRate;
	}
	public void setAccInRate(double aAccInRate)
	{
		AccInRate = aAccInRate;
	}
	public void setAccInRate(String aAccInRate)
	{
		if (aAccInRate != null && !aAccInRate.equals(""))
		{
			Double tDouble = new Double(aAccInRate);
			double d = tDouble.doubleValue();
			AccInRate = d;
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
	* 使用另外一个 LPInsuAccInSchema 对象给 Schema 赋值
	* @param: aLPInsuAccInSchema LPInsuAccInSchema
	**/
	public void setSchema(LPInsuAccInSchema aLPInsuAccInSchema)
	{
		this.SerialNo = aLPInsuAccInSchema.getSerialNo();
		this.EdorNo = aLPInsuAccInSchema.getEdorNo();
		this.EdorType = aLPInsuAccInSchema.getEdorType();
		this.GrpContNo = aLPInsuAccInSchema.getGrpContNo();
		this.GrpPolNo = aLPInsuAccInSchema.getGrpPolNo();
		this.ContNo = aLPInsuAccInSchema.getContNo();
		this.InInsuredNo = aLPInsuAccInSchema.getInInsuredNo();
		this.InAppntNo = aLPInsuAccInSchema.getInAppntNo();
		this.InPolNo = aLPInsuAccInSchema.getInPolNo();
		this.InInsuAccNo = aLPInsuAccInSchema.getInInsuAccNo();
		this.InPayPlanCode = aLPInsuAccInSchema.getInPayPlanCode();
		this.RiskCode = aLPInsuAccInSchema.getRiskCode();
		this.AccType = aLPInsuAccInSchema.getAccType();
		this.AccInType = aLPInsuAccInSchema.getAccInType();
		this.AccInBala = aLPInsuAccInSchema.getAccInBala();
		this.AccInUnit = aLPInsuAccInSchema.getAccInUnit();
		this.AccInRate = aLPInsuAccInSchema.getAccInRate();
		this.State = aLPInsuAccInSchema.getState();
		this.Operator = aLPInsuAccInSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPInsuAccInSchema.getMakeDate());
		this.MakeTime = aLPInsuAccInSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPInsuAccInSchema.getModifyDate());
		this.ModifyTime = aLPInsuAccInSchema.getModifyTime();
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

			if( rs.getString("InInsuredNo") == null )
				this.InInsuredNo = null;
			else
				this.InInsuredNo = rs.getString("InInsuredNo").trim();

			if( rs.getString("InAppntNo") == null )
				this.InAppntNo = null;
			else
				this.InAppntNo = rs.getString("InAppntNo").trim();

			if( rs.getString("InPolNo") == null )
				this.InPolNo = null;
			else
				this.InPolNo = rs.getString("InPolNo").trim();

			if( rs.getString("InInsuAccNo") == null )
				this.InInsuAccNo = null;
			else
				this.InInsuAccNo = rs.getString("InInsuAccNo").trim();

			if( rs.getString("InPayPlanCode") == null )
				this.InPayPlanCode = null;
			else
				this.InPayPlanCode = rs.getString("InPayPlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccInType") == null )
				this.AccInType = null;
			else
				this.AccInType = rs.getString("AccInType").trim();

			this.AccInBala = rs.getDouble("AccInBala");
			this.AccInUnit = rs.getDouble("AccInUnit");
			this.AccInRate = rs.getDouble("AccInRate");
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
			logger.debug("数据库中的LPInsuAccIn表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPInsuAccInSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPInsuAccInSchema getSchema()
	{
		LPInsuAccInSchema aLPInsuAccInSchema = new LPInsuAccInSchema();
		aLPInsuAccInSchema.setSchema(this);
		return aLPInsuAccInSchema;
	}

	public LPInsuAccInDB getDB()
	{
		LPInsuAccInDB aDBOper = new LPInsuAccInDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuAccIn描述/A>表字段
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
		strReturn.append(StrTool.cTrim(InInsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InAppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InInsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InPayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccInType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccInBala));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccInUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccInRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPInsuAccIn>历史记账凭证主表信息</A>表字段
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
			InInsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InAppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InInsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InPayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AccInType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AccInBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			AccInUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			AccInRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
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
			tError.moduleName = "LPInsuAccInSchema";
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
		if (FCode.equalsIgnoreCase("InInsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InInsuredNo));
		}
		if (FCode.equalsIgnoreCase("InAppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InAppntNo));
		}
		if (FCode.equalsIgnoreCase("InPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InPolNo));
		}
		if (FCode.equalsIgnoreCase("InInsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InInsuAccNo));
		}
		if (FCode.equalsIgnoreCase("InPayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InPayPlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccInType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccInType));
		}
		if (FCode.equalsIgnoreCase("AccInBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccInBala));
		}
		if (FCode.equalsIgnoreCase("AccInUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccInUnit));
		}
		if (FCode.equalsIgnoreCase("AccInRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccInRate));
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
				strFieldValue = StrTool.GBKToUnicode(InInsuredNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InAppntNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InPolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InInsuAccNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InPayPlanCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(AccInType);
				break;
			case 14:
				strFieldValue = String.valueOf(AccInBala);
				break;
			case 15:
				strFieldValue = String.valueOf(AccInUnit);
				break;
			case 16:
				strFieldValue = String.valueOf(AccInRate);
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
		if (FCode.equalsIgnoreCase("InInsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InInsuredNo = FValue.trim();
			}
			else
				InInsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("InAppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InAppntNo = FValue.trim();
			}
			else
				InAppntNo = null;
		}
		if (FCode.equalsIgnoreCase("InPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InPolNo = FValue.trim();
			}
			else
				InPolNo = null;
		}
		if (FCode.equalsIgnoreCase("InInsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InInsuAccNo = FValue.trim();
			}
			else
				InInsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("InPayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InPayPlanCode = FValue.trim();
			}
			else
				InPayPlanCode = null;
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
		if (FCode.equalsIgnoreCase("AccInType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccInType = FValue.trim();
			}
			else
				AccInType = null;
		}
		if (FCode.equalsIgnoreCase("AccInBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccInBala = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccInUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccInUnit = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccInRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccInRate = d;
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
		LPInsuAccInSchema other = (LPInsuAccInSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& InInsuredNo.equals(other.getInInsuredNo())
			&& InAppntNo.equals(other.getInAppntNo())
			&& InPolNo.equals(other.getInPolNo())
			&& InInsuAccNo.equals(other.getInInsuAccNo())
			&& InPayPlanCode.equals(other.getInPayPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& AccInType.equals(other.getAccInType())
			&& AccInBala == other.getAccInBala()
			&& AccInUnit == other.getAccInUnit()
			&& AccInRate == other.getAccInRate()
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
		if( strFieldName.equals("InInsuredNo") ) {
			return 6;
		}
		if( strFieldName.equals("InAppntNo") ) {
			return 7;
		}
		if( strFieldName.equals("InPolNo") ) {
			return 8;
		}
		if( strFieldName.equals("InInsuAccNo") ) {
			return 9;
		}
		if( strFieldName.equals("InPayPlanCode") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("AccType") ) {
			return 12;
		}
		if( strFieldName.equals("AccInType") ) {
			return 13;
		}
		if( strFieldName.equals("AccInBala") ) {
			return 14;
		}
		if( strFieldName.equals("AccInUnit") ) {
			return 15;
		}
		if( strFieldName.equals("AccInRate") ) {
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
				strFieldName = "InInsuredNo";
				break;
			case 7:
				strFieldName = "InAppntNo";
				break;
			case 8:
				strFieldName = "InPolNo";
				break;
			case 9:
				strFieldName = "InInsuAccNo";
				break;
			case 10:
				strFieldName = "InPayPlanCode";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "AccType";
				break;
			case 13:
				strFieldName = "AccInType";
				break;
			case 14:
				strFieldName = "AccInBala";
				break;
			case 15:
				strFieldName = "AccInUnit";
				break;
			case 16:
				strFieldName = "AccInRate";
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
		if( strFieldName.equals("InInsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InAppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InInsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InPayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccInType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccInBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccInUnit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccInRate") ) {
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
