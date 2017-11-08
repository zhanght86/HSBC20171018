/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIDataDealRulesDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataDealRulesDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataDealRulesDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataDealRulesDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 数据类型编码 */
	private String DataType;
	/** 处理编号 */
	private String ProcessID;
	/** 规则处理类 */
	private String ProcessClass;
	/** 规则描述 */
	private String ProcessReMark;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataDealRulesDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "VersionNo";
		pk[1] = "DataType";
		pk[2] = "ProcessID";

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
                FIDataDealRulesDefSchema cloned = (FIDataDealRulesDefSchema)super.clone();
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
	public String getDataType()
	{
		return DataType;
	}
	public void setDataType(String aDataType)
	{
		DataType = aDataType;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		ProcessID = aProcessID;
	}
	public String getProcessClass()
	{
		return ProcessClass;
	}
	public void setProcessClass(String aProcessClass)
	{
		ProcessClass = aProcessClass;
	}
	public String getProcessReMark()
	{
		return ProcessReMark;
	}
	public void setProcessReMark(String aProcessReMark)
	{
		ProcessReMark = aProcessReMark;
	}

	/**
	* 使用另外一个 FIDataDealRulesDefSchema 对象给 Schema 赋值
	* @param: aFIDataDealRulesDefSchema FIDataDealRulesDefSchema
	**/
	public void setSchema(FIDataDealRulesDefSchema aFIDataDealRulesDefSchema)
	{
		this.VersionNo = aFIDataDealRulesDefSchema.getVersionNo();
		this.DataType = aFIDataDealRulesDefSchema.getDataType();
		this.ProcessID = aFIDataDealRulesDefSchema.getProcessID();
		this.ProcessClass = aFIDataDealRulesDefSchema.getProcessClass();
		this.ProcessReMark = aFIDataDealRulesDefSchema.getProcessReMark();
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

			if( rs.getString("DataType") == null )
				this.DataType = null;
			else
				this.DataType = rs.getString("DataType").trim();

			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("ProcessClass") == null )
				this.ProcessClass = null;
			else
				this.ProcessClass = rs.getString("ProcessClass").trim();

			if( rs.getString("ProcessReMark") == null )
				this.ProcessReMark = null;
			else
				this.ProcessReMark = rs.getString("ProcessReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataDealRulesDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealRulesDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataDealRulesDefSchema getSchema()
	{
		FIDataDealRulesDefSchema aFIDataDealRulesDefSchema = new FIDataDealRulesDefSchema();
		aFIDataDealRulesDefSchema.setSchema(this);
		return aFIDataDealRulesDefSchema;
	}

	public FIDataDealRulesDefDB getDB()
	{
		FIDataDealRulesDefDB aDBOper = new FIDataDealRulesDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealRulesDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProcessClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProcessReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealRulesDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DataType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProcessClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProcessReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealRulesDefSchema";
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
		if (FCode.equalsIgnoreCase("DataType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataType));
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("ProcessClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessClass));
		}
		if (FCode.equalsIgnoreCase("ProcessReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessReMark));
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
				strFieldValue = StrTool.GBKToUnicode(DataType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProcessClass);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProcessReMark);
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
		if (FCode.equalsIgnoreCase("DataType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataType = FValue.trim();
			}
			else
				DataType = null;
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("ProcessClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessClass = FValue.trim();
			}
			else
				ProcessClass = null;
		}
		if (FCode.equalsIgnoreCase("ProcessReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessReMark = FValue.trim();
			}
			else
				ProcessReMark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataDealRulesDefSchema other = (FIDataDealRulesDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& DataType.equals(other.getDataType())
			&& ProcessID.equals(other.getProcessID())
			&& ProcessClass.equals(other.getProcessClass())
			&& ProcessReMark.equals(other.getProcessReMark());
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
		if( strFieldName.equals("DataType") ) {
			return 1;
		}
		if( strFieldName.equals("ProcessID") ) {
			return 2;
		}
		if( strFieldName.equals("ProcessClass") ) {
			return 3;
		}
		if( strFieldName.equals("ProcessReMark") ) {
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
				strFieldName = "DataType";
				break;
			case 2:
				strFieldName = "ProcessID";
				break;
			case 3:
				strFieldName = "ProcessClass";
				break;
			case 4:
				strFieldName = "ProcessReMark";
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
		if( strFieldName.equals("DataType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessReMark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
