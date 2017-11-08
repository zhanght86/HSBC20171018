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
import com.sinosoft.lis.db.LDUWPopedomDB;

/*
 * <p>ClassName: LDUWPopedomSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LDUWPopedomSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWPopedomSchema.class);
	// @Field
	/** 权限级别 */
	private String PopedomLevel;
	/** 权限名称 */
	private String PopedomName;
	/** 个人寿险保额 */
	private double PerLifeAmnt;
	/** 个人意外险保额 */
	private double PerAcciAmnt;
	/** 个人重疾保额 */
	private double PerIllAmnt;
	/** 个人医疗险保额 */
	private double PerMedAmnt;
	/** 保费规模 */
	private double PremScale;
	/** 主险费率浮动 */
	private double MainPremRateFloat;
	/** 医疗险费率浮动 */
	private double MedPremRateFloat;
	/** 生效日期 */
	private Date ValDate;
	/** 终止日期 */
	private Date EndDate;
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
	/** 寿险意外险费率浮动 */
	private double LifePremRateFloat;
	/** 重疾险费率浮动 */
	private double IllnessPremRateFloat;
	/** 医疗费率浮动 */
	private double MedicalPremRateFloat;
	/** 建工险费率浮动 */
	private double EnginPremRateFloat;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWPopedomSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PopedomLevel";

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
		LDUWPopedomSchema cloned = (LDUWPopedomSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPopedomLevel()
	{
		return PopedomLevel;
	}
	public void setPopedomLevel(String aPopedomLevel)
	{
		if(aPopedomLevel!=null && aPopedomLevel.length()>6)
			throw new IllegalArgumentException("权限级别PopedomLevel值"+aPopedomLevel+"的长度"+aPopedomLevel.length()+"大于最大值6");
		PopedomLevel = aPopedomLevel;
	}
	public String getPopedomName()
	{
		return PopedomName;
	}
	public void setPopedomName(String aPopedomName)
	{
		if(aPopedomName!=null && aPopedomName.length()>100)
			throw new IllegalArgumentException("权限名称PopedomName值"+aPopedomName+"的长度"+aPopedomName.length()+"大于最大值100");
		PopedomName = aPopedomName;
	}
	public double getPerLifeAmnt()
	{
		return PerLifeAmnt;
	}
	public void setPerLifeAmnt(double aPerLifeAmnt)
	{
		PerLifeAmnt = aPerLifeAmnt;
	}
	public void setPerLifeAmnt(String aPerLifeAmnt)
	{
		if (aPerLifeAmnt != null && !aPerLifeAmnt.equals(""))
		{
			Double tDouble = new Double(aPerLifeAmnt);
			double d = tDouble.doubleValue();
			PerLifeAmnt = d;
		}
	}

	public double getPerAcciAmnt()
	{
		return PerAcciAmnt;
	}
	public void setPerAcciAmnt(double aPerAcciAmnt)
	{
		PerAcciAmnt = aPerAcciAmnt;
	}
	public void setPerAcciAmnt(String aPerAcciAmnt)
	{
		if (aPerAcciAmnt != null && !aPerAcciAmnt.equals(""))
		{
			Double tDouble = new Double(aPerAcciAmnt);
			double d = tDouble.doubleValue();
			PerAcciAmnt = d;
		}
	}

	public double getPerIllAmnt()
	{
		return PerIllAmnt;
	}
	public void setPerIllAmnt(double aPerIllAmnt)
	{
		PerIllAmnt = aPerIllAmnt;
	}
	public void setPerIllAmnt(String aPerIllAmnt)
	{
		if (aPerIllAmnt != null && !aPerIllAmnt.equals(""))
		{
			Double tDouble = new Double(aPerIllAmnt);
			double d = tDouble.doubleValue();
			PerIllAmnt = d;
		}
	}

	public double getPerMedAmnt()
	{
		return PerMedAmnt;
	}
	public void setPerMedAmnt(double aPerMedAmnt)
	{
		PerMedAmnt = aPerMedAmnt;
	}
	public void setPerMedAmnt(String aPerMedAmnt)
	{
		if (aPerMedAmnt != null && !aPerMedAmnt.equals(""))
		{
			Double tDouble = new Double(aPerMedAmnt);
			double d = tDouble.doubleValue();
			PerMedAmnt = d;
		}
	}

	public double getPremScale()
	{
		return PremScale;
	}
	public void setPremScale(double aPremScale)
	{
		PremScale = aPremScale;
	}
	public void setPremScale(String aPremScale)
	{
		if (aPremScale != null && !aPremScale.equals(""))
		{
			Double tDouble = new Double(aPremScale);
			double d = tDouble.doubleValue();
			PremScale = d;
		}
	}

	public double getMainPremRateFloat()
	{
		return MainPremRateFloat;
	}
	public void setMainPremRateFloat(double aMainPremRateFloat)
	{
		MainPremRateFloat = aMainPremRateFloat;
	}
	public void setMainPremRateFloat(String aMainPremRateFloat)
	{
		if (aMainPremRateFloat != null && !aMainPremRateFloat.equals(""))
		{
			Double tDouble = new Double(aMainPremRateFloat);
			double d = tDouble.doubleValue();
			MainPremRateFloat = d;
		}
	}

	public double getMedPremRateFloat()
	{
		return MedPremRateFloat;
	}
	public void setMedPremRateFloat(double aMedPremRateFloat)
	{
		MedPremRateFloat = aMedPremRateFloat;
	}
	public void setMedPremRateFloat(String aMedPremRateFloat)
	{
		if (aMedPremRateFloat != null && !aMedPremRateFloat.equals(""))
		{
			Double tDouble = new Double(aMedPremRateFloat);
			double d = tDouble.doubleValue();
			MedPremRateFloat = d;
		}
	}

	public String getValDate()
	{
		if( ValDate != null )
			return fDate.getString(ValDate);
		else
			return null;
	}
	public void setValDate(Date aValDate)
	{
		ValDate = aValDate;
	}
	public void setValDate(String aValDate)
	{
		if (aValDate != null && !aValDate.equals("") )
		{
			ValDate = fDate.getDate( aValDate );
		}
		else
			ValDate = null;
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
	public double getLifePremRateFloat()
	{
		return LifePremRateFloat;
	}
	public void setLifePremRateFloat(double aLifePremRateFloat)
	{
		LifePremRateFloat = aLifePremRateFloat;
	}
	public void setLifePremRateFloat(String aLifePremRateFloat)
	{
		if (aLifePremRateFloat != null && !aLifePremRateFloat.equals(""))
		{
			Double tDouble = new Double(aLifePremRateFloat);
			double d = tDouble.doubleValue();
			LifePremRateFloat = d;
		}
	}

	public double getIllnessPremRateFloat()
	{
		return IllnessPremRateFloat;
	}
	public void setIllnessPremRateFloat(double aIllnessPremRateFloat)
	{
		IllnessPremRateFloat = aIllnessPremRateFloat;
	}
	public void setIllnessPremRateFloat(String aIllnessPremRateFloat)
	{
		if (aIllnessPremRateFloat != null && !aIllnessPremRateFloat.equals(""))
		{
			Double tDouble = new Double(aIllnessPremRateFloat);
			double d = tDouble.doubleValue();
			IllnessPremRateFloat = d;
		}
	}

	public double getMedicalPremRateFloat()
	{
		return MedicalPremRateFloat;
	}
	public void setMedicalPremRateFloat(double aMedicalPremRateFloat)
	{
		MedicalPremRateFloat = aMedicalPremRateFloat;
	}
	public void setMedicalPremRateFloat(String aMedicalPremRateFloat)
	{
		if (aMedicalPremRateFloat != null && !aMedicalPremRateFloat.equals(""))
		{
			Double tDouble = new Double(aMedicalPremRateFloat);
			double d = tDouble.doubleValue();
			MedicalPremRateFloat = d;
		}
	}

	public double getEnginPremRateFloat()
	{
		return EnginPremRateFloat;
	}
	public void setEnginPremRateFloat(double aEnginPremRateFloat)
	{
		EnginPremRateFloat = aEnginPremRateFloat;
	}
	public void setEnginPremRateFloat(String aEnginPremRateFloat)
	{
		if (aEnginPremRateFloat != null && !aEnginPremRateFloat.equals(""))
		{
			Double tDouble = new Double(aEnginPremRateFloat);
			double d = tDouble.doubleValue();
			EnginPremRateFloat = d;
		}
	}


	/**
	* 使用另外一个 LDUWPopedomSchema 对象给 Schema 赋值
	* @param: aLDUWPopedomSchema LDUWPopedomSchema
	**/
	public void setSchema(LDUWPopedomSchema aLDUWPopedomSchema)
	{
		this.PopedomLevel = aLDUWPopedomSchema.getPopedomLevel();
		this.PopedomName = aLDUWPopedomSchema.getPopedomName();
		this.PerLifeAmnt = aLDUWPopedomSchema.getPerLifeAmnt();
		this.PerAcciAmnt = aLDUWPopedomSchema.getPerAcciAmnt();
		this.PerIllAmnt = aLDUWPopedomSchema.getPerIllAmnt();
		this.PerMedAmnt = aLDUWPopedomSchema.getPerMedAmnt();
		this.PremScale = aLDUWPopedomSchema.getPremScale();
		this.MainPremRateFloat = aLDUWPopedomSchema.getMainPremRateFloat();
		this.MedPremRateFloat = aLDUWPopedomSchema.getMedPremRateFloat();
		this.ValDate = fDate.getDate( aLDUWPopedomSchema.getValDate());
		this.EndDate = fDate.getDate( aLDUWPopedomSchema.getEndDate());
		this.MakeOperator = aLDUWPopedomSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLDUWPopedomSchema.getMakeDate());
		this.MakeTime = aLDUWPopedomSchema.getMakeTime();
		this.ModifyOperator = aLDUWPopedomSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLDUWPopedomSchema.getModifyDate());
		this.ModifyTime = aLDUWPopedomSchema.getModifyTime();
		this.LifePremRateFloat = aLDUWPopedomSchema.getLifePremRateFloat();
		this.IllnessPremRateFloat = aLDUWPopedomSchema.getIllnessPremRateFloat();
		this.MedicalPremRateFloat = aLDUWPopedomSchema.getMedicalPremRateFloat();
		this.EnginPremRateFloat = aLDUWPopedomSchema.getEnginPremRateFloat();
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
			if( rs.getString("PopedomLevel") == null )
				this.PopedomLevel = null;
			else
				this.PopedomLevel = rs.getString("PopedomLevel").trim();

			if( rs.getString("PopedomName") == null )
				this.PopedomName = null;
			else
				this.PopedomName = rs.getString("PopedomName").trim();

			this.PerLifeAmnt = rs.getDouble("PerLifeAmnt");
			this.PerAcciAmnt = rs.getDouble("PerAcciAmnt");
			this.PerIllAmnt = rs.getDouble("PerIllAmnt");
			this.PerMedAmnt = rs.getDouble("PerMedAmnt");
			this.PremScale = rs.getDouble("PremScale");
			this.MainPremRateFloat = rs.getDouble("MainPremRateFloat");
			this.MedPremRateFloat = rs.getDouble("MedPremRateFloat");
			this.ValDate = rs.getDate("ValDate");
			this.EndDate = rs.getDate("EndDate");
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

			this.LifePremRateFloat = rs.getDouble("LifePremRateFloat");
			this.IllnessPremRateFloat = rs.getDouble("IllnessPremRateFloat");
			this.MedicalPremRateFloat = rs.getDouble("MedicalPremRateFloat");
			this.EnginPremRateFloat = rs.getDouble("EnginPremRateFloat");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWPopedom表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWPopedomSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWPopedomSchema getSchema()
	{
		LDUWPopedomSchema aLDUWPopedomSchema = new LDUWPopedomSchema();
		aLDUWPopedomSchema.setSchema(this);
		return aLDUWPopedomSchema;
	}

	public LDUWPopedomDB getDB()
	{
		LDUWPopedomDB aDBOper = new LDUWPopedomDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWPopedom描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PopedomLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PopedomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PerLifeAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PerAcciAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PerIllAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PerMedAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremScale));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MainPremRateFloat));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MedPremRateFloat));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ValDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LifePremRateFloat));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(IllnessPremRateFloat));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MedicalPremRateFloat));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EnginPremRateFloat));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWPopedom>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PopedomLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PopedomName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PerLifeAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			PerAcciAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			PerIllAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			PerMedAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			PremScale = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			MainPremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			MedPremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			ValDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			LifePremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			IllnessPremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			MedicalPremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			EnginPremRateFloat = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWPopedomSchema";
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
		if (FCode.equalsIgnoreCase("PopedomLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomLevel));
		}
		if (FCode.equalsIgnoreCase("PopedomName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopedomName));
		}
		if (FCode.equalsIgnoreCase("PerLifeAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerLifeAmnt));
		}
		if (FCode.equalsIgnoreCase("PerAcciAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerAcciAmnt));
		}
		if (FCode.equalsIgnoreCase("PerIllAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerIllAmnt));
		}
		if (FCode.equalsIgnoreCase("PerMedAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PerMedAmnt));
		}
		if (FCode.equalsIgnoreCase("PremScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremScale));
		}
		if (FCode.equalsIgnoreCase("MainPremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainPremRateFloat));
		}
		if (FCode.equalsIgnoreCase("MedPremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedPremRateFloat));
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
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
		if (FCode.equalsIgnoreCase("LifePremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LifePremRateFloat));
		}
		if (FCode.equalsIgnoreCase("IllnessPremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IllnessPremRateFloat));
		}
		if (FCode.equalsIgnoreCase("MedicalPremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MedicalPremRateFloat));
		}
		if (FCode.equalsIgnoreCase("EnginPremRateFloat"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginPremRateFloat));
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
				strFieldValue = StrTool.GBKToUnicode(PopedomLevel);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PopedomName);
				break;
			case 2:
				strFieldValue = String.valueOf(PerLifeAmnt);
				break;
			case 3:
				strFieldValue = String.valueOf(PerAcciAmnt);
				break;
			case 4:
				strFieldValue = String.valueOf(PerIllAmnt);
				break;
			case 5:
				strFieldValue = String.valueOf(PerMedAmnt);
				break;
			case 6:
				strFieldValue = String.valueOf(PremScale);
				break;
			case 7:
				strFieldValue = String.valueOf(MainPremRateFloat);
				break;
			case 8:
				strFieldValue = String.valueOf(MedPremRateFloat);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = String.valueOf(LifePremRateFloat);
				break;
			case 18:
				strFieldValue = String.valueOf(IllnessPremRateFloat);
				break;
			case 19:
				strFieldValue = String.valueOf(MedicalPremRateFloat);
				break;
			case 20:
				strFieldValue = String.valueOf(EnginPremRateFloat);
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

		if (FCode.equalsIgnoreCase("PopedomLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PopedomLevel = FValue.trim();
			}
			else
				PopedomLevel = null;
		}
		if (FCode.equalsIgnoreCase("PopedomName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PopedomName = FValue.trim();
			}
			else
				PopedomName = null;
		}
		if (FCode.equalsIgnoreCase("PerLifeAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PerLifeAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PerAcciAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PerAcciAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PerIllAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PerIllAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PerMedAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PerMedAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremScale = d;
			}
		}
		if (FCode.equalsIgnoreCase("MainPremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MainPremRateFloat = d;
			}
		}
		if (FCode.equalsIgnoreCase("MedPremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MedPremRateFloat = d;
			}
		}
		if (FCode.equalsIgnoreCase("ValDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValDate = fDate.getDate( FValue );
			}
			else
				ValDate = null;
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
		if (FCode.equalsIgnoreCase("LifePremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LifePremRateFloat = d;
			}
		}
		if (FCode.equalsIgnoreCase("IllnessPremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				IllnessPremRateFloat = d;
			}
		}
		if (FCode.equalsIgnoreCase("MedicalPremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MedicalPremRateFloat = d;
			}
		}
		if (FCode.equalsIgnoreCase("EnginPremRateFloat"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EnginPremRateFloat = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWPopedomSchema other = (LDUWPopedomSchema)otherObject;
		return
			PopedomLevel.equals(other.getPopedomLevel())
			&& PopedomName.equals(other.getPopedomName())
			&& PerLifeAmnt == other.getPerLifeAmnt()
			&& PerAcciAmnt == other.getPerAcciAmnt()
			&& PerIllAmnt == other.getPerIllAmnt()
			&& PerMedAmnt == other.getPerMedAmnt()
			&& PremScale == other.getPremScale()
			&& MainPremRateFloat == other.getMainPremRateFloat()
			&& MedPremRateFloat == other.getMedPremRateFloat()
			&& fDate.getString(ValDate).equals(other.getValDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& LifePremRateFloat == other.getLifePremRateFloat()
			&& IllnessPremRateFloat == other.getIllnessPremRateFloat()
			&& MedicalPremRateFloat == other.getMedicalPremRateFloat()
			&& EnginPremRateFloat == other.getEnginPremRateFloat();
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
		if( strFieldName.equals("PopedomLevel") ) {
			return 0;
		}
		if( strFieldName.equals("PopedomName") ) {
			return 1;
		}
		if( strFieldName.equals("PerLifeAmnt") ) {
			return 2;
		}
		if( strFieldName.equals("PerAcciAmnt") ) {
			return 3;
		}
		if( strFieldName.equals("PerIllAmnt") ) {
			return 4;
		}
		if( strFieldName.equals("PerMedAmnt") ) {
			return 5;
		}
		if( strFieldName.equals("PremScale") ) {
			return 6;
		}
		if( strFieldName.equals("MainPremRateFloat") ) {
			return 7;
		}
		if( strFieldName.equals("MedPremRateFloat") ) {
			return 8;
		}
		if( strFieldName.equals("ValDate") ) {
			return 9;
		}
		if( strFieldName.equals("EndDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("LifePremRateFloat") ) {
			return 17;
		}
		if( strFieldName.equals("IllnessPremRateFloat") ) {
			return 18;
		}
		if( strFieldName.equals("MedicalPremRateFloat") ) {
			return 19;
		}
		if( strFieldName.equals("EnginPremRateFloat") ) {
			return 20;
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
				strFieldName = "PopedomLevel";
				break;
			case 1:
				strFieldName = "PopedomName";
				break;
			case 2:
				strFieldName = "PerLifeAmnt";
				break;
			case 3:
				strFieldName = "PerAcciAmnt";
				break;
			case 4:
				strFieldName = "PerIllAmnt";
				break;
			case 5:
				strFieldName = "PerMedAmnt";
				break;
			case 6:
				strFieldName = "PremScale";
				break;
			case 7:
				strFieldName = "MainPremRateFloat";
				break;
			case 8:
				strFieldName = "MedPremRateFloat";
				break;
			case 9:
				strFieldName = "ValDate";
				break;
			case 10:
				strFieldName = "EndDate";
				break;
			case 11:
				strFieldName = "MakeOperator";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyOperator";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "LifePremRateFloat";
				break;
			case 18:
				strFieldName = "IllnessPremRateFloat";
				break;
			case 19:
				strFieldName = "MedicalPremRateFloat";
				break;
			case 20:
				strFieldName = "EnginPremRateFloat";
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
		if( strFieldName.equals("PopedomLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopedomName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PerLifeAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PerAcciAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PerIllAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PerMedAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremScale") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MainPremRateFloat") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MedPremRateFloat") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ValDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
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
		if( strFieldName.equals("LifePremRateFloat") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("IllnessPremRateFloat") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MedicalPremRateFloat") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EnginPremRateFloat") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
