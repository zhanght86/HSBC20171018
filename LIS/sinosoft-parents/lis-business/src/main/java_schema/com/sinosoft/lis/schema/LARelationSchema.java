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
import com.sinosoft.lis.db.LARelationDB;

/*
 * <p>ClassName: LARelationSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug2
 */
public class LARelationSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LARelationSchema.class);
	// @Field
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
	/** 回算抽取标记/增员奖金标记 */
	private String ReCalFlag;
	/** 考核年月 */
	private String IndexCalNo;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LARelationSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RelaType";
		pk[1] = "RelaLevel";
		pk[2] = "RelaGens";
		pk[3] = "AgentCode";

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
		LARelationSchema cloned = (LARelationSchema)super.clone();
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
	/**
	* 育成关系:<p>
	* 0:不回算<p>
	* <p>
	* 1:回算<p>
	* <p>
	* 增员奖金<p>
	* 0:不参与增员奖金统计<p>
	* <p>
	* 1:参与增员奖金统计
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
	* 使用另外一个 LARelationSchema 对象给 Schema 赋值
	* @param: aLARelationSchema LARelationSchema
	**/
	public void setSchema(LARelationSchema aLARelationSchema)
	{
		this.RelaType = aLARelationSchema.getRelaType();
		this.RelaLevel = aLARelationSchema.getRelaLevel();
		this.RelaGens = aLARelationSchema.getRelaGens();
		this.AgentCode = aLARelationSchema.getAgentCode();
		this.RelaAgentCode = aLARelationSchema.getRelaAgentCode();
		this.AgentGroup = aLARelationSchema.getAgentGroup();
		this.startDate = fDate.getDate( aLARelationSchema.getstartDate());
		this.EndDate = fDate.getDate( aLARelationSchema.getEndDate());
		this.RearFlag = aLARelationSchema.getRearFlag();
		this.RearCommFlag = aLARelationSchema.getRearCommFlag();
		this.RearStartYear = aLARelationSchema.getRearStartYear();
		this.MakeDate = fDate.getDate( aLARelationSchema.getMakeDate());
		this.MakeTime = aLARelationSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLARelationSchema.getModifyDate());
		this.ModifyTime = aLARelationSchema.getModifyTime();
		this.Operator = aLARelationSchema.getOperator();
		this.ReCalFlag = aLARelationSchema.getReCalFlag();
		this.IndexCalNo = aLARelationSchema.getIndexCalNo();
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
			logger.debug("数据库中的LARelation表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARelationSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LARelationSchema getSchema()
	{
		LARelationSchema aLARelationSchema = new LARelationSchema();
		aLARelationSchema.setSchema(this);
		return aLARelationSchema;
	}

	public LARelationDB getDB()
	{
		LARelationDB aDBOper = new LARelationDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARelation描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
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
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReCalFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexCalNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLARelation>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RelaLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RelaGens= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			startDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			RearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RearCommFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RearStartYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ReCalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LARelationSchema";
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
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RelaLevel);
				break;
			case 2:
				strFieldValue = String.valueOf(RelaGens);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelaAgentCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getstartDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RearFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RearCommFlag);
				break;
			case 10:
				strFieldValue = String.valueOf(RearStartYear);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ReCalFlag);
				break;
			case 17:
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
		LARelationSchema other = (LARelationSchema)otherObject;
		return
			RelaType.equals(other.getRelaType())
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
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("RelaType") ) {
			return 0;
		}
		if( strFieldName.equals("RelaLevel") ) {
			return 1;
		}
		if( strFieldName.equals("RelaGens") ) {
			return 2;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 3;
		}
		if( strFieldName.equals("RelaAgentCode") ) {
			return 4;
		}
		if( strFieldName.equals("AgentGroup") ) {
			return 5;
		}
		if( strFieldName.equals("startDate") ) {
			return 6;
		}
		if( strFieldName.equals("EndDate") ) {
			return 7;
		}
		if( strFieldName.equals("RearFlag") ) {
			return 8;
		}
		if( strFieldName.equals("RearCommFlag") ) {
			return 9;
		}
		if( strFieldName.equals("RearStartYear") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("ReCalFlag") ) {
			return 16;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return 17;
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
				strFieldName = "RelaType";
				break;
			case 1:
				strFieldName = "RelaLevel";
				break;
			case 2:
				strFieldName = "RelaGens";
				break;
			case 3:
				strFieldName = "AgentCode";
				break;
			case 4:
				strFieldName = "RelaAgentCode";
				break;
			case 5:
				strFieldName = "AgentGroup";
				break;
			case 6:
				strFieldName = "startDate";
				break;
			case 7:
				strFieldName = "EndDate";
				break;
			case 8:
				strFieldName = "RearFlag";
				break;
			case 9:
				strFieldName = "RearCommFlag";
				break;
			case 10:
				strFieldName = "RearStartYear";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "ReCalFlag";
				break;
			case 17:
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
