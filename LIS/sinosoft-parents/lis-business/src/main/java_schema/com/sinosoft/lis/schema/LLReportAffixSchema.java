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
import com.sinosoft.lis.db.LLReportAffixDB;

/*
 * <p>ClassName: LLReportAffixSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LLReportAffixSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLReportAffixSchema.class);
	// @Field
	/** 报案号 */
	private String RptNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 附件号码 */
	private String AffixNo;
	/** 流水号 */
	private int SerialNo;
	/** 原因代码 */
	private String ReasonCode;
	/** 附件类型 */
	private String AffixType;
	/** 附件代码 */
	private String AffixCode;
	/** 附件名称 */
	private String AffixName;
	/** 属性 */
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
	/** 是否必需 */
	private String NeedFlag;
	/** 业务号 */
	private String BusNo;
	/** 单证类别名称 */
	private String ReasonName;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLReportAffixSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RptNo";
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
		LLReportAffixSchema cloned = (LLReportAffixSchema)super.clone();
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
	public String getRptNo()
	{
		return RptNo;
	}
	public void setRptNo(String aRptNo)
	{
		RptNo = aRptNo;
	}
	/**
	* 该次咨询所关联的客户号码<p>
	* 包括被保险人；投保人；连带被保险人；被保险人的配偶；被保险人的子女；被保险人的父母等等相关连的各种类型的人员。
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/**
	* 自动生成
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
	* 【不用】
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
	* 索赔资料为报案号，1-回访附件
	*/
	public String getBusNo()
	{
		return BusNo;
	}
	public void setBusNo(String aBusNo)
	{
		BusNo = aBusNo;
	}
	public String getReasonName()
	{
		return ReasonName;
	}
	public void setReasonName(String aReasonName)
	{
		ReasonName = aReasonName;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		ModifyOperator = aModifyOperator;
	}

	/**
	* 使用另外一个 LLReportAffixSchema 对象给 Schema 赋值
	* @param: aLLReportAffixSchema LLReportAffixSchema
	**/
	public void setSchema(LLReportAffixSchema aLLReportAffixSchema)
	{
		this.RptNo = aLLReportAffixSchema.getRptNo();
		this.CustomerNo = aLLReportAffixSchema.getCustomerNo();
		this.AffixNo = aLLReportAffixSchema.getAffixNo();
		this.SerialNo = aLLReportAffixSchema.getSerialNo();
		this.ReasonCode = aLLReportAffixSchema.getReasonCode();
		this.AffixType = aLLReportAffixSchema.getAffixType();
		this.AffixCode = aLLReportAffixSchema.getAffixCode();
		this.AffixName = aLLReportAffixSchema.getAffixName();
		this.Property = aLLReportAffixSchema.getProperty();
		this.ReadyCount = aLLReportAffixSchema.getReadyCount();
		this.ShortCount = aLLReportAffixSchema.getShortCount();
		this.SupplyDate = fDate.getDate( aLLReportAffixSchema.getSupplyDate());
		this.FileStartDate = fDate.getDate( aLLReportAffixSchema.getFileStartDate());
		this.FileEndDate = fDate.getDate( aLLReportAffixSchema.getFileEndDate());
		this.FileSummary = aLLReportAffixSchema.getFileSummary();
		this.Remark = aLLReportAffixSchema.getRemark();
		this.MngCom = aLLReportAffixSchema.getMngCom();
		this.Operator = aLLReportAffixSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLReportAffixSchema.getMakeDate());
		this.MakeTime = aLLReportAffixSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLReportAffixSchema.getModifyDate());
		this.ModifyTime = aLLReportAffixSchema.getModifyTime();
		this.NeedFlag = aLLReportAffixSchema.getNeedFlag();
		this.BusNo = aLLReportAffixSchema.getBusNo();
		this.ReasonName = aLLReportAffixSchema.getReasonName();
		this.ComCode = aLLReportAffixSchema.getComCode();
		this.ModifyOperator = aLLReportAffixSchema.getModifyOperator();
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
			if( rs.getString("RptNo") == null )
				this.RptNo = null;
			else
				this.RptNo = rs.getString("RptNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("AffixNo") == null )
				this.AffixNo = null;
			else
				this.AffixNo = rs.getString("AffixNo").trim();

			this.SerialNo = rs.getInt("SerialNo");
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

			if( rs.getString("NeedFlag") == null )
				this.NeedFlag = null;
			else
				this.NeedFlag = rs.getString("NeedFlag").trim();

			if( rs.getString("BusNo") == null )
				this.BusNo = null;
			else
				this.BusNo = rs.getString("BusNo").trim();

			if( rs.getString("ReasonName") == null )
				this.ReasonName = null;
			else
				this.ReasonName = rs.getString("ReasonName").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLReportAffix表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportAffixSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLReportAffixSchema getSchema()
	{
		LLReportAffixSchema aLLReportAffixSchema = new LLReportAffixSchema();
		aLLReportAffixSchema.setSchema(this);
		return aLLReportAffixSchema;
	}

	public LLReportAffixDB getDB()
	{
		LLReportAffixDB aDBOper = new LLReportAffixDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLReportAffix描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AffixNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(NeedFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLReportAffix>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AffixNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AffixType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AffixName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Property = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ReadyCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			ShortCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			SupplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			FileStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			FileEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			FileSummary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			NeedFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			BusNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ReasonName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLReportAffixSchema";
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
		if (FCode.equalsIgnoreCase("RptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RptNo));
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
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedFlag));
		}
		if (FCode.equalsIgnoreCase("BusNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusNo));
		}
		if (FCode.equalsIgnoreCase("ReasonName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonName));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
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
				strFieldValue = StrTool.GBKToUnicode(RptNo);
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
				strFieldValue = StrTool.GBKToUnicode(ReasonCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AffixType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AffixCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AffixName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Property);
				break;
			case 9:
				strFieldValue = String.valueOf(ReadyCount);
				break;
			case 10:
				strFieldValue = String.valueOf(ShortCount);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSupplyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileStartDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileEndDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(FileSummary);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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
			case 22:
				strFieldValue = StrTool.GBKToUnicode(NeedFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(BusNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ReasonName);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 26:
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

		if (FCode.equalsIgnoreCase("RptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RptNo = FValue.trim();
			}
			else
				RptNo = null;
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
		if (FCode.equalsIgnoreCase("NeedFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedFlag = FValue.trim();
			}
			else
				NeedFlag = null;
		}
		if (FCode.equalsIgnoreCase("BusNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusNo = FValue.trim();
			}
			else
				BusNo = null;
		}
		if (FCode.equalsIgnoreCase("ReasonName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonName = FValue.trim();
			}
			else
				ReasonName = null;
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
		LLReportAffixSchema other = (LLReportAffixSchema)otherObject;
		return
			RptNo.equals(other.getRptNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& AffixNo.equals(other.getAffixNo())
			&& SerialNo == other.getSerialNo()
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
			&& NeedFlag.equals(other.getNeedFlag())
			&& BusNo.equals(other.getBusNo())
			&& ReasonName.equals(other.getReasonName())
			&& ComCode.equals(other.getComCode())
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
		if( strFieldName.equals("RptNo") ) {
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
		if( strFieldName.equals("ReasonCode") ) {
			return 4;
		}
		if( strFieldName.equals("AffixType") ) {
			return 5;
		}
		if( strFieldName.equals("AffixCode") ) {
			return 6;
		}
		if( strFieldName.equals("AffixName") ) {
			return 7;
		}
		if( strFieldName.equals("Property") ) {
			return 8;
		}
		if( strFieldName.equals("ReadyCount") ) {
			return 9;
		}
		if( strFieldName.equals("ShortCount") ) {
			return 10;
		}
		if( strFieldName.equals("SupplyDate") ) {
			return 11;
		}
		if( strFieldName.equals("FileStartDate") ) {
			return 12;
		}
		if( strFieldName.equals("FileEndDate") ) {
			return 13;
		}
		if( strFieldName.equals("FileSummary") ) {
			return 14;
		}
		if( strFieldName.equals("Remark") ) {
			return 15;
		}
		if( strFieldName.equals("MngCom") ) {
			return 16;
		}
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("NeedFlag") ) {
			return 22;
		}
		if( strFieldName.equals("BusNo") ) {
			return 23;
		}
		if( strFieldName.equals("ReasonName") ) {
			return 24;
		}
		if( strFieldName.equals("ComCode") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "RptNo";
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
				strFieldName = "ReasonCode";
				break;
			case 5:
				strFieldName = "AffixType";
				break;
			case 6:
				strFieldName = "AffixCode";
				break;
			case 7:
				strFieldName = "AffixName";
				break;
			case 8:
				strFieldName = "Property";
				break;
			case 9:
				strFieldName = "ReadyCount";
				break;
			case 10:
				strFieldName = "ShortCount";
				break;
			case 11:
				strFieldName = "SupplyDate";
				break;
			case 12:
				strFieldName = "FileStartDate";
				break;
			case 13:
				strFieldName = "FileEndDate";
				break;
			case 14:
				strFieldName = "FileSummary";
				break;
			case 15:
				strFieldName = "Remark";
				break;
			case 16:
				strFieldName = "MngCom";
				break;
			case 17:
				strFieldName = "Operator";
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
			case 22:
				strFieldName = "NeedFlag";
				break;
			case 23:
				strFieldName = "BusNo";
				break;
			case 24:
				strFieldName = "ReasonName";
				break;
			case 25:
				strFieldName = "ComCode";
				break;
			case 26:
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
		if( strFieldName.equals("RptNo") ) {
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
		if( strFieldName.equals("NeedFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
