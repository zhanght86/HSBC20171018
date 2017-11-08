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
import com.sinosoft.lis.db.LOBContPlanEnginDetailDB;

/*
 * 与数据库结构不一致，暂不处理
 * <p>ClassName: LOBContPlanEnginDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LOBContPlanEnginDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBContPlanEnginDetailSchema.class);
	// @Field
	/** 业务类型 */
	private String BussType;
	/** 业务号码 */
	private String BussNo;
	/** 保单号码 */
	private String PolicyNo;
	/** 投保单号 */
	private String PropNo;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 工程明细编码 */
	private String EnginCode;
	/** 其他描述 */
	private String OtherDesc;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBContPlanEnginDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "BussType";
		pk[1] = "BussNo";
		pk[2] = "PolicyNo";
		pk[3] = "SysPlanCode";
		pk[4] = "PlanCode";
		pk[5] = "EnginCode";

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
		LOBContPlanEnginDetailSchema cloned = (LOBContPlanEnginDetailSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>10)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值10");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
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
	public String getPropNo()
	{
		return PropNo;
	}
	public void setPropNo(String aPropNo)
	{
		if(aPropNo!=null && aPropNo.length()>20)
			throw new IllegalArgumentException("投保单号PropNo值"+aPropNo+"的长度"+aPropNo.length()+"大于最大值20");
		PropNo = aPropNo;
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
	/**
	* 一般询价：000(固定)<p>
	* 项目询价：正常方案编码
	*/
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
	public String getEnginCode()
	{
		return EnginCode;
	}
	public void setEnginCode(String aEnginCode)
	{
		if(aEnginCode!=null && aEnginCode.length()>2)
			throw new IllegalArgumentException("工程明细编码EnginCode值"+aEnginCode+"的长度"+aEnginCode.length()+"大于最大值2");
		EnginCode = aEnginCode;
	}
	public String getOtherDesc()
	{
		return OtherDesc;
	}
	public void setOtherDesc(String aOtherDesc)
	{
		if(aOtherDesc!=null && aOtherDesc.length()>100)
			throw new IllegalArgumentException("其他描述OtherDesc值"+aOtherDesc+"的长度"+aOtherDesc.length()+"大于最大值100");
		OtherDesc = aOtherDesc;
	}

	/**
	* 使用另外一个 LOBContPlanEnginDetailSchema 对象给 Schema 赋值
	* @param: aLOBContPlanEnginDetailSchema LOBContPlanEnginDetailSchema
	**/
	public void setSchema(LOBContPlanEnginDetailSchema aLOBContPlanEnginDetailSchema)
	{
		this.BussType = aLOBContPlanEnginDetailSchema.getBussType();
		this.BussNo = aLOBContPlanEnginDetailSchema.getBussNo();
		this.PolicyNo = aLOBContPlanEnginDetailSchema.getPolicyNo();
		this.PropNo = aLOBContPlanEnginDetailSchema.getPropNo();
		this.SysPlanCode = aLOBContPlanEnginDetailSchema.getSysPlanCode();
		this.PlanCode = aLOBContPlanEnginDetailSchema.getPlanCode();
		this.EnginCode = aLOBContPlanEnginDetailSchema.getEnginCode();
		this.OtherDesc = aLOBContPlanEnginDetailSchema.getOtherDesc();
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
			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("PolicyNo") == null )
				this.PolicyNo = null;
			else
				this.PolicyNo = rs.getString("PolicyNo").trim();

			if( rs.getString("PropNo") == null )
				this.PropNo = null;
			else
				this.PropNo = rs.getString("PropNo").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("EnginCode") == null )
				this.EnginCode = null;
			else
				this.EnginCode = rs.getString("EnginCode").trim();

			if( rs.getString("OtherDesc") == null )
				this.OtherDesc = null;
			else
				this.OtherDesc = rs.getString("OtherDesc").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBContPlanEnginDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBContPlanEnginDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBContPlanEnginDetailSchema getSchema()
	{
		LOBContPlanEnginDetailSchema aLOBContPlanEnginDetailSchema = new LOBContPlanEnginDetailSchema();
		aLOBContPlanEnginDetailSchema.setSchema(this);
		return aLOBContPlanEnginDetailSchema;
	}

	public LOBContPlanEnginDetailDB getDB()
	{
		LOBContPlanEnginDetailDB aDBOper = new LOBContPlanEnginDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBContPlanEnginDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolicyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBContPlanEnginDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			EnginCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OtherDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBContPlanEnginDetailSchema";
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolicyNo));
		}
		if (FCode.equalsIgnoreCase("PropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropNo));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("EnginCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EnginCode));
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherDesc));
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
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PropNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EnginCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OtherDesc);
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

		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("PolicyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolicyNo = FValue.trim();
			}
			else
				PolicyNo = null;
		}
		if (FCode.equalsIgnoreCase("PropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropNo = FValue.trim();
			}
			else
				PropNo = null;
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
		if (FCode.equalsIgnoreCase("EnginCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EnginCode = FValue.trim();
			}
			else
				EnginCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherDesc = FValue.trim();
			}
			else
				OtherDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBContPlanEnginDetailSchema other = (LOBContPlanEnginDetailSchema)otherObject;
		return
			BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& PolicyNo.equals(other.getPolicyNo())
			&& PropNo.equals(other.getPropNo())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& EnginCode.equals(other.getEnginCode())
			&& OtherDesc.equals(other.getOtherDesc());
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
		if( strFieldName.equals("BussType") ) {
			return 0;
		}
		if( strFieldName.equals("BussNo") ) {
			return 1;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return 2;
		}
		if( strFieldName.equals("PropNo") ) {
			return 3;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("EnginCode") ) {
			return 6;
		}
		if( strFieldName.equals("OtherDesc") ) {
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
				strFieldName = "BussType";
				break;
			case 1:
				strFieldName = "BussNo";
				break;
			case 2:
				strFieldName = "PolicyNo";
				break;
			case 3:
				strFieldName = "PropNo";
				break;
			case 4:
				strFieldName = "SysPlanCode";
				break;
			case 5:
				strFieldName = "PlanCode";
				break;
			case 6:
				strFieldName = "EnginCode";
				break;
			case 7:
				strFieldName = "OtherDesc";
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
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolicyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnginCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherDesc") ) {
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
