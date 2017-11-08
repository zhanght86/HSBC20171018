

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
import com.sinosoft.lis.db.RIRecordDB;

/*
 * <p>ClassName: RIRecordSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIRecordSchema implements Schema, Cloneable
{
	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 其他号码 */
	private String OtherNo;
	/** 合同号码 */
	private String ContNo;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 区域编号 */
	private String AreaID;
	/** 当前保额 */
	private double CurentAmnt;
	/** 分出保额 */
	private double CessionAmount;
	/** 分保比例 */
	private double CessionRate;
	/** 备用字段 */
	private String StandbyFlag;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIRecordSchema()
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
		RIRecordSchema cloned = (RIRecordSchema)super.clone();
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
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	/**
	* AccumulateMode = 01-个人单合同累计  OtherNo=contno 个人合同号码<p>
	* AccumulateMode = 02-个人多合同累计  OtherNo=insuredno 个人客户号码<p>
	* AccumulateMode = 03-多人多合同累计  OtherNo=“000000”
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 团单为contno 个单为contno + ',' + insuredno
	*/
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getRIPreceptNo()
	{
		return RIPreceptNo;
	}
	public void setRIPreceptNo(String aRIPreceptNo)
	{
		RIPreceptNo = aRIPreceptNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getAreaID()
	{
		return AreaID;
	}
	public void setAreaID(String aAreaID)
	{
		AreaID = aAreaID;
	}
	public double getCurentAmnt()
	{
		return CurentAmnt;
	}
	public void setCurentAmnt(double aCurentAmnt)
	{
		CurentAmnt = aCurentAmnt;
	}
	public void setCurentAmnt(String aCurentAmnt)
	{
		if (aCurentAmnt != null && !aCurentAmnt.equals(""))
		{
			Double tDouble = new Double(aCurentAmnt);
			double d = tDouble.doubleValue();
			CurentAmnt = d;
		}
	}

	public double getCessionAmount()
	{
		return CessionAmount;
	}
	public void setCessionAmount(double aCessionAmount)
	{
		CessionAmount = aCessionAmount;
	}
	public void setCessionAmount(String aCessionAmount)
	{
		if (aCessionAmount != null && !aCessionAmount.equals(""))
		{
			Double tDouble = new Double(aCessionAmount);
			double d = tDouble.doubleValue();
			CessionAmount = d;
		}
	}

	public double getCessionRate()
	{
		return CessionRate;
	}
	public void setCessionRate(double aCessionRate)
	{
		CessionRate = aCessionRate;
	}
	public void setCessionRate(String aCessionRate)
	{
		if (aCessionRate != null && !aCessionRate.equals(""))
		{
			Double tDouble = new Double(aCessionRate);
			double d = tDouble.doubleValue();
			CessionRate = d;
		}
	}

	public String getStandbyFlag()
	{
		return StandbyFlag;
	}
	public void setStandbyFlag(String aStandbyFlag)
	{
		StandbyFlag = aStandbyFlag;
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
	* 使用另外一个 RIRecordSchema 对象给 Schema 赋值
	* @param: aRIRecordSchema RIRecordSchema
	**/
	public void setSchema(RIRecordSchema aRIRecordSchema)
	{
		this.SerialNo = aRIRecordSchema.getSerialNo();
		this.AccumulateDefNO = aRIRecordSchema.getAccumulateDefNO();
		this.OtherNo = aRIRecordSchema.getOtherNo();
		this.ContNo = aRIRecordSchema.getContNo();
		this.RIPreceptNo = aRIRecordSchema.getRIPreceptNo();
		this.RiskCode = aRIRecordSchema.getRiskCode();
		this.DutyCode = aRIRecordSchema.getDutyCode();
		this.AreaID = aRIRecordSchema.getAreaID();
		this.CurentAmnt = aRIRecordSchema.getCurentAmnt();
		this.CessionAmount = aRIRecordSchema.getCessionAmount();
		this.CessionRate = aRIRecordSchema.getCessionRate();
		this.StandbyFlag = aRIRecordSchema.getStandbyFlag();
		this.State = aRIRecordSchema.getState();
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

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("RIPreceptNo") == null )
				this.RIPreceptNo = null;
			else
				this.RIPreceptNo = rs.getString("RIPreceptNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("AreaID") == null )
				this.AreaID = null;
			else
				this.AreaID = rs.getString("AreaID").trim();

			this.CurentAmnt = rs.getDouble("CurentAmnt");
			this.CessionAmount = rs.getDouble("CessionAmount");
			this.CessionRate = rs.getDouble("CessionRate");
			if( rs.getString("StandbyFlag") == null )
				this.StandbyFlag = null;
			else
				this.StandbyFlag = rs.getString("StandbyFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIRecord表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIRecordSchema getSchema()
	{
		RIRecordSchema aRIRecordSchema = new RIRecordSchema();
		aRIRecordSchema.setSchema(this);
		return aRIRecordSchema;
	}

	public RIRecordDB getDB()
	{
		RIRecordDB aDBOper = new RIRecordDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRecord描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurentAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionAmount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRecord>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AreaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CurentAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			CessionAmount = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			CessionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRecordSchema";
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
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIPreceptNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaID));
		}
		if (FCode.equalsIgnoreCase("CurentAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurentAmnt));
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionAmount));
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionRate));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AreaID);
				break;
			case 8:
				strFieldValue = String.valueOf(CurentAmnt);
				break;
			case 9:
				strFieldValue = String.valueOf(CessionAmount);
				break;
			case 10:
				strFieldValue = String.valueOf(CessionRate);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag);
				break;
			case 12:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
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
		if (FCode.equalsIgnoreCase("RIPreceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIPreceptNo = FValue.trim();
			}
			else
				RIPreceptNo = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaID = FValue.trim();
			}
			else
				AreaID = null;
		}
		if (FCode.equalsIgnoreCase("CurentAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurentAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionAmount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionAmount = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag = FValue.trim();
			}
			else
				StandbyFlag = null;
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
		RIRecordSchema other = (RIRecordSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& OtherNo.equals(other.getOtherNo())
			&& ContNo.equals(other.getContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AreaID.equals(other.getAreaID())
			&& CurentAmnt == other.getCurentAmnt()
			&& CessionAmount == other.getCessionAmount()
			&& CessionRate == other.getCessionRate()
			&& StandbyFlag.equals(other.getStandbyFlag())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 6;
		}
		if( strFieldName.equals("AreaID") ) {
			return 7;
		}
		if( strFieldName.equals("CurentAmnt") ) {
			return 8;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return 9;
		}
		if( strFieldName.equals("CessionRate") ) {
			return 10;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return 11;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "AccumulateDefNO";
				break;
			case 2:
				strFieldName = "OtherNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "RIPreceptNo";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "DutyCode";
				break;
			case 7:
				strFieldName = "AreaID";
				break;
			case 8:
				strFieldName = "CurentAmnt";
				break;
			case 9:
				strFieldName = "CessionAmount";
				break;
			case 10:
				strFieldName = "CessionRate";
				break;
			case 11:
				strFieldName = "StandbyFlag";
				break;
			case 12:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIPreceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurentAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionAmount") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
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
