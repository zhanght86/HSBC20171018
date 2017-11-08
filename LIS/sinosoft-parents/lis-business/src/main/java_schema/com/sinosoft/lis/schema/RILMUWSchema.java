

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RILMUWDB;

/*
 * <p>ClassName: RILMUWSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RILMUWSchema implements Schema, Cloneable
{
	// @Field
	/** 规则代码 */
	private String RuleCode;
	/** 规则名称 */
	private String RuleName;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 核保顺序号 */
	private int RuleOrder;
	/** 应用类别 */
	private String CalItemType;
	/** 规则类别 */
	private String RuleType;
	/** 计算类型 */
	private String ItemCalType;
	/** 固定数字值 */
	private double DoubleValue;
	/** 计算处理类 */
	private String CalClass;
	/** 计算sql算法 */
	private String CalSQL;
	/** 计算结果存储字段 */
	private String TarGetClumn;
	/** 备注 */
	private String Remark;
	/** 核保级别 */
	private String UWGrade;
	/** 核保通过标记 */
	private String PassFlag;
	/** 保障计划 */
	private String ContPlanCode;
	/** 核保描述 */
	private String UWDesc;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RILMUWSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RuleCode";

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
		RILMUWSchema cloned = (RILMUWSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 以0开头：新单<p>
	* 以1开头：保全<p>
	* 以2开头：理赔
	*/
	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		RuleCode = aRuleCode;
	}
	public String getRuleName()
	{
		return RuleName;
	}
	public void setRuleName(String aRuleName)
	{
		RuleName = aRuleName;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public int getRuleOrder()
	{
		return RuleOrder;
	}
	public void setRuleOrder(int aRuleOrder)
	{
		RuleOrder = aRuleOrder;
	}
	public void setRuleOrder(String aRuleOrder)
	{
		if (aRuleOrder != null && !aRuleOrder.equals(""))
		{
			Integer tInteger = new Integer(aRuleOrder);
			int i = tInteger.intValue();
			RuleOrder = i;
		}
	}

	/**
	* 01-个单新单 02-个单续期 03-个单保全 04-理赔摊回 05-新单团单 06-团体保全
	*/
	public String getCalItemType()
	{
		return CalItemType;
	}
	public void setCalItemType(String aCalItemType)
	{
		CalItemType = aCalItemType;
	}
	/**
	* 01：临分<p>
	* <p>
	* 02：核保<p>
	* <p>
	* 03：核赔
	*/
	public String getRuleType()
	{
		return RuleType;
	}
	public void setRuleType(String aRuleType)
	{
		RuleType = aRuleType;
	}
	/**
	* 1-固定数值,2-Sql计算,3-类计算
	*/
	public String getItemCalType()
	{
		return ItemCalType;
	}
	public void setItemCalType(String aItemCalType)
	{
		ItemCalType = aItemCalType;
	}
	public double getDoubleValue()
	{
		return DoubleValue;
	}
	public void setDoubleValue(double aDoubleValue)
	{
		DoubleValue = aDoubleValue;
	}
	public void setDoubleValue(String aDoubleValue)
	{
		if (aDoubleValue != null && !aDoubleValue.equals(""))
		{
			Double tDouble = new Double(aDoubleValue);
			double d = tDouble.doubleValue();
			DoubleValue = d;
		}
	}

	public String getCalClass()
	{
		return CalClass;
	}
	public void setCalClass(String aCalClass)
	{
		CalClass = aCalClass;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}
	public String getTarGetClumn()
	{
		return TarGetClumn;
	}
	public void setTarGetClumn(String aTarGetClumn)
	{
		TarGetClumn = aTarGetClumn;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 核保核赔时有用,分级核保核赔.
	*/
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}
	/**
	* 核保可通过标记(“N”-不能核保通过标志)在remark中记录标记, 在外部关联程序修改此标记后,可以由核保员决定是否核保通过
	*/
	public String getPassFlag()
	{
		return PassFlag;
	}
	public void setPassFlag(String aPassFlag)
	{
		PassFlag = aPassFlag;
	}
	/**
	* 对保障计划的为保障计划代码，否则为‘000000’
	*/
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getUWDesc()
	{
		return UWDesc;
	}
	public void setUWDesc(String aUWDesc)
	{
		UWDesc = aUWDesc;
	}

	/**
	* 使用另外一个 RILMUWSchema 对象给 Schema 赋值
	* @param: aRILMUWSchema RILMUWSchema
	**/
	public void setSchema(RILMUWSchema aRILMUWSchema)
	{
		this.RuleCode = aRILMUWSchema.getRuleCode();
		this.RuleName = aRILMUWSchema.getRuleName();
		this.RIPreceptNo = aRILMUWSchema.getRIPreceptNo();
		this.RuleOrder = aRILMUWSchema.getRuleOrder();
		this.CalItemType = aRILMUWSchema.getCalItemType();
		this.RuleType = aRILMUWSchema.getRuleType();
		this.ItemCalType = aRILMUWSchema.getItemCalType();
		this.DoubleValue = aRILMUWSchema.getDoubleValue();
		this.CalClass = aRILMUWSchema.getCalClass();
		this.CalSQL = aRILMUWSchema.getCalSQL();
		this.TarGetClumn = aRILMUWSchema.getTarGetClumn();
		this.Remark = aRILMUWSchema.getRemark();
		this.UWGrade = aRILMUWSchema.getUWGrade();
		this.PassFlag = aRILMUWSchema.getPassFlag();
		this.ContPlanCode = aRILMUWSchema.getContPlanCode();
		this.UWDesc = aRILMUWSchema.getUWDesc();
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
			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

			if( rs.getString("RuleName") == null )
				this.RuleName = null;
			else
				this.RuleName = rs.getString("RuleName").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			this.RuleOrder = rs.getInt("RuleOrder");
			if( rs.getString("CalItemType") == null )
				this.CalItemType = null;
			else
				this.CalItemType = rs.getString("CalItemType").trim();

			if( rs.getString("RuleType") == null )
				this.RuleType = null;
			else
				this.RuleType = rs.getString("RuleType").trim();

			if( rs.getString("ItemCalType") == null )
				this.ItemCalType = null;
			else
				this.ItemCalType = rs.getString("ItemCalType").trim();

			this.DoubleValue = rs.getDouble("DoubleValue");
			if( rs.getString("CalClass") == null )
				this.CalClass = null;
			else
				this.CalClass = rs.getString("CalClass").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

			if( rs.getString("TarGetClumn") == null )
				this.TarGetClumn = null;
			else
				this.TarGetClumn = rs.getString("TarGetClumn").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("PassFlag") == null )
				this.PassFlag = null;
			else
				this.PassFlag = rs.getString("PassFlag").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("UWDesc") == null )
				this.UWDesc = null;
			else
				this.UWDesc = rs.getString("UWDesc").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RILMUW表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RILMUWSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RILMUWSchema getSchema()
	{
		RILMUWSchema aRILMUWSchema = new RILMUWSchema();
		aRILMUWSchema.setSchema(this);
		return aRILMUWSchema;
	}

	public RILMUWDB getDB()
	{
		RILMUWDB aDBOper = new RILMUWDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRILMUW描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RuleOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DoubleValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarGetClumn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRILMUW>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			CalItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ItemCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DoubleValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			CalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			TarGetClumn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			UWDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RILMUWSchema";
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
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleName));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("RuleOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleOrder));
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemType));
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleType));
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCalType));
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoubleValue));
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalClass));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
		}
		if (FCode.equalsIgnoreCase("TarGetClumn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarGetClumn));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassFlag));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("UWDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWDesc));
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
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 3:
				strFieldValue = String.valueOf(RuleOrder);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalItemType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ItemCalType);
				break;
			case 7:
				strFieldValue = String.valueOf(DoubleValue);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalClass);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(TarGetClumn);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PassFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(UWDesc);
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

		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleName = FValue.trim();
			}
			else
				RuleName = null;
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
		}
		if (FCode.equalsIgnoreCase("RuleOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RuleOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemType = FValue.trim();
			}
			else
				CalItemType = null;
		}
		if (FCode.equalsIgnoreCase("RuleType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleType = FValue.trim();
			}
			else
				RuleType = null;
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCalType = FValue.trim();
			}
			else
				ItemCalType = null;
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DoubleValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalClass = FValue.trim();
			}
			else
				CalClass = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		if (FCode.equalsIgnoreCase("TarGetClumn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarGetClumn = FValue.trim();
			}
			else
				TarGetClumn = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassFlag = FValue.trim();
			}
			else
				PassFlag = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("UWDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWDesc = FValue.trim();
			}
			else
				UWDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RILMUWSchema other = (RILMUWSchema)otherObject;
		return
			RuleCode.equals(other.getRuleCode())
			&& RuleName.equals(other.getRuleName())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& RuleOrder == other.getRuleOrder()
			&& CalItemType.equals(other.getCalItemType())
			&& RuleType.equals(other.getRuleType())
			&& ItemCalType.equals(other.getItemCalType())
			&& DoubleValue == other.getDoubleValue()
			&& CalClass.equals(other.getCalClass())
			&& CalSQL.equals(other.getCalSQL())
			&& TarGetClumn.equals(other.getTarGetClumn())
			&& Remark.equals(other.getRemark())
			&& UWGrade.equals(other.getUWGrade())
			&& PassFlag.equals(other.getPassFlag())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& UWDesc.equals(other.getUWDesc());
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
		if( strFieldName.equals("RuleCode") ) {
			return 0;
		}
		if( strFieldName.equals("RuleName") ) {
			return 1;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 2;
		}
		if( strFieldName.equals("RuleOrder") ) {
			return 3;
		}
		if( strFieldName.equals("CalItemType") ) {
			return 4;
		}
		if( strFieldName.equals("RuleType") ) {
			return 5;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return 6;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return 7;
		}
		if( strFieldName.equals("CalClass") ) {
			return 8;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 9;
		}
		if( strFieldName.equals("TarGetClumn") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
			return 11;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 12;
		}
		if( strFieldName.equals("PassFlag") ) {
			return 13;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 14;
		}
		if( strFieldName.equals("UWDesc") ) {
			return 15;
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
				strFieldName = "RuleCode";
				break;
			case 1:
				strFieldName = "RuleName";
				break;
			case 2:
				strFieldName = "RIPreceptNo";
				break;
			case 3:
				strFieldName = "RuleOrder";
				break;
			case 4:
				strFieldName = "CalItemType";
				break;
			case 5:
				strFieldName = "RuleType";
				break;
			case 6:
				strFieldName = "ItemCalType";
				break;
			case 7:
				strFieldName = "DoubleValue";
				break;
			case 8:
				strFieldName = "CalClass";
				break;
			case 9:
				strFieldName = "CalSQL";
				break;
			case 10:
				strFieldName = "TarGetClumn";
				break;
			case 11:
				strFieldName = "Remark";
				break;
			case 12:
				strFieldName = "UWGrade";
				break;
			case 13:
				strFieldName = "PassFlag";
				break;
			case 14:
				strFieldName = "ContPlanCode";
				break;
			case 15:
				strFieldName = "UWDesc";
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
		if( strFieldName.equals("RuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarGetClumn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDesc") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
