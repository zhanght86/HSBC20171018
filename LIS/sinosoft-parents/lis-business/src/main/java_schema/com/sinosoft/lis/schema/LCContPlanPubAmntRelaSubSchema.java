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
import com.sinosoft.lis.db.LCContPlanPubAmntRelaSubDB;

/*
 * <p>ClassName: LCContPlanPubAmntRelaSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCContPlanPubAmntRelaSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContPlanPubAmntRelaSubSchema.class);
	// @Field
	/** 保单号码 */
	private String PolicyNo;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 公共保额使用标志 */
	private String PubFlag;
	/** 责任限额 */
	private String DutyLimitAmnt;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContPlanPubAmntRelaSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "PolicyNo";
		pk[1] = "SysPlanCode";
		pk[2] = "PlanCode";
		pk[3] = "RiskCode";
		pk[4] = "DutyCode";

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
		LCContPlanPubAmntRelaSubSchema cloned = (LCContPlanPubAmntRelaSubSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 团体保单号/个人保单号
	*/
	public String getPolicyNo()
	{
		return PolicyNo;
	}
	public void setPolicyNo(String aPolicyNo)
	{
		if(aPolicyNo!=null && aPolicyNo.length()>20)
			throw new IllegalArgumentException("保单号码PolicyNo值"+aPolicyNo+"的长度"+aPolicyNo.length()+"大于最大值20");
		PolicyNo = aPolicyNo;
	}
	public String getSysPlanCode()
	{
		return SysPlanCode;
	}
	public void setSysPlanCode(String aSysPlanCode)
	{
		if(aSysPlanCode!=null && aSysPlanCode.length()>10)
			throw new IllegalArgumentException("系统方案编码SysPlanCode值"+aSysPlanCode+"的长度"+aSysPlanCode.length()+"大于最大值10");
		SysPlanCode = aSysPlanCode;
	}
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>10)
			throw new IllegalArgumentException("方案编码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值10");
		PlanCode = aPlanCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>20)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值20");
		DutyCode = aDutyCode;
	}
	public String getPubFlag()
	{
		return PubFlag;
	}
	public void setPubFlag(String aPubFlag)
	{
		if(aPubFlag!=null && aPubFlag.length()>2)
			throw new IllegalArgumentException("公共保额使用标志PubFlag值"+aPubFlag+"的长度"+aPubFlag.length()+"大于最大值2");
		PubFlag = aPubFlag;
	}
	public String getDutyLimitAmnt()
	{
		return DutyLimitAmnt;
	}
	public void setDutyLimitAmnt(String aDutyLimitAmnt)
	{
		if(aDutyLimitAmnt!=null && aDutyLimitAmnt.length()>20)
			throw new IllegalArgumentException("责任限额DutyLimitAmnt值"+aDutyLimitAmnt+"的长度"+aDutyLimitAmnt.length()+"大于最大值20");
		DutyLimitAmnt = aDutyLimitAmnt;
	}

	/**
	* 使用另外一个 LCContPlanPubAmntRelaSubSchema 对象给 Schema 赋值
	* @param: aLCContPlanPubAmntRelaSubSchema LCContPlanPubAmntRelaSubSchema
	**/
	public void setSchema(LCContPlanPubAmntRelaSubSchema aLCContPlanPubAmntRelaSubSchema)
	{
		this.PolicyNo = aLCContPlanPubAmntRelaSubSchema.getPolicyNo();
		this.SysPlanCode = aLCContPlanPubAmntRelaSubSchema.getSysPlanCode();
		this.PlanCode = aLCContPlanPubAmntRelaSubSchema.getPlanCode();
		this.RiskCode = aLCContPlanPubAmntRelaSubSchema.getRiskCode();
		this.DutyCode = aLCContPlanPubAmntRelaSubSchema.getDutyCode();
		this.PubFlag = aLCContPlanPubAmntRelaSubSchema.getPubFlag();
		this.DutyLimitAmnt = aLCContPlanPubAmntRelaSubSchema.getDutyLimitAmnt();
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
			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("PubFlag") == null )
				this.PubFlag = null;
			else
				this.PubFlag = rs.getString("PubFlag").trim();

			if( rs.getString("DutyLimitAmnt") == null )
				this.DutyLimitAmnt = null;
			else
				this.DutyLimitAmnt = rs.getString("DutyLimitAmnt").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCContPlanPubAmntRelaSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanPubAmntRelaSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContPlanPubAmntRelaSubSchema getSchema()
	{
		LCContPlanPubAmntRelaSubSchema aLCContPlanPubAmntRelaSubSchema = new LCContPlanPubAmntRelaSubSchema();
		aLCContPlanPubAmntRelaSubSchema.setSchema(this);
		return aLCContPlanPubAmntRelaSubSchema;
	}

	public LCContPlanPubAmntRelaSubDB getDB()
	{
		LCContPlanPubAmntRelaSubDB aDBOper = new LCContPlanPubAmntRelaSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanPubAmntRelaSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolicyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PubFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyLimitAmnt));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanPubAmntRelaSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PubFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DutyLimitAmnt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanPubAmntRelaSubSchema";
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
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PubFlag));
		}
		if (FCode.equalsIgnoreCase("DutyLimitAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyLimitAmnt));
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
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PubFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DutyLimitAmnt);
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

		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysPlanCode = FValue.trim();
			}
			else
				SysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("PubFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PubFlag = FValue.trim();
			}
			else
				PubFlag = null;
		}
		if (FCode.equalsIgnoreCase("DutyLimitAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyLimitAmnt = FValue.trim();
			}
			else
				DutyLimitAmnt = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContPlanPubAmntRelaSubSchema other = (LCContPlanPubAmntRelaSubSchema)otherObject;
		return
			PolicyNo.equals(other.getPolicyNo())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& PubFlag.equals(other.getPubFlag())
			&& DutyLimitAmnt.equals(other.getDutyLimitAmnt());
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
		if( strFieldName.equals("PolicyNo") ) {
			return 0;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 1;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("PubFlag") ) {
			return 5;
		}
		if( strFieldName.equals("DutyLimitAmnt") ) {
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
				strFieldName = "PolicyNo";
				break;
			case 1:
				strFieldName = "SysPlanCode";
				break;
			case 2:
				strFieldName = "PlanCode";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "PubFlag";
				break;
			case 6:
				strFieldName = "DutyLimitAmnt";
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
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PubFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyLimitAmnt") ) {
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
