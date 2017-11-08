/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIRulePlanDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIRulePlanDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIRulePlanDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIRulePlanDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 校验计划编码 */
	private String RulesPlanID;
	/** 校验计划名称 */
	private String RulesPlanName;
	/** 校验计划状态 */
	private String RulePlanState;
	/** 校验计划执行优先级 */
	private int Sequence;
	/** 调用节点id */
	private String CallPointID;
	/** 描述 */
	private String MarkInfo;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIRulePlanDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "RulesPlanID";

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
                FIRulePlanDefSchema cloned = (FIRulePlanDefSchema)super.clone();
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
	public String getRulesPlanID()
	{
		return RulesPlanID;
	}
	public void setRulesPlanID(String aRulesPlanID)
	{
		RulesPlanID = aRulesPlanID;
	}
	public String getRulesPlanName()
	{
		return RulesPlanName;
	}
	public void setRulesPlanName(String aRulesPlanName)
	{
		RulesPlanName = aRulesPlanName;
	}
	public String getRulePlanState()
	{
		return RulePlanState;
	}
	public void setRulePlanState(String aRulePlanState)
	{
		RulePlanState = aRulePlanState;
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

	public String getCallPointID()
	{
		return CallPointID;
	}
	public void setCallPointID(String aCallPointID)
	{
		CallPointID = aCallPointID;
	}
	public String getMarkInfo()
	{
		return MarkInfo;
	}
	public void setMarkInfo(String aMarkInfo)
	{
		MarkInfo = aMarkInfo;
	}

	/**
	* 使用另外一个 FIRulePlanDefSchema 对象给 Schema 赋值
	* @param: aFIRulePlanDefSchema FIRulePlanDefSchema
	**/
	public void setSchema(FIRulePlanDefSchema aFIRulePlanDefSchema)
	{
		this.VersionNo = aFIRulePlanDefSchema.getVersionNo();
		this.RulesPlanID = aFIRulePlanDefSchema.getRulesPlanID();
		this.RulesPlanName = aFIRulePlanDefSchema.getRulesPlanName();
		this.RulePlanState = aFIRulePlanDefSchema.getRulePlanState();
		this.Sequence = aFIRulePlanDefSchema.getSequence();
		this.CallPointID = aFIRulePlanDefSchema.getCallPointID();
		this.MarkInfo = aFIRulePlanDefSchema.getMarkInfo();
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

			if( rs.getString("RulesPlanID") == null )
				this.RulesPlanID = null;
			else
				this.RulesPlanID = rs.getString("RulesPlanID").trim();

			if( rs.getString("RulesPlanName") == null )
				this.RulesPlanName = null;
			else
				this.RulesPlanName = rs.getString("RulesPlanName").trim();

			if( rs.getString("RulePlanState") == null )
				this.RulePlanState = null;
			else
				this.RulePlanState = rs.getString("RulePlanState").trim();

			this.Sequence = rs.getInt("Sequence");
			if( rs.getString("CallPointID") == null )
				this.CallPointID = null;
			else
				this.CallPointID = rs.getString("CallPointID").trim();

			if( rs.getString("MarkInfo") == null )
				this.MarkInfo = null;
			else
				this.MarkInfo = rs.getString("MarkInfo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIRulePlanDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIRulePlanDefSchema getSchema()
	{
		FIRulePlanDefSchema aFIRulePlanDefSchema = new FIRulePlanDefSchema();
		aFIRulePlanDefSchema.setSchema(this);
		return aFIRulePlanDefSchema;
	}

	public FIRulePlanDefDB getDB()
	{
		FIRulePlanDefDB aDBOper = new FIRulePlanDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRulePlanDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RulesPlanID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RulesPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RulePlanState)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(Sequence));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CallPointID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MarkInfo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIRulePlanDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RulesPlanID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RulesPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RulePlanState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Sequence= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			CallPointID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MarkInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIRulePlanDefSchema";
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
		if (FCode.equalsIgnoreCase("RulesPlanID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RulesPlanID));
		}
		if (FCode.equalsIgnoreCase("RulesPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RulesPlanName));
		}
		if (FCode.equalsIgnoreCase("RulePlanState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RulePlanState));
		}
		if (FCode.equalsIgnoreCase("Sequence"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sequence));
		}
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CallPointID));
		}
		if (FCode.equalsIgnoreCase("MarkInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MarkInfo));
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
				strFieldValue = StrTool.GBKToUnicode(RulesPlanID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RulesPlanName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RulePlanState);
				break;
			case 4:
				strFieldValue = String.valueOf(Sequence);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CallPointID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MarkInfo);
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
		if (FCode.equalsIgnoreCase("RulesPlanID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RulesPlanID = FValue.trim();
			}
			else
				RulesPlanID = null;
		}
		if (FCode.equalsIgnoreCase("RulesPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RulesPlanName = FValue.trim();
			}
			else
				RulesPlanName = null;
		}
		if (FCode.equalsIgnoreCase("RulePlanState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RulePlanState = FValue.trim();
			}
			else
				RulePlanState = null;
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
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CallPointID = FValue.trim();
			}
			else
				CallPointID = null;
		}
		if (FCode.equalsIgnoreCase("MarkInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MarkInfo = FValue.trim();
			}
			else
				MarkInfo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIRulePlanDefSchema other = (FIRulePlanDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& RulesPlanID.equals(other.getRulesPlanID())
			&& RulesPlanName.equals(other.getRulesPlanName())
			&& RulePlanState.equals(other.getRulePlanState())
			&& Sequence == other.getSequence()
			&& CallPointID.equals(other.getCallPointID())
			&& MarkInfo.equals(other.getMarkInfo());
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
		if( strFieldName.equals("RulesPlanID") ) {
			return 1;
		}
		if( strFieldName.equals("RulesPlanName") ) {
			return 2;
		}
		if( strFieldName.equals("RulePlanState") ) {
			return 3;
		}
		if( strFieldName.equals("Sequence") ) {
			return 4;
		}
		if( strFieldName.equals("CallPointID") ) {
			return 5;
		}
		if( strFieldName.equals("MarkInfo") ) {
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "RulesPlanID";
				break;
			case 2:
				strFieldName = "RulesPlanName";
				break;
			case 3:
				strFieldName = "RulePlanState";
				break;
			case 4:
				strFieldName = "Sequence";
				break;
			case 5:
				strFieldName = "CallPointID";
				break;
			case 6:
				strFieldName = "MarkInfo";
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
		if( strFieldName.equals("RulesPlanID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RulesPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RulePlanState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sequence") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CallPointID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MarkInfo") ) {
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
				nFieldType = Schema.TYPE_INT;
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
