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
import com.sinosoft.lis.db.PD_LMEdorZTDB;

/*
 * <p>ClassName: PD_LMEdorZTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台
 */
public class PD_LMEdorZTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMEdorZTSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 期交是否允许通融退保 */
	private String CycFlag;
	/** 期交退保控制范围类型 */
	private String CycType;
	/** 期交退保控制范围，起点 */
	private int CycStart;
	/** 期交退保控制范围，终点 */
	private int CycEnd;
	/** 趸交是否允许通融退保 */
	private String OnePayFlag;
	/** 趸交退保控制范围类型 */
	private String OnePayType;
	/** 趸交退保控制范围起点 */
	private int OnePayStart;
	/** 趸交退保控制范围终点 */
	private int OnePayEnd;
	/** 补/退费财务类型 */
	private String FeeFinaType;
	/** 加费是否退费标记 */
	private String AddFeeBackFlag;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMEdorZTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		PD_LMEdorZTSchema cloned = (PD_LMEdorZTSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>8)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值8");
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		if(aRiskVersion!=null && aRiskVersion.length()>8)
			throw new IllegalArgumentException("险种版本RiskVersion值"+aRiskVersion+"的长度"+aRiskVersion.length()+"大于最大值8");
		RiskVersion = aRiskVersion;
	}
	/**
	* 1--是 0--否
	*/
	public String getCycFlag()
	{
		return CycFlag;
	}
	public void setCycFlag(String aCycFlag)
	{
		if(aCycFlag!=null && aCycFlag.length()>1)
			throw new IllegalArgumentException("期交是否允许通融退保CycFlag值"+aCycFlag+"的长度"+aCycFlag.length()+"大于最大值1");
		CycFlag = aCycFlag;
	}
	/**
	* Y--年 M--月 D--日
	*/
	public String getCycType()
	{
		return CycType;
	}
	public void setCycType(String aCycType)
	{
		if(aCycType!=null && aCycType.length()>5)
			throw new IllegalArgumentException("期交退保控制范围类型CycType值"+aCycType+"的长度"+aCycType.length()+"大于最大值5");
		CycType = aCycType;
	}
	/**
	* 和期交???保控制范围类型搭配
	*/
	public int getCycStart()
	{
		return CycStart;
	}
	public void setCycStart(int aCycStart)
	{
		CycStart = aCycStart;
	}
	public void setCycStart(String aCycStart)
	{
		if (aCycStart != null && !aCycStart.equals(""))
		{
			Integer tInteger = new Integer(aCycStart);
			int i = tInteger.intValue();
			CycStart = i;
		}
	}

	public int getCycEnd()
	{
		return CycEnd;
	}
	public void setCycEnd(int aCycEnd)
	{
		CycEnd = aCycEnd;
	}
	public void setCycEnd(String aCycEnd)
	{
		if (aCycEnd != null && !aCycEnd.equals(""))
		{
			Integer tInteger = new Integer(aCycEnd);
			int i = tInteger.intValue();
			CycEnd = i;
		}
	}

	/**
	* 1--是 0--否
	*/
	public String getOnePayFlag()
	{
		return OnePayFlag;
	}
	public void setOnePayFlag(String aOnePayFlag)
	{
		if(aOnePayFlag!=null && aOnePayFlag.length()>1)
			throw new IllegalArgumentException("趸交是否允许通融退保OnePayFlag值"+aOnePayFlag+"的长度"+aOnePayFlag.length()+"大于最大值1");
		OnePayFlag = aOnePayFlag;
	}
	/**
	* Y--年 M--月 D--日
	*/
	public String getOnePayType()
	{
		return OnePayType;
	}
	public void setOnePayType(String aOnePayType)
	{
		if(aOnePayType!=null && aOnePayType.length()>5)
			throw new IllegalArgumentException("趸交退保控制范围类型OnePayType值"+aOnePayType+"的长度"+aOnePayType.length()+"大于最大值5");
		OnePayType = aOnePayType;
	}
	/**
	* Y--年 M--月 D--日
	*/
	public int getOnePayStart()
	{
		return OnePayStart;
	}
	public void setOnePayStart(int aOnePayStart)
	{
		OnePayStart = aOnePayStart;
	}
	public void setOnePayStart(String aOnePayStart)
	{
		if (aOnePayStart != null && !aOnePayStart.equals(""))
		{
			Integer tInteger = new Integer(aOnePayStart);
			int i = tInteger.intValue();
			OnePayStart = i;
		}
	}

	/**
	* 和趸交退保控制范围类型搭配
	*/
	public int getOnePayEnd()
	{
		return OnePayEnd;
	}
	public void setOnePayEnd(int aOnePayEnd)
	{
		OnePayEnd = aOnePayEnd;
	}
	public void setOnePayEnd(String aOnePayEnd)
	{
		if (aOnePayEnd != null && !aOnePayEnd.equals(""))
		{
			Integer tInteger = new Integer(aOnePayEnd);
			int i = tInteger.intValue();
			OnePayEnd = i;
		}
	}

	/**
	* 该字段根据不同的退费类型填写(可能根据不同公司的要求,编写不同的财务类型) 现有的类型有： TB--表示正常的退保 TF--表示正常退保导致的退费 *
	*/
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		if(aFeeFinaType!=null && aFeeFinaType.length()>6)
			throw new IllegalArgumentException("补/退费财务类型FeeFinaType值"+aFeeFinaType+"的长度"+aFeeFinaType.length()+"大于最大值6");
		FeeFinaType = aFeeFinaType;
	}
	/**
	* 00--不退还加费 10--退还投保人健康加费 20--退还保人职业加费 等 第一位表示是否退还投保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有) 第二位表示是否退还被保人加费(0-无,1-健康加费,2-职业加费,3-两种加费都有)
	*/
	public String getAddFeeBackFlag()
	{
		return AddFeeBackFlag;
	}
	public void setAddFeeBackFlag(String aAddFeeBackFlag)
	{
		if(aAddFeeBackFlag!=null && aAddFeeBackFlag.length()>2)
			throw new IllegalArgumentException("加费是否退费标记AddFeeBackFlag值"+aAddFeeBackFlag+"的长度"+aAddFeeBackFlag.length()+"大于最大值2");
		AddFeeBackFlag = aAddFeeBackFlag;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		if(aStandbyflag1!=null && aStandbyflag1.length()>20)
			throw new IllegalArgumentException("Standbyflag1Standbyflag1值"+aStandbyflag1+"的长度"+aStandbyflag1.length()+"大于最大值20");
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		if(aStandbyflag2!=null && aStandbyflag2.length()>20)
			throw new IllegalArgumentException("Standbyflag2Standbyflag2值"+aStandbyflag2+"的长度"+aStandbyflag2.length()+"大于最大值20");
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}


	/**
	* 使用另外一个 PD_LMEdorZTSchema 对象给 Schema 赋值
	* @param: aPD_LMEdorZTSchema PD_LMEdorZTSchema
	**/
	public void setSchema(PD_LMEdorZTSchema aPD_LMEdorZTSchema)
	{
		this.RiskCode = aPD_LMEdorZTSchema.getRiskCode();
		this.RiskVersion = aPD_LMEdorZTSchema.getRiskVersion();
		this.CycFlag = aPD_LMEdorZTSchema.getCycFlag();
		this.CycType = aPD_LMEdorZTSchema.getCycType();
		this.CycStart = aPD_LMEdorZTSchema.getCycStart();
		this.CycEnd = aPD_LMEdorZTSchema.getCycEnd();
		this.OnePayFlag = aPD_LMEdorZTSchema.getOnePayFlag();
		this.OnePayType = aPD_LMEdorZTSchema.getOnePayType();
		this.OnePayStart = aPD_LMEdorZTSchema.getOnePayStart();
		this.OnePayEnd = aPD_LMEdorZTSchema.getOnePayEnd();
		this.FeeFinaType = aPD_LMEdorZTSchema.getFeeFinaType();
		this.AddFeeBackFlag = aPD_LMEdorZTSchema.getAddFeeBackFlag();
		this.Operator = aPD_LMEdorZTSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMEdorZTSchema.getMakeDate());
		this.MakeTime = aPD_LMEdorZTSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMEdorZTSchema.getModifyDate());
		this.ModifyTime = aPD_LMEdorZTSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMEdorZTSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMEdorZTSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMEdorZTSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMEdorZTSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMEdorZTSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMEdorZTSchema.getStandbyflag6();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("CycFlag") == null )
				this.CycFlag = null;
			else
				this.CycFlag = rs.getString("CycFlag").trim();

			if( rs.getString("CycType") == null )
				this.CycType = null;
			else
				this.CycType = rs.getString("CycType").trim();

			this.CycStart = rs.getInt("CycStart");
			this.CycEnd = rs.getInt("CycEnd");
			if( rs.getString("OnePayFlag") == null )
				this.OnePayFlag = null;
			else
				this.OnePayFlag = rs.getString("OnePayFlag").trim();

			if( rs.getString("OnePayType") == null )
				this.OnePayType = null;
			else
				this.OnePayType = rs.getString("OnePayType").trim();

			this.OnePayStart = rs.getInt("OnePayStart");
			this.OnePayEnd = rs.getInt("OnePayEnd");
			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("AddFeeBackFlag") == null )
				this.AddFeeBackFlag = null;
			else
				this.AddFeeBackFlag = rs.getString("AddFeeBackFlag").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMEdorZT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMEdorZTSchema getSchema()
	{
		PD_LMEdorZTSchema aPD_LMEdorZTSchema = new PD_LMEdorZTSchema();
		aPD_LMEdorZTSchema.setSchema(this);
		return aPD_LMEdorZTSchema;
	}

	public PD_LMEdorZTDB getDB()
	{
		PD_LMEdorZTDB aDBOper = new PD_LMEdorZTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorZT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CycStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CycEnd));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnePayStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnePayEnd));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeBackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMEdorZT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CycFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CycType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CycStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			CycEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			OnePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OnePayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OnePayStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			OnePayEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AddFeeBackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,23,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMEdorZTSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("CycFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycFlag));
		}
		if (FCode.equalsIgnoreCase("CycType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycType));
		}
		if (FCode.equalsIgnoreCase("CycStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycStart));
		}
		if (FCode.equalsIgnoreCase("CycEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycEnd));
		}
		if (FCode.equalsIgnoreCase("OnePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayFlag));
		}
		if (FCode.equalsIgnoreCase("OnePayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayType));
		}
		if (FCode.equalsIgnoreCase("OnePayStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayStart));
		}
		if (FCode.equalsIgnoreCase("OnePayEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayEnd));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("AddFeeBackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeBackFlag));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CycFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CycType);
				break;
			case 4:
				strFieldValue = String.valueOf(CycStart);
				break;
			case 5:
				strFieldValue = String.valueOf(CycEnd);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OnePayFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OnePayType);
				break;
			case 8:
				strFieldValue = String.valueOf(OnePayStart);
				break;
			case 9:
				strFieldValue = String.valueOf(OnePayEnd);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AddFeeBackFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 19:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 20:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 21:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 22:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("CycFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycFlag = FValue.trim();
			}
			else
				CycFlag = null;
		}
		if (FCode.equalsIgnoreCase("CycType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycType = FValue.trim();
			}
			else
				CycType = null;
		}
		if (FCode.equalsIgnoreCase("CycStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CycStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("CycEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CycEnd = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayFlag = FValue.trim();
			}
			else
				OnePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("OnePayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayType = FValue.trim();
			}
			else
				OnePayType = null;
		}
		if (FCode.equalsIgnoreCase("OnePayStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnePayStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnePayEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnePayEnd = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeBackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeBackFlag = FValue.trim();
			}
			else
				AddFeeBackFlag = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMEdorZTSchema other = (PD_LMEdorZTSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& CycFlag.equals(other.getCycFlag())
			&& CycType.equals(other.getCycType())
			&& CycStart == other.getCycStart()
			&& CycEnd == other.getCycEnd()
			&& OnePayFlag.equals(other.getOnePayFlag())
			&& OnePayType.equals(other.getOnePayType())
			&& OnePayStart == other.getOnePayStart()
			&& OnePayEnd == other.getOnePayEnd()
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& AddFeeBackFlag.equals(other.getAddFeeBackFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 1;
		}
		if( strFieldName.equals("CycFlag") ) {
			return 2;
		}
		if( strFieldName.equals("CycType") ) {
			return 3;
		}
		if( strFieldName.equals("CycStart") ) {
			return 4;
		}
		if( strFieldName.equals("CycEnd") ) {
			return 5;
		}
		if( strFieldName.equals("OnePayFlag") ) {
			return 6;
		}
		if( strFieldName.equals("OnePayType") ) {
			return 7;
		}
		if( strFieldName.equals("OnePayStart") ) {
			return 8;
		}
		if( strFieldName.equals("OnePayEnd") ) {
			return 9;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 10;
		}
		if( strFieldName.equals("AddFeeBackFlag") ) {
			return 11;
		}
		if( strFieldName.equals("Operator") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 18;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 19;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 20;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 21;
		}
		if( strFieldName.equals("Standbyflag6") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVersion";
				break;
			case 2:
				strFieldName = "CycFlag";
				break;
			case 3:
				strFieldName = "CycType";
				break;
			case 4:
				strFieldName = "CycStart";
				break;
			case 5:
				strFieldName = "CycEnd";
				break;
			case 6:
				strFieldName = "OnePayFlag";
				break;
			case 7:
				strFieldName = "OnePayType";
				break;
			case 8:
				strFieldName = "OnePayStart";
				break;
			case 9:
				strFieldName = "OnePayEnd";
				break;
			case 10:
				strFieldName = "FeeFinaType";
				break;
			case 11:
				strFieldName = "AddFeeBackFlag";
				break;
			case 12:
				strFieldName = "Operator";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "Standbyflag1";
				break;
			case 18:
				strFieldName = "Standbyflag2";
				break;
			case 19:
				strFieldName = "Standbyflag3";
				break;
			case 20:
				strFieldName = "Standbyflag4";
				break;
			case 21:
				strFieldName = "Standbyflag5";
				break;
			case 22:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CycEnd") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnePayEnd") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeBackFlag") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_INT;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
