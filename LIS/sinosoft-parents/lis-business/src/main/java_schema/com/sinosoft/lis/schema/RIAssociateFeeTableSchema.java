

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
import com.sinosoft.lis.db.RIAssociateFeeTableDB;

/*
 * <p>ClassName: RIAssociateFeeTableSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIAssociateFeeTableSchema implements Schema, Cloneable
{
	// @Field
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 区域编号 */
	private String AreaID;
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 累计明细标志 */
	private String DeTailFlag;
	/** 分保公司编号 */
	private String IncomeCompanyNo;
	/** 区域层级 */
	private int AreaLevel;
	/** 区域上限 */
	private double UpperLimit;
	/** 区域下限 */
	private double LowerLimit;
	/** 分保费率表 */
	private String PremFeeTableNo;
	/** 佣金费率表 */
	private String ComFeeTableNo;
	/** 描述 */
	private String ReMark;
	/** 备注字段 */
	private String StandbyFlag;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIAssociateFeeTableSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RIPreceptNo";
		pk[1] = "RiskCode";
		pk[2] = "DutyCode";
		pk[3] = "AreaID";

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
		RIAssociateFeeTableSchema cloned = (RIAssociateFeeTableSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	/**
	* 1-代表险种级别 2-代表责任级别
	*/
	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	/**
	* 01-代表险种级别<p>
	* 02-代表责任级别
	*/
	public String getDeTailFlag()
	{
		return DeTailFlag;
	}
	public void setDeTailFlag(String aDeTailFlag)
	{
		DeTailFlag = aDeTailFlag;
	}
	public String getIncomeCompanyNo()
	{
		return IncomeCompanyNo;
	}
	public void setIncomeCompanyNo(String aIncomeCompanyNo)
	{
		IncomeCompanyNo = aIncomeCompanyNo;
	}
	public int getAreaLevel()
	{
		return AreaLevel;
	}
	public void setAreaLevel(int aAreaLevel)
	{
		AreaLevel = aAreaLevel;
	}
	public void setAreaLevel(String aAreaLevel)
	{
		if (aAreaLevel != null && !aAreaLevel.equals(""))
		{
			Integer tInteger = new Integer(aAreaLevel);
			int i = tInteger.intValue();
			AreaLevel = i;
		}
	}

	public double getUpperLimit()
	{
		return UpperLimit;
	}
	public void setUpperLimit(double aUpperLimit)
	{
		UpperLimit = aUpperLimit;
	}
	public void setUpperLimit(String aUpperLimit)
	{
		if (aUpperLimit != null && !aUpperLimit.equals(""))
		{
			Double tDouble = new Double(aUpperLimit);
			double d = tDouble.doubleValue();
			UpperLimit = d;
		}
	}

	public double getLowerLimit()
	{
		return LowerLimit;
	}
	public void setLowerLimit(double aLowerLimit)
	{
		LowerLimit = aLowerLimit;
	}
	public void setLowerLimit(String aLowerLimit)
	{
		if (aLowerLimit != null && !aLowerLimit.equals(""))
		{
			Double tDouble = new Double(aLowerLimit);
			double d = tDouble.doubleValue();
			LowerLimit = d;
		}
	}

	public String getPremFeeTableNo()
	{
		return PremFeeTableNo;
	}
	public void setPremFeeTableNo(String aPremFeeTableNo)
	{
		PremFeeTableNo = aPremFeeTableNo;
	}
	public String getComFeeTableNo()
	{
		return ComFeeTableNo;
	}
	public void setComFeeTableNo(String aComFeeTableNo)
	{
		ComFeeTableNo = aComFeeTableNo;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}
	public String getStandbyFlag()
	{
		return StandbyFlag;
	}
	public void setStandbyFlag(String aStandbyFlag)
	{
		StandbyFlag = aStandbyFlag;
	}

	/**
	* 使用另外一个 RIAssociateFeeTableSchema 对象给 Schema 赋值
	* @param: aRIAssociateFeeTableSchema RIAssociateFeeTableSchema
	**/
	public void setSchema(RIAssociateFeeTableSchema aRIAssociateFeeTableSchema)
	{
		this.RIPreceptNo = aRIAssociateFeeTableSchema.getRIPreceptNo();
		this.RiskCode = aRIAssociateFeeTableSchema.getRiskCode();
		this.DutyCode = aRIAssociateFeeTableSchema.getDutyCode();
		this.AreaID = aRIAssociateFeeTableSchema.getAreaID();
		this.AccumulateDefNO = aRIAssociateFeeTableSchema.getAccumulateDefNO();
		this.DeTailFlag = aRIAssociateFeeTableSchema.getDeTailFlag();
		this.IncomeCompanyNo = aRIAssociateFeeTableSchema.getIncomeCompanyNo();
		this.AreaLevel = aRIAssociateFeeTableSchema.getAreaLevel();
		this.UpperLimit = aRIAssociateFeeTableSchema.getUpperLimit();
		this.LowerLimit = aRIAssociateFeeTableSchema.getLowerLimit();
		this.PremFeeTableNo = aRIAssociateFeeTableSchema.getPremFeeTableNo();
		this.ComFeeTableNo = aRIAssociateFeeTableSchema.getComFeeTableNo();
		this.ReMark = aRIAssociateFeeTableSchema.getReMark();
		this.StandbyFlag = aRIAssociateFeeTableSchema.getStandbyFlag();
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

			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("DeTailFlag") == null )
				this.DeTailFlag = null;
			else
				this.DeTailFlag = rs.getString("DeTailFlag").trim();

			if( rs.getString("IncomeCompanyNo") == null )
				this.IncomeCompanyNo = null;
			else
				this.IncomeCompanyNo = rs.getString("IncomeCompanyNo").trim();

			this.AreaLevel = rs.getInt("AreaLevel");
			this.UpperLimit = rs.getDouble("UpperLimit");
			this.LowerLimit = rs.getDouble("LowerLimit");
			if( rs.getString("PremFeeTableNo") == null )
				this.PremFeeTableNo = null;
			else
				this.PremFeeTableNo = rs.getString("PremFeeTableNo").trim();

			if( rs.getString("ComFeeTableNo") == null )
				this.ComFeeTableNo = null;
			else
				this.ComFeeTableNo = rs.getString("ComFeeTableNo").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

			if( rs.getString("StandbyFlag") == null )
				this.StandbyFlag = null;
			else
				this.StandbyFlag = rs.getString("StandbyFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIAssociateFeeTable表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAssociateFeeTableSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIAssociateFeeTableSchema getSchema()
	{
		RIAssociateFeeTableSchema aRIAssociateFeeTableSchema = new RIAssociateFeeTableSchema();
		aRIAssociateFeeTableSchema.setSchema(this);
		return aRIAssociateFeeTableSchema;
	}

	public RIAssociateFeeTableDB getDB()
	{
		RIAssociateFeeTableDB aDBOper = new RIAssociateFeeTableDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAssociateFeeTable描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DeTailFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AreaLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UpperLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LowerLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremFeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComFeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAssociateFeeTable>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AreaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DeTailFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			IncomeCompanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AreaLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			UpperLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			LowerLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			PremFeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ComFeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAssociateFeeTableSchema";
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
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("DeTailFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeTailFlag));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyNo));
		}
		if (FCode.equalsIgnoreCase("AreaLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaLevel));
		}
		if (FCode.equalsIgnoreCase("UpperLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpperLimit));
		}
		if (FCode.equalsIgnoreCase("LowerLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LowerLimit));
		}
		if (FCode.equalsIgnoreCase("PremFeeTableNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremFeeTableNo));
		}
		if (FCode.equalsIgnoreCase("ComFeeTableNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComFeeTableNo));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RIPreceptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AreaID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DeTailFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyNo);
				break;
			case 7:
				strFieldValue = String.valueOf(AreaLevel);
				break;
			case 8:
				strFieldValue = String.valueOf(UpperLimit);
				break;
			case 9:
				strFieldValue = String.valueOf(LowerLimit);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PremFeeTableNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ComFeeTableNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag);
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
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("DeTailFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DeTailFlag = FValue.trim();
			}
			else
				DeTailFlag = null;
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
		if (FCode.equalsIgnoreCase("AreaLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AreaLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("UpperLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UpperLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("LowerLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				LowerLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremFeeTableNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremFeeTableNo = FValue.trim();
			}
			else
				PremFeeTableNo = null;
		}
		if (FCode.equalsIgnoreCase("ComFeeTableNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComFeeTableNo = FValue.trim();
			}
			else
				ComFeeTableNo = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIAssociateFeeTableSchema other = (RIAssociateFeeTableSchema)otherObject;
		return
			RIPreceptNo.equals(other.getRIPreceptNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AreaID.equals(other.getAreaID())
			&& AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& DeTailFlag.equals(other.getDeTailFlag())
			&& IncomeCompanyNo.equals(other.getIncomeCompanyNo())
			&& AreaLevel == other.getAreaLevel()
			&& UpperLimit == other.getUpperLimit()
			&& LowerLimit == other.getLowerLimit()
			&& PremFeeTableNo.equals(other.getPremFeeTableNo())
			&& ComFeeTableNo.equals(other.getComFeeTableNo())
			&& ReMark.equals(other.getReMark())
			&& StandbyFlag.equals(other.getStandbyFlag());
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
		if( strFieldName.equals("RIPreceptNo") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("AreaID") ) {
			return 3;
		}
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 4;
		}
		if( strFieldName.equals("DeTailFlag") ) {
			return 5;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return 6;
		}
		if( strFieldName.equals("AreaLevel") ) {
			return 7;
		}
		if( strFieldName.equals("UpperLimit") ) {
			return 8;
		}
		if( strFieldName.equals("LowerLimit") ) {
			return 9;
		}
		if( strFieldName.equals("PremFeeTableNo") ) {
			return 10;
		}
		if( strFieldName.equals("ComFeeTableNo") ) {
			return 11;
		}
		if( strFieldName.equals("ReMark") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return 13;
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
				strFieldName = "RIPreceptNo";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "AreaID";
				break;
			case 4:
				strFieldName = "AccumulateDefNO";
				break;
			case 5:
				strFieldName = "DeTailFlag";
				break;
			case 6:
				strFieldName = "IncomeCompanyNo";
				break;
			case 7:
				strFieldName = "AreaLevel";
				break;
			case 8:
				strFieldName = "UpperLimit";
				break;
			case 9:
				strFieldName = "LowerLimit";
				break;
			case 10:
				strFieldName = "PremFeeTableNo";
				break;
			case 11:
				strFieldName = "ComFeeTableNo";
				break;
			case 12:
				strFieldName = "ReMark";
				break;
			case 13:
				strFieldName = "StandbyFlag";
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
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeTailFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("UpperLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LowerLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremFeeTableNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComFeeTableNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
