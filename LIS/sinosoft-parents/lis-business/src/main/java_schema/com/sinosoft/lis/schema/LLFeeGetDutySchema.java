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
import com.sinosoft.lis.db.LLFeeGetDutyDB;

/*
 * <p>ClassName: LLFeeGetDutySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class LLFeeGetDutySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLFeeGetDutySchema.class);
	// @Field
	/** 立案号 */
	private String RgtNo;
	/** 账单号 */
	private String MainFeeNo;
	/** 保单号 */
	private String ContNo;
	/** 客户号 */
	private String CustomerNo;
	/** 责任编码 */
	private String DutyCode;
	/** 给付责任编码 */
	private String GetDutyCode;
	/** 责任类型 */
	private String DutyType;
	/** 自付比例/免赔额已支付标记 */
	private String PaidFlag;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLFeeGetDutySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "RgtNo";
		pk[1] = "MainFeeNo";
		pk[2] = "ContNo";
		pk[3] = "CustomerNo";
		pk[4] = "DutyCode";
		pk[5] = "GetDutyCode";

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
		LLFeeGetDutySchema cloned = (LLFeeGetDutySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("立案号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	public String getMainFeeNo()
	{
		return MainFeeNo;
	}
	public void setMainFeeNo(String aMainFeeNo)
	{
		if(aMainFeeNo!=null && aMainFeeNo.length()>20)
			throw new IllegalArgumentException("账单号MainFeeNo值"+aMainFeeNo+"的长度"+aMainFeeNo.length()+"大于最大值20");
		MainFeeNo = aMainFeeNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>20)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值20");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>20)
			throw new IllegalArgumentException("给付责任编码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值20");
		GetDutyCode = aGetDutyCode;
	}
	public String getDutyType()
	{
		return DutyType;
	}
	public void setDutyType(String aDutyType)
	{
		if(aDutyType!=null && aDutyType.length()>6)
			throw new IllegalArgumentException("责任类型DutyType值"+aDutyType+"的长度"+aDutyType.length()+"大于最大值6");
		DutyType = aDutyType;
	}
	public String getPaidFlag()
	{
		return PaidFlag;
	}
	public void setPaidFlag(String aPaidFlag)
	{
		if(aPaidFlag!=null && aPaidFlag.length()>1)
			throw new IllegalArgumentException("自付比例/免赔额已支付标记PaidFlag值"+aPaidFlag+"的长度"+aPaidFlag.length()+"大于最大值1");
		PaidFlag = aPaidFlag;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>200)
			throw new IllegalArgumentException("备用字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值200");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>200)
			throw new IllegalArgumentException("备用字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值200");
		StandbyFlag2 = aStandbyFlag2;
	}

	/**
	* 使用另外一个 LLFeeGetDutySchema 对象给 Schema 赋值
	* @param: aLLFeeGetDutySchema LLFeeGetDutySchema
	**/
	public void setSchema(LLFeeGetDutySchema aLLFeeGetDutySchema)
	{
		this.RgtNo = aLLFeeGetDutySchema.getRgtNo();
		this.MainFeeNo = aLLFeeGetDutySchema.getMainFeeNo();
		this.ContNo = aLLFeeGetDutySchema.getContNo();
		this.CustomerNo = aLLFeeGetDutySchema.getCustomerNo();
		this.DutyCode = aLLFeeGetDutySchema.getDutyCode();
		this.GetDutyCode = aLLFeeGetDutySchema.getGetDutyCode();
		this.DutyType = aLLFeeGetDutySchema.getDutyType();
		this.PaidFlag = aLLFeeGetDutySchema.getPaidFlag();
		this.StandbyFlag1 = aLLFeeGetDutySchema.getStandbyFlag1();
		this.StandbyFlag2 = aLLFeeGetDutySchema.getStandbyFlag2();
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
			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("MainFeeNo") == null )
				this.MainFeeNo = null;
			else
				this.MainFeeNo = rs.getString("MainFeeNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("DutyType") == null )
				this.DutyType = null;
			else
				this.DutyType = rs.getString("DutyType").trim();

			if( rs.getString("PaidFlag") == null )
				this.PaidFlag = null;
			else
				this.PaidFlag = rs.getString("PaidFlag").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLFeeGetDuty表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLFeeGetDutySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLFeeGetDutySchema getSchema()
	{
		LLFeeGetDutySchema aLLFeeGetDutySchema = new LLFeeGetDutySchema();
		aLLFeeGetDutySchema.setSchema(this);
		return aLLFeeGetDutySchema;
	}

	public LLFeeGetDutyDB getDB()
	{
		LLFeeGetDutyDB aDBOper = new LLFeeGetDutyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLFeeGetDuty描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PaidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLFeeGetDuty>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MainFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PaidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLFeeGetDutySchema";
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
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFeeNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyType));
		}
		if (FCode.equalsIgnoreCase("PaidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaidFlag));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MainFeeNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(PaidFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
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

		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
		}
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFeeNo = FValue.trim();
			}
			else
				MainFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyType = FValue.trim();
			}
			else
				DutyType = null;
		}
		if (FCode.equalsIgnoreCase("PaidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaidFlag = FValue.trim();
			}
			else
				PaidFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLFeeGetDutySchema other = (LLFeeGetDutySchema)otherObject;
		return
			RgtNo.equals(other.getRgtNo())
			&& MainFeeNo.equals(other.getMainFeeNo())
			&& ContNo.equals(other.getContNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& DutyType.equals(other.getDutyType())
			&& PaidFlag.equals(other.getPaidFlag())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2());
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
		if( strFieldName.equals("RgtNo") ) {
			return 0;
		}
		if( strFieldName.equals("MainFeeNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("DutyType") ) {
			return 6;
		}
		if( strFieldName.equals("PaidFlag") ) {
			return 7;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 8;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
				strFieldName = "RgtNo";
				break;
			case 1:
				strFieldName = "MainFeeNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "GetDutyCode";
				break;
			case 6:
				strFieldName = "DutyType";
				break;
			case 7:
				strFieldName = "PaidFlag";
				break;
			case 8:
				strFieldName = "StandbyFlag1";
				break;
			case 9:
				strFieldName = "StandbyFlag2";
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
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
