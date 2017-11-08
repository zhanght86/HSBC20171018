/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIRuleDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRuleDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRuleDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIRuleDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 校验规则编码 */
	private String RuleID;
	/** 校验规则名称 */
	private String RuleName;
	/** 校验处理方式 */
	private String RuleDealMode;
	/** 处理类名称 */
	private String RuleDealClass;
	/** 规则关联的文件名称 */
	private String RuleFileName;
	/** 校验sql */
	private String RuleDealSQL;
	/** 校验返回含义 */
	private String RuleReturnMean;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIRuleDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "RuleID";

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
                FIRuleDefSchema cloned = (FIRuleDefSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getVersionNo()
	{
		return VersionNo;
	}
	public void setVersionNo(String aVersionNo)
	{
		VersionNo = aVersionNo;
	}
	public String getRuleID()
	{
		return RuleID;
	}
	public void setRuleID(String aRuleID)
	{
		RuleID = aRuleID;
	}
	public String getRuleName()
	{
		return RuleName;
	}
	public void setRuleName(String aRuleName)
	{
		RuleName = aRuleName;
	}
	public String getRuleDealMode()
	{
		return RuleDealMode;
	}
	public void setRuleDealMode(String aRuleDealMode)
	{
		RuleDealMode = aRuleDealMode;
	}
	public String getRuleDealClass()
	{
		return RuleDealClass;
	}
	public void setRuleDealClass(String aRuleDealClass)
	{
		RuleDealClass = aRuleDealClass;
	}
	public String getRuleFileName()
	{
		return RuleFileName;
	}
	public void setRuleFileName(String aRuleFileName)
	{
		RuleFileName = aRuleFileName;
	}
	public String getRuleDealSQL()
	{
		return RuleDealSQL;
	}
	public void setRuleDealSQL(String aRuleDealSQL)
	{
		RuleDealSQL = aRuleDealSQL;
	}
	public String getRuleReturnMean()
	{
		return RuleReturnMean;
	}
	public void setRuleReturnMean(String aRuleReturnMean)
	{
		RuleReturnMean = aRuleReturnMean;
	}

	/**
	* 使用另外一个 FIRuleDefSchema 对象给 Schema 赋值
	* @param: aFIRuleDefSchema FIRuleDefSchema
	**/
	public void setSchema(FIRuleDefSchema aFIRuleDefSchema)
	{
		this.VersionNo = aFIRuleDefSchema.getVersionNo();
		this.RuleID = aFIRuleDefSchema.getRuleID();
		this.RuleName = aFIRuleDefSchema.getRuleName();
		this.RuleDealMode = aFIRuleDefSchema.getRuleDealMode();
		this.RuleDealClass = aFIRuleDefSchema.getRuleDealClass();
		this.RuleFileName = aFIRuleDefSchema.getRuleFileName();
		this.RuleDealSQL = aFIRuleDefSchema.getRuleDealSQL();
		this.RuleReturnMean = aFIRuleDefSchema.getRuleReturnMean();
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
			if( rs.getString("VersionNo") == null )
				this.VersionNo = null;
			else
				this.VersionNo = rs.getString("VersionNo").trim();

			if( rs.getString("RuleID") == null )
				this.RuleID = null;
			else
				this.RuleID = rs.getString("RuleID").trim();

			if( rs.getString("RuleName") == null )
				this.RuleName = null;
			else
				this.RuleName = rs.getString("RuleName").trim();

			if( rs.getString("RuleDealMode") == null )
				this.RuleDealMode = null;
			else
				this.RuleDealMode = rs.getString("RuleDealMode").trim();

			if( rs.getString("RuleDealClass") == null )
				this.RuleDealClass = null;
			else
				this.RuleDealClass = rs.getString("RuleDealClass").trim();

			if( rs.getString("RuleFileName") == null )
				this.RuleFileName = null;
			else
				this.RuleFileName = rs.getString("RuleFileName").trim();

			if( rs.getString("RuleDealSQL") == null )
				this.RuleDealSQL = null;
			else
				this.RuleDealSQL = rs.getString("RuleDealSQL").trim();

			if( rs.getString("RuleReturnMean") == null )
				this.RuleReturnMean = null;
			else
				this.RuleReturnMean = rs.getString("RuleReturnMean").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIRuleDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIRuleDefSchema getSchema()
	{
		FIRuleDefSchema aFIRuleDefSchema = new FIRuleDefSchema();
		aFIRuleDefSchema.setSchema(this);
		return aFIRuleDefSchema;
	}

	public FIRuleDefDB getDB()
	{
		FIRuleDefDB aDBOper = new FIRuleDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRuleDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealMode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleFileName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleDealSQL)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleReturnMean));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRuleDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleDealMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleDealClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleFileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RuleDealSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RuleReturnMean = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRuleDefSchema";
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
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionNo));
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleID));
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleName));
		}
		if (FCode.equalsIgnoreCase("RuleDealMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealMode));
		}
		if (FCode.equalsIgnoreCase("RuleDealClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealClass));
		}
		if (FCode.equalsIgnoreCase("RuleFileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleFileName));
		}
		if (FCode.equalsIgnoreCase("RuleDealSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealSQL));
		}
		if (FCode.equalsIgnoreCase("RuleReturnMean"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleReturnMean));
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
				strFieldValue = StrTool.GBKToUnicode(VersionNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleDealMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleDealClass);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleFileName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RuleDealSQL);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RuleReturnMean);
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

		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionNo = FValue.trim();
			}
			else
				VersionNo = null;
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleID = FValue.trim();
			}
			else
				RuleID = null;
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleName = FValue.trim();
			}
			else
				RuleName = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealMode = FValue.trim();
			}
			else
				RuleDealMode = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealClass = FValue.trim();
			}
			else
				RuleDealClass = null;
		}
		if (FCode.equalsIgnoreCase("RuleFileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleFileName = FValue.trim();
			}
			else
				RuleFileName = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealSQL = FValue.trim();
			}
			else
				RuleDealSQL = null;
		}
		if (FCode.equalsIgnoreCase("RuleReturnMean"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleReturnMean = FValue.trim();
			}
			else
				RuleReturnMean = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIRuleDefSchema other = (FIRuleDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& RuleID.equals(other.getRuleID())
			&& RuleName.equals(other.getRuleName())
			&& RuleDealMode.equals(other.getRuleDealMode())
			&& RuleDealClass.equals(other.getRuleDealClass())
			&& RuleFileName.equals(other.getRuleFileName())
			&& RuleDealSQL.equals(other.getRuleDealSQL())
			&& RuleReturnMean.equals(other.getRuleReturnMean());
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
		if( strFieldName.equals("VersionNo") ) {
			return 0;
		}
		if( strFieldName.equals("RuleID") ) {
			return 1;
		}
		if( strFieldName.equals("RuleName") ) {
			return 2;
		}
		if( strFieldName.equals("RuleDealMode") ) {
			return 3;
		}
		if( strFieldName.equals("RuleDealClass") ) {
			return 4;
		}
		if( strFieldName.equals("RuleFileName") ) {
			return 5;
		}
		if( strFieldName.equals("RuleDealSQL") ) {
			return 6;
		}
		if( strFieldName.equals("RuleReturnMean") ) {
			return 7;
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "RuleID";
				break;
			case 2:
				strFieldName = "RuleName";
				break;
			case 3:
				strFieldName = "RuleDealMode";
				break;
			case 4:
				strFieldName = "RuleDealClass";
				break;
			case 5:
				strFieldName = "RuleFileName";
				break;
			case 6:
				strFieldName = "RuleDealSQL";
				break;
			case 7:
				strFieldName = "RuleReturnMean";
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
		if( strFieldName.equals("VersionNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleFileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleReturnMean") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
