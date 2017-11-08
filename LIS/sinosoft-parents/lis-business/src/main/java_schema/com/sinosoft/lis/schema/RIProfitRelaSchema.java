

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
import com.sinosoft.lis.db.RIProfitRelaDB;

/*
 * <p>ClassName: RIProfitRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIProfitRelaSchema implements Schema, Cloneable
{
	// @Field
	/** 纯益手续费编码 */
	private String RIProfitNo;
	/** 关联id */
	private String RIProfitRelaID;
	/** 关联名称 */
	private String RIProfitRelaName;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作人 */
	private String Operator;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIProfitRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RIProfitNo";
		pk[1] = "RIProfitRelaID";

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
		RIProfitRelaSchema cloned = (RIProfitRelaSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRIProfitNo()
	{
		return RIProfitNo;
	}
	public void setRIProfitNo(String aRIProfitNo)
	{
		RIProfitNo = aRIProfitNo;
	}
	public String getRIProfitRelaID()
	{
		return RIProfitRelaID;
	}
	public void setRIProfitRelaID(String aRIProfitRelaID)
	{
		RIProfitRelaID = aRIProfitRelaID;
	}
	public String getRIProfitRelaName()
	{
		return RIProfitRelaName;
	}
	public void setRIProfitRelaName(String aRIProfitRelaName)
	{
		RIProfitRelaName = aRIProfitRelaName;
	}
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	public String getModifyTime()
	{
		return ModifyTime;
	}
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}

	/**
	* 使用另外一个 RIProfitRelaSchema 对象给 Schema 赋值
	* @param: aRIProfitRelaSchema RIProfitRelaSchema
	**/
	public void setSchema(RIProfitRelaSchema aRIProfitRelaSchema)
	{
		this.RIProfitNo = aRIProfitRelaSchema.getRIProfitNo();
		this.RIProfitRelaID = aRIProfitRelaSchema.getRIProfitRelaID();
		this.RIProfitRelaName = aRIProfitRelaSchema.getRIProfitRelaName();
		this.ModifyDate = fDate.getDate( aRIProfitRelaSchema.getModifyDate());
		this.ModifyTime = aRIProfitRelaSchema.getModifyTime();
		this.Operator = aRIProfitRelaSchema.getOperator();
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
			if( rs.getString("RIProfitNo") == null )
				this.RIProfitNo = null;
			else
				this.RIProfitNo = rs.getString("RIProfitNo").trim();

			if( rs.getString("RIProfitRelaID") == null )
				this.RIProfitRelaID = null;
			else
				this.RIProfitRelaID = rs.getString("RIProfitRelaID").trim();

			if( rs.getString("RIProfitRelaName") == null )
				this.RIProfitRelaName = null;
			else
				this.RIProfitRelaName = rs.getString("RIProfitRelaName").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIProfitRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIProfitRelaSchema getSchema()
	{
		RIProfitRelaSchema aRIProfitRelaSchema = new RIProfitRelaSchema();
		aRIProfitRelaSchema.setSchema(this);
		return aRIProfitRelaSchema;
	}

	public RIProfitRelaDB getDB()
	{
		RIProfitRelaDB aDBOper = new RIProfitRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProfitRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIProfitNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIProfitRelaID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIProfitRelaName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProfitRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIProfitNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIProfitRelaID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RIProfitRelaName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitRelaSchema";
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
		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitNo));
		}
		if (FCode.equalsIgnoreCase("RIProfitRelaID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitRelaID));
		}
		if (FCode.equalsIgnoreCase("RIProfitRelaName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitRelaName));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(RIProfitNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIProfitRelaID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RIProfitRelaName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("RIProfitNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitNo = FValue.trim();
			}
			else
				RIProfitNo = null;
		}
		if (FCode.equalsIgnoreCase("RIProfitRelaID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitRelaID = FValue.trim();
			}
			else
				RIProfitRelaID = null;
		}
		if (FCode.equalsIgnoreCase("RIProfitRelaName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitRelaName = FValue.trim();
			}
			else
				RIProfitRelaName = null;
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIProfitRelaSchema other = (RIProfitRelaSchema)otherObject;
		return
			RIProfitNo.equals(other.getRIProfitNo())
			&& RIProfitRelaID.equals(other.getRIProfitRelaID())
			&& RIProfitRelaName.equals(other.getRIProfitRelaName())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("RIProfitNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIProfitRelaID") ) {
			return 1;
		}
		if( strFieldName.equals("RIProfitRelaName") ) {
			return 2;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 3;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
			return 5;
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
				strFieldName = "RIProfitNo";
				break;
			case 1:
				strFieldName = "RIProfitRelaID";
				break;
			case 2:
				strFieldName = "RIProfitRelaName";
				break;
			case 3:
				strFieldName = "ModifyDate";
				break;
			case 4:
				strFieldName = "ModifyTime";
				break;
			case 5:
				strFieldName = "Operator";
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
		if( strFieldName.equals("RIProfitNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIProfitRelaID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIProfitRelaName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
