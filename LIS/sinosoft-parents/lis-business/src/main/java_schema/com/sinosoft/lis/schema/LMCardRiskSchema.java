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
import com.sinosoft.lis.db.LMCardRiskDB;

/*
 * <p>ClassName: LMCardRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LMCardRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMCardRiskSchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 险种编码 */
	private String RiskCode;
	/** 保费 */
	private double Prem;
	/** 保费特性 */
	private String PremProp;
	/** 保费份额 */
	private double PremLot;
	/** 是否产品组合 */
	private String PortfolioFlag;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCardRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CertifyCode";
		pk[1] = "RiskCode";

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
		LMCardRiskSchema cloned = (LMCardRiskSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public double getPrem()
	{
		return Prem;
	}
	public void setPrem(double aPrem)
	{
		Prem = aPrem;
	}
	public void setPrem(String aPrem)
	{
		if (aPrem != null && !aPrem.equals(""))
		{
			Double tDouble = new Double(aPrem);
			double d = tDouble.doubleValue();
			Prem = d;
		}
	}

	/**
	* 1 -- 固定保费<p>
	* 2 -- 保费倍数<p>
	* 3 -- 任意保费
	*/
	public String getPremProp()
	{
		return PremProp;
	}
	public void setPremProp(String aPremProp)
	{
		PremProp = aPremProp;
	}
	public double getPremLot()
	{
		return PremLot;
	}
	public void setPremLot(double aPremLot)
	{
		PremLot = aPremLot;
	}
	public void setPremLot(String aPremLot)
	{
		if (aPremLot != null && !aPremLot.equals(""))
		{
			Double tDouble = new Double(aPremLot);
			double d = tDouble.doubleValue();
			PremLot = d;
		}
	}

	public String getPortfolioFlag()
	{
		return PortfolioFlag;
	}
	public void setPortfolioFlag(String aPortfolioFlag)
	{
		PortfolioFlag = aPortfolioFlag;
	}

	/**
	* 使用另外一个 LMCardRiskSchema 对象给 Schema 赋值
	* @param: aLMCardRiskSchema LMCardRiskSchema
	**/
	public void setSchema(LMCardRiskSchema aLMCardRiskSchema)
	{
		this.CertifyCode = aLMCardRiskSchema.getCertifyCode();
		this.RiskCode = aLMCardRiskSchema.getRiskCode();
		this.Prem = aLMCardRiskSchema.getPrem();
		this.PremProp = aLMCardRiskSchema.getPremProp();
		this.PremLot = aLMCardRiskSchema.getPremLot();
		this.PortfolioFlag = aLMCardRiskSchema.getPortfolioFlag();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.Prem = rs.getDouble("Prem");
			if( rs.getString("PremProp") == null )
				this.PremProp = null;
			else
				this.PremProp = rs.getString("PremProp").trim();

			this.PremLot = rs.getDouble("PremLot");
			if( rs.getString("PortfolioFlag") == null )
				this.PortfolioFlag = null;
			else
				this.PortfolioFlag = rs.getString("PortfolioFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMCardRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCardRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCardRiskSchema getSchema()
	{
		LMCardRiskSchema aLMCardRiskSchema = new LMCardRiskSchema();
		aLMCardRiskSchema.setSchema(this);
		return aLMCardRiskSchema;
	}

	public LMCardRiskDB getDB()
	{
		LMCardRiskDB aDBOper = new LMCardRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCardRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Prem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PremProp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PremLot));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PortfolioFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCardRisk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Prem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			PremProp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PremLot = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			PortfolioFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCardRiskSchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("Prem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prem));
		}
		if (FCode.equalsIgnoreCase("PremProp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremProp));
		}
		if (FCode.equalsIgnoreCase("PremLot"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremLot));
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PortfolioFlag));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = String.valueOf(Prem);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PremProp);
				break;
			case 4:
				strFieldValue = String.valueOf(PremLot);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PortfolioFlag);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
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
		if (FCode.equalsIgnoreCase("Prem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Prem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PremProp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremProp = FValue.trim();
			}
			else
				PremProp = null;
		}
		if (FCode.equalsIgnoreCase("PremLot"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PremLot = d;
			}
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PortfolioFlag = FValue.trim();
			}
			else
				PortfolioFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCardRiskSchema other = (LMCardRiskSchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& RiskCode.equals(other.getRiskCode())
			&& Prem == other.getPrem()
			&& PremProp.equals(other.getPremProp())
			&& PremLot == other.getPremLot()
			&& PortfolioFlag.equals(other.getPortfolioFlag());
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("Prem") ) {
			return 2;
		}
		if( strFieldName.equals("PremProp") ) {
			return 3;
		}
		if( strFieldName.equals("PremLot") ) {
			return 4;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
			return 5;
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "Prem";
				break;
			case 3:
				strFieldName = "PremProp";
				break;
			case 4:
				strFieldName = "PremLot";
				break;
			case 5:
				strFieldName = "PortfolioFlag";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PremProp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremLot") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
