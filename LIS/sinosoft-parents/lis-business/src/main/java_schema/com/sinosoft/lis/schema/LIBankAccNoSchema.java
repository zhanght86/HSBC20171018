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
import com.sinosoft.lis.db.LIBankAccNoDB;

/*
 * <p>ClassName: LIBankAccNoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIBankAccNoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIBankAccNoSchema.class);

	// @Field
	/** 银行帐号 */
	private String BankAccNo;
	/** 会计科目 */
	private String AccItem;
	/** 收付费类型 */
	private String AccType;
	/** 机构代码 */
	private String ComCode;
	/** 银行代码 */
	private String BankCode;
	/** 银行名 */
	private String BankName;
	/** 所属公司 */
	private String UpComName;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIBankAccNoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BankAccNo";
		pk[1] = "AccType";
		pk[2] = "ComCode";

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
		LIBankAccNoSchema cloned = (LIBankAccNoSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 1.公司代码<p>
	* 2.成本中心<p>
	* 3.代理机构<p>
	* 4.货币类型<p>
	* 5.分保公司<p>
	* 6.销售渠道<p>
	* 7.险种分类1<p>
	* 8.险种分类2
	*/
	public String getBankAccNo()
	{
		return BankAccNo;
	}
	public void setBankAccNo(String aBankAccNo)
	{
		BankAccNo = aBankAccNo;
	}
	public String getAccItem()
	{
		return AccItem;
	}
	public void setAccItem(String aAccItem)
	{
		AccItem = aAccItem;
	}
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/**
	* 求险种性质1时:<p>
	* memo1:性质代码H(健康),L(寿险),A(意外)
	*/
	public String getBankName()
	{
		return BankName;
	}
	public void setBankName(String aBankName)
	{
		BankName = aBankName;
	}
	/**
	* 求险种性质1时:<p>
	* memo2:指代码memo1的含义
	*/
	public String getUpComName()
	{
		return UpComName;
	}
	public void setUpComName(String aUpComName)
	{
		UpComName = aUpComName;
	}

	/**
	* 使用另外一个 LIBankAccNoSchema 对象给 Schema 赋值
	* @param: aLIBankAccNoSchema LIBankAccNoSchema
	**/
	public void setSchema(LIBankAccNoSchema aLIBankAccNoSchema)
	{
		this.BankAccNo = aLIBankAccNoSchema.getBankAccNo();
		this.AccItem = aLIBankAccNoSchema.getAccItem();
		this.AccType = aLIBankAccNoSchema.getAccType();
		this.ComCode = aLIBankAccNoSchema.getComCode();
		this.BankCode = aLIBankAccNoSchema.getBankCode();
		this.BankName = aLIBankAccNoSchema.getBankName();
		this.UpComName = aLIBankAccNoSchema.getUpComName();
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
			if( rs.getString("BankAccNo") == null )
				this.BankAccNo = null;
			else
				this.BankAccNo = rs.getString("BankAccNo").trim();

			if( rs.getString("AccItem") == null )
				this.AccItem = null;
			else
				this.AccItem = rs.getString("AccItem").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankName") == null )
				this.BankName = null;
			else
				this.BankName = rs.getString("BankName").trim();

			if( rs.getString("UpComName") == null )
				this.UpComName = null;
			else
				this.UpComName = rs.getString("UpComName").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIBankAccNo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIBankAccNoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIBankAccNoSchema getSchema()
	{
		LIBankAccNoSchema aLIBankAccNoSchema = new LIBankAccNoSchema();
		aLIBankAccNoSchema.setSchema(this);
		return aLIBankAccNoSchema;
	}

	public LIBankAccNoDB getDB()
	{
		LIBankAccNoDB aDBOper = new LIBankAccNoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIBankAccNo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpComName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIBankAccNo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BankName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UpComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIBankAccNoSchema";
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
		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccNo));
		}
		if (FCode.equalsIgnoreCase("AccItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccItem));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankName));
		}
		if (FCode.equalsIgnoreCase("UpComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpComName));
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
				strFieldValue = StrTool.GBKToUnicode(BankAccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccItem);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BankName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UpComName);
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

		if (FCode.equalsIgnoreCase("BankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccNo = FValue.trim();
			}
			else
				BankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("AccItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccItem = FValue.trim();
			}
			else
				AccItem = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankName = FValue.trim();
			}
			else
				BankName = null;
		}
		if (FCode.equalsIgnoreCase("UpComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpComName = FValue.trim();
			}
			else
				UpComName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIBankAccNoSchema other = (LIBankAccNoSchema)otherObject;
		return
			BankAccNo.equals(other.getBankAccNo())
			&& AccItem.equals(other.getAccItem())
			&& AccType.equals(other.getAccType())
			&& ComCode.equals(other.getComCode())
			&& BankCode.equals(other.getBankCode())
			&& BankName.equals(other.getBankName())
			&& UpComName.equals(other.getUpComName());
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
		if( strFieldName.equals("BankAccNo") ) {
			return 0;
		}
		if( strFieldName.equals("AccItem") ) {
			return 1;
		}
		if( strFieldName.equals("AccType") ) {
			return 2;
		}
		if( strFieldName.equals("ComCode") ) {
			return 3;
		}
		if( strFieldName.equals("BankCode") ) {
			return 4;
		}
		if( strFieldName.equals("BankName") ) {
			return 5;
		}
		if( strFieldName.equals("UpComName") ) {
			return 6;
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
				strFieldName = "BankAccNo";
				break;
			case 1:
				strFieldName = "AccItem";
				break;
			case 2:
				strFieldName = "AccType";
				break;
			case 3:
				strFieldName = "ComCode";
				break;
			case 4:
				strFieldName = "BankCode";
				break;
			case 5:
				strFieldName = "BankName";
				break;
			case 6:
				strFieldName = "UpComName";
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
		if( strFieldName.equals("BankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpComName") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
