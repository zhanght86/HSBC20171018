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
import com.sinosoft.lis.db.LZCardTrackBDB;

/*
 * <p>ClassName: LZCardTrackBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardTrackBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCardTrackBSchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 附标号 */
	private String SubCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 起始号 */
	private String StartNo;
	/** 终止号 */
	private String EndNo;
	/** 发放机构 */
	private String SendOutCom;
	/** 接受机构 */
	private String ReceiveCom;
	/** 数量 */
	private int SumCount;
	/** 总保费 */
	private double Prem;
	/** 总保额 */
	private double Amnt;
	/** 经办人 */
	private String Handler;
	/** 经办日期 */
	private Date HandleDate;
	/** 失效日期 */
	private Date InvaliDate;
	/** 回收清算单号 */
	private String TakeBackNo;
	/** 销售渠道 */
	private String SaleChnl;
	/** 状态标志 */
	private String StateFlag;
	/** 操作类型标志 */
	private String OperateFlag;
	/** 交费标志 */
	private String PayFlag;
	/** 到帐标志 */
	private String EnterAccFlag;
	/** 反发放原因 */
	private String Reason;
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
	/** 增领申请号码 */
	private String ApplyNo;
	/** 代理机构编码 */
	private String AgentCom;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCardTrackBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[10];
		pk[0] = "CertifyCode";
		pk[1] = "SubCode";
		pk[2] = "RiskCode";
		pk[3] = "RiskVersion";
		pk[4] = "StartNo";
		pk[5] = "EndNo";
		pk[6] = "SendOutCom";
		pk[7] = "ReceiveCom";
		pk[8] = "MakeDate";
		pk[9] = "MakeTime";

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
		LZCardTrackBSchema cloned = (LZCardTrackBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getSubCode()
	{
		return SubCode;
	}
	public void setSubCode(String aSubCode)
	{
		SubCode = aSubCode;
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
	public String getStartNo()
	{
		return StartNo;
	}
	public void setStartNo(String aStartNo)
	{
		StartNo = aStartNo;
	}
	public String getEndNo()
	{
		return EndNo;
	}
	public void setEndNo(String aEndNo)
	{
		EndNo = aEndNo;
	}
	public String getSendOutCom()
	{
		return SendOutCom;
	}
	public void setSendOutCom(String aSendOutCom)
	{
		SendOutCom = aSendOutCom;
	}
	public String getReceiveCom()
	{
		return ReceiveCom;
	}
	public void setReceiveCom(String aReceiveCom)
	{
		ReceiveCom = aReceiveCom;
	}
	public int getSumCount()
	{
		return SumCount;
	}
	public void setSumCount(int aSumCount)
	{
		SumCount = aSumCount;
	}
	public void setSumCount(String aSumCount)
	{
		if (aSumCount != null && !aSumCount.equals(""))
		{
			Integer tInteger = new Integer(aSumCount);
			int i = tInteger.intValue();
			SumCount = i;
		}
	}

	/**
	* 对于定额单，该字段为保费，对于普通单证，<p>
	* 该字段为最大金额
	*/
	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	/**
	* 对于定额单，该字段保额，普通单证为0
	*/
	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public String getHandler()
	{
		return Handler;
	}
	public void setHandler(String aHandler)
	{
		Handler = aHandler;
	}
	public String getHandleDate()
	{
		if( HandleDate != null )
			return fDate.getString(HandleDate);
		else
			return null;
	}
	public void setHandleDate(Date aHandleDate)
	{
		HandleDate = aHandleDate;
	}
	public void setHandleDate(String aHandleDate)
	{
		if (aHandleDate != null && !aHandleDate.equals("") )
		{
			HandleDate = fDate.getDate( aHandleDate );
		}
		else
			HandleDate = null;
	}

	public String getInvaliDate()
	{
		if( InvaliDate != null )
			return fDate.getString(InvaliDate);
		else
			return null;
	}
	public void setInvaliDate(Date aInvaliDate)
	{
		InvaliDate = aInvaliDate;
	}
	public void setInvaliDate(String aInvaliDate)
	{
		if (aInvaliDate != null && !aInvaliDate.equals("") )
		{
			InvaliDate = fDate.getDate( aInvaliDate );
		}
		else
			InvaliDate = null;
	}

	/**
	* 回收清算单号
	*/
	public String getTakeBackNo()
	{
		return TakeBackNo;
	}
	public void setTakeBackNo(String aTakeBackNo)
	{
		TakeBackNo = aTakeBackNo;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	/**
	* 0：  未用<p>
	* 1：  正常回收<p>
	* 2：  作废<p>
	* 3：  挂失<p>
	* 4：  销毁（新增）
	*/
	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		StateFlag = aStateFlag;
	}
	/**
	* 第一位 ：单证的操作类型<p>
	* 0：发放<p>
	* 1：回收<p>
	* 2：反发放<p>
	* 3：反回收
	*/
	public String getOperateFlag()
	{
		return OperateFlag;
	}
	public void setOperateFlag(String aOperateFlag)
	{
		OperateFlag = aOperateFlag;
	}
	/**
	* 第二位： 0  未交费(只有定额单使用)<p>
	* 1   交费
	*/
	public String getPayFlag()
	{
		return PayFlag;
	}
	public void setPayFlag(String aPayFlag)
	{
		PayFlag = aPayFlag;
	}
	/**
	* 第三位： 0  未到帐(只有定额单使用)<p>
	* 1  到帐
	*/
	public String getEnterAccFlag()
	{
		return EnterAccFlag;
	}
	public void setEnterAccFlag(String aEnterAccFlag)
	{
		EnterAccFlag = aEnterAccFlag;
	}
	/**
	* 第四位： 反发放原因(在code表中lx_code=rea_dis)
	*/
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		Reason = aReason;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	public String getApplyNo()
	{
		return ApplyNo;
	}
	public void setApplyNo(String aApplyNo)
	{
		ApplyNo = aApplyNo;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}

	/**
	* 使用另外一个 LZCardTrackBSchema 对象给 Schema 赋值
	* @param: aLZCardTrackBSchema LZCardTrackBSchema
	**/
	public void setSchema(LZCardTrackBSchema aLZCardTrackBSchema)
	{
		this.CertifyCode = aLZCardTrackBSchema.getCertifyCode();
		this.SubCode = aLZCardTrackBSchema.getSubCode();
		this.RiskCode = aLZCardTrackBSchema.getRiskCode();
		this.RiskVersion = aLZCardTrackBSchema.getRiskVersion();
		this.StartNo = aLZCardTrackBSchema.getStartNo();
		this.EndNo = aLZCardTrackBSchema.getEndNo();
		this.SendOutCom = aLZCardTrackBSchema.getSendOutCom();
		this.ReceiveCom = aLZCardTrackBSchema.getReceiveCom();
		this.SumCount = aLZCardTrackBSchema.getSumCount();
		this.Prem = aLZCardTrackBSchema.getPrem();
		this.Amnt = aLZCardTrackBSchema.getAmnt();
		this.Handler = aLZCardTrackBSchema.getHandler();
		this.HandleDate = fDate.getDate( aLZCardTrackBSchema.getHandleDate());
		this.InvaliDate = fDate.getDate( aLZCardTrackBSchema.getInvaliDate());
		this.TakeBackNo = aLZCardTrackBSchema.getTakeBackNo();
		this.SaleChnl = aLZCardTrackBSchema.getSaleChnl();
		this.StateFlag = aLZCardTrackBSchema.getStateFlag();
		this.OperateFlag = aLZCardTrackBSchema.getOperateFlag();
		this.PayFlag = aLZCardTrackBSchema.getPayFlag();
		this.EnterAccFlag = aLZCardTrackBSchema.getEnterAccFlag();
		this.Reason = aLZCardTrackBSchema.getReason();
		this.State = aLZCardTrackBSchema.getState();
		this.Operator = aLZCardTrackBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLZCardTrackBSchema.getMakeDate());
		this.MakeTime = aLZCardTrackBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLZCardTrackBSchema.getModifyDate());
		this.ModifyTime = aLZCardTrackBSchema.getModifyTime();
		this.ApplyNo = aLZCardTrackBSchema.getApplyNo();
		this.AgentCom = aLZCardTrackBSchema.getAgentCom();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("SubCode") == null )
				this.SubCode = null;
			else
				this.SubCode = rs.getString("SubCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("StartNo") == null )
				this.StartNo = null;
			else
				this.StartNo = rs.getString("StartNo").trim();

			if( rs.getString("EndNo") == null )
				this.EndNo = null;
			else
				this.EndNo = rs.getString("EndNo").trim();

			if( rs.getString("SendOutCom") == null )
				this.SendOutCom = null;
			else
				this.SendOutCom = rs.getString("SendOutCom").trim();

			if( rs.getString("ReceiveCom") == null )
				this.ReceiveCom = null;
			else
				this.ReceiveCom = rs.getString("ReceiveCom").trim();

			this.SumCount = rs.getInt("SumCount");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			this.HandleDate = rs.getDate("HandleDate");
			this.InvaliDate = rs.getDate("InvaliDate");
			if( rs.getString("TakeBackNo") == null )
				this.TakeBackNo = null;
			else
				this.TakeBackNo = rs.getString("TakeBackNo").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			if( rs.getString("OperateFlag") == null )
				this.OperateFlag = null;
			else
				this.OperateFlag = rs.getString("OperateFlag").trim();

			if( rs.getString("PayFlag") == null )
				this.PayFlag = null;
			else
				this.PayFlag = rs.getString("PayFlag").trim();

			if( rs.getString("EnterAccFlag") == null )
				this.EnterAccFlag = null;
			else
				this.EnterAccFlag = rs.getString("EnterAccFlag").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

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

			if( rs.getString("ApplyNo") == null )
				this.ApplyNo = null;
			else
				this.ApplyNo = rs.getString("ApplyNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCardTrackB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardTrackBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCardTrackBSchema getSchema()
	{
		LZCardTrackBSchema aLZCardTrackBSchema = new LZCardTrackBSchema();
		aLZCardTrackBSchema.setSchema(this);
		return aLZCardTrackBSchema;
	}

	public LZCardTrackBDB getDB()
	{
		LZCardTrackBDB aDBOper = new LZCardTrackBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardTrackB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendOutCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HandleDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InvaliDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeBackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnterAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardTrackB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			EndNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SendOutCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReceiveCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SumCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			HandleDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			InvaliDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			TakeBackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			OperateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			EnterAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ApplyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardTrackBSchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("SubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartNo));
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndNo));
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendOutCom));
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveCom));
		}
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumCount));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
		}
		if (FCode.equalsIgnoreCase("InvaliDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInvaliDate()));
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeBackNo));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("OperateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperateFlag));
		}
		if (FCode.equalsIgnoreCase("PayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayFlag));
		}
		if (FCode.equalsIgnoreCase("EnterAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnterAccFlag));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
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
		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyNo));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SubCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(StartNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(EndNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SendOutCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReceiveCom);
				break;
			case 8:
				strFieldValue = String.valueOf(SumCount);
				break;
			case 9:
				strFieldValue = String.valueOf(Prem);
				break;
			case 10:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInvaliDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(TakeBackNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(OperateFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PayFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(EnterAccFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ApplyNo);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("SubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCode = FValue.trim();
			}
			else
				SubCode = null;
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
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartNo = FValue.trim();
			}
			else
				StartNo = null;
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndNo = FValue.trim();
			}
			else
				EndNo = null;
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendOutCom = FValue.trim();
			}
			else
				SendOutCom = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveCom = FValue.trim();
			}
			else
				ReceiveCom = null;
		}
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Handler"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Handler = FValue.trim();
			}
			else
				Handler = null;
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				HandleDate = fDate.getDate( FValue );
			}
			else
				HandleDate = null;
		}
		if (FCode.equalsIgnoreCase("InvaliDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InvaliDate = fDate.getDate( FValue );
			}
			else
				InvaliDate = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeBackNo = FValue.trim();
			}
			else
				TakeBackNo = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
		}
		if (FCode.equalsIgnoreCase("OperateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperateFlag = FValue.trim();
			}
			else
				OperateFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayFlag = FValue.trim();
			}
			else
				PayFlag = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnterAccFlag = FValue.trim();
			}
			else
				EnterAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyNo = FValue.trim();
			}
			else
				ApplyNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCardTrackBSchema other = (LZCardTrackBSchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& SubCode.equals(other.getSubCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& StartNo.equals(other.getStartNo())
			&& EndNo.equals(other.getEndNo())
			&& SendOutCom.equals(other.getSendOutCom())
			&& ReceiveCom.equals(other.getReceiveCom())
			&& SumCount == other.getSumCount()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& Handler.equals(other.getHandler())
			&& fDate.getString(HandleDate).equals(other.getHandleDate())
			&& fDate.getString(InvaliDate).equals(other.getInvaliDate())
			&& TakeBackNo.equals(other.getTakeBackNo())
			&& SaleChnl.equals(other.getSaleChnl())
			&& StateFlag.equals(other.getStateFlag())
			&& OperateFlag.equals(other.getOperateFlag())
			&& PayFlag.equals(other.getPayFlag())
			&& EnterAccFlag.equals(other.getEnterAccFlag())
			&& Reason.equals(other.getReason())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ApplyNo.equals(other.getApplyNo())
			&& AgentCom.equals(other.getAgentCom());
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("SubCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 3;
		}
		if( strFieldName.equals("StartNo") ) {
			return 4;
		}
		if( strFieldName.equals("EndNo") ) {
			return 5;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return 6;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return 7;
		}
		if( strFieldName.equals("SumCount") ) {
			return 8;
		}
		if( strFieldName.equals("Prem") ) {
			return 9;
		}
		if( strFieldName.equals("Amnt") ) {
			return 10;
		}
		if( strFieldName.equals("Handler") ) {
			return 11;
		}
		if( strFieldName.equals("HandleDate") ) {
			return 12;
		}
		if( strFieldName.equals("InvaliDate") ) {
			return 13;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return 14;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 15;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 16;
		}
		if( strFieldName.equals("OperateFlag") ) {
			return 17;
		}
		if( strFieldName.equals("PayFlag") ) {
			return 18;
		}
		if( strFieldName.equals("EnterAccFlag") ) {
			return 19;
		}
		if( strFieldName.equals("Reason") ) {
			return 20;
		}
		if( strFieldName.equals("State") ) {
			return 21;
		}
		if( strFieldName.equals("Operator") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
		}
		if( strFieldName.equals("ApplyNo") ) {
			return 27;
		}
		if( strFieldName.equals("AgentCom") ) {
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "SubCode";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "RiskVersion";
				break;
			case 4:
				strFieldName = "StartNo";
				break;
			case 5:
				strFieldName = "EndNo";
				break;
			case 6:
				strFieldName = "SendOutCom";
				break;
			case 7:
				strFieldName = "ReceiveCom";
				break;
			case 8:
				strFieldName = "SumCount";
				break;
			case 9:
				strFieldName = "Prem";
				break;
			case 10:
				strFieldName = "Amnt";
				break;
			case 11:
				strFieldName = "Handler";
				break;
			case 12:
				strFieldName = "HandleDate";
				break;
			case 13:
				strFieldName = "InvaliDate";
				break;
			case 14:
				strFieldName = "TakeBackNo";
				break;
			case 15:
				strFieldName = "SaleChnl";
				break;
			case 16:
				strFieldName = "StateFlag";
				break;
			case 17:
				strFieldName = "OperateFlag";
				break;
			case 18:
				strFieldName = "PayFlag";
				break;
			case 19:
				strFieldName = "EnterAccFlag";
				break;
			case 20:
				strFieldName = "Reason";
				break;
			case 21:
				strFieldName = "State";
				break;
			case 22:
				strFieldName = "Operator";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
				strFieldName = "ModifyTime";
				break;
			case 27:
				strFieldName = "ApplyNo";
				break;
			case 28:
				strFieldName = "AgentCom";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandleDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InvaliDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnterAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ApplyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
