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
import com.sinosoft.lis.db.LREdorMainDB;

/*
 * <p>ClassName: LREdorMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LREdorMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LREdorMainSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 保单号码 */
	private String PolNo;
	/** 批改类型 */
	private String EdorType;
	/** 再保险公司 */
	private String ReinsureCom;
	/** 再保项目 */
	private String ReinsurItem;
	/** 保单年度 */
	private int InsuredYear;
	/** 批改申请号 */
	private String EdorAppNo;
	/** 管理机构 */
	private String ManageCom;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 被保人名称 */
	private String InsuredName;
	/** 交至日期 */
	private Date PaytoDate;
	/** 总累计保费 */
	private double SumPrem;
	/** 算法编码 */
	private String CalCode;
	/** 变动的保费 */
	private double ChgPrem;
	/** 变动的保额 */
	private double ChgAmnt;
	/** 变动的领取保额 */
	private double ChgGetAmnt;
	/** 补/退费金额 */
	private double GetMoney;
	/** 补/退费利息 */
	private double GetInterest;
	/** 批改生效日期 */
	private Date EdorValiDate;
	/** 批改申请有效日期 */
	private Date EdorAppValiDate;
	/** 批改申请日期 */
	private Date EdorAppDate;
	/** 批改状态 */
	private String EdorState;
	/** 核保标志 */
	private String UWState;
	/** 转入公共帐户 */
	private String PubAccFlag;
	/** 复核人编码 */
	private String ApproveCode;
	/** 复核日期 */
	private Date ApproveDate;
	/** 核保人 */
	private String UWOperator;
	/** 确认人 */
	private String ConfOperator;
	/** 确认日期 */
	private Date ConfDate;
	/** 确认时间 */
	private String ConfTime;
	/** 减少分保保额 */
	private double ChgCessAmt;
	/** 应退回分保费 */
	private double ShRePrem;
	/** 应扣除分保佣金 */
	private double ShReComm;
	/** 分保开始日期 */
	private Date CessStart;
	/** 分保结束日期 */
	private Date CessEnd;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 流水号 */
	private String SerialNo;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 险种计算分类 */
	private String RiskCalSort;
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 转换渠道 */
	private String TransSaleChnl;
	/** 责任代码 */
	private String DutyCode;

	public static final int FIELDNUM = 47;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LREdorMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[8];
		pk[0] = "EdorNo";
		pk[1] = "PolNo";
		pk[2] = "EdorType";
		pk[3] = "ReinsureCom";
		pk[4] = "ReinsurItem";
		pk[5] = "InsuredYear";
		pk[6] = "RiskCalSort";
		pk[7] = "DutyCode";

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
		LREdorMainSchema cloned = (LREdorMainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
	}
	/**
	* L--法定分保<p>
	* C--商业分保
	*/
	public String getReinsurItem()
	{
		return ReinsurItem;
	}
	public void setReinsurItem(String aReinsurItem)
	{
		ReinsurItem = aReinsurItem;
	}
	public int getInsuredYear()
	{
		return InsuredYear;
	}
	public void setInsuredYear(int aInsuredYear)
	{
		InsuredYear = aInsuredYear;
	}
	public void setInsuredYear(String aInsuredYear)
	{
		if (aInsuredYear != null && !aInsuredYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuredYear);
			int i = tInteger.intValue();
			InsuredYear = i;
		}
	}

	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		EdorAppNo = aEdorAppNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	public String getPaytoDate()
	{
		if( PaytoDate != null )
			return fDate.getString(PaytoDate);
		else
			return null;
	}
	public void setPaytoDate(Date aPaytoDate)
	{
		PaytoDate = aPaytoDate;
	}
	public void setPaytoDate(String aPaytoDate)
	{
		if (aPaytoDate != null && !aPaytoDate.equals("") )
		{
			PaytoDate = fDate.getDate( aPaytoDate );
		}
		else
			PaytoDate = null;
	}

	public double getSumPrem()
	{
		return SumPrem;
	}
	public void setSumPrem(double aSumPrem)
	{
		SumPrem = aSumPrem;
	}
	public void setSumPrem(String aSumPrem)
	{
		if (aSumPrem != null && !aSumPrem.equals(""))
		{
			Double tDouble = new Double(aSumPrem);
			double d = tDouble.doubleValue();
			SumPrem = d;
		}
	}

	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public double getChgPrem()
	{
		return ChgPrem;
	}
	public void setChgPrem(double aChgPrem)
	{
		ChgPrem = aChgPrem;
	}
	public void setChgPrem(String aChgPrem)
	{
		if (aChgPrem != null && !aChgPrem.equals(""))
		{
			Double tDouble = new Double(aChgPrem);
			double d = tDouble.doubleValue();
			ChgPrem = d;
		}
	}

	public double getChgAmnt()
	{
		return ChgAmnt;
	}
	public void setChgAmnt(double aChgAmnt)
	{
		ChgAmnt = aChgAmnt;
	}
	public void setChgAmnt(String aChgAmnt)
	{
		if (aChgAmnt != null && !aChgAmnt.equals(""))
		{
			Double tDouble = new Double(aChgAmnt);
			double d = tDouble.doubleValue();
			ChgAmnt = d;
		}
	}

	public double getChgGetAmnt()
	{
		return ChgGetAmnt;
	}
	public void setChgGetAmnt(double aChgGetAmnt)
	{
		ChgGetAmnt = aChgGetAmnt;
	}
	public void setChgGetAmnt(String aChgGetAmnt)
	{
		if (aChgGetAmnt != null && !aChgGetAmnt.equals(""))
		{
			Double tDouble = new Double(aChgGetAmnt);
			double d = tDouble.doubleValue();
			ChgGetAmnt = d;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public double getGetInterest()
	{
		return GetInterest;
	}
	public void setGetInterest(double aGetInterest)
	{
		GetInterest = aGetInterest;
	}
	public void setGetInterest(String aGetInterest)
	{
		if (aGetInterest != null && !aGetInterest.equals(""))
		{
			Double tDouble = new Double(aGetInterest);
			double d = tDouble.doubleValue();
			GetInterest = d;
		}
	}

	public String getEdorValiDate()
	{
		if( EdorValiDate != null )
			return fDate.getString(EdorValiDate);
		else
			return null;
	}
	public void setEdorValiDate(Date aEdorValiDate)
	{
		EdorValiDate = aEdorValiDate;
	}
	public void setEdorValiDate(String aEdorValiDate)
	{
		if (aEdorValiDate != null && !aEdorValiDate.equals("") )
		{
			EdorValiDate = fDate.getDate( aEdorValiDate );
		}
		else
			EdorValiDate = null;
	}

	/**
	* 批改申请有效日期(计算对应日)
	*/
	public String getEdorAppValiDate()
	{
		if( EdorAppValiDate != null )
			return fDate.getString(EdorAppValiDate);
		else
			return null;
	}
	public void setEdorAppValiDate(Date aEdorAppValiDate)
	{
		EdorAppValiDate = aEdorAppValiDate;
	}
	public void setEdorAppValiDate(String aEdorAppValiDate)
	{
		if (aEdorAppValiDate != null && !aEdorAppValiDate.equals("") )
		{
			EdorAppValiDate = fDate.getDate( aEdorAppValiDate );
		}
		else
			EdorAppValiDate = null;
	}

	public String getEdorAppDate()
	{
		if( EdorAppDate != null )
			return fDate.getString(EdorAppDate);
		else
			return null;
	}
	public void setEdorAppDate(Date aEdorAppDate)
	{
		EdorAppDate = aEdorAppDate;
	}
	public void setEdorAppDate(String aEdorAppDate)
	{
		if (aEdorAppDate != null && !aEdorAppDate.equals("") )
		{
			EdorAppDate = fDate.getDate( aEdorAppDate );
		}
		else
			EdorAppDate = null;
	}

	/**
	* 批改状态(<p>
	* 0-批改确认,<p>
	* 1-批改申请<p>
	* 2 －－ 申请确认。
	*/
	public String getEdorState()
	{
		return EdorState;
	}
	public void setEdorState(String aEdorState)
	{
		EdorState = aEdorState;
	}
	/**
	* 0---申请中未经过核保；<p>
	* 5-----核保但是没有完全通过；<p>
	* 9------核保通过
	*/
	public String getUWState()
	{
		return UWState;
	}
	public void setUWState(String aUWState)
	{
		UWState = aUWState;
	}
	/**
	* 转入公共帐户(0-不转入,1-转入公共帐户)
	*/
	public String getPubAccFlag()
	{
		return PubAccFlag;
	}
	public void setPubAccFlag(String aPubAccFlag)
	{
		PubAccFlag = aPubAccFlag;
	}
	public String getApproveCode()
	{
		return ApproveCode;
	}
	public void setApproveCode(String aApproveCode)
	{
		ApproveCode = aApproveCode;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
	}
	public String getConfOperator()
	{
		return ConfOperator;
	}
	public void setConfOperator(String aConfOperator)
	{
		ConfOperator = aConfOperator;
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

	public String getConfTime()
	{
		return ConfTime;
	}
	public void setConfTime(String aConfTime)
	{
		ConfTime = aConfTime;
	}
	public double getChgCessAmt()
	{
		return ChgCessAmt;
	}
	public void setChgCessAmt(double aChgCessAmt)
	{
		ChgCessAmt = aChgCessAmt;
	}
	public void setChgCessAmt(String aChgCessAmt)
	{
		if (aChgCessAmt != null && !aChgCessAmt.equals(""))
		{
			Double tDouble = new Double(aChgCessAmt);
			double d = tDouble.doubleValue();
			ChgCessAmt = d;
		}
	}

	public double getShRePrem()
	{
		return ShRePrem;
	}
	public void setShRePrem(double aShRePrem)
	{
		ShRePrem = aShRePrem;
	}
	public void setShRePrem(String aShRePrem)
	{
		if (aShRePrem != null && !aShRePrem.equals(""))
		{
			Double tDouble = new Double(aShRePrem);
			double d = tDouble.doubleValue();
			ShRePrem = d;
		}
	}

	public double getShReComm()
	{
		return ShReComm;
	}
	public void setShReComm(double aShReComm)
	{
		ShReComm = aShReComm;
	}
	public void setShReComm(String aShReComm)
	{
		if (aShReComm != null && !aShReComm.equals(""))
		{
			Double tDouble = new Double(aShReComm);
			double d = tDouble.doubleValue();
			ShReComm = d;
		}
	}

	public String getCessStart()
	{
		if( CessStart != null )
			return fDate.getString(CessStart);
		else
			return null;
	}
	public void setCessStart(Date aCessStart)
	{
		CessStart = aCessStart;
	}
	public void setCessStart(String aCessStart)
	{
		if (aCessStart != null && !aCessStart.equals("") )
		{
			CessStart = fDate.getDate( aCessStart );
		}
		else
			CessStart = null;
	}

	public String getCessEnd()
	{
		if( CessEnd != null )
			return fDate.getString(CessEnd);
		else
			return null;
	}
	public void setCessEnd(Date aCessEnd)
	{
		CessEnd = aCessEnd;
	}
	public void setCessEnd(String aCessEnd)
	{
		if (aCessEnd != null && !aCessEnd.equals("") )
		{
			CessEnd = fDate.getDate( aCessEnd );
		}
		else
			CessEnd = null;
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
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
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
	public String getRiskCalSort()
	{
		return RiskCalSort;
	}
	public void setRiskCalSort(String aRiskCalSort)
	{
		RiskCalSort = aRiskCalSort;
	}
	public String getEdorAcceptNo()
	{
		return EdorAcceptNo;
	}
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		EdorAcceptNo = aEdorAcceptNo;
	}
	public String getTransSaleChnl()
	{
		return TransSaleChnl;
	}
	public void setTransSaleChnl(String aTransSaleChnl)
	{
		TransSaleChnl = aTransSaleChnl;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}

	/**
	* 使用另外一个 LREdorMainSchema 对象给 Schema 赋值
	* @param: aLREdorMainSchema LREdorMainSchema
	**/
	public void setSchema(LREdorMainSchema aLREdorMainSchema)
	{
		this.EdorNo = aLREdorMainSchema.getEdorNo();
		this.PolNo = aLREdorMainSchema.getPolNo();
		this.EdorType = aLREdorMainSchema.getEdorType();
		this.ReinsureCom = aLREdorMainSchema.getReinsureCom();
		this.ReinsurItem = aLREdorMainSchema.getReinsurItem();
		this.InsuredYear = aLREdorMainSchema.getInsuredYear();
		this.EdorAppNo = aLREdorMainSchema.getEdorAppNo();
		this.ManageCom = aLREdorMainSchema.getManageCom();
		this.ContNo = aLREdorMainSchema.getContNo();
		this.GrpPolNo = aLREdorMainSchema.getGrpPolNo();
		this.InsuredNo = aLREdorMainSchema.getInsuredNo();
		this.InsuredName = aLREdorMainSchema.getInsuredName();
		this.PaytoDate = fDate.getDate( aLREdorMainSchema.getPaytoDate());
		this.SumPrem = aLREdorMainSchema.getSumPrem();
		this.CalCode = aLREdorMainSchema.getCalCode();
		this.ChgPrem = aLREdorMainSchema.getChgPrem();
		this.ChgAmnt = aLREdorMainSchema.getChgAmnt();
		this.ChgGetAmnt = aLREdorMainSchema.getChgGetAmnt();
		this.GetMoney = aLREdorMainSchema.getGetMoney();
		this.GetInterest = aLREdorMainSchema.getGetInterest();
		this.EdorValiDate = fDate.getDate( aLREdorMainSchema.getEdorValiDate());
		this.EdorAppValiDate = fDate.getDate( aLREdorMainSchema.getEdorAppValiDate());
		this.EdorAppDate = fDate.getDate( aLREdorMainSchema.getEdorAppDate());
		this.EdorState = aLREdorMainSchema.getEdorState();
		this.UWState = aLREdorMainSchema.getUWState();
		this.PubAccFlag = aLREdorMainSchema.getPubAccFlag();
		this.ApproveCode = aLREdorMainSchema.getApproveCode();
		this.ApproveDate = fDate.getDate( aLREdorMainSchema.getApproveDate());
		this.UWOperator = aLREdorMainSchema.getUWOperator();
		this.ConfOperator = aLREdorMainSchema.getConfOperator();
		this.ConfDate = fDate.getDate( aLREdorMainSchema.getConfDate());
		this.ConfTime = aLREdorMainSchema.getConfTime();
		this.ChgCessAmt = aLREdorMainSchema.getChgCessAmt();
		this.ShRePrem = aLREdorMainSchema.getShRePrem();
		this.ShReComm = aLREdorMainSchema.getShReComm();
		this.CessStart = fDate.getDate( aLREdorMainSchema.getCessStart());
		this.CessEnd = fDate.getDate( aLREdorMainSchema.getCessEnd());
		this.Operator = aLREdorMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aLREdorMainSchema.getMakeDate());
		this.MakeTime = aLREdorMainSchema.getMakeTime();
		this.SerialNo = aLREdorMainSchema.getSerialNo();
		this.ModifyDate = fDate.getDate( aLREdorMainSchema.getModifyDate());
		this.ModifyTime = aLREdorMainSchema.getModifyTime();
		this.RiskCalSort = aLREdorMainSchema.getRiskCalSort();
		this.EdorAcceptNo = aLREdorMainSchema.getEdorAcceptNo();
		this.TransSaleChnl = aLREdorMainSchema.getTransSaleChnl();
		this.DutyCode = aLREdorMainSchema.getDutyCode();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("ReinsurItem") == null )
				this.ReinsurItem = null;
			else
				this.ReinsurItem = rs.getString("ReinsurItem").trim();

			this.InsuredYear = rs.getInt("InsuredYear");
			if( rs.getString("EdorAppNo") == null )
				this.EdorAppNo = null;
			else
				this.EdorAppNo = rs.getString("EdorAppNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			this.PaytoDate = rs.getDate("PaytoDate");
			this.SumPrem = rs.getDouble("SumPrem");
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			this.ChgPrem = rs.getDouble("ChgPrem");
			this.ChgAmnt = rs.getDouble("ChgAmnt");
			this.ChgGetAmnt = rs.getDouble("ChgGetAmnt");
			this.GetMoney = rs.getDouble("GetMoney");
			this.GetInterest = rs.getDouble("GetInterest");
			this.EdorValiDate = rs.getDate("EdorValiDate");
			this.EdorAppValiDate = rs.getDate("EdorAppValiDate");
			this.EdorAppDate = rs.getDate("EdorAppDate");
			if( rs.getString("EdorState") == null )
				this.EdorState = null;
			else
				this.EdorState = rs.getString("EdorState").trim();

			if( rs.getString("UWState") == null )
				this.UWState = null;
			else
				this.UWState = rs.getString("UWState").trim();

			if( rs.getString("PubAccFlag") == null )
				this.PubAccFlag = null;
			else
				this.PubAccFlag = rs.getString("PubAccFlag").trim();

			if( rs.getString("ApproveCode") == null )
				this.ApproveCode = null;
			else
				this.ApproveCode = rs.getString("ApproveCode").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			if( rs.getString("ConfOperator") == null )
				this.ConfOperator = null;
			else
				this.ConfOperator = rs.getString("ConfOperator").trim();

			this.ConfDate = rs.getDate("ConfDate");
			if( rs.getString("ConfTime") == null )
				this.ConfTime = null;
			else
				this.ConfTime = rs.getString("ConfTime").trim();

			this.ChgCessAmt = rs.getDouble("ChgCessAmt");
			this.ShRePrem = rs.getDouble("ShRePrem");
			this.ShReComm = rs.getDouble("ShReComm");
			this.CessStart = rs.getDate("CessStart");
			this.CessEnd = rs.getDate("CessEnd");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("RiskCalSort") == null )
				this.RiskCalSort = null;
			else
				this.RiskCalSort = rs.getString("RiskCalSort").trim();

			if( rs.getString("EdorAcceptNo") == null )
				this.EdorAcceptNo = null;
			else
				this.EdorAcceptNo = rs.getString("EdorAcceptNo").trim();

			if( rs.getString("TransSaleChnl") == null )
				this.TransSaleChnl = null;
			else
				this.TransSaleChnl = rs.getString("TransSaleChnl").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LREdorMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LREdorMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LREdorMainSchema getSchema()
	{
		LREdorMainSchema aLREdorMainSchema = new LREdorMainSchema();
		aLREdorMainSchema.setSchema(this);
		return aLREdorMainSchema;
	}

	public LREdorMainDB getDB()
	{
		LREdorMainDB aDBOper = new LREdorMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLREdorMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsurItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuredYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( PaytoDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgGetAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorAppValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorAppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PubAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgCessAmt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShRePrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ShReComm));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CessEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransSaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLREdorMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReinsurItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			EdorAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PaytoDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			SumPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ChgPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			ChgAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			ChgGetAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			GetInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			EdorValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			EdorAppValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			EdorAppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			EdorState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			PubAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ApproveCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ConfOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ConfDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ConfTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ChgCessAmt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,33,SysConst.PACKAGESPILTER))).doubleValue();
			ShRePrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,34,SysConst.PACKAGESPILTER))).doubleValue();
			ShReComm = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			CessStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36,SysConst.PACKAGESPILTER));
			CessEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
			RiskCalSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 44, SysConst.PACKAGESPILTER );
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 45, SysConst.PACKAGESPILTER );
			TransSaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 46, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 47, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LREdorMainSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurItem));
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredYear));
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumPrem));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgPrem));
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgAmnt));
		}
		if (FCode.equalsIgnoreCase("ChgGetAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgGetAmnt));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetInterest));
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
		}
		if (FCode.equalsIgnoreCase("EdorAppValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppValiDate()));
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorState));
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWState));
		}
		if (FCode.equalsIgnoreCase("PubAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PubAccFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveCode));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfOperator));
		}
		if (FCode.equalsIgnoreCase("ConfDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
		}
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfTime));
		}
		if (FCode.equalsIgnoreCase("ChgCessAmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgCessAmt));
		}
		if (FCode.equalsIgnoreCase("ShRePrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShRePrem));
		}
		if (FCode.equalsIgnoreCase("ShReComm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShReComm));
		}
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalSort));
		}
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSaleChnl));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReinsurItem);
				break;
			case 5:
				strFieldValue = String.valueOf(InsuredYear);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EdorAppNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getPaytoDate()));
				break;
			case 13:
				strFieldValue = String.valueOf(SumPrem);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 15:
				strFieldValue = String.valueOf(ChgPrem);
				break;
			case 16:
				strFieldValue = String.valueOf(ChgAmnt);
				break;
			case 17:
				strFieldValue = String.valueOf(ChgGetAmnt);
				break;
			case 18:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 19:
				strFieldValue = String.valueOf(GetInterest);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppValiDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(EdorState);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(PubAccFlag);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ApproveCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ConfOperator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ConfTime);
				break;
			case 32:
				strFieldValue = String.valueOf(ChgCessAmt);
				break;
			case 33:
				strFieldValue = String.valueOf(ShRePrem);
				break;
			case 34:
				strFieldValue = String.valueOf(ShReComm);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessStart()));
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCessEnd()));
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 43:
				strFieldValue = StrTool.GBKToUnicode(RiskCalSort);
				break;
			case 44:
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 45:
				strFieldValue = StrTool.GBKToUnicode(TransSaleChnl);
				break;
			case 46:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureCom = FValue.trim();
			}
			else
				ReinsureCom = null;
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsurItem = FValue.trim();
			}
			else
				ReinsurItem = null;
		}
		if (FCode.equalsIgnoreCase("InsuredYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuredYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppNo = FValue.trim();
			}
			else
				EdorAppNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
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
		if (FCode.equalsIgnoreCase("PaytoDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				PaytoDate = fDate.getDate( FValue );
			}
			else
				PaytoDate = null;
		}
		if (FCode.equalsIgnoreCase("SumPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChgGetAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgGetAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetInterest = d;
			}
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorValiDate = fDate.getDate( FValue );
			}
			else
				EdorValiDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorAppValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorAppValiDate = fDate.getDate( FValue );
			}
			else
				EdorAppValiDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorAppDate = fDate.getDate( FValue );
			}
			else
				EdorAppDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorState = FValue.trim();
			}
			else
				EdorState = null;
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWState = FValue.trim();
			}
			else
				UWState = null;
		}
		if (FCode.equalsIgnoreCase("PubAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PubAccFlag = FValue.trim();
			}
			else
				PubAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveCode = FValue.trim();
			}
			else
				ApproveCode = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfOperator = FValue.trim();
			}
			else
				ConfOperator = null;
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
		if (FCode.equalsIgnoreCase("ConfTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfTime = FValue.trim();
			}
			else
				ConfTime = null;
		}
		if (FCode.equalsIgnoreCase("ChgCessAmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgCessAmt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ShRePrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ShRePrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ShReComm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ShReComm = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessStart = fDate.getDate( FValue );
			}
			else
				CessStart = null;
		}
		if (FCode.equalsIgnoreCase("CessEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CessEnd = fDate.getDate( FValue );
			}
			else
				CessEnd = null;
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalSort = FValue.trim();
			}
			else
				RiskCalSort = null;
		}
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("TransSaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSaleChnl = FValue.trim();
			}
			else
				TransSaleChnl = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LREdorMainSchema other = (LREdorMainSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& PolNo.equals(other.getPolNo())
			&& EdorType.equals(other.getEdorType())
			&& ReinsureCom.equals(other.getReinsureCom())
			&& ReinsurItem.equals(other.getReinsurItem())
			&& InsuredYear == other.getInsuredYear()
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& ManageCom.equals(other.getManageCom())
			&& ContNo.equals(other.getContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& InsuredName.equals(other.getInsuredName())
			&& fDate.getString(PaytoDate).equals(other.getPaytoDate())
			&& SumPrem == other.getSumPrem()
			&& CalCode.equals(other.getCalCode())
			&& ChgPrem == other.getChgPrem()
			&& ChgAmnt == other.getChgAmnt()
			&& ChgGetAmnt == other.getChgGetAmnt()
			&& GetMoney == other.getGetMoney()
			&& GetInterest == other.getGetInterest()
			&& fDate.getString(EdorValiDate).equals(other.getEdorValiDate())
			&& fDate.getString(EdorAppValiDate).equals(other.getEdorAppValiDate())
			&& fDate.getString(EdorAppDate).equals(other.getEdorAppDate())
			&& EdorState.equals(other.getEdorState())
			&& UWState.equals(other.getUWState())
			&& PubAccFlag.equals(other.getPubAccFlag())
			&& ApproveCode.equals(other.getApproveCode())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& UWOperator.equals(other.getUWOperator())
			&& ConfOperator.equals(other.getConfOperator())
			&& fDate.getString(ConfDate).equals(other.getConfDate())
			&& ConfTime.equals(other.getConfTime())
			&& ChgCessAmt == other.getChgCessAmt()
			&& ShRePrem == other.getShRePrem()
			&& ShReComm == other.getShReComm()
			&& fDate.getString(CessStart).equals(other.getCessStart())
			&& fDate.getString(CessEnd).equals(other.getCessEnd())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& SerialNo.equals(other.getSerialNo())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RiskCalSort.equals(other.getRiskCalSort())
			&& EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& TransSaleChnl.equals(other.getTransSaleChnl())
			&& DutyCode.equals(other.getDutyCode());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("EdorType") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return 3;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return 5;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("ContNo") ) {
			return 8;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 9;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 10;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 11;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return 12;
		}
		if( strFieldName.equals("SumPrem") ) {
			return 13;
		}
		if( strFieldName.equals("CalCode") ) {
			return 14;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return 15;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return 16;
		}
		if( strFieldName.equals("ChgGetAmnt") ) {
			return 17;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 18;
		}
		if( strFieldName.equals("GetInterest") ) {
			return 19;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return 20;
		}
		if( strFieldName.equals("EdorAppValiDate") ) {
			return 21;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return 22;
		}
		if( strFieldName.equals("EdorState") ) {
			return 23;
		}
		if( strFieldName.equals("UWState") ) {
			return 24;
		}
		if( strFieldName.equals("PubAccFlag") ) {
			return 25;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return 26;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 27;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 28;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return 29;
		}
		if( strFieldName.equals("ConfDate") ) {
			return 30;
		}
		if( strFieldName.equals("ConfTime") ) {
			return 31;
		}
		if( strFieldName.equals("ChgCessAmt") ) {
			return 32;
		}
		if( strFieldName.equals("ShRePrem") ) {
			return 33;
		}
		if( strFieldName.equals("ShReComm") ) {
			return 34;
		}
		if( strFieldName.equals("CessStart") ) {
			return 35;
		}
		if( strFieldName.equals("CessEnd") ) {
			return 36;
		}
		if( strFieldName.equals("Operator") ) {
			return 37;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 38;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 39;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 40;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 41;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 42;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return 43;
		}
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 44;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return 45;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 46;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "EdorType";
				break;
			case 3:
				strFieldName = "ReinsureCom";
				break;
			case 4:
				strFieldName = "ReinsurItem";
				break;
			case 5:
				strFieldName = "InsuredYear";
				break;
			case 6:
				strFieldName = "EdorAppNo";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "ContNo";
				break;
			case 9:
				strFieldName = "GrpPolNo";
				break;
			case 10:
				strFieldName = "InsuredNo";
				break;
			case 11:
				strFieldName = "InsuredName";
				break;
			case 12:
				strFieldName = "PaytoDate";
				break;
			case 13:
				strFieldName = "SumPrem";
				break;
			case 14:
				strFieldName = "CalCode";
				break;
			case 15:
				strFieldName = "ChgPrem";
				break;
			case 16:
				strFieldName = "ChgAmnt";
				break;
			case 17:
				strFieldName = "ChgGetAmnt";
				break;
			case 18:
				strFieldName = "GetMoney";
				break;
			case 19:
				strFieldName = "GetInterest";
				break;
			case 20:
				strFieldName = "EdorValiDate";
				break;
			case 21:
				strFieldName = "EdorAppValiDate";
				break;
			case 22:
				strFieldName = "EdorAppDate";
				break;
			case 23:
				strFieldName = "EdorState";
				break;
			case 24:
				strFieldName = "UWState";
				break;
			case 25:
				strFieldName = "PubAccFlag";
				break;
			case 26:
				strFieldName = "ApproveCode";
				break;
			case 27:
				strFieldName = "ApproveDate";
				break;
			case 28:
				strFieldName = "UWOperator";
				break;
			case 29:
				strFieldName = "ConfOperator";
				break;
			case 30:
				strFieldName = "ConfDate";
				break;
			case 31:
				strFieldName = "ConfTime";
				break;
			case 32:
				strFieldName = "ChgCessAmt";
				break;
			case 33:
				strFieldName = "ShRePrem";
				break;
			case 34:
				strFieldName = "ShReComm";
				break;
			case 35:
				strFieldName = "CessStart";
				break;
			case 36:
				strFieldName = "CessEnd";
				break;
			case 37:
				strFieldName = "Operator";
				break;
			case 38:
				strFieldName = "MakeDate";
				break;
			case 39:
				strFieldName = "MakeTime";
				break;
			case 40:
				strFieldName = "SerialNo";
				break;
			case 41:
				strFieldName = "ModifyDate";
				break;
			case 42:
				strFieldName = "ModifyTime";
				break;
			case 43:
				strFieldName = "RiskCalSort";
				break;
			case 44:
				strFieldName = "EdorAcceptNo";
				break;
			case 45:
				strFieldName = "TransSaleChnl";
				break;
			case 46:
				strFieldName = "DutyCode";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaytoDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SumPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChgGetAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetInterest") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorAppValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PubAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgCessAmt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ShRePrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ShReComm") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CessEnd") ) {
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransSaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
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
			case 32:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 33:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 43:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 44:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 45:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 46:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
