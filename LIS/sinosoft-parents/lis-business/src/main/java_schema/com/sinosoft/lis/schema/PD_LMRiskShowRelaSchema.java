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
import com.sinosoft.lis.db.PD_LMRiskShowRelaDB;

/*
 * <p>ClassName: PD_LMRiskShowRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品引擎相关表
 * @CreateDate：2011-11-17
 */
public class PD_LMRiskShowRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMRiskShowRelaSchema.class);

	// @Field
	/** 险种代码 */
	private String RiskCode;
	/** 关联控件id */
	private String ControlCode;
	/** 关联控件值 */
	private String ControlValue;
	/** 控制类型 */
	private String FunctionType;
	/** 控制的控件id */
	private String RelaCode;
	/** 控制的控件显隐控制 */
	private String RelaShowFlag;
	/** 控制的控件下拉列表值 */
	private String RelaValueSql;
	/** 控制的控件默认值 */
	private String RelaShowValue;
	/** 备注信息 */
	private String Remark;
	/** 新增日期 */
	private Date MakeDate;
	/** 新增时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskShowRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "RiskCode";
		pk[1] = "ControlCode";
		pk[2] = "ControlValue";
		pk[3] = "FunctionType";
		pk[4] = "RelaCode";

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
                PD_LMRiskShowRelaSchema cloned = (PD_LMRiskShowRelaSchema)super.clone();
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
		RiskCode = aRiskCode;
	}
	public String getControlCode()
	{
		return ControlCode;
	}
	public void setControlCode(String aControlCode)
	{
		ControlCode = aControlCode;
	}
	public String getControlValue()
	{
		return ControlValue;
	}
	public void setControlValue(String aControlValue)
	{
		ControlValue = aControlValue;
	}
	public String getFunctionType()
	{
		return FunctionType;
	}
	public void setFunctionType(String aFunctionType)
	{
		FunctionType = aFunctionType;
	}
	public String getRelaCode()
	{
		return RelaCode;
	}
	public void setRelaCode(String aRelaCode)
	{
		RelaCode = aRelaCode;
	}
	public String getRelaShowFlag()
	{
		return RelaShowFlag;
	}
	public void setRelaShowFlag(String aRelaShowFlag)
	{
		RelaShowFlag = aRelaShowFlag;
	}
	public String getRelaValueSql()
	{
		return RelaValueSql;
	}
	public void setRelaValueSql(String aRelaValueSql)
	{
		RelaValueSql = aRelaValueSql;
	}
	public String getRelaShowValue()
	{
		return RelaShowValue;
	}
	public void setRelaShowValue(String aRelaShowValue)
	{
		RelaShowValue = aRelaShowValue;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	* 使用另外一个 PD_LMRiskShowRelaSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskShowRelaSchema PD_LMRiskShowRelaSchema
	**/
	public void setSchema(PD_LMRiskShowRelaSchema aPD_LMRiskShowRelaSchema)
	{
		this.RiskCode = aPD_LMRiskShowRelaSchema.getRiskCode();
		this.ControlCode = aPD_LMRiskShowRelaSchema.getControlCode();
		this.ControlValue = aPD_LMRiskShowRelaSchema.getControlValue();
		this.FunctionType = aPD_LMRiskShowRelaSchema.getFunctionType();
		this.RelaCode = aPD_LMRiskShowRelaSchema.getRelaCode();
		this.RelaShowFlag = aPD_LMRiskShowRelaSchema.getRelaShowFlag();
		this.RelaValueSql = aPD_LMRiskShowRelaSchema.getRelaValueSql();
		this.RelaShowValue = aPD_LMRiskShowRelaSchema.getRelaShowValue();
		this.Remark = aPD_LMRiskShowRelaSchema.getRemark();
		this.MakeDate = fDate.getDate( aPD_LMRiskShowRelaSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskShowRelaSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskShowRelaSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskShowRelaSchema.getModifyTime();
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

			if( rs.getString("ControlCode") == null )
				this.ControlCode = null;
			else
				this.ControlCode = rs.getString("ControlCode").trim();

			if( rs.getString("ControlValue") == null )
				this.ControlValue = null;
			else
				this.ControlValue = rs.getString("ControlValue").trim();

			if( rs.getString("FunctionType") == null )
				this.FunctionType = null;
			else
				this.FunctionType = rs.getString("FunctionType").trim();

			if( rs.getString("RelaCode") == null )
				this.RelaCode = null;
			else
				this.RelaCode = rs.getString("RelaCode").trim();

			if( rs.getString("RelaShowFlag") == null )
				this.RelaShowFlag = null;
			else
				this.RelaShowFlag = rs.getString("RelaShowFlag").trim();

			if( rs.getString("RelaValueSql") == null )
				this.RelaValueSql = null;
			else
				this.RelaValueSql = rs.getString("RelaValueSql").trim();

			if( rs.getString("RelaShowValue") == null )
				this.RelaShowValue = null;
			else
				this.RelaShowValue = rs.getString("RelaShowValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的PD_LMRiskShowRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskShowRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskShowRelaSchema getSchema()
	{
		PD_LMRiskShowRelaSchema aPD_LMRiskShowRelaSchema = new PD_LMRiskShowRelaSchema();
		aPD_LMRiskShowRelaSchema.setSchema(this);
		return aPD_LMRiskShowRelaSchema;
	}

	public PD_LMRiskShowRelaDB getDB()
	{
		PD_LMRiskShowRelaDB aDBOper = new PD_LMRiskShowRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskShowRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ControlCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ControlValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FunctionType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RelaCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RelaShowFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RelaValueSql)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RelaShowValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskShowRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ControlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ControlValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FunctionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RelaShowFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RelaValueSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RelaShowValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskShowRelaSchema";
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
		if (FCode.equalsIgnoreCase("ControlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ControlCode));
		}
		if (FCode.equalsIgnoreCase("ControlValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ControlValue));
		}
		if (FCode.equalsIgnoreCase("FunctionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FunctionType));
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaCode));
		}
		if (FCode.equalsIgnoreCase("RelaShowFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaShowFlag));
		}
		if (FCode.equalsIgnoreCase("RelaValueSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaValueSql));
		}
		if (FCode.equalsIgnoreCase("RelaShowValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaShowValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ControlCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ControlValue);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FunctionType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelaCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RelaShowFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RelaValueSql);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RelaShowValue);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ControlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ControlCode = FValue.trim();
			}
			else
				ControlCode = null;
		}
		if (FCode.equalsIgnoreCase("ControlValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ControlValue = FValue.trim();
			}
			else
				ControlValue = null;
		}
		if (FCode.equalsIgnoreCase("FunctionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FunctionType = FValue.trim();
			}
			else
				FunctionType = null;
		}
		if (FCode.equalsIgnoreCase("RelaCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaCode = FValue.trim();
			}
			else
				RelaCode = null;
		}
		if (FCode.equalsIgnoreCase("RelaShowFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaShowFlag = FValue.trim();
			}
			else
				RelaShowFlag = null;
		}
		if (FCode.equalsIgnoreCase("RelaValueSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaValueSql = FValue.trim();
			}
			else
				RelaValueSql = null;
		}
		if (FCode.equalsIgnoreCase("RelaShowValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaShowValue = FValue.trim();
			}
			else
				RelaShowValue = null;
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
		PD_LMRiskShowRelaSchema other = (PD_LMRiskShowRelaSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& ControlCode.equals(other.getControlCode())
			&& ControlValue.equals(other.getControlValue())
			&& FunctionType.equals(other.getFunctionType())
			&& RelaCode.equals(other.getRelaCode())
			&& RelaShowFlag.equals(other.getRelaShowFlag())
			&& RelaValueSql.equals(other.getRelaValueSql())
			&& RelaShowValue.equals(other.getRelaShowValue())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("ControlCode") ) {
			return 1;
		}
		if( strFieldName.equals("ControlValue") ) {
			return 2;
		}
		if( strFieldName.equals("FunctionType") ) {
			return 3;
		}
		if( strFieldName.equals("RelaCode") ) {
			return 4;
		}
		if( strFieldName.equals("RelaShowFlag") ) {
			return 5;
		}
		if( strFieldName.equals("RelaValueSql") ) {
			return 6;
		}
		if( strFieldName.equals("RelaShowValue") ) {
			return 7;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "ControlCode";
				break;
			case 2:
				strFieldName = "ControlValue";
				break;
			case 3:
				strFieldName = "FunctionType";
				break;
			case 4:
				strFieldName = "RelaCode";
				break;
			case 5:
				strFieldName = "RelaShowFlag";
				break;
			case 6:
				strFieldName = "RelaValueSql";
				break;
			case 7:
				strFieldName = "RelaShowValue";
				break;
			case 8:
				strFieldName = "Remark";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ControlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ControlValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FunctionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaShowFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaValueSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaShowValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
