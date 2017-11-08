/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sinosoft.lis.db.FIDataForDealDistilledInfoDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDataForDealDistilledInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDataForDealDistilledInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDataForDealDistilledInfoSchema.class);

	// @Field
	/** 数据采集批次号 */
	private String BatchNo;
	/** 数据采集编号 */
	private String AcquisitionID;
	/** 主键组合值 */
	private String KeyUnionValue;
	/** 数据流水号 */
	private String SerialNo;
	/** 数据类型 */
	private String DataType;
	/** 业务号码 */
	private String BusinessNo;
	/** 业务日期 */
	private Date BussDate;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDataForDealDistilledInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AcquisitionID";
		pk[1] = "KeyUnionValue";

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
                FIDataForDealDistilledInfoSchema cloned = (FIDataForDealDistilledInfoSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getAcquisitionID()
	{
		return AcquisitionID;
	}
	public void setAcquisitionID(String aAcquisitionID)
	{
		AcquisitionID = aAcquisitionID;
	}
	public String getKeyUnionValue()
	{
		return KeyUnionValue;
	}
	public void setKeyUnionValue(String aKeyUnionValue)
	{
		KeyUnionValue = aKeyUnionValue;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getDataType()
	{
		return DataType;
	}
	public void setDataType(String aDataType)
	{
		DataType = aDataType;
	}
	public String getBusinessNo()
	{
		return BusinessNo;
	}
	public void setBusinessNo(String aBusinessNo)
	{
		BusinessNo = aBusinessNo;
	}
	public String getBussDate()
	{
		if( BussDate != null )
			return fDate.getString(BussDate);
		else
			return null;
	}
	public void setBussDate(Date aBussDate)
	{
		BussDate = aBussDate;
	}
	public void setBussDate(String aBussDate)
	{
		if (aBussDate != null && !aBussDate.equals("") )
		{
			BussDate = fDate.getDate( aBussDate );
		}
		else
			BussDate = null;
	}

	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}

	/**
	* 使用另外一个 FIDataForDealDistilledInfoSchema 对象给 Schema 赋值
	* @param: aFIDataForDealDistilledInfoSchema FIDataForDealDistilledInfoSchema
	**/
	public void setSchema(FIDataForDealDistilledInfoSchema aFIDataForDealDistilledInfoSchema)
	{
		this.BatchNo = aFIDataForDealDistilledInfoSchema.getBatchNo();
		this.AcquisitionID = aFIDataForDealDistilledInfoSchema.getAcquisitionID();
		this.KeyUnionValue = aFIDataForDealDistilledInfoSchema.getKeyUnionValue();
		this.SerialNo = aFIDataForDealDistilledInfoSchema.getSerialNo();
		this.DataType = aFIDataForDealDistilledInfoSchema.getDataType();
		this.BusinessNo = aFIDataForDealDistilledInfoSchema.getBusinessNo();
		this.BussDate = fDate.getDate( aFIDataForDealDistilledInfoSchema.getBussDate());
		this.MakeDate = fDate.getDate( aFIDataForDealDistilledInfoSchema.getMakeDate());
		this.MakeTime = aFIDataForDealDistilledInfoSchema.getMakeTime();
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
			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("AcquisitionID") == null )
				this.AcquisitionID = null;
			else
				this.AcquisitionID = rs.getString("AcquisitionID").trim();

			if( rs.getString("KeyUnionValue") == null )
				this.KeyUnionValue = null;
			else
				this.KeyUnionValue = rs.getString("KeyUnionValue").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("DataType") == null )
				this.DataType = null;
			else
				this.DataType = rs.getString("DataType").trim();

			if( rs.getString("BusinessNo") == null )
				this.BusinessNo = null;
			else
				this.BusinessNo = rs.getString("BusinessNo").trim();

			this.BussDate = rs.getDate("BussDate");
			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDataForDealDistilledInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataForDealDistilledInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDataForDealDistilledInfoSchema getSchema()
	{
		FIDataForDealDistilledInfoSchema aFIDataForDealDistilledInfoSchema = new FIDataForDealDistilledInfoSchema();
		aFIDataForDealDistilledInfoSchema.setSchema(this);
		return aFIDataForDealDistilledInfoSchema;
	}

	public FIDataForDealDistilledInfoDB getDB()
	{
		FIDataForDealDistilledInfoDB aDBOper = new FIDataForDealDistilledInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataForDealDistilledInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(KeyUnionValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DataType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(BusinessNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( BussDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDataForDealDistilledInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AcquisitionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			KeyUnionValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DataType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BusinessNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BussDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataForDealDistilledInfoSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("AcquisitionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcquisitionID));
		}
		if (FCode.equalsIgnoreCase("KeyUnionValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyUnionValue));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("DataType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataType));
		}
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusinessNo));
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AcquisitionID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(KeyUnionValue);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DataType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BusinessNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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

		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
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
		if (FCode.equalsIgnoreCase("KeyUnionValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyUnionValue = FValue.trim();
			}
			else
				KeyUnionValue = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("BusinessNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusinessNo = FValue.trim();
			}
			else
				BusinessNo = null;
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BussDate = fDate.getDate( FValue );
			}
			else
				BussDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDataForDealDistilledInfoSchema other = (FIDataForDealDistilledInfoSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& AcquisitionID.equals(other.getAcquisitionID())
			&& KeyUnionValue.equals(other.getKeyUnionValue())
			&& SerialNo.equals(other.getSerialNo())
			&& DataType.equals(other.getDataType())
			&& BusinessNo.equals(other.getBusinessNo())
			&& fDate.getString(BussDate).equals(other.getBussDate())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("AcquisitionID") ) {
			return 1;
		}
		if( strFieldName.equals("KeyUnionValue") ) {
			return 2;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 3;
		}
		if( strFieldName.equals("DataType") ) {
			return 4;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return 5;
		}
		if( strFieldName.equals("BussDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "AcquisitionID";
				break;
			case 2:
				strFieldName = "KeyUnionValue";
				break;
			case 3:
				strFieldName = "SerialNo";
				break;
			case 4:
				strFieldName = "DataType";
				break;
			case 5:
				strFieldName = "BusinessNo";
				break;
			case 6:
				strFieldName = "BussDate";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcquisitionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyUnionValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusinessNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
