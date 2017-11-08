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
import com.sinosoft.lis.db.LRAscriptionDB;

/*
 * <p>ClassName: LRAscriptionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LRAscriptionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRAscriptionSchema.class);

	// @Field
	/** 保单号码 */
	private String PolNo;
	/** 挂靠代理人编码 */
	private String UnionAgent;
	/** 原代理人编码 */
	private String AgentOld;
	/** 新代理人编码 */
	private String AgentNew;
	/** 归属类别 */
	private String AClass;
	/** 最后一次有效标志 */
	private String ValidFlag;
	/** 归属日期 */
	private Date AscriptionDate;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 主险保单号 */
	private String MainPolNo;
	/** 备注 */
	private String Noti;
	/** 管理机构 */
	private String ManageCom;
	/** 归属类型 */
	private String AscriptType;
	/** 印刷号码 */
	private String PrtNo;
	/** 应交日期 */
	private Date PayToDate;
	/** 第几次交费 */
	private int PayCount;
	/** 老保单号 */
	private String OldPolno;
	/** 险种编码 */
	private String RiskCode;
	/** 生效日期 */
	private Date CValiDate;
	/** 保单年度 */
	private int PayYear;
	/** 邮编 */
	private String ZipCode;
	/** 邮编附加码 */
	private String AddCode;
	/** 合同号 */
	private String ContNo;

	public static final int FIELDNUM = 26;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRAscriptionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PolNo";

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
		LRAscriptionSchema cloned = (LRAscriptionSchema)super.clone();
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
	public String getUnionAgent()
	{
		return UnionAgent;
	}
	public void setUnionAgent(String aUnionAgent)
	{
		UnionAgent = aUnionAgent;
	}
	public String getAgentOld()
	{
		return AgentOld;
	}
	public void setAgentOld(String aAgentOld)
	{
		AgentOld = aAgentOld;
	}
	public String getAgentNew()
	{
		return AgentNew;
	}
	public void setAgentNew(String aAgentNew)
	{
		AgentNew = aAgentNew;
	}
	/**
	* 归属类别（01-代理人离职 02-续收员处理 03-内勤人员处理 99-其他）
	*/
	public String getAClass()
	{
		return AClass;
	}
	public void setAClass(String aAClass)
	{
		AClass = aAClass;
	}
	/**
	* 1-长期险<p>
	* 0-短期险
	*/
	public String getValidFlag()
	{
		return ValidFlag;
	}
	public void setValidFlag(String aValidFlag)
	{
		ValidFlag = aValidFlag;
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
	public String getMainPolNo()
	{
		return MainPolNo;
	}
	public void setMainPolNo(String aMainPolNo)
	{
		MainPolNo = aMainPolNo;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
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
	* 00 - 一次保单归属<p>
	* <p>
	* 01 - 手工调配归属<p>
	* 02 - 离职归属<p>
	* 03 - 保单迁移归属
	*/
	public String getAscriptType()
	{
		return AscriptType;
	}
	public void setAscriptType(String aAscriptType)
	{
		AscriptType = aAscriptType;
	}
	/**
	* 由操作员录入<p>
	* 第3,4位的意义如下：<p>
	* <p>
	* 11：个人印刷号码<p>
	* <p>
	* 12：集体印刷号码<p>
	* <p>
	* 15：银行险印刷号码
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
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

	/**
	* 由于续保的保单在归属表中存放的是投保单号，为方便寻找其对应的老保单号，增加老保单号字段
	*/
	public String getOldPolno()
	{
		return OldPolno;
	}
	public void setOldPolno(String aOldPolno)
	{
		OldPolno = aOldPolno;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
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

	/**
	* 使用另外一个 LRAscriptionSchema 对象给 Schema 赋值
	* @param: aLRAscriptionSchema LRAscriptionSchema
	**/
	public void setSchema(LRAscriptionSchema aLRAscriptionSchema)
	{
		this.PolNo = aLRAscriptionSchema.getPolNo();
		this.UnionAgent = aLRAscriptionSchema.getUnionAgent();
		this.AgentOld = aLRAscriptionSchema.getAgentOld();
		this.AgentNew = aLRAscriptionSchema.getAgentNew();
		this.AClass = aLRAscriptionSchema.getAClass();
		this.ValidFlag = aLRAscriptionSchema.getValidFlag();
		this.AscriptionDate = fDate.getDate( aLRAscriptionSchema.getAscriptionDate());
		this.Operator = aLRAscriptionSchema.getOperator();
		this.MakeDate = fDate.getDate( aLRAscriptionSchema.getMakeDate());
		this.MakeTime = aLRAscriptionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLRAscriptionSchema.getModifyDate());
		this.ModifyTime = aLRAscriptionSchema.getModifyTime();
		this.MainPolNo = aLRAscriptionSchema.getMainPolNo();
		this.Noti = aLRAscriptionSchema.getNoti();
		this.ManageCom = aLRAscriptionSchema.getManageCom();
		this.AscriptType = aLRAscriptionSchema.getAscriptType();
		this.PrtNo = aLRAscriptionSchema.getPrtNo();
		this.PayToDate = fDate.getDate( aLRAscriptionSchema.getPayToDate());
		this.PayCount = aLRAscriptionSchema.getPayCount();
		this.OldPolno = aLRAscriptionSchema.getOldPolno();
		this.RiskCode = aLRAscriptionSchema.getRiskCode();
		this.CValiDate = fDate.getDate( aLRAscriptionSchema.getCValiDate());
		this.PayYear = aLRAscriptionSchema.getPayYear();
		this.ZipCode = aLRAscriptionSchema.getZipCode();
		this.AddCode = aLRAscriptionSchema.getAddCode();
		this.ContNo = aLRAscriptionSchema.getContNo();
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

			if( rs.getString("UnionAgent") == null )
				this.UnionAgent = null;
			else
				this.UnionAgent = rs.getString("UnionAgent").trim();

			if( rs.getString("AgentOld") == null )
				this.AgentOld = null;
			else
				this.AgentOld = rs.getString("AgentOld").trim();

			if( rs.getString("AgentNew") == null )
				this.AgentNew = null;
			else
				this.AgentNew = rs.getString("AgentNew").trim();

			if( rs.getString("AClass") == null )
				this.AClass = null;
			else
				this.AClass = rs.getString("AClass").trim();

			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

			this.AscriptionDate = rs.getDate("AscriptionDate");
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

			if( rs.getString("MainPolNo") == null )
				this.MainPolNo = null;
			else
				this.MainPolNo = rs.getString("MainPolNo").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AscriptType") == null )
				this.AscriptType = null;
			else
				this.AscriptType = rs.getString("AscriptType").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			this.PayToDate = rs.getDate("PayToDate");
			this.PayCount = rs.getInt("PayCount");
			if( rs.getString("OldPolno") == null )
				this.OldPolno = null;
			else
				this.OldPolno = rs.getString("OldPolno").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.PayYear = rs.getInt("PayYear");
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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRAscription表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRAscriptionSchema getSchema()
	{
		LRAscriptionSchema aLRAscriptionSchema = new LRAscriptionSchema();
		aLRAscriptionSchema.setSchema(this);
		return aLRAscriptionSchema;
	}

	public LRAscriptionDB getDB()
	{
		LRAscriptionDB aDBOper = new LRAscriptionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRAscription描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnionAgent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentOld)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentNew)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AscriptionDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PayToDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldPolno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRAscription>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UnionAgent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentOld = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AscriptionDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MainPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AscriptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PayToDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			PayCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			OldPolno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			PayYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AddCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRAscriptionSchema";
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
		if (FCode.equalsIgnoreCase("UnionAgent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnionAgent));
		}
		if (FCode.equalsIgnoreCase("AgentOld"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentOld));
		}
		if (FCode.equalsIgnoreCase("AgentNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentNew));
		}
		if (FCode.equalsIgnoreCase("AClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AClass));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
		}
		if (FCode.equalsIgnoreCase("AscriptionDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAscriptionDate()));
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
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPolNo));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptType));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
		}
		if (FCode.equalsIgnoreCase("PayCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCount));
		}
		if (FCode.equalsIgnoreCase("OldPolno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldPolno));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("PayYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayYear));
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
				strFieldValue = StrTool.GBKToUnicode(UnionAgent);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentOld);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentNew);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AClass);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAscriptionDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MainPolNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Noti);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AscriptType);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPayToDate()));
				break;
			case 18:
				strFieldValue = String.valueOf(PayCount);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(OldPolno);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 22:
				strFieldValue = String.valueOf(PayYear);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AddCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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
		if (FCode.equalsIgnoreCase("UnionAgent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnionAgent = FValue.trim();
			}
			else
				UnionAgent = null;
		}
		if (FCode.equalsIgnoreCase("AgentOld"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentOld = FValue.trim();
			}
			else
				AgentOld = null;
		}
		if (FCode.equalsIgnoreCase("AgentNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentNew = FValue.trim();
			}
			else
				AgentNew = null;
		}
		if (FCode.equalsIgnoreCase("AClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AClass = FValue.trim();
			}
			else
				AClass = null;
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidFlag = FValue.trim();
			}
			else
				ValidFlag = null;
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
		if (FCode.equalsIgnoreCase("MainPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainPolNo = FValue.trim();
			}
			else
				MainPolNo = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
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
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptType = FValue.trim();
			}
			else
				AscriptType = null;
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
		if (FCode.equalsIgnoreCase("PayToDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PayToDate = fDate.getDate( FValue );
			}
			else
				PayToDate = null;
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
		if (FCode.equalsIgnoreCase("OldPolno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldPolno = FValue.trim();
			}
			else
				OldPolno = null;
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
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRAscriptionSchema other = (LRAscriptionSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& UnionAgent.equals(other.getUnionAgent())
			&& AgentOld.equals(other.getAgentOld())
			&& AgentNew.equals(other.getAgentNew())
			&& AClass.equals(other.getAClass())
			&& ValidFlag.equals(other.getValidFlag())
			&& fDate.getString(AscriptionDate).equals(other.getAscriptionDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& MainPolNo.equals(other.getMainPolNo())
			&& Noti.equals(other.getNoti())
			&& ManageCom.equals(other.getManageCom())
			&& AscriptType.equals(other.getAscriptType())
			&& PrtNo.equals(other.getPrtNo())
			&& fDate.getString(PayToDate).equals(other.getPayToDate())
			&& PayCount == other.getPayCount()
			&& OldPolno.equals(other.getOldPolno())
			&& RiskCode.equals(other.getRiskCode())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& PayYear == other.getPayYear()
			&& ZipCode.equals(other.getZipCode())
			&& AddCode.equals(other.getAddCode())
			&& ContNo.equals(other.getContNo());
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
		if( strFieldName.equals("UnionAgent") ) {
			return 1;
		}
		if( strFieldName.equals("AgentOld") ) {
			return 2;
		}
		if( strFieldName.equals("AgentNew") ) {
			return 3;
		}
		if( strFieldName.equals("AClass") ) {
			return 4;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return 5;
		}
		if( strFieldName.equals("AscriptionDate") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 11;
		}
		if( strFieldName.equals("MainPolNo") ) {
			return 12;
		}
		if( strFieldName.equals("Noti") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("AscriptType") ) {
			return 15;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 16;
		}
		if( strFieldName.equals("PayToDate") ) {
			return 17;
		}
		if( strFieldName.equals("PayCount") ) {
			return 18;
		}
		if( strFieldName.equals("OldPolno") ) {
			return 19;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 20;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 21;
		}
		if( strFieldName.equals("PayYear") ) {
			return 22;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 23;
		}
		if( strFieldName.equals("AddCode") ) {
			return 24;
		}
		if( strFieldName.equals("ContNo") ) {
			return 25;
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
				strFieldName = "UnionAgent";
				break;
			case 2:
				strFieldName = "AgentOld";
				break;
			case 3:
				strFieldName = "AgentNew";
				break;
			case 4:
				strFieldName = "AClass";
				break;
			case 5:
				strFieldName = "ValidFlag";
				break;
			case 6:
				strFieldName = "AscriptionDate";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
				break;
			case 12:
				strFieldName = "MainPolNo";
				break;
			case 13:
				strFieldName = "Noti";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "AscriptType";
				break;
			case 16:
				strFieldName = "PrtNo";
				break;
			case 17:
				strFieldName = "PayToDate";
				break;
			case 18:
				strFieldName = "PayCount";
				break;
			case 19:
				strFieldName = "OldPolno";
				break;
			case 20:
				strFieldName = "RiskCode";
				break;
			case 21:
				strFieldName = "CValiDate";
				break;
			case 22:
				strFieldName = "PayYear";
				break;
			case 23:
				strFieldName = "ZipCode";
				break;
			case 24:
				strFieldName = "AddCode";
				break;
			case 25:
				strFieldName = "ContNo";
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
		if( strFieldName.equals("UnionAgent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentOld") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentNew") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionDate") ) {
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
		if( strFieldName.equals("MainPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayToDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OldPolno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("PayYear") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
