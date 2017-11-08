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
import com.sinosoft.lis.db.LARelationBDB;

/*
 * <p>ClassName: LARelationBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LARelationBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LARelationBSchema.class);

	// @Field
	/** 转储号 */
	private String EdorNo;
	/** 转储类型 */
	private String EdorType;
	/** 记录关系类型 */
	private String RelaType;
	/** 关系级别 */
	private String RelaLevel;
	/** 关系代数目 */
	private int RelaGens;
	/** 被育成人 */
	private String AgentCode;
	/** 育成人 */
	private String RelaAgentCode;
	/** 被育成机构 */
	private String AgentGroup;
	/** 关系建立起期 */
	private Date startDate;
	/** 关系建立止期 */
	private Date EndDate;
	/** 关系存在标记 */
	private String RearFlag;
	/** 津贴抽取标记 */
	private String RearCommFlag;
	/** 津贴抽取起始年度 */
	private int RearStartYear;
	/** 原最近修改时间 */
	private String ModifyTime2;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最近修改日期 */
	private Date ModifyDate;
	/** 最近修改时间 */
	private String ModifyTime;
	/** 操作员代码 */
	private String Operator;
	/** 原操作员 */
	private String Operator2;
	/** 原入机日期 */
	private Date MakeDate2;
	/** 原入机时间 */
	private String MakeTime2;
	/** 原最近修改日期 */
	private Date ModifyDate2;
	/** 回算抽取标记/增员奖金标记 */
	private String ReCalFlag;
	/** 考核年月 */
	private String IndexCalNo;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LARelationBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "RelaType";
		pk[3] = "RelaLevel";
		pk[4] = "RelaGens";
		pk[5] = "AgentCode";

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
		LARelationBSchema cloned = (LARelationBSchema)super.clone();
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
	/**
	* 01:考核结果<p>
	* 02:初始育成关系修改(初始信息修改把一个主管降级,对其相应级别后链进行备份)<p>
	* 03:增员关系继承<p>
	* 04:<p>
	* 05:初始信息修改<p>
	* 06:行政信息修改<p>
	* <p>
	* <p>
	* <p>
	* 08:离职？？？？？<p>
	* <p>
	* 09:考核恢复？？？
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
	* 01: 增员关系<p>
	* 02: 育成关系<p>
	* 03: 增员关系继承<p>
	* 04: 增员奖金
	*/
	public String getRelaType()
	{
		return RelaType;
	}
	public void setRelaType(String aRelaType)
	{
		RelaType = aRelaType;
	}
	/**
	* 育成关系：<p>
	* <p>
	* 01: 组育成人<p>
	* 02：增部人<p>
	* 03：区育成人<p>
	* <p>
	* 04：督导区育成人<p>
	* <p>
	* <p>
	* 增员关系：<p>
	* <p>
	* 10<p>
	* <p>
	* 增员关系继承<p>
	* 20<p>
	* <p>
	* 增员奖金<p>
	* 30
	*/
	public String getRelaLevel()
	{
		return RelaLevel;
	}
	public void setRelaLevel(String aRelaLevel)
	{
		RelaLevel = aRelaLevel;
	}
	public int getRelaGens()
	{
		return RelaGens;
	}
	public void setRelaGens(int aRelaGens)
	{
		RelaGens = aRelaGens;
	}
	public void setRelaGens(String aRelaGens)
	{
		if (aRelaGens != null && !aRelaGens.equals(""))
		{
			Integer tInteger = new Integer(aRelaGens);
			int i = tInteger.intValue();
			RelaGens = i;
		}
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getRelaAgentCode()
	{
		return RelaAgentCode;
	}
	public void setRelaAgentCode(String aRelaAgentCode)
	{
		RelaAgentCode = aRelaAgentCode;
	}
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	public String getstartDate()
	{
		if( startDate != null )
			return fDate.getString(startDate);
		else
			return null;
	}
	public void setstartDate(Date astartDate)
	{
		startDate = astartDate;
	}
	public void setstartDate(String astartDate)
	{
		if (astartDate != null && !astartDate.equals("") )
		{
			startDate = fDate.getDate( astartDate );
		}
		else
			startDate = null;
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

	/**
	* 0-无效<p>
	* 1-有效
	*/
	public String getRearFlag()
	{
		return RearFlag;
	}
	public void setRearFlag(String aRearFlag)
	{
		RearFlag = aRearFlag;
	}
	/**
	* 0-无效<p>
	* 1-有效
	*/
	public String getRearCommFlag()
	{
		return RearCommFlag;
	}
	public void setRearCommFlag(String aRearCommFlag)
	{
		RearCommFlag = aRearCommFlag;
	}
	/**
	* 1-第一次被育成<p>
	* 2-第二次被同一育成人育成（提育成奖比例不同）<p>
	* <p>
	* 3-。。。
	*/
	public int getRearStartYear()
	{
		return RearStartYear;
	}
	public void setRearStartYear(int aRearStartYear)
	{
		RearStartYear = aRearStartYear;
	}
	public void setRearStartYear(String aRearStartYear)
	{
		if (aRearStartYear != null && !aRearStartYear.equals(""))
		{
			Integer tInteger = new Integer(aRearStartYear);
			int i = tInteger.intValue();
			RearStartYear = i;
		}
	}

	public String getModifyTime2()
	{
		return ModifyTime2;
	}
	public void setModifyTime2(String aModifyTime2)
	{
		ModifyTime2 = aModifyTime2;
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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

	/**
	* 回算关系:<p>
	* 0:不回算<p>
	* <p>
	* 1:回算
	*/
	public String getReCalFlag()
	{
		return ReCalFlag;
	}
	public void setReCalFlag(String aReCalFlag)
	{
		ReCalFlag = aReCalFlag;
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
	* 使用另外一个 LARelationBSchema 对象给 Schema 赋值
	* @param: aLARelationBSchema LARelationBSchema
	**/
	public void setSchema(LARelationBSchema aLARelationBSchema)
	{
		this.EdorNo = aLARelationBSchema.getEdorNo();
		this.EdorType = aLARelationBSchema.getEdorType();
		this.RelaType = aLARelationBSchema.getRelaType();
		this.RelaLevel = aLARelationBSchema.getRelaLevel();
		this.RelaGens = aLARelationBSchema.getRelaGens();
		this.AgentCode = aLARelationBSchema.getAgentCode();
		this.RelaAgentCode = aLARelationBSchema.getRelaAgentCode();
		this.AgentGroup = aLARelationBSchema.getAgentGroup();
		this.startDate = fDate.getDate( aLARelationBSchema.getstartDate());
		this.EndDate = fDate.getDate( aLARelationBSchema.getEndDate());
		this.RearFlag = aLARelationBSchema.getRearFlag();
		this.RearCommFlag = aLARelationBSchema.getRearCommFlag();
		this.RearStartYear = aLARelationBSchema.getRearStartYear();
		this.ModifyTime2 = aLARelationBSchema.getModifyTime2();
		this.MakeDate = fDate.getDate( aLARelationBSchema.getMakeDate());
		this.MakeTime = aLARelationBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLARelationBSchema.getModifyDate());
		this.ModifyTime = aLARelationBSchema.getModifyTime();
		this.Operator = aLARelationBSchema.getOperator();
		this.Operator2 = aLARelationBSchema.getOperator2();
		this.MakeDate2 = fDate.getDate( aLARelationBSchema.getMakeDate2());
		this.MakeTime2 = aLARelationBSchema.getMakeTime2();
		this.ModifyDate2 = fDate.getDate( aLARelationBSchema.getModifyDate2());
		this.ReCalFlag = aLARelationBSchema.getReCalFlag();
		this.IndexCalNo = aLARelationBSchema.getIndexCalNo();
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

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("RelaType") == null )
				this.RelaType = null;
			else
				this.RelaType = rs.getString("RelaType").trim();

			if( rs.getString("RelaLevel") == null )
				this.RelaLevel = null;
			else
				this.RelaLevel = rs.getString("RelaLevel").trim();

			this.RelaGens = rs.getInt("RelaGens");
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("RelaAgentCode") == null )
				this.RelaAgentCode = null;
			else
				this.RelaAgentCode = rs.getString("RelaAgentCode").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			this.startDate = rs.getDate("startDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("RearFlag") == null )
				this.RearFlag = null;
			else
				this.RearFlag = rs.getString("RearFlag").trim();

			if( rs.getString("RearCommFlag") == null )
				this.RearCommFlag = null;
			else
				this.RearCommFlag = rs.getString("RearCommFlag").trim();

			this.RearStartYear = rs.getInt("RearStartYear");
			if( rs.getString("ModifyTime2") == null )
				this.ModifyTime2 = null;
			else
				this.ModifyTime2 = rs.getString("ModifyTime2").trim();

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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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
			if( rs.getString("ReCalFlag") == null )
				this.ReCalFlag = null;
			else
				this.ReCalFlag = rs.getString("ReCalFlag").trim();

			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LARelationB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARelationBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LARelationBSchema getSchema()
	{
		LARelationBSchema aLARelationBSchema = new LARelationBSchema();
		aLARelationBSchema.setSchema(this);
		return aLARelationBSchema;
	}

	public LARelationBDB getDB()
	{
		LARelationBDB aDBOper = new LARelationBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARelationB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaGens));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaAgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( startDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RearFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RearCommFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RearStartYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReCalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexCalNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARelationB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RelaLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaGens= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RelaAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			startDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			RearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RearCommFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RearStartYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ReCalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARelationBSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaType));
		}
		if (FCode.equalsIgnoreCase("RelaLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaLevel));
		}
		if (FCode.equalsIgnoreCase("RelaGens"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaGens));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("RelaAgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaAgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("startDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getstartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("RearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RearFlag));
		}
		if (FCode.equalsIgnoreCase("RearCommFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RearCommFlag));
		}
		if (FCode.equalsIgnoreCase("RearStartYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RearStartYear));
		}
		if (FCode.equalsIgnoreCase("ModifyTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime2));
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
		if (FCode.equalsIgnoreCase("ReCalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReCalFlag));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RelaLevel);
				break;
			case 4:
				strFieldValue = String.valueOf(RelaGens);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RelaAgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getstartDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RearFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RearCommFlag);
				break;
			case 12:
				strFieldValue = String.valueOf(RearStartYear);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime2);
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
				strFieldValue = StrTool.GBKToUnicode(Operator);
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
				strFieldValue = StrTool.GBKToUnicode(ReCalFlag);
				break;
			case 24:
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaType = FValue.trim();
			}
			else
				RelaType = null;
		}
		if (FCode.equalsIgnoreCase("RelaLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaLevel = FValue.trim();
			}
			else
				RelaLevel = null;
		}
		if (FCode.equalsIgnoreCase("RelaGens"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaGens = i;
			}
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
		if (FCode.equalsIgnoreCase("RelaAgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaAgentCode = FValue.trim();
			}
			else
				RelaAgentCode = null;
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
		if (FCode.equalsIgnoreCase("startDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				startDate = fDate.getDate( FValue );
			}
			else
				startDate = null;
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
		if (FCode.equalsIgnoreCase("RearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RearFlag = FValue.trim();
			}
			else
				RearFlag = null;
		}
		if (FCode.equalsIgnoreCase("RearCommFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RearCommFlag = FValue.trim();
			}
			else
				RearCommFlag = null;
		}
		if (FCode.equalsIgnoreCase("RearStartYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RearStartYear = i;
			}
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("ReCalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReCalFlag = FValue.trim();
			}
			else
				ReCalFlag = null;
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
		LARelationBSchema other = (LARelationBSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& RelaType.equals(other.getRelaType())
			&& RelaLevel.equals(other.getRelaLevel())
			&& RelaGens == other.getRelaGens()
			&& AgentCode.equals(other.getAgentCode())
			&& RelaAgentCode.equals(other.getRelaAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& fDate.getString(startDate).equals(other.getstartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& RearFlag.equals(other.getRearFlag())
			&& RearCommFlag.equals(other.getRearCommFlag())
			&& RearStartYear == other.getRearStartYear()
			&& ModifyTime2.equals(other.getModifyTime2())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& Operator2.equals(other.getOperator2())
			&& fDate.getString(MakeDate2).equals(other.getMakeDate2())
			&& MakeTime2.equals(other.getMakeTime2())
			&& fDate.getString(ModifyDate2).equals(other.getModifyDate2())
			&& ReCalFlag.equals(other.getReCalFlag())
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("RelaType") ) {
			return 2;
		}
		if( strFieldName.equals("RelaLevel") ) {
			return 3;
		}
		if( strFieldName.equals("RelaGens") ) {
			return 4;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 5;
		}
		if( strFieldName.equals("RelaAgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 7;
		}
		if( strFieldName.equals("startDate") ) {
			return 8;
		}
		if( strFieldName.equals("EndDate") ) {
			return 9;
		}
		if( strFieldName.equals("RearFlag") ) {
			return 10;
		}
		if( strFieldName.equals("RearCommFlag") ) {
			return 11;
		}
		if( strFieldName.equals("RearStartYear") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime2") ) {
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
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("ReCalFlag") ) {
			return 23;
		}
		if( strFieldName.equals("IndexCalNo") ) {
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "RelaType";
				break;
			case 3:
				strFieldName = "RelaLevel";
				break;
			case 4:
				strFieldName = "RelaGens";
				break;
			case 5:
				strFieldName = "AgentCode";
				break;
			case 6:
				strFieldName = "RelaAgentCode";
				break;
			case 7:
				strFieldName = "AgentGroup";
				break;
			case 8:
				strFieldName = "startDate";
				break;
			case 9:
				strFieldName = "EndDate";
				break;
			case 10:
				strFieldName = "RearFlag";
				break;
			case 11:
				strFieldName = "RearCommFlag";
				break;
			case 12:
				strFieldName = "RearStartYear";
				break;
			case 13:
				strFieldName = "ModifyTime2";
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
				strFieldName = "Operator";
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
				strFieldName = "ReCalFlag";
				break;
			case 24:
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaGens") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaAgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("startDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RearCommFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RearStartYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime2") ) {
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
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("ReCalFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
