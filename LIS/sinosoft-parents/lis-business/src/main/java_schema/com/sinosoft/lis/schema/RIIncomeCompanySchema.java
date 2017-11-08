

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
import com.sinosoft.lis.db.RIIncomeCompanyDB;

/*
 * <p>ClassName: RIIncomeCompanySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIIncomeCompanySchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号码 */
	private String RIContNo;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 账单周期 */
	private String BillingCycle;
	/** 分保公司顺序编号 */
	private int IncomeCompanyOrder;
	/** 分保公司编号 */
	private String IncomeCompanyNo;
	/** 分保公司类型 */
	private String IncomeCompanyType;
	/** 备用字段1 */
	private String StandByOne;
	/** 备用字段2 */
	private String StandByTwo;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIIncomeCompanySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RIPreceptNo";
		pk[1] = "IncomeCompanyOrder";

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
		RIIncomeCompanySchema cloned = (RIIncomeCompanySchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public String getBillingCycle()
	{
		return BillingCycle;
	}
	public void setBillingCycle(String aBillingCycle)
	{
		BillingCycle = aBillingCycle;
	}
	public int getIncomeCompanyOrder()
	{
		return IncomeCompanyOrder;
	}
	public void setIncomeCompanyOrder(int aIncomeCompanyOrder)
	{
		IncomeCompanyOrder = aIncomeCompanyOrder;
	}
	public void setIncomeCompanyOrder(String aIncomeCompanyOrder)
	{
		if (aIncomeCompanyOrder != null && !aIncomeCompanyOrder.equals(""))
		{
			Integer tInteger = new Integer(aIncomeCompanyOrder);
			int i = tInteger.intValue();
			IncomeCompanyOrder = i;
		}
	}

	public String getIncomeCompanyNo()
	{
		return IncomeCompanyNo;
	}
	public void setIncomeCompanyNo(String aIncomeCompanyNo)
	{
		IncomeCompanyNo = aIncomeCompanyNo;
	}
	/**
	* 01-计算分出 02-不计算分出
	*/
	public String getIncomeCompanyType()
	{
		return IncomeCompanyType;
	}
	public void setIncomeCompanyType(String aIncomeCompanyType)
	{
		IncomeCompanyType = aIncomeCompanyType;
	}
	public String getStandByOne()
	{
		return StandByOne;
	}
	public void setStandByOne(String aStandByOne)
	{
		StandByOne = aStandByOne;
	}
	public String getStandByTwo()
	{
		return StandByTwo;
	}
	public void setStandByTwo(String aStandByTwo)
	{
		StandByTwo = aStandByTwo;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 RIIncomeCompanySchema 对象给 Schema 赋值
	* @param: aRIIncomeCompanySchema RIIncomeCompanySchema
	**/
	public void setSchema(RIIncomeCompanySchema aRIIncomeCompanySchema)
	{
		this.RIContNo = aRIIncomeCompanySchema.getRIContNo();
		this.RIPreceptNo = aRIIncomeCompanySchema.getRIPreceptNo();
		this.BillingCycle = aRIIncomeCompanySchema.getBillingCycle();
		this.IncomeCompanyOrder = aRIIncomeCompanySchema.getIncomeCompanyOrder();
		this.IncomeCompanyNo = aRIIncomeCompanySchema.getIncomeCompanyNo();
		this.IncomeCompanyType = aRIIncomeCompanySchema.getIncomeCompanyType();
		this.StandByOne = aRIIncomeCompanySchema.getStandByOne();
		this.StandByTwo = aRIIncomeCompanySchema.getStandByTwo();
		this.State = aRIIncomeCompanySchema.getState();
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
			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("BillingCycle") == null )
				this.BillingCycle = null;
			else
				this.BillingCycle = rs.getString("BillingCycle").trim();

			this.IncomeCompanyOrder = rs.getInt("IncomeCompanyOrder");
			if( rs.getString("IncomeCompanyNo") == null )
				this.IncomeCompanyNo = null;
			else
				this.IncomeCompanyNo = rs.getString("IncomeCompanyNo").trim();

			if( rs.getString("IncomeCompanyType") == null )
				this.IncomeCompanyType = null;
			else
				this.IncomeCompanyType = rs.getString("IncomeCompanyType").trim();

			if( rs.getString("StandByOne") == null )
				this.StandByOne = null;
			else
				this.StandByOne = rs.getString("StandByOne").trim();

			if( rs.getString("StandByTwo") == null )
				this.StandByTwo = null;
			else
				this.StandByTwo = rs.getString("StandByTwo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIIncomeCompany表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIIncomeCompanySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIIncomeCompanySchema getSchema()
	{
		RIIncomeCompanySchema aRIIncomeCompanySchema = new RIIncomeCompanySchema();
		aRIIncomeCompanySchema.setSchema(this);
		return aRIIncomeCompanySchema;
	}

	public RIIncomeCompanyDB getDB()
	{
		RIIncomeCompanyDB aDBOper = new RIIncomeCompanyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIIncomeCompany描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillingCycle)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(IncomeCompanyOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByOne)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByTwo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIIncomeCompany>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BillingCycle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IncomeCompanyOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			IncomeCompanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IncomeCompanyType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			StandByOne = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StandByTwo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIIncomeCompanySchema";
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
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("BillingCycle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillingCycle));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyOrder));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyNo));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyType));
		}
		if (FCode.equalsIgnoreCase("StandByOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByOne));
		}
		if (FCode.equalsIgnoreCase("StandByTwo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByTwo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BillingCycle);
				break;
			case 3:
				strFieldValue = String.valueOf(IncomeCompanyOrder);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(StandByOne);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StandByTwo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
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

		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
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
		if (FCode.equalsIgnoreCase("BillingCycle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillingCycle = FValue.trim();
			}
			else
				BillingCycle = null;
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				IncomeCompanyOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncomeCompanyNo = FValue.trim();
			}
			else
				IncomeCompanyNo = null;
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncomeCompanyType = FValue.trim();
			}
			else
				IncomeCompanyType = null;
		}
		if (FCode.equalsIgnoreCase("StandByOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByOne = FValue.trim();
			}
			else
				StandByOne = null;
		}
		if (FCode.equalsIgnoreCase("StandByTwo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByTwo = FValue.trim();
			}
			else
				StandByTwo = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIIncomeCompanySchema other = (RIIncomeCompanySchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& BillingCycle.equals(other.getBillingCycle())
			&& IncomeCompanyOrder == other.getIncomeCompanyOrder()
			&& IncomeCompanyNo.equals(other.getIncomeCompanyNo())
			&& IncomeCompanyType.equals(other.getIncomeCompanyType())
			&& StandByOne.equals(other.getStandByOne())
			&& StandByTwo.equals(other.getStandByTwo())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("RIContNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 1;
		}
		if( strFieldName.equals("BillingCycle") ) {
			return 2;
		}
		if( strFieldName.equals("IncomeCompanyOrder") ) {
			return 3;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return 4;
		}
		if( strFieldName.equals("IncomeCompanyType") ) {
			return 5;
		}
		if( strFieldName.equals("StandByOne") ) {
			return 6;
		}
		if( strFieldName.equals("StandByTwo") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "RIPreceptNo";
				break;
			case 2:
				strFieldName = "BillingCycle";
				break;
			case 3:
				strFieldName = "IncomeCompanyOrder";
				break;
			case 4:
				strFieldName = "IncomeCompanyNo";
				break;
			case 5:
				strFieldName = "IncomeCompanyType";
				break;
			case 6:
				strFieldName = "StandByOne";
				break;
			case 7:
				strFieldName = "StandByTwo";
				break;
			case 8:
				strFieldName = "State";
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
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillingCycle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncomeCompanyOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncomeCompanyType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByOne") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByTwo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
