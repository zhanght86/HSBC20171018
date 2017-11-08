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
import com.sinosoft.lis.db.LCContPlanEnginDetailDB;

/*
 * <p>ClassName: LCContPlanEnginDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCContPlanEnginDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContPlanEnginDetailSchema.class);
	// @Field
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

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContPlanEnginDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "PolicyNo";
		pk[1] = "SysPlanCode";
		pk[2] = "PlanCode";
		pk[3] = "EnginCode";

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
		LCContPlanEnginDetailSchema cloned = (LCContPlanEnginDetailSchema)super.clone();
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
	* 使用另外一个 LCContPlanEnginDetailSchema 对象给 Schema 赋值
	* @param: aLCContPlanEnginDetailSchema LCContPlanEnginDetailSchema
	**/
	public void setSchema(LCContPlanEnginDetailSchema aLCContPlanEnginDetailSchema)
	{
		this.PolicyNo = aLCContPlanEnginDetailSchema.getPolicyNo();
		this.PropNo = aLCContPlanEnginDetailSchema.getPropNo();
		this.SysPlanCode = aLCContPlanEnginDetailSchema.getSysPlanCode();
		this.PlanCode = aLCContPlanEnginDetailSchema.getPlanCode();
		this.EnginCode = aLCContPlanEnginDetailSchema.getEnginCode();
		this.OtherDesc = aLCContPlanEnginDetailSchema.getOtherDesc();
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
			logger.debug("数据库中的LCContPlanEnginDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanEnginDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContPlanEnginDetailSchema getSchema()
	{
		LCContPlanEnginDetailSchema aLCContPlanEnginDetailSchema = new LCContPlanEnginDetailSchema();
		aLCContPlanEnginDetailSchema.setSchema(this);
		return aLCContPlanEnginDetailSchema;
	}

	public LCContPlanEnginDetailDB getDB()
	{
		LCContPlanEnginDetailDB aDBOper = new LCContPlanEnginDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanEnginDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolicyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EnginCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanEnginDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolicyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EnginCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanEnginDetailSchema";
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
				strFieldValue = StrTool.GBKToUnicode(PolicyNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PropNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EnginCode);
				break;
			case 5:
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
		LCContPlanEnginDetailSchema other = (LCContPlanEnginDetailSchema)otherObject;
		return
			PolicyNo.equals(other.getPolicyNo())
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
		if( strFieldName.equals("PolicyNo") ) {
			return 0;
		}
		if( strFieldName.equals("PropNo") ) {
			return 1;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 2;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 3;
		}
		if( strFieldName.equals("EnginCode") ) {
			return 4;
		}
		if( strFieldName.equals("OtherDesc") ) {
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
				strFieldName = "PolicyNo";
				break;
			case 1:
				strFieldName = "PropNo";
				break;
			case 2:
				strFieldName = "SysPlanCode";
				break;
			case 3:
				strFieldName = "PlanCode";
				break;
			case 4:
				strFieldName = "EnginCode";
				break;
			case 5:
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
