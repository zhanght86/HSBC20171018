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
import com.sinosoft.lis.db.LLCaseBackDB;

/*
 * <p>ClassName: LLCaseBackSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLCaseBackSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseBackSchema.class);
	// @Field
	/** 回退序号 */
	private String BackNo;
	/** 赔案号 */
	private String ClmNo;
	/** 回退类型 */
	private String BackType;
	/** 回退原因 */
	private String BackReason;
	/** 回退描述 */
	private String BackDesc;
	/** 回退状态 */
	private String BackState;
	/** 回退日期 */
	private Date BackDate;
	/** 备注 */
	private String Remark;
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
	/** 申请人或签报号 */
	private String ApplyUser;
	/** 申请日期 */
	private Date ApplyDate;
	/** 申请时间 */
	private String ApplyTime;
	/** 原审批机构 */
	private String OriExamCom;
	/** 原给付总额 */
	private double OriRealyPay;
	/** 原理赔结论 */
	private String OriGiveType;
	/** 新理赔结论 */
	private String NewGiveType;
	/** 确认人 */
	private String AffirmUser;
	/** 确认意见 */
	private String AffirmIdea;
	/** 确认日期 */
	private Date AffirmDate;
	/** 确认时间 */
	private String AffirmTime;
	/** 分案号(个人理赔号) */
	private String OldClmNo;
	/** 是否回退预付记录标志 */
	private String CheckBackPreFlag;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseBackSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BackNo";

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
		LLCaseBackSchema cloned = (LLCaseBackSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBackNo()
	{
		return BackNo;
	}
	public void setBackNo(String aBackNo)
	{
		if(aBackNo!=null && aBackNo.length()>20)
			throw new IllegalArgumentException("回退序号BackNo值"+aBackNo+"的长度"+aBackNo.length()+"大于最大值20");
		BackNo = aBackNo;
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
	/**
	* 0  已财??给付的案件回退<p>
	* 1  签批同意的案件回退
	*/
	public String getBackType()
	{
		return BackType;
	}
	public void setBackType(String aBackType)
	{
		if(aBackType!=null && aBackType.length()>6)
			throw new IllegalArgumentException("回退类型BackType值"+aBackType+"的长度"+aBackType.length()+"大于最大值6");
		BackType = aBackType;
	}
	/**
	* 01	系统操作错误<p>
	* 02	专业技术错误<p>
	* 03	法律诉讼结论<p>
	* 04	新关键证据<p>
	* 05	民事申诉调解
	*/
	public String getBackReason()
	{
		return BackReason;
	}
	public void setBackReason(String aBackReason)
	{
		if(aBackReason!=null && aBackReason.length()>6)
			throw new IllegalArgumentException("回退原因BackReason值"+aBackReason+"的长度"+aBackReason.length()+"大于最大值6");
		BackReason = aBackReason;
	}
	public String getBackDesc()
	{
		return BackDesc;
	}
	public void setBackDesc(String aBackDesc)
	{
		if(aBackDesc!=null && aBackDesc.length()>2000)
			throw new IllegalArgumentException("回退描述BackDesc值"+aBackDesc+"的长度"+aBackDesc.length()+"大于最大值2000");
		BackDesc = aBackDesc;
	}
	/**
	* 0-申请<p>
	* 1-完成<p>
	* 2-申请确认<p>
	* 3-撤消
	*/
	public String getBackState()
	{
		return BackState;
	}
	public void setBackState(String aBackState)
	{
		if(aBackState!=null && aBackState.length()>6)
			throw new IllegalArgumentException("回退状态BackState值"+aBackState+"的长度"+aBackState.length()+"大于最大值6");
		BackState = aBackState;
	}
	public String getBackDate()
	{
		if( BackDate != null )
			return fDate.getString(BackDate);
		else
			return null;
	}
	public void setBackDate(Date aBackDate)
	{
		BackDate = aBackDate;
	}
	public void setBackDate(String aBackDate)
	{
		if (aBackDate != null && !aBackDate.equals("") )
		{
			BackDate = fDate.getDate( aBackDate );
		}
		else
			BackDate = null;
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>2000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值2000");
		Remark = aRemark;
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
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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
	public String getApplyUser()
	{
		return ApplyUser;
	}
	public void setApplyUser(String aApplyUser)
	{
		if(aApplyUser!=null && aApplyUser.length()>20)
			throw new IllegalArgumentException("申请人或签报号ApplyUser值"+aApplyUser+"的长度"+aApplyUser.length()+"大于最大值20");
		ApplyUser = aApplyUser;
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

	public String getApplyTime()
	{
		return ApplyTime;
	}
	public void setApplyTime(String aApplyTime)
	{
		if(aApplyTime!=null && aApplyTime.length()>8)
			throw new IllegalArgumentException("申请时间ApplyTime值"+aApplyTime+"的长度"+aApplyTime.length()+"大于最大值8");
		ApplyTime = aApplyTime;
	}
	public String getOriExamCom()
	{
		return OriExamCom;
	}
	public void setOriExamCom(String aOriExamCom)
	{
		if(aOriExamCom!=null && aOriExamCom.length()>10)
			throw new IllegalArgumentException("原审批机构OriExamCom值"+aOriExamCom+"的长度"+aOriExamCom.length()+"大于最大值10");
		OriExamCom = aOriExamCom;
	}
	public double getOriRealyPay()
	{
		return OriRealyPay;
	}
	public void setOriRealyPay(double aOriRealyPay)
	{
		OriRealyPay = aOriRealyPay;
	}
	public void setOriRealyPay(String aOriRealyPay)
	{
		if (aOriRealyPay != null && !aOriRealyPay.equals(""))
		{
			Double tDouble = new Double(aOriRealyPay);
			double d = tDouble.doubleValue();
			OriRealyPay = d;
		}
	}

	public String getOriGiveType()
	{
		return OriGiveType;
	}
	public void setOriGiveType(String aOriGiveType)
	{
		if(aOriGiveType!=null && aOriGiveType.length()>6)
			throw new IllegalArgumentException("原理赔结论OriGiveType值"+aOriGiveType+"的长度"+aOriGiveType.length()+"大于最大值6");
		OriGiveType = aOriGiveType;
	}
	public String getNewGiveType()
	{
		return NewGiveType;
	}
	public void setNewGiveType(String aNewGiveType)
	{
		if(aNewGiveType!=null && aNewGiveType.length()>6)
			throw new IllegalArgumentException("新理赔结论NewGiveType值"+aNewGiveType+"的长度"+aNewGiveType.length()+"大于最大值6");
		NewGiveType = aNewGiveType;
	}
	public String getAffirmUser()
	{
		return AffirmUser;
	}
	public void setAffirmUser(String aAffirmUser)
	{
		if(aAffirmUser!=null && aAffirmUser.length()>20)
			throw new IllegalArgumentException("确认人AffirmUser值"+aAffirmUser+"的长度"+aAffirmUser.length()+"大于最大值20");
		AffirmUser = aAffirmUser;
	}
	/**
	* 0-同意<p>
	* 1-不同意
	*/
	public String getAffirmIdea()
	{
		return AffirmIdea;
	}
	public void setAffirmIdea(String aAffirmIdea)
	{
		if(aAffirmIdea!=null && aAffirmIdea.length()>6)
			throw new IllegalArgumentException("确认意见AffirmIdea值"+aAffirmIdea+"的长度"+aAffirmIdea.length()+"大于最大值6");
		AffirmIdea = aAffirmIdea;
	}
	public String getAffirmDate()
	{
		if( AffirmDate != null )
			return fDate.getString(AffirmDate);
		else
			return null;
	}
	public void setAffirmDate(Date aAffirmDate)
	{
		AffirmDate = aAffirmDate;
	}
	public void setAffirmDate(String aAffirmDate)
	{
		if (aAffirmDate != null && !aAffirmDate.equals("") )
		{
			AffirmDate = fDate.getDate( aAffirmDate );
		}
		else
			AffirmDate = null;
	}

	public String getAffirmTime()
	{
		return AffirmTime;
	}
	public void setAffirmTime(String aAffirmTime)
	{
		if(aAffirmTime!=null && aAffirmTime.length()>8)
			throw new IllegalArgumentException("确认时间AffirmTime值"+aAffirmTime+"的长度"+aAffirmTime.length()+"大于最大值8");
		AffirmTime = aAffirmTime;
	}
	public String getOldClmNo()
	{
		return OldClmNo;
	}
	public void setOldClmNo(String aOldClmNo)
	{
		if(aOldClmNo!=null && aOldClmNo.length()>20)
			throw new IllegalArgumentException("分案号(个人理赔号)OldClmNo值"+aOldClmNo+"的长度"+aOldClmNo.length()+"大于最大值20");
		OldClmNo = aOldClmNo;
	}
	/**
	* 0  不回退预付记录<p>
	* 1 同时回退预付记录
	*/
	public String getCheckBackPreFlag()
	{
		return CheckBackPreFlag;
	}
	public void setCheckBackPreFlag(String aCheckBackPreFlag)
	{
		if(aCheckBackPreFlag!=null && aCheckBackPreFlag.length()>1)
			throw new IllegalArgumentException("是否回退预付记录标志CheckBackPreFlag值"+aCheckBackPreFlag+"的长度"+aCheckBackPreFlag.length()+"大于最大值1");
		CheckBackPreFlag = aCheckBackPreFlag;
	}

	/**
	* 使用另外一个 LLCaseBackSchema 对象给 Schema 赋值
	* @param: aLLCaseBackSchema LLCaseBackSchema
	**/
	public void setSchema(LLCaseBackSchema aLLCaseBackSchema)
	{
		this.BackNo = aLLCaseBackSchema.getBackNo();
		this.ClmNo = aLLCaseBackSchema.getClmNo();
		this.BackType = aLLCaseBackSchema.getBackType();
		this.BackReason = aLLCaseBackSchema.getBackReason();
		this.BackDesc = aLLCaseBackSchema.getBackDesc();
		this.BackState = aLLCaseBackSchema.getBackState();
		this.BackDate = fDate.getDate( aLLCaseBackSchema.getBackDate());
		this.Remark = aLLCaseBackSchema.getRemark();
		this.MngCom = aLLCaseBackSchema.getMngCom();
		this.Operator = aLLCaseBackSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLCaseBackSchema.getMakeDate());
		this.MakeTime = aLLCaseBackSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCaseBackSchema.getModifyDate());
		this.ModifyTime = aLLCaseBackSchema.getModifyTime();
		this.ApplyUser = aLLCaseBackSchema.getApplyUser();
		this.ApplyDate = fDate.getDate( aLLCaseBackSchema.getApplyDate());
		this.ApplyTime = aLLCaseBackSchema.getApplyTime();
		this.OriExamCom = aLLCaseBackSchema.getOriExamCom();
		this.OriRealyPay = aLLCaseBackSchema.getOriRealyPay();
		this.OriGiveType = aLLCaseBackSchema.getOriGiveType();
		this.NewGiveType = aLLCaseBackSchema.getNewGiveType();
		this.AffirmUser = aLLCaseBackSchema.getAffirmUser();
		this.AffirmIdea = aLLCaseBackSchema.getAffirmIdea();
		this.AffirmDate = fDate.getDate( aLLCaseBackSchema.getAffirmDate());
		this.AffirmTime = aLLCaseBackSchema.getAffirmTime();
		this.OldClmNo = aLLCaseBackSchema.getOldClmNo();
		this.CheckBackPreFlag = aLLCaseBackSchema.getCheckBackPreFlag();
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
			if( rs.getString("BackNo") == null )
				this.BackNo = null;
			else
				this.BackNo = rs.getString("BackNo").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("BackType") == null )
				this.BackType = null;
			else
				this.BackType = rs.getString("BackType").trim();

			if( rs.getString("BackReason") == null )
				this.BackReason = null;
			else
				this.BackReason = rs.getString("BackReason").trim();

			if( rs.getString("BackDesc") == null )
				this.BackDesc = null;
			else
				this.BackDesc = rs.getString("BackDesc").trim();

			if( rs.getString("BackState") == null )
				this.BackState = null;
			else
				this.BackState = rs.getString("BackState").trim();

			this.BackDate = rs.getDate("BackDate");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("ApplyUser") == null )
				this.ApplyUser = null;
			else
				this.ApplyUser = rs.getString("ApplyUser").trim();

			this.ApplyDate = rs.getDate("ApplyDate");
			if( rs.getString("ApplyTime") == null )
				this.ApplyTime = null;
			else
				this.ApplyTime = rs.getString("ApplyTime").trim();

			if( rs.getString("OriExamCom") == null )
				this.OriExamCom = null;
			else
				this.OriExamCom = rs.getString("OriExamCom").trim();

			this.OriRealyPay = rs.getDouble("OriRealyPay");
			if( rs.getString("OriGiveType") == null )
				this.OriGiveType = null;
			else
				this.OriGiveType = rs.getString("OriGiveType").trim();

			if( rs.getString("NewGiveType") == null )
				this.NewGiveType = null;
			else
				this.NewGiveType = rs.getString("NewGiveType").trim();

			if( rs.getString("AffirmUser") == null )
				this.AffirmUser = null;
			else
				this.AffirmUser = rs.getString("AffirmUser").trim();

			if( rs.getString("AffirmIdea") == null )
				this.AffirmIdea = null;
			else
				this.AffirmIdea = rs.getString("AffirmIdea").trim();

			this.AffirmDate = rs.getDate("AffirmDate");
			if( rs.getString("AffirmTime") == null )
				this.AffirmTime = null;
			else
				this.AffirmTime = rs.getString("AffirmTime").trim();

			if( rs.getString("OldClmNo") == null )
				this.OldClmNo = null;
			else
				this.OldClmNo = rs.getString("OldClmNo").trim();

			if( rs.getString("CheckBackPreFlag") == null )
				this.CheckBackPreFlag = null;
			else
				this.CheckBackPreFlag = rs.getString("CheckBackPreFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCaseBack表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseBackSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseBackSchema getSchema()
	{
		LLCaseBackSchema aLLCaseBackSchema = new LLCaseBackSchema();
		aLLCaseBackSchema.setSchema(this);
		return aLLCaseBackSchema;
	}

	public LLCaseBackDB getDB()
	{
		LLCaseBackDB aDBOper = new LLCaseBackDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseBack描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BackDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyUser)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApplyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriExamCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OriRealyPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriGiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NewGiveType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffirmUser)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffirmIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AffirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckBackPreFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseBack>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BackType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BackReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BackDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BackState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BackDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ApplyUser = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ApplyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			OriExamCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			OriRealyPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			OriGiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			NewGiveType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			AffirmUser = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			AffirmIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AffirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			AffirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			OldClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CheckBackPreFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseBackSchema";
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
		if (FCode.equalsIgnoreCase("BackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackNo));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("BackType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackType));
		}
		if (FCode.equalsIgnoreCase("BackReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackReason));
		}
		if (FCode.equalsIgnoreCase("BackDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackDesc));
		}
		if (FCode.equalsIgnoreCase("BackState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackState));
		}
		if (FCode.equalsIgnoreCase("BackDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBackDate()));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("ApplyUser"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyUser));
		}
		if (FCode.equalsIgnoreCase("ApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
		}
		if (FCode.equalsIgnoreCase("ApplyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyTime));
		}
		if (FCode.equalsIgnoreCase("OriExamCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriExamCom));
		}
		if (FCode.equalsIgnoreCase("OriRealyPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriRealyPay));
		}
		if (FCode.equalsIgnoreCase("OriGiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriGiveType));
		}
		if (FCode.equalsIgnoreCase("NewGiveType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NewGiveType));
		}
		if (FCode.equalsIgnoreCase("AffirmUser"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffirmUser));
		}
		if (FCode.equalsIgnoreCase("AffirmIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffirmIdea));
		}
		if (FCode.equalsIgnoreCase("AffirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAffirmDate()));
		}
		if (FCode.equalsIgnoreCase("AffirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffirmTime));
		}
		if (FCode.equalsIgnoreCase("OldClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldClmNo));
		}
		if (FCode.equalsIgnoreCase("CheckBackPreFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckBackPreFlag));
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
				strFieldValue = StrTool.GBKToUnicode(BackNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BackType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BackReason);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BackDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BackState);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBackDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ApplyUser);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApplyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ApplyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(OriExamCom);
				break;
			case 18:
				strFieldValue = String.valueOf(OriRealyPay);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(OriGiveType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(NewGiveType);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(AffirmUser);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(AffirmIdea);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAffirmDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AffirmTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(OldClmNo);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(CheckBackPreFlag);
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

		if (FCode.equalsIgnoreCase("BackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackNo = FValue.trim();
			}
			else
				BackNo = null;
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("BackType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackType = FValue.trim();
			}
			else
				BackType = null;
		}
		if (FCode.equalsIgnoreCase("BackReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackReason = FValue.trim();
			}
			else
				BackReason = null;
		}
		if (FCode.equalsIgnoreCase("BackDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackDesc = FValue.trim();
			}
			else
				BackDesc = null;
		}
		if (FCode.equalsIgnoreCase("BackState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackState = FValue.trim();
			}
			else
				BackState = null;
		}
		if (FCode.equalsIgnoreCase("BackDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BackDate = fDate.getDate( FValue );
			}
			else
				BackDate = null;
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
		if (FCode.equalsIgnoreCase("ApplyUser"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyUser = FValue.trim();
			}
			else
				ApplyUser = null;
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
		if (FCode.equalsIgnoreCase("ApplyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyTime = FValue.trim();
			}
			else
				ApplyTime = null;
		}
		if (FCode.equalsIgnoreCase("OriExamCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriExamCom = FValue.trim();
			}
			else
				OriExamCom = null;
		}
		if (FCode.equalsIgnoreCase("OriRealyPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OriRealyPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("OriGiveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriGiveType = FValue.trim();
			}
			else
				OriGiveType = null;
		}
		if (FCode.equalsIgnoreCase("NewGiveType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NewGiveType = FValue.trim();
			}
			else
				NewGiveType = null;
		}
		if (FCode.equalsIgnoreCase("AffirmUser"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffirmUser = FValue.trim();
			}
			else
				AffirmUser = null;
		}
		if (FCode.equalsIgnoreCase("AffirmIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffirmIdea = FValue.trim();
			}
			else
				AffirmIdea = null;
		}
		if (FCode.equalsIgnoreCase("AffirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AffirmDate = fDate.getDate( FValue );
			}
			else
				AffirmDate = null;
		}
		if (FCode.equalsIgnoreCase("AffirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffirmTime = FValue.trim();
			}
			else
				AffirmTime = null;
		}
		if (FCode.equalsIgnoreCase("OldClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldClmNo = FValue.trim();
			}
			else
				OldClmNo = null;
		}
		if (FCode.equalsIgnoreCase("CheckBackPreFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckBackPreFlag = FValue.trim();
			}
			else
				CheckBackPreFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseBackSchema other = (LLCaseBackSchema)otherObject;
		return
			BackNo.equals(other.getBackNo())
			&& ClmNo.equals(other.getClmNo())
			&& BackType.equals(other.getBackType())
			&& BackReason.equals(other.getBackReason())
			&& BackDesc.equals(other.getBackDesc())
			&& BackState.equals(other.getBackState())
			&& fDate.getString(BackDate).equals(other.getBackDate())
			&& Remark.equals(other.getRemark())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ApplyUser.equals(other.getApplyUser())
			&& fDate.getString(ApplyDate).equals(other.getApplyDate())
			&& ApplyTime.equals(other.getApplyTime())
			&& OriExamCom.equals(other.getOriExamCom())
			&& OriRealyPay == other.getOriRealyPay()
			&& OriGiveType.equals(other.getOriGiveType())
			&& NewGiveType.equals(other.getNewGiveType())
			&& AffirmUser.equals(other.getAffirmUser())
			&& AffirmIdea.equals(other.getAffirmIdea())
			&& fDate.getString(AffirmDate).equals(other.getAffirmDate())
			&& AffirmTime.equals(other.getAffirmTime())
			&& OldClmNo.equals(other.getOldClmNo())
			&& CheckBackPreFlag.equals(other.getCheckBackPreFlag());
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
		if( strFieldName.equals("BackNo") ) {
			return 0;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 1;
		}
		if( strFieldName.equals("BackType") ) {
			return 2;
		}
		if( strFieldName.equals("BackReason") ) {
			return 3;
		}
		if( strFieldName.equals("BackDesc") ) {
			return 4;
		}
		if( strFieldName.equals("BackState") ) {
			return 5;
		}
		if( strFieldName.equals("BackDate") ) {
			return 6;
		}
		if( strFieldName.equals("Remark") ) {
			return 7;
		}
		if( strFieldName.equals("MngCom") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("ApplyUser") ) {
			return 14;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ApplyTime") ) {
			return 16;
		}
		if( strFieldName.equals("OriExamCom") ) {
			return 17;
		}
		if( strFieldName.equals("OriRealyPay") ) {
			return 18;
		}
		if( strFieldName.equals("OriGiveType") ) {
			return 19;
		}
		if( strFieldName.equals("NewGiveType") ) {
			return 20;
		}
		if( strFieldName.equals("AffirmUser") ) {
			return 21;
		}
		if( strFieldName.equals("AffirmIdea") ) {
			return 22;
		}
		if( strFieldName.equals("AffirmDate") ) {
			return 23;
		}
		if( strFieldName.equals("AffirmTime") ) {
			return 24;
		}
		if( strFieldName.equals("OldClmNo") ) {
			return 25;
		}
		if( strFieldName.equals("CheckBackPreFlag") ) {
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
				strFieldName = "BackNo";
				break;
			case 1:
				strFieldName = "ClmNo";
				break;
			case 2:
				strFieldName = "BackType";
				break;
			case 3:
				strFieldName = "BackReason";
				break;
			case 4:
				strFieldName = "BackDesc";
				break;
			case 5:
				strFieldName = "BackState";
				break;
			case 6:
				strFieldName = "BackDate";
				break;
			case 7:
				strFieldName = "Remark";
				break;
			case 8:
				strFieldName = "MngCom";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "ApplyUser";
				break;
			case 15:
				strFieldName = "ApplyDate";
				break;
			case 16:
				strFieldName = "ApplyTime";
				break;
			case 17:
				strFieldName = "OriExamCom";
				break;
			case 18:
				strFieldName = "OriRealyPay";
				break;
			case 19:
				strFieldName = "OriGiveType";
				break;
			case 20:
				strFieldName = "NewGiveType";
				break;
			case 21:
				strFieldName = "AffirmUser";
				break;
			case 22:
				strFieldName = "AffirmIdea";
				break;
			case 23:
				strFieldName = "AffirmDate";
				break;
			case 24:
				strFieldName = "AffirmTime";
				break;
			case 25:
				strFieldName = "OldClmNo";
				break;
			case 26:
				strFieldName = "CheckBackPreFlag";
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
		if( strFieldName.equals("BackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("ApplyUser") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApplyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriExamCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriRealyPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OriGiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NewGiveType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffirmUser") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffirmIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AffirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckBackPreFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
