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
import com.sinosoft.lis.db.LDInformationDB;

/*
 * <p>ClassName: LDInformationSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDInformationSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDInformationSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 资料分类 */
	private String InformationKind;
	/** 资料代码 */
	private String InformationCode;
	/** 资料名称 */
	private String InformationName;
	/** 资料额度起始值 */
	private double StartMoney;
	/** 资料额度终止值 */
	private double EndMoney;
	/** 资料年龄起始值 */
	private int StartAge;
	/** 资料年龄终止值 */
	private int EndAge;
	/** 资料人性别 */
	private String Sex;
	/** 资料说明 */
	private String Remark;
	/** 备注2 */
	private String Note2;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDInformationSchema()
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
		LDInformationSchema cloned = (LDInformationSchema)super.clone();
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
	public String getInformationKind()
	{
		return InformationKind;
	}
	public void setInformationKind(String aInformationKind)
	{
		InformationKind = aInformationKind;
	}
	public String getInformationCode()
	{
		return InformationCode;
	}
	public void setInformationCode(String aInformationCode)
	{
		InformationCode = aInformationCode;
	}
	public String getInformationName()
	{
		return InformationName;
	}
	public void setInformationName(String aInformationName)
	{
		InformationName = aInformationName;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	* 使用另外一个 LDInformationSchema 对象给 Schema 赋值
	* @param: aLDInformationSchema LDInformationSchema
	**/
	public void setSchema(LDInformationSchema aLDInformationSchema)
	{
		this.SerialNo = aLDInformationSchema.getSerialNo();
		this.InformationKind = aLDInformationSchema.getInformationKind();
		this.InformationCode = aLDInformationSchema.getInformationCode();
		this.InformationName = aLDInformationSchema.getInformationName();
		this.StartMoney = aLDInformationSchema.getStartMoney();
		this.EndMoney = aLDInformationSchema.getEndMoney();
		this.StartAge = aLDInformationSchema.getStartAge();
		this.EndAge = aLDInformationSchema.getEndAge();
		this.Sex = aLDInformationSchema.getSex();
		this.Remark = aLDInformationSchema.getRemark();
		this.Note2 = aLDInformationSchema.getNote2();
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

			if( rs.getString("InformationKind") == null )
				this.InformationKind = null;
			else
				this.InformationKind = rs.getString("InformationKind").trim();

			if( rs.getString("InformationCode") == null )
				this.InformationCode = null;
			else
				this.InformationCode = rs.getString("InformationCode").trim();

			if( rs.getString("InformationName") == null )
				this.InformationName = null;
			else
				this.InformationName = rs.getString("InformationName").trim();

			this.StartMoney = rs.getDouble("StartMoney");
			this.EndMoney = rs.getDouble("EndMoney");
			this.StartAge = rs.getInt("StartAge");
			this.EndAge = rs.getInt("EndAge");
			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Note2") == null )
				this.Note2 = null;
			else
				this.Note2 = rs.getString("Note2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDInformation表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDInformationSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDInformationSchema getSchema()
	{
		LDInformationSchema aLDInformationSchema = new LDInformationSchema();
		aLDInformationSchema.setSchema(this);
		return aLDInformationSchema;
	}

	public LDInformationDB getDB()
	{
		LDInformationDB aDBOper = new LDInformationDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDInformation描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InformationKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InformationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InformationName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Note2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDInformation>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InformationKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InformationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InformationName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			EndMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			StartAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			EndAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Note2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDInformationSchema";
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
		if (FCode.equalsIgnoreCase("InformationKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InformationKind));
		}
		if (FCode.equalsIgnoreCase("InformationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InformationCode));
		}
		if (FCode.equalsIgnoreCase("InformationName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InformationName));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(InformationKind);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InformationCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InformationName);
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
				strFieldValue = StrTool.GBKToUnicode(Remark);
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
		if (FCode.equalsIgnoreCase("InformationKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InformationKind = FValue.trim();
			}
			else
				InformationKind = null;
		}
		if (FCode.equalsIgnoreCase("InformationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InformationCode = FValue.trim();
			}
			else
				InformationCode = null;
		}
		if (FCode.equalsIgnoreCase("InformationName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InformationName = FValue.trim();
			}
			else
				InformationName = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		LDInformationSchema other = (LDInformationSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& InformationKind.equals(other.getInformationKind())
			&& InformationCode.equals(other.getInformationCode())
			&& InformationName.equals(other.getInformationName())
			&& StartMoney == other.getStartMoney()
			&& EndMoney == other.getEndMoney()
			&& StartAge == other.getStartAge()
			&& EndAge == other.getEndAge()
			&& Sex.equals(other.getSex())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("InformationKind") ) {
			return 1;
		}
		if( strFieldName.equals("InformationCode") ) {
			return 2;
		}
		if( strFieldName.equals("InformationName") ) {
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
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "InformationKind";
				break;
			case 2:
				strFieldName = "InformationCode";
				break;
			case 3:
				strFieldName = "InformationName";
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
				strFieldName = "Remark";
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
		if( strFieldName.equals("InformationKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InformationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InformationName") ) {
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
		if( strFieldName.equals("Remark") ) {
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
