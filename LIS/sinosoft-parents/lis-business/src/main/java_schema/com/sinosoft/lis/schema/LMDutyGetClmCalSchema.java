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
import com.sinosoft.lis.db.LMDutyGetClmCalDB;

/*
 * <p>ClassName: LMDutyGetClmCalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMDutyGetClmCalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMDutyGetClmCalSchema.class);

	// @Field
	/** 给付代码 */
	private String GetDutyCode;
	/** 给付名称 */
	private String GetDutyName;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 算法1 */
	private String CalCode1;
	/** 算法2 */
	private String CalCode2;
	/** 算法3 */
	private String CalCode3;
	/** 算法4 */
	private String CalCode4;
	/** 算法5 */
	private String CalCode5;
	/** 算法6 */
	private String CalCode6;
	/** 算法7 */
	private String CalCode7;
	/** 算法8 */
	private String CalCode8;
	/** 算法9 */
	private String CalCode9;
	/** 算法10 */
	private String CalCode10;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMDutyGetClmCalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GetDutyCode";
		pk[1] = "GetDutyKind";

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
		LMDutyGetClmCalSchema cloned = (LMDutyGetClmCalSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public String getGetDutyName()
	{
		return GetDutyName;
	}
	public void setGetDutyName(String aGetDutyName)
	{
		GetDutyName = aGetDutyName;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	/**
	* 未成年人保额
	*/
	public String getCalCode1()
	{
		return CalCode1;
	}
	public void setCalCode1(String aCalCode1)
	{
		CalCode1 = aCalCode1;
	}
	/**
	* 用于描述不用于理赔权限分配的公式
	*/
	public String getCalCode2()
	{
		return CalCode2;
	}
	public void setCalCode2(String aCalCode2)
	{
		CalCode2 = aCalCode2;
	}
	/**
	* 描述涉及未成年人的不用于权限分配的公式
	*/
	public String getCalCode3()
	{
		return CalCode3;
	}
	public void setCalCode3(String aCalCode3)
	{
		CalCode3 = aCalCode3;
	}
	/**
	* 每个豁免险会根据出险时距离合同生效（或复效）一年外才能豁免被豁免险的保费,否则只能退还本附加险所需保费,在该字段描述校验;
	*/
	public String getCalCode4()
	{
		return CalCode4;
	}
	public void setCalCode4(String aCalCode4)
	{
		CalCode4 = aCalCode4;
	}
	public String getCalCode5()
	{
		return CalCode5;
	}
	public void setCalCode5(String aCalCode5)
	{
		CalCode5 = aCalCode5;
	}
	public String getCalCode6()
	{
		return CalCode6;
	}
	public void setCalCode6(String aCalCode6)
	{
		CalCode6 = aCalCode6;
	}
	public String getCalCode7()
	{
		return CalCode7;
	}
	public void setCalCode7(String aCalCode7)
	{
		CalCode7 = aCalCode7;
	}
	public String getCalCode8()
	{
		return CalCode8;
	}
	public void setCalCode8(String aCalCode8)
	{
		CalCode8 = aCalCode8;
	}
	public String getCalCode9()
	{
		return CalCode9;
	}
	public void setCalCode9(String aCalCode9)
	{
		CalCode9 = aCalCode9;
	}
	public String getCalCode10()
	{
		return CalCode10;
	}
	public void setCalCode10(String aCalCode10)
	{
		CalCode10 = aCalCode10;
	}

	/**
	* 使用另外一个 LMDutyGetClmCalSchema 对象给 Schema 赋值
	* @param: aLMDutyGetClmCalSchema LMDutyGetClmCalSchema
	**/
	public void setSchema(LMDutyGetClmCalSchema aLMDutyGetClmCalSchema)
	{
		this.GetDutyCode = aLMDutyGetClmCalSchema.getGetDutyCode();
		this.GetDutyName = aLMDutyGetClmCalSchema.getGetDutyName();
		this.GetDutyKind = aLMDutyGetClmCalSchema.getGetDutyKind();
		this.CalCode1 = aLMDutyGetClmCalSchema.getCalCode1();
		this.CalCode2 = aLMDutyGetClmCalSchema.getCalCode2();
		this.CalCode3 = aLMDutyGetClmCalSchema.getCalCode3();
		this.CalCode4 = aLMDutyGetClmCalSchema.getCalCode4();
		this.CalCode5 = aLMDutyGetClmCalSchema.getCalCode5();
		this.CalCode6 = aLMDutyGetClmCalSchema.getCalCode6();
		this.CalCode7 = aLMDutyGetClmCalSchema.getCalCode7();
		this.CalCode8 = aLMDutyGetClmCalSchema.getCalCode8();
		this.CalCode9 = aLMDutyGetClmCalSchema.getCalCode9();
		this.CalCode10 = aLMDutyGetClmCalSchema.getCalCode10();
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
			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyName") == null )
				this.GetDutyName = null;
			else
				this.GetDutyName = rs.getString("GetDutyName").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("CalCode1") == null )
				this.CalCode1 = null;
			else
				this.CalCode1 = rs.getString("CalCode1").trim();

			if( rs.getString("CalCode2") == null )
				this.CalCode2 = null;
			else
				this.CalCode2 = rs.getString("CalCode2").trim();

			if( rs.getString("CalCode3") == null )
				this.CalCode3 = null;
			else
				this.CalCode3 = rs.getString("CalCode3").trim();

			if( rs.getString("CalCode4") == null )
				this.CalCode4 = null;
			else
				this.CalCode4 = rs.getString("CalCode4").trim();

			if( rs.getString("CalCode5") == null )
				this.CalCode5 = null;
			else
				this.CalCode5 = rs.getString("CalCode5").trim();

			if( rs.getString("CalCode6") == null )
				this.CalCode6 = null;
			else
				this.CalCode6 = rs.getString("CalCode6").trim();

			if( rs.getString("CalCode7") == null )
				this.CalCode7 = null;
			else
				this.CalCode7 = rs.getString("CalCode7").trim();

			if( rs.getString("CalCode8") == null )
				this.CalCode8 = null;
			else
				this.CalCode8 = rs.getString("CalCode8").trim();

			if( rs.getString("CalCode9") == null )
				this.CalCode9 = null;
			else
				this.CalCode9 = rs.getString("CalCode9").trim();

			if( rs.getString("CalCode10") == null )
				this.CalCode10 = null;
			else
				this.CalCode10 = rs.getString("CalCode10").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMDutyGetClmCal表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetClmCalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMDutyGetClmCalSchema getSchema()
	{
		LMDutyGetClmCalSchema aLMDutyGetClmCalSchema = new LMDutyGetClmCalSchema();
		aLMDutyGetClmCalSchema.setSchema(this);
		return aLMDutyGetClmCalSchema;
	}

	public LMDutyGetClmCalDB getDB()
	{
		LMDutyGetClmCalDB aDBOper = new LMDutyGetClmCalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetClmCal描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode10));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMDutyGetClmCal>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CalCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CalCode3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCode4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalCode5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalCode6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalCode7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CalCode8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalCode9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CalCode10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMDutyGetClmCalSchema";
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
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyName));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("CalCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode1));
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode2));
		}
		if (FCode.equalsIgnoreCase("CalCode3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode3));
		}
		if (FCode.equalsIgnoreCase("CalCode4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode4));
		}
		if (FCode.equalsIgnoreCase("CalCode5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode5));
		}
		if (FCode.equalsIgnoreCase("CalCode6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode6));
		}
		if (FCode.equalsIgnoreCase("CalCode7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode7));
		}
		if (FCode.equalsIgnoreCase("CalCode8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode8));
		}
		if (FCode.equalsIgnoreCase("CalCode9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode9));
		}
		if (FCode.equalsIgnoreCase("CalCode10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode10));
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
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CalCode1);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CalCode2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CalCode3);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCode4);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCode5);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalCode6);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalCode7);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CalCode8);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalCode9);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalCode10);
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

		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyName = FValue.trim();
			}
			else
				GetDutyName = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("CalCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode1 = FValue.trim();
			}
			else
				CalCode1 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode2 = FValue.trim();
			}
			else
				CalCode2 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode3 = FValue.trim();
			}
			else
				CalCode3 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode4 = FValue.trim();
			}
			else
				CalCode4 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode5 = FValue.trim();
			}
			else
				CalCode5 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode6 = FValue.trim();
			}
			else
				CalCode6 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode7 = FValue.trim();
			}
			else
				CalCode7 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode8 = FValue.trim();
			}
			else
				CalCode8 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode9 = FValue.trim();
			}
			else
				CalCode9 = null;
		}
		if (FCode.equalsIgnoreCase("CalCode10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode10 = FValue.trim();
			}
			else
				CalCode10 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMDutyGetClmCalSchema other = (LMDutyGetClmCalSchema)otherObject;
		return
			GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyName.equals(other.getGetDutyName())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& CalCode1.equals(other.getCalCode1())
			&& CalCode2.equals(other.getCalCode2())
			&& CalCode3.equals(other.getCalCode3())
			&& CalCode4.equals(other.getCalCode4())
			&& CalCode5.equals(other.getCalCode5())
			&& CalCode6.equals(other.getCalCode6())
			&& CalCode7.equals(other.getCalCode7())
			&& CalCode8.equals(other.getCalCode8())
			&& CalCode9.equals(other.getCalCode9())
			&& CalCode10.equals(other.getCalCode10());
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
		if( strFieldName.equals("GetDutyCode") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 2;
		}
		if( strFieldName.equals("CalCode1") ) {
			return 3;
		}
		if( strFieldName.equals("CalCode2") ) {
			return 4;
		}
		if( strFieldName.equals("CalCode3") ) {
			return 5;
		}
		if( strFieldName.equals("CalCode4") ) {
			return 6;
		}
		if( strFieldName.equals("CalCode5") ) {
			return 7;
		}
		if( strFieldName.equals("CalCode6") ) {
			return 8;
		}
		if( strFieldName.equals("CalCode7") ) {
			return 9;
		}
		if( strFieldName.equals("CalCode8") ) {
			return 10;
		}
		if( strFieldName.equals("CalCode9") ) {
			return 11;
		}
		if( strFieldName.equals("CalCode10") ) {
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
				strFieldName = "GetDutyCode";
				break;
			case 1:
				strFieldName = "GetDutyName";
				break;
			case 2:
				strFieldName = "GetDutyKind";
				break;
			case 3:
				strFieldName = "CalCode1";
				break;
			case 4:
				strFieldName = "CalCode2";
				break;
			case 5:
				strFieldName = "CalCode3";
				break;
			case 6:
				strFieldName = "CalCode4";
				break;
			case 7:
				strFieldName = "CalCode5";
				break;
			case 8:
				strFieldName = "CalCode6";
				break;
			case 9:
				strFieldName = "CalCode7";
				break;
			case 10:
				strFieldName = "CalCode8";
				break;
			case 11:
				strFieldName = "CalCode9";
				break;
			case 12:
				strFieldName = "CalCode10";
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
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode10") ) {
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
