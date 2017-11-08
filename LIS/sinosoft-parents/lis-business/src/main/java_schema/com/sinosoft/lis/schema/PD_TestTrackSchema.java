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
import com.sinosoft.lis.db.PD_TestTrackDB;

/*
 * <p>ClassName: PD_TESTTRACKSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台补充表
 */
public class PD_TestTrackSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_TestTrackSchema.class);

	// @Field
	/** Batchnum */
	private String BATCHNUM;
	/** Testplankind */
	private String TESTPLANKIND;
	/** Clewcontentcode */
	private String CLEWCONTENTCODE;
	/** Clewcontentname */
	private String CLEWCONTENTNAME;
	/** Teststate */
	private String TESTSTATE;
	/** Testperson */
	private String TESTPERSON;
	/** Testtime */
	private Date TESTTIME;
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

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_TestTrackSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BATCHNUM";
		pk[1] = "TESTPLANKIND";
		pk[2] = "CLEWCONTENTCODE";

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
		PD_TestTrackSchema cloned = (PD_TestTrackSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBATCHNUM()
	{
		return BATCHNUM;
	}
	public void setBATCHNUM(String aBATCHNUM)
	{
		if(aBATCHNUM!=null && aBATCHNUM.length()>12)
			throw new IllegalArgumentException("BatchnumBATCHNUM值"+aBATCHNUM+"的长度"+aBATCHNUM.length()+"大于最大值12");
		BATCHNUM = aBATCHNUM;
	}
	public String getTESTPLANKIND()
	{
		return TESTPLANKIND;
	}
	public void setTESTPLANKIND(String aTESTPLANKIND)
	{
		if(aTESTPLANKIND!=null && aTESTPLANKIND.length()>8)
			throw new IllegalArgumentException("TestplankindTESTPLANKIND值"+aTESTPLANKIND+"的长度"+aTESTPLANKIND.length()+"大于最大值8");
		TESTPLANKIND = aTESTPLANKIND;
	}
	public String getCLEWCONTENTCODE()
	{
		return CLEWCONTENTCODE;
	}
	public void setCLEWCONTENTCODE(String aCLEWCONTENTCODE)
	{
		if(aCLEWCONTENTCODE!=null && aCLEWCONTENTCODE.length()>30)
			throw new IllegalArgumentException("ClewcontentcodeCLEWCONTENTCODE值"+aCLEWCONTENTCODE+"的长度"+aCLEWCONTENTCODE.length()+"大于最大值30");
		CLEWCONTENTCODE = aCLEWCONTENTCODE;
	}
	public String getCLEWCONTENTNAME()
	{
		return CLEWCONTENTNAME;
	}
	public void setCLEWCONTENTNAME(String aCLEWCONTENTNAME)
	{
		if(aCLEWCONTENTNAME!=null && aCLEWCONTENTNAME.length()>30)
			throw new IllegalArgumentException("ClewcontentnameCLEWCONTENTNAME值"+aCLEWCONTENTNAME+"的长度"+aCLEWCONTENTNAME.length()+"大于最大值30");
		CLEWCONTENTNAME = aCLEWCONTENTNAME;
	}
	public String getTESTSTATE()
	{
		return TESTSTATE;
	}
	public void setTESTSTATE(String aTESTSTATE)
	{
		if(aTESTSTATE!=null && aTESTSTATE.length()>1)
			throw new IllegalArgumentException("TeststateTESTSTATE值"+aTESTSTATE+"的长度"+aTESTSTATE.length()+"大于最大值1");
		TESTSTATE = aTESTSTATE;
	}
	/**
	* 被保人险种中份数的冗余。??
	*/
	public String getTESTPERSON()
	{
		return TESTPERSON;
	}
	public void setTESTPERSON(String aTESTPERSON)
	{
		if(aTESTPERSON!=null && aTESTPERSON.length()>30)
			throw new IllegalArgumentException("TestpersonTESTPERSON值"+aTESTPERSON+"的长度"+aTESTPERSON.length()+"大于最大值30");
		TESTPERSON = aTESTPERSON;
	}
	public String getTESTTIME()
	{
		if( TESTTIME != null )
			return fDate.getString(TESTTIME);
		else
			return null;
	}
	public void setTESTTIME(Date aTESTTIME)
	{
		TESTTIME = aTESTTIME;
	}
	public void setTESTTIME(String aTESTTIME)
	{
		if (aTESTTIME != null && !aTESTTIME.equals("") )
		{
			TESTTIME = fDate.getDate( aTESTTIME );
		}
		else
			TESTTIME = null;
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
	* 使用另外一个 PD_TESTTRACKSchema 对象给 Schema 赋值
	* @param: aPD_TESTTRACKSchema PD_TESTTRACKSchema
	**/
	public void setSchema(PD_TestTrackSchema aPD_TESTTRACKSchema)
	{
		this.BATCHNUM = aPD_TESTTRACKSchema.getBATCHNUM();
		this.TESTPLANKIND = aPD_TESTTRACKSchema.getTESTPLANKIND();
		this.CLEWCONTENTCODE = aPD_TESTTRACKSchema.getCLEWCONTENTCODE();
		this.CLEWCONTENTNAME = aPD_TESTTRACKSchema.getCLEWCONTENTNAME();
		this.TESTSTATE = aPD_TESTTRACKSchema.getTESTSTATE();
		this.TESTPERSON = aPD_TESTTRACKSchema.getTESTPERSON();
		this.TESTTIME = fDate.getDate( aPD_TESTTRACKSchema.getTESTTIME());
		this.OPERATOR = aPD_TESTTRACKSchema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aPD_TESTTRACKSchema.getMAKEDATE());
		this.MAKETIME = aPD_TESTTRACKSchema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aPD_TESTTRACKSchema.getMODIFYDATE());
		this.MODIFYTIME = aPD_TESTTRACKSchema.getMODIFYTIME();
		this.STANDBYFLAG1 = aPD_TESTTRACKSchema.getSTANDBYFLAG1();
		this.STANDBYFLAG2 = aPD_TESTTRACKSchema.getSTANDBYFLAG2();
		this.STANDBYFLAG3 = aPD_TESTTRACKSchema.getSTANDBYFLAG3();
		this.STANDBYFLAG4 = aPD_TESTTRACKSchema.getSTANDBYFLAG4();
		this.STANDBYFLAG5 = aPD_TESTTRACKSchema.getSTANDBYFLAG5();
		this.STANDBYFLAG6 = aPD_TESTTRACKSchema.getSTANDBYFLAG6();
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
			if( rs.getString("BATCHNUM") == null )
				this.BATCHNUM = null;
			else
				this.BATCHNUM = rs.getString("BATCHNUM").trim();

			if( rs.getString("TESTPLANKIND") == null )
				this.TESTPLANKIND = null;
			else
				this.TESTPLANKIND = rs.getString("TESTPLANKIND").trim();

			if( rs.getString("CLEWCONTENTCODE") == null )
				this.CLEWCONTENTCODE = null;
			else
				this.CLEWCONTENTCODE = rs.getString("CLEWCONTENTCODE").trim();

			if( rs.getString("CLEWCONTENTNAME") == null )
				this.CLEWCONTENTNAME = null;
			else
				this.CLEWCONTENTNAME = rs.getString("CLEWCONTENTNAME").trim();

			if( rs.getString("TESTSTATE") == null )
				this.TESTSTATE = null;
			else
				this.TESTSTATE = rs.getString("TESTSTATE").trim();

			if( rs.getString("TESTPERSON") == null )
				this.TESTPERSON = null;
			else
				this.TESTPERSON = rs.getString("TESTPERSON").trim();

			this.TESTTIME = rs.getDate("TESTTIME");
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
			logger.debug("数据库中的PD_TESTTRACK表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_TESTTRACKSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_TestTrackSchema getSchema()
	{
		PD_TestTrackSchema aPD_TESTTRACKSchema = new PD_TestTrackSchema();
		aPD_TESTTRACKSchema.setSchema(this);
		return aPD_TESTTRACKSchema;
	}

	public PD_TestTrackDB getDB()
	{
		PD_TestTrackDB aDBOper = new PD_TestTrackDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_TESTTRACK描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BATCHNUM)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TESTPLANKIND)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CLEWCONTENTCODE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CLEWCONTENTNAME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TESTSTATE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TESTPERSON)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TESTTIME ))); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_TESTTRACK>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BATCHNUM = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TESTPLANKIND = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CLEWCONTENTCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CLEWCONTENTNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TESTSTATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TESTPERSON = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TESTTIME = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			STANDBYFLAG1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			STANDBYFLAG2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			STANDBYFLAG3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			STANDBYFLAG5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			STANDBYFLAG6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_TESTTRACKSchema";
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
		if (FCode.equalsIgnoreCase("BATCHNUM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BATCHNUM));
		}
		if (FCode.equalsIgnoreCase("TESTPLANKIND"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TESTPLANKIND));
		}
		if (FCode.equalsIgnoreCase("CLEWCONTENTCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CLEWCONTENTCODE));
		}
		if (FCode.equalsIgnoreCase("CLEWCONTENTNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CLEWCONTENTNAME));
		}
		if (FCode.equalsIgnoreCase("TESTSTATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TESTSTATE));
		}
		if (FCode.equalsIgnoreCase("TESTPERSON"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TESTPERSON));
		}
		if (FCode.equalsIgnoreCase("TESTTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTESTTIME()));
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
				strFieldValue = StrTool.GBKToUnicode(BATCHNUM);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TESTPLANKIND);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CLEWCONTENTCODE);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CLEWCONTENTNAME);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TESTSTATE);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TESTPERSON);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTESTTIME()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(STANDBYFLAG2);
				break;
			case 14:
				strFieldValue = String.valueOf(STANDBYFLAG3);
				break;
			case 15:
				strFieldValue = String.valueOf(STANDBYFLAG4);
				break;
			case 16:
				strFieldValue = String.valueOf(STANDBYFLAG5);
				break;
			case 17:
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

		if (FCode.equalsIgnoreCase("BATCHNUM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BATCHNUM = FValue.trim();
			}
			else
				BATCHNUM = null;
		}
		if (FCode.equalsIgnoreCase("TESTPLANKIND"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TESTPLANKIND = FValue.trim();
			}
			else
				TESTPLANKIND = null;
		}
		if (FCode.equalsIgnoreCase("CLEWCONTENTCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CLEWCONTENTCODE = FValue.trim();
			}
			else
				CLEWCONTENTCODE = null;
		}
		if (FCode.equalsIgnoreCase("CLEWCONTENTNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CLEWCONTENTNAME = FValue.trim();
			}
			else
				CLEWCONTENTNAME = null;
		}
		if (FCode.equalsIgnoreCase("TESTSTATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TESTSTATE = FValue.trim();
			}
			else
				TESTSTATE = null;
		}
		if (FCode.equalsIgnoreCase("TESTPERSON"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TESTPERSON = FValue.trim();
			}
			else
				TESTPERSON = null;
		}
		if (FCode.equalsIgnoreCase("TESTTIME"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TESTTIME = fDate.getDate( FValue );
			}
			else
				TESTTIME = null;
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
		PD_TestTrackSchema other = (PD_TestTrackSchema)otherObject;
		return
			BATCHNUM.equals(other.getBATCHNUM())
			&& TESTPLANKIND.equals(other.getTESTPLANKIND())
			&& CLEWCONTENTCODE.equals(other.getCLEWCONTENTCODE())
			&& CLEWCONTENTNAME.equals(other.getCLEWCONTENTNAME())
			&& TESTSTATE.equals(other.getTESTSTATE())
			&& TESTPERSON.equals(other.getTESTPERSON())
			&& fDate.getString(TESTTIME).equals(other.getTESTTIME())
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
		if( strFieldName.equals("BATCHNUM") ) {
			return 0;
		}
		if( strFieldName.equals("TESTPLANKIND") ) {
			return 1;
		}
		if( strFieldName.equals("CLEWCONTENTCODE") ) {
			return 2;
		}
		if( strFieldName.equals("CLEWCONTENTNAME") ) {
			return 3;
		}
		if( strFieldName.equals("TESTSTATE") ) {
			return 4;
		}
		if( strFieldName.equals("TESTPERSON") ) {
			return 5;
		}
		if( strFieldName.equals("TESTTIME") ) {
			return 6;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 7;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 8;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 9;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 10;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 11;
		}
		if( strFieldName.equals("STANDBYFLAG1") ) {
			return 12;
		}
		if( strFieldName.equals("STANDBYFLAG2") ) {
			return 13;
		}
		if( strFieldName.equals("STANDBYFLAG3") ) {
			return 14;
		}
		if( strFieldName.equals("STANDBYFLAG4") ) {
			return 15;
		}
		if( strFieldName.equals("STANDBYFLAG5") ) {
			return 16;
		}
		if( strFieldName.equals("STANDBYFLAG6") ) {
			return 17;
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
				strFieldName = "BATCHNUM";
				break;
			case 1:
				strFieldName = "TESTPLANKIND";
				break;
			case 2:
				strFieldName = "CLEWCONTENTCODE";
				break;
			case 3:
				strFieldName = "CLEWCONTENTNAME";
				break;
			case 4:
				strFieldName = "TESTSTATE";
				break;
			case 5:
				strFieldName = "TESTPERSON";
				break;
			case 6:
				strFieldName = "TESTTIME";
				break;
			case 7:
				strFieldName = "OPERATOR";
				break;
			case 8:
				strFieldName = "MAKEDATE";
				break;
			case 9:
				strFieldName = "MAKETIME";
				break;
			case 10:
				strFieldName = "MODIFYDATE";
				break;
			case 11:
				strFieldName = "MODIFYTIME";
				break;
			case 12:
				strFieldName = "STANDBYFLAG1";
				break;
			case 13:
				strFieldName = "STANDBYFLAG2";
				break;
			case 14:
				strFieldName = "STANDBYFLAG3";
				break;
			case 15:
				strFieldName = "STANDBYFLAG4";
				break;
			case 16:
				strFieldName = "STANDBYFLAG5";
				break;
			case 17:
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
		if( strFieldName.equals("BATCHNUM") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TESTPLANKIND") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CLEWCONTENTCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CLEWCONTENTNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TESTSTATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TESTPERSON") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TESTTIME") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
