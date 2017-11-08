

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RICheckErrDB;

/*
 * <p>ClassName: RICheckErrSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RICheckErrSchema implements Schema, Cloneable
{
	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 批次号 */
	private String BatchNo;
	/** 算法编码 */
	private String ArithmeticID;
	/** 计算项编码 */
	private String CalItemID;
	/** 错误信息 */
	private String ErrInfo;
	/** 描述 */
	private String ReMark;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RICheckErrSchema()
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
		RICheckErrSchema cloned = (RICheckErrSchema)super.clone();
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
		SerialNo = aSerialNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getArithmeticID()
	{
		return ArithmeticID;
	}
	public void setArithmeticID(String aArithmeticID)
	{
		ArithmeticID = aArithmeticID;
	}
	public String getCalItemID()
	{
		return CalItemID;
	}
	public void setCalItemID(String aCalItemID)
	{
		CalItemID = aCalItemID;
	}
	public String getErrInfo()
	{
		return ErrInfo;
	}
	public void setErrInfo(String aErrInfo)
	{
		ErrInfo = aErrInfo;
	}
	/**
	* 如果数据保全计算的计算项，此字段保存参与此计算项计算的关联的保全项目名称，关联多个用逗号分隔
	*/
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
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

	/**
	* 使用另外一个 RICheckErrSchema 对象给 Schema 赋值
	* @param: aRICheckErrSchema RICheckErrSchema
	**/
	public void setSchema(RICheckErrSchema aRICheckErrSchema)
	{
		this.SerialNo = aRICheckErrSchema.getSerialNo();
		this.BatchNo = aRICheckErrSchema.getBatchNo();
		this.ArithmeticID = aRICheckErrSchema.getArithmeticID();
		this.CalItemID = aRICheckErrSchema.getCalItemID();
		this.ErrInfo = aRICheckErrSchema.getErrInfo();
		this.ReMark = aRICheckErrSchema.getReMark();
		this.MakeDate = fDate.getDate( aRICheckErrSchema.getMakeDate());
		this.MakeTime = aRICheckErrSchema.getMakeTime();
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

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("ArithmeticID") == null )
				this.ArithmeticID = null;
			else
				this.ArithmeticID = rs.getString("ArithmeticID").trim();

			if( rs.getString("CalItemID") == null )
				this.CalItemID = null;
			else
				this.CalItemID = rs.getString("CalItemID").trim();

			if( rs.getString("ErrInfo") == null )
				this.ErrInfo = null;
			else
				this.ErrInfo = rs.getString("ErrInfo").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RICheckErr表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICheckErrSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RICheckErrSchema getSchema()
	{
		RICheckErrSchema aRICheckErrSchema = new RICheckErrSchema();
		aRICheckErrSchema.setSchema(this);
		return aRICheckErrSchema;
	}

	public RICheckErrDB getDB()
	{
		RICheckErrDB aDBOper = new RICheckErrDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICheckErr描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ArithmeticID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalItemID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ErrInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReMark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRICheckErr>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ArithmeticID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ErrInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RICheckErrSchema";
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ArithmeticID));
		}
		if (FCode.equalsIgnoreCase("CalItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalItemID));
		}
		if (FCode.equalsIgnoreCase("ErrInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ErrInfo));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ArithmeticID);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalItemID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ErrInfo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
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
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("ArithmeticID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ArithmeticID = FValue.trim();
			}
			else
				ArithmeticID = null;
		}
		if (FCode.equalsIgnoreCase("CalItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalItemID = FValue.trim();
			}
			else
				CalItemID = null;
		}
		if (FCode.equalsIgnoreCase("ErrInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ErrInfo = FValue.trim();
			}
			else
				ErrInfo = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RICheckErrSchema other = (RICheckErrSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& BatchNo.equals(other.getBatchNo())
			&& ArithmeticID.equals(other.getArithmeticID())
			&& CalItemID.equals(other.getCalItemID())
			&& ErrInfo.equals(other.getErrInfo())
			&& ReMark.equals(other.getReMark())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime());
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
		if( strFieldName.equals("BatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return 2;
		}
		if( strFieldName.equals("CalItemID") ) {
			return 3;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return 4;
		}
		if( strFieldName.equals("ReMark") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "BatchNo";
				break;
			case 2:
				strFieldName = "ArithmeticID";
				break;
			case 3:
				strFieldName = "CalItemID";
				break;
			case 4:
				strFieldName = "ErrInfo";
				break;
			case 5:
				strFieldName = "ReMark";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ArithmeticID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ErrInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
