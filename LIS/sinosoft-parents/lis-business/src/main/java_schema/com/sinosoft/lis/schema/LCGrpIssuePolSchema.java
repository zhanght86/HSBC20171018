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
import com.sinosoft.lis.db.LCGrpIssuePolDB;

/*
 * <p>ClassName: LCGrpIssuePolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCGrpIssuePolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpIssuePolSchema.class);
	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体合同投保单号码 */
	private String ProposalGrpContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 流水号 */
	private String SerialNo;
	/** 问题件字段 */
	private String FieldName;
	/** 字段页面位置 */
	private String Location;
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
	/** 问题对象类型 */
	private String QuestionObjType;
	/** 问题对象代码 */
	private String QuestionObj;
	/** 问题对象名称 */
	private String QuestionObjName;
	/** 错误字段 */
	private String ErrField;
	/** 错误字段名称 */
	private String ErrFieldName;
	/** 原填写内容 */
	private String ErrContent;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpIssuePolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ProposalGrpContNo";
		pk[1] = "SerialNo";

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
		LCGrpIssuePolSchema cloned = (LCGrpIssuePolSchema)super.clone();
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
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		if(aProposalGrpContNo!=null && aProposalGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同投保单号码ProposalGrpContNo值"+aProposalGrpContNo+"的长度"+aProposalGrpContNo.length()+"大于最大值20");
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		if(aPrtSeq!=null && aPrtSeq.length()>20)
			throw new IllegalArgumentException("打印流水号PrtSeq值"+aPrtSeq+"的长度"+aPrtSeq.length()+"大于最大值20");
		PrtSeq = aPrtSeq;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getFieldName()
	{
		return FieldName;
	}
	public void setFieldName(String aFieldName)
	{
		if(aFieldName!=null && aFieldName.length()>20)
			throw new IllegalArgumentException("问题件字段FieldName值"+aFieldName+"的长度"+aFieldName.length()+"大于最大值20");
		FieldName = aFieldName;
	}
	public String getLocation()
	{
		return Location;
	}
	public void setLocation(String aLocation)
	{
		if(aLocation!=null && aLocation.length()>1)
			throw new IllegalArgumentException("字段页面位置Location值"+aLocation+"的长度"+aLocation.length()+"大于最大值1");
		Location = aLocation;
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
		if(aIssueType!=null && aIssueType.length()>10)
			throw new IllegalArgumentException("问题件类型IssueType值"+aIssueType+"的长度"+aIssueType.length()+"大于最大值10");
		IssueType = aIssueType;
	}
	/**
	* 1 －－ 核保时产生<p>
	* 2 －－ 签单时产生<p>
	* 3 －－ 保全时产生<p>
	* 4 －－ 理赔时产生
	*/
	public String getOperatePos()
	{
		return OperatePos;
	}
	public void setOperatePos(String aOperatePos)
	{
		if(aOperatePos!=null && aOperatePos.length()>2)
			throw new IllegalArgumentException("操作位置OperatePos值"+aOperatePos+"的长度"+aOperatePos.length()+"大于最大值2");
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
		if(aBackObjType!=null && aBackObjType.length()>1)
			throw new IllegalArgumentException("退回对象类型BackObjType值"+aBackObjType+"的长度"+aBackObjType.length()+"大于最大值1");
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
		if(aBackObj!=null && aBackObj.length()>10)
			throw new IllegalArgumentException("退回对象BackObj值"+aBackObj+"的长度"+aBackObj.length()+"大于最大值10");
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
		if(aIsueManageCom!=null && aIsueManageCom.length()>10)
			throw new IllegalArgumentException("问题件所在管理机构IsueManageCom值"+aIsueManageCom+"的长度"+aIsueManageCom.length()+"大于最大值10");
		IsueManageCom = aIsueManageCom;
	}
	public String getIssueCont()
	{
		return IssueCont;
	}
	public void setIssueCont(String aIssueCont)
	{
		if(aIssueCont!=null && aIssueCont.length()>1000)
			throw new IllegalArgumentException("问题件内容IssueCont值"+aIssueCont+"的长度"+aIssueCont.length()+"大于最大值1000");
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
	* Y － 表示需要打印
	*/
	public String getNeedPrint()
	{
		return NeedPrint;
	}
	public void setNeedPrint(String aNeedPrint)
	{
		if(aNeedPrint!=null && aNeedPrint.length()>1)
			throw new IllegalArgumentException("是否打印标志NeedPrint值"+aNeedPrint+"的长度"+aNeedPrint.length()+"大于最大值1");
		NeedPrint = aNeedPrint;
	}
	public String getReplyMan()
	{
		return ReplyMan;
	}
	public void setReplyMan(String aReplyMan)
	{
		if(aReplyMan!=null && aReplyMan.length()>10)
			throw new IllegalArgumentException("回复人ReplyMan值"+aReplyMan+"的长度"+aReplyMan.length()+"大于最大值10");
		ReplyMan = aReplyMan;
	}
	public String getReplyResult()
	{
		return ReplyResult;
	}
	public void setReplyResult(String aReplyResult)
	{
		if(aReplyResult!=null && aReplyResult.length()>200)
			throw new IllegalArgumentException("回复结果ReplyResult值"+aReplyResult+"的长度"+aReplyResult.length()+"大于最大值200");
		ReplyResult = aReplyResult;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>10)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值10");
		State = aState;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getQuestionObjType()
	{
		return QuestionObjType;
	}
	public void setQuestionObjType(String aQuestionObjType)
	{
		if(aQuestionObjType!=null && aQuestionObjType.length()>4)
			throw new IllegalArgumentException("问题对象类型QuestionObjType值"+aQuestionObjType+"的长度"+aQuestionObjType.length()+"大于最大值4");
		QuestionObjType = aQuestionObjType;
	}
	public String getQuestionObj()
	{
		return QuestionObj;
	}
	public void setQuestionObj(String aQuestionObj)
	{
		if(aQuestionObj!=null && aQuestionObj.length()>30)
			throw new IllegalArgumentException("问题对象代码QuestionObj值"+aQuestionObj+"的长度"+aQuestionObj.length()+"大于最大值30");
		QuestionObj = aQuestionObj;
	}
	public String getQuestionObjName()
	{
		return QuestionObjName;
	}
	public void setQuestionObjName(String aQuestionObjName)
	{
		if(aQuestionObjName!=null && aQuestionObjName.length()>120)
			throw new IllegalArgumentException("问题对象名称QuestionObjName值"+aQuestionObjName+"的长度"+aQuestionObjName.length()+"大于最大值120");
		QuestionObjName = aQuestionObjName;
	}
	public String getErrField()
	{
		return ErrField;
	}
	public void setErrField(String aErrField)
	{
		if(aErrField!=null && aErrField.length()>30)
			throw new IllegalArgumentException("错误字段ErrField值"+aErrField+"的长度"+aErrField.length()+"大于最大值30");
		ErrField = aErrField;
	}
	public String getErrFieldName()
	{
		return ErrFieldName;
	}
	public void setErrFieldName(String aErrFieldName)
	{
		if(aErrFieldName!=null && aErrFieldName.length()>60)
			throw new IllegalArgumentException("错误字段名称ErrFieldName值"+aErrFieldName+"的长度"+aErrFieldName.length()+"大于最大值60");
		ErrFieldName = aErrFieldName;
	}
	public String getErrContent()
	{
		return ErrContent;
	}
	public void setErrContent(String aErrContent)
	{
		if(aErrContent!=null && aErrContent.length()>200)
			throw new IllegalArgumentException("原填写内容ErrContent值"+aErrContent+"的长度"+aErrContent.length()+"大于最大值200");
		ErrContent = aErrContent;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>60)
			throw new IllegalArgumentException("备用字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值60");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>60)
			throw new IllegalArgumentException("备用字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值60");
		StandbyFlag2 = aStandbyFlag2;
	}

	/**
	* 使用另外一个 LCGrpIssuePolSchema 对象给 Schema 赋值
	* @param: aLCGrpIssuePolSchema LCGrpIssuePolSchema
	**/
	public void setSchema(LCGrpIssuePolSchema aLCGrpIssuePolSchema)
	{
		this.GrpContNo = aLCGrpIssuePolSchema.getGrpContNo();
		this.ProposalGrpContNo = aLCGrpIssuePolSchema.getProposalGrpContNo();
		this.PrtSeq = aLCGrpIssuePolSchema.getPrtSeq();
		this.SerialNo = aLCGrpIssuePolSchema.getSerialNo();
		this.FieldName = aLCGrpIssuePolSchema.getFieldName();
		this.Location = aLCGrpIssuePolSchema.getLocation();
		this.IssueType = aLCGrpIssuePolSchema.getIssueType();
		this.OperatePos = aLCGrpIssuePolSchema.getOperatePos();
		this.BackObjType = aLCGrpIssuePolSchema.getBackObjType();
		this.BackObj = aLCGrpIssuePolSchema.getBackObj();
		this.IsueManageCom = aLCGrpIssuePolSchema.getIsueManageCom();
		this.IssueCont = aLCGrpIssuePolSchema.getIssueCont();
		this.PrintCount = aLCGrpIssuePolSchema.getPrintCount();
		this.NeedPrint = aLCGrpIssuePolSchema.getNeedPrint();
		this.ReplyMan = aLCGrpIssuePolSchema.getReplyMan();
		this.ReplyResult = aLCGrpIssuePolSchema.getReplyResult();
		this.State = aLCGrpIssuePolSchema.getState();
		this.Operator = aLCGrpIssuePolSchema.getOperator();
		this.ManageCom = aLCGrpIssuePolSchema.getManageCom();
		this.MakeDate = fDate.getDate( aLCGrpIssuePolSchema.getMakeDate());
		this.MakeTime = aLCGrpIssuePolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpIssuePolSchema.getModifyDate());
		this.ModifyTime = aLCGrpIssuePolSchema.getModifyTime();
		this.QuestionObjType = aLCGrpIssuePolSchema.getQuestionObjType();
		this.QuestionObj = aLCGrpIssuePolSchema.getQuestionObj();
		this.QuestionObjName = aLCGrpIssuePolSchema.getQuestionObjName();
		this.ErrField = aLCGrpIssuePolSchema.getErrField();
		this.ErrFieldName = aLCGrpIssuePolSchema.getErrFieldName();
		this.ErrContent = aLCGrpIssuePolSchema.getErrContent();
		this.StandbyFlag1 = aLCGrpIssuePolSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCGrpIssuePolSchema.getStandbyFlag2();
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

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("FieldName") == null )
				this.FieldName = null;
			else
				this.FieldName = rs.getString("FieldName").trim();

			if( rs.getString("Location") == null )
				this.Location = null;
			else
				this.Location = rs.getString("Location").trim();

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

			if( rs.getString("QuestionObjType") == null )
				this.QuestionObjType = null;
			else
				this.QuestionObjType = rs.getString("QuestionObjType").trim();

			if( rs.getString("QuestionObj") == null )
				this.QuestionObj = null;
			else
				this.QuestionObj = rs.getString("QuestionObj").trim();

			if( rs.getString("QuestionObjName") == null )
				this.QuestionObjName = null;
			else
				this.QuestionObjName = rs.getString("QuestionObjName").trim();

			if( rs.getString("ErrField") == null )
				this.ErrField = null;
			else
				this.ErrField = rs.getString("ErrField").trim();

			if( rs.getString("ErrFieldName") == null )
				this.ErrFieldName = null;
			else
				this.ErrFieldName = rs.getString("ErrFieldName").trim();

			if( rs.getString("ErrContent") == null )
				this.ErrContent = null;
			else
				this.ErrContent = rs.getString("ErrContent").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpIssuePol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpIssuePolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpIssuePolSchema getSchema()
	{
		LCGrpIssuePolSchema aLCGrpIssuePolSchema = new LCGrpIssuePolSchema();
		aLCGrpIssuePolSchema.setSchema(this);
		return aLCGrpIssuePolSchema;
	}

	public LCGrpIssuePolDB getDB()
	{
		LCGrpIssuePolDB aDBOper = new LCGrpIssuePolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpIssuePol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Location)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuestionObjType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuestionObj)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuestionObjName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrField)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrFieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpIssuePol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Location = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IssueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OperatePos = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BackObjType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BackObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IsueManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			IssueCont = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PrintCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			NeedPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ReplyMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReplyResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			QuestionObjType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			QuestionObj = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			QuestionObjName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ErrField = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ErrFieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ErrContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpIssuePolSchema";
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldName));
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Location));
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
		if (FCode.equalsIgnoreCase("QuestionObjType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuestionObjType));
		}
		if (FCode.equalsIgnoreCase("QuestionObj"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuestionObj));
		}
		if (FCode.equalsIgnoreCase("QuestionObjName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuestionObjName));
		}
		if (FCode.equalsIgnoreCase("ErrField"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrField));
		}
		if (FCode.equalsIgnoreCase("ErrFieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrFieldName));
		}
		if (FCode.equalsIgnoreCase("ErrContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrContent));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FieldName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Location);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(IssueType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OperatePos);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BackObjType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BackObj);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IsueManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(IssueCont);
				break;
			case 12:
				strFieldValue = String.valueOf(PrintCount);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(NeedPrint);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ReplyMan);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReplyResult);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(QuestionObjType);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(QuestionObj);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(QuestionObjName);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ErrField);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ErrFieldName);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ErrContent);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
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
		if (FCode.equalsIgnoreCase("FieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldName = FValue.trim();
			}
			else
				FieldName = null;
		}
		if (FCode.equalsIgnoreCase("Location"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Location = FValue.trim();
			}
			else
				Location = null;
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
		if (FCode.equalsIgnoreCase("QuestionObjType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuestionObjType = FValue.trim();
			}
			else
				QuestionObjType = null;
		}
		if (FCode.equalsIgnoreCase("QuestionObj"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuestionObj = FValue.trim();
			}
			else
				QuestionObj = null;
		}
		if (FCode.equalsIgnoreCase("QuestionObjName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuestionObjName = FValue.trim();
			}
			else
				QuestionObjName = null;
		}
		if (FCode.equalsIgnoreCase("ErrField"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrField = FValue.trim();
			}
			else
				ErrField = null;
		}
		if (FCode.equalsIgnoreCase("ErrFieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrFieldName = FValue.trim();
			}
			else
				ErrFieldName = null;
		}
		if (FCode.equalsIgnoreCase("ErrContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrContent = FValue.trim();
			}
			else
				ErrContent = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpIssuePolSchema other = (LCGrpIssuePolSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& SerialNo.equals(other.getSerialNo())
			&& FieldName.equals(other.getFieldName())
			&& Location.equals(other.getLocation())
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
			&& ModifyTime.equals(other.getModifyTime())
			&& QuestionObjType.equals(other.getQuestionObjType())
			&& QuestionObj.equals(other.getQuestionObj())
			&& QuestionObjName.equals(other.getQuestionObjName())
			&& ErrField.equals(other.getErrField())
			&& ErrFieldName.equals(other.getErrFieldName())
			&& ErrContent.equals(other.getErrContent())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2());
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
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("FieldName") ) {
			return 4;
		}
		if( strFieldName.equals("Location") ) {
			return 5;
		}
		if( strFieldName.equals("IssueType") ) {
			return 6;
		}
		if( strFieldName.equals("OperatePos") ) {
			return 7;
		}
		if( strFieldName.equals("BackObjType") ) {
			return 8;
		}
		if( strFieldName.equals("BackObj") ) {
			return 9;
		}
		if( strFieldName.equals("IsueManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("IssueCont") ) {
			return 11;
		}
		if( strFieldName.equals("PrintCount") ) {
			return 12;
		}
		if( strFieldName.equals("NeedPrint") ) {
			return 13;
		}
		if( strFieldName.equals("ReplyMan") ) {
			return 14;
		}
		if( strFieldName.equals("ReplyResult") ) {
			return 15;
		}
		if( strFieldName.equals("State") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
			return 17;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		if( strFieldName.equals("QuestionObjType") ) {
			return 23;
		}
		if( strFieldName.equals("QuestionObj") ) {
			return 24;
		}
		if( strFieldName.equals("QuestionObjName") ) {
			return 25;
		}
		if( strFieldName.equals("ErrField") ) {
			return 26;
		}
		if( strFieldName.equals("ErrFieldName") ) {
			return 27;
		}
		if( strFieldName.equals("ErrContent") ) {
			return 28;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 29;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 30;
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
				strFieldName = "ProposalGrpContNo";
				break;
			case 2:
				strFieldName = "PrtSeq";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "FieldName";
				break;
			case 5:
				strFieldName = "Location";
				break;
			case 6:
				strFieldName = "IssueType";
				break;
			case 7:
				strFieldName = "OperatePos";
				break;
			case 8:
				strFieldName = "BackObjType";
				break;
			case 9:
				strFieldName = "BackObj";
				break;
			case 10:
				strFieldName = "IsueManageCom";
				break;
			case 11:
				strFieldName = "IssueCont";
				break;
			case 12:
				strFieldName = "PrintCount";
				break;
			case 13:
				strFieldName = "NeedPrint";
				break;
			case 14:
				strFieldName = "ReplyMan";
				break;
			case 15:
				strFieldName = "ReplyResult";
				break;
			case 16:
				strFieldName = "State";
				break;
			case 17:
				strFieldName = "Operator";
				break;
			case 18:
				strFieldName = "ManageCom";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "QuestionObjType";
				break;
			case 24:
				strFieldName = "QuestionObj";
				break;
			case 25:
				strFieldName = "QuestionObjName";
				break;
			case 26:
				strFieldName = "ErrField";
				break;
			case 27:
				strFieldName = "ErrFieldName";
				break;
			case 28:
				strFieldName = "ErrContent";
				break;
			case 29:
				strFieldName = "StandbyFlag1";
				break;
			case 30:
				strFieldName = "StandbyFlag2";
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
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Location") ) {
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
		if( strFieldName.equals("QuestionObjType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuestionObj") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuestionObjName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrField") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrFieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
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
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
