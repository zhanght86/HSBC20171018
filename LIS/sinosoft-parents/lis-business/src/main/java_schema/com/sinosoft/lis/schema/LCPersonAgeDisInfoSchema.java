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
import com.sinosoft.lis.db.LCPersonAgeDisInfoDB;

/*
 * <p>ClassName: LCPersonAgeDisInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCPersonAgeDisInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPersonAgeDisInfoSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 印刷号 */
	private String PrtNo;
	/** 投保分类 */
	private String ClassCode;
	/** 平均合计男人年龄 */
	private double AverageMaleAge;
	/** 平均合计女人年龄 */
	private double AverageFemalAge;
	/** 平均在职男人年龄 */
	private double AverageOnWorkMAge;
	/** 平均在职女人年龄 */
	private double AverageOnWorkFAge;
	/** 平均退休男人年龄 */
	private double AverageOffWorkMAge;
	/** 平均退休女人年龄 */
	private double AverageOffWorkFAge;
	/** 平均配偶男人年龄 */
	private double AverageMateMAge;
	/** 平均配偶女人年龄 */
	private double AverageMateFAge;
	/** 平均子女男人年龄 */
	private double AverageYoungMAge;
	/** 平均子女女人年龄 */
	private double AverageYoungFAge;
	/** 平均其它男人年龄 */
	private double AverageOtherMAge;
	/** 平均其它女人年龄 */
	private double AverageOtherFAge;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPersonAgeDisInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LCPersonAgeDisInfoSchema cloned = (LCPersonAgeDisInfoSchema)super.clone();
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
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getClassCode()
	{
		return ClassCode;
	}
	public void setClassCode(String aClassCode)
	{
		ClassCode = aClassCode;
	}
	public double getAverageMaleAge()
	{
		return AverageMaleAge;
	}
	public void setAverageMaleAge(double aAverageMaleAge)
	{
		AverageMaleAge = aAverageMaleAge;
	}
	public void setAverageMaleAge(String aAverageMaleAge)
	{
		if (aAverageMaleAge != null && !aAverageMaleAge.equals(""))
		{
			Double tDouble = new Double(aAverageMaleAge);
			double d = tDouble.doubleValue();
			AverageMaleAge = d;
		}
	}

	public double getAverageFemalAge()
	{
		return AverageFemalAge;
	}
	public void setAverageFemalAge(double aAverageFemalAge)
	{
		AverageFemalAge = aAverageFemalAge;
	}
	public void setAverageFemalAge(String aAverageFemalAge)
	{
		if (aAverageFemalAge != null && !aAverageFemalAge.equals(""))
		{
			Double tDouble = new Double(aAverageFemalAge);
			double d = tDouble.doubleValue();
			AverageFemalAge = d;
		}
	}

	public double getAverageOnWorkMAge()
	{
		return AverageOnWorkMAge;
	}
	public void setAverageOnWorkMAge(double aAverageOnWorkMAge)
	{
		AverageOnWorkMAge = aAverageOnWorkMAge;
	}
	public void setAverageOnWorkMAge(String aAverageOnWorkMAge)
	{
		if (aAverageOnWorkMAge != null && !aAverageOnWorkMAge.equals(""))
		{
			Double tDouble = new Double(aAverageOnWorkMAge);
			double d = tDouble.doubleValue();
			AverageOnWorkMAge = d;
		}
	}

	public double getAverageOnWorkFAge()
	{
		return AverageOnWorkFAge;
	}
	public void setAverageOnWorkFAge(double aAverageOnWorkFAge)
	{
		AverageOnWorkFAge = aAverageOnWorkFAge;
	}
	public void setAverageOnWorkFAge(String aAverageOnWorkFAge)
	{
		if (aAverageOnWorkFAge != null && !aAverageOnWorkFAge.equals(""))
		{
			Double tDouble = new Double(aAverageOnWorkFAge);
			double d = tDouble.doubleValue();
			AverageOnWorkFAge = d;
		}
	}

	public double getAverageOffWorkMAge()
	{
		return AverageOffWorkMAge;
	}
	public void setAverageOffWorkMAge(double aAverageOffWorkMAge)
	{
		AverageOffWorkMAge = aAverageOffWorkMAge;
	}
	public void setAverageOffWorkMAge(String aAverageOffWorkMAge)
	{
		if (aAverageOffWorkMAge != null && !aAverageOffWorkMAge.equals(""))
		{
			Double tDouble = new Double(aAverageOffWorkMAge);
			double d = tDouble.doubleValue();
			AverageOffWorkMAge = d;
		}
	}

	public double getAverageOffWorkFAge()
	{
		return AverageOffWorkFAge;
	}
	public void setAverageOffWorkFAge(double aAverageOffWorkFAge)
	{
		AverageOffWorkFAge = aAverageOffWorkFAge;
	}
	public void setAverageOffWorkFAge(String aAverageOffWorkFAge)
	{
		if (aAverageOffWorkFAge != null && !aAverageOffWorkFAge.equals(""))
		{
			Double tDouble = new Double(aAverageOffWorkFAge);
			double d = tDouble.doubleValue();
			AverageOffWorkFAge = d;
		}
	}

	public double getAverageMateMAge()
	{
		return AverageMateMAge;
	}
	public void setAverageMateMAge(double aAverageMateMAge)
	{
		AverageMateMAge = aAverageMateMAge;
	}
	public void setAverageMateMAge(String aAverageMateMAge)
	{
		if (aAverageMateMAge != null && !aAverageMateMAge.equals(""))
		{
			Double tDouble = new Double(aAverageMateMAge);
			double d = tDouble.doubleValue();
			AverageMateMAge = d;
		}
	}

	public double getAverageMateFAge()
	{
		return AverageMateFAge;
	}
	public void setAverageMateFAge(double aAverageMateFAge)
	{
		AverageMateFAge = aAverageMateFAge;
	}
	public void setAverageMateFAge(String aAverageMateFAge)
	{
		if (aAverageMateFAge != null && !aAverageMateFAge.equals(""))
		{
			Double tDouble = new Double(aAverageMateFAge);
			double d = tDouble.doubleValue();
			AverageMateFAge = d;
		}
	}

	public double getAverageYoungMAge()
	{
		return AverageYoungMAge;
	}
	public void setAverageYoungMAge(double aAverageYoungMAge)
	{
		AverageYoungMAge = aAverageYoungMAge;
	}
	public void setAverageYoungMAge(String aAverageYoungMAge)
	{
		if (aAverageYoungMAge != null && !aAverageYoungMAge.equals(""))
		{
			Double tDouble = new Double(aAverageYoungMAge);
			double d = tDouble.doubleValue();
			AverageYoungMAge = d;
		}
	}

	public double getAverageYoungFAge()
	{
		return AverageYoungFAge;
	}
	public void setAverageYoungFAge(double aAverageYoungFAge)
	{
		AverageYoungFAge = aAverageYoungFAge;
	}
	public void setAverageYoungFAge(String aAverageYoungFAge)
	{
		if (aAverageYoungFAge != null && !aAverageYoungFAge.equals(""))
		{
			Double tDouble = new Double(aAverageYoungFAge);
			double d = tDouble.doubleValue();
			AverageYoungFAge = d;
		}
	}

	public double getAverageOtherMAge()
	{
		return AverageOtherMAge;
	}
	public void setAverageOtherMAge(double aAverageOtherMAge)
	{
		AverageOtherMAge = aAverageOtherMAge;
	}
	public void setAverageOtherMAge(String aAverageOtherMAge)
	{
		if (aAverageOtherMAge != null && !aAverageOtherMAge.equals(""))
		{
			Double tDouble = new Double(aAverageOtherMAge);
			double d = tDouble.doubleValue();
			AverageOtherMAge = d;
		}
	}

	public double getAverageOtherFAge()
	{
		return AverageOtherFAge;
	}
	public void setAverageOtherFAge(double aAverageOtherFAge)
	{
		AverageOtherFAge = aAverageOtherFAge;
	}
	public void setAverageOtherFAge(String aAverageOtherFAge)
	{
		if (aAverageOtherFAge != null && !aAverageOtherFAge.equals(""))
		{
			Double tDouble = new Double(aAverageOtherFAge);
			double d = tDouble.doubleValue();
			AverageOtherFAge = d;
		}
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
	* 使用另外一个 LCPersonAgeDisInfoSchema 对象给 Schema 赋值
	* @param: aLCPersonAgeDisInfoSchema LCPersonAgeDisInfoSchema
	**/
	public void setSchema(LCPersonAgeDisInfoSchema aLCPersonAgeDisInfoSchema)
	{
		this.SerialNo = aLCPersonAgeDisInfoSchema.getSerialNo();
		this.GrpContNo = aLCPersonAgeDisInfoSchema.getGrpContNo();
		this.PrtNo = aLCPersonAgeDisInfoSchema.getPrtNo();
		this.ClassCode = aLCPersonAgeDisInfoSchema.getClassCode();
		this.AverageMaleAge = aLCPersonAgeDisInfoSchema.getAverageMaleAge();
		this.AverageFemalAge = aLCPersonAgeDisInfoSchema.getAverageFemalAge();
		this.AverageOnWorkMAge = aLCPersonAgeDisInfoSchema.getAverageOnWorkMAge();
		this.AverageOnWorkFAge = aLCPersonAgeDisInfoSchema.getAverageOnWorkFAge();
		this.AverageOffWorkMAge = aLCPersonAgeDisInfoSchema.getAverageOffWorkMAge();
		this.AverageOffWorkFAge = aLCPersonAgeDisInfoSchema.getAverageOffWorkFAge();
		this.AverageMateMAge = aLCPersonAgeDisInfoSchema.getAverageMateMAge();
		this.AverageMateFAge = aLCPersonAgeDisInfoSchema.getAverageMateFAge();
		this.AverageYoungMAge = aLCPersonAgeDisInfoSchema.getAverageYoungMAge();
		this.AverageYoungFAge = aLCPersonAgeDisInfoSchema.getAverageYoungFAge();
		this.AverageOtherMAge = aLCPersonAgeDisInfoSchema.getAverageOtherMAge();
		this.AverageOtherFAge = aLCPersonAgeDisInfoSchema.getAverageOtherFAge();
		this.MakeDate = fDate.getDate( aLCPersonAgeDisInfoSchema.getMakeDate());
		this.MakeTime = aLCPersonAgeDisInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPersonAgeDisInfoSchema.getModifyDate());
		this.ModifyTime = aLCPersonAgeDisInfoSchema.getModifyTime();
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

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ClassCode") == null )
				this.ClassCode = null;
			else
				this.ClassCode = rs.getString("ClassCode").trim();

			this.AverageMaleAge = rs.getDouble("AverageMaleAge");
			this.AverageFemalAge = rs.getDouble("AverageFemalAge");
			this.AverageOnWorkMAge = rs.getDouble("AverageOnWorkMAge");
			this.AverageOnWorkFAge = rs.getDouble("AverageOnWorkFAge");
			this.AverageOffWorkMAge = rs.getDouble("AverageOffWorkMAge");
			this.AverageOffWorkFAge = rs.getDouble("AverageOffWorkFAge");
			this.AverageMateMAge = rs.getDouble("AverageMateMAge");
			this.AverageMateFAge = rs.getDouble("AverageMateFAge");
			this.AverageYoungMAge = rs.getDouble("AverageYoungMAge");
			this.AverageYoungFAge = rs.getDouble("AverageYoungFAge");
			this.AverageOtherMAge = rs.getDouble("AverageOtherMAge");
			this.AverageOtherFAge = rs.getDouble("AverageOtherFAge");
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
			logger.debug("数据库中的LCPersonAgeDisInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonAgeDisInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPersonAgeDisInfoSchema getSchema()
	{
		LCPersonAgeDisInfoSchema aLCPersonAgeDisInfoSchema = new LCPersonAgeDisInfoSchema();
		aLCPersonAgeDisInfoSchema.setSchema(this);
		return aLCPersonAgeDisInfoSchema;
	}

	public LCPersonAgeDisInfoDB getDB()
	{
		LCPersonAgeDisInfoDB aDBOper = new LCPersonAgeDisInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonAgeDisInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClassCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageMaleAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageFemalAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOnWorkMAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOnWorkFAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOffWorkMAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOffWorkFAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageMateMAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageMateFAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageYoungMAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageYoungFAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOtherMAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AverageOtherFAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonAgeDisInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClassCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AverageMaleAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			AverageFemalAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOnWorkMAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOnWorkFAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOffWorkMAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOffWorkFAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			AverageMateMAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			AverageMateFAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			AverageYoungMAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			AverageYoungFAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOtherMAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			AverageOtherFAge = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonAgeDisInfoSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ClassCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClassCode));
		}
		if (FCode.equalsIgnoreCase("AverageMaleAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageMaleAge));
		}
		if (FCode.equalsIgnoreCase("AverageFemalAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageFemalAge));
		}
		if (FCode.equalsIgnoreCase("AverageOnWorkMAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOnWorkMAge));
		}
		if (FCode.equalsIgnoreCase("AverageOnWorkFAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOnWorkFAge));
		}
		if (FCode.equalsIgnoreCase("AverageOffWorkMAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOffWorkMAge));
		}
		if (FCode.equalsIgnoreCase("AverageOffWorkFAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOffWorkFAge));
		}
		if (FCode.equalsIgnoreCase("AverageMateMAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageMateMAge));
		}
		if (FCode.equalsIgnoreCase("AverageMateFAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageMateFAge));
		}
		if (FCode.equalsIgnoreCase("AverageYoungMAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageYoungMAge));
		}
		if (FCode.equalsIgnoreCase("AverageYoungFAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageYoungFAge));
		}
		if (FCode.equalsIgnoreCase("AverageOtherMAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOtherMAge));
		}
		if (FCode.equalsIgnoreCase("AverageOtherFAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AverageOtherFAge));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClassCode);
				break;
			case 4:
				strFieldValue = String.valueOf(AverageMaleAge);
				break;
			case 5:
				strFieldValue = String.valueOf(AverageFemalAge);
				break;
			case 6:
				strFieldValue = String.valueOf(AverageOnWorkMAge);
				break;
			case 7:
				strFieldValue = String.valueOf(AverageOnWorkFAge);
				break;
			case 8:
				strFieldValue = String.valueOf(AverageOffWorkMAge);
				break;
			case 9:
				strFieldValue = String.valueOf(AverageOffWorkFAge);
				break;
			case 10:
				strFieldValue = String.valueOf(AverageMateMAge);
				break;
			case 11:
				strFieldValue = String.valueOf(AverageMateFAge);
				break;
			case 12:
				strFieldValue = String.valueOf(AverageYoungMAge);
				break;
			case 13:
				strFieldValue = String.valueOf(AverageYoungFAge);
				break;
			case 14:
				strFieldValue = String.valueOf(AverageOtherMAge);
				break;
			case 15:
				strFieldValue = String.valueOf(AverageOtherFAge);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("ClassCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClassCode = FValue.trim();
			}
			else
				ClassCode = null;
		}
		if (FCode.equalsIgnoreCase("AverageMaleAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageMaleAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageFemalAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageFemalAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOnWorkMAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOnWorkMAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOnWorkFAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOnWorkFAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOffWorkMAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOffWorkMAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOffWorkFAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOffWorkFAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageMateMAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageMateMAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageMateFAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageMateFAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageYoungMAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageYoungMAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageYoungFAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageYoungFAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOtherMAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOtherMAge = d;
			}
		}
		if (FCode.equalsIgnoreCase("AverageOtherFAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AverageOtherFAge = d;
			}
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
		LCPersonAgeDisInfoSchema other = (LCPersonAgeDisInfoSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ClassCode.equals(other.getClassCode())
			&& AverageMaleAge == other.getAverageMaleAge()
			&& AverageFemalAge == other.getAverageFemalAge()
			&& AverageOnWorkMAge == other.getAverageOnWorkMAge()
			&& AverageOnWorkFAge == other.getAverageOnWorkFAge()
			&& AverageOffWorkMAge == other.getAverageOffWorkMAge()
			&& AverageOffWorkFAge == other.getAverageOffWorkFAge()
			&& AverageMateMAge == other.getAverageMateMAge()
			&& AverageMateFAge == other.getAverageMateFAge()
			&& AverageYoungMAge == other.getAverageYoungMAge()
			&& AverageYoungFAge == other.getAverageYoungFAge()
			&& AverageOtherMAge == other.getAverageOtherMAge()
			&& AverageOtherFAge == other.getAverageOtherFAge()
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 2;
		}
		if( strFieldName.equals("ClassCode") ) {
			return 3;
		}
		if( strFieldName.equals("AverageMaleAge") ) {
			return 4;
		}
		if( strFieldName.equals("AverageFemalAge") ) {
			return 5;
		}
		if( strFieldName.equals("AverageOnWorkMAge") ) {
			return 6;
		}
		if( strFieldName.equals("AverageOnWorkFAge") ) {
			return 7;
		}
		if( strFieldName.equals("AverageOffWorkMAge") ) {
			return 8;
		}
		if( strFieldName.equals("AverageOffWorkFAge") ) {
			return 9;
		}
		if( strFieldName.equals("AverageMateMAge") ) {
			return 10;
		}
		if( strFieldName.equals("AverageMateFAge") ) {
			return 11;
		}
		if( strFieldName.equals("AverageYoungMAge") ) {
			return 12;
		}
		if( strFieldName.equals("AverageYoungFAge") ) {
			return 13;
		}
		if( strFieldName.equals("AverageOtherMAge") ) {
			return 14;
		}
		if( strFieldName.equals("AverageOtherFAge") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "PrtNo";
				break;
			case 3:
				strFieldName = "ClassCode";
				break;
			case 4:
				strFieldName = "AverageMaleAge";
				break;
			case 5:
				strFieldName = "AverageFemalAge";
				break;
			case 6:
				strFieldName = "AverageOnWorkMAge";
				break;
			case 7:
				strFieldName = "AverageOnWorkFAge";
				break;
			case 8:
				strFieldName = "AverageOffWorkMAge";
				break;
			case 9:
				strFieldName = "AverageOffWorkFAge";
				break;
			case 10:
				strFieldName = "AverageMateMAge";
				break;
			case 11:
				strFieldName = "AverageMateFAge";
				break;
			case 12:
				strFieldName = "AverageYoungMAge";
				break;
			case 13:
				strFieldName = "AverageYoungFAge";
				break;
			case 14:
				strFieldName = "AverageOtherMAge";
				break;
			case 15:
				strFieldName = "AverageOtherFAge";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClassCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AverageMaleAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageFemalAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOnWorkMAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOnWorkFAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOffWorkMAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOffWorkFAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageMateMAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageMateFAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageYoungMAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageYoungFAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOtherMAge") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AverageOtherFAge") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
