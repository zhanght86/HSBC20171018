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
import com.sinosoft.lis.db.LAAssessDB;

/*
 * <p>ClassName: LAAssessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAAssessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAAssessSchema.class);

	// @Field
	/** 指标计算编码 */
	private String IndexCalNo;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 管理机构 */
	private String ManageCom;
	/** 建议人事变动标志 */
	private String ModifyFlag;
	/** 代理人系列 */
	private String AgentSeries;
	/** 代理人级别 */
	private String AgentGrade;
	/** 新代理人系列 */
	private String AgentSeries1;
	/** 新代理人级别 */
	private String AgentGrade1;
	/** 新代理人组别 */
	private String AgentGroupNew;
	/** 确认人代码 */
	private String Confirmer;
	/** 确认日期 */
	private Date ConfirmDate;
	/** 状态 */
	private String State;
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
	/** 展业类型 */
	private String BranchType;
	/** 是否是基本法考核 */
	private String StandAssessFlag;
	/** 是否是第一次考核 */
	private String FirstAssessFlag;
	/** 展业机构外部编码 */
	private String BranchAttr;
	/** 建议代理人系列 */
	private String CalAgentSeries;
	/** 建议代理人级别 */
	private String CalAgentGrade;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAAssessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "IndexCalNo";
		pk[1] = "AgentCode";

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
		LAAssessSchema cloned = (LAAssessSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getIndexCalNo()
	{
		return IndexCalNo;
	}
	public void setIndexCalNo(String aIndexCalNo)
	{
		IndexCalNo = aIndexCalNo;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 01-降级<p>
	* 02-维持<p>
	* 03-晋升
	*/
	public String getModifyFlag()
	{
		return ModifyFlag;
	}
	public void setModifyFlag(String aModifyFlag)
	{
		ModifyFlag = aModifyFlag;
	}
	public String getAgentSeries()
	{
		return AgentSeries;
	}
	public void setAgentSeries(String aAgentSeries)
	{
		AgentSeries = aAgentSeries;
	}
	/**
	* A01:理财专员<p>
	* A02:理财主任<p>
	* A03：见习业务经理<p>
	* <p>
	* A04：业务经理一级<p>
	* <p>
	* A05:业务经理二级<p>
	* A06:高级业务经理一级<p>
	* <p>
	* A07:高级业务经理二级<p>
	* A08:督导长<p>
	* <p>
	* A09:区域督导长<p>
	* <p>
	* "
	*/
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	public String getAgentSeries1()
	{
		return AgentSeries1;
	}
	public void setAgentSeries1(String aAgentSeries1)
	{
		AgentSeries1 = aAgentSeries1;
	}
	public String getAgentGrade1()
	{
		return AgentGrade1;
	}
	public void setAgentGrade1(String aAgentGrade1)
	{
		AgentGrade1 = aAgentGrade1;
	}
	public String getAgentGroupNew()
	{
		return AgentGroupNew;
	}
	public void setAgentGroupNew(String aAgentGroupNew)
	{
		AgentGroupNew = aAgentGroupNew;
	}
	public String getConfirmer()
	{
		return Confirmer;
	}
	public void setConfirmer(String aConfirmer)
	{
		Confirmer = aConfirmer;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	/**
	* 0-考核未确认<p>
	* <p>
	* 1-考核确认未归属<p>
	* <p>
	* 2-组织归属完毕
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
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
	* 0-否<p>
	* <p>
	* 1-是
	*/
	public String getStandAssessFlag()
	{
		return StandAssessFlag;
	}
	public void setStandAssessFlag(String aStandAssessFlag)
	{
		StandAssessFlag = aStandAssessFlag;
	}
	/**
	* 0-否<p>
	* <p>
	* 1-是
	*/
	public String getFirstAssessFlag()
	{
		return FirstAssessFlag;
	}
	public void setFirstAssessFlag(String aFirstAssessFlag)
	{
		FirstAssessFlag = aFirstAssessFlag;
	}
	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}
	public String getCalAgentSeries()
	{
		return CalAgentSeries;
	}
	public void setCalAgentSeries(String aCalAgentSeries)
	{
		CalAgentSeries = aCalAgentSeries;
	}
	public String getCalAgentGrade()
	{
		return CalAgentGrade;
	}
	public void setCalAgentGrade(String aCalAgentGrade)
	{
		CalAgentGrade = aCalAgentGrade;
	}

	/**
	* 使用另外一个 LAAssessSchema 对象给 Schema 赋值
	* @param: aLAAssessSchema LAAssessSchema
	**/
	public void setSchema(LAAssessSchema aLAAssessSchema)
	{
		this.IndexCalNo = aLAAssessSchema.getIndexCalNo();
		this.AgentCode = aLAAssessSchema.getAgentCode();
		this.AgentGroup = aLAAssessSchema.getAgentGroup();
		this.ManageCom = aLAAssessSchema.getManageCom();
		this.ModifyFlag = aLAAssessSchema.getModifyFlag();
		this.AgentSeries = aLAAssessSchema.getAgentSeries();
		this.AgentGrade = aLAAssessSchema.getAgentGrade();
		this.AgentSeries1 = aLAAssessSchema.getAgentSeries1();
		this.AgentGrade1 = aLAAssessSchema.getAgentGrade1();
		this.AgentGroupNew = aLAAssessSchema.getAgentGroupNew();
		this.Confirmer = aLAAssessSchema.getConfirmer();
		this.ConfirmDate = fDate.getDate( aLAAssessSchema.getConfirmDate());
		this.State = aLAAssessSchema.getState();
		this.Operator = aLAAssessSchema.getOperator();
		this.MakeDate = fDate.getDate( aLAAssessSchema.getMakeDate());
		this.MakeTime = aLAAssessSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAAssessSchema.getModifyDate());
		this.ModifyTime = aLAAssessSchema.getModifyTime();
		this.BranchType = aLAAssessSchema.getBranchType();
		this.StandAssessFlag = aLAAssessSchema.getStandAssessFlag();
		this.FirstAssessFlag = aLAAssessSchema.getFirstAssessFlag();
		this.BranchAttr = aLAAssessSchema.getBranchAttr();
		this.CalAgentSeries = aLAAssessSchema.getCalAgentSeries();
		this.CalAgentGrade = aLAAssessSchema.getCalAgentGrade();
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
			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ModifyFlag") == null )
				this.ModifyFlag = null;
			else
				this.ModifyFlag = rs.getString("ModifyFlag").trim();

			if( rs.getString("AgentSeries") == null )
				this.AgentSeries = null;
			else
				this.AgentSeries = rs.getString("AgentSeries").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("AgentSeries1") == null )
				this.AgentSeries1 = null;
			else
				this.AgentSeries1 = rs.getString("AgentSeries1").trim();

			if( rs.getString("AgentGrade1") == null )
				this.AgentGrade1 = null;
			else
				this.AgentGrade1 = rs.getString("AgentGrade1").trim();

			if( rs.getString("AgentGroupNew") == null )
				this.AgentGroupNew = null;
			else
				this.AgentGroupNew = rs.getString("AgentGroupNew").trim();

			if( rs.getString("Confirmer") == null )
				this.Confirmer = null;
			else
				this.Confirmer = rs.getString("Confirmer").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("StandAssessFlag") == null )
				this.StandAssessFlag = null;
			else
				this.StandAssessFlag = rs.getString("StandAssessFlag").trim();

			if( rs.getString("FirstAssessFlag") == null )
				this.FirstAssessFlag = null;
			else
				this.FirstAssessFlag = rs.getString("FirstAssessFlag").trim();

			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

			if( rs.getString("CalAgentSeries") == null )
				this.CalAgentSeries = null;
			else
				this.CalAgentSeries = rs.getString("CalAgentSeries").trim();

			if( rs.getString("CalAgentGrade") == null )
				this.CalAgentGrade = null;
			else
				this.CalAgentGrade = rs.getString("CalAgentGrade").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAAssess表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAssessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAAssessSchema getSchema()
	{
		LAAssessSchema aLAAssessSchema = new LAAssessSchema();
		aLAAssessSchema.setSchema(this);
		return aLAAssessSchema;
	}

	public LAAssessDB getDB()
	{
		LAAssessDB aDBOper = new LAAssessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAssess描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(IndexCalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentSeries)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentSeries1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroupNew)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Confirmer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandAssessFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FirstAssessFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalAgentSeries)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalAgentGrade));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAAssess>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ModifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentSeries = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentSeries1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentGrade1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentGroupNew = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Confirmer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandAssessFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			FirstAssessFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			CalAgentSeries = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			CalAgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAAssessSchema";
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
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexCalNo));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyFlag));
		}
		if (FCode.equalsIgnoreCase("AgentSeries"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentSeries));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("AgentSeries1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentSeries1));
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade1));
		}
		if (FCode.equalsIgnoreCase("AgentGroupNew"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroupNew));
		}
		if (FCode.equalsIgnoreCase("Confirmer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Confirmer));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("StandAssessFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandAssessFlag));
		}
		if (FCode.equalsIgnoreCase("FirstAssessFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAssessFlag));
		}
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
		}
		if (FCode.equalsIgnoreCase("CalAgentSeries"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalAgentSeries));
		}
		if (FCode.equalsIgnoreCase("CalAgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalAgentGrade));
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
				strFieldValue = StrTool.GBKToUnicode(IndexCalNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ModifyFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentSeries);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentSeries1);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentGroupNew);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Confirmer);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandAssessFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(FirstAssessFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(CalAgentSeries);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(CalAgentGrade);
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

		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexCalNo = FValue.trim();
			}
			else
				IndexCalNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyFlag = FValue.trim();
			}
			else
				ModifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgentSeries"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentSeries = FValue.trim();
			}
			else
				AgentSeries = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
		}
		if (FCode.equalsIgnoreCase("AgentSeries1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentSeries1 = FValue.trim();
			}
			else
				AgentSeries1 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade1 = FValue.trim();
			}
			else
				AgentGrade1 = null;
		}
		if (FCode.equalsIgnoreCase("AgentGroupNew"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroupNew = FValue.trim();
			}
			else
				AgentGroupNew = null;
		}
		if (FCode.equalsIgnoreCase("Confirmer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Confirmer = FValue.trim();
			}
			else
				Confirmer = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
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
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("StandAssessFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandAssessFlag = FValue.trim();
			}
			else
				StandAssessFlag = null;
		}
		if (FCode.equalsIgnoreCase("FirstAssessFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstAssessFlag = FValue.trim();
			}
			else
				FirstAssessFlag = null;
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
		if (FCode.equalsIgnoreCase("CalAgentSeries"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalAgentSeries = FValue.trim();
			}
			else
				CalAgentSeries = null;
		}
		if (FCode.equalsIgnoreCase("CalAgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalAgentGrade = FValue.trim();
			}
			else
				CalAgentGrade = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAAssessSchema other = (LAAssessSchema)otherObject;
		return
			IndexCalNo.equals(other.getIndexCalNo())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ManageCom.equals(other.getManageCom())
			&& ModifyFlag.equals(other.getModifyFlag())
			&& AgentSeries.equals(other.getAgentSeries())
			&& AgentGrade.equals(other.getAgentGrade())
			&& AgentSeries1.equals(other.getAgentSeries1())
			&& AgentGrade1.equals(other.getAgentGrade1())
			&& AgentGroupNew.equals(other.getAgentGroupNew())
			&& Confirmer.equals(other.getConfirmer())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& State.equals(other.getState())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchType.equals(other.getBranchType())
			&& StandAssessFlag.equals(other.getStandAssessFlag())
			&& FirstAssessFlag.equals(other.getFirstAssessFlag())
			&& BranchAttr.equals(other.getBranchAttr())
			&& CalAgentSeries.equals(other.getCalAgentSeries())
			&& CalAgentGrade.equals(other.getCalAgentGrade());
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
		if( strFieldName.equals("IndexCalNo") ) {
			return 0;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 1;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return 4;
		}
		if( strFieldName.equals("AgentSeries") ) {
			return 5;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 6;
		}
		if( strFieldName.equals("AgentSeries1") ) {
			return 7;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return 8;
		}
		if( strFieldName.equals("AgentGroupNew") ) {
			return 9;
		}
		if( strFieldName.equals("Confirmer") ) {
			return 10;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 11;
		}
		if( strFieldName.equals("State") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("BranchType") ) {
			return 18;
		}
		if( strFieldName.equals("StandAssessFlag") ) {
			return 19;
		}
		if( strFieldName.equals("FirstAssessFlag") ) {
			return 20;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 21;
		}
		if( strFieldName.equals("CalAgentSeries") ) {
			return 22;
		}
		if( strFieldName.equals("CalAgentGrade") ) {
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
				strFieldName = "IndexCalNo";
				break;
			case 1:
				strFieldName = "AgentCode";
				break;
			case 2:
				strFieldName = "AgentGroup";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "ModifyFlag";
				break;
			case 5:
				strFieldName = "AgentSeries";
				break;
			case 6:
				strFieldName = "AgentGrade";
				break;
			case 7:
				strFieldName = "AgentSeries1";
				break;
			case 8:
				strFieldName = "AgentGrade1";
				break;
			case 9:
				strFieldName = "AgentGroupNew";
				break;
			case 10:
				strFieldName = "Confirmer";
				break;
			case 11:
				strFieldName = "ConfirmDate";
				break;
			case 12:
				strFieldName = "State";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "BranchType";
				break;
			case 19:
				strFieldName = "StandAssessFlag";
				break;
			case 20:
				strFieldName = "FirstAssessFlag";
				break;
			case 21:
				strFieldName = "BranchAttr";
				break;
			case 22:
				strFieldName = "CalAgentSeries";
				break;
			case 23:
				strFieldName = "CalAgentGrade";
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
		if( strFieldName.equals("IndexCalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentSeries") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentSeries1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroupNew") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Confirmer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandAssessFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstAssessFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalAgentSeries") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalAgentGrade") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
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
