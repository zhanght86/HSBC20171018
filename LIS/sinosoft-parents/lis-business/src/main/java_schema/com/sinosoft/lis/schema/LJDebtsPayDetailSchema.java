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
import com.sinosoft.lis.db.LJDebtsPayDetailDB;

/*
 * <p>ClassName: LJDebtsPayDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJDebtsPayDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJDebtsPayDetailSchema.class);
	// @Field
	/** 坏账流水号 */
	private String DebtsNo;
	/** 子流水号 */
	private String DebtsSubNo;
	/** 业务类型 */
	private String BussType;
	/** 业务号码 */
	private String BussNo;
	/** 应收金额 */
	private double DebtsMoney;
	/** 实收金额 */
	private double ActualMoney;
	/** 应收日期 */
	private Date ShouldDate;
	/** 坏账标识 */
	private String DebtsFlag;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJDebtsPayDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "DebtsNo";
		pk[1] = "DebtsSubNo";

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
		LJDebtsPayDetailSchema cloned = (LJDebtsPayDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDebtsNo()
	{
		return DebtsNo;
	}
	public void setDebtsNo(String aDebtsNo)
	{
		if(aDebtsNo!=null && aDebtsNo.length()>20)
			throw new IllegalArgumentException("坏账流水号DebtsNo值"+aDebtsNo+"的长度"+aDebtsNo.length()+"大于最大值20");
		DebtsNo = aDebtsNo;
	}
	public String getDebtsSubNo()
	{
		return DebtsSubNo;
	}
	public void setDebtsSubNo(String aDebtsSubNo)
	{
		if(aDebtsSubNo!=null && aDebtsSubNo.length()>20)
			throw new IllegalArgumentException("子流水号DebtsSubNo值"+aDebtsSubNo+"的长度"+aDebtsSubNo.length()+"大于最大值20");
		DebtsSubNo = aDebtsSubNo;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>2)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值2");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public double getDebtsMoney()
	{
		return DebtsMoney;
	}
	public void setDebtsMoney(double aDebtsMoney)
	{
		DebtsMoney = aDebtsMoney;
	}
	public void setDebtsMoney(String aDebtsMoney)
	{
		if (aDebtsMoney != null && !aDebtsMoney.equals(""))
		{
			Double tDouble = new Double(aDebtsMoney);
			double d = tDouble.doubleValue();
			DebtsMoney = d;
		}
	}

	public double getActualMoney()
	{
		return ActualMoney;
	}
	public void setActualMoney(double aActualMoney)
	{
		ActualMoney = aActualMoney;
	}
	public void setActualMoney(String aActualMoney)
	{
		if (aActualMoney != null && !aActualMoney.equals(""))
		{
			Double tDouble = new Double(aActualMoney);
			double d = tDouble.doubleValue();
			ActualMoney = d;
		}
	}

	public String getShouldDate()
	{
		if( ShouldDate != null )
			return fDate.getString(ShouldDate);
		else
			return null;
	}
	public void setShouldDate(Date aShouldDate)
	{
		ShouldDate = aShouldDate;
	}
	public void setShouldDate(String aShouldDate)
	{
		if (aShouldDate != null && !aShouldDate.equals("") )
		{
			ShouldDate = fDate.getDate( aShouldDate );
		}
		else
			ShouldDate = null;
	}

	/**
	* 0-否，1-是
	*/
	public String getDebtsFlag()
	{
		return DebtsFlag;
	}
	public void setDebtsFlag(String aDebtsFlag)
	{
		if(aDebtsFlag!=null && aDebtsFlag.length()>2)
			throw new IllegalArgumentException("坏账标识DebtsFlag值"+aDebtsFlag+"的长度"+aDebtsFlag.length()+"大于最大值2");
		DebtsFlag = aDebtsFlag;
	}

	/**
	* 使用另外一个 LJDebtsPayDetailSchema 对象给 Schema 赋值
	* @param: aLJDebtsPayDetailSchema LJDebtsPayDetailSchema
	**/
	public void setSchema(LJDebtsPayDetailSchema aLJDebtsPayDetailSchema)
	{
		this.DebtsNo = aLJDebtsPayDetailSchema.getDebtsNo();
		this.DebtsSubNo = aLJDebtsPayDetailSchema.getDebtsSubNo();
		this.BussType = aLJDebtsPayDetailSchema.getBussType();
		this.BussNo = aLJDebtsPayDetailSchema.getBussNo();
		this.DebtsMoney = aLJDebtsPayDetailSchema.getDebtsMoney();
		this.ActualMoney = aLJDebtsPayDetailSchema.getActualMoney();
		this.ShouldDate = fDate.getDate( aLJDebtsPayDetailSchema.getShouldDate());
		this.DebtsFlag = aLJDebtsPayDetailSchema.getDebtsFlag();
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
			if( rs.getString("DebtsNo") == null )
				this.DebtsNo = null;
			else
				this.DebtsNo = rs.getString("DebtsNo").trim();

			if( rs.getString("DebtsSubNo") == null )
				this.DebtsSubNo = null;
			else
				this.DebtsSubNo = rs.getString("DebtsSubNo").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			this.DebtsMoney = rs.getDouble("DebtsMoney");
			this.ActualMoney = rs.getDouble("ActualMoney");
			this.ShouldDate = rs.getDate("ShouldDate");
			if( rs.getString("DebtsFlag") == null )
				this.DebtsFlag = null;
			else
				this.DebtsFlag = rs.getString("DebtsFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJDebtsPayDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJDebtsPayDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJDebtsPayDetailSchema getSchema()
	{
		LJDebtsPayDetailSchema aLJDebtsPayDetailSchema = new LJDebtsPayDetailSchema();
		aLJDebtsPayDetailSchema.setSchema(this);
		return aLJDebtsPayDetailSchema;
	}

	public LJDebtsPayDetailDB getDB()
	{
		LJDebtsPayDetailDB aDBOper = new LJDebtsPayDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJDebtsPayDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DebtsNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DebtsSubNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DebtsMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ActualMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ShouldDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DebtsFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJDebtsPayDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DebtsNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DebtsSubNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DebtsMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			ActualMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			ShouldDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			DebtsFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJDebtsPayDetailSchema";
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
		if (FCode.equalsIgnoreCase("DebtsNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsNo));
		}
		if (FCode.equalsIgnoreCase("DebtsSubNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsSubNo));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("DebtsMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsMoney));
		}
		if (FCode.equalsIgnoreCase("ActualMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActualMoney));
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
		}
		if (FCode.equalsIgnoreCase("DebtsFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebtsFlag));
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
				strFieldValue = StrTool.GBKToUnicode(DebtsNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DebtsSubNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 4:
				strFieldValue = String.valueOf(DebtsMoney);
				break;
			case 5:
				strFieldValue = String.valueOf(ActualMoney);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getShouldDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DebtsFlag);
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

		if (FCode.equalsIgnoreCase("DebtsNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebtsNo = FValue.trim();
			}
			else
				DebtsNo = null;
		}
		if (FCode.equalsIgnoreCase("DebtsSubNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebtsSubNo = FValue.trim();
			}
			else
				DebtsSubNo = null;
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("DebtsMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DebtsMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ActualMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ActualMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ShouldDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ShouldDate = fDate.getDate( FValue );
			}
			else
				ShouldDate = null;
		}
		if (FCode.equalsIgnoreCase("DebtsFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebtsFlag = FValue.trim();
			}
			else
				DebtsFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJDebtsPayDetailSchema other = (LJDebtsPayDetailSchema)otherObject;
		return
			DebtsNo.equals(other.getDebtsNo())
			&& DebtsSubNo.equals(other.getDebtsSubNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& DebtsMoney == other.getDebtsMoney()
			&& ActualMoney == other.getActualMoney()
			&& fDate.getString(ShouldDate).equals(other.getShouldDate())
			&& DebtsFlag.equals(other.getDebtsFlag());
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
		if( strFieldName.equals("DebtsNo") ) {
			return 0;
		}
		if( strFieldName.equals("DebtsSubNo") ) {
			return 1;
		}
		if( strFieldName.equals("BussType") ) {
			return 2;
		}
		if( strFieldName.equals("BussNo") ) {
			return 3;
		}
		if( strFieldName.equals("DebtsMoney") ) {
			return 4;
		}
		if( strFieldName.equals("ActualMoney") ) {
			return 5;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return 6;
		}
		if( strFieldName.equals("DebtsFlag") ) {
			return 7;
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
				strFieldName = "DebtsNo";
				break;
			case 1:
				strFieldName = "DebtsSubNo";
				break;
			case 2:
				strFieldName = "BussType";
				break;
			case 3:
				strFieldName = "BussNo";
				break;
			case 4:
				strFieldName = "DebtsMoney";
				break;
			case 5:
				strFieldName = "ActualMoney";
				break;
			case 6:
				strFieldName = "ShouldDate";
				break;
			case 7:
				strFieldName = "DebtsFlag";
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
		if( strFieldName.equals("DebtsNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DebtsSubNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DebtsMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ActualMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ShouldDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DebtsFlag") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
