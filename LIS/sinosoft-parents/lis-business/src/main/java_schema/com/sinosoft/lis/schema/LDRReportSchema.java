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
import com.sinosoft.lis.db.LDRReportDB;

/*
 * <p>ClassName: LDRReportSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDRReportSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDRReportSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 生调项目代码 */
	private String RReportCode;
	/** 生调项目名称 */
	private String RReportName;
	/** 生调类型 */
	private String RReportClass;
	/** 生调额度起始值 */
	private double StartMoney;
	/** 生调额度终止值 */
	private double EndMoney;
	/** 生调年龄起始值 */
	private int StartAge;
	/** 生调年龄终止值 */
	private int EndAge;
	/** 生调人性别 */
	private String Sex;
	/** 备注1 */
	private String Note1;
	/** 备注2 */
	private String Note2;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDRReportSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LDRReportSchema cloned = (LDRReportSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getRReportCode()
	{
		return RReportCode;
	}
	public void setRReportCode(String aRReportCode)
	{
		RReportCode = aRReportCode;
	}
	public String getRReportName()
	{
		return RReportName;
	}
	public void setRReportName(String aRReportName)
	{
		RReportName = aRReportName;
	}
	/**
	* 1－－个单生调<p>
	* <p>
	* 2－－团单生调
	*/
	public String getRReportClass()
	{
		return RReportClass;
	}
	public void setRReportClass(String aRReportClass)
	{
		RReportClass = aRReportClass;
	}
	public double getStartMoney()
	{
		return StartMoney;
	}
	public void setStartMoney(double aStartMoney)
	{
		StartMoney = aStartMoney;
	}
	public void setStartMoney(String aStartMoney)
	{
		if (aStartMoney != null && !aStartMoney.equals(""))
		{
			Double tDouble = new Double(aStartMoney);
			double d = tDouble.doubleValue();
			StartMoney = d;
		}
	}

	public double getEndMoney()
	{
		return EndMoney;
	}
	public void setEndMoney(double aEndMoney)
	{
		EndMoney = aEndMoney;
	}
	public void setEndMoney(String aEndMoney)
	{
		if (aEndMoney != null && !aEndMoney.equals(""))
		{
			Double tDouble = new Double(aEndMoney);
			double d = tDouble.doubleValue();
			EndMoney = d;
		}
	}

	public int getStartAge()
	{
		return StartAge;
	}
	public void setStartAge(int aStartAge)
	{
		StartAge = aStartAge;
	}
	public void setStartAge(String aStartAge)
	{
		if (aStartAge != null && !aStartAge.equals(""))
		{
			Integer tInteger = new Integer(aStartAge);
			int i = tInteger.intValue();
			StartAge = i;
		}
	}

	public int getEndAge()
	{
		return EndAge;
	}
	public void setEndAge(int aEndAge)
	{
		EndAge = aEndAge;
	}
	public void setEndAge(String aEndAge)
	{
		if (aEndAge != null && !aEndAge.equals(""))
		{
			Integer tInteger = new Integer(aEndAge);
			int i = tInteger.intValue();
			EndAge = i;
		}
	}

	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	public String getNote1()
	{
		return Note1;
	}
	public void setNote1(String aNote1)
	{
		Note1 = aNote1;
	}
	public String getNote2()
	{
		return Note2;
	}
	public void setNote2(String aNote2)
	{
		Note2 = aNote2;
	}

	/**
	* 使用另外一个 LDRReportSchema 对象给 Schema 赋值
	* @param: aLDRReportSchema LDRReportSchema
	**/
	public void setSchema(LDRReportSchema aLDRReportSchema)
	{
		this.SerialNo = aLDRReportSchema.getSerialNo();
		this.RReportCode = aLDRReportSchema.getRReportCode();
		this.RReportName = aLDRReportSchema.getRReportName();
		this.RReportClass = aLDRReportSchema.getRReportClass();
		this.StartMoney = aLDRReportSchema.getStartMoney();
		this.EndMoney = aLDRReportSchema.getEndMoney();
		this.StartAge = aLDRReportSchema.getStartAge();
		this.EndAge = aLDRReportSchema.getEndAge();
		this.Sex = aLDRReportSchema.getSex();
		this.Note1 = aLDRReportSchema.getNote1();
		this.Note2 = aLDRReportSchema.getNote2();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RReportCode") == null )
				this.RReportCode = null;
			else
				this.RReportCode = rs.getString("RReportCode").trim();

			if( rs.getString("RReportName") == null )
				this.RReportName = null;
			else
				this.RReportName = rs.getString("RReportName").trim();

			if( rs.getString("RReportClass") == null )
				this.RReportClass = null;
			else
				this.RReportClass = rs.getString("RReportClass").trim();

			this.StartMoney = rs.getDouble("StartMoney");
			this.EndMoney = rs.getDouble("EndMoney");
			this.StartAge = rs.getInt("StartAge");
			this.EndAge = rs.getInt("EndAge");
			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			if( rs.getString("Note1") == null )
				this.Note1 = null;
			else
				this.Note1 = rs.getString("Note1").trim();

			if( rs.getString("Note2") == null )
				this.Note2 = null;
			else
				this.Note2 = rs.getString("Note2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDRReport表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDRReportSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDRReportSchema getSchema()
	{
		LDRReportSchema aLDRReportSchema = new LDRReportSchema();
		aLDRReportSchema.setSchema(this);
		return aLDRReportSchema;
	}

	public LDRReportDB getDB()
	{
		LDRReportDB aDBOper = new LDRReportDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDRReport描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RReportCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RReportName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RReportClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Note1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Note2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDRReport>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RReportCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RReportName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RReportClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			EndMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			StartAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			EndAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Note1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Note2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDRReportSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RReportCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RReportCode));
		}
		if (FCode.equalsIgnoreCase("RReportName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RReportName));
		}
		if (FCode.equalsIgnoreCase("RReportClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RReportClass));
		}
		if (FCode.equalsIgnoreCase("StartMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartMoney));
		}
		if (FCode.equalsIgnoreCase("EndMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndMoney));
		}
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartAge));
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndAge));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Note1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Note1));
		}
		if (FCode.equalsIgnoreCase("Note2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Note2));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RReportCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RReportName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RReportClass);
				break;
			case 4:
				strFieldValue = String.valueOf(StartMoney);
				break;
			case 5:
				strFieldValue = String.valueOf(EndMoney);
				break;
			case 6:
				strFieldValue = String.valueOf(StartAge);
				break;
			case 7:
				strFieldValue = String.valueOf(EndAge);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Note1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Note2);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("RReportCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RReportCode = FValue.trim();
			}
			else
				RReportCode = null;
		}
		if (FCode.equalsIgnoreCase("RReportName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RReportName = FValue.trim();
			}
			else
				RReportName = null;
		}
		if (FCode.equalsIgnoreCase("RReportClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RReportClass = FValue.trim();
			}
			else
				RReportClass = null;
		}
		if (FCode.equalsIgnoreCase("StartMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StartMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("EndMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				EndMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StartAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Note1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Note1 = FValue.trim();
			}
			else
				Note1 = null;
		}
		if (FCode.equalsIgnoreCase("Note2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Note2 = FValue.trim();
			}
			else
				Note2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDRReportSchema other = (LDRReportSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& RReportCode.equals(other.getRReportCode())
			&& RReportName.equals(other.getRReportName())
			&& RReportClass.equals(other.getRReportClass())
			&& StartMoney == other.getStartMoney()
			&& EndMoney == other.getEndMoney()
			&& StartAge == other.getStartAge()
			&& EndAge == other.getEndAge()
			&& Sex.equals(other.getSex())
			&& Note1.equals(other.getNote1())
			&& Note2.equals(other.getNote2());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("RReportCode") ) {
			return 1;
		}
		if( strFieldName.equals("RReportName") ) {
			return 2;
		}
		if( strFieldName.equals("RReportClass") ) {
			return 3;
		}
		if( strFieldName.equals("StartMoney") ) {
			return 4;
		}
		if( strFieldName.equals("EndMoney") ) {
			return 5;
		}
		if( strFieldName.equals("StartAge") ) {
			return 6;
		}
		if( strFieldName.equals("EndAge") ) {
			return 7;
		}
		if( strFieldName.equals("Sex") ) {
			return 8;
		}
		if( strFieldName.equals("Note1") ) {
			return 9;
		}
		if( strFieldName.equals("Note2") ) {
			return 10;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "RReportCode";
				break;
			case 2:
				strFieldName = "RReportName";
				break;
			case 3:
				strFieldName = "RReportClass";
				break;
			case 4:
				strFieldName = "StartMoney";
				break;
			case 5:
				strFieldName = "EndMoney";
				break;
			case 6:
				strFieldName = "StartAge";
				break;
			case 7:
				strFieldName = "EndAge";
				break;
			case 8:
				strFieldName = "Sex";
				break;
			case 9:
				strFieldName = "Note1";
				break;
			case 10:
				strFieldName = "Note2";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RReportCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RReportName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RReportClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("EndMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Note1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Note2") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
