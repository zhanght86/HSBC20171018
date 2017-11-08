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
import com.sinosoft.lis.db.LWFieldMapDB;

/*
 * <p>ClassName: LWFieldMapSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWFieldMapSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWFieldMapSchema.class);
	// @Field
	/** 活动id */
	private String ActivityID;
	/** 顺序号 */
	private int FieldOrder;
	/** 源表名 */
	private String SourTableName;
	/** 源字段 */
	private String SourFieldName;
	/** 源字段中文名 */
	private String SourFieldCName;
	/** 目标表名 */
	private String DestTableName;
	/** 目标字段 */
	private String DestFieldName;
	/** 目标字段中文名 */
	private String DestFieldCName;
	/** 从源到目标的取数规则 */
	private String GetValue;
	/** 从源到目标的取数规则类型 */
	private String GetValueType;
	/** 是否显示 */
	private String CanShow;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWFieldMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ActivityID";
		pk[1] = "FieldOrder";

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
		LWFieldMapSchema cloned = (LWFieldMapSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getActivityID()
	{
		return ActivityID;
	}
	public void setActivityID(String aActivityID)
	{
		if(aActivityID!=null && aActivityID.length()>10)
			throw new IllegalArgumentException("活动idActivityID值"+aActivityID+"的长度"+aActivityID.length()+"大于最大值10");
		ActivityID = aActivityID;
	}
	public int getFieldOrder()
	{
		return FieldOrder;
	}
	public void setFieldOrder(int aFieldOrder)
	{
		FieldOrder = aFieldOrder;
	}
	public void setFieldOrder(String aFieldOrder)
	{
		if (aFieldOrder != null && !aFieldOrder.equals(""))
		{
			Integer tInteger = new Integer(aFieldOrder);
			int i = tInteger.intValue();
			FieldOrder = i;
		}
	}

	public String getSourTableName()
	{
		return SourTableName;
	}
	public void setSourTableName(String aSourTableName)
	{
		if(aSourTableName!=null && aSourTableName.length()>40)
			throw new IllegalArgumentException("源表名SourTableName值"+aSourTableName+"的长度"+aSourTableName.length()+"大于最大值40");
		SourTableName = aSourTableName;
	}
	public String getSourFieldName()
	{
		return SourFieldName;
	}
	public void setSourFieldName(String aSourFieldName)
	{
		if(aSourFieldName!=null && aSourFieldName.length()>40)
			throw new IllegalArgumentException("源字段SourFieldName值"+aSourFieldName+"的长度"+aSourFieldName.length()+"大于最大值40");
		SourFieldName = aSourFieldName;
	}
	public String getSourFieldCName()
	{
		return SourFieldCName;
	}
	public void setSourFieldCName(String aSourFieldCName)
	{
		if(aSourFieldCName!=null && aSourFieldCName.length()>40)
			throw new IllegalArgumentException("源字段中文名SourFieldCName值"+aSourFieldCName+"的长度"+aSourFieldCName.length()+"大于最大值40");
		SourFieldCName = aSourFieldCName;
	}
	public String getDestTableName()
	{
		return DestTableName;
	}
	public void setDestTableName(String aDestTableName)
	{
		if(aDestTableName!=null && aDestTableName.length()>40)
			throw new IllegalArgumentException("目标表名DestTableName值"+aDestTableName+"的长度"+aDestTableName.length()+"大于最大值40");
		DestTableName = aDestTableName;
	}
	public String getDestFieldName()
	{
		return DestFieldName;
	}
	public void setDestFieldName(String aDestFieldName)
	{
		if(aDestFieldName!=null && aDestFieldName.length()>40)
			throw new IllegalArgumentException("目标字段DestFieldName值"+aDestFieldName+"的长度"+aDestFieldName.length()+"大于最大值40");
		DestFieldName = aDestFieldName;
	}
	public String getDestFieldCName()
	{
		return DestFieldCName;
	}
	public void setDestFieldCName(String aDestFieldCName)
	{
		if(aDestFieldCName!=null && aDestFieldCName.length()>40)
			throw new IllegalArgumentException("目标字段中文名DestFieldCName值"+aDestFieldCName+"的长度"+aDestFieldCName.length()+"大于最大值40");
		DestFieldCName = aDestFieldCName;
	}
	public String getGetValue()
	{
		return GetValue;
	}
	public void setGetValue(String aGetValue)
	{
		if(aGetValue!=null && aGetValue.length()>4000)
			throw new IllegalArgumentException("从源到目标的取数规则GetValue值"+aGetValue+"的长度"+aGetValue.length()+"大于最大值4000");
		GetValue = aGetValue;
	}
	/**
	* 0 -- 默认，表示通过SQL语句取数<p>
	* 1 -- 表示通过特殊的类来取数。
	*/
	public String getGetValueType()
	{
		return GetValueType;
	}
	public void setGetValueType(String aGetValueType)
	{
		if(aGetValueType!=null && aGetValueType.length()>1)
			throw new IllegalArgumentException("从源到目标的取数规则类型GetValueType值"+aGetValueType+"的长度"+aGetValueType.length()+"大于最大值1");
		GetValueType = aGetValueType;
	}
	/**
	* 0--不显示<p>
	* 1--显示
	*/
	public String getCanShow()
	{
		return CanShow;
	}
	public void setCanShow(String aCanShow)
	{
		if(aCanShow!=null && aCanShow.length()>1)
			throw new IllegalArgumentException("是否显示CanShow值"+aCanShow+"的长度"+aCanShow.length()+"大于最大值1");
		CanShow = aCanShow;
	}

	/**
	* 使用另外一个 LWFieldMapSchema 对象给 Schema 赋值
	* @param: aLWFieldMapSchema LWFieldMapSchema
	**/
	public void setSchema(LWFieldMapSchema aLWFieldMapSchema)
	{
		this.ActivityID = aLWFieldMapSchema.getActivityID();
		this.FieldOrder = aLWFieldMapSchema.getFieldOrder();
		this.SourTableName = aLWFieldMapSchema.getSourTableName();
		this.SourFieldName = aLWFieldMapSchema.getSourFieldName();
		this.SourFieldCName = aLWFieldMapSchema.getSourFieldCName();
		this.DestTableName = aLWFieldMapSchema.getDestTableName();
		this.DestFieldName = aLWFieldMapSchema.getDestFieldName();
		this.DestFieldCName = aLWFieldMapSchema.getDestFieldCName();
		this.GetValue = aLWFieldMapSchema.getGetValue();
		this.GetValueType = aLWFieldMapSchema.getGetValueType();
		this.CanShow = aLWFieldMapSchema.getCanShow();
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
			if( rs.getString("ActivityID") == null )
				this.ActivityID = null;
			else
				this.ActivityID = rs.getString("ActivityID").trim();

			this.FieldOrder = rs.getInt("FieldOrder");
			if( rs.getString("SourTableName") == null )
				this.SourTableName = null;
			else
				this.SourTableName = rs.getString("SourTableName").trim();

			if( rs.getString("SourFieldName") == null )
				this.SourFieldName = null;
			else
				this.SourFieldName = rs.getString("SourFieldName").trim();

			if( rs.getString("SourFieldCName") == null )
				this.SourFieldCName = null;
			else
				this.SourFieldCName = rs.getString("SourFieldCName").trim();

			if( rs.getString("DestTableName") == null )
				this.DestTableName = null;
			else
				this.DestTableName = rs.getString("DestTableName").trim();

			if( rs.getString("DestFieldName") == null )
				this.DestFieldName = null;
			else
				this.DestFieldName = rs.getString("DestFieldName").trim();

			if( rs.getString("DestFieldCName") == null )
				this.DestFieldCName = null;
			else
				this.DestFieldCName = rs.getString("DestFieldCName").trim();

			if( rs.getString("GetValue") == null )
				this.GetValue = null;
			else
				this.GetValue = rs.getString("GetValue").trim();

			if( rs.getString("GetValueType") == null )
				this.GetValueType = null;
			else
				this.GetValueType = rs.getString("GetValueType").trim();

			if( rs.getString("CanShow") == null )
				this.CanShow = null;
			else
				this.CanShow = rs.getString("CanShow").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWFieldMap表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWFieldMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWFieldMapSchema getSchema()
	{
		LWFieldMapSchema aLWFieldMapSchema = new LWFieldMapSchema();
		aLWFieldMapSchema.setSchema(this);
		return aLWFieldMapSchema;
	}

	public LWFieldMapDB getDB()
	{
		LWFieldMapDB aDBOper = new LWFieldMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWFieldMap描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ActivityID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FieldOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourFieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourFieldCName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestFieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestFieldCName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetValueType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CanShow));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWFieldMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ActivityID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FieldOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			SourTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SourFieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SourFieldCName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DestTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DestFieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DestFieldCName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GetValueType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CanShow = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWFieldMapSchema";
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
		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActivityID));
		}
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldOrder));
		}
		if (FCode.equalsIgnoreCase("SourTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourTableName));
		}
		if (FCode.equalsIgnoreCase("SourFieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourFieldName));
		}
		if (FCode.equalsIgnoreCase("SourFieldCName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourFieldCName));
		}
		if (FCode.equalsIgnoreCase("DestTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestTableName));
		}
		if (FCode.equalsIgnoreCase("DestFieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestFieldName));
		}
		if (FCode.equalsIgnoreCase("DestFieldCName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestFieldCName));
		}
		if (FCode.equalsIgnoreCase("GetValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetValue));
		}
		if (FCode.equalsIgnoreCase("GetValueType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetValueType));
		}
		if (FCode.equalsIgnoreCase("CanShow"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CanShow));
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
				strFieldValue = StrTool.GBKToUnicode(ActivityID);
				break;
			case 1:
				strFieldValue = String.valueOf(FieldOrder);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SourTableName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SourFieldName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SourFieldCName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DestTableName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DestFieldName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DestFieldCName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GetValue);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GetValueType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CanShow);
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

		if (FCode.equalsIgnoreCase("ActivityID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActivityID = FValue.trim();
			}
			else
				ActivityID = null;
		}
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FieldOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("SourTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourTableName = FValue.trim();
			}
			else
				SourTableName = null;
		}
		if (FCode.equalsIgnoreCase("SourFieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourFieldName = FValue.trim();
			}
			else
				SourFieldName = null;
		}
		if (FCode.equalsIgnoreCase("SourFieldCName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourFieldCName = FValue.trim();
			}
			else
				SourFieldCName = null;
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
		if (FCode.equalsIgnoreCase("DestFieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestFieldName = FValue.trim();
			}
			else
				DestFieldName = null;
		}
		if (FCode.equalsIgnoreCase("DestFieldCName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestFieldCName = FValue.trim();
			}
			else
				DestFieldCName = null;
		}
		if (FCode.equalsIgnoreCase("GetValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetValue = FValue.trim();
			}
			else
				GetValue = null;
		}
		if (FCode.equalsIgnoreCase("GetValueType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetValueType = FValue.trim();
			}
			else
				GetValueType = null;
		}
		if (FCode.equalsIgnoreCase("CanShow"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CanShow = FValue.trim();
			}
			else
				CanShow = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWFieldMapSchema other = (LWFieldMapSchema)otherObject;
		return
			ActivityID.equals(other.getActivityID())
			&& FieldOrder == other.getFieldOrder()
			&& SourTableName.equals(other.getSourTableName())
			&& SourFieldName.equals(other.getSourFieldName())
			&& SourFieldCName.equals(other.getSourFieldCName())
			&& DestTableName.equals(other.getDestTableName())
			&& DestFieldName.equals(other.getDestFieldName())
			&& DestFieldCName.equals(other.getDestFieldCName())
			&& GetValue.equals(other.getGetValue())
			&& GetValueType.equals(other.getGetValueType())
			&& CanShow.equals(other.getCanShow());
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
		if( strFieldName.equals("ActivityID") ) {
			return 0;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return 1;
		}
		if( strFieldName.equals("SourTableName") ) {
			return 2;
		}
		if( strFieldName.equals("SourFieldName") ) {
			return 3;
		}
		if( strFieldName.equals("SourFieldCName") ) {
			return 4;
		}
		if( strFieldName.equals("DestTableName") ) {
			return 5;
		}
		if( strFieldName.equals("DestFieldName") ) {
			return 6;
		}
		if( strFieldName.equals("DestFieldCName") ) {
			return 7;
		}
		if( strFieldName.equals("GetValue") ) {
			return 8;
		}
		if( strFieldName.equals("GetValueType") ) {
			return 9;
		}
		if( strFieldName.equals("CanShow") ) {
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
				strFieldName = "ActivityID";
				break;
			case 1:
				strFieldName = "FieldOrder";
				break;
			case 2:
				strFieldName = "SourTableName";
				break;
			case 3:
				strFieldName = "SourFieldName";
				break;
			case 4:
				strFieldName = "SourFieldCName";
				break;
			case 5:
				strFieldName = "DestTableName";
				break;
			case 6:
				strFieldName = "DestFieldName";
				break;
			case 7:
				strFieldName = "DestFieldCName";
				break;
			case 8:
				strFieldName = "GetValue";
				break;
			case 9:
				strFieldName = "GetValueType";
				break;
			case 10:
				strFieldName = "CanShow";
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
		if( strFieldName.equals("ActivityID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SourTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourFieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourFieldCName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestFieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestFieldCName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetValueType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CanShow") ) {
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
