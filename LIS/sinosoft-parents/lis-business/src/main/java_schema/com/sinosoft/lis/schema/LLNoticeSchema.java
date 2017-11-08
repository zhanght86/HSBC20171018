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
import com.sinosoft.lis.db.LLNoticeDB;

/*
 * <p>ClassName: LLNoticeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLNoticeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLNoticeSchema.class);
	// @Field
	/** 通知号 */
	private String NoticeNo;
	/** 登记号码 */
	private String LogNo;
	/** 客户号 */
	private String CustomerNo;
	/** 客户姓名 */
	private String CustomerName;
	/** 关联客户类型 */
	private String CustomerType;
	/** 通知日期 */
	private Date NoticeDate;
	/** 通知事件 */
	private String NoticeTime;
	/** 通知主题 */
	private String NSubject;
	/** 通知内容 */
	private String NContent;
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
	/** 调查报告标志 */
	private String SurveyFlag;
	/** 备注 */
	private String Remark;
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

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLNoticeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "NoticeNo";

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
		LLNoticeSchema cloned = (LLNoticeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNoticeNo()
	{
		return NoticeNo;
	}
	public void setNoticeNo(String aNoticeNo)
	{
		if(aNoticeNo!=null && aNoticeNo.length()>20)
			throw new IllegalArgumentException("通知号NoticeNo值"+aNoticeNo+"的长度"+aNoticeNo.length()+"大于最大值20");
		NoticeNo = aNoticeNo;
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
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>20)
			throw new IllegalArgumentException("客户姓名CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值20");
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
			throw new IllegalArgumentException("关联客户类型CustomerType值"+aCustomerType+"的长度"+aCustomerType.length()+"大于最大值20");
		CustomerType = aCustomerType;
	}
	public String getNoticeDate()
	{
		if( NoticeDate != null )
			return fDate.getString(NoticeDate);
		else
			return null;
	}
	public void setNoticeDate(Date aNoticeDate)
	{
		NoticeDate = aNoticeDate;
	}
	public void setNoticeDate(String aNoticeDate)
	{
		if (aNoticeDate != null && !aNoticeDate.equals("") )
		{
			NoticeDate = fDate.getDate( aNoticeDate );
		}
		else
			NoticeDate = null;
	}

	public String getNoticeTime()
	{
		return NoticeTime;
	}
	public void setNoticeTime(String aNoticeTime)
	{
		if(aNoticeTime!=null && aNoticeTime.length()>8)
			throw new IllegalArgumentException("通知事件NoticeTime值"+aNoticeTime+"的长度"+aNoticeTime.length()+"大于最大值8");
		NoticeTime = aNoticeTime;
	}
	public String getNSubject()
	{
		return NSubject;
	}
	public void setNSubject(String aNSubject)
	{
		if(aNSubject!=null && aNSubject.length()>200)
			throw new IllegalArgumentException("通知主题NSubject值"+aNSubject+"的长度"+aNSubject.length()+"大于最大值200");
		NSubject = aNSubject;
	}
	public String getNContent()
	{
		return NContent;
	}
	public void setNContent(String aNContent)
	{
		if(aNContent!=null && aNContent.length()>1000)
			throw new IllegalArgumentException("通知内容NContent值"+aNContent+"的长度"+aNContent.length()+"大于最大值1000");
		NContent = aNContent;
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
	/**
	* 包括：有、没有
	*/
	public String getSurveyFlag()
	{
		return SurveyFlag;
	}
	public void setSurveyFlag(String aSurveyFlag)
	{
		if(aSurveyFlag!=null && aSurveyFlag.length()>1)
			throw new IllegalArgumentException("调查报告标志SurveyFlag值"+aSurveyFlag+"的长度"+aSurveyFlag.length()+"大于最大值1");
		SurveyFlag = aSurveyFlag;
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
	* 使用另外一个 LLNoticeSchema 对象给 Schema 赋值
	* @param: aLLNoticeSchema LLNoticeSchema
	**/
	public void setSchema(LLNoticeSchema aLLNoticeSchema)
	{
		this.NoticeNo = aLLNoticeSchema.getNoticeNo();
		this.LogNo = aLLNoticeSchema.getLogNo();
		this.CustomerNo = aLLNoticeSchema.getCustomerNo();
		this.CustomerName = aLLNoticeSchema.getCustomerName();
		this.CustomerType = aLLNoticeSchema.getCustomerType();
		this.NoticeDate = fDate.getDate( aLLNoticeSchema.getNoticeDate());
		this.NoticeTime = aLLNoticeSchema.getNoticeTime();
		this.NSubject = aLLNoticeSchema.getNSubject();
		this.NContent = aLLNoticeSchema.getNContent();
		this.DiseaseCode = aLLNoticeSchema.getDiseaseCode();
		this.AccCode = aLLNoticeSchema.getAccCode();
		this.DiseaseDesc = aLLNoticeSchema.getDiseaseDesc();
		this.AccDesc = aLLNoticeSchema.getAccDesc();
		this.HospitalCode = aLLNoticeSchema.getHospitalCode();
		this.HospitalName = aLLNoticeSchema.getHospitalName();
		this.InHospitalDate = fDate.getDate( aLLNoticeSchema.getInHospitalDate());
		this.OutHospitalDate = fDate.getDate( aLLNoticeSchema.getOutHospitalDate());
		this.CustStatus = aLLNoticeSchema.getCustStatus();
		this.SurveyFlag = aLLNoticeSchema.getSurveyFlag();
		this.Remark = aLLNoticeSchema.getRemark();
		this.Operator = aLLNoticeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLNoticeSchema.getMakeDate());
		this.MakeTime = aLLNoticeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLNoticeSchema.getModifyDate());
		this.ModifyTime = aLLNoticeSchema.getModifyTime();
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
			if( rs.getString("NoticeNo") == null )
				this.NoticeNo = null;
			else
				this.NoticeNo = rs.getString("NoticeNo").trim();

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

			this.NoticeDate = rs.getDate("NoticeDate");
			if( rs.getString("NoticeTime") == null )
				this.NoticeTime = null;
			else
				this.NoticeTime = rs.getString("NoticeTime").trim();

			if( rs.getString("NSubject") == null )
				this.NSubject = null;
			else
				this.NSubject = rs.getString("NSubject").trim();

			if( rs.getString("NContent") == null )
				this.NContent = null;
			else
				this.NContent = rs.getString("NContent").trim();

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

			if( rs.getString("SurveyFlag") == null )
				this.SurveyFlag = null;
			else
				this.SurveyFlag = rs.getString("SurveyFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLNotice表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLNoticeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLNoticeSchema getSchema()
	{
		LLNoticeSchema aLLNoticeSchema = new LLNoticeSchema();
		aLLNoticeSchema.setSchema(this);
		return aLLNoticeSchema;
	}

	public LLNoticeDB getDB()
	{
		LLNoticeDB aDBOper = new LLNoticeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLNotice描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( NoticeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NoticeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NSubject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutHospitalDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SurveyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLNotice>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			LogNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NoticeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			NoticeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NSubject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			NContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DiseaseCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DiseaseDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AccDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			InHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			OutHospitalDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			CustStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			SurveyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLNoticeSchema";
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
		if (FCode.equalsIgnoreCase("NoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoticeNo));
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
		if (FCode.equalsIgnoreCase("NoticeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getNoticeDate()));
		}
		if (FCode.equalsIgnoreCase("NoticeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoticeTime));
		}
		if (FCode.equalsIgnoreCase("NSubject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NSubject));
		}
		if (FCode.equalsIgnoreCase("NContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NContent));
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
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SurveyFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(NoticeNo);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getNoticeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(NoticeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NSubject);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(NContent);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DiseaseCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AccCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DiseaseDesc);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AccDesc);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInHospitalDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutHospitalDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CustStatus);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(SurveyFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("NoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoticeNo = FValue.trim();
			}
			else
				NoticeNo = null;
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
		if (FCode.equalsIgnoreCase("NoticeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				NoticeDate = fDate.getDate( FValue );
			}
			else
				NoticeDate = null;
		}
		if (FCode.equalsIgnoreCase("NoticeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoticeTime = FValue.trim();
			}
			else
				NoticeTime = null;
		}
		if (FCode.equalsIgnoreCase("NSubject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NSubject = FValue.trim();
			}
			else
				NSubject = null;
		}
		if (FCode.equalsIgnoreCase("NContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NContent = FValue.trim();
			}
			else
				NContent = null;
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
		if (FCode.equalsIgnoreCase("SurveyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SurveyFlag = FValue.trim();
			}
			else
				SurveyFlag = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLNoticeSchema other = (LLNoticeSchema)otherObject;
		return
			NoticeNo.equals(other.getNoticeNo())
			&& LogNo.equals(other.getLogNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& CustomerType.equals(other.getCustomerType())
			&& fDate.getString(NoticeDate).equals(other.getNoticeDate())
			&& NoticeTime.equals(other.getNoticeTime())
			&& NSubject.equals(other.getNSubject())
			&& NContent.equals(other.getNContent())
			&& DiseaseCode.equals(other.getDiseaseCode())
			&& AccCode.equals(other.getAccCode())
			&& DiseaseDesc.equals(other.getDiseaseDesc())
			&& AccDesc.equals(other.getAccDesc())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& fDate.getString(InHospitalDate).equals(other.getInHospitalDate())
			&& fDate.getString(OutHospitalDate).equals(other.getOutHospitalDate())
			&& CustStatus.equals(other.getCustStatus())
			&& SurveyFlag.equals(other.getSurveyFlag())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("NoticeNo") ) {
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
		if( strFieldName.equals("NoticeDate") ) {
			return 5;
		}
		if( strFieldName.equals("NoticeTime") ) {
			return 6;
		}
		if( strFieldName.equals("NSubject") ) {
			return 7;
		}
		if( strFieldName.equals("NContent") ) {
			return 8;
		}
		if( strFieldName.equals("DiseaseCode") ) {
			return 9;
		}
		if( strFieldName.equals("AccCode") ) {
			return 10;
		}
		if( strFieldName.equals("DiseaseDesc") ) {
			return 11;
		}
		if( strFieldName.equals("AccDesc") ) {
			return 12;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 13;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 14;
		}
		if( strFieldName.equals("InHospitalDate") ) {
			return 15;
		}
		if( strFieldName.equals("OutHospitalDate") ) {
			return 16;
		}
		if( strFieldName.equals("CustStatus") ) {
			return 17;
		}
		if( strFieldName.equals("SurveyFlag") ) {
			return 18;
		}
		if( strFieldName.equals("Remark") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
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
				strFieldName = "NoticeNo";
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
				strFieldName = "NoticeDate";
				break;
			case 6:
				strFieldName = "NoticeTime";
				break;
			case 7:
				strFieldName = "NSubject";
				break;
			case 8:
				strFieldName = "NContent";
				break;
			case 9:
				strFieldName = "DiseaseCode";
				break;
			case 10:
				strFieldName = "AccCode";
				break;
			case 11:
				strFieldName = "DiseaseDesc";
				break;
			case 12:
				strFieldName = "AccDesc";
				break;
			case 13:
				strFieldName = "HospitalCode";
				break;
			case 14:
				strFieldName = "HospitalName";
				break;
			case 15:
				strFieldName = "InHospitalDate";
				break;
			case 16:
				strFieldName = "OutHospitalDate";
				break;
			case 17:
				strFieldName = "CustStatus";
				break;
			case 18:
				strFieldName = "SurveyFlag";
				break;
			case 19:
				strFieldName = "Remark";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
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
		if( strFieldName.equals("NoticeNo") ) {
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
		if( strFieldName.equals("NoticeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("NoticeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NSubject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NContent") ) {
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
		if( strFieldName.equals("SurveyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
