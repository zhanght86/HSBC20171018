

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
import com.sinosoft.lis.db.RIParamMapDB;

/*
 * <p>ClassName: RIParamMapSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIParamMapSchema implements Schema, Cloneable
{
	// @Field
	/** 算法编码 */
	private String ArithmeticID;
	/** 序号 */
	private int SerialNo;
	/** 参数名称 */
	private String ParamName;
	/** 计算项类别 */
	private String CalItemType;
	/** 参数代码 */
	private String ParamCode;
	/** 映射字段 */
	private String MapCode;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIParamMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ArithmeticID";
		pk[1] = "SerialNo";

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
		RIParamMapSchema cloned = (RIParamMapSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
	}
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getParamName()
	{
		return ParamName;
	}
	public void setParamName(String aParamName)
	{
		ParamName = aParamName;
	}
	/**
	* 01-新单计算 02-续期计算 03-保全调整 04-理赔摊回 05通用
	*/
	public String getCalItemType()
	{
		return CalItemType;
	}
	public void setCalItemType(String aCalItemType)
	{
		CalItemType = aCalItemType;
	}
	public String getParamCode()
	{
		return ParamCode;
	}
	public void setParamCode(String aParamCode)
	{
		ParamCode = aParamCode;
	}
	public String getMapCode()
	{
		return MapCode;
	}
	public void setMapCode(String aMapCode)
	{
		MapCode = aMapCode;
	}

	/**
	* 使用另外一个 RIParamMapSchema 对象给 Schema 赋值
	* @param: aRIParamMapSchema RIParamMapSchema
	**/
	public void setSchema(RIParamMapSchema aRIParamMapSchema)
	{
		this.ArithmeticID = aRIParamMapSchema.getArithmeticID();
		this.SerialNo = aRIParamMapSchema.getSerialNo();
		this.ParamName = aRIParamMapSchema.getParamName();
		this.CalItemType = aRIParamMapSchema.getCalItemType();
		this.ParamCode = aRIParamMapSchema.getParamCode();
		this.MapCode = aRIParamMapSchema.getMapCode();
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
			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("ParamName") == null )
				this.ParamName = null;
			else
				this.ParamName = rs.getString("ParamName").trim();

			if( rs.getString("CalItemType") == null )
				this.CalItemType = null;
			else
				this.CalItemType = rs.getString("CalItemType").trim();

			if( rs.getString("ParamCode") == null )
				this.ParamCode = null;
			else
				this.ParamCode = rs.getString("ParamCode").trim();

			if( rs.getString("MapCode") == null )
				this.MapCode = null;
			else
				this.MapCode = rs.getString("MapCode").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIParamMap表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIParamMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIParamMapSchema getSchema()
	{
		RIParamMapSchema aRIParamMapSchema = new RIParamMapSchema();
		aRIParamMapSchema.setSchema(this);
		return aRIParamMapSchema;
	}

	public RIParamMapDB getDB()
	{
		RIParamMapDB aDBOper = new RIParamMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIParamMap描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MapCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIParamMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ParamName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ParamCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MapCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIParamMapSchema";
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
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamName));
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemType));
		}
		if (FCode.equalsIgnoreCase("ParamCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamCode));
		}
		if (FCode.equalsIgnoreCase("MapCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MapCode));
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
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 1:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParamName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalItemType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ParamCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MapCode);
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

		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamName = FValue.trim();
			}
			else
				ParamName = null;
		}
		if (FCode.equalsIgnoreCase("CalItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemType = FValue.trim();
			}
			else
				CalItemType = null;
		}
		if (FCode.equalsIgnoreCase("ParamCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamCode = FValue.trim();
			}
			else
				ParamCode = null;
		}
		if (FCode.equalsIgnoreCase("MapCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MapCode = FValue.trim();
			}
			else
				MapCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIParamMapSchema other = (RIParamMapSchema)otherObject;
		return
			ArithmeticID.equals(other.getArithmeticID())
			&& SerialNo == other.getSerialNo()
			&& ParamName.equals(other.getParamName())
			&& CalItemType.equals(other.getCalItemType())
			&& ParamCode.equals(other.getParamCode())
			&& MapCode.equals(other.getMapCode());
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
		if( strFieldName.equals("ArithmeticID") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("ParamName") ) {
			return 2;
		}
		if( strFieldName.equals("CalItemType") ) {
			return 3;
		}
		if( strFieldName.equals("ParamCode") ) {
			return 4;
		}
		if( strFieldName.equals("MapCode") ) {
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
				strFieldName = "ArithmeticID";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "ParamName";
				break;
			case 3:
				strFieldName = "CalItemType";
				break;
			case 4:
				strFieldName = "ParamCode";
				break;
			case 5:
				strFieldName = "MapCode";
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
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ParamName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MapCode") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
