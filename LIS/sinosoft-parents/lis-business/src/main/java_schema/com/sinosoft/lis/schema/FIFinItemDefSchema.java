/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIFinItemDefDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIFinItemDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIFinItemDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIFinItemDefSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 科目编号 */
	private String FinItemID;
	/** 科目名称 */
	private String FinItemName;
	/** 科目类型 */
	private String FinItemType;
	/** 科目代码（一级） */
	private String ItemMainCode;
	/** 科目处理方式 */
	private String DealMode;
	/** 特殊处理类 */
	private String DealSpecialClass;
	/** 描述 */
	private String ReMark;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIFinItemDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "FinItemID";

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
                FIFinItemDefSchema cloned = (FIFinItemDefSchema)super.clone();
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
	public String getFinItemName()
	{
		return FinItemName;
	}
	public void setFinItemName(String aFinItemName)
	{
		FinItemName = aFinItemName;
	}
	public String getFinItemType()
	{
		return FinItemType;
	}
	public void setFinItemType(String aFinItemType)
	{
		FinItemType = aFinItemType;
	}
	public String getItemMainCode()
	{
		return ItemMainCode;
	}
	public void setItemMainCode(String aItemMainCode)
	{
		ItemMainCode = aItemMainCode;
	}
	public String getDealMode()
	{
		return DealMode;
	}
	public void setDealMode(String aDealMode)
	{
		DealMode = aDealMode;
	}
	public String getDealSpecialClass()
	{
		return DealSpecialClass;
	}
	public void setDealSpecialClass(String aDealSpecialClass)
	{
		DealSpecialClass = aDealSpecialClass;
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
	* 使用另外一个 FIFinItemDefSchema 对象给 Schema 赋值
	* @param: aFIFinItemDefSchema FIFinItemDefSchema
	**/
	public void setSchema(FIFinItemDefSchema aFIFinItemDefSchema)
	{
		this.VersionNo = aFIFinItemDefSchema.getVersionNo();
		this.FinItemID = aFIFinItemDefSchema.getFinItemID();
		this.FinItemName = aFIFinItemDefSchema.getFinItemName();
		this.FinItemType = aFIFinItemDefSchema.getFinItemType();
		this.ItemMainCode = aFIFinItemDefSchema.getItemMainCode();
		this.DealMode = aFIFinItemDefSchema.getDealMode();
		this.DealSpecialClass = aFIFinItemDefSchema.getDealSpecialClass();
		this.ReMark = aFIFinItemDefSchema.getReMark();
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

			if( rs.getString("FinItemName") == null )
				this.FinItemName = null;
			else
				this.FinItemName = rs.getString("FinItemName").trim();

			if( rs.getString("FinItemType") == null )
				this.FinItemType = null;
			else
				this.FinItemType = rs.getString("FinItemType").trim();

			if( rs.getString("ItemMainCode") == null )
				this.ItemMainCode = null;
			else
				this.ItemMainCode = rs.getString("ItemMainCode").trim();

			if( rs.getString("DealMode") == null )
				this.DealMode = null;
			else
				this.DealMode = rs.getString("DealMode").trim();

			if( rs.getString("DealSpecialClass") == null )
				this.DealSpecialClass = null;
			else
				this.DealSpecialClass = rs.getString("DealSpecialClass").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIFinItemDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIFinItemDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIFinItemDefSchema getSchema()
	{
		FIFinItemDefSchema aFIFinItemDefSchema = new FIFinItemDefSchema();
		aFIFinItemDefSchema.setSchema(this);
		return aFIFinItemDefSchema;
	}

	public FIFinItemDefDB getDB()
	{
		FIFinItemDefDB aDBOper = new FIFinItemDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIFinItemDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ItemMainCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealMode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(DealSpecialClass)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIFinItemDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FinItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FinItemName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FinItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ItemMainCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DealMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DealSpecialClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIFinItemDefSchema";
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
		if (FCode.equalsIgnoreCase("FinItemName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemName));
		}
		if (FCode.equalsIgnoreCase("FinItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemType));
		}
		if (FCode.equalsIgnoreCase("ItemMainCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemMainCode));
		}
		if (FCode.equalsIgnoreCase("DealMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealMode));
		}
		if (FCode.equalsIgnoreCase("DealSpecialClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealSpecialClass));
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
				strFieldValue = StrTool.GBKToUnicode(FinItemName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FinItemType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ItemMainCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DealMode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DealSpecialClass);
				break;
			case 7:
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
		if (FCode.equalsIgnoreCase("FinItemName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemName = FValue.trim();
			}
			else
				FinItemName = null;
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
		if (FCode.equalsIgnoreCase("ItemMainCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemMainCode = FValue.trim();
			}
			else
				ItemMainCode = null;
		}
		if (FCode.equalsIgnoreCase("DealMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealMode = FValue.trim();
			}
			else
				DealMode = null;
		}
		if (FCode.equalsIgnoreCase("DealSpecialClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealSpecialClass = FValue.trim();
			}
			else
				DealSpecialClass = null;
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
		FIFinItemDefSchema other = (FIFinItemDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& FinItemID.equals(other.getFinItemID())
			&& FinItemName.equals(other.getFinItemName())
			&& FinItemType.equals(other.getFinItemType())
			&& ItemMainCode.equals(other.getItemMainCode())
			&& DealMode.equals(other.getDealMode())
			&& DealSpecialClass.equals(other.getDealSpecialClass())
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
		if( strFieldName.equals("FinItemName") ) {
			return 2;
		}
		if( strFieldName.equals("FinItemType") ) {
			return 3;
		}
		if( strFieldName.equals("ItemMainCode") ) {
			return 4;
		}
		if( strFieldName.equals("DealMode") ) {
			return 5;
		}
		if( strFieldName.equals("DealSpecialClass") ) {
			return 6;
		}
		if( strFieldName.equals("ReMark") ) {
			return 7;
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
				strFieldName = "FinItemName";
				break;
			case 3:
				strFieldName = "FinItemType";
				break;
			case 4:
				strFieldName = "ItemMainCode";
				break;
			case 5:
				strFieldName = "DealMode";
				break;
			case 6:
				strFieldName = "DealSpecialClass";
				break;
			case 7:
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
		if( strFieldName.equals("FinItemName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemMainCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealSpecialClass") ) {
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
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
