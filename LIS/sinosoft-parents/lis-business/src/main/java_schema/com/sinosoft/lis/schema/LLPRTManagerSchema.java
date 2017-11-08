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
import com.sinosoft.lis.db.LLPRTManagerDB;

/*
 * <p>ClassName: LLPRTManagerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统缺失模型
 */
public class LLPRTManagerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLPRTManagerSchema.class);

	// @Field
	/** Clmno */
	private String ClmNo;
	/** Customerno */
	private String CustomerNo;
	/** Prtcode */
	private String PrtCode;
	/** Prtname */
	private String PrtName;
	/** Prtseq */
	private String PrtSeq;
	/** Prtmode */
	private String PrtMode;
	/** Prtphase */
	private String PrtPhase;
	/** Prtzone */
	private String PrtZone;
	/** Patchflag */
	private String PatchFlag;
	/** Patchyn */
	private String PatchYN;
	/** Patchreainpflag */
	private String PatchReaInpFlag;
	/** Patchreason */
	private String PatchReason;
	/** Patchdesc */
	private String PatchDesc;
	/** Patchcount */
	private int PatchCount;
	/** Prtstate */
	private String PrtState;
	/** Reqoperator */
	private String ReqOperator;
	/** Reqdate */
	private Date ReqDate;
	/** Reqtime */
	private String ReqTime;
	/** Reqcom */
	private String ReqCom;
	/** Exeoperator */
	private String ExeOperator;
	/** Execom */
	private String ExeCom;
	/** Agentcode */
	private String AgentCode;
	/** Managecom */
	private String ManageCom;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Donedate */
	private Date DoneDate;
	/** Donetime */
	private String DoneTime;
	/** Standbyflag1 */
	private String StandbyFlag1;
	/** Standbyflag2 */
	private String StandbyFlag2;
	/** Standbyflag3 */
	private String StandbyFlag3;
	/** Standbyflag4 */
	private String StandbyFlag4;
	/** Remark */
	private String Remark;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLPRTManagerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ClmNo";
		pk[1] = "CustomerNo";
		pk[2] = "PrtCode";

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
		LLPRTManagerSchema cloned = (LLPRTManagerSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getPrtCode()
	{
		return PrtCode;
	}
	public void setPrtCode(String aPrtCode)
	{
		PrtCode = aPrtCode;
	}
	public String getPrtName()
	{
		return PrtName;
	}
	public void setPrtName(String aPrtName)
	{
		PrtName = aPrtName;
	}
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getPrtMode()
	{
		return PrtMode;
	}
	public void setPrtMode(String aPrtMode)
	{
		PrtMode = aPrtMode;
	}
	public String getPrtPhase()
	{
		return PrtPhase;
	}
	public void setPrtPhase(String aPrtPhase)
	{
		PrtPhase = aPrtPhase;
	}
	public String getPrtZone()
	{
		return PrtZone;
	}
	public void setPrtZone(String aPrtZone)
	{
		PrtZone = aPrtZone;
	}
	public String getPatchFlag()
	{
		return PatchFlag;
	}
	public void setPatchFlag(String aPatchFlag)
	{
		PatchFlag = aPatchFlag;
	}
	public String getPatchYN()
	{
		return PatchYN;
	}
	public void setPatchYN(String aPatchYN)
	{
		PatchYN = aPatchYN;
	}
	public String getPatchReaInpFlag()
	{
		return PatchReaInpFlag;
	}
	public void setPatchReaInpFlag(String aPatchReaInpFlag)
	{
		PatchReaInpFlag = aPatchReaInpFlag;
	}
	public String getPatchReason()
	{
		return PatchReason;
	}
	public void setPatchReason(String aPatchReason)
	{
		PatchReason = aPatchReason;
	}
	public String getPatchDesc()
	{
		return PatchDesc;
	}
	public void setPatchDesc(String aPatchDesc)
	{
		PatchDesc = aPatchDesc;
	}
	public int getPatchCount()
	{
		return PatchCount;
	}
	public void setPatchCount(int aPatchCount)
	{
		PatchCount = aPatchCount;
	}
	public void setPatchCount(String aPatchCount)
	{
		if (aPatchCount != null && !aPatchCount.equals(""))
		{
			Integer tInteger = new Integer(aPatchCount);
			int i = tInteger.intValue();
			PatchCount = i;
		}
	}

	public String getPrtState()
	{
		return PrtState;
	}
	public void setPrtState(String aPrtState)
	{
		PrtState = aPrtState;
	}
	public String getReqOperator()
	{
		return ReqOperator;
	}
	public void setReqOperator(String aReqOperator)
	{
		ReqOperator = aReqOperator;
	}
	public String getReqDate()
	{
		if( ReqDate != null )
			return fDate.getString(ReqDate);
		else
			return null;
	}
	public void setReqDate(Date aReqDate)
	{
		ReqDate = aReqDate;
	}
	public void setReqDate(String aReqDate)
	{
		if (aReqDate != null && !aReqDate.equals("") )
		{
			ReqDate = fDate.getDate( aReqDate );
		}
		else
			ReqDate = null;
	}

	public String getReqTime()
	{
		return ReqTime;
	}
	public void setReqTime(String aReqTime)
	{
		ReqTime = aReqTime;
	}
	public String getReqCom()
	{
		return ReqCom;
	}
	public void setReqCom(String aReqCom)
	{
		ReqCom = aReqCom;
	}
	public String getExeOperator()
	{
		return ExeOperator;
	}
	public void setExeOperator(String aExeOperator)
	{
		ExeOperator = aExeOperator;
	}
	public String getExeCom()
	{
		return ExeCom;
	}
	public void setExeCom(String aExeCom)
	{
		ExeCom = aExeCom;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	public String getDoneDate()
	{
		if( DoneDate != null )
			return fDate.getString(DoneDate);
		else
			return null;
	}
	public void setDoneDate(Date aDoneDate)
	{
		DoneDate = aDoneDate;
	}
	public void setDoneDate(String aDoneDate)
	{
		if (aDoneDate != null && !aDoneDate.equals("") )
		{
			DoneDate = fDate.getDate( aDoneDate );
		}
		else
			DoneDate = null;
	}

	public String getDoneTime()
	{
		return DoneTime;
	}
	public void setDoneTime(String aDoneTime)
	{
		DoneTime = aDoneTime;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		StandbyFlag4 = aStandbyFlag4;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLPRTManagerSchema 对象给 Schema 赋值
	* @param: aLLPRTManagerSchema LLPRTManagerSchema
	**/
	public void setSchema(LLPRTManagerSchema aLLPRTManagerSchema)
	{
		this.ClmNo = aLLPRTManagerSchema.getClmNo();
		this.CustomerNo = aLLPRTManagerSchema.getCustomerNo();
		this.PrtCode = aLLPRTManagerSchema.getPrtCode();
		this.PrtName = aLLPRTManagerSchema.getPrtName();
		this.PrtSeq = aLLPRTManagerSchema.getPrtSeq();
		this.PrtMode = aLLPRTManagerSchema.getPrtMode();
		this.PrtPhase = aLLPRTManagerSchema.getPrtPhase();
		this.PrtZone = aLLPRTManagerSchema.getPrtZone();
		this.PatchFlag = aLLPRTManagerSchema.getPatchFlag();
		this.PatchYN = aLLPRTManagerSchema.getPatchYN();
		this.PatchReaInpFlag = aLLPRTManagerSchema.getPatchReaInpFlag();
		this.PatchReason = aLLPRTManagerSchema.getPatchReason();
		this.PatchDesc = aLLPRTManagerSchema.getPatchDesc();
		this.PatchCount = aLLPRTManagerSchema.getPatchCount();
		this.PrtState = aLLPRTManagerSchema.getPrtState();
		this.ReqOperator = aLLPRTManagerSchema.getReqOperator();
		this.ReqDate = fDate.getDate( aLLPRTManagerSchema.getReqDate());
		this.ReqTime = aLLPRTManagerSchema.getReqTime();
		this.ReqCom = aLLPRTManagerSchema.getReqCom();
		this.ExeOperator = aLLPRTManagerSchema.getExeOperator();
		this.ExeCom = aLLPRTManagerSchema.getExeCom();
		this.AgentCode = aLLPRTManagerSchema.getAgentCode();
		this.ManageCom = aLLPRTManagerSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLLPRTManagerSchema.getMakeDate());
		this.MakeTime = aLLPRTManagerSchema.getMakeTime();
		this.DoneDate = fDate.getDate( aLLPRTManagerSchema.getDoneDate());
		this.DoneTime = aLLPRTManagerSchema.getDoneTime();
		this.StandbyFlag1 = aLLPRTManagerSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLLPRTManagerSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLLPRTManagerSchema.getStandbyFlag3();
		this.StandbyFlag4 = aLLPRTManagerSchema.getStandbyFlag4();
		this.Remark = aLLPRTManagerSchema.getRemark();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("PrtCode") == null )
				this.PrtCode = null;
			else
				this.PrtCode = rs.getString("PrtCode").trim();

			if( rs.getString("PrtName") == null )
				this.PrtName = null;
			else
				this.PrtName = rs.getString("PrtName").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("PrtMode") == null )
				this.PrtMode = null;
			else
				this.PrtMode = rs.getString("PrtMode").trim();

			if( rs.getString("PrtPhase") == null )
				this.PrtPhase = null;
			else
				this.PrtPhase = rs.getString("PrtPhase").trim();

			if( rs.getString("PrtZone") == null )
				this.PrtZone = null;
			else
				this.PrtZone = rs.getString("PrtZone").trim();

			if( rs.getString("PatchFlag") == null )
				this.PatchFlag = null;
			else
				this.PatchFlag = rs.getString("PatchFlag").trim();

			if( rs.getString("PatchYN") == null )
				this.PatchYN = null;
			else
				this.PatchYN = rs.getString("PatchYN").trim();

			if( rs.getString("PatchReaInpFlag") == null )
				this.PatchReaInpFlag = null;
			else
				this.PatchReaInpFlag = rs.getString("PatchReaInpFlag").trim();

			if( rs.getString("PatchReason") == null )
				this.PatchReason = null;
			else
				this.PatchReason = rs.getString("PatchReason").trim();

			if( rs.getString("PatchDesc") == null )
				this.PatchDesc = null;
			else
				this.PatchDesc = rs.getString("PatchDesc").trim();

			this.PatchCount = rs.getInt("PatchCount");
			if( rs.getString("PrtState") == null )
				this.PrtState = null;
			else
				this.PrtState = rs.getString("PrtState").trim();

			if( rs.getString("ReqOperator") == null )
				this.ReqOperator = null;
			else
				this.ReqOperator = rs.getString("ReqOperator").trim();

			this.ReqDate = rs.getDate("ReqDate");
			if( rs.getString("ReqTime") == null )
				this.ReqTime = null;
			else
				this.ReqTime = rs.getString("ReqTime").trim();

			if( rs.getString("ReqCom") == null )
				this.ReqCom = null;
			else
				this.ReqCom = rs.getString("ReqCom").trim();

			if( rs.getString("ExeOperator") == null )
				this.ExeOperator = null;
			else
				this.ExeOperator = rs.getString("ExeOperator").trim();

			if( rs.getString("ExeCom") == null )
				this.ExeCom = null;
			else
				this.ExeCom = rs.getString("ExeCom").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.DoneDate = rs.getDate("DoneDate");
			if( rs.getString("DoneTime") == null )
				this.DoneTime = null;
			else
				this.DoneTime = rs.getString("DoneTime").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("StandbyFlag4") == null )
				this.StandbyFlag4 = null;
			else
				this.StandbyFlag4 = rs.getString("StandbyFlag4").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLPRTManager表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTManagerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLPRTManagerSchema getSchema()
	{
		LLPRTManagerSchema aLLPRTManagerSchema = new LLPRTManagerSchema();
		aLLPRTManagerSchema.setSchema(this);
		return aLLPRTManagerSchema;
	}

	public LLPRTManagerDB getDB()
	{
		LLPRTManagerDB aDBOper = new LLPRTManagerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLPRTManager描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtZone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchYN)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchReaInpFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PatchDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PatchCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReqOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReqDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReqTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReqCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExeCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DoneDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoneTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLPRTManager>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrtPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PrtZone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PatchFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PatchYN = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PatchReaInpFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PatchReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PatchDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PatchCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			PrtState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReqOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ReqDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ReqTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ReqCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ExeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ExeCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			DoneDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			DoneTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			StandbyFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPRTManagerSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("PrtCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtCode));
		}
		if (FCode.equalsIgnoreCase("PrtName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtName));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("PrtMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtMode));
		}
		if (FCode.equalsIgnoreCase("PrtPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtPhase));
		}
		if (FCode.equalsIgnoreCase("PrtZone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtZone));
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchFlag));
		}
		if (FCode.equalsIgnoreCase("PatchYN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchYN));
		}
		if (FCode.equalsIgnoreCase("PatchReaInpFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchReaInpFlag));
		}
		if (FCode.equalsIgnoreCase("PatchReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchReason));
		}
		if (FCode.equalsIgnoreCase("PatchDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchDesc));
		}
		if (FCode.equalsIgnoreCase("PatchCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchCount));
		}
		if (FCode.equalsIgnoreCase("PrtState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtState));
		}
		if (FCode.equalsIgnoreCase("ReqOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReqOperator));
		}
		if (FCode.equalsIgnoreCase("ReqDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReqDate()));
		}
		if (FCode.equalsIgnoreCase("ReqTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReqTime));
		}
		if (FCode.equalsIgnoreCase("ReqCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReqCom));
		}
		if (FCode.equalsIgnoreCase("ExeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExeOperator));
		}
		if (FCode.equalsIgnoreCase("ExeCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExeCom));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
		}
		if (FCode.equalsIgnoreCase("DoneTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoneTime));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag4));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrtPhase);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PrtZone);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PatchFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PatchYN);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PatchReaInpFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PatchReason);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PatchDesc);
				break;
			case 13:
				strFieldValue = String.valueOf(PatchCount);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PrtState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReqOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReqDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ReqTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ReqCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ExeOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ExeCom);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(DoneTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag4);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtCode = FValue.trim();
			}
			else
				PrtCode = null;
		}
		if (FCode.equalsIgnoreCase("PrtName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtName = FValue.trim();
			}
			else
				PrtName = null;
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("PrtMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtMode = FValue.trim();
			}
			else
				PrtMode = null;
		}
		if (FCode.equalsIgnoreCase("PrtPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtPhase = FValue.trim();
			}
			else
				PrtPhase = null;
		}
		if (FCode.equalsIgnoreCase("PrtZone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtZone = FValue.trim();
			}
			else
				PrtZone = null;
		}
		if (FCode.equalsIgnoreCase("PatchFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchFlag = FValue.trim();
			}
			else
				PatchFlag = null;
		}
		if (FCode.equalsIgnoreCase("PatchYN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchYN = FValue.trim();
			}
			else
				PatchYN = null;
		}
		if (FCode.equalsIgnoreCase("PatchReaInpFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchReaInpFlag = FValue.trim();
			}
			else
				PatchReaInpFlag = null;
		}
		if (FCode.equalsIgnoreCase("PatchReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchReason = FValue.trim();
			}
			else
				PatchReason = null;
		}
		if (FCode.equalsIgnoreCase("PatchDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PatchDesc = FValue.trim();
			}
			else
				PatchDesc = null;
		}
		if (FCode.equalsIgnoreCase("PatchCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PatchCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("PrtState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtState = FValue.trim();
			}
			else
				PrtState = null;
		}
		if (FCode.equalsIgnoreCase("ReqOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReqOperator = FValue.trim();
			}
			else
				ReqOperator = null;
		}
		if (FCode.equalsIgnoreCase("ReqDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReqDate = fDate.getDate( FValue );
			}
			else
				ReqDate = null;
		}
		if (FCode.equalsIgnoreCase("ReqTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReqTime = FValue.trim();
			}
			else
				ReqTime = null;
		}
		if (FCode.equalsIgnoreCase("ReqCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReqCom = FValue.trim();
			}
			else
				ReqCom = null;
		}
		if (FCode.equalsIgnoreCase("ExeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExeOperator = FValue.trim();
			}
			else
				ExeOperator = null;
		}
		if (FCode.equalsIgnoreCase("ExeCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExeCom = FValue.trim();
			}
			else
				ExeCom = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DoneDate = fDate.getDate( FValue );
			}
			else
				DoneDate = null;
		}
		if (FCode.equalsIgnoreCase("DoneTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoneTime = FValue.trim();
			}
			else
				DoneTime = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag4 = FValue.trim();
			}
			else
				StandbyFlag4 = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLPRTManagerSchema other = (LLPRTManagerSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& PrtCode.equals(other.getPrtCode())
			&& PrtName.equals(other.getPrtName())
			&& PrtSeq.equals(other.getPrtSeq())
			&& PrtMode.equals(other.getPrtMode())
			&& PrtPhase.equals(other.getPrtPhase())
			&& PrtZone.equals(other.getPrtZone())
			&& PatchFlag.equals(other.getPatchFlag())
			&& PatchYN.equals(other.getPatchYN())
			&& PatchReaInpFlag.equals(other.getPatchReaInpFlag())
			&& PatchReason.equals(other.getPatchReason())
			&& PatchDesc.equals(other.getPatchDesc())
			&& PatchCount == other.getPatchCount()
			&& PrtState.equals(other.getPrtState())
			&& ReqOperator.equals(other.getReqOperator())
			&& fDate.getString(ReqDate).equals(other.getReqDate())
			&& ReqTime.equals(other.getReqTime())
			&& ReqCom.equals(other.getReqCom())
			&& ExeOperator.equals(other.getExeOperator())
			&& ExeCom.equals(other.getExeCom())
			&& AgentCode.equals(other.getAgentCode())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(DoneDate).equals(other.getDoneDate())
			&& DoneTime.equals(other.getDoneTime())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& StandbyFlag4.equals(other.getStandbyFlag4())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtCode") ) {
			return 2;
		}
		if( strFieldName.equals("PrtName") ) {
			return 3;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 4;
		}
		if( strFieldName.equals("PrtMode") ) {
			return 5;
		}
		if( strFieldName.equals("PrtPhase") ) {
			return 6;
		}
		if( strFieldName.equals("PrtZone") ) {
			return 7;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return 8;
		}
		if( strFieldName.equals("PatchYN") ) {
			return 9;
		}
		if( strFieldName.equals("PatchReaInpFlag") ) {
			return 10;
		}
		if( strFieldName.equals("PatchReason") ) {
			return 11;
		}
		if( strFieldName.equals("PatchDesc") ) {
			return 12;
		}
		if( strFieldName.equals("PatchCount") ) {
			return 13;
		}
		if( strFieldName.equals("PrtState") ) {
			return 14;
		}
		if( strFieldName.equals("ReqOperator") ) {
			return 15;
		}
		if( strFieldName.equals("ReqDate") ) {
			return 16;
		}
		if( strFieldName.equals("ReqTime") ) {
			return 17;
		}
		if( strFieldName.equals("ReqCom") ) {
			return 18;
		}
		if( strFieldName.equals("ExeOperator") ) {
			return 19;
		}
		if( strFieldName.equals("ExeCom") ) {
			return 20;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 21;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("DoneDate") ) {
			return 25;
		}
		if( strFieldName.equals("DoneTime") ) {
			return 26;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 27;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 28;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 29;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return 30;
		}
		if( strFieldName.equals("Remark") ) {
			return 31;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "PrtCode";
				break;
			case 3:
				strFieldName = "PrtName";
				break;
			case 4:
				strFieldName = "PrtSeq";
				break;
			case 5:
				strFieldName = "PrtMode";
				break;
			case 6:
				strFieldName = "PrtPhase";
				break;
			case 7:
				strFieldName = "PrtZone";
				break;
			case 8:
				strFieldName = "PatchFlag";
				break;
			case 9:
				strFieldName = "PatchYN";
				break;
			case 10:
				strFieldName = "PatchReaInpFlag";
				break;
			case 11:
				strFieldName = "PatchReason";
				break;
			case 12:
				strFieldName = "PatchDesc";
				break;
			case 13:
				strFieldName = "PatchCount";
				break;
			case 14:
				strFieldName = "PrtState";
				break;
			case 15:
				strFieldName = "ReqOperator";
				break;
			case 16:
				strFieldName = "ReqDate";
				break;
			case 17:
				strFieldName = "ReqTime";
				break;
			case 18:
				strFieldName = "ReqCom";
				break;
			case 19:
				strFieldName = "ExeOperator";
				break;
			case 20:
				strFieldName = "ExeCom";
				break;
			case 21:
				strFieldName = "AgentCode";
				break;
			case 22:
				strFieldName = "ManageCom";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "DoneDate";
				break;
			case 26:
				strFieldName = "DoneTime";
				break;
			case 27:
				strFieldName = "StandbyFlag1";
				break;
			case 28:
				strFieldName = "StandbyFlag2";
				break;
			case 29:
				strFieldName = "StandbyFlag3";
				break;
			case 30:
				strFieldName = "StandbyFlag4";
				break;
			case 31:
				strFieldName = "Remark";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtZone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchYN") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchReaInpFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PrtState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReqOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReqDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReqTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReqCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExeCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoneDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DoneTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
