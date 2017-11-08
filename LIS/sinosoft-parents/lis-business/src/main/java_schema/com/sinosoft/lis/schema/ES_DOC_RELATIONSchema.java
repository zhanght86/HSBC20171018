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
import com.sinosoft.lis.db.ES_DOC_RELATIONDB;

/*
 * <p>ClassName: ES_DOC_RELATIONSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOC_RELATIONSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOC_RELATIONSchema.class);

	// @Field
	/** 单证编号 */
	private double DocID;
	/** 业务号码类型 */
	private String BussNoType;
	/** 业务号码 */
	private String BussNo;
	/** 单证号码 */
	private String DocCode;
	/** 业务类型 */
	private String BussType;
	/** 单证细类 */
	private String SubType;
	/** 关联状态 */
	private String RelaFlag;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOC_RELATIONSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "DocID";
		pk[1] = "BussNoType";
		pk[2] = "BussNo";

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
		ES_DOC_RELATIONSchema cloned = (ES_DOC_RELATIONSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 唯一标示一份单证的编号，系统生成
	*/
	public double getDocID()
	{
		return DocID;
	}
	public void setDocID(double aDocID)
	{
		DocID = aDocID;
	}
	public void setDocID(String aDocID)
	{
		if (aDocID != null && !aDocID.equals(""))
		{
			Double tDouble = new Double(aDocID);
			double d = tDouble.doubleValue();
			DocID = d;
		}
	}

	/**
	* 11 投保单印刷号PrtNo<p>
	* 12 团单号码GrpContNo<p>
	* 13 保单号码ContNo<p>
	* 14 保单险种号PolNo<p>
	* <p>
	* 21 理赔申请号<p>
	* 22 理赔立案号RgtNo<p>
	* 23 理赔分案号<p>
	* <p>
	* 31 保全申请号<p>
	* 32 保全批单号（分保单）<p>
	* <p>
	* 41 被保险人客户号码<p>
	* 42 投保人客户号码<p>
	* <p>
	* 91 工单号码<p>
	* <p>
	* 各种通知书的流水号
	*/
	public String getBussNoType()
	{
		return BussNoType;
	}
	public void setBussNoType(String aBussNoType)
	{
		BussNoType = aBussNoType;
	}
	/**
	* 各业务号码类型对应的号码
	*/
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		BussNo = aBussNo;
	}
	/**
	* 一份单证的号码，如投保单印刷号等
	*/
	public String getDocCode()
	{
		return DocCode;
	}
	public void setDocCode(String aDocCode)
	{
		DocCode = aDocCode;
	}
	/**
	* 承保<p>
	* 理赔<p>
	* 保全<p>
	* 未清分
	*/
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		BussType = aBussType;
	}
	/**
	* 投保单<p>
	* 体检资料<p>
	* 生调资料<p>
	* ......
	*/
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	/**
	* 0 正常状态<p>
	* 1 失效状态  如：拒保客户号关联等
	*/
	public String getRelaFlag()
	{
		return RelaFlag;
	}
	public void setRelaFlag(String aRelaFlag)
	{
		RelaFlag = aRelaFlag;
	}

	/**
	* 使用另外一个 ES_DOC_RELATIONSchema 对象给 Schema 赋值
	* @param: aES_DOC_RELATIONSchema ES_DOC_RELATIONSchema
	**/
	public void setSchema(ES_DOC_RELATIONSchema aES_DOC_RELATIONSchema)
	{
		this.DocID = aES_DOC_RELATIONSchema.getDocID();
		this.BussNoType = aES_DOC_RELATIONSchema.getBussNoType();
		this.BussNo = aES_DOC_RELATIONSchema.getBussNo();
		this.DocCode = aES_DOC_RELATIONSchema.getDocCode();
		this.BussType = aES_DOC_RELATIONSchema.getBussType();
		this.SubType = aES_DOC_RELATIONSchema.getSubType();
		this.RelaFlag = aES_DOC_RELATIONSchema.getRelaFlag();
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
			this.DocID = rs.getDouble("DocID");
			if( rs.getString("BussNoType") == null )
				this.BussNoType = null;
			else
				this.BussNoType = rs.getString("BussNoType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("DocCode") == null )
				this.DocCode = null;
			else
				this.DocCode = rs.getString("DocCode").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("RelaFlag") == null )
				this.RelaFlag = null;
			else
				this.RelaFlag = rs.getString("RelaFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOC_RELATION表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_RELATIONSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOC_RELATIONSchema getSchema()
	{
		ES_DOC_RELATIONSchema aES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
		aES_DOC_RELATIONSchema.setSchema(this);
		return aES_DOC_RELATIONSchema;
	}

	public ES_DOC_RELATIONDB getDB()
	{
		ES_DOC_RELATIONDB aDBOper = new ES_DOC_RELATIONDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_RELATION描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(DocID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DocCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOC_RELATION>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DocID = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).doubleValue();
			BussNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DocCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RelaFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOC_RELATIONSchema";
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
		if (FCode.equalsIgnoreCase("DocID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocID));
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNoType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("DocCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DocCode));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("RelaFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaFlag));
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
				strFieldValue = String.valueOf(DocID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BussNoType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DocCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RelaFlag);
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

		if (FCode.equalsIgnoreCase("DocID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DocID = d;
			}
		}
		if (FCode.equalsIgnoreCase("BussNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNoType = FValue.trim();
			}
			else
				BussNoType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("DocCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DocCode = FValue.trim();
			}
			else
				DocCode = null;
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("RelaFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaFlag = FValue.trim();
			}
			else
				RelaFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOC_RELATIONSchema other = (ES_DOC_RELATIONSchema)otherObject;
		return
			DocID == other.getDocID()
			&& BussNoType.equals(other.getBussNoType())
			&& BussNo.equals(other.getBussNo())
			&& DocCode.equals(other.getDocCode())
			&& BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& RelaFlag.equals(other.getRelaFlag());
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
		if( strFieldName.equals("DocID") ) {
			return 0;
		}
		if( strFieldName.equals("BussNoType") ) {
			return 1;
		}
		if( strFieldName.equals("BussNo") ) {
			return 2;
		}
		if( strFieldName.equals("DocCode") ) {
			return 3;
		}
		if( strFieldName.equals("BussType") ) {
			return 4;
		}
		if( strFieldName.equals("SubType") ) {
			return 5;
		}
		if( strFieldName.equals("RelaFlag") ) {
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
				strFieldName = "DocID";
				break;
			case 1:
				strFieldName = "BussNoType";
				break;
			case 2:
				strFieldName = "BussNo";
				break;
			case 3:
				strFieldName = "DocCode";
				break;
			case 4:
				strFieldName = "BussType";
				break;
			case 5:
				strFieldName = "SubType";
				break;
			case 6:
				strFieldName = "RelaFlag";
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
		if( strFieldName.equals("DocID") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BussNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DocCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaFlag") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
