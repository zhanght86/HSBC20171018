/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FICertificateTypeDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FICertificateTypeDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICertificateTypeDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 凭证类型编号 */
	private String CertificateID;
	/** 凭证类型名称 */
	private String CertificateName;
	/** 明细索引代码 */
	private String DetailIndexID;
	/** 明细索引名称 */
	private String DetailIndexName;
	/** 数据采集类型 */
	private String AcquisitionType;
	/** 说明 */
	private String Remark;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FICertificateTypeDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "CertificateID";

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
                FICertificateTypeDefSchema cloned = (FICertificateTypeDefSchema)super.clone();
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
	public String getCertificateID()
	{
		return CertificateID;
	}
	public void setCertificateID(String aCertificateID)
	{
		CertificateID = aCertificateID;
	}
	public String getCertificateName()
	{
		return CertificateName;
	}
	public void setCertificateName(String aCertificateName)
	{
		CertificateName = aCertificateName;
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
	public String getAcquisitionType()
	{
		return AcquisitionType;
	}
	public void setAcquisitionType(String aAcquisitionType)
	{
		AcquisitionType = aAcquisitionType;
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
	* 使用另外一个 FICertificateTypeDefSchema 对象给 Schema 赋值
	* @param: aFICertificateTypeDefSchema FICertificateTypeDefSchema
	**/
	public void setSchema(FICertificateTypeDefSchema aFICertificateTypeDefSchema)
	{
		this.VersionNo = aFICertificateTypeDefSchema.getVersionNo();
		this.CertificateID = aFICertificateTypeDefSchema.getCertificateID();
		this.CertificateName = aFICertificateTypeDefSchema.getCertificateName();
		this.DetailIndexID = aFICertificateTypeDefSchema.getDetailIndexID();
		this.DetailIndexName = aFICertificateTypeDefSchema.getDetailIndexName();
		this.AcquisitionType = aFICertificateTypeDefSchema.getAcquisitionType();
		this.Remark = aFICertificateTypeDefSchema.getRemark();
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

			if( rs.getString("CertificateID") == null )
				this.CertificateID = null;
			else
				this.CertificateID = rs.getString("CertificateID").trim();

			if( rs.getString("CertificateName") == null )
				this.CertificateName = null;
			else
				this.CertificateName = rs.getString("CertificateName").trim();

			if( rs.getString("DetailIndexID") == null )
				this.DetailIndexID = null;
			else
				this.DetailIndexID = rs.getString("DetailIndexID").trim();

			if( rs.getString("DetailIndexName") == null )
				this.DetailIndexName = null;
			else
				this.DetailIndexName = rs.getString("DetailIndexName").trim();

			if( rs.getString("AcquisitionType") == null )
				this.AcquisitionType = null;
			else
				this.AcquisitionType = rs.getString("AcquisitionType").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FICertificateTypeDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FICertificateTypeDefSchema getSchema()
	{
		FICertificateTypeDefSchema aFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
		aFICertificateTypeDefSchema.setSchema(this);
		return aFICertificateTypeDefSchema;
	}

	public FICertificateTypeDefDB getDB()
	{
		FICertificateTypeDefDB aDBOper = new FICertificateTypeDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICertificateTypeDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DetailIndexName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AcquisitionType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICertificateTypeDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CertificateName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DetailIndexID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DetailIndexName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AcquisitionType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefSchema";
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
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateID));
		}
		if (FCode.equalsIgnoreCase("CertificateName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertificateName));
		}
		if (FCode.equalsIgnoreCase("DetailIndexID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexID));
		}
		if (FCode.equalsIgnoreCase("DetailIndexName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailIndexName));
		}
		if (FCode.equalsIgnoreCase("AcquisitionType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AcquisitionType));
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
				strFieldValue = StrTool.GBKToUnicode(CertificateID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CertificateName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DetailIndexName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AcquisitionType);
				break;
			case 6:
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
		if (FCode.equalsIgnoreCase("CertificateID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertificateID = FValue.trim();
			}
			else
				CertificateID = null;
		}
		if (FCode.equalsIgnoreCase("CertificateName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertificateName = FValue.trim();
			}
			else
				CertificateName = null;
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
		if (FCode.equalsIgnoreCase("AcquisitionType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AcquisitionType = FValue.trim();
			}
			else
				AcquisitionType = null;
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
		FICertificateTypeDefSchema other = (FICertificateTypeDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& CertificateID.equals(other.getCertificateID())
			&& CertificateName.equals(other.getCertificateName())
			&& DetailIndexID.equals(other.getDetailIndexID())
			&& DetailIndexName.equals(other.getDetailIndexName())
			&& AcquisitionType.equals(other.getAcquisitionType())
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
		if( strFieldName.equals("CertificateID") ) {
			return 1;
		}
		if( strFieldName.equals("CertificateName") ) {
			return 2;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return 3;
		}
		if( strFieldName.equals("DetailIndexName") ) {
			return 4;
		}
		if( strFieldName.equals("AcquisitionType") ) {
			return 5;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "CertificateID";
				break;
			case 2:
				strFieldName = "CertificateName";
				break;
			case 3:
				strFieldName = "DetailIndexID";
				break;
			case 4:
				strFieldName = "DetailIndexName";
				break;
			case 5:
				strFieldName = "AcquisitionType";
				break;
			case 6:
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
		if( strFieldName.equals("CertificateID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertificateName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailIndexName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AcquisitionType") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
