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
import com.sinosoft.lis.db.LCPolOtherDB;

/*
 * <p>ClassName: LCPolOtherSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCPolOtherSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPolOtherSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 合同号码 */
	private String ContNo;
	/** 保单险种号码 */
	private String PolNo;
	/** 责任编码 */
	private String DutyCode;
	/** P1 */
	private String P1;
	/** P2 */
	private String P2;
	/** P3 */
	private String P3;
	/** P4 */
	private String P4;
	/** P5 */
	private String P5;
	/** P6 */
	private String P6;
	/** P7 */
	private String P7;
	/** P8 */
	private String P8;
	/** P9 */
	private String P9;
	/** P10 */
	private String P10;
	/** P11 */
	private String P11;
	/** P12 */
	private String P12;
	/** P13 */
	private String P13;
	/** P14 */
	private String P14;
	/** P15 */
	private String P15;
	/** P16 */
	private String P16;
	/** P17 */
	private String P17;
	/** P18 */
	private String P18;
	/** P19 */
	private String P19;
	/** P20 */
	private String P20;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPolOtherSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "GrpContNo";
		pk[1] = "GrpPolNo";
		pk[2] = "ContNo";
		pk[3] = "PolNo";
		pk[4] = "DutyCode";

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
		LCPolOtherSchema cloned = (LCPolOtherSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getP1()
	{
		return P1;
	}
	public void setP1(String aP1)
	{
		P1 = aP1;
	}
	public String getP2()
	{
		return P2;
	}
	public void setP2(String aP2)
	{
		P2 = aP2;
	}
	public String getP3()
	{
		return P3;
	}
	public void setP3(String aP3)
	{
		P3 = aP3;
	}
	public String getP4()
	{
		return P4;
	}
	public void setP4(String aP4)
	{
		P4 = aP4;
	}
	public String getP5()
	{
		return P5;
	}
	public void setP5(String aP5)
	{
		P5 = aP5;
	}
	public String getP6()
	{
		return P6;
	}
	public void setP6(String aP6)
	{
		P6 = aP6;
	}
	public String getP7()
	{
		return P7;
	}
	public void setP7(String aP7)
	{
		P7 = aP7;
	}
	public String getP8()
	{
		return P8;
	}
	public void setP8(String aP8)
	{
		P8 = aP8;
	}
	public String getP9()
	{
		return P9;
	}
	public void setP9(String aP9)
	{
		P9 = aP9;
	}
	public String getP10()
	{
		return P10;
	}
	public void setP10(String aP10)
	{
		P10 = aP10;
	}
	public String getP11()
	{
		return P11;
	}
	public void setP11(String aP11)
	{
		P11 = aP11;
	}
	public String getP12()
	{
		return P12;
	}
	public void setP12(String aP12)
	{
		P12 = aP12;
	}
	public String getP13()
	{
		return P13;
	}
	public void setP13(String aP13)
	{
		P13 = aP13;
	}
	public String getP14()
	{
		return P14;
	}
	public void setP14(String aP14)
	{
		P14 = aP14;
	}
	public String getP15()
	{
		return P15;
	}
	public void setP15(String aP15)
	{
		P15 = aP15;
	}
	public String getP16()
	{
		return P16;
	}
	public void setP16(String aP16)
	{
		P16 = aP16;
	}
	public String getP17()
	{
		return P17;
	}
	public void setP17(String aP17)
	{
		P17 = aP17;
	}
	public String getP18()
	{
		return P18;
	}
	public void setP18(String aP18)
	{
		P18 = aP18;
	}
	public String getP19()
	{
		return P19;
	}
	public void setP19(String aP19)
	{
		P19 = aP19;
	}
	public String getP20()
	{
		return P20;
	}
	public void setP20(String aP20)
	{
		P20 = aP20;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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

	/**
	* 使用另外一个 LCPolOtherSchema 对象给 Schema 赋值
	* @param: aLCPolOtherSchema LCPolOtherSchema
	**/
	public void setSchema(LCPolOtherSchema aLCPolOtherSchema)
	{
		this.GrpContNo = aLCPolOtherSchema.getGrpContNo();
		this.GrpPolNo = aLCPolOtherSchema.getGrpPolNo();
		this.ContNo = aLCPolOtherSchema.getContNo();
		this.PolNo = aLCPolOtherSchema.getPolNo();
		this.DutyCode = aLCPolOtherSchema.getDutyCode();
		this.P1 = aLCPolOtherSchema.getP1();
		this.P2 = aLCPolOtherSchema.getP2();
		this.P3 = aLCPolOtherSchema.getP3();
		this.P4 = aLCPolOtherSchema.getP4();
		this.P5 = aLCPolOtherSchema.getP5();
		this.P6 = aLCPolOtherSchema.getP6();
		this.P7 = aLCPolOtherSchema.getP7();
		this.P8 = aLCPolOtherSchema.getP8();
		this.P9 = aLCPolOtherSchema.getP9();
		this.P10 = aLCPolOtherSchema.getP10();
		this.P11 = aLCPolOtherSchema.getP11();
		this.P12 = aLCPolOtherSchema.getP12();
		this.P13 = aLCPolOtherSchema.getP13();
		this.P14 = aLCPolOtherSchema.getP14();
		this.P15 = aLCPolOtherSchema.getP15();
		this.P16 = aLCPolOtherSchema.getP16();
		this.P17 = aLCPolOtherSchema.getP17();
		this.P18 = aLCPolOtherSchema.getP18();
		this.P19 = aLCPolOtherSchema.getP19();
		this.P20 = aLCPolOtherSchema.getP20();
		this.Operator = aLCPolOtherSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCPolOtherSchema.getMakeDate());
		this.MakeTime = aLCPolOtherSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPolOtherSchema.getModifyDate());
		this.ModifyTime = aLCPolOtherSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("P1") == null )
				this.P1 = null;
			else
				this.P1 = rs.getString("P1").trim();

			if( rs.getString("P2") == null )
				this.P2 = null;
			else
				this.P2 = rs.getString("P2").trim();

			if( rs.getString("P3") == null )
				this.P3 = null;
			else
				this.P3 = rs.getString("P3").trim();

			if( rs.getString("P4") == null )
				this.P4 = null;
			else
				this.P4 = rs.getString("P4").trim();

			if( rs.getString("P5") == null )
				this.P5 = null;
			else
				this.P5 = rs.getString("P5").trim();

			if( rs.getString("P6") == null )
				this.P6 = null;
			else
				this.P6 = rs.getString("P6").trim();

			if( rs.getString("P7") == null )
				this.P7 = null;
			else
				this.P7 = rs.getString("P7").trim();

			if( rs.getString("P8") == null )
				this.P8 = null;
			else
				this.P8 = rs.getString("P8").trim();

			if( rs.getString("P9") == null )
				this.P9 = null;
			else
				this.P9 = rs.getString("P9").trim();

			if( rs.getString("P10") == null )
				this.P10 = null;
			else
				this.P10 = rs.getString("P10").trim();

			if( rs.getString("P11") == null )
				this.P11 = null;
			else
				this.P11 = rs.getString("P11").trim();

			if( rs.getString("P12") == null )
				this.P12 = null;
			else
				this.P12 = rs.getString("P12").trim();

			if( rs.getString("P13") == null )
				this.P13 = null;
			else
				this.P13 = rs.getString("P13").trim();

			if( rs.getString("P14") == null )
				this.P14 = null;
			else
				this.P14 = rs.getString("P14").trim();

			if( rs.getString("P15") == null )
				this.P15 = null;
			else
				this.P15 = rs.getString("P15").trim();

			if( rs.getString("P16") == null )
				this.P16 = null;
			else
				this.P16 = rs.getString("P16").trim();

			if( rs.getString("P17") == null )
				this.P17 = null;
			else
				this.P17 = rs.getString("P17").trim();

			if( rs.getString("P18") == null )
				this.P18 = null;
			else
				this.P18 = rs.getString("P18").trim();

			if( rs.getString("P19") == null )
				this.P19 = null;
			else
				this.P19 = rs.getString("P19").trim();

			if( rs.getString("P20") == null )
				this.P20 = null;
			else
				this.P20 = rs.getString("P20").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCPolOther表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolOtherSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPolOtherSchema getSchema()
	{
		LCPolOtherSchema aLCPolOtherSchema = new LCPolOtherSchema();
		aLCPolOtherSchema.setSchema(this);
		return aLCPolOtherSchema;
	}

	public LCPolOtherDB getDB()
	{
		LCPolOtherDB aDBOper = new LCPolOtherDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolOther描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(P20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolOther>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			P1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			P2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			P3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			P4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			P5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			P6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			P7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			P8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			P9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			P10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			P11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			P12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			P13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			P14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			P15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			P16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			P17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			P18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			P19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			P20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolOtherSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P1));
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P2));
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P3));
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P4));
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P5));
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P6));
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P7));
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P8));
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P9));
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P10));
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P11));
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P12));
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P13));
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P14));
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P15));
		}
		if (FCode.equalsIgnoreCase("P16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P16));
		}
		if (FCode.equalsIgnoreCase("P17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P17));
		}
		if (FCode.equalsIgnoreCase("P18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P18));
		}
		if (FCode.equalsIgnoreCase("P19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P19));
		}
		if (FCode.equalsIgnoreCase("P20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(P20));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(P1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(P2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(P3);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(P4);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(P5);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(P6);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(P7);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(P8);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(P9);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(P10);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(P11);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(P12);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(P13);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(P14);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(P15);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(P16);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(P17);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(P18);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(P19);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(P20);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("P1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P1 = FValue.trim();
			}
			else
				P1 = null;
		}
		if (FCode.equalsIgnoreCase("P2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P2 = FValue.trim();
			}
			else
				P2 = null;
		}
		if (FCode.equalsIgnoreCase("P3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P3 = FValue.trim();
			}
			else
				P3 = null;
		}
		if (FCode.equalsIgnoreCase("P4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P4 = FValue.trim();
			}
			else
				P4 = null;
		}
		if (FCode.equalsIgnoreCase("P5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P5 = FValue.trim();
			}
			else
				P5 = null;
		}
		if (FCode.equalsIgnoreCase("P6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P6 = FValue.trim();
			}
			else
				P6 = null;
		}
		if (FCode.equalsIgnoreCase("P7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P7 = FValue.trim();
			}
			else
				P7 = null;
		}
		if (FCode.equalsIgnoreCase("P8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P8 = FValue.trim();
			}
			else
				P8 = null;
		}
		if (FCode.equalsIgnoreCase("P9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P9 = FValue.trim();
			}
			else
				P9 = null;
		}
		if (FCode.equalsIgnoreCase("P10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P10 = FValue.trim();
			}
			else
				P10 = null;
		}
		if (FCode.equalsIgnoreCase("P11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P11 = FValue.trim();
			}
			else
				P11 = null;
		}
		if (FCode.equalsIgnoreCase("P12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P12 = FValue.trim();
			}
			else
				P12 = null;
		}
		if (FCode.equalsIgnoreCase("P13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P13 = FValue.trim();
			}
			else
				P13 = null;
		}
		if (FCode.equalsIgnoreCase("P14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P14 = FValue.trim();
			}
			else
				P14 = null;
		}
		if (FCode.equalsIgnoreCase("P15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P15 = FValue.trim();
			}
			else
				P15 = null;
		}
		if (FCode.equalsIgnoreCase("P16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P16 = FValue.trim();
			}
			else
				P16 = null;
		}
		if (FCode.equalsIgnoreCase("P17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P17 = FValue.trim();
			}
			else
				P17 = null;
		}
		if (FCode.equalsIgnoreCase("P18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P18 = FValue.trim();
			}
			else
				P18 = null;
		}
		if (FCode.equalsIgnoreCase("P19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P19 = FValue.trim();
			}
			else
				P19 = null;
		}
		if (FCode.equalsIgnoreCase("P20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				P20 = FValue.trim();
			}
			else
				P20 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCPolOtherSchema other = (LCPolOtherSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& ContNo.equals(other.getContNo())
			&& PolNo.equals(other.getPolNo())
			&& DutyCode.equals(other.getDutyCode())
			&& P1.equals(other.getP1())
			&& P2.equals(other.getP2())
			&& P3.equals(other.getP3())
			&& P4.equals(other.getP4())
			&& P5.equals(other.getP5())
			&& P6.equals(other.getP6())
			&& P7.equals(other.getP7())
			&& P8.equals(other.getP8())
			&& P9.equals(other.getP9())
			&& P10.equals(other.getP10())
			&& P11.equals(other.getP11())
			&& P12.equals(other.getP12())
			&& P13.equals(other.getP13())
			&& P14.equals(other.getP14())
			&& P15.equals(other.getP15())
			&& P16.equals(other.getP16())
			&& P17.equals(other.getP17())
			&& P18.equals(other.getP18())
			&& P19.equals(other.getP19())
			&& P20.equals(other.getP20())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("PolNo") ) {
			return 3;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("P1") ) {
			return 5;
		}
		if( strFieldName.equals("P2") ) {
			return 6;
		}
		if( strFieldName.equals("P3") ) {
			return 7;
		}
		if( strFieldName.equals("P4") ) {
			return 8;
		}
		if( strFieldName.equals("P5") ) {
			return 9;
		}
		if( strFieldName.equals("P6") ) {
			return 10;
		}
		if( strFieldName.equals("P7") ) {
			return 11;
		}
		if( strFieldName.equals("P8") ) {
			return 12;
		}
		if( strFieldName.equals("P9") ) {
			return 13;
		}
		if( strFieldName.equals("P10") ) {
			return 14;
		}
		if( strFieldName.equals("P11") ) {
			return 15;
		}
		if( strFieldName.equals("P12") ) {
			return 16;
		}
		if( strFieldName.equals("P13") ) {
			return 17;
		}
		if( strFieldName.equals("P14") ) {
			return 18;
		}
		if( strFieldName.equals("P15") ) {
			return 19;
		}
		if( strFieldName.equals("P16") ) {
			return 20;
		}
		if( strFieldName.equals("P17") ) {
			return 21;
		}
		if( strFieldName.equals("P18") ) {
			return 22;
		}
		if( strFieldName.equals("P19") ) {
			return 23;
		}
		if( strFieldName.equals("P20") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 29;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPolNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "PolNo";
				break;
			case 4:
				strFieldName = "DutyCode";
				break;
			case 5:
				strFieldName = "P1";
				break;
			case 6:
				strFieldName = "P2";
				break;
			case 7:
				strFieldName = "P3";
				break;
			case 8:
				strFieldName = "P4";
				break;
			case 9:
				strFieldName = "P5";
				break;
			case 10:
				strFieldName = "P6";
				break;
			case 11:
				strFieldName = "P7";
				break;
			case 12:
				strFieldName = "P8";
				break;
			case 13:
				strFieldName = "P9";
				break;
			case 14:
				strFieldName = "P10";
				break;
			case 15:
				strFieldName = "P11";
				break;
			case 16:
				strFieldName = "P12";
				break;
			case 17:
				strFieldName = "P13";
				break;
			case 18:
				strFieldName = "P14";
				break;
			case 19:
				strFieldName = "P15";
				break;
			case 20:
				strFieldName = "P16";
				break;
			case 21:
				strFieldName = "P17";
				break;
			case 22:
				strFieldName = "P18";
				break;
			case 23:
				strFieldName = "P19";
				break;
			case 24:
				strFieldName = "P20";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "ModifyDate";
				break;
			case 29:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("P20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
