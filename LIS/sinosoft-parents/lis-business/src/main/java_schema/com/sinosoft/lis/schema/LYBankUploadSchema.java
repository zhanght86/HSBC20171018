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
import com.sinosoft.lis.db.LYBankUploadDB;

/*
 * <p>ClassName: LYBankUploadSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行业务
 */
public class LYBankUploadSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LYBankUploadSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 银行编码 */
	private String BankCode;
	/** 上载日期 */
	private Date UploadDate;
	/** 字段1 */
	private String Col1;
	/** 字段2 */
	private String Col2;
	/** 字段3 */
	private String Col3;
	/** 字段4 */
	private String Col4;
	/** 字段5 */
	private String Col5;
	/** 字段6 */
	private String Col6;
	/** 字段7 */
	private String Col7;
	/** 字段8 */
	private String Col8;
	/** 字段9 */
	private String Col9;
	/** 字段10 */
	private String Col10;
	/** 字段11 */
	private String Col11;
	/** 字段12 */
	private String Col12;
	/** 字段13 */
	private String Col13;
	/** 字段14 */
	private String Col14;
	/** 字段15 */
	private String Col15;
	/** 长字段1 */
	private String LongCol1;
	/** 长字段2 */
	private String LongCol2;
	/** 长字段3 */
	private String LongCol3;
	/** 操作员 */
	private String Operator;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LYBankUploadSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "BankCode";

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
		LYBankUploadSchema cloned = (LYBankUploadSchema)super.clone();
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
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getUploadDate()
	{
		if( UploadDate != null )
			return fDate.getString(UploadDate);
		else
			return null;
	}
	public void setUploadDate(Date aUploadDate)
	{
		UploadDate = aUploadDate;
	}
	public void setUploadDate(String aUploadDate)
	{
		if (aUploadDate != null && !aUploadDate.equals("") )
		{
			UploadDate = fDate.getDate( aUploadDate );
		}
		else
			UploadDate = null;
	}

	public String getCol1()
	{
		return Col1;
	}
	public void setCol1(String aCol1)
	{
		Col1 = aCol1;
	}
	public String getCol2()
	{
		return Col2;
	}
	public void setCol2(String aCol2)
	{
		Col2 = aCol2;
	}
	public String getCol3()
	{
		return Col3;
	}
	public void setCol3(String aCol3)
	{
		Col3 = aCol3;
	}
	public String getCol4()
	{
		return Col4;
	}
	public void setCol4(String aCol4)
	{
		Col4 = aCol4;
	}
	public String getCol5()
	{
		return Col5;
	}
	public void setCol5(String aCol5)
	{
		Col5 = aCol5;
	}
	public String getCol6()
	{
		return Col6;
	}
	public void setCol6(String aCol6)
	{
		Col6 = aCol6;
	}
	public String getCol7()
	{
		return Col7;
	}
	public void setCol7(String aCol7)
	{
		Col7 = aCol7;
	}
	public String getCol8()
	{
		return Col8;
	}
	public void setCol8(String aCol8)
	{
		Col8 = aCol8;
	}
	public String getCol9()
	{
		return Col9;
	}
	public void setCol9(String aCol9)
	{
		Col9 = aCol9;
	}
	public String getCol10()
	{
		return Col10;
	}
	public void setCol10(String aCol10)
	{
		Col10 = aCol10;
	}
	public String getCol11()
	{
		return Col11;
	}
	public void setCol11(String aCol11)
	{
		Col11 = aCol11;
	}
	public String getCol12()
	{
		return Col12;
	}
	public void setCol12(String aCol12)
	{
		Col12 = aCol12;
	}
	public String getCol13()
	{
		return Col13;
	}
	public void setCol13(String aCol13)
	{
		Col13 = aCol13;
	}
	public String getCol14()
	{
		return Col14;
	}
	public void setCol14(String aCol14)
	{
		Col14 = aCol14;
	}
	public String getCol15()
	{
		return Col15;
	}
	public void setCol15(String aCol15)
	{
		Col15 = aCol15;
	}
	public String getLongCol1()
	{
		return LongCol1;
	}
	public void setLongCol1(String aLongCol1)
	{
		LongCol1 = aLongCol1;
	}
	public String getLongCol2()
	{
		return LongCol2;
	}
	public void setLongCol2(String aLongCol2)
	{
		LongCol2 = aLongCol2;
	}
	public String getLongCol3()
	{
		return LongCol3;
	}
	public void setLongCol3(String aLongCol3)
	{
		LongCol3 = aLongCol3;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
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


	/**
	* 使用另外一个 LYBankUploadSchema 对象给 Schema 赋值
	* @param: aLYBankUploadSchema LYBankUploadSchema
	**/
	public void setSchema(LYBankUploadSchema aLYBankUploadSchema)
	{
		this.SerialNo = aLYBankUploadSchema.getSerialNo();
		this.BankCode = aLYBankUploadSchema.getBankCode();
		this.UploadDate = fDate.getDate( aLYBankUploadSchema.getUploadDate());
		this.Col1 = aLYBankUploadSchema.getCol1();
		this.Col2 = aLYBankUploadSchema.getCol2();
		this.Col3 = aLYBankUploadSchema.getCol3();
		this.Col4 = aLYBankUploadSchema.getCol4();
		this.Col5 = aLYBankUploadSchema.getCol5();
		this.Col6 = aLYBankUploadSchema.getCol6();
		this.Col7 = aLYBankUploadSchema.getCol7();
		this.Col8 = aLYBankUploadSchema.getCol8();
		this.Col9 = aLYBankUploadSchema.getCol9();
		this.Col10 = aLYBankUploadSchema.getCol10();
		this.Col11 = aLYBankUploadSchema.getCol11();
		this.Col12 = aLYBankUploadSchema.getCol12();
		this.Col13 = aLYBankUploadSchema.getCol13();
		this.Col14 = aLYBankUploadSchema.getCol14();
		this.Col15 = aLYBankUploadSchema.getCol15();
		this.LongCol1 = aLYBankUploadSchema.getLongCol1();
		this.LongCol2 = aLYBankUploadSchema.getLongCol2();
		this.LongCol3 = aLYBankUploadSchema.getLongCol3();
		this.Operator = aLYBankUploadSchema.getOperator();
		this.MakeTime = aLYBankUploadSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLYBankUploadSchema.getMakeDate());
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

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			this.UploadDate = rs.getDate("UploadDate");
			if( rs.getString("Col1") == null )
				this.Col1 = null;
			else
				this.Col1 = rs.getString("Col1").trim();

			if( rs.getString("Col2") == null )
				this.Col2 = null;
			else
				this.Col2 = rs.getString("Col2").trim();

			if( rs.getString("Col3") == null )
				this.Col3 = null;
			else
				this.Col3 = rs.getString("Col3").trim();

			if( rs.getString("Col4") == null )
				this.Col4 = null;
			else
				this.Col4 = rs.getString("Col4").trim();

			if( rs.getString("Col5") == null )
				this.Col5 = null;
			else
				this.Col5 = rs.getString("Col5").trim();

			if( rs.getString("Col6") == null )
				this.Col6 = null;
			else
				this.Col6 = rs.getString("Col6").trim();

			if( rs.getString("Col7") == null )
				this.Col7 = null;
			else
				this.Col7 = rs.getString("Col7").trim();

			if( rs.getString("Col8") == null )
				this.Col8 = null;
			else
				this.Col8 = rs.getString("Col8").trim();

			if( rs.getString("Col9") == null )
				this.Col9 = null;
			else
				this.Col9 = rs.getString("Col9").trim();

			if( rs.getString("Col10") == null )
				this.Col10 = null;
			else
				this.Col10 = rs.getString("Col10").trim();

			if( rs.getString("Col11") == null )
				this.Col11 = null;
			else
				this.Col11 = rs.getString("Col11").trim();

			if( rs.getString("Col12") == null )
				this.Col12 = null;
			else
				this.Col12 = rs.getString("Col12").trim();

			if( rs.getString("Col13") == null )
				this.Col13 = null;
			else
				this.Col13 = rs.getString("Col13").trim();

			if( rs.getString("Col14") == null )
				this.Col14 = null;
			else
				this.Col14 = rs.getString("Col14").trim();

			if( rs.getString("Col15") == null )
				this.Col15 = null;
			else
				this.Col15 = rs.getString("Col15").trim();

			if( rs.getString("LongCol1") == null )
				this.LongCol1 = null;
			else
				this.LongCol1 = rs.getString("LongCol1").trim();

			if( rs.getString("LongCol2") == null )
				this.LongCol2 = null;
			else
				this.LongCol2 = rs.getString("LongCol2").trim();

			if( rs.getString("LongCol3") == null )
				this.LongCol3 = null;
			else
				this.LongCol3 = rs.getString("LongCol3").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LYBankUpload表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankUploadSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LYBankUploadSchema getSchema()
	{
		LYBankUploadSchema aLYBankUploadSchema = new LYBankUploadSchema();
		aLYBankUploadSchema.setSchema(this);
		return aLYBankUploadSchema;
	}

	public LYBankUploadDB getDB()
	{
		LYBankUploadDB aDBOper = new LYBankUploadDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankUpload描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UploadDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Col15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LongCol1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LongCol2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LongCol3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate )));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLYBankUpload>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UploadDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			Col1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Col2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Col3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Col4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Col5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Col6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Col7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Col8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Col9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Col10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Col11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Col12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Col13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Col14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Col15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			LongCol1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			LongCol2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			LongCol3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LYBankUploadSchema";
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("UploadDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUploadDate()));
		}
		if (FCode.equalsIgnoreCase("Col1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col1));
		}
		if (FCode.equalsIgnoreCase("Col2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col2));
		}
		if (FCode.equalsIgnoreCase("Col3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col3));
		}
		if (FCode.equalsIgnoreCase("Col4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col4));
		}
		if (FCode.equalsIgnoreCase("Col5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col5));
		}
		if (FCode.equalsIgnoreCase("Col6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col6));
		}
		if (FCode.equalsIgnoreCase("Col7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col7));
		}
		if (FCode.equalsIgnoreCase("Col8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col8));
		}
		if (FCode.equalsIgnoreCase("Col9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col9));
		}
		if (FCode.equalsIgnoreCase("Col10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col10));
		}
		if (FCode.equalsIgnoreCase("Col11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col11));
		}
		if (FCode.equalsIgnoreCase("Col12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col12));
		}
		if (FCode.equalsIgnoreCase("Col13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col13));
		}
		if (FCode.equalsIgnoreCase("Col14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col14));
		}
		if (FCode.equalsIgnoreCase("Col15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Col15));
		}
		if (FCode.equalsIgnoreCase("LongCol1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LongCol1));
		}
		if (FCode.equalsIgnoreCase("LongCol2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LongCol2));
		}
		if (FCode.equalsIgnoreCase("LongCol3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LongCol3));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUploadDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Col1);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Col2);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Col3);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Col4);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Col5);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Col6);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Col7);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Col8);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Col9);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Col10);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Col11);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Col12);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Col13);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Col14);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Col15);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(LongCol1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(LongCol2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(LongCol3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("UploadDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UploadDate = fDate.getDate( FValue );
			}
			else
				UploadDate = null;
		}
		if (FCode.equalsIgnoreCase("Col1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col1 = FValue.trim();
			}
			else
				Col1 = null;
		}
		if (FCode.equalsIgnoreCase("Col2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col2 = FValue.trim();
			}
			else
				Col2 = null;
		}
		if (FCode.equalsIgnoreCase("Col3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col3 = FValue.trim();
			}
			else
				Col3 = null;
		}
		if (FCode.equalsIgnoreCase("Col4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col4 = FValue.trim();
			}
			else
				Col4 = null;
		}
		if (FCode.equalsIgnoreCase("Col5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col5 = FValue.trim();
			}
			else
				Col5 = null;
		}
		if (FCode.equalsIgnoreCase("Col6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col6 = FValue.trim();
			}
			else
				Col6 = null;
		}
		if (FCode.equalsIgnoreCase("Col7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col7 = FValue.trim();
			}
			else
				Col7 = null;
		}
		if (FCode.equalsIgnoreCase("Col8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col8 = FValue.trim();
			}
			else
				Col8 = null;
		}
		if (FCode.equalsIgnoreCase("Col9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col9 = FValue.trim();
			}
			else
				Col9 = null;
		}
		if (FCode.equalsIgnoreCase("Col10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col10 = FValue.trim();
			}
			else
				Col10 = null;
		}
		if (FCode.equalsIgnoreCase("Col11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col11 = FValue.trim();
			}
			else
				Col11 = null;
		}
		if (FCode.equalsIgnoreCase("Col12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col12 = FValue.trim();
			}
			else
				Col12 = null;
		}
		if (FCode.equalsIgnoreCase("Col13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col13 = FValue.trim();
			}
			else
				Col13 = null;
		}
		if (FCode.equalsIgnoreCase("Col14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col14 = FValue.trim();
			}
			else
				Col14 = null;
		}
		if (FCode.equalsIgnoreCase("Col15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Col15 = FValue.trim();
			}
			else
				Col15 = null;
		}
		if (FCode.equalsIgnoreCase("LongCol1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LongCol1 = FValue.trim();
			}
			else
				LongCol1 = null;
		}
		if (FCode.equalsIgnoreCase("LongCol2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LongCol2 = FValue.trim();
			}
			else
				LongCol2 = null;
		}
		if (FCode.equalsIgnoreCase("LongCol3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LongCol3 = FValue.trim();
			}
			else
				LongCol3 = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LYBankUploadSchema other = (LYBankUploadSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& BankCode.equals(other.getBankCode())
			&& fDate.getString(UploadDate).equals(other.getUploadDate())
			&& Col1.equals(other.getCol1())
			&& Col2.equals(other.getCol2())
			&& Col3.equals(other.getCol3())
			&& Col4.equals(other.getCol4())
			&& Col5.equals(other.getCol5())
			&& Col6.equals(other.getCol6())
			&& Col7.equals(other.getCol7())
			&& Col8.equals(other.getCol8())
			&& Col9.equals(other.getCol9())
			&& Col10.equals(other.getCol10())
			&& Col11.equals(other.getCol11())
			&& Col12.equals(other.getCol12())
			&& Col13.equals(other.getCol13())
			&& Col14.equals(other.getCol14())
			&& Col15.equals(other.getCol15())
			&& LongCol1.equals(other.getLongCol1())
			&& LongCol2.equals(other.getLongCol2())
			&& LongCol3.equals(other.getLongCol3())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate());
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
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("UploadDate") ) {
			return 2;
		}
		if( strFieldName.equals("Col1") ) {
			return 3;
		}
		if( strFieldName.equals("Col2") ) {
			return 4;
		}
		if( strFieldName.equals("Col3") ) {
			return 5;
		}
		if( strFieldName.equals("Col4") ) {
			return 6;
		}
		if( strFieldName.equals("Col5") ) {
			return 7;
		}
		if( strFieldName.equals("Col6") ) {
			return 8;
		}
		if( strFieldName.equals("Col7") ) {
			return 9;
		}
		if( strFieldName.equals("Col8") ) {
			return 10;
		}
		if( strFieldName.equals("Col9") ) {
			return 11;
		}
		if( strFieldName.equals("Col10") ) {
			return 12;
		}
		if( strFieldName.equals("Col11") ) {
			return 13;
		}
		if( strFieldName.equals("Col12") ) {
			return 14;
		}
		if( strFieldName.equals("Col13") ) {
			return 15;
		}
		if( strFieldName.equals("Col14") ) {
			return 16;
		}
		if( strFieldName.equals("Col15") ) {
			return 17;
		}
		if( strFieldName.equals("LongCol1") ) {
			return 18;
		}
		if( strFieldName.equals("LongCol2") ) {
			return 19;
		}
		if( strFieldName.equals("LongCol3") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
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
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "UploadDate";
				break;
			case 3:
				strFieldName = "Col1";
				break;
			case 4:
				strFieldName = "Col2";
				break;
			case 5:
				strFieldName = "Col3";
				break;
			case 6:
				strFieldName = "Col4";
				break;
			case 7:
				strFieldName = "Col5";
				break;
			case 8:
				strFieldName = "Col6";
				break;
			case 9:
				strFieldName = "Col7";
				break;
			case 10:
				strFieldName = "Col8";
				break;
			case 11:
				strFieldName = "Col9";
				break;
			case 12:
				strFieldName = "Col10";
				break;
			case 13:
				strFieldName = "Col11";
				break;
			case 14:
				strFieldName = "Col12";
				break;
			case 15:
				strFieldName = "Col13";
				break;
			case 16:
				strFieldName = "Col14";
				break;
			case 17:
				strFieldName = "Col15";
				break;
			case 18:
				strFieldName = "LongCol1";
				break;
			case 19:
				strFieldName = "LongCol2";
				break;
			case 20:
				strFieldName = "LongCol3";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "MakeDate";
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
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UploadDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Col1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Col15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LongCol1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LongCol2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LongCol3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_DATE;
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
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
