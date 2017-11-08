

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
import com.sinosoft.lis.db.RIAccumulateRDCodeDB;

/*
 * <p>ClassName: RIAccumulateRDCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIAccumulateRDCodeSchema implements Schema, Cloneable
{
	// @Field
	/** 累计方案编码 */
	private String AccumulateDefNO;
	/** 险种编码/责任编码 */
	private String AssociatedCode;
	/** 险种编码/责任名称 */
	private String AssociatedName;
	/** 备用字段 */
	private String StandbyFlag;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIAccumulateRDCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AccumulateDefNO";
		pk[1] = "AssociatedCode";

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
		RIAccumulateRDCodeSchema cloned = (RIAccumulateRDCodeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccumulateDefNO()
	{
		return AccumulateDefNO;
	}
	public void setAccumulateDefNO(String aAccumulateDefNO)
	{
		AccumulateDefNO = aAccumulateDefNO;
	}
	public String getAssociatedCode()
	{
		return AssociatedCode;
	}
	public void setAssociatedCode(String aAssociatedCode)
	{
		AssociatedCode = aAssociatedCode;
	}
	public String getAssociatedName()
	{
		return AssociatedName;
	}
	public void setAssociatedName(String aAssociatedName)
	{
		AssociatedName = aAssociatedName;
	}
	public String getStandbyFlag()
	{
		return StandbyFlag;
	}
	public void setStandbyFlag(String aStandbyFlag)
	{
		StandbyFlag = aStandbyFlag;
	}
	/**
	* 01-有效 02-失效
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 RIAccumulateRDCodeSchema 对象给 Schema 赋值
	* @param: aRIAccumulateRDCodeSchema RIAccumulateRDCodeSchema
	**/
	public void setSchema(RIAccumulateRDCodeSchema aRIAccumulateRDCodeSchema)
	{
		this.AccumulateDefNO = aRIAccumulateRDCodeSchema.getAccumulateDefNO();
		this.AssociatedCode = aRIAccumulateRDCodeSchema.getAssociatedCode();
		this.AssociatedName = aRIAccumulateRDCodeSchema.getAssociatedName();
		this.StandbyFlag = aRIAccumulateRDCodeSchema.getStandbyFlag();
		this.State = aRIAccumulateRDCodeSchema.getState();
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
			if( rs.getString("AccumulateDefNO") == null )
				this.AccumulateDefNO = null;
			else
				this.AccumulateDefNO = rs.getString("AccumulateDefNO").trim();

			if( rs.getString("AssociatedCode") == null )
				this.AssociatedCode = null;
			else
				this.AssociatedCode = rs.getString("AssociatedCode").trim();

			if( rs.getString("AssociatedName") == null )
				this.AssociatedName = null;
			else
				this.AssociatedName = rs.getString("AssociatedName").trim();

			if( rs.getString("StandbyFlag") == null )
				this.StandbyFlag = null;
			else
				this.StandbyFlag = rs.getString("StandbyFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIAccumulateRDCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAccumulateRDCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIAccumulateRDCodeSchema getSchema()
	{
		RIAccumulateRDCodeSchema aRIAccumulateRDCodeSchema = new RIAccumulateRDCodeSchema();
		aRIAccumulateRDCodeSchema.setSchema(this);
		return aRIAccumulateRDCodeSchema;
	}

	public RIAccumulateRDCodeDB getDB()
	{
		RIAccumulateRDCodeDB aDBOper = new RIAccumulateRDCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAccumulateRDCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccumulateDefNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssociatedCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssociatedName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAccumulateRDCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccumulateDefNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AssociatedCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AssociatedName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StandbyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAccumulateRDCodeSchema";
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
		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNO));
		}
		if (FCode.equalsIgnoreCase("AssociatedCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedCode));
		}
		if (FCode.equalsIgnoreCase("AssociatedName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssociatedName));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNO);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AssociatedCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AssociatedName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(State);
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

		if (FCode.equalsIgnoreCase("AccumulateDefNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNO = FValue.trim();
			}
			else
				AccumulateDefNO = null;
		}
		if (FCode.equalsIgnoreCase("AssociatedCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssociatedCode = FValue.trim();
			}
			else
				AssociatedCode = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag = FValue.trim();
			}
			else
				StandbyFlag = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIAccumulateRDCodeSchema other = (RIAccumulateRDCodeSchema)otherObject;
		return
			AccumulateDefNO.equals(other.getAccumulateDefNO())
			&& AssociatedCode.equals(other.getAssociatedCode())
			&& AssociatedName.equals(other.getAssociatedName())
			&& StandbyFlag.equals(other.getStandbyFlag())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("AccumulateDefNO") ) {
			return 0;
		}
		if( strFieldName.equals("AssociatedCode") ) {
			return 1;
		}
		if( strFieldName.equals("AssociatedName") ) {
			return 2;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return 3;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "AccumulateDefNO";
				break;
			case 1:
				strFieldName = "AssociatedCode";
				break;
			case 2:
				strFieldName = "AssociatedName";
				break;
			case 3:
				strFieldName = "StandbyFlag";
				break;
			case 4:
				strFieldName = "State";
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
		if( strFieldName.equals("AccumulateDefNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssociatedCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssociatedName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
