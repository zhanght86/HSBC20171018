/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIInfoFinItemAssociatedDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIInfoFinItemAssociatedSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIInfoFinItemAssociatedSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIInfoFinItemAssociatedSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 科目类型编码 */
	private String FinItemID;
	/** 专项编号 */
	private String AssociatedID;
	/** 专项名称 */
	private String AssociatedName;
	/** 描述 */
	private String ReMark;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIInfoFinItemAssociatedSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "VersionNo";
		pk[1] = "FinItemID";
		pk[2] = "AssociatedID";

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
                FIInfoFinItemAssociatedSchema cloned = (FIInfoFinItemAssociatedSchema)super.clone();
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
	public String getFinItemID()
	{
		return FinItemID;
	}
	public void setFinItemID(String aFinItemID)
	{
		FinItemID = aFinItemID;
	}
	public String getAssociatedID()
	{
		return AssociatedID;
	}
	public void setAssociatedID(String aAssociatedID)
	{
		AssociatedID = aAssociatedID;
	}
	public String getAssociatedName()
	{
		return AssociatedName;
	}
	public void setAssociatedName(String aAssociatedName)
	{
		AssociatedName = aAssociatedName;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}

	/**
	* 使用另外一个 FIInfoFinItemAssociatedSchema 对象给 Schema 赋值
	* @param: aFIInfoFinItemAssociatedSchema FIInfoFinItemAssociatedSchema
	**/
	public void setSchema(FIInfoFinItemAssociatedSchema aFIInfoFinItemAssociatedSchema)
	{
		this.VersionNo = aFIInfoFinItemAssociatedSchema.getVersionNo();
		this.FinItemID = aFIInfoFinItemAssociatedSchema.getFinItemID();
		this.AssociatedID = aFIInfoFinItemAssociatedSchema.getAssociatedID();
		this.AssociatedName = aFIInfoFinItemAssociatedSchema.getAssociatedName();
		this.ReMark = aFIInfoFinItemAssociatedSchema.getReMark();
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

			if( rs.getString("FinItemID") == null )
				this.FinItemID = null;
			else
				this.FinItemID = rs.getString("FinItemID").trim();

			if( rs.getString("AssociatedID") == null )
				this.AssociatedID = null;
			else
				this.AssociatedID = rs.getString("AssociatedID").trim();

			if( rs.getString("AssociatedName") == null )
				this.AssociatedName = null;
			else
				this.AssociatedName = rs.getString("AssociatedName").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIInfoFinItemAssociated表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIInfoFinItemAssociatedSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIInfoFinItemAssociatedSchema getSchema()
	{
		FIInfoFinItemAssociatedSchema aFIInfoFinItemAssociatedSchema = new FIInfoFinItemAssociatedSchema();
		aFIInfoFinItemAssociatedSchema.setSchema(this);
		return aFIInfoFinItemAssociatedSchema;
	}

	public FIInfoFinItemAssociatedDB getDB()
	{
		FIInfoFinItemAssociatedDB aDBOper = new FIInfoFinItemAssociatedDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIInfoFinItemAssociated描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AssociatedID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AssociatedName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIInfoFinItemAssociated>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FinItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AssociatedID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AssociatedName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIInfoFinItemAssociatedSchema";
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
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemID));
		}
		if (FCode.equalsIgnoreCase("AssociatedID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedID));
		}
		if (FCode.equalsIgnoreCase("AssociatedName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedName));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
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
				strFieldValue = StrTool.GBKToUnicode(FinItemID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AssociatedID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AssociatedName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
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
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemID = FValue.trim();
			}
			else
				FinItemID = null;
		}
		if (FCode.equalsIgnoreCase("AssociatedID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssociatedID = FValue.trim();
			}
			else
				AssociatedID = null;
		}
		if (FCode.equalsIgnoreCase("AssociatedName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssociatedName = FValue.trim();
			}
			else
				AssociatedName = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIInfoFinItemAssociatedSchema other = (FIInfoFinItemAssociatedSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& FinItemID.equals(other.getFinItemID())
			&& AssociatedID.equals(other.getAssociatedID())
			&& AssociatedName.equals(other.getAssociatedName())
			&& ReMark.equals(other.getReMark());
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
		if( strFieldName.equals("FinItemID") ) {
			return 1;
		}
		if( strFieldName.equals("AssociatedID") ) {
			return 2;
		}
		if( strFieldName.equals("AssociatedName") ) {
			return 3;
		}
		if( strFieldName.equals("ReMark") ) {
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
				strFieldName = "FinItemID";
				break;
			case 2:
				strFieldName = "AssociatedID";
				break;
			case 3:
				strFieldName = "AssociatedName";
				break;
			case 4:
				strFieldName = "ReMark";
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
		if( strFieldName.equals("FinItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssociatedID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssociatedName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
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
