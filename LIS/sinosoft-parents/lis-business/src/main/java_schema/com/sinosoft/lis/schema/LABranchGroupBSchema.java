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
import com.sinosoft.lis.db.LABranchGroupBDB;

/*
 * <p>ClassName: LABranchGroupBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LABranchGroupBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LABranchGroupBSchema.class);

	// @Field
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 展业机构名称 */
	private String Name;
	/** 管理机构 */
	private String ManageCom;
	/** 上级展业机构代码 */
	private String UpBranch;
	/** 展业机构外部编码 */
	private String BranchAttr;
	/** 展业类型 */
	private String BranchType;
	/** 展业机构级别 */
	private String BranchLevel;
	/** 展业机构管理人员 */
	private String BranchManager;
	/** 展业机构地址编码 */
	private String BranchAddressCode;
	/** 展业机构地址 */
	private String BranchAddress;
	/** 展业机构电话 */
	private String BranchPhone;
	/** 展业机构传真 */
	private String BranchFax;
	/** 展业机构邮编 */
	private String BranchZipcode;
	/** 成立标志日期 */
	private Date FoundDate;
	/** 停业日期 */
	private Date EndDate;
	/** 停业 */
	private String EndFlag;
	/** 是否单证交接 */
	private String CertifyFlag;
	/** 是否有独立的营销职场 */
	private String FieldFlag;
	/** 标志 */
	private String State;
	/** 原操作员 */
	private String Operator2;
	/** 原入机日期 */
	private Date MakeDate2;
	/** 原入机时间 */
	private String MakeTime2;
	/** 原最后一次修改日期 */
	private Date ModifyDate2;
	/** 原最后一次修改时间 */
	private String ModifyTime2;
	/** 批改号 */
	private String EdorNo;
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
	/** 展业机构管理人员姓名 */
	private String BranchManagerName;
	/** 展业机构的上下级属性 */
	private String UpBranchAttr;
	/** 展业机构工作类型 */
	private String BranchJobType;
	/** 转储类型 */
	private String EdorType;
	/** 展业机构类别 */
	private String BranchKind;
	/** 指标计算编码 */
	private String IndexCalNo;

	public static final int FIELDNUM = 36;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LABranchGroupBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AgentGroup";
		pk[1] = "EdorNo";

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
		LABranchGroupBSchema cloned = (LABranchGroupBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getUpBranch()
	{
		return UpBranch;
	}
	public void setUpBranch(String aUpBranch)
	{
		UpBranch = aUpBranch;
	}
	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	/**
	* 01	营业组<p>
	* <p>
	* 02	营业部<p>
	* <p>
	* 03	督导部<p>
	* <p>
	* 04	区域督导部<p>
	* <p>
	* 05	支公司
	*/
	public String getBranchLevel()
	{
		return BranchLevel;
	}
	public void setBranchLevel(String aBranchLevel)
	{
		BranchLevel = aBranchLevel;
	}
	public String getBranchManager()
	{
		return BranchManager;
	}
	public void setBranchManager(String aBranchManager)
	{
		BranchManager = aBranchManager;
	}
	public String getBranchAddressCode()
	{
		return BranchAddressCode;
	}
	public void setBranchAddressCode(String aBranchAddressCode)
	{
		BranchAddressCode = aBranchAddressCode;
	}
	public String getBranchAddress()
	{
		return BranchAddress;
	}
	public void setBranchAddress(String aBranchAddress)
	{
		BranchAddress = aBranchAddress;
	}
	public String getBranchPhone()
	{
		return BranchPhone;
	}
	public void setBranchPhone(String aBranchPhone)
	{
		BranchPhone = aBranchPhone;
	}
	public String getBranchFax()
	{
		return BranchFax;
	}
	public void setBranchFax(String aBranchFax)
	{
		BranchFax = aBranchFax;
	}
	public String getBranchZipcode()
	{
		return BranchZipcode;
	}
	public void setBranchZipcode(String aBranchZipcode)
	{
		BranchZipcode = aBranchZipcode;
	}
	public String getFoundDate()
	{
		if( FoundDate != null )
			return fDate.getString(FoundDate);
		else
			return null;
	}
	public void setFoundDate(Date aFoundDate)
	{
		FoundDate = aFoundDate;
	}
	public void setFoundDate(String aFoundDate)
	{
		if (aFoundDate != null && !aFoundDate.equals("") )
		{
			FoundDate = fDate.getDate( aFoundDate );
		}
		else
			FoundDate = null;
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

	public String getEndFlag()
	{
		return EndFlag;
	}
	public void setEndFlag(String aEndFlag)
	{
		EndFlag = aEndFlag;
	}
	public String getCertifyFlag()
	{
		return CertifyFlag;
	}
	public void setCertifyFlag(String aCertifyFlag)
	{
		CertifyFlag = aCertifyFlag;
	}
	public String getFieldFlag()
	{
		return FieldFlag;
	}
	public void setFieldFlag(String aFieldFlag)
	{
		FieldFlag = aFieldFlag;
	}
	/**
	* [1]等于1不计入正常组进行统计.
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getOperator2()
	{
		return Operator2;
	}
	public void setOperator2(String aOperator2)
	{
		Operator2 = aOperator2;
	}
	public String getMakeDate2()
	{
		if( MakeDate2 != null )
			return fDate.getString(MakeDate2);
		else
			return null;
	}
	public void setMakeDate2(Date aMakeDate2)
	{
		MakeDate2 = aMakeDate2;
	}
	public void setMakeDate2(String aMakeDate2)
	{
		if (aMakeDate2 != null && !aMakeDate2.equals("") )
		{
			MakeDate2 = fDate.getDate( aMakeDate2 );
		}
		else
			MakeDate2 = null;
	}

	public String getMakeTime2()
	{
		return MakeTime2;
	}
	public void setMakeTime2(String aMakeTime2)
	{
		MakeTime2 = aMakeTime2;
	}
	public String getModifyDate2()
	{
		if( ModifyDate2 != null )
			return fDate.getString(ModifyDate2);
		else
			return null;
	}
	public void setModifyDate2(Date aModifyDate2)
	{
		ModifyDate2 = aModifyDate2;
	}
	public void setModifyDate2(String aModifyDate2)
	{
		if (aModifyDate2 != null && !aModifyDate2.equals("") )
		{
			ModifyDate2 = fDate.getDate( aModifyDate2 );
		}
		else
			ModifyDate2 = null;
	}

	public String getModifyTime2()
	{
		return ModifyTime2;
	}
	public void setModifyTime2(String aModifyTime2)
	{
		ModifyTime2 = aModifyTime2;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
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
	public String getBranchManagerName()
	{
		return BranchManagerName;
	}
	public void setBranchManagerName(String aBranchManagerName)
	{
		BranchManagerName = aBranchManagerName;
	}
	/**
	* 0-非直辖<p>
	* <p>
	* 1-直辖
	*/
	public String getUpBranchAttr()
	{
		return UpBranchAttr;
	}
	public void setUpBranchAttr(String aUpBranchAttr)
	{
		UpBranchAttr = aUpBranchAttr;
	}
	/**
	* 0-内勤<p>
	* 1-外勤
	*/
	public String getBranchJobType()
	{
		return BranchJobType;
	}
	public void setBranchJobType(String aBranchJobType)
	{
		BranchJobType = aBranchJobType;
	}
	/**
	* 01-考核结果<p>
	* 02-机构调整<p>
	* 03-人员调动<p>
	* 04-二次增员<p>
	* 05-原始修改备份<p>
	* 06-轨迹修改备份（育成关系，所属关系）<p>
	* 07-主管任命修改
	*/
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	/**
	* 01 --客户经理<p>
	* 02 --培训岗<p>
	* <p>
	* 03--企划策划岗<p>
	* <p>
	* 04--销售支援岗<p>
	* 05 --综合岗<p>
	* <p>
	* 06 --其他
	*/
	public String getBranchKind()
	{
		return BranchKind;
	}
	public void setBranchKind(String aBranchKind)
	{
		BranchKind = aBranchKind;
	}
	public String getIndexCalNo()
	{
		return IndexCalNo;
	}
	public void setIndexCalNo(String aIndexCalNo)
	{
		IndexCalNo = aIndexCalNo;
	}

	/**
	* 使用另外一个 LABranchGroupBSchema 对象给 Schema 赋值
	* @param: aLABranchGroupBSchema LABranchGroupBSchema
	**/
	public void setSchema(LABranchGroupBSchema aLABranchGroupBSchema)
	{
		this.AgentGroup = aLABranchGroupBSchema.getAgentGroup();
		this.Name = aLABranchGroupBSchema.getName();
		this.ManageCom = aLABranchGroupBSchema.getManageCom();
		this.UpBranch = aLABranchGroupBSchema.getUpBranch();
		this.BranchAttr = aLABranchGroupBSchema.getBranchAttr();
		this.BranchType = aLABranchGroupBSchema.getBranchType();
		this.BranchLevel = aLABranchGroupBSchema.getBranchLevel();
		this.BranchManager = aLABranchGroupBSchema.getBranchManager();
		this.BranchAddressCode = aLABranchGroupBSchema.getBranchAddressCode();
		this.BranchAddress = aLABranchGroupBSchema.getBranchAddress();
		this.BranchPhone = aLABranchGroupBSchema.getBranchPhone();
		this.BranchFax = aLABranchGroupBSchema.getBranchFax();
		this.BranchZipcode = aLABranchGroupBSchema.getBranchZipcode();
		this.FoundDate = fDate.getDate( aLABranchGroupBSchema.getFoundDate());
		this.EndDate = fDate.getDate( aLABranchGroupBSchema.getEndDate());
		this.EndFlag = aLABranchGroupBSchema.getEndFlag();
		this.CertifyFlag = aLABranchGroupBSchema.getCertifyFlag();
		this.FieldFlag = aLABranchGroupBSchema.getFieldFlag();
		this.State = aLABranchGroupBSchema.getState();
		this.Operator2 = aLABranchGroupBSchema.getOperator2();
		this.MakeDate2 = fDate.getDate( aLABranchGroupBSchema.getMakeDate2());
		this.MakeTime2 = aLABranchGroupBSchema.getMakeTime2();
		this.ModifyDate2 = fDate.getDate( aLABranchGroupBSchema.getModifyDate2());
		this.ModifyTime2 = aLABranchGroupBSchema.getModifyTime2();
		this.EdorNo = aLABranchGroupBSchema.getEdorNo();
		this.Operator = aLABranchGroupBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLABranchGroupBSchema.getMakeDate());
		this.MakeTime = aLABranchGroupBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLABranchGroupBSchema.getModifyDate());
		this.ModifyTime = aLABranchGroupBSchema.getModifyTime();
		this.BranchManagerName = aLABranchGroupBSchema.getBranchManagerName();
		this.UpBranchAttr = aLABranchGroupBSchema.getUpBranchAttr();
		this.BranchJobType = aLABranchGroupBSchema.getBranchJobType();
		this.EdorType = aLABranchGroupBSchema.getEdorType();
		this.BranchKind = aLABranchGroupBSchema.getBranchKind();
		this.IndexCalNo = aLABranchGroupBSchema.getIndexCalNo();
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
			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("UpBranch") == null )
				this.UpBranch = null;
			else
				this.UpBranch = rs.getString("UpBranch").trim();

			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("BranchLevel") == null )
				this.BranchLevel = null;
			else
				this.BranchLevel = rs.getString("BranchLevel").trim();

			if( rs.getString("BranchManager") == null )
				this.BranchManager = null;
			else
				this.BranchManager = rs.getString("BranchManager").trim();

			if( rs.getString("BranchAddressCode") == null )
				this.BranchAddressCode = null;
			else
				this.BranchAddressCode = rs.getString("BranchAddressCode").trim();

			if( rs.getString("BranchAddress") == null )
				this.BranchAddress = null;
			else
				this.BranchAddress = rs.getString("BranchAddress").trim();

			if( rs.getString("BranchPhone") == null )
				this.BranchPhone = null;
			else
				this.BranchPhone = rs.getString("BranchPhone").trim();

			if( rs.getString("BranchFax") == null )
				this.BranchFax = null;
			else
				this.BranchFax = rs.getString("BranchFax").trim();

			if( rs.getString("BranchZipcode") == null )
				this.BranchZipcode = null;
			else
				this.BranchZipcode = rs.getString("BranchZipcode").trim();

			this.FoundDate = rs.getDate("FoundDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("EndFlag") == null )
				this.EndFlag = null;
			else
				this.EndFlag = rs.getString("EndFlag").trim();

			if( rs.getString("CertifyFlag") == null )
				this.CertifyFlag = null;
			else
				this.CertifyFlag = rs.getString("CertifyFlag").trim();

			if( rs.getString("FieldFlag") == null )
				this.FieldFlag = null;
			else
				this.FieldFlag = rs.getString("FieldFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Operator2") == null )
				this.Operator2 = null;
			else
				this.Operator2 = rs.getString("Operator2").trim();

			this.MakeDate2 = rs.getDate("MakeDate2");
			if( rs.getString("MakeTime2") == null )
				this.MakeTime2 = null;
			else
				this.MakeTime2 = rs.getString("MakeTime2").trim();

			this.ModifyDate2 = rs.getDate("ModifyDate2");
			if( rs.getString("ModifyTime2") == null )
				this.ModifyTime2 = null;
			else
				this.ModifyTime2 = rs.getString("ModifyTime2").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

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

			if( rs.getString("BranchManagerName") == null )
				this.BranchManagerName = null;
			else
				this.BranchManagerName = rs.getString("BranchManagerName").trim();

			if( rs.getString("UpBranchAttr") == null )
				this.UpBranchAttr = null;
			else
				this.UpBranchAttr = rs.getString("UpBranchAttr").trim();

			if( rs.getString("BranchJobType") == null )
				this.BranchJobType = null;
			else
				this.BranchJobType = rs.getString("BranchJobType").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("BranchKind") == null )
				this.BranchKind = null;
			else
				this.BranchKind = rs.getString("BranchKind").trim();

			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LABranchGroupB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABranchGroupBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LABranchGroupBSchema getSchema()
	{
		LABranchGroupBSchema aLABranchGroupBSchema = new LABranchGroupBSchema();
		aLABranchGroupBSchema.setSchema(this);
		return aLABranchGroupBSchema;
	}

	public LABranchGroupBDB getDB()
	{
		LABranchGroupBDB aDBOper = new LABranchGroupBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLABranchGroupB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpBranch)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchManager)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchFax)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchZipcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FoundDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FieldFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchManagerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpBranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchJobType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexCalNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLABranchGroupB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UpBranch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BranchLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BranchManager = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BranchAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BranchAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BranchPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BranchFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BranchZipcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			FoundDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			EndFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CertifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FieldFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BranchManagerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			UpBranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BranchJobType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BranchKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABranchGroupBSchema";
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("UpBranch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpBranch));
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("BranchLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevel));
		}
		if (FCode.equalsIgnoreCase("BranchManager"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchManager));
		}
		if (FCode.equalsIgnoreCase("BranchAddressCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAddressCode));
		}
		if (FCode.equalsIgnoreCase("BranchAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAddress));
		}
		if (FCode.equalsIgnoreCase("BranchPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchPhone));
		}
		if (FCode.equalsIgnoreCase("BranchFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchFax));
		}
		if (FCode.equalsIgnoreCase("BranchZipcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchZipcode));
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("EndFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndFlag));
		}
		if (FCode.equalsIgnoreCase("CertifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyFlag));
		}
		if (FCode.equalsIgnoreCase("FieldFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator2));
		}
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime2));
		}
		if (FCode.equalsIgnoreCase("ModifyDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate2()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime2));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
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
		if (FCode.equalsIgnoreCase("BranchManagerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchManagerName));
		}
		if (FCode.equalsIgnoreCase("UpBranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpBranchAttr));
		}
		if (FCode.equalsIgnoreCase("BranchJobType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchJobType));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("BranchKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchKind));
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexCalNo));
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
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UpBranch);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BranchLevel);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BranchManager);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BranchAddressCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BranchAddress);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BranchPhone);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BranchFax);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BranchZipcode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFoundDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EndFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CertifyFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(FieldFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate2()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime2);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
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
				strFieldValue = StrTool.GBKToUnicode(BranchManagerName);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(UpBranchAttr);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BranchJobType);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(BranchKind);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(IndexCalNo);
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

		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("UpBranch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpBranch = FValue.trim();
			}
			else
				UpBranch = null;
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAttr = FValue.trim();
			}
			else
				BranchAttr = null;
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
		if (FCode.equalsIgnoreCase("BranchLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevel = FValue.trim();
			}
			else
				BranchLevel = null;
		}
		if (FCode.equalsIgnoreCase("BranchManager"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchManager = FValue.trim();
			}
			else
				BranchManager = null;
		}
		if (FCode.equalsIgnoreCase("BranchAddressCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAddressCode = FValue.trim();
			}
			else
				BranchAddressCode = null;
		}
		if (FCode.equalsIgnoreCase("BranchAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAddress = FValue.trim();
			}
			else
				BranchAddress = null;
		}
		if (FCode.equalsIgnoreCase("BranchPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchPhone = FValue.trim();
			}
			else
				BranchPhone = null;
		}
		if (FCode.equalsIgnoreCase("BranchFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchFax = FValue.trim();
			}
			else
				BranchFax = null;
		}
		if (FCode.equalsIgnoreCase("BranchZipcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchZipcode = FValue.trim();
			}
			else
				BranchZipcode = null;
		}
		if (FCode.equalsIgnoreCase("FoundDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FoundDate = fDate.getDate( FValue );
			}
			else
				FoundDate = null;
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
		if (FCode.equalsIgnoreCase("EndFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndFlag = FValue.trim();
			}
			else
				EndFlag = null;
		}
		if (FCode.equalsIgnoreCase("CertifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyFlag = FValue.trim();
			}
			else
				CertifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("FieldFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FieldFlag = FValue.trim();
			}
			else
				FieldFlag = null;
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
		if (FCode.equalsIgnoreCase("Operator2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator2 = FValue.trim();
			}
			else
				Operator2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate2 = fDate.getDate( FValue );
			}
			else
				MakeDate2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime2 = FValue.trim();
			}
			else
				MakeTime2 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate2 = fDate.getDate( FValue );
			}
			else
				ModifyDate2 = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime2 = FValue.trim();
			}
			else
				ModifyTime2 = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("BranchManagerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchManagerName = FValue.trim();
			}
			else
				BranchManagerName = null;
		}
		if (FCode.equalsIgnoreCase("UpBranchAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpBranchAttr = FValue.trim();
			}
			else
				UpBranchAttr = null;
		}
		if (FCode.equalsIgnoreCase("BranchJobType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchJobType = FValue.trim();
			}
			else
				BranchJobType = null;
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
		if (FCode.equalsIgnoreCase("BranchKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchKind = FValue.trim();
			}
			else
				BranchKind = null;
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexCalNo = FValue.trim();
			}
			else
				IndexCalNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LABranchGroupBSchema other = (LABranchGroupBSchema)otherObject;
		return
			AgentGroup.equals(other.getAgentGroup())
			&& Name.equals(other.getName())
			&& ManageCom.equals(other.getManageCom())
			&& UpBranch.equals(other.getUpBranch())
			&& BranchAttr.equals(other.getBranchAttr())
			&& BranchType.equals(other.getBranchType())
			&& BranchLevel.equals(other.getBranchLevel())
			&& BranchManager.equals(other.getBranchManager())
			&& BranchAddressCode.equals(other.getBranchAddressCode())
			&& BranchAddress.equals(other.getBranchAddress())
			&& BranchPhone.equals(other.getBranchPhone())
			&& BranchFax.equals(other.getBranchFax())
			&& BranchZipcode.equals(other.getBranchZipcode())
			&& fDate.getString(FoundDate).equals(other.getFoundDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& EndFlag.equals(other.getEndFlag())
			&& CertifyFlag.equals(other.getCertifyFlag())
			&& FieldFlag.equals(other.getFieldFlag())
			&& State.equals(other.getState())
			&& Operator2.equals(other.getOperator2())
			&& fDate.getString(MakeDate2).equals(other.getMakeDate2())
			&& MakeTime2.equals(other.getMakeTime2())
			&& fDate.getString(ModifyDate2).equals(other.getModifyDate2())
			&& ModifyTime2.equals(other.getModifyTime2())
			&& EdorNo.equals(other.getEdorNo())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchManagerName.equals(other.getBranchManagerName())
			&& UpBranchAttr.equals(other.getUpBranchAttr())
			&& BranchJobType.equals(other.getBranchJobType())
			&& EdorType.equals(other.getEdorType())
			&& BranchKind.equals(other.getBranchKind())
			&& IndexCalNo.equals(other.getIndexCalNo());
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
		if( strFieldName.equals("AgentGroup") ) {
			return 0;
		}
		if( strFieldName.equals("Name") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("UpBranch") ) {
			return 3;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 4;
		}
		if( strFieldName.equals("BranchType") ) {
			return 5;
		}
		if( strFieldName.equals("BranchLevel") ) {
			return 6;
		}
		if( strFieldName.equals("BranchManager") ) {
			return 7;
		}
		if( strFieldName.equals("BranchAddressCode") ) {
			return 8;
		}
		if( strFieldName.equals("BranchAddress") ) {
			return 9;
		}
		if( strFieldName.equals("BranchPhone") ) {
			return 10;
		}
		if( strFieldName.equals("BranchFax") ) {
			return 11;
		}
		if( strFieldName.equals("BranchZipcode") ) {
			return 12;
		}
		if( strFieldName.equals("FoundDate") ) {
			return 13;
		}
		if( strFieldName.equals("EndDate") ) {
			return 14;
		}
		if( strFieldName.equals("EndFlag") ) {
			return 15;
		}
		if( strFieldName.equals("CertifyFlag") ) {
			return 16;
		}
		if( strFieldName.equals("FieldFlag") ) {
			return 17;
		}
		if( strFieldName.equals("State") ) {
			return 18;
		}
		if( strFieldName.equals("Operator2") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate2") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate2") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime2") ) {
			return 23;
		}
		if( strFieldName.equals("EdorNo") ) {
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
		if( strFieldName.equals("BranchManagerName") ) {
			return 30;
		}
		if( strFieldName.equals("UpBranchAttr") ) {
			return 31;
		}
		if( strFieldName.equals("BranchJobType") ) {
			return 32;
		}
		if( strFieldName.equals("EdorType") ) {
			return 33;
		}
		if( strFieldName.equals("BranchKind") ) {
			return 34;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return 35;
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
				strFieldName = "AgentGroup";
				break;
			case 1:
				strFieldName = "Name";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "UpBranch";
				break;
			case 4:
				strFieldName = "BranchAttr";
				break;
			case 5:
				strFieldName = "BranchType";
				break;
			case 6:
				strFieldName = "BranchLevel";
				break;
			case 7:
				strFieldName = "BranchManager";
				break;
			case 8:
				strFieldName = "BranchAddressCode";
				break;
			case 9:
				strFieldName = "BranchAddress";
				break;
			case 10:
				strFieldName = "BranchPhone";
				break;
			case 11:
				strFieldName = "BranchFax";
				break;
			case 12:
				strFieldName = "BranchZipcode";
				break;
			case 13:
				strFieldName = "FoundDate";
				break;
			case 14:
				strFieldName = "EndDate";
				break;
			case 15:
				strFieldName = "EndFlag";
				break;
			case 16:
				strFieldName = "CertifyFlag";
				break;
			case 17:
				strFieldName = "FieldFlag";
				break;
			case 18:
				strFieldName = "State";
				break;
			case 19:
				strFieldName = "Operator2";
				break;
			case 20:
				strFieldName = "MakeDate2";
				break;
			case 21:
				strFieldName = "MakeTime2";
				break;
			case 22:
				strFieldName = "ModifyDate2";
				break;
			case 23:
				strFieldName = "ModifyTime2";
				break;
			case 24:
				strFieldName = "EdorNo";
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
				strFieldName = "BranchManagerName";
				break;
			case 31:
				strFieldName = "UpBranchAttr";
				break;
			case 32:
				strFieldName = "BranchJobType";
				break;
			case 33:
				strFieldName = "EdorType";
				break;
			case 34:
				strFieldName = "BranchKind";
				break;
			case 35:
				strFieldName = "IndexCalNo";
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
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpBranch") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchManager") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchAddressCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchZipcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FoundDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
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
		if( strFieldName.equals("BranchManagerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpBranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchJobType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndexCalNo") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
