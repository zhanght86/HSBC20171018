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
import com.sinosoft.lis.db.TAB_PARSE_CONFIGDB;

/*
 * <p>ClassName: TAB_PARSE_CONFIGSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class TAB_PARSE_CONFIGSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(TAB_PARSE_CONFIGSchema.class);

	// @Field
	/** 银保通的标准报文类型id */
	private String STD_REPORT_TYPE;
	/** 解析报文头信息程序 */
	private String PARSE_PROVIDER;
	/** 程序描述 */
	private String PARSE_INFO;
	/** 备用字段 */
	private String bak1;
	/** 备用字段2 */
	private String bak2;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public TAB_PARSE_CONFIGSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "STD_REPORT_TYPE";

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
		TAB_PARSE_CONFIGSchema cloned = (TAB_PARSE_CONFIGSchema)super.clone();
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
	* 银保通的标准报文类型id
	*/
	public String getSTD_REPORT_TYPE()
	{
		return STD_REPORT_TYPE;
	}
	public void setSTD_REPORT_TYPE(String aSTD_REPORT_TYPE)
	{
		STD_REPORT_TYPE = aSTD_REPORT_TYPE;
	}
	/**
	* 解析报文头信息程序
	*/
	public String getPARSE_PROVIDER()
	{
		return PARSE_PROVIDER;
	}
	public void setPARSE_PROVIDER(String aPARSE_PROVIDER)
	{
		PARSE_PROVIDER = aPARSE_PROVIDER;
	}
	/**
	* 程序描述
	*/
	public String getPARSE_INFO()
	{
		return PARSE_INFO;
	}
	public void setPARSE_INFO(String aPARSE_INFO)
	{
		PARSE_INFO = aPARSE_INFO;
	}
	public String getbak1()
	{
		return bak1;
	}
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	public String getbak2()
	{
		return bak2;
	}
	public void setbak2(String abak2)
	{
		bak2 = abak2;
	}
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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

	/**
	* 使用另外一个 TAB_PARSE_CONFIGSchema 对象给 Schema 赋值
	* @param: aTAB_PARSE_CONFIGSchema TAB_PARSE_CONFIGSchema
	**/
	public void setSchema(TAB_PARSE_CONFIGSchema aTAB_PARSE_CONFIGSchema)
	{
		this.STD_REPORT_TYPE = aTAB_PARSE_CONFIGSchema.getSTD_REPORT_TYPE();
		this.PARSE_PROVIDER = aTAB_PARSE_CONFIGSchema.getPARSE_PROVIDER();
		this.PARSE_INFO = aTAB_PARSE_CONFIGSchema.getPARSE_INFO();
		this.bak1 = aTAB_PARSE_CONFIGSchema.getbak1();
		this.bak2 = aTAB_PARSE_CONFIGSchema.getbak2();
		this.MakeDate = fDate.getDate( aTAB_PARSE_CONFIGSchema.getMakeDate());
		this.MakeTime = aTAB_PARSE_CONFIGSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aTAB_PARSE_CONFIGSchema.getModifyDate());
		this.ModifyTime = aTAB_PARSE_CONFIGSchema.getModifyTime();
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
			if( rs.getString("STD_REPORT_TYPE") == null )
				this.STD_REPORT_TYPE = null;
			else
				this.STD_REPORT_TYPE = rs.getString("STD_REPORT_TYPE").trim();

			if( rs.getString("PARSE_PROVIDER") == null )
				this.PARSE_PROVIDER = null;
			else
				this.PARSE_PROVIDER = rs.getString("PARSE_PROVIDER").trim();

			if( rs.getString("PARSE_INFO") == null )
				this.PARSE_INFO = null;
			else
				this.PARSE_INFO = rs.getString("PARSE_INFO").trim();

			if( rs.getString("bak1") == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString("bak1").trim();

			if( rs.getString("bak2") == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString("bak2").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的TAB_PARSE_CONFIG表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TAB_PARSE_CONFIGSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public TAB_PARSE_CONFIGSchema getSchema()
	{
		TAB_PARSE_CONFIGSchema aTAB_PARSE_CONFIGSchema = new TAB_PARSE_CONFIGSchema();
		aTAB_PARSE_CONFIGSchema.setSchema(this);
		return aTAB_PARSE_CONFIGSchema;
	}

	public TAB_PARSE_CONFIGDB getDB()
	{
		TAB_PARSE_CONFIGDB aDBOper = new TAB_PARSE_CONFIGDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTAB_PARSE_CONFIG描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(STD_REPORT_TYPE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PARSE_PROVIDER)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PARSE_INFO)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bak1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(bak2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTAB_PARSE_CONFIG>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			STD_REPORT_TYPE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PARSE_PROVIDER = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PARSE_INFO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TAB_PARSE_CONFIGSchema";
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
		if (FCode.equalsIgnoreCase("STD_REPORT_TYPE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STD_REPORT_TYPE));
		}
		if (FCode.equalsIgnoreCase("PARSE_PROVIDER"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PARSE_PROVIDER));
		}
		if (FCode.equalsIgnoreCase("PARSE_INFO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PARSE_INFO));
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak1));
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak2));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
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
				strFieldValue = StrTool.GBKToUnicode(STD_REPORT_TYPE);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PARSE_PROVIDER);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PARSE_INFO);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(bak2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("STD_REPORT_TYPE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				STD_REPORT_TYPE = FValue.trim();
			}
			else
				STD_REPORT_TYPE = null;
		}
		if (FCode.equalsIgnoreCase("PARSE_PROVIDER"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PARSE_PROVIDER = FValue.trim();
			}
			else
				PARSE_PROVIDER = null;
		}
		if (FCode.equalsIgnoreCase("PARSE_INFO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PARSE_INFO = FValue.trim();
			}
			else
				PARSE_INFO = null;
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak1 = FValue.trim();
			}
			else
				bak1 = null;
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak2 = FValue.trim();
			}
			else
				bak2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		TAB_PARSE_CONFIGSchema other = (TAB_PARSE_CONFIGSchema)otherObject;
		return
			STD_REPORT_TYPE.equals(other.getSTD_REPORT_TYPE())
			&& PARSE_PROVIDER.equals(other.getPARSE_PROVIDER())
			&& PARSE_INFO.equals(other.getPARSE_INFO())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("STD_REPORT_TYPE") ) {
			return 0;
		}
		if( strFieldName.equals("PARSE_PROVIDER") ) {
			return 1;
		}
		if( strFieldName.equals("PARSE_INFO") ) {
			return 2;
		}
		if( strFieldName.equals("bak1") ) {
			return 3;
		}
		if( strFieldName.equals("bak2") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "STD_REPORT_TYPE";
				break;
			case 1:
				strFieldName = "PARSE_PROVIDER";
				break;
			case 2:
				strFieldName = "PARSE_INFO";
				break;
			case 3:
				strFieldName = "bak1";
				break;
			case 4:
				strFieldName = "bak2";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "ModifyDate";
				break;
			case 8:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("STD_REPORT_TYPE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PARSE_PROVIDER") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PARSE_INFO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
