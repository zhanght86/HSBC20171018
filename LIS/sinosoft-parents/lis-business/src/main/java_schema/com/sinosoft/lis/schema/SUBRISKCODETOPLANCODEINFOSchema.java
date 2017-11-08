

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
import com.sinosoft.lis.db.SUBRISKCODETOPLANCODEINFODB;

/*
 * <p>ClassName: SUBRISKCODETOPLANCODEINFOSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class SUBRISKCODETOPLANCODEINFOSchema implements Schema, Cloneable
{
	// @Field
	/** 主险产品编码 */
	private String RISKCODE;
	/** 主险plancode */
	private String PLANCODE;
	/** 附加险产品编码 */
	private String SUBRISKCODE;
	/** 附加险plancode */
	private String SUBPLANCODE;
	/** Batchno */
	private int BATCHNO;
	/** 操作员 */
	private String OPERATOR;
	/** 生成日期 */
	private Date MAKEDATE;
	/** 生成时间 */
	private String MAKETIME;
	/** 修改日期 */
	private Date MODIFYDATE;
	/** 修改时间 */
	private String MODIFYTIME;
	/** Standbyflag1 */
	private String StandByFlag1;
	/** Standbyflag2 */
	private String StandByFlag2;
	/** Standbyflag3 */
	private String StandByFlag3;
	/** 是否与保障计划相关 */
	private String ISBENEFITOPTION;
	/** 保障计划类型 */
	private String BENEFITOPTIONTYPE;
	/** 保障计划相关值 */
	private String BENEFITOPTION;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public SUBRISKCODETOPLANCODEINFOSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RISKCODE";
		pk[1] = "PLANCODE";
		pk[2] = "SUBRISKCODE";
		pk[3] = "SUBPLANCODE";

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
		SUBRISKCODETOPLANCODEINFOSchema cloned = (SUBRISKCODETOPLANCODEINFOSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRISKCODE()
	{
		return RISKCODE;
	}
	public void setRISKCODE(String aRISKCODE)
	{
		RISKCODE = aRISKCODE;
	}
	public String getPLANCODE()
	{
		return PLANCODE;
	}
	public void setPLANCODE(String aPLANCODE)
	{
		PLANCODE = aPLANCODE;
	}
	public String getSUBRISKCODE()
	{
		return SUBRISKCODE;
	}
	public void setSUBRISKCODE(String aSUBRISKCODE)
	{
		SUBRISKCODE = aSUBRISKCODE;
	}
	public String getSUBPLANCODE()
	{
		return SUBPLANCODE;
	}
	public void setSUBPLANCODE(String aSUBPLANCODE)
	{
		SUBPLANCODE = aSUBPLANCODE;
	}
	public int getBATCHNO()
	{
		return BATCHNO;
	}
	public void setBATCHNO(int aBATCHNO)
	{
		BATCHNO = aBATCHNO;
	}
	public void setBATCHNO(String aBATCHNO)
	{
		if (aBATCHNO != null && !aBATCHNO.equals(""))
		{
			Integer tInteger = new Integer(aBATCHNO);
			int i = tInteger.intValue();
			BATCHNO = i;
		}
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
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}
	public String getStandByFlag3()
	{
		return StandByFlag3;
	}
	public void setStandByFlag3(String aStandByFlag3)
	{
		StandByFlag3 = aStandByFlag3;
	}
	public String getISBENEFITOPTION()
	{
		return ISBENEFITOPTION;
	}
	public void setISBENEFITOPTION(String aISBENEFITOPTION)
	{
		ISBENEFITOPTION = aISBENEFITOPTION;
	}
	public String getBENEFITOPTIONTYPE()
	{
		return BENEFITOPTIONTYPE;
	}
	public void setBENEFITOPTIONTYPE(String aBENEFITOPTIONTYPE)
	{
		BENEFITOPTIONTYPE = aBENEFITOPTIONTYPE;
	}
	public String getBENEFITOPTION()
	{
		return BENEFITOPTION;
	}
	public void setBENEFITOPTION(String aBENEFITOPTION)
	{
		BENEFITOPTION = aBENEFITOPTION;
	}

	/**
	* 使用另外一个 SUBRISKCODETOPLANCODEINFOSchema 对象给 Schema 赋值
	* @param: aSUBRISKCODETOPLANCODEINFOSchema SUBRISKCODETOPLANCODEINFOSchema
	**/
	public void setSchema(SUBRISKCODETOPLANCODEINFOSchema aSUBRISKCODETOPLANCODEINFOSchema)
	{
		this.RISKCODE = aSUBRISKCODETOPLANCODEINFOSchema.getRISKCODE();
		this.PLANCODE = aSUBRISKCODETOPLANCODEINFOSchema.getPLANCODE();
		this.SUBRISKCODE = aSUBRISKCODETOPLANCODEINFOSchema.getSUBRISKCODE();
		this.SUBPLANCODE = aSUBRISKCODETOPLANCODEINFOSchema.getSUBPLANCODE();
		this.BATCHNO = aSUBRISKCODETOPLANCODEINFOSchema.getBATCHNO();
		this.OPERATOR = aSUBRISKCODETOPLANCODEINFOSchema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aSUBRISKCODETOPLANCODEINFOSchema.getMAKEDATE());
		this.MAKETIME = aSUBRISKCODETOPLANCODEINFOSchema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aSUBRISKCODETOPLANCODEINFOSchema.getMODIFYDATE());
		this.MODIFYTIME = aSUBRISKCODETOPLANCODEINFOSchema.getMODIFYTIME();
		this.StandByFlag1 = aSUBRISKCODETOPLANCODEINFOSchema.getStandByFlag1();
		this.StandByFlag2 = aSUBRISKCODETOPLANCODEINFOSchema.getStandByFlag2();
		this.StandByFlag3 = aSUBRISKCODETOPLANCODEINFOSchema.getStandByFlag3();
		this.ISBENEFITOPTION = aSUBRISKCODETOPLANCODEINFOSchema.getISBENEFITOPTION();
		this.BENEFITOPTIONTYPE = aSUBRISKCODETOPLANCODEINFOSchema.getBENEFITOPTIONTYPE();
		this.BENEFITOPTION = aSUBRISKCODETOPLANCODEINFOSchema.getBENEFITOPTION();
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
			if( rs.getString("RISKCODE") == null )
				this.RISKCODE = null;
			else
				this.RISKCODE = rs.getString("RISKCODE").trim();

			if( rs.getString("PLANCODE") == null )
				this.PLANCODE = null;
			else
				this.PLANCODE = rs.getString("PLANCODE").trim();

			if( rs.getString("SUBRISKCODE") == null )
				this.SUBRISKCODE = null;
			else
				this.SUBRISKCODE = rs.getString("SUBRISKCODE").trim();

			if( rs.getString("SUBPLANCODE") == null )
				this.SUBPLANCODE = null;
			else
				this.SUBPLANCODE = rs.getString("SUBPLANCODE").trim();

			this.BATCHNO = rs.getInt("BATCHNO");
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

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("StandByFlag3") == null )
				this.StandByFlag3 = null;
			else
				this.StandByFlag3 = rs.getString("StandByFlag3").trim();

			if( rs.getString("ISBENEFITOPTION") == null )
				this.ISBENEFITOPTION = null;
			else
				this.ISBENEFITOPTION = rs.getString("ISBENEFITOPTION").trim();

			if( rs.getString("BENEFITOPTIONTYPE") == null )
				this.BENEFITOPTIONTYPE = null;
			else
				this.BENEFITOPTIONTYPE = rs.getString("BENEFITOPTIONTYPE").trim();

			if( rs.getString("BENEFITOPTION") == null )
				this.BENEFITOPTION = null;
			else
				this.BENEFITOPTION = rs.getString("BENEFITOPTION").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的SUBRISKCODETOPLANCODEINFO表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "SUBRISKCODETOPLANCODEINFOSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public SUBRISKCODETOPLANCODEINFOSchema getSchema()
	{
		SUBRISKCODETOPLANCODEINFOSchema aSUBRISKCODETOPLANCODEINFOSchema = new SUBRISKCODETOPLANCODEINFOSchema();
		aSUBRISKCODETOPLANCODEINFOSchema.setSchema(this);
		return aSUBRISKCODETOPLANCODEINFOSchema;
	}

	public SUBRISKCODETOPLANCODEINFODB getDB()
	{
		SUBRISKCODETOPLANCODEINFODB aDBOper = new SUBRISKCODETOPLANCODEINFODB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpSUBRISKCODETOPLANCODEINFO描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RISKCODE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PLANCODE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SUBRISKCODE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SUBPLANCODE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BATCHNO));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPERATOR)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MAKEDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MAKETIME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MODIFYDATE ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MODIFYTIME)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ISBENEFITOPTION)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BENEFITOPTIONTYPE)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BENEFITOPTION));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpSUBRISKCODETOPLANCODEINFO>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RISKCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PLANCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SUBRISKCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SUBPLANCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BATCHNO= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandByFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ISBENEFITOPTION = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			BENEFITOPTIONTYPE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BENEFITOPTION = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "SUBRISKCODETOPLANCODEINFOSchema";
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
		if (FCode.equalsIgnoreCase("RISKCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RISKCODE));
		}
		if (FCode.equalsIgnoreCase("PLANCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PLANCODE));
		}
		if (FCode.equalsIgnoreCase("SUBRISKCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SUBRISKCODE));
		}
		if (FCode.equalsIgnoreCase("SUBPLANCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SUBPLANCODE));
		}
		if (FCode.equalsIgnoreCase("BATCHNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BATCHNO));
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
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag3));
		}
		if (FCode.equalsIgnoreCase("ISBENEFITOPTION"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ISBENEFITOPTION));
		}
		if (FCode.equalsIgnoreCase("BENEFITOPTIONTYPE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BENEFITOPTIONTYPE));
		}
		if (FCode.equalsIgnoreCase("BENEFITOPTION"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BENEFITOPTION));
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
				strFieldValue = StrTool.GBKToUnicode(RISKCODE);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PLANCODE);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SUBRISKCODE);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SUBPLANCODE);
				break;
			case 4:
				strFieldValue = String.valueOf(BATCHNO);
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
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag3);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ISBENEFITOPTION);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(BENEFITOPTIONTYPE);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(BENEFITOPTION);
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

		if (FCode.equalsIgnoreCase("RISKCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RISKCODE = FValue.trim();
			}
			else
				RISKCODE = null;
		}
		if (FCode.equalsIgnoreCase("PLANCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PLANCODE = FValue.trim();
			}
			else
				PLANCODE = null;
		}
		if (FCode.equalsIgnoreCase("SUBRISKCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SUBRISKCODE = FValue.trim();
			}
			else
				SUBRISKCODE = null;
		}
		if (FCode.equalsIgnoreCase("SUBPLANCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SUBPLANCODE = FValue.trim();
			}
			else
				SUBPLANCODE = null;
		}
		if (FCode.equalsIgnoreCase("BATCHNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BATCHNO = i;
			}
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
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag3 = FValue.trim();
			}
			else
				StandByFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("ISBENEFITOPTION"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ISBENEFITOPTION = FValue.trim();
			}
			else
				ISBENEFITOPTION = null;
		}
		if (FCode.equalsIgnoreCase("BENEFITOPTIONTYPE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BENEFITOPTIONTYPE = FValue.trim();
			}
			else
				BENEFITOPTIONTYPE = null;
		}
		if (FCode.equalsIgnoreCase("BENEFITOPTION"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BENEFITOPTION = FValue.trim();
			}
			else
				BENEFITOPTION = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		SUBRISKCODETOPLANCODEINFOSchema other = (SUBRISKCODETOPLANCODEINFOSchema)otherObject;
		return
			RISKCODE.equals(other.getRISKCODE())
			&& PLANCODE.equals(other.getPLANCODE())
			&& SUBRISKCODE.equals(other.getSUBRISKCODE())
			&& SUBPLANCODE.equals(other.getSUBPLANCODE())
			&& BATCHNO == other.getBATCHNO()
			&& OPERATOR.equals(other.getOPERATOR())
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& fDate.getString(MODIFYDATE).equals(other.getMODIFYDATE())
			&& MODIFYTIME.equals(other.getMODIFYTIME())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& StandByFlag3.equals(other.getStandByFlag3())
			&& ISBENEFITOPTION.equals(other.getISBENEFITOPTION())
			&& BENEFITOPTIONTYPE.equals(other.getBENEFITOPTIONTYPE())
			&& BENEFITOPTION.equals(other.getBENEFITOPTION());
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
		if( strFieldName.equals("RISKCODE") ) {
			return 0;
		}
		if( strFieldName.equals("PLANCODE") ) {
			return 1;
		}
		if( strFieldName.equals("SUBRISKCODE") ) {
			return 2;
		}
		if( strFieldName.equals("SUBPLANCODE") ) {
			return 3;
		}
		if( strFieldName.equals("BATCHNO") ) {
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
		if( strFieldName.equals("StandByFlag1") ) {
			return 10;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 11;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return 12;
		}
		if( strFieldName.equals("ISBENEFITOPTION") ) {
			return 13;
		}
		if( strFieldName.equals("BENEFITOPTIONTYPE") ) {
			return 14;
		}
		if( strFieldName.equals("BENEFITOPTION") ) {
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
				strFieldName = "RISKCODE";
				break;
			case 1:
				strFieldName = "PLANCODE";
				break;
			case 2:
				strFieldName = "SUBRISKCODE";
				break;
			case 3:
				strFieldName = "SUBPLANCODE";
				break;
			case 4:
				strFieldName = "BATCHNO";
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
				strFieldName = "StandByFlag1";
				break;
			case 11:
				strFieldName = "StandByFlag2";
				break;
			case 12:
				strFieldName = "StandByFlag3";
				break;
			case 13:
				strFieldName = "ISBENEFITOPTION";
				break;
			case 14:
				strFieldName = "BENEFITOPTIONTYPE";
				break;
			case 15:
				strFieldName = "BENEFITOPTION";
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
		if( strFieldName.equals("RISKCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PLANCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SUBRISKCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SUBPLANCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BATCHNO") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ISBENEFITOPTION") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BENEFITOPTIONTYPE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BENEFITOPTION") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}

