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
import com.sinosoft.lis.db.PD_LCalculatorNatureTimeDB;

/*
 * <p>ClassName: PD_LCalculatorNatureTimeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LCalculatorNatureTimeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LCalculatorNatureTimeSchema.class);
	// @Field
	/** 累加器编号 */
	private String CalculatorCode;
	/** 生效起期 */
	private String StartDate;
	/** 生效止期 */
	private String EndDate;
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
	/** 备注1 */
	private String Remark1;
	/** 备注2 */
	private String Remark2;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LCalculatorNatureTimeSchema()
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
		PD_LCalculatorNatureTimeSchema cloned = (PD_LCalculatorNatureTimeSchema)super.clone();
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
		CalculatorCode = aCalculatorCode;
	}
	public String getStartDate()
	{
		return StartDate;
	}
	public void setStartDate(String aStartDate)
	{
		StartDate = aStartDate;
	}
	public String getEndDate()
	{
		return EndDate;
	}
	public void setEndDate(String aEndDate)
	{
		EndDate = aEndDate;
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
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		Remark1 = aRemark1;
	}
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		Remark2 = aRemark2;
	}

	/**
	* 使用另外一个 PD_LCalculatorNatureTimeSchema 对象给 Schema 赋值
	* @param: aPD_LCalculatorNatureTimeSchema PD_LCalculatorNatureTimeSchema
	**/
	public void setSchema(PD_LCalculatorNatureTimeSchema aPD_LCalculatorNatureTimeSchema)
	{
		this.CalculatorCode = aPD_LCalculatorNatureTimeSchema.getCalculatorCode();
		this.StartDate = aPD_LCalculatorNatureTimeSchema.getStartDate();
		this.EndDate = aPD_LCalculatorNatureTimeSchema.getEndDate();
		this.ModifyDate = fDate.getDate( aPD_LCalculatorNatureTimeSchema.getModifyDate());
		this.ModifyTime = aPD_LCalculatorNatureTimeSchema.getModifyTime();
		this.Operator = aPD_LCalculatorNatureTimeSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LCalculatorNatureTimeSchema.getMakeDate());
		this.MakeTime = aPD_LCalculatorNatureTimeSchema.getMakeTime();
		this.Remark1 = aPD_LCalculatorNatureTimeSchema.getRemark1();
		this.Remark2 = aPD_LCalculatorNatureTimeSchema.getRemark2();
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

			if( rs.getString("StartDate") == null )
				this.StartDate = null;
			else
				this.StartDate = rs.getString("StartDate").trim();

			if( rs.getString("EndDate") == null )
				this.EndDate = null;
			else
				this.EndDate = rs.getString("EndDate").trim();

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

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LCalculatorNatureTime表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorNatureTimeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LCalculatorNatureTimeSchema getSchema()
	{
		PD_LCalculatorNatureTimeSchema aPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
		aPD_LCalculatorNatureTimeSchema.setSchema(this);
		return aPD_LCalculatorNatureTimeSchema;
	}

	public PD_LCalculatorNatureTimeDB getDB()
	{
		PD_LCalculatorNatureTimeDB aDBOper = new PD_LCalculatorNatureTimeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorNatureTime描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorNatureTime>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			StartDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EndDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorNatureTimeSchema";
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
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDate));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDate));
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
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
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
				strFieldValue = StrTool.GBKToUnicode(StartDate);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EndDate);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
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
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDate = FValue.trim();
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDate = FValue.trim();
			}
			else
				EndDate = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LCalculatorNatureTimeSchema other = (PD_LCalculatorNatureTimeSchema)otherObject;
		return
			CalculatorCode.equals(other.getCalculatorCode())
			&& StartDate.equals(other.getStartDate())
			&& EndDate.equals(other.getEndDate())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2());
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
		if( strFieldName.equals("StartDate") ) {
			return 1;
		}
		if( strFieldName.equals("EndDate") ) {
			return 2;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 3;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
		}
		if( strFieldName.equals("Remark1") ) {
			return 8;
		}
		if( strFieldName.equals("Remark2") ) {
			return 9;
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
				strFieldName = "StartDate";
				break;
			case 2:
				strFieldName = "EndDate";
				break;
			case 3:
				strFieldName = "ModifyDate";
				break;
			case 4:
				strFieldName = "ModifyTime";
				break;
			case 5:
				strFieldName = "Operator";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
				break;
			case 8:
				strFieldName = "Remark1";
				break;
			case 9:
				strFieldName = "Remark2";
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
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
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
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
