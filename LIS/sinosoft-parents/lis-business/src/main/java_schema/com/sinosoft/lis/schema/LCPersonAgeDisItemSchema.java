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
import com.sinosoft.lis.db.LCPersonAgeDisItemDB;

/*
 * <p>ClassName: LCPersonAgeDisItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCPersonAgeDisItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPersonAgeDisItemSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 印刷号 */
	private String PrtNo;
	/** 被保人年龄区间开始值 */
	private int StartAge;
	/** 被保人年龄区间终止值 */
	private int EndAge;
	/** 合计男人数 */
	private int MaleCount;
	/** 合计女人数 */
	private int FemalCount;
	/** 在职男人数 */
	private int OnWorkMCount;
	/** 在职女人数 */
	private int OnWorkFCount;
	/** 退休男人数 */
	private int OffWorkMCount;
	/** 退休女人数 */
	private int OffWorkFCount;
	/** 配偶男人数 */
	private int MateMCount;
	/** 配偶女人数 */
	private int MateFCount;
	/** 子女男人数 */
	private int YoungMCount;
	/** 子女女人数 */
	private int YoungFCount;
	/** 其它男人数 */
	private int OtherMCount;
	/** 其它女人数 */
	private int OtherFCount;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPersonAgeDisItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "SerialNo";
		pk[1] = "StartAge";
		pk[2] = "EndAge";

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
		LCPersonAgeDisItemSchema cloned = (LCPersonAgeDisItemSchema)super.clone();
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
	/**
	* 0-最小年龄
	*/
	public int getStartAge()
	{
		return StartAge;
	}
	public void setStartAge(int aStartAge)
	{
		StartAge = aStartAge;
	}
	public void setStartAge(String aStartAge)
	{
		if (aStartAge != null && !aStartAge.equals(""))
		{
			Integer tInteger = new Integer(aStartAge);
			int i = tInteger.intValue();
			StartAge = i;
		}
	}

	/**
	* 1000为无限大值
	*/
	public int getEndAge()
	{
		return EndAge;
	}
	public void setEndAge(int aEndAge)
	{
		EndAge = aEndAge;
	}
	public void setEndAge(String aEndAge)
	{
		if (aEndAge != null && !aEndAge.equals(""))
		{
			Integer tInteger = new Integer(aEndAge);
			int i = tInteger.intValue();
			EndAge = i;
		}
	}

	public int getMaleCount()
	{
		return MaleCount;
	}
	public void setMaleCount(int aMaleCount)
	{
		MaleCount = aMaleCount;
	}
	public void setMaleCount(String aMaleCount)
	{
		if (aMaleCount != null && !aMaleCount.equals(""))
		{
			Integer tInteger = new Integer(aMaleCount);
			int i = tInteger.intValue();
			MaleCount = i;
		}
	}

	public int getFemalCount()
	{
		return FemalCount;
	}
	public void setFemalCount(int aFemalCount)
	{
		FemalCount = aFemalCount;
	}
	public void setFemalCount(String aFemalCount)
	{
		if (aFemalCount != null && !aFemalCount.equals(""))
		{
			Integer tInteger = new Integer(aFemalCount);
			int i = tInteger.intValue();
			FemalCount = i;
		}
	}

	public int getOnWorkMCount()
	{
		return OnWorkMCount;
	}
	public void setOnWorkMCount(int aOnWorkMCount)
	{
		OnWorkMCount = aOnWorkMCount;
	}
	public void setOnWorkMCount(String aOnWorkMCount)
	{
		if (aOnWorkMCount != null && !aOnWorkMCount.equals(""))
		{
			Integer tInteger = new Integer(aOnWorkMCount);
			int i = tInteger.intValue();
			OnWorkMCount = i;
		}
	}

	public int getOnWorkFCount()
	{
		return OnWorkFCount;
	}
	public void setOnWorkFCount(int aOnWorkFCount)
	{
		OnWorkFCount = aOnWorkFCount;
	}
	public void setOnWorkFCount(String aOnWorkFCount)
	{
		if (aOnWorkFCount != null && !aOnWorkFCount.equals(""))
		{
			Integer tInteger = new Integer(aOnWorkFCount);
			int i = tInteger.intValue();
			OnWorkFCount = i;
		}
	}

	public int getOffWorkMCount()
	{
		return OffWorkMCount;
	}
	public void setOffWorkMCount(int aOffWorkMCount)
	{
		OffWorkMCount = aOffWorkMCount;
	}
	public void setOffWorkMCount(String aOffWorkMCount)
	{
		if (aOffWorkMCount != null && !aOffWorkMCount.equals(""))
		{
			Integer tInteger = new Integer(aOffWorkMCount);
			int i = tInteger.intValue();
			OffWorkMCount = i;
		}
	}

	public int getOffWorkFCount()
	{
		return OffWorkFCount;
	}
	public void setOffWorkFCount(int aOffWorkFCount)
	{
		OffWorkFCount = aOffWorkFCount;
	}
	public void setOffWorkFCount(String aOffWorkFCount)
	{
		if (aOffWorkFCount != null && !aOffWorkFCount.equals(""))
		{
			Integer tInteger = new Integer(aOffWorkFCount);
			int i = tInteger.intValue();
			OffWorkFCount = i;
		}
	}

	public int getMateMCount()
	{
		return MateMCount;
	}
	public void setMateMCount(int aMateMCount)
	{
		MateMCount = aMateMCount;
	}
	public void setMateMCount(String aMateMCount)
	{
		if (aMateMCount != null && !aMateMCount.equals(""))
		{
			Integer tInteger = new Integer(aMateMCount);
			int i = tInteger.intValue();
			MateMCount = i;
		}
	}

	public int getMateFCount()
	{
		return MateFCount;
	}
	public void setMateFCount(int aMateFCount)
	{
		MateFCount = aMateFCount;
	}
	public void setMateFCount(String aMateFCount)
	{
		if (aMateFCount != null && !aMateFCount.equals(""))
		{
			Integer tInteger = new Integer(aMateFCount);
			int i = tInteger.intValue();
			MateFCount = i;
		}
	}

	public int getYoungMCount()
	{
		return YoungMCount;
	}
	public void setYoungMCount(int aYoungMCount)
	{
		YoungMCount = aYoungMCount;
	}
	public void setYoungMCount(String aYoungMCount)
	{
		if (aYoungMCount != null && !aYoungMCount.equals(""))
		{
			Integer tInteger = new Integer(aYoungMCount);
			int i = tInteger.intValue();
			YoungMCount = i;
		}
	}

	public int getYoungFCount()
	{
		return YoungFCount;
	}
	public void setYoungFCount(int aYoungFCount)
	{
		YoungFCount = aYoungFCount;
	}
	public void setYoungFCount(String aYoungFCount)
	{
		if (aYoungFCount != null && !aYoungFCount.equals(""))
		{
			Integer tInteger = new Integer(aYoungFCount);
			int i = tInteger.intValue();
			YoungFCount = i;
		}
	}

	public int getOtherMCount()
	{
		return OtherMCount;
	}
	public void setOtherMCount(int aOtherMCount)
	{
		OtherMCount = aOtherMCount;
	}
	public void setOtherMCount(String aOtherMCount)
	{
		if (aOtherMCount != null && !aOtherMCount.equals(""))
		{
			Integer tInteger = new Integer(aOtherMCount);
			int i = tInteger.intValue();
			OtherMCount = i;
		}
	}

	public int getOtherFCount()
	{
		return OtherFCount;
	}
	public void setOtherFCount(int aOtherFCount)
	{
		OtherFCount = aOtherFCount;
	}
	public void setOtherFCount(String aOtherFCount)
	{
		if (aOtherFCount != null && !aOtherFCount.equals(""))
		{
			Integer tInteger = new Integer(aOtherFCount);
			int i = tInteger.intValue();
			OtherFCount = i;
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
	* 使用另外一个 LCPersonAgeDisItemSchema 对象给 Schema 赋值
	* @param: aLCPersonAgeDisItemSchema LCPersonAgeDisItemSchema
	**/
	public void setSchema(LCPersonAgeDisItemSchema aLCPersonAgeDisItemSchema)
	{
		this.SerialNo = aLCPersonAgeDisItemSchema.getSerialNo();
		this.GrpContNo = aLCPersonAgeDisItemSchema.getGrpContNo();
		this.PrtNo = aLCPersonAgeDisItemSchema.getPrtNo();
		this.StartAge = aLCPersonAgeDisItemSchema.getStartAge();
		this.EndAge = aLCPersonAgeDisItemSchema.getEndAge();
		this.MaleCount = aLCPersonAgeDisItemSchema.getMaleCount();
		this.FemalCount = aLCPersonAgeDisItemSchema.getFemalCount();
		this.OnWorkMCount = aLCPersonAgeDisItemSchema.getOnWorkMCount();
		this.OnWorkFCount = aLCPersonAgeDisItemSchema.getOnWorkFCount();
		this.OffWorkMCount = aLCPersonAgeDisItemSchema.getOffWorkMCount();
		this.OffWorkFCount = aLCPersonAgeDisItemSchema.getOffWorkFCount();
		this.MateMCount = aLCPersonAgeDisItemSchema.getMateMCount();
		this.MateFCount = aLCPersonAgeDisItemSchema.getMateFCount();
		this.YoungMCount = aLCPersonAgeDisItemSchema.getYoungMCount();
		this.YoungFCount = aLCPersonAgeDisItemSchema.getYoungFCount();
		this.OtherMCount = aLCPersonAgeDisItemSchema.getOtherMCount();
		this.OtherFCount = aLCPersonAgeDisItemSchema.getOtherFCount();
		this.MakeDate = fDate.getDate( aLCPersonAgeDisItemSchema.getMakeDate());
		this.MakeTime = aLCPersonAgeDisItemSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPersonAgeDisItemSchema.getModifyDate());
		this.ModifyTime = aLCPersonAgeDisItemSchema.getModifyTime();
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

			this.StartAge = rs.getInt("StartAge");
			this.EndAge = rs.getInt("EndAge");
			this.MaleCount = rs.getInt("MaleCount");
			this.FemalCount = rs.getInt("FemalCount");
			this.OnWorkMCount = rs.getInt("OnWorkMCount");
			this.OnWorkFCount = rs.getInt("OnWorkFCount");
			this.OffWorkMCount = rs.getInt("OffWorkMCount");
			this.OffWorkFCount = rs.getInt("OffWorkFCount");
			this.MateMCount = rs.getInt("MateMCount");
			this.MateFCount = rs.getInt("MateFCount");
			this.YoungMCount = rs.getInt("YoungMCount");
			this.YoungFCount = rs.getInt("YoungFCount");
			this.OtherMCount = rs.getInt("OtherMCount");
			this.OtherFCount = rs.getInt("OtherFCount");
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
			logger.debug("数据库中的LCPersonAgeDisItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonAgeDisItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPersonAgeDisItemSchema getSchema()
	{
		LCPersonAgeDisItemSchema aLCPersonAgeDisItemSchema = new LCPersonAgeDisItemSchema();
		aLCPersonAgeDisItemSchema.setSchema(this);
		return aLCPersonAgeDisItemSchema;
	}

	public LCPersonAgeDisItemDB getDB()
	{
		LCPersonAgeDisItemDB aDBOper = new LCPersonAgeDisItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonAgeDisItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StartAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaleCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FemalCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnWorkMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnWorkFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OffWorkMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OffWorkFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MateMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MateFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YoungMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(YoungFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherMCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OtherFCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPersonAgeDisItem>历史记账凭证主表信息</A>表字段
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
			StartAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			EndAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			MaleCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			FemalCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			OnWorkMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			OnWorkFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			OffWorkMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			OffWorkFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			MateMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			MateFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			YoungMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			YoungFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			OtherMCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			OtherFCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPersonAgeDisItemSchema";
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
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartAge));
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndAge));
		}
		if (FCode.equalsIgnoreCase("MaleCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaleCount));
		}
		if (FCode.equalsIgnoreCase("FemalCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FemalCount));
		}
		if (FCode.equalsIgnoreCase("OnWorkMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnWorkMCount));
		}
		if (FCode.equalsIgnoreCase("OnWorkFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnWorkFCount));
		}
		if (FCode.equalsIgnoreCase("OffWorkMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OffWorkMCount));
		}
		if (FCode.equalsIgnoreCase("OffWorkFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OffWorkFCount));
		}
		if (FCode.equalsIgnoreCase("MateMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MateMCount));
		}
		if (FCode.equalsIgnoreCase("MateFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MateFCount));
		}
		if (FCode.equalsIgnoreCase("YoungMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YoungMCount));
		}
		if (FCode.equalsIgnoreCase("YoungFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(YoungFCount));
		}
		if (FCode.equalsIgnoreCase("OtherMCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherMCount));
		}
		if (FCode.equalsIgnoreCase("OtherFCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherFCount));
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
				strFieldValue = String.valueOf(StartAge);
				break;
			case 4:
				strFieldValue = String.valueOf(EndAge);
				break;
			case 5:
				strFieldValue = String.valueOf(MaleCount);
				break;
			case 6:
				strFieldValue = String.valueOf(FemalCount);
				break;
			case 7:
				strFieldValue = String.valueOf(OnWorkMCount);
				break;
			case 8:
				strFieldValue = String.valueOf(OnWorkFCount);
				break;
			case 9:
				strFieldValue = String.valueOf(OffWorkMCount);
				break;
			case 10:
				strFieldValue = String.valueOf(OffWorkFCount);
				break;
			case 11:
				strFieldValue = String.valueOf(MateMCount);
				break;
			case 12:
				strFieldValue = String.valueOf(MateFCount);
				break;
			case 13:
				strFieldValue = String.valueOf(YoungMCount);
				break;
			case 14:
				strFieldValue = String.valueOf(YoungFCount);
				break;
			case 15:
				strFieldValue = String.valueOf(OtherMCount);
				break;
			case 16:
				strFieldValue = String.valueOf(OtherFCount);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
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
		if (FCode.equalsIgnoreCase("StartAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StartAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("EndAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaleCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaleCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("FemalCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FemalCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnWorkMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnWorkMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnWorkFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnWorkFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OffWorkMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OffWorkMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OffWorkFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OffWorkFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("MateMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MateMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("MateFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MateFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("YoungMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				YoungMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("YoungFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				YoungFCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherMCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherMCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherFCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OtherFCount = i;
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
		LCPersonAgeDisItemSchema other = (LCPersonAgeDisItemSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& StartAge == other.getStartAge()
			&& EndAge == other.getEndAge()
			&& MaleCount == other.getMaleCount()
			&& FemalCount == other.getFemalCount()
			&& OnWorkMCount == other.getOnWorkMCount()
			&& OnWorkFCount == other.getOnWorkFCount()
			&& OffWorkMCount == other.getOffWorkMCount()
			&& OffWorkFCount == other.getOffWorkFCount()
			&& MateMCount == other.getMateMCount()
			&& MateFCount == other.getMateFCount()
			&& YoungMCount == other.getYoungMCount()
			&& YoungFCount == other.getYoungFCount()
			&& OtherMCount == other.getOtherMCount()
			&& OtherFCount == other.getOtherFCount()
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
		if( strFieldName.equals("StartAge") ) {
			return 3;
		}
		if( strFieldName.equals("EndAge") ) {
			return 4;
		}
		if( strFieldName.equals("MaleCount") ) {
			return 5;
		}
		if( strFieldName.equals("FemalCount") ) {
			return 6;
		}
		if( strFieldName.equals("OnWorkMCount") ) {
			return 7;
		}
		if( strFieldName.equals("OnWorkFCount") ) {
			return 8;
		}
		if( strFieldName.equals("OffWorkMCount") ) {
			return 9;
		}
		if( strFieldName.equals("OffWorkFCount") ) {
			return 10;
		}
		if( strFieldName.equals("MateMCount") ) {
			return 11;
		}
		if( strFieldName.equals("MateFCount") ) {
			return 12;
		}
		if( strFieldName.equals("YoungMCount") ) {
			return 13;
		}
		if( strFieldName.equals("YoungFCount") ) {
			return 14;
		}
		if( strFieldName.equals("OtherMCount") ) {
			return 15;
		}
		if( strFieldName.equals("OtherFCount") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
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
				strFieldName = "StartAge";
				break;
			case 4:
				strFieldName = "EndAge";
				break;
			case 5:
				strFieldName = "MaleCount";
				break;
			case 6:
				strFieldName = "FemalCount";
				break;
			case 7:
				strFieldName = "OnWorkMCount";
				break;
			case 8:
				strFieldName = "OnWorkFCount";
				break;
			case 9:
				strFieldName = "OffWorkMCount";
				break;
			case 10:
				strFieldName = "OffWorkFCount";
				break;
			case 11:
				strFieldName = "MateMCount";
				break;
			case 12:
				strFieldName = "MateFCount";
				break;
			case 13:
				strFieldName = "YoungMCount";
				break;
			case 14:
				strFieldName = "YoungFCount";
				break;
			case 15:
				strFieldName = "OtherMCount";
				break;
			case 16:
				strFieldName = "OtherFCount";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
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
		if( strFieldName.equals("StartAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("EndAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaleCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FemalCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnWorkMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnWorkFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OffWorkMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OffWorkFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MateMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MateFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("YoungMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("YoungFCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherMCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherFCount") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
