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
import com.sinosoft.lis.db.LMRiskBonusDB;

/*
 * <p>ClassName: LMRiskBonusSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_23
 */
public class LMRiskBonusSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskBonusSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 红利系数计算公式 */
	private String BonusCoefCode;
	/** 增额交清计算公式 */
	private String AddAmntCoefCode;
	/** 红利有效终止时间 */
	private Date BonusCaldEnd;
	/** 红利有效开始时间 */
	private Date BonusCalStart;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskBonusSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		LMRiskBonusSchema cloned = (LMRiskBonusSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/**
	* 描述该险种的每个保单的红利系数。 对于现在的情况，需要描述子计算编码，分别计算分开的值。
	*/
	public String getBonusCoefCode()
	{
		return BonusCoefCode;
	}
	public void setBonusCoefCode(String aBonusCoefCode)
	{
		BonusCoefCode = aBonusCoefCode;
	}
	public String getAddAmntCoefCode()
	{
		return AddAmntCoefCode;
	}
	public void setAddAmntCoefCode(String aAddAmntCoefCode)
	{
		AddAmntCoefCode = aAddAmntCoefCode;
	}
	public String getBonusCaldEnd()
	{
		if( BonusCaldEnd != null )
			return fDate.getString(BonusCaldEnd);
		else
			return null;
	}
	public void setBonusCaldEnd(Date aBonusCaldEnd)
	{
		BonusCaldEnd = aBonusCaldEnd;
	}
	public void setBonusCaldEnd(String aBonusCaldEnd)
	{
		if (aBonusCaldEnd != null && !aBonusCaldEnd.equals("") )
		{
			BonusCaldEnd = fDate.getDate( aBonusCaldEnd );
		}
		else
			BonusCaldEnd = null;
	}

	public String getBonusCalStart()
	{
		if( BonusCalStart != null )
			return fDate.getString(BonusCalStart);
		else
			return null;
	}
	public void setBonusCalStart(Date aBonusCalStart)
	{
		BonusCalStart = aBonusCalStart;
	}
	public void setBonusCalStart(String aBonusCalStart)
	{
		if (aBonusCalStart != null && !aBonusCalStart.equals("") )
		{
			BonusCalStart = fDate.getDate( aBonusCalStart );
		}
		else
			BonusCalStart = null;
	}


	/**
	* 使用另外一个 LMRiskBonusSchema 对象给 Schema 赋值
	* @param: aLMRiskBonusSchema LMRiskBonusSchema
	**/
	public void setSchema(LMRiskBonusSchema aLMRiskBonusSchema)
	{
		this.RiskCode = aLMRiskBonusSchema.getRiskCode();
		this.RiskVer = aLMRiskBonusSchema.getRiskVer();
		this.BonusCoefCode = aLMRiskBonusSchema.getBonusCoefCode();
		this.AddAmntCoefCode = aLMRiskBonusSchema.getAddAmntCoefCode();
		this.BonusCaldEnd = fDate.getDate( aLMRiskBonusSchema.getBonusCaldEnd());
		this.BonusCalStart = fDate.getDate( aLMRiskBonusSchema.getBonusCalStart());
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("BonusCoefCode") == null )
				this.BonusCoefCode = null;
			else
				this.BonusCoefCode = rs.getString("BonusCoefCode").trim();

			if( rs.getString("AddAmntCoefCode") == null )
				this.AddAmntCoefCode = null;
			else
				this.AddAmntCoefCode = rs.getString("AddAmntCoefCode").trim();

			this.BonusCaldEnd = rs.getDate("BonusCaldEnd");
			this.BonusCalStart = rs.getDate("BonusCalStart");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskBonus表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskBonusSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskBonusSchema getSchema()
	{
		LMRiskBonusSchema aLMRiskBonusSchema = new LMRiskBonusSchema();
		aLMRiskBonusSchema.setSchema(this);
		return aLMRiskBonusSchema;
	}

	public LMRiskBonusDB getDB()
	{
		LMRiskBonusDB aDBOper = new LMRiskBonusDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskBonus描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusCoefCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddAmntCoefCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusCaldEnd ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusCalStart )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskBonus>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BonusCoefCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AddAmntCoefCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BonusCaldEnd = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			BonusCalStart = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskBonusSchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("BonusCoefCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusCoefCode));
		}
		if (FCode.equalsIgnoreCase("AddAmntCoefCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddAmntCoefCode));
		}
		if (FCode.equalsIgnoreCase("BonusCaldEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusCaldEnd()));
		}
		if (FCode.equalsIgnoreCase("BonusCalStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusCalStart()));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BonusCoefCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AddAmntCoefCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusCaldEnd()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusCalStart()));
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("BonusCoefCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusCoefCode = FValue.trim();
			}
			else
				BonusCoefCode = null;
		}
		if (FCode.equalsIgnoreCase("AddAmntCoefCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddAmntCoefCode = FValue.trim();
			}
			else
				AddAmntCoefCode = null;
		}
		if (FCode.equalsIgnoreCase("BonusCaldEnd"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusCaldEnd = fDate.getDate( FValue );
			}
			else
				BonusCaldEnd = null;
		}
		if (FCode.equalsIgnoreCase("BonusCalStart"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusCalStart = fDate.getDate( FValue );
			}
			else
				BonusCalStart = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskBonusSchema other = (LMRiskBonusSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& BonusCoefCode.equals(other.getBonusCoefCode())
			&& AddAmntCoefCode.equals(other.getAddAmntCoefCode())
			&& fDate.getString(BonusCaldEnd).equals(other.getBonusCaldEnd())
			&& fDate.getString(BonusCalStart).equals(other.getBonusCalStart());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("BonusCoefCode") ) {
			return 2;
		}
		if( strFieldName.equals("AddAmntCoefCode") ) {
			return 3;
		}
		if( strFieldName.equals("BonusCaldEnd") ) {
			return 4;
		}
		if( strFieldName.equals("BonusCalStart") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "BonusCoefCode";
				break;
			case 3:
				strFieldName = "AddAmntCoefCode";
				break;
			case 4:
				strFieldName = "BonusCaldEnd";
				break;
			case 5:
				strFieldName = "BonusCalStart";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusCoefCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddAmntCoefCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusCaldEnd") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BonusCalStart") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
