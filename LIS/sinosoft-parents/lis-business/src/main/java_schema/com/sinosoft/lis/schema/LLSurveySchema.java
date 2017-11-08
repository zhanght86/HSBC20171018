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
import com.sinosoft.lis.db.LLSurveyDB;

/*
 * <p>ClassName: LLSurveySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统lis5模型
 */
public class LLSurveySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLSurveySchema.class);
	// @Field
	/** 案件号 */
	private String ClmCaseNo;
	/** 案件类型 */
	private String Type;
	/** 序号/调查次数 */
	private String SerialNo;
	/** 立案号 */
	private String RgtNo;
	/** 主案件号 */
	private String MainClmCaseNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 调查人 */
	private String Surveyor;
	/** 调查人姓名 */
	private String SurveyorName;
	/** 调查地点 */
	private String SurveySite;
	/** 调查内容 */
	private String Content;
	/** 调查结论 */
	private String result;
	/** 调查开始日期 */
	private Date SurveyStartDate;
	/** 调查结束日期 */
	private Date SurveyEndDate;
	/** 管理机构 */
	private String MngCom;
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
	/** 提起阶段 */
	private String StartPhase;
	/** 调查报告状态 */
	private String SurveyFlag;
	/** 提起人 */
	private String StartMan;
	/** 接收人 */
	private String ReceiveMan;
	/** 分报案号(事件号) */
	private String SubRptNo;
	/** 调查回复人 */
	private String SurveyOperator;
	/** 对应号码 */
	private String OtherNo;
	/** 调查类型 */
	private String SurveyType;
	/** 调查号 */
	private String SurveyNo;
	/** 调查原因内容 */
	private String SurveyRDesc;
	/** 调查原因 */
	private String SurveyRCode;
	/** 审核时间 */
	private Date ConfDate;
	/** 审核意见 */
	private String ConfNote;
	/** 其他对应号码类型 */
	private String OtherNoType;
	/** 调查类别 */
	private String SurveyClass;
	/** 审核人 */
	private String Confer;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLSurveySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ClmCaseNo";
		pk[1] = "Type";
		pk[2] = "SerialNo";

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
		LLSurveySchema cloned = (LLSurveySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 包括：分案号、报案分案号
	*/
	public String getClmCaseNo()
	{
		return ClmCaseNo;
	}
	public void setClmCaseNo(String aClmCaseNo)
	{
		if(aClmCaseNo!=null && aClmCaseNo.length()>20)
			throw new IllegalArgumentException("案件号ClmCaseNo值"+aClmCaseNo+"的长度"+aClmCaseNo.length()+"大于最大值20");
		ClmCaseNo = aClmCaseNo;
	}
	/**
	* 包括：<p>
	* 1--分案、<p>
	* 0--报案分案
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>1)
			throw new IllegalArgumentException("案件类型Type值"+aType+"的长度"+aType.length()+"大于最大值1");
		Type = aType;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("序号/调查次数SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	/**
	* 包括：分案号、报案分案号
	*/
	public String getMainClmCaseNo()
	{
		return MainClmCaseNo;
	}
	public void setMainClmCaseNo(String aMainClmCaseNo)
	{
		if(aMainClmCaseNo!=null && aMainClmCaseNo.length()>20)
			throw new IllegalArgumentException("主案件号MainClmCaseNo值"+aMainClmCaseNo+"的长度"+aMainClmCaseNo.length()+"大于最大值20");
		MainClmCaseNo = aMainClmCaseNo;
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
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	public String getSurveyor()
	{
		return Surveyor;
	}
	public void setSurveyor(String aSurveyor)
	{
		if(aSurveyor!=null && aSurveyor.length()>20)
			throw new IllegalArgumentException("调查人Surveyor值"+aSurveyor+"的长度"+aSurveyor.length()+"大于最大值20");
		Surveyor = aSurveyor;
	}
	public String getSurveyorName()
	{
		return SurveyorName;
	}
	public void setSurveyorName(String aSurveyorName)
	{
		if(aSurveyorName!=null && aSurveyorName.length()>20)
			throw new IllegalArgumentException("调查人姓名SurveyorName值"+aSurveyorName+"的长度"+aSurveyorName.length()+"大于最大值20");
		SurveyorName = aSurveyorName;
	}
	public String getSurveySite()
	{
		return SurveySite;
	}
	public void setSurveySite(String aSurveySite)
	{
		if(aSurveySite!=null && aSurveySite.length()>60)
			throw new IllegalArgumentException("调查地点SurveySite值"+aSurveySite+"的长度"+aSurveySite.length()+"大于最大值60");
		SurveySite = aSurveySite;
	}
	public String getContent()
	{
		return Content;
	}
	public void setContent(String aContent)
	{
		if(aContent!=null && aContent.length()>1000)
			throw new IllegalArgumentException("调查内容Content值"+aContent+"的长度"+aContent.length()+"大于最大值1000");
		Content = aContent;
	}
	public String getresult()
	{
		return result;
	}
	public void setresult(String aresult)
	{
		if(aresult!=null && aresult.length()>2000)
			throw new IllegalArgumentException("调查结论result值"+aresult+"的长度"+aresult.length()+"大于最大值2000");
		result = aresult;
	}
	public String getSurveyStartDate()
	{
		if( SurveyStartDate != null )
			return fDate.getString(SurveyStartDate);
		else
			return null;
	}
	public void setSurveyStartDate(Date aSurveyStartDate)
	{
		SurveyStartDate = aSurveyStartDate;
	}
	public void setSurveyStartDate(String aSurveyStartDate)
	{
		if (aSurveyStartDate != null && !aSurveyStartDate.equals("") )
		{
			SurveyStartDate = fDate.getDate( aSurveyStartDate );
		}
		else
			SurveyStartDate = null;
	}

	public String getSurveyEndDate()
	{
		if( SurveyEndDate != null )
			return fDate.getString(SurveyEndDate);
		else
			return null;
	}
	public void setSurveyEndDate(Date aSurveyEndDate)
	{
		SurveyEndDate = aSurveyEndDate;
	}
	public void setSurveyEndDate(String aSurveyEndDate)
	{
		if (aSurveyEndDate != null && !aSurveyEndDate.equals("") )
		{
			SurveyEndDate = fDate.getDate( aSurveyEndDate );
		}
		else
			SurveyEndDate = null;
	}

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
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
	/**
	* 0 －－ 立案阶段<p>
	* 1 －－ 审核阶段
	*/
	public String getStartPhase()
	{
		return StartPhase;
	}
	public void setStartPhase(String aStartPhase)
	{
		if(aStartPhase!=null && aStartPhase.length()>1)
			throw new IllegalArgumentException("提起阶段StartPhase值"+aStartPhase+"的长度"+aStartPhase.length()+"大于最大值1");
		StartPhase = aStartPhase;
	}
	/**
	* 0 －－ 已提起未接受<p>
	* 1 －－ 已接受未调查<p>
	* 2 －－ 已调查（调查完成）
	*/
	public String getSurveyFlag()
	{
		return SurveyFlag;
	}
	public void setSurveyFlag(String aSurveyFlag)
	{
		if(aSurveyFlag!=null && aSurveyFlag.length()>1)
			throw new IllegalArgumentException("调查报告状态SurveyFlag值"+aSurveyFlag+"的长度"+aSurveyFlag.length()+"大于最大值1");
		SurveyFlag = aSurveyFlag;
	}
	public String getStartMan()
	{
		return StartMan;
	}
	public void setStartMan(String aStartMan)
	{
		if(aStartMan!=null && aStartMan.length()>10)
			throw new IllegalArgumentException("提起人StartMan值"+aStartMan+"的长度"+aStartMan.length()+"大于最大值10");
		StartMan = aStartMan;
	}
	public String getReceiveMan()
	{
		return ReceiveMan;
	}
	public void setReceiveMan(String aReceiveMan)
	{
		if(aReceiveMan!=null && aReceiveMan.length()>10)
			throw new IllegalArgumentException("接收人ReceiveMan值"+aReceiveMan+"的长度"+aReceiveMan.length()+"大于最大值10");
		ReceiveMan = aReceiveMan;
	}
	public String getSubRptNo()
	{
		return SubRptNo;
	}
	public void setSubRptNo(String aSubRptNo)
	{
		if(aSubRptNo!=null && aSubRptNo.length()>20)
			throw new IllegalArgumentException("分报案号(事件号)SubRptNo值"+aSubRptNo+"的长度"+aSubRptNo.length()+"大于最大值20");
		SubRptNo = aSubRptNo;
	}
	public String getSurveyOperator()
	{
		return SurveyOperator;
	}
	public void setSurveyOperator(String aSurveyOperator)
	{
		if(aSurveyOperator!=null && aSurveyOperator.length()>10)
			throw new IllegalArgumentException("调查回复人SurveyOperator值"+aSurveyOperator+"的长度"+aSurveyOperator.length()+"大于最大值10");
		SurveyOperator = aSurveyOperator;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("对应号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	/**
	* 0院内调查<p>
	* 1院外调查
	*/
	public String getSurveyType()
	{
		return SurveyType;
	}
	public void setSurveyType(String aSurveyType)
	{
		if(aSurveyType!=null && aSurveyType.length()>10)
			throw new IllegalArgumentException("调查类型SurveyType值"+aSurveyType+"的长度"+aSurveyType.length()+"大于最大值10");
		SurveyType = aSurveyType;
	}
	/**
	* 包括：分案号、报案分案号
	*/
	public String getSurveyNo()
	{
		return SurveyNo;
	}
	public void setSurveyNo(String aSurveyNo)
	{
		if(aSurveyNo!=null && aSurveyNo.length()>20)
			throw new IllegalArgumentException("调查号SurveyNo值"+aSurveyNo+"的长度"+aSurveyNo.length()+"大于最大值20");
		SurveyNo = aSurveyNo;
	}
	public String getSurveyRDesc()
	{
		return SurveyRDesc;
	}
	public void setSurveyRDesc(String aSurveyRDesc)
	{
		if(aSurveyRDesc!=null && aSurveyRDesc.length()>100)
			throw new IllegalArgumentException("调查原因内容SurveyRDesc值"+aSurveyRDesc+"的长度"+aSurveyRDesc.length()+"大于最大值100");
		SurveyRDesc = aSurveyRDesc;
	}
	public String getSurveyRCode()
	{
		return SurveyRCode;
	}
	public void setSurveyRCode(String aSurveyRCode)
	{
		if(aSurveyRCode!=null && aSurveyRCode.length()>10)
			throw new IllegalArgumentException("调查原因SurveyRCode值"+aSurveyRCode+"的长度"+aSurveyRCode.length()+"大于最大值10");
		SurveyRCode = aSurveyRCode;
	}
	public String getConfDate()
	{
		if( ConfDate != null )
			return fDate.getString(ConfDate);
		else
			return null;
	}
	public void setConfDate(Date aConfDate)
	{
		ConfDate = aConfDate;
	}
	public void setConfDate(String aConfDate)
	{
		if (aConfDate != null && !aConfDate.equals("") )
		{
			ConfDate = fDate.getDate( aConfDate );
		}
		else
			ConfDate = null;
	}

	public String getConfNote()
	{
		return ConfNote;
	}
	public void setConfNote(String aConfNote)
	{
		if(aConfNote!=null && aConfNote.length()>1000)
			throw new IllegalArgumentException("审核意见ConfNote值"+aConfNote+"的长度"+aConfNote.length()+"大于最大值1000");
		ConfNote = aConfNote;
	}
	/**
	* 包括：<p>
	* 1--分案、(立案?)<p>
	* 0--报案分案<p>
	* 2--通知
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>10)
			throw new IllegalArgumentException("其他对应号码类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值10");
		OtherNoType = aOtherNoType;
	}
	/**
	* 0 即时调查<p>
	* 1 一般调查<p>
	* 2 再次调查
	*/
	public String getSurveyClass()
	{
		return SurveyClass;
	}
	public void setSurveyClass(String aSurveyClass)
	{
		if(aSurveyClass!=null && aSurveyClass.length()>10)
			throw new IllegalArgumentException("调查类别SurveyClass值"+aSurveyClass+"的长度"+aSurveyClass.length()+"大于最大值10");
		SurveyClass = aSurveyClass;
	}
	public String getConfer()
	{
		return Confer;
	}
	public void setConfer(String aConfer)
	{
		if(aConfer!=null && aConfer.length()>10)
			throw new IllegalArgumentException("审核人Confer值"+aConfer+"的长度"+aConfer.length()+"大于最大值10");
		Confer = aConfer;
	}

	/**
	* 使用另外一个 LLSurveySchema 对象给 Schema 赋值
	* @param: aLLSurveySchema LLSurveySchema
	**/
	public void setSchema(LLSurveySchema aLLSurveySchema)
	{
		this.ClmCaseNo = aLLSurveySchema.getClmCaseNo();
		this.Type = aLLSurveySchema.getType();
		this.SerialNo = aLLSurveySchema.getSerialNo();
		this.RgtNo = aLLSurveySchema.getRgtNo();
		this.MainClmCaseNo = aLLSurveySchema.getMainClmCaseNo();
		this.CustomerNo = aLLSurveySchema.getCustomerNo();
		this.CustomerName = aLLSurveySchema.getCustomerName();
		this.Surveyor = aLLSurveySchema.getSurveyor();
		this.SurveyorName = aLLSurveySchema.getSurveyorName();
		this.SurveySite = aLLSurveySchema.getSurveySite();
		this.Content = aLLSurveySchema.getContent();
		this.result = aLLSurveySchema.getresult();
		this.SurveyStartDate = fDate.getDate( aLLSurveySchema.getSurveyStartDate());
		this.SurveyEndDate = fDate.getDate( aLLSurveySchema.getSurveyEndDate());
		this.MngCom = aLLSurveySchema.getMngCom();
		this.Operator = aLLSurveySchema.getOperator();
		this.MakeDate = fDate.getDate( aLLSurveySchema.getMakeDate());
		this.MakeTime = aLLSurveySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLSurveySchema.getModifyDate());
		this.ModifyTime = aLLSurveySchema.getModifyTime();
		this.StartPhase = aLLSurveySchema.getStartPhase();
		this.SurveyFlag = aLLSurveySchema.getSurveyFlag();
		this.StartMan = aLLSurveySchema.getStartMan();
		this.ReceiveMan = aLLSurveySchema.getReceiveMan();
		this.SubRptNo = aLLSurveySchema.getSubRptNo();
		this.SurveyOperator = aLLSurveySchema.getSurveyOperator();
		this.OtherNo = aLLSurveySchema.getOtherNo();
		this.SurveyType = aLLSurveySchema.getSurveyType();
		this.SurveyNo = aLLSurveySchema.getSurveyNo();
		this.SurveyRDesc = aLLSurveySchema.getSurveyRDesc();
		this.SurveyRCode = aLLSurveySchema.getSurveyRCode();
		this.ConfDate = fDate.getDate( aLLSurveySchema.getConfDate());
		this.ConfNote = aLLSurveySchema.getConfNote();
		this.OtherNoType = aLLSurveySchema.getOtherNoType();
		this.SurveyClass = aLLSurveySchema.getSurveyClass();
		this.Confer = aLLSurveySchema.getConfer();
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
			if( rs.getString("ClmCaseNo") == null )
				this.ClmCaseNo = null;
			else
				this.ClmCaseNo = rs.getString("ClmCaseNo").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("MainClmCaseNo") == null )
				this.MainClmCaseNo = null;
			else
				this.MainClmCaseNo = rs.getString("MainClmCaseNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("Surveyor") == null )
				this.Surveyor = null;
			else
				this.Surveyor = rs.getString("Surveyor").trim();

			if( rs.getString("SurveyorName") == null )
				this.SurveyorName = null;
			else
				this.SurveyorName = rs.getString("SurveyorName").trim();

			if( rs.getString("SurveySite") == null )
				this.SurveySite = null;
			else
				this.SurveySite = rs.getString("SurveySite").trim();

			if( rs.getString("Content") == null )
				this.Content = null;
			else
				this.Content = rs.getString("Content").trim();

			if( rs.getString("result") == null )
				this.result = null;
			else
				this.result = rs.getString("result").trim();

			this.SurveyStartDate = rs.getDate("SurveyStartDate");
			this.SurveyEndDate = rs.getDate("SurveyEndDate");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("StartPhase") == null )
				this.StartPhase = null;
			else
				this.StartPhase = rs.getString("StartPhase").trim();

			if( rs.getString("SurveyFlag") == null )
				this.SurveyFlag = null;
			else
				this.SurveyFlag = rs.getString("SurveyFlag").trim();

			if( rs.getString("StartMan") == null )
				this.StartMan = null;
			else
				this.StartMan = rs.getString("StartMan").trim();

			if( rs.getString("ReceiveMan") == null )
				this.ReceiveMan = null;
			else
				this.ReceiveMan = rs.getString("ReceiveMan").trim();

			if( rs.getString("SubRptNo") == null )
				this.SubRptNo = null;
			else
				this.SubRptNo = rs.getString("SubRptNo").trim();

			if( rs.getString("SurveyOperator") == null )
				this.SurveyOperator = null;
			else
				this.SurveyOperator = rs.getString("SurveyOperator").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("SurveyType") == null )
				this.SurveyType = null;
			else
				this.SurveyType = rs.getString("SurveyType").trim();

			if( rs.getString("SurveyNo") == null )
				this.SurveyNo = null;
			else
				this.SurveyNo = rs.getString("SurveyNo").trim();

			if( rs.getString("SurveyRDesc") == null )
				this.SurveyRDesc = null;
			else
				this.SurveyRDesc = rs.getString("SurveyRDesc").trim();

			if( rs.getString("SurveyRCode") == null )
				this.SurveyRCode = null;
			else
				this.SurveyRCode = rs.getString("SurveyRCode").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfNote") == null )
				this.ConfNote = null;
			else
				this.ConfNote = rs.getString("ConfNote").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("SurveyClass") == null )
				this.SurveyClass = null;
			else
				this.SurveyClass = rs.getString("SurveyClass").trim();

			if( rs.getString("Confer") == null )
				this.Confer = null;
			else
				this.Confer = rs.getString("Confer").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLSurvey表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSurveySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLSurveySchema getSchema()
	{
		LLSurveySchema aLLSurveySchema = new LLSurveySchema();
		aLLSurveySchema.setSchema(this);
		return aLLSurveySchema;
	}

	public LLSurveyDB getDB()
	{
		LLSurveyDB aDBOper = new LLSurveyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLSurvey描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmCaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainClmCaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Surveyor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveySite)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Content)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(result)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SurveyStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SurveyEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveMan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubRptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyRDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyRCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfNote)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Confer));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLSurvey>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmCaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MainClmCaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Surveyor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SurveyorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SurveySite = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Content = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			result = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SurveyStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			SurveyEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StartPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StartMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ReceiveMan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			SubRptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			SurveyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			SurveyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			SurveyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			SurveyRDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			SurveyRCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ConfNote = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			SurveyClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Confer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLSurveySchema";
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
		if (FCode.equalsIgnoreCase("ClmCaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmCaseNo));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("MainClmCaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainClmCaseNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("Surveyor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Surveyor));
		}
		if (FCode.equalsIgnoreCase("SurveyorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyorName));
		}
		if (FCode.equalsIgnoreCase("SurveySite"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveySite));
		}
		if (FCode.equalsIgnoreCase("Content"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Content));
		}
		if (FCode.equalsIgnoreCase("result"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(result));
		}
		if (FCode.equalsIgnoreCase("SurveyStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSurveyStartDate()));
		}
		if (FCode.equalsIgnoreCase("SurveyEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSurveyEndDate()));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("StartPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartPhase));
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyFlag));
		}
		if (FCode.equalsIgnoreCase("StartMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartMan));
		}
		if (FCode.equalsIgnoreCase("ReceiveMan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveMan));
		}
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRptNo));
		}
		if (FCode.equalsIgnoreCase("SurveyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyOperator));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("SurveyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyType));
		}
		if (FCode.equalsIgnoreCase("SurveyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyNo));
		}
		if (FCode.equalsIgnoreCase("SurveyRDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyRDesc));
		}
		if (FCode.equalsIgnoreCase("SurveyRCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyRCode));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfNote"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfNote));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("SurveyClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyClass));
		}
		if (FCode.equalsIgnoreCase("Confer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Confer));
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
				strFieldValue = StrTool.GBKToUnicode(ClmCaseNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MainClmCaseNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Surveyor);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SurveyorName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SurveySite);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Content);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(result);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSurveyStartDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSurveyEndDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StartPhase);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StartMan);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ReceiveMan);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(SubRptNo);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(SurveyOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(SurveyType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SurveyNo);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(SurveyRDesc);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(SurveyRCode);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ConfNote);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(SurveyClass);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Confer);
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

		if (FCode.equalsIgnoreCase("ClmCaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmCaseNo = FValue.trim();
			}
			else
				ClmCaseNo = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("MainClmCaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainClmCaseNo = FValue.trim();
			}
			else
				MainClmCaseNo = null;
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
		if (FCode.equalsIgnoreCase("Surveyor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Surveyor = FValue.trim();
			}
			else
				Surveyor = null;
		}
		if (FCode.equalsIgnoreCase("SurveyorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyorName = FValue.trim();
			}
			else
				SurveyorName = null;
		}
		if (FCode.equalsIgnoreCase("SurveySite"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveySite = FValue.trim();
			}
			else
				SurveySite = null;
		}
		if (FCode.equalsIgnoreCase("Content"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Content = FValue.trim();
			}
			else
				Content = null;
		}
		if (FCode.equalsIgnoreCase("result"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				result = FValue.trim();
			}
			else
				result = null;
		}
		if (FCode.equalsIgnoreCase("SurveyStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SurveyStartDate = fDate.getDate( FValue );
			}
			else
				SurveyStartDate = null;
		}
		if (FCode.equalsIgnoreCase("SurveyEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SurveyEndDate = fDate.getDate( FValue );
			}
			else
				SurveyEndDate = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("StartPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartPhase = FValue.trim();
			}
			else
				StartPhase = null;
		}
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyFlag = FValue.trim();
			}
			else
				SurveyFlag = null;
		}
		if (FCode.equalsIgnoreCase("StartMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartMan = FValue.trim();
			}
			else
				StartMan = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveMan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveMan = FValue.trim();
			}
			else
				ReceiveMan = null;
		}
		if (FCode.equalsIgnoreCase("SubRptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRptNo = FValue.trim();
			}
			else
				SubRptNo = null;
		}
		if (FCode.equalsIgnoreCase("SurveyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyOperator = FValue.trim();
			}
			else
				SurveyOperator = null;
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
		if (FCode.equalsIgnoreCase("SurveyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyType = FValue.trim();
			}
			else
				SurveyType = null;
		}
		if (FCode.equalsIgnoreCase("SurveyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyNo = FValue.trim();
			}
			else
				SurveyNo = null;
		}
		if (FCode.equalsIgnoreCase("SurveyRDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyRDesc = FValue.trim();
			}
			else
				SurveyRDesc = null;
		}
		if (FCode.equalsIgnoreCase("SurveyRCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyRCode = FValue.trim();
			}
			else
				SurveyRCode = null;
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfDate = fDate.getDate( FValue );
			}
			else
				ConfDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfNote"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfNote = FValue.trim();
			}
			else
				ConfNote = null;
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
		if (FCode.equalsIgnoreCase("SurveyClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyClass = FValue.trim();
			}
			else
				SurveyClass = null;
		}
		if (FCode.equalsIgnoreCase("Confer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Confer = FValue.trim();
			}
			else
				Confer = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLSurveySchema other = (LLSurveySchema)otherObject;
		return
			ClmCaseNo.equals(other.getClmCaseNo())
			&& Type.equals(other.getType())
			&& SerialNo.equals(other.getSerialNo())
			&& RgtNo.equals(other.getRgtNo())
			&& MainClmCaseNo.equals(other.getMainClmCaseNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& Surveyor.equals(other.getSurveyor())
			&& SurveyorName.equals(other.getSurveyorName())
			&& SurveySite.equals(other.getSurveySite())
			&& Content.equals(other.getContent())
			&& result.equals(other.getresult())
			&& fDate.getString(SurveyStartDate).equals(other.getSurveyStartDate())
			&& fDate.getString(SurveyEndDate).equals(other.getSurveyEndDate())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StartPhase.equals(other.getStartPhase())
			&& SurveyFlag.equals(other.getSurveyFlag())
			&& StartMan.equals(other.getStartMan())
			&& ReceiveMan.equals(other.getReceiveMan())
			&& SubRptNo.equals(other.getSubRptNo())
			&& SurveyOperator.equals(other.getSurveyOperator())
			&& OtherNo.equals(other.getOtherNo())
			&& SurveyType.equals(other.getSurveyType())
			&& SurveyNo.equals(other.getSurveyNo())
			&& SurveyRDesc.equals(other.getSurveyRDesc())
			&& SurveyRCode.equals(other.getSurveyRCode())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfNote.equals(other.getConfNote())
			&& OtherNoType.equals(other.getOtherNoType())
			&& SurveyClass.equals(other.getSurveyClass())
			&& Confer.equals(other.getConfer());
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
		if( strFieldName.equals("ClmCaseNo") ) {
			return 0;
		}
		if( strFieldName.equals("Type") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 3;
		}
		if( strFieldName.equals("MainClmCaseNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 6;
		}
		if( strFieldName.equals("Surveyor") ) {
			return 7;
		}
		if( strFieldName.equals("SurveyorName") ) {
			return 8;
		}
		if( strFieldName.equals("SurveySite") ) {
			return 9;
		}
		if( strFieldName.equals("Content") ) {
			return 10;
		}
		if( strFieldName.equals("result") ) {
			return 11;
		}
		if( strFieldName.equals("SurveyStartDate") ) {
			return 12;
		}
		if( strFieldName.equals("SurveyEndDate") ) {
			return 13;
		}
		if( strFieldName.equals("MngCom") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("StartPhase") ) {
			return 20;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 21;
		}
		if( strFieldName.equals("StartMan") ) {
			return 22;
		}
		if( strFieldName.equals("ReceiveMan") ) {
			return 23;
		}
		if( strFieldName.equals("SubRptNo") ) {
			return 24;
		}
		if( strFieldName.equals("SurveyOperator") ) {
			return 25;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 26;
		}
		if( strFieldName.equals("SurveyType") ) {
			return 27;
		}
		if( strFieldName.equals("SurveyNo") ) {
			return 28;
		}
		if( strFieldName.equals("SurveyRDesc") ) {
			return 29;
		}
		if( strFieldName.equals("SurveyRCode") ) {
			return 30;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 31;
		}
		if( strFieldName.equals("ConfNote") ) {
			return 32;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 33;
		}
		if( strFieldName.equals("SurveyClass") ) {
			return 34;
		}
		if( strFieldName.equals("Confer") ) {
			return 35;
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
				strFieldName = "ClmCaseNo";
				break;
			case 1:
				strFieldName = "Type";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "RgtNo";
				break;
			case 4:
				strFieldName = "MainClmCaseNo";
				break;
			case 5:
				strFieldName = "CustomerNo";
				break;
			case 6:
				strFieldName = "CustomerName";
				break;
			case 7:
				strFieldName = "Surveyor";
				break;
			case 8:
				strFieldName = "SurveyorName";
				break;
			case 9:
				strFieldName = "SurveySite";
				break;
			case 10:
				strFieldName = "Content";
				break;
			case 11:
				strFieldName = "result";
				break;
			case 12:
				strFieldName = "SurveyStartDate";
				break;
			case 13:
				strFieldName = "SurveyEndDate";
				break;
			case 14:
				strFieldName = "MngCom";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "StartPhase";
				break;
			case 21:
				strFieldName = "SurveyFlag";
				break;
			case 22:
				strFieldName = "StartMan";
				break;
			case 23:
				strFieldName = "ReceiveMan";
				break;
			case 24:
				strFieldName = "SubRptNo";
				break;
			case 25:
				strFieldName = "SurveyOperator";
				break;
			case 26:
				strFieldName = "OtherNo";
				break;
			case 27:
				strFieldName = "SurveyType";
				break;
			case 28:
				strFieldName = "SurveyNo";
				break;
			case 29:
				strFieldName = "SurveyRDesc";
				break;
			case 30:
				strFieldName = "SurveyRCode";
				break;
			case 31:
				strFieldName = "ConfDate";
				break;
			case 32:
				strFieldName = "ConfNote";
				break;
			case 33:
				strFieldName = "OtherNoType";
				break;
			case 34:
				strFieldName = "SurveyClass";
				break;
			case 35:
				strFieldName = "Confer";
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
		if( strFieldName.equals("ClmCaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainClmCaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Surveyor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveySite") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Content") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("result") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SurveyEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("StartPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveMan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyRDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyRCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfNote") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SurveyClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Confer") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
			case 31:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
