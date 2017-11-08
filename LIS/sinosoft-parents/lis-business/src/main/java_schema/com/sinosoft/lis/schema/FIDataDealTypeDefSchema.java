/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIDataDealTypeDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataDealTypeDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataDealTypeDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataDealTypeDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 数据类型编码 */
	private String DataType;
	/** 数据类型名称 */
	private String DataName;
	/** 明细索引代码 */
	private String DetailIndexID;
	/** 明细索引名称 */
	private String DetailIndexName;
	/** 规则处理类 */
	private String ProcessClass;
	/** 规则描述 */
	private String ProcessReMark;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataDealTypeDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "DataType";

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
                FIDataDealTypeDefSchema cloned = (FIDataDealTypeDefSchema)super.clone();
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
	public String getDataName()
	{
		return DataName;
	}
	public void setDataName(String aDataName)
	{
		DataName = aDataName;
	}
	public String getDetailIndexID()
	{
		return DetailIndexID;
	}
	public void setDetailIndexID(String aDetailIndexID)
	{
		DetailIndexID = aDetailIndexID;
	}
	public String getDetailIndexName()
	{
		return DetailIndexName;
	}
	public void setDetailIndexName(String aDetailIndexName)
	{
		DetailIndexName = aDetailIndexName;
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
	* 使用另外一个 FIDataDealTypeDefSchema 对象给 Schema 赋值
	* @param: aFIDataDealTypeDefSchema FIDataDealTypeDefSchema
	**/
	public void setSchema(FIDataDealTypeDefSchema aFIDataDealTypeDefSchema)
	{
		this.VersionNo = aFIDataDealTypeDefSchema.getVersionNo();
		this.DataType = aFIDataDealTypeDefSchema.getDataType();
		this.DataName = aFIDataDealTypeDefSchema.getDataName();
		this.DetailIndexID = aFIDataDealTypeDefSchema.getDetailIndexID();
		this.DetailIndexName = aFIDataDealTypeDefSchema.getDetailIndexName();
		this.ProcessClass = aFIDataDealTypeDefSchema.getProcessClass();
		this.ProcessReMark = aFIDataDealTypeDefSchema.getProcessReMark();
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

			if( rs.getString("DataName") == null )
				this.DataName = null;
			else
				this.DataName = rs.getString("DataName").trim();

			if( rs.getString("DetailIndexID") == null )
				this.DetailIndexID = null;
			else
				this.DetailIndexID = rs.getString("DetailIndexID").trim();

			if( rs.getString("DetailIndexName") == null )
				this.DetailIndexName = null;
			else
				this.DetailIndexName = rs.getString("DetailIndexName").trim();

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
			logger.debug("数据库中的FIDataDealTypeDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealTypeDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataDealTypeDefSchema getSchema()
	{
		FIDataDealTypeDefSchema aFIDataDealTypeDefSchema = new FIDataDealTypeDefSchema();
		aFIDataDealTypeDefSchema.setSchema(this);
		return aFIDataDealTypeDefSchema;
	}

	public FIDataDealTypeDefDB getDB()
	{
		FIDataDealTypeDefDB aDBOper = new FIDataDealTypeDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealTypeDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProcessClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ProcessReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataDealTypeDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DataType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DataName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DetailIndexID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DetailIndexName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ProcessClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProcessReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataDealTypeDefSchema";
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
		if (FCode.equalsIgnoreCase("DataName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataName));
		}
		if (FCode.equalsIgnoreCase("DetailIndexID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexID));
		}
		if (FCode.equalsIgnoreCase("DetailIndexName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexName));
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
				strFieldValue = StrTool.GBKToUnicode(DataName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ProcessClass);
				break;
			case 6:
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
		if (FCode.equalsIgnoreCase("DataName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataName = FValue.trim();
			}
			else
				DataName = null;
		}
		if (FCode.equalsIgnoreCase("DetailIndexID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailIndexID = FValue.trim();
			}
			else
				DetailIndexID = null;
		}
		if (FCode.equalsIgnoreCase("DetailIndexName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailIndexName = FValue.trim();
			}
			else
				DetailIndexName = null;
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
		FIDataDealTypeDefSchema other = (FIDataDealTypeDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& DataType.equals(other.getDataType())
			&& DataName.equals(other.getDataName())
			&& DetailIndexID.equals(other.getDetailIndexID())
			&& DetailIndexName.equals(other.getDetailIndexName())
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
		if( strFieldName.equals("DataName") ) {
			return 2;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return 3;
		}
		if( strFieldName.equals("DetailIndexName") ) {
			return 4;
		}
		if( strFieldName.equals("ProcessClass") ) {
			return 5;
		}
		if( strFieldName.equals("ProcessReMark") ) {
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
				strFieldName = "DataType";
				break;
			case 2:
				strFieldName = "DataName";
				break;
			case 3:
				strFieldName = "DetailIndexID";
				break;
			case 4:
				strFieldName = "DetailIndexName";
				break;
			case 5:
				strFieldName = "ProcessClass";
				break;
			case 6:
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
		if( strFieldName.equals("DataName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexName") ) {
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
