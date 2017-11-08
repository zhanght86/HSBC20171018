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
import com.sinosoft.lis.db.PD_LMAccFundDB;

/*
 * <p>ClassName: PD_LMAccFundSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class PD_LMAccFundSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMAccFundSchema.class);

	// @Field
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 基金代码 */
	private String FundNo;
	/** 基金简称 */
	private String FundName;
	/** 销售状态 */
	private String SaleState;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMAccFundSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "InsuAccNo";
		pk[1] = "FundNo";

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
		PD_LMAccFundSchema cloned = (PD_LMAccFundSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 账户号码的定义规则： 6位长度，其他不限制。 从000001开始往后排。
	*/
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	public String getFundNo()
	{
		return FundNo;
	}
	public void setFundNo(String aFundNo)
	{
		FundNo = aFundNo;
	}
	public String getFundName()
	{
		return FundName;
	}
	public void setFundName(String aFundName)
	{
		FundName = aFundName;
	}
	/**
	* 1-有效 2-暂停 3-终止
	*/
	public String getSaleState()
	{
		return SaleState;
	}
	public void setSaleState(String aSaleState)
	{
		SaleState = aSaleState;
	}

	/**
	* 使用另外一个 PD_LMAccFundSchema 对象给 Schema 赋值
	* @param: aPD_LMAccFundSchema PD_LMAccFundSchema
	**/
	public void setSchema(PD_LMAccFundSchema aPD_LMAccFundSchema)
	{
		this.InsuAccNo = aPD_LMAccFundSchema.getInsuAccNo();
		this.FundNo = aPD_LMAccFundSchema.getFundNo();
		this.FundName = aPD_LMAccFundSchema.getFundName();
		this.SaleState = aPD_LMAccFundSchema.getSaleState();
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
			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("FundNo") == null )
				this.FundNo = null;
			else
				this.FundNo = rs.getString("FundNo").trim();

			if( rs.getString("FundName") == null )
				this.FundName = null;
			else
				this.FundName = rs.getString("FundName").trim();

			if( rs.getString("SaleState") == null )
				this.SaleState = null;
			else
				this.SaleState = rs.getString("SaleState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LMAccFund表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMAccFundSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMAccFundSchema getSchema()
	{
		PD_LMAccFundSchema aPD_LMAccFundSchema = new PD_LMAccFundSchema();
		aPD_LMAccFundSchema.setSchema(this);
		return aPD_LMAccFundSchema;
	}

	public PD_LMAccFundDB getDB()
	{
		PD_LMAccFundDB aDBOper = new PD_LMAccFundDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMAccFund描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FundName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMAccFund>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FundNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FundName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SaleState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMAccFundSchema";
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
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("FundNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundNo));
		}
		if (FCode.equalsIgnoreCase("FundName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FundName));
		}
		if (FCode.equalsIgnoreCase("SaleState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleState));
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
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FundNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FundName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SaleState);
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

		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("FundNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundNo = FValue.trim();
			}
			else
				FundNo = null;
		}
		if (FCode.equalsIgnoreCase("FundName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FundName = FValue.trim();
			}
			else
				FundName = null;
		}
		if (FCode.equalsIgnoreCase("SaleState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleState = FValue.trim();
			}
			else
				SaleState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMAccFundSchema other = (PD_LMAccFundSchema)otherObject;
		return
			InsuAccNo.equals(other.getInsuAccNo())
			&& FundNo.equals(other.getFundNo())
			&& FundName.equals(other.getFundName())
			&& SaleState.equals(other.getSaleState());
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
		if( strFieldName.equals("InsuAccNo") ) {
			return 0;
		}
		if( strFieldName.equals("FundNo") ) {
			return 1;
		}
		if( strFieldName.equals("FundName") ) {
			return 2;
		}
		if( strFieldName.equals("SaleState") ) {
			return 3;
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
				strFieldName = "InsuAccNo";
				break;
			case 1:
				strFieldName = "FundNo";
				break;
			case 2:
				strFieldName = "FundName";
				break;
			case 3:
				strFieldName = "SaleState";
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
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FundName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleState") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
