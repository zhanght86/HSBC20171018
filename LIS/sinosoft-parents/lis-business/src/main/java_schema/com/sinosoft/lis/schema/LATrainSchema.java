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
import com.sinosoft.lis.db.LATrainDB;

/*
 * <p>ClassName: LATrainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATrainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LATrainSchema.class);

	// @Field
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人展业机构代码 */
	private String AgentGroup;
	/** 管理机构 */
	private String ManageCom;
	/** 纪录顺序号 */
	private int Idx;
	/** 培训类别 */
	private String AClass;
	/** 培训单位 */
	private String TrainUnit;
	/** 培训名称 */
	private String TrainName;
	/** 负责人 */
	private String Charger;
	/** 成绩级别 */
	private String ResultLevel;
	/** 成绩 */
	private String Result;
	/** 培训通过标记 */
	private String TrainPassFlag;
	/** 培训起始时间 */
	private Date TrainStart;
	/** 培训终止时间 */
	private Date TrainEnd;
	/** 操作员代码 */
	private String Operator;
	/** 执行日期 */
	private Date DoneDate;
	/** 处理标记 */
	private String DoneFlag;
	/** 批注 */
	private String Noti;
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
	/** 展业机构外部编码 */
	private String BranchAttr;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LATrainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AgentCode";
		pk[1] = "Idx";

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
		LATrainSchema cloned = (LATrainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 通过该字段对应银行专有属性表(可能是银行信息表）
	*/
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
	public int getIdx()
	{
		return Idx;
	}
	public void setIdx(int aIdx)
	{
		Idx = aIdx;
	}
	public void setIdx(String aIdx)
	{
		if (aIdx != null && !aIdx.equals(""))
		{
			Integer tInteger = new Integer(aIdx);
			int i = tInteger.intValue();
			Idx = i;
		}
	}

	/**
	* 
	*/
	public String getAClass()
	{
		return AClass;
	}
	public void setAClass(String aAClass)
	{
		AClass = aAClass;
	}
	public String getTrainUnit()
	{
		return TrainUnit;
	}
	public void setTrainUnit(String aTrainUnit)
	{
		TrainUnit = aTrainUnit;
	}
	public String getTrainName()
	{
		return TrainName;
	}
	public void setTrainName(String aTrainName)
	{
		TrainName = aTrainName;
	}
	public String getCharger()
	{
		return Charger;
	}
	public void setCharger(String aCharger)
	{
		Charger = aCharger;
	}
	/**
	* （优良中差）
	*/
	public String getResultLevel()
	{
		return ResultLevel;
	}
	public void setResultLevel(String aResultLevel)
	{
		ResultLevel = aResultLevel;
	}
	public String getResult()
	{
		return Result;
	}
	public void setResult(String aResult)
	{
		Result = aResult;
	}
	public String getTrainPassFlag()
	{
		return TrainPassFlag;
	}
	public void setTrainPassFlag(String aTrainPassFlag)
	{
		TrainPassFlag = aTrainPassFlag;
	}
	public String getTrainStart()
	{
		if( TrainStart != null )
			return fDate.getString(TrainStart);
		else
			return null;
	}
	public void setTrainStart(Date aTrainStart)
	{
		TrainStart = aTrainStart;
	}
	public void setTrainStart(String aTrainStart)
	{
		if (aTrainStart != null && !aTrainStart.equals("") )
		{
			TrainStart = fDate.getDate( aTrainStart );
		}
		else
			TrainStart = null;
	}

	public String getTrainEnd()
	{
		if( TrainEnd != null )
			return fDate.getString(TrainEnd);
		else
			return null;
	}
	public void setTrainEnd(Date aTrainEnd)
	{
		TrainEnd = aTrainEnd;
	}
	public void setTrainEnd(String aTrainEnd)
	{
		if (aTrainEnd != null && !aTrainEnd.equals("") )
		{
			TrainEnd = fDate.getDate( aTrainEnd );
		}
		else
			TrainEnd = null;
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getDoneDate()
	{
		if( DoneDate != null )
			return fDate.getString(DoneDate);
		else
			return null;
	}
	public void setDoneDate(Date aDoneDate)
	{
		DoneDate = aDoneDate;
	}
	public void setDoneDate(String aDoneDate)
	{
		if (aDoneDate != null && !aDoneDate.equals("") )
		{
			DoneDate = fDate.getDate( aDoneDate );
		}
		else
			DoneDate = null;
	}

	public String getDoneFlag()
	{
		return DoneFlag;
	}
	public void setDoneFlag(String aDoneFlag)
	{
		DoneFlag = aDoneFlag;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
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
	public String getBranchAttr()
	{
		return BranchAttr;
	}
	public void setBranchAttr(String aBranchAttr)
	{
		BranchAttr = aBranchAttr;
	}

	/**
	* 使用另外一个 LATrainSchema 对象给 Schema 赋值
	* @param: aLATrainSchema LATrainSchema
	**/
	public void setSchema(LATrainSchema aLATrainSchema)
	{
		this.AgentCode = aLATrainSchema.getAgentCode();
		this.AgentGroup = aLATrainSchema.getAgentGroup();
		this.ManageCom = aLATrainSchema.getManageCom();
		this.Idx = aLATrainSchema.getIdx();
		this.AClass = aLATrainSchema.getAClass();
		this.TrainUnit = aLATrainSchema.getTrainUnit();
		this.TrainName = aLATrainSchema.getTrainName();
		this.Charger = aLATrainSchema.getCharger();
		this.ResultLevel = aLATrainSchema.getResultLevel();
		this.Result = aLATrainSchema.getResult();
		this.TrainPassFlag = aLATrainSchema.getTrainPassFlag();
		this.TrainStart = fDate.getDate( aLATrainSchema.getTrainStart());
		this.TrainEnd = fDate.getDate( aLATrainSchema.getTrainEnd());
		this.Operator = aLATrainSchema.getOperator();
		this.DoneDate = fDate.getDate( aLATrainSchema.getDoneDate());
		this.DoneFlag = aLATrainSchema.getDoneFlag();
		this.Noti = aLATrainSchema.getNoti();
		this.MakeDate = fDate.getDate( aLATrainSchema.getMakeDate());
		this.MakeTime = aLATrainSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLATrainSchema.getModifyDate());
		this.ModifyTime = aLATrainSchema.getModifyTime();
		this.BranchType = aLATrainSchema.getBranchType();
		this.BranchAttr = aLATrainSchema.getBranchAttr();
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

			this.Idx = rs.getInt("Idx");
			if( rs.getString("AClass") == null )
				this.AClass = null;
			else
				this.AClass = rs.getString("AClass").trim();

			if( rs.getString("TrainUnit") == null )
				this.TrainUnit = null;
			else
				this.TrainUnit = rs.getString("TrainUnit").trim();

			if( rs.getString("TrainName") == null )
				this.TrainName = null;
			else
				this.TrainName = rs.getString("TrainName").trim();

			if( rs.getString("Charger") == null )
				this.Charger = null;
			else
				this.Charger = rs.getString("Charger").trim();

			if( rs.getString("ResultLevel") == null )
				this.ResultLevel = null;
			else
				this.ResultLevel = rs.getString("ResultLevel").trim();

			if( rs.getString("Result") == null )
				this.Result = null;
			else
				this.Result = rs.getString("Result").trim();

			if( rs.getString("TrainPassFlag") == null )
				this.TrainPassFlag = null;
			else
				this.TrainPassFlag = rs.getString("TrainPassFlag").trim();

			this.TrainStart = rs.getDate("TrainStart");
			this.TrainEnd = rs.getDate("TrainEnd");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.DoneDate = rs.getDate("DoneDate");
			if( rs.getString("DoneFlag") == null )
				this.DoneFlag = null;
			else
				this.DoneFlag = rs.getString("DoneFlag").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

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

			if( rs.getString("BranchAttr") == null )
				this.BranchAttr = null;
			else
				this.BranchAttr = rs.getString("BranchAttr").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LATrain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATrainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LATrainSchema getSchema()
	{
		LATrainSchema aLATrainSchema = new LATrainSchema();
		aLATrainSchema.setSchema(this);
		return aLATrainSchema;
	}

	public LATrainDB getDB()
	{
		LATrainDB aDBOper = new LATrainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATrain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Idx));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrainUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrainName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Charger)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ResultLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Result)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TrainPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TrainStart ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TrainEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DoneDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DoneFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchAttr));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATrain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Idx= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			AClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TrainUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TrainName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Charger = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ResultLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Result = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TrainPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TrainStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			TrainEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DoneDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			DoneFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			BranchAttr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATrainSchema";
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
		if (FCode.equalsIgnoreCase("Idx"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Idx));
		}
		if (FCode.equalsIgnoreCase("AClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AClass));
		}
		if (FCode.equalsIgnoreCase("TrainUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrainUnit));
		}
		if (FCode.equalsIgnoreCase("TrainName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrainName));
		}
		if (FCode.equalsIgnoreCase("Charger"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Charger));
		}
		if (FCode.equalsIgnoreCase("ResultLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ResultLevel));
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Result));
		}
		if (FCode.equalsIgnoreCase("TrainPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TrainPassFlag));
		}
		if (FCode.equalsIgnoreCase("TrainStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTrainStart()));
		}
		if (FCode.equalsIgnoreCase("TrainEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTrainEnd()));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
		}
		if (FCode.equalsIgnoreCase("DoneFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoneFlag));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchAttr));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = String.valueOf(Idx);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AClass);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TrainUnit);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TrainName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Charger);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ResultLevel);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Result);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(TrainPassFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTrainStart()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTrainEnd()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDoneDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(DoneFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Noti);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(BranchAttr);
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
		if (FCode.equalsIgnoreCase("Idx"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Idx = i;
			}
		}
		if (FCode.equalsIgnoreCase("AClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AClass = FValue.trim();
			}
			else
				AClass = null;
		}
		if (FCode.equalsIgnoreCase("TrainUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrainUnit = FValue.trim();
			}
			else
				TrainUnit = null;
		}
		if (FCode.equalsIgnoreCase("TrainName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrainName = FValue.trim();
			}
			else
				TrainName = null;
		}
		if (FCode.equalsIgnoreCase("Charger"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Charger = FValue.trim();
			}
			else
				Charger = null;
		}
		if (FCode.equalsIgnoreCase("ResultLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ResultLevel = FValue.trim();
			}
			else
				ResultLevel = null;
		}
		if (FCode.equalsIgnoreCase("Result"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Result = FValue.trim();
			}
			else
				Result = null;
		}
		if (FCode.equalsIgnoreCase("TrainPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TrainPassFlag = FValue.trim();
			}
			else
				TrainPassFlag = null;
		}
		if (FCode.equalsIgnoreCase("TrainStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TrainStart = fDate.getDate( FValue );
			}
			else
				TrainStart = null;
		}
		if (FCode.equalsIgnoreCase("TrainEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TrainEnd = fDate.getDate( FValue );
			}
			else
				TrainEnd = null;
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
		if (FCode.equalsIgnoreCase("DoneDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DoneDate = fDate.getDate( FValue );
			}
			else
				DoneDate = null;
		}
		if (FCode.equalsIgnoreCase("DoneFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DoneFlag = FValue.trim();
			}
			else
				DoneFlag = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
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
		if (FCode.equalsIgnoreCase("BranchAttr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchAttr = FValue.trim();
			}
			else
				BranchAttr = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LATrainSchema other = (LATrainSchema)otherObject;
		return
			AgentCode.equals(other.getAgentCode())
			&& AgentGroup.equals(other.getAgentGroup())
			&& ManageCom.equals(other.getManageCom())
			&& Idx == other.getIdx()
			&& AClass.equals(other.getAClass())
			&& TrainUnit.equals(other.getTrainUnit())
			&& TrainName.equals(other.getTrainName())
			&& Charger.equals(other.getCharger())
			&& ResultLevel.equals(other.getResultLevel())
			&& Result.equals(other.getResult())
			&& TrainPassFlag.equals(other.getTrainPassFlag())
			&& fDate.getString(TrainStart).equals(other.getTrainStart())
			&& fDate.getString(TrainEnd).equals(other.getTrainEnd())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(DoneDate).equals(other.getDoneDate())
			&& DoneFlag.equals(other.getDoneFlag())
			&& Noti.equals(other.getNoti())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& BranchType.equals(other.getBranchType())
			&& BranchAttr.equals(other.getBranchAttr());
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
		if( strFieldName.equals("AgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("Idx") ) {
			return 3;
		}
		if( strFieldName.equals("AClass") ) {
			return 4;
		}
		if( strFieldName.equals("TrainUnit") ) {
			return 5;
		}
		if( strFieldName.equals("TrainName") ) {
			return 6;
		}
		if( strFieldName.equals("Charger") ) {
			return 7;
		}
		if( strFieldName.equals("ResultLevel") ) {
			return 8;
		}
		if( strFieldName.equals("Result") ) {
			return 9;
		}
		if( strFieldName.equals("TrainPassFlag") ) {
			return 10;
		}
		if( strFieldName.equals("TrainStart") ) {
			return 11;
		}
		if( strFieldName.equals("TrainEnd") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("DoneDate") ) {
			return 14;
		}
		if( strFieldName.equals("DoneFlag") ) {
			return 15;
		}
		if( strFieldName.equals("Noti") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
		}
		if( strFieldName.equals("BranchType") ) {
			return 21;
		}
		if( strFieldName.equals("BranchAttr") ) {
			return 22;
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
				strFieldName = "AgentCode";
				break;
			case 1:
				strFieldName = "AgentGroup";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "Idx";
				break;
			case 4:
				strFieldName = "AClass";
				break;
			case 5:
				strFieldName = "TrainUnit";
				break;
			case 6:
				strFieldName = "TrainName";
				break;
			case 7:
				strFieldName = "Charger";
				break;
			case 8:
				strFieldName = "ResultLevel";
				break;
			case 9:
				strFieldName = "Result";
				break;
			case 10:
				strFieldName = "TrainPassFlag";
				break;
			case 11:
				strFieldName = "TrainStart";
				break;
			case 12:
				strFieldName = "TrainEnd";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "DoneDate";
				break;
			case 15:
				strFieldName = "DoneFlag";
				break;
			case 16:
				strFieldName = "Noti";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "BranchType";
				break;
			case 22:
				strFieldName = "BranchAttr";
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Idx") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Charger") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ResultLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Result") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TrainStart") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TrainEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoneDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DoneFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
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
		if( strFieldName.equals("BranchAttr") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
