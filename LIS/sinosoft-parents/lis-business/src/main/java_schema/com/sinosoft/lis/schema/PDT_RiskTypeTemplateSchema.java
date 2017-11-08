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
import com.sinosoft.lis.db.PDT_RiskTypeTemplateDB;

/*
 * <p>ClassName: PDT_RiskTypeTemplateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PDT_RiskTypeTemplateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PDT_RiskTypeTemplateSchema.class);

	// @Field
	/** 模板id */
	private String TemplateID;
	/** 适用险种类型 */
	private String RiskType;
	/** 适用系统 */
	private String StandbyFlag1;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PDT_RiskTypeTemplateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TemplateID";

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
		PDT_RiskTypeTemplateSchema cloned = (PDT_RiskTypeTemplateSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTemplateID()
	{
		return TemplateID;
	}
	public void setTemplateID(String aTemplateID)
	{
		if(aTemplateID!=null && aTemplateID.length()>10)
			throw new IllegalArgumentException("模板idTemplateID值"+aTemplateID+"的长度"+aTemplateID.length()+"大于最大值10");
		TemplateID = aTemplateID;
	}
	public String getRiskType()
	{
		return RiskType;
	}
	public void setRiskType(String aRiskType)
	{
		if(aRiskType!=null && aRiskType.length()>10)
			throw new IllegalArgumentException("适用险种类型RiskType值"+aRiskType+"的长度"+aRiskType.length()+"大于最大值10");
		RiskType = aRiskType;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>20)
			throw new IllegalArgumentException("适用系统StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值20");
		StandbyFlag1 = aStandbyFlag1;
	}

	/**
	* 使用另外一个 PDT_RiskTypeTemplateSchema 对象给 Schema 赋值
	* @param: aPDT_RiskTypeTemplateSchema PDT_RiskTypeTemplateSchema
	**/
	public void setSchema(PDT_RiskTypeTemplateSchema aPDT_RiskTypeTemplateSchema)
	{
		this.TemplateID = aPDT_RiskTypeTemplateSchema.getTemplateID();
		this.RiskType = aPDT_RiskTypeTemplateSchema.getRiskType();
		this.StandbyFlag1 = aPDT_RiskTypeTemplateSchema.getStandbyFlag1();
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
			if( rs.getString("TemplateID") == null )
				this.TemplateID = null;
			else
				this.TemplateID = rs.getString("TemplateID").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PDT_RiskTypeTemplate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskTypeTemplateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PDT_RiskTypeTemplateSchema getSchema()
	{
		PDT_RiskTypeTemplateSchema aPDT_RiskTypeTemplateSchema = new PDT_RiskTypeTemplateSchema();
		aPDT_RiskTypeTemplateSchema.setSchema(this);
		return aPDT_RiskTypeTemplateSchema;
	}

	public PDT_RiskTypeTemplateDB getDB()
	{
		PDT_RiskTypeTemplateDB aDBOper = new PDT_RiskTypeTemplateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskTypeTemplate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TemplateID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPDT_RiskTypeTemplate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TemplateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PDT_RiskTypeTemplateSchema";
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
		if (FCode.equalsIgnoreCase("TemplateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TemplateID));
		}
		if (FCode.equalsIgnoreCase("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
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
				strFieldValue = StrTool.GBKToUnicode(TemplateID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
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

		if (FCode.equalsIgnoreCase("TemplateID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TemplateID = FValue.trim();
			}
			else
				TemplateID = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PDT_RiskTypeTemplateSchema other = (PDT_RiskTypeTemplateSchema)otherObject;
		return
			TemplateID.equals(other.getTemplateID())
			&& RiskType.equals(other.getRiskType())
			&& StandbyFlag1.equals(other.getStandbyFlag1());
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
		if( strFieldName.equals("TemplateID") ) {
			return 0;
		}
		if( strFieldName.equals("RiskType") ) {
			return 1;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 2;
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
				strFieldName = "TemplateID";
				break;
			case 1:
				strFieldName = "RiskType";
				break;
			case 2:
				strFieldName = "StandbyFlag1";
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
		if( strFieldName.equals("TemplateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
