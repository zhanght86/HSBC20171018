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
import com.sinosoft.lis.db.LDCodeDB;

/*
 * <p>ClassName: LDCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDCodeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDCodeSchema.class);
	// @Field
	/** 编码类型 */
	private String CodeType;
	/** 编码 */
	private String Code;
	/** 编码名称 */
	private String CodeName;
	/** 编码别名 */
	private String CodeAlias;
	/** 机构代码 */
	private String ComCode;
	/** 其它标志 */
	private String OtherSign;
	/** 状态 */
	private String State;
	/** 顺序 */
	private int OrderNo;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CodeType";
		pk[1] = "Code";

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
		LDCodeSchema cloned = (LDCodeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
//		if(aCodeType!=null && aCodeType.length()>20)
//			throw new IllegalArgumentException("编码类型CodeType值"+aCodeType+"的长度"+aCodeType.length()+"大于最大值20");
		CodeType = aCodeType;
	}
	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
//		if(aCode!=null && aCode.length()>20)
//			throw new IllegalArgumentException("编码Code值"+aCode+"的长度"+aCode.length()+"大于最大值20");
		Code = aCode;
	}
	public String getCodeName()
	{
		return CodeName;
	}
	public void setCodeName(String aCodeName)
	{
//		if(aCodeName!=null && aCodeName.length()>270)
//			throw new IllegalArgumentException("编码名称CodeName值"+aCodeName+"的长度"+aCodeName.length()+"大于最大值270");
		CodeName = aCodeName;
	}
	public String getCodeAlias()
	{
		return CodeAlias;
	}
	public void setCodeAlias(String aCodeAlias)
	{
//		if(aCodeAlias!=null && aCodeAlias.length()>270)
//			throw new IllegalArgumentException("编码别名CodeAlias值"+aCodeAlias+"的长度"+aCodeAlias.length()+"大于最大值270");
		CodeAlias = aCodeAlias;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
//		if(aComCode!=null && aComCode.length()>50)
//			throw new IllegalArgumentException("机构代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值50");
		ComCode = aComCode;
	}
	public String getOtherSign()
	{
		return OtherSign;
	}
	public void setOtherSign(String aOtherSign)
	{
//		if(aOtherSign!=null && aOtherSign.length()>10)
//			throw new IllegalArgumentException("其它标志OtherSign值"+aOtherSign+"的长度"+aOtherSign.length()+"大于最大值10");
		OtherSign = aOtherSign;
	}
	/**
	* 0-删除；1-新增；2-生效
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
//		if(aState!=null && aState.length()>1)
//			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
	}
	public int getOrderNo()
	{
		return OrderNo;
	}
	public void setOrderNo(int aOrderNo)
	{
		OrderNo = aOrderNo;
	}
	public void setOrderNo(String aOrderNo)
	{
		if (aOrderNo != null && !aOrderNo.equals(""))
		{
			Integer tInteger = new Integer(aOrderNo);
			int i = tInteger.intValue();
			OrderNo = i;
		}
	}


	/**
	* 使用另外一个 LDCodeSchema 对象给 Schema 赋值
	* @param: aLDCodeSchema LDCodeSchema
	**/
	public void setSchema(LDCodeSchema aLDCodeSchema)
	{
		this.CodeType = aLDCodeSchema.getCodeType();
		this.Code = aLDCodeSchema.getCode();
		this.CodeName = aLDCodeSchema.getCodeName();
		this.CodeAlias = aLDCodeSchema.getCodeAlias();
		this.ComCode = aLDCodeSchema.getComCode();
		this.OtherSign = aLDCodeSchema.getOtherSign();
		this.State = aLDCodeSchema.getState();
		this.OrderNo = aLDCodeSchema.getOrderNo();
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
			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("CodeName") == null )
				this.CodeName = null;
			else
				this.CodeName = rs.getString("CodeName").trim();

			if( rs.getString("CodeAlias") == null )
				this.CodeAlias = null;
			else
				this.CodeAlias = rs.getString("CodeAlias").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OtherSign") == null )
				this.OtherSign = null;
			else
				this.OtherSign = rs.getString("OtherSign").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			this.OrderNo = rs.getInt("OrderNo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDCodeSchema getSchema()
	{
		LDCodeSchema aLDCodeSchema = new LDCodeSchema();
		aLDCodeSchema.setSchema(this);
		return aLDCodeSchema;
	}

	public LDCodeDB getDB()
	{
		LDCodeDB aDBOper = new LDCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeAlias)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherSign)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OrderNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CodeAlias = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OtherSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OrderNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCodeSchema";
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
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeAlias));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherSign));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrderNo));
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
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CodeAlias);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherSign);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 7:
				strFieldValue = String.valueOf(OrderNo);
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

		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName = FValue.trim();
			}
			else
				CodeName = null;
		}
		if (FCode.equalsIgnoreCase("CodeAlias"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeAlias = FValue.trim();
			}
			else
				CodeAlias = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("OtherSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherSign = FValue.trim();
			}
			else
				OtherSign = null;
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
		if (FCode.equalsIgnoreCase("OrderNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OrderNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDCodeSchema other = (LDCodeSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& Code.equals(other.getCode())
			&& CodeName.equals(other.getCodeName())
			&& CodeAlias.equals(other.getCodeAlias())
			&& ComCode.equals(other.getComCode())
			&& OtherSign.equals(other.getOtherSign())
			&& State.equals(other.getState())
			&& OrderNo == other.getOrderNo();
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
		if( strFieldName.equals("CodeType") ) {
			return 0;
		}
		if( strFieldName.equals("Code") ) {
			return 1;
		}
		if( strFieldName.equals("CodeName") ) {
			return 2;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return 3;
		}
		if( strFieldName.equals("ComCode") ) {
			return 4;
		}
		if( strFieldName.equals("OtherSign") ) {
			return 5;
		}
		if( strFieldName.equals("State") ) {
			return 6;
		}
		if( strFieldName.equals("OrderNo") ) {
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
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "Code";
				break;
			case 2:
				strFieldName = "CodeName";
				break;
			case 3:
				strFieldName = "CodeAlias";
				break;
			case 4:
				strFieldName = "ComCode";
				break;
			case 5:
				strFieldName = "OtherSign";
				break;
			case 6:
				strFieldName = "State";
				break;
			case 7:
				strFieldName = "OrderNo";
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
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeAlias") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherSign") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OrderNo") ) {
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
