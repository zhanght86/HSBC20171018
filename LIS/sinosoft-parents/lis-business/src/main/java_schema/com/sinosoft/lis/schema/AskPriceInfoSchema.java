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
import com.sinosoft.lis.db.AskPriceInfoDB;

/*
 * <p>ClassName: AskPriceInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: Quotation System
 * @CreateDate：2010-09-04
 */
public class AskPriceInfoSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(AskPriceInfoSchema.class);

	// @Field
	/** 询价编码 */
	private String AskPriceNo;
	/** 询价版本号 */
	private String AskNo;
	/** 展业类型 */
	private String BranchType;
	/** 渠道 */
	private String BranchType2;
	/** 管理机构 */
	private String ManageCom;
	/** 中介机构 */
	private String AgentCom;
	/** 人员编码 */
	private String AgentCode;
	/** 单位名称 */
	private String UnitName;
	/** 企业注册类型 */
	private String RegistrationTypes;
	/** 在职人数 */
	private int Incumbency;
	/** 行业类别 */
	private String IndustryType;
	/** 参加社保类别 */
	private String InsuranceType;
	/** 投保类别 */
	private String Note;
	/** 日期 */
	private Date AskPriceDate;
	/** 询价起期 */
	private Date StartDate;
	/** 询价止期 */
	private Date StopDate;
	/** 疾病及离岗 */
	private String Disease;
	/** 危险作业 */
	private String Dangerous;
	/** 投保不成功原因 */
	private String Changes;
	/** 健康管理 */
	private String Health;
	/** 救援服务 */
	private String Rescue;
	/** 补充说明一 */
	private String Others1;
	/** 补充说明二 */
	private String Others2;
	/** 备注 */
	private String Remarks;
	/** 属性1 */
	private String Attribute1;
	/** 投保单号 */
	private String Attribute2;
	/** 保费 */
	private String Attribute3;
	/** 完成标志 */
	private String Attribute4;
	/** 属性5 */
	private String Attribute5;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 员工人数 */
	private double MainInsured;
	/** 子女人数 */
	private double InsuredOther1;
	/** 配偶人数 */
	private double InsuredOther2;
	/** 其他人数 */
	private double InsuredOther3;
	/** 公共保额 */
	private String PublicInsured;
	/** 医疗基金 */
	private String Medical;
	/** 销售人员 */
	private String Sales;

	public static final int FIELDNUM = 41;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AskPriceInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AskPriceNo";
		pk[1] = "AskNo";

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
                AskPriceInfoSchema cloned = (AskPriceInfoSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAskPriceNo()
	{
		return AskPriceNo;
	}
	public void setAskPriceNo(String aAskPriceNo)
	{
		AskPriceNo = aAskPriceNo;
	}
	public String getAskNo()
	{
		return AskNo;
	}
	public void setAskNo(String aAskNo)
	{
		AskNo = aAskNo;
	}
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	public String getBranchType2()
	{
		return BranchType2;
	}
	public void setBranchType2(String aBranchType2)
	{
		BranchType2 = aBranchType2;
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
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getUnitName()
	{
		return UnitName;
	}
	public void setUnitName(String aUnitName)
	{
		UnitName = aUnitName;
	}
	public String getRegistrationTypes()
	{
		return RegistrationTypes;
	}
	public void setRegistrationTypes(String aRegistrationTypes)
	{
		RegistrationTypes = aRegistrationTypes;
	}
	public int getIncumbency()
	{
		return Incumbency;
	}
	public void setIncumbency(int aIncumbency)
	{
		Incumbency = aIncumbency;
	}
	public void setIncumbency(String aIncumbency)
	{
		if (aIncumbency != null && !aIncumbency.equals(""))
		{
			Integer tInteger = new Integer(aIncumbency);
			int i = tInteger.intValue();
			Incumbency = i;
		}
	}

	public String getIndustryType()
	{
		return IndustryType;
	}
	public void setIndustryType(String aIndustryType)
	{
		IndustryType = aIndustryType;
	}
	public String getInsuranceType()
	{
		return InsuranceType;
	}
	public void setInsuranceType(String aInsuranceType)
	{
		InsuranceType = aInsuranceType;
	}
	public String getNote()
	{
		return Note;
	}
	public void setNote(String aNote)
	{
		Note = aNote;
	}
	public String getAskPriceDate()
	{
		if( AskPriceDate != null )
			return fDate.getString(AskPriceDate);
		else
			return null;
	}
	public void setAskPriceDate(Date aAskPriceDate)
	{
		AskPriceDate = aAskPriceDate;
	}
	public void setAskPriceDate(String aAskPriceDate)
	{
		if (aAskPriceDate != null && !aAskPriceDate.equals("") )
		{
			AskPriceDate = fDate.getDate( aAskPriceDate );
		}
		else
			AskPriceDate = null;
	}

	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getStopDate()
	{
		if( StopDate != null )
			return fDate.getString(StopDate);
		else
			return null;
	}
	public void setStopDate(Date aStopDate)
	{
		StopDate = aStopDate;
	}
	public void setStopDate(String aStopDate)
	{
		if (aStopDate != null && !aStopDate.equals("") )
		{
			StopDate = fDate.getDate( aStopDate );
		}
		else
			StopDate = null;
	}

	public String getDisease()
	{
		return Disease;
	}
	public void setDisease(String aDisease)
	{
		Disease = aDisease;
	}
	public String getDangerous()
	{
		return Dangerous;
	}
	public void setDangerous(String aDangerous)
	{
		Dangerous = aDangerous;
	}
	public String getChanges()
	{
		return Changes;
	}
	public void setChanges(String aChanges)
	{
		Changes = aChanges;
	}
	public String getHealth()
	{
		return Health;
	}
	public void setHealth(String aHealth)
	{
		Health = aHealth;
	}
	public String getRescue()
	{
		return Rescue;
	}
	public void setRescue(String aRescue)
	{
		Rescue = aRescue;
	}
	public String getOthers1()
	{
		return Others1;
	}
	public void setOthers1(String aOthers1)
	{
		Others1 = aOthers1;
	}
	public String getOthers2()
	{
		return Others2;
	}
	public void setOthers2(String aOthers2)
	{
		Others2 = aOthers2;
	}
	public String getRemarks()
	{
		return Remarks;
	}
	public void setRemarks(String aRemarks)
	{
		Remarks = aRemarks;
	}
	public String getAttribute1()
	{
		return Attribute1;
	}
	public void setAttribute1(String aAttribute1)
	{
		Attribute1 = aAttribute1;
	}
	public String getAttribute2()
	{
		return Attribute2;
	}
	public void setAttribute2(String aAttribute2)
	{
		Attribute2 = aAttribute2;
	}
	public String getAttribute3()
	{
		return Attribute3;
	}
	public void setAttribute3(String aAttribute3)
	{
		Attribute3 = aAttribute3;
	}
	public String getAttribute4()
	{
		return Attribute4;
	}
	public void setAttribute4(String aAttribute4)
	{
		Attribute4 = aAttribute4;
	}
	public String getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		Attribute5 = aAttribute5;
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
	public double getMainInsured()
	{
		return MainInsured;
	}
	public void setMainInsured(double aMainInsured)
	{
		MainInsured = aMainInsured;
	}
	public void setMainInsured(String aMainInsured)
	{
		if (aMainInsured != null && !aMainInsured.equals(""))
		{
			Double tDouble = new Double(aMainInsured);
			double d = tDouble.doubleValue();
			MainInsured = d;
		}
	}

	public double getInsuredOther1()
	{
		return InsuredOther1;
	}
	public void setInsuredOther1(double aInsuredOther1)
	{
		InsuredOther1 = aInsuredOther1;
	}
	public void setInsuredOther1(String aInsuredOther1)
	{
		if (aInsuredOther1 != null && !aInsuredOther1.equals(""))
		{
			Double tDouble = new Double(aInsuredOther1);
			double d = tDouble.doubleValue();
			InsuredOther1 = d;
		}
	}

	public double getInsuredOther2()
	{
		return InsuredOther2;
	}
	public void setInsuredOther2(double aInsuredOther2)
	{
		InsuredOther2 = aInsuredOther2;
	}
	public void setInsuredOther2(String aInsuredOther2)
	{
		if (aInsuredOther2 != null && !aInsuredOther2.equals(""))
		{
			Double tDouble = new Double(aInsuredOther2);
			double d = tDouble.doubleValue();
			InsuredOther2 = d;
		}
	}

	public double getInsuredOther3()
	{
		return InsuredOther3;
	}
	public void setInsuredOther3(double aInsuredOther3)
	{
		InsuredOther3 = aInsuredOther3;
	}
	public void setInsuredOther3(String aInsuredOther3)
	{
		if (aInsuredOther3 != null && !aInsuredOther3.equals(""))
		{
			Double tDouble = new Double(aInsuredOther3);
			double d = tDouble.doubleValue();
			InsuredOther3 = d;
		}
	}

	public String getPublicInsured()
	{
		return PublicInsured;
	}
	public void setPublicInsured(String aPublicInsured)
	{
		PublicInsured = aPublicInsured;
	}
	public String getMedical()
	{
		return Medical;
	}
	public void setMedical(String aMedical)
	{
		Medical = aMedical;
	}
	public String getSales()
	{
		return Sales;
	}
	public void setSales(String aSales)
	{
		Sales = aSales;
	}

	/**
	* 使用另外一个 AskPriceInfoSchema 对象给 Schema 赋值
	* @param: aAskPriceInfoSchema AskPriceInfoSchema
	**/
	public void setSchema(AskPriceInfoSchema aAskPriceInfoSchema)
	{
		this.AskPriceNo = aAskPriceInfoSchema.getAskPriceNo();
		this.AskNo = aAskPriceInfoSchema.getAskNo();
		this.BranchType = aAskPriceInfoSchema.getBranchType();
		this.BranchType2 = aAskPriceInfoSchema.getBranchType2();
		this.ManageCom = aAskPriceInfoSchema.getManageCom();
		this.AgentCom = aAskPriceInfoSchema.getAgentCom();
		this.AgentCode = aAskPriceInfoSchema.getAgentCode();
		this.UnitName = aAskPriceInfoSchema.getUnitName();
		this.RegistrationTypes = aAskPriceInfoSchema.getRegistrationTypes();
		this.Incumbency = aAskPriceInfoSchema.getIncumbency();
		this.IndustryType = aAskPriceInfoSchema.getIndustryType();
		this.InsuranceType = aAskPriceInfoSchema.getInsuranceType();
		this.Note = aAskPriceInfoSchema.getNote();
		this.AskPriceDate = fDate.getDate( aAskPriceInfoSchema.getAskPriceDate());
		this.StartDate = fDate.getDate( aAskPriceInfoSchema.getStartDate());
		this.StopDate = fDate.getDate( aAskPriceInfoSchema.getStopDate());
		this.Disease = aAskPriceInfoSchema.getDisease();
		this.Dangerous = aAskPriceInfoSchema.getDangerous();
		this.Changes = aAskPriceInfoSchema.getChanges();
		this.Health = aAskPriceInfoSchema.getHealth();
		this.Rescue = aAskPriceInfoSchema.getRescue();
		this.Others1 = aAskPriceInfoSchema.getOthers1();
		this.Others2 = aAskPriceInfoSchema.getOthers2();
		this.Remarks = aAskPriceInfoSchema.getRemarks();
		this.Attribute1 = aAskPriceInfoSchema.getAttribute1();
		this.Attribute2 = aAskPriceInfoSchema.getAttribute2();
		this.Attribute3 = aAskPriceInfoSchema.getAttribute3();
		this.Attribute4 = aAskPriceInfoSchema.getAttribute4();
		this.Attribute5 = aAskPriceInfoSchema.getAttribute5();
		this.Operator = aAskPriceInfoSchema.getOperator();
		this.MakeDate = fDate.getDate( aAskPriceInfoSchema.getMakeDate());
		this.MakeTime = aAskPriceInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aAskPriceInfoSchema.getModifyDate());
		this.ModifyTime = aAskPriceInfoSchema.getModifyTime();
		this.MainInsured = aAskPriceInfoSchema.getMainInsured();
		this.InsuredOther1 = aAskPriceInfoSchema.getInsuredOther1();
		this.InsuredOther2 = aAskPriceInfoSchema.getInsuredOther2();
		this.InsuredOther3 = aAskPriceInfoSchema.getInsuredOther3();
		this.PublicInsured = aAskPriceInfoSchema.getPublicInsured();
		this.Medical = aAskPriceInfoSchema.getMedical();
		this.Sales = aAskPriceInfoSchema.getSales();
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
			if( rs.getString("AskPriceNo") == null )
				this.AskPriceNo = null;
			else
				this.AskPriceNo = rs.getString("AskPriceNo").trim();

			if( rs.getString("AskNo") == null )
				this.AskNo = null;
			else
				this.AskNo = rs.getString("AskNo").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("BranchType2") == null )
				this.BranchType2 = null;
			else
				this.BranchType2 = rs.getString("BranchType2").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("UnitName") == null )
				this.UnitName = null;
			else
				this.UnitName = rs.getString("UnitName").trim();

			if( rs.getString("RegistrationTypes") == null )
				this.RegistrationTypes = null;
			else
				this.RegistrationTypes = rs.getString("RegistrationTypes").trim();

			this.Incumbency = rs.getInt("Incumbency");
			if( rs.getString("IndustryType") == null )
				this.IndustryType = null;
			else
				this.IndustryType = rs.getString("IndustryType").trim();

			if( rs.getString("InsuranceType") == null )
				this.InsuranceType = null;
			else
				this.InsuranceType = rs.getString("InsuranceType").trim();

			if( rs.getString("Note") == null )
				this.Note = null;
			else
				this.Note = rs.getString("Note").trim();

			this.AskPriceDate = rs.getDate("AskPriceDate");
			this.StartDate = rs.getDate("StartDate");
			this.StopDate = rs.getDate("StopDate");
			if( rs.getString("Disease") == null )
				this.Disease = null;
			else
				this.Disease = rs.getString("Disease").trim();

			if( rs.getString("Dangerous") == null )
				this.Dangerous = null;
			else
				this.Dangerous = rs.getString("Dangerous").trim();

			if( rs.getString("Changes") == null )
				this.Changes = null;
			else
				this.Changes = rs.getString("Changes").trim();

			if( rs.getString("Health") == null )
				this.Health = null;
			else
				this.Health = rs.getString("Health").trim();

			if( rs.getString("Rescue") == null )
				this.Rescue = null;
			else
				this.Rescue = rs.getString("Rescue").trim();

			if( rs.getString("Others1") == null )
				this.Others1 = null;
			else
				this.Others1 = rs.getString("Others1").trim();

			if( rs.getString("Others2") == null )
				this.Others2 = null;
			else
				this.Others2 = rs.getString("Others2").trim();

			if( rs.getString("Remarks") == null )
				this.Remarks = null;
			else
				this.Remarks = rs.getString("Remarks").trim();

			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("Attribute2") == null )
				this.Attribute2 = null;
			else
				this.Attribute2 = rs.getString("Attribute2").trim();

			if( rs.getString("Attribute3") == null )
				this.Attribute3 = null;
			else
				this.Attribute3 = rs.getString("Attribute3").trim();

			if( rs.getString("Attribute4") == null )
				this.Attribute4 = null;
			else
				this.Attribute4 = rs.getString("Attribute4").trim();

			if( rs.getString("Attribute5") == null )
				this.Attribute5 = null;
			else
				this.Attribute5 = rs.getString("Attribute5").trim();

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

			this.MainInsured = rs.getDouble("MainInsured");
			this.InsuredOther1 = rs.getDouble("InsuredOther1");
			this.InsuredOther2 = rs.getDouble("InsuredOther2");
			this.InsuredOther3 = rs.getDouble("InsuredOther3");
			if( rs.getString("PublicInsured") == null )
				this.PublicInsured = null;
			else
				this.PublicInsured = rs.getString("PublicInsured").trim();

			if( rs.getString("Medical") == null )
				this.Medical = null;
			else
				this.Medical = rs.getString("Medical").trim();

			if( rs.getString("Sales") == null )
				this.Sales = null;
			else
				this.Sales = rs.getString("Sales").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的AskPriceInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AskPriceInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public AskPriceInfoSchema getSchema()
	{
		AskPriceInfoSchema aAskPriceInfoSchema = new AskPriceInfoSchema();
		aAskPriceInfoSchema.setSchema(this);
		return aAskPriceInfoSchema;
	}

	public AskPriceInfoDB getDB()
	{
		AskPriceInfoDB aDBOper = new AskPriceInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAskPriceInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(AskPriceNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AskNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BranchType2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(UnitName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RegistrationTypes)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(Incumbency));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(IndustryType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(InsuranceType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Note)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( AskPriceDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Disease)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Dangerous)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Changes)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Health)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Rescue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Others1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Others2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remarks)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute5)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(MainInsured));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(InsuredOther1));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(InsuredOther2));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(InsuredOther3));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(PublicInsured)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Medical)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Sales));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAskPriceInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AskPriceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BranchType2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UnitName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RegistrationTypes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Incumbency= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			IndustryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InsuranceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Note = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AskPriceDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			StopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			Disease = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Dangerous = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Changes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Health = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Rescue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Others1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Others2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Remarks = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Attribute2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			Attribute3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			Attribute4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Attribute5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			MainInsured = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,35,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredOther1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,36,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredOther2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,37,SysConst.PACKAGESPILTER))).doubleValue();
			InsuredOther3 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,38,SysConst.PACKAGESPILTER))).doubleValue();
			PublicInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			Medical = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
			Sales = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 41, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AskPriceInfoSchema";
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
		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskPriceNo));
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskNo));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("BranchType2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType2));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("UnitName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitName));
		}
		if (FCode.equalsIgnoreCase("RegistrationTypes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegistrationTypes));
		}
		if (FCode.equalsIgnoreCase("Incumbency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Incumbency));
		}
		if (FCode.equalsIgnoreCase("IndustryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndustryType));
		}
		if (FCode.equalsIgnoreCase("InsuranceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuranceType));
		}
		if (FCode.equalsIgnoreCase("Note"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Note));
		}
		if (FCode.equalsIgnoreCase("AskPriceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAskPriceDate()));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
		}
		if (FCode.equalsIgnoreCase("Disease"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Disease));
		}
		if (FCode.equalsIgnoreCase("Dangerous"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Dangerous));
		}
		if (FCode.equalsIgnoreCase("Changes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Changes));
		}
		if (FCode.equalsIgnoreCase("Health"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Health));
		}
		if (FCode.equalsIgnoreCase("Rescue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rescue));
		}
		if (FCode.equalsIgnoreCase("Others1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Others1));
		}
		if (FCode.equalsIgnoreCase("Others2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Others2));
		}
		if (FCode.equalsIgnoreCase("Remarks"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remarks));
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute1));
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute2));
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute3));
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute4));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
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
		if (FCode.equalsIgnoreCase("MainInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainInsured));
		}
		if (FCode.equalsIgnoreCase("InsuredOther1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredOther1));
		}
		if (FCode.equalsIgnoreCase("InsuredOther2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredOther2));
		}
		if (FCode.equalsIgnoreCase("InsuredOther3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredOther3));
		}
		if (FCode.equalsIgnoreCase("PublicInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PublicInsured));
		}
		if (FCode.equalsIgnoreCase("Medical"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Medical));
		}
		if (FCode.equalsIgnoreCase("Sales"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sales));
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
				strFieldValue = StrTool.GBKToUnicode(AskPriceNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AskNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BranchType2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UnitName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RegistrationTypes);
				break;
			case 9:
				strFieldValue = String.valueOf(Incumbency);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IndustryType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InsuranceType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Note);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAskPriceDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Disease);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Dangerous);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Changes);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Health);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Rescue);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Others1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Others2);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Remarks);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Attribute2);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Attribute3);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(Attribute4);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Attribute5);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 34:
				strFieldValue = String.valueOf(MainInsured);
				break;
			case 35:
				strFieldValue = String.valueOf(InsuredOther1);
				break;
			case 36:
				strFieldValue = String.valueOf(InsuredOther2);
				break;
			case 37:
				strFieldValue = String.valueOf(InsuredOther3);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(PublicInsured);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(Medical);
				break;
			case 40:
				strFieldValue = StrTool.GBKToUnicode(Sales);
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

		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskPriceNo = FValue.trim();
			}
			else
				AskPriceNo = null;
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskNo = FValue.trim();
			}
			else
				AskNo = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("BranchType2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType2 = FValue.trim();
			}
			else
				BranchType2 = null;
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("UnitName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitName = FValue.trim();
			}
			else
				UnitName = null;
		}
		if (FCode.equalsIgnoreCase("RegistrationTypes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RegistrationTypes = FValue.trim();
			}
			else
				RegistrationTypes = null;
		}
		if (FCode.equalsIgnoreCase("Incumbency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Incumbency = i;
			}
		}
		if (FCode.equalsIgnoreCase("IndustryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndustryType = FValue.trim();
			}
			else
				IndustryType = null;
		}
		if (FCode.equalsIgnoreCase("InsuranceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuranceType = FValue.trim();
			}
			else
				InsuranceType = null;
		}
		if (FCode.equalsIgnoreCase("Note"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Note = FValue.trim();
			}
			else
				Note = null;
		}
		if (FCode.equalsIgnoreCase("AskPriceDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AskPriceDate = fDate.getDate( FValue );
			}
			else
				AskPriceDate = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StopDate = fDate.getDate( FValue );
			}
			else
				StopDate = null;
		}
		if (FCode.equalsIgnoreCase("Disease"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Disease = FValue.trim();
			}
			else
				Disease = null;
		}
		if (FCode.equalsIgnoreCase("Dangerous"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Dangerous = FValue.trim();
			}
			else
				Dangerous = null;
		}
		if (FCode.equalsIgnoreCase("Changes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Changes = FValue.trim();
			}
			else
				Changes = null;
		}
		if (FCode.equalsIgnoreCase("Health"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Health = FValue.trim();
			}
			else
				Health = null;
		}
		if (FCode.equalsIgnoreCase("Rescue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Rescue = FValue.trim();
			}
			else
				Rescue = null;
		}
		if (FCode.equalsIgnoreCase("Others1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Others1 = FValue.trim();
			}
			else
				Others1 = null;
		}
		if (FCode.equalsIgnoreCase("Others2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Others2 = FValue.trim();
			}
			else
				Others2 = null;
		}
		if (FCode.equalsIgnoreCase("Remarks"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remarks = FValue.trim();
			}
			else
				Remarks = null;
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute1 = FValue.trim();
			}
			else
				Attribute1 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute2 = FValue.trim();
			}
			else
				Attribute2 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute3 = FValue.trim();
			}
			else
				Attribute3 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute4 = FValue.trim();
			}
			else
				Attribute4 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute5 = FValue.trim();
			}
			else
				Attribute5 = null;
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
		if (FCode.equalsIgnoreCase("MainInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MainInsured = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredOther1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuredOther1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredOther2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuredOther2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("InsuredOther3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				InsuredOther3 = d;
			}
		}
		if (FCode.equalsIgnoreCase("PublicInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PublicInsured = FValue.trim();
			}
			else
				PublicInsured = null;
		}
		if (FCode.equalsIgnoreCase("Medical"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Medical = FValue.trim();
			}
			else
				Medical = null;
		}
		if (FCode.equalsIgnoreCase("Sales"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sales = FValue.trim();
			}
			else
				Sales = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AskPriceInfoSchema other = (AskPriceInfoSchema)otherObject;
		return
			AskPriceNo.equals(other.getAskPriceNo())
			&& AskNo.equals(other.getAskNo())
			&& BranchType.equals(other.getBranchType())
			&& BranchType2.equals(other.getBranchType2())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& UnitName.equals(other.getUnitName())
			&& RegistrationTypes.equals(other.getRegistrationTypes())
			&& Incumbency == other.getIncumbency()
			&& IndustryType.equals(other.getIndustryType())
			&& InsuranceType.equals(other.getInsuranceType())
			&& Note.equals(other.getNote())
			&& fDate.getString(AskPriceDate).equals(other.getAskPriceDate())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(StopDate).equals(other.getStopDate())
			&& Disease.equals(other.getDisease())
			&& Dangerous.equals(other.getDangerous())
			&& Changes.equals(other.getChanges())
			&& Health.equals(other.getHealth())
			&& Rescue.equals(other.getRescue())
			&& Others1.equals(other.getOthers1())
			&& Others2.equals(other.getOthers2())
			&& Remarks.equals(other.getRemarks())
			&& Attribute1.equals(other.getAttribute1())
			&& Attribute2.equals(other.getAttribute2())
			&& Attribute3.equals(other.getAttribute3())
			&& Attribute4.equals(other.getAttribute4())
			&& Attribute5.equals(other.getAttribute5())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& MainInsured == other.getMainInsured()
			&& InsuredOther1 == other.getInsuredOther1()
			&& InsuredOther2 == other.getInsuredOther2()
			&& InsuredOther3 == other.getInsuredOther3()
			&& PublicInsured.equals(other.getPublicInsured())
			&& Medical.equals(other.getMedical())
			&& Sales.equals(other.getSales());
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
		if( strFieldName.equals("AskPriceNo") ) {
			return 0;
		}
		if( strFieldName.equals("AskNo") ) {
			return 1;
		}
		if( strFieldName.equals("BranchType") ) {
			return 2;
		}
		if( strFieldName.equals("BranchType2") ) {
			return 3;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 4;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 5;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("UnitName") ) {
			return 7;
		}
		if( strFieldName.equals("RegistrationTypes") ) {
			return 8;
		}
		if( strFieldName.equals("Incumbency") ) {
			return 9;
		}
		if( strFieldName.equals("IndustryType") ) {
			return 10;
		}
		if( strFieldName.equals("InsuranceType") ) {
			return 11;
		}
		if( strFieldName.equals("Note") ) {
			return 12;
		}
		if( strFieldName.equals("AskPriceDate") ) {
			return 13;
		}
		if( strFieldName.equals("StartDate") ) {
			return 14;
		}
		if( strFieldName.equals("StopDate") ) {
			return 15;
		}
		if( strFieldName.equals("Disease") ) {
			return 16;
		}
		if( strFieldName.equals("Dangerous") ) {
			return 17;
		}
		if( strFieldName.equals("Changes") ) {
			return 18;
		}
		if( strFieldName.equals("Health") ) {
			return 19;
		}
		if( strFieldName.equals("Rescue") ) {
			return 20;
		}
		if( strFieldName.equals("Others1") ) {
			return 21;
		}
		if( strFieldName.equals("Others2") ) {
			return 22;
		}
		if( strFieldName.equals("Remarks") ) {
			return 23;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 24;
		}
		if( strFieldName.equals("Attribute2") ) {
			return 25;
		}
		if( strFieldName.equals("Attribute3") ) {
			return 26;
		}
		if( strFieldName.equals("Attribute4") ) {
			return 27;
		}
		if( strFieldName.equals("Attribute5") ) {
			return 28;
		}
		if( strFieldName.equals("Operator") ) {
			return 29;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 30;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 32;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 33;
		}
		if( strFieldName.equals("MainInsured") ) {
			return 34;
		}
		if( strFieldName.equals("InsuredOther1") ) {
			return 35;
		}
		if( strFieldName.equals("InsuredOther2") ) {
			return 36;
		}
		if( strFieldName.equals("InsuredOther3") ) {
			return 37;
		}
		if( strFieldName.equals("PublicInsured") ) {
			return 38;
		}
		if( strFieldName.equals("Medical") ) {
			return 39;
		}
		if( strFieldName.equals("Sales") ) {
			return 40;
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
				strFieldName = "AskPriceNo";
				break;
			case 1:
				strFieldName = "AskNo";
				break;
			case 2:
				strFieldName = "BranchType";
				break;
			case 3:
				strFieldName = "BranchType2";
				break;
			case 4:
				strFieldName = "ManageCom";
				break;
			case 5:
				strFieldName = "AgentCom";
				break;
			case 6:
				strFieldName = "AgentCode";
				break;
			case 7:
				strFieldName = "UnitName";
				break;
			case 8:
				strFieldName = "RegistrationTypes";
				break;
			case 9:
				strFieldName = "Incumbency";
				break;
			case 10:
				strFieldName = "IndustryType";
				break;
			case 11:
				strFieldName = "InsuranceType";
				break;
			case 12:
				strFieldName = "Note";
				break;
			case 13:
				strFieldName = "AskPriceDate";
				break;
			case 14:
				strFieldName = "StartDate";
				break;
			case 15:
				strFieldName = "StopDate";
				break;
			case 16:
				strFieldName = "Disease";
				break;
			case 17:
				strFieldName = "Dangerous";
				break;
			case 18:
				strFieldName = "Changes";
				break;
			case 19:
				strFieldName = "Health";
				break;
			case 20:
				strFieldName = "Rescue";
				break;
			case 21:
				strFieldName = "Others1";
				break;
			case 22:
				strFieldName = "Others2";
				break;
			case 23:
				strFieldName = "Remarks";
				break;
			case 24:
				strFieldName = "Attribute1";
				break;
			case 25:
				strFieldName = "Attribute2";
				break;
			case 26:
				strFieldName = "Attribute3";
				break;
			case 27:
				strFieldName = "Attribute4";
				break;
			case 28:
				strFieldName = "Attribute5";
				break;
			case 29:
				strFieldName = "Operator";
				break;
			case 30:
				strFieldName = "MakeDate";
				break;
			case 31:
				strFieldName = "MakeTime";
				break;
			case 32:
				strFieldName = "ModifyDate";
				break;
			case 33:
				strFieldName = "ModifyTime";
				break;
			case 34:
				strFieldName = "MainInsured";
				break;
			case 35:
				strFieldName = "InsuredOther1";
				break;
			case 36:
				strFieldName = "InsuredOther2";
				break;
			case 37:
				strFieldName = "InsuredOther3";
				break;
			case 38:
				strFieldName = "PublicInsured";
				break;
			case 39:
				strFieldName = "Medical";
				break;
			case 40:
				strFieldName = "Sales";
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
		if( strFieldName.equals("AskPriceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RegistrationTypes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Incumbency") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IndustryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuranceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Note") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskPriceDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Disease") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Dangerous") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Changes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Health") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Rescue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Others1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Others2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remarks") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute5") ) {
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
		if( strFieldName.equals("MainInsured") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredOther1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredOther2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("InsuredOther3") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PublicInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Medical") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sales") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 35:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 36:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 37:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
