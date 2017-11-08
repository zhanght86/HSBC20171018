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
import com.sinosoft.lis.db.LIAccBillDB;

/*
 * <p>ClassName: LIAccBillSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIAccBillSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIAccBillSchema.class);

	// @Field
	/** 业务类型 */
	private String FeeOperationType;
	/** 财务类型 */
	private String FeeFinaType;
	/** 参考项1 */
	private String RefeFlag;
	/** 凭证类型 */
	private String BillType;
	/** 参照文本 */
	private String BillRefe;
	/** 摘要 */
	private String BillAbst;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIAccBillSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "FeeOperationType";
		pk[1] = "FeeFinaType";
		pk[2] = "RefeFlag";

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
		LIAccBillSchema cloned = (LIAccBillSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFeeOperationType()
	{
		return FeeOperationType;
	}
	public void setFeeOperationType(String aFeeOperationType)
	{
		FeeOperationType = aFeeOperationType;
	}
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		FeeFinaType = aFeeFinaType;
	}
	/**
	* JJ：业务<p>
	* D0：财务<p>
	* D1：代收代付
	*/
	public String getRefeFlag()
	{
		return RefeFlag;
	}
	public void setRefeFlag(String aRefeFlag)
	{
		RefeFlag = aRefeFlag;
	}
	public String getBillType()
	{
		return BillType;
	}
	public void setBillType(String aBillType)
	{
		BillType = aBillType;
	}
	public String getBillRefe()
	{
		return BillRefe;
	}
	public void setBillRefe(String aBillRefe)
	{
		BillRefe = aBillRefe;
	}
	public String getBillAbst()
	{
		return BillAbst;
	}
	public void setBillAbst(String aBillAbst)
	{
		BillAbst = aBillAbst;
	}

	/**
	* 使用另外一个 LIAccBillSchema 对象给 Schema 赋值
	* @param: aLIAccBillSchema LIAccBillSchema
	**/
	public void setSchema(LIAccBillSchema aLIAccBillSchema)
	{
		this.FeeOperationType = aLIAccBillSchema.getFeeOperationType();
		this.FeeFinaType = aLIAccBillSchema.getFeeFinaType();
		this.RefeFlag = aLIAccBillSchema.getRefeFlag();
		this.BillType = aLIAccBillSchema.getBillType();
		this.BillRefe = aLIAccBillSchema.getBillRefe();
		this.BillAbst = aLIAccBillSchema.getBillAbst();
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
			if( rs.getString("FeeOperationType") == null )
				this.FeeOperationType = null;
			else
				this.FeeOperationType = rs.getString("FeeOperationType").trim();

			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("RefeFlag") == null )
				this.RefeFlag = null;
			else
				this.RefeFlag = rs.getString("RefeFlag").trim();

			if( rs.getString("BillType") == null )
				this.BillType = null;
			else
				this.BillType = rs.getString("BillType").trim();

			if( rs.getString("BillRefe") == null )
				this.BillRefe = null;
			else
				this.BillRefe = rs.getString("BillRefe").trim();

			if( rs.getString("BillAbst") == null )
				this.BillAbst = null;
			else
				this.BillAbst = rs.getString("BillAbst").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIAccBill表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIAccBillSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIAccBillSchema getSchema()
	{
		LIAccBillSchema aLIAccBillSchema = new LIAccBillSchema();
		aLIAccBillSchema.setSchema(this);
		return aLIAccBillSchema;
	}

	public LIAccBillDB getDB()
	{
		LIAccBillDB aDBOper = new LIAccBillDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIAccBill描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(FeeOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RefeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillRefe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillAbst));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIAccBill>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			FeeOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RefeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BillType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BillRefe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BillAbst = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIAccBillSchema";
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
		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeOperationType));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("RefeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefeFlag));
		}
		if (FCode.equalsIgnoreCase("BillType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillType));
		}
		if (FCode.equalsIgnoreCase("BillRefe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillRefe));
		}
		if (FCode.equalsIgnoreCase("BillAbst"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillAbst));
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
				strFieldValue = StrTool.GBKToUnicode(FeeOperationType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RefeFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BillType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BillRefe);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BillAbst);
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

		if (FCode.equalsIgnoreCase("FeeOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeOperationType = FValue.trim();
			}
			else
				FeeOperationType = null;
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("RefeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RefeFlag = FValue.trim();
			}
			else
				RefeFlag = null;
		}
		if (FCode.equalsIgnoreCase("BillType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillType = FValue.trim();
			}
			else
				BillType = null;
		}
		if (FCode.equalsIgnoreCase("BillRefe"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillRefe = FValue.trim();
			}
			else
				BillRefe = null;
		}
		if (FCode.equalsIgnoreCase("BillAbst"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillAbst = FValue.trim();
			}
			else
				BillAbst = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIAccBillSchema other = (LIAccBillSchema)otherObject;
		return
			FeeOperationType.equals(other.getFeeOperationType())
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& RefeFlag.equals(other.getRefeFlag())
			&& BillType.equals(other.getBillType())
			&& BillRefe.equals(other.getBillRefe())
			&& BillAbst.equals(other.getBillAbst());
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
		if( strFieldName.equals("FeeOperationType") ) {
			return 0;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 1;
		}
		if( strFieldName.equals("RefeFlag") ) {
			return 2;
		}
		if( strFieldName.equals("BillType") ) {
			return 3;
		}
		if( strFieldName.equals("BillRefe") ) {
			return 4;
		}
		if( strFieldName.equals("BillAbst") ) {
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
				strFieldName = "FeeOperationType";
				break;
			case 1:
				strFieldName = "FeeFinaType";
				break;
			case 2:
				strFieldName = "RefeFlag";
				break;
			case 3:
				strFieldName = "BillType";
				break;
			case 4:
				strFieldName = "BillRefe";
				break;
			case 5:
				strFieldName = "BillAbst";
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
		if( strFieldName.equals("FeeOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RefeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillRefe") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillAbst") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
