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
import com.sinosoft.lis.db.LOBonusGrpPolDB;

/*
 * <p>ClassName: LOBonusGrpPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 分红管理
 */
public class LOBonusGrpPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBonusGrpPolSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 红利分配会计年度 */
	private int FiscalYear;
	/** 总单/合同号码 */
	private String ContNo;
	/** 保单年度 */
	private int PolYear;
	/** 保单类型标记 */
	private String PolTypeFlag;
	/** 红利金额 */
	private double BonusMoney;
	/** 红利领取标志 */
	private String BonusFlag;
	/** 红利计算日期 */
	private Date BonusMakeDate;
	/** 红利应该分配日期 */
	private Date SGetDate;
	/** 红利实际分配日期 */
	private Date AGetDate;
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
	/** 交费计划编码 */
	private String PayPlanCode;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBonusGrpPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "PolNo";
		pk[1] = "GrpPolNo";
		pk[2] = "InsuAccNo";
		pk[3] = "FiscalYear";
		pk[4] = "PayPlanCode";

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
		LOBonusGrpPolSchema cloned = (LOBonusGrpPolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 导入红利分配时的会计年度。（该字段在导入每年的可分配盈余时同时导入）
	*/
	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public int getPolYear()
	{
		return PolYear;
	}
	public void setPolYear(int aPolYear)
	{
		PolYear = aPolYear;
	}
	public void setPolYear(String aPolYear)
	{
		if (aPolYear != null && !aPolYear.equals(""))
		{
			Integer tInteger = new Integer(aPolYear);
			int i = tInteger.intValue();
			PolYear = i;
		}
	}

	/**
	* 0 --个人单：<p>
	* 1 --无名单：<p>
	* 2 --（团单）公共帐户
	*/
	public String getPolTypeFlag()
	{
		return PolTypeFlag;
	}
	public void setPolTypeFlag(String aPolTypeFlag)
	{
		PolTypeFlag = aPolTypeFlag;
	}
	public double getBonusMoney()
	{
		return BonusMoney;
	}
	public void setBonusMoney(double aBonusMoney)
	{
		BonusMoney = aBonusMoney;
	}
	public void setBonusMoney(String aBonusMoney)
	{
		if (aBonusMoney != null && !aBonusMoney.equals(""))
		{
			Double tDouble = new Double(aBonusMoney);
			double d = tDouble.doubleValue();
			BonusMoney = d;
		}
	}

	/**
	* 0 －－ 未领取<p>
	* 1 －－ 已经领取
	*/
	public String getBonusFlag()
	{
		return BonusFlag;
	}
	public void setBonusFlag(String aBonusFlag)
	{
		BonusFlag = aBonusFlag;
	}
	public String getBonusMakeDate()
	{
		if( BonusMakeDate != null )
			return fDate.getString(BonusMakeDate);
		else
			return null;
	}
	public void setBonusMakeDate(Date aBonusMakeDate)
	{
		BonusMakeDate = aBonusMakeDate;
	}
	public void setBonusMakeDate(String aBonusMakeDate)
	{
		if (aBonusMakeDate != null && !aBonusMakeDate.equals("") )
		{
			BonusMakeDate = fDate.getDate( aBonusMakeDate );
		}
		else
			BonusMakeDate = null;
	}

	public String getSGetDate()
	{
		if( SGetDate != null )
			return fDate.getString(SGetDate);
		else
			return null;
	}
	public void setSGetDate(Date aSGetDate)
	{
		SGetDate = aSGetDate;
	}
	public void setSGetDate(String aSGetDate)
	{
		if (aSGetDate != null && !aSGetDate.equals("") )
		{
			SGetDate = fDate.getDate( aSGetDate );
		}
		else
			SGetDate = null;
	}

	public String getAGetDate()
	{
		if( AGetDate != null )
			return fDate.getString(AGetDate);
		else
			return null;
	}
	public void setAGetDate(Date aAGetDate)
	{
		AGetDate = aAGetDate;
	}
	public void setAGetDate(String aAGetDate)
	{
		if (aAGetDate != null && !aAGetDate.equals("") )
		{
			AGetDate = fDate.getDate( aAGetDate );
		}
		else
			AGetDate = null;
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
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}

	/**
	* 使用另外一个 LOBonusGrpPolSchema 对象给 Schema 赋值
	* @param: aLOBonusGrpPolSchema LOBonusGrpPolSchema
	**/
	public void setSchema(LOBonusGrpPolSchema aLOBonusGrpPolSchema)
	{
		this.PolNo = aLOBonusGrpPolSchema.getPolNo();
		this.GrpPolNo = aLOBonusGrpPolSchema.getGrpPolNo();
		this.InsuAccNo = aLOBonusGrpPolSchema.getInsuAccNo();
		this.FiscalYear = aLOBonusGrpPolSchema.getFiscalYear();
		this.ContNo = aLOBonusGrpPolSchema.getContNo();
		this.PolYear = aLOBonusGrpPolSchema.getPolYear();
		this.PolTypeFlag = aLOBonusGrpPolSchema.getPolTypeFlag();
		this.BonusMoney = aLOBonusGrpPolSchema.getBonusMoney();
		this.BonusFlag = aLOBonusGrpPolSchema.getBonusFlag();
		this.BonusMakeDate = fDate.getDate( aLOBonusGrpPolSchema.getBonusMakeDate());
		this.SGetDate = fDate.getDate( aLOBonusGrpPolSchema.getSGetDate());
		this.AGetDate = fDate.getDate( aLOBonusGrpPolSchema.getAGetDate());
		this.Operator = aLOBonusGrpPolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBonusGrpPolSchema.getMakeDate());
		this.MakeTime = aLOBonusGrpPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBonusGrpPolSchema.getModifyDate());
		this.ModifyTime = aLOBonusGrpPolSchema.getModifyTime();
		this.PayPlanCode = aLOBonusGrpPolSchema.getPayPlanCode();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			this.FiscalYear = rs.getInt("FiscalYear");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			this.PolYear = rs.getInt("PolYear");
			if( rs.getString("PolTypeFlag") == null )
				this.PolTypeFlag = null;
			else
				this.PolTypeFlag = rs.getString("PolTypeFlag").trim();

			this.BonusMoney = rs.getDouble("BonusMoney");
			if( rs.getString("BonusFlag") == null )
				this.BonusFlag = null;
			else
				this.BonusFlag = rs.getString("BonusFlag").trim();

			this.BonusMakeDate = rs.getDate("BonusMakeDate");
			this.SGetDate = rs.getDate("SGetDate");
			this.AGetDate = rs.getDate("AGetDate");
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

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBonusGrpPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusGrpPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBonusGrpPolSchema getSchema()
	{
		LOBonusGrpPolSchema aLOBonusGrpPolSchema = new LOBonusGrpPolSchema();
		aLOBonusGrpPolSchema.setSchema(this);
		return aLOBonusGrpPolSchema;
	}

	public LOBonusGrpPolDB getDB()
	{
		LOBonusGrpPolDB aDBOper = new LOBonusGrpPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusGrpPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PolYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolTypeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBonusGrpPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PolYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			PolTypeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BonusMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			BonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BonusMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			SGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			AGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBonusGrpPolSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolYear));
		}
		if (FCode.equalsIgnoreCase("PolTypeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolTypeFlag));
		}
		if (FCode.equalsIgnoreCase("BonusMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMoney));
		}
		if (FCode.equalsIgnoreCase("BonusFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusFlag));
		}
		if (FCode.equalsIgnoreCase("BonusMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusMakeDate()));
		}
		if (FCode.equalsIgnoreCase("SGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSGetDate()));
		}
		if (FCode.equalsIgnoreCase("AGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAGetDate()));
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 3:
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 5:
				strFieldValue = String.valueOf(PolYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PolTypeFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(BonusMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BonusFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSGetDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAGetDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
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
		if (FCode.equalsIgnoreCase("PolYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PolYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("PolTypeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolTypeFlag = FValue.trim();
			}
			else
				PolTypeFlag = null;
		}
		if (FCode.equalsIgnoreCase("BonusMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusMoney = d;
			}
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
		if (FCode.equalsIgnoreCase("BonusMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusMakeDate = fDate.getDate( FValue );
			}
			else
				BonusMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("SGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SGetDate = fDate.getDate( FValue );
			}
			else
				SGetDate = null;
		}
		if (FCode.equalsIgnoreCase("AGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AGetDate = fDate.getDate( FValue );
			}
			else
				AGetDate = null;
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
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBonusGrpPolSchema other = (LOBonusGrpPolSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& FiscalYear == other.getFiscalYear()
			&& ContNo.equals(other.getContNo())
			&& PolYear == other.getPolYear()
			&& PolTypeFlag.equals(other.getPolTypeFlag())
			&& BonusMoney == other.getBonusMoney()
			&& BonusFlag.equals(other.getBonusFlag())
			&& fDate.getString(BonusMakeDate).equals(other.getBonusMakeDate())
			&& fDate.getString(SGetDate).equals(other.getSGetDate())
			&& fDate.getString(AGetDate).equals(other.getAGetDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PayPlanCode.equals(other.getPayPlanCode());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 2;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return 3;
		}
		if( strFieldName.equals("ContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PolYear") ) {
			return 5;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return 6;
		}
		if( strFieldName.equals("BonusMoney") ) {
			return 7;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return 8;
		}
		if( strFieldName.equals("BonusMakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("SGetDate") ) {
			return 10;
		}
		if( strFieldName.equals("AGetDate") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 17;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "InsuAccNo";
				break;
			case 3:
				strFieldName = "FiscalYear";
				break;
			case 4:
				strFieldName = "ContNo";
				break;
			case 5:
				strFieldName = "PolYear";
				break;
			case 6:
				strFieldName = "PolTypeFlag";
				break;
			case 7:
				strFieldName = "BonusMoney";
				break;
			case 8:
				strFieldName = "BonusFlag";
				break;
			case 9:
				strFieldName = "BonusMakeDate";
				break;
			case 10:
				strFieldName = "SGetDate";
				break;
			case 11:
				strFieldName = "AGetDate";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "PayPlanCode";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PolTypeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AGetDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("PayPlanCode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
