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
import com.sinosoft.lis.db.LRVastPolParmDB;

/*
 * <p>ClassName: LRVastPolParmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRVastPolParmSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRVastPolParmSchema.class);

	// @Field
	/** 再保公司 */
	private String ReinsurCom;
	/** 合同年度 */
	private int ContYear;
	/** 再保费率1 */
	private double ReinsurFeeRate1;
	/** 再保费率2 */
	private double ReinsurFeeRate2;
	/** 年初预付再保险费 */
	private double PrePayFee;
	/** 最低年度净再保费 */
	private double MinReinsureFee;
	/** 每一事故免赔额 */
	private double AmntPerAccident;
	/** 每一事故最大责任 */
	private double MaxAmntPerAccident;
	/** 每一事故最低理赔人数 */
	private int MinCasePeople;
	/** 每年最大责任限额 */
	private double MaxDutyLine;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRVastPolParmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ReinsurCom";
		pk[1] = "ContYear";

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
		LRVastPolParmSchema cloned = (LRVastPolParmSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getReinsurCom()
	{
		return ReinsurCom;
	}
	public void setReinsurCom(String aReinsurCom)
	{
		ReinsurCom = aReinsurCom;
	}
	/**
	* 法定分保:<p>
	* 1-一年期意外<p>
	* 2-极短期意外险<p>
	* 3-航意险<p>
	* 4-短期健康险<p>
	* <p>
	* 商业分保<p>
	* 1-传统寿险<p>
	* 2-分红寿险<p>
	* 3-重大疾病保险<p>
	* 4-短期健康险<p>
	* 5-意外险<p>
	* 6-团体寿险<p>
	* 7-其它<p>
	* 8-团体意外险<p>
	* 9-团体健康险<p>
	* a-团体商业补充医疗保险
	*/
	public int getContYear()
	{
		return ContYear;
	}
	public void setContYear(int aContYear)
	{
		ContYear = aContYear;
	}
	public void setContYear(String aContYear)
	{
		if (aContYear != null && !aContYear.equals(""))
		{
			Integer tInteger = new Integer(aContYear);
			int i = tInteger.intValue();
			ContYear = i;
		}
	}

	/**
	* 第一类险种的再保费率：19元/百万平均风险保额
	*/
	public double getReinsurFeeRate1()
	{
		return ReinsurFeeRate1;
	}
	public void setReinsurFeeRate1(double aReinsurFeeRate1)
	{
		ReinsurFeeRate1 = aReinsurFeeRate1;
	}
	public void setReinsurFeeRate1(String aReinsurFeeRate1)
	{
		if (aReinsurFeeRate1 != null && !aReinsurFeeRate1.equals(""))
		{
			Double tDouble = new Double(aReinsurFeeRate1);
			double d = tDouble.doubleValue();
			ReinsurFeeRate1 = d;
		}
	}

	/**
	* 第二类险种的再保费率：5％
	*/
	public double getReinsurFeeRate2()
	{
		return ReinsurFeeRate2;
	}
	public void setReinsurFeeRate2(double aReinsurFeeRate2)
	{
		ReinsurFeeRate2 = aReinsurFeeRate2;
	}
	public void setReinsurFeeRate2(String aReinsurFeeRate2)
	{
		if (aReinsurFeeRate2 != null && !aReinsurFeeRate2.equals(""))
		{
			Double tDouble = new Double(aReinsurFeeRate2);
			double d = tDouble.doubleValue();
			ReinsurFeeRate2 = d;
		}
	}

	/**
	* 如：50,000元
	*/
	public double getPrePayFee()
	{
		return PrePayFee;
	}
	public void setPrePayFee(double aPrePayFee)
	{
		PrePayFee = aPrePayFee;
	}
	public void setPrePayFee(String aPrePayFee)
	{
		if (aPrePayFee != null && !aPrePayFee.equals(""))
		{
			Double tDouble = new Double(aPrePayFee);
			double d = tDouble.doubleValue();
			PrePayFee = d;
		}
	}

	/**
	* 如：50,000元
	*/
	public double getMinReinsureFee()
	{
		return MinReinsureFee;
	}
	public void setMinReinsureFee(double aMinReinsureFee)
	{
		MinReinsureFee = aMinReinsureFee;
	}
	public void setMinReinsureFee(String aMinReinsureFee)
	{
		if (aMinReinsureFee != null && !aMinReinsureFee.equals(""))
		{
			Double tDouble = new Double(aMinReinsureFee);
			double d = tDouble.doubleValue();
			MinReinsureFee = d;
		}
	}

	/**
	* 如：1,000,000元
	*/
	public double getAmntPerAccident()
	{
		return AmntPerAccident;
	}
	public void setAmntPerAccident(double aAmntPerAccident)
	{
		AmntPerAccident = aAmntPerAccident;
	}
	public void setAmntPerAccident(String aAmntPerAccident)
	{
		if (aAmntPerAccident != null && !aAmntPerAccident.equals(""))
		{
			Double tDouble = new Double(aAmntPerAccident);
			double d = tDouble.doubleValue();
			AmntPerAccident = d;
		}
	}

	/**
	* 如：10,000,000元
	*/
	public double getMaxAmntPerAccident()
	{
		return MaxAmntPerAccident;
	}
	public void setMaxAmntPerAccident(double aMaxAmntPerAccident)
	{
		MaxAmntPerAccident = aMaxAmntPerAccident;
	}
	public void setMaxAmntPerAccident(String aMaxAmntPerAccident)
	{
		if (aMaxAmntPerAccident != null && !aMaxAmntPerAccident.equals(""))
		{
			Double tDouble = new Double(aMaxAmntPerAccident);
			double d = tDouble.doubleValue();
			MaxAmntPerAccident = d;
		}
	}

	/**
	* 如：3人
	*/
	public int getMinCasePeople()
	{
		return MinCasePeople;
	}
	public void setMinCasePeople(int aMinCasePeople)
	{
		MinCasePeople = aMinCasePeople;
	}
	public void setMinCasePeople(String aMinCasePeople)
	{
		if (aMinCasePeople != null && !aMinCasePeople.equals(""))
		{
			Integer tInteger = new Integer(aMinCasePeople);
			int i = tInteger.intValue();
			MinCasePeople = i;
		}
	}

	/**
	* 如：20,000,000元
	*/
	public double getMaxDutyLine()
	{
		return MaxDutyLine;
	}
	public void setMaxDutyLine(double aMaxDutyLine)
	{
		MaxDutyLine = aMaxDutyLine;
	}
	public void setMaxDutyLine(String aMaxDutyLine)
	{
		if (aMaxDutyLine != null && !aMaxDutyLine.equals(""))
		{
			Double tDouble = new Double(aMaxDutyLine);
			double d = tDouble.doubleValue();
			MaxDutyLine = d;
		}
	}


	/**
	* 使用另外一个 LRVastPolParmSchema 对象给 Schema 赋值
	* @param: aLRVastPolParmSchema LRVastPolParmSchema
	**/
	public void setSchema(LRVastPolParmSchema aLRVastPolParmSchema)
	{
		this.ReinsurCom = aLRVastPolParmSchema.getReinsurCom();
		this.ContYear = aLRVastPolParmSchema.getContYear();
		this.ReinsurFeeRate1 = aLRVastPolParmSchema.getReinsurFeeRate1();
		this.ReinsurFeeRate2 = aLRVastPolParmSchema.getReinsurFeeRate2();
		this.PrePayFee = aLRVastPolParmSchema.getPrePayFee();
		this.MinReinsureFee = aLRVastPolParmSchema.getMinReinsureFee();
		this.AmntPerAccident = aLRVastPolParmSchema.getAmntPerAccident();
		this.MaxAmntPerAccident = aLRVastPolParmSchema.getMaxAmntPerAccident();
		this.MinCasePeople = aLRVastPolParmSchema.getMinCasePeople();
		this.MaxDutyLine = aLRVastPolParmSchema.getMaxDutyLine();
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
			if( rs.getString("ReinsurCom") == null )
				this.ReinsurCom = null;
			else
				this.ReinsurCom = rs.getString("ReinsurCom").trim();

			this.ContYear = rs.getInt("ContYear");
			this.ReinsurFeeRate1 = rs.getDouble("ReinsurFeeRate1");
			this.ReinsurFeeRate2 = rs.getDouble("ReinsurFeeRate2");
			this.PrePayFee = rs.getDouble("PrePayFee");
			this.MinReinsureFee = rs.getDouble("MinReinsureFee");
			this.AmntPerAccident = rs.getDouble("AmntPerAccident");
			this.MaxAmntPerAccident = rs.getDouble("MaxAmntPerAccident");
			this.MinCasePeople = rs.getInt("MinCasePeople");
			this.MaxDutyLine = rs.getDouble("MaxDutyLine");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRVastPolParm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRVastPolParmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRVastPolParmSchema getSchema()
	{
		LRVastPolParmSchema aLRVastPolParmSchema = new LRVastPolParmSchema();
		aLRVastPolParmSchema.setSchema(this);
		return aLRVastPolParmSchema;
	}

	public LRVastPolParmDB getDB()
	{
		LRVastPolParmDB aDBOper = new LRVastPolParmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRVastPolParm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ReinsurCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ContYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReinsurFeeRate1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ReinsurFeeRate2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrePayFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinReinsureFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AmntPerAccident));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAmntPerAccident));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MinCasePeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxDutyLine));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRVastPolParm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ReinsurCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ReinsurFeeRate1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			ReinsurFeeRate2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			PrePayFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			MinReinsureFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AmntPerAccident = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			MaxAmntPerAccident = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			MinCasePeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			MaxDutyLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRVastPolParmSchema";
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
		if (FCode.equalsIgnoreCase("ReinsurCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurCom));
		}
		if (FCode.equalsIgnoreCase("ContYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContYear));
		}
		if (FCode.equalsIgnoreCase("ReinsurFeeRate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurFeeRate1));
		}
		if (FCode.equalsIgnoreCase("ReinsurFeeRate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurFeeRate2));
		}
		if (FCode.equalsIgnoreCase("PrePayFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrePayFee));
		}
		if (FCode.equalsIgnoreCase("MinReinsureFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinReinsureFee));
		}
		if (FCode.equalsIgnoreCase("AmntPerAccident"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmntPerAccident));
		}
		if (FCode.equalsIgnoreCase("MaxAmntPerAccident"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAmntPerAccident));
		}
		if (FCode.equalsIgnoreCase("MinCasePeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MinCasePeople));
		}
		if (FCode.equalsIgnoreCase("MaxDutyLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxDutyLine));
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
				strFieldValue = StrTool.GBKToUnicode(ReinsurCom);
				break;
			case 1:
				strFieldValue = String.valueOf(ContYear);
				break;
			case 2:
				strFieldValue = String.valueOf(ReinsurFeeRate1);
				break;
			case 3:
				strFieldValue = String.valueOf(ReinsurFeeRate2);
				break;
			case 4:
				strFieldValue = String.valueOf(PrePayFee);
				break;
			case 5:
				strFieldValue = String.valueOf(MinReinsureFee);
				break;
			case 6:
				strFieldValue = String.valueOf(AmntPerAccident);
				break;
			case 7:
				strFieldValue = String.valueOf(MaxAmntPerAccident);
				break;
			case 8:
				strFieldValue = String.valueOf(MinCasePeople);
				break;
			case 9:
				strFieldValue = String.valueOf(MaxDutyLine);
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

		if (FCode.equalsIgnoreCase("ReinsurCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsurCom = FValue.trim();
			}
			else
				ReinsurCom = null;
		}
		if (FCode.equalsIgnoreCase("ContYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ContYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("ReinsurFeeRate1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ReinsurFeeRate1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("ReinsurFeeRate2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ReinsurFeeRate2 = d;
			}
		}
		if (FCode.equalsIgnoreCase("PrePayFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrePayFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("MinReinsureFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MinReinsureFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("AmntPerAccident"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AmntPerAccident = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxAmntPerAccident"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxAmntPerAccident = d;
			}
		}
		if (FCode.equalsIgnoreCase("MinCasePeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MinCasePeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxDutyLine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxDutyLine = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRVastPolParmSchema other = (LRVastPolParmSchema)otherObject;
		return
			ReinsurCom.equals(other.getReinsurCom())
			&& ContYear == other.getContYear()
			&& ReinsurFeeRate1 == other.getReinsurFeeRate1()
			&& ReinsurFeeRate2 == other.getReinsurFeeRate2()
			&& PrePayFee == other.getPrePayFee()
			&& MinReinsureFee == other.getMinReinsureFee()
			&& AmntPerAccident == other.getAmntPerAccident()
			&& MaxAmntPerAccident == other.getMaxAmntPerAccident()
			&& MinCasePeople == other.getMinCasePeople()
			&& MaxDutyLine == other.getMaxDutyLine();
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
		if( strFieldName.equals("ReinsurCom") ) {
			return 0;
		}
		if( strFieldName.equals("ContYear") ) {
			return 1;
		}
		if( strFieldName.equals("ReinsurFeeRate1") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsurFeeRate2") ) {
			return 3;
		}
		if( strFieldName.equals("PrePayFee") ) {
			return 4;
		}
		if( strFieldName.equals("MinReinsureFee") ) {
			return 5;
		}
		if( strFieldName.equals("AmntPerAccident") ) {
			return 6;
		}
		if( strFieldName.equals("MaxAmntPerAccident") ) {
			return 7;
		}
		if( strFieldName.equals("MinCasePeople") ) {
			return 8;
		}
		if( strFieldName.equals("MaxDutyLine") ) {
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
				strFieldName = "ReinsurCom";
				break;
			case 1:
				strFieldName = "ContYear";
				break;
			case 2:
				strFieldName = "ReinsurFeeRate1";
				break;
			case 3:
				strFieldName = "ReinsurFeeRate2";
				break;
			case 4:
				strFieldName = "PrePayFee";
				break;
			case 5:
				strFieldName = "MinReinsureFee";
				break;
			case 6:
				strFieldName = "AmntPerAccident";
				break;
			case 7:
				strFieldName = "MaxAmntPerAccident";
				break;
			case 8:
				strFieldName = "MinCasePeople";
				break;
			case 9:
				strFieldName = "MaxDutyLine";
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
		if( strFieldName.equals("ReinsurCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ReinsurFeeRate1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ReinsurFeeRate2") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PrePayFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinReinsureFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AmntPerAccident") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxAmntPerAccident") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MinCasePeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxDutyLine") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
