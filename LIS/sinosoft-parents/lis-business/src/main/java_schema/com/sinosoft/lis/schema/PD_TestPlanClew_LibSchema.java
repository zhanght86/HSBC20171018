/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.PD_TestPlanClew_LibDB;

/**
 * <p>ClassName: PD_TestPlanClew_LibSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 浜у搧瀹氫箟鏂板姞琛?
 * @CreateDate：2010-10-14
 */
public class PD_TestPlanClew_LibSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_TestPlanClew_LibSchema.class);

	// @Field
	/** 娴嬭瘯鏂规鍒嗙被 */
	private String TESTPLANKIND;
	/** 娴嬭瘯鏂规闄╃鍒嗙被 */
	private String TESTPLANRISKKIND;
	/** 娴嬭瘯瑕佺礌浠ｇ爜 */
	private String ClewContentCode;
	/** 娴嬭瘯瑕佺偣 */
	private String ClewContent;
	/** 澶囨敞 */
	private String Remark;
	/** Operator */
	private String OPERATOR;
	/** Makedate */
	private Date MAKEDATE;
	/** Maketime */
	private String MAKETIME;
	/** Modifydate */
	private Date MODIFYDATE;
	/** Modifytime */
	private String MODIFYTIME;
	/** Standbyflag1 */
	private String STANDBYFLAG1;
	/** Standbyflag2 */
	private String STANDBYFLAG2;
	/** Standbyflag3 */
	private int STANDBYFLAG3;
	/** Standbyflag4 */
	private int STANDBYFLAG4;
	/** Standbyflag5 */
	private double STANDBYFLAG5;
	/** Standbyflag6 */
	private double STANDBYFLAG6;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_TestPlanClew_LibSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClewContentCode";

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
                PD_TestPlanClew_LibSchema cloned = (PD_TestPlanClew_LibSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTESTPLANKIND()
	{
		return TESTPLANKIND;
	}
	public void setTESTPLANKIND(String aTESTPLANKIND)
	{
		TESTPLANKIND = aTESTPLANKIND;
	}
	public String getTESTPLANRISKKIND()
	{
		return TESTPLANRISKKIND;
	}
	public void setTESTPLANRISKKIND(String aTESTPLANRISKKIND)
	{
		TESTPLANRISKKIND = aTESTPLANRISKKIND;
	}
	public String getClewContentCode()
	{
		return ClewContentCode;
	}
	public void setClewContentCode(String aClewContentCode)
	{
		ClewContentCode = aClewContentCode;
	}
	public String getClewContent()
	{
		return ClewContent;
	}
	public void setClewContent(String aClewContent)
	{
		ClewContent = aClewContent;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getOPERATOR()
	{
		return OPERATOR;
	}
	public void setOPERATOR(String aOPERATOR)
	{
		OPERATOR = aOPERATOR;
	}
	public String getMAKEDATE()
	{
		if( MAKEDATE != null )
			return fDate.getString(MAKEDATE);
		else
			return null;
	}
	public void setMAKEDATE(Date aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	public void setMAKEDATE(String aMAKEDATE)
	{
		if (aMAKEDATE != null && !aMAKEDATE.equals("") )
		{
			MAKEDATE = fDate.getDate( aMAKEDATE );
		}
		else
			MAKEDATE = null;
	}

	public String getMAKETIME()
	{
		return MAKETIME;
	}
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
	}
	public String getMODIFYDATE()
	{
		if( MODIFYDATE != null )
			return fDate.getString(MODIFYDATE);
		else
			return null;
	}
	public void setMODIFYDATE(Date aMODIFYDATE)
	{
		MODIFYDATE = aMODIFYDATE;
	}
	public void setMODIFYDATE(String aMODIFYDATE)
	{
		if (aMODIFYDATE != null && !aMODIFYDATE.equals("") )
		{
			MODIFYDATE = fDate.getDate( aMODIFYDATE );
		}
		else
			MODIFYDATE = null;
	}

	public String getMODIFYTIME()
	{
		return MODIFYTIME;
	}
	public void setMODIFYTIME(String aMODIFYTIME)
	{
		MODIFYTIME = aMODIFYTIME;
	}
	public String getSTANDBYFLAG1()
	{
		return STANDBYFLAG1;
	}
	public void setSTANDBYFLAG1(String aSTANDBYFLAG1)
	{
		STANDBYFLAG1 = aSTANDBYFLAG1;
	}
	public String getSTANDBYFLAG2()
	{
		return STANDBYFLAG2;
	}
	public void setSTANDBYFLAG2(String aSTANDBYFLAG2)
	{
		STANDBYFLAG2 = aSTANDBYFLAG2;
	}
	public int getSTANDBYFLAG3()
	{
		return STANDBYFLAG3;
	}
	public void setSTANDBYFLAG3(int aSTANDBYFLAG3)
	{
		STANDBYFLAG3 = aSTANDBYFLAG3;
	}
	public void setSTANDBYFLAG3(String aSTANDBYFLAG3)
	{
		if (aSTANDBYFLAG3 != null && !aSTANDBYFLAG3.equals(""))
		{
			Integer tInteger = new Integer(aSTANDBYFLAG3);
			int i = tInteger.intValue();
			STANDBYFLAG3 = i;
		}
	}

	public int getSTANDBYFLAG4()
	{
		return STANDBYFLAG4;
	}
	public void setSTANDBYFLAG4(int aSTANDBYFLAG4)
	{
		STANDBYFLAG4 = aSTANDBYFLAG4;
	}
	public void setSTANDBYFLAG4(String aSTANDBYFLAG4)
	{
		if (aSTANDBYFLAG4 != null && !aSTANDBYFLAG4.equals(""))
		{
			Integer tInteger = new Integer(aSTANDBYFLAG4);
			int i = tInteger.intValue();
			STANDBYFLAG4 = i;
		}
	}

	public double getSTANDBYFLAG5()
	{
		return STANDBYFLAG5;
	}
	public void setSTANDBYFLAG5(double aSTANDBYFLAG5)
	{
		STANDBYFLAG5 = aSTANDBYFLAG5;
	}
	public void setSTANDBYFLAG5(String aSTANDBYFLAG5)
	{
		if (aSTANDBYFLAG5 != null && !aSTANDBYFLAG5.equals(""))
		{
			Double tDouble = new Double(aSTANDBYFLAG5);
			double d = tDouble.doubleValue();
			STANDBYFLAG5 = d;
		}
	}

	public double getSTANDBYFLAG6()
	{
		return STANDBYFLAG6;
	}
	public void setSTANDBYFLAG6(double aSTANDBYFLAG6)
	{
		STANDBYFLAG6 = aSTANDBYFLAG6;
	}
	public void setSTANDBYFLAG6(String aSTANDBYFLAG6)
	{
		if (aSTANDBYFLAG6 != null && !aSTANDBYFLAG6.equals(""))
		{
			Double tDouble = new Double(aSTANDBYFLAG6);
			double d = tDouble.doubleValue();
			STANDBYFLAG6 = d;
		}
	}


	/**
	* 使用另外一个 PD_TestPlanClew_LibSchema 对象给 Schema 赋值
	* @param: aPD_TestPlanClew_LibSchema PD_TestPlanClew_LibSchema
	**/
	public void setSchema(PD_TestPlanClew_LibSchema aPD_TestPlanClew_LibSchema)
	{
		this.TESTPLANKIND = aPD_TestPlanClew_LibSchema.getTESTPLANKIND();
		this.TESTPLANRISKKIND = aPD_TestPlanClew_LibSchema.getTESTPLANRISKKIND();
		this.ClewContentCode = aPD_TestPlanClew_LibSchema.getClewContentCode();
		this.ClewContent = aPD_TestPlanClew_LibSchema.getClewContent();
		this.Remark = aPD_TestPlanClew_LibSchema.getRemark();
		this.OPERATOR = aPD_TestPlanClew_LibSchema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aPD_TestPlanClew_LibSchema.getMAKEDATE());
		this.MAKETIME = aPD_TestPlanClew_LibSchema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aPD_TestPlanClew_LibSchema.getMODIFYDATE());
		this.MODIFYTIME = aPD_TestPlanClew_LibSchema.getMODIFYTIME();
		this.STANDBYFLAG1 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG1();
		this.STANDBYFLAG2 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG2();
		this.STANDBYFLAG3 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG3();
		this.STANDBYFLAG4 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG4();
		this.STANDBYFLAG5 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG5();
		this.STANDBYFLAG6 = aPD_TestPlanClew_LibSchema.getSTANDBYFLAG6();
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
			if( rs.getString("TESTPLANKIND") == null )
				this.TESTPLANKIND = null;
			else
				this.TESTPLANKIND = rs.getString("TESTPLANKIND").trim();

			if( rs.getString("TESTPLANRISKKIND") == null )
				this.TESTPLANRISKKIND = null;
			else
				this.TESTPLANRISKKIND = rs.getString("TESTPLANRISKKIND").trim();

			if( rs.getString("ClewContentCode") == null )
				this.ClewContentCode = null;
			else
				this.ClewContentCode = rs.getString("ClewContentCode").trim();

			if( rs.getString("ClewContent") == null )
				this.ClewContent = null;
			else
				this.ClewContent = rs.getString("ClewContent").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("OPERATOR") == null )
				this.OPERATOR = null;
			else
				this.OPERATOR = rs.getString("OPERATOR").trim();

			this.MAKEDATE = rs.getDate("MAKEDATE");
			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

			this.MODIFYDATE = rs.getDate("MODIFYDATE");
			if( rs.getString("MODIFYTIME") == null )
				this.MODIFYTIME = null;
			else
				this.MODIFYTIME = rs.getString("MODIFYTIME").trim();

			if( rs.getString("STANDBYFLAG1") == null )
				this.STANDBYFLAG1 = null;
			else
				this.STANDBYFLAG1 = rs.getString("STANDBYFLAG1").trim();

			if( rs.getString("STANDBYFLAG2") == null )
				this.STANDBYFLAG2 = null;
			else
				this.STANDBYFLAG2 = rs.getString("STANDBYFLAG2").trim();

			this.STANDBYFLAG3 = rs.getInt("STANDBYFLAG3");
			this.STANDBYFLAG4 = rs.getInt("STANDBYFLAG4");
			this.STANDBYFLAG5 = rs.getDouble("STANDBYFLAG5");
			this.STANDBYFLAG6 = rs.getDouble("STANDBYFLAG6");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_TestPlanClew_Lib表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_TestPlanClew_LibSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_TestPlanClew_LibSchema getSchema()
	{
		PD_TestPlanClew_LibSchema aPD_TestPlanClew_LibSchema = new PD_TestPlanClew_LibSchema();
		aPD_TestPlanClew_LibSchema.setSchema(this);
		return aPD_TestPlanClew_LibSchema;
	}

	public PD_TestPlanClew_LibDB getDB()
	{
		PD_TestPlanClew_LibDB aDBOper = new PD_TestPlanClew_LibDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_TestPlanClew_Lib描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(TESTPLANKIND)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(TESTPLANRISKKIND)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ClewContentCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ClewContent)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(OPERATOR)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MAKEDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MAKETIME)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MODIFYDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MODIFYTIME)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(STANDBYFLAG1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(STANDBYFLAG2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(STANDBYFLAG3));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(STANDBYFLAG4));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(STANDBYFLAG5));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(STANDBYFLAG6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_TestPlanClew_Lib>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TESTPLANKIND = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TESTPLANRISKKIND = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClewContentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClewContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			STANDBYFLAG1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			STANDBYFLAG2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			STANDBYFLAG3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			STANDBYFLAG6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_TestPlanClew_LibSchema";
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
		if (FCode.equalsIgnoreCase("TESTPLANKIND"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TESTPLANKIND));
		}
		if (FCode.equalsIgnoreCase("TESTPLANRISKKIND"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TESTPLANRISKKIND));
		}
		if (FCode.equalsIgnoreCase("ClewContentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClewContentCode));
		}
		if (FCode.equalsIgnoreCase("ClewContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClewContent));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("OPERATOR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPERATOR));
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
		}
		if (FCode.equalsIgnoreCase("MODIFYDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
		}
		if (FCode.equalsIgnoreCase("MODIFYTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYTIME));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG1));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG2));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG3));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG4));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG5));
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STANDBYFLAG6));
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
				strFieldValue = StrTool.GBKToUnicode(TESTPLANKIND);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TESTPLANRISKKIND);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClewContentCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClewContent);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG2);
				break;
			case 12:
				strFieldValue = String.valueOf(STANDBYFLAG3);
				break;
			case 13:
				strFieldValue = String.valueOf(STANDBYFLAG4);
				break;
			case 14:
				strFieldValue = String.valueOf(STANDBYFLAG5);
				break;
			case 15:
				strFieldValue = String.valueOf(STANDBYFLAG6);
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

		if (FCode.equalsIgnoreCase("TESTPLANKIND"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TESTPLANKIND = FValue.trim();
			}
			else
				TESTPLANKIND = null;
		}
		if (FCode.equalsIgnoreCase("TESTPLANRISKKIND"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TESTPLANRISKKIND = FValue.trim();
			}
			else
				TESTPLANRISKKIND = null;
		}
		if (FCode.equalsIgnoreCase("ClewContentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClewContentCode = FValue.trim();
			}
			else
				ClewContentCode = null;
		}
		if (FCode.equalsIgnoreCase("ClewContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClewContent = FValue.trim();
			}
			else
				ClewContent = null;
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
		if (FCode.equalsIgnoreCase("OPERATOR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPERATOR = FValue.trim();
			}
			else
				OPERATOR = null;
		}
		if (FCode.equalsIgnoreCase("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MAKEDATE = fDate.getDate( FValue );
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equalsIgnoreCase("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
		}
		if (FCode.equalsIgnoreCase("MODIFYDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MODIFYDATE = fDate.getDate( FValue );
			}
			else
				MODIFYDATE = null;
		}
		if (FCode.equalsIgnoreCase("MODIFYTIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MODIFYTIME = FValue.trim();
			}
			else
				MODIFYTIME = null;
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				STANDBYFLAG1 = FValue.trim();
			}
			else
				STANDBYFLAG1 = null;
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				STANDBYFLAG2 = FValue.trim();
			}
			else
				STANDBYFLAG2 = null;
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				STANDBYFLAG3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				STANDBYFLAG4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				STANDBYFLAG5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("STANDBYFLAG6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				STANDBYFLAG6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_TestPlanClew_LibSchema other = (PD_TestPlanClew_LibSchema)otherObject;
		return
			TESTPLANKIND.equals(other.getTESTPLANKIND())
			&& TESTPLANRISKKIND.equals(other.getTESTPLANRISKKIND())
			&& ClewContentCode.equals(other.getClewContentCode())
			&& ClewContent.equals(other.getClewContent())
			&& Remark.equals(other.getRemark())
			&& OPERATOR.equals(other.getOPERATOR())
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& fDate.getString(MODIFYDATE).equals(other.getMODIFYDATE())
			&& MODIFYTIME.equals(other.getMODIFYTIME())
			&& STANDBYFLAG1.equals(other.getSTANDBYFLAG1())
			&& STANDBYFLAG2.equals(other.getSTANDBYFLAG2())
			&& STANDBYFLAG3 == other.getSTANDBYFLAG3()
			&& STANDBYFLAG4 == other.getSTANDBYFLAG4()
			&& STANDBYFLAG5 == other.getSTANDBYFLAG5()
			&& STANDBYFLAG6 == other.getSTANDBYFLAG6();
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
		if( strFieldName.equals("TESTPLANKIND") ) {
			return 0;
		}
		if( strFieldName.equals("TESTPLANRISKKIND") ) {
			return 1;
		}
		if( strFieldName.equals("ClewContentCode") ) {
			return 2;
		}
		if( strFieldName.equals("ClewContent") ) {
			return 3;
		}
		if( strFieldName.equals("Remark") ) {
			return 4;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 5;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 6;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 7;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 8;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 9;
		}
		if( strFieldName.equals("STANDBYFLAG1") ) {
			return 10;
		}
		if( strFieldName.equals("STANDBYFLAG2") ) {
			return 11;
		}
		if( strFieldName.equals("STANDBYFLAG3") ) {
			return 12;
		}
		if( strFieldName.equals("STANDBYFLAG4") ) {
			return 13;
		}
		if( strFieldName.equals("STANDBYFLAG5") ) {
			return 14;
		}
		if( strFieldName.equals("STANDBYFLAG6") ) {
			return 15;
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
				strFieldName = "TESTPLANKIND";
				break;
			case 1:
				strFieldName = "TESTPLANRISKKIND";
				break;
			case 2:
				strFieldName = "ClewContentCode";
				break;
			case 3:
				strFieldName = "ClewContent";
				break;
			case 4:
				strFieldName = "Remark";
				break;
			case 5:
				strFieldName = "OPERATOR";
				break;
			case 6:
				strFieldName = "MAKEDATE";
				break;
			case 7:
				strFieldName = "MAKETIME";
				break;
			case 8:
				strFieldName = "MODIFYDATE";
				break;
			case 9:
				strFieldName = "MODIFYTIME";
				break;
			case 10:
				strFieldName = "STANDBYFLAG1";
				break;
			case 11:
				strFieldName = "STANDBYFLAG2";
				break;
			case 12:
				strFieldName = "STANDBYFLAG3";
				break;
			case 13:
				strFieldName = "STANDBYFLAG4";
				break;
			case 14:
				strFieldName = "STANDBYFLAG5";
				break;
			case 15:
				strFieldName = "STANDBYFLAG6";
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
		if( strFieldName.equals("TESTPLANKIND") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TESTPLANRISKKIND") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClewContentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClewContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("STANDBYFLAG1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("STANDBYFLAG2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("STANDBYFLAG3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("STANDBYFLAG4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("STANDBYFLAG5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("STANDBYFLAG6") ) {
			return Schema.TYPE_DOUBLE;
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
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
