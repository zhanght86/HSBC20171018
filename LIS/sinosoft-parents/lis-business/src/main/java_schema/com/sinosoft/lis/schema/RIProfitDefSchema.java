

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
import com.sinosoft.lis.db.RIProfitDefDB;

/*
 * <p>ClassName: RIProfitDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIProfitDefSchema implements Schema, Cloneable
{
	// @Field
	/** 纯益手续费编码 */
	private String RIProfitNo;
	/** 纯益手续费名称 */
	private String RIProfitName;
	/** 再保公司 */
	private String ReComCode;
	/** 再保合同 */
	private String RIContNo;
	/** 关联类型 */
	private String RelaType;
	/** 描述 */
	private String RIProfitDes;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作人 */
	private String Operator;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIProfitDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RIProfitNo";

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
		RIProfitDefSchema cloned = (RIProfitDefSchema)super.clone();
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
	public String getRIProfitName()
	{
		return RIProfitName;
	}
	public void setRIProfitName(String aRIProfitName)
	{
		RIProfitName = aRIProfitName;
	}
	public String getReComCode()
	{
		return ReComCode;
	}
	public void setReComCode(String aReComCode)
	{
		ReComCode = aReComCode;
	}
	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	/**
	* 1-再保方案关联<p>
	* 2-分出责任关联<p>
	* 3-险种关联<p>
	* 4-责任关联
	*/
	public String getRelaType()
	{
		return RelaType;
	}
	public void setRelaType(String aRelaType)
	{
		RelaType = aRelaType;
	}
	public String getRIProfitDes()
	{
		return RIProfitDes;
	}
	public void setRIProfitDes(String aRIProfitDes)
	{
		RIProfitDes = aRIProfitDes;
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
	* 使用另外一个 RIProfitDefSchema 对象给 Schema 赋值
	* @param: aRIProfitDefSchema RIProfitDefSchema
	**/
	public void setSchema(RIProfitDefSchema aRIProfitDefSchema)
	{
		this.RIProfitNo = aRIProfitDefSchema.getRIProfitNo();
		this.RIProfitName = aRIProfitDefSchema.getRIProfitName();
		this.ReComCode = aRIProfitDefSchema.getReComCode();
		this.RIContNo = aRIProfitDefSchema.getRIContNo();
		this.RelaType = aRIProfitDefSchema.getRelaType();
		this.RIProfitDes = aRIProfitDefSchema.getRIProfitDes();
		this.ModifyDate = fDate.getDate( aRIProfitDefSchema.getModifyDate());
		this.ModifyTime = aRIProfitDefSchema.getModifyTime();
		this.Operator = aRIProfitDefSchema.getOperator();
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

			if( rs.getString("RIProfitName") == null )
				this.RIProfitName = null;
			else
				this.RIProfitName = rs.getString("RIProfitName").trim();

			if( rs.getString("ReComCode") == null )
				this.ReComCode = null;
			else
				this.ReComCode = rs.getString("ReComCode").trim();

			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("RelaType") == null )
				this.RelaType = null;
			else
				this.RelaType = rs.getString("RelaType").trim();

			if( rs.getString("RIProfitDes") == null )
				this.RIProfitDes = null;
			else
				this.RIProfitDes = rs.getString("RIProfitDes").trim();

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
			System.out.println("数据库中的RIProfitDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIProfitDefSchema getSchema()
	{
		RIProfitDefSchema aRIProfitDefSchema = new RIProfitDefSchema();
		aRIProfitDefSchema.setSchema(this);
		return aRIProfitDefSchema;
	}

	public RIProfitDefDB getDB()
	{
		RIProfitDefDB aDBOper = new RIProfitDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProfitDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RIProfitNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIProfitName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIProfitDes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIProfitDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RIProfitNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIProfitName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ReComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RIProfitDes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIProfitDefSchema";
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
		if (FCode.equalsIgnoreCase("RIProfitName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitName));
		}
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReComCode));
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaType));
		}
		if (FCode.equalsIgnoreCase("RIProfitDes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIProfitDes));
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
				strFieldValue = StrTool.GBKToUnicode(RIProfitName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ReComCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RIProfitDes);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 8:
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
		if (FCode.equalsIgnoreCase("RIProfitName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitName = FValue.trim();
			}
			else
				RIProfitName = null;
		}
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReComCode = FValue.trim();
			}
			else
				ReComCode = null;
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaType = FValue.trim();
			}
			else
				RelaType = null;
		}
		if (FCode.equalsIgnoreCase("RIProfitDes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIProfitDes = FValue.trim();
			}
			else
				RIProfitDes = null;
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
		RIProfitDefSchema other = (RIProfitDefSchema)otherObject;
		return
			RIProfitNo.equals(other.getRIProfitNo())
			&& RIProfitName.equals(other.getRIProfitName())
			&& ReComCode.equals(other.getReComCode())
			&& RIContNo.equals(other.getRIContNo())
			&& RelaType.equals(other.getRelaType())
			&& RIProfitDes.equals(other.getRIProfitDes())
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
		if( strFieldName.equals("RIProfitName") ) {
			return 1;
		}
		if( strFieldName.equals("ReComCode") ) {
			return 2;
		}
		if( strFieldName.equals("RIContNo") ) {
			return 3;
		}
		if( strFieldName.equals("RelaType") ) {
			return 4;
		}
		if( strFieldName.equals("RIProfitDes") ) {
			return 5;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "RIProfitNo";
				break;
			case 1:
				strFieldName = "RIProfitName";
				break;
			case 2:
				strFieldName = "ReComCode";
				break;
			case 3:
				strFieldName = "RIContNo";
				break;
			case 4:
				strFieldName = "RelaType";
				break;
			case 5:
				strFieldName = "RIProfitDes";
				break;
			case 6:
				strFieldName = "ModifyDate";
				break;
			case 7:
				strFieldName = "ModifyTime";
				break;
			case 8:
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
		if( strFieldName.equals("RIProfitName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIProfitDes") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
