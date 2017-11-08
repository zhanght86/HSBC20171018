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
import com.sinosoft.lis.db.LPMoveDB;

/*
 * <p>ClassName: LPMoveSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPMoveSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPMoveSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 旧合同号码 */
	private String ContNoOld;
	/** 新合同号码 */
	private String ContNoNew;
	/** 旧管理机构 */
	private String ManageComOld;
	/** 新管理机构 */
	private String ManageComNew;
	/** 原公司名 */
	private String OldCoName;
	/** 现公司名 */
	private String NewCoName;
	/** 转入日期 */
	private Date InDate;
	/** 转出日期 */
	private Date OutDate;
	/** 每期保费 */
	private double StandPrem;
	/** 最后一次给付日期 */
	private Date LastGetDate;
	/** 免交开始日期 */
	private Date FreeDate;
	/** 起领日期 */
	private Date GetStartDate;
	/** 总余额 */
	private double LeavingMoney;
	/** 累计保费 */
	private double SumPrem;
	/** 交至日期 */
	private Date PaytoDate;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 旧保单号码 */
	private String PolNoOld;
	/** 新保单号码 */
	private String PolNoNew;
	/** 旧集体保单号码 */
	private String GrpPolNoOld;
	/** 新集体保单号码 */
	private String GrpPolNoNew;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPMoveSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "PolNoOld";

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
		LPMoveSchema cloned = (LPMoveSchema)super.clone();
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
	public String getContNoOld()
	{
		return ContNoOld;
	}
	public void setContNoOld(String aContNoOld)
	{
		ContNoOld = aContNoOld;
	}
	public String getContNoNew()
	{
		return ContNoNew;
	}
	public void setContNoNew(String aContNoNew)
	{
		ContNoNew = aContNoNew;
	}
	public String getManageComOld()
	{
		return ManageComOld;
	}
	public void setManageComOld(String aManageComOld)
	{
		ManageComOld = aManageComOld;
	}
	public String getManageComNew()
	{
		return ManageComNew;
	}
	public void setManageComNew(String aManageComNew)
	{
		ManageComNew = aManageComNew;
	}
	public String getOldCoName()
	{
		return OldCoName;
	}
	public void setOldCoName(String aOldCoName)
	{
		OldCoName = aOldCoName;
	}
	public String getNewCoName()
	{
		return NewCoName;
	}
	public void setNewCoName(String aNewCoName)
	{
		NewCoName = aNewCoName;
	}
	public String getInDate()
	{
		if( InDate != null )
			return fDate.getString(InDate);
		else
			return null;
	}
	public void setInDate(Date aInDate)
	{
		InDate = aInDate;
	}
	public void setInDate(String aInDate)
	{
		if (aInDate != null && !aInDate.equals("") )
		{
			InDate = fDate.getDate( aInDate );
		}
		else
			InDate = null;
	}

	public String getOutDate()
	{
		if( OutDate != null )
			return fDate.getString(OutDate);
		else
			return null;
	}
	public void setOutDate(Date aOutDate)
	{
		OutDate = aOutDate;
	}
	public void setOutDate(String aOutDate)
	{
		if (aOutDate != null && !aOutDate.equals("") )
		{
			OutDate = fDate.getDate( aOutDate );
		}
		else
			OutDate = null;
	}

	public double getStandPrem()
	{
		return StandPrem;
	}
	public void setStandPrem(double aStandPrem)
	{
		StandPrem = aStandPrem;
	}
	public void setStandPrem(String aStandPrem)
	{
		if (aStandPrem != null && !aStandPrem.equals(""))
		{
			Double tDouble = new Double(aStandPrem);
			double d = tDouble.doubleValue();
			StandPrem = d;
		}
	}

	public String getLastGetDate()
	{
		if( LastGetDate != null )
			return fDate.getString(LastGetDate);
		else
			return null;
	}
	public void setLastGetDate(Date aLastGetDate)
	{
		LastGetDate = aLastGetDate;
	}
	public void setLastGetDate(String aLastGetDate)
	{
		if (aLastGetDate != null && !aLastGetDate.equals("") )
		{
			LastGetDate = fDate.getDate( aLastGetDate );
		}
		else
			LastGetDate = null;
	}

	public String getFreeDate()
	{
		if( FreeDate != null )
			return fDate.getString(FreeDate);
		else
			return null;
	}
	public void setFreeDate(Date aFreeDate)
	{
		FreeDate = aFreeDate;
	}
	public void setFreeDate(String aFreeDate)
	{
		if (aFreeDate != null && !aFreeDate.equals("") )
		{
			FreeDate = fDate.getDate( aFreeDate );
		}
		else
			FreeDate = null;
	}

	/**
	* 为责任表中最早的起领日期
	*/
	public String getGetStartDate()
	{
		if( GetStartDate != null )
			return fDate.getString(GetStartDate);
		else
			return null;
	}
	public void setGetStartDate(Date aGetStartDate)
	{
		GetStartDate = aGetStartDate;
	}
	public void setGetStartDate(String aGetStartDate)
	{
		if (aGetStartDate != null && !aGetStartDate.equals("") )
		{
			GetStartDate = fDate.getDate( aGetStartDate );
		}
		else
			GetStartDate = null;
	}

	public double getLeavingMoney()
	{
		return LeavingMoney;
	}
	public void setLeavingMoney(double aLeavingMoney)
	{
		LeavingMoney = aLeavingMoney;
	}
	public void setLeavingMoney(String aLeavingMoney)
	{
		if (aLeavingMoney != null && !aLeavingMoney.equals(""))
		{
			Double tDouble = new Double(aLeavingMoney);
			double d = tDouble.doubleValue();
			LeavingMoney = d;
		}
	}

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
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
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	public String getPolNoOld()
	{
		return PolNoOld;
	}
	public void setPolNoOld(String aPolNoOld)
	{
		PolNoOld = aPolNoOld;
	}
	public String getPolNoNew()
	{
		return PolNoNew;
	}
	public void setPolNoNew(String aPolNoNew)
	{
		PolNoNew = aPolNoNew;
	}
	public String getGrpPolNoOld()
	{
		return GrpPolNoOld;
	}
	public void setGrpPolNoOld(String aGrpPolNoOld)
	{
		GrpPolNoOld = aGrpPolNoOld;
	}
	public String getGrpPolNoNew()
	{
		return GrpPolNoNew;
	}
	public void setGrpPolNoNew(String aGrpPolNoNew)
	{
		GrpPolNoNew = aGrpPolNoNew;
	}

	/**
	* 使用另外一个 LPMoveSchema 对象给 Schema 赋值
	* @param: aLPMoveSchema LPMoveSchema
	**/
	public void setSchema(LPMoveSchema aLPMoveSchema)
	{
		this.EdorNo = aLPMoveSchema.getEdorNo();
		this.EdorType = aLPMoveSchema.getEdorType();
		this.ContNoOld = aLPMoveSchema.getContNoOld();
		this.ContNoNew = aLPMoveSchema.getContNoNew();
		this.ManageComOld = aLPMoveSchema.getManageComOld();
		this.ManageComNew = aLPMoveSchema.getManageComNew();
		this.OldCoName = aLPMoveSchema.getOldCoName();
		this.NewCoName = aLPMoveSchema.getNewCoName();
		this.InDate = fDate.getDate( aLPMoveSchema.getInDate());
		this.OutDate = fDate.getDate( aLPMoveSchema.getOutDate());
		this.StandPrem = aLPMoveSchema.getStandPrem();
		this.LastGetDate = fDate.getDate( aLPMoveSchema.getLastGetDate());
		this.FreeDate = fDate.getDate( aLPMoveSchema.getFreeDate());
		this.GetStartDate = fDate.getDate( aLPMoveSchema.getGetStartDate());
		this.LeavingMoney = aLPMoveSchema.getLeavingMoney();
		this.SumPrem = aLPMoveSchema.getSumPrem();
		this.PaytoDate = fDate.getDate( aLPMoveSchema.getPaytoDate());
		this.ModifyDate = fDate.getDate( aLPMoveSchema.getModifyDate());
		this.ModifyTime = aLPMoveSchema.getModifyTime();
		this.RiskCode = aLPMoveSchema.getRiskCode();
		this.RiskVersion = aLPMoveSchema.getRiskVersion();
		this.PolNoOld = aLPMoveSchema.getPolNoOld();
		this.PolNoNew = aLPMoveSchema.getPolNoNew();
		this.GrpPolNoOld = aLPMoveSchema.getGrpPolNoOld();
		this.GrpPolNoNew = aLPMoveSchema.getGrpPolNoNew();
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

			if( rs.getString("ContNoOld") == null )
				this.ContNoOld = null;
			else
				this.ContNoOld = rs.getString("ContNoOld").trim();

			if( rs.getString("ContNoNew") == null )
				this.ContNoNew = null;
			else
				this.ContNoNew = rs.getString("ContNoNew").trim();

			if( rs.getString("ManageComOld") == null )
				this.ManageComOld = null;
			else
				this.ManageComOld = rs.getString("ManageComOld").trim();

			if( rs.getString("ManageComNew") == null )
				this.ManageComNew = null;
			else
				this.ManageComNew = rs.getString("ManageComNew").trim();

			if( rs.getString("OldCoName") == null )
				this.OldCoName = null;
			else
				this.OldCoName = rs.getString("OldCoName").trim();

			if( rs.getString("NewCoName") == null )
				this.NewCoName = null;
			else
				this.NewCoName = rs.getString("NewCoName").trim();

			this.InDate = rs.getDate("InDate");
			this.OutDate = rs.getDate("OutDate");
			this.StandPrem = rs.getDouble("StandPrem");
			this.LastGetDate = rs.getDate("LastGetDate");
			this.FreeDate = rs.getDate("FreeDate");
			this.GetStartDate = rs.getDate("GetStartDate");
			this.LeavingMoney = rs.getDouble("LeavingMoney");
			this.SumPrem = rs.getDouble("SumPrem");
			this.PaytoDate = rs.getDate("PaytoDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("PolNoOld") == null )
				this.PolNoOld = null;
			else
				this.PolNoOld = rs.getString("PolNoOld").trim();

			if( rs.getString("PolNoNew") == null )
				this.PolNoNew = null;
			else
				this.PolNoNew = rs.getString("PolNoNew").trim();

			if( rs.getString("GrpPolNoOld") == null )
				this.GrpPolNoOld = null;
			else
				this.GrpPolNoOld = rs.getString("GrpPolNoOld").trim();

			if( rs.getString("GrpPolNoNew") == null )
				this.GrpPolNoNew = null;
			else
				this.GrpPolNoNew = rs.getString("GrpPolNoNew").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPMove表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPMoveSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPMoveSchema getSchema()
	{
		LPMoveSchema aLPMoveSchema = new LPMoveSchema();
		aLPMoveSchema.setSchema(this);
		return aLPMoveSchema;
	}

	public LPMoveDB getDB()
	{
		LPMoveDB aDBOper = new LPMoveDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPMove描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNoOld)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNoNew)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageComOld)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageComNew)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldCoName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewCoName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FreeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LeavingMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNoOld)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNoNew)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNoOld)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNoNew));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPMove>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNoOld = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNoNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageComOld = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageComNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OldCoName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NewCoName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			OutDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			StandPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			LastGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			FreeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			GetStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			LeavingMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PolNoOld = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			PolNoNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			GrpPolNoOld = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			GrpPolNoNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPMoveSchema";
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
		if (FCode.equalsIgnoreCase("ContNoOld"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNoOld));
		}
		if (FCode.equalsIgnoreCase("ContNoNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNoNew));
		}
		if (FCode.equalsIgnoreCase("ManageComOld"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComOld));
		}
		if (FCode.equalsIgnoreCase("ManageComNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComNew));
		}
		if (FCode.equalsIgnoreCase("OldCoName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldCoName));
		}
		if (FCode.equalsIgnoreCase("NewCoName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewCoName));
		}
		if (FCode.equalsIgnoreCase("InDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPrem));
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
		}
		if (FCode.equalsIgnoreCase("FreeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFreeDate()));
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
		}
		if (FCode.equalsIgnoreCase("LeavingMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LeavingMoney));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("PolNoOld"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNoOld));
		}
		if (FCode.equalsIgnoreCase("PolNoNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNoNew));
		}
		if (FCode.equalsIgnoreCase("GrpPolNoOld"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNoOld));
		}
		if (FCode.equalsIgnoreCase("GrpPolNoNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNoNew));
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
				strFieldValue = StrTool.GBKToUnicode(ContNoOld);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNoNew);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageComOld);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageComNew);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OldCoName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NewCoName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
				break;
			case 10:
				strFieldValue = String.valueOf(StandPrem);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastGetDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFreeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetStartDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(LeavingMoney);
				break;
			case 15:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PolNoOld);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(PolNoNew);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNoOld);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNoNew);
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
		if (FCode.equalsIgnoreCase("ContNoOld"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNoOld = FValue.trim();
			}
			else
				ContNoOld = null;
		}
		if (FCode.equalsIgnoreCase("ContNoNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNoNew = FValue.trim();
			}
			else
				ContNoNew = null;
		}
		if (FCode.equalsIgnoreCase("ManageComOld"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComOld = FValue.trim();
			}
			else
				ManageComOld = null;
		}
		if (FCode.equalsIgnoreCase("ManageComNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComNew = FValue.trim();
			}
			else
				ManageComNew = null;
		}
		if (FCode.equalsIgnoreCase("OldCoName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldCoName = FValue.trim();
			}
			else
				OldCoName = null;
		}
		if (FCode.equalsIgnoreCase("NewCoName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewCoName = FValue.trim();
			}
			else
				NewCoName = null;
		}
		if (FCode.equalsIgnoreCase("InDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InDate = fDate.getDate( FValue );
			}
			else
				InDate = null;
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutDate = fDate.getDate( FValue );
			}
			else
				OutDate = null;
		}
		if (FCode.equalsIgnoreCase("StandPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("LastGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastGetDate = fDate.getDate( FValue );
			}
			else
				LastGetDate = null;
		}
		if (FCode.equalsIgnoreCase("FreeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FreeDate = fDate.getDate( FValue );
			}
			else
				FreeDate = null;
		}
		if (FCode.equalsIgnoreCase("GetStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetStartDate = fDate.getDate( FValue );
			}
			else
				GetStartDate = null;
		}
		if (FCode.equalsIgnoreCase("LeavingMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LeavingMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("PolNoOld"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNoOld = FValue.trim();
			}
			else
				PolNoOld = null;
		}
		if (FCode.equalsIgnoreCase("PolNoNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNoNew = FValue.trim();
			}
			else
				PolNoNew = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNoOld"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNoOld = FValue.trim();
			}
			else
				GrpPolNoOld = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNoNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNoNew = FValue.trim();
			}
			else
				GrpPolNoNew = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPMoveSchema other = (LPMoveSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& ContNoOld.equals(other.getContNoOld())
			&& ContNoNew.equals(other.getContNoNew())
			&& ManageComOld.equals(other.getManageComOld())
			&& ManageComNew.equals(other.getManageComNew())
			&& OldCoName.equals(other.getOldCoName())
			&& NewCoName.equals(other.getNewCoName())
			&& fDate.getString(InDate).equals(other.getInDate())
			&& fDate.getString(OutDate).equals(other.getOutDate())
			&& StandPrem == other.getStandPrem()
			&& fDate.getString(LastGetDate).equals(other.getLastGetDate())
			&& fDate.getString(FreeDate).equals(other.getFreeDate())
			&& fDate.getString(GetStartDate).equals(other.getGetStartDate())
			&& LeavingMoney == other.getLeavingMoney()
			&& SumPrem == other.getSumPrem()
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& PolNoOld.equals(other.getPolNoOld())
			&& PolNoNew.equals(other.getPolNoNew())
			&& GrpPolNoOld.equals(other.getGrpPolNoOld())
			&& GrpPolNoNew.equals(other.getGrpPolNoNew());
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
		if( strFieldName.equals("ContNoOld") ) {
			return 2;
		}
		if( strFieldName.equals("ContNoNew") ) {
			return 3;
		}
		if( strFieldName.equals("ManageComOld") ) {
			return 4;
		}
		if( strFieldName.equals("ManageComNew") ) {
			return 5;
		}
		if( strFieldName.equals("OldCoName") ) {
			return 6;
		}
		if( strFieldName.equals("NewCoName") ) {
			return 7;
		}
		if( strFieldName.equals("InDate") ) {
			return 8;
		}
		if( strFieldName.equals("OutDate") ) {
			return 9;
		}
		if( strFieldName.equals("StandPrem") ) {
			return 10;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return 11;
		}
		if( strFieldName.equals("FreeDate") ) {
			return 12;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return 13;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return 14;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 15;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 19;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 20;
		}
		if( strFieldName.equals("PolNoOld") ) {
			return 21;
		}
		if( strFieldName.equals("PolNoNew") ) {
			return 22;
		}
		if( strFieldName.equals("GrpPolNoOld") ) {
			return 23;
		}
		if( strFieldName.equals("GrpPolNoNew") ) {
			return 24;
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
				strFieldName = "ContNoOld";
				break;
			case 3:
				strFieldName = "ContNoNew";
				break;
			case 4:
				strFieldName = "ManageComOld";
				break;
			case 5:
				strFieldName = "ManageComNew";
				break;
			case 6:
				strFieldName = "OldCoName";
				break;
			case 7:
				strFieldName = "NewCoName";
				break;
			case 8:
				strFieldName = "InDate";
				break;
			case 9:
				strFieldName = "OutDate";
				break;
			case 10:
				strFieldName = "StandPrem";
				break;
			case 11:
				strFieldName = "LastGetDate";
				break;
			case 12:
				strFieldName = "FreeDate";
				break;
			case 13:
				strFieldName = "GetStartDate";
				break;
			case 14:
				strFieldName = "LeavingMoney";
				break;
			case 15:
				strFieldName = "SumPrem";
				break;
			case 16:
				strFieldName = "PaytoDate";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "RiskCode";
				break;
			case 20:
				strFieldName = "RiskVersion";
				break;
			case 21:
				strFieldName = "PolNoOld";
				break;
			case 22:
				strFieldName = "PolNoNew";
				break;
			case 23:
				strFieldName = "GrpPolNoOld";
				break;
			case 24:
				strFieldName = "GrpPolNoNew";
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
		if( strFieldName.equals("ContNoOld") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNoNew") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageComOld") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageComNew") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldCoName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewCoName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LastGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FreeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LeavingMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNoOld") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNoNew") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNoOld") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNoNew") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
