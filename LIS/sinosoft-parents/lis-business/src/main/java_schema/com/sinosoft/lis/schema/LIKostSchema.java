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
import com.sinosoft.lis.db.LIKostDB;

/*
 * <p>ClassName: LIKostSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIKostSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIKostSchema.class);

	// @Field
	/** 一级代码 */
	private String Fircode;
	/** 二级代码 */
	private String Seccode;
	/** 三级代码 */
	private String Thrcode;
	/** 四级代码 */
	private String Forcode;
	/** 区代码 */
	private String Salecode;
	/** 机构代码 */
	private String SynCom;
	/** 四级描述 */
	private String ForName;
	/** 区描述 */
	private String Salename;
	/** 公司代码 */
	private String Comcode;
	/** 成本中心 */
	private String KostCode;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIKostSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SynCom";

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
		LIKostSchema cloned = (LIKostSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getFircode()
	{
		return Fircode;
	}
	public void setFircode(String aFircode)
	{
		Fircode = aFircode;
	}
	public String getSeccode()
	{
		return Seccode;
	}
	public void setSeccode(String aSeccode)
	{
		Seccode = aSeccode;
	}
	public String getThrcode()
	{
		return Thrcode;
	}
	public void setThrcode(String aThrcode)
	{
		Thrcode = aThrcode;
	}
	public String getForcode()
	{
		return Forcode;
	}
	public void setForcode(String aForcode)
	{
		Forcode = aForcode;
	}
	public String getSalecode()
	{
		return Salecode;
	}
	public void setSalecode(String aSalecode)
	{
		Salecode = aSalecode;
	}
	public String getSynCom()
	{
		return SynCom;
	}
	public void setSynCom(String aSynCom)
	{
		SynCom = aSynCom;
	}
	public String getForName()
	{
		return ForName;
	}
	public void setForName(String aForName)
	{
		ForName = aForName;
	}
	public String getSalename()
	{
		return Salename;
	}
	public void setSalename(String aSalename)
	{
		Salename = aSalename;
	}
	public String getComcode()
	{
		return Comcode;
	}
	public void setComcode(String aComcode)
	{
		Comcode = aComcode;
	}
	public String getKostCode()
	{
		return KostCode;
	}
	public void setKostCode(String aKostCode)
	{
		KostCode = aKostCode;
	}

	/**
	* 使用另外一个 LIKostSchema 对象给 Schema 赋值
	* @param: aLIKostSchema LIKostSchema
	**/
	public void setSchema(LIKostSchema aLIKostSchema)
	{
		this.Fircode = aLIKostSchema.getFircode();
		this.Seccode = aLIKostSchema.getSeccode();
		this.Thrcode = aLIKostSchema.getThrcode();
		this.Forcode = aLIKostSchema.getForcode();
		this.Salecode = aLIKostSchema.getSalecode();
		this.SynCom = aLIKostSchema.getSynCom();
		this.ForName = aLIKostSchema.getForName();
		this.Salename = aLIKostSchema.getSalename();
		this.Comcode = aLIKostSchema.getComcode();
		this.KostCode = aLIKostSchema.getKostCode();
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
			if( rs.getString("Fircode") == null )
				this.Fircode = null;
			else
				this.Fircode = rs.getString("Fircode").trim();

			if( rs.getString("Seccode") == null )
				this.Seccode = null;
			else
				this.Seccode = rs.getString("Seccode").trim();

			if( rs.getString("Thrcode") == null )
				this.Thrcode = null;
			else
				this.Thrcode = rs.getString("Thrcode").trim();

			if( rs.getString("Forcode") == null )
				this.Forcode = null;
			else
				this.Forcode = rs.getString("Forcode").trim();

			if( rs.getString("Salecode") == null )
				this.Salecode = null;
			else
				this.Salecode = rs.getString("Salecode").trim();

			if( rs.getString("SynCom") == null )
				this.SynCom = null;
			else
				this.SynCom = rs.getString("SynCom").trim();

			if( rs.getString("ForName") == null )
				this.ForName = null;
			else
				this.ForName = rs.getString("ForName").trim();

			if( rs.getString("Salename") == null )
				this.Salename = null;
			else
				this.Salename = rs.getString("Salename").trim();

			if( rs.getString("Comcode") == null )
				this.Comcode = null;
			else
				this.Comcode = rs.getString("Comcode").trim();

			if( rs.getString("KostCode") == null )
				this.KostCode = null;
			else
				this.KostCode = rs.getString("KostCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIKost表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIKostSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIKostSchema getSchema()
	{
		LIKostSchema aLIKostSchema = new LIKostSchema();
		aLIKostSchema.setSchema(this);
		return aLIKostSchema;
	}

	public LIKostDB getDB()
	{
		LIKostDB aDBOper = new LIKostDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIKost描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Fircode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Seccode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Thrcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Forcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Salecode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SynCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ForName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Salename)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Comcode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(KostCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIKost>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Fircode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Seccode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Thrcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Forcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Salecode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SynCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ForName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Salename = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Comcode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			KostCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIKostSchema";
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
		if (FCode.equalsIgnoreCase("Fircode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fircode));
		}
		if (FCode.equalsIgnoreCase("Seccode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Seccode));
		}
		if (FCode.equalsIgnoreCase("Thrcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Thrcode));
		}
		if (FCode.equalsIgnoreCase("Forcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Forcode));
		}
		if (FCode.equalsIgnoreCase("Salecode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salecode));
		}
		if (FCode.equalsIgnoreCase("SynCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SynCom));
		}
		if (FCode.equalsIgnoreCase("ForName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ForName));
		}
		if (FCode.equalsIgnoreCase("Salename"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Salename));
		}
		if (FCode.equalsIgnoreCase("Comcode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Comcode));
		}
		if (FCode.equalsIgnoreCase("KostCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(KostCode));
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
				strFieldValue = StrTool.GBKToUnicode(Fircode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Seccode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Thrcode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Forcode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Salecode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SynCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ForName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Salename);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Comcode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(KostCode);
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

		if (FCode.equalsIgnoreCase("Fircode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fircode = FValue.trim();
			}
			else
				Fircode = null;
		}
		if (FCode.equalsIgnoreCase("Seccode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Seccode = FValue.trim();
			}
			else
				Seccode = null;
		}
		if (FCode.equalsIgnoreCase("Thrcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Thrcode = FValue.trim();
			}
			else
				Thrcode = null;
		}
		if (FCode.equalsIgnoreCase("Forcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Forcode = FValue.trim();
			}
			else
				Forcode = null;
		}
		if (FCode.equalsIgnoreCase("Salecode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Salecode = FValue.trim();
			}
			else
				Salecode = null;
		}
		if (FCode.equalsIgnoreCase("SynCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SynCom = FValue.trim();
			}
			else
				SynCom = null;
		}
		if (FCode.equalsIgnoreCase("ForName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ForName = FValue.trim();
			}
			else
				ForName = null;
		}
		if (FCode.equalsIgnoreCase("Salename"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Salename = FValue.trim();
			}
			else
				Salename = null;
		}
		if (FCode.equalsIgnoreCase("Comcode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Comcode = FValue.trim();
			}
			else
				Comcode = null;
		}
		if (FCode.equalsIgnoreCase("KostCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				KostCode = FValue.trim();
			}
			else
				KostCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIKostSchema other = (LIKostSchema)otherObject;
		return
			Fircode.equals(other.getFircode())
			&& Seccode.equals(other.getSeccode())
			&& Thrcode.equals(other.getThrcode())
			&& Forcode.equals(other.getForcode())
			&& Salecode.equals(other.getSalecode())
			&& SynCom.equals(other.getSynCom())
			&& ForName.equals(other.getForName())
			&& Salename.equals(other.getSalename())
			&& Comcode.equals(other.getComcode())
			&& KostCode.equals(other.getKostCode());
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
		if( strFieldName.equals("Fircode") ) {
			return 0;
		}
		if( strFieldName.equals("Seccode") ) {
			return 1;
		}
		if( strFieldName.equals("Thrcode") ) {
			return 2;
		}
		if( strFieldName.equals("Forcode") ) {
			return 3;
		}
		if( strFieldName.equals("Salecode") ) {
			return 4;
		}
		if( strFieldName.equals("SynCom") ) {
			return 5;
		}
		if( strFieldName.equals("ForName") ) {
			return 6;
		}
		if( strFieldName.equals("Salename") ) {
			return 7;
		}
		if( strFieldName.equals("Comcode") ) {
			return 8;
		}
		if( strFieldName.equals("KostCode") ) {
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
				strFieldName = "Fircode";
				break;
			case 1:
				strFieldName = "Seccode";
				break;
			case 2:
				strFieldName = "Thrcode";
				break;
			case 3:
				strFieldName = "Forcode";
				break;
			case 4:
				strFieldName = "Salecode";
				break;
			case 5:
				strFieldName = "SynCom";
				break;
			case 6:
				strFieldName = "ForName";
				break;
			case 7:
				strFieldName = "Salename";
				break;
			case 8:
				strFieldName = "Comcode";
				break;
			case 9:
				strFieldName = "KostCode";
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
		if( strFieldName.equals("Fircode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Seccode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Thrcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Forcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salecode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SynCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ForName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Salename") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Comcode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("KostCode") ) {
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
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
