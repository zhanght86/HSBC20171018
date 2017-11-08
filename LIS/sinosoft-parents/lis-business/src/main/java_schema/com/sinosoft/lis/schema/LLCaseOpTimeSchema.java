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
import com.sinosoft.lis.db.LLCaseOpTimeDB;

/*
 * <p>ClassName: LLCaseOpTimeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseOpTimeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseOpTimeSchema.class);
	// @Field
	/** 记录号 */
	private String SerialNo;
	/** 赔案号 */
	private String CliamNo;
	/** 赔案阶段 */
	private String CliamPhase;
	/** 页面编码 */
	private String PageNo;
	/** 进入日期 */
	private Date InDate;
	/** 进入时间 */
	private String InTime;
	/** 进入操作员 */
	private String InOperator;
	/** 退出日期 */
	private Date OutDate;
	/** 退出时间 */
	private String OutTime;
	/** 退出操作员 */
	private String OutOperator;
	/** 管理机构 */
	private String MngCom;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseOpTimeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LLCaseOpTimeSchema cloned = (LLCaseOpTimeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>40)
			throw new IllegalArgumentException("记录号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值40");
		SerialNo = aSerialNo;
	}
	public String getCliamNo()
	{
		return CliamNo;
	}
	public void setCliamNo(String aCliamNo)
	{
		if(aCliamNo!=null && aCliamNo.length()>20)
			throw new IllegalArgumentException("赔案号CliamNo值"+aCliamNo+"的长度"+aCliamNo.length()+"大于最大值20");
		CliamNo = aCliamNo;
	}
	public String getCliamPhase()
	{
		return CliamPhase;
	}
	public void setCliamPhase(String aCliamPhase)
	{
		if(aCliamPhase!=null && aCliamPhase.length()>6)
			throw new IllegalArgumentException("赔案阶段CliamPhase值"+aCliamPhase+"的长度"+aCliamPhase.length()+"大于最大值6");
		CliamPhase = aCliamPhase;
	}
	public String getPageNo()
	{
		return PageNo;
	}
	public void setPageNo(String aPageNo)
	{
		if(aPageNo!=null && aPageNo.length()>20)
			throw new IllegalArgumentException("页面编码PageNo值"+aPageNo+"的长度"+aPageNo.length()+"大于最大值20");
		PageNo = aPageNo;
	}
	public String getInDate()
	{
		if( InDate != null )
			return fDate.getString(InDate);
		else
			return null;
	}
	public void setInDate(Date aInDate)
	{
		InDate = aInDate;
	}
	public void setInDate(String aInDate)
	{
		if (aInDate != null && !aInDate.equals("") )
		{
			InDate = fDate.getDate( aInDate );
		}
		else
			InDate = null;
	}

	public String getInTime()
	{
		return InTime;
	}
	public void setInTime(String aInTime)
	{
		if(aInTime!=null && aInTime.length()>8)
			throw new IllegalArgumentException("进入时间InTime值"+aInTime+"的长度"+aInTime.length()+"大于最大值8");
		InTime = aInTime;
	}
	public String getInOperator()
	{
		return InOperator;
	}
	public void setInOperator(String aInOperator)
	{
		if(aInOperator!=null && aInOperator.length()>20)
			throw new IllegalArgumentException("进入操作员InOperator值"+aInOperator+"的长度"+aInOperator.length()+"大于最大值20");
		InOperator = aInOperator;
	}
	public String getOutDate()
	{
		if( OutDate != null )
			return fDate.getString(OutDate);
		else
			return null;
	}
	public void setOutDate(Date aOutDate)
	{
		OutDate = aOutDate;
	}
	public void setOutDate(String aOutDate)
	{
		if (aOutDate != null && !aOutDate.equals("") )
		{
			OutDate = fDate.getDate( aOutDate );
		}
		else
			OutDate = null;
	}

	public String getOutTime()
	{
		return OutTime;
	}
	public void setOutTime(String aOutTime)
	{
		if(aOutTime!=null && aOutTime.length()>8)
			throw new IllegalArgumentException("退出时间OutTime值"+aOutTime+"的长度"+aOutTime.length()+"大于最大值8");
		OutTime = aOutTime;
	}
	public String getOutOperator()
	{
		return OutOperator;
	}
	public void setOutOperator(String aOutOperator)
	{
		if(aOutOperator!=null && aOutOperator.length()>20)
			throw new IllegalArgumentException("退出操作员OutOperator值"+aOutOperator+"的长度"+aOutOperator.length()+"大于最大值20");
		OutOperator = aOutOperator;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
	}

	/**
	* 使用另外一个 LLCaseOpTimeSchema 对象给 Schema 赋值
	* @param: aLLCaseOpTimeSchema LLCaseOpTimeSchema
	**/
	public void setSchema(LLCaseOpTimeSchema aLLCaseOpTimeSchema)
	{
		this.SerialNo = aLLCaseOpTimeSchema.getSerialNo();
		this.CliamNo = aLLCaseOpTimeSchema.getCliamNo();
		this.CliamPhase = aLLCaseOpTimeSchema.getCliamPhase();
		this.PageNo = aLLCaseOpTimeSchema.getPageNo();
		this.InDate = fDate.getDate( aLLCaseOpTimeSchema.getInDate());
		this.InTime = aLLCaseOpTimeSchema.getInTime();
		this.InOperator = aLLCaseOpTimeSchema.getInOperator();
		this.OutDate = fDate.getDate( aLLCaseOpTimeSchema.getOutDate());
		this.OutTime = aLLCaseOpTimeSchema.getOutTime();
		this.OutOperator = aLLCaseOpTimeSchema.getOutOperator();
		this.MngCom = aLLCaseOpTimeSchema.getMngCom();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("CliamNo") == null )
				this.CliamNo = null;
			else
				this.CliamNo = rs.getString("CliamNo").trim();

			if( rs.getString("CliamPhase") == null )
				this.CliamPhase = null;
			else
				this.CliamPhase = rs.getString("CliamPhase").trim();

			if( rs.getString("PageNo") == null )
				this.PageNo = null;
			else
				this.PageNo = rs.getString("PageNo").trim();

			this.InDate = rs.getDate("InDate");
			if( rs.getString("InTime") == null )
				this.InTime = null;
			else
				this.InTime = rs.getString("InTime").trim();

			if( rs.getString("InOperator") == null )
				this.InOperator = null;
			else
				this.InOperator = rs.getString("InOperator").trim();

			this.OutDate = rs.getDate("OutDate");
			if( rs.getString("OutTime") == null )
				this.OutTime = null;
			else
				this.OutTime = rs.getString("OutTime").trim();

			if( rs.getString("OutOperator") == null )
				this.OutOperator = null;
			else
				this.OutOperator = rs.getString("OutOperator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCaseOpTime表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseOpTimeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseOpTimeSchema getSchema()
	{
		LLCaseOpTimeSchema aLLCaseOpTimeSchema = new LLCaseOpTimeSchema();
		aLLCaseOpTimeSchema.setSchema(this);
		return aLLCaseOpTimeSchema;
	}

	public LLCaseOpTimeDB getDB()
	{
		LLCaseOpTimeDB aDBOper = new LLCaseOpTimeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseOpTime描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CliamNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CliamPhase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PageNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OutDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseOpTime>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CliamNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CliamPhase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PageNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			InDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			InTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OutDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			OutTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OutOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseOpTimeSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CliamNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CliamNo));
		}
		if (FCode.equalsIgnoreCase("CliamPhase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CliamPhase));
		}
		if (FCode.equalsIgnoreCase("PageNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PageNo));
		}
		if (FCode.equalsIgnoreCase("InDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
		}
		if (FCode.equalsIgnoreCase("InTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InTime));
		}
		if (FCode.equalsIgnoreCase("InOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InOperator));
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
		}
		if (FCode.equalsIgnoreCase("OutTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutTime));
		}
		if (FCode.equalsIgnoreCase("OutOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutOperator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CliamNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CliamPhase);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PageNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InOperator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOutDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OutTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OutOperator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("CliamNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CliamNo = FValue.trim();
			}
			else
				CliamNo = null;
		}
		if (FCode.equalsIgnoreCase("CliamPhase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CliamPhase = FValue.trim();
			}
			else
				CliamPhase = null;
		}
		if (FCode.equalsIgnoreCase("PageNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PageNo = FValue.trim();
			}
			else
				PageNo = null;
		}
		if (FCode.equalsIgnoreCase("InDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InDate = fDate.getDate( FValue );
			}
			else
				InDate = null;
		}
		if (FCode.equalsIgnoreCase("InTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InTime = FValue.trim();
			}
			else
				InTime = null;
		}
		if (FCode.equalsIgnoreCase("InOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InOperator = FValue.trim();
			}
			else
				InOperator = null;
		}
		if (FCode.equalsIgnoreCase("OutDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OutDate = fDate.getDate( FValue );
			}
			else
				OutDate = null;
		}
		if (FCode.equalsIgnoreCase("OutTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutTime = FValue.trim();
			}
			else
				OutTime = null;
		}
		if (FCode.equalsIgnoreCase("OutOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutOperator = FValue.trim();
			}
			else
				OutOperator = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseOpTimeSchema other = (LLCaseOpTimeSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& CliamNo.equals(other.getCliamNo())
			&& CliamPhase.equals(other.getCliamPhase())
			&& PageNo.equals(other.getPageNo())
			&& fDate.getString(InDate).equals(other.getInDate())
			&& InTime.equals(other.getInTime())
			&& InOperator.equals(other.getInOperator())
			&& fDate.getString(OutDate).equals(other.getOutDate())
			&& OutTime.equals(other.getOutTime())
			&& OutOperator.equals(other.getOutOperator())
			&& MngCom.equals(other.getMngCom());
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("CliamNo") ) {
			return 1;
		}
		if( strFieldName.equals("CliamPhase") ) {
			return 2;
		}
		if( strFieldName.equals("PageNo") ) {
			return 3;
		}
		if( strFieldName.equals("InDate") ) {
			return 4;
		}
		if( strFieldName.equals("InTime") ) {
			return 5;
		}
		if( strFieldName.equals("InOperator") ) {
			return 6;
		}
		if( strFieldName.equals("OutDate") ) {
			return 7;
		}
		if( strFieldName.equals("OutTime") ) {
			return 8;
		}
		if( strFieldName.equals("OutOperator") ) {
			return 9;
		}
		if( strFieldName.equals("MngCom") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "CliamNo";
				break;
			case 2:
				strFieldName = "CliamPhase";
				break;
			case 3:
				strFieldName = "PageNo";
				break;
			case 4:
				strFieldName = "InDate";
				break;
			case 5:
				strFieldName = "InTime";
				break;
			case 6:
				strFieldName = "InOperator";
				break;
			case 7:
				strFieldName = "OutDate";
				break;
			case 8:
				strFieldName = "OutTime";
				break;
			case 9:
				strFieldName = "OutOperator";
				break;
			case 10:
				strFieldName = "MngCom";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CliamNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CliamPhase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PageNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OutTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
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
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
