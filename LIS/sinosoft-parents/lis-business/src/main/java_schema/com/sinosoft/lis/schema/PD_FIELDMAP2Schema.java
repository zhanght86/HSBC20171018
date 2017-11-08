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
import com.sinosoft.lis.db.PD_FIELDMAP2DB;

/*
 * <p>ClassName: PD_FIELDMAP2Schema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台
 */
public class PD_FIELDMAP2Schema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_FIELDMAP2Schema.class);
	// @Field
	/** Tablecode1 */
	private String TABLECODE1;
	/** Tablecode2 */
	private String TABLECODE2;
	/** Fieldcode1 */
	private String FIELDCODE1;
	/** Fieldcode2 */
	private String FIELDCODE2;
	/** Fieldtype1 */
	private String FIELDTYPE1;
	/** Ispdtable1primary */
	private String ISPDTABLE1PRIMARY;
	/** Fieldtype2 */
	private String FIELDTYPE2;
	/** Istable2primary */
	private String ISTABLE2PRIMARY;
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

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_FIELDMAP2Schema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TABLECODE1";
		pk[1] = "TABLECODE2";
		pk[2] = "FIELDCODE1";
		pk[3] = "FIELDCODE2";

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
		PD_FIELDMAP2Schema cloned = (PD_FIELDMAP2Schema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTABLECODE1()
	{
		return TABLECODE1;
	}
	public void setTABLECODE1(String aTABLECODE1)
	{
		if(aTABLECODE1!=null && aTABLECODE1.length()>30)
			throw new IllegalArgumentException("Tablecode1TABLECODE1值"+aTABLECODE1+"的长度"+aTABLECODE1.length()+"大于最大值30");
		TABLECODE1 = aTABLECODE1;
	}
	public String getTABLECODE2()
	{
		return TABLECODE2;
	}
	public void setTABLECODE2(String aTABLECODE2)
	{
		if(aTABLECODE2!=null && aTABLECODE2.length()>30)
			throw new IllegalArgumentException("Tablecode2TABLECODE2值"+aTABLECODE2+"的长度"+aTABLECODE2.length()+"大于最大值30");
		TABLECODE2 = aTABLECODE2;
	}
	public String getFIELDCODE1()
	{
		return FIELDCODE1;
	}
	public void setFIELDCODE1(String aFIELDCODE1)
	{
		if(aFIELDCODE1!=null && aFIELDCODE1.length()>30)
			throw new IllegalArgumentException("Fieldcode1FIELDCODE1值"+aFIELDCODE1+"的长度"+aFIELDCODE1.length()+"大于最大值30");
		FIELDCODE1 = aFIELDCODE1;
	}
	public String getFIELDCODE2()
	{
		return FIELDCODE2;
	}
	public void setFIELDCODE2(String aFIELDCODE2)
	{
		if(aFIELDCODE2!=null && aFIELDCODE2.length()>30)
			throw new IllegalArgumentException("Fieldcode2FIELDCODE2值"+aFIELDCODE2+"的长度"+aFIELDCODE2.length()+"大于最大值30");
		FIELDCODE2 = aFIELDCODE2;
	}
	public String getFIELDTYPE1()
	{
		return FIELDTYPE1;
	}
	public void setFIELDTYPE1(String aFIELDTYPE1)
	{
		if(aFIELDTYPE1!=null && aFIELDTYPE1.length()>30)
			throw new IllegalArgumentException("Fieldtype1FIELDTYPE1值"+aFIELDTYPE1+"的长度"+aFIELDTYPE1.length()+"大于最大值30");
		FIELDTYPE1 = aFIELDTYPE1;
	}
	public String getISPDTABLE1PRIMARY()
	{
		return ISPDTABLE1PRIMARY;
	}
	public void setISPDTABLE1PRIMARY(String aISPDTABLE1PRIMARY)
	{
		if(aISPDTABLE1PRIMARY!=null && aISPDTABLE1PRIMARY.length()>1)
			throw new IllegalArgumentException("Ispdtable1primaryISPDTABLE1PRIMARY值"+aISPDTABLE1PRIMARY+"的长度"+aISPDTABLE1PRIMARY.length()+"大于最大值1");
		ISPDTABLE1PRIMARY = aISPDTABLE1PRIMARY;
	}
	public String getFIELDTYPE2()
	{
		return FIELDTYPE2;
	}
	public void setFIELDTYPE2(String aFIELDTYPE2)
	{
		if(aFIELDTYPE2!=null && aFIELDTYPE2.length()>30)
			throw new IllegalArgumentException("Fieldtype2FIELDTYPE2值"+aFIELDTYPE2+"的长度"+aFIELDTYPE2.length()+"大于最大值30");
		FIELDTYPE2 = aFIELDTYPE2;
	}
	public String getISTABLE2PRIMARY()
	{
		return ISTABLE2PRIMARY;
	}
	public void setISTABLE2PRIMARY(String aISTABLE2PRIMARY)
	{
		if(aISTABLE2PRIMARY!=null && aISTABLE2PRIMARY.length()>1)
			throw new IllegalArgumentException("Istable2primaryISTABLE2PRIMARY值"+aISTABLE2PRIMARY+"的长度"+aISTABLE2PRIMARY.length()+"大于最大值1");
		ISTABLE2PRIMARY = aISTABLE2PRIMARY;
	}
	public String getOPERATOR()
	{
		return OPERATOR;
	}
	public void setOPERATOR(String aOPERATOR)
	{
		if(aOPERATOR!=null && aOPERATOR.length()>10)
			throw new IllegalArgumentException("OperatorOPERATOR值"+aOPERATOR+"的长度"+aOPERATOR.length()+"大于最大值10");
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
		if(aMAKETIME!=null && aMAKETIME.length()>8)
			throw new IllegalArgumentException("MaketimeMAKETIME值"+aMAKETIME+"的长度"+aMAKETIME.length()+"大于最大值8");
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
		if(aMODIFYTIME!=null && aMODIFYTIME.length()>8)
			throw new IllegalArgumentException("ModifytimeMODIFYTIME值"+aMODIFYTIME+"的长度"+aMODIFYTIME.length()+"大于最大值8");
		MODIFYTIME = aMODIFYTIME;
	}
	public String getSTANDBYFLAG1()
	{
		return STANDBYFLAG1;
	}
	public void setSTANDBYFLAG1(String aSTANDBYFLAG1)
	{
		if(aSTANDBYFLAG1!=null && aSTANDBYFLAG1.length()>20)
			throw new IllegalArgumentException("Standbyflag1STANDBYFLAG1值"+aSTANDBYFLAG1+"的长度"+aSTANDBYFLAG1.length()+"大于最大值20");
		STANDBYFLAG1 = aSTANDBYFLAG1;
	}
	public String getSTANDBYFLAG2()
	{
		return STANDBYFLAG2;
	}
	public void setSTANDBYFLAG2(String aSTANDBYFLAG2)
	{
		if(aSTANDBYFLAG2!=null && aSTANDBYFLAG2.length()>20)
			throw new IllegalArgumentException("Standbyflag2STANDBYFLAG2值"+aSTANDBYFLAG2+"的长度"+aSTANDBYFLAG2.length()+"大于最大值20");
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
	* 使用另外一个 PD_FIELDMAP2Schema 对象给 Schema 赋值
	* @param: aPD_FIELDMAP2Schema PD_FIELDMAP2Schema
	**/
	public void setSchema(PD_FIELDMAP2Schema aPD_FIELDMAP2Schema)
	{
		this.TABLECODE1 = aPD_FIELDMAP2Schema.getTABLECODE1();
		this.TABLECODE2 = aPD_FIELDMAP2Schema.getTABLECODE2();
		this.FIELDCODE1 = aPD_FIELDMAP2Schema.getFIELDCODE1();
		this.FIELDCODE2 = aPD_FIELDMAP2Schema.getFIELDCODE2();
		this.FIELDTYPE1 = aPD_FIELDMAP2Schema.getFIELDTYPE1();
		this.ISPDTABLE1PRIMARY = aPD_FIELDMAP2Schema.getISPDTABLE1PRIMARY();
		this.FIELDTYPE2 = aPD_FIELDMAP2Schema.getFIELDTYPE2();
		this.ISTABLE2PRIMARY = aPD_FIELDMAP2Schema.getISTABLE2PRIMARY();
		this.OPERATOR = aPD_FIELDMAP2Schema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aPD_FIELDMAP2Schema.getMAKEDATE());
		this.MAKETIME = aPD_FIELDMAP2Schema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aPD_FIELDMAP2Schema.getMODIFYDATE());
		this.MODIFYTIME = aPD_FIELDMAP2Schema.getMODIFYTIME();
		this.STANDBYFLAG1 = aPD_FIELDMAP2Schema.getSTANDBYFLAG1();
		this.STANDBYFLAG2 = aPD_FIELDMAP2Schema.getSTANDBYFLAG2();
		this.STANDBYFLAG3 = aPD_FIELDMAP2Schema.getSTANDBYFLAG3();
		this.STANDBYFLAG4 = aPD_FIELDMAP2Schema.getSTANDBYFLAG4();
		this.STANDBYFLAG5 = aPD_FIELDMAP2Schema.getSTANDBYFLAG5();
		this.STANDBYFLAG6 = aPD_FIELDMAP2Schema.getSTANDBYFLAG6();
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
			if( rs.getString("TABLECODE1") == null )
				this.TABLECODE1 = null;
			else
				this.TABLECODE1 = rs.getString("TABLECODE1").trim();

			if( rs.getString("TABLECODE2") == null )
				this.TABLECODE2 = null;
			else
				this.TABLECODE2 = rs.getString("TABLECODE2").trim();

			if( rs.getString("FIELDCODE1") == null )
				this.FIELDCODE1 = null;
			else
				this.FIELDCODE1 = rs.getString("FIELDCODE1").trim();

			if( rs.getString("FIELDCODE2") == null )
				this.FIELDCODE2 = null;
			else
				this.FIELDCODE2 = rs.getString("FIELDCODE2").trim();

			if( rs.getString("FIELDTYPE1") == null )
				this.FIELDTYPE1 = null;
			else
				this.FIELDTYPE1 = rs.getString("FIELDTYPE1").trim();

			if( rs.getString("ISPDTABLE1PRIMARY") == null )
				this.ISPDTABLE1PRIMARY = null;
			else
				this.ISPDTABLE1PRIMARY = rs.getString("ISPDTABLE1PRIMARY").trim();

			if( rs.getString("FIELDTYPE2") == null )
				this.FIELDTYPE2 = null;
			else
				this.FIELDTYPE2 = rs.getString("FIELDTYPE2").trim();

			if( rs.getString("ISTABLE2PRIMARY") == null )
				this.ISTABLE2PRIMARY = null;
			else
				this.ISTABLE2PRIMARY = rs.getString("ISTABLE2PRIMARY").trim();

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
			logger.debug("数据库中的PD_FIELDMAP2表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2Schema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_FIELDMAP2Schema getSchema()
	{
		PD_FIELDMAP2Schema aPD_FIELDMAP2Schema = new PD_FIELDMAP2Schema();
		aPD_FIELDMAP2Schema.setSchema(this);
		return aPD_FIELDMAP2Schema;
	}

	public PD_FIELDMAP2DB getDB()
	{
		PD_FIELDMAP2DB aDBOper = new PD_FIELDMAP2DB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_FIELDMAP2描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TABLECODE1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TABLECODE2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FIELDCODE1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FIELDCODE2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FIELDTYPE1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ISPDTABLE1PRIMARY)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FIELDTYPE2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ISTABLE2PRIMARY)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_FIELDMAP2>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TABLECODE1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TABLECODE2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FIELDCODE1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FIELDCODE2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FIELDTYPE1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ISPDTABLE1PRIMARY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FIELDTYPE2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ISTABLE2PRIMARY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			STANDBYFLAG1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			STANDBYFLAG2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			STANDBYFLAG3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			STANDBYFLAG6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FIELDMAP2Schema";
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
		if (FCode.equalsIgnoreCase("TABLECODE1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TABLECODE1));
		}
		if (FCode.equalsIgnoreCase("TABLECODE2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TABLECODE2));
		}
		if (FCode.equalsIgnoreCase("FIELDCODE1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FIELDCODE1));
		}
		if (FCode.equalsIgnoreCase("FIELDCODE2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FIELDCODE2));
		}
		if (FCode.equalsIgnoreCase("FIELDTYPE1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FIELDTYPE1));
		}
		if (FCode.equalsIgnoreCase("ISPDTABLE1PRIMARY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ISPDTABLE1PRIMARY));
		}
		if (FCode.equalsIgnoreCase("FIELDTYPE2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FIELDTYPE2));
		}
		if (FCode.equalsIgnoreCase("ISTABLE2PRIMARY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ISTABLE2PRIMARY));
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
				strFieldValue = StrTool.GBKToUnicode(TABLECODE1);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TABLECODE2);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FIELDCODE1);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FIELDCODE2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FIELDTYPE1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ISPDTABLE1PRIMARY);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FIELDTYPE2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ISTABLE2PRIMARY);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG2);
				break;
			case 15:
				strFieldValue = String.valueOf(STANDBYFLAG3);
				break;
			case 16:
				strFieldValue = String.valueOf(STANDBYFLAG4);
				break;
			case 17:
				strFieldValue = String.valueOf(STANDBYFLAG5);
				break;
			case 18:
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

		if (FCode.equalsIgnoreCase("TABLECODE1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TABLECODE1 = FValue.trim();
			}
			else
				TABLECODE1 = null;
		}
		if (FCode.equalsIgnoreCase("TABLECODE2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TABLECODE2 = FValue.trim();
			}
			else
				TABLECODE2 = null;
		}
		if (FCode.equalsIgnoreCase("FIELDCODE1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FIELDCODE1 = FValue.trim();
			}
			else
				FIELDCODE1 = null;
		}
		if (FCode.equalsIgnoreCase("FIELDCODE2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FIELDCODE2 = FValue.trim();
			}
			else
				FIELDCODE2 = null;
		}
		if (FCode.equalsIgnoreCase("FIELDTYPE1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FIELDTYPE1 = FValue.trim();
			}
			else
				FIELDTYPE1 = null;
		}
		if (FCode.equalsIgnoreCase("ISPDTABLE1PRIMARY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ISPDTABLE1PRIMARY = FValue.trim();
			}
			else
				ISPDTABLE1PRIMARY = null;
		}
		if (FCode.equalsIgnoreCase("FIELDTYPE2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FIELDTYPE2 = FValue.trim();
			}
			else
				FIELDTYPE2 = null;
		}
		if (FCode.equalsIgnoreCase("ISTABLE2PRIMARY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ISTABLE2PRIMARY = FValue.trim();
			}
			else
				ISTABLE2PRIMARY = null;
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
		PD_FIELDMAP2Schema other = (PD_FIELDMAP2Schema)otherObject;
		return
			TABLECODE1.equals(other.getTABLECODE1())
			&& TABLECODE2.equals(other.getTABLECODE2())
			&& FIELDCODE1.equals(other.getFIELDCODE1())
			&& FIELDCODE2.equals(other.getFIELDCODE2())
			&& FIELDTYPE1.equals(other.getFIELDTYPE1())
			&& ISPDTABLE1PRIMARY.equals(other.getISPDTABLE1PRIMARY())
			&& FIELDTYPE2.equals(other.getFIELDTYPE2())
			&& ISTABLE2PRIMARY.equals(other.getISTABLE2PRIMARY())
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
		if( strFieldName.equals("TABLECODE1") ) {
			return 0;
		}
		if( strFieldName.equals("TABLECODE2") ) {
			return 1;
		}
		if( strFieldName.equals("FIELDCODE1") ) {
			return 2;
		}
		if( strFieldName.equals("FIELDCODE2") ) {
			return 3;
		}
		if( strFieldName.equals("FIELDTYPE1") ) {
			return 4;
		}
		if( strFieldName.equals("ISPDTABLE1PRIMARY") ) {
			return 5;
		}
		if( strFieldName.equals("FIELDTYPE2") ) {
			return 6;
		}
		if( strFieldName.equals("ISTABLE2PRIMARY") ) {
			return 7;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 8;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 9;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 10;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 11;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 12;
		}
		if( strFieldName.equals("STANDBYFLAG1") ) {
			return 13;
		}
		if( strFieldName.equals("STANDBYFLAG2") ) {
			return 14;
		}
		if( strFieldName.equals("STANDBYFLAG3") ) {
			return 15;
		}
		if( strFieldName.equals("STANDBYFLAG4") ) {
			return 16;
		}
		if( strFieldName.equals("STANDBYFLAG5") ) {
			return 17;
		}
		if( strFieldName.equals("STANDBYFLAG6") ) {
			return 18;
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
				strFieldName = "TABLECODE1";
				break;
			case 1:
				strFieldName = "TABLECODE2";
				break;
			case 2:
				strFieldName = "FIELDCODE1";
				break;
			case 3:
				strFieldName = "FIELDCODE2";
				break;
			case 4:
				strFieldName = "FIELDTYPE1";
				break;
			case 5:
				strFieldName = "ISPDTABLE1PRIMARY";
				break;
			case 6:
				strFieldName = "FIELDTYPE2";
				break;
			case 7:
				strFieldName = "ISTABLE2PRIMARY";
				break;
			case 8:
				strFieldName = "OPERATOR";
				break;
			case 9:
				strFieldName = "MAKEDATE";
				break;
			case 10:
				strFieldName = "MAKETIME";
				break;
			case 11:
				strFieldName = "MODIFYDATE";
				break;
			case 12:
				strFieldName = "MODIFYTIME";
				break;
			case 13:
				strFieldName = "STANDBYFLAG1";
				break;
			case 14:
				strFieldName = "STANDBYFLAG2";
				break;
			case 15:
				strFieldName = "STANDBYFLAG3";
				break;
			case 16:
				strFieldName = "STANDBYFLAG4";
				break;
			case 17:
				strFieldName = "STANDBYFLAG5";
				break;
			case 18:
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
		if( strFieldName.equals("TABLECODE1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TABLECODE2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FIELDCODE1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FIELDCODE2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FIELDTYPE1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ISPDTABLE1PRIMARY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FIELDTYPE2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ISTABLE2PRIMARY") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
