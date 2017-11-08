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
import com.sinosoft.lis.db.LJPremMatchLogDB;

/*
 * <p>ClassName: LJPremMatchLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPremMatchLogSchema.class);
	// @Field
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 匹配节点 */
	private String MatchNode;
	/** 匹配类型 */
	private String MatchType;
	/** 投保单位名称 */
	private String GrpName;
	/** 收费金额 */
	private double SumMoney;
	/** 使用溢缴金额 */
	private double HisOutPayMoney;
	/** 应收金额 */
	private double DuePayMoney;
	/** 本次溢缴金额 */
	private double CurOutPayMoney;
	/** 匹配日期 */
	private Date MatchDate;
	/** 匹配时间 */
	private String MatchTime;
	/** 校验标识 */
	private String CheckFlag;
	/** 状态 */
	private String State;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPremMatchLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "MatchSerialNo";
		pk[1] = "MatchNode";

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
		LJPremMatchLogSchema cloned = (LJPremMatchLogSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMatchSerialNo()
	{
		return MatchSerialNo;
	}
	public void setMatchSerialNo(String aMatchSerialNo)
	{
		if(aMatchSerialNo!=null && aMatchSerialNo.length()>20)
			throw new IllegalArgumentException("保费匹配流水号MatchSerialNo值"+aMatchSerialNo+"的长度"+aMatchSerialNo.length()+"大于最大值20");
		MatchSerialNo = aMatchSerialNo;
	}
	public String getMatchNode()
	{
		return MatchNode;
	}
	public void setMatchNode(String aMatchNode)
	{
		if(aMatchNode!=null && aMatchNode.length()>2)
			throw new IllegalArgumentException("匹配节点MatchNode值"+aMatchNode+"的长度"+aMatchNode.length()+"大于最大值2");
		MatchNode = aMatchNode;
	}
	/**
	* 00-投保单位交费，01-中介公司代交，02-共保公司交费，03-第三方交费
	*/
	public String getMatchType()
	{
		return MatchType;
	}
	public void setMatchType(String aMatchType)
	{
		if(aMatchType!=null && aMatchType.length()>2)
			throw new IllegalArgumentException("匹配类型MatchType值"+aMatchType+"的长度"+aMatchType.length()+"大于最大值2");
		MatchType = aMatchType;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>200)
			throw new IllegalArgumentException("投保单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值200");
		GrpName = aGrpName;
	}
	public double getSumMoney()
	{
		return SumMoney;
	}
	public void setSumMoney(double aSumMoney)
	{
		SumMoney = aSumMoney;
	}
	public void setSumMoney(String aSumMoney)
	{
		if (aSumMoney != null && !aSumMoney.equals(""))
		{
			Double tDouble = new Double(aSumMoney);
			double d = tDouble.doubleValue();
			SumMoney = d;
		}
	}

	public double getHisOutPayMoney()
	{
		return HisOutPayMoney;
	}
	public void setHisOutPayMoney(double aHisOutPayMoney)
	{
		HisOutPayMoney = aHisOutPayMoney;
	}
	public void setHisOutPayMoney(String aHisOutPayMoney)
	{
		if (aHisOutPayMoney != null && !aHisOutPayMoney.equals(""))
		{
			Double tDouble = new Double(aHisOutPayMoney);
			double d = tDouble.doubleValue();
			HisOutPayMoney = d;
		}
	}

	public double getDuePayMoney()
	{
		return DuePayMoney;
	}
	public void setDuePayMoney(double aDuePayMoney)
	{
		DuePayMoney = aDuePayMoney;
	}
	public void setDuePayMoney(String aDuePayMoney)
	{
		if (aDuePayMoney != null && !aDuePayMoney.equals(""))
		{
			Double tDouble = new Double(aDuePayMoney);
			double d = tDouble.doubleValue();
			DuePayMoney = d;
		}
	}

	public double getCurOutPayMoney()
	{
		return CurOutPayMoney;
	}
	public void setCurOutPayMoney(double aCurOutPayMoney)
	{
		CurOutPayMoney = aCurOutPayMoney;
	}
	public void setCurOutPayMoney(String aCurOutPayMoney)
	{
		if (aCurOutPayMoney != null && !aCurOutPayMoney.equals(""))
		{
			Double tDouble = new Double(aCurOutPayMoney);
			double d = tDouble.doubleValue();
			CurOutPayMoney = d;
		}
	}

	public String getMatchDate()
	{
		if( MatchDate != null )
			return fDate.getString(MatchDate);
		else
			return null;
	}
	public void setMatchDate(Date aMatchDate)
	{
		MatchDate = aMatchDate;
	}
	public void setMatchDate(String aMatchDate)
	{
		if (aMatchDate != null && !aMatchDate.equals("") )
		{
			MatchDate = fDate.getDate( aMatchDate );
		}
		else
			MatchDate = null;
	}

	public String getMatchTime()
	{
		return MatchTime;
	}
	public void setMatchTime(String aMatchTime)
	{
		if(aMatchTime!=null && aMatchTime.length()>8)
			throw new IllegalArgumentException("匹配时间MatchTime值"+aMatchTime+"的长度"+aMatchTime.length()+"大于最大值8");
		MatchTime = aMatchTime;
	}
	/**
	* 0-不需要校验，1-需要校验
	*/
	public String getCheckFlag()
	{
		return CheckFlag;
	}
	public void setCheckFlag(String aCheckFlag)
	{
		if(aCheckFlag!=null && aCheckFlag.length()>1)
			throw new IllegalArgumentException("校验标识CheckFlag值"+aCheckFlag+"的长度"+aCheckFlag.length()+"大于最大值1");
		CheckFlag = aCheckFlag;
	}
	/**
	* 00-保费申请，01-保费匹配，02-匹配审核，03-审核通过，04-申请撤销
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>2)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值2");
		State = aState;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LJPremMatchLogSchema 对象给 Schema 赋值
	* @param: aLJPremMatchLogSchema LJPremMatchLogSchema
	**/
	public void setSchema(LJPremMatchLogSchema aLJPremMatchLogSchema)
	{
		this.MatchSerialNo = aLJPremMatchLogSchema.getMatchSerialNo();
		this.MatchNode = aLJPremMatchLogSchema.getMatchNode();
		this.MatchType = aLJPremMatchLogSchema.getMatchType();
		this.GrpName = aLJPremMatchLogSchema.getGrpName();
		this.SumMoney = aLJPremMatchLogSchema.getSumMoney();
		this.HisOutPayMoney = aLJPremMatchLogSchema.getHisOutPayMoney();
		this.DuePayMoney = aLJPremMatchLogSchema.getDuePayMoney();
		this.CurOutPayMoney = aLJPremMatchLogSchema.getCurOutPayMoney();
		this.MatchDate = fDate.getDate( aLJPremMatchLogSchema.getMatchDate());
		this.MatchTime = aLJPremMatchLogSchema.getMatchTime();
		this.CheckFlag = aLJPremMatchLogSchema.getCheckFlag();
		this.State = aLJPremMatchLogSchema.getState();
		this.ManageCom = aLJPremMatchLogSchema.getManageCom();
		this.ComCode = aLJPremMatchLogSchema.getComCode();
		this.MakeOperator = aLJPremMatchLogSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJPremMatchLogSchema.getMakeDate());
		this.MakeTime = aLJPremMatchLogSchema.getMakeTime();
		this.ModifyOperator = aLJPremMatchLogSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJPremMatchLogSchema.getModifyDate());
		this.ModifyTime = aLJPremMatchLogSchema.getModifyTime();
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
			if( rs.getString("MatchSerialNo") == null )
				this.MatchSerialNo = null;
			else
				this.MatchSerialNo = rs.getString("MatchSerialNo").trim();

			if( rs.getString("MatchNode") == null )
				this.MatchNode = null;
			else
				this.MatchNode = rs.getString("MatchNode").trim();

			if( rs.getString("MatchType") == null )
				this.MatchType = null;
			else
				this.MatchType = rs.getString("MatchType").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			this.SumMoney = rs.getDouble("SumMoney");
			this.HisOutPayMoney = rs.getDouble("HisOutPayMoney");
			this.DuePayMoney = rs.getDouble("DuePayMoney");
			this.CurOutPayMoney = rs.getDouble("CurOutPayMoney");
			this.MatchDate = rs.getDate("MatchDate");
			if( rs.getString("MatchTime") == null )
				this.MatchTime = null;
			else
				this.MatchTime = rs.getString("MatchTime").trim();

			if( rs.getString("CheckFlag") == null )
				this.CheckFlag = null;
			else
				this.CheckFlag = rs.getString("CheckFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJPremMatchLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPremMatchLogSchema getSchema()
	{
		LJPremMatchLogSchema aLJPremMatchLogSchema = new LJPremMatchLogSchema();
		aLJPremMatchLogSchema.setSchema(this);
		return aLJPremMatchLogSchema;
	}

	public LJPremMatchLogDB getDB()
	{
		LJPremMatchLogDB aDBOper = new LJPremMatchLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(HisOutPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurOutPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MatchDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MatchNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MatchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SumMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			HisOutPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			DuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			CurOutPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			MatchDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MatchTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CheckFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchLogSchema";
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
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchSerialNo));
		}
		if (FCode.equalsIgnoreCase("MatchNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchNode));
		}
		if (FCode.equalsIgnoreCase("MatchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchType));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumMoney));
		}
		if (FCode.equalsIgnoreCase("HisOutPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HisOutPayMoney));
		}
		if (FCode.equalsIgnoreCase("DuePayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DuePayMoney));
		}
		if (FCode.equalsIgnoreCase("CurOutPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurOutPayMoney));
		}
		if (FCode.equalsIgnoreCase("MatchDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMatchDate()));
		}
		if (FCode.equalsIgnoreCase("MatchTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchTime));
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(MatchSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MatchNode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MatchType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 4:
				strFieldValue = String.valueOf(SumMoney);
				break;
			case 5:
				strFieldValue = String.valueOf(HisOutPayMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(DuePayMoney);
				break;
			case 7:
				strFieldValue = String.valueOf(CurOutPayMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMatchDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MatchTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CheckFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("MatchNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchNode = FValue.trim();
			}
			else
				MatchNode = null;
		}
		if (FCode.equalsIgnoreCase("MatchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchType = FValue.trim();
			}
			else
				MatchType = null;
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equalsIgnoreCase("SumMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SumMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("HisOutPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				HisOutPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("DuePayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DuePayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurOutPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurOutPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("MatchDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MatchDate = fDate.getDate( FValue );
			}
			else
				MatchDate = null;
		}
		if (FCode.equalsIgnoreCase("MatchTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchTime = FValue.trim();
			}
			else
				MatchTime = null;
		}
		if (FCode.equalsIgnoreCase("CheckFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckFlag = FValue.trim();
			}
			else
				CheckFlag = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJPremMatchLogSchema other = (LJPremMatchLogSchema)otherObject;
		return
			MatchSerialNo.equals(other.getMatchSerialNo())
			&& MatchNode.equals(other.getMatchNode())
			&& MatchType.equals(other.getMatchType())
			&& GrpName.equals(other.getGrpName())
			&& SumMoney == other.getSumMoney()
			&& HisOutPayMoney == other.getHisOutPayMoney()
			&& DuePayMoney == other.getDuePayMoney()
			&& CurOutPayMoney == other.getCurOutPayMoney()
			&& fDate.getString(MatchDate).equals(other.getMatchDate())
			&& MatchTime.equals(other.getMatchTime())
			&& CheckFlag.equals(other.getCheckFlag())
			&& State.equals(other.getState())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("MatchNode") ) {
			return 1;
		}
		if( strFieldName.equals("MatchType") ) {
			return 2;
		}
		if( strFieldName.equals("GrpName") ) {
			return 3;
		}
		if( strFieldName.equals("SumMoney") ) {
			return 4;
		}
		if( strFieldName.equals("HisOutPayMoney") ) {
			return 5;
		}
		if( strFieldName.equals("DuePayMoney") ) {
			return 6;
		}
		if( strFieldName.equals("CurOutPayMoney") ) {
			return 7;
		}
		if( strFieldName.equals("MatchDate") ) {
			return 8;
		}
		if( strFieldName.equals("MatchTime") ) {
			return 9;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return 10;
		}
		if( strFieldName.equals("State") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("ComCode") ) {
			return 13;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "MatchSerialNo";
				break;
			case 1:
				strFieldName = "MatchNode";
				break;
			case 2:
				strFieldName = "MatchType";
				break;
			case 3:
				strFieldName = "GrpName";
				break;
			case 4:
				strFieldName = "SumMoney";
				break;
			case 5:
				strFieldName = "HisOutPayMoney";
				break;
			case 6:
				strFieldName = "DuePayMoney";
				break;
			case 7:
				strFieldName = "CurOutPayMoney";
				break;
			case 8:
				strFieldName = "MatchDate";
				break;
			case 9:
				strFieldName = "MatchTime";
				break;
			case 10:
				strFieldName = "CheckFlag";
				break;
			case 11:
				strFieldName = "State";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "ComCode";
				break;
			case 14:
				strFieldName = "MakeOperator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyOperator";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("HisOutPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DuePayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurOutPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MatchDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MatchTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
