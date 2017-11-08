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
import com.sinosoft.lis.db.LJRefundConfirmSubDB;

/*
 * <p>ClassName: LJRefundConfirmSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJRefundConfirmSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJRefundConfirmSubSchema.class);
	// @Field
	/** 申请批次号 */
	private String ApplyBatNo;
	/** 金额 */
	private double RefundMoney;
	/** 客户开户行 */
	private String CustBankCode;
	/** 银行编码 */
	private String BankCode;
	/** 客户开户行省 */
	private String CustBankProvince;
	/** 客户开户行市 */
	private String CustBankCity;
	/** 客户开户行明细 */
	private String CustBankDetail;
	/** 客户银行账号 */
	private String CustBankAccNo;
	/** 客户账户名 */
	private String CustAccName;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJRefundConfirmSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ApplyBatNo";

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
		LJRefundConfirmSubSchema cloned = (LJRefundConfirmSubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getApplyBatNo()
	{
		return ApplyBatNo;
	}
	public void setApplyBatNo(String aApplyBatNo)
	{
		if(aApplyBatNo!=null && aApplyBatNo.length()>20)
			throw new IllegalArgumentException("申请批次号ApplyBatNo值"+aApplyBatNo+"的长度"+aApplyBatNo.length()+"大于最大值20");
		ApplyBatNo = aApplyBatNo;
	}
	public double getRefundMoney()
	{
		return RefundMoney;
	}
	public void setRefundMoney(double aRefundMoney)
	{
		RefundMoney = aRefundMoney;
	}
	public void setRefundMoney(String aRefundMoney)
	{
		if (aRefundMoney != null && !aRefundMoney.equals(""))
		{
			Double tDouble = new Double(aRefundMoney);
			double d = tDouble.doubleValue();
			RefundMoney = d;
		}
	}

	public String getCustBankCode()
	{
		return CustBankCode;
	}
	public void setCustBankCode(String aCustBankCode)
	{
		if(aCustBankCode!=null && aCustBankCode.length()>30)
			throw new IllegalArgumentException("客户开户行CustBankCode值"+aCustBankCode+"的长度"+aCustBankCode.length()+"大于最大值30");
		CustBankCode = aCustBankCode;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		if(aBankCode!=null && aBankCode.length()>30)
			throw new IllegalArgumentException("银行编码BankCode值"+aBankCode+"的长度"+aBankCode.length()+"大于最大值30");
		BankCode = aBankCode;
	}
	public String getCustBankProvince()
	{
		return CustBankProvince;
	}
	public void setCustBankProvince(String aCustBankProvince)
	{
		if(aCustBankProvince!=null && aCustBankProvince.length()>30)
			throw new IllegalArgumentException("客户开户行省CustBankProvince值"+aCustBankProvince+"的长度"+aCustBankProvince.length()+"大于最大值30");
		CustBankProvince = aCustBankProvince;
	}
	public String getCustBankCity()
	{
		return CustBankCity;
	}
	public void setCustBankCity(String aCustBankCity)
	{
		if(aCustBankCity!=null && aCustBankCity.length()>30)
			throw new IllegalArgumentException("客户开户行市CustBankCity值"+aCustBankCity+"的长度"+aCustBankCity.length()+"大于最大值30");
		CustBankCity = aCustBankCity;
	}
	public String getCustBankDetail()
	{
		return CustBankDetail;
	}
	public void setCustBankDetail(String aCustBankDetail)
	{
		if(aCustBankDetail!=null && aCustBankDetail.length()>300)
			throw new IllegalArgumentException("客户开户行明细CustBankDetail值"+aCustBankDetail+"的长度"+aCustBankDetail.length()+"大于最大值300");
		CustBankDetail = aCustBankDetail;
	}
	public String getCustBankAccNo()
	{
		return CustBankAccNo;
	}
	public void setCustBankAccNo(String aCustBankAccNo)
	{
		if(aCustBankAccNo!=null && aCustBankAccNo.length()>30)
			throw new IllegalArgumentException("客户银行账号CustBankAccNo值"+aCustBankAccNo+"的长度"+aCustBankAccNo.length()+"大于最大值30");
		CustBankAccNo = aCustBankAccNo;
	}
	public String getCustAccName()
	{
		return CustAccName;
	}
	public void setCustAccName(String aCustAccName)
	{
		if(aCustAccName!=null && aCustAccName.length()>200)
			throw new IllegalArgumentException("客户账户名CustAccName值"+aCustAccName+"的长度"+aCustAccName.length()+"大于最大值200");
		CustAccName = aCustAccName;
	}

	/**
	* 使用另外一个 LJRefundConfirmSubSchema 对象给 Schema 赋值
	* @param: aLJRefundConfirmSubSchema LJRefundConfirmSubSchema
	**/
	public void setSchema(LJRefundConfirmSubSchema aLJRefundConfirmSubSchema)
	{
		this.ApplyBatNo = aLJRefundConfirmSubSchema.getApplyBatNo();
		this.RefundMoney = aLJRefundConfirmSubSchema.getRefundMoney();
		this.CustBankCode = aLJRefundConfirmSubSchema.getCustBankCode();
		this.BankCode = aLJRefundConfirmSubSchema.getBankCode();
		this.CustBankProvince = aLJRefundConfirmSubSchema.getCustBankProvince();
		this.CustBankCity = aLJRefundConfirmSubSchema.getCustBankCity();
		this.CustBankDetail = aLJRefundConfirmSubSchema.getCustBankDetail();
		this.CustBankAccNo = aLJRefundConfirmSubSchema.getCustBankAccNo();
		this.CustAccName = aLJRefundConfirmSubSchema.getCustAccName();
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
			if( rs.getString("ApplyBatNo") == null )
				this.ApplyBatNo = null;
			else
				this.ApplyBatNo = rs.getString("ApplyBatNo").trim();

			this.RefundMoney = rs.getDouble("RefundMoney");
			if( rs.getString("CustBankCode") == null )
				this.CustBankCode = null;
			else
				this.CustBankCode = rs.getString("CustBankCode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("CustBankProvince") == null )
				this.CustBankProvince = null;
			else
				this.CustBankProvince = rs.getString("CustBankProvince").trim();

			if( rs.getString("CustBankCity") == null )
				this.CustBankCity = null;
			else
				this.CustBankCity = rs.getString("CustBankCity").trim();

			if( rs.getString("CustBankDetail") == null )
				this.CustBankDetail = null;
			else
				this.CustBankDetail = rs.getString("CustBankDetail").trim();

			if( rs.getString("CustBankAccNo") == null )
				this.CustBankAccNo = null;
			else
				this.CustBankAccNo = rs.getString("CustBankAccNo").trim();

			if( rs.getString("CustAccName") == null )
				this.CustAccName = null;
			else
				this.CustAccName = rs.getString("CustAccName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJRefundConfirmSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJRefundConfirmSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJRefundConfirmSubSchema getSchema()
	{
		LJRefundConfirmSubSchema aLJRefundConfirmSubSchema = new LJRefundConfirmSubSchema();
		aLJRefundConfirmSubSchema.setSchema(this);
		return aLJRefundConfirmSubSchema;
	}

	public LJRefundConfirmSubDB getDB()
	{
		LJRefundConfirmSubDB aDBOper = new LJRefundConfirmSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJRefundConfirmSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RefundMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankDetail)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustAccName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJRefundConfirmSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RefundMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).doubleValue();
			CustBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustBankDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CustBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJRefundConfirmSubSchema";
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
		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyBatNo));
		}
		if (FCode.equalsIgnoreCase("RefundMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefundMoney));
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCode));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankProvince));
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCity));
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankDetail));
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankAccNo));
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustAccName));
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
				strFieldValue = StrTool.GBKToUnicode(ApplyBatNo);
				break;
			case 1:
				strFieldValue = String.valueOf(RefundMoney);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustBankCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustBankProvince);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustBankCity);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustBankDetail);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CustBankAccNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustAccName);
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

		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyBatNo = FValue.trim();
			}
			else
				ApplyBatNo = null;
		}
		if (FCode.equalsIgnoreCase("RefundMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RefundMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCode = FValue.trim();
			}
			else
				CustBankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("CustBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankProvince = FValue.trim();
			}
			else
				CustBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("CustBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCity = FValue.trim();
			}
			else
				CustBankCity = null;
		}
		if (FCode.equalsIgnoreCase("CustBankDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankDetail = FValue.trim();
			}
			else
				CustBankDetail = null;
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankAccNo = FValue.trim();
			}
			else
				CustBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustAccName = FValue.trim();
			}
			else
				CustAccName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJRefundConfirmSubSchema other = (LJRefundConfirmSubSchema)otherObject;
		return
			ApplyBatNo.equals(other.getApplyBatNo())
			&& RefundMoney == other.getRefundMoney()
			&& CustBankCode.equals(other.getCustBankCode())
			&& BankCode.equals(other.getBankCode())
			&& CustBankProvince.equals(other.getCustBankProvince())
			&& CustBankCity.equals(other.getCustBankCity())
			&& CustBankDetail.equals(other.getCustBankDetail())
			&& CustBankAccNo.equals(other.getCustBankAccNo())
			&& CustAccName.equals(other.getCustAccName());
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return 0;
		}
		if( strFieldName.equals("RefundMoney") ) {
			return 1;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return 2;
		}
		if( strFieldName.equals("BankCode") ) {
			return 3;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return 4;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return 5;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return 6;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return 7;
		}
		if( strFieldName.equals("CustAccName") ) {
			return 8;
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
				strFieldName = "ApplyBatNo";
				break;
			case 1:
				strFieldName = "RefundMoney";
				break;
			case 2:
				strFieldName = "CustBankCode";
				break;
			case 3:
				strFieldName = "BankCode";
				break;
			case 4:
				strFieldName = "CustBankProvince";
				break;
			case 5:
				strFieldName = "CustBankCity";
				break;
			case 6:
				strFieldName = "CustBankDetail";
				break;
			case 7:
				strFieldName = "CustBankAccNo";
				break;
			case 8:
				strFieldName = "CustAccName";
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RefundMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankDetail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustAccName") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
