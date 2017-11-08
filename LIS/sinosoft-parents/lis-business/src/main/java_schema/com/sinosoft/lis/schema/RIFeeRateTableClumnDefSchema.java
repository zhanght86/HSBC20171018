

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIFeeRateTableClumnDefDB;

/*
 * <p>ClassName: RIFeeRateTableClumnDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIFeeRateTableClumnDefSchema implements Schema, Cloneable
{
	// @Field
	/** 费率表编号 */
	private String FeeTableNo;
	/** 费率表名称 */
	private String FeeTableName;
	/** 费率表字段编号 */
	private int FeeClumnNo;
	/** 费率表字段名称 */
	private String FeeClumnName;
	/** 费率表字段数据源代号 */
	private String FeeClumnDataCode;
	/** 费率表字段类型 */
	private String FeeClumnType;
	/** 定值字段映射代码 */
	private String TagetClumn;
	/** 区间上限字段映射代码 */
	private String TagetULClumn;
	/** 区间下限字段影射代码 */
	private String TagetDLClumn;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIFeeRateTableClumnDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "FeeTableNo";
		pk[1] = "FeeClumnNo";

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
		RIFeeRateTableClumnDefSchema cloned = (RIFeeRateTableClumnDefSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeTableNo()
	{
		return FeeTableNo;
	}
	public void setFeeTableNo(String aFeeTableNo)
	{
		FeeTableNo = aFeeTableNo;
	}
	public String getFeeTableName()
	{
		return FeeTableName;
	}
	public void setFeeTableName(String aFeeTableName)
	{
		FeeTableName = aFeeTableName;
	}
	public int getFeeClumnNo()
	{
		return FeeClumnNo;
	}
	public void setFeeClumnNo(int aFeeClumnNo)
	{
		FeeClumnNo = aFeeClumnNo;
	}
	public void setFeeClumnNo(String aFeeClumnNo)
	{
		if (aFeeClumnNo != null && !aFeeClumnNo.equals(""))
		{
			Integer tInteger = new Integer(aFeeClumnNo);
			int i = tInteger.intValue();
			FeeClumnNo = i;
		}
	}

	public String getFeeClumnName()
	{
		return FeeClumnName;
	}
	public void setFeeClumnName(String aFeeClumnName)
	{
		FeeClumnName = aFeeClumnName;
	}
	public String getFeeClumnDataCode()
	{
		return FeeClumnDataCode;
	}
	public void setFeeClumnDataCode(String aFeeClumnDataCode)
	{
		FeeClumnDataCode = aFeeClumnDataCode;
	}
	/**
	* 01-固定值 02-区间值 03-分段类加
	*/
	public String getFeeClumnType()
	{
		return FeeClumnType;
	}
	public void setFeeClumnType(String aFeeClumnType)
	{
		FeeClumnType = aFeeClumnType;
	}
	public String getTagetClumn()
	{
		return TagetClumn;
	}
	public void setTagetClumn(String aTagetClumn)
	{
		TagetClumn = aTagetClumn;
	}
	public String getTagetULClumn()
	{
		return TagetULClumn;
	}
	public void setTagetULClumn(String aTagetULClumn)
	{
		TagetULClumn = aTagetULClumn;
	}
	public String getTagetDLClumn()
	{
		return TagetDLClumn;
	}
	public void setTagetDLClumn(String aTagetDLClumn)
	{
		TagetDLClumn = aTagetDLClumn;
	}

	/**
	* 使用另外一个 RIFeeRateTableClumnDefSchema 对象给 Schema 赋值
	* @param: aRIFeeRateTableClumnDefSchema RIFeeRateTableClumnDefSchema
	**/
	public void setSchema(RIFeeRateTableClumnDefSchema aRIFeeRateTableClumnDefSchema)
	{
		this.FeeTableNo = aRIFeeRateTableClumnDefSchema.getFeeTableNo();
		this.FeeTableName = aRIFeeRateTableClumnDefSchema.getFeeTableName();
		this.FeeClumnNo = aRIFeeRateTableClumnDefSchema.getFeeClumnNo();
		this.FeeClumnName = aRIFeeRateTableClumnDefSchema.getFeeClumnName();
		this.FeeClumnDataCode = aRIFeeRateTableClumnDefSchema.getFeeClumnDataCode();
		this.FeeClumnType = aRIFeeRateTableClumnDefSchema.getFeeClumnType();
		this.TagetClumn = aRIFeeRateTableClumnDefSchema.getTagetClumn();
		this.TagetULClumn = aRIFeeRateTableClumnDefSchema.getTagetULClumn();
		this.TagetDLClumn = aRIFeeRateTableClumnDefSchema.getTagetDLClumn();
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
			if( rs.getString("FeeTableNo") == null )
				this.FeeTableNo = null;
			else
				this.FeeTableNo = rs.getString("FeeTableNo").trim();

			if( rs.getString("FeeTableName") == null )
				this.FeeTableName = null;
			else
				this.FeeTableName = rs.getString("FeeTableName").trim();

			this.FeeClumnNo = rs.getInt("FeeClumnNo");
			if( rs.getString("FeeClumnName") == null )
				this.FeeClumnName = null;
			else
				this.FeeClumnName = rs.getString("FeeClumnName").trim();

			if( rs.getString("FeeClumnDataCode") == null )
				this.FeeClumnDataCode = null;
			else
				this.FeeClumnDataCode = rs.getString("FeeClumnDataCode").trim();

			if( rs.getString("FeeClumnType") == null )
				this.FeeClumnType = null;
			else
				this.FeeClumnType = rs.getString("FeeClumnType").trim();

			if( rs.getString("TagetClumn") == null )
				this.TagetClumn = null;
			else
				this.TagetClumn = rs.getString("TagetClumn").trim();

			if( rs.getString("TagetULClumn") == null )
				this.TagetULClumn = null;
			else
				this.TagetULClumn = rs.getString("TagetULClumn").trim();

			if( rs.getString("TagetDLClumn") == null )
				this.TagetDLClumn = null;
			else
				this.TagetDLClumn = rs.getString("TagetDLClumn").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIFeeRateTableClumnDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableClumnDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIFeeRateTableClumnDefSchema getSchema()
	{
		RIFeeRateTableClumnDefSchema aRIFeeRateTableClumnDefSchema = new RIFeeRateTableClumnDefSchema();
		aRIFeeRateTableClumnDefSchema.setSchema(this);
		return aRIFeeRateTableClumnDefSchema;
	}

	public RIFeeRateTableClumnDefDB getDB()
	{
		RIFeeRateTableClumnDefDB aDBOper = new RIFeeRateTableClumnDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableClumnDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeTableNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeClumnNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeClumnName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeClumnDataCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeClumnType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TagetClumn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TagetULClumn)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TagetDLClumn));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIFeeRateTableClumnDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeTableNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FeeClumnNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			FeeClumnName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeClumnDataCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FeeClumnType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TagetClumn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			TagetULClumn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TagetDLClumn = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIFeeRateTableClumnDefSchema";
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
		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableNo));
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeTableName));
		}
		if (FCode.equalsIgnoreCase("FeeClumnNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeClumnNo));
		}
		if (FCode.equalsIgnoreCase("FeeClumnName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeClumnName));
		}
		if (FCode.equalsIgnoreCase("FeeClumnDataCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeClumnDataCode));
		}
		if (FCode.equalsIgnoreCase("FeeClumnType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeClumnType));
		}
		if (FCode.equalsIgnoreCase("TagetClumn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TagetClumn));
		}
		if (FCode.equalsIgnoreCase("TagetULClumn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TagetULClumn));
		}
		if (FCode.equalsIgnoreCase("TagetDLClumn"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TagetDLClumn));
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
				strFieldValue = StrTool.GBKToUnicode(FeeTableNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeTableName);
				break;
			case 2:
				strFieldValue = String.valueOf(FeeClumnNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeClumnName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeClumnDataCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FeeClumnType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TagetClumn);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TagetULClumn);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TagetDLClumn);
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

		if (FCode.equalsIgnoreCase("FeeTableNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableNo = FValue.trim();
			}
			else
				FeeTableNo = null;
		}
		if (FCode.equalsIgnoreCase("FeeTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeTableName = FValue.trim();
			}
			else
				FeeTableName = null;
		}
		if (FCode.equalsIgnoreCase("FeeClumnNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FeeClumnNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeClumnName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeClumnName = FValue.trim();
			}
			else
				FeeClumnName = null;
		}
		if (FCode.equalsIgnoreCase("FeeClumnDataCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeClumnDataCode = FValue.trim();
			}
			else
				FeeClumnDataCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeClumnType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeClumnType = FValue.trim();
			}
			else
				FeeClumnType = null;
		}
		if (FCode.equalsIgnoreCase("TagetClumn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TagetClumn = FValue.trim();
			}
			else
				TagetClumn = null;
		}
		if (FCode.equalsIgnoreCase("TagetULClumn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TagetULClumn = FValue.trim();
			}
			else
				TagetULClumn = null;
		}
		if (FCode.equalsIgnoreCase("TagetDLClumn"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TagetDLClumn = FValue.trim();
			}
			else
				TagetDLClumn = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIFeeRateTableClumnDefSchema other = (RIFeeRateTableClumnDefSchema)otherObject;
		return
			FeeTableNo.equals(other.getFeeTableNo())
			&& FeeTableName.equals(other.getFeeTableName())
			&& FeeClumnNo == other.getFeeClumnNo()
			&& FeeClumnName.equals(other.getFeeClumnName())
			&& FeeClumnDataCode.equals(other.getFeeClumnDataCode())
			&& FeeClumnType.equals(other.getFeeClumnType())
			&& TagetClumn.equals(other.getTagetClumn())
			&& TagetULClumn.equals(other.getTagetULClumn())
			&& TagetDLClumn.equals(other.getTagetDLClumn());
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
		if( strFieldName.equals("FeeTableNo") ) {
			return 0;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return 1;
		}
		if( strFieldName.equals("FeeClumnNo") ) {
			return 2;
		}
		if( strFieldName.equals("FeeClumnName") ) {
			return 3;
		}
		if( strFieldName.equals("FeeClumnDataCode") ) {
			return 4;
		}
		if( strFieldName.equals("FeeClumnType") ) {
			return 5;
		}
		if( strFieldName.equals("TagetClumn") ) {
			return 6;
		}
		if( strFieldName.equals("TagetULClumn") ) {
			return 7;
		}
		if( strFieldName.equals("TagetDLClumn") ) {
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
				strFieldName = "FeeTableNo";
				break;
			case 1:
				strFieldName = "FeeTableName";
				break;
			case 2:
				strFieldName = "FeeClumnNo";
				break;
			case 3:
				strFieldName = "FeeClumnName";
				break;
			case 4:
				strFieldName = "FeeClumnDataCode";
				break;
			case 5:
				strFieldName = "FeeClumnType";
				break;
			case 6:
				strFieldName = "TagetClumn";
				break;
			case 7:
				strFieldName = "TagetULClumn";
				break;
			case 8:
				strFieldName = "TagetDLClumn";
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
		if( strFieldName.equals("FeeTableNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeClumnNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeClumnName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeClumnDataCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeClumnType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TagetClumn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TagetULClumn") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TagetDLClumn") ) {
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
				nFieldType = Schema.TYPE_INT;
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
