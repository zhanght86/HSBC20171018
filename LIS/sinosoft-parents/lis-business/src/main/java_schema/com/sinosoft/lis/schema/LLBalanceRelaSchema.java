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
import com.sinosoft.lis.db.LLBalanceRelaDB;

/*
 * <p>ClassName: LLBalanceRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLBalanceRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLBalanceRelaSchema.class);

	// @Field
	/** 结算业务类型 */
	private String BalType;
	/** 结算业务类型描述 */
	private String BalTypeDesc;
	/** 结算子业务类型 */
	private String SubBalType;
	/** 结算子业务类型描述 */
	private String SubBalTypeDesc;
	/** 对应财务类型 */
	private String FinaType;
	/** 对应财务类型描述 */
	private String FinaTypeDesc;
	/** 属性1 */
	private String Prop1;
	/** 属性2 */
	private String Prop2;
	/** 属性3 */
	private String Prop3;
	/** 属性4 */
	private String Prop4;
	/** 属性5 */
	private String Prop5;
	/** 属性6 */
	private String Prop6;
	/** 属性7 */
	private String Prop7;
	/** 属性8 */
	private String Prop8;
	/** 属性9 */
	private String Prop9;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLBalanceRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BalType";
		pk[1] = "SubBalType";
		pk[2] = "FinaType";

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
		LLBalanceRelaSchema cloned = (LLBalanceRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBalType()
	{
		return BalType;
	}
	public void setBalType(String aBalType)
	{
		BalType = aBalType;
	}
	public String getBalTypeDesc()
	{
		return BalTypeDesc;
	}
	public void setBalTypeDesc(String aBalTypeDesc)
	{
		BalTypeDesc = aBalTypeDesc;
	}
	public String getSubBalType()
	{
		return SubBalType;
	}
	public void setSubBalType(String aSubBalType)
	{
		SubBalType = aSubBalType;
	}
	public String getSubBalTypeDesc()
	{
		return SubBalTypeDesc;
	}
	public void setSubBalTypeDesc(String aSubBalTypeDesc)
	{
		SubBalTypeDesc = aSubBalTypeDesc;
	}
	public String getFinaType()
	{
		return FinaType;
	}
	public void setFinaType(String aFinaType)
	{
		FinaType = aFinaType;
	}
	public String getFinaTypeDesc()
	{
		return FinaTypeDesc;
	}
	public void setFinaTypeDesc(String aFinaTypeDesc)
	{
		FinaTypeDesc = aFinaTypeDesc;
	}
	public String getProp1()
	{
		return Prop1;
	}
	public void setProp1(String aProp1)
	{
		Prop1 = aProp1;
	}
	public String getProp2()
	{
		return Prop2;
	}
	public void setProp2(String aProp2)
	{
		Prop2 = aProp2;
	}
	public String getProp3()
	{
		return Prop3;
	}
	public void setProp3(String aProp3)
	{
		Prop3 = aProp3;
	}
	public String getProp4()
	{
		return Prop4;
	}
	public void setProp4(String aProp4)
	{
		Prop4 = aProp4;
	}
	public String getProp5()
	{
		return Prop5;
	}
	public void setProp5(String aProp5)
	{
		Prop5 = aProp5;
	}
	public String getProp6()
	{
		return Prop6;
	}
	public void setProp6(String aProp6)
	{
		Prop6 = aProp6;
	}
	public String getProp7()
	{
		return Prop7;
	}
	public void setProp7(String aProp7)
	{
		Prop7 = aProp7;
	}
	public String getProp8()
	{
		return Prop8;
	}
	public void setProp8(String aProp8)
	{
		Prop8 = aProp8;
	}
	public String getProp9()
	{
		return Prop9;
	}
	public void setProp9(String aProp9)
	{
		Prop9 = aProp9;
	}

	/**
	* 使用另外一个 LLBalanceRelaSchema 对象给 Schema 赋值
	* @param: aLLBalanceRelaSchema LLBalanceRelaSchema
	**/
	public void setSchema(LLBalanceRelaSchema aLLBalanceRelaSchema)
	{
		this.BalType = aLLBalanceRelaSchema.getBalType();
		this.BalTypeDesc = aLLBalanceRelaSchema.getBalTypeDesc();
		this.SubBalType = aLLBalanceRelaSchema.getSubBalType();
		this.SubBalTypeDesc = aLLBalanceRelaSchema.getSubBalTypeDesc();
		this.FinaType = aLLBalanceRelaSchema.getFinaType();
		this.FinaTypeDesc = aLLBalanceRelaSchema.getFinaTypeDesc();
		this.Prop1 = aLLBalanceRelaSchema.getProp1();
		this.Prop2 = aLLBalanceRelaSchema.getProp2();
		this.Prop3 = aLLBalanceRelaSchema.getProp3();
		this.Prop4 = aLLBalanceRelaSchema.getProp4();
		this.Prop5 = aLLBalanceRelaSchema.getProp5();
		this.Prop6 = aLLBalanceRelaSchema.getProp6();
		this.Prop7 = aLLBalanceRelaSchema.getProp7();
		this.Prop8 = aLLBalanceRelaSchema.getProp8();
		this.Prop9 = aLLBalanceRelaSchema.getProp9();
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
			if( rs.getString("BalType") == null )
				this.BalType = null;
			else
				this.BalType = rs.getString("BalType").trim();

			if( rs.getString("BalTypeDesc") == null )
				this.BalTypeDesc = null;
			else
				this.BalTypeDesc = rs.getString("BalTypeDesc").trim();

			if( rs.getString("SubBalType") == null )
				this.SubBalType = null;
			else
				this.SubBalType = rs.getString("SubBalType").trim();

			if( rs.getString("SubBalTypeDesc") == null )
				this.SubBalTypeDesc = null;
			else
				this.SubBalTypeDesc = rs.getString("SubBalTypeDesc").trim();

			if( rs.getString("FinaType") == null )
				this.FinaType = null;
			else
				this.FinaType = rs.getString("FinaType").trim();

			if( rs.getString("FinaTypeDesc") == null )
				this.FinaTypeDesc = null;
			else
				this.FinaTypeDesc = rs.getString("FinaTypeDesc").trim();

			if( rs.getString("Prop1") == null )
				this.Prop1 = null;
			else
				this.Prop1 = rs.getString("Prop1").trim();

			if( rs.getString("Prop2") == null )
				this.Prop2 = null;
			else
				this.Prop2 = rs.getString("Prop2").trim();

			if( rs.getString("Prop3") == null )
				this.Prop3 = null;
			else
				this.Prop3 = rs.getString("Prop3").trim();

			if( rs.getString("Prop4") == null )
				this.Prop4 = null;
			else
				this.Prop4 = rs.getString("Prop4").trim();

			if( rs.getString("Prop5") == null )
				this.Prop5 = null;
			else
				this.Prop5 = rs.getString("Prop5").trim();

			if( rs.getString("Prop6") == null )
				this.Prop6 = null;
			else
				this.Prop6 = rs.getString("Prop6").trim();

			if( rs.getString("Prop7") == null )
				this.Prop7 = null;
			else
				this.Prop7 = rs.getString("Prop7").trim();

			if( rs.getString("Prop8") == null )
				this.Prop8 = null;
			else
				this.Prop8 = rs.getString("Prop8").trim();

			if( rs.getString("Prop9") == null )
				this.Prop9 = null;
			else
				this.Prop9 = rs.getString("Prop9").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLBalanceRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBalanceRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLBalanceRelaSchema getSchema()
	{
		LLBalanceRelaSchema aLLBalanceRelaSchema = new LLBalanceRelaSchema();
		aLLBalanceRelaSchema.setSchema(this);
		return aLLBalanceRelaSchema;
	}

	public LLBalanceRelaDB getDB()
	{
		LLBalanceRelaDB aDBOper = new LLBalanceRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBalanceRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubBalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubBalTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FinaTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prop9));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLBalanceRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BalTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubBalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SubBalTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FinaTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Prop1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Prop2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Prop3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Prop4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Prop5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Prop6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Prop7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Prop8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Prop9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLBalanceRelaSchema";
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
		if (FCode.equalsIgnoreCase("BalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalType));
		}
		if (FCode.equalsIgnoreCase("BalTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalTypeDesc));
		}
		if (FCode.equalsIgnoreCase("SubBalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubBalType));
		}
		if (FCode.equalsIgnoreCase("SubBalTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubBalTypeDesc));
		}
		if (FCode.equalsIgnoreCase("FinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinaType));
		}
		if (FCode.equalsIgnoreCase("FinaTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinaTypeDesc));
		}
		if (FCode.equalsIgnoreCase("Prop1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop1));
		}
		if (FCode.equalsIgnoreCase("Prop2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop2));
		}
		if (FCode.equalsIgnoreCase("Prop3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop3));
		}
		if (FCode.equalsIgnoreCase("Prop4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop4));
		}
		if (FCode.equalsIgnoreCase("Prop5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop5));
		}
		if (FCode.equalsIgnoreCase("Prop6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop6));
		}
		if (FCode.equalsIgnoreCase("Prop7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop7));
		}
		if (FCode.equalsIgnoreCase("Prop8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop8));
		}
		if (FCode.equalsIgnoreCase("Prop9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prop9));
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
				strFieldValue = StrTool.GBKToUnicode(BalType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BalTypeDesc);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubBalType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SubBalTypeDesc);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FinaType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FinaTypeDesc);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Prop1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Prop2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Prop3);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Prop4);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Prop5);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Prop6);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Prop7);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Prop8);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Prop9);
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

		if (FCode.equalsIgnoreCase("BalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalType = FValue.trim();
			}
			else
				BalType = null;
		}
		if (FCode.equalsIgnoreCase("BalTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalTypeDesc = FValue.trim();
			}
			else
				BalTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("SubBalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubBalType = FValue.trim();
			}
			else
				SubBalType = null;
		}
		if (FCode.equalsIgnoreCase("SubBalTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubBalTypeDesc = FValue.trim();
			}
			else
				SubBalTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("FinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinaType = FValue.trim();
			}
			else
				FinaType = null;
		}
		if (FCode.equalsIgnoreCase("FinaTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinaTypeDesc = FValue.trim();
			}
			else
				FinaTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("Prop1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop1 = FValue.trim();
			}
			else
				Prop1 = null;
		}
		if (FCode.equalsIgnoreCase("Prop2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop2 = FValue.trim();
			}
			else
				Prop2 = null;
		}
		if (FCode.equalsIgnoreCase("Prop3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop3 = FValue.trim();
			}
			else
				Prop3 = null;
		}
		if (FCode.equalsIgnoreCase("Prop4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop4 = FValue.trim();
			}
			else
				Prop4 = null;
		}
		if (FCode.equalsIgnoreCase("Prop5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop5 = FValue.trim();
			}
			else
				Prop5 = null;
		}
		if (FCode.equalsIgnoreCase("Prop6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop6 = FValue.trim();
			}
			else
				Prop6 = null;
		}
		if (FCode.equalsIgnoreCase("Prop7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop7 = FValue.trim();
			}
			else
				Prop7 = null;
		}
		if (FCode.equalsIgnoreCase("Prop8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop8 = FValue.trim();
			}
			else
				Prop8 = null;
		}
		if (FCode.equalsIgnoreCase("Prop9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prop9 = FValue.trim();
			}
			else
				Prop9 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLBalanceRelaSchema other = (LLBalanceRelaSchema)otherObject;
		return
			BalType.equals(other.getBalType())
			&& BalTypeDesc.equals(other.getBalTypeDesc())
			&& SubBalType.equals(other.getSubBalType())
			&& SubBalTypeDesc.equals(other.getSubBalTypeDesc())
			&& FinaType.equals(other.getFinaType())
			&& FinaTypeDesc.equals(other.getFinaTypeDesc())
			&& Prop1.equals(other.getProp1())
			&& Prop2.equals(other.getProp2())
			&& Prop3.equals(other.getProp3())
			&& Prop4.equals(other.getProp4())
			&& Prop5.equals(other.getProp5())
			&& Prop6.equals(other.getProp6())
			&& Prop7.equals(other.getProp7())
			&& Prop8.equals(other.getProp8())
			&& Prop9.equals(other.getProp9());
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
		if( strFieldName.equals("BalType") ) {
			return 0;
		}
		if( strFieldName.equals("BalTypeDesc") ) {
			return 1;
		}
		if( strFieldName.equals("SubBalType") ) {
			return 2;
		}
		if( strFieldName.equals("SubBalTypeDesc") ) {
			return 3;
		}
		if( strFieldName.equals("FinaType") ) {
			return 4;
		}
		if( strFieldName.equals("FinaTypeDesc") ) {
			return 5;
		}
		if( strFieldName.equals("Prop1") ) {
			return 6;
		}
		if( strFieldName.equals("Prop2") ) {
			return 7;
		}
		if( strFieldName.equals("Prop3") ) {
			return 8;
		}
		if( strFieldName.equals("Prop4") ) {
			return 9;
		}
		if( strFieldName.equals("Prop5") ) {
			return 10;
		}
		if( strFieldName.equals("Prop6") ) {
			return 11;
		}
		if( strFieldName.equals("Prop7") ) {
			return 12;
		}
		if( strFieldName.equals("Prop8") ) {
			return 13;
		}
		if( strFieldName.equals("Prop9") ) {
			return 14;
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
				strFieldName = "BalType";
				break;
			case 1:
				strFieldName = "BalTypeDesc";
				break;
			case 2:
				strFieldName = "SubBalType";
				break;
			case 3:
				strFieldName = "SubBalTypeDesc";
				break;
			case 4:
				strFieldName = "FinaType";
				break;
			case 5:
				strFieldName = "FinaTypeDesc";
				break;
			case 6:
				strFieldName = "Prop1";
				break;
			case 7:
				strFieldName = "Prop2";
				break;
			case 8:
				strFieldName = "Prop3";
				break;
			case 9:
				strFieldName = "Prop4";
				break;
			case 10:
				strFieldName = "Prop5";
				break;
			case 11:
				strFieldName = "Prop6";
				break;
			case 12:
				strFieldName = "Prop7";
				break;
			case 13:
				strFieldName = "Prop8";
				break;
			case 14:
				strFieldName = "Prop9";
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
		if( strFieldName.equals("BalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubBalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubBalTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinaTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Prop9") ) {
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
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
