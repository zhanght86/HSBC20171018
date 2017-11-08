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
import com.sinosoft.lis.db.LCGrpImportLogDB;

/*
 * <p>ClassName: LCGrpImportLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_17
 */
public class LCGrpImportLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpImportLogSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 其他号码 */
	private String OtherNo;
	/** 其他号码类型 */
	private String OtherNoType;
	/** 批次号 */
	private String BatchNo;
	/** 导入序号 */
	private String ID;
	/** 险种唯一id */
	private String IdNo;
	/** 被保人客户id */
	private String InsuredID;
	/** 印刷号 */
	private String PrtNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单号码 */
	private String PolNo;
	/** 险种代码 */
	private String RiskCode;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 错误类型 */
	private String ErrorType;
	/** 错误信息 */
	private String ErrorInfo;
	/** 错误状态 */
	private String ErrorState;
	/** 被保人客户号 */
	private String InsuredNo;
	/** 被保人客户姓名 */
	private String InsuredName;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** Contid */
	private String ContID;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpImportLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "OtherNoType";
		pk[1] = "BatchNo";
		pk[2] = "ID";

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
		LCGrpImportLogSchema cloned = (LCGrpImportLogSchema)super.clone();
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
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	/**
	* 各模块专用号码:<p>
	* 新契约 -- 保单号<p>
	* 理赔   -- 赔案号<p>
	* 保全   -- 保全受理号
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其他号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	/**
	* 团体新契约 -- 4<p>
	* 团体理赔   -- 5<p>
	* 团体保全   -- 10
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		if(aOtherNoType!=null && aOtherNoType.length()>2)
			throw new IllegalArgumentException("其他号码类型OtherNoType值"+aOtherNoType+"的长度"+aOtherNoType.length()+"大于最大值2");
		OtherNoType = aOtherNoType;
	}
	/**
	* 团体理赔 -- 团体赔案号
	*/
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		if(aBatchNo!=null && aBatchNo.length()>22)
			throw new IllegalArgumentException("批次号BatchNo值"+aBatchNo+"的长度"+aBatchNo.length()+"大于最大值22");
		BatchNo = aBatchNo;
	}
	/**
	* 新契约 -- 合同ID<p>
	* 理赔 -- 导入顺序号
	*/
	public String getID()
	{
		return ID;
	}
	public void setID(String aID)
	{
		if(aID!=null && aID.length()>20)
			throw new IllegalArgumentException("导入序号ID值"+aID+"的长度"+aID.length()+"大于最大值20");
		ID = aID;
	}
	public String getIdNo()
	{
		return IdNo;
	}
	public void setIdNo(String aIdNo)
	{
		if(aIdNo!=null && aIdNo.length()>20)
			throw new IllegalArgumentException("险种唯一idIdNo值"+aIdNo+"的长度"+aIdNo.length()+"大于最大值20");
		IdNo = aIdNo;
	}
	public String getInsuredID()
	{
		return InsuredID;
	}
	public void setInsuredID(String aInsuredID)
	{
		if(aInsuredID!=null && aInsuredID.length()>24)
			throw new IllegalArgumentException("被保人客户idInsuredID值"+aInsuredID+"的长度"+aInsuredID.length()+"大于最大值24");
		InsuredID = aInsuredID;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		if(aPrtNo!=null && aPrtNo.length()>20)
			throw new IllegalArgumentException("印刷号PrtNo值"+aPrtNo+"的长度"+aPrtNo.length()+"大于最大值20");
		PrtNo = aPrtNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单号码PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>6)
			throw new IllegalArgumentException("险种代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值6");
		RiskCode = aRiskCode;
	}
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		if(aContPlanCode!=null && aContPlanCode.length()>2)
			throw new IllegalArgumentException("保险计划编码ContPlanCode值"+aContPlanCode+"的长度"+aContPlanCode.length()+"大于最大值2");
		ContPlanCode = aContPlanCode;
	}
	/**
	* 0 -- 中断退出<p>
	* 1 -- 继续下一个
	*/
	public String getErrorType()
	{
		return ErrorType;
	}
	public void setErrorType(String aErrorType)
	{
		if(aErrorType!=null && aErrorType.length()>6)
			throw new IllegalArgumentException("错误类型ErrorType值"+aErrorType+"的长度"+aErrorType.length()+"大于最大值6");
		ErrorType = aErrorType;
	}
	public String getErrorInfo()
	{
		return ErrorInfo;
	}
	public void setErrorInfo(String aErrorInfo)
	{
		if(aErrorInfo!=null && aErrorInfo.length()>4000)
			throw new IllegalArgumentException("错误信息ErrorInfo值"+aErrorInfo+"的长度"+aErrorInfo.length()+"大于最大值4000");
		ErrorInfo = aErrorInfo;
	}
	/**
	* 0 成功<p>
	* 1 失败
	*/
	public String getErrorState()
	{
		return ErrorState;
	}
	public void setErrorState(String aErrorState)
	{
		if(aErrorState!=null && aErrorState.length()>6)
			throw new IllegalArgumentException("错误状态ErrorState值"+aErrorState+"的长度"+aErrorState.length()+"大于最大值6");
		ErrorState = aErrorState;
	}
	/**
	* 团体理赔 -- 出险人客户号
	*/
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	/**
	* 团体理赔 -- 出险人姓名
	*/
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		if(aInsuredName!=null && aInsuredName.length()>120)
			throw new IllegalArgumentException("被保人客户姓名InsuredName值"+aInsuredName+"的长度"+aInsuredName.length()+"大于最大值120");
		InsuredName = aInsuredName;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>20)
			throw new IllegalArgumentException("备用属性字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值20");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>20)
			throw new IllegalArgumentException("备用属性字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值20");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		if(aStandbyFlag3!=null && aStandbyFlag3.length()>4000)
			throw new IllegalArgumentException("备用属性字段3StandbyFlag3值"+aStandbyFlag3+"的长度"+aStandbyFlag3.length()+"大于最大值4000");
		StandbyFlag3 = aStandbyFlag3;
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
	public String getContID()
	{
		return ContID;
	}
	public void setContID(String aContID)
	{
		if(aContID!=null && aContID.length()>20)
			throw new IllegalArgumentException("ContidContID值"+aContID+"的长度"+aContID.length()+"大于最大值20");
		ContID = aContID;
	}

	/**
	* 使用另外一个 LCGrpImportLogSchema 对象给 Schema 赋值
	* @param: aLCGrpImportLogSchema LCGrpImportLogSchema
	**/
	public void setSchema(LCGrpImportLogSchema aLCGrpImportLogSchema)
	{
		this.GrpContNo = aLCGrpImportLogSchema.getGrpContNo();
		this.OtherNo = aLCGrpImportLogSchema.getOtherNo();
		this.OtherNoType = aLCGrpImportLogSchema.getOtherNoType();
		this.BatchNo = aLCGrpImportLogSchema.getBatchNo();
		this.ID = aLCGrpImportLogSchema.getID();
		this.IdNo = aLCGrpImportLogSchema.getIdNo();
		this.InsuredID = aLCGrpImportLogSchema.getInsuredID();
		this.PrtNo = aLCGrpImportLogSchema.getPrtNo();
		this.ContNo = aLCGrpImportLogSchema.getContNo();
		this.PolNo = aLCGrpImportLogSchema.getPolNo();
		this.RiskCode = aLCGrpImportLogSchema.getRiskCode();
		this.ContPlanCode = aLCGrpImportLogSchema.getContPlanCode();
		this.ErrorType = aLCGrpImportLogSchema.getErrorType();
		this.ErrorInfo = aLCGrpImportLogSchema.getErrorInfo();
		this.ErrorState = aLCGrpImportLogSchema.getErrorState();
		this.InsuredNo = aLCGrpImportLogSchema.getInsuredNo();
		this.InsuredName = aLCGrpImportLogSchema.getInsuredName();
		this.StandbyFlag1 = aLCGrpImportLogSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCGrpImportLogSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCGrpImportLogSchema.getStandbyFlag3();
		this.Operator = aLCGrpImportLogSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpImportLogSchema.getMakeDate());
		this.MakeTime = aLCGrpImportLogSchema.getMakeTime();
		this.ContID = aLCGrpImportLogSchema.getContID();
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

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("ID") == null )
				this.ID = null;
			else
				this.ID = rs.getString("ID").trim();

			if( rs.getString("IdNo") == null )
				this.IdNo = null;
			else
				this.IdNo = rs.getString("IdNo").trim();

			if( rs.getString("InsuredID") == null )
				this.InsuredID = null;
			else
				this.InsuredID = rs.getString("InsuredID").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("ErrorType") == null )
				this.ErrorType = null;
			else
				this.ErrorType = rs.getString("ErrorType").trim();

			if( rs.getString("ErrorInfo") == null )
				this.ErrorInfo = null;
			else
				this.ErrorInfo = rs.getString("ErrorInfo").trim();

			if( rs.getString("ErrorState") == null )
				this.ErrorState = null;
			else
				this.ErrorState = rs.getString("ErrorState").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ContID") == null )
				this.ContID = null;
			else
				this.ContID = rs.getString("ContID").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpImportLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpImportLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpImportLogSchema getSchema()
	{
		LCGrpImportLogSchema aLCGrpImportLogSchema = new LCGrpImportLogSchema();
		aLCGrpImportLogSchema.setSchema(this);
		return aLCGrpImportLogSchema;
	}

	public LCGrpImportLogDB getDB()
	{
		LCGrpImportLogDB aDBOper = new LCGrpImportLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpImportLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IdNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrorState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContID));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpImportLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IdNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuredID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ErrorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ErrorInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ErrorState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ContID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpImportLogSchema";
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("ID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ID));
		}
		if (FCode.equalsIgnoreCase("IdNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IdNo));
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredID));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("ErrorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorType));
		}
		if (FCode.equalsIgnoreCase("ErrorInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorInfo));
		}
		if (FCode.equalsIgnoreCase("ErrorState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrorState));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
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
		if (FCode.equalsIgnoreCase("ContID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContID));
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
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IdNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuredID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ErrorType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ErrorInfo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ErrorState);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
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
				strFieldValue = StrTool.GBKToUnicode(ContID);
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
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("ID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ID = FValue.trim();
			}
			else
				ID = null;
		}
		if (FCode.equalsIgnoreCase("IdNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IdNo = FValue.trim();
			}
			else
				IdNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredID = FValue.trim();
			}
			else
				InsuredID = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("ErrorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorType = FValue.trim();
			}
			else
				ErrorType = null;
		}
		if (FCode.equalsIgnoreCase("ErrorInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorInfo = FValue.trim();
			}
			else
				ErrorInfo = null;
		}
		if (FCode.equalsIgnoreCase("ErrorState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrorState = FValue.trim();
			}
			else
				ErrorState = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredName = FValue.trim();
			}
			else
				InsuredName = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
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
		if (FCode.equalsIgnoreCase("ContID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContID = FValue.trim();
			}
			else
				ContID = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpImportLogSchema other = (LCGrpImportLogSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& BatchNo.equals(other.getBatchNo())
			&& ID.equals(other.getID())
			&& IdNo.equals(other.getIdNo())
			&& InsuredID.equals(other.getInsuredID())
			&& PrtNo.equals(other.getPrtNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& ErrorType.equals(other.getErrorType())
			&& ErrorInfo.equals(other.getErrorInfo())
			&& ErrorState.equals(other.getErrorState())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ContID.equals(other.getContID());
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
		if( strFieldName.equals("OtherNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 2;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 3;
		}
		if( strFieldName.equals("ID") ) {
			return 4;
		}
		if( strFieldName.equals("IdNo") ) {
			return 5;
		}
		if( strFieldName.equals("InsuredID") ) {
			return 6;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 7;
		}
		if( strFieldName.equals("ContNo") ) {
			return 8;
		}
		if( strFieldName.equals("PolNo") ) {
			return 9;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 10;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 11;
		}
		if( strFieldName.equals("ErrorType") ) {
			return 12;
		}
		if( strFieldName.equals("ErrorInfo") ) {
			return 13;
		}
		if( strFieldName.equals("ErrorState") ) {
			return 14;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 15;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 17;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 18;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
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
		if( strFieldName.equals("ContID") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "OtherNo";
				break;
			case 2:
				strFieldName = "OtherNoType";
				break;
			case 3:
				strFieldName = "BatchNo";
				break;
			case 4:
				strFieldName = "ID";
				break;
			case 5:
				strFieldName = "IdNo";
				break;
			case 6:
				strFieldName = "InsuredID";
				break;
			case 7:
				strFieldName = "PrtNo";
				break;
			case 8:
				strFieldName = "ContNo";
				break;
			case 9:
				strFieldName = "PolNo";
				break;
			case 10:
				strFieldName = "RiskCode";
				break;
			case 11:
				strFieldName = "ContPlanCode";
				break;
			case 12:
				strFieldName = "ErrorType";
				break;
			case 13:
				strFieldName = "ErrorInfo";
				break;
			case 14:
				strFieldName = "ErrorState";
				break;
			case 15:
				strFieldName = "InsuredNo";
				break;
			case 16:
				strFieldName = "InsuredName";
				break;
			case 17:
				strFieldName = "StandbyFlag1";
				break;
			case 18:
				strFieldName = "StandbyFlag2";
				break;
			case 19:
				strFieldName = "StandbyFlag3";
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
				strFieldName = "ContID";
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
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IdNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrorState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
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
		if( strFieldName.equals("ContID") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
