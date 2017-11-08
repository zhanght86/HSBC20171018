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
import com.sinosoft.lis.db.LZCardAppDB;

/*
 * <p>ClassName: LZCardAppSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardAppSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCardAppSchema.class);

	// @Field
	/** 申请号码 */
	private String ApplyNo;
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
	/** 申请人 */
	private String Handler;
	/** 申请日期 */
	private Date HandleDate;
	/** 申请类型 */
	private String OperateFlag;
	/** 申请原因 */
	private String Reason;
	/** 申请机构 */
	private String ApplyCom;
	/** 批复人 */
	private String ReplyPerson;
	/** 批复日期 */
	private Date ReplyDate;
	/** 批复备注 */
	private String note;
	/** 批复机构 */
	private String ReplyCom;
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
	/** 状态标志 */
	private String StateFlag;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCardAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ApplyNo";

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
		LZCardAppSchema cloned = (LZCardAppSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getApplyNo()
	{
		return ApplyNo;
	}
	public void setApplyNo(String aApplyNo)
	{
		ApplyNo = aApplyNo;
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

	/**
	* 1、增领申请<p>
	* 2、核销修订申请<p>
	* 3、遗失申请(挂失)<p>
	* 4、销毁申请
	*/
	public String getOperateFlag()
	{
		return OperateFlag;
	}
	public void setOperateFlag(String aOperateFlag)
	{
		OperateFlag = aOperateFlag;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		Reason = aReason;
	}
	public String getApplyCom()
	{
		return ApplyCom;
	}
	public void setApplyCom(String aApplyCom)
	{
		ApplyCom = aApplyCom;
	}
	public String getReplyPerson()
	{
		return ReplyPerson;
	}
	public void setReplyPerson(String aReplyPerson)
	{
		ReplyPerson = aReplyPerson;
	}
	public String getReplyDate()
	{
		if( ReplyDate != null )
			return fDate.getString(ReplyDate);
		else
			return null;
	}
	public void setReplyDate(Date aReplyDate)
	{
		ReplyDate = aReplyDate;
	}
	public void setReplyDate(String aReplyDate)
	{
		if (aReplyDate != null && !aReplyDate.equals("") )
		{
			ReplyDate = fDate.getDate( aReplyDate );
		}
		else
			ReplyDate = null;
	}

	public String getnote()
	{
		return note;
	}
	public void setnote(String anote)
	{
		note = anote;
	}
	public String getReplyCom()
	{
		return ReplyCom;
	}
	public void setReplyCom(String aReplyCom)
	{
		ReplyCom = aReplyCom;
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
	* 增领申请<p>
	* 1、申请待批复<p>
	* 2、批复确认未发放<p>
	* 3、批复确认已发放<p>
	* 4、批复拒绝<p>
	* <p>
	* 核销修订申请<p>
	* 1、申请待批复<p>
	* 3、批复确认<p>
	* <p>
	* 遗失申请(挂失)<p>
	* 1、申请待批复<p>
	* 3、遗失确认<p>
	* 4、解除挂失<p>
	* <p>
	* 销毁申请<p>
	* 1、申请待批复<p>
	* 3、批复确认
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
	* 使用另外一个 LZCardAppSchema 对象给 Schema 赋值
	* @param: aLZCardAppSchema LZCardAppSchema
	**/
	public void setSchema(LZCardAppSchema aLZCardAppSchema)
	{
		this.ApplyNo = aLZCardAppSchema.getApplyNo();
		this.CertifyCode = aLZCardAppSchema.getCertifyCode();
		this.SubCode = aLZCardAppSchema.getSubCode();
		this.RiskCode = aLZCardAppSchema.getRiskCode();
		this.RiskVersion = aLZCardAppSchema.getRiskVersion();
		this.StartNo = aLZCardAppSchema.getStartNo();
		this.EndNo = aLZCardAppSchema.getEndNo();
		this.SendOutCom = aLZCardAppSchema.getSendOutCom();
		this.ReceiveCom = aLZCardAppSchema.getReceiveCom();
		this.SumCount = aLZCardAppSchema.getSumCount();
		this.Handler = aLZCardAppSchema.getHandler();
		this.HandleDate = fDate.getDate( aLZCardAppSchema.getHandleDate());
		this.OperateFlag = aLZCardAppSchema.getOperateFlag();
		this.Reason = aLZCardAppSchema.getReason();
		this.ApplyCom = aLZCardAppSchema.getApplyCom();
		this.ReplyPerson = aLZCardAppSchema.getReplyPerson();
		this.ReplyDate = fDate.getDate( aLZCardAppSchema.getReplyDate());
		this.note = aLZCardAppSchema.getnote();
		this.ReplyCom = aLZCardAppSchema.getReplyCom();
		this.Operator = aLZCardAppSchema.getOperator();
		this.MakeDate = fDate.getDate( aLZCardAppSchema.getMakeDate());
		this.MakeTime = aLZCardAppSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLZCardAppSchema.getModifyDate());
		this.ModifyTime = aLZCardAppSchema.getModifyTime();
		this.StateFlag = aLZCardAppSchema.getStateFlag();
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
			if( rs.getString("ApplyNo") == null )
				this.ApplyNo = null;
			else
				this.ApplyNo = rs.getString("ApplyNo").trim();

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
			if( rs.getString("Handler") == null )
				this.Handler = null;
			else
				this.Handler = rs.getString("Handler").trim();

			this.HandleDate = rs.getDate("HandleDate");
			if( rs.getString("OperateFlag") == null )
				this.OperateFlag = null;
			else
				this.OperateFlag = rs.getString("OperateFlag").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("ApplyCom") == null )
				this.ApplyCom = null;
			else
				this.ApplyCom = rs.getString("ApplyCom").trim();

			if( rs.getString("ReplyPerson") == null )
				this.ReplyPerson = null;
			else
				this.ReplyPerson = rs.getString("ReplyPerson").trim();

			this.ReplyDate = rs.getDate("ReplyDate");
			if( rs.getString("note") == null )
				this.note = null;
			else
				this.note = rs.getString("note").trim();

			if( rs.getString("ReplyCom") == null )
				this.ReplyCom = null;
			else
				this.ReplyCom = rs.getString("ReplyCom").trim();

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

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCardApp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCardAppSchema getSchema()
	{
		LZCardAppSchema aLZCardAppSchema = new LZCardAppSchema();
		aLZCardAppSchema.setSchema(this);
		return aLZCardAppSchema;
	}

	public LZCardAppDB getDB()
	{
		LZCardAppDB aDBOper = new LZCardAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardApp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendOutCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Handler)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( HandleDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyPerson)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(note)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardApp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StartNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			EndNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SendOutCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ReceiveCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SumCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			Handler = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HandleDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			OperateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ApplyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReplyPerson = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ReplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			note = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ReplyCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardAppSchema";
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
		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyNo));
		}
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
		if (FCode.equalsIgnoreCase("Handler"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Handler));
		}
		if (FCode.equalsIgnoreCase("HandleDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
		}
		if (FCode.equalsIgnoreCase("OperateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperateFlag));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("ApplyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyCom));
		}
		if (FCode.equalsIgnoreCase("ReplyPerson"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyPerson));
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
		}
		if (FCode.equalsIgnoreCase("note"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(note));
		}
		if (FCode.equalsIgnoreCase("ReplyCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyCom));
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
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ApplyNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StartNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EndNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SendOutCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ReceiveCom);
				break;
			case 9:
				strFieldValue = String.valueOf(SumCount);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Handler);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getHandleDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OperateFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ApplyCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReplyPerson);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(note);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ReplyCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
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

		if (FCode.equalsIgnoreCase("ApplyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyNo = FValue.trim();
			}
			else
				ApplyNo = null;
		}
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
		if (FCode.equalsIgnoreCase("OperateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperateFlag = FValue.trim();
			}
			else
				OperateFlag = null;
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
		if (FCode.equalsIgnoreCase("ApplyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyCom = FValue.trim();
			}
			else
				ApplyCom = null;
		}
		if (FCode.equalsIgnoreCase("ReplyPerson"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyPerson = FValue.trim();
			}
			else
				ReplyPerson = null;
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReplyDate = fDate.getDate( FValue );
			}
			else
				ReplyDate = null;
		}
		if (FCode.equalsIgnoreCase("note"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				note = FValue.trim();
			}
			else
				note = null;
		}
		if (FCode.equalsIgnoreCase("ReplyCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyCom = FValue.trim();
			}
			else
				ReplyCom = null;
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
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCardAppSchema other = (LZCardAppSchema)otherObject;
		return
			ApplyNo.equals(other.getApplyNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& SubCode.equals(other.getSubCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& StartNo.equals(other.getStartNo())
			&& EndNo.equals(other.getEndNo())
			&& SendOutCom.equals(other.getSendOutCom())
			&& ReceiveCom.equals(other.getReceiveCom())
			&& SumCount == other.getSumCount()
			&& Handler.equals(other.getHandler())
			&& fDate.getString(HandleDate).equals(other.getHandleDate())
			&& OperateFlag.equals(other.getOperateFlag())
			&& Reason.equals(other.getReason())
			&& ApplyCom.equals(other.getApplyCom())
			&& ReplyPerson.equals(other.getReplyPerson())
			&& fDate.getString(ReplyDate).equals(other.getReplyDate())
			&& note.equals(other.getnote())
			&& ReplyCom.equals(other.getReplyCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StateFlag.equals(other.getStateFlag());
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
		if( strFieldName.equals("ApplyNo") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 1;
		}
		if( strFieldName.equals("SubCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 4;
		}
		if( strFieldName.equals("StartNo") ) {
			return 5;
		}
		if( strFieldName.equals("EndNo") ) {
			return 6;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return 7;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return 8;
		}
		if( strFieldName.equals("SumCount") ) {
			return 9;
		}
		if( strFieldName.equals("Handler") ) {
			return 10;
		}
		if( strFieldName.equals("HandleDate") ) {
			return 11;
		}
		if( strFieldName.equals("OperateFlag") ) {
			return 12;
		}
		if( strFieldName.equals("Reason") ) {
			return 13;
		}
		if( strFieldName.equals("ApplyCom") ) {
			return 14;
		}
		if( strFieldName.equals("ReplyPerson") ) {
			return 15;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return 16;
		}
		if( strFieldName.equals("note") ) {
			return 17;
		}
		if( strFieldName.equals("ReplyCom") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("StateFlag") ) {
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
				strFieldName = "ApplyNo";
				break;
			case 1:
				strFieldName = "CertifyCode";
				break;
			case 2:
				strFieldName = "SubCode";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "RiskVersion";
				break;
			case 5:
				strFieldName = "StartNo";
				break;
			case 6:
				strFieldName = "EndNo";
				break;
			case 7:
				strFieldName = "SendOutCom";
				break;
			case 8:
				strFieldName = "ReceiveCom";
				break;
			case 9:
				strFieldName = "SumCount";
				break;
			case 10:
				strFieldName = "Handler";
				break;
			case 11:
				strFieldName = "HandleDate";
				break;
			case 12:
				strFieldName = "OperateFlag";
				break;
			case 13:
				strFieldName = "Reason";
				break;
			case 14:
				strFieldName = "ApplyCom";
				break;
			case 15:
				strFieldName = "ReplyPerson";
				break;
			case 16:
				strFieldName = "ReplyDate";
				break;
			case 17:
				strFieldName = "note";
				break;
			case 18:
				strFieldName = "ReplyCom";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "StateFlag";
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
		if( strFieldName.equals("ApplyNo") ) {
			return Schema.TYPE_STRING;
		}
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
		if( strFieldName.equals("Handler") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HandleDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OperateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyPerson") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("note") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyCom") ) {
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
		if( strFieldName.equals("StateFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
