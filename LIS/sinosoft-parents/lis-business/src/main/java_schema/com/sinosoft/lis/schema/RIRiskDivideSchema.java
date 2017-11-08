

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
import com.sinosoft.lis.db.RIRiskDivideDB;

/*
 * <p>ClassName: RIRiskDivideSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIRiskDivideSchema implements Schema, Cloneable
{
	// @Field
	/** 再保合同号码 */
	private String RIContNo;
	/** 再保方案号码 */
	private String RIPreceptNo;
	/** 区域编号 */
	private String AreaID;
	/** 区域层级 */
	private int AreaLevel;
	/** 分保公司编号 */
	private String IncomeCompanyNo;
	/** 区域上限 */
	private double UpperLimit;
	/** 区域下限 */
	private double LowerLimit;
	/** 区域占有比列 */
	private double PossessScale;
	/** 分保费率表 */
	private String PremFeeTableNo;
	/** 佣金费率表 */
	private String ComFeeTableNo;
	/** 描述 */
	private String ReMark;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIRiskDivideSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RIPreceptNo";
		pk[1] = "AreaID";

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
		RIRiskDivideSchema cloned = (RIRiskDivideSchema)super.clone();
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
	public String getAreaID()
	{
		return AreaID;
	}
	public void setAreaID(String aAreaID)
	{
		AreaID = aAreaID;
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

	public String getIncomeCompanyNo()
	{
		return IncomeCompanyNo;
	}
	public void setIncomeCompanyNo(String aIncomeCompanyNo)
	{
		IncomeCompanyNo = aIncomeCompanyNo;
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

	public double getPossessScale()
	{
		return PossessScale;
	}
	public void setPossessScale(double aPossessScale)
	{
		PossessScale = aPossessScale;
	}
	public void setPossessScale(String aPossessScale)
	{
		if (aPossessScale != null && !aPossessScale.equals(""))
		{
			Double tDouble = new Double(aPossessScale);
			double d = tDouble.doubleValue();
			PossessScale = d;
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

	/**
	* 使用另外一个 RIRiskDivideSchema 对象给 Schema 赋值
	* @param: aRIRiskDivideSchema RIRiskDivideSchema
	**/
	public void setSchema(RIRiskDivideSchema aRIRiskDivideSchema)
	{
		this.RIContNo = aRIRiskDivideSchema.getRIContNo();
		this.RIPreceptNo = aRIRiskDivideSchema.getRIPreceptNo();
		this.AreaID = aRIRiskDivideSchema.getAreaID();
		this.AreaLevel = aRIRiskDivideSchema.getAreaLevel();
		this.IncomeCompanyNo = aRIRiskDivideSchema.getIncomeCompanyNo();
		this.UpperLimit = aRIRiskDivideSchema.getUpperLimit();
		this.LowerLimit = aRIRiskDivideSchema.getLowerLimit();
		this.PossessScale = aRIRiskDivideSchema.getPossessScale();
		this.PremFeeTableNo = aRIRiskDivideSchema.getPremFeeTableNo();
		this.ComFeeTableNo = aRIRiskDivideSchema.getComFeeTableNo();
		this.ReMark = aRIRiskDivideSchema.getReMark();
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

			if( rs.getString("AreaID") == null )
				this.AreaID = null;
			else
				this.AreaID = rs.getString("AreaID").trim();

			this.AreaLevel = rs.getInt("AreaLevel");
			if( rs.getString("IncomeCompanyNo") == null )
				this.IncomeCompanyNo = null;
			else
				this.IncomeCompanyNo = rs.getString("IncomeCompanyNo").trim();

			this.UpperLimit = rs.getDouble("UpperLimit");
			this.LowerLimit = rs.getDouble("LowerLimit");
			this.PossessScale = rs.getDouble("PossessScale");
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

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIRiskDivide表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRiskDivideSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIRiskDivideSchema getSchema()
	{
		RIRiskDivideSchema aRIRiskDivideSchema = new RIRiskDivideSchema();
		aRIRiskDivideSchema.setSchema(this);
		return aRIRiskDivideSchema;
	}

	public RIRiskDivideDB getDB()
	{
		RIRiskDivideDB aDBOper = new RIRiskDivideDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRiskDivide描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIPreceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AreaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AreaLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UpperLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(LowerLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PossessScale));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremFeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComFeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIRiskDivide>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIPreceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AreaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AreaLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			IncomeCompanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UpperLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			LowerLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			PossessScale = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			PremFeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ComFeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIRiskDivideSchema";
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
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaID));
		}
		if (FCode.equalsIgnoreCase("AreaLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AreaLevel));
		}
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyNo));
		}
		if (FCode.equalsIgnoreCase("UpperLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpperLimit));
		}
		if (FCode.equalsIgnoreCase("LowerLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LowerLimit));
		}
		if (FCode.equalsIgnoreCase("PossessScale"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PossessScale));
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
				strFieldValue = StrTool.GBKToUnicode(AreaID);
				break;
			case 3:
				strFieldValue = String.valueOf(AreaLevel);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyNo);
				break;
			case 5:
				strFieldValue = String.valueOf(UpperLimit);
				break;
			case 6:
				strFieldValue = String.valueOf(LowerLimit);
				break;
			case 7:
				strFieldValue = String.valueOf(PossessScale);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PremFeeTableNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ComFeeTableNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
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
		if (FCode.equalsIgnoreCase("AreaID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AreaID = FValue.trim();
			}
			else
				AreaID = null;
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
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncomeCompanyNo = FValue.trim();
			}
			else
				IncomeCompanyNo = null;
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
		if (FCode.equalsIgnoreCase("PossessScale"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PossessScale = d;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIRiskDivideSchema other = (RIRiskDivideSchema)otherObject;
		return
			RIContNo.equals(other.getRIContNo())
			&& RIPreceptNo.equals(other.getRIPreceptNo())
			&& AreaID.equals(other.getAreaID())
			&& AreaLevel == other.getAreaLevel()
			&& IncomeCompanyNo.equals(other.getIncomeCompanyNo())
			&& UpperLimit == other.getUpperLimit()
			&& LowerLimit == other.getLowerLimit()
			&& PossessScale == other.getPossessScale()
			&& PremFeeTableNo.equals(other.getPremFeeTableNo())
			&& ComFeeTableNo.equals(other.getComFeeTableNo())
			&& ReMark.equals(other.getReMark());
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
		if( strFieldName.equals("AreaID") ) {
			return 2;
		}
		if( strFieldName.equals("AreaLevel") ) {
			return 3;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return 4;
		}
		if( strFieldName.equals("UpperLimit") ) {
			return 5;
		}
		if( strFieldName.equals("LowerLimit") ) {
			return 6;
		}
		if( strFieldName.equals("PossessScale") ) {
			return 7;
		}
		if( strFieldName.equals("PremFeeTableNo") ) {
			return 8;
		}
		if( strFieldName.equals("ComFeeTableNo") ) {
			return 9;
		}
		if( strFieldName.equals("ReMark") ) {
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
				strFieldName = "RIContNo";
				break;
			case 1:
				strFieldName = "RIPreceptNo";
				break;
			case 2:
				strFieldName = "AreaID";
				break;
			case 3:
				strFieldName = "AreaLevel";
				break;
			case 4:
				strFieldName = "IncomeCompanyNo";
				break;
			case 5:
				strFieldName = "UpperLimit";
				break;
			case 6:
				strFieldName = "LowerLimit";
				break;
			case 7:
				strFieldName = "PossessScale";
				break;
			case 8:
				strFieldName = "PremFeeTableNo";
				break;
			case 9:
				strFieldName = "ComFeeTableNo";
				break;
			case 10:
				strFieldName = "ReMark";
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
		if( strFieldName.equals("AreaID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AreaLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpperLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("LowerLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PossessScale") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
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
