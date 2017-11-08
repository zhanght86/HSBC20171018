/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FICostDataKeyDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FICostDataKeyDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FICostDataKeyDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICostDataKeyDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 数据采集编号 */
	private String AcquisitionID;
	/** 主键标识 */
	private String KeyID;
	/** 主键名称 */
	private String KeyName;
	/** 主键序号 */
	private int KeyOrder;
	/** 描述 */
	private String Remark;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FICostDataKeyDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "VersionNo";
		pk[1] = "AcquisitionID";
		pk[2] = "KeyID";

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
                FICostDataKeyDefSchema cloned = (FICostDataKeyDefSchema)super.clone();
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
	public String getKeyID()
	{
		return KeyID;
	}
	public void setKeyID(String aKeyID)
	{
		KeyID = aKeyID;
	}
	public String getKeyName()
	{
		return KeyName;
	}
	public void setKeyName(String aKeyName)
	{
		KeyName = aKeyName;
	}
	public int getKeyOrder()
	{
		return KeyOrder;
	}
	public void setKeyOrder(int aKeyOrder)
	{
		KeyOrder = aKeyOrder;
	}
	public void setKeyOrder(String aKeyOrder)
	{
		if (aKeyOrder != null && !aKeyOrder.equals(""))
		{
			Integer tInteger = new Integer(aKeyOrder);
			int i = tInteger.intValue();
			KeyOrder = i;
		}
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
	* 使用另外一个 FICostDataKeyDefSchema 对象给 Schema 赋值
	* @param: aFICostDataKeyDefSchema FICostDataKeyDefSchema
	**/
	public void setSchema(FICostDataKeyDefSchema aFICostDataKeyDefSchema)
	{
		this.VersionNo = aFICostDataKeyDefSchema.getVersionNo();
		this.AcquisitionID = aFICostDataKeyDefSchema.getAcquisitionID();
		this.KeyID = aFICostDataKeyDefSchema.getKeyID();
		this.KeyName = aFICostDataKeyDefSchema.getKeyName();
		this.KeyOrder = aFICostDataKeyDefSchema.getKeyOrder();
		this.Remark = aFICostDataKeyDefSchema.getRemark();
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

			if( rs.getString("KeyID") == null )
				this.KeyID = null;
			else
				this.KeyID = rs.getString("KeyID").trim();

			if( rs.getString("KeyName") == null )
				this.KeyName = null;
			else
				this.KeyName = rs.getString("KeyName").trim();

			this.KeyOrder = rs.getInt("KeyOrder");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FICostDataKeyDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataKeyDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FICostDataKeyDefSchema getSchema()
	{
		FICostDataKeyDefSchema aFICostDataKeyDefSchema = new FICostDataKeyDefSchema();
		aFICostDataKeyDefSchema.setSchema(this);
		return aFICostDataKeyDefSchema;
	}

	public FICostDataKeyDefDB getDB()
	{
		FICostDataKeyDefDB aDBOper = new FICostDataKeyDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostDataKeyDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(KeyID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(KeyName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(KeyOrder));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostDataKeyDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AcquisitionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			KeyID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			KeyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			KeyOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataKeyDefSchema";
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
		if (FCode.equalsIgnoreCase("KeyID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyID));
		}
		if (FCode.equalsIgnoreCase("KeyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyName));
		}
		if (FCode.equalsIgnoreCase("KeyOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KeyOrder));
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
				strFieldValue = StrTool.GBKToUnicode(KeyID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(KeyName);
				break;
			case 4:
				strFieldValue = String.valueOf(KeyOrder);
				break;
			case 5:
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
		if (FCode.equalsIgnoreCase("KeyID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyID = FValue.trim();
			}
			else
				KeyID = null;
		}
		if (FCode.equalsIgnoreCase("KeyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KeyName = FValue.trim();
			}
			else
				KeyName = null;
		}
		if (FCode.equalsIgnoreCase("KeyOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				KeyOrder = i;
			}
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
		FICostDataKeyDefSchema other = (FICostDataKeyDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& AcquisitionID.equals(other.getAcquisitionID())
			&& KeyID.equals(other.getKeyID())
			&& KeyName.equals(other.getKeyName())
			&& KeyOrder == other.getKeyOrder()
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
		if( strFieldName.equals("KeyID") ) {
			return 2;
		}
		if( strFieldName.equals("KeyName") ) {
			return 3;
		}
		if( strFieldName.equals("KeyOrder") ) {
			return 4;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "AcquisitionID";
				break;
			case 2:
				strFieldName = "KeyID";
				break;
			case 3:
				strFieldName = "KeyName";
				break;
			case 4:
				strFieldName = "KeyOrder";
				break;
			case 5:
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
		if( strFieldName.equals("KeyID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KeyOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
