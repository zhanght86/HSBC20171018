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
import com.sinosoft.lis.db.LPBonusPolDB;

/*
 * <p>ClassName: LPBonusPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPBonusPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPBonusPolSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 会计年度 */
	private int FiscalYear;
	/** 红利分配组号 */
	private int GroupID;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 红利金额 */
	private double BonusMoney;
	/** 红利利息 */
	private double BonusInterest;
	/** 红利领取标志 */
	private String BonusFlag;
	/** 红利计算日期 */
	private Date BonusMakeDate;
	/** 红利应该分配日期 */
	private Date SGetDate;
	/** 红利实际分配日期 */
	private Date AGetDate;
	/** 该保单红利结算时刻的红利系数和 */
	private double BonusCoef;
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
	/** 红利领取方式 */
	private String BonusGetMode;
	/** 保单有效标记 */
	private String ValiFlag;
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPBonusPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "PolNo";
		pk[1] = "FiscalYear";
		pk[2] = "EdorNo";
		pk[3] = "EdorType";

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
		LPBonusPolSchema cloned = (LPBonusPolSchema)super.clone();
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

	/**
	* 每个分配的组都有一个组号
	*/
	public int getGroupID()
	{
		return GroupID;
	}
	public void setGroupID(int aGroupID)
	{
		GroupID = aGroupID;
	}
	public void setGroupID(String aGroupID)
	{
		if (aGroupID != null && !aGroupID.equals(""))
		{
			Integer tInteger = new Integer(aGroupID);
			int i = tInteger.intValue();
			GroupID = i;
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
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
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

	public double getBonusInterest()
	{
		return BonusInterest;
	}
	public void setBonusInterest(double aBonusInterest)
	{
		BonusInterest = aBonusInterest;
	}
	public void setBonusInterest(String aBonusInterest)
	{
		if (aBonusInterest != null && !aBonusInterest.equals(""))
		{
			Double tDouble = new Double(aBonusInterest);
			double d = tDouble.doubleValue();
			BonusInterest = d;
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

	/**
	* 红利结算时刻的红利系数和，将每个会计年度末的红利系数和保存在该字段中。
	*/
	public double getBonusCoef()
	{
		return BonusCoef;
	}
	public void setBonusCoef(double aBonusCoef)
	{
		BonusCoef = aBonusCoef;
	}
	public void setBonusCoef(String aBonusCoef)
	{
		if (aBonusCoef != null && !aBonusCoef.equals(""))
		{
			Double tDouble = new Double(aBonusCoef);
			double d = tDouble.doubleValue();
			BonusCoef = d;
		}
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
	public String getBonusGetMode()
	{
		return BonusGetMode;
	}
	public void setBonusGetMode(String aBonusGetMode)
	{
		BonusGetMode = aBonusGetMode;
	}
	/**
	* 0--保单失效<p>
	* 1--保单有效
	*/
	public String getValiFlag()
	{
		return ValiFlag;
	}
	public void setValiFlag(String aValiFlag)
	{
		ValiFlag = aValiFlag;
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

	/**
	* 使用另外一个 LPBonusPolSchema 对象给 Schema 赋值
	* @param: aLPBonusPolSchema LPBonusPolSchema
	**/
	public void setSchema(LPBonusPolSchema aLPBonusPolSchema)
	{
		this.PolNo = aLPBonusPolSchema.getPolNo();
		this.FiscalYear = aLPBonusPolSchema.getFiscalYear();
		this.GroupID = aLPBonusPolSchema.getGroupID();
		this.ContNo = aLPBonusPolSchema.getContNo();
		this.GrpPolNo = aLPBonusPolSchema.getGrpPolNo();
		this.BonusMoney = aLPBonusPolSchema.getBonusMoney();
		this.BonusInterest = aLPBonusPolSchema.getBonusInterest();
		this.BonusFlag = aLPBonusPolSchema.getBonusFlag();
		this.BonusMakeDate = fDate.getDate( aLPBonusPolSchema.getBonusMakeDate());
		this.SGetDate = fDate.getDate( aLPBonusPolSchema.getSGetDate());
		this.AGetDate = fDate.getDate( aLPBonusPolSchema.getAGetDate());
		this.BonusCoef = aLPBonusPolSchema.getBonusCoef();
		this.Operator = aLPBonusPolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLPBonusPolSchema.getMakeDate());
		this.MakeTime = aLPBonusPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPBonusPolSchema.getModifyDate());
		this.ModifyTime = aLPBonusPolSchema.getModifyTime();
		this.BonusGetMode = aLPBonusPolSchema.getBonusGetMode();
		this.ValiFlag = aLPBonusPolSchema.getValiFlag();
		this.EdorNo = aLPBonusPolSchema.getEdorNo();
		this.EdorType = aLPBonusPolSchema.getEdorType();
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

			this.FiscalYear = rs.getInt("FiscalYear");
			this.GroupID = rs.getInt("GroupID");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			this.BonusMoney = rs.getDouble("BonusMoney");
			this.BonusInterest = rs.getDouble("BonusInterest");
			if( rs.getString("BonusFlag") == null )
				this.BonusFlag = null;
			else
				this.BonusFlag = rs.getString("BonusFlag").trim();

			this.BonusMakeDate = rs.getDate("BonusMakeDate");
			this.SGetDate = rs.getDate("SGetDate");
			this.AGetDate = rs.getDate("AGetDate");
			this.BonusCoef = rs.getDouble("BonusCoef");
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

			if( rs.getString("BonusGetMode") == null )
				this.BonusGetMode = null;
			else
				this.BonusGetMode = rs.getString("BonusGetMode").trim();

			if( rs.getString("ValiFlag") == null )
				this.ValiFlag = null;
			else
				this.ValiFlag = rs.getString("ValiFlag").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPBonusPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPBonusPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPBonusPolSchema getSchema()
	{
		LPBonusPolSchema aLPBonusPolSchema = new LPBonusPolSchema();
		aLPBonusPolSchema.setSchema(this);
		return aLPBonusPolSchema;
	}

	public LPBonusPolDB getDB()
	{
		LPBonusPolDB aDBOper = new LPBonusPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPBonusPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GroupID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusCoef));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPBonusPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			GroupID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BonusMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BonusInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			BonusFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BonusMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			SGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			AGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			BonusCoef = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BonusGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ValiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPBonusPolSchema";
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
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("GroupID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupID));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("BonusMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusMoney));
		}
		if (FCode.equalsIgnoreCase("BonusInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusInterest));
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
		if (FCode.equalsIgnoreCase("BonusCoef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusCoef));
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
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGetMode));
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValiFlag));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
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
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 2:
				strFieldValue = String.valueOf(GroupID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = String.valueOf(BonusMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(BonusInterest);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BonusFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSGetDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAGetDate()));
				break;
			case 11:
				strFieldValue = String.valueOf(BonusCoef);
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
				strFieldValue = StrTool.GBKToUnicode(BonusGetMode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ValiFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
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
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("GroupID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GroupID = i;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("BonusInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusInterest = d;
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
		if (FCode.equalsIgnoreCase("BonusCoef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusCoef = d;
			}
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
		if (FCode.equalsIgnoreCase("BonusGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGetMode = FValue.trim();
			}
			else
				BonusGetMode = null;
		}
		if (FCode.equalsIgnoreCase("ValiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValiFlag = FValue.trim();
			}
			else
				ValiFlag = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPBonusPolSchema other = (LPBonusPolSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& FiscalYear == other.getFiscalYear()
			&& GroupID == other.getGroupID()
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& BonusMoney == other.getBonusMoney()
			&& BonusInterest == other.getBonusInterest()
			&& BonusFlag.equals(other.getBonusFlag())
			&& fDate.getString(BonusMakeDate).equals(other.getBonusMakeDate())
			&& fDate.getString(SGetDate).equals(other.getSGetDate())
			&& fDate.getString(AGetDate).equals(other.getAGetDate())
			&& BonusCoef == other.getBonusCoef()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BonusGetMode.equals(other.getBonusGetMode())
			&& ValiFlag.equals(other.getValiFlag())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType());
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
		if( strFieldName.equals("FiscalYear") ) {
			return 1;
		}
		if( strFieldName.equals("GroupID") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("BonusMoney") ) {
			return 5;
		}
		if( strFieldName.equals("BonusInterest") ) {
			return 6;
		}
		if( strFieldName.equals("BonusFlag") ) {
			return 7;
		}
		if( strFieldName.equals("BonusMakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("SGetDate") ) {
			return 9;
		}
		if( strFieldName.equals("AGetDate") ) {
			return 10;
		}
		if( strFieldName.equals("BonusCoef") ) {
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
		if( strFieldName.equals("BonusGetMode") ) {
			return 17;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return 18;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 19;
		}
		if( strFieldName.equals("EdorType") ) {
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "FiscalYear";
				break;
			case 2:
				strFieldName = "GroupID";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "BonusMoney";
				break;
			case 6:
				strFieldName = "BonusInterest";
				break;
			case 7:
				strFieldName = "BonusFlag";
				break;
			case 8:
				strFieldName = "BonusMakeDate";
				break;
			case 9:
				strFieldName = "SGetDate";
				break;
			case 10:
				strFieldName = "AGetDate";
				break;
			case 11:
				strFieldName = "BonusCoef";
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
				strFieldName = "BonusGetMode";
				break;
			case 18:
				strFieldName = "ValiFlag";
				break;
			case 19:
				strFieldName = "EdorNo";
				break;
			case 20:
				strFieldName = "EdorType";
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
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GroupID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusInterest") ) {
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
		if( strFieldName.equals("BonusCoef") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("BonusGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
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
