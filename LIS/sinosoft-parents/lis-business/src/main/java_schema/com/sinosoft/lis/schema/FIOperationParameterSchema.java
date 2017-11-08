/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIOperationParameterDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIOperationParameterSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIOperationParameterSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIOperationParameterSchema.class);

	// @Field
	/** 事件号码 */
	private String EventNo;
	/** 事件类型 */
	private String EventType;
	/** 参数类型 */
	private String ParameterType;
	/** 参数描述 */
	private String ParameterMark;
	/** 参数值 */
	private String ParameterValue;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIOperationParameterSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "EventNo";
		pk[1] = "ParameterType";

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
                FIOperationParameterSchema cloned = (FIOperationParameterSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEventNo()
	{
		return EventNo;
	}
	public void setEventNo(String aEventNo)
	{
		EventNo = aEventNo;
	}
	public String getEventType()
	{
		return EventType;
	}
	public void setEventType(String aEventType)
	{
		EventType = aEventType;
	}
	public String getParameterType()
	{
		return ParameterType;
	}
	public void setParameterType(String aParameterType)
	{
		ParameterType = aParameterType;
	}
	public String getParameterMark()
	{
		return ParameterMark;
	}
	public void setParameterMark(String aParameterMark)
	{
		ParameterMark = aParameterMark;
	}
	public String getParameterValue()
	{
		return ParameterValue;
	}
	public void setParameterValue(String aParameterValue)
	{
		ParameterValue = aParameterValue;
	}

	/**
	* 使用另外一个 FIOperationParameterSchema 对象给 Schema 赋值
	* @param: aFIOperationParameterSchema FIOperationParameterSchema
	**/
	public void setSchema(FIOperationParameterSchema aFIOperationParameterSchema)
	{
		this.EventNo = aFIOperationParameterSchema.getEventNo();
		this.EventType = aFIOperationParameterSchema.getEventType();
		this.ParameterType = aFIOperationParameterSchema.getParameterType();
		this.ParameterMark = aFIOperationParameterSchema.getParameterMark();
		this.ParameterValue = aFIOperationParameterSchema.getParameterValue();
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
			if( rs.getString("EventNo") == null )
				this.EventNo = null;
			else
				this.EventNo = rs.getString("EventNo").trim();

			if( rs.getString("EventType") == null )
				this.EventType = null;
			else
				this.EventType = rs.getString("EventType").trim();

			if( rs.getString("ParameterType") == null )
				this.ParameterType = null;
			else
				this.ParameterType = rs.getString("ParameterType").trim();

			if( rs.getString("ParameterMark") == null )
				this.ParameterMark = null;
			else
				this.ParameterMark = rs.getString("ParameterMark").trim();

			if( rs.getString("ParameterValue") == null )
				this.ParameterValue = null;
			else
				this.ParameterValue = rs.getString("ParameterValue").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIOperationParameter表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIOperationParameterSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIOperationParameterSchema getSchema()
	{
		FIOperationParameterSchema aFIOperationParameterSchema = new FIOperationParameterSchema();
		aFIOperationParameterSchema.setSchema(this);
		return aFIOperationParameterSchema;
	}

	public FIOperationParameterDB getDB()
	{
		FIOperationParameterDB aDBOper = new FIOperationParameterDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIOperationParameter描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(EventNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(EventType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParameterType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParameterMark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParameterValue));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIOperationParameter>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EventNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EventType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ParameterType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ParameterMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ParameterValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIOperationParameterSchema";
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
		if (FCode.equalsIgnoreCase("EventNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventNo));
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EventType));
		}
		if (FCode.equalsIgnoreCase("ParameterType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParameterType));
		}
		if (FCode.equalsIgnoreCase("ParameterMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParameterMark));
		}
		if (FCode.equalsIgnoreCase("ParameterValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParameterValue));
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
				strFieldValue = StrTool.GBKToUnicode(EventNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EventType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParameterType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ParameterMark);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ParameterValue);
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

		if (FCode.equalsIgnoreCase("EventNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventNo = FValue.trim();
			}
			else
				EventNo = null;
		}
		if (FCode.equalsIgnoreCase("EventType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EventType = FValue.trim();
			}
			else
				EventType = null;
		}
		if (FCode.equalsIgnoreCase("ParameterType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParameterType = FValue.trim();
			}
			else
				ParameterType = null;
		}
		if (FCode.equalsIgnoreCase("ParameterMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParameterMark = FValue.trim();
			}
			else
				ParameterMark = null;
		}
		if (FCode.equalsIgnoreCase("ParameterValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParameterValue = FValue.trim();
			}
			else
				ParameterValue = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIOperationParameterSchema other = (FIOperationParameterSchema)otherObject;
		return
			EventNo.equals(other.getEventNo())
			&& EventType.equals(other.getEventType())
			&& ParameterType.equals(other.getParameterType())
			&& ParameterMark.equals(other.getParameterMark())
			&& ParameterValue.equals(other.getParameterValue());
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
		if( strFieldName.equals("EventNo") ) {
			return 0;
		}
		if( strFieldName.equals("EventType") ) {
			return 1;
		}
		if( strFieldName.equals("ParameterType") ) {
			return 2;
		}
		if( strFieldName.equals("ParameterMark") ) {
			return 3;
		}
		if( strFieldName.equals("ParameterValue") ) {
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
				strFieldName = "EventNo";
				break;
			case 1:
				strFieldName = "EventType";
				break;
			case 2:
				strFieldName = "ParameterType";
				break;
			case 3:
				strFieldName = "ParameterMark";
				break;
			case 4:
				strFieldName = "ParameterValue";
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
		if( strFieldName.equals("EventNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EventType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParameterType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParameterMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParameterValue") ) {
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
