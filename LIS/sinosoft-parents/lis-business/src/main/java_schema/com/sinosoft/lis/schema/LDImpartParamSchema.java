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
import com.sinosoft.lis.db.LDImpartParamDB;

/*
 * <p>ClassName: LDImpartParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDImpartParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDImpartParamSchema.class);

	// @Field
	/** 告知版别 */
	private String ImpartVer;
	/** 告知编码 */
	private String ImpartCode;
	/** 告知参数顺序号 */
	private String ImpartParamNo;
	/** 告知参数名 */
	private String ImpartParamName;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDImpartParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ImpartVer";
		pk[1] = "ImpartCode";
		pk[2] = "ImpartParamNo";

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
		LDImpartParamSchema cloned = (LDImpartParamSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getImpartVer()
	{
		return ImpartVer;
	}
	public void setImpartVer(String aImpartVer)
	{
		ImpartVer = aImpartVer;
	}
	public String getImpartCode()
	{
		return ImpartCode;
	}
	public void setImpartCode(String aImpartCode)
	{
		ImpartCode = aImpartCode;
	}
	public String getImpartParamNo()
	{
		return ImpartParamNo;
	}
	public void setImpartParamNo(String aImpartParamNo)
	{
		ImpartParamNo = aImpartParamNo;
	}
	public String getImpartParamName()
	{
		return ImpartParamName;
	}
	public void setImpartParamName(String aImpartParamName)
	{
		ImpartParamName = aImpartParamName;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LDImpartParamSchema 对象给 Schema 赋值
	* @param: aLDImpartParamSchema LDImpartParamSchema
	**/
	public void setSchema(LDImpartParamSchema aLDImpartParamSchema)
	{
		this.ImpartVer = aLDImpartParamSchema.getImpartVer();
		this.ImpartCode = aLDImpartParamSchema.getImpartCode();
		this.ImpartParamNo = aLDImpartParamSchema.getImpartParamNo();
		this.ImpartParamName = aLDImpartParamSchema.getImpartParamName();
		this.MakeDate = fDate.getDate( aLDImpartParamSchema.getMakeDate());
		this.MakeTime = aLDImpartParamSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDImpartParamSchema.getModifyDate());
		this.ModifyTime = aLDImpartParamSchema.getModifyTime();
		this.Remark = aLDImpartParamSchema.getRemark();
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
			if( rs.getString("ImpartVer") == null )
				this.ImpartVer = null;
			else
				this.ImpartVer = rs.getString("ImpartVer").trim();

			if( rs.getString("ImpartCode") == null )
				this.ImpartCode = null;
			else
				this.ImpartCode = rs.getString("ImpartCode").trim();

			if( rs.getString("ImpartParamNo") == null )
				this.ImpartParamNo = null;
			else
				this.ImpartParamNo = rs.getString("ImpartParamNo").trim();

			if( rs.getString("ImpartParamName") == null )
				this.ImpartParamName = null;
			else
				this.ImpartParamName = rs.getString("ImpartParamName").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDImpartParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDImpartParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDImpartParamSchema getSchema()
	{
		LDImpartParamSchema aLDImpartParamSchema = new LDImpartParamSchema();
		aLDImpartParamSchema.setSchema(this);
		return aLDImpartParamSchema;
	}

	public LDImpartParamDB getDB()
	{
		LDImpartParamDB aDBOper = new LDImpartParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDImpartParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ImpartVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartParamNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartParamName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDImpartParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ImpartVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ImpartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ImpartParamNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ImpartParamName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDImpartParamSchema";
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
		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartVer));
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartCode));
		}
		if (FCode.equalsIgnoreCase("ImpartParamNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartParamNo));
		}
		if (FCode.equalsIgnoreCase("ImpartParamName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartParamName));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(ImpartVer);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ImpartCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ImpartParamNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ImpartParamName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartVer = FValue.trim();
			}
			else
				ImpartVer = null;
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartCode = FValue.trim();
			}
			else
				ImpartCode = null;
		}
		if (FCode.equalsIgnoreCase("ImpartParamNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartParamNo = FValue.trim();
			}
			else
				ImpartParamNo = null;
		}
		if (FCode.equalsIgnoreCase("ImpartParamName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartParamName = FValue.trim();
			}
			else
				ImpartParamName = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDImpartParamSchema other = (LDImpartParamSchema)otherObject;
		return
			ImpartVer.equals(other.getImpartVer())
			&& ImpartCode.equals(other.getImpartCode())
			&& ImpartParamNo.equals(other.getImpartParamNo())
			&& ImpartParamName.equals(other.getImpartParamName())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("ImpartVer") ) {
			return 0;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return 1;
		}
		if( strFieldName.equals("ImpartParamNo") ) {
			return 2;
		}
		if( strFieldName.equals("ImpartParamName") ) {
			return 3;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 4;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 5;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 7;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "ImpartVer";
				break;
			case 1:
				strFieldName = "ImpartCode";
				break;
			case 2:
				strFieldName = "ImpartParamNo";
				break;
			case 3:
				strFieldName = "ImpartParamName";
				break;
			case 4:
				strFieldName = "MakeDate";
				break;
			case 5:
				strFieldName = "MakeTime";
				break;
			case 6:
				strFieldName = "ModifyDate";
				break;
			case 7:
				strFieldName = "ModifyTime";
				break;
			case 8:
				strFieldName = "Remark";
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
		if( strFieldName.equals("ImpartVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartParamNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartParamName") ) {
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
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
