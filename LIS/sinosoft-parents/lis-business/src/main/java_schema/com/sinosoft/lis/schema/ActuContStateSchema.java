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
import com.sinosoft.lis.db.ActuContStateDB;

/*
 * <p>ClassName: ActuContStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class ActuContStateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ActuContStateSchema.class);

	// @Field
	/** 流水号 */
	private int WatNum;
	/** 保单号 */
	private String ContNo;
	/** 客户号 */
	private String CustomerNo;
	/** 主、附险标识 */
	private String MainFlag;
	/** 险种代码 */
	private String RiskCode;
	/** 变动序号 */
	private int ChangeNo;
	/** 保单变动原因 */
	private String ChangeReason;
	/** 退保金额 */
	private double CtMoney;
	/** 变动日期 */
	private Date ChangeDate;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ActuContStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "WatNum";

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
		ActuContStateSchema cloned = (ActuContStateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getWatNum()
	{
		return WatNum;
	}
	public void setWatNum(int aWatNum)
	{
		WatNum = aWatNum;
	}
	public void setWatNum(String aWatNum)
	{
		if (aWatNum != null && !aWatNum.equals(""))
		{
			Integer tInteger = new Integer(aWatNum);
			int i = tInteger.intValue();
			WatNum = i;
		}
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getMainFlag()
	{
		return MainFlag;
	}
	public void setMainFlag(String aMainFlag)
	{
		MainFlag = aMainFlag;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public int getChangeNo()
	{
		return ChangeNo;
	}
	public void setChangeNo(int aChangeNo)
	{
		ChangeNo = aChangeNo;
	}
	public void setChangeNo(String aChangeNo)
	{
		if (aChangeNo != null && !aChangeNo.equals(""))
		{
			Integer tInteger = new Integer(aChangeNo);
			int i = tInteger.intValue();
			ChangeNo = i;
		}
	}

	public String getChangeReason()
	{
		return ChangeReason;
	}
	public void setChangeReason(String aChangeReason)
	{
		ChangeReason = aChangeReason;
	}
	public double getCtMoney()
	{
		return CtMoney;
	}
	public void setCtMoney(double aCtMoney)
	{
		CtMoney = aCtMoney;
	}
	public void setCtMoney(String aCtMoney)
	{
		if (aCtMoney != null && !aCtMoney.equals(""))
		{
			Double tDouble = new Double(aCtMoney);
			double d = tDouble.doubleValue();
			CtMoney = d;
		}
	}

	public String getChangeDate()
	{
		if( ChangeDate != null )
			return fDate.getString(ChangeDate);
		else
			return null;
	}
	public void setChangeDate(Date aChangeDate)
	{
		ChangeDate = aChangeDate;
	}
	public void setChangeDate(String aChangeDate)
	{
		if (aChangeDate != null && !aChangeDate.equals("") )
		{
			ChangeDate = fDate.getDate( aChangeDate );
		}
		else
			ChangeDate = null;
	}


	/**
	* 使用另外一个 ActuContStateSchema 对象给 Schema 赋值
	* @param: aActuContStateSchema ActuContStateSchema
	**/
	public void setSchema(ActuContStateSchema aActuContStateSchema)
	{
		this.WatNum = aActuContStateSchema.getWatNum();
		this.ContNo = aActuContStateSchema.getContNo();
		this.CustomerNo = aActuContStateSchema.getCustomerNo();
		this.MainFlag = aActuContStateSchema.getMainFlag();
		this.RiskCode = aActuContStateSchema.getRiskCode();
		this.ChangeNo = aActuContStateSchema.getChangeNo();
		this.ChangeReason = aActuContStateSchema.getChangeReason();
		this.CtMoney = aActuContStateSchema.getCtMoney();
		this.ChangeDate = fDate.getDate( aActuContStateSchema.getChangeDate());
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
			this.WatNum = rs.getInt("WatNum");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("MainFlag") == null )
				this.MainFlag = null;
			else
				this.MainFlag = rs.getString("MainFlag").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.ChangeNo = rs.getInt("ChangeNo");
			if( rs.getString("ChangeReason") == null )
				this.ChangeReason = null;
			else
				this.ChangeReason = rs.getString("ChangeReason").trim();

			this.CtMoney = rs.getDouble("CtMoney");
			this.ChangeDate = rs.getDate("ChangeDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ActuContState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActuContStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ActuContStateSchema getSchema()
	{
		ActuContStateSchema aActuContStateSchema = new ActuContStateSchema();
		aActuContStateSchema.setSchema(this);
		return aActuContStateSchema;
	}

	public ActuContStateDB getDB()
	{
		ActuContStateDB aDBOper = new ActuContStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActuContState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(WatNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChangeNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChangeReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ChangeDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActuContState>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			WatNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MainFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ChangeNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			ChangeReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CtMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			ChangeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActuContStateSchema";
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
		if (FCode.equalsIgnoreCase("WatNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WatNum));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFlag));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ChangeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeNo));
		}
		if (FCode.equalsIgnoreCase("ChangeReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChangeReason));
		}
		if (FCode.equalsIgnoreCase("CtMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtMoney));
		}
		if (FCode.equalsIgnoreCase("ChangeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getChangeDate()));
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
				strFieldValue = String.valueOf(WatNum);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MainFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 5:
				strFieldValue = String.valueOf(ChangeNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ChangeReason);
				break;
			case 7:
				strFieldValue = String.valueOf(CtMoney);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getChangeDate()));
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

		if (FCode.equalsIgnoreCase("WatNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WatNum = i;
			}
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
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFlag = FValue.trim();
			}
			else
				MainFlag = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ChangeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ChangeNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ChangeReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChangeReason = FValue.trim();
			}
			else
				ChangeReason = null;
		}
		if (FCode.equalsIgnoreCase("CtMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CtMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChangeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ChangeDate = fDate.getDate( FValue );
			}
			else
				ChangeDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ActuContStateSchema other = (ActuContStateSchema)otherObject;
		return
			WatNum == other.getWatNum()
			&& ContNo.equals(other.getContNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& MainFlag.equals(other.getMainFlag())
			&& RiskCode.equals(other.getRiskCode())
			&& ChangeNo == other.getChangeNo()
			&& ChangeReason.equals(other.getChangeReason())
			&& CtMoney == other.getCtMoney()
			&& fDate.getString(ChangeDate).equals(other.getChangeDate());
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
		if( strFieldName.equals("WatNum") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("MainFlag") ) {
			return 3;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 4;
		}
		if( strFieldName.equals("ChangeNo") ) {
			return 5;
		}
		if( strFieldName.equals("ChangeReason") ) {
			return 6;
		}
		if( strFieldName.equals("CtMoney") ) {
			return 7;
		}
		if( strFieldName.equals("ChangeDate") ) {
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
				strFieldName = "WatNum";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "MainFlag";
				break;
			case 4:
				strFieldName = "RiskCode";
				break;
			case 5:
				strFieldName = "ChangeNo";
				break;
			case 6:
				strFieldName = "ChangeReason";
				break;
			case 7:
				strFieldName = "CtMoney";
				break;
			case 8:
				strFieldName = "ChangeDate";
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
		if( strFieldName.equals("WatNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChangeNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ChangeReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChangeDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
