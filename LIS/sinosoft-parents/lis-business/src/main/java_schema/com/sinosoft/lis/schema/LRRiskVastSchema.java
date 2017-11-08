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
import com.sinosoft.lis.db.LRRiskVastDB;

/*
 * <p>ClassName: LRRiskVastSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRRiskVastSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRRiskVastSchema.class);

	// @Field
	/** 再保险公司 */
	private String ReinsureCom;
	/** 险种编码 */
	private String RiskCode;
	/** 险种分类 */
	private String RiskType;
	/** 险种名称 */
	private String RiskName;
	/** 险种期间 */
	private String RiskPeriod;
	/** 险种计算编码 */
	private String RiskCalCode;
	/** 当前保单风险保额算法 */
	private String RiskAmntCalCode;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRRiskVastSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ReinsureCom";
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
		LRRiskVastSchema cloned = (LRRiskVastSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
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
	* C－意外险：<p>
	* L－寿险；<p>
	* H－附加险
	*/
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/**
	* E－极短期<p>
	* L－长期<p>
	* S－一年期
	*/
	public String getRiskPeriod()
	{
		return RiskPeriod;
	}
	public void setRiskPeriod(String aRiskPeriod)
	{
		RiskPeriod = aRiskPeriod;
	}
	/**
	* 1－第一类险种<p>
	* 2－第二类险种
	*/
	public String getRiskCalCode()
	{
		return RiskCalCode;
	}
	public void setRiskCalCode(String aRiskCalCode)
	{
		RiskCalCode = aRiskCalCode;
	}
	/**
	* 值为1:该险种对应的保单风险保额=基本保额<p>
	* 值为2:该险种对应的保单风险保额=(再保系统中当前风险保额的算法:考虑客户身上承担的其它风险保额)
	*/
	public String getRiskAmntCalCode()
	{
		return RiskAmntCalCode;
	}
	public void setRiskAmntCalCode(String aRiskAmntCalCode)
	{
		RiskAmntCalCode = aRiskAmntCalCode;
	}

	/**
	* 使用另外一个 LRRiskVastSchema 对象给 Schema 赋值
	* @param: aLRRiskVastSchema LRRiskVastSchema
	**/
	public void setSchema(LRRiskVastSchema aLRRiskVastSchema)
	{
		this.ReinsureCom = aLRRiskVastSchema.getReinsureCom();
		this.RiskCode = aLRRiskVastSchema.getRiskCode();
		this.RiskType = aLRRiskVastSchema.getRiskType();
		this.RiskName = aLRRiskVastSchema.getRiskName();
		this.RiskPeriod = aLRRiskVastSchema.getRiskPeriod();
		this.RiskCalCode = aLRRiskVastSchema.getRiskCalCode();
		this.RiskAmntCalCode = aLRRiskVastSchema.getRiskAmntCalCode();
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
			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("RiskPeriod") == null )
				this.RiskPeriod = null;
			else
				this.RiskPeriod = rs.getString("RiskPeriod").trim();

			if( rs.getString("RiskCalCode") == null )
				this.RiskCalCode = null;
			else
				this.RiskCalCode = rs.getString("RiskCalCode").trim();

			if( rs.getString("RiskAmntCalCode") == null )
				this.RiskAmntCalCode = null;
			else
				this.RiskAmntCalCode = rs.getString("RiskAmntCalCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRRiskVast表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRiskVastSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRRiskVastSchema getSchema()
	{
		LRRiskVastSchema aLRRiskVastSchema = new LRRiskVastSchema();
		aLRRiskVastSchema.setSchema(this);
		return aLRRiskVastSchema;
	}

	public LRRiskVastDB getDB()
	{
		LRRiskVastDB aDBOper = new LRRiskVastDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRiskVast描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskPeriod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAmntCalCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRiskVast>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskPeriod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskAmntCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRiskVastSchema";
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
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskPeriod));
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalCode));
		}
		if (FCode.equalsIgnoreCase("RiskAmntCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmntCalCode));
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
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskPeriod);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskAmntCalCode);
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

		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureCom = FValue.trim();
			}
			else
				ReinsureCom = null;
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
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("RiskPeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskPeriod = FValue.trim();
			}
			else
				RiskPeriod = null;
		}
		if (FCode.equalsIgnoreCase("RiskCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalCode = FValue.trim();
			}
			else
				RiskCalCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmntCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAmntCalCode = FValue.trim();
			}
			else
				RiskAmntCalCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRRiskVastSchema other = (LRRiskVastSchema)otherObject;
		return
			ReinsureCom.equals(other.getReinsureCom())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskType.equals(other.getRiskType())
			&& RiskName.equals(other.getRiskName())
			&& RiskPeriod.equals(other.getRiskPeriod())
			&& RiskCalCode.equals(other.getRiskCalCode())
			&& RiskAmntCalCode.equals(other.getRiskAmntCalCode());
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
		if( strFieldName.equals("ReinsureCom") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskType") ) {
			return 2;
		}
		if( strFieldName.equals("RiskName") ) {
			return 3;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return 5;
		}
		if( strFieldName.equals("RiskAmntCalCode") ) {
			return 6;
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
				strFieldName = "ReinsureCom";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "RiskType";
				break;
			case 3:
				strFieldName = "RiskName";
				break;
			case 4:
				strFieldName = "RiskPeriod";
				break;
			case 5:
				strFieldName = "RiskCalCode";
				break;
			case 6:
				strFieldName = "RiskAmntCalCode";
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
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskPeriod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmntCalCode") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
