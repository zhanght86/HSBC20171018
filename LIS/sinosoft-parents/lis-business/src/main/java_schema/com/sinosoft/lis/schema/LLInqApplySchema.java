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
import com.sinosoft.lis.db.LLInqApplyDB;

/*
 * <p>ClassName: LLInqApplySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLInqApplySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLInqApplySchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 调查序号 */
	private String InqNo;
	/** 调查批次 */
	private String BatNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** Vip客户标志 */
	private String VIPFlag;
	/** 提起阶段 */
	private String InitPhase;
	/** 调查原因 */
	private String InqRCode;
	/** 调查项目 */
	private String InqItem;
	/** 调查描述 */
	private String InqDesc;
	/** 调查机构 */
	private String InqDept;
	/** 申请人 */
	private String ApplyPer;
	/** 申请日期 */
	private Date ApplyDate;
	/** 发起机构 */
	private String InitDept;
	/** 调查状态 */
	private String InqState;
	/** 本地标志 */
	private String LocFlag;
	/** 调查分配人 */
	private String InqPer;
	/** 调查分配日期 */
	private Date InqStartDate;
	/** 调查结束日期 */
	private Date InqEndDate;
	/** 调查结论 */
	private String InqConclusion;
	/** 结论确定人 */
	private String ConPer;
	/** 结论确定时间 */
	private Date ConDate;
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
	/** 备注 */
	private String Remark;
	/** 附件名称 */
	private String AffixName;
	/** 附件地址 */
	private String Affix;
	/** 团体赔案号 */
	private String GrpRgtNo;
	/** 投保人客户号 */
	private String AppntNo;
	/** 调查类型 */
	private String InqType;
	/** 调查分配机构 */
	private String InqMngCom;
	/** 调查流程状态 */
	private String InqFlowState;
	/** 调查关闭原因 */
	private String InqCloseReason;
	/** 调查发起方式 */
	private String InitiationType;
	/** 最后一次修改操作人 */
	private String ModifyOperator;

	public static final int FIELDNUM = 38;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLInqApplySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ClmNo";
		pk[1] = "InqNo";

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
		LLInqApplySchema cloned = (LLInqApplySchema)super.clone();
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
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getInqNo()
	{
		return InqNo;
	}
	public void setInqNo(String aInqNo)
	{
		if(aInqNo!=null && aInqNo.length()>20)
			throw new IllegalArgumentException("调查序号InqNo值"+aInqNo+"的长度"+aInqNo.length()+"大于最大值20");
		InqNo = aInqNo;
	}
	public String getBatNo()
	{
		return BatNo;
	}
	public void setBatNo(String aBatNo)
	{
		if(aBatNo!=null && aBatNo.length()>20)
			throw new IllegalArgumentException("调查批次BatNo值"+aBatNo+"的长度"+aBatNo.length()+"大于最大值20");
		BatNo = aBatNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>40)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值40");
		CustomerName = aCustomerName;
	}
	/**
	* 0或Null普通<p>
	* 1VIP
	*/
	public String getVIPFlag()
	{
		return VIPFlag;
	}
	public void setVIPFlag(String aVIPFlag)
	{
		if(aVIPFlag!=null && aVIPFlag.length()>6)
			throw new IllegalArgumentException("Vip客户标志VIPFlag值"+aVIPFlag+"的长度"+aVIPFlag.length()+"大于最大值6");
		VIPFlag = aVIPFlag;
	}
	/**
	* 01报案<p>
	* 02审核<p>
	* 03呈报
	*/
	public String getInitPhase()
	{
		return InitPhase;
	}
	public void setInitPhase(String aInitPhase)
	{
		if(aInitPhase!=null && aInitPhase.length()>6)
			throw new IllegalArgumentException("提起阶段InitPhase值"+aInitPhase+"的长度"+aInitPhase.length()+"大于最大值6");
		InitPhase = aInitPhase;
	}
	public String getInqRCode()
	{
		return InqRCode;
	}
	public void setInqRCode(String aInqRCode)
	{
		if(aInqRCode!=null && aInqRCode.length()>6)
			throw new IllegalArgumentException("调查原因InqRCode值"+aInqRCode+"的长度"+aInqRCode.length()+"大于最大值6");
		InqRCode = aInqRCode;
	}
	public String getInqItem()
	{
		return InqItem;
	}
	public void setInqItem(String aInqItem)
	{
		if(aInqItem!=null && aInqItem.length()>1000)
			throw new IllegalArgumentException("调查项目InqItem值"+aInqItem+"的长度"+aInqItem.length()+"大于最大值1000");
		InqItem = aInqItem;
	}
	public String getInqDesc()
	{
		return InqDesc;
	}
	public void setInqDesc(String aInqDesc)
	{
		if(aInqDesc!=null && aInqDesc.length()>2000)
			throw new IllegalArgumentException("调查描述InqDesc值"+aInqDesc+"的长度"+aInqDesc.length()+"大于最大值2000");
		InqDesc = aInqDesc;
	}
	public String getInqDept()
	{
		return InqDept;
	}
	public void setInqDept(String aInqDept)
	{
		if(aInqDept!=null && aInqDept.length()>20)
			throw new IllegalArgumentException("调查机构InqDept值"+aInqDept+"的长度"+aInqDept.length()+"大于最大值20");
		InqDept = aInqDept;
	}
	public String getApplyPer()
	{
		return ApplyPer;
	}
	public void setApplyPer(String aApplyPer)
	{
		if(aApplyPer!=null && aApplyPer.length()>20)
			throw new IllegalArgumentException("申请人ApplyPer值"+aApplyPer+"的长度"+aApplyPer.length()+"大于最大值20");
		ApplyPer = aApplyPer;
	}
	public String getApplyDate()
	{
		if( ApplyDate != null )
			return fDate.getString(ApplyDate);
		else
			return null;
	}
	public void setApplyDate(Date aApplyDate)
	{
		ApplyDate = aApplyDate;
	}
	public void setApplyDate(String aApplyDate)
	{
		if (aApplyDate != null && !aApplyDate.equals("") )
		{
			ApplyDate = fDate.getDate( aApplyDate );
		}
		else
			ApplyDate = null;
	}

	public String getInitDept()
	{
		return InitDept;
	}
	public void setInitDept(String aInitDept)
	{
		if(aInitDept!=null && aInitDept.length()>20)
			throw new IllegalArgumentException("发起机构InitDept值"+aInitDept+"的长度"+aInitDept.length()+"大于最大值20");
		InitDept = aInitDept;
	}
	/**
	* 0申请<p>
	* 1完成
	*/
	public String getInqState()
	{
		return InqState;
	}
	public void setInqState(String aInqState)
	{
		if(aInqState!=null && aInqState.length()>6)
			throw new IllegalArgumentException("调查状态InqState值"+aInqState+"的长度"+aInqState.length()+"大于最大值6");
		InqState = aInqState;
	}
	/**
	* 是否是本地调查<p>
	* 0本地<p>
	* 1异地
	*/
	public String getLocFlag()
	{
		return LocFlag;
	}
	public void setLocFlag(String aLocFlag)
	{
		if(aLocFlag!=null && aLocFlag.length()>6)
			throw new IllegalArgumentException("本地标志LocFlag值"+aLocFlag+"的长度"+aLocFlag.length()+"大于最大值6");
		LocFlag = aLocFlag;
	}
	public String getInqPer()
	{
		return InqPer;
	}
	public void setInqPer(String aInqPer)
	{
		if(aInqPer!=null && aInqPer.length()>20)
			throw new IllegalArgumentException("调查分配人InqPer值"+aInqPer+"的长度"+aInqPer.length()+"大于最大值20");
		InqPer = aInqPer;
	}
	public String getInqStartDate()
	{
		if( InqStartDate != null )
			return fDate.getString(InqStartDate);
		else
			return null;
	}
	public void setInqStartDate(Date aInqStartDate)
	{
		InqStartDate = aInqStartDate;
	}
	public void setInqStartDate(String aInqStartDate)
	{
		if (aInqStartDate != null && !aInqStartDate.equals("") )
		{
			InqStartDate = fDate.getDate( aInqStartDate );
		}
		else
			InqStartDate = null;
	}

	public String getInqEndDate()
	{
		if( InqEndDate != null )
			return fDate.getString(InqEndDate);
		else
			return null;
	}
	public void setInqEndDate(Date aInqEndDate)
	{
		InqEndDate = aInqEndDate;
	}
	public void setInqEndDate(String aInqEndDate)
	{
		if (aInqEndDate != null && !aInqEndDate.equals("") )
		{
			InqEndDate = fDate.getDate( aInqEndDate );
		}
		else
			InqEndDate = null;
	}

	public String getInqConclusion()
	{
		return InqConclusion;
	}
	public void setInqConclusion(String aInqConclusion)
	{
		if(aInqConclusion!=null && aInqConclusion.length()>2000)
			throw new IllegalArgumentException("调查结论InqConclusion值"+aInqConclusion+"的长度"+aInqConclusion.length()+"大于最大值2000");
		InqConclusion = aInqConclusion;
	}
	public String getConPer()
	{
		return ConPer;
	}
	public void setConPer(String aConPer)
	{
		if(aConPer!=null && aConPer.length()>20)
			throw new IllegalArgumentException("结论确定人ConPer值"+aConPer+"的长度"+aConPer.length()+"大于最大值20");
		ConPer = aConPer;
	}
	public String getConDate()
	{
		if( ConDate != null )
			return fDate.getString(ConDate);
		else
			return null;
	}
	public void setConDate(Date aConDate)
	{
		ConDate = aConDate;
	}
	public void setConDate(String aConDate)
	{
		if (aConDate != null && !aConDate.equals("") )
		{
			ConDate = fDate.getDate( aConDate );
		}
		else
			ConDate = null;
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>30)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值30");
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public String getAffixName()
	{
		return AffixName;
	}
	public void setAffixName(String aAffixName)
	{
		if(aAffixName!=null && aAffixName.length()>40)
			throw new IllegalArgumentException("附件名称AffixName值"+aAffixName+"的长度"+aAffixName.length()+"大于最大值40");
		AffixName = aAffixName;
	}
	public String getAffix()
	{
		return Affix;
	}
	public void setAffix(String aAffix)
	{
		if(aAffix!=null && aAffix.length()>100)
			throw new IllegalArgumentException("附件地址Affix值"+aAffix+"的长度"+aAffix.length()+"大于最大值100");
		Affix = aAffix;
	}
	public String getGrpRgtNo()
	{
		return GrpRgtNo;
	}
	public void setGrpRgtNo(String aGrpRgtNo)
	{
		if(aGrpRgtNo!=null && aGrpRgtNo.length()>20)
			throw new IllegalArgumentException("团体赔案号GrpRgtNo值"+aGrpRgtNo+"的长度"+aGrpRgtNo.length()+"大于最大值20");
		GrpRgtNo = aGrpRgtNo;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保人客户号AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	/**
	* 0前置调查 1赔案调查
	*/
	public String getInqType()
	{
		return InqType;
	}
	public void setInqType(String aInqType)
	{
		if(aInqType!=null && aInqType.length()>6)
			throw new IllegalArgumentException("调查类型InqType值"+aInqType+"的长度"+aInqType.length()+"大于最大值6");
		InqType = aInqType;
	}
	public String getInqMngCom()
	{
		return InqMngCom;
	}
	public void setInqMngCom(String aInqMngCom)
	{
		if(aInqMngCom!=null && aInqMngCom.length()>20)
			throw new IllegalArgumentException("调查分配机构InqMngCom值"+aInqMngCom+"的长度"+aInqMngCom.length()+"大于最大值20");
		InqMngCom = aInqMngCom;
	}
	/**
	* 00-待发起 <p>
	* 01-已申请待分配<p>
	* 02-已机构分配待任务分配<p>
	* 03-已任务分配待录入<p>
	* 04-已录入待复核<p>
	* 05-复核回退<p>
	* 06-调查关闭<p>
	* 07-调查完毕
	*/
	public String getInqFlowState()
	{
		return InqFlowState;
	}
	public void setInqFlowState(String aInqFlowState)
	{
		if(aInqFlowState!=null && aInqFlowState.length()>6)
			throw new IllegalArgumentException("调查流程状态InqFlowState值"+aInqFlowState+"的长度"+aInqFlowState.length()+"大于最大值6");
		InqFlowState = aInqFlowState;
	}
	public String getInqCloseReason()
	{
		return InqCloseReason;
	}
	public void setInqCloseReason(String aInqCloseReason)
	{
		if(aInqCloseReason!=null && aInqCloseReason.length()>200)
			throw new IllegalArgumentException("调查关闭原因InqCloseReason值"+aInqCloseReason+"的长度"+aInqCloseReason.length()+"大于最大值200");
		InqCloseReason = aInqCloseReason;
	}
	/**
	* 0-手动发起 1-自动发起
	*/
	public String getInitiationType()
	{
		return InitiationType;
	}
	public void setInitiationType(String aInitiationType)
	{
		if(aInitiationType!=null && aInitiationType.length()>6)
			throw new IllegalArgumentException("调查发起方式InitiationType值"+aInitiationType+"的长度"+aInitiationType.length()+"大于最大值6");
		InitiationType = aInitiationType;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作人ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LLInqApplySchema 对象给 Schema 赋值
	* @param: aLLInqApplySchema LLInqApplySchema
	**/
	public void setSchema(LLInqApplySchema aLLInqApplySchema)
	{
		this.ClmNo = aLLInqApplySchema.getClmNo();
		this.InqNo = aLLInqApplySchema.getInqNo();
		this.BatNo = aLLInqApplySchema.getBatNo();
		this.CustomerNo = aLLInqApplySchema.getCustomerNo();
		this.CustomerName = aLLInqApplySchema.getCustomerName();
		this.VIPFlag = aLLInqApplySchema.getVIPFlag();
		this.InitPhase = aLLInqApplySchema.getInitPhase();
		this.InqRCode = aLLInqApplySchema.getInqRCode();
		this.InqItem = aLLInqApplySchema.getInqItem();
		this.InqDesc = aLLInqApplySchema.getInqDesc();
		this.InqDept = aLLInqApplySchema.getInqDept();
		this.ApplyPer = aLLInqApplySchema.getApplyPer();
		this.ApplyDate = fDate.getDate( aLLInqApplySchema.getApplyDate());
		this.InitDept = aLLInqApplySchema.getInitDept();
		this.InqState = aLLInqApplySchema.getInqState();
		this.LocFlag = aLLInqApplySchema.getLocFlag();
		this.InqPer = aLLInqApplySchema.getInqPer();
		this.InqStartDate = fDate.getDate( aLLInqApplySchema.getInqStartDate());
		this.InqEndDate = fDate.getDate( aLLInqApplySchema.getInqEndDate());
		this.InqConclusion = aLLInqApplySchema.getInqConclusion();
		this.ConPer = aLLInqApplySchema.getConPer();
		this.ConDate = fDate.getDate( aLLInqApplySchema.getConDate());
		this.Operator = aLLInqApplySchema.getOperator();
		this.MakeDate = fDate.getDate( aLLInqApplySchema.getMakeDate());
		this.MakeTime = aLLInqApplySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLInqApplySchema.getModifyDate());
		this.ModifyTime = aLLInqApplySchema.getModifyTime();
		this.Remark = aLLInqApplySchema.getRemark();
		this.AffixName = aLLInqApplySchema.getAffixName();
		this.Affix = aLLInqApplySchema.getAffix();
		this.GrpRgtNo = aLLInqApplySchema.getGrpRgtNo();
		this.AppntNo = aLLInqApplySchema.getAppntNo();
		this.InqType = aLLInqApplySchema.getInqType();
		this.InqMngCom = aLLInqApplySchema.getInqMngCom();
		this.InqFlowState = aLLInqApplySchema.getInqFlowState();
		this.InqCloseReason = aLLInqApplySchema.getInqCloseReason();
		this.InitiationType = aLLInqApplySchema.getInitiationType();
		this.ModifyOperator = aLLInqApplySchema.getModifyOperator();
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

			if( rs.getString("InqNo") == null )
				this.InqNo = null;
			else
				this.InqNo = rs.getString("InqNo").trim();

			if( rs.getString("BatNo") == null )
				this.BatNo = null;
			else
				this.BatNo = rs.getString("BatNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("VIPFlag") == null )
				this.VIPFlag = null;
			else
				this.VIPFlag = rs.getString("VIPFlag").trim();

			if( rs.getString("InitPhase") == null )
				this.InitPhase = null;
			else
				this.InitPhase = rs.getString("InitPhase").trim();

			if( rs.getString("InqRCode") == null )
				this.InqRCode = null;
			else
				this.InqRCode = rs.getString("InqRCode").trim();

			if( rs.getString("InqItem") == null )
				this.InqItem = null;
			else
				this.InqItem = rs.getString("InqItem").trim();

			if( rs.getString("InqDesc") == null )
				this.InqDesc = null;
			else
				this.InqDesc = rs.getString("InqDesc").trim();

			if( rs.getString("InqDept") == null )
				this.InqDept = null;
			else
				this.InqDept = rs.getString("InqDept").trim();

			if( rs.getString("ApplyPer") == null )
				this.ApplyPer = null;
			else
				this.ApplyPer = rs.getString("ApplyPer").trim();

			this.ApplyDate = rs.getDate("ApplyDate");
			if( rs.getString("InitDept") == null )
				this.InitDept = null;
			else
				this.InitDept = rs.getString("InitDept").trim();

			if( rs.getString("InqState") == null )
				this.InqState = null;
			else
				this.InqState = rs.getString("InqState").trim();

			if( rs.getString("LocFlag") == null )
				this.LocFlag = null;
			else
				this.LocFlag = rs.getString("LocFlag").trim();

			if( rs.getString("InqPer") == null )
				this.InqPer = null;
			else
				this.InqPer = rs.getString("InqPer").trim();

			this.InqStartDate = rs.getDate("InqStartDate");
			this.InqEndDate = rs.getDate("InqEndDate");
			if( rs.getString("InqConclusion") == null )
				this.InqConclusion = null;
			else
				this.InqConclusion = rs.getString("InqConclusion").trim();

			if( rs.getString("ConPer") == null )
				this.ConPer = null;
			else
				this.ConPer = rs.getString("ConPer").trim();

			this.ConDate = rs.getDate("ConDate");
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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("AffixName") == null )
				this.AffixName = null;
			else
				this.AffixName = rs.getString("AffixName").trim();

			if( rs.getString("Affix") == null )
				this.Affix = null;
			else
				this.Affix = rs.getString("Affix").trim();

			if( rs.getString("GrpRgtNo") == null )
				this.GrpRgtNo = null;
			else
				this.GrpRgtNo = rs.getString("GrpRgtNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("InqType") == null )
				this.InqType = null;
			else
				this.InqType = rs.getString("InqType").trim();

			if( rs.getString("InqMngCom") == null )
				this.InqMngCom = null;
			else
				this.InqMngCom = rs.getString("InqMngCom").trim();

			if( rs.getString("InqFlowState") == null )
				this.InqFlowState = null;
			else
				this.InqFlowState = rs.getString("InqFlowState").trim();

			if( rs.getString("InqCloseReason") == null )
				this.InqCloseReason = null;
			else
				this.InqCloseReason = rs.getString("InqCloseReason").trim();

			if( rs.getString("InitiationType") == null )
				this.InitiationType = null;
			else
				this.InitiationType = rs.getString("InitiationType").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLInqApply表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqApplySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLInqApplySchema getSchema()
	{
		LLInqApplySchema aLLInqApplySchema = new LLInqApplySchema();
		aLLInqApplySchema.setSchema(this);
		return aLLInqApplySchema;
	}

	public LLInqApplyDB getDB()
	{
		LLInqApplyDB aDBOper = new LLInqApplyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqApply描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(VIPFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqRCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitDept)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LocFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InqStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InqEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConPer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Affix)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpRgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqFlowState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqCloseReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InitiationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqApply>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			VIPFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InitPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InqRCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InqItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			InqDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InqDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ApplyPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			InitDept = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			InqState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			LocFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InqPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			InqStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			InqEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			InqConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ConPer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ConDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AffixName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Affix = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			GrpRgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			InqType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			InqMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			InqFlowState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			InqCloseReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			InitiationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqApplySchema";
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
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqNo));
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VIPFlag));
		}
		if (FCode.equalsIgnoreCase("InitPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitPhase));
		}
		if (FCode.equalsIgnoreCase("InqRCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqRCode));
		}
		if (FCode.equalsIgnoreCase("InqItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqItem));
		}
		if (FCode.equalsIgnoreCase("InqDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqDesc));
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqDept));
		}
		if (FCode.equalsIgnoreCase("ApplyPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyPer));
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
		}
		if (FCode.equalsIgnoreCase("InitDept"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitDept));
		}
		if (FCode.equalsIgnoreCase("InqState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqState));
		}
		if (FCode.equalsIgnoreCase("LocFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LocFlag));
		}
		if (FCode.equalsIgnoreCase("InqPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqPer));
		}
		if (FCode.equalsIgnoreCase("InqStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInqStartDate()));
		}
		if (FCode.equalsIgnoreCase("InqEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInqEndDate()));
		}
		if (FCode.equalsIgnoreCase("InqConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqConclusion));
		}
		if (FCode.equalsIgnoreCase("ConPer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConPer));
		}
		if (FCode.equalsIgnoreCase("ConDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConDate()));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixName));
		}
		if (FCode.equalsIgnoreCase("Affix"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Affix));
		}
		if (FCode.equalsIgnoreCase("GrpRgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpRgtNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("InqType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqType));
		}
		if (FCode.equalsIgnoreCase("InqMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqMngCom));
		}
		if (FCode.equalsIgnoreCase("InqFlowState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqFlowState));
		}
		if (FCode.equalsIgnoreCase("InqCloseReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqCloseReason));
		}
		if (FCode.equalsIgnoreCase("InitiationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InitiationType));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(InqNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(VIPFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InitPhase);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InqRCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InqItem);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(InqDesc);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InqDept);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ApplyPer);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InitDept);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(InqState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(LocFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InqPer);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInqStartDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInqEndDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(InqConclusion);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ConPer);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConDate()));
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
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AffixName);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Affix);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(GrpRgtNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(InqType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(InqMngCom);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(InqFlowState);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(InqCloseReason);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(InitiationType);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqNo = FValue.trim();
			}
			else
				InqNo = null;
		}
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatNo = FValue.trim();
			}
			else
				BatNo = null;
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
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("VIPFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VIPFlag = FValue.trim();
			}
			else
				VIPFlag = null;
		}
		if (FCode.equalsIgnoreCase("InitPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitPhase = FValue.trim();
			}
			else
				InitPhase = null;
		}
		if (FCode.equalsIgnoreCase("InqRCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqRCode = FValue.trim();
			}
			else
				InqRCode = null;
		}
		if (FCode.equalsIgnoreCase("InqItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqItem = FValue.trim();
			}
			else
				InqItem = null;
		}
		if (FCode.equalsIgnoreCase("InqDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqDesc = FValue.trim();
			}
			else
				InqDesc = null;
		}
		if (FCode.equalsIgnoreCase("InqDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqDept = FValue.trim();
			}
			else
				InqDept = null;
		}
		if (FCode.equalsIgnoreCase("ApplyPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyPer = FValue.trim();
			}
			else
				ApplyPer = null;
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApplyDate = fDate.getDate( FValue );
			}
			else
				ApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("InitDept"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitDept = FValue.trim();
			}
			else
				InitDept = null;
		}
		if (FCode.equalsIgnoreCase("InqState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqState = FValue.trim();
			}
			else
				InqState = null;
		}
		if (FCode.equalsIgnoreCase("LocFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LocFlag = FValue.trim();
			}
			else
				LocFlag = null;
		}
		if (FCode.equalsIgnoreCase("InqPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqPer = FValue.trim();
			}
			else
				InqPer = null;
		}
		if (FCode.equalsIgnoreCase("InqStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InqStartDate = fDate.getDate( FValue );
			}
			else
				InqStartDate = null;
		}
		if (FCode.equalsIgnoreCase("InqEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InqEndDate = fDate.getDate( FValue );
			}
			else
				InqEndDate = null;
		}
		if (FCode.equalsIgnoreCase("InqConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqConclusion = FValue.trim();
			}
			else
				InqConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConPer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConPer = FValue.trim();
			}
			else
				ConPer = null;
		}
		if (FCode.equalsIgnoreCase("ConDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConDate = fDate.getDate( FValue );
			}
			else
				ConDate = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixName = FValue.trim();
			}
			else
				AffixName = null;
		}
		if (FCode.equalsIgnoreCase("Affix"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Affix = FValue.trim();
			}
			else
				Affix = null;
		}
		if (FCode.equalsIgnoreCase("GrpRgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpRgtNo = FValue.trim();
			}
			else
				GrpRgtNo = null;
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
		if (FCode.equalsIgnoreCase("InqType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqType = FValue.trim();
			}
			else
				InqType = null;
		}
		if (FCode.equalsIgnoreCase("InqMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqMngCom = FValue.trim();
			}
			else
				InqMngCom = null;
		}
		if (FCode.equalsIgnoreCase("InqFlowState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqFlowState = FValue.trim();
			}
			else
				InqFlowState = null;
		}
		if (FCode.equalsIgnoreCase("InqCloseReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqCloseReason = FValue.trim();
			}
			else
				InqCloseReason = null;
		}
		if (FCode.equalsIgnoreCase("InitiationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InitiationType = FValue.trim();
			}
			else
				InitiationType = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLInqApplySchema other = (LLInqApplySchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& InqNo.equals(other.getInqNo())
			&& BatNo.equals(other.getBatNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& VIPFlag.equals(other.getVIPFlag())
			&& InitPhase.equals(other.getInitPhase())
			&& InqRCode.equals(other.getInqRCode())
			&& InqItem.equals(other.getInqItem())
			&& InqDesc.equals(other.getInqDesc())
			&& InqDept.equals(other.getInqDept())
			&& ApplyPer.equals(other.getApplyPer())
			&& fDate.getString(ApplyDate).equals(other.getApplyDate())
			&& InitDept.equals(other.getInitDept())
			&& InqState.equals(other.getInqState())
			&& LocFlag.equals(other.getLocFlag())
			&& InqPer.equals(other.getInqPer())
			&& fDate.getString(InqStartDate).equals(other.getInqStartDate())
			&& fDate.getString(InqEndDate).equals(other.getInqEndDate())
			&& InqConclusion.equals(other.getInqConclusion())
			&& ConPer.equals(other.getConPer())
			&& fDate.getString(ConDate).equals(other.getConDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark())
			&& AffixName.equals(other.getAffixName())
			&& Affix.equals(other.getAffix())
			&& GrpRgtNo.equals(other.getGrpRgtNo())
			&& AppntNo.equals(other.getAppntNo())
			&& InqType.equals(other.getInqType())
			&& InqMngCom.equals(other.getInqMngCom())
			&& InqFlowState.equals(other.getInqFlowState())
			&& InqCloseReason.equals(other.getInqCloseReason())
			&& InitiationType.equals(other.getInitiationType())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("InqNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 4;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return 5;
		}
		if( strFieldName.equals("InitPhase") ) {
			return 6;
		}
		if( strFieldName.equals("InqRCode") ) {
			return 7;
		}
		if( strFieldName.equals("InqItem") ) {
			return 8;
		}
		if( strFieldName.equals("InqDesc") ) {
			return 9;
		}
		if( strFieldName.equals("InqDept") ) {
			return 10;
		}
		if( strFieldName.equals("ApplyPer") ) {
			return 11;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return 12;
		}
		if( strFieldName.equals("InitDept") ) {
			return 13;
		}
		if( strFieldName.equals("InqState") ) {
			return 14;
		}
		if( strFieldName.equals("LocFlag") ) {
			return 15;
		}
		if( strFieldName.equals("InqPer") ) {
			return 16;
		}
		if( strFieldName.equals("InqStartDate") ) {
			return 17;
		}
		if( strFieldName.equals("InqEndDate") ) {
			return 18;
		}
		if( strFieldName.equals("InqConclusion") ) {
			return 19;
		}
		if( strFieldName.equals("ConPer") ) {
			return 20;
		}
		if( strFieldName.equals("ConDate") ) {
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
		if( strFieldName.equals("Remark") ) {
			return 27;
		}
		if( strFieldName.equals("AffixName") ) {
			return 28;
		}
		if( strFieldName.equals("Affix") ) {
			return 29;
		}
		if( strFieldName.equals("GrpRgtNo") ) {
			return 30;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 31;
		}
		if( strFieldName.equals("InqType") ) {
			return 32;
		}
		if( strFieldName.equals("InqMngCom") ) {
			return 33;
		}
		if( strFieldName.equals("InqFlowState") ) {
			return 34;
		}
		if( strFieldName.equals("InqCloseReason") ) {
			return 35;
		}
		if( strFieldName.equals("InitiationType") ) {
			return 36;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 37;
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
				strFieldName = "InqNo";
				break;
			case 2:
				strFieldName = "BatNo";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "CustomerName";
				break;
			case 5:
				strFieldName = "VIPFlag";
				break;
			case 6:
				strFieldName = "InitPhase";
				break;
			case 7:
				strFieldName = "InqRCode";
				break;
			case 8:
				strFieldName = "InqItem";
				break;
			case 9:
				strFieldName = "InqDesc";
				break;
			case 10:
				strFieldName = "InqDept";
				break;
			case 11:
				strFieldName = "ApplyPer";
				break;
			case 12:
				strFieldName = "ApplyDate";
				break;
			case 13:
				strFieldName = "InitDept";
				break;
			case 14:
				strFieldName = "InqState";
				break;
			case 15:
				strFieldName = "LocFlag";
				break;
			case 16:
				strFieldName = "InqPer";
				break;
			case 17:
				strFieldName = "InqStartDate";
				break;
			case 18:
				strFieldName = "InqEndDate";
				break;
			case 19:
				strFieldName = "InqConclusion";
				break;
			case 20:
				strFieldName = "ConPer";
				break;
			case 21:
				strFieldName = "ConDate";
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
				strFieldName = "Remark";
				break;
			case 28:
				strFieldName = "AffixName";
				break;
			case 29:
				strFieldName = "Affix";
				break;
			case 30:
				strFieldName = "GrpRgtNo";
				break;
			case 31:
				strFieldName = "AppntNo";
				break;
			case 32:
				strFieldName = "InqType";
				break;
			case 33:
				strFieldName = "InqMngCom";
				break;
			case 34:
				strFieldName = "InqFlowState";
				break;
			case 35:
				strFieldName = "InqCloseReason";
				break;
			case 36:
				strFieldName = "InitiationType";
				break;
			case 37:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("InqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VIPFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqRCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InitDept") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LocFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InqEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InqConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConPer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConDate") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Affix") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpRgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqFlowState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqCloseReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InitiationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
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
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
