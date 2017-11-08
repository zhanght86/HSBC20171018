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
import com.sinosoft.lis.db.YBTEnterLogDB;

/*
 * <p>ClassName: YBTEnterLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务收费
 */
public class YBTEnterLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(YBTEnterLogSchema.class);
	// @Field
	/** 流水号 */
	private String SERIALNO;
	/** 文件名 */
	private String FILENAME;
	/** 到帐日期 */
	private Date ENTERDATE;
	/** 操作标志 */
	private String FLAG;
	/** 描述 */
	private String OPERDESC;
	/** 入机日期 */
	private Date MAKEDATE;
	/** 入机时间 */
	private String MAKETIME;
	/** 管理机构 */
	private String ManageCom;
	/** 银行编码 */
	private String BankCode;
	/** 收费金额 */
	private double FeeSum;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public YBTEnterLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SERIALNO";

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
		YBTEnterLogSchema cloned = (YBTEnterLogSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSERIALNO()
	{
		return SERIALNO;
	}
	public void setSERIALNO(String aSERIALNO)
	{
		SERIALNO = aSERIALNO;
	}
	public String getFILENAME()
	{
		return FILENAME;
	}
	public void setFILENAME(String aFILENAME)
	{
		FILENAME = aFILENAME;
	}
	public String getENTERDATE()
	{
		if( ENTERDATE != null )
			return fDate.getString(ENTERDATE);
		else
			return null;
	}
	public void setENTERDATE(Date aENTERDATE)
	{
		ENTERDATE = aENTERDATE;
	}
	public void setENTERDATE(String aENTERDATE)
	{
		if (aENTERDATE != null && !aENTERDATE.equals("") )
		{
			ENTERDATE = fDate.getDate( aENTERDATE );
		}
		else
			ENTERDATE = null;
	}

	public String getFLAG()
	{
		return FLAG;
	}
	public void setFLAG(String aFLAG)
	{
		FLAG = aFLAG;
	}
	public String getOPERDESC()
	{
		return OPERDESC;
	}
	public void setOPERDESC(String aOPERDESC)
	{
		OPERDESC = aOPERDESC;
	}
	public String getMAKEDATE()
	{
		if( MAKEDATE != null )
			return fDate.getString(MAKEDATE);
		else
			return null;
	}
	public void setMAKEDATE(Date aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	public void setMAKEDATE(String aMAKEDATE)
	{
		if (aMAKEDATE != null && !aMAKEDATE.equals("") )
		{
			MAKEDATE = fDate.getDate( aMAKEDATE );
		}
		else
			MAKEDATE = null;
	}

	public String getMAKETIME()
	{
		return MAKETIME;
	}
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public double getFeeSum()
	{
		return FeeSum;
	}
	public void setFeeSum(double aFeeSum)
	{
		FeeSum = aFeeSum;
	}
	public void setFeeSum(String aFeeSum)
	{
		if (aFeeSum != null && !aFeeSum.equals(""))
		{
			Double tDouble = new Double(aFeeSum);
			double d = tDouble.doubleValue();
			FeeSum = d;
		}
	}


	/**
	* 使用另外一个 YBTEnterLogSchema 对象给 Schema 赋值
	* @param: aYBTEnterLogSchema YBTEnterLogSchema
	**/
	public void setSchema(YBTEnterLogSchema aYBTEnterLogSchema)
	{
		this.SERIALNO = aYBTEnterLogSchema.getSERIALNO();
		this.FILENAME = aYBTEnterLogSchema.getFILENAME();
		this.ENTERDATE = fDate.getDate( aYBTEnterLogSchema.getENTERDATE());
		this.FLAG = aYBTEnterLogSchema.getFLAG();
		this.OPERDESC = aYBTEnterLogSchema.getOPERDESC();
		this.MAKEDATE = fDate.getDate( aYBTEnterLogSchema.getMAKEDATE());
		this.MAKETIME = aYBTEnterLogSchema.getMAKETIME();
		this.ManageCom = aYBTEnterLogSchema.getManageCom();
		this.BankCode = aYBTEnterLogSchema.getBankCode();
		this.FeeSum = aYBTEnterLogSchema.getFeeSum();
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
			if( rs.getString("SERIALNO") == null )
				this.SERIALNO = null;
			else
				this.SERIALNO = rs.getString("SERIALNO").trim();

			if( rs.getString("FILENAME") == null )
				this.FILENAME = null;
			else
				this.FILENAME = rs.getString("FILENAME").trim();

			this.ENTERDATE = rs.getDate("ENTERDATE");
			if( rs.getString("FLAG") == null )
				this.FLAG = null;
			else
				this.FLAG = rs.getString("FLAG").trim();

			if( rs.getString("OPERDESC") == null )
				this.OPERDESC = null;
			else
				this.OPERDESC = rs.getString("OPERDESC").trim();

			this.MAKEDATE = rs.getDate("MAKEDATE");
			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			this.FeeSum = rs.getDouble("FeeSum");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的YBTEnterLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTEnterLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public YBTEnterLogSchema getSchema()
	{
		YBTEnterLogSchema aYBTEnterLogSchema = new YBTEnterLogSchema();
		aYBTEnterLogSchema.setSchema(this);
		return aYBTEnterLogSchema;
	}

	public YBTEnterLogDB getDB()
	{
		YBTEnterLogDB aDBOper = new YBTEnterLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTEnterLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SERIALNO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FILENAME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ENTERDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FLAG)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPERDESC)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MAKEDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MAKETIME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeSum));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTEnterLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SERIALNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FILENAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ENTERDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			FLAG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OPERDESC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			FeeSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "YBTEnterLogSchema";
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
		if (FCode.equalsIgnoreCase("SERIALNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SERIALNO));
		}
		if (FCode.equalsIgnoreCase("FILENAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FILENAME));
		}
		if (FCode.equalsIgnoreCase("ENTERDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getENTERDATE()));
		}
		if (FCode.equalsIgnoreCase("FLAG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FLAG));
		}
		if (FCode.equalsIgnoreCase("OPERDESC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPERDESC));
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("FeeSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeSum));
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
				strFieldValue = StrTool.GBKToUnicode(SERIALNO);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FILENAME);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getENTERDATE()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FLAG);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OPERDESC);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 9:
				strFieldValue = String.valueOf(FeeSum);
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

		if (FCode.equalsIgnoreCase("SERIALNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SERIALNO = FValue.trim();
			}
			else
				SERIALNO = null;
		}
		if (FCode.equalsIgnoreCase("FILENAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FILENAME = FValue.trim();
			}
			else
				FILENAME = null;
		}
		if (FCode.equalsIgnoreCase("ENTERDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ENTERDATE = fDate.getDate( FValue );
			}
			else
				ENTERDATE = null;
		}
		if (FCode.equalsIgnoreCase("FLAG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FLAG = FValue.trim();
			}
			else
				FLAG = null;
		}
		if (FCode.equalsIgnoreCase("OPERDESC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPERDESC = FValue.trim();
			}
			else
				OPERDESC = null;
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MAKEDATE = fDate.getDate( FValue );
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
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
		if (FCode.equalsIgnoreCase("FeeSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeSum = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		YBTEnterLogSchema other = (YBTEnterLogSchema)otherObject;
		return
			SERIALNO.equals(other.getSERIALNO())
			&& FILENAME.equals(other.getFILENAME())
			&& fDate.getString(ENTERDATE).equals(other.getENTERDATE())
			&& FLAG.equals(other.getFLAG())
			&& OPERDESC.equals(other.getOPERDESC())
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& ManageCom.equals(other.getManageCom())
			&& BankCode.equals(other.getBankCode())
			&& FeeSum == other.getFeeSum();
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
		if( strFieldName.equals("SERIALNO") ) {
			return 0;
		}
		if( strFieldName.equals("FILENAME") ) {
			return 1;
		}
		if( strFieldName.equals("ENTERDATE") ) {
			return 2;
		}
		if( strFieldName.equals("FLAG") ) {
			return 3;
		}
		if( strFieldName.equals("OPERDESC") ) {
			return 4;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 5;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 6;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 7;
		}
		if( strFieldName.equals("BankCode") ) {
			return 8;
		}
		if( strFieldName.equals("FeeSum") ) {
			return 9;
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
				strFieldName = "SERIALNO";
				break;
			case 1:
				strFieldName = "FILENAME";
				break;
			case 2:
				strFieldName = "ENTERDATE";
				break;
			case 3:
				strFieldName = "FLAG";
				break;
			case 4:
				strFieldName = "OPERDESC";
				break;
			case 5:
				strFieldName = "MAKEDATE";
				break;
			case 6:
				strFieldName = "MAKETIME";
				break;
			case 7:
				strFieldName = "ManageCom";
				break;
			case 8:
				strFieldName = "BankCode";
				break;
			case 9:
				strFieldName = "FeeSum";
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
		if( strFieldName.equals("SERIALNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FILENAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ENTERDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FLAG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPERDESC") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeSum") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
