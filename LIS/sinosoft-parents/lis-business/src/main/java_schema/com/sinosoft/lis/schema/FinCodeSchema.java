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
import com.sinosoft.lis.db.FinCodeDB;

/*
 * <p>ClassName: FinCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinCodeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FinCodeSchema.class);
	// @Field
	/** 编码类型 */
	private String CodeType;
	/** 编码1 */
	private String Code1;
	/** 编码名称1 */
	private String CodeName1;
	/** 编码2 */
	private String Code2;
	/** 编码名称2 */
	private String CodeName2;
	/** 编码3 */
	private String Code3;
	/** 编码名称3 */
	private String CodeName3;
	/** 编码4 */
	private String Code4;
	/** 编码名称4 */
	private String CodeName4;
	/** 编码5 */
	private String Code5;
	/** 编码名称5 */
	private String CodeName5;
	/** 编码6 */
	private String Code6;
	/** 编码名称6 */
	private String CodeName6;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FinCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "CodeType";
		pk[1] = "Code1";
		pk[2] = "Code2";
		pk[3] = "Code3";
		pk[4] = "Code4";
		pk[5] = "Code5";
		pk[6] = "Code6";

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
		FinCodeSchema cloned = (FinCodeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		if(aCodeType!=null && aCodeType.length()>30)
			throw new IllegalArgumentException("编码类型CodeType值"+aCodeType+"的长度"+aCodeType.length()+"大于最大值30");
		CodeType = aCodeType;
	}
	public String getCode1()
	{
		return Code1;
	}
	public void setCode1(String aCode1)
	{
		if(aCode1!=null && aCode1.length()>30)
			throw new IllegalArgumentException("编码1Code1值"+aCode1+"的长度"+aCode1.length()+"大于最大值30");
		Code1 = aCode1;
	}
	public String getCodeName1()
	{
		return CodeName1;
	}
	public void setCodeName1(String aCodeName1)
	{
		if(aCodeName1!=null && aCodeName1.length()>100)
			throw new IllegalArgumentException("编码名称1CodeName1值"+aCodeName1+"的长度"+aCodeName1.length()+"大于最大值100");
		CodeName1 = aCodeName1;
	}
	public String getCode2()
	{
		return Code2;
	}
	public void setCode2(String aCode2)
	{
		if(aCode2!=null && aCode2.length()>30)
			throw new IllegalArgumentException("编码2Code2值"+aCode2+"的长度"+aCode2.length()+"大于最大值30");
		Code2 = aCode2;
	}
	public String getCodeName2()
	{
		return CodeName2;
	}
	public void setCodeName2(String aCodeName2)
	{
		if(aCodeName2!=null && aCodeName2.length()>100)
			throw new IllegalArgumentException("编码名称2CodeName2值"+aCodeName2+"的长度"+aCodeName2.length()+"大于最大值100");
		CodeName2 = aCodeName2;
	}
	public String getCode3()
	{
		return Code3;
	}
	public void setCode3(String aCode3)
	{
		if(aCode3!=null && aCode3.length()>30)
			throw new IllegalArgumentException("编码3Code3值"+aCode3+"的长度"+aCode3.length()+"大于最大值30");
		Code3 = aCode3;
	}
	public String getCodeName3()
	{
		return CodeName3;
	}
	public void setCodeName3(String aCodeName3)
	{
		if(aCodeName3!=null && aCodeName3.length()>100)
			throw new IllegalArgumentException("编码名称3CodeName3值"+aCodeName3+"的长度"+aCodeName3.length()+"大于最大值100");
		CodeName3 = aCodeName3;
	}
	public String getCode4()
	{
		return Code4;
	}
	public void setCode4(String aCode4)
	{
		if(aCode4!=null && aCode4.length()>30)
			throw new IllegalArgumentException("编码4Code4值"+aCode4+"的长度"+aCode4.length()+"大于最大值30");
		Code4 = aCode4;
	}
	public String getCodeName4()
	{
		return CodeName4;
	}
	public void setCodeName4(String aCodeName4)
	{
		if(aCodeName4!=null && aCodeName4.length()>100)
			throw new IllegalArgumentException("编码名称4CodeName4值"+aCodeName4+"的长度"+aCodeName4.length()+"大于最大值100");
		CodeName4 = aCodeName4;
	}
	public String getCode5()
	{
		return Code5;
	}
	public void setCode5(String aCode5)
	{
		if(aCode5!=null && aCode5.length()>30)
			throw new IllegalArgumentException("编码5Code5值"+aCode5+"的长度"+aCode5.length()+"大于最大值30");
		Code5 = aCode5;
	}
	public String getCodeName5()
	{
		return CodeName5;
	}
	public void setCodeName5(String aCodeName5)
	{
		if(aCodeName5!=null && aCodeName5.length()>100)
			throw new IllegalArgumentException("编码名称5CodeName5值"+aCodeName5+"的长度"+aCodeName5.length()+"大于最大值100");
		CodeName5 = aCodeName5;
	}
	public String getCode6()
	{
		return Code6;
	}
	public void setCode6(String aCode6)
	{
		if(aCode6!=null && aCode6.length()>30)
			throw new IllegalArgumentException("编码6Code6值"+aCode6+"的长度"+aCode6.length()+"大于最大值30");
		Code6 = aCode6;
	}
	public String getCodeName6()
	{
		return CodeName6;
	}
	public void setCodeName6(String aCodeName6)
	{
		if(aCodeName6!=null && aCodeName6.length()>100)
			throw new IllegalArgumentException("编码名称6CodeName6值"+aCodeName6+"的长度"+aCodeName6.length()+"大于最大值100");
		CodeName6 = aCodeName6;
	}

	/**
	* 使用另外一个 FinCodeSchema 对象给 Schema 赋值
	* @param: aFinCodeSchema FinCodeSchema
	**/
	public void setSchema(FinCodeSchema aFinCodeSchema)
	{
		this.CodeType = aFinCodeSchema.getCodeType();
		this.Code1 = aFinCodeSchema.getCode1();
		this.CodeName1 = aFinCodeSchema.getCodeName1();
		this.Code2 = aFinCodeSchema.getCode2();
		this.CodeName2 = aFinCodeSchema.getCodeName2();
		this.Code3 = aFinCodeSchema.getCode3();
		this.CodeName3 = aFinCodeSchema.getCodeName3();
		this.Code4 = aFinCodeSchema.getCode4();
		this.CodeName4 = aFinCodeSchema.getCodeName4();
		this.Code5 = aFinCodeSchema.getCode5();
		this.CodeName5 = aFinCodeSchema.getCodeName5();
		this.Code6 = aFinCodeSchema.getCode6();
		this.CodeName6 = aFinCodeSchema.getCodeName6();
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
			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("Code1") == null )
				this.Code1 = null;
			else
				this.Code1 = rs.getString("Code1").trim();

			if( rs.getString("CodeName1") == null )
				this.CodeName1 = null;
			else
				this.CodeName1 = rs.getString("CodeName1").trim();

			if( rs.getString("Code2") == null )
				this.Code2 = null;
			else
				this.Code2 = rs.getString("Code2").trim();

			if( rs.getString("CodeName2") == null )
				this.CodeName2 = null;
			else
				this.CodeName2 = rs.getString("CodeName2").trim();

			if( rs.getString("Code3") == null )
				this.Code3 = null;
			else
				this.Code3 = rs.getString("Code3").trim();

			if( rs.getString("CodeName3") == null )
				this.CodeName3 = null;
			else
				this.CodeName3 = rs.getString("CodeName3").trim();

			if( rs.getString("Code4") == null )
				this.Code4 = null;
			else
				this.Code4 = rs.getString("Code4").trim();

			if( rs.getString("CodeName4") == null )
				this.CodeName4 = null;
			else
				this.CodeName4 = rs.getString("CodeName4").trim();

			if( rs.getString("Code5") == null )
				this.Code5 = null;
			else
				this.Code5 = rs.getString("Code5").trim();

			if( rs.getString("CodeName5") == null )
				this.CodeName5 = null;
			else
				this.CodeName5 = rs.getString("CodeName5").trim();

			if( rs.getString("Code6") == null )
				this.Code6 = null;
			else
				this.Code6 = rs.getString("Code6").trim();

			if( rs.getString("CodeName6") == null )
				this.CodeName6 = null;
			else
				this.CodeName6 = rs.getString("CodeName6").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FinCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FinCodeSchema getSchema()
	{
		FinCodeSchema aFinCodeSchema = new FinCodeSchema();
		aFinCodeSchema.setSchema(this);
		return aFinCodeSchema;
	}

	public FinCodeDB getDB()
	{
		FinCodeDB aDBOper = new FinCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Code6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Code1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeName1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Code2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CodeName2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Code3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CodeName3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Code4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CodeName4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Code5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CodeName5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Code6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CodeName6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinCodeSchema";
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
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("Code1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code1));
		}
		if (FCode.equalsIgnoreCase("CodeName1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName1));
		}
		if (FCode.equalsIgnoreCase("Code2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code2));
		}
		if (FCode.equalsIgnoreCase("CodeName2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName2));
		}
		if (FCode.equalsIgnoreCase("Code3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code3));
		}
		if (FCode.equalsIgnoreCase("CodeName3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName3));
		}
		if (FCode.equalsIgnoreCase("Code4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code4));
		}
		if (FCode.equalsIgnoreCase("CodeName4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName4));
		}
		if (FCode.equalsIgnoreCase("Code5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code5));
		}
		if (FCode.equalsIgnoreCase("CodeName5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName5));
		}
		if (FCode.equalsIgnoreCase("Code6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code6));
		}
		if (FCode.equalsIgnoreCase("CodeName6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName6));
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
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Code1);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeName1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Code2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CodeName2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Code3);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CodeName3);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Code4);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CodeName4);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Code5);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CodeName5);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Code6);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CodeName6);
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

		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("Code1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code1 = FValue.trim();
			}
			else
				Code1 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName1 = FValue.trim();
			}
			else
				CodeName1 = null;
		}
		if (FCode.equalsIgnoreCase("Code2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code2 = FValue.trim();
			}
			else
				Code2 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName2 = FValue.trim();
			}
			else
				CodeName2 = null;
		}
		if (FCode.equalsIgnoreCase("Code3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code3 = FValue.trim();
			}
			else
				Code3 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName3 = FValue.trim();
			}
			else
				CodeName3 = null;
		}
		if (FCode.equalsIgnoreCase("Code4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code4 = FValue.trim();
			}
			else
				Code4 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName4 = FValue.trim();
			}
			else
				CodeName4 = null;
		}
		if (FCode.equalsIgnoreCase("Code5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code5 = FValue.trim();
			}
			else
				Code5 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName5 = FValue.trim();
			}
			else
				CodeName5 = null;
		}
		if (FCode.equalsIgnoreCase("Code6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code6 = FValue.trim();
			}
			else
				Code6 = null;
		}
		if (FCode.equalsIgnoreCase("CodeName6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName6 = FValue.trim();
			}
			else
				CodeName6 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FinCodeSchema other = (FinCodeSchema)otherObject;
		return
			CodeType.equals(other.getCodeType())
			&& Code1.equals(other.getCode1())
			&& CodeName1.equals(other.getCodeName1())
			&& Code2.equals(other.getCode2())
			&& CodeName2.equals(other.getCodeName2())
			&& Code3.equals(other.getCode3())
			&& CodeName3.equals(other.getCodeName3())
			&& Code4.equals(other.getCode4())
			&& CodeName4.equals(other.getCodeName4())
			&& Code5.equals(other.getCode5())
			&& CodeName5.equals(other.getCodeName5())
			&& Code6.equals(other.getCode6())
			&& CodeName6.equals(other.getCodeName6());
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
		if( strFieldName.equals("CodeType") ) {
			return 0;
		}
		if( strFieldName.equals("Code1") ) {
			return 1;
		}
		if( strFieldName.equals("CodeName1") ) {
			return 2;
		}
		if( strFieldName.equals("Code2") ) {
			return 3;
		}
		if( strFieldName.equals("CodeName2") ) {
			return 4;
		}
		if( strFieldName.equals("Code3") ) {
			return 5;
		}
		if( strFieldName.equals("CodeName3") ) {
			return 6;
		}
		if( strFieldName.equals("Code4") ) {
			return 7;
		}
		if( strFieldName.equals("CodeName4") ) {
			return 8;
		}
		if( strFieldName.equals("Code5") ) {
			return 9;
		}
		if( strFieldName.equals("CodeName5") ) {
			return 10;
		}
		if( strFieldName.equals("Code6") ) {
			return 11;
		}
		if( strFieldName.equals("CodeName6") ) {
			return 12;
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
				strFieldName = "CodeType";
				break;
			case 1:
				strFieldName = "Code1";
				break;
			case 2:
				strFieldName = "CodeName1";
				break;
			case 3:
				strFieldName = "Code2";
				break;
			case 4:
				strFieldName = "CodeName2";
				break;
			case 5:
				strFieldName = "Code3";
				break;
			case 6:
				strFieldName = "CodeName3";
				break;
			case 7:
				strFieldName = "Code4";
				break;
			case 8:
				strFieldName = "CodeName4";
				break;
			case 9:
				strFieldName = "Code5";
				break;
			case 10:
				strFieldName = "CodeName5";
				break;
			case 11:
				strFieldName = "Code6";
				break;
			case 12:
				strFieldName = "CodeName6";
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
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Code6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName6") ) {
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
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
