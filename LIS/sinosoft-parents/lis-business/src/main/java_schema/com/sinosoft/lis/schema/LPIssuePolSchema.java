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
import com.sinosoft.lis.db.LPIssuePolDB;

/*
 * <p>ClassName: LPIssuePolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPIssuePolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPIssuePolSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 流水号 */
	private String SerialNo;
	/** 问题件类型 */
	private String IssueType;
	/** 操作位置 */
	private String OperatePos;
	/** 退回对象类型 */
	private String BackObjType;
	/** 退回对象 */
	private String BackObj;
	/** 问题件所在管理机构 */
	private String IsueManageCom;
	/** 问题件内容 */
	private String IssueCont;
	/** 打印次数 */
	private int PrintCount;
	/** 是否打印标志 */
	private String NeedPrint;
	/** 回复人 */
	private String ReplyMan;
	/** 回复结果 */
	private String ReplyResult;
	/** 状态 */
	private String State;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPIssuePolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "ProposalContNo";
		pk[3] = "SerialNo";

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
		LPIssuePolSchema cloned = (LPIssuePolSchema)super.clone();
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
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
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
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 需要新建字典表
	*/
	public String getIssueType()
	{
		return IssueType;
	}
	public void setIssueType(String aIssueType)
	{
		IssueType = aIssueType;
	}
	/**
	* 0 －－ 承保时产生<p>
	* 1 －－ 核保时产生<p>
	* 2 －－ 签单时产生<p>
	* 3 －－ 保全时产生<p>
	* 4 －－ 理赔时产生<p>
	* 5 －－ 复核时产生
	*/
	public String getOperatePos()
	{
		return OperatePos;
	}
	public void setOperatePos(String aOperatePos)
	{
		OperatePos = aOperatePos;
	}
	/**
	* 1 －－ 操作员<p>
	* 2 －－ 业务员<p>
	* 3 －－ 保户<p>
	* 4 －－ 机构<p>
	* <p>
	* 退回到保户的问题件打印核保通知书。<p>
	* 退回到业务员的问题件打印业务员问题通知书。
	*/
	public String getBackObjType()
	{
		return BackObjType;
	}
	public void setBackObjType(String aBackObjType)
	{
		BackObjType = aBackObjType;
	}
	/**
	* 操作员：对应投保单的录入操作员<p>
	* 机构：对应投保单的录入管理机构<p>
	* 保户：对应投保单的代理人。<p>
	* 当该字段对应保户的时候，问题件退回到<p>
	* 投保单所在管理机构字段指定的机构中。
	*/
	public String getBackObj()
	{
		return BackObj;
	}
	public void setBackObj(String aBackObj)
	{
		BackObj = aBackObj;
	}
	/**
	* 该问题件对应保单/投保单的管理机构。
	*/
	public String getIsueManageCom()
	{
		return IsueManageCom;
	}
	public void setIsueManageCom(String aIsueManageCom)
	{
		IsueManageCom = aIsueManageCom;
	}
	public String getIssueCont()
	{
		return IssueCont;
	}
	public void setIssueCont(String aIssueCont)
	{
		IssueCont = aIssueCont;
	}
	/**
	* 每次打印后该次数加1
	*/
	public int getPrintCount()
	{
		return PrintCount;
	}
	public void setPrintCount(int aPrintCount)
	{
		PrintCount = aPrintCount;
	}
	public void setPrintCount(String aPrintCount)
	{
		if (aPrintCount != null && !aPrintCount.equals(""))
		{
			Integer tInteger = new Integer(aPrintCount);
			int i = tInteger.intValue();
			PrintCount = i;
		}
	}

	/**
	* N -- 表示不打印<p>
	* Y － 表示需要打印<p>
	* P － 表示需要打印，而且已经打印。
	*/
	public String getNeedPrint()
	{
		return NeedPrint;
	}
	public void setNeedPrint(String aNeedPrint)
	{
		NeedPrint = aNeedPrint;
	}
	public String getReplyMan()
	{
		return ReplyMan;
	}
	public void setReplyMan(String aReplyMan)
	{
		ReplyMan = aReplyMan;
	}
	public String getReplyResult()
	{
		return ReplyResult;
	}
	public void setReplyResult(String aReplyResult)
	{
		ReplyResult = aReplyResult;
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
	* 使用另外一个 LPIssuePolSchema 对象给 Schema 赋值
	* @param: aLPIssuePolSchema LPIssuePolSchema
	**/
	public void setSchema(LPIssuePolSchema aLPIssuePolSchema)
	{
		this.EdorNo = aLPIssuePolSchema.getEdorNo();
		this.EdorType = aLPIssuePolSchema.getEdorType();
		this.GrpContNo = aLPIssuePolSchema.getGrpContNo();
		this.ContNo = aLPIssuePolSchema.getContNo();
		this.ProposalContNo = aLPIssuePolSchema.getProposalContNo();
		this.PrtSeq = aLPIssuePolSchema.getPrtSeq();
		this.SerialNo = aLPIssuePolSchema.getSerialNo();
		this.IssueType = aLPIssuePolSchema.getIssueType();
		this.OperatePos = aLPIssuePolSchema.getOperatePos();
		this.BackObjType = aLPIssuePolSchema.getBackObjType();
		this.BackObj = aLPIssuePolSchema.getBackObj();
		this.IsueManageCom = aLPIssuePolSchema.getIsueManageCom();
		this.IssueCont = aLPIssuePolSchema.getIssueCont();
		this.PrintCount = aLPIssuePolSchema.getPrintCount();
		this.NeedPrint = aLPIssuePolSchema.getNeedPrint();
		this.ReplyMan = aLPIssuePolSchema.getReplyMan();
		this.ReplyResult = aLPIssuePolSchema.getReplyResult();
		this.State = aLPIssuePolSchema.getState();
		this.Operator = aLPIssuePolSchema.getOperator();
		this.ManageCom = aLPIssuePolSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLPIssuePolSchema.getMakeDate());
		this.MakeTime = aLPIssuePolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPIssuePolSchema.getModifyDate());
		this.ModifyTime = aLPIssuePolSchema.getModifyTime();
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

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("IssueType") == null )
				this.IssueType = null;
			else
				this.IssueType = rs.getString("IssueType").trim();

			if( rs.getString("OperatePos") == null )
				this.OperatePos = null;
			else
				this.OperatePos = rs.getString("OperatePos").trim();

			if( rs.getString("BackObjType") == null )
				this.BackObjType = null;
			else
				this.BackObjType = rs.getString("BackObjType").trim();

			if( rs.getString("BackObj") == null )
				this.BackObj = null;
			else
				this.BackObj = rs.getString("BackObj").trim();

			if( rs.getString("IsueManageCom") == null )
				this.IsueManageCom = null;
			else
				this.IsueManageCom = rs.getString("IsueManageCom").trim();

			if( rs.getString("IssueCont") == null )
				this.IssueCont = null;
			else
				this.IssueCont = rs.getString("IssueCont").trim();

			this.PrintCount = rs.getInt("PrintCount");
			if( rs.getString("NeedPrint") == null )
				this.NeedPrint = null;
			else
				this.NeedPrint = rs.getString("NeedPrint").trim();

			if( rs.getString("ReplyMan") == null )
				this.ReplyMan = null;
			else
				this.ReplyMan = rs.getString("ReplyMan").trim();

			if( rs.getString("ReplyResult") == null )
				this.ReplyResult = null;
			else
				this.ReplyResult = rs.getString("ReplyResult").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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
			logger.debug("数据库中的LPIssuePol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPIssuePolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPIssuePolSchema getSchema()
	{
		LPIssuePolSchema aLPIssuePolSchema = new LPIssuePolSchema();
		aLPIssuePolSchema.setSchema(this);
		return aLPIssuePolSchema;
	}

	public LPIssuePolDB getDB()
	{
		LPIssuePolDB aDBOper = new LPIssuePolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPIssuePol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperatePos)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackObjType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsueManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IssueCont)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrintCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedPrint)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPIssuePol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IssueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OperatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BackObjType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BackObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IsueManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			IssueCont = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			NeedPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReplyMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ReplyResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPIssuePolSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("IssueType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueType));
		}
		if (FCode.equalsIgnoreCase("OperatePos"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperatePos));
		}
		if (FCode.equalsIgnoreCase("BackObjType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackObjType));
		}
		if (FCode.equalsIgnoreCase("BackObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackObj));
		}
		if (FCode.equalsIgnoreCase("IsueManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsueManageCom));
		}
		if (FCode.equalsIgnoreCase("IssueCont"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IssueCont));
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintCount));
		}
		if (FCode.equalsIgnoreCase("NeedPrint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedPrint));
		}
		if (FCode.equalsIgnoreCase("ReplyMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyMan));
		}
		if (FCode.equalsIgnoreCase("ReplyResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyResult));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IssueType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OperatePos);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BackObjType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BackObj);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IsueManageCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(IssueCont);
				break;
			case 13:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(NeedPrint);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReplyMan);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ReplyResult);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("IssueType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueType = FValue.trim();
			}
			else
				IssueType = null;
		}
		if (FCode.equalsIgnoreCase("OperatePos"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperatePos = FValue.trim();
			}
			else
				OperatePos = null;
		}
		if (FCode.equalsIgnoreCase("BackObjType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackObjType = FValue.trim();
			}
			else
				BackObjType = null;
		}
		if (FCode.equalsIgnoreCase("BackObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackObj = FValue.trim();
			}
			else
				BackObj = null;
		}
		if (FCode.equalsIgnoreCase("IsueManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsueManageCom = FValue.trim();
			}
			else
				IsueManageCom = null;
		}
		if (FCode.equalsIgnoreCase("IssueCont"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IssueCont = FValue.trim();
			}
			else
				IssueCont = null;
		}
		if (FCode.equalsIgnoreCase("PrintCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PrintCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("NeedPrint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedPrint = FValue.trim();
			}
			else
				NeedPrint = null;
		}
		if (FCode.equalsIgnoreCase("ReplyMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyMan = FValue.trim();
			}
			else
				ReplyMan = null;
		}
		if (FCode.equalsIgnoreCase("ReplyResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyResult = FValue.trim();
			}
			else
				ReplyResult = null;
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
		LPIssuePolSchema other = (LPIssuePolSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& SerialNo.equals(other.getSerialNo())
			&& IssueType.equals(other.getIssueType())
			&& OperatePos.equals(other.getOperatePos())
			&& BackObjType.equals(other.getBackObjType())
			&& BackObj.equals(other.getBackObj())
			&& IsueManageCom.equals(other.getIsueManageCom())
			&& IssueCont.equals(other.getIssueCont())
			&& PrintCount == other.getPrintCount()
			&& NeedPrint.equals(other.getNeedPrint())
			&& ReplyMan.equals(other.getReplyMan())
			&& ReplyResult.equals(other.getReplyResult())
			&& State.equals(other.getState())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 5;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 6;
		}
		if( strFieldName.equals("IssueType") ) {
			return 7;
		}
		if( strFieldName.equals("OperatePos") ) {
			return 8;
		}
		if( strFieldName.equals("BackObjType") ) {
			return 9;
		}
		if( strFieldName.equals("BackObj") ) {
			return 10;
		}
		if( strFieldName.equals("IsueManageCom") ) {
			return 11;
		}
		if( strFieldName.equals("IssueCont") ) {
			return 12;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 13;
		}
		if( strFieldName.equals("NeedPrint") ) {
			return 14;
		}
		if( strFieldName.equals("ReplyMan") ) {
			return 15;
		}
		if( strFieldName.equals("ReplyResult") ) {
			return 16;
		}
		if( strFieldName.equals("State") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "PrtSeq";
				break;
			case 6:
				strFieldName = "SerialNo";
				break;
			case 7:
				strFieldName = "IssueType";
				break;
			case 8:
				strFieldName = "OperatePos";
				break;
			case 9:
				strFieldName = "BackObjType";
				break;
			case 10:
				strFieldName = "BackObj";
				break;
			case 11:
				strFieldName = "IsueManageCom";
				break;
			case 12:
				strFieldName = "IssueCont";
				break;
			case 13:
				strFieldName = "PrintCount";
				break;
			case 14:
				strFieldName = "NeedPrint";
				break;
			case 15:
				strFieldName = "ReplyMan";
				break;
			case 16:
				strFieldName = "ReplyResult";
				break;
			case 17:
				strFieldName = "State";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperatePos") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackObjType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsueManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IssueCont") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NeedPrint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
