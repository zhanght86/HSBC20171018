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
import com.sinosoft.lis.db.LCPersonClassDisInfoDB;

/*
 * <p>ClassName: LCPersonClassDisInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCPersonClassDisInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPersonClassDisInfoSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 印刷号 */
	private String PrtNo;
	/** 投保分类 */
	private String ClassCode;
	/** 总合计男人数 */
	private int TotalMaleCount;
	/** 总合计女人数 */
	private int TotalFemalCount;
	/** 总在职男人数 */
	private int TotalOnWorkMCount;
	/** 总在职女人数 */
	private int TotalOnWorkFCount;
	/** 总退休男人数 */
	private int TotalOffWorkMCount;
	/** 总退休女人数 */
	private int TotalOffWorkFCount;
	/** 总配偶男人数 */
	private int TotalMateMCount;
	/** 总配偶女人数 */
	private int TotalMateFCount;
	/** 总子女男人数 */
	private int TotalYoungMCount;
	/** 总子女女人数 */
	private int TotalYoungFCount;
	/** 总其它男人数 */
	private int TotalOtherMCount;
	/** 总其它女人数 */
	private int TotalOtherFCount;
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
	public LCPersonClassDisInfoSchema()
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
		LCPersonClassDisInfoSchema cloned = (LCPersonClassDisInfoSchema)super.clone();
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
	public int getTotalMaleCount()
	{
		return TotalMaleCount;
	}
	public void setTotalMaleCount(int aTotalMaleCount)
	{
		TotalMaleCount = aTotalMaleCount;
	}
	public void setTotalMaleCount(String aTotalMaleCount)
	{
		if (aTotalMaleCount != null && !aTotalMaleCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalMaleCount);
			int i = tInteger.intValue();
			TotalMaleCount = i;
		}
	}

	public int getTotalFemalCount()
	{
		return TotalFemalCount;
	}
	public void setTotalFemalCount(int aTotalFemalCount)
	{
		TotalFemalCount = aTotalFemalCount;
	}
	public void setTotalFemalCount(String aTotalFemalCount)
	{
		if (aTotalFemalCount != null && !aTotalFemalCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalFemalCount);
			int i = tInteger.intValue();
			TotalFemalCount = i;
		}
	}

	public int getTotalOnWorkMCount()
	{
		return TotalOnWorkMCount;
	}
	public void setTotalOnWorkMCount(int aTotalOnWorkMCount)
	{
		TotalOnWorkMCount = aTotalOnWorkMCount;
	}
	public void setTotalOnWorkMCount(String aTotalOnWorkMCount)
	{
		if (aTotalOnWorkMCount != null && !aTotalOnWorkMCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOnWorkMCount);
			int i = tInteger.intValue();
			TotalOnWorkMCount = i;
		}
	}

	public int getTotalOnWorkFCount()
	{
		return TotalOnWorkFCount;
	}
	public void setTotalOnWorkFCount(int aTotalOnWorkFCount)
	{
		TotalOnWorkFCount = aTotalOnWorkFCount;
	}
	public void setTotalOnWorkFCount(String aTotalOnWorkFCount)
	{
		if (aTotalOnWorkFCount != null && !aTotalOnWorkFCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOnWorkFCount);
			int i = tInteger.intValue();
			TotalOnWorkFCount = i;
		}
	}

	public int getTotalOffWorkMCount()
	{
		return TotalOffWorkMCount;
	}
	public void setTotalOffWorkMCount(int aTotalOffWorkMCount)
	{
		TotalOffWorkMCount = aTotalOffWorkMCount;
	}
	public void setTotalOffWorkMCount(String aTotalOffWorkMCount)
	{
		if (aTotalOffWorkMCount != null && !aTotalOffWorkMCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOffWorkMCount);
			int i = tInteger.intValue();
			TotalOffWorkMCount = i;
		}
	}

	public int getTotalOffWorkFCount()
	{
		return TotalOffWorkFCount;
	}
	public void setTotalOffWorkFCount(int aTotalOffWorkFCount)
	{
		TotalOffWorkFCount = aTotalOffWorkFCount;
	}
	public void setTotalOffWorkFCount(String aTotalOffWorkFCount)
	{
		if (aTotalOffWorkFCount != null && !aTotalOffWorkFCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOffWorkFCount);
			int i = tInteger.intValue();
			TotalOffWorkFCount = i;
		}
	}

	public int getTotalMateMCount()
	{
		return TotalMateMCount;
	}
	public void setTotalMateMCount(int aTotalMateMCount)
	{
		TotalMateMCount = aTotalMateMCount;
	}
	public void setTotalMateMCount(String aTotalMateMCount)
	{
		if (aTotalMateMCount != null && !aTotalMateMCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalMateMCount);
			int i = tInteger.intValue();
			TotalMateMCount = i;
		}
	}

	public int getTotalMateFCount()
	{
		return TotalMateFCount;
	}
	public void setTotalMateFCount(int aTotalMateFCount)
	{
		TotalMateFCount = aTotalMateFCount;
	}
	public void setTotalMateFCount(String aTotalMateFCount)
	{
		if (aTotalMateFCount != null && !aTotalMateFCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalMateFCount);
			int i = tInteger.intValue();
			TotalMateFCount = i;
		}
	}

	public int getTotalYoungMCount()
	{
		return TotalYoungMCount;
	}
	public void setTotalYoungMCount(int aTotalYoungMCount)
	{
		TotalYoungMCount = aTotalYoungMCount;
	}
	public void setTotalYoungMCount(String aTotalYoungMCount)
	{
		if (aTotalYoungMCount != null && !aTotalYoungMCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalYoungMCount);
			int i = tInteger.intValue();
			TotalYoungMCount = i;
		}
	}

	public int getTotalYoungFCount()
	{
		return TotalYoungFCount;
	}
	public void setTotalYoungFCount(int aTotalYoungFCount)
	{
		TotalYoungFCount = aTotalYoungFCount;
	}
	public void setTotalYoungFCount(String aTotalYoungFCount)
	{
		if (aTotalYoungFCount != null && !aTotalYoungFCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalYoungFCount);
			int i = tInteger.intValue();
			TotalYoungFCount = i;
		}
	}

	public int getTotalOtherMCount()
	{
		return TotalOtherMCount;
	}
	public void setTotalOtherMCount(int aTotalOtherMCount)
	{
		TotalOtherMCount = aTotalOtherMCount;
	}
	public void setTotalOtherMCount(String aTotalOtherMCount)
	{
		if (aTotalOtherMCount != null && !aTotalOtherMCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOtherMCount);
			int i = tInteger.intValue();
			TotalOtherMCount = i;
		}
	}

	public int getTotalOtherFCount()
	{
		return TotalOtherFCount;
	}
	public void setTotalOtherFCount(int aTotalOtherFCount)
	{
		TotalOtherFCount = aTotalOtherFCount;
	}
	public void setTotalOtherFCount(String aTotalOtherFCount)
	{
		if (aTotalOtherFCount != null && !aTotalOtherFCount.equals(""))
		{
			Integer tInteger = new Integer(aTotalOtherFCount);
			int i = tInteger.intValue();
			TotalOtherFCount = i;
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
	* 使用另外一个 LCPersonClassDisInfoSchema 对象给 Schema 赋值
	* @param: aLCPersonClassDisInfoSchema LCPersonClassDisInfoSchema
	**/
	public void setSchema(LCPersonClassDisInfoSchema aLCPersonClassDisInfoSchema)
	{
		this.SerialNo = aLCPersonClassDisInfoSchema.getSerialNo();
		this.GrpContNo = aLCPersonClassDisInfoSchema.getGrpContNo();
		this.PrtNo = aLCPersonClassDisInfoSchema.getPrtNo();
		this.ClassCode = aLCPersonClassDisInfoSchema.getClassCode();
		this.TotalMaleCount = aLCPersonClassDisInfoSchema.getTotalMaleCount();
		this.TotalFemalCount = aLCPersonClassDisInfoSchema.getTotalFemalCount();
		this.TotalOnWorkMCount = aLCPersonClassDisInfoSchema.getTotalOnWorkMCount();
		this.TotalOnWorkFCount = aLCPersonClassDisInfoSchema.getTotalOnWorkFCount();
		this.TotalOffWorkMCount = aLCPersonClassDisInfoSchema.getTotalOffWorkMCount();
		this.TotalOffWorkFCount = aLCPersonClassDisInfoSchema.getTotalOffWorkFCount();
		this.TotalMateMCount = aLCPersonClassDisInfoSchema.getTotalMateMCount();
		this.TotalMateFCount = aLCPersonClassDisInfoSchema.getTotalMateFCount();
		this.TotalYoungMCount = aLCPersonClassDisInfoSchema.getTotalYoungMCount();
		this.TotalYoungFCount = aLCPersonClassDisInfoSchema.getTotalYoungFCount();
		this.TotalOtherMCount = aLCPersonClassDisInfoSchema.getTotalOtherMCount();
		this.TotalOtherFCount = aLCPersonClassDisInfoSchema.getTotalOtherFCount();
		this.MakeDate = fDate.getDate( aLCPersonClassDisInfoSchema.getMakeDate());
		this.MakeTime = aLCPersonClassDisInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPersonClassDisInfoSchema.getModifyDate());
		this.ModifyTime = aLCPersonClassDisInfoSchema.getModifyTime();
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

			this.TotalMaleCount = rs.getInt("TotalMaleCount");
			this.TotalFemalCount = rs.getInt("TotalFemalCount");
			this.TotalOnWorkMCount = rs.getInt("TotalOnWorkMCount");
			this.TotalOnWorkFCount = rs.getInt("TotalOnWorkFCount");
			this.TotalOffWorkMCount = rs.getInt("TotalOffWorkMCount");
			this.TotalOffWorkFCount = rs.getInt("TotalOffWorkFCount");
			this.TotalMateMCount = rs.getInt("TotalMateMCount");
			this.TotalMateFCount = rs.getInt("TotalMateFCount");
			this.TotalYoungMCount = rs.getInt("TotalYoungMCount");
			this.TotalYoungFCount = rs.getInt("TotalYoungFCount");
			this.TotalOtherMCount = rs.getInt("TotalOtherMCount");
			this.TotalOtherFCount = rs.getInt("TotalOtherFCount");
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
			logger.debug("数据库中的LCPersonClassDisInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonClassDisInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPersonClassDisInfoSchema getSchema()
	{
		LCPersonClassDisInfoSchema aLCPersonClassDisInfoSchema = new LCPersonClassDisInfoSchema();
		aLCPersonClassDisInfoSchema.setSchema(this);
		return aLCPersonClassDisInfoSchema;
	}

	public LCPersonClassDisInfoDB getDB()
	{
		LCPersonClassDisInfoDB aDBOper = new LCPersonClassDisInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonClassDisInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClassCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalMaleCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalFemalCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOnWorkMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOnWorkFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOffWorkMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOffWorkFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalMateMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalMateFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalYoungMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalYoungFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOtherMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TotalOtherFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonClassDisInfo>历史记账凭证主表信息</A>表字段
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
			TotalMaleCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			TotalFemalCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			TotalOnWorkMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			TotalOnWorkFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			TotalOffWorkMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			TotalOffWorkFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			TotalMateMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			TotalMateFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			TotalYoungMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			TotalYoungFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			TotalOtherMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			TotalOtherFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonClassDisInfoSchema";
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
		if (FCode.equalsIgnoreCase("TotalMaleCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalMaleCount));
		}
		if (FCode.equalsIgnoreCase("TotalFemalCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalFemalCount));
		}
		if (FCode.equalsIgnoreCase("TotalOnWorkMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOnWorkMCount));
		}
		if (FCode.equalsIgnoreCase("TotalOnWorkFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOnWorkFCount));
		}
		if (FCode.equalsIgnoreCase("TotalOffWorkMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOffWorkMCount));
		}
		if (FCode.equalsIgnoreCase("TotalOffWorkFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOffWorkFCount));
		}
		if (FCode.equalsIgnoreCase("TotalMateMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalMateMCount));
		}
		if (FCode.equalsIgnoreCase("TotalMateFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalMateFCount));
		}
		if (FCode.equalsIgnoreCase("TotalYoungMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalYoungMCount));
		}
		if (FCode.equalsIgnoreCase("TotalYoungFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalYoungFCount));
		}
		if (FCode.equalsIgnoreCase("TotalOtherMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOtherMCount));
		}
		if (FCode.equalsIgnoreCase("TotalOtherFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TotalOtherFCount));
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
				strFieldValue = String.valueOf(TotalMaleCount);
				break;
			case 5:
				strFieldValue = String.valueOf(TotalFemalCount);
				break;
			case 6:
				strFieldValue = String.valueOf(TotalOnWorkMCount);
				break;
			case 7:
				strFieldValue = String.valueOf(TotalOnWorkFCount);
				break;
			case 8:
				strFieldValue = String.valueOf(TotalOffWorkMCount);
				break;
			case 9:
				strFieldValue = String.valueOf(TotalOffWorkFCount);
				break;
			case 10:
				strFieldValue = String.valueOf(TotalMateMCount);
				break;
			case 11:
				strFieldValue = String.valueOf(TotalMateFCount);
				break;
			case 12:
				strFieldValue = String.valueOf(TotalYoungMCount);
				break;
			case 13:
				strFieldValue = String.valueOf(TotalYoungFCount);
				break;
			case 14:
				strFieldValue = String.valueOf(TotalOtherMCount);
				break;
			case 15:
				strFieldValue = String.valueOf(TotalOtherFCount);
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
		if (FCode.equalsIgnoreCase("TotalMaleCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalMaleCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalFemalCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalFemalCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOnWorkMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOnWorkMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOnWorkFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOnWorkFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOffWorkMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOffWorkMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOffWorkFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOffWorkFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalMateMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalMateMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalMateFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalMateFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalYoungMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalYoungMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalYoungFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalYoungFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOtherMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOtherMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("TotalOtherFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TotalOtherFCount = i;
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
		LCPersonClassDisInfoSchema other = (LCPersonClassDisInfoSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ClassCode.equals(other.getClassCode())
			&& TotalMaleCount == other.getTotalMaleCount()
			&& TotalFemalCount == other.getTotalFemalCount()
			&& TotalOnWorkMCount == other.getTotalOnWorkMCount()
			&& TotalOnWorkFCount == other.getTotalOnWorkFCount()
			&& TotalOffWorkMCount == other.getTotalOffWorkMCount()
			&& TotalOffWorkFCount == other.getTotalOffWorkFCount()
			&& TotalMateMCount == other.getTotalMateMCount()
			&& TotalMateFCount == other.getTotalMateFCount()
			&& TotalYoungMCount == other.getTotalYoungMCount()
			&& TotalYoungFCount == other.getTotalYoungFCount()
			&& TotalOtherMCount == other.getTotalOtherMCount()
			&& TotalOtherFCount == other.getTotalOtherFCount()
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
		if( strFieldName.equals("TotalMaleCount") ) {
			return 4;
		}
		if( strFieldName.equals("TotalFemalCount") ) {
			return 5;
		}
		if( strFieldName.equals("TotalOnWorkMCount") ) {
			return 6;
		}
		if( strFieldName.equals("TotalOnWorkFCount") ) {
			return 7;
		}
		if( strFieldName.equals("TotalOffWorkMCount") ) {
			return 8;
		}
		if( strFieldName.equals("TotalOffWorkFCount") ) {
			return 9;
		}
		if( strFieldName.equals("TotalMateMCount") ) {
			return 10;
		}
		if( strFieldName.equals("TotalMateFCount") ) {
			return 11;
		}
		if( strFieldName.equals("TotalYoungMCount") ) {
			return 12;
		}
		if( strFieldName.equals("TotalYoungFCount") ) {
			return 13;
		}
		if( strFieldName.equals("TotalOtherMCount") ) {
			return 14;
		}
		if( strFieldName.equals("TotalOtherFCount") ) {
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
				strFieldName = "TotalMaleCount";
				break;
			case 5:
				strFieldName = "TotalFemalCount";
				break;
			case 6:
				strFieldName = "TotalOnWorkMCount";
				break;
			case 7:
				strFieldName = "TotalOnWorkFCount";
				break;
			case 8:
				strFieldName = "TotalOffWorkMCount";
				break;
			case 9:
				strFieldName = "TotalOffWorkFCount";
				break;
			case 10:
				strFieldName = "TotalMateMCount";
				break;
			case 11:
				strFieldName = "TotalMateFCount";
				break;
			case 12:
				strFieldName = "TotalYoungMCount";
				break;
			case 13:
				strFieldName = "TotalYoungFCount";
				break;
			case 14:
				strFieldName = "TotalOtherMCount";
				break;
			case 15:
				strFieldName = "TotalOtherFCount";
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
		if( strFieldName.equals("TotalMaleCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalFemalCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOnWorkMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOnWorkFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOffWorkMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOffWorkFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalMateMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalMateFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalYoungMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalYoungFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOtherMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TotalOtherFCount") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
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
