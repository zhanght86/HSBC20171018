/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIRulePlanDefDetailDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRulePlanDefDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRulePlanDefDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIRulePlanDefDetailSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 校验计划编码 */
	private String RulePlanID;
	/** 校验规则编码 */
	private String RuleID;
	/** 校验规则执行顺序 */
	private int Sequence;
	/** 校验规则状态 */
	private String RuleState;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIRulePlanDefDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "VersionNo";
		pk[1] = "RulePlanID";
		pk[2] = "RuleID";

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
                FIRulePlanDefDetailSchema cloned = (FIRulePlanDefDetailSchema)super.clone();
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
	public String getRulePlanID()
	{
		return RulePlanID;
	}
	public void setRulePlanID(String aRulePlanID)
	{
		RulePlanID = aRulePlanID;
	}
	public String getRuleID()
	{
		return RuleID;
	}
	public void setRuleID(String aRuleID)
	{
		RuleID = aRuleID;
	}
	public int getSequence()
	{
		return Sequence;
	}
	public void setSequence(int aSequence)
	{
		Sequence = aSequence;
	}
	public void setSequence(String aSequence)
	{
		if (aSequence != null && !aSequence.equals(""))
		{
			Integer tInteger = new Integer(aSequence);
			int i = tInteger.intValue();
			Sequence = i;
		}
	}

	public String getRuleState()
	{
		return RuleState;
	}
	public void setRuleState(String aRuleState)
	{
		RuleState = aRuleState;
	}

	/**
	* 使用另外一个 FIRulePlanDefDetailSchema 对象给 Schema 赋值
	* @param: aFIRulePlanDefDetailSchema FIRulePlanDefDetailSchema
	**/
	public void setSchema(FIRulePlanDefDetailSchema aFIRulePlanDefDetailSchema)
	{
		this.VersionNo = aFIRulePlanDefDetailSchema.getVersionNo();
		this.RulePlanID = aFIRulePlanDefDetailSchema.getRulePlanID();
		this.RuleID = aFIRulePlanDefDetailSchema.getRuleID();
		this.Sequence = aFIRulePlanDefDetailSchema.getSequence();
		this.RuleState = aFIRulePlanDefDetailSchema.getRuleState();
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

			if( rs.getString("RulePlanID") == null )
				this.RulePlanID = null;
			else
				this.RulePlanID = rs.getString("RulePlanID").trim();

			if( rs.getString("RuleID") == null )
				this.RuleID = null;
			else
				this.RuleID = rs.getString("RuleID").trim();

			this.Sequence = rs.getInt("Sequence");
			if( rs.getString("RuleState") == null )
				this.RuleState = null;
			else
				this.RuleState = rs.getString("RuleState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIRulePlanDefDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIRulePlanDefDetailSchema getSchema()
	{
		FIRulePlanDefDetailSchema aFIRulePlanDefDetailSchema = new FIRulePlanDefDetailSchema();
		aFIRulePlanDefDetailSchema.setSchema(this);
		return aFIRulePlanDefDetailSchema;
	}

	public FIRulePlanDefDetailDB getDB()
	{
		FIRulePlanDefDetailDB aDBOper = new FIRulePlanDefDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRulePlanDefDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RulePlanID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(Sequence));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RuleState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRulePlanDefDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RulePlanID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Sequence= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			RuleState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefDetailSchema";
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
		if (FCode.equalsIgnoreCase("RulePlanID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RulePlanID));
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleID));
		}
		if (FCode.equalsIgnoreCase("Sequence"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sequence));
		}
		if (FCode.equalsIgnoreCase("RuleState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleState));
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
				strFieldValue = StrTool.GBKToUnicode(RulePlanID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleID);
				break;
			case 3:
				strFieldValue = String.valueOf(Sequence);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleState);
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
		if (FCode.equalsIgnoreCase("RulePlanID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RulePlanID = FValue.trim();
			}
			else
				RulePlanID = null;
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
		if (FCode.equalsIgnoreCase("Sequence"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Sequence = i;
			}
		}
		if (FCode.equalsIgnoreCase("RuleState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleState = FValue.trim();
			}
			else
				RuleState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIRulePlanDefDetailSchema other = (FIRulePlanDefDetailSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& RulePlanID.equals(other.getRulePlanID())
			&& RuleID.equals(other.getRuleID())
			&& Sequence == other.getSequence()
			&& RuleState.equals(other.getRuleState());
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
		if( strFieldName.equals("RulePlanID") ) {
			return 1;
		}
		if( strFieldName.equals("RuleID") ) {
			return 2;
		}
		if( strFieldName.equals("Sequence") ) {
			return 3;
		}
		if( strFieldName.equals("RuleState") ) {
			return 4;
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
				strFieldName = "RulePlanID";
				break;
			case 2:
				strFieldName = "RuleID";
				break;
			case 3:
				strFieldName = "Sequence";
				break;
			case 4:
				strFieldName = "RuleState";
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
		if( strFieldName.equals("RulePlanID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sequence") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RuleState") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
