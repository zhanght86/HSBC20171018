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
import com.sinosoft.lis.db.LCGrpFeeToAccDB;

/*
 * <p>ClassName: LCGrpFeeToAccSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 投连改造
 */
public class LCGrpFeeToAccSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpFeeToAccSchema.class);

	// @Field
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 管理费编码 */
	private String FeeCode;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 收取交费账户号码 */
	private String PayPlanCode1;
	/** 收取投资账户号码 */
	private String InsuAccNo1;
	/** 管理费收取顺序 */
	private int FeeNum;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpFeeToAccSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "GrpPolNo";
		pk[1] = "FeeCode";
		pk[2] = "PayPlanCode";
		pk[3] = "InsuAccNo";
		pk[4] = "PayPlanCode1";
		pk[5] = "InsuAccNo1";

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
		LCGrpFeeToAccSchema cloned = (LCGrpFeeToAccSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		if(aGrpPolNo!=null && aGrpPolNo.length()>20)
			throw new IllegalArgumentException("集体保单险种号码GrpPolNo值"+aGrpPolNo+"的长度"+aGrpPolNo.length()+"大于最大值20");
		GrpPolNo = aGrpPolNo;
	}
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		if(aFeeCode!=null && aFeeCode.length()>6)
			throw new IllegalArgumentException("管理费编码FeeCode值"+aFeeCode+"的长度"+aFeeCode.length()+"大于最大值6");
		FeeCode = aFeeCode;
	}
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		if(aPayPlanCode!=null && aPayPlanCode.length()>8)
			throw new IllegalArgumentException("交费计划编码PayPlanCode值"+aPayPlanCode+"的长度"+aPayPlanCode.length()+"大于最大值8");
		PayPlanCode = aPayPlanCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		if(aInsuAccNo!=null && aInsuAccNo.length()>20)
			throw new IllegalArgumentException("保险帐户号码InsuAccNo值"+aInsuAccNo+"的长度"+aInsuAccNo.length()+"大于最大值20");
		InsuAccNo = aInsuAccNo;
	}
	public String getPayPlanCode1()
	{
		return PayPlanCode1;
	}
	public void setPayPlanCode1(String aPayPlanCode1)
	{
		if(aPayPlanCode1!=null && aPayPlanCode1.length()>8)
			throw new IllegalArgumentException("收取交费账户号码PayPlanCode1值"+aPayPlanCode1+"的长度"+aPayPlanCode1.length()+"大于最大值8");
		PayPlanCode1 = aPayPlanCode1;
	}
	public String getInsuAccNo1()
	{
		return InsuAccNo1;
	}
	public void setInsuAccNo1(String aInsuAccNo1)
	{
		if(aInsuAccNo1!=null && aInsuAccNo1.length()>20)
			throw new IllegalArgumentException("收取投资账户号码InsuAccNo1值"+aInsuAccNo1+"的长度"+aInsuAccNo1.length()+"大于最大值20");
		InsuAccNo1 = aInsuAccNo1;
	}
	/**
	* 按照费用扣减或奖励的顺序
	*/
	public int getFeeNum()
	{
		return FeeNum;
	}
	public void setFeeNum(int aFeeNum)
	{
		FeeNum = aFeeNum;
	}
	public void setFeeNum(String aFeeNum)
	{
		if (aFeeNum != null && !aFeeNum.equals(""))
		{
			Integer tInteger = new Integer(aFeeNum);
			int i = tInteger.intValue();
			FeeNum = i;
		}
	}


	/**
	* 使用另外一个 LCGrpFeeToAccSchema 对象给 Schema 赋值
	* @param: aLCGrpFeeToAccSchema LCGrpFeeToAccSchema
	**/
	public void setSchema(LCGrpFeeToAccSchema aLCGrpFeeToAccSchema)
	{
		this.GrpPolNo = aLCGrpFeeToAccSchema.getGrpPolNo();
		this.FeeCode = aLCGrpFeeToAccSchema.getFeeCode();
		this.PayPlanCode = aLCGrpFeeToAccSchema.getPayPlanCode();
		this.InsuAccNo = aLCGrpFeeToAccSchema.getInsuAccNo();
		this.PayPlanCode1 = aLCGrpFeeToAccSchema.getPayPlanCode1();
		this.InsuAccNo1 = aLCGrpFeeToAccSchema.getInsuAccNo1();
		this.FeeNum = aLCGrpFeeToAccSchema.getFeeNum();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("PayPlanCode1") == null )
				this.PayPlanCode1 = null;
			else
				this.PayPlanCode1 = rs.getString("PayPlanCode1").trim();

			if( rs.getString("InsuAccNo1") == null )
				this.InsuAccNo1 = null;
			else
				this.InsuAccNo1 = rs.getString("InsuAccNo1").trim();

			this.FeeNum = rs.getInt("FeeNum");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpFeeToAcc表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpFeeToAccSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpFeeToAccSchema getSchema()
	{
		LCGrpFeeToAccSchema aLCGrpFeeToAccSchema = new LCGrpFeeToAccSchema();
		aLCGrpFeeToAccSchema.setSchema(this);
		return aLCGrpFeeToAccSchema;
	}

	public LCGrpFeeToAccDB getDB()
	{
		LCGrpFeeToAccDB aDBOper = new LCGrpFeeToAccDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpFeeToAcc描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeNum));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpFeeToAcc>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayPlanCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuAccNo1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpFeeToAccSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode1));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo1));
		}
		if (FCode.equalsIgnoreCase("FeeNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeNum));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo1);
				break;
			case 6:
				strFieldValue = String.valueOf(FeeNum);
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

		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode1 = FValue.trim();
			}
			else
				PayPlanCode1 = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo1 = FValue.trim();
			}
			else
				InsuAccNo1 = null;
		}
		if (FCode.equalsIgnoreCase("FeeNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FeeNum = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpFeeToAccSchema other = (LCGrpFeeToAccSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& FeeCode.equals(other.getFeeCode())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& PayPlanCode1.equals(other.getPayPlanCode1())
			&& InsuAccNo1.equals(other.getInsuAccNo1())
			&& FeeNum == other.getFeeNum();
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 1;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 3;
		}
		if( strFieldName.equals("PayPlanCode1") ) {
			return 4;
		}
		if( strFieldName.equals("InsuAccNo1") ) {
			return 5;
		}
		if( strFieldName.equals("FeeNum") ) {
			return 6;
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "FeeCode";
				break;
			case 2:
				strFieldName = "PayPlanCode";
				break;
			case 3:
				strFieldName = "InsuAccNo";
				break;
			case 4:
				strFieldName = "PayPlanCode1";
				break;
			case 5:
				strFieldName = "InsuAccNo1";
				break;
			case 6:
				strFieldName = "FeeNum";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeNum") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
