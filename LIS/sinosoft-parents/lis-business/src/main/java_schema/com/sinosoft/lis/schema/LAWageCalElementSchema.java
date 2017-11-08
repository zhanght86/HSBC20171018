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
import com.sinosoft.lis.db.LAWageCalElementDB;

/*
 * <p>ClassName: LAWageCalElementSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAWageCalElementSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAWageCalElementSchema.class);

	// @Field
	/** 险种 */
	private String RiskCode;
	/** 计算类型 */
	private String CalType;
	/** 要素1 */
	private String F1;
	/** 要素2 */
	private String F2;
	/** 要素3 */
	private String F3;
	/** 要素4 */
	private String F4;
	/** 要素5 */
	private String F5;
	/** 佣金计算编码 */
	private String CalCode;
	/** 展业类型 */
	private String BranchType;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAWageCalElementSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "CalType";

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
		LAWageCalElementSchema cloned = (LAWageCalElementSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 03-折算标保<p>
	* 04-折算件数<p>
	* <p>
	* 00-个人提佣计算<p>
	* <p>
	* 09-银代个人提奖<p>
	* 01-组提佣计算<p>
	* <p>
	* 02-部提佣计算<p>
	* <p>
	* <p>
	* 10-团体提奖<p>
	* <p>
	* 21                  	标准网点手续费<p>
	* <p>
	* 22                  	标准分理处手续费<p>
	* 23                  	标准支行手续费<p>
	* <p>
	* 24                  	标准分行手续费<p>
	* <p>
	* 25                  	标准总行手续费<p>
	* <p>
	* 26                  	标准柜员手续费<p>
	* <p>
	* 27                  	标准留用手续费<p>
	* <p>
	* 31                  	激励网点手续费(劳务）<p>
	* <p>
	* 32                  	激励分理处手续费<p>
	* <p>
	* 33                  	激励支行手续费<p>
	* 34                  	激励分行手续费<p>
	* 35                  	激励总行手续费<p>
	* <p>
	* 36                  	激励柜员手续费<p>
	* 37                  	激励留用手续费<p>
	* 41                  	其他网点手续费（业务）<p>
	* <p>
	* 42                  	其他分理处手续费<p>
	* 43                  	其他支行手续费<p>
	* <p>
	* 44                  	其他分行手续费<p>
	* <p>
	* 45                  	其他总行手续费<p>
	* <p>
	* 46                  	其他柜员手续费<p>
	* <p>
	* 47                  	其他留用手续费<p>
	* <p>
	* <p>
	* <p>
	* 51 机构代理手续费<p>
	* 52 机构劳务手续费<p>
	* 53 机构业务手续费<p>
	* 54 机构节余手续费
	*/
	public String getCalType()
	{
		return CalType;
	}
	public void setCalType(String aCalType)
	{
		CalType = aCalType;
	}
	public String getF1()
	{
		return F1;
	}
	public void setF1(String aF1)
	{
		F1 = aF1;
	}
	public String getF2()
	{
		return F2;
	}
	public void setF2(String aF2)
	{
		F2 = aF2;
	}
	public String getF3()
	{
		return F3;
	}
	public void setF3(String aF3)
	{
		F3 = aF3;
	}
	public String getF4()
	{
		return F4;
	}
	public void setF4(String aF4)
	{
		F4 = aF4;
	}
	/**
	* 团险－－计算提奖的基数：1-保费 2-保费减手续费 3-管理费
	*/
	public String getF5()
	{
		return F5;
	}
	public void setF5(String aF5)
	{
		F5 = aF5;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}

	/**
	* 使用另外一个 LAWageCalElementSchema 对象给 Schema 赋值
	* @param: aLAWageCalElementSchema LAWageCalElementSchema
	**/
	public void setSchema(LAWageCalElementSchema aLAWageCalElementSchema)
	{
		this.RiskCode = aLAWageCalElementSchema.getRiskCode();
		this.CalType = aLAWageCalElementSchema.getCalType();
		this.F1 = aLAWageCalElementSchema.getF1();
		this.F2 = aLAWageCalElementSchema.getF2();
		this.F3 = aLAWageCalElementSchema.getF3();
		this.F4 = aLAWageCalElementSchema.getF4();
		this.F5 = aLAWageCalElementSchema.getF5();
		this.CalCode = aLAWageCalElementSchema.getCalCode();
		this.BranchType = aLAWageCalElementSchema.getBranchType();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("CalType") == null )
				this.CalType = null;
			else
				this.CalType = rs.getString("CalType").trim();

			if( rs.getString("F1") == null )
				this.F1 = null;
			else
				this.F1 = rs.getString("F1").trim();

			if( rs.getString("F2") == null )
				this.F2 = null;
			else
				this.F2 = rs.getString("F2").trim();

			if( rs.getString("F3") == null )
				this.F3 = null;
			else
				this.F3 = rs.getString("F3").trim();

			if( rs.getString("F4") == null )
				this.F4 = null;
			else
				this.F4 = rs.getString("F4").trim();

			if( rs.getString("F5") == null )
				this.F5 = null;
			else
				this.F5 = rs.getString("F5").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAWageCalElement表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageCalElementSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAWageCalElementSchema getSchema()
	{
		LAWageCalElementSchema aLAWageCalElementSchema = new LAWageCalElementSchema();
		aLAWageCalElementSchema.setSchema(this);
		return aLAWageCalElementSchema;
	}

	public LAWageCalElementDB getDB()
	{
		LAWageCalElementDB aDBOper = new LAWageCalElementDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWageCalElement描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(F5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWageCalElement>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			F1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			F2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			F3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			F4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			F5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWageCalElementSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
		}
		if (FCode.equalsIgnoreCase("F1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F1));
		}
		if (FCode.equalsIgnoreCase("F2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F2));
		}
		if (FCode.equalsIgnoreCase("F3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F3));
		}
		if (FCode.equalsIgnoreCase("F4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F4));
		}
		if (FCode.equalsIgnoreCase("F5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(F5));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CalType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(F1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(F2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(F3);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(F4);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(F5);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalType = FValue.trim();
			}
			else
				CalType = null;
		}
		if (FCode.equalsIgnoreCase("F1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F1 = FValue.trim();
			}
			else
				F1 = null;
		}
		if (FCode.equalsIgnoreCase("F2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F2 = FValue.trim();
			}
			else
				F2 = null;
		}
		if (FCode.equalsIgnoreCase("F3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F3 = FValue.trim();
			}
			else
				F3 = null;
		}
		if (FCode.equalsIgnoreCase("F4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F4 = FValue.trim();
			}
			else
				F4 = null;
		}
		if (FCode.equalsIgnoreCase("F5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				F5 = FValue.trim();
			}
			else
				F5 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAWageCalElementSchema other = (LAWageCalElementSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& CalType.equals(other.getCalType())
			&& F1.equals(other.getF1())
			&& F2.equals(other.getF2())
			&& F3.equals(other.getF3())
			&& F4.equals(other.getF4())
			&& F5.equals(other.getF5())
			&& CalCode.equals(other.getCalCode())
			&& BranchType.equals(other.getBranchType());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("CalType") ) {
			return 1;
		}
		if( strFieldName.equals("F1") ) {
			return 2;
		}
		if( strFieldName.equals("F2") ) {
			return 3;
		}
		if( strFieldName.equals("F3") ) {
			return 4;
		}
		if( strFieldName.equals("F4") ) {
			return 5;
		}
		if( strFieldName.equals("F5") ) {
			return 6;
		}
		if( strFieldName.equals("CalCode") ) {
			return 7;
		}
		if( strFieldName.equals("BranchType") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "CalType";
				break;
			case 2:
				strFieldName = "F1";
				break;
			case 3:
				strFieldName = "F2";
				break;
			case 4:
				strFieldName = "F3";
				break;
			case 5:
				strFieldName = "F4";
				break;
			case 6:
				strFieldName = "F5";
				break;
			case 7:
				strFieldName = "CalCode";
				break;
			case 8:
				strFieldName = "BranchType";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("F5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
