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
import com.sinosoft.lis.db.LCGrpContStopEdorStateDB;

/*
 * <p>ClassName: LCGrpContStopEdorStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCGrpContStopEdorStateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpContStopEdorStateSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体险种保单号 */
	private String GrpPolNo;
	/** 险种编码 */
	private String RiskCode;
	/** 保险期间中断保全号 */
	private String StopEdorNo;
	/** 保险期间恢复保全号 */
	private String ReStartEdorNo;
	/** 保险期间中断日期 */
	private Date StopDate;
	/** 保险期间恢复日期 */
	private Date ReStartDate;
	/** 保单状态 */
	private String Flag;
	/** 保单生效日 */
	private Date CValiDate;
	/** 保单失效日 */
	private Date EndDate;
	/** 备用标志1 */
	private String StandFlag1;
	/** 备用标志2 */
	private String StandFlag2;
	/** 备用标志3 */
	private String StandFlag3;
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

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpContStopEdorStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "GrpContNo";
		pk[1] = "GrpPolNo";
		pk[2] = "StopEdorNo";

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
		LCGrpContStopEdorStateSchema cloned = (LCGrpContStopEdorStateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("团体险种保单号GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	public String getStopEdorNo()
	{
		return StopEdorNo;
	}
	public void setStopEdorNo(String aStopEdorNo)
	{
		if(aStopEdorNo!=null && aStopEdorNo.length()>20)
			throw new IllegalArgumentException("保险期间中断保全号StopEdorNo值"+aStopEdorNo+"的长度"+aStopEdorNo.length()+"大于最大值20");
		StopEdorNo = aStopEdorNo;
	}
	public String getReStartEdorNo()
	{
		return ReStartEdorNo;
	}
	public void setReStartEdorNo(String aReStartEdorNo)
	{
		if(aReStartEdorNo!=null && aReStartEdorNo.length()>20)
			throw new IllegalArgumentException("保险期间恢复保全号ReStartEdorNo值"+aReStartEdorNo+"的长度"+aReStartEdorNo.length()+"大于最大值20");
		ReStartEdorNo = aReStartEdorNo;
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

	public String getReStartDate()
	{
		if( ReStartDate != null )
			return fDate.getString(ReStartDate);
		else
			return null;
	}
	public void setReStartDate(Date aReStartDate)
	{
		ReStartDate = aReStartDate;
	}
	public void setReStartDate(String aReStartDate)
	{
		if (aReStartDate != null && !aReStartDate.equals("") )
		{
			ReStartDate = fDate.getDate( aReStartDate );
		}
		else
			ReStartDate = null;
	}

	/**
	* 0：保险期间被中断 1：保险期间已恢复
	*/
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		if(aFlag!=null && aFlag.length()>1)
			throw new IllegalArgumentException("保单状态Flag值"+aFlag+"的长度"+aFlag.length()+"大于最大值1");
		Flag = aFlag;
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

	public String getStandFlag1()
	{
		return StandFlag1;
	}
	public void setStandFlag1(String aStandFlag1)
	{
		if(aStandFlag1!=null && aStandFlag1.length()>24)
			throw new IllegalArgumentException("备用标志1StandFlag1值"+aStandFlag1+"的长度"+aStandFlag1.length()+"大于最大值24");
		StandFlag1 = aStandFlag1;
	}
	public String getStandFlag2()
	{
		return StandFlag2;
	}
	public void setStandFlag2(String aStandFlag2)
	{
		if(aStandFlag2!=null && aStandFlag2.length()>24)
			throw new IllegalArgumentException("备用标志2StandFlag2值"+aStandFlag2+"的长度"+aStandFlag2.length()+"大于最大值24");
		StandFlag2 = aStandFlag2;
	}
	public String getStandFlag3()
	{
		return StandFlag3;
	}
	public void setStandFlag3(String aStandFlag3)
	{
		if(aStandFlag3!=null && aStandFlag3.length()>24)
			throw new IllegalArgumentException("备用标志3StandFlag3值"+aStandFlag3+"的长度"+aStandFlag3.length()+"大于最大值24");
		StandFlag3 = aStandFlag3;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LCGrpContStopEdorStateSchema 对象给 Schema 赋值
	* @param: aLCGrpContStopEdorStateSchema LCGrpContStopEdorStateSchema
	**/
	public void setSchema(LCGrpContStopEdorStateSchema aLCGrpContStopEdorStateSchema)
	{
		this.GrpContNo = aLCGrpContStopEdorStateSchema.getGrpContNo();
		this.GrpPolNo = aLCGrpContStopEdorStateSchema.getGrpPolNo();
		this.RiskCode = aLCGrpContStopEdorStateSchema.getRiskCode();
		this.StopEdorNo = aLCGrpContStopEdorStateSchema.getStopEdorNo();
		this.ReStartEdorNo = aLCGrpContStopEdorStateSchema.getReStartEdorNo();
		this.StopDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getStopDate());
		this.ReStartDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getReStartDate());
		this.Flag = aLCGrpContStopEdorStateSchema.getFlag();
		this.CValiDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getCValiDate());
		this.EndDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getEndDate());
		this.StandFlag1 = aLCGrpContStopEdorStateSchema.getStandFlag1();
		this.StandFlag2 = aLCGrpContStopEdorStateSchema.getStandFlag2();
		this.StandFlag3 = aLCGrpContStopEdorStateSchema.getStandFlag3();
		this.Operator = aLCGrpContStopEdorStateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getMakeDate());
		this.MakeTime = aLCGrpContStopEdorStateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpContStopEdorStateSchema.getModifyDate());
		this.ModifyTime = aLCGrpContStopEdorStateSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("StopEdorNo") == null )
				this.StopEdorNo = null;
			else
				this.StopEdorNo = rs.getString("StopEdorNo").trim();

			if( rs.getString("ReStartEdorNo") == null )
				this.ReStartEdorNo = null;
			else
				this.ReStartEdorNo = rs.getString("ReStartEdorNo").trim();

			this.StopDate = rs.getDate("StopDate");
			this.ReStartDate = rs.getDate("ReStartDate");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			this.CValiDate = rs.getDate("CValiDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("StandFlag1") == null )
				this.StandFlag1 = null;
			else
				this.StandFlag1 = rs.getString("StandFlag1").trim();

			if( rs.getString("StandFlag2") == null )
				this.StandFlag2 = null;
			else
				this.StandFlag2 = rs.getString("StandFlag2").trim();

			if( rs.getString("StandFlag3") == null )
				this.StandFlag3 = null;
			else
				this.StandFlag3 = rs.getString("StandFlag3").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpContStopEdorState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContStopEdorStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpContStopEdorStateSchema getSchema()
	{
		LCGrpContStopEdorStateSchema aLCGrpContStopEdorStateSchema = new LCGrpContStopEdorStateSchema();
		aLCGrpContStopEdorStateSchema.setSchema(this);
		return aLCGrpContStopEdorStateSchema;
	}

	public LCGrpContStopEdorStateDB getDB()
	{
		LCGrpContStopEdorStateDB aDBOper = new LCGrpContStopEdorStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContStopEdorState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StopEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReStartEdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StopDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReStartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpContStopEdorState>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StopEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReStartEdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StopDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ReStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			StandFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpContStopEdorStateSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("StopEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StopEdorNo));
		}
		if (FCode.equalsIgnoreCase("ReStartEdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReStartEdorNo));
		}
		if (FCode.equalsIgnoreCase("StopDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
		}
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("CValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag1));
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag2));
		}
		if (FCode.equalsIgnoreCase("StandFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag3));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(StopEdorNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReStartEdorNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStopDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReStartDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCValiDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandFlag1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandFlag2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandFlag3);
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("StopEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StopEdorNo = FValue.trim();
			}
			else
				StopEdorNo = null;
		}
		if (FCode.equalsIgnoreCase("ReStartEdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReStartEdorNo = FValue.trim();
			}
			else
				ReStartEdorNo = null;
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
		if (FCode.equalsIgnoreCase("ReStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReStartDate = fDate.getDate( FValue );
			}
			else
				ReStartDate = null;
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
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
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag1 = FValue.trim();
			}
			else
				StandFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag2 = FValue.trim();
			}
			else
				StandFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag3 = FValue.trim();
			}
			else
				StandFlag3 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpContStopEdorStateSchema other = (LCGrpContStopEdorStateSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& RiskCode.equals(other.getRiskCode())
			&& StopEdorNo.equals(other.getStopEdorNo())
			&& ReStartEdorNo.equals(other.getReStartEdorNo())
			&& fDate.getString(StopDate).equals(other.getStopDate())
			&& fDate.getString(ReStartDate).equals(other.getReStartDate())
			&& Flag.equals(other.getFlag())
			&& fDate.getString(CValiDate).equals(other.getCValiDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& StandFlag1.equals(other.getStandFlag1())
			&& StandFlag2.equals(other.getStandFlag2())
			&& StandFlag3.equals(other.getStandFlag3())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("StopEdorNo") ) {
			return 3;
		}
		if( strFieldName.equals("ReStartEdorNo") ) {
			return 4;
		}
		if( strFieldName.equals("StopDate") ) {
			return 5;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return 6;
		}
		if( strFieldName.equals("Flag") ) {
			return 7;
		}
		if( strFieldName.equals("CValiDate") ) {
			return 8;
		}
		if( strFieldName.equals("EndDate") ) {
			return 9;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return 10;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return 11;
		}
		if( strFieldName.equals("StandFlag3") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "StopEdorNo";
				break;
			case 4:
				strFieldName = "ReStartEdorNo";
				break;
			case 5:
				strFieldName = "StopDate";
				break;
			case 6:
				strFieldName = "ReStartDate";
				break;
			case 7:
				strFieldName = "Flag";
				break;
			case 8:
				strFieldName = "CValiDate";
				break;
			case 9:
				strFieldName = "EndDate";
				break;
			case 10:
				strFieldName = "StandFlag1";
				break;
			case 11:
				strFieldName = "StandFlag2";
				break;
			case 12:
				strFieldName = "StandFlag3";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReStartEdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StopDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StandFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag3") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
