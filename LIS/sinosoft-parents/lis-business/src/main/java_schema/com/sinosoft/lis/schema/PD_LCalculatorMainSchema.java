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
import com.sinosoft.lis.db.PD_LCalculatorMainDB;

/*
 * <p>ClassName: PD_LCalculatorMainSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LCalculatorMainSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LCalculatorMainSchema.class);
	// @Field
	/** 累加器编号 */
	private String CalculatorCode;
	/** 累加器名称 */
	private String CalculatorName;
	/** 累加器层级 */
	private int CtrlLevel;
	/** 累加器类型 */
	private String Type;
	/** 要素类型 */
	private String CtrlFactorType;
	/** 要素值 */
	private double CtrlFactorValue;
	/** 要素单位 */
	private String CtrlFactorUnit;
	/** 要素值计算方式 */
	private String CalMode;
	/** 要素值计算公式 */
	private String CalCode;
	/** 默认值 */
	private double DefaultValue;
	/** 备注1 */
	private String Remark1;
	/** 备注2 */
	private String Remark2;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LCalculatorMainSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CalculatorCode";

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
		PD_LCalculatorMainSchema cloned = (PD_LCalculatorMainSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalculatorCode()
	{
		return CalculatorCode;
	}
	public void setCalculatorCode(String aCalculatorCode)
	{
		if(aCalculatorCode!=null && aCalculatorCode.length()>20)
			throw new IllegalArgumentException("累加器编号CalculatorCode值"+aCalculatorCode+"的长度"+aCalculatorCode.length()+"大于最大值20");
		CalculatorCode = aCalculatorCode;
	}
	public String getCalculatorName()
	{
		return CalculatorName;
	}
	public void setCalculatorName(String aCalculatorName)
	{
		if(aCalculatorName!=null && aCalculatorName.length()>100)
			throw new IllegalArgumentException("累加器名称CalculatorName值"+aCalculatorName+"的长度"+aCalculatorName.length()+"大于最大值100");
		CalculatorName = aCalculatorName;
	}
	/**
	* 控制层次：<p>
	* 1-保障责任（给付责任）<p>
	* 2-责任<p>
	* 3-连生被保人<p>
	* 4-产品<p>
	* 5-被保人（多被保人-家庭单的情况）<p>
	* 6-保单<p>
	* 7-个人（个险系统累加器只包含以上7中层级）<p>
	* 8-计划<p>
	* 9.团单
	*/
	public int getCtrlLevel()
	{
		return CtrlLevel;
	}
	public void setCtrlLevel(int aCtrlLevel)
	{
		CtrlLevel = aCtrlLevel;
	}
	public void setCtrlLevel(String aCtrlLevel)
	{
		if (aCtrlLevel != null && !aCtrlLevel.equals(""))
		{
			Integer tInteger = new Integer(aCtrlLevel);
			int i = tInteger.intValue();
			CtrlLevel = i;
		}
	}

	/**
	* 类型：<p>
	* 1-限额<p>
	* 2-免赔额<p>
	* 3-计算(公式)
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		if(aType!=null && aType.length()>1)
			throw new IllegalArgumentException("累加器类型Type值"+aType+"的长度"+aType.length()+"大于最大值1");
		Type = aType;
	}
	/**
	* 累加要素（类型）：<p>
	* 1-金额<p>
	* 2-天数<p>
	* 3-次数<p>
	* 4-天金额<p>
	* 5-次金额
	*/
	public String getCtrlFactorType()
	{
		return CtrlFactorType;
	}
	public void setCtrlFactorType(String aCtrlFactorType)
	{
		if(aCtrlFactorType!=null && aCtrlFactorType.length()>1)
			throw new IllegalArgumentException("要素类型CtrlFactorType值"+aCtrlFactorType+"的长度"+aCtrlFactorType.length()+"大于最大值1");
		CtrlFactorType = aCtrlFactorType;
	}
	public double getCtrlFactorValue()
	{
		return CtrlFactorValue;
	}
	public void setCtrlFactorValue(double aCtrlFactorValue)
	{
		CtrlFactorValue = aCtrlFactorValue;
	}
	public void setCtrlFactorValue(String aCtrlFactorValue)
	{
		if (aCtrlFactorValue != null && !aCtrlFactorValue.equals(""))
		{
			Double tDouble = new Double(aCtrlFactorValue);
			double d = tDouble.doubleValue();
			CtrlFactorValue = d;
		}
	}

	/**
	* 1-金额（元）<p>
	* 2-天<p>
	* 3-次<p>
	* 4-小时
	*/
	public String getCtrlFactorUnit()
	{
		return CtrlFactorUnit;
	}
	public void setCtrlFactorUnit(String aCtrlFactorUnit)
	{
		if(aCtrlFactorUnit!=null && aCtrlFactorUnit.length()>1)
			throw new IllegalArgumentException("要素单位CtrlFactorUnit值"+aCtrlFactorUnit+"的长度"+aCtrlFactorUnit.length()+"大于最大值1");
		CtrlFactorUnit = aCtrlFactorUnit;
	}
	/**
	* 本字段用来描述CtrlFactorValue的取值原理或算法：<p>
	* 1-取描述默认值<p>
	* 2-实例化时取默认计算要素计算<p>
	* 3-实例化时取约定计算要素计算
	*/
	public String getCalMode()
	{
		return CalMode;
	}
	public void setCalMode(String aCalMode)
	{
		if(aCalMode!=null && aCalMode.length()>1)
			throw new IllegalArgumentException("要素值计算方式CalMode值"+aCalMode+"的长度"+aCalMode.length()+"大于最大值1");
		CalMode = aCalMode;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		if(aCalCode!=null && aCalCode.length()>10)
			throw new IllegalArgumentException("要素值计算公式CalCode值"+aCalCode+"的长度"+aCalCode.length()+"大于最大值10");
		CalCode = aCalCode;
	}
	/**
	* 暂用于存储天数/次数累加器的天金额/次金额
	*/
	public double getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(double aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if (aDefaultValue != null && !aDefaultValue.equals(""))
		{
			Double tDouble = new Double(aDefaultValue);
			double d = tDouble.doubleValue();
			DefaultValue = d;
		}
	}

	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		if(aRemark1!=null && aRemark1.length()>100)
			throw new IllegalArgumentException("备注1Remark1值"+aRemark1+"的长度"+aRemark1.length()+"大于最大值100");
		Remark1 = aRemark1;
	}
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		if(aRemark2!=null && aRemark2.length()>100)
			throw new IllegalArgumentException("备注2Remark2值"+aRemark2+"的长度"+aRemark2.length()+"大于最大值100");
		Remark2 = aRemark2;
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
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
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

	/**
	* 使用另外一个 PD_LCalculatorMainSchema 对象给 Schema 赋值
	* @param: aPD_LCalculatorMainSchema PD_LCalculatorMainSchema
	**/
	public void setSchema(PD_LCalculatorMainSchema aPD_LCalculatorMainSchema)
	{
		this.CalculatorCode = aPD_LCalculatorMainSchema.getCalculatorCode();
		this.CalculatorName = aPD_LCalculatorMainSchema.getCalculatorName();
		this.CtrlLevel = aPD_LCalculatorMainSchema.getCtrlLevel();
		this.Type = aPD_LCalculatorMainSchema.getType();
		this.CtrlFactorType = aPD_LCalculatorMainSchema.getCtrlFactorType();
		this.CtrlFactorValue = aPD_LCalculatorMainSchema.getCtrlFactorValue();
		this.CtrlFactorUnit = aPD_LCalculatorMainSchema.getCtrlFactorUnit();
		this.CalMode = aPD_LCalculatorMainSchema.getCalMode();
		this.CalCode = aPD_LCalculatorMainSchema.getCalCode();
		this.DefaultValue = aPD_LCalculatorMainSchema.getDefaultValue();
		this.Remark1 = aPD_LCalculatorMainSchema.getRemark1();
		this.Remark2 = aPD_LCalculatorMainSchema.getRemark2();
		this.ModifyDate = fDate.getDate( aPD_LCalculatorMainSchema.getModifyDate());
		this.ModifyTime = aPD_LCalculatorMainSchema.getModifyTime();
		this.Operator = aPD_LCalculatorMainSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LCalculatorMainSchema.getMakeDate());
		this.MakeTime = aPD_LCalculatorMainSchema.getMakeTime();
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
			if( rs.getString("CalculatorCode") == null )
				this.CalculatorCode = null;
			else
				this.CalculatorCode = rs.getString("CalculatorCode").trim();

			if( rs.getString("CalculatorName") == null )
				this.CalculatorName = null;
			else
				this.CalculatorName = rs.getString("CalculatorName").trim();

			this.CtrlLevel = rs.getInt("CtrlLevel");
			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("CtrlFactorType") == null )
				this.CtrlFactorType = null;
			else
				this.CtrlFactorType = rs.getString("CtrlFactorType").trim();

			this.CtrlFactorValue = rs.getDouble("CtrlFactorValue");
			if( rs.getString("CtrlFactorUnit") == null )
				this.CtrlFactorUnit = null;
			else
				this.CtrlFactorUnit = rs.getString("CtrlFactorUnit").trim();

			if( rs.getString("CalMode") == null )
				this.CalMode = null;
			else
				this.CalMode = rs.getString("CalMode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			this.DefaultValue = rs.getDouble("DefaultValue");
			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LCalculatorMain表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorMainSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LCalculatorMainSchema getSchema()
	{
		PD_LCalculatorMainSchema aPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
		aPD_LCalculatorMainSchema.setSchema(this);
		return aPD_LCalculatorMainSchema;
	}

	public PD_LCalculatorMainDB getDB()
	{
		PD_LCalculatorMainDB aDBOper = new PD_LCalculatorMainDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorMain描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalculatorName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlFactorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlFactorValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlFactorUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorMain>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalculatorName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CtrlLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CtrlFactorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CtrlFactorValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			CtrlFactorUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorMainSchema";
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
		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculatorCode));
		}
		if (FCode.equalsIgnoreCase("CalculatorName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculatorName));
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlLevel));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("CtrlFactorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlFactorType));
		}
		if (FCode.equalsIgnoreCase("CtrlFactorValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlFactorValue));
		}
		if (FCode.equalsIgnoreCase("CtrlFactorUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlFactorUnit));
		}
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalMode));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(CalculatorCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalculatorName);
				break;
			case 2:
				strFieldValue = String.valueOf(CtrlLevel);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CtrlFactorType);
				break;
			case 5:
				strFieldValue = String.valueOf(CtrlFactorValue);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CtrlFactorUnit);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalMode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 9:
				strFieldValue = String.valueOf(DefaultValue);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalculatorCode = FValue.trim();
			}
			else
				CalculatorCode = null;
		}
		if (FCode.equalsIgnoreCase("CalculatorName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalculatorName = FValue.trim();
			}
			else
				CalculatorName = null;
		}
		if (FCode.equalsIgnoreCase("CtrlLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("CtrlFactorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlFactorType = FValue.trim();
			}
			else
				CtrlFactorType = null;
		}
		if (FCode.equalsIgnoreCase("CtrlFactorValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CtrlFactorValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CtrlFactorUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlFactorUnit = FValue.trim();
			}
			else
				CtrlFactorUnit = null;
		}
		if (FCode.equalsIgnoreCase("CalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalMode = FValue.trim();
			}
			else
				CalMode = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LCalculatorMainSchema other = (PD_LCalculatorMainSchema)otherObject;
		return
			CalculatorCode.equals(other.getCalculatorCode())
			&& CalculatorName.equals(other.getCalculatorName())
			&& CtrlLevel == other.getCtrlLevel()
			&& Type.equals(other.getType())
			&& CtrlFactorType.equals(other.getCtrlFactorType())
			&& CtrlFactorValue == other.getCtrlFactorValue()
			&& CtrlFactorUnit.equals(other.getCtrlFactorUnit())
			&& CalMode.equals(other.getCalMode())
			&& CalCode.equals(other.getCalCode())
			&& DefaultValue == other.getDefaultValue()
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("CalculatorCode") ) {
			return 0;
		}
		if( strFieldName.equals("CalculatorName") ) {
			return 1;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return 2;
		}
		if( strFieldName.equals("Type") ) {
			return 3;
		}
		if( strFieldName.equals("CtrlFactorType") ) {
			return 4;
		}
		if( strFieldName.equals("CtrlFactorValue") ) {
			return 5;
		}
		if( strFieldName.equals("CtrlFactorUnit") ) {
			return 6;
		}
		if( strFieldName.equals("CalMode") ) {
			return 7;
		}
		if( strFieldName.equals("CalCode") ) {
			return 8;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 9;
		}
		if( strFieldName.equals("Remark1") ) {
			return 10;
		}
		if( strFieldName.equals("Remark2") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
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
				strFieldName = "CalculatorCode";
				break;
			case 1:
				strFieldName = "CalculatorName";
				break;
			case 2:
				strFieldName = "CtrlLevel";
				break;
			case 3:
				strFieldName = "Type";
				break;
			case 4:
				strFieldName = "CtrlFactorType";
				break;
			case 5:
				strFieldName = "CtrlFactorValue";
				break;
			case 6:
				strFieldName = "CtrlFactorUnit";
				break;
			case 7:
				strFieldName = "CalMode";
				break;
			case 8:
				strFieldName = "CalCode";
				break;
			case 9:
				strFieldName = "DefaultValue";
				break;
			case 10:
				strFieldName = "Remark1";
				break;
			case 11:
				strFieldName = "Remark2";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
				strFieldName = "ModifyTime";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("CalculatorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalculatorName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlFactorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlFactorValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CtrlFactorUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
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
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
