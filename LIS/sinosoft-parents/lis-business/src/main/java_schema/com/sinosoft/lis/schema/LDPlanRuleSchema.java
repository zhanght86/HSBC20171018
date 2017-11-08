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
import com.sinosoft.lis.db.LDPlanRuleDB;

/*
 * <p>ClassName: LDPlanRuleSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDPlanRuleSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPlanRuleSchema.class);

	// @Field
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 规则类型 */
	private String FactoryType;
	/** 计算编码 */
	private String FactoryCode;
	/** 计算名称 */
	private String FactoryName;
	/** 计算子编码 */
	private String FactorySubCode;
	/** 规则编码 */
	private String RuleCode;
	/** 规则要素参数名称 */
	private String ParamName;
	/** 规则要素参数值 */
	private String Param;
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

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPlanRuleSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "ContPlanCode";
		pk[1] = "FactoryType";
		pk[2] = "FactoryCode";
		pk[3] = "FactorySubCode";
		pk[4] = "RuleCode";
		pk[5] = "ParamName";

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
		LDPlanRuleSchema cloned = (LDPlanRuleSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	public String getFactoryCode()
	{
		return FactoryCode;
	}
	public void setFactoryCode(String aFactoryCode)
	{
		FactoryCode = aFactoryCode;
	}
	public String getFactoryName()
	{
		return FactoryName;
	}
	public void setFactoryName(String aFactoryName)
	{
		FactoryName = aFactoryName;
	}
	public String getFactorySubCode()
	{
		return FactorySubCode;
	}
	public void setFactorySubCode(String aFactorySubCode)
	{
		FactorySubCode = aFactorySubCode;
	}
	/**
	* 自动生成一个号码，一个投保规则生成一个号码
	*/
	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		RuleCode = aRuleCode;
	}
	public String getParamName()
	{
		return ParamName;
	}
	public void setParamName(String aParamName)
	{
		ParamName = aParamName;
	}
	public String getParam()
	{
		return Param;
	}
	public void setParam(String aParam)
	{
		Param = aParam;
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
	* 使用另外一个 LDPlanRuleSchema 对象给 Schema 赋值
	* @param: aLDPlanRuleSchema LDPlanRuleSchema
	**/
	public void setSchema(LDPlanRuleSchema aLDPlanRuleSchema)
	{
		this.ContPlanCode = aLDPlanRuleSchema.getContPlanCode();
		this.FactoryType = aLDPlanRuleSchema.getFactoryType();
		this.FactoryCode = aLDPlanRuleSchema.getFactoryCode();
		this.FactoryName = aLDPlanRuleSchema.getFactoryName();
		this.FactorySubCode = aLDPlanRuleSchema.getFactorySubCode();
		this.RuleCode = aLDPlanRuleSchema.getRuleCode();
		this.ParamName = aLDPlanRuleSchema.getParamName();
		this.Param = aLDPlanRuleSchema.getParam();
		this.Operator = aLDPlanRuleSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDPlanRuleSchema.getMakeDate());
		this.MakeTime = aLDPlanRuleSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDPlanRuleSchema.getModifyDate());
		this.ModifyTime = aLDPlanRuleSchema.getModifyTime();
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
			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("FactoryType") == null )
				this.FactoryType = null;
			else
				this.FactoryType = rs.getString("FactoryType").trim();

			if( rs.getString("FactoryCode") == null )
				this.FactoryCode = null;
			else
				this.FactoryCode = rs.getString("FactoryCode").trim();

			if( rs.getString("FactoryName") == null )
				this.FactoryName = null;
			else
				this.FactoryName = rs.getString("FactoryName").trim();

			if( rs.getString("FactorySubCode") == null )
				this.FactorySubCode = null;
			else
				this.FactorySubCode = rs.getString("FactorySubCode").trim();

			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

			if( rs.getString("ParamName") == null )
				this.ParamName = null;
			else
				this.ParamName = rs.getString("ParamName").trim();

			if( rs.getString("Param") == null )
				this.Param = null;
			else
				this.Param = rs.getString("Param").trim();

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
			logger.debug("数据库中的LDPlanRule表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanRuleSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPlanRuleSchema getSchema()
	{
		LDPlanRuleSchema aLDPlanRuleSchema = new LDPlanRuleSchema();
		aLDPlanRuleSchema.setSchema(this);
		return aLDPlanRuleSchema;
	}

	public LDPlanRuleDB getDB()
	{
		LDPlanRuleDB aDBOper = new LDPlanRuleDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanRule描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactorySubCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Param)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlanRule>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FactoryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FactoryCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FactoryName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FactorySubCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ParamName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Param = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanRuleSchema";
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryType));
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryCode));
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryName));
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubCode));
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamName));
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Param));
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
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FactoryType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FactoryCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FactoryName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FactorySubCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ParamName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Param);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
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

		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryType = FValue.trim();
			}
			else
				FactoryType = null;
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryCode = FValue.trim();
			}
			else
				FactoryCode = null;
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryName = FValue.trim();
			}
			else
				FactoryName = null;
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactorySubCode = FValue.trim();
			}
			else
				FactorySubCode = null;
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamName = FValue.trim();
			}
			else
				ParamName = null;
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Param = FValue.trim();
			}
			else
				Param = null;
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
		LDPlanRuleSchema other = (LDPlanRuleSchema)otherObject;
		return
			ContPlanCode.equals(other.getContPlanCode())
			&& FactoryType.equals(other.getFactoryType())
			&& FactoryCode.equals(other.getFactoryCode())
			&& FactoryName.equals(other.getFactoryName())
			&& FactorySubCode.equals(other.getFactorySubCode())
			&& RuleCode.equals(other.getRuleCode())
			&& ParamName.equals(other.getParamName())
			&& Param.equals(other.getParam())
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
		if( strFieldName.equals("ContPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("FactoryType") ) {
			return 1;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return 2;
		}
		if( strFieldName.equals("FactoryName") ) {
			return 3;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return 4;
		}
		if( strFieldName.equals("RuleCode") ) {
			return 5;
		}
		if( strFieldName.equals("ParamName") ) {
			return 6;
		}
		if( strFieldName.equals("Param") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
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
				strFieldName = "ContPlanCode";
				break;
			case 1:
				strFieldName = "FactoryType";
				break;
			case 2:
				strFieldName = "FactoryCode";
				break;
			case 3:
				strFieldName = "FactoryName";
				break;
			case 4:
				strFieldName = "FactorySubCode";
				break;
			case 5:
				strFieldName = "RuleCode";
				break;
			case 6:
				strFieldName = "ParamName";
				break;
			case 7:
				strFieldName = "Param";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
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
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Param") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
