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
import com.sinosoft.lis.db.LDQuestionDB;

/*
 * <p>ClassName: LDQuestionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 配置管理
 */
public class LDQuestionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDQuestionSchema.class);
	// @Field
	/** 问题件id */
	private String QuesID;
	/** 业务类型 */
	private String OtherNoType;
	/** 业务号码 */
	private String OtherNo;
	/** 次业务号码 */
	private String SubOtherNo;
	/** 问题件类型 */
	private String QuesType;
	/** 问题件细类 */
	private String DetailType;
	/** 发送节点 */
	private String SendNode;
	/** 发送人 */
	private String Sender;
	/** 发送内容 */
	private String SendContent;
	/** 发送日期 */
	private Date SendDate;
	/** 发送时间 */
	private String SendTime;
	/** 回复节点 */
	private String ReplyNode;
	/** 回复人 */
	private String Replier;
	/** 回复内容 */
	private String ReplyContent;
	/** 回复日期 */
	private Date ReplyDate;
	/** 回复时间 */
	private String ReplyTime;
	/** 附件id */
	private String AttachID;
	/** 备注 */
	private String Remark;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDQuestionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "QuesID";

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
		LDQuestionSchema cloned = (LDQuestionSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuesID()
	{
		return QuesID;
	}
	public void setQuesID(String aQuesID)
	{
		if(aQuesID!=null && aQuesID.length()>10)
			throw new IllegalArgumentException("问题件idQuesID值"+aQuesID+"的长度"+aQuesID.length()+"大于最大值10");
		QuesID = aQuesID;
	}
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>5)
			throw new IllegalArgumentException("业务类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值5");
		OtherNoType = aOtherNoType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("业务号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getSubOtherNo()
	{
		return SubOtherNo;
	}
	public void setSubOtherNo(String aSubOtherNo)
	{
		if(aSubOtherNo!=null && aSubOtherNo.length()>20)
			throw new IllegalArgumentException("次业务号码SubOtherNo值"+aSubOtherNo+"的长度"+aSubOtherNo.length()+"大于最大值20");
		SubOtherNo = aSubOtherNo;
	}
	public String getQuesType()
	{
		return QuesType;
	}
	public void setQuesType(String aQuesType)
	{
		if(aQuesType!=null && aQuesType.length()>2)
			throw new IllegalArgumentException("问题件类型QuesType值"+aQuesType+"的长度"+aQuesType.length()+"大于最大值2");
		QuesType = aQuesType;
	}
	public String getDetailType()
	{
		return DetailType;
	}
	public void setDetailType(String aDetailType)
	{
		if(aDetailType!=null && aDetailType.length()>2)
			throw new IllegalArgumentException("问题件细类DetailType值"+aDetailType+"的长度"+aDetailType.length()+"大于最大值2");
		DetailType = aDetailType;
	}
	public String getSendNode()
	{
		return SendNode;
	}
	public void setSendNode(String aSendNode)
	{
		if(aSendNode!=null && aSendNode.length()>10)
			throw new IllegalArgumentException("发送节点SendNode值"+aSendNode+"的长度"+aSendNode.length()+"大于最大值10");
		SendNode = aSendNode;
	}
	public String getSender()
	{
		return Sender;
	}
	public void setSender(String aSender)
	{
		if(aSender!=null && aSender.length()>30)
			throw new IllegalArgumentException("发送人Sender值"+aSender+"的长度"+aSender.length()+"大于最大值30");
		Sender = aSender;
	}
	public String getSendContent()
	{
		return SendContent;
	}
	public void setSendContent(String aSendContent)
	{
		if(aSendContent!=null && aSendContent.length()>3000)
			throw new IllegalArgumentException("发送内容SendContent值"+aSendContent+"的长度"+aSendContent.length()+"大于最大值3000");
		SendContent = aSendContent;
	}
	public String getSendDate()
	{
		if( SendDate != null )
			return fDate.getString(SendDate);
		else
			return null;
	}
	public void setSendDate(Date aSendDate)
	{
		SendDate = aSendDate;
	}
	public void setSendDate(String aSendDate)
	{
		if (aSendDate != null && !aSendDate.equals("") )
		{
			SendDate = fDate.getDate( aSendDate );
		}
		else
			SendDate = null;
	}

	public String getSendTime()
	{
		return SendTime;
	}
	public void setSendTime(String aSendTime)
	{
		if(aSendTime!=null && aSendTime.length()>8)
			throw new IllegalArgumentException("发送时间SendTime值"+aSendTime+"的长度"+aSendTime.length()+"大于最大值8");
		SendTime = aSendTime;
	}
	public String getReplyNode()
	{
		return ReplyNode;
	}
	public void setReplyNode(String aReplyNode)
	{
		if(aReplyNode!=null && aReplyNode.length()>10)
			throw new IllegalArgumentException("回复节点ReplyNode值"+aReplyNode+"的长度"+aReplyNode.length()+"大于最大值10");
		ReplyNode = aReplyNode;
	}
	public String getReplier()
	{
		return Replier;
	}
	public void setReplier(String aReplier)
	{
		if(aReplier!=null && aReplier.length()>30)
			throw new IllegalArgumentException("回复人Replier值"+aReplier+"的长度"+aReplier.length()+"大于最大值30");
		Replier = aReplier;
	}
	public String getReplyContent()
	{
		return ReplyContent;
	}
	public void setReplyContent(String aReplyContent)
	{
		if(aReplyContent!=null && aReplyContent.length()>3000)
			throw new IllegalArgumentException("回复内容ReplyContent值"+aReplyContent+"的长度"+aReplyContent.length()+"大于最大值3000");
		ReplyContent = aReplyContent;
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
		if(aReplyTime!=null && aReplyTime.length()>8)
			throw new IllegalArgumentException("回复时间ReplyTime值"+aReplyTime+"的长度"+aReplyTime.length()+"大于最大值8");
		ReplyTime = aReplyTime;
	}
	public String getAttachID()
	{
		return AttachID;
	}
	public void setAttachID(String aAttachID)
	{
		if(aAttachID!=null && aAttachID.length()>10)
			throw new IllegalArgumentException("附件idAttachID值"+aAttachID+"的长度"+aAttachID.length()+"大于最大值10");
		AttachID = aAttachID;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>3000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值3000");
		Remark = aRemark;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDQuestionSchema 对象给 Schema 赋值
	* @param: aLDQuestionSchema LDQuestionSchema
	**/
	public void setSchema(LDQuestionSchema aLDQuestionSchema)
	{
		this.QuesID = aLDQuestionSchema.getQuesID();
		this.OtherNoType = aLDQuestionSchema.getOtherNoType();
		this.OtherNo = aLDQuestionSchema.getOtherNo();
		this.SubOtherNo = aLDQuestionSchema.getSubOtherNo();
		this.QuesType = aLDQuestionSchema.getQuesType();
		this.DetailType = aLDQuestionSchema.getDetailType();
		this.SendNode = aLDQuestionSchema.getSendNode();
		this.Sender = aLDQuestionSchema.getSender();
		this.SendContent = aLDQuestionSchema.getSendContent();
		this.SendDate = fDate.getDate( aLDQuestionSchema.getSendDate());
		this.SendTime = aLDQuestionSchema.getSendTime();
		this.ReplyNode = aLDQuestionSchema.getReplyNode();
		this.Replier = aLDQuestionSchema.getReplier();
		this.ReplyContent = aLDQuestionSchema.getReplyContent();
		this.ReplyDate = fDate.getDate( aLDQuestionSchema.getReplyDate());
		this.ReplyTime = aLDQuestionSchema.getReplyTime();
		this.AttachID = aLDQuestionSchema.getAttachID();
		this.Remark = aLDQuestionSchema.getRemark();
		this.State = aLDQuestionSchema.getState();
		this.ManageCom = aLDQuestionSchema.getManageCom();
		this.ComCode = aLDQuestionSchema.getComCode();
		this.MakeOperator = aLDQuestionSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDQuestionSchema.getMakeDate());
		this.MakeTime = aLDQuestionSchema.getMakeTime();
		this.ModifyOperator = aLDQuestionSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDQuestionSchema.getModifyDate());
		this.ModifyTime = aLDQuestionSchema.getModifyTime();
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
			if( rs.getString("QuesID") == null )
				this.QuesID = null;
			else
				this.QuesID = rs.getString("QuesID").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("SubOtherNo") == null )
				this.SubOtherNo = null;
			else
				this.SubOtherNo = rs.getString("SubOtherNo").trim();

			if( rs.getString("QuesType") == null )
				this.QuesType = null;
			else
				this.QuesType = rs.getString("QuesType").trim();

			if( rs.getString("DetailType") == null )
				this.DetailType = null;
			else
				this.DetailType = rs.getString("DetailType").trim();

			if( rs.getString("SendNode") == null )
				this.SendNode = null;
			else
				this.SendNode = rs.getString("SendNode").trim();

			if( rs.getString("Sender") == null )
				this.Sender = null;
			else
				this.Sender = rs.getString("Sender").trim();

			if( rs.getString("SendContent") == null )
				this.SendContent = null;
			else
				this.SendContent = rs.getString("SendContent").trim();

			this.SendDate = rs.getDate("SendDate");
			if( rs.getString("SendTime") == null )
				this.SendTime = null;
			else
				this.SendTime = rs.getString("SendTime").trim();

			if( rs.getString("ReplyNode") == null )
				this.ReplyNode = null;
			else
				this.ReplyNode = rs.getString("ReplyNode").trim();

			if( rs.getString("Replier") == null )
				this.Replier = null;
			else
				this.Replier = rs.getString("Replier").trim();

			if( rs.getString("ReplyContent") == null )
				this.ReplyContent = null;
			else
				this.ReplyContent = rs.getString("ReplyContent").trim();

			this.ReplyDate = rs.getDate("ReplyDate");
			if( rs.getString("ReplyTime") == null )
				this.ReplyTime = null;
			else
				this.ReplyTime = rs.getString("ReplyTime").trim();

			if( rs.getString("AttachID") == null )
				this.AttachID = null;
			else
				this.AttachID = rs.getString("AttachID").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDQuestion表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDQuestionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDQuestionSchema getSchema()
	{
		LDQuestionSchema aLDQuestionSchema = new LDQuestionSchema();
		aLDQuestionSchema.setSchema(this);
		return aLDQuestionSchema;
	}

	public LDQuestionDB getDB()
	{
		LDQuestionDB aDBOper = new LDQuestionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDQuestion描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuesID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubOtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuesType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DetailType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sender)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SendDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Replier)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AttachID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDQuestion>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuesID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubOtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			QuesType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DetailType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SendNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Sender = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SendContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SendDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			SendTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ReplyNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Replier = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ReplyContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ReplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ReplyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AttachID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDQuestionSchema";
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
		if (FCode.equalsIgnoreCase("QuesID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuesID));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("SubOtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubOtherNo));
		}
		if (FCode.equalsIgnoreCase("QuesType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuesType));
		}
		if (FCode.equalsIgnoreCase("DetailType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailType));
		}
		if (FCode.equalsIgnoreCase("SendNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendNode));
		}
		if (FCode.equalsIgnoreCase("Sender"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sender));
		}
		if (FCode.equalsIgnoreCase("SendContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendContent));
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
		}
		if (FCode.equalsIgnoreCase("SendTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendTime));
		}
		if (FCode.equalsIgnoreCase("ReplyNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyNode));
		}
		if (FCode.equalsIgnoreCase("Replier"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Replier));
		}
		if (FCode.equalsIgnoreCase("ReplyContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyContent));
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
		}
		if (FCode.equalsIgnoreCase("ReplyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyTime));
		}
		if (FCode.equalsIgnoreCase("AttachID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AttachID));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(QuesID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubOtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(QuesType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DetailType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SendNode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Sender);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SendContent);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSendDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SendTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ReplyNode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Replier);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ReplyContent);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReplyTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AttachID);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
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

		if (FCode.equalsIgnoreCase("QuesID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuesID = FValue.trim();
			}
			else
				QuesID = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
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
		if (FCode.equalsIgnoreCase("SubOtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubOtherNo = FValue.trim();
			}
			else
				SubOtherNo = null;
		}
		if (FCode.equalsIgnoreCase("QuesType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuesType = FValue.trim();
			}
			else
				QuesType = null;
		}
		if (FCode.equalsIgnoreCase("DetailType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailType = FValue.trim();
			}
			else
				DetailType = null;
		}
		if (FCode.equalsIgnoreCase("SendNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendNode = FValue.trim();
			}
			else
				SendNode = null;
		}
		if (FCode.equalsIgnoreCase("Sender"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sender = FValue.trim();
			}
			else
				Sender = null;
		}
		if (FCode.equalsIgnoreCase("SendContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendContent = FValue.trim();
			}
			else
				SendContent = null;
		}
		if (FCode.equalsIgnoreCase("SendDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SendDate = fDate.getDate( FValue );
			}
			else
				SendDate = null;
		}
		if (FCode.equalsIgnoreCase("SendTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendTime = FValue.trim();
			}
			else
				SendTime = null;
		}
		if (FCode.equalsIgnoreCase("ReplyNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyNode = FValue.trim();
			}
			else
				ReplyNode = null;
		}
		if (FCode.equalsIgnoreCase("Replier"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Replier = FValue.trim();
			}
			else
				Replier = null;
		}
		if (FCode.equalsIgnoreCase("ReplyContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyContent = FValue.trim();
			}
			else
				ReplyContent = null;
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
		if (FCode.equalsIgnoreCase("AttachID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AttachID = FValue.trim();
			}
			else
				AttachID = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LDQuestionSchema other = (LDQuestionSchema)otherObject;
		return
			QuesID.equals(other.getQuesID())
			&& OtherNoType.equals(other.getOtherNoType())
			&& OtherNo.equals(other.getOtherNo())
			&& SubOtherNo.equals(other.getSubOtherNo())
			&& QuesType.equals(other.getQuesType())
			&& DetailType.equals(other.getDetailType())
			&& SendNode.equals(other.getSendNode())
			&& Sender.equals(other.getSender())
			&& SendContent.equals(other.getSendContent())
			&& fDate.getString(SendDate).equals(other.getSendDate())
			&& SendTime.equals(other.getSendTime())
			&& ReplyNode.equals(other.getReplyNode())
			&& Replier.equals(other.getReplier())
			&& ReplyContent.equals(other.getReplyContent())
			&& fDate.getString(ReplyDate).equals(other.getReplyDate())
			&& ReplyTime.equals(other.getReplyTime())
			&& AttachID.equals(other.getAttachID())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("QuesID") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 2;
		}
		if( strFieldName.equals("SubOtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("QuesType") ) {
			return 4;
		}
		if( strFieldName.equals("DetailType") ) {
			return 5;
		}
		if( strFieldName.equals("SendNode") ) {
			return 6;
		}
		if( strFieldName.equals("Sender") ) {
			return 7;
		}
		if( strFieldName.equals("SendContent") ) {
			return 8;
		}
		if( strFieldName.equals("SendDate") ) {
			return 9;
		}
		if( strFieldName.equals("SendTime") ) {
			return 10;
		}
		if( strFieldName.equals("ReplyNode") ) {
			return 11;
		}
		if( strFieldName.equals("Replier") ) {
			return 12;
		}
		if( strFieldName.equals("ReplyContent") ) {
			return 13;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ReplyTime") ) {
			return 15;
		}
		if( strFieldName.equals("AttachID") ) {
			return 16;
		}
		if( strFieldName.equals("Remark") ) {
			return 17;
		}
		if( strFieldName.equals("State") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("ComCode") ) {
			return 20;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
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
				strFieldName = "QuesID";
				break;
			case 1:
				strFieldName = "OtherNoType";
				break;
			case 2:
				strFieldName = "OtherNo";
				break;
			case 3:
				strFieldName = "SubOtherNo";
				break;
			case 4:
				strFieldName = "QuesType";
				break;
			case 5:
				strFieldName = "DetailType";
				break;
			case 6:
				strFieldName = "SendNode";
				break;
			case 7:
				strFieldName = "Sender";
				break;
			case 8:
				strFieldName = "SendContent";
				break;
			case 9:
				strFieldName = "SendDate";
				break;
			case 10:
				strFieldName = "SendTime";
				break;
			case 11:
				strFieldName = "ReplyNode";
				break;
			case 12:
				strFieldName = "Replier";
				break;
			case 13:
				strFieldName = "ReplyContent";
				break;
			case 14:
				strFieldName = "ReplyDate";
				break;
			case 15:
				strFieldName = "ReplyTime";
				break;
			case 16:
				strFieldName = "AttachID";
				break;
			case 17:
				strFieldName = "Remark";
				break;
			case 18:
				strFieldName = "State";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "ComCode";
				break;
			case 21:
				strFieldName = "MakeOperator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyOperator";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
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
		if( strFieldName.equals("QuesID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubOtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuesType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sender") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SendTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Replier") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReplyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AttachID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
