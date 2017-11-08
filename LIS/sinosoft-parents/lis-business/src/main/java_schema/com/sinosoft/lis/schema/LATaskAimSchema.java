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
import com.sinosoft.lis.db.LATaskAimDB;

/*
 * <p>ClassName: LATaskAimSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LATaskAimSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LATaskAimSchema.class);

	// @Field
	/** 任务号 */
	private String TaskID;
	/** 目标表 */
	private String AimTable;
	/** 目标字段 */
	private String AimCol;
	/** 相关指标 */
	private String IndexSet;
	/** 指标类型 */
	private String IndexType;
	/** Sql计算编码 */
	private String CalCode;
	/** 计算类型 */
	private String CalType;
	/** 计算顺序 */
	private int CalOrder;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LATaskAimSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "TaskID";
		pk[1] = "AimTable";
		pk[2] = "AimCol";

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
		LATaskAimSchema cloned = (LATaskAimSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTaskID()
	{
		return TaskID;
	}
	public void setTaskID(String aTaskID)
	{
		TaskID = aTaskID;
	}
	public String getAimTable()
	{
		return AimTable;
	}
	public void setAimTable(String aAimTable)
	{
		AimTable = aAimTable;
	}
	public String getAimCol()
	{
		return AimCol;
	}
	public void setAimCol(String aAimCol)
	{
		AimCol = aAimCol;
	}
	public String getIndexSet()
	{
		return IndexSet;
	}
	public void setIndexSet(String aIndexSet)
	{
		IndexSet = aIndexSet;
	}
	/**
	* 99-系统指标<p>
	* 00-基本指标<p>
	* 01-个人佣金<p>
	* 02-个人考核<p>
	* 04-银代考核
	*/
	public String getIndexType()
	{
		return IndexType;
	}
	public void setIndexType(String aIndexType)
	{
		IndexType = aIndexType;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 计算类型（0-Sql、1-其它）
	*/
	public String getCalType()
	{
		return CalType;
	}
	public void setCalType(String aCalType)
	{
		CalType = aCalType;
	}
	public int getCalOrder()
	{
		return CalOrder;
	}
	public void setCalOrder(int aCalOrder)
	{
		CalOrder = aCalOrder;
	}
	public void setCalOrder(String aCalOrder)
	{
		if (aCalOrder != null && !aCalOrder.equals(""))
		{
			Integer tInteger = new Integer(aCalOrder);
			int i = tInteger.intValue();
			CalOrder = i;
		}
	}


	/**
	* 使用另外一个 LATaskAimSchema 对象给 Schema 赋值
	* @param: aLATaskAimSchema LATaskAimSchema
	**/
	public void setSchema(LATaskAimSchema aLATaskAimSchema)
	{
		this.TaskID = aLATaskAimSchema.getTaskID();
		this.AimTable = aLATaskAimSchema.getAimTable();
		this.AimCol = aLATaskAimSchema.getAimCol();
		this.IndexSet = aLATaskAimSchema.getIndexSet();
		this.IndexType = aLATaskAimSchema.getIndexType();
		this.CalCode = aLATaskAimSchema.getCalCode();
		this.CalType = aLATaskAimSchema.getCalType();
		this.CalOrder = aLATaskAimSchema.getCalOrder();
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
			if( rs.getString("TaskID") == null )
				this.TaskID = null;
			else
				this.TaskID = rs.getString("TaskID").trim();

			if( rs.getString("AimTable") == null )
				this.AimTable = null;
			else
				this.AimTable = rs.getString("AimTable").trim();

			if( rs.getString("AimCol") == null )
				this.AimCol = null;
			else
				this.AimCol = rs.getString("AimCol").trim();

			if( rs.getString("IndexSet") == null )
				this.IndexSet = null;
			else
				this.IndexSet = rs.getString("IndexSet").trim();

			if( rs.getString("IndexType") == null )
				this.IndexType = null;
			else
				this.IndexType = rs.getString("IndexType").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalType") == null )
				this.CalType = null;
			else
				this.CalType = rs.getString("CalType").trim();

			this.CalOrder = rs.getInt("CalOrder");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LATaskAim表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATaskAimSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LATaskAimSchema getSchema()
	{
		LATaskAimSchema aLATaskAimSchema = new LATaskAimSchema();
		aLATaskAimSchema.setSchema(this);
		return aLATaskAimSchema;
	}

	public LATaskAimDB getDB()
	{
		LATaskAimDB aDBOper = new LATaskAimDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATaskAim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TaskID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AimTable)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AimCol)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexSet)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalOrder));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLATaskAim>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TaskID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AimTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AimCol = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IndexSet = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IndexType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LATaskAimSchema";
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
		if (FCode.equalsIgnoreCase("TaskID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TaskID));
		}
		if (FCode.equalsIgnoreCase("AimTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AimTable));
		}
		if (FCode.equalsIgnoreCase("AimCol"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AimCol));
		}
		if (FCode.equalsIgnoreCase("IndexSet"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexSet));
		}
		if (FCode.equalsIgnoreCase("IndexType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexType));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalType));
		}
		if (FCode.equalsIgnoreCase("CalOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalOrder));
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
				strFieldValue = StrTool.GBKToUnicode(TaskID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AimTable);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AimCol);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IndexSet);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IndexType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalType);
				break;
			case 7:
				strFieldValue = String.valueOf(CalOrder);
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

		if (FCode.equalsIgnoreCase("TaskID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TaskID = FValue.trim();
			}
			else
				TaskID = null;
		}
		if (FCode.equalsIgnoreCase("AimTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AimTable = FValue.trim();
			}
			else
				AimTable = null;
		}
		if (FCode.equalsIgnoreCase("AimCol"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AimCol = FValue.trim();
			}
			else
				AimCol = null;
		}
		if (FCode.equalsIgnoreCase("IndexSet"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexSet = FValue.trim();
			}
			else
				IndexSet = null;
		}
		if (FCode.equalsIgnoreCase("IndexType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexType = FValue.trim();
			}
			else
				IndexType = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalType = FValue.trim();
			}
			else
				CalType = null;
		}
		if (FCode.equalsIgnoreCase("CalOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalOrder = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LATaskAimSchema other = (LATaskAimSchema)otherObject;
		return
			TaskID.equals(other.getTaskID())
			&& AimTable.equals(other.getAimTable())
			&& AimCol.equals(other.getAimCol())
			&& IndexSet.equals(other.getIndexSet())
			&& IndexType.equals(other.getIndexType())
			&& CalCode.equals(other.getCalCode())
			&& CalType.equals(other.getCalType())
			&& CalOrder == other.getCalOrder();
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
		if( strFieldName.equals("TaskID") ) {
			return 0;
		}
		if( strFieldName.equals("AimTable") ) {
			return 1;
		}
		if( strFieldName.equals("AimCol") ) {
			return 2;
		}
		if( strFieldName.equals("IndexSet") ) {
			return 3;
		}
		if( strFieldName.equals("IndexType") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode") ) {
			return 5;
		}
		if( strFieldName.equals("CalType") ) {
			return 6;
		}
		if( strFieldName.equals("CalOrder") ) {
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
				strFieldName = "TaskID";
				break;
			case 1:
				strFieldName = "AimTable";
				break;
			case 2:
				strFieldName = "AimCol";
				break;
			case 3:
				strFieldName = "IndexSet";
				break;
			case 4:
				strFieldName = "IndexType";
				break;
			case 5:
				strFieldName = "CalCode";
				break;
			case 6:
				strFieldName = "CalType";
				break;
			case 7:
				strFieldName = "CalOrder";
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
		if( strFieldName.equals("TaskID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AimTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AimCol") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndexSet") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndexType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
