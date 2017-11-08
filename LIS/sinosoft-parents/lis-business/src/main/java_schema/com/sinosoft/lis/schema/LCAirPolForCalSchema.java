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
import com.sinosoft.lis.db.LCAirPolForCalDB;

/*
 * <p>ClassName: LCAirPolForCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 短期险平台接口表
 */
public class LCAirPolForCalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCAirPolForCalSchema.class);

	// @Field
	/** 保单号 */
	private String PolNo;
	/** 单证类型编码 */
	private String CertifyCode;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构编码 */
	private String AgentCom;
	/** 密码 */
	private String Password;
	/** 被保险人姓名 */
	private String InsuredName;
	/** 被保险人证件类型 */
	private String InsuredIDType;
	/** 被保险人证件号 */
	private String InsuredIDNo;
	/** 与被保险人关系 */
	private String RelationToInsured;
	/** 受益人地址 */
	private String BnfAddress;
	/** 受益人姓名 */
	private String BnfName;
	/** 受益人邮编 */
	private String BnfZipCode;
	/** 受益人联系电话 */
	private String BnfPhone;
	/** 份数 */
	private double Mult;
	/** 保费 */
	private double Prem;
	/** 保额 */
	private double Amnt;
	/** 保单状态 */
	private String PolState;
	/** 乘机日期 */
	private Date EmplaneDate;
	/** 航班号 */
	private String FlightNo;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人组别 */
	private String AgentGroup;
	/** 保险年期年龄标志 */
	private String InsuYearFlag;
	/** 保险年期年龄 */
	private int InsuYear;
	/** 终止日期 */
	private Date EndDate;
	/** 生效日期 */
	private Date CValiDate;
	/** 代理公司登录用户 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 签单日期 */
	private Date TSignDate;
	/** 险种编码 */
	private String RiskCode;
	/** 状态 */
	private String State;
	/** 险种款式 */
	private String RiskType;
	/** 前往国家 */
	private String Destination;
	/** 出国事由 */
	private String Reason;
	/** 被保人性别 */
	private String InsuredSex;
	/** 被保人出生日期 */
	private Date InsuredBirthday;
	/** 被保人职业类别 */
	private String OccupationType;
	/** 单证印刷号 */
	private String PrtNo;
	/** 预留字段1 */
	private String StandByFlag1;
	/** 预留字段2 */
	private String StandByFlag2;
	/** 预留字段3 */
	private String StandByFlag3;

	public static final int FIELDNUM = 43;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAirPolForCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PolNo";

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
		LCAirPolForCalSchema cloned = (LCAirPolForCalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getPassword()
	{
		return Password;
	}
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	public String getInsuredName()
	{
		return InsuredName;
	}
	public void setInsuredName(String aInsuredName)
	{
		InsuredName = aInsuredName;
	}
	/**
	* 0                   	身份证<p>
	* 1                   	护照<p>
	* 2                   	军官证<p>
	* 3                   	驾照<p>
	* 4                   	户口本<p>
	* 5                   	学生证<p>
	* 6                   	工作证<p>
	* 8                   	其它<p>
	* 9                   	无证件
	*/
	public String getInsuredIDType()
	{
		return InsuredIDType;
	}
	public void setInsuredIDType(String aInsuredIDType)
	{
		InsuredIDType = aInsuredIDType;
	}
	public String getInsuredIDNo()
	{
		return InsuredIDNo;
	}
	public void setInsuredIDNo(String aInsuredIDNo)
	{
		InsuredIDNo = aInsuredIDNo;
	}
	/**
	* 00                  	本人<p>
	* 01                  	丈夫<p>
	* 02                  	妻子<p>
	* 03                  	父亲<p>
	* 04                  	母亲<p>
	* 05                  	儿子<p>
	* 06                  	女儿<p>
	* 07                  	祖父<p>
	* 08                  	祖母<p>
	* 09                  	孙子<p>
	* 10                  	孙女<p>
	* 11                  	外祖父<p>
	* 12                  	外祖母<p>
	* 13                  	外孙<p>
	* 14                  	外孙女<p>
	* 15                  	哥哥<p>
	* 16                  	姐姐<p>
	* 17                  	弟弟<p>
	* 18                  	妹妹<p>
	* 19                  	公公<p>
	* 20                  	婆婆<p>
	* 21                  	岳父<p>
	* 22                  	岳母<p>
	* 23                  	儿媳<p>
	* 24                  	女婿<p>
	* 25                  	其他亲属<p>
	* 26                  	同事<p>
	* 27                  	朋友<p>
	* 28                  	雇主<p>
	* 29                  	雇员<p>
	* 30                  	其他<p>
	* 31                  	父母<p>
	* 32                  	子女<p>
	* 33                  	配偶
	*/
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	public String getBnfAddress()
	{
		return BnfAddress;
	}
	public void setBnfAddress(String aBnfAddress)
	{
		BnfAddress = aBnfAddress;
	}
	public String getBnfName()
	{
		return BnfName;
	}
	public void setBnfName(String aBnfName)
	{
		BnfName = aBnfName;
	}
	public String getBnfZipCode()
	{
		return BnfZipCode;
	}
	public void setBnfZipCode(String aBnfZipCode)
	{
		BnfZipCode = aBnfZipCode;
	}
	public String getBnfPhone()
	{
		return BnfPhone;
	}
	public void setBnfPhone(String aBnfPhone)
	{
		BnfPhone = aBnfPhone;
	}
	public double getMult()
	{
		return Mult;
	}
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}

	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	/**
	* 2-撤单,1-有效
	*/
	public String getPolState()
	{
		return PolState;
	}
	public void setPolState(String aPolState)
	{
		PolState = aPolState;
	}
	/**
	* 未用<p>
	* 老的航意险用，新的平台已经不用了
	*/
	public String getEmplaneDate()
	{
		if( EmplaneDate != null )
			return fDate.getString(EmplaneDate);
		else
			return null;
	}
	public void setEmplaneDate(Date aEmplaneDate)
	{
		EmplaneDate = aEmplaneDate;
	}
	public void setEmplaneDate(String aEmplaneDate)
	{
		if (aEmplaneDate != null && !aEmplaneDate.equals("") )
		{
			EmplaneDate = fDate.getDate( aEmplaneDate );
		}
		else
			EmplaneDate = null;
	}

	public String getFlightNo()
	{
		return FlightNo;
	}
	public void setFlightNo(String aFlightNo)
	{
		FlightNo = aFlightNo;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getInsuYearFlag()
	{
		return InsuYearFlag;
	}
	public void setInsuYearFlag(String aInsuYearFlag)
	{
		InsuYearFlag = aInsuYearFlag;
	}
	public int getInsuYear()
	{
		return InsuYear;
	}
	public void setInsuYear(int aInsuYear)
	{
		InsuYear = aInsuYear;
	}
	public void setInsuYear(String aInsuYear)
	{
		if (aInsuYear != null && !aInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aInsuYear);
			int i = tInteger.intValue();
			InsuYear = i;
		}
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getCValiDate()
	{
		if( CValiDate != null )
			return fDate.getString(CValiDate);
		else
			return null;
	}
	public void setCValiDate(Date aCValiDate)
	{
		CValiDate = aCValiDate;
	}
	public void setCValiDate(String aCValiDate)
	{
		if (aCValiDate != null && !aCValiDate.equals("") )
		{
			CValiDate = fDate.getDate( aCValiDate );
		}
		else
			CValiDate = null;
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
	public String getTSignDate()
	{
		if( TSignDate != null )
			return fDate.getString(TSignDate);
		else
			return null;
	}
	public void setTSignDate(Date aTSignDate)
	{
		TSignDate = aTSignDate;
	}
	public void setTSignDate(String aTSignDate)
	{
		if (aTSignDate != null && !aTSignDate.equals("") )
		{
			TSignDate = fDate.getDate( aTSignDate );
		}
		else
			TSignDate = null;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 1 初始化时置A;<p>
	*  2 倒团单进入核心时置B(保单结算界面保存选中保单按钮);<p>
	*  3 完全导入成功后表示已经在核心中有记录了,则置C,此时在航意险结算时就不会再查询到了! <p>
	* 待结算保单表中此字段都为B
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	public String getDestination()
	{
		return Destination;
	}
	public void setDestination(String aDestination)
	{
		Destination = aDestination;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		Reason = aReason;
	}
	public String getInsuredSex()
	{
		return InsuredSex;
	}
	public void setInsuredSex(String aInsuredSex)
	{
		InsuredSex = aInsuredSex;
	}
	public String getInsuredBirthday()
	{
		if( InsuredBirthday != null )
			return fDate.getString(InsuredBirthday);
		else
			return null;
	}
	public void setInsuredBirthday(Date aInsuredBirthday)
	{
		InsuredBirthday = aInsuredBirthday;
	}
	public void setInsuredBirthday(String aInsuredBirthday)
	{
		if (aInsuredBirthday != null && !aInsuredBirthday.equals("") )
		{
			InsuredBirthday = fDate.getDate( aInsuredBirthday );
		}
		else
			InsuredBirthday = null;
	}

	public String getOccupationType()
	{
		return OccupationType;
	}
	public void setOccupationType(String aOccupationType)
	{
		OccupationType = aOccupationType;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}
	public String getStandByFlag3()
	{
		return StandByFlag3;
	}
	public void setStandByFlag3(String aStandByFlag3)
	{
		StandByFlag3 = aStandByFlag3;
	}

	/**
	* 使用另外一个 LCAirPolForCalSchema 对象给 Schema 赋值
	* @param: aLCAirPolForCalSchema LCAirPolForCalSchema
	**/
	public void setSchema(LCAirPolForCalSchema aLCAirPolForCalSchema)
	{
		this.PolNo = aLCAirPolForCalSchema.getPolNo();
		this.CertifyCode = aLCAirPolForCalSchema.getCertifyCode();
		this.ManageCom = aLCAirPolForCalSchema.getManageCom();
		this.AgentCom = aLCAirPolForCalSchema.getAgentCom();
		this.Password = aLCAirPolForCalSchema.getPassword();
		this.InsuredName = aLCAirPolForCalSchema.getInsuredName();
		this.InsuredIDType = aLCAirPolForCalSchema.getInsuredIDType();
		this.InsuredIDNo = aLCAirPolForCalSchema.getInsuredIDNo();
		this.RelationToInsured = aLCAirPolForCalSchema.getRelationToInsured();
		this.BnfAddress = aLCAirPolForCalSchema.getBnfAddress();
		this.BnfName = aLCAirPolForCalSchema.getBnfName();
		this.BnfZipCode = aLCAirPolForCalSchema.getBnfZipCode();
		this.BnfPhone = aLCAirPolForCalSchema.getBnfPhone();
		this.Mult = aLCAirPolForCalSchema.getMult();
		this.Prem = aLCAirPolForCalSchema.getPrem();
		this.Amnt = aLCAirPolForCalSchema.getAmnt();
		this.PolState = aLCAirPolForCalSchema.getPolState();
		this.EmplaneDate = fDate.getDate( aLCAirPolForCalSchema.getEmplaneDate());
		this.FlightNo = aLCAirPolForCalSchema.getFlightNo();
		this.AgentCode = aLCAirPolForCalSchema.getAgentCode();
		this.AgentGroup = aLCAirPolForCalSchema.getAgentGroup();
		this.InsuYearFlag = aLCAirPolForCalSchema.getInsuYearFlag();
		this.InsuYear = aLCAirPolForCalSchema.getInsuYear();
		this.EndDate = fDate.getDate( aLCAirPolForCalSchema.getEndDate());
		this.CValiDate = fDate.getDate( aLCAirPolForCalSchema.getCValiDate());
		this.Operator = aLCAirPolForCalSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCAirPolForCalSchema.getMakeDate());
		this.MakeTime = aLCAirPolForCalSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCAirPolForCalSchema.getModifyDate());
		this.ModifyTime = aLCAirPolForCalSchema.getModifyTime();
		this.TSignDate = fDate.getDate( aLCAirPolForCalSchema.getTSignDate());
		this.RiskCode = aLCAirPolForCalSchema.getRiskCode();
		this.State = aLCAirPolForCalSchema.getState();
		this.RiskType = aLCAirPolForCalSchema.getRiskType();
		this.Destination = aLCAirPolForCalSchema.getDestination();
		this.Reason = aLCAirPolForCalSchema.getReason();
		this.InsuredSex = aLCAirPolForCalSchema.getInsuredSex();
		this.InsuredBirthday = fDate.getDate( aLCAirPolForCalSchema.getInsuredBirthday());
		this.OccupationType = aLCAirPolForCalSchema.getOccupationType();
		this.PrtNo = aLCAirPolForCalSchema.getPrtNo();
		this.StandByFlag1 = aLCAirPolForCalSchema.getStandByFlag1();
		this.StandByFlag2 = aLCAirPolForCalSchema.getStandByFlag2();
		this.StandByFlag3 = aLCAirPolForCalSchema.getStandByFlag3();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("InsuredName") == null )
				this.InsuredName = null;
			else
				this.InsuredName = rs.getString("InsuredName").trim();

			if( rs.getString("InsuredIDType") == null )
				this.InsuredIDType = null;
			else
				this.InsuredIDType = rs.getString("InsuredIDType").trim();

			if( rs.getString("InsuredIDNo") == null )
				this.InsuredIDNo = null;
			else
				this.InsuredIDNo = rs.getString("InsuredIDNo").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("BnfAddress") == null )
				this.BnfAddress = null;
			else
				this.BnfAddress = rs.getString("BnfAddress").trim();

			if( rs.getString("BnfName") == null )
				this.BnfName = null;
			else
				this.BnfName = rs.getString("BnfName").trim();

			if( rs.getString("BnfZipCode") == null )
				this.BnfZipCode = null;
			else
				this.BnfZipCode = rs.getString("BnfZipCode").trim();

			if( rs.getString("BnfPhone") == null )
				this.BnfPhone = null;
			else
				this.BnfPhone = rs.getString("BnfPhone").trim();

			this.Mult = rs.getDouble("Mult");
			this.Prem = rs.getDouble("Prem");
			this.Amnt = rs.getDouble("Amnt");
			if( rs.getString("PolState") == null )
				this.PolState = null;
			else
				this.PolState = rs.getString("PolState").trim();

			this.EmplaneDate = rs.getDate("EmplaneDate");
			if( rs.getString("FlightNo") == null )
				this.FlightNo = null;
			else
				this.FlightNo = rs.getString("FlightNo").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("InsuYearFlag") == null )
				this.InsuYearFlag = null;
			else
				this.InsuYearFlag = rs.getString("InsuYearFlag").trim();

			this.InsuYear = rs.getInt("InsuYear");
			this.EndDate = rs.getDate("EndDate");
			this.CValiDate = rs.getDate("CValiDate");
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

			this.TSignDate = rs.getDate("TSignDate");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("Destination") == null )
				this.Destination = null;
			else
				this.Destination = rs.getString("Destination").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("InsuredSex") == null )
				this.InsuredSex = null;
			else
				this.InsuredSex = rs.getString("InsuredSex").trim();

			this.InsuredBirthday = rs.getDate("InsuredBirthday");
			if( rs.getString("OccupationType") == null )
				this.OccupationType = null;
			else
				this.OccupationType = rs.getString("OccupationType").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("StandByFlag3") == null )
				this.StandByFlag3 = null;
			else
				this.StandByFlag3 = rs.getString("StandByFlag3").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCAirPolForCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolForCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCAirPolForCalSchema getSchema()
	{
		LCAirPolForCalSchema aLCAirPolForCalSchema = new LCAirPolForCalSchema();
		aLCAirPolForCalSchema.setSchema(this);
		return aLCAirPolForCalSchema;
	}

	public LCAirPolForCalDB getDB()
	{
		LCAirPolForCalDB aDBOper = new LCAirPolForCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAirPolForCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Password)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredIDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BnfPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EmplaneDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FlightNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuYearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InsuYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TSignDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Destination)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InsuredBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OccupationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAirPolForCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuredName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InsuredIDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuredIDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BnfAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BnfName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BnfZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BnfPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			PolState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			EmplaneDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			FlightNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			InsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).intValue();
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			TSignDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			Destination = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			InsuredSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			InsuredBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38,SysConst.PACKAGESPILTER));
			OccupationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 42, SysConst.PACKAGESPILTER );
			StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 43, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAirPolForCalSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equalsIgnoreCase("InsuredName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredName));
		}
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDType));
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredIDNo));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("BnfAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfAddress));
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfName));
		}
		if (FCode.equalsIgnoreCase("BnfZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfZipCode));
		}
		if (FCode.equalsIgnoreCase("BnfPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BnfPhone));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolState));
		}
		if (FCode.equalsIgnoreCase("EmplaneDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEmplaneDate()));
		}
		if (FCode.equalsIgnoreCase("FlightNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FlightNo));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYearFlag));
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuYear));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
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
		if (FCode.equalsIgnoreCase("TSignDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTSignDate()));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Destination));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredSex));
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OccupationType));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuredName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsuredIDNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BnfAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BnfName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BnfZipCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BnfPhone);
				break;
			case 13:
				strFieldValue = String.valueOf(Mult);
				break;
			case 14:
				strFieldValue = String.valueOf(Prem);
				break;
			case 15:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(PolState);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEmplaneDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(FlightNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InsuYearFlag);
				break;
			case 22:
				strFieldValue = String.valueOf(InsuYear);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTSignDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(Destination);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(InsuredSex);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInsuredBirthday()));
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(OccupationType);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 41:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 42:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
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
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
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
		if (FCode.equalsIgnoreCase("InsuredIDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDType = FValue.trim();
			}
			else
				InsuredIDType = null;
		}
		if (FCode.equalsIgnoreCase("InsuredIDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredIDNo = FValue.trim();
			}
			else
				InsuredIDNo = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("BnfAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfAddress = FValue.trim();
			}
			else
				BnfAddress = null;
		}
		if (FCode.equalsIgnoreCase("BnfName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfName = FValue.trim();
			}
			else
				BnfName = null;
		}
		if (FCode.equalsIgnoreCase("BnfZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfZipCode = FValue.trim();
			}
			else
				BnfZipCode = null;
		}
		if (FCode.equalsIgnoreCase("BnfPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BnfPhone = FValue.trim();
			}
			else
				BnfPhone = null;
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PolState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolState = FValue.trim();
			}
			else
				PolState = null;
		}
		if (FCode.equalsIgnoreCase("EmplaneDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EmplaneDate = fDate.getDate( FValue );
			}
			else
				EmplaneDate = null;
		}
		if (FCode.equalsIgnoreCase("FlightNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FlightNo = FValue.trim();
			}
			else
				FlightNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equalsIgnoreCase("InsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuYearFlag = FValue.trim();
			}
			else
				InsuYearFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InsuYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CValiDate = fDate.getDate( FValue );
			}
			else
				CValiDate = null;
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
		if (FCode.equalsIgnoreCase("TSignDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TSignDate = fDate.getDate( FValue );
			}
			else
				TSignDate = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("Destination"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Destination = FValue.trim();
			}
			else
				Destination = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("InsuredSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredSex = FValue.trim();
			}
			else
				InsuredSex = null;
		}
		if (FCode.equalsIgnoreCase("InsuredBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InsuredBirthday = fDate.getDate( FValue );
			}
			else
				InsuredBirthday = null;
		}
		if (FCode.equalsIgnoreCase("OccupationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OccupationType = FValue.trim();
			}
			else
				OccupationType = null;
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
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag3 = FValue.trim();
			}
			else
				StandByFlag3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCAirPolForCalSchema other = (LCAirPolForCalSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& Password.equals(other.getPassword())
			&& InsuredName.equals(other.getInsuredName())
			&& InsuredIDType.equals(other.getInsuredIDType())
			&& InsuredIDNo.equals(other.getInsuredIDNo())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& BnfAddress.equals(other.getBnfAddress())
			&& BnfName.equals(other.getBnfName())
			&& BnfZipCode.equals(other.getBnfZipCode())
			&& BnfPhone.equals(other.getBnfPhone())
			&& Mult == other.getMult()
			&& Prem == other.getPrem()
			&& Amnt == other.getAmnt()
			&& PolState.equals(other.getPolState())
			&& fDate.getString(EmplaneDate).equals(other.getEmplaneDate())
			&& FlightNo.equals(other.getFlightNo())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& InsuYearFlag.equals(other.getInsuYearFlag())
			&& InsuYear == other.getInsuYear()
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(TSignDate).equals(other.getTSignDate())
			&& RiskCode.equals(other.getRiskCode())
			&& State.equals(other.getState())
			&& RiskType.equals(other.getRiskType())
			&& Destination.equals(other.getDestination())
			&& Reason.equals(other.getReason())
			&& InsuredSex.equals(other.getInsuredSex())
			&& fDate.getString(InsuredBirthday).equals(other.getInsuredBirthday())
			&& OccupationType.equals(other.getOccupationType())
			&& PrtNo.equals(other.getPrtNo())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& StandByFlag3.equals(other.getStandByFlag3());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 3;
		}
		if( strFieldName.equals("Password") ) {
			return 4;
		}
		if( strFieldName.equals("InsuredName") ) {
			return 5;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return 6;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return 7;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 8;
		}
		if( strFieldName.equals("BnfAddress") ) {
			return 9;
		}
		if( strFieldName.equals("BnfName") ) {
			return 10;
		}
		if( strFieldName.equals("BnfZipCode") ) {
			return 11;
		}
		if( strFieldName.equals("BnfPhone") ) {
			return 12;
		}
		if( strFieldName.equals("Mult") ) {
			return 13;
		}
		if( strFieldName.equals("Prem") ) {
			return 14;
		}
		if( strFieldName.equals("Amnt") ) {
			return 15;
		}
		if( strFieldName.equals("PolState") ) {
			return 16;
		}
		if( strFieldName.equals("EmplaneDate") ) {
			return 17;
		}
		if( strFieldName.equals("FlightNo") ) {
			return 18;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 19;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 20;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return 21;
		}
		if( strFieldName.equals("InsuYear") ) {
			return 22;
		}
		if( strFieldName.equals("EndDate") ) {
			return 23;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
		}
		if( strFieldName.equals("TSignDate") ) {
			return 30;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 31;
		}
		if( strFieldName.equals("State") ) {
			return 32;
		}
		if( strFieldName.equals("RiskType") ) {
			return 33;
		}
		if( strFieldName.equals("Destination") ) {
			return 34;
		}
		if( strFieldName.equals("Reason") ) {
			return 35;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return 36;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return 37;
		}
		if( strFieldName.equals("OccupationType") ) {
			return 38;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 39;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 40;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 41;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return 42;
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "CertifyCode";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "AgentCom";
				break;
			case 4:
				strFieldName = "Password";
				break;
			case 5:
				strFieldName = "InsuredName";
				break;
			case 6:
				strFieldName = "InsuredIDType";
				break;
			case 7:
				strFieldName = "InsuredIDNo";
				break;
			case 8:
				strFieldName = "RelationToInsured";
				break;
			case 9:
				strFieldName = "BnfAddress";
				break;
			case 10:
				strFieldName = "BnfName";
				break;
			case 11:
				strFieldName = "BnfZipCode";
				break;
			case 12:
				strFieldName = "BnfPhone";
				break;
			case 13:
				strFieldName = "Mult";
				break;
			case 14:
				strFieldName = "Prem";
				break;
			case 15:
				strFieldName = "Amnt";
				break;
			case 16:
				strFieldName = "PolState";
				break;
			case 17:
				strFieldName = "EmplaneDate";
				break;
			case 18:
				strFieldName = "FlightNo";
				break;
			case 19:
				strFieldName = "AgentCode";
				break;
			case 20:
				strFieldName = "AgentGroup";
				break;
			case 21:
				strFieldName = "InsuYearFlag";
				break;
			case 22:
				strFieldName = "InsuYear";
				break;
			case 23:
				strFieldName = "EndDate";
				break;
			case 24:
				strFieldName = "CValiDate";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyDate";
				break;
			case 29:
				strFieldName = "ModifyTime";
				break;
			case 30:
				strFieldName = "TSignDate";
				break;
			case 31:
				strFieldName = "RiskCode";
				break;
			case 32:
				strFieldName = "State";
				break;
			case 33:
				strFieldName = "RiskType";
				break;
			case 34:
				strFieldName = "Destination";
				break;
			case 35:
				strFieldName = "Reason";
				break;
			case 36:
				strFieldName = "InsuredSex";
				break;
			case 37:
				strFieldName = "InsuredBirthday";
				break;
			case 38:
				strFieldName = "OccupationType";
				break;
			case 39:
				strFieldName = "PrtNo";
				break;
			case 40:
				strFieldName = "StandByFlag1";
				break;
			case 41:
				strFieldName = "StandByFlag2";
				break;
			case 42:
				strFieldName = "StandByFlag3";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredIDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BnfPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PolState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EmplaneDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FlightNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CValiDate") ) {
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
		if( strFieldName.equals("TSignDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Destination") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OccupationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag3") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 40:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 41:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 42:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
