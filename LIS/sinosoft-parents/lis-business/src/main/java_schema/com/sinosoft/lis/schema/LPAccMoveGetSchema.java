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
import com.sinosoft.lis.db.LPAccMoveGetDB;

/*
 * <p>ClassName: LPAccMoveGetSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPAccMoveGetSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPAccMoveGetSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 保单号码 */
	private String PolNo;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 保险子帐户 */
	private String PayPlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 账户类型 */
	private String AccType;
	/** 对应其它号码 */
	private String OtherNo;
	/** 对应其它号码类型 */
	private String OtherType;
	/** 账户归属属性 */
	private String AccAscription;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 转换保险帐户金额 */
	private double AccMoveBala;
	/** 转换保险帐户单位数 */
	private double AccMoveUnit;
	/** 转换保险帐户比例 */
	private double AccMoveRate;
	/** 资金转换类型 */
	private String MoneyMoveType;
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

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPAccMoveGetSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolNo";
		pk[3] = "InsuAccNo";
		pk[4] = "PayPlanCode";
		pk[5] = "OtherNo";
		pk[6] = "AccAscription";
		pk[7] = "DutyCode";
		pk[8] = "GetDutyCode";

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
		LPAccMoveGetSchema cloned = (LPAccMoveGetSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
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
		AccType = aAccType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 1 --其他子帐户<p>
	* 2 --给付项
	*/
	public String getOtherType()
	{
		return OtherType;
	}
	public void setOtherType(String aOtherType)
	{
		OtherType = aOtherType;
	}
	/**
	* 0-未归属<p>
	* 1-已归属
	*/
	public String getAccAscription()
	{
		return AccAscription;
	}
	public void setAccAscription(String aAccAscription)
	{
		AccAscription = aAccAscription;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public double getAccMoveBala()
	{
		return AccMoveBala;
	}
	public void setAccMoveBala(double aAccMoveBala)
	{
		AccMoveBala = aAccMoveBala;
	}
	public void setAccMoveBala(String aAccMoveBala)
	{
		if (aAccMoveBala != null && !aAccMoveBala.equals(""))
		{
			Double tDouble = new Double(aAccMoveBala);
			double d = tDouble.doubleValue();
			AccMoveBala = d;
		}
	}

	public double getAccMoveUnit()
	{
		return AccMoveUnit;
	}
	public void setAccMoveUnit(double aAccMoveUnit)
	{
		AccMoveUnit = aAccMoveUnit;
	}
	public void setAccMoveUnit(String aAccMoveUnit)
	{
		if (aAccMoveUnit != null && !aAccMoveUnit.equals(""))
		{
			Double tDouble = new Double(aAccMoveUnit);
			double d = tDouble.doubleValue();
			AccMoveUnit = d;
		}
	}

	/**
	* 表示当前可以进行账户领取或者账户转出的金额。
	*/
	public double getAccMoveRate()
	{
		return AccMoveRate;
	}
	public void setAccMoveRate(double aAccMoveRate)
	{
		AccMoveRate = aAccMoveRate;
	}
	public void setAccMoveRate(String aAccMoveRate)
	{
		if (aAccMoveRate != null && !aAccMoveRate.equals(""))
		{
			Double tDouble = new Double(aAccMoveRate);
			double d = tDouble.doubleValue();
			AccMoveRate = d;
		}
	}

	/**
	* R －－ 比例<p>
	* C －－ 现金
	*/
	public String getMoneyMoveType()
	{
		return MoneyMoveType;
	}
	public void setMoneyMoveType(String aMoneyMoveType)
	{
		MoneyMoveType = aMoneyMoveType;
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
	* 使用另外一个 LPAccMoveGetSchema 对象给 Schema 赋值
	* @param: aLPAccMoveGetSchema LPAccMoveGetSchema
	**/
	public void setSchema(LPAccMoveGetSchema aLPAccMoveGetSchema)
	{
		this.EdorNo = aLPAccMoveGetSchema.getEdorNo();
		this.EdorType = aLPAccMoveGetSchema.getEdorType();
		this.PolNo = aLPAccMoveGetSchema.getPolNo();
		this.InsuAccNo = aLPAccMoveGetSchema.getInsuAccNo();
		this.PayPlanCode = aLPAccMoveGetSchema.getPayPlanCode();
		this.RiskCode = aLPAccMoveGetSchema.getRiskCode();
		this.AccType = aLPAccMoveGetSchema.getAccType();
		this.OtherNo = aLPAccMoveGetSchema.getOtherNo();
		this.OtherType = aLPAccMoveGetSchema.getOtherType();
		this.AccAscription = aLPAccMoveGetSchema.getAccAscription();
		this.DutyCode = aLPAccMoveGetSchema.getDutyCode();
		this.GetDutyCode = aLPAccMoveGetSchema.getGetDutyCode();
		this.AccMoveBala = aLPAccMoveGetSchema.getAccMoveBala();
		this.AccMoveUnit = aLPAccMoveGetSchema.getAccMoveUnit();
		this.AccMoveRate = aLPAccMoveGetSchema.getAccMoveRate();
		this.MoneyMoveType = aLPAccMoveGetSchema.getMoneyMoveType();
		this.Operator = aLPAccMoveGetSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPAccMoveGetSchema.getMakeDate());
		this.MakeTime = aLPAccMoveGetSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPAccMoveGetSchema.getModifyDate());
		this.ModifyTime = aLPAccMoveGetSchema.getModifyTime();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherType") == null )
				this.OtherType = null;
			else
				this.OtherType = rs.getString("OtherType").trim();

			if( rs.getString("AccAscription") == null )
				this.AccAscription = null;
			else
				this.AccAscription = rs.getString("AccAscription").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			this.AccMoveBala = rs.getDouble("AccMoveBala");
			this.AccMoveUnit = rs.getDouble("AccMoveUnit");
			this.AccMoveRate = rs.getDouble("AccMoveRate");
			if( rs.getString("MoneyMoveType") == null )
				this.MoneyMoveType = null;
			else
				this.MoneyMoveType = rs.getString("MoneyMoveType").trim();

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
			logger.debug("数据库中的LPAccMoveGet表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAccMoveGetSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPAccMoveGetSchema getSchema()
	{
		LPAccMoveGetSchema aLPAccMoveGetSchema = new LPAccMoveGetSchema();
		aLPAccMoveGetSchema.setSchema(this);
		return aLPAccMoveGetSchema;
	}

	public LPAccMoveGetDB getDB()
	{
		LPAccMoveGetDB aDBOper = new LPAccMoveGetDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPAccMoveGet描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccAscription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccMoveBala));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccMoveUnit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AccMoveRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoneyMoveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPAccMoveGet>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OtherType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AccAscription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccMoveBala = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			AccMoveUnit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			AccMoveRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			MoneyMoveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPAccMoveGetSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherType));
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccAscription));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("AccMoveBala"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccMoveBala));
		}
		if (FCode.equalsIgnoreCase("AccMoveUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccMoveUnit));
		}
		if (FCode.equalsIgnoreCase("AccMoveRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccMoveRate));
		}
		if (FCode.equalsIgnoreCase("MoneyMoveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoneyMoveType));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OtherType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AccAscription);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 12:
				strFieldValue = String.valueOf(AccMoveBala);
				break;
			case 13:
				strFieldValue = String.valueOf(AccMoveUnit);
				break;
			case 14:
				strFieldValue = String.valueOf(AccMoveRate);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MoneyMoveType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherType = FValue.trim();
			}
			else
				OtherType = null;
		}
		if (FCode.equalsIgnoreCase("AccAscription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccAscription = FValue.trim();
			}
			else
				AccAscription = null;
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("AccMoveBala"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccMoveBala = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccMoveUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccMoveUnit = d;
			}
		}
		if (FCode.equalsIgnoreCase("AccMoveRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AccMoveRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("MoneyMoveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoneyMoveType = FValue.trim();
			}
			else
				MoneyMoveType = null;
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
		LPAccMoveGetSchema other = (LPAccMoveGetSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& PolNo.equals(other.getPolNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherType.equals(other.getOtherType())
			&& AccAscription.equals(other.getAccAscription())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& AccMoveBala == other.getAccMoveBala()
			&& AccMoveUnit == other.getAccMoveUnit()
			&& AccMoveRate == other.getAccMoveRate()
			&& MoneyMoveType.equals(other.getMoneyMoveType())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 3;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("AccType") ) {
			return 6;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 7;
		}
		if( strFieldName.equals("OtherType") ) {
			return 8;
		}
		if( strFieldName.equals("AccAscription") ) {
			return 9;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 10;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 11;
		}
		if( strFieldName.equals("AccMoveBala") ) {
			return 12;
		}
		if( strFieldName.equals("AccMoveUnit") ) {
			return 13;
		}
		if( strFieldName.equals("AccMoveRate") ) {
			return 14;
		}
		if( strFieldName.equals("MoneyMoveType") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "InsuAccNo";
				break;
			case 4:
				strFieldName = "PayPlanCode";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "AccType";
				break;
			case 7:
				strFieldName = "OtherNo";
				break;
			case 8:
				strFieldName = "OtherType";
				break;
			case 9:
				strFieldName = "AccAscription";
				break;
			case 10:
				strFieldName = "DutyCode";
				break;
			case 11:
				strFieldName = "GetDutyCode";
				break;
			case 12:
				strFieldName = "AccMoveBala";
				break;
			case 13:
				strFieldName = "AccMoveUnit";
				break;
			case 14:
				strFieldName = "AccMoveRate";
				break;
			case 15:
				strFieldName = "MoneyMoveType";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccAscription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccMoveBala") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccMoveUnit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AccMoveRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MoneyMoveType") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
