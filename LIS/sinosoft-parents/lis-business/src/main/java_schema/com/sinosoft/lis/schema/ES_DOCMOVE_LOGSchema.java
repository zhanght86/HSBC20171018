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
import com.sinosoft.lis.db.ES_DOCMOVE_LOGDB;

/*
 * <p>ClassName: ES_DOCMOVE_LOGSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: EasyScanV4
 */
public class ES_DOCMOVE_LOGSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_DOCMOVE_LOGSchema.class);

	// @Field
	/** Docid */
	private int DocID;
	/** 成功标志 */
	private String Flag;
	/** 最后一次迁移时间 */
	private String LastTime;
	/** 最后一次迁移日期 */
	private Date LastDate;
	/** 最后成功传送批次号 */
	private String MoveID;
	/** 管理机构 */
	private String ManageCom;
	/** 目标机构 */
	private String ToManageCom;
	/** Filepath */
	private String FilePath;
	/** Filedate */
	private Date FileDate;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_DOCMOVE_LOGSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "DocID";
		pk[1] = "MoveID";
		pk[2] = "ManageCom";
		pk[3] = "ToManageCom";

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
		ES_DOCMOVE_LOGSchema cloned = (ES_DOCMOVE_LOGSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getDocID()
	{
		return DocID;
	}
	public void setDocID(int aDocID)
	{
		DocID = aDocID;
	}
	public void setDocID(String aDocID)
	{
		if (aDocID != null && !aDocID.equals(""))
		{
			Integer tInteger = new Integer(aDocID);
			int i = tInteger.intValue();
			DocID = i;
		}
	}

	/**
	* 0-单证转移成功<p>
	* 1-单证转移失败
	*/
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
	}
	public String getLastTime()
	{
		return LastTime;
	}
	public void setLastTime(String aLastTime)
	{
		LastTime = aLastTime;
	}
	public String getLastDate()
	{
		if( LastDate != null )
			return fDate.getString(LastDate);
		else
			return null;
	}
	public void setLastDate(Date aLastDate)
	{
		LastDate = aLastDate;
	}
	public void setLastDate(String aLastDate)
	{
		if (aLastDate != null && !aLastDate.equals("") )
		{
			LastDate = fDate.getDate( aLastDate );
		}
		else
			LastDate = null;
	}

	public String getMoveID()
	{
		return MoveID;
	}
	public void setMoveID(String aMoveID)
	{
		MoveID = aMoveID;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getToManageCom()
	{
		return ToManageCom;
	}
	public void setToManageCom(String aToManageCom)
	{
		ToManageCom = aToManageCom;
	}
	public String getFilePath()
	{
		return FilePath;
	}
	public void setFilePath(String aFilePath)
	{
		FilePath = aFilePath;
	}
	public String getFileDate()
	{
		if( FileDate != null )
			return fDate.getString(FileDate);
		else
			return null;
	}
	public void setFileDate(Date aFileDate)
	{
		FileDate = aFileDate;
	}
	public void setFileDate(String aFileDate)
	{
		if (aFileDate != null && !aFileDate.equals("") )
		{
			FileDate = fDate.getDate( aFileDate );
		}
		else
			FileDate = null;
	}


	/**
	* 使用另外一个 ES_DOCMOVE_LOGSchema 对象给 Schema 赋值
	* @param: aES_DOCMOVE_LOGSchema ES_DOCMOVE_LOGSchema
	**/
	public void setSchema(ES_DOCMOVE_LOGSchema aES_DOCMOVE_LOGSchema)
	{
		this.DocID = aES_DOCMOVE_LOGSchema.getDocID();
		this.Flag = aES_DOCMOVE_LOGSchema.getFlag();
		this.LastTime = aES_DOCMOVE_LOGSchema.getLastTime();
		this.LastDate = fDate.getDate( aES_DOCMOVE_LOGSchema.getLastDate());
		this.MoveID = aES_DOCMOVE_LOGSchema.getMoveID();
		this.ManageCom = aES_DOCMOVE_LOGSchema.getManageCom();
		this.ToManageCom = aES_DOCMOVE_LOGSchema.getToManageCom();
		this.FilePath = aES_DOCMOVE_LOGSchema.getFilePath();
		this.FileDate = fDate.getDate( aES_DOCMOVE_LOGSchema.getFileDate());
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
			this.DocID = rs.getInt("DocID");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			if( rs.getString("LastTime") == null )
				this.LastTime = null;
			else
				this.LastTime = rs.getString("LastTime").trim();

			this.LastDate = rs.getDate("LastDate");
			if( rs.getString("MoveID") == null )
				this.MoveID = null;
			else
				this.MoveID = rs.getString("MoveID").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ToManageCom") == null )
				this.ToManageCom = null;
			else
				this.ToManageCom = rs.getString("ToManageCom").trim();

			if( rs.getString("FilePath") == null )
				this.FilePath = null;
			else
				this.FilePath = rs.getString("FilePath").trim();

			this.FileDate = rs.getDate("FileDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_DOCMOVE_LOG表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_LOGSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_DOCMOVE_LOGSchema getSchema()
	{
		ES_DOCMOVE_LOGSchema aES_DOCMOVE_LOGSchema = new ES_DOCMOVE_LOGSchema();
		aES_DOCMOVE_LOGSchema.setSchema(this);
		return aES_DOCMOVE_LOGSchema;
	}

	public ES_DOCMOVE_LOGDB getDB()
	{
		ES_DOCMOVE_LOGDB aDBOper = new ES_DOCMOVE_LOGDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_LOG描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(DocID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LastDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MoveID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FilePath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FileDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_DOCMOVE_LOG>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DocID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			LastTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LastDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			MoveID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ToManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FilePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FileDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_DOCMOVE_LOGSchema";
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
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("LastTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastTime));
		}
		if (FCode.equalsIgnoreCase("LastDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLastDate()));
		}
		if (FCode.equalsIgnoreCase("MoveID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MoveID));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToManageCom));
		}
		if (FCode.equalsIgnoreCase("FilePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FilePath));
		}
		if (FCode.equalsIgnoreCase("FileDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFileDate()));
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
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(LastTime);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLastDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MoveID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ToManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FilePath);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFileDate()));
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
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				DocID = i;
			}
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
		}
		if (FCode.equalsIgnoreCase("LastTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastTime = FValue.trim();
			}
			else
				LastTime = null;
		}
		if (FCode.equalsIgnoreCase("LastDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LastDate = fDate.getDate( FValue );
			}
			else
				LastDate = null;
		}
		if (FCode.equalsIgnoreCase("MoveID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MoveID = FValue.trim();
			}
			else
				MoveID = null;
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
		if (FCode.equalsIgnoreCase("ToManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToManageCom = FValue.trim();
			}
			else
				ToManageCom = null;
		}
		if (FCode.equalsIgnoreCase("FilePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FilePath = FValue.trim();
			}
			else
				FilePath = null;
		}
		if (FCode.equalsIgnoreCase("FileDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FileDate = fDate.getDate( FValue );
			}
			else
				FileDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_DOCMOVE_LOGSchema other = (ES_DOCMOVE_LOGSchema)otherObject;
		return
			DocID == other.getDocID()
			&& Flag.equals(other.getFlag())
			&& LastTime.equals(other.getLastTime())
			&& fDate.getString(LastDate).equals(other.getLastDate())
			&& MoveID.equals(other.getMoveID())
			&& ManageCom.equals(other.getManageCom())
			&& ToManageCom.equals(other.getToManageCom())
			&& FilePath.equals(other.getFilePath())
			&& fDate.getString(FileDate).equals(other.getFileDate());
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
		if( strFieldName.equals("Flag") ) {
			return 1;
		}
		if( strFieldName.equals("LastTime") ) {
			return 2;
		}
		if( strFieldName.equals("LastDate") ) {
			return 3;
		}
		if( strFieldName.equals("MoveID") ) {
			return 4;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 5;
		}
		if( strFieldName.equals("ToManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("FilePath") ) {
			return 7;
		}
		if( strFieldName.equals("FileDate") ) {
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
				strFieldName = "DocID";
				break;
			case 1:
				strFieldName = "Flag";
				break;
			case 2:
				strFieldName = "LastTime";
				break;
			case 3:
				strFieldName = "LastDate";
				break;
			case 4:
				strFieldName = "MoveID";
				break;
			case 5:
				strFieldName = "ManageCom";
				break;
			case 6:
				strFieldName = "ToManageCom";
				break;
			case 7:
				strFieldName = "FilePath";
				break;
			case 8:
				strFieldName = "FileDate";
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
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MoveID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FilePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
