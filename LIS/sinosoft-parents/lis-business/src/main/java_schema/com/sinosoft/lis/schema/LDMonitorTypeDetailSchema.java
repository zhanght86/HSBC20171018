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
import com.sinosoft.lis.db.LDMonitorTypeDetailDB;

/*
 * <p>ClassName: LDMonitorTypeDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 自动监控平台
 */
public class LDMonitorTypeDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMonitorTypeDetailSchema.class);

	// @Field
	/** 监控类型编码 */
	private String MonitorTypeCode;
	/** 监控字段编码 */
	private String MonitorColCode;
	/** 显示顺序 */
	private int MonitorOrder;
	/** 监控字段名称 */
	private String MonitorColCodeName;
	/** 显示字段名称 */
	private String ShowColDetail;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMonitorTypeDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "MonitorTypeCode";
		pk[1] = "MonitorColCode";
		pk[2] = "MonitorOrder";

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
		LDMonitorTypeDetailSchema cloned = (LDMonitorTypeDetailSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMonitorTypeCode()
	{
		return MonitorTypeCode;
	}
	public void setMonitorTypeCode(String aMonitorTypeCode)
	{
		MonitorTypeCode = aMonitorTypeCode;
	}
	public String getMonitorColCode()
	{
		return MonitorColCode;
	}
	public void setMonitorColCode(String aMonitorColCode)
	{
		MonitorColCode = aMonitorColCode;
	}
	public int getMonitorOrder()
	{
		return MonitorOrder;
	}
	public void setMonitorOrder(int aMonitorOrder)
	{
		MonitorOrder = aMonitorOrder;
	}
	public void setMonitorOrder(String aMonitorOrder)
	{
		if (aMonitorOrder != null && !aMonitorOrder.equals(""))
		{
			Integer tInteger = new Integer(aMonitorOrder);
			int i = tInteger.intValue();
			MonitorOrder = i;
		}
	}

	public String getMonitorColCodeName()
	{
		return MonitorColCodeName;
	}
	public void setMonitorColCodeName(String aMonitorColCodeName)
	{
		MonitorColCodeName = aMonitorColCodeName;
	}
	public String getShowColDetail()
	{
		return ShowColDetail;
	}
	public void setShowColDetail(String aShowColDetail)
	{
		ShowColDetail = aShowColDetail;
	}

	/**
	* 使用另外一个 LDMonitorTypeDetailSchema 对象给 Schema 赋值
	* @param: aLDMonitorTypeDetailSchema LDMonitorTypeDetailSchema
	**/
	public void setSchema(LDMonitorTypeDetailSchema aLDMonitorTypeDetailSchema)
	{
		this.MonitorTypeCode = aLDMonitorTypeDetailSchema.getMonitorTypeCode();
		this.MonitorColCode = aLDMonitorTypeDetailSchema.getMonitorColCode();
		this.MonitorOrder = aLDMonitorTypeDetailSchema.getMonitorOrder();
		this.MonitorColCodeName = aLDMonitorTypeDetailSchema.getMonitorColCodeName();
		this.ShowColDetail = aLDMonitorTypeDetailSchema.getShowColDetail();
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
			if( rs.getString("MonitorTypeCode") == null )
				this.MonitorTypeCode = null;
			else
				this.MonitorTypeCode = rs.getString("MonitorTypeCode").trim();

			if( rs.getString("MonitorColCode") == null )
				this.MonitorColCode = null;
			else
				this.MonitorColCode = rs.getString("MonitorColCode").trim();

			this.MonitorOrder = rs.getInt("MonitorOrder");
			if( rs.getString("MonitorColCodeName") == null )
				this.MonitorColCodeName = null;
			else
				this.MonitorColCodeName = rs.getString("MonitorColCodeName").trim();

			if( rs.getString("ShowColDetail") == null )
				this.ShowColDetail = null;
			else
				this.ShowColDetail = rs.getString("ShowColDetail").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMonitorTypeDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorTypeDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMonitorTypeDetailSchema getSchema()
	{
		LDMonitorTypeDetailSchema aLDMonitorTypeDetailSchema = new LDMonitorTypeDetailSchema();
		aLDMonitorTypeDetailSchema.setSchema(this);
		return aLDMonitorTypeDetailSchema;
	}

	public LDMonitorTypeDetailDB getDB()
	{
		LDMonitorTypeDetailDB aDBOper = new LDMonitorTypeDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorTypeDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MonitorTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MonitorColCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MonitorOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MonitorColCodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ShowColDetail));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMonitorTypeDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MonitorTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MonitorColCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MonitorOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			MonitorColCodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ShowColDetail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMonitorTypeDetailSchema";
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
		if (FCode.equalsIgnoreCase("MonitorTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorTypeCode));
		}
		if (FCode.equalsIgnoreCase("MonitorColCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorColCode));
		}
		if (FCode.equalsIgnoreCase("MonitorOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorOrder));
		}
		if (FCode.equalsIgnoreCase("MonitorColCodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MonitorColCodeName));
		}
		if (FCode.equalsIgnoreCase("ShowColDetail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShowColDetail));
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
				strFieldValue = StrTool.GBKToUnicode(MonitorTypeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MonitorColCode);
				break;
			case 2:
				strFieldValue = String.valueOf(MonitorOrder);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MonitorColCodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ShowColDetail);
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

		if (FCode.equalsIgnoreCase("MonitorTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorTypeCode = FValue.trim();
			}
			else
				MonitorTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("MonitorColCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorColCode = FValue.trim();
			}
			else
				MonitorColCode = null;
		}
		if (FCode.equalsIgnoreCase("MonitorOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MonitorOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("MonitorColCodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MonitorColCodeName = FValue.trim();
			}
			else
				MonitorColCodeName = null;
		}
		if (FCode.equalsIgnoreCase("ShowColDetail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShowColDetail = FValue.trim();
			}
			else
				ShowColDetail = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMonitorTypeDetailSchema other = (LDMonitorTypeDetailSchema)otherObject;
		return
			MonitorTypeCode.equals(other.getMonitorTypeCode())
			&& MonitorColCode.equals(other.getMonitorColCode())
			&& MonitorOrder == other.getMonitorOrder()
			&& MonitorColCodeName.equals(other.getMonitorColCodeName())
			&& ShowColDetail.equals(other.getShowColDetail());
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
		if( strFieldName.equals("MonitorTypeCode") ) {
			return 0;
		}
		if( strFieldName.equals("MonitorColCode") ) {
			return 1;
		}
		if( strFieldName.equals("MonitorOrder") ) {
			return 2;
		}
		if( strFieldName.equals("MonitorColCodeName") ) {
			return 3;
		}
		if( strFieldName.equals("ShowColDetail") ) {
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
				strFieldName = "MonitorTypeCode";
				break;
			case 1:
				strFieldName = "MonitorColCode";
				break;
			case 2:
				strFieldName = "MonitorOrder";
				break;
			case 3:
				strFieldName = "MonitorColCodeName";
				break;
			case 4:
				strFieldName = "ShowColDetail";
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
		if( strFieldName.equals("MonitorTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MonitorColCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MonitorOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MonitorColCodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShowColDetail") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
