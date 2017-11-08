/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LFDesbModeDB;

/*
 * <p>ClassName: LFDesbModeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LFDesbModeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LFDesbModeSchema.class);

	// @Field
	/** 科目编码 */
	private String ItemCode;
	/** 科目序号 */
	private int ItemNum;
	/** 科目类型 */
	private String ItemType;
	/** 处理类型 */
	private String DealType;
	/** 算法内容 */
	private String CalSQL;
	/** 应用类名 */
	private String InterfaceClassName;
	/** 目标表名 */
	private String DestTableName;
	/** 算法说明 */
	private String Remark;
	/** 算法内容1 */
	private String CalSQL1;
	/** 算法内容2 */
	private String CalSQL2;
	/** 算法内容3 */
	private String CalSQL3;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFDesbModeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ItemCode";
		pk[1] = "ItemNum";
		pk[2] = "ItemType";

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
		LFDesbModeSchema cloned = (LFDesbModeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String aItemCode)
	{
		ItemCode = aItemCode;
	}
	public int getItemNum()
	{
		return ItemNum;
	}
	public void setItemNum(int aItemNum)
	{
		ItemNum = aItemNum;
	}
	public void setItemNum(String aItemNum)
	{
		if (aItemNum != null && !aItemNum.equals(""))
		{
			Integer tInteger = new Integer(aItemNum);
			int i = tInteger.intValue();
			ItemNum = i;
		}
	}

	/**
	* 01-业务数据<p>
	* 02-销售数据<p>
	* 03-财务数据<p>
	* 04-精算数据<p>
	* 05-再保数据<p>
	* 06-资金运用数据<p>
	* 07-人力资源数据<p>
	* <p>
	* X1-业务数据<p>
	* X2-销售数据<p>
	* X3-财务数据<p>
	* X4-精算数据<p>
	* X5-再保数据<p>
	* X6-资金运用数据<p>
	* X7-人力资源数据<p>
	* <p>
	* C1-一级计算科目汇总
	*/
	public String getItemType()
	{
		return ItemType;
	}
	public void setItemType(String aItemType)
	{
		ItemType = aItemType;
	}
	/**
	* S-应用SQL语句进行处理<p>
	* C-应用Class类进行处理
	*/
	public String getDealType()
	{
		return DealType;
	}
	public void setDealType(String aDealType)
	{
		DealType = aDealType;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}
	public String getInterfaceClassName()
	{
		return InterfaceClassName;
	}
	public void setInterfaceClassName(String aInterfaceClassName)
	{
		InterfaceClassName = aInterfaceClassName;
	}
	public String getDestTableName()
	{
		return DestTableName;
	}
	public void setDestTableName(String aDestTableName)
	{
		DestTableName = aDestTableName;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getCalSQL1()
	{
		return CalSQL1;
	}
	public void setCalSQL1(String aCalSQL1)
	{
		CalSQL1 = aCalSQL1;
	}
	public String getCalSQL2()
	{
		return CalSQL2;
	}
	public void setCalSQL2(String aCalSQL2)
	{
		CalSQL2 = aCalSQL2;
	}
	public String getCalSQL3()
	{
		return CalSQL3;
	}
	public void setCalSQL3(String aCalSQL3)
	{
		CalSQL3 = aCalSQL3;
	}

	/**
	* 使用另外一个 LFDesbModeSchema 对象给 Schema 赋值
	* @param: aLFDesbModeSchema LFDesbModeSchema
	**/
	public void setSchema(LFDesbModeSchema aLFDesbModeSchema)
	{
		this.ItemCode = aLFDesbModeSchema.getItemCode();
		this.ItemNum = aLFDesbModeSchema.getItemNum();
		this.ItemType = aLFDesbModeSchema.getItemType();
		this.DealType = aLFDesbModeSchema.getDealType();
		this.CalSQL = aLFDesbModeSchema.getCalSQL();
		this.InterfaceClassName = aLFDesbModeSchema.getInterfaceClassName();
		this.DestTableName = aLFDesbModeSchema.getDestTableName();
		this.Remark = aLFDesbModeSchema.getRemark();
		this.CalSQL1 = aLFDesbModeSchema.getCalSQL1();
		this.CalSQL2 = aLFDesbModeSchema.getCalSQL2();
		this.CalSQL3 = aLFDesbModeSchema.getCalSQL3();
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
			if( rs.getString("ItemCode") == null )
				this.ItemCode = null;
			else
				this.ItemCode = rs.getString("ItemCode").trim();

			this.ItemNum = rs.getInt("ItemNum");
			if( rs.getString("ItemType") == null )
				this.ItemType = null;
			else
				this.ItemType = rs.getString("ItemType").trim();

			if( rs.getString("DealType") == null )
				this.DealType = null;
			else
				this.DealType = rs.getString("DealType").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

			if( rs.getString("InterfaceClassName") == null )
				this.InterfaceClassName = null;
			else
				this.InterfaceClassName = rs.getString("InterfaceClassName").trim();

			if( rs.getString("DestTableName") == null )
				this.DestTableName = null;
			else
				this.DestTableName = rs.getString("DestTableName").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("CalSQL1") == null )
				this.CalSQL1 = null;
			else
				this.CalSQL1 = rs.getString("CalSQL1").trim();

			if( rs.getString("CalSQL2") == null )
				this.CalSQL2 = null;
			else
				this.CalSQL2 = rs.getString("CalSQL2").trim();

			if( rs.getString("CalSQL3") == null )
				this.CalSQL3 = null;
			else
				this.CalSQL3 = rs.getString("CalSQL3").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFDesbMode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFDesbModeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFDesbModeSchema getSchema()
	{
		LFDesbModeSchema aLFDesbModeSchema = new LFDesbModeSchema();
		aLFDesbModeSchema.setSchema(this);
		return aLFDesbModeSchema;
	}

	public LFDesbModeDB getDB()
	{
		LFDesbModeDB aDBOper = new LFDesbModeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFDesbMode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ItemNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InterfaceClassName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFDesbMode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DealType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InterfaceClassName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DestTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalSQL1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalSQL2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CalSQL3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFDesbModeSchema";
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
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCode));
		}
		if (FCode.equalsIgnoreCase("ItemNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemNum));
		}
		if (FCode.equalsIgnoreCase("ItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemType));
		}
		if (FCode.equalsIgnoreCase("DealType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealType));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
		}
		if (FCode.equalsIgnoreCase("InterfaceClassName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InterfaceClassName));
		}
		if (FCode.equalsIgnoreCase("DestTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestTableName));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("CalSQL1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL1));
		}
		if (FCode.equalsIgnoreCase("CalSQL2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL2));
		}
		if (FCode.equalsIgnoreCase("CalSQL3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL3));
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
				strFieldValue = StrTool.GBKToUnicode(ItemCode);
				break;
			case 1:
				strFieldValue = String.valueOf(ItemNum);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ItemType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DealType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InterfaceClassName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DestTableName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalSQL1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalSQL2);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalSQL3);
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

		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCode = FValue.trim();
			}
			else
				ItemCode = null;
		}
		if (FCode.equalsIgnoreCase("ItemNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ItemNum = i;
			}
		}
		if (FCode.equalsIgnoreCase("ItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemType = FValue.trim();
			}
			else
				ItemType = null;
		}
		if (FCode.equalsIgnoreCase("DealType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealType = FValue.trim();
			}
			else
				DealType = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		if (FCode.equalsIgnoreCase("InterfaceClassName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InterfaceClassName = FValue.trim();
			}
			else
				InterfaceClassName = null;
		}
		if (FCode.equalsIgnoreCase("DestTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestTableName = FValue.trim();
			}
			else
				DestTableName = null;
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
		if (FCode.equalsIgnoreCase("CalSQL1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL1 = FValue.trim();
			}
			else
				CalSQL1 = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL2 = FValue.trim();
			}
			else
				CalSQL2 = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL3 = FValue.trim();
			}
			else
				CalSQL3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFDesbModeSchema other = (LFDesbModeSchema)otherObject;
		return
			ItemCode.equals(other.getItemCode())
			&& ItemNum == other.getItemNum()
			&& ItemType.equals(other.getItemType())
			&& DealType.equals(other.getDealType())
			&& CalSQL.equals(other.getCalSQL())
			&& InterfaceClassName.equals(other.getInterfaceClassName())
			&& DestTableName.equals(other.getDestTableName())
			&& Remark.equals(other.getRemark())
			&& CalSQL1.equals(other.getCalSQL1())
			&& CalSQL2.equals(other.getCalSQL2())
			&& CalSQL3.equals(other.getCalSQL3());
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
		if( strFieldName.equals("ItemCode") ) {
			return 0;
		}
		if( strFieldName.equals("ItemNum") ) {
			return 1;
		}
		if( strFieldName.equals("ItemType") ) {
			return 2;
		}
		if( strFieldName.equals("DealType") ) {
			return 3;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 4;
		}
		if( strFieldName.equals("InterfaceClassName") ) {
			return 5;
		}
		if( strFieldName.equals("DestTableName") ) {
			return 6;
		}
		if( strFieldName.equals("Remark") ) {
			return 7;
		}
		if( strFieldName.equals("CalSQL1") ) {
			return 8;
		}
		if( strFieldName.equals("CalSQL2") ) {
			return 9;
		}
		if( strFieldName.equals("CalSQL3") ) {
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
				strFieldName = "ItemCode";
				break;
			case 1:
				strFieldName = "ItemNum";
				break;
			case 2:
				strFieldName = "ItemType";
				break;
			case 3:
				strFieldName = "DealType";
				break;
			case 4:
				strFieldName = "CalSQL";
				break;
			case 5:
				strFieldName = "InterfaceClassName";
				break;
			case 6:
				strFieldName = "DestTableName";
				break;
			case 7:
				strFieldName = "Remark";
				break;
			case 8:
				strFieldName = "CalSQL1";
				break;
			case 9:
				strFieldName = "CalSQL2";
				break;
			case 10:
				strFieldName = "CalSQL3";
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
		if( strFieldName.equals("ItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InterfaceClassName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL3") ) {
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
				nFieldType = Schema.TYPE_INT;
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
