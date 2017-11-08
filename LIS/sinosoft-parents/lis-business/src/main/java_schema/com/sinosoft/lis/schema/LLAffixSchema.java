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
import com.sinosoft.lis.db.LLAffixDB;

/*
 * <p>ClassName: LLAffixSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLAffixSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAffixSchema.class);

	// @Field
	/** 赔案号 */
	private String RgtNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 附件号码 */
	private String AffixNo;
	/** 流水号 */
	private int SerialNo;
	/** 分案号(个人理赔号) */
	private String CaseNo;
	/** 原因代码 */
	private String ReasonCode;
	/** 附件类型 */
	private String AffixType;
	/** 附件代码 */
	private String AffixCode;
	/** 附件名称 */
	private String AffixName;
	/** 材料类型 */
	private String Property;
	/** 齐备件数 */
	private int ReadyCount;
	/** 缺少件数 */
	private int ShortCount;
	/** 提供日期 */
	private Date SupplyDate;
	/** 起用日期 */
	private Date FileStartDate;
	/** 停止日期 */
	private Date FileEndDate;
	/** 内容概述 */
	private String FileSummary;
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
	/** 单证检查结论 */
	private String AffixConclusion;
	/** 单证不全原因 */
	private String AffixReason;
	/** 是否必需 */
	private String NeedFlag;
	/** 是否提交 */
	private String SubFlag;
	/** 是否退回 */
	private String ReturnFlag;
	/** 发起阶段 */
	private String SupplyStage;
	/** 补充材料受理日期 */
	private Date ReAffixDate;
	/** 所处阶段 */
	private String AffixState;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAffixSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RgtNo";
		pk[1] = "CustomerNo";
		pk[2] = "AffixNo";
		pk[3] = "AffixCode";

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
		LLAffixSchema cloned = (LLAffixSchema)super.clone();
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
	* 如果是团体，可以理解为:团体申请受理号
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/**
	* 自动生成,用于扫描号
	*/
	public String getAffixNo()
	{
		return AffixNo;
	}
	public void setAffixNo(String aAffixNo)
	{
		AffixNo = aAffixNo;
	}
	/**
	* 【不用】
	*/
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	/**
	* 默认为理赔号
	*/
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	/**
	* 【不用】
	*/
	public String getReasonCode()
	{
		return ReasonCode;
	}
	public void setReasonCode(String aReasonCode)
	{
		ReasonCode = aReasonCode;
	}
	/**
	* 【不用】<p>
	* 信函<p>
	* 疾病伤残<p>
	* 医疗账单<p>
	* 门诊账单<p>
	* 社保账单
	*/
	public String getAffixType()
	{
		return AffixType;
	}
	public void setAffixType(String aAffixType)
	{
		AffixType = aAffixType;
	}
	/**
	* 附件上的代码<p>
	* CLM001    	保险合同正本(如保险合同曾作变更需提供批单)<p>
	* CLM002    	末次保费缴纳凭证和出险当期保费缴纳凭证<p>
	* CLM003    	有关合同变更的批单(如合同曾作变更处理请提供)<p>
	* CLM004    	索赔申请书<p>
	* CLM005    	索赔委托书(如有受托人请提供)<p>
	* CLM006    	赔款给付方式协议书<p>
	* CLM007    	出险人身份证明原件和户籍证明原件<p>
	* CLM008    	受托人身份证明原件（如有受托人请提供）<p>
	* CLM009    	受益人身份证明原件和户籍证明原件(身故保险金)<p>
	* CLM010    	受益人与出险人关系证明原件(身故保险金)<p>
	* CLM011    	申请人身份证明原件<p>
	* CLM012    	申请人与出险人关系证明原件<p>
	* CLM013    	监护证明原件<p>
	* CLM014    	病案材料(门诊、急诊或住院病历复印件)<p>
	* CLM015    	意外事故证明<p>
	* CLM016    	伤残鉴定书原件<p>
	* CLM017    	门诊医疗费用收据原件和处方原件<p>
	* CLM018    	住院医疗费用收据原件和住院费用明细清单原件/复印件<p>
	* CLM019    	死亡证明原件（医学死亡证明书、丧葬证明、户口注销证明）<p>
	* CLM020    	宣告死亡证明原件<p>
	* CLM021    	能确诊的病理、化验、影像、心电图等检查报告原件/复印件<p>
	* CLM022    	诊断证明书原件<p>
	* CLM023    	出险人银行存折原件(留存复印件)<p>
	* CLM024    	受益人银行存折原件(留存复印件)<p>
	* CLM025    	被保险人最近一次还贷收据或证明原件<p>
	* CLM026    	合法有效的住房公积金贷款合同原件<p>
	* CLM027    	被保险人的失业证明及领取失业保险金证明原件<p>
	* CLM028    	发放住房公积金贷款的机构法人资格证明原件<p>
	* CLM029    	医疗费用分割单<p>
	* CLM030    	谈话记录<p>
	* CLM031    	勘验笔录<p>
	* CLM032    	照片<p>
	* CLM033    	其它
	*/
	public String getAffixCode()
	{
		return AffixCode;
	}
	public void setAffixCode(String aAffixCode)
	{
		AffixCode = aAffixCode;
	}
	public String getAffixName()
	{
		return AffixName;
	}
	public void setAffixName(String aAffixName)
	{
		AffixName = aAffixName;
	}
	/**
	* 0 原件<p>
	* 1 复印件
	*/
	public String getProperty()
	{
		return Property;
	}
	public void setProperty(String aProperty)
	{
		Property = aProperty;
	}
	public int getReadyCount()
	{
		return ReadyCount;
	}
	public void setReadyCount(int aReadyCount)
	{
		ReadyCount = aReadyCount;
	}
	public void setReadyCount(String aReadyCount)
	{
		if (aReadyCount != null && !aReadyCount.equals(""))
		{
			Integer tInteger = new Integer(aReadyCount);
			int i = tInteger.intValue();
			ReadyCount = i;
		}
	}

	public int getShortCount()
	{
		return ShortCount;
	}
	public void setShortCount(int aShortCount)
	{
		ShortCount = aShortCount;
	}
	public void setShortCount(String aShortCount)
	{
		if (aShortCount != null && !aShortCount.equals(""))
		{
			Integer tInteger = new Integer(aShortCount);
			int i = tInteger.intValue();
			ShortCount = i;
		}
	}

	public String getSupplyDate()
	{
		if( SupplyDate != null )
			return fDate.getString(SupplyDate);
		else
			return null;
	}
	public void setSupplyDate(Date aSupplyDate)
	{
		SupplyDate = aSupplyDate;
	}
	public void setSupplyDate(String aSupplyDate)
	{
		if (aSupplyDate != null && !aSupplyDate.equals("") )
		{
			SupplyDate = fDate.getDate( aSupplyDate );
		}
		else
			SupplyDate = null;
	}

	public String getFileStartDate()
	{
		if( FileStartDate != null )
			return fDate.getString(FileStartDate);
		else
			return null;
	}
	public void setFileStartDate(Date aFileStartDate)
	{
		FileStartDate = aFileStartDate;
	}
	public void setFileStartDate(String aFileStartDate)
	{
		if (aFileStartDate != null && !aFileStartDate.equals("") )
		{
			FileStartDate = fDate.getDate( aFileStartDate );
		}
		else
			FileStartDate = null;
	}

	public String getFileEndDate()
	{
		if( FileEndDate != null )
			return fDate.getString(FileEndDate);
		else
			return null;
	}
	public void setFileEndDate(Date aFileEndDate)
	{
		FileEndDate = aFileEndDate;
	}
	public void setFileEndDate(String aFileEndDate)
	{
		if (aFileEndDate != null && !aFileEndDate.equals("") )
		{
			FileEndDate = fDate.getDate( aFileEndDate );
		}
		else
			FileEndDate = null;
	}

	public String getFileSummary()
	{
		return FileSummary;
	}
	public void setFileSummary(String aFileSummary)
	{
		FileSummary = aFileSummary;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
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
	* 0齐全<p>
	* null 或1不齐全
	*/
	public String getAffixConclusion()
	{
		return AffixConclusion;
	}
	public void setAffixConclusion(String aAffixConclusion)
	{
		AffixConclusion = aAffixConclusion;
	}
	public String getAffixReason()
	{
		return AffixReason;
	}
	public void setAffixReason(String aAffixReason)
	{
		AffixReason = aAffixReason;
	}
	/**
	* 0是<p>
	* 1否
	*/
	public String getNeedFlag()
	{
		return NeedFlag;
	}
	public void setNeedFlag(String aNeedFlag)
	{
		NeedFlag = aNeedFlag;
	}
	/**
	* 0是<p>
	* 1否
	*/
	public String getSubFlag()
	{
		return SubFlag;
	}
	public void setSubFlag(String aSubFlag)
	{
		SubFlag = aSubFlag;
	}
	/**
	* 0是<p>
	* 1否
	*/
	public String getReturnFlag()
	{
		return ReturnFlag;
	}
	public void setReturnFlag(String aReturnFlag)
	{
		ReturnFlag = aReturnFlag;
	}
	public String getSupplyStage()
	{
		return SupplyStage;
	}
	public void setSupplyStage(String aSupplyStage)
	{
		SupplyStage = aSupplyStage;
	}
	public String getReAffixDate()
	{
		if( ReAffixDate != null )
			return fDate.getString(ReAffixDate);
		else
			return null;
	}
	public void setReAffixDate(Date aReAffixDate)
	{
		ReAffixDate = aReAffixDate;
	}
	public void setReAffixDate(String aReAffixDate)
	{
		if (aReAffixDate != null && !aReAffixDate.equals("") )
		{
			ReAffixDate = fDate.getDate( aReAffixDate );
		}
		else
			ReAffixDate = null;
	}

	public String getAffixState()
	{
		return AffixState;
	}
	public void setAffixState(String aAffixState)
	{
		AffixState = aAffixState;
	}

	/**
	* 使用另外一个 LLAffixSchema 对象给 Schema 赋值
	* @param: aLLAffixSchema LLAffixSchema
	**/
	public void setSchema(LLAffixSchema aLLAffixSchema)
	{
		this.RgtNo = aLLAffixSchema.getRgtNo();
		this.CustomerNo = aLLAffixSchema.getCustomerNo();
		this.AffixNo = aLLAffixSchema.getAffixNo();
		this.SerialNo = aLLAffixSchema.getSerialNo();
		this.CaseNo = aLLAffixSchema.getCaseNo();
		this.ReasonCode = aLLAffixSchema.getReasonCode();
		this.AffixType = aLLAffixSchema.getAffixType();
		this.AffixCode = aLLAffixSchema.getAffixCode();
		this.AffixName = aLLAffixSchema.getAffixName();
		this.Property = aLLAffixSchema.getProperty();
		this.ReadyCount = aLLAffixSchema.getReadyCount();
		this.ShortCount = aLLAffixSchema.getShortCount();
		this.SupplyDate = fDate.getDate( aLLAffixSchema.getSupplyDate());
		this.FileStartDate = fDate.getDate( aLLAffixSchema.getFileStartDate());
		this.FileEndDate = fDate.getDate( aLLAffixSchema.getFileEndDate());
		this.FileSummary = aLLAffixSchema.getFileSummary();
		this.Remark = aLLAffixSchema.getRemark();
		this.MngCom = aLLAffixSchema.getMngCom();
		this.Operator = aLLAffixSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLAffixSchema.getMakeDate());
		this.MakeTime = aLLAffixSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLAffixSchema.getModifyDate());
		this.ModifyTime = aLLAffixSchema.getModifyTime();
		this.AffixConclusion = aLLAffixSchema.getAffixConclusion();
		this.AffixReason = aLLAffixSchema.getAffixReason();
		this.NeedFlag = aLLAffixSchema.getNeedFlag();
		this.SubFlag = aLLAffixSchema.getSubFlag();
		this.ReturnFlag = aLLAffixSchema.getReturnFlag();
		this.SupplyStage = aLLAffixSchema.getSupplyStage();
		this.ReAffixDate = fDate.getDate( aLLAffixSchema.getReAffixDate());
		this.AffixState = aLLAffixSchema.getAffixState();
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
			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AffixNo") == null )
				this.AffixNo = null;
			else
				this.AffixNo = rs.getString("AffixNo").trim();

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("ReasonCode") == null )
				this.ReasonCode = null;
			else
				this.ReasonCode = rs.getString("ReasonCode").trim();

			if( rs.getString("AffixType") == null )
				this.AffixType = null;
			else
				this.AffixType = rs.getString("AffixType").trim();

			if( rs.getString("AffixCode") == null )
				this.AffixCode = null;
			else
				this.AffixCode = rs.getString("AffixCode").trim();

			if( rs.getString("AffixName") == null )
				this.AffixName = null;
			else
				this.AffixName = rs.getString("AffixName").trim();

			if( rs.getString("Property") == null )
				this.Property = null;
			else
				this.Property = rs.getString("Property").trim();

			this.ReadyCount = rs.getInt("ReadyCount");
			this.ShortCount = rs.getInt("ShortCount");
			this.SupplyDate = rs.getDate("SupplyDate");
			this.FileStartDate = rs.getDate("FileStartDate");
			this.FileEndDate = rs.getDate("FileEndDate");
			if( rs.getString("FileSummary") == null )
				this.FileSummary = null;
			else
				this.FileSummary = rs.getString("FileSummary").trim();

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

			if( rs.getString("AffixConclusion") == null )
				this.AffixConclusion = null;
			else
				this.AffixConclusion = rs.getString("AffixConclusion").trim();

			if( rs.getString("AffixReason") == null )
				this.AffixReason = null;
			else
				this.AffixReason = rs.getString("AffixReason").trim();

			if( rs.getString("NeedFlag") == null )
				this.NeedFlag = null;
			else
				this.NeedFlag = rs.getString("NeedFlag").trim();

			if( rs.getString("SubFlag") == null )
				this.SubFlag = null;
			else
				this.SubFlag = rs.getString("SubFlag").trim();

			if( rs.getString("ReturnFlag") == null )
				this.ReturnFlag = null;
			else
				this.ReturnFlag = rs.getString("ReturnFlag").trim();

			if( rs.getString("SupplyStage") == null )
				this.SupplyStage = null;
			else
				this.SupplyStage = rs.getString("SupplyStage").trim();

			this.ReAffixDate = rs.getDate("ReAffixDate");
			if( rs.getString("AffixState") == null )
				this.AffixState = null;
			else
				this.AffixState = rs.getString("AffixState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAffix表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAffixSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAffixSchema getSchema()
	{
		LLAffixSchema aLLAffixSchema = new LLAffixSchema();
		aLLAffixSchema.setSchema(this);
		return aLLAffixSchema;
	}

	public LLAffixDB getDB()
	{
		LLAffixDB aDBOper = new LLAffixDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAffix描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Property)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReadyCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShortCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SupplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FileStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FileEndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileSummary)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SupplyStage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReAffixDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAffix>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AffixNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AffixType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AffixName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Property = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ReadyCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			ShortCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			SupplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			FileStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			FileEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			FileSummary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			AffixConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AffixReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			NeedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			SubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ReturnFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			SupplyStage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ReAffixDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			AffixState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAffixSchema";
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("AffixNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonCode));
		}
		if (FCode.equalsIgnoreCase("AffixType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixType));
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixCode));
		}
		if (FCode.equalsIgnoreCase("AffixName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixName));
		}
		if (FCode.equalsIgnoreCase("Property"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Property));
		}
		if (FCode.equalsIgnoreCase("ReadyCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReadyCount));
		}
		if (FCode.equalsIgnoreCase("ShortCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShortCount));
		}
		if (FCode.equalsIgnoreCase("SupplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSupplyDate()));
		}
		if (FCode.equalsIgnoreCase("FileStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFileStartDate()));
		}
		if (FCode.equalsIgnoreCase("FileEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFileEndDate()));
		}
		if (FCode.equalsIgnoreCase("FileSummary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileSummary));
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
		if (FCode.equalsIgnoreCase("AffixConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixConclusion));
		}
		if (FCode.equalsIgnoreCase("AffixReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixReason));
		}
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedFlag));
		}
		if (FCode.equalsIgnoreCase("SubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubFlag));
		}
		if (FCode.equalsIgnoreCase("ReturnFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnFlag));
		}
		if (FCode.equalsIgnoreCase("SupplyStage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SupplyStage));
		}
		if (FCode.equalsIgnoreCase("ReAffixDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReAffixDate()));
		}
		if (FCode.equalsIgnoreCase("AffixState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AffixState));
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
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AffixNo);
				break;
			case 3:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ReasonCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AffixType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AffixCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AffixName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Property);
				break;
			case 10:
				strFieldValue = String.valueOf(ReadyCount);
				break;
			case 11:
				strFieldValue = String.valueOf(ShortCount);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSupplyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileStartDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileEndDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(FileSummary);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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
				strFieldValue = StrTool.GBKToUnicode(AffixConclusion);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AffixReason);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(NeedFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(SubFlag);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(ReturnFlag);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(SupplyStage);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReAffixDate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(AffixState);
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

		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
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
		if (FCode.equalsIgnoreCase("AffixNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixNo = FValue.trim();
			}
			else
				AffixNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonCode = FValue.trim();
			}
			else
				ReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("AffixType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixType = FValue.trim();
			}
			else
				AffixType = null;
		}
		if (FCode.equalsIgnoreCase("AffixCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixCode = FValue.trim();
			}
			else
				AffixCode = null;
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
		if (FCode.equalsIgnoreCase("Property"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Property = FValue.trim();
			}
			else
				Property = null;
		}
		if (FCode.equalsIgnoreCase("ReadyCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ReadyCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("ShortCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ShortCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("SupplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SupplyDate = fDate.getDate( FValue );
			}
			else
				SupplyDate = null;
		}
		if (FCode.equalsIgnoreCase("FileStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FileStartDate = fDate.getDate( FValue );
			}
			else
				FileStartDate = null;
		}
		if (FCode.equalsIgnoreCase("FileEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FileEndDate = fDate.getDate( FValue );
			}
			else
				FileEndDate = null;
		}
		if (FCode.equalsIgnoreCase("FileSummary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileSummary = FValue.trim();
			}
			else
				FileSummary = null;
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
		if (FCode.equalsIgnoreCase("AffixConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixConclusion = FValue.trim();
			}
			else
				AffixConclusion = null;
		}
		if (FCode.equalsIgnoreCase("AffixReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixReason = FValue.trim();
			}
			else
				AffixReason = null;
		}
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedFlag = FValue.trim();
			}
			else
				NeedFlag = null;
		}
		if (FCode.equalsIgnoreCase("SubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubFlag = FValue.trim();
			}
			else
				SubFlag = null;
		}
		if (FCode.equalsIgnoreCase("ReturnFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnFlag = FValue.trim();
			}
			else
				ReturnFlag = null;
		}
		if (FCode.equalsIgnoreCase("SupplyStage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SupplyStage = FValue.trim();
			}
			else
				SupplyStage = null;
		}
		if (FCode.equalsIgnoreCase("ReAffixDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReAffixDate = fDate.getDate( FValue );
			}
			else
				ReAffixDate = null;
		}
		if (FCode.equalsIgnoreCase("AffixState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AffixState = FValue.trim();
			}
			else
				AffixState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAffixSchema other = (LLAffixSchema)otherObject;
		return
			RgtNo.equals(other.getRgtNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AffixNo.equals(other.getAffixNo())
			&& SerialNo == other.getSerialNo()
			&& CaseNo.equals(other.getCaseNo())
			&& ReasonCode.equals(other.getReasonCode())
			&& AffixType.equals(other.getAffixType())
			&& AffixCode.equals(other.getAffixCode())
			&& AffixName.equals(other.getAffixName())
			&& Property.equals(other.getProperty())
			&& ReadyCount == other.getReadyCount()
			&& ShortCount == other.getShortCount()
			&& fDate.getString(SupplyDate).equals(other.getSupplyDate())
			&& fDate.getString(FileStartDate).equals(other.getFileStartDate())
			&& fDate.getString(FileEndDate).equals(other.getFileEndDate())
			&& FileSummary.equals(other.getFileSummary())
			&& Remark.equals(other.getRemark())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AffixConclusion.equals(other.getAffixConclusion())
			&& AffixReason.equals(other.getAffixReason())
			&& NeedFlag.equals(other.getNeedFlag())
			&& SubFlag.equals(other.getSubFlag())
			&& ReturnFlag.equals(other.getReturnFlag())
			&& SupplyStage.equals(other.getSupplyStage())
			&& fDate.getString(ReAffixDate).equals(other.getReAffixDate())
			&& AffixState.equals(other.getAffixState());
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
		if( strFieldName.equals("RgtNo") ) {
			return 0;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("AffixNo") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 4;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return 5;
		}
		if( strFieldName.equals("AffixType") ) {
			return 6;
		}
		if( strFieldName.equals("AffixCode") ) {
			return 7;
		}
		if( strFieldName.equals("AffixName") ) {
			return 8;
		}
		if( strFieldName.equals("Property") ) {
			return 9;
		}
		if( strFieldName.equals("ReadyCount") ) {
			return 10;
		}
		if( strFieldName.equals("ShortCount") ) {
			return 11;
		}
		if( strFieldName.equals("SupplyDate") ) {
			return 12;
		}
		if( strFieldName.equals("FileStartDate") ) {
			return 13;
		}
		if( strFieldName.equals("FileEndDate") ) {
			return 14;
		}
		if( strFieldName.equals("FileSummary") ) {
			return 15;
		}
		if( strFieldName.equals("Remark") ) {
			return 16;
		}
		if( strFieldName.equals("MngCom") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("AffixConclusion") ) {
			return 23;
		}
		if( strFieldName.equals("AffixReason") ) {
			return 24;
		}
		if( strFieldName.equals("NeedFlag") ) {
			return 25;
		}
		if( strFieldName.equals("SubFlag") ) {
			return 26;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return 27;
		}
		if( strFieldName.equals("SupplyStage") ) {
			return 28;
		}
		if( strFieldName.equals("ReAffixDate") ) {
			return 29;
		}
		if( strFieldName.equals("AffixState") ) {
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
				strFieldName = "RgtNo";
				break;
			case 1:
				strFieldName = "CustomerNo";
				break;
			case 2:
				strFieldName = "AffixNo";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "CaseNo";
				break;
			case 5:
				strFieldName = "ReasonCode";
				break;
			case 6:
				strFieldName = "AffixType";
				break;
			case 7:
				strFieldName = "AffixCode";
				break;
			case 8:
				strFieldName = "AffixName";
				break;
			case 9:
				strFieldName = "Property";
				break;
			case 10:
				strFieldName = "ReadyCount";
				break;
			case 11:
				strFieldName = "ShortCount";
				break;
			case 12:
				strFieldName = "SupplyDate";
				break;
			case 13:
				strFieldName = "FileStartDate";
				break;
			case 14:
				strFieldName = "FileEndDate";
				break;
			case 15:
				strFieldName = "FileSummary";
				break;
			case 16:
				strFieldName = "Remark";
				break;
			case 17:
				strFieldName = "MngCom";
				break;
			case 18:
				strFieldName = "Operator";
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
				strFieldName = "AffixConclusion";
				break;
			case 24:
				strFieldName = "AffixReason";
				break;
			case 25:
				strFieldName = "NeedFlag";
				break;
			case 26:
				strFieldName = "SubFlag";
				break;
			case 27:
				strFieldName = "ReturnFlag";
				break;
			case 28:
				strFieldName = "SupplyStage";
				break;
			case 29:
				strFieldName = "ReAffixDate";
				break;
			case 30:
				strFieldName = "AffixState";
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
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Property") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReadyCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ShortCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SupplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FileStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FileEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FileSummary") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("AffixConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SupplyStage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReAffixDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AffixState") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
