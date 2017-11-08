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
import com.sinosoft.lis.db.LLPrepayDetailDB;

/*
 * <p>ClassName: LLPrepayDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LLPrepayDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLPrepayDetailSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 保单号 */
	private String PolNo;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 序号 */
	private String SerialNo;
	/** 预付批次号 */
	private String PrepayNo;
	/** 立案号 */
	private String RgtNo;
	/** 拒赔号 */
	private String DeclineNo;
	/** 集体合同号 */
	private String GrpContNo;
	/** 集体保单号 */
	private String GrpPolNo;
	/** 个单合同号 */
	private String ContNo;
	/** 险类代码 */
	private String KindCode;
	/** 险种代码 */
	private String RiskCode;
	/** 险种版本号 */
	private String RiskVer;
	/** 保单管理机构 */
	private String PolMngCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 预付金额 */
	private double PrepaySum;
	/** 备注 */
	private String Remark;
	/** 责任编码 */
	private String DutyCode;
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
	/** 保险金领取方式 */
	private String CaseGetMode;
	/** 保险金支付方式 */
	private String CasePayMode;
	/** 保险金支付标志 */
	private String CasePayFlag;
	/** 承保时保单号 */
	private String NBPolNo;
	/** 事故号 */
	private String CaseRelaNo;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 32;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLPrepayDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "PolNo";
		pk[3] = "GetDutyKind";
		pk[4] = "GetDutyCode";
		pk[5] = "SerialNo";
		pk[6] = "PrepayNo";
		pk[7] = "DutyCode";
		pk[8] = "CaseRelaNo";

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
		LLPrepayDetailSchema cloned = (LLPrepayDetailSchema)super.clone();
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
		ClmNo = aClmNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	/**
	* 100  意外医疗<p>
	* 101  意外伤残<p>
	* 102  意外死亡<p>
	* 103  意外高残<p>
	* 104  意外大病<p>
	* 105  意外特种疾病<p>
	* 106  意外失业失能<p>
	* 109  意外豁免<p>
	* 200  疾病医疗<p>
	* 201  疾病伤残<p>
	* 202  疾病死亡<p>
	* 203  疾病高残<p>
	* 204  疾病大病<p>
	* 205  疾病特种疾病<p>
	* 206  疾病失业失能<p>
	* 209  疾病豁免<p>
	* select code,codename from ldcode where codetype='llclaimtype';
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	/**
	* 作为一个给付责任的第N笔预付金额
	*/
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* 进入预付功能，必须先申请此号，为10位流水号<p>
	* 作为每个批次预付的编号<p>
	* 该字段与LLBalance表的批次号BatNo相同<p>
	* 该字段与LLBnf表的批次号BatNo相同
	*/
	public String getPrepayNo()
	{
		return PrepayNo;
	}
	public void setPrepayNo(String aPrepayNo)
	{
		PrepayNo = aPrepayNo;
	}
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		RgtNo = aRgtNo;
	}
	public String getDeclineNo()
	{
		return DeclineNo;
	}
	public void setDeclineNo(String aDeclineNo)
	{
		DeclineNo = aDeclineNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getKindCode()
	{
		return KindCode;
	}
	public void setKindCode(String aKindCode)
	{
		KindCode = aKindCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getPolMngCom()
	{
		return PolMngCom;
	}
	public void setPolMngCom(String aPolMngCom)
	{
		PolMngCom = aPolMngCom;
	}
	/**
	* 01-团险直销,02-个人营销,03-银行代理<p>
	* 04-兼业代理,05-专业代理,06-经纪公司<p>
	* 07-不计业绩销售渠道,99-其他
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public double getPrepaySum()
	{
		return PrepaySum;
	}
	public void setPrepaySum(double aPrepaySum)
	{
		PrepaySum = aPrepaySum;
	}
	public void setPrepaySum(String aPrepaySum)
	{
		if (aPrepaySum != null && !aPrepaySum.equals(""))
		{
			Double tDouble = new Double(aPrepaySum);
			double d = tDouble.doubleValue();
			PrepaySum = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
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
	* [不用]<p>
	* 1	现金<p>
	* 2	现金支票<p>
	* 3	转账支票<p>
	* 4	银行转账<p>
	* 5	内部转帐<p>
	* 6	银行托收<p>
	* 7	其他<p>
	* 8 new 统一转账(团单，家庭单按一个申请统一转账)
	*/
	public String getCaseGetMode()
	{
		return CaseGetMode;
	}
	public void setCaseGetMode(String aCaseGetMode)
	{
		CaseGetMode = aCaseGetMode;
	}
	/**
	* [不用]<p>
	* 0	一次领取<p>
	* 1	固定多次领取<p>
	* 2	不固定多次领取
	*/
	public String getCasePayMode()
	{
		return CasePayMode;
	}
	public void setCasePayMode(String aCasePayMode)
	{
		CasePayMode = aCasePayMode;
	}
	/**
	* [不用]<p>
	* 0未支付<p>
	* 1已支付
	*/
	public String getCasePayFlag()
	{
		return CasePayFlag;
	}
	public void setCasePayFlag(String aCasePayFlag)
	{
		CasePayFlag = aCasePayFlag;
	}
	public String getNBPolNo()
	{
		return NBPolNo;
	}
	public void setNBPolNo(String aNBPolNo)
	{
		NBPolNo = aNBPolNo;
	}
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		CaseRelaNo = aCaseRelaNo;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}

	/**
	* 使用另外一个 LLPrepayDetailSchema 对象给 Schema 赋值
	* @param: aLLPrepayDetailSchema LLPrepayDetailSchema
	**/
	public void setSchema(LLPrepayDetailSchema aLLPrepayDetailSchema)
	{
		this.ClmNo = aLLPrepayDetailSchema.getClmNo();
		this.CaseNo = aLLPrepayDetailSchema.getCaseNo();
		this.PolNo = aLLPrepayDetailSchema.getPolNo();
		this.GetDutyKind = aLLPrepayDetailSchema.getGetDutyKind();
		this.GetDutyCode = aLLPrepayDetailSchema.getGetDutyCode();
		this.SerialNo = aLLPrepayDetailSchema.getSerialNo();
		this.PrepayNo = aLLPrepayDetailSchema.getPrepayNo();
		this.RgtNo = aLLPrepayDetailSchema.getRgtNo();
		this.DeclineNo = aLLPrepayDetailSchema.getDeclineNo();
		this.GrpContNo = aLLPrepayDetailSchema.getGrpContNo();
		this.GrpPolNo = aLLPrepayDetailSchema.getGrpPolNo();
		this.ContNo = aLLPrepayDetailSchema.getContNo();
		this.KindCode = aLLPrepayDetailSchema.getKindCode();
		this.RiskCode = aLLPrepayDetailSchema.getRiskCode();
		this.RiskVer = aLLPrepayDetailSchema.getRiskVer();
		this.PolMngCom = aLLPrepayDetailSchema.getPolMngCom();
		this.SaleChnl = aLLPrepayDetailSchema.getSaleChnl();
		this.PrepaySum = aLLPrepayDetailSchema.getPrepaySum();
		this.Remark = aLLPrepayDetailSchema.getRemark();
		this.DutyCode = aLLPrepayDetailSchema.getDutyCode();
		this.MngCom = aLLPrepayDetailSchema.getMngCom();
		this.Operator = aLLPrepayDetailSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLPrepayDetailSchema.getMakeDate());
		this.MakeTime = aLLPrepayDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLPrepayDetailSchema.getModifyDate());
		this.ModifyTime = aLLPrepayDetailSchema.getModifyTime();
		this.CaseGetMode = aLLPrepayDetailSchema.getCaseGetMode();
		this.CasePayMode = aLLPrepayDetailSchema.getCasePayMode();
		this.CasePayFlag = aLLPrepayDetailSchema.getCasePayFlag();
		this.NBPolNo = aLLPrepayDetailSchema.getNBPolNo();
		this.CaseRelaNo = aLLPrepayDetailSchema.getCaseRelaNo();
		this.Currency = aLLPrepayDetailSchema.getCurrency();
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

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("PrepayNo") == null )
				this.PrepayNo = null;
			else
				this.PrepayNo = rs.getString("PrepayNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("DeclineNo") == null )
				this.DeclineNo = null;
			else
				this.DeclineNo = rs.getString("DeclineNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("KindCode") == null )
				this.KindCode = null;
			else
				this.KindCode = rs.getString("KindCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("PolMngCom") == null )
				this.PolMngCom = null;
			else
				this.PolMngCom = rs.getString("PolMngCom").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.PrepaySum = rs.getDouble("PrepaySum");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

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

			if( rs.getString("CaseGetMode") == null )
				this.CaseGetMode = null;
			else
				this.CaseGetMode = rs.getString("CaseGetMode").trim();

			if( rs.getString("CasePayMode") == null )
				this.CasePayMode = null;
			else
				this.CasePayMode = rs.getString("CasePayMode").trim();

			if( rs.getString("CasePayFlag") == null )
				this.CasePayFlag = null;
			else
				this.CasePayFlag = rs.getString("CasePayFlag").trim();

			if( rs.getString("NBPolNo") == null )
				this.NBPolNo = null;
			else
				this.NBPolNo = rs.getString("NBPolNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLPrepayDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLPrepayDetailSchema getSchema()
	{
		LLPrepayDetailSchema aLLPrepayDetailSchema = new LLPrepayDetailSchema();
		aLLPrepayDetailSchema.setSchema(this);
		return aLLPrepayDetailSchema;
	}

	public LLPrepayDetailDB getDB()
	{
		LLPrepayDetailDB aDBOper = new LLPrepayDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLPrepayDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrepayNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeclineNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KindCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrepaySum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseGetMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CasePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NBPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLPrepayDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PrepayNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DeclineNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			KindCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PolMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			PrepaySum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			CaseGetMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			CasePayMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			CasePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			NBPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLPrepayDetailSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("PrepayNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepayNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclineNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KindCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolMngCom));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrepaySum));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseGetMode));
		}
		if (FCode.equalsIgnoreCase("CasePayMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayMode));
		}
		if (FCode.equalsIgnoreCase("CasePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CasePayFlag));
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NBPolNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PrepayNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DeclineNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(KindCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PolMngCom);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 17:
				strFieldValue = String.valueOf(PrepaySum);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(CaseGetMode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(CasePayMode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(CasePayFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(NBPolNo);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(Currency);
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
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
		if (FCode.equalsIgnoreCase("PrepayNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrepayNo = FValue.trim();
			}
			else
				PrepayNo = null;
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
		if (FCode.equalsIgnoreCase("DeclineNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeclineNo = FValue.trim();
			}
			else
				DeclineNo = null;
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("KindCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KindCode = FValue.trim();
			}
			else
				KindCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("PolMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolMngCom = FValue.trim();
			}
			else
				PolMngCom = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("PrepaySum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrepaySum = d;
			}
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
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
		if (FCode.equalsIgnoreCase("CaseGetMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseGetMode = FValue.trim();
			}
			else
				CaseGetMode = null;
		}
		if (FCode.equalsIgnoreCase("CasePayMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePayMode = FValue.trim();
			}
			else
				CasePayMode = null;
		}
		if (FCode.equalsIgnoreCase("CasePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CasePayFlag = FValue.trim();
			}
			else
				CasePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("NBPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NBPolNo = FValue.trim();
			}
			else
				NBPolNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLPrepayDetailSchema other = (LLPrepayDetailSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& PolNo.equals(other.getPolNo())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& SerialNo.equals(other.getSerialNo())
			&& PrepayNo.equals(other.getPrepayNo())
			&& RgtNo.equals(other.getRgtNo())
			&& DeclineNo.equals(other.getDeclineNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& KindCode.equals(other.getKindCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& PolMngCom.equals(other.getPolMngCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& PrepaySum == other.getPrepaySum()
			&& Remark.equals(other.getRemark())
			&& DutyCode.equals(other.getDutyCode())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& CaseGetMode.equals(other.getCaseGetMode())
			&& CasePayMode.equals(other.getCasePayMode())
			&& CasePayFlag.equals(other.getCasePayFlag())
			&& NBPolNo.equals(other.getNBPolNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& Currency.equals(other.getCurrency());
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
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 5;
		}
		if( strFieldName.equals("PrepayNo") ) {
			return 6;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 7;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 10;
		}
		if( strFieldName.equals("ContNo") ) {
			return 11;
		}
		if( strFieldName.equals("KindCode") ) {
			return 12;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 13;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 14;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return 15;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 16;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return 17;
		}
		if( strFieldName.equals("Remark") ) {
			return 18;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 19;
		}
		if( strFieldName.equals("MngCom") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 25;
		}
		if( strFieldName.equals("CaseGetMode") ) {
			return 26;
		}
		if( strFieldName.equals("CasePayMode") ) {
			return 27;
		}
		if( strFieldName.equals("CasePayFlag") ) {
			return 28;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return 29;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 30;
		}
		if( strFieldName.equals("Currency") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "PolNo";
				break;
			case 3:
				strFieldName = "GetDutyKind";
				break;
			case 4:
				strFieldName = "GetDutyCode";
				break;
			case 5:
				strFieldName = "SerialNo";
				break;
			case 6:
				strFieldName = "PrepayNo";
				break;
			case 7:
				strFieldName = "RgtNo";
				break;
			case 8:
				strFieldName = "DeclineNo";
				break;
			case 9:
				strFieldName = "GrpContNo";
				break;
			case 10:
				strFieldName = "GrpPolNo";
				break;
			case 11:
				strFieldName = "ContNo";
				break;
			case 12:
				strFieldName = "KindCode";
				break;
			case 13:
				strFieldName = "RiskCode";
				break;
			case 14:
				strFieldName = "RiskVer";
				break;
			case 15:
				strFieldName = "PolMngCom";
				break;
			case 16:
				strFieldName = "SaleChnl";
				break;
			case 17:
				strFieldName = "PrepaySum";
				break;
			case 18:
				strFieldName = "Remark";
				break;
			case 19:
				strFieldName = "DutyCode";
				break;
			case 20:
				strFieldName = "MngCom";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
				strFieldName = "ModifyTime";
				break;
			case 26:
				strFieldName = "CaseGetMode";
				break;
			case 27:
				strFieldName = "CasePayMode";
				break;
			case 28:
				strFieldName = "CasePayFlag";
				break;
			case 29:
				strFieldName = "NBPolNo";
				break;
			case 30:
				strFieldName = "CaseRelaNo";
				break;
			case 31:
				strFieldName = "Currency";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepayNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclineNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KindCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrepaySum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
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
		if( strFieldName.equals("CaseGetMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePayMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CasePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NBPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
