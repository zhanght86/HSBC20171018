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
import com.sinosoft.lis.db.PD_FieldMapDB;

/*
 * <p>ClassName: PD_FieldMapSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台
 */
public class PD_FieldMapSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_FieldMapSchema.class);
	// @Field
	/** 表名代码 */
	private String TableCode;
	/** 核心表名代码 */
	private String CoreTableCode;
	/** 产品定义平台字段代码 */
	private String PDFieldCode;
	/** 产品定义平台字段类型 */
	private String PDFieldType;
	/** 是否为产品定义平台表的主键 */
	private String IsPDTablePrimary;
	/** 核心业务系统字段代码 */
	private String CoreFieldCode;
	/** 核心业务系统字段类型 */
	private String CoreFieldType;
	/** 是否为核心业务系统表的主键 */
	private String IsCoreTablePrimary;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_FieldMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TableCode";
		pk[1] = "CoreTableCode";
		pk[2] = "PDFieldCode";
		pk[3] = "CoreFieldCode";

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
		PD_FieldMapSchema cloned = (PD_FieldMapSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTableCode()
	{
		return TableCode;
	}
	public void setTableCode(String aTableCode)
	{
		if(aTableCode!=null && aTableCode.length()>20)
			throw new IllegalArgumentException("表名代码TableCode值"+aTableCode+"的长度"+aTableCode.length()+"大于最大值20");
		TableCode = aTableCode;
	}
	public String getCoreTableCode()
	{
		return CoreTableCode;
	}
	public void setCoreTableCode(String aCoreTableCode)
	{
		if(aCoreTableCode!=null && aCoreTableCode.length()>30)
			throw new IllegalArgumentException("核心表名代码CoreTableCode值"+aCoreTableCode+"的长度"+aCoreTableCode.length()+"大于最大值30");
		CoreTableCode = aCoreTableCode;
	}
	public String getPDFieldCode()
	{
		return PDFieldCode;
	}
	public void setPDFieldCode(String aPDFieldCode)
	{
		if(aPDFieldCode!=null && aPDFieldCode.length()>30)
			throw new IllegalArgumentException("产品定义平台字段代码PDFieldCode值"+aPDFieldCode+"的长度"+aPDFieldCode.length()+"大于最大值30");
		PDFieldCode = aPDFieldCode;
	}
	public String getPDFieldType()
	{
		return PDFieldType;
	}
	public void setPDFieldType(String aPDFieldType)
	{
		if(aPDFieldType!=null && aPDFieldType.length()>30)
			throw new IllegalArgumentException("产品定义平台字段类型PDFieldType值"+aPDFieldType+"的长度"+aPDFieldType.length()+"大于最大值30");
		PDFieldType = aPDFieldType;
	}
	public String getIsPDTablePrimary()
	{
		return IsPDTablePrimary;
	}
	public void setIsPDTablePrimary(String aIsPDTablePrimary)
	{
		if(aIsPDTablePrimary!=null && aIsPDTablePrimary.length()>1)
			throw new IllegalArgumentException("是否为产品定义平台表的主键IsPDTablePrimary值"+aIsPDTablePrimary+"的长度"+aIsPDTablePrimary.length()+"大于最大值1");
		IsPDTablePrimary = aIsPDTablePrimary;
	}
	public String getCoreFieldCode()
	{
		return CoreFieldCode;
	}
	public void setCoreFieldCode(String aCoreFieldCode)
	{
		if(aCoreFieldCode!=null && aCoreFieldCode.length()>30)
			throw new IllegalArgumentException("核心业务系统字段代码CoreFieldCode值"+aCoreFieldCode+"的长度"+aCoreFieldCode.length()+"大于最大值30");
		CoreFieldCode = aCoreFieldCode;
	}
	public String getCoreFieldType()
	{
		return CoreFieldType;
	}
	public void setCoreFieldType(String aCoreFieldType)
	{
		if(aCoreFieldType!=null && aCoreFieldType.length()>30)
			throw new IllegalArgumentException("核心业务系统字段类型CoreFieldType值"+aCoreFieldType+"的长度"+aCoreFieldType.length()+"大于最大值30");
		CoreFieldType = aCoreFieldType;
	}
	public String getIsCoreTablePrimary()
	{
		return IsCoreTablePrimary;
	}
	public void setIsCoreTablePrimary(String aIsCoreTablePrimary)
	{
		if(aIsCoreTablePrimary!=null && aIsCoreTablePrimary.length()>1)
			throw new IllegalArgumentException("是否为核心业务系统表的主键IsCoreTablePrimary值"+aIsCoreTablePrimary+"的长度"+aIsCoreTablePrimary.length()+"大于最大值1");
		IsCoreTablePrimary = aIsCoreTablePrimary;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		if(aStandbyflag1!=null && aStandbyflag1.length()>20)
			throw new IllegalArgumentException("Standbyflag1Standbyflag1值"+aStandbyflag1+"的长度"+aStandbyflag1.length()+"大于最大值20");
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		if(aStandbyflag2!=null && aStandbyflag2.length()>20)
			throw new IllegalArgumentException("Standbyflag2Standbyflag2值"+aStandbyflag2+"的长度"+aStandbyflag2.length()+"大于最大值20");
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}


	/**
	* 使用另外一个 PD_FieldMapSchema 对象给 Schema 赋值
	* @param: aPD_FieldMapSchema PD_FieldMapSchema
	**/
	public void setSchema(PD_FieldMapSchema aPD_FieldMapSchema)
	{
		this.TableCode = aPD_FieldMapSchema.getTableCode();
		this.CoreTableCode = aPD_FieldMapSchema.getCoreTableCode();
		this.PDFieldCode = aPD_FieldMapSchema.getPDFieldCode();
		this.PDFieldType = aPD_FieldMapSchema.getPDFieldType();
		this.IsPDTablePrimary = aPD_FieldMapSchema.getIsPDTablePrimary();
		this.CoreFieldCode = aPD_FieldMapSchema.getCoreFieldCode();
		this.CoreFieldType = aPD_FieldMapSchema.getCoreFieldType();
		this.IsCoreTablePrimary = aPD_FieldMapSchema.getIsCoreTablePrimary();
		this.Operator = aPD_FieldMapSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_FieldMapSchema.getMakeDate());
		this.MakeTime = aPD_FieldMapSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_FieldMapSchema.getModifyDate());
		this.ModifyTime = aPD_FieldMapSchema.getModifyTime();
		this.Standbyflag1 = aPD_FieldMapSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_FieldMapSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_FieldMapSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_FieldMapSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_FieldMapSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_FieldMapSchema.getStandbyflag6();
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
			if( rs.getString("TableCode") == null )
				this.TableCode = null;
			else
				this.TableCode = rs.getString("TableCode").trim();

			if( rs.getString("CoreTableCode") == null )
				this.CoreTableCode = null;
			else
				this.CoreTableCode = rs.getString("CoreTableCode").trim();

			if( rs.getString("PDFieldCode") == null )
				this.PDFieldCode = null;
			else
				this.PDFieldCode = rs.getString("PDFieldCode").trim();

			if( rs.getString("PDFieldType") == null )
				this.PDFieldType = null;
			else
				this.PDFieldType = rs.getString("PDFieldType").trim();

			if( rs.getString("IsPDTablePrimary") == null )
				this.IsPDTablePrimary = null;
			else
				this.IsPDTablePrimary = rs.getString("IsPDTablePrimary").trim();

			if( rs.getString("CoreFieldCode") == null )
				this.CoreFieldCode = null;
			else
				this.CoreFieldCode = rs.getString("CoreFieldCode").trim();

			if( rs.getString("CoreFieldType") == null )
				this.CoreFieldType = null;
			else
				this.CoreFieldType = rs.getString("CoreFieldType").trim();

			if( rs.getString("IsCoreTablePrimary") == null )
				this.IsCoreTablePrimary = null;
			else
				this.IsCoreTablePrimary = rs.getString("IsCoreTablePrimary").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_FieldMap表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FieldMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_FieldMapSchema getSchema()
	{
		PD_FieldMapSchema aPD_FieldMapSchema = new PD_FieldMapSchema();
		aPD_FieldMapSchema.setSchema(this);
		return aPD_FieldMapSchema;
	}

	public PD_FieldMapDB getDB()
	{
		PD_FieldMapDB aDBOper = new PD_FieldMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_FieldMap描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TableCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CoreTableCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PDFieldCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PDFieldType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsPDTablePrimary)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CoreFieldCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CoreFieldType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsCoreTablePrimary)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_FieldMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CoreTableCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PDFieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PDFieldType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IsPDTablePrimary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CoreFieldCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CoreFieldType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IsCoreTablePrimary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_FieldMapSchema";
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
		if (FCode.equalsIgnoreCase("TableCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TableCode));
		}
		if (FCode.equalsIgnoreCase("CoreTableCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CoreTableCode));
		}
		if (FCode.equalsIgnoreCase("PDFieldCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PDFieldCode));
		}
		if (FCode.equalsIgnoreCase("PDFieldType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PDFieldType));
		}
		if (FCode.equalsIgnoreCase("IsPDTablePrimary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsPDTablePrimary));
		}
		if (FCode.equalsIgnoreCase("CoreFieldCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CoreFieldCode));
		}
		if (FCode.equalsIgnoreCase("CoreFieldType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CoreFieldType));
		}
		if (FCode.equalsIgnoreCase("IsCoreTablePrimary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsCoreTablePrimary));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
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
				strFieldValue = StrTool.GBKToUnicode(TableCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CoreTableCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PDFieldCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PDFieldType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IsPDTablePrimary);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CoreFieldCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CoreFieldType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IsCoreTablePrimary);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 15:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 16:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 17:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 18:
				strFieldValue = String.valueOf(Standbyflag6);
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

		if (FCode.equalsIgnoreCase("TableCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TableCode = FValue.trim();
			}
			else
				TableCode = null;
		}
		if (FCode.equalsIgnoreCase("CoreTableCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CoreTableCode = FValue.trim();
			}
			else
				CoreTableCode = null;
		}
		if (FCode.equalsIgnoreCase("PDFieldCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PDFieldCode = FValue.trim();
			}
			else
				PDFieldCode = null;
		}
		if (FCode.equalsIgnoreCase("PDFieldType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PDFieldType = FValue.trim();
			}
			else
				PDFieldType = null;
		}
		if (FCode.equalsIgnoreCase("IsPDTablePrimary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsPDTablePrimary = FValue.trim();
			}
			else
				IsPDTablePrimary = null;
		}
		if (FCode.equalsIgnoreCase("CoreFieldCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CoreFieldCode = FValue.trim();
			}
			else
				CoreFieldCode = null;
		}
		if (FCode.equalsIgnoreCase("CoreFieldType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CoreFieldType = FValue.trim();
			}
			else
				CoreFieldType = null;
		}
		if (FCode.equalsIgnoreCase("IsCoreTablePrimary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsCoreTablePrimary = FValue.trim();
			}
			else
				IsCoreTablePrimary = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_FieldMapSchema other = (PD_FieldMapSchema)otherObject;
		return
			TableCode.equals(other.getTableCode())
			&& CoreTableCode.equals(other.getCoreTableCode())
			&& PDFieldCode.equals(other.getPDFieldCode())
			&& PDFieldType.equals(other.getPDFieldType())
			&& IsPDTablePrimary.equals(other.getIsPDTablePrimary())
			&& CoreFieldCode.equals(other.getCoreFieldCode())
			&& CoreFieldType.equals(other.getCoreFieldType())
			&& IsCoreTablePrimary.equals(other.getIsCoreTablePrimary())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("TableCode") ) {
			return 0;
		}
		if( strFieldName.equals("CoreTableCode") ) {
			return 1;
		}
		if( strFieldName.equals("PDFieldCode") ) {
			return 2;
		}
		if( strFieldName.equals("PDFieldType") ) {
			return 3;
		}
		if( strFieldName.equals("IsPDTablePrimary") ) {
			return 4;
		}
		if( strFieldName.equals("CoreFieldCode") ) {
			return 5;
		}
		if( strFieldName.equals("CoreFieldType") ) {
			return 6;
		}
		if( strFieldName.equals("IsCoreTablePrimary") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 13;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 14;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 15;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 16;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 17;
		}
		if( strFieldName.equals("Standbyflag6") ) {
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
				strFieldName = "TableCode";
				break;
			case 1:
				strFieldName = "CoreTableCode";
				break;
			case 2:
				strFieldName = "PDFieldCode";
				break;
			case 3:
				strFieldName = "PDFieldType";
				break;
			case 4:
				strFieldName = "IsPDTablePrimary";
				break;
			case 5:
				strFieldName = "CoreFieldCode";
				break;
			case 6:
				strFieldName = "CoreFieldType";
				break;
			case 7:
				strFieldName = "IsCoreTablePrimary";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "Standbyflag1";
				break;
			case 14:
				strFieldName = "Standbyflag2";
				break;
			case 15:
				strFieldName = "Standbyflag3";
				break;
			case 16:
				strFieldName = "Standbyflag4";
				break;
			case 17:
				strFieldName = "Standbyflag5";
				break;
			case 18:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("TableCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CoreTableCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PDFieldCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PDFieldType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsPDTablePrimary") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CoreFieldCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CoreFieldType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsCoreTablePrimary") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
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
