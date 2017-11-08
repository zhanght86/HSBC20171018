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
import com.sinosoft.lis.db.FinTransTraceDB;

/*
 * <p>ClassName: FinTransTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class FinTransTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FinTransTraceSchema.class);
	// @Field
	/** 交易流水号 */
	private String TransSerialNo;
	/** 交易类型编码 */
	private String TransTypeCode;
	/** 交易日期 */
	private Date TransDate;
	/** 其它号码 */
	private String OtherNo;
	/** 属性字段1 */
	private String Segment1;
	/** 属性字段2 */
	private String Segment2;
	/** 属性字段3 */
	private String Segment3;
	/** 属性字段4 */
	private String Segment4;
	/** 属性字段5 */
	private String Segment5;
	/** 属性字段6 */
	private String Segment6;
	/** 属性字段7 */
	private String Segment7;
	/** 属性字段8 */
	private String Segment8;
	/** 属性字段9 */
	private String Segment9;
	/** 属性字段10 */
	private String Segment10;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FinTransTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TransSerialNo";

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
		FinTransTraceSchema cloned = (FinTransTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTransSerialNo()
	{
		return TransSerialNo;
	}
	public void setTransSerialNo(String aTransSerialNo)
	{
		if(aTransSerialNo!=null && aTransSerialNo.length()>20)
			throw new IllegalArgumentException("交易流水号TransSerialNo值"+aTransSerialNo+"的长度"+aTransSerialNo.length()+"大于最大值20");
		TransSerialNo = aTransSerialNo;
	}
	public String getTransTypeCode()
	{
		return TransTypeCode;
	}
	public void setTransTypeCode(String aTransTypeCode)
	{
		if(aTransTypeCode!=null && aTransTypeCode.length()>10)
			throw new IllegalArgumentException("交易类型编码TransTypeCode值"+aTransTypeCode+"的长度"+aTransTypeCode.length()+"大于最大值10");
		TransTypeCode = aTransTypeCode;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		if(aOtherNo!=null && aOtherNo.length()>20)
			throw new IllegalArgumentException("其它号码OtherNo值"+aOtherNo+"的长度"+aOtherNo.length()+"大于最大值20");
		OtherNo = aOtherNo;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>30)
			throw new IllegalArgumentException("属性字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值30");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>30)
			throw new IllegalArgumentException("属性字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值30");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>30)
			throw new IllegalArgumentException("属性字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值30");
		Segment3 = aSegment3;
	}
	public String getSegment4()
	{
		return Segment4;
	}
	public void setSegment4(String aSegment4)
	{
		if(aSegment4!=null && aSegment4.length()>30)
			throw new IllegalArgumentException("属性字段4Segment4值"+aSegment4+"的长度"+aSegment4.length()+"大于最大值30");
		Segment4 = aSegment4;
	}
	public String getSegment5()
	{
		return Segment5;
	}
	public void setSegment5(String aSegment5)
	{
		if(aSegment5!=null && aSegment5.length()>30)
			throw new IllegalArgumentException("属性字段5Segment5值"+aSegment5+"的长度"+aSegment5.length()+"大于最大值30");
		Segment5 = aSegment5;
	}
	public String getSegment6()
	{
		return Segment6;
	}
	public void setSegment6(String aSegment6)
	{
		if(aSegment6!=null && aSegment6.length()>30)
			throw new IllegalArgumentException("属性字段6Segment6值"+aSegment6+"的长度"+aSegment6.length()+"大于最大值30");
		Segment6 = aSegment6;
	}
	public String getSegment7()
	{
		return Segment7;
	}
	public void setSegment7(String aSegment7)
	{
		if(aSegment7!=null && aSegment7.length()>30)
			throw new IllegalArgumentException("属性字段7Segment7值"+aSegment7+"的长度"+aSegment7.length()+"大于最大值30");
		Segment7 = aSegment7;
	}
	public String getSegment8()
	{
		return Segment8;
	}
	public void setSegment8(String aSegment8)
	{
		if(aSegment8!=null && aSegment8.length()>30)
			throw new IllegalArgumentException("属性字段8Segment8值"+aSegment8+"的长度"+aSegment8.length()+"大于最大值30");
		Segment8 = aSegment8;
	}
	public String getSegment9()
	{
		return Segment9;
	}
	public void setSegment9(String aSegment9)
	{
		if(aSegment9!=null && aSegment9.length()>30)
			throw new IllegalArgumentException("属性字段9Segment9值"+aSegment9+"的长度"+aSegment9.length()+"大于最大值30");
		Segment9 = aSegment9;
	}
	public String getSegment10()
	{
		return Segment10;
	}
	public void setSegment10(String aSegment10)
	{
		if(aSegment10!=null && aSegment10.length()>30)
			throw new IllegalArgumentException("属性字段10Segment10值"+aSegment10+"的长度"+aSegment10.length()+"大于最大值30");
		Segment10 = aSegment10;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 FinTransTraceSchema 对象给 Schema 赋值
	* @param: aFinTransTraceSchema FinTransTraceSchema
	**/
	public void setSchema(FinTransTraceSchema aFinTransTraceSchema)
	{
		this.TransSerialNo = aFinTransTraceSchema.getTransSerialNo();
		this.TransTypeCode = aFinTransTraceSchema.getTransTypeCode();
		this.TransDate = fDate.getDate( aFinTransTraceSchema.getTransDate());
		this.OtherNo = aFinTransTraceSchema.getOtherNo();
		this.Segment1 = aFinTransTraceSchema.getSegment1();
		this.Segment2 = aFinTransTraceSchema.getSegment2();
		this.Segment3 = aFinTransTraceSchema.getSegment3();
		this.Segment4 = aFinTransTraceSchema.getSegment4();
		this.Segment5 = aFinTransTraceSchema.getSegment5();
		this.Segment6 = aFinTransTraceSchema.getSegment6();
		this.Segment7 = aFinTransTraceSchema.getSegment7();
		this.Segment8 = aFinTransTraceSchema.getSegment8();
		this.Segment9 = aFinTransTraceSchema.getSegment9();
		this.Segment10 = aFinTransTraceSchema.getSegment10();
		this.ManageCom = aFinTransTraceSchema.getManageCom();
		this.ComCode = aFinTransTraceSchema.getComCode();
		this.MakeOperator = aFinTransTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aFinTransTraceSchema.getMakeDate());
		this.MakeTime = aFinTransTraceSchema.getMakeTime();
		this.ModifyOperator = aFinTransTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aFinTransTraceSchema.getModifyDate());
		this.ModifyTime = aFinTransTraceSchema.getModifyTime();
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
			if( rs.getString("TransSerialNo") == null )
				this.TransSerialNo = null;
			else
				this.TransSerialNo = rs.getString("TransSerialNo").trim();

			if( rs.getString("TransTypeCode") == null )
				this.TransTypeCode = null;
			else
				this.TransTypeCode = rs.getString("TransTypeCode").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("Segment4") == null )
				this.Segment4 = null;
			else
				this.Segment4 = rs.getString("Segment4").trim();

			if( rs.getString("Segment5") == null )
				this.Segment5 = null;
			else
				this.Segment5 = rs.getString("Segment5").trim();

			if( rs.getString("Segment6") == null )
				this.Segment6 = null;
			else
				this.Segment6 = rs.getString("Segment6").trim();

			if( rs.getString("Segment7") == null )
				this.Segment7 = null;
			else
				this.Segment7 = rs.getString("Segment7").trim();

			if( rs.getString("Segment8") == null )
				this.Segment8 = null;
			else
				this.Segment8 = rs.getString("Segment8").trim();

			if( rs.getString("Segment9") == null )
				this.Segment9 = null;
			else
				this.Segment9 = rs.getString("Segment9").trim();

			if( rs.getString("Segment10") == null )
				this.Segment10 = null;
			else
				this.Segment10 = rs.getString("Segment10").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FinTransTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinTransTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FinTransTraceSchema getSchema()
	{
		FinTransTraceSchema aFinTransTraceSchema = new FinTransTraceSchema();
		aFinTransTraceSchema.setSchema(this);
		return aFinTransTraceSchema;
	}

	public FinTransTraceDB getDB()
	{
		FinTransTraceDB aDBOper = new FinTransTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinTransTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TransSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment6)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment7)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment8)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment9)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment10)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFinTransTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TransTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Segment4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Segment5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Segment6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Segment7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Segment8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Segment9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinTransTraceSchema";
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
		if (FCode.equalsIgnoreCase("TransSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransSerialNo));
		}
		if (FCode.equalsIgnoreCase("TransTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTypeCode));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment4));
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment5));
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment6));
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment7));
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment8));
		}
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment9));
		}
		if (FCode.equalsIgnoreCase("Segment10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment10));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(TransSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TransTypeCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Segment4);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Segment5);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Segment6);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Segment7);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Segment8);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Segment9);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment10);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 21:
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

		if (FCode.equalsIgnoreCase("TransSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransSerialNo = FValue.trim();
			}
			else
				TransSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("TransTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTypeCode = FValue.trim();
			}
			else
				TransTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
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
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("Segment4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment4 = FValue.trim();
			}
			else
				Segment4 = null;
		}
		if (FCode.equalsIgnoreCase("Segment5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment5 = FValue.trim();
			}
			else
				Segment5 = null;
		}
		if (FCode.equalsIgnoreCase("Segment6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment6 = FValue.trim();
			}
			else
				Segment6 = null;
		}
		if (FCode.equalsIgnoreCase("Segment7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment7 = FValue.trim();
			}
			else
				Segment7 = null;
		}
		if (FCode.equalsIgnoreCase("Segment8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment8 = FValue.trim();
			}
			else
				Segment8 = null;
		}
		if (FCode.equalsIgnoreCase("Segment9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment9 = FValue.trim();
			}
			else
				Segment9 = null;
		}
		if (FCode.equalsIgnoreCase("Segment10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment10 = FValue.trim();
			}
			else
				Segment10 = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		FinTransTraceSchema other = (FinTransTraceSchema)otherObject;
		return
			TransSerialNo.equals(other.getTransSerialNo())
			&& TransTypeCode.equals(other.getTransTypeCode())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& OtherNo.equals(other.getOtherNo())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& Segment4.equals(other.getSegment4())
			&& Segment5.equals(other.getSegment5())
			&& Segment6.equals(other.getSegment6())
			&& Segment7.equals(other.getSegment7())
			&& Segment8.equals(other.getSegment8())
			&& Segment9.equals(other.getSegment9())
			&& Segment10.equals(other.getSegment10())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("TransSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("TransTypeCode") ) {
			return 1;
		}
		if( strFieldName.equals("TransDate") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("Segment1") ) {
			return 4;
		}
		if( strFieldName.equals("Segment2") ) {
			return 5;
		}
		if( strFieldName.equals("Segment3") ) {
			return 6;
		}
		if( strFieldName.equals("Segment4") ) {
			return 7;
		}
		if( strFieldName.equals("Segment5") ) {
			return 8;
		}
		if( strFieldName.equals("Segment6") ) {
			return 9;
		}
		if( strFieldName.equals("Segment7") ) {
			return 10;
		}
		if( strFieldName.equals("Segment8") ) {
			return 11;
		}
		if( strFieldName.equals("Segment9") ) {
			return 12;
		}
		if( strFieldName.equals("Segment10") ) {
			return 13;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 14;
		}
		if( strFieldName.equals("ComCode") ) {
			return 15;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 21;
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
				strFieldName = "TransSerialNo";
				break;
			case 1:
				strFieldName = "TransTypeCode";
				break;
			case 2:
				strFieldName = "TransDate";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "Segment1";
				break;
			case 5:
				strFieldName = "Segment2";
				break;
			case 6:
				strFieldName = "Segment3";
				break;
			case 7:
				strFieldName = "Segment4";
				break;
			case 8:
				strFieldName = "Segment5";
				break;
			case 9:
				strFieldName = "Segment6";
				break;
			case 10:
				strFieldName = "Segment7";
				break;
			case 11:
				strFieldName = "Segment8";
				break;
			case 12:
				strFieldName = "Segment9";
				break;
			case 13:
				strFieldName = "Segment10";
				break;
			case 14:
				strFieldName = "ManageCom";
				break;
			case 15:
				strFieldName = "ComCode";
				break;
			case 16:
				strFieldName = "MakeOperator";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyOperator";
				break;
			case 20:
				strFieldName = "ModifyDate";
				break;
			case 21:
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
		if( strFieldName.equals("TransSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
