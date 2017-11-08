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
import com.sinosoft.lis.db.ES_DOCMOVE_CONFIGDB;

/*
 * <p>ClassName: ES_DOCMOVE_CONFIGSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOCMOVE_CONFIGSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOCMOVE_CONFIGSchema.class);

	// @Field
	/** 目标服务器的管理机构 */
	private String ToManageCom;
	/** 批量大小 */
	private int ANumber;
	/** 批量执行周期 */
	private int ACricle;
	/** 每日允许执行开始时间 */
	private String ABeginTime;
	/** 每日允许执行结束时间 */
	private String AEndTime;
	/** 操作机构 */
	private String ManageCom;
	/** 单证录入开始日期 */
	private Date DBeginTime;
	/** 单证录入截止日期 */
	private Date DEndTime;
	/** 扫描批次号 */
	private String ScanNo;
	/** 连续错误数 */
	private int SerialNo;
	/** 总体错误数 */
	private int TFailNo;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOCMOVE_CONFIGSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ToManageCom";
		pk[1] = "ManageCom";

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
		ES_DOCMOVE_CONFIGSchema cloned = (ES_DOCMOVE_CONFIGSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 服务器的详细信息在ES_SERVER_INFO表中配置
	*/
	public String getToManageCom()
	{
		return ToManageCom;
	}
	public void setToManageCom(String aToManageCom)
	{
		ToManageCom = aToManageCom;
	}
	public int getANumber()
	{
		return ANumber;
	}
	public void setANumber(int aANumber)
	{
		ANumber = aANumber;
	}
	public void setANumber(String aANumber)
	{
		if (aANumber != null && !aANumber.equals(""))
		{
			Integer tInteger = new Integer(aANumber);
			int i = tInteger.intValue();
			ANumber = i;
		}
	}

	/**
	* 单位:天
	*/
	public int getACricle()
	{
		return ACricle;
	}
	public void setACricle(int aACricle)
	{
		ACricle = aACricle;
	}
	public void setACricle(String aACricle)
	{
		if (aACricle != null && !aACricle.equals(""))
		{
			Integer tInteger = new Integer(aACricle);
			int i = tInteger.intValue();
			ACricle = i;
		}
	}

	public String getABeginTime()
	{
		return ABeginTime;
	}
	public void setABeginTime(String aABeginTime)
	{
		ABeginTime = aABeginTime;
	}
	public String getAEndTime()
	{
		return AEndTime;
	}
	public void setAEndTime(String aAEndTime)
	{
		AEndTime = aAEndTime;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getDBeginTime()
	{
		if( DBeginTime != null )
			return fDate.getString(DBeginTime);
		else
			return null;
	}
	public void setDBeginTime(Date aDBeginTime)
	{
		DBeginTime = aDBeginTime;
	}
	public void setDBeginTime(String aDBeginTime)
	{
		if (aDBeginTime != null && !aDBeginTime.equals("") )
		{
			DBeginTime = fDate.getDate( aDBeginTime );
		}
		else
			DBeginTime = null;
	}

	public String getDEndTime()
	{
		if( DEndTime != null )
			return fDate.getString(DEndTime);
		else
			return null;
	}
	public void setDEndTime(Date aDEndTime)
	{
		DEndTime = aDEndTime;
	}
	public void setDEndTime(String aDEndTime)
	{
		if (aDEndTime != null && !aDEndTime.equals("") )
		{
			DEndTime = fDate.getDate( aDEndTime );
		}
		else
			DEndTime = null;
	}

	public String getScanNo()
	{
		return ScanNo;
	}
	public void setScanNo(String aScanNo)
	{
		ScanNo = aScanNo;
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

	public int getTFailNo()
	{
		return TFailNo;
	}
	public void setTFailNo(int aTFailNo)
	{
		TFailNo = aTFailNo;
	}
	public void setTFailNo(String aTFailNo)
	{
		if (aTFailNo != null && !aTFailNo.equals(""))
		{
			Integer tInteger = new Integer(aTFailNo);
			int i = tInteger.intValue();
			TFailNo = i;
		}
	}


	/**
	* 使用另外一个 ES_DOCMOVE_CONFIGSchema 对象给 Schema 赋值
	* @param: aES_DOCMOVE_CONFIGSchema ES_DOCMOVE_CONFIGSchema
	**/
	public void setSchema(ES_DOCMOVE_CONFIGSchema aES_DOCMOVE_CONFIGSchema)
	{
		this.ToManageCom = aES_DOCMOVE_CONFIGSchema.getToManageCom();
		this.ANumber = aES_DOCMOVE_CONFIGSchema.getANumber();
		this.ACricle = aES_DOCMOVE_CONFIGSchema.getACricle();
		this.ABeginTime = aES_DOCMOVE_CONFIGSchema.getABeginTime();
		this.AEndTime = aES_DOCMOVE_CONFIGSchema.getAEndTime();
		this.ManageCom = aES_DOCMOVE_CONFIGSchema.getManageCom();
		this.DBeginTime = fDate.getDate( aES_DOCMOVE_CONFIGSchema.getDBeginTime());
		this.DEndTime = fDate.getDate( aES_DOCMOVE_CONFIGSchema.getDEndTime());
		this.ScanNo = aES_DOCMOVE_CONFIGSchema.getScanNo();
		this.SerialNo = aES_DOCMOVE_CONFIGSchema.getSerialNo();
		this.TFailNo = aES_DOCMOVE_CONFIGSchema.getTFailNo();
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
			if( rs.getString("ToManageCom") == null )
				this.ToManageCom = null;
			else
				this.ToManageCom = rs.getString("ToManageCom").trim();

			this.ANumber = rs.getInt("ANumber");
			this.ACricle = rs.getInt("ACricle");
			if( rs.getString("ABeginTime") == null )
				this.ABeginTime = null;
			else
				this.ABeginTime = rs.getString("ABeginTime").trim();

			if( rs.getString("AEndTime") == null )
				this.AEndTime = null;
			else
				this.AEndTime = rs.getString("AEndTime").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.DBeginTime = rs.getDate("DBeginTime");
			this.DEndTime = rs.getDate("DEndTime");
			if( rs.getString("ScanNo") == null )
				this.ScanNo = null;
			else
				this.ScanNo = rs.getString("ScanNo").trim();

			this.SerialNo = rs.getInt("SerialNo");
			this.TFailNo = rs.getInt("TFailNo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOCMOVE_CONFIG表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_CONFIGSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOCMOVE_CONFIGSchema getSchema()
	{
		ES_DOCMOVE_CONFIGSchema aES_DOCMOVE_CONFIGSchema = new ES_DOCMOVE_CONFIGSchema();
		aES_DOCMOVE_CONFIGSchema.setSchema(this);
		return aES_DOCMOVE_CONFIGSchema;
	}

	public ES_DOCMOVE_CONFIGDB getDB()
	{
		ES_DOCMOVE_CONFIGDB aDBOper = new ES_DOCMOVE_CONFIGDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_CONFIG描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ToManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ANumber));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ACricle));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ABeginTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AEndTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DBeginTime ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DEndTime ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ScanNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TFailNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_CONFIG>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ToManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ANumber= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ACricle= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ABeginTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AEndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DBeginTime = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			DEndTime = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			ScanNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			TFailNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_CONFIGSchema";
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
		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToManageCom));
		}
		if (FCode.equalsIgnoreCase("ANumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ANumber));
		}
		if (FCode.equalsIgnoreCase("ACricle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ACricle));
		}
		if (FCode.equalsIgnoreCase("ABeginTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ABeginTime));
		}
		if (FCode.equalsIgnoreCase("AEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AEndTime));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("DBeginTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDBeginTime()));
		}
		if (FCode.equalsIgnoreCase("DEndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDEndTime()));
		}
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ScanNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("TFailNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TFailNo));
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
				strFieldValue = StrTool.GBKToUnicode(ToManageCom);
				break;
			case 1:
				strFieldValue = String.valueOf(ANumber);
				break;
			case 2:
				strFieldValue = String.valueOf(ACricle);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ABeginTime);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AEndTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDBeginTime()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDEndTime()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ScanNo);
				break;
			case 9:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 10:
				strFieldValue = String.valueOf(TFailNo);
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

		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToManageCom = FValue.trim();
			}
			else
				ToManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ANumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ANumber = i;
			}
		}
		if (FCode.equalsIgnoreCase("ACricle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ACricle = i;
			}
		}
		if (FCode.equalsIgnoreCase("ABeginTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ABeginTime = FValue.trim();
			}
			else
				ABeginTime = null;
		}
		if (FCode.equalsIgnoreCase("AEndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AEndTime = FValue.trim();
			}
			else
				AEndTime = null;
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
		if (FCode.equalsIgnoreCase("DBeginTime"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DBeginTime = fDate.getDate( FValue );
			}
			else
				DBeginTime = null;
		}
		if (FCode.equalsIgnoreCase("DEndTime"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DEndTime = fDate.getDate( FValue );
			}
			else
				DEndTime = null;
		}
		if (FCode.equalsIgnoreCase("ScanNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ScanNo = FValue.trim();
			}
			else
				ScanNo = null;
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
		if (FCode.equalsIgnoreCase("TFailNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TFailNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOCMOVE_CONFIGSchema other = (ES_DOCMOVE_CONFIGSchema)otherObject;
		return
			ToManageCom.equals(other.getToManageCom())
			&& ANumber == other.getANumber()
			&& ACricle == other.getACricle()
			&& ABeginTime.equals(other.getABeginTime())
			&& AEndTime.equals(other.getAEndTime())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(DBeginTime).equals(other.getDBeginTime())
			&& fDate.getString(DEndTime).equals(other.getDEndTime())
			&& ScanNo.equals(other.getScanNo())
			&& SerialNo == other.getSerialNo()
			&& TFailNo == other.getTFailNo();
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
		if( strFieldName.equals("ToManageCom") ) {
			return 0;
		}
		if( strFieldName.equals("ANumber") ) {
			return 1;
		}
		if( strFieldName.equals("ACricle") ) {
			return 2;
		}
		if( strFieldName.equals("ABeginTime") ) {
			return 3;
		}
		if( strFieldName.equals("AEndTime") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("DBeginTime") ) {
			return 6;
		}
		if( strFieldName.equals("DEndTime") ) {
			return 7;
		}
		if( strFieldName.equals("ScanNo") ) {
			return 8;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 9;
		}
		if( strFieldName.equals("TFailNo") ) {
			return 10;
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
				strFieldName = "ToManageCom";
				break;
			case 1:
				strFieldName = "ANumber";
				break;
			case 2:
				strFieldName = "ACricle";
				break;
			case 3:
				strFieldName = "ABeginTime";
				break;
			case 4:
				strFieldName = "AEndTime";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "DBeginTime";
				break;
			case 7:
				strFieldName = "DEndTime";
				break;
			case 8:
				strFieldName = "ScanNo";
				break;
			case 9:
				strFieldName = "SerialNo";
				break;
			case 10:
				strFieldName = "TFailNo";
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
		if( strFieldName.equals("ToManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ANumber") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ACricle") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ABeginTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AEndTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DBeginTime") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DEndTime") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ScanNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TFailNo") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
