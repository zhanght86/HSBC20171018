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
import com.sinosoft.lis.db.LCGrpGenaltuaitionDB;

/*
 * <p>ClassName: LCGrpGenaltuaitionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 核保测算
 */
public class LCGrpGenaltuaitionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpGenaltuaitionSchema.class);

	// @Field
	/** 测算分类号 */
	private String BudgetNo;
	/** 其他号码 */
	private String OtherNo;
	/** 其他号码类型 */
	private String OtherNoType;
	/** 总投保人数 */
	private int ExpPeoples;
	/** Standbyflag1 */
	private String StandbyFlag1;
	/** Standbyflag2 */
	private String StandbyFlag2;
	/** Standbyflag3 */
	private String StandbyFlag3;
	/** Standbyflag4 */
	private String StandbyFlag4;
	/** Standbyflag5 */
	private String StandbyFlag5;
	/** Standbyflag6 */
	private String StandbyFlag6;
	/** Standbyflag7 */
	private String StandbyFlag7;
	/** Standbyflag8 */
	private String StandbyFlag8;
	/** Standbyflag9 */
	private String StandbyFlag9;
	/** Standbyflag10 */
	private String StandbyFlag10;
	/** Standbyflag11 */
	private String StandbyFlag11;
	/** Standbyflag12 */
	private String StandbyFlag12;
	/** Standbyflag13 */
	private String StandbyFlag13;
	/** Standbyflag14 */
	private String StandbyFlag14;
	/** Standbyflag15 */
	private String StandbyFlag15;
	/** Standbyflag16 */
	private String StandbyFlag16;
	/** Standbyflag17 */
	private String StandbyFlag17;
	/** Standbyflag18 */
	private String StandbyFlag18;
	/** Standbyflag19 */
	private String StandbyFlag19;
	/** Standbyflag20 */
	private String StandbyFlag20;
	/** Standbyflag21 */
	private String StandbyFlag21;
	/** Standbyflag22 */
	private String StandbyFlag22;
	/** Standbyflag23 */
	private String StandbyFlag23;
	/** Standbyflag24 */
	private String StandbyFlag24;
	/** 备注 */
	private String Remark;
	/** 入机日期 */
	private Date Makedate;
	/** 入机时间 */
	private String Maketime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpGenaltuaitionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BudgetNo";
		pk[1] = "OtherNo";
		pk[2] = "OtherNoType";

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
		LCGrpGenaltuaitionSchema cloned = (LCGrpGenaltuaitionSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBudgetNo()
	{
		return BudgetNo;
	}
	public void setBudgetNo(String aBudgetNo)
	{
		BudgetNo = aBudgetNo;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	public int getExpPeoples()
	{
		return ExpPeoples;
	}
	public void setExpPeoples(int aExpPeoples)
	{
		ExpPeoples = aExpPeoples;
	}
	public void setExpPeoples(String aExpPeoples)
	{
		if (aExpPeoples != null && !aExpPeoples.equals(""))
		{
			Integer tInteger = new Integer(aExpPeoples);
			int i = tInteger.intValue();
			ExpPeoples = i;
		}
	}

	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
	}
	public String getStandbyFlag4()
	{
		return StandbyFlag4;
	}
	public void setStandbyFlag4(String aStandbyFlag4)
	{
		StandbyFlag4 = aStandbyFlag4;
	}
	public String getStandbyFlag5()
	{
		return StandbyFlag5;
	}
	public void setStandbyFlag5(String aStandbyFlag5)
	{
		StandbyFlag5 = aStandbyFlag5;
	}
	public String getStandbyFlag6()
	{
		return StandbyFlag6;
	}
	public void setStandbyFlag6(String aStandbyFlag6)
	{
		StandbyFlag6 = aStandbyFlag6;
	}
	public String getStandbyFlag7()
	{
		return StandbyFlag7;
	}
	public void setStandbyFlag7(String aStandbyFlag7)
	{
		StandbyFlag7 = aStandbyFlag7;
	}
	public String getStandbyFlag8()
	{
		return StandbyFlag8;
	}
	public void setStandbyFlag8(String aStandbyFlag8)
	{
		StandbyFlag8 = aStandbyFlag8;
	}
	public String getStandbyFlag9()
	{
		return StandbyFlag9;
	}
	public void setStandbyFlag9(String aStandbyFlag9)
	{
		StandbyFlag9 = aStandbyFlag9;
	}
	public String getStandbyFlag10()
	{
		return StandbyFlag10;
	}
	public void setStandbyFlag10(String aStandbyFlag10)
	{
		StandbyFlag10 = aStandbyFlag10;
	}
	public String getStandbyFlag11()
	{
		return StandbyFlag11;
	}
	public void setStandbyFlag11(String aStandbyFlag11)
	{
		StandbyFlag11 = aStandbyFlag11;
	}
	public String getStandbyFlag12()
	{
		return StandbyFlag12;
	}
	public void setStandbyFlag12(String aStandbyFlag12)
	{
		StandbyFlag12 = aStandbyFlag12;
	}
	public String getStandbyFlag13()
	{
		return StandbyFlag13;
	}
	public void setStandbyFlag13(String aStandbyFlag13)
	{
		StandbyFlag13 = aStandbyFlag13;
	}
	public String getStandbyFlag14()
	{
		return StandbyFlag14;
	}
	public void setStandbyFlag14(String aStandbyFlag14)
	{
		StandbyFlag14 = aStandbyFlag14;
	}
	public String getStandbyFlag15()
	{
		return StandbyFlag15;
	}
	public void setStandbyFlag15(String aStandbyFlag15)
	{
		StandbyFlag15 = aStandbyFlag15;
	}
	public String getStandbyFlag16()
	{
		return StandbyFlag16;
	}
	public void setStandbyFlag16(String aStandbyFlag16)
	{
		StandbyFlag16 = aStandbyFlag16;
	}
	public String getStandbyFlag17()
	{
		return StandbyFlag17;
	}
	public void setStandbyFlag17(String aStandbyFlag17)
	{
		StandbyFlag17 = aStandbyFlag17;
	}
	public String getStandbyFlag18()
	{
		return StandbyFlag18;
	}
	public void setStandbyFlag18(String aStandbyFlag18)
	{
		StandbyFlag18 = aStandbyFlag18;
	}
	public String getStandbyFlag19()
	{
		return StandbyFlag19;
	}
	public void setStandbyFlag19(String aStandbyFlag19)
	{
		StandbyFlag19 = aStandbyFlag19;
	}
	public String getStandbyFlag20()
	{
		return StandbyFlag20;
	}
	public void setStandbyFlag20(String aStandbyFlag20)
	{
		StandbyFlag20 = aStandbyFlag20;
	}
	public String getStandbyFlag21()
	{
		return StandbyFlag21;
	}
	public void setStandbyFlag21(String aStandbyFlag21)
	{
		StandbyFlag21 = aStandbyFlag21;
	}
	public String getStandbyFlag22()
	{
		return StandbyFlag22;
	}
	public void setStandbyFlag22(String aStandbyFlag22)
	{
		StandbyFlag22 = aStandbyFlag22;
	}
	public String getStandbyFlag23()
	{
		return StandbyFlag23;
	}
	public void setStandbyFlag23(String aStandbyFlag23)
	{
		StandbyFlag23 = aStandbyFlag23;
	}
	public String getStandbyFlag24()
	{
		return StandbyFlag24;
	}
	public void setStandbyFlag24(String aStandbyFlag24)
	{
		StandbyFlag24 = aStandbyFlag24;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getMakedate()
	{
		if( Makedate != null )
			return fDate.getString(Makedate);
		else
			return null;
	}
	public void setMakedate(Date aMakedate)
	{
		Makedate = aMakedate;
	}
	public void setMakedate(String aMakedate)
	{
		if (aMakedate != null && !aMakedate.equals("") )
		{
			Makedate = fDate.getDate( aMakedate );
		}
		else
			Makedate = null;
	}

	public String getMaketime()
	{
		return Maketime;
	}
	public void setMaketime(String aMaketime)
	{
		Maketime = aMaketime;
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}

	/**
	* 使用另外一个 LCGrpGenaltuaitionSchema 对象给 Schema 赋值
	* @param: aLCGrpGenaltuaitionSchema LCGrpGenaltuaitionSchema
	**/
	public void setSchema(LCGrpGenaltuaitionSchema aLCGrpGenaltuaitionSchema)
	{
		this.BudgetNo = aLCGrpGenaltuaitionSchema.getBudgetNo();
		this.OtherNo = aLCGrpGenaltuaitionSchema.getOtherNo();
		this.OtherNoType = aLCGrpGenaltuaitionSchema.getOtherNoType();
		this.ExpPeoples = aLCGrpGenaltuaitionSchema.getExpPeoples();
		this.StandbyFlag1 = aLCGrpGenaltuaitionSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCGrpGenaltuaitionSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLCGrpGenaltuaitionSchema.getStandbyFlag3();
		this.StandbyFlag4 = aLCGrpGenaltuaitionSchema.getStandbyFlag4();
		this.StandbyFlag5 = aLCGrpGenaltuaitionSchema.getStandbyFlag5();
		this.StandbyFlag6 = aLCGrpGenaltuaitionSchema.getStandbyFlag6();
		this.StandbyFlag7 = aLCGrpGenaltuaitionSchema.getStandbyFlag7();
		this.StandbyFlag8 = aLCGrpGenaltuaitionSchema.getStandbyFlag8();
		this.StandbyFlag9 = aLCGrpGenaltuaitionSchema.getStandbyFlag9();
		this.StandbyFlag10 = aLCGrpGenaltuaitionSchema.getStandbyFlag10();
		this.StandbyFlag11 = aLCGrpGenaltuaitionSchema.getStandbyFlag11();
		this.StandbyFlag12 = aLCGrpGenaltuaitionSchema.getStandbyFlag12();
		this.StandbyFlag13 = aLCGrpGenaltuaitionSchema.getStandbyFlag13();
		this.StandbyFlag14 = aLCGrpGenaltuaitionSchema.getStandbyFlag14();
		this.StandbyFlag15 = aLCGrpGenaltuaitionSchema.getStandbyFlag15();
		this.StandbyFlag16 = aLCGrpGenaltuaitionSchema.getStandbyFlag16();
		this.StandbyFlag17 = aLCGrpGenaltuaitionSchema.getStandbyFlag17();
		this.StandbyFlag18 = aLCGrpGenaltuaitionSchema.getStandbyFlag18();
		this.StandbyFlag19 = aLCGrpGenaltuaitionSchema.getStandbyFlag19();
		this.StandbyFlag20 = aLCGrpGenaltuaitionSchema.getStandbyFlag20();
		this.StandbyFlag21 = aLCGrpGenaltuaitionSchema.getStandbyFlag21();
		this.StandbyFlag22 = aLCGrpGenaltuaitionSchema.getStandbyFlag22();
		this.StandbyFlag23 = aLCGrpGenaltuaitionSchema.getStandbyFlag23();
		this.StandbyFlag24 = aLCGrpGenaltuaitionSchema.getStandbyFlag24();
		this.Remark = aLCGrpGenaltuaitionSchema.getRemark();
		this.Makedate = fDate.getDate( aLCGrpGenaltuaitionSchema.getMakedate());
		this.Maketime = aLCGrpGenaltuaitionSchema.getMaketime();
		this.ModifyDate = fDate.getDate( aLCGrpGenaltuaitionSchema.getModifyDate());
		this.ModifyTime = aLCGrpGenaltuaitionSchema.getModifyTime();
		this.Operator = aLCGrpGenaltuaitionSchema.getOperator();
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
			if( rs.getString("BudgetNo") == null )
				this.BudgetNo = null;
			else
				this.BudgetNo = rs.getString("BudgetNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			this.ExpPeoples = rs.getInt("ExpPeoples");
			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

			if( rs.getString("StandbyFlag4") == null )
				this.StandbyFlag4 = null;
			else
				this.StandbyFlag4 = rs.getString("StandbyFlag4").trim();

			if( rs.getString("StandbyFlag5") == null )
				this.StandbyFlag5 = null;
			else
				this.StandbyFlag5 = rs.getString("StandbyFlag5").trim();

			if( rs.getString("StandbyFlag6") == null )
				this.StandbyFlag6 = null;
			else
				this.StandbyFlag6 = rs.getString("StandbyFlag6").trim();

			if( rs.getString("StandbyFlag7") == null )
				this.StandbyFlag7 = null;
			else
				this.StandbyFlag7 = rs.getString("StandbyFlag7").trim();

			if( rs.getString("StandbyFlag8") == null )
				this.StandbyFlag8 = null;
			else
				this.StandbyFlag8 = rs.getString("StandbyFlag8").trim();

			if( rs.getString("StandbyFlag9") == null )
				this.StandbyFlag9 = null;
			else
				this.StandbyFlag9 = rs.getString("StandbyFlag9").trim();

			if( rs.getString("StandbyFlag10") == null )
				this.StandbyFlag10 = null;
			else
				this.StandbyFlag10 = rs.getString("StandbyFlag10").trim();

			if( rs.getString("StandbyFlag11") == null )
				this.StandbyFlag11 = null;
			else
				this.StandbyFlag11 = rs.getString("StandbyFlag11").trim();

			if( rs.getString("StandbyFlag12") == null )
				this.StandbyFlag12 = null;
			else
				this.StandbyFlag12 = rs.getString("StandbyFlag12").trim();

			if( rs.getString("StandbyFlag13") == null )
				this.StandbyFlag13 = null;
			else
				this.StandbyFlag13 = rs.getString("StandbyFlag13").trim();

			if( rs.getString("StandbyFlag14") == null )
				this.StandbyFlag14 = null;
			else
				this.StandbyFlag14 = rs.getString("StandbyFlag14").trim();

			if( rs.getString("StandbyFlag15") == null )
				this.StandbyFlag15 = null;
			else
				this.StandbyFlag15 = rs.getString("StandbyFlag15").trim();

			if( rs.getString("StandbyFlag16") == null )
				this.StandbyFlag16 = null;
			else
				this.StandbyFlag16 = rs.getString("StandbyFlag16").trim();

			if( rs.getString("StandbyFlag17") == null )
				this.StandbyFlag17 = null;
			else
				this.StandbyFlag17 = rs.getString("StandbyFlag17").trim();

			if( rs.getString("StandbyFlag18") == null )
				this.StandbyFlag18 = null;
			else
				this.StandbyFlag18 = rs.getString("StandbyFlag18").trim();

			if( rs.getString("StandbyFlag19") == null )
				this.StandbyFlag19 = null;
			else
				this.StandbyFlag19 = rs.getString("StandbyFlag19").trim();

			if( rs.getString("StandbyFlag20") == null )
				this.StandbyFlag20 = null;
			else
				this.StandbyFlag20 = rs.getString("StandbyFlag20").trim();

			if( rs.getString("StandbyFlag21") == null )
				this.StandbyFlag21 = null;
			else
				this.StandbyFlag21 = rs.getString("StandbyFlag21").trim();

			if( rs.getString("StandbyFlag22") == null )
				this.StandbyFlag22 = null;
			else
				this.StandbyFlag22 = rs.getString("StandbyFlag22").trim();

			if( rs.getString("StandbyFlag23") == null )
				this.StandbyFlag23 = null;
			else
				this.StandbyFlag23 = rs.getString("StandbyFlag23").trim();

			if( rs.getString("StandbyFlag24") == null )
				this.StandbyFlag24 = null;
			else
				this.StandbyFlag24 = rs.getString("StandbyFlag24").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.Makedate = rs.getDate("Makedate");
			if( rs.getString("Maketime") == null )
				this.Maketime = null;
			else
				this.Maketime = rs.getString("Maketime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCGrpGenaltuaition表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpGenaltuaitionSchema getSchema()
	{
		LCGrpGenaltuaitionSchema aLCGrpGenaltuaitionSchema = new LCGrpGenaltuaitionSchema();
		aLCGrpGenaltuaitionSchema.setSchema(this);
		return aLCGrpGenaltuaitionSchema;
	}

	public LCGrpGenaltuaitionDB getDB()
	{
		LCGrpGenaltuaitionDB aDBOper = new LCGrpGenaltuaitionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpGenaltuaition描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BudgetNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExpPeoples));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag11)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag12)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag13)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag14)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag15)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag16)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag17)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag18)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag19)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag20)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag21)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag22)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag23)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag24)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( Makedate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Maketime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpGenaltuaition>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BudgetNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ExpPeoples= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StandbyFlag4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			StandbyFlag5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandbyFlag6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandbyFlag7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandbyFlag8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandbyFlag9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyFlag10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandbyFlag11 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StandbyFlag12 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyFlag13 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyFlag14 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandbyFlag15 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandbyFlag16 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			StandbyFlag17 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			StandbyFlag18 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			StandbyFlag19 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			StandbyFlag20 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			StandbyFlag21 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			StandbyFlag22 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			StandbyFlag23 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			StandbyFlag24 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			Makedate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30,SysConst.PACKAGESPILTER));
			Maketime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpGenaltuaitionSchema";
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
		if (FCode.equalsIgnoreCase("BudgetNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BudgetNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpPeoples));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag4));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag5));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag6));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag7));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag8));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag9));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag10));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag11"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag11));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag12"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag12));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag13"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag13));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag14"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag14));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag15"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag15));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag16"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag16));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag17"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag17));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag18"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag18));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag19"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag19));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag20"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag20));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag21"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag21));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag22"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag22));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag23"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag23));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag24"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag24));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("Makedate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakedate()));
		}
		if (FCode.equalsIgnoreCase("Maketime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Maketime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(BudgetNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 3:
				strFieldValue = String.valueOf(ExpPeoples);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag4);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag5);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag6);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag7);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag8);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag9);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag10);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag11);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag12);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag13);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag14);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag15);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag16);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag17);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag18);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag19);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag20);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag21);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag22);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag23);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag24);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakedate()));
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(Maketime);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("BudgetNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BudgetNo = FValue.trim();
			}
			else
				BudgetNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("ExpPeoples"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ExpPeoples = i;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag4 = FValue.trim();
			}
			else
				StandbyFlag4 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag5 = FValue.trim();
			}
			else
				StandbyFlag5 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag6 = FValue.trim();
			}
			else
				StandbyFlag6 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag7 = FValue.trim();
			}
			else
				StandbyFlag7 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag8 = FValue.trim();
			}
			else
				StandbyFlag8 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag9 = FValue.trim();
			}
			else
				StandbyFlag9 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag10 = FValue.trim();
			}
			else
				StandbyFlag10 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag11"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag11 = FValue.trim();
			}
			else
				StandbyFlag11 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag12"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag12 = FValue.trim();
			}
			else
				StandbyFlag12 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag13"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag13 = FValue.trim();
			}
			else
				StandbyFlag13 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag14"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag14 = FValue.trim();
			}
			else
				StandbyFlag14 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag15"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag15 = FValue.trim();
			}
			else
				StandbyFlag15 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag16"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag16 = FValue.trim();
			}
			else
				StandbyFlag16 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag17"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag17 = FValue.trim();
			}
			else
				StandbyFlag17 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag18"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag18 = FValue.trim();
			}
			else
				StandbyFlag18 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag19"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag19 = FValue.trim();
			}
			else
				StandbyFlag19 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag20"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag20 = FValue.trim();
			}
			else
				StandbyFlag20 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag21"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag21 = FValue.trim();
			}
			else
				StandbyFlag21 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag22"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag22 = FValue.trim();
			}
			else
				StandbyFlag22 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag23"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag23 = FValue.trim();
			}
			else
				StandbyFlag23 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag24"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag24 = FValue.trim();
			}
			else
				StandbyFlag24 = null;
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
		if (FCode.equalsIgnoreCase("Makedate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Makedate = fDate.getDate( FValue );
			}
			else
				Makedate = null;
		}
		if (FCode.equalsIgnoreCase("Maketime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Maketime = FValue.trim();
			}
			else
				Maketime = null;
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCGrpGenaltuaitionSchema other = (LCGrpGenaltuaitionSchema)otherObject;
		return
			BudgetNo.equals(other.getBudgetNo())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& ExpPeoples == other.getExpPeoples()
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
			&& StandbyFlag4.equals(other.getStandbyFlag4())
			&& StandbyFlag5.equals(other.getStandbyFlag5())
			&& StandbyFlag6.equals(other.getStandbyFlag6())
			&& StandbyFlag7.equals(other.getStandbyFlag7())
			&& StandbyFlag8.equals(other.getStandbyFlag8())
			&& StandbyFlag9.equals(other.getStandbyFlag9())
			&& StandbyFlag10.equals(other.getStandbyFlag10())
			&& StandbyFlag11.equals(other.getStandbyFlag11())
			&& StandbyFlag12.equals(other.getStandbyFlag12())
			&& StandbyFlag13.equals(other.getStandbyFlag13())
			&& StandbyFlag14.equals(other.getStandbyFlag14())
			&& StandbyFlag15.equals(other.getStandbyFlag15())
			&& StandbyFlag16.equals(other.getStandbyFlag16())
			&& StandbyFlag17.equals(other.getStandbyFlag17())
			&& StandbyFlag18.equals(other.getStandbyFlag18())
			&& StandbyFlag19.equals(other.getStandbyFlag19())
			&& StandbyFlag20.equals(other.getStandbyFlag20())
			&& StandbyFlag21.equals(other.getStandbyFlag21())
			&& StandbyFlag22.equals(other.getStandbyFlag22())
			&& StandbyFlag23.equals(other.getStandbyFlag23())
			&& StandbyFlag24.equals(other.getStandbyFlag24())
			&& Remark.equals(other.getRemark())
			&& fDate.getString(Makedate).equals(other.getMakedate())
			&& Maketime.equals(other.getMaketime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("BudgetNo") ) {
			return 0;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 2;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return 3;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 4;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 5;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return 6;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return 7;
		}
		if( strFieldName.equals("StandbyFlag5") ) {
			return 8;
		}
		if( strFieldName.equals("StandbyFlag6") ) {
			return 9;
		}
		if( strFieldName.equals("StandbyFlag7") ) {
			return 10;
		}
		if( strFieldName.equals("StandbyFlag8") ) {
			return 11;
		}
		if( strFieldName.equals("StandbyFlag9") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyFlag10") ) {
			return 13;
		}
		if( strFieldName.equals("StandbyFlag11") ) {
			return 14;
		}
		if( strFieldName.equals("StandbyFlag12") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyFlag13") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyFlag14") ) {
			return 17;
		}
		if( strFieldName.equals("StandbyFlag15") ) {
			return 18;
		}
		if( strFieldName.equals("StandbyFlag16") ) {
			return 19;
		}
		if( strFieldName.equals("StandbyFlag17") ) {
			return 20;
		}
		if( strFieldName.equals("StandbyFlag18") ) {
			return 21;
		}
		if( strFieldName.equals("StandbyFlag19") ) {
			return 22;
		}
		if( strFieldName.equals("StandbyFlag20") ) {
			return 23;
		}
		if( strFieldName.equals("StandbyFlag21") ) {
			return 24;
		}
		if( strFieldName.equals("StandbyFlag22") ) {
			return 25;
		}
		if( strFieldName.equals("StandbyFlag23") ) {
			return 26;
		}
		if( strFieldName.equals("StandbyFlag24") ) {
			return 27;
		}
		if( strFieldName.equals("Remark") ) {
			return 28;
		}
		if( strFieldName.equals("Makedate") ) {
			return 29;
		}
		if( strFieldName.equals("Maketime") ) {
			return 30;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 31;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 32;
		}
		if( strFieldName.equals("Operator") ) {
			return 33;
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
				strFieldName = "BudgetNo";
				break;
			case 1:
				strFieldName = "OtherNo";
				break;
			case 2:
				strFieldName = "OtherNoType";
				break;
			case 3:
				strFieldName = "ExpPeoples";
				break;
			case 4:
				strFieldName = "StandbyFlag1";
				break;
			case 5:
				strFieldName = "StandbyFlag2";
				break;
			case 6:
				strFieldName = "StandbyFlag3";
				break;
			case 7:
				strFieldName = "StandbyFlag4";
				break;
			case 8:
				strFieldName = "StandbyFlag5";
				break;
			case 9:
				strFieldName = "StandbyFlag6";
				break;
			case 10:
				strFieldName = "StandbyFlag7";
				break;
			case 11:
				strFieldName = "StandbyFlag8";
				break;
			case 12:
				strFieldName = "StandbyFlag9";
				break;
			case 13:
				strFieldName = "StandbyFlag10";
				break;
			case 14:
				strFieldName = "StandbyFlag11";
				break;
			case 15:
				strFieldName = "StandbyFlag12";
				break;
			case 16:
				strFieldName = "StandbyFlag13";
				break;
			case 17:
				strFieldName = "StandbyFlag14";
				break;
			case 18:
				strFieldName = "StandbyFlag15";
				break;
			case 19:
				strFieldName = "StandbyFlag16";
				break;
			case 20:
				strFieldName = "StandbyFlag17";
				break;
			case 21:
				strFieldName = "StandbyFlag18";
				break;
			case 22:
				strFieldName = "StandbyFlag19";
				break;
			case 23:
				strFieldName = "StandbyFlag20";
				break;
			case 24:
				strFieldName = "StandbyFlag21";
				break;
			case 25:
				strFieldName = "StandbyFlag22";
				break;
			case 26:
				strFieldName = "StandbyFlag23";
				break;
			case 27:
				strFieldName = "StandbyFlag24";
				break;
			case 28:
				strFieldName = "Remark";
				break;
			case 29:
				strFieldName = "Makedate";
				break;
			case 30:
				strFieldName = "Maketime";
				break;
			case 31:
				strFieldName = "ModifyDate";
				break;
			case 32:
				strFieldName = "ModifyTime";
				break;
			case 33:
				strFieldName = "Operator";
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
		if( strFieldName.equals("BudgetNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpPeoples") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag11") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag12") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag13") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag14") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag15") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag16") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag17") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag18") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag19") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag20") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag21") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag22") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag23") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag24") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Makedate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Maketime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
