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
import com.sinosoft.lis.db.LLConsultDB;

/*
 * <p>ClassName: LLConsultSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLConsultSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLConsultSchema.class);
	// @Field
	/** 咨询号码 */
	private String ConsultNo;
	/** 登记号码 */
	private String LogNo;
	/** 关联客户号码 */
	private String CustomerNo;
	/** 关联客户的名称 */
	private String CustomerName;
	/** 关联客户身份 */
	private String CustomerType;
	/** 咨询主题 */
	private String CSubject;
	/** 咨询内容 */
	private String CContent;
	/** 回复状态 */
	private String ReplyState;
	/** 咨询级别 */
	private String AskGrade;
	/** 是否咨询专家 */
	private String ExpertFlag;
	/** 专家编号 */
	private String ExpertNo;
	/** 专家姓名 */
	private String ExpertName;
	/** 期待回复日期 */
	private Date ExpRDate;
	/** 期望回复时间 */
	private String ExpRTime;
	/** 疾病代码 */
	private String DiseaseCode;
	/** 意外代码 */
	private String AccCode;
	/** 疾病描述 */
	private String DiseaseDesc;
	/** 意外描述 */
	private String AccDesc;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 入院日期 */
	private Date InHospitalDate;
	/** 出院日期 */
	private Date OutHospitalDate;
	/** 客户现状 */
	private String CustStatus;
	/** 备注 */
	private String Remark;
	/** 咨询有效标志 */
	private String AvaiFlag;
	/** 咨询有效原因 */
	private String AvaliReason;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLConsultSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ConsultNo";

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
		LLConsultSchema cloned = (LLConsultSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getConsultNo()
	{
		return ConsultNo;
	}
	public void setConsultNo(String aConsultNo)
	{
		if(aConsultNo!=null && aConsultNo.length()>20)
			throw new IllegalArgumentException("咨询号码ConsultNo值"+aConsultNo+"的长度"+aConsultNo.length()+"大于最大值20");
		ConsultNo = aConsultNo;
	}
	/**
	* 登记流水号码
	*/
	public String getLogNo()
	{
		return LogNo;
	}
	public void setLogNo(String aLogNo)
	{
		if(aLogNo!=null && aLogNo.length()>20)
			throw new IllegalArgumentException("登记号码LogNo值"+aLogNo+"的长度"+aLogNo.length()+"大于最大值20");
		LogNo = aLogNo;
	}
	/**
	* 该次咨询所关联的客户号码 包括被保险人；投保人；连带被保险人；被保险人的配偶；被保险人的子女；被保险人的父母等等相关连的各种类型的人员。
	*/
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("关联客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("关联客户的名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
		CustomerName = aCustomerName;
	}
	/**
	* 0-主被保险人； 1-投保人； 2-连带被保险人； 3-主被保人配偶； 4-主被保险人子女； 5-主被保险人父母
	*/
	public String getCustomerType()
	{
		return CustomerType;
	}
	public void setCustomerType(String aCustomerType)
	{
		if(aCustomerType!=null && aCustomerType.length()>20)
			throw new IllegalArgumentException("关联客户身份CustomerType值"+aCustomerType+"的长度"+aCustomerType.length()+"大于最大值20");
		CustomerType = aCustomerType;
	}
	public String getCSubject()
	{
		return CSubject;
	}
	public void setCSubject(String aCSubject)
	{
		if(aCSubject!=null && aCSubject.length()>600)
			throw new IllegalArgumentException("咨询主题CSubject值"+aCSubject+"的长度"+aCSubject.length()+"大于最大值600");
		CSubject = aCSubject;
	}
	public String getCContent()
	{
		return CContent;
	}
	public void setCContent(String aCContent)
	{
		if(aCContent!=null && aCContent.length()>2000)
			throw new IllegalArgumentException("咨询内容CContent值"+aCContent+"的长度"+aCContent.length()+"大于最大值2000");
		CContent = aCContent;
	}
	/**
	* 0-提起询问，未进行回复 1-该询问已经回复，但不完全，可以对其修改； 2-该询问已经进行了完整的回复 （针对每个询问记录的状态）
	*/
	public String getReplyState()
	{
		return ReplyState;
	}
	public void setReplyState(String aReplyState)
	{
		if(aReplyState!=null && aReplyState.length()>1)
			throw new IllegalArgumentException("回复状态ReplyState值"+aReplyState+"的长度"+aReplyState.length()+"大于最大值1");
		ReplyState = aReplyState;
	}
	/**
	* 1-一级 2-二级 3-三级 具体的级别待定，可以对其进行扩充。
	*/
	public String getAskGrade()
	{
		return AskGrade;
	}
	public void setAskGrade(String aAskGrade)
	{
		if(aAskGrade!=null && aAskGrade.length()>1)
			throw new IllegalArgumentException("咨询级别AskGrade值"+aAskGrade+"的长度"+aAskGrade.length()+"大于最大值1");
		AskGrade = aAskGrade;
	}
	public String getExpertFlag()
	{
		return ExpertFlag;
	}
	public void setExpertFlag(String aExpertFlag)
	{
		if(aExpertFlag!=null && aExpertFlag.length()>2)
			throw new IllegalArgumentException("是否咨询专家ExpertFlag值"+aExpertFlag+"的长度"+aExpertFlag.length()+"大于最大值2");
		ExpertFlag = aExpertFlag;
	}
	/**
	* 关联医师表的医师字段
	*/
	public String getExpertNo()
	{
		return ExpertNo;
	}
	public void setExpertNo(String aExpertNo)
	{
		if(aExpertNo!=null && aExpertNo.length()>20)
			throw new IllegalArgumentException("专家编号ExpertNo值"+aExpertNo+"的长度"+aExpertNo.length()+"大于最大值20");
		ExpertNo = aExpertNo;
	}
	public String getExpertName()
	{
		return ExpertName;
	}
	public void setExpertName(String aExpertName)
	{
		if(aExpertName!=null && aExpertName.length()>20)
			throw new IllegalArgumentException("专家姓名ExpertName值"+aExpertName+"的长度"+aExpertName.length()+"大于最大值20");
		ExpertName = aExpertName;
	}
	public String getExpRDate()
	{
		if( ExpRDate != null )
			return fDate.getString(ExpRDate);
		else
			return null;
	}
	public void setExpRDate(Date aExpRDate)
	{
		ExpRDate = aExpRDate;
	}
	public void setExpRDate(String aExpRDate)
	{
		if (aExpRDate != null && !aExpRDate.equals("") )
		{
			ExpRDate = fDate.getDate( aExpRDate );
		}
		else
			ExpRDate = null;
	}

	public String getExpRTime()
	{
		return ExpRTime;
	}
	public void setExpRTime(String aExpRTime)
	{
		if(aExpRTime!=null && aExpRTime.length()>8)
			throw new IllegalArgumentException("期望回复时间ExpRTime值"+aExpRTime+"的长度"+aExpRTime.length()+"大于最大值8");
		ExpRTime = aExpRTime;
	}
	public String getDiseaseCode()
	{
		return DiseaseCode;
	}
	public void setDiseaseCode(String aDiseaseCode)
	{
		if(aDiseaseCode!=null && aDiseaseCode.length()>6)
			throw new IllegalArgumentException("疾病代码DiseaseCode值"+aDiseaseCode+"的长度"+aDiseaseCode.length()+"大于最大值6");
		DiseaseCode = aDiseaseCode;
	}
	public String getAccCode()
	{
		return AccCode;
	}
	public void setAccCode(String aAccCode)
	{
		if(aAccCode!=null && aAccCode.length()>6)
			throw new IllegalArgumentException("意外代码AccCode值"+aAccCode+"的长度"+aAccCode.length()+"大于最大值6");
		AccCode = aAccCode;
	}
	public String getDiseaseDesc()
	{
		return DiseaseDesc;
	}
	public void setDiseaseDesc(String aDiseaseDesc)
	{
		if(aDiseaseDesc!=null && aDiseaseDesc.length()>1000)
			throw new IllegalArgumentException("疾病描述DiseaseDesc值"+aDiseaseDesc+"的长度"+aDiseaseDesc.length()+"大于最大值1000");
		DiseaseDesc = aDiseaseDesc;
	}
	public String getAccDesc()
	{
		return AccDesc;
	}
	public void setAccDesc(String aAccDesc)
	{
		if(aAccDesc!=null && aAccDesc.length()>1000)
			throw new IllegalArgumentException("意外描述AccDesc值"+aAccDesc+"的长度"+aAccDesc.length()+"大于最大值1000");
		AccDesc = aAccDesc;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		if(aHospitalCode!=null && aHospitalCode.length()>10)
			throw new IllegalArgumentException("医院代码HospitalCode值"+aHospitalCode+"的长度"+aHospitalCode.length()+"大于最大值10");
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		if(aHospitalName!=null && aHospitalName.length()>60)
			throw new IllegalArgumentException("医院名称HospitalName值"+aHospitalName+"的长度"+aHospitalName.length()+"大于最大值60");
		HospitalName = aHospitalName;
	}
	public String getInHospitalDate()
	{
		if( InHospitalDate != null )
			return fDate.getString(InHospitalDate);
		else
			return null;
	}
	public void setInHospitalDate(Date aInHospitalDate)
	{
		InHospitalDate = aInHospitalDate;
	}
	public void setInHospitalDate(String aInHospitalDate)
	{
		if (aInHospitalDate != null && !aInHospitalDate.equals("") )
		{
			InHospitalDate = fDate.getDate( aInHospitalDate );
		}
		else
			InHospitalDate = null;
	}

	public String getOutHospitalDate()
	{
		if( OutHospitalDate != null )
			return fDate.getString(OutHospitalDate);
		else
			return null;
	}
	public void setOutHospitalDate(Date aOutHospitalDate)
	{
		OutHospitalDate = aOutHospitalDate;
	}
	public void setOutHospitalDate(String aOutHospitalDate)
	{
		if (aOutHospitalDate != null && !aOutHospitalDate.equals("") )
		{
			OutHospitalDate = fDate.getDate( aOutHospitalDate );
		}
		else
			OutHospitalDate = null;
	}

	public String getCustStatus()
	{
		return CustStatus;
	}
	public void setCustStatus(String aCustStatus)
	{
		if(aCustStatus!=null && aCustStatus.length()>2)
			throw new IllegalArgumentException("客户现状CustStatus值"+aCustStatus+"的长度"+aCustStatus.length()+"大于最大值2");
		CustStatus = aCustStatus;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>600)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值600");
		Remark = aRemark;
	}
	public String getAvaiFlag()
	{
		return AvaiFlag;
	}
	public void setAvaiFlag(String aAvaiFlag)
	{
		if(aAvaiFlag!=null && aAvaiFlag.length()>1)
			throw new IllegalArgumentException("咨询有效标志AvaiFlag值"+aAvaiFlag+"的长度"+aAvaiFlag.length()+"大于最大值1");
		AvaiFlag = aAvaiFlag;
	}
	public String getAvaliReason()
	{
		return AvaliReason;
	}
	public void setAvaliReason(String aAvaliReason)
	{
		if(aAvaliReason!=null && aAvaliReason.length()>200)
			throw new IllegalArgumentException("咨询有效原因AvaliReason值"+aAvaliReason+"的长度"+aAvaliReason.length()+"大于最大值200");
		AvaliReason = aAvaliReason;
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
	* 使用另外一个 LLConsultSchema 对象给 Schema 赋值
	* @param: aLLConsultSchema LLConsultSchema
	**/
	public void setSchema(LLConsultSchema aLLConsultSchema)
	{
		this.ConsultNo = aLLConsultSchema.getConsultNo();
		this.LogNo = aLLConsultSchema.getLogNo();
		this.CustomerNo = aLLConsultSchema.getCustomerNo();
		this.CustomerName = aLLConsultSchema.getCustomerName();
		this.CustomerType = aLLConsultSchema.getCustomerType();
		this.CSubject = aLLConsultSchema.getCSubject();
		this.CContent = aLLConsultSchema.getCContent();
		this.ReplyState = aLLConsultSchema.getReplyState();
		this.AskGrade = aLLConsultSchema.getAskGrade();
		this.ExpertFlag = aLLConsultSchema.getExpertFlag();
		this.ExpertNo = aLLConsultSchema.getExpertNo();
		this.ExpertName = aLLConsultSchema.getExpertName();
		this.ExpRDate = fDate.getDate( aLLConsultSchema.getExpRDate());
		this.ExpRTime = aLLConsultSchema.getExpRTime();
		this.DiseaseCode = aLLConsultSchema.getDiseaseCode();
		this.AccCode = aLLConsultSchema.getAccCode();
		this.DiseaseDesc = aLLConsultSchema.getDiseaseDesc();
		this.AccDesc = aLLConsultSchema.getAccDesc();
		this.HospitalCode = aLLConsultSchema.getHospitalCode();
		this.HospitalName = aLLConsultSchema.getHospitalName();
		this.InHospitalDate = fDate.getDate( aLLConsultSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLConsultSchema.getOutHospitalDate());
		this.CustStatus = aLLConsultSchema.getCustStatus();
		this.Remark = aLLConsultSchema.getRemark();
		this.AvaiFlag = aLLConsultSchema.getAvaiFlag();
		this.AvaliReason = aLLConsultSchema.getAvaliReason();
		this.Operator = aLLConsultSchema.getOperator();
		this.MngCom = aLLConsultSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLConsultSchema.getMakeDate());
		this.MakeTime = aLLConsultSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLConsultSchema.getModifyDate());
		this.ModifyTime = aLLConsultSchema.getModifyTime();
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
			if( rs.getString("ConsultNo") == null )
				this.ConsultNo = null;
			else
				this.ConsultNo = rs.getString("ConsultNo").trim();

			if( rs.getString("LogNo") == null )
				this.LogNo = null;
			else
				this.LogNo = rs.getString("LogNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("CustomerType") == null )
				this.CustomerType = null;
			else
				this.CustomerType = rs.getString("CustomerType").trim();

			if( rs.getString("CSubject") == null )
				this.CSubject = null;
			else
				this.CSubject = rs.getString("CSubject").trim();

			if( rs.getString("CContent") == null )
				this.CContent = null;
			else
				this.CContent = rs.getString("CContent").trim();

			if( rs.getString("ReplyState") == null )
				this.ReplyState = null;
			else
				this.ReplyState = rs.getString("ReplyState").trim();

			if( rs.getString("AskGrade") == null )
				this.AskGrade = null;
			else
				this.AskGrade = rs.getString("AskGrade").trim();

			if( rs.getString("ExpertFlag") == null )
				this.ExpertFlag = null;
			else
				this.ExpertFlag = rs.getString("ExpertFlag").trim();

			if( rs.getString("ExpertNo") == null )
				this.ExpertNo = null;
			else
				this.ExpertNo = rs.getString("ExpertNo").trim();

			if( rs.getString("ExpertName") == null )
				this.ExpertName = null;
			else
				this.ExpertName = rs.getString("ExpertName").trim();

			this.ExpRDate = rs.getDate("ExpRDate");
			if( rs.getString("ExpRTime") == null )
				this.ExpRTime = null;
			else
				this.ExpRTime = rs.getString("ExpRTime").trim();

			if( rs.getString("DiseaseCode") == null )
				this.DiseaseCode = null;
			else
				this.DiseaseCode = rs.getString("DiseaseCode").trim();

			if( rs.getString("AccCode") == null )
				this.AccCode = null;
			else
				this.AccCode = rs.getString("AccCode").trim();

			if( rs.getString("DiseaseDesc") == null )
				this.DiseaseDesc = null;
			else
				this.DiseaseDesc = rs.getString("DiseaseDesc").trim();

			if( rs.getString("AccDesc") == null )
				this.AccDesc = null;
			else
				this.AccDesc = rs.getString("AccDesc").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			this.InHospitalDate = rs.getDate("InHospitalDate");
			this.OutHospitalDate = rs.getDate("OutHospitalDate");
			if( rs.getString("CustStatus") == null )
				this.CustStatus = null;
			else
				this.CustStatus = rs.getString("CustStatus").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("AvaiFlag") == null )
				this.AvaiFlag = null;
			else
				this.AvaiFlag = rs.getString("AvaiFlag").trim();

			if( rs.getString("AvaliReason") == null )
				this.AvaliReason = null;
			else
				this.AvaliReason = rs.getString("AvaliReason").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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
			logger.debug("数据库中的LLConsult表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLConsultSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLConsultSchema getSchema()
	{
		LLConsultSchema aLLConsultSchema = new LLConsultSchema();
		aLLConsultSchema.setSchema(this);
		return aLLConsultSchema;
	}

	public LLConsultDB getDB()
	{
		LLConsultDB aDBOper = new LLConsultDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLConsult描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ConsultNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CSubject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReplyState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AskGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpertFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpertNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpertName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExpRDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpRTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaiFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvaliReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLConsult>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ConsultNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			LogNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CSubject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReplyState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AskGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ExpertFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ExpertNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ExpertName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ExpRDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ExpRTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DiseaseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DiseaseDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AccDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			CustStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			AvaiFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AvaliReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLConsultSchema";
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
		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsultNo));
		}
		if (FCode.equalsIgnoreCase("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerType));
		}
		if (FCode.equalsIgnoreCase("CSubject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CSubject));
		}
		if (FCode.equalsIgnoreCase("CContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CContent));
		}
		if (FCode.equalsIgnoreCase("ReplyState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyState));
		}
		if (FCode.equalsIgnoreCase("AskGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskGrade));
		}
		if (FCode.equalsIgnoreCase("ExpertFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpertFlag));
		}
		if (FCode.equalsIgnoreCase("ExpertNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpertNo));
		}
		if (FCode.equalsIgnoreCase("ExpertName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpertName));
		}
		if (FCode.equalsIgnoreCase("ExpRDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExpRDate()));
		}
		if (FCode.equalsIgnoreCase("ExpRTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpRTime));
		}
		if (FCode.equalsIgnoreCase("DiseaseCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseCode));
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCode));
		}
		if (FCode.equalsIgnoreCase("DiseaseDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseDesc));
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccDesc));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
		}
		if (FCode.equalsIgnoreCase("CustStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustStatus));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaiFlag));
		}
		if (FCode.equalsIgnoreCase("AvaliReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvaliReason));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
				strFieldValue = StrTool.GBKToUnicode(ConsultNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(LogNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CSubject);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CContent);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ReplyState);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AskGrade);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ExpertFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ExpertNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ExpertName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExpRDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ExpRTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(DiseaseCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AccCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DiseaseDesc);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AccDesc);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(CustStatus);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(AvaiFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AvaliReason);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 31:
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

		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsultNo = FValue.trim();
			}
			else
				ConsultNo = null;
		}
		if (FCode.equalsIgnoreCase("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogNo = FValue.trim();
			}
			else
				LogNo = null;
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
		if (FCode.equalsIgnoreCase("CustomerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerType = FValue.trim();
			}
			else
				CustomerType = null;
		}
		if (FCode.equalsIgnoreCase("CSubject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CSubject = FValue.trim();
			}
			else
				CSubject = null;
		}
		if (FCode.equalsIgnoreCase("CContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CContent = FValue.trim();
			}
			else
				CContent = null;
		}
		if (FCode.equalsIgnoreCase("ReplyState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyState = FValue.trim();
			}
			else
				ReplyState = null;
		}
		if (FCode.equalsIgnoreCase("AskGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskGrade = FValue.trim();
			}
			else
				AskGrade = null;
		}
		if (FCode.equalsIgnoreCase("ExpertFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpertFlag = FValue.trim();
			}
			else
				ExpertFlag = null;
		}
		if (FCode.equalsIgnoreCase("ExpertNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpertNo = FValue.trim();
			}
			else
				ExpertNo = null;
		}
		if (FCode.equalsIgnoreCase("ExpertName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpertName = FValue.trim();
			}
			else
				ExpertName = null;
		}
		if (FCode.equalsIgnoreCase("ExpRDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExpRDate = fDate.getDate( FValue );
			}
			else
				ExpRDate = null;
		}
		if (FCode.equalsIgnoreCase("ExpRTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpRTime = FValue.trim();
			}
			else
				ExpRTime = null;
		}
		if (FCode.equalsIgnoreCase("DiseaseCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaseCode = FValue.trim();
			}
			else
				DiseaseCode = null;
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCode = FValue.trim();
			}
			else
				AccCode = null;
		}
		if (FCode.equalsIgnoreCase("DiseaseDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaseDesc = FValue.trim();
			}
			else
				DiseaseDesc = null;
		}
		if (FCode.equalsIgnoreCase("AccDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccDesc = FValue.trim();
			}
			else
				AccDesc = null;
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalCode = FValue.trim();
			}
			else
				HospitalCode = null;
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalName = FValue.trim();
			}
			else
				HospitalName = null;
		}
		if (FCode.equalsIgnoreCase("InHospitalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InHospitalDate = fDate.getDate( FValue );
			}
			else
				InHospitalDate = null;
		}
		if (FCode.equalsIgnoreCase("OutHospitalDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutHospitalDate = fDate.getDate( FValue );
			}
			else
				OutHospitalDate = null;
		}
		if (FCode.equalsIgnoreCase("CustStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustStatus = FValue.trim();
			}
			else
				CustStatus = null;
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
		if (FCode.equalsIgnoreCase("AvaiFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaiFlag = FValue.trim();
			}
			else
				AvaiFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvaliReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvaliReason = FValue.trim();
			}
			else
				AvaliReason = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		LLConsultSchema other = (LLConsultSchema)otherObject;
		return
			ConsultNo.equals(other.getConsultNo())
			&& LogNo.equals(other.getLogNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& CustomerType.equals(other.getCustomerType())
			&& CSubject.equals(other.getCSubject())
			&& CContent.equals(other.getCContent())
			&& ReplyState.equals(other.getReplyState())
			&& AskGrade.equals(other.getAskGrade())
			&& ExpertFlag.equals(other.getExpertFlag())
			&& ExpertNo.equals(other.getExpertNo())
			&& ExpertName.equals(other.getExpertName())
			&& fDate.getString(ExpRDate).equals(other.getExpRDate())
			&& ExpRTime.equals(other.getExpRTime())
			&& DiseaseCode.equals(other.getDiseaseCode())
			&& AccCode.equals(other.getAccCode())
			&& DiseaseDesc.equals(other.getDiseaseDesc())
			&& AccDesc.equals(other.getAccDesc())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& fDate.getString(InHospitalDate).equals(other.getInHospitalDate())
			&& fDate.getString(OutHospitalDate).equals(other.getOutHospitalDate())
			&& CustStatus.equals(other.getCustStatus())
			&& Remark.equals(other.getRemark())
			&& AvaiFlag.equals(other.getAvaiFlag())
			&& AvaliReason.equals(other.getAvaliReason())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
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
		if( strFieldName.equals("ConsultNo") ) {
			return 0;
		}
		if( strFieldName.equals("LogNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerType") ) {
			return 4;
		}
		if( strFieldName.equals("CSubject") ) {
			return 5;
		}
		if( strFieldName.equals("CContent") ) {
			return 6;
		}
		if( strFieldName.equals("ReplyState") ) {
			return 7;
		}
		if( strFieldName.equals("AskGrade") ) {
			return 8;
		}
		if( strFieldName.equals("ExpertFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ExpertNo") ) {
			return 10;
		}
		if( strFieldName.equals("ExpertName") ) {
			return 11;
		}
		if( strFieldName.equals("ExpRDate") ) {
			return 12;
		}
		if( strFieldName.equals("ExpRTime") ) {
			return 13;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return 14;
		}
		if( strFieldName.equals("AccCode") ) {
			return 15;
		}
		if( strFieldName.equals("DiseaseDesc") ) {
			return 16;
		}
		if( strFieldName.equals("AccDesc") ) {
			return 17;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 18;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 19;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 20;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 21;
		}
		if( strFieldName.equals("CustStatus") ) {
			return 22;
		}
		if( strFieldName.equals("Remark") ) {
			return 23;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return 24;
		}
		if( strFieldName.equals("AvaliReason") ) {
			return 25;
		}
		if( strFieldName.equals("Operator") ) {
			return 26;
		}
		if( strFieldName.equals("MngCom") ) {
			return 27;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 28;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 29;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 31;
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
				strFieldName = "ConsultNo";
				break;
			case 1:
				strFieldName = "LogNo";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "CustomerName";
				break;
			case 4:
				strFieldName = "CustomerType";
				break;
			case 5:
				strFieldName = "CSubject";
				break;
			case 6:
				strFieldName = "CContent";
				break;
			case 7:
				strFieldName = "ReplyState";
				break;
			case 8:
				strFieldName = "AskGrade";
				break;
			case 9:
				strFieldName = "ExpertFlag";
				break;
			case 10:
				strFieldName = "ExpertNo";
				break;
			case 11:
				strFieldName = "ExpertName";
				break;
			case 12:
				strFieldName = "ExpRDate";
				break;
			case 13:
				strFieldName = "ExpRTime";
				break;
			case 14:
				strFieldName = "DiseaseCode";
				break;
			case 15:
				strFieldName = "AccCode";
				break;
			case 16:
				strFieldName = "DiseaseDesc";
				break;
			case 17:
				strFieldName = "AccDesc";
				break;
			case 18:
				strFieldName = "HospitalCode";
				break;
			case 19:
				strFieldName = "HospitalName";
				break;
			case 20:
				strFieldName = "InHospitalDate";
				break;
			case 21:
				strFieldName = "OutHospitalDate";
				break;
			case 22:
				strFieldName = "CustStatus";
				break;
			case 23:
				strFieldName = "Remark";
				break;
			case 24:
				strFieldName = "AvaiFlag";
				break;
			case 25:
				strFieldName = "AvaliReason";
				break;
			case 26:
				strFieldName = "Operator";
				break;
			case 27:
				strFieldName = "MngCom";
				break;
			case 28:
				strFieldName = "MakeDate";
				break;
			case 29:
				strFieldName = "MakeTime";
				break;
			case 30:
				strFieldName = "ModifyDate";
				break;
			case 31:
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
		if( strFieldName.equals("ConsultNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CSubject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpertFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpertNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpertName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpRDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ExpRTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseaseDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaiFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvaliReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
