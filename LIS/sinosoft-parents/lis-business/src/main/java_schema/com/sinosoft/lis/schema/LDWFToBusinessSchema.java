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
import com.sinosoft.lis.db.LDWFToBusinessDB;

/*
 * <p>ClassName: LDWFToBusinessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LDWFToBusinessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDWFToBusinessSchema.class);
	// @Field
	/** 功能id */
	private String FunctionID;
	/** 表序列号 */
	private String TableSerialno;
	/** 业务表 */
	private String BusinessTable;
	/** 业务表名 */
	private String BusinessTableName;
	/** 关联条件 */
	private String LinkCondition;
	/** 版本 */
	private String ClassVersion;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDWFToBusinessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "FunctionID";
		pk[1] = "TableSerialno";

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
		LDWFToBusinessSchema cloned = (LDWFToBusinessSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFunctionID()
	{
		return FunctionID;
	}
	public void setFunctionID(String aFunctionID)
	{
		if(aFunctionID!=null && aFunctionID.length()>10)
			throw new IllegalArgumentException("功能idFunctionID值"+aFunctionID+"的长度"+aFunctionID.length()+"大于最大值10");
		FunctionID = aFunctionID;
	}
	public String getTableSerialno()
	{
		return TableSerialno;
	}
	public void setTableSerialno(String aTableSerialno)
	{
		if(aTableSerialno!=null && aTableSerialno.length()>2)
			throw new IllegalArgumentException("表序列号TableSerialno值"+aTableSerialno+"的长度"+aTableSerialno.length()+"大于最大值2");
		TableSerialno = aTableSerialno;
	}
	public String getBusinessTable()
	{
		return BusinessTable;
	}
	public void setBusinessTable(String aBusinessTable)
	{
		if(aBusinessTable!=null && aBusinessTable.length()>40)
			throw new IllegalArgumentException("业务表BusinessTable值"+aBusinessTable+"的长度"+aBusinessTable.length()+"大于最大值40");
		BusinessTable = aBusinessTable;
	}
	public String getBusinessTableName()
	{
		return BusinessTableName;
	}
	public void setBusinessTableName(String aBusinessTableName)
	{
		if(aBusinessTableName!=null && aBusinessTableName.length()>100)
			throw new IllegalArgumentException("业务表名BusinessTableName值"+aBusinessTableName+"的长度"+aBusinessTableName.length()+"大于最大值100");
		BusinessTableName = aBusinessTableName;
	}
	public String getLinkCondition()
	{
		return LinkCondition;
	}
	public void setLinkCondition(String aLinkCondition)
	{
		if(aLinkCondition!=null && aLinkCondition.length()>200)
			throw new IllegalArgumentException("关联条件LinkCondition值"+aLinkCondition+"的长度"+aLinkCondition.length()+"大于最大值200");
		LinkCondition = aLinkCondition;
	}
	/**
	* 版本，定义1——个人工作池 第一个版本，2——公共工作池第二版本
	*/
	public String getClassVersion()
	{
		return ClassVersion;
	}
	public void setClassVersion(String aClassVersion)
	{
		if(aClassVersion!=null && aClassVersion.length()>2)
			throw new IllegalArgumentException("版本ClassVersion值"+aClassVersion+"的长度"+aClassVersion.length()+"大于最大值2");
		ClassVersion = aClassVersion;
	}

	/**
	* 使用另外一个 LDWFToBusinessSchema 对象给 Schema 赋值
	* @param: aLDWFToBusinessSchema LDWFToBusinessSchema
	**/
	public void setSchema(LDWFToBusinessSchema aLDWFToBusinessSchema)
	{
		this.FunctionID = aLDWFToBusinessSchema.getFunctionID();
		this.TableSerialno = aLDWFToBusinessSchema.getTableSerialno();
		this.BusinessTable = aLDWFToBusinessSchema.getBusinessTable();
		this.BusinessTableName = aLDWFToBusinessSchema.getBusinessTableName();
		this.LinkCondition = aLDWFToBusinessSchema.getLinkCondition();
		this.ClassVersion = aLDWFToBusinessSchema.getClassVersion();
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
			if( rs.getString("FunctionID") == null )
				this.FunctionID = null;
			else
				this.FunctionID = rs.getString("FunctionID").trim();

			if( rs.getString("TableSerialno") == null )
				this.TableSerialno = null;
			else
				this.TableSerialno = rs.getString("TableSerialno").trim();

			if( rs.getString("BusinessTable") == null )
				this.BusinessTable = null;
			else
				this.BusinessTable = rs.getString("BusinessTable").trim();

			if( rs.getString("BusinessTableName") == null )
				this.BusinessTableName = null;
			else
				this.BusinessTableName = rs.getString("BusinessTableName").trim();

			if( rs.getString("LinkCondition") == null )
				this.LinkCondition = null;
			else
				this.LinkCondition = rs.getString("LinkCondition").trim();

			if( rs.getString("ClassVersion") == null )
				this.ClassVersion = null;
			else
				this.ClassVersion = rs.getString("ClassVersion").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDWFToBusiness表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFToBusinessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDWFToBusinessSchema getSchema()
	{
		LDWFToBusinessSchema aLDWFToBusinessSchema = new LDWFToBusinessSchema();
		aLDWFToBusinessSchema.setSchema(this);
		return aLDWFToBusinessSchema;
	}

	public LDWFToBusinessDB getDB()
	{
		LDWFToBusinessDB aDBOper = new LDWFToBusinessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFToBusiness描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FunctionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TableSerialno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessTable)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusinessTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LinkCondition)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClassVersion));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDWFToBusiness>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FunctionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TableSerialno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BusinessTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BusinessTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LinkCondition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ClassVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDWFToBusinessSchema";
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
		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FunctionID));
		}
		if (FCode.equalsIgnoreCase("TableSerialno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableSerialno));
		}
		if (FCode.equalsIgnoreCase("BusinessTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessTable));
		}
		if (FCode.equalsIgnoreCase("BusinessTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessTableName));
		}
		if (FCode.equalsIgnoreCase("LinkCondition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LinkCondition));
		}
		if (FCode.equalsIgnoreCase("ClassVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClassVersion));
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
				strFieldValue = StrTool.GBKToUnicode(FunctionID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TableSerialno);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BusinessTable);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BusinessTableName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LinkCondition);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ClassVersion);
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

		if (FCode.equalsIgnoreCase("FunctionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FunctionID = FValue.trim();
			}
			else
				FunctionID = null;
		}
		if (FCode.equalsIgnoreCase("TableSerialno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableSerialno = FValue.trim();
			}
			else
				TableSerialno = null;
		}
		if (FCode.equalsIgnoreCase("BusinessTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessTable = FValue.trim();
			}
			else
				BusinessTable = null;
		}
		if (FCode.equalsIgnoreCase("BusinessTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessTableName = FValue.trim();
			}
			else
				BusinessTableName = null;
		}
		if (FCode.equalsIgnoreCase("LinkCondition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LinkCondition = FValue.trim();
			}
			else
				LinkCondition = null;
		}
		if (FCode.equalsIgnoreCase("ClassVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClassVersion = FValue.trim();
			}
			else
				ClassVersion = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDWFToBusinessSchema other = (LDWFToBusinessSchema)otherObject;
		return
			FunctionID.equals(other.getFunctionID())
			&& TableSerialno.equals(other.getTableSerialno())
			&& BusinessTable.equals(other.getBusinessTable())
			&& BusinessTableName.equals(other.getBusinessTableName())
			&& LinkCondition.equals(other.getLinkCondition())
			&& ClassVersion.equals(other.getClassVersion());
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
		if( strFieldName.equals("FunctionID") ) {
			return 0;
		}
		if( strFieldName.equals("TableSerialno") ) {
			return 1;
		}
		if( strFieldName.equals("BusinessTable") ) {
			return 2;
		}
		if( strFieldName.equals("BusinessTableName") ) {
			return 3;
		}
		if( strFieldName.equals("LinkCondition") ) {
			return 4;
		}
		if( strFieldName.equals("ClassVersion") ) {
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
				strFieldName = "FunctionID";
				break;
			case 1:
				strFieldName = "TableSerialno";
				break;
			case 2:
				strFieldName = "BusinessTable";
				break;
			case 3:
				strFieldName = "BusinessTableName";
				break;
			case 4:
				strFieldName = "LinkCondition";
				break;
			case 5:
				strFieldName = "ClassVersion";
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
		if( strFieldName.equals("FunctionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TableSerialno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LinkCondition") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClassVersion") ) {
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
