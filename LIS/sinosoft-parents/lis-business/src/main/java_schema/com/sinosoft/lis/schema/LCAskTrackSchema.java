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
import com.sinosoft.lis.db.LCAskTrackDB;

/*
 * <p>ClassName: LCAskTrackSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCAskTrackSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCAskTrackSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 投保人编码 */
	private String AppntNo;
	/** 投保人 */
	private String AppntName;
	/** 跟踪结果 */
	private String TrackResult;
	/** 跟踪结果说明 */
	private String TrackResultInfo;
	/** 流失原因 */
	private String LoseReason;
	/** 流失原因说明 */
	private String LoseReasonInfo;
	/** 备注 */
	private String Remark;
	/** 回复标记 */
	private String ReplyFlag;
	/** 回复人 */
	private String ReplyOperator;
	/** 回复日期 */
	private Date ReplyDate;
	/** 回复时间 */
	private String ReplyTime;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 录入日期 */
	private Date MakeDate;
	/** 录入时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAskTrackSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpContNo";
		pk[1] = "PrtSeq";

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
		LCAskTrackSchema cloned = (LCAskTrackSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		AppntNo = aAppntNo;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	public String getTrackResult()
	{
		return TrackResult;
	}
	public void setTrackResult(String aTrackResult)
	{
		TrackResult = aTrackResult;
	}
	public String getTrackResultInfo()
	{
		return TrackResultInfo;
	}
	public void setTrackResultInfo(String aTrackResultInfo)
	{
		TrackResultInfo = aTrackResultInfo;
	}
	public String getLoseReason()
	{
		return LoseReason;
	}
	public void setLoseReason(String aLoseReason)
	{
		LoseReason = aLoseReason;
	}
	public String getLoseReasonInfo()
	{
		return LoseReasonInfo;
	}
	public void setLoseReasonInfo(String aLoseReasonInfo)
	{
		LoseReasonInfo = aLoseReasonInfo;
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
	* 0 -- 未回复<p>
	* 1 －－已打印<p>
	* 2 -- 已回复
	*/
	public String getReplyFlag()
	{
		return ReplyFlag;
	}
	public void setReplyFlag(String aReplyFlag)
	{
		ReplyFlag = aReplyFlag;
	}
	public String getReplyOperator()
	{
		return ReplyOperator;
	}
	public void setReplyOperator(String aReplyOperator)
	{
		ReplyOperator = aReplyOperator;
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

	public String getReplyTime()
	{
		return ReplyTime;
	}
	public void setReplyTime(String aReplyTime)
	{
		ReplyTime = aReplyTime;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	* 使用另外一个 LCAskTrackSchema 对象给 Schema 赋值
	* @param: aLCAskTrackSchema LCAskTrackSchema
	**/
	public void setSchema(LCAskTrackSchema aLCAskTrackSchema)
	{
		this.GrpContNo = aLCAskTrackSchema.getGrpContNo();
		this.ProposalContNo = aLCAskTrackSchema.getProposalContNo();
		this.PrtSeq = aLCAskTrackSchema.getPrtSeq();
		this.AppntNo = aLCAskTrackSchema.getAppntNo();
		this.AppntName = aLCAskTrackSchema.getAppntName();
		this.TrackResult = aLCAskTrackSchema.getTrackResult();
		this.TrackResultInfo = aLCAskTrackSchema.getTrackResultInfo();
		this.LoseReason = aLCAskTrackSchema.getLoseReason();
		this.LoseReasonInfo = aLCAskTrackSchema.getLoseReasonInfo();
		this.Remark = aLCAskTrackSchema.getRemark();
		this.ReplyFlag = aLCAskTrackSchema.getReplyFlag();
		this.ReplyOperator = aLCAskTrackSchema.getReplyOperator();
		this.ReplyDate = fDate.getDate( aLCAskTrackSchema.getReplyDate());
		this.ReplyTime = aLCAskTrackSchema.getReplyTime();
		this.AgentCode = aLCAskTrackSchema.getAgentCode();
		this.AgentGroup = aLCAskTrackSchema.getAgentGroup();
		this.Operator = aLCAskTrackSchema.getOperator();
		this.ManageCom = aLCAskTrackSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLCAskTrackSchema.getMakeDate());
		this.MakeTime = aLCAskTrackSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCAskTrackSchema.getModifyDate());
		this.ModifyTime = aLCAskTrackSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("TrackResult") == null )
				this.TrackResult = null;
			else
				this.TrackResult = rs.getString("TrackResult").trim();

			if( rs.getString("TrackResultInfo") == null )
				this.TrackResultInfo = null;
			else
				this.TrackResultInfo = rs.getString("TrackResultInfo").trim();

			if( rs.getString("LoseReason") == null )
				this.LoseReason = null;
			else
				this.LoseReason = rs.getString("LoseReason").trim();

			if( rs.getString("LoseReasonInfo") == null )
				this.LoseReasonInfo = null;
			else
				this.LoseReasonInfo = rs.getString("LoseReasonInfo").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ReplyFlag") == null )
				this.ReplyFlag = null;
			else
				this.ReplyFlag = rs.getString("ReplyFlag").trim();

			if( rs.getString("ReplyOperator") == null )
				this.ReplyOperator = null;
			else
				this.ReplyOperator = rs.getString("ReplyOperator").trim();

			this.ReplyDate = rs.getDate("ReplyDate");
			if( rs.getString("ReplyTime") == null )
				this.ReplyTime = null;
			else
				this.ReplyTime = rs.getString("ReplyTime").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			logger.debug("数据库中的LCAskTrack表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAskTrackSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCAskTrackSchema getSchema()
	{
		LCAskTrackSchema aLCAskTrackSchema = new LCAskTrackSchema();
		aLCAskTrackSchema.setSchema(this);
		return aLCAskTrackSchema;
	}

	public LCAskTrackDB getDB()
	{
		LCAskTrackDB aDBOper = new LCAskTrackDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAskTrack描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrackResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrackResultInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoseReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LoseReasonInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAskTrack>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TrackResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TrackResultInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			LoseReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			LoseReasonInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ReplyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReplyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ReplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ReplyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAskTrackSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("TrackResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrackResult));
		}
		if (FCode.equalsIgnoreCase("TrackResultInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrackResultInfo));
		}
		if (FCode.equalsIgnoreCase("LoseReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoseReason));
		}
		if (FCode.equalsIgnoreCase("LoseReasonInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LoseReasonInfo));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ReplyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyFlag));
		}
		if (FCode.equalsIgnoreCase("ReplyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyOperator));
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
		}
		if (FCode.equalsIgnoreCase("ReplyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyTime));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TrackResult);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TrackResultInfo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(LoseReason);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(LoseReasonInfo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ReplyFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReplyOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ReplyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
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
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equalsIgnoreCase("TrackResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrackResult = FValue.trim();
			}
			else
				TrackResult = null;
		}
		if (FCode.equalsIgnoreCase("TrackResultInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrackResultInfo = FValue.trim();
			}
			else
				TrackResultInfo = null;
		}
		if (FCode.equalsIgnoreCase("LoseReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoseReason = FValue.trim();
			}
			else
				LoseReason = null;
		}
		if (FCode.equalsIgnoreCase("LoseReasonInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LoseReasonInfo = FValue.trim();
			}
			else
				LoseReasonInfo = null;
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
		if (FCode.equalsIgnoreCase("ReplyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyFlag = FValue.trim();
			}
			else
				ReplyFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReplyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyOperator = FValue.trim();
			}
			else
				ReplyOperator = null;
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
		if (FCode.equalsIgnoreCase("ReplyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyTime = FValue.trim();
			}
			else
				ReplyTime = null;
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
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
		LCAskTrackSchema other = (LCAskTrackSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& AppntNo.equals(other.getAppntNo())
			&& AppntName.equals(other.getAppntName())
			&& TrackResult.equals(other.getTrackResult())
			&& TrackResultInfo.equals(other.getTrackResultInfo())
			&& LoseReason.equals(other.getLoseReason())
			&& LoseReasonInfo.equals(other.getLoseReasonInfo())
			&& Remark.equals(other.getRemark())
			&& ReplyFlag.equals(other.getReplyFlag())
			&& ReplyOperator.equals(other.getReplyOperator())
			&& fDate.getString(ReplyDate).equals(other.getReplyDate())
			&& ReplyTime.equals(other.getReplyTime())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 2;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 3;
		}
		if( strFieldName.equals("AppntName") ) {
			return 4;
		}
		if( strFieldName.equals("TrackResult") ) {
			return 5;
		}
		if( strFieldName.equals("TrackResultInfo") ) {
			return 6;
		}
		if( strFieldName.equals("LoseReason") ) {
			return 7;
		}
		if( strFieldName.equals("LoseReasonInfo") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("ReplyFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ReplyOperator") ) {
			return 11;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ReplyTime") ) {
			return 13;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 14;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ProposalContNo";
				break;
			case 2:
				strFieldName = "PrtSeq";
				break;
			case 3:
				strFieldName = "AppntNo";
				break;
			case 4:
				strFieldName = "AppntName";
				break;
			case 5:
				strFieldName = "TrackResult";
				break;
			case 6:
				strFieldName = "TrackResultInfo";
				break;
			case 7:
				strFieldName = "LoseReason";
				break;
			case 8:
				strFieldName = "LoseReasonInfo";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "ReplyFlag";
				break;
			case 11:
				strFieldName = "ReplyOperator";
				break;
			case 12:
				strFieldName = "ReplyDate";
				break;
			case 13:
				strFieldName = "ReplyTime";
				break;
			case 14:
				strFieldName = "AgentCode";
				break;
			case 15:
				strFieldName = "AgentGroup";
				break;
			case 16:
				strFieldName = "Operator";
				break;
			case 17:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrackResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrackResultInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoseReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LoseReasonInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReplyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
