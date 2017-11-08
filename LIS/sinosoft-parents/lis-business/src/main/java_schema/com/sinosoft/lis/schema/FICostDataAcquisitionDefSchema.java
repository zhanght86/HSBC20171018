/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FICostDataAcquisitionDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FICostDataAcquisitionDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FICostDataAcquisitionDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICostDataAcquisitionDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 数据采集编号 */
	private String AcquisitionID;
	/** 数据采集类型 */
	private String AcquisitionType;
	/** 费用或数据类型编码 */
	private String CostOrDataID;
	/** 采集数据库类型 */
	private String DataSourceType;
	/** 数据采集方式 */
	private String DistillMode;
	/** 批量采集sql */
	private String DistillSQL;
	/** 批量采集处理类 */
	private String DistillClass;
	/** 单一采集数sql */
	private String DistillSQLForOne;
	/** 单一采集处理类 */
	private String DistillClassForOne;
	/** 描述 */
	private String Remark;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FICostDataAcquisitionDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "AcquisitionID";

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
                FICostDataAcquisitionDefSchema cloned = (FICostDataAcquisitionDefSchema)super.clone();
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
	public String getAcquisitionID()
	{
		return AcquisitionID;
	}
	public void setAcquisitionID(String aAcquisitionID)
	{
		AcquisitionID = aAcquisitionID;
	}
	public String getAcquisitionType()
	{
		return AcquisitionType;
	}
	public void setAcquisitionType(String aAcquisitionType)
	{
		AcquisitionType = aAcquisitionType;
	}
	public String getCostOrDataID()
	{
		return CostOrDataID;
	}
	public void setCostOrDataID(String aCostOrDataID)
	{
		CostOrDataID = aCostOrDataID;
	}
	public String getDataSourceType()
	{
		return DataSourceType;
	}
	public void setDataSourceType(String aDataSourceType)
	{
		DataSourceType = aDataSourceType;
	}
	public String getDistillMode()
	{
		return DistillMode;
	}
	public void setDistillMode(String aDistillMode)
	{
		DistillMode = aDistillMode;
	}
	public String getDistillSQL()
	{
		return DistillSQL;
	}
	public void setDistillSQL(String aDistillSQL)
	{
		DistillSQL = aDistillSQL;
	}
	public String getDistillClass()
	{
		return DistillClass;
	}
	public void setDistillClass(String aDistillClass)
	{
		DistillClass = aDistillClass;
	}
	public String getDistillSQLForOne()
	{
		return DistillSQLForOne;
	}
	public void setDistillSQLForOne(String aDistillSQLForOne)
	{
		DistillSQLForOne = aDistillSQLForOne;
	}
	public String getDistillClassForOne()
	{
		return DistillClassForOne;
	}
	public void setDistillClassForOne(String aDistillClassForOne)
	{
		DistillClassForOne = aDistillClassForOne;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 FICostDataAcquisitionDefSchema 对象给 Schema 赋值
	* @param: aFICostDataAcquisitionDefSchema FICostDataAcquisitionDefSchema
	**/
	public void setSchema(FICostDataAcquisitionDefSchema aFICostDataAcquisitionDefSchema)
	{
		this.VersionNo = aFICostDataAcquisitionDefSchema.getVersionNo();
		this.AcquisitionID = aFICostDataAcquisitionDefSchema.getAcquisitionID();
		this.AcquisitionType = aFICostDataAcquisitionDefSchema.getAcquisitionType();
		this.CostOrDataID = aFICostDataAcquisitionDefSchema.getCostOrDataID();
		this.DataSourceType = aFICostDataAcquisitionDefSchema.getDataSourceType();
		this.DistillMode = aFICostDataAcquisitionDefSchema.getDistillMode();
		this.DistillSQL = aFICostDataAcquisitionDefSchema.getDistillSQL();
		this.DistillClass = aFICostDataAcquisitionDefSchema.getDistillClass();
		this.DistillSQLForOne = aFICostDataAcquisitionDefSchema.getDistillSQLForOne();
		this.DistillClassForOne = aFICostDataAcquisitionDefSchema.getDistillClassForOne();
		this.Remark = aFICostDataAcquisitionDefSchema.getRemark();
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

			if( rs.getString("AcquisitionID") == null )
				this.AcquisitionID = null;
			else
				this.AcquisitionID = rs.getString("AcquisitionID").trim();

			if( rs.getString("AcquisitionType") == null )
				this.AcquisitionType = null;
			else
				this.AcquisitionType = rs.getString("AcquisitionType").trim();

			if( rs.getString("CostOrDataID") == null )
				this.CostOrDataID = null;
			else
				this.CostOrDataID = rs.getString("CostOrDataID").trim();

			if( rs.getString("DataSourceType") == null )
				this.DataSourceType = null;
			else
				this.DataSourceType = rs.getString("DataSourceType").trim();

			if( rs.getString("DistillMode") == null )
				this.DistillMode = null;
			else
				this.DistillMode = rs.getString("DistillMode").trim();

			if( rs.getString("DistillSQL") == null )
				this.DistillSQL = null;
			else
				this.DistillSQL = rs.getString("DistillSQL").trim();

			if( rs.getString("DistillClass") == null )
				this.DistillClass = null;
			else
				this.DistillClass = rs.getString("DistillClass").trim();

			if( rs.getString("DistillSQLForOne") == null )
				this.DistillSQLForOne = null;
			else
				this.DistillSQLForOne = rs.getString("DistillSQLForOne").trim();

			if( rs.getString("DistillClassForOne") == null )
				this.DistillClassForOne = null;
			else
				this.DistillClassForOne = rs.getString("DistillClassForOne").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FICostDataAcquisitionDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FICostDataAcquisitionDefSchema getSchema()
	{
		FICostDataAcquisitionDefSchema aFICostDataAcquisitionDefSchema = new FICostDataAcquisitionDefSchema();
		aFICostDataAcquisitionDefSchema.setSchema(this);
		return aFICostDataAcquisitionDefSchema;
	}

	public FICostDataAcquisitionDefDB getDB()
	{
		FICostDataAcquisitionDefDB aDBOper = new FICostDataAcquisitionDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostDataAcquisitionDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CostOrDataID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataSourceType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DistillMode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DistillSQL)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DistillClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DistillSQLForOne)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DistillClassForOne)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostDataAcquisitionDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AcquisitionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AcquisitionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CostOrDataID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DataSourceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DistillMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DistillSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DistillClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DistillSQLForOne = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DistillClassForOne = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefSchema";
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
		if (FCode.equalsIgnoreCase("AcquisitionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcquisitionID));
		}
		if (FCode.equalsIgnoreCase("AcquisitionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcquisitionType));
		}
		if (FCode.equalsIgnoreCase("CostOrDataID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostOrDataID));
		}
		if (FCode.equalsIgnoreCase("DataSourceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataSourceType));
		}
		if (FCode.equalsIgnoreCase("DistillMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistillMode));
		}
		if (FCode.equalsIgnoreCase("DistillSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistillSQL));
		}
		if (FCode.equalsIgnoreCase("DistillClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistillClass));
		}
		if (FCode.equalsIgnoreCase("DistillSQLForOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistillSQLForOne));
		}
		if (FCode.equalsIgnoreCase("DistillClassForOne"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DistillClassForOne));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(AcquisitionID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AcquisitionType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CostOrDataID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DataSourceType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DistillMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DistillSQL);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DistillClass);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DistillSQLForOne);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DistillClassForOne);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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
		if (FCode.equalsIgnoreCase("AcquisitionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcquisitionID = FValue.trim();
			}
			else
				AcquisitionID = null;
		}
		if (FCode.equalsIgnoreCase("AcquisitionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcquisitionType = FValue.trim();
			}
			else
				AcquisitionType = null;
		}
		if (FCode.equalsIgnoreCase("CostOrDataID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CostOrDataID = FValue.trim();
			}
			else
				CostOrDataID = null;
		}
		if (FCode.equalsIgnoreCase("DataSourceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataSourceType = FValue.trim();
			}
			else
				DataSourceType = null;
		}
		if (FCode.equalsIgnoreCase("DistillMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistillMode = FValue.trim();
			}
			else
				DistillMode = null;
		}
		if (FCode.equalsIgnoreCase("DistillSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistillSQL = FValue.trim();
			}
			else
				DistillSQL = null;
		}
		if (FCode.equalsIgnoreCase("DistillClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistillClass = FValue.trim();
			}
			else
				DistillClass = null;
		}
		if (FCode.equalsIgnoreCase("DistillSQLForOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistillSQLForOne = FValue.trim();
			}
			else
				DistillSQLForOne = null;
		}
		if (FCode.equalsIgnoreCase("DistillClassForOne"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DistillClassForOne = FValue.trim();
			}
			else
				DistillClassForOne = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FICostDataAcquisitionDefSchema other = (FICostDataAcquisitionDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& AcquisitionID.equals(other.getAcquisitionID())
			&& AcquisitionType.equals(other.getAcquisitionType())
			&& CostOrDataID.equals(other.getCostOrDataID())
			&& DataSourceType.equals(other.getDataSourceType())
			&& DistillMode.equals(other.getDistillMode())
			&& DistillSQL.equals(other.getDistillSQL())
			&& DistillClass.equals(other.getDistillClass())
			&& DistillSQLForOne.equals(other.getDistillSQLForOne())
			&& DistillClassForOne.equals(other.getDistillClassForOne())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("AcquisitionID") ) {
			return 1;
		}
		if( strFieldName.equals("AcquisitionType") ) {
			return 2;
		}
		if( strFieldName.equals("CostOrDataID") ) {
			return 3;
		}
		if( strFieldName.equals("DataSourceType") ) {
			return 4;
		}
		if( strFieldName.equals("DistillMode") ) {
			return 5;
		}
		if( strFieldName.equals("DistillSQL") ) {
			return 6;
		}
		if( strFieldName.equals("DistillClass") ) {
			return 7;
		}
		if( strFieldName.equals("DistillSQLForOne") ) {
			return 8;
		}
		if( strFieldName.equals("DistillClassForOne") ) {
			return 9;
		}
		if( strFieldName.equals("Remark") ) {
			return 10;
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
				strFieldName = "AcquisitionID";
				break;
			case 2:
				strFieldName = "AcquisitionType";
				break;
			case 3:
				strFieldName = "CostOrDataID";
				break;
			case 4:
				strFieldName = "DataSourceType";
				break;
			case 5:
				strFieldName = "DistillMode";
				break;
			case 6:
				strFieldName = "DistillSQL";
				break;
			case 7:
				strFieldName = "DistillClass";
				break;
			case 8:
				strFieldName = "DistillSQLForOne";
				break;
			case 9:
				strFieldName = "DistillClassForOne";
				break;
			case 10:
				strFieldName = "Remark";
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
		if( strFieldName.equals("AcquisitionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcquisitionType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CostOrDataID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataSourceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistillMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistillSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistillClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistillSQLForOne") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DistillClassForOne") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
