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
import com.sinosoft.lis.db.LRAdimAscriptionBDB;

/*
 * <p>ClassName: LRAdimAscriptionBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LRAdimAscriptionBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRAdimAscriptionBSchema.class);

	// @Field
	/** 批改号 */
	private String EdorNo;
	/** 保单号 */
	private String PolNo;
	/** 主险保单号 */
	private String MainPolno;
	/** 险种编码 */
	private String RiskCode;
	/** 印刷号 */
	private String PrtNo;
	/** 批改类型 */
	private String EdorType;
	/** 续收督导工号 */
	private String AgentCode;
	/** 交至日期 */
	private Date PayToDate;
	/** 生效日期 */
	private Date CValiDate;
	/** 归属日期 */
	private Date AscriptionDate;
	/** 归属类型 */
	private String AscriptionType;
	/** 是否计算达成率标示 */
	private String Flag;
	/** 原操作员 */
	private String Operator1;
	/** 原入机日期 */
	private Date MakeDate1;
	/** 原入机时间 */
	private String MakeTime1;
	/** 原修改日期 */
	private Date ModifyDate1;
	/** 原修改时间 */
	private String ModifyTime1;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 保单年度 */
	private int PayYear;
	/** 长短险标识 */
	private String LongShortFlag;
	/** 缴费次数 */
	private int PayCount;
	/** 邮编 */
	private String ZipCode;
	/** 邮编附加码 */
	private String AddCode;
	/** 合同号 */
	private String ContNo;
	/** 管理机构 */
	private String ManageCom;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRAdimAscriptionBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "EdorNo";

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
		LRAdimAscriptionBSchema cloned = (LRAdimAscriptionBSchema)super.clone();
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
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getMainPolno()
	{
		return MainPolno;
	}
	public void setMainPolno(String aMainPolno)
	{
		MainPolno = aMainPolno;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	/**
	* 01--重新归属备份<p>
	* 02--剔出保单备份<p>
	* 03--续收督导离职接替操作备份<p>
	* 04--续收督导手工调配营销服务部<p>
	* <p>
	* 05--在职单手工调配<p>
	* <p>
	* 06--保单机构迁移<p>
	* 07--保单终止备份<p>
	* 08--预归属保单备份
	*/
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getPayToDate()
	{
		if( PayToDate != null )
			return fDate.getString(PayToDate);
		else
			return null;
	}
	public void setPayToDate(Date aPayToDate)
	{
		PayToDate = aPayToDate;
	}
	public void setPayToDate(String aPayToDate)
	{
		if (aPayToDate != null && !aPayToDate.equals("") )
		{
			PayToDate = fDate.getDate( aPayToDate );
		}
		else
			PayToDate = null;
	}

	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
	}

	public String getAscriptionDate()
	{
		if( AscriptionDate != null )
			return fDate.getString(AscriptionDate);
		else
			return null;
	}
	public void setAscriptionDate(Date aAscriptionDate)
	{
		AscriptionDate = aAscriptionDate;
	}
	public void setAscriptionDate(String aAscriptionDate)
	{
		if (aAscriptionDate != null && !aAscriptionDate.equals("") )
		{
			AscriptionDate = fDate.getDate( aAscriptionDate );
		}
		else
			AscriptionDate = null;
	}

	public String getAscriptionType()
	{
		return AscriptionType;
	}
	public void setAscriptionType(String aAscriptionType)
	{
		AscriptionType = aAscriptionType;
	}
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
	}
	public String getOperator1()
	{
		return Operator1;
	}
	public void setOperator1(String aOperator1)
	{
		Operator1 = aOperator1;
	}
	public String getMakeDate1()
	{
		if( MakeDate1 != null )
			return fDate.getString(MakeDate1);
		else
			return null;
	}
	public void setMakeDate1(Date aMakeDate1)
	{
		MakeDate1 = aMakeDate1;
	}
	public void setMakeDate1(String aMakeDate1)
	{
		if (aMakeDate1 != null && !aMakeDate1.equals("") )
		{
			MakeDate1 = fDate.getDate( aMakeDate1 );
		}
		else
			MakeDate1 = null;
	}

	public String getMakeTime1()
	{
		return MakeTime1;
	}
	public void setMakeTime1(String aMakeTime1)
	{
		MakeTime1 = aMakeTime1;
	}
	public String getModifyDate1()
	{
		if( ModifyDate1 != null )
			return fDate.getString(ModifyDate1);
		else
			return null;
	}
	public void setModifyDate1(Date aModifyDate1)
	{
		ModifyDate1 = aModifyDate1;
	}
	public void setModifyDate1(String aModifyDate1)
	{
		if (aModifyDate1 != null && !aModifyDate1.equals("") )
		{
			ModifyDate1 = fDate.getDate( aModifyDate1 );
		}
		else
			ModifyDate1 = null;
	}

	public String getModifyTime1()
	{
		return ModifyTime1;
	}
	public void setModifyTime1(String aModifyTime1)
	{
		ModifyTime1 = aModifyTime1;
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
	public int getPayYear()
	{
		return PayYear;
	}
	public void setPayYear(int aPayYear)
	{
		PayYear = aPayYear;
	}
	public void setPayYear(String aPayYear)
	{
		if (aPayYear != null && !aPayYear.equals(""))
		{
			Integer tInteger = new Integer(aPayYear);
			int i = tInteger.intValue();
			PayYear = i;
		}
	}

	public String getLongShortFlag()
	{
		return LongShortFlag;
	}
	public void setLongShortFlag(String aLongShortFlag)
	{
		LongShortFlag = aLongShortFlag;
	}
	public int getPayCount()
	{
		return PayCount;
	}
	public void setPayCount(int aPayCount)
	{
		PayCount = aPayCount;
	}
	public void setPayCount(String aPayCount)
	{
		if (aPayCount != null && !aPayCount.equals(""))
		{
			Integer tInteger = new Integer(aPayCount);
			int i = tInteger.intValue();
			PayCount = i;
		}
	}

	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	public String getAddCode()
	{
		return AddCode;
	}
	public void setAddCode(String aAddCode)
	{
		AddCode = aAddCode;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}

	/**
	* 使用另外一个 LRAdimAscriptionBSchema 对象给 Schema 赋值
	* @param: aLRAdimAscriptionBSchema LRAdimAscriptionBSchema
	**/
	public void setSchema(LRAdimAscriptionBSchema aLRAdimAscriptionBSchema)
	{
		this.EdorNo = aLRAdimAscriptionBSchema.getEdorNo();
		this.PolNo = aLRAdimAscriptionBSchema.getPolNo();
		this.MainPolno = aLRAdimAscriptionBSchema.getMainPolno();
		this.RiskCode = aLRAdimAscriptionBSchema.getRiskCode();
		this.PrtNo = aLRAdimAscriptionBSchema.getPrtNo();
		this.EdorType = aLRAdimAscriptionBSchema.getEdorType();
		this.AgentCode = aLRAdimAscriptionBSchema.getAgentCode();
		this.PayToDate = fDate.getDate( aLRAdimAscriptionBSchema.getPayToDate());
		this.CValiDate = fDate.getDate( aLRAdimAscriptionBSchema.getCValiDate());
		this.AscriptionDate = fDate.getDate( aLRAdimAscriptionBSchema.getAscriptionDate());
		this.AscriptionType = aLRAdimAscriptionBSchema.getAscriptionType();
		this.Flag = aLRAdimAscriptionBSchema.getFlag();
		this.Operator1 = aLRAdimAscriptionBSchema.getOperator1();
		this.MakeDate1 = fDate.getDate( aLRAdimAscriptionBSchema.getMakeDate1());
		this.MakeTime1 = aLRAdimAscriptionBSchema.getMakeTime1();
		this.ModifyDate1 = fDate.getDate( aLRAdimAscriptionBSchema.getModifyDate1());
		this.ModifyTime1 = aLRAdimAscriptionBSchema.getModifyTime1();
		this.Operator = aLRAdimAscriptionBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLRAdimAscriptionBSchema.getMakeDate());
		this.MakeTime = aLRAdimAscriptionBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLRAdimAscriptionBSchema.getModifyDate());
		this.ModifyTime = aLRAdimAscriptionBSchema.getModifyTime();
		this.PayYear = aLRAdimAscriptionBSchema.getPayYear();
		this.LongShortFlag = aLRAdimAscriptionBSchema.getLongShortFlag();
		this.PayCount = aLRAdimAscriptionBSchema.getPayCount();
		this.ZipCode = aLRAdimAscriptionBSchema.getZipCode();
		this.AddCode = aLRAdimAscriptionBSchema.getAddCode();
		this.ContNo = aLRAdimAscriptionBSchema.getContNo();
		this.ManageCom = aLRAdimAscriptionBSchema.getManageCom();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("MainPolno") == null )
				this.MainPolno = null;
			else
				this.MainPolno = rs.getString("MainPolno").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.PayToDate = rs.getDate("PayToDate");
			this.CValiDate = rs.getDate("CValiDate");
			this.AscriptionDate = rs.getDate("AscriptionDate");
			if( rs.getString("AscriptionType") == null )
				this.AscriptionType = null;
			else
				this.AscriptionType = rs.getString("AscriptionType").trim();

			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			if( rs.getString("Operator1") == null )
				this.Operator1 = null;
			else
				this.Operator1 = rs.getString("Operator1").trim();

			this.MakeDate1 = rs.getDate("MakeDate1");
			if( rs.getString("MakeTime1") == null )
				this.MakeTime1 = null;
			else
				this.MakeTime1 = rs.getString("MakeTime1").trim();

			this.ModifyDate1 = rs.getDate("ModifyDate1");
			if( rs.getString("ModifyTime1") == null )
				this.ModifyTime1 = null;
			else
				this.ModifyTime1 = rs.getString("ModifyTime1").trim();

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

			this.PayYear = rs.getInt("PayYear");
			if( rs.getString("LongShortFlag") == null )
				this.LongShortFlag = null;
			else
				this.LongShortFlag = rs.getString("LongShortFlag").trim();

			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("AddCode") == null )
				this.AddCode = null;
			else
				this.AddCode = rs.getString("AddCode").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRAdimAscriptionB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAdimAscriptionBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRAdimAscriptionBSchema getSchema()
	{
		LRAdimAscriptionBSchema aLRAdimAscriptionBSchema = new LRAdimAscriptionBSchema();
		aLRAdimAscriptionBSchema.setSchema(this);
		return aLRAdimAscriptionBSchema;
	}

	public LRAdimAscriptionBDB getDB()
	{
		LRAdimAscriptionBDB aDBOper = new LRAdimAscriptionBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRAdimAscriptionB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AscriptionDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate1 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LongShortFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRAdimAscriptionB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MainPolno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			AscriptionDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			AscriptionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate1 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			PayYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			LongShortFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AddCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAdimAscriptionBSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("MainPolno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolno));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("AscriptionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAscriptionDate()));
		}
		if (FCode.equalsIgnoreCase("AscriptionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionType));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("Operator1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator1));
		}
		if (FCode.equalsIgnoreCase("MakeDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate1()));
		}
		if (FCode.equalsIgnoreCase("MakeTime1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime1));
		}
		if (FCode.equalsIgnoreCase("ModifyDate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate1()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime1));
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
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYear));
		}
		if (FCode.equalsIgnoreCase("LongShortFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LongShortFlag));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("AddCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddCode));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MainPolno);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAscriptionDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AscriptionType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate1()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime1);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate1()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 22:
				strFieldValue = String.valueOf(PayYear);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(LongShortFlag);
				break;
			case 24:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AddCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("MainPolno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolno = FValue.trim();
			}
			else
				MainPolno = null;
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
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayToDate = fDate.getDate( FValue );
			}
			else
				PayToDate = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
		}
		if (FCode.equalsIgnoreCase("AscriptionDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AscriptionDate = fDate.getDate( FValue );
			}
			else
				AscriptionDate = null;
		}
		if (FCode.equalsIgnoreCase("AscriptionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionType = FValue.trim();
			}
			else
				AscriptionType = null;
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
		}
		if (FCode.equalsIgnoreCase("Operator1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator1 = FValue.trim();
			}
			else
				Operator1 = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate1 = fDate.getDate( FValue );
			}
			else
				MakeDate1 = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime1 = FValue.trim();
			}
			else
				MakeTime1 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate1"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate1 = fDate.getDate( FValue );
			}
			else
				ModifyDate1 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime1 = FValue.trim();
			}
			else
				ModifyTime1 = null;
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
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("LongShortFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LongShortFlag = FValue.trim();
			}
			else
				LongShortFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equalsIgnoreCase("AddCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddCode = FValue.trim();
			}
			else
				AddCode = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRAdimAscriptionBSchema other = (LRAdimAscriptionBSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& PolNo.equals(other.getPolNo())
			&& MainPolno.equals(other.getMainPolno())
			&& RiskCode.equals(other.getRiskCode())
			&& PrtNo.equals(other.getPrtNo())
			&& EdorType.equals(other.getEdorType())
			&& AgentCode.equals(other.getAgentCode())
			&& fDate.getString(PayToDate).equals(other.getPayToDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(AscriptionDate).equals(other.getAscriptionDate())
			&& AscriptionType.equals(other.getAscriptionType())
			&& Flag.equals(other.getFlag())
			&& Operator1.equals(other.getOperator1())
			&& fDate.getString(MakeDate1).equals(other.getMakeDate1())
			&& MakeTime1.equals(other.getMakeTime1())
			&& fDate.getString(ModifyDate1).equals(other.getModifyDate1())
			&& ModifyTime1.equals(other.getModifyTime1())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PayYear == other.getPayYear()
			&& LongShortFlag.equals(other.getLongShortFlag())
			&& PayCount == other.getPayCount()
			&& ZipCode.equals(other.getZipCode())
			&& AddCode.equals(other.getAddCode())
			&& ContNo.equals(other.getContNo())
			&& ManageCom.equals(other.getManageCom());
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
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("MainPolno") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("EdorType") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("PayToDate") ) {
			return 7;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 8;
		}
		if( strFieldName.equals("AscriptionDate") ) {
			return 9;
		}
		if( strFieldName.equals("AscriptionType") ) {
			return 10;
		}
		if( strFieldName.equals("Flag") ) {
			return 11;
		}
		if( strFieldName.equals("Operator1") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate1") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime1") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate1") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime1") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 18;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
		}
		if( strFieldName.equals("PayYear") ) {
			return 22;
		}
		if( strFieldName.equals("LongShortFlag") ) {
			return 23;
		}
		if( strFieldName.equals("PayCount") ) {
			return 24;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 25;
		}
		if( strFieldName.equals("AddCode") ) {
			return 26;
		}
		if( strFieldName.equals("ContNo") ) {
			return 27;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 28;
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
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "MainPolno";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "EdorType";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "PayToDate";
				break;
			case 8:
				strFieldName = "CValiDate";
				break;
			case 9:
				strFieldName = "AscriptionDate";
				break;
			case 10:
				strFieldName = "AscriptionType";
				break;
			case 11:
				strFieldName = "Flag";
				break;
			case 12:
				strFieldName = "Operator1";
				break;
			case 13:
				strFieldName = "MakeDate1";
				break;
			case 14:
				strFieldName = "MakeTime1";
				break;
			case 15:
				strFieldName = "ModifyDate1";
				break;
			case 16:
				strFieldName = "ModifyTime1";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "MakeDate";
				break;
			case 19:
				strFieldName = "MakeTime";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
				strFieldName = "ModifyTime";
				break;
			case 22:
				strFieldName = "PayYear";
				break;
			case 23:
				strFieldName = "LongShortFlag";
				break;
			case 24:
				strFieldName = "PayCount";
				break;
			case 25:
				strFieldName = "ZipCode";
				break;
			case 26:
				strFieldName = "AddCode";
				break;
			case 27:
				strFieldName = "ContNo";
				break;
			case 28:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainPolno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AscriptionDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AscriptionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate1") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime1") ) {
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
		if( strFieldName.equals("PayYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LongShortFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
