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
import com.sinosoft.lis.db.LDMaxNoRulePropDB;

/*
 * <p>ClassName: LDMaxNoRulePropSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 号段管理
 */
public class LDMaxNoRulePropSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMaxNoRulePropSchema.class);
	// @Field
	/** 号段编码 */
	private String NoCode;
	/** 实例编码 */
	private String RuleCode;
	/** 属性编码 */
	private String PropCode;
	/** 属性名称 */
	private String PropName;
	/** 属性值 */
	private String PropValue;
	/** 是否可修改 */
	private String ModifyFlag;
	/** 是否显示缩略 */
	private String ShowFlag;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMaxNoRulePropSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "NoCode";
		pk[1] = "RuleCode";
		pk[2] = "PropCode";

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
		LDMaxNoRulePropSchema cloned = (LDMaxNoRulePropSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNoCode()
	{
		return NoCode;
	}
	public void setNoCode(String aNoCode)
	{
		if(aNoCode!=null && aNoCode.length()>100)
			throw new IllegalArgumentException("号段编码NoCode值"+aNoCode+"的长度"+aNoCode.length()+"大于最大值100");
		NoCode = aNoCode;
	}
	public String getRuleCode()
	{
		return RuleCode;
	}
	public void setRuleCode(String aRuleCode)
	{
		if(aRuleCode!=null && aRuleCode.length()>100)
			throw new IllegalArgumentException("实例编码RuleCode值"+aRuleCode+"的长度"+aRuleCode.length()+"大于最大值100");
		RuleCode = aRuleCode;
	}
	public String getPropCode()
	{
		return PropCode;
	}
	public void setPropCode(String aPropCode)
	{
		if(aPropCode!=null && aPropCode.length()>100)
			throw new IllegalArgumentException("属性编码PropCode值"+aPropCode+"的长度"+aPropCode.length()+"大于最大值100");
		PropCode = aPropCode;
	}
	public String getPropName()
	{
		return PropName;
	}
	public void setPropName(String aPropName)
	{
		if(aPropName!=null && aPropName.length()>100)
			throw new IllegalArgumentException("属性名称PropName值"+aPropName+"的长度"+aPropName.length()+"大于最大值100");
		PropName = aPropName;
	}
	public String getPropValue()
	{
		return PropValue;
	}
	public void setPropValue(String aPropValue)
	{
		if(aPropValue!=null && aPropValue.length()>100)
			throw new IllegalArgumentException("属性值PropValue值"+aPropValue+"的长度"+aPropValue.length()+"大于最大值100");
		PropValue = aPropValue;
	}
	/**
	* 0-不可修改 1-可以修改
	*/
	public String getModifyFlag()
	{
		return ModifyFlag;
	}
	public void setModifyFlag(String aModifyFlag)
	{
		if(aModifyFlag!=null && aModifyFlag.length()>1)
			throw new IllegalArgumentException("是否可修改ModifyFlag值"+aModifyFlag+"的长度"+aModifyFlag.length()+"大于最大值1");
		ModifyFlag = aModifyFlag;
	}
	/**
	* 0-不显示 1-显示
	*/
	public String getShowFlag()
	{
		return ShowFlag;
	}
	public void setShowFlag(String aShowFlag)
	{
		if(aShowFlag!=null && aShowFlag.length()>1)
			throw new IllegalArgumentException("是否显示缩略ShowFlag值"+aShowFlag+"的长度"+aShowFlag.length()+"大于最大值1");
		ShowFlag = aShowFlag;
	}

	/**
	* 使用另外一个 LDMaxNoRulePropSchema 对象给 Schema 赋值
	* @param: aLDMaxNoRulePropSchema LDMaxNoRulePropSchema
	**/
	public void setSchema(LDMaxNoRulePropSchema aLDMaxNoRulePropSchema)
	{
		this.NoCode = aLDMaxNoRulePropSchema.getNoCode();
		this.RuleCode = aLDMaxNoRulePropSchema.getRuleCode();
		this.PropCode = aLDMaxNoRulePropSchema.getPropCode();
		this.PropName = aLDMaxNoRulePropSchema.getPropName();
		this.PropValue = aLDMaxNoRulePropSchema.getPropValue();
		this.ModifyFlag = aLDMaxNoRulePropSchema.getModifyFlag();
		this.ShowFlag = aLDMaxNoRulePropSchema.getShowFlag();
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
			if( rs.getString("NoCode") == null )
				this.NoCode = null;
			else
				this.NoCode = rs.getString("NoCode").trim();

			if( rs.getString("RuleCode") == null )
				this.RuleCode = null;
			else
				this.RuleCode = rs.getString("RuleCode").trim();

			if( rs.getString("PropCode") == null )
				this.PropCode = null;
			else
				this.PropCode = rs.getString("PropCode").trim();

			if( rs.getString("PropName") == null )
				this.PropName = null;
			else
				this.PropName = rs.getString("PropName").trim();

			if( rs.getString("PropValue") == null )
				this.PropValue = null;
			else
				this.PropValue = rs.getString("PropValue").trim();

			if( rs.getString("ModifyFlag") == null )
				this.ModifyFlag = null;
			else
				this.ModifyFlag = rs.getString("ModifyFlag").trim();

			if( rs.getString("ShowFlag") == null )
				this.ShowFlag = null;
			else
				this.ShowFlag = rs.getString("ShowFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMaxNoRuleProp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoRulePropSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMaxNoRulePropSchema getSchema()
	{
		LDMaxNoRulePropSchema aLDMaxNoRulePropSchema = new LDMaxNoRulePropSchema();
		aLDMaxNoRulePropSchema.setSchema(this);
		return aLDMaxNoRulePropSchema;
	}

	public LDMaxNoRulePropDB getDB()
	{
		LDMaxNoRulePropDB aDBOper = new LDMaxNoRulePropDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoRuleProp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NoCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMaxNoRuleProp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NoCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PropCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PropName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PropValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ModifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ShowFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMaxNoRulePropSchema";
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
		if (FCode.equalsIgnoreCase("NoCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NoCode));
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleCode));
		}
		if (FCode.equalsIgnoreCase("PropCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropCode));
		}
		if (FCode.equalsIgnoreCase("PropName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropName));
		}
		if (FCode.equalsIgnoreCase("PropValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropValue));
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyFlag));
		}
		if (FCode.equalsIgnoreCase("ShowFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowFlag));
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
				strFieldValue = StrTool.GBKToUnicode(NoCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PropCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PropName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PropValue);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ModifyFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ShowFlag);
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

		if (FCode.equalsIgnoreCase("NoCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NoCode = FValue.trim();
			}
			else
				NoCode = null;
		}
		if (FCode.equalsIgnoreCase("RuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleCode = FValue.trim();
			}
			else
				RuleCode = null;
		}
		if (FCode.equalsIgnoreCase("PropCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropCode = FValue.trim();
			}
			else
				PropCode = null;
		}
		if (FCode.equalsIgnoreCase("PropName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropName = FValue.trim();
			}
			else
				PropName = null;
		}
		if (FCode.equalsIgnoreCase("PropValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropValue = FValue.trim();
			}
			else
				PropValue = null;
		}
		if (FCode.equalsIgnoreCase("ModifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyFlag = FValue.trim();
			}
			else
				ModifyFlag = null;
		}
		if (FCode.equalsIgnoreCase("ShowFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowFlag = FValue.trim();
			}
			else
				ShowFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMaxNoRulePropSchema other = (LDMaxNoRulePropSchema)otherObject;
		return
			NoCode.equals(other.getNoCode())
			&& RuleCode.equals(other.getRuleCode())
			&& PropCode.equals(other.getPropCode())
			&& PropName.equals(other.getPropName())
			&& PropValue.equals(other.getPropValue())
			&& ModifyFlag.equals(other.getModifyFlag())
			&& ShowFlag.equals(other.getShowFlag());
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
		if( strFieldName.equals("NoCode") ) {
			return 0;
		}
		if( strFieldName.equals("RuleCode") ) {
			return 1;
		}
		if( strFieldName.equals("PropCode") ) {
			return 2;
		}
		if( strFieldName.equals("PropName") ) {
			return 3;
		}
		if( strFieldName.equals("PropValue") ) {
			return 4;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ShowFlag") ) {
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
				strFieldName = "NoCode";
				break;
			case 1:
				strFieldName = "RuleCode";
				break;
			case 2:
				strFieldName = "PropCode";
				break;
			case 3:
				strFieldName = "PropName";
				break;
			case 4:
				strFieldName = "PropValue";
				break;
			case 5:
				strFieldName = "ModifyFlag";
				break;
			case 6:
				strFieldName = "ShowFlag";
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
		if( strFieldName.equals("NoCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowFlag") ) {
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
