/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FICostTypeDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FICostTypeDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FICostTypeDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 凭证类型编号 */
	private String CertificateID;
	/** 费用类型编码 */
	private String CostID;
	/** 费用类型名称 */
	private String CostName;
	/** 借贷标志 */
	private String FinItemType;
	/** 科目类型编码 */
	private String FinItemID;
	/** 科目类型名称 */
	private String FinItemName;
	/** 费用类型描述 */
	private String Remark;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FICostTypeDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "CostID";

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
                FICostTypeDefSchema cloned = (FICostTypeDefSchema)super.clone();
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
	public String getCostID()
	{
		return CostID;
	}
	public void setCostID(String aCostID)
	{
		CostID = aCostID;
	}
	public String getCostName()
	{
		return CostName;
	}
	public void setCostName(String aCostName)
	{
		CostName = aCostName;
	}
	public String getFinItemType()
	{
		return FinItemType;
	}
	public void setFinItemType(String aFinItemType)
	{
		FinItemType = aFinItemType;
	}
	public String getFinItemID()
	{
		return FinItemID;
	}
	public void setFinItemID(String aFinItemID)
	{
		FinItemID = aFinItemID;
	}
	public String getFinItemName()
	{
		return FinItemName;
	}
	public void setFinItemName(String aFinItemName)
	{
		FinItemName = aFinItemName;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 FICostTypeDefSchema 对象给 Schema 赋值
	* @param: aFICostTypeDefSchema FICostTypeDefSchema
	**/
	public void setSchema(FICostTypeDefSchema aFICostTypeDefSchema)
	{
		this.VersionNo = aFICostTypeDefSchema.getVersionNo();
		this.CertificateID = aFICostTypeDefSchema.getCertificateID();
		this.CostID = aFICostTypeDefSchema.getCostID();
		this.CostName = aFICostTypeDefSchema.getCostName();
		this.FinItemType = aFICostTypeDefSchema.getFinItemType();
		this.FinItemID = aFICostTypeDefSchema.getFinItemID();
		this.FinItemName = aFICostTypeDefSchema.getFinItemName();
		this.Remark = aFICostTypeDefSchema.getRemark();
		this.State = aFICostTypeDefSchema.getState();
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

			if( rs.getString("CostID") == null )
				this.CostID = null;
			else
				this.CostID = rs.getString("CostID").trim();

			if( rs.getString("CostName") == null )
				this.CostName = null;
			else
				this.CostName = rs.getString("CostName").trim();

			if( rs.getString("FinItemType") == null )
				this.FinItemType = null;
			else
				this.FinItemType = rs.getString("FinItemType").trim();

			if( rs.getString("FinItemID") == null )
				this.FinItemID = null;
			else
				this.FinItemID = rs.getString("FinItemID").trim();

			if( rs.getString("FinItemName") == null )
				this.FinItemName = null;
			else
				this.FinItemName = rs.getString("FinItemName").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FICostTypeDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostTypeDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FICostTypeDefSchema getSchema()
	{
		FICostTypeDefSchema aFICostTypeDefSchema = new FICostTypeDefSchema();
		aFICostTypeDefSchema.setSchema(this);
		return aFICostTypeDefSchema;
	}

	public FICostTypeDefDB getDB()
	{
		FICostTypeDefDB aDBOper = new FICostTypeDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostTypeDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CertificateID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CostID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CostName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFICostTypeDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertificateID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CostID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CostName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FinItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FinItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FinItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostTypeDefSchema";
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
		if (FCode.equalsIgnoreCase("CostID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostID));
		}
		if (FCode.equalsIgnoreCase("CostName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CostName));
		}
		if (FCode.equalsIgnoreCase("FinItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemType));
		}
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemID));
		}
		if (FCode.equalsIgnoreCase("FinItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemName));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(CostID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CostName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FinItemType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FinItemID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FinItemName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(State);
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
		if (FCode.equalsIgnoreCase("CostID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CostID = FValue.trim();
			}
			else
				CostID = null;
		}
		if (FCode.equalsIgnoreCase("CostName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CostName = FValue.trim();
			}
			else
				CostName = null;
		}
		if (FCode.equalsIgnoreCase("FinItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemType = FValue.trim();
			}
			else
				FinItemType = null;
		}
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemID = FValue.trim();
			}
			else
				FinItemID = null;
		}
		if (FCode.equalsIgnoreCase("FinItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemName = FValue.trim();
			}
			else
				FinItemName = null;
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
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FICostTypeDefSchema other = (FICostTypeDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& CertificateID.equals(other.getCertificateID())
			&& CostID.equals(other.getCostID())
			&& CostName.equals(other.getCostName())
			&& FinItemType.equals(other.getFinItemType())
			&& FinItemID.equals(other.getFinItemID())
			&& FinItemName.equals(other.getFinItemName())
			&& Remark.equals(other.getRemark())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("CostID") ) {
			return 2;
		}
		if( strFieldName.equals("CostName") ) {
			return 3;
		}
		if( strFieldName.equals("FinItemType") ) {
			return 4;
		}
		if( strFieldName.equals("FinItemID") ) {
			return 5;
		}
		if( strFieldName.equals("FinItemName") ) {
			return 6;
		}
		if( strFieldName.equals("Remark") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "CertificateID";
				break;
			case 2:
				strFieldName = "CostID";
				break;
			case 3:
				strFieldName = "CostName";
				break;
			case 4:
				strFieldName = "FinItemType";
				break;
			case 5:
				strFieldName = "FinItemID";
				break;
			case 6:
				strFieldName = "FinItemName";
				break;
			case 7:
				strFieldName = "Remark";
				break;
			case 8:
				strFieldName = "State";
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
		if( strFieldName.equals("CostID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CostName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
