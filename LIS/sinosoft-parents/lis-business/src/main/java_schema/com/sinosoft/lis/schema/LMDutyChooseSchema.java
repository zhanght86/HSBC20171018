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
import com.sinosoft.lis.db.LMDutyChooseDB;

/*
 * <p>ClassName: LMDutyChooseSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyChooseSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyChooseSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 责任代码 */
	private String DutyCode;
	/** 责任名称 */
	private String DutyName;
	/** 关联责任 */
	private String RelaDutyCode;
	/** 关联责任名称 */
	private String RelaDutyName;
	/** 关联关系 */
	private String Relation;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyChooseSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "RiskVer";
		pk[2] = "DutyCode";
		pk[3] = "RelaDutyCode";

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
		LMDutyChooseSchema cloned = (LMDutyChooseSchema)super.clone();
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
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getDutyName()
	{
		return DutyName;
	}
	public void setDutyName(String aDutyName)
	{
		DutyName = aDutyName;
	}
	public String getRelaDutyCode()
	{
		return RelaDutyCode;
	}
	public void setRelaDutyCode(String aRelaDutyCode)
	{
		RelaDutyCode = aRelaDutyCode;
	}
	public String getRelaDutyName()
	{
		return RelaDutyName;
	}
	public void setRelaDutyName(String aRelaDutyName)
	{
		RelaDutyName = aRelaDutyName;
	}
	/**
	* 0--可选、1--必选、2--必不可选
	*/
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		Relation = aRelation;
	}

	/**
	* 使用另外一个 LMDutyChooseSchema 对象给 Schema 赋值
	* @param: aLMDutyChooseSchema LMDutyChooseSchema
	**/
	public void setSchema(LMDutyChooseSchema aLMDutyChooseSchema)
	{
		this.RiskCode = aLMDutyChooseSchema.getRiskCode();
		this.RiskVer = aLMDutyChooseSchema.getRiskVer();
		this.DutyCode = aLMDutyChooseSchema.getDutyCode();
		this.DutyName = aLMDutyChooseSchema.getDutyName();
		this.RelaDutyCode = aLMDutyChooseSchema.getRelaDutyCode();
		this.RelaDutyName = aLMDutyChooseSchema.getRelaDutyName();
		this.Relation = aLMDutyChooseSchema.getRelation();
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

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("DutyName") == null )
				this.DutyName = null;
			else
				this.DutyName = rs.getString("DutyName").trim();

			if( rs.getString("RelaDutyCode") == null )
				this.RelaDutyCode = null;
			else
				this.RelaDutyCode = rs.getString("RelaDutyCode").trim();

			if( rs.getString("RelaDutyName") == null )
				this.RelaDutyName = null;
			else
				this.RelaDutyName = rs.getString("RelaDutyName").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyChoose表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyChooseSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyChooseSchema getSchema()
	{
		LMDutyChooseSchema aLMDutyChooseSchema = new LMDutyChooseSchema();
		aLMDutyChooseSchema.setSchema(this);
		return aLMDutyChooseSchema;
	}

	public LMDutyChooseDB getDB()
	{
		LMDutyChooseDB aDBOper = new LMDutyChooseDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyChoose描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relation));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyChoose>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RelaDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyChooseSchema";
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("DutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyName));
		}
		if (FCode.equalsIgnoreCase("RelaDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaDutyCode));
		}
		if (FCode.equalsIgnoreCase("RelaDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaDutyName));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
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
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DutyName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelaDutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RelaDutyName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Relation);
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyName = FValue.trim();
			}
			else
				DutyName = null;
		}
		if (FCode.equalsIgnoreCase("RelaDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaDutyCode = FValue.trim();
			}
			else
				RelaDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("RelaDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaDutyName = FValue.trim();
			}
			else
				RelaDutyName = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyChooseSchema other = (LMDutyChooseSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& DutyCode.equals(other.getDutyCode())
			&& DutyName.equals(other.getDutyName())
			&& RelaDutyCode.equals(other.getRelaDutyCode())
			&& RelaDutyName.equals(other.getRelaDutyName())
			&& Relation.equals(other.getRelation());
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
		if( strFieldName.equals("DutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("DutyName") ) {
			return 3;
		}
		if( strFieldName.equals("RelaDutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("RelaDutyName") ) {
			return 5;
		}
		if( strFieldName.equals("Relation") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "DutyCode";
				break;
			case 3:
				strFieldName = "DutyName";
				break;
			case 4:
				strFieldName = "RelaDutyCode";
				break;
			case 5:
				strFieldName = "RelaDutyName";
				break;
			case 6:
				strFieldName = "Relation";
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
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
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
