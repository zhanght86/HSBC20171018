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
import com.sinosoft.lis.db.LOEngBonusPolDB;

/*
 * <p>ClassName: LOEngBonusPolSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 分红管理
 */
public class LOEngBonusPolSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOEngBonusPolSchema.class);

	// @Field
	/** 保单险种号码 */
	private String PolNo;
	/** 会计年度 */
	private int FiscalYear;
	/** 总单/合同号码 */
	private String ContNo;
	/** 集体保单号码 */
	private String GrpContNo;
	/** 分红率 */
	private double BonusRate;
	/** 终了红利利率 */
	private double TerminalBonusRate;
	/** 基本保额 */
	private double BaseAmnt;
	/** 年度红利保额 */
	private double BonusAmnt;
	/** 有效保额 */
	private double AvailableAmnt;
	/** 红利公布日 */
	private Date BonusMakeDate;
	/** 红利应该分配日期 */
	private Date SDispatchDate;
	/** 红利实际分配日期 */
	private Date ADispatchDate;
	/** 分红派发类型 */
	private String DispatchType;
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
	/** 领取标志 */
	private String GetFlag;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOEngBonusPolSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "PolNo";
		pk[1] = "FiscalYear";

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
		LOEngBonusPolSchema cloned = (LOEngBonusPolSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public int getFiscalYear()
	{
		return FiscalYear;
	}
	public void setFiscalYear(int aFiscalYear)
	{
		FiscalYear = aFiscalYear;
	}
	public void setFiscalYear(String aFiscalYear)
	{
		if (aFiscalYear != null && !aFiscalYear.equals(""))
		{
			Integer tInteger = new Integer(aFiscalYear);
			int i = tInteger.intValue();
			FiscalYear = i;
		}
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public double getBonusRate()
	{
		return BonusRate;
	}
	public void setBonusRate(double aBonusRate)
	{
		BonusRate = aBonusRate;
	}
	public void setBonusRate(String aBonusRate)
	{
		if (aBonusRate != null && !aBonusRate.equals(""))
		{
			Double tDouble = new Double(aBonusRate);
			double d = tDouble.doubleValue();
			BonusRate = d;
		}
	}

	public double getTerminalBonusRate()
	{
		return TerminalBonusRate;
	}
	public void setTerminalBonusRate(double aTerminalBonusRate)
	{
		TerminalBonusRate = aTerminalBonusRate;
	}
	public void setTerminalBonusRate(String aTerminalBonusRate)
	{
		if (aTerminalBonusRate != null && !aTerminalBonusRate.equals(""))
		{
			Double tDouble = new Double(aTerminalBonusRate);
			double d = tDouble.doubleValue();
			TerminalBonusRate = d;
		}
	}

	/**
	* 本年度计算红利时的基本保额
	*/
	public double getBaseAmnt()
	{
		return BaseAmnt;
	}
	public void setBaseAmnt(double aBaseAmnt)
	{
		BaseAmnt = aBaseAmnt;
	}
	public void setBaseAmnt(String aBaseAmnt)
	{
		if (aBaseAmnt != null && !aBaseAmnt.equals(""))
		{
			Double tDouble = new Double(aBaseAmnt);
			double d = tDouble.doubleValue();
			BaseAmnt = d;
		}
	}

	public double getBonusAmnt()
	{
		return BonusAmnt;
	}
	public void setBonusAmnt(double aBonusAmnt)
	{
		BonusAmnt = aBonusAmnt;
	}
	public void setBonusAmnt(String aBonusAmnt)
	{
		if (aBonusAmnt != null && !aBonusAmnt.equals(""))
		{
			Double tDouble = new Double(aBonusAmnt);
			double d = tDouble.doubleValue();
			BonusAmnt = d;
		}
	}

	/**
	* 本年度分红后的有效保额
	*/
	public double getAvailableAmnt()
	{
		return AvailableAmnt;
	}
	public void setAvailableAmnt(double aAvailableAmnt)
	{
		AvailableAmnt = aAvailableAmnt;
	}
	public void setAvailableAmnt(String aAvailableAmnt)
	{
		if (aAvailableAmnt != null && !aAvailableAmnt.equals(""))
		{
			Double tDouble = new Double(aAvailableAmnt);
			double d = tDouble.doubleValue();
			AvailableAmnt = d;
		}
	}

	public String getBonusMakeDate()
	{
		if( BonusMakeDate != null )
			return fDate.getString(BonusMakeDate);
		else
			return null;
	}
	public void setBonusMakeDate(Date aBonusMakeDate)
	{
		BonusMakeDate = aBonusMakeDate;
	}
	public void setBonusMakeDate(String aBonusMakeDate)
	{
		if (aBonusMakeDate != null && !aBonusMakeDate.equals("") )
		{
			BonusMakeDate = fDate.getDate( aBonusMakeDate );
		}
		else
			BonusMakeDate = null;
	}

	public String getSDispatchDate()
	{
		if( SDispatchDate != null )
			return fDate.getString(SDispatchDate);
		else
			return null;
	}
	public void setSDispatchDate(Date aSDispatchDate)
	{
		SDispatchDate = aSDispatchDate;
	}
	public void setSDispatchDate(String aSDispatchDate)
	{
		if (aSDispatchDate != null && !aSDispatchDate.equals("") )
		{
			SDispatchDate = fDate.getDate( aSDispatchDate );
		}
		else
			SDispatchDate = null;
	}

	public String getADispatchDate()
	{
		if( ADispatchDate != null )
			return fDate.getString(ADispatchDate);
		else
			return null;
	}
	public void setADispatchDate(Date aADispatchDate)
	{
		ADispatchDate = aADispatchDate;
	}
	public void setADispatchDate(String aADispatchDate)
	{
		if (aADispatchDate != null && !aADispatchDate.equals("") )
		{
			ADispatchDate = fDate.getDate( aADispatchDate );
		}
		else
			ADispatchDate = null;
	}

	/**
	* 0-年度分红<p>
	* 1-补派红利<p>
	* 2-强制分红
	*/
	public String getDispatchType()
	{
		return DispatchType;
	}
	public void setDispatchType(String aDispatchType)
	{
		DispatchType = aDispatchType;
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
	* 0 - 未领取<p>
	* 1 - 已领取
	*/
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		GetFlag = aGetFlag;
	}

	/**
	* 使用另外一个 LOEngBonusPolSchema 对象给 Schema 赋值
	* @param: aLOEngBonusPolSchema LOEngBonusPolSchema
	**/
	public void setSchema(LOEngBonusPolSchema aLOEngBonusPolSchema)
	{
		this.PolNo = aLOEngBonusPolSchema.getPolNo();
		this.FiscalYear = aLOEngBonusPolSchema.getFiscalYear();
		this.ContNo = aLOEngBonusPolSchema.getContNo();
		this.GrpContNo = aLOEngBonusPolSchema.getGrpContNo();
		this.BonusRate = aLOEngBonusPolSchema.getBonusRate();
		this.TerminalBonusRate = aLOEngBonusPolSchema.getTerminalBonusRate();
		this.BaseAmnt = aLOEngBonusPolSchema.getBaseAmnt();
		this.BonusAmnt = aLOEngBonusPolSchema.getBonusAmnt();
		this.AvailableAmnt = aLOEngBonusPolSchema.getAvailableAmnt();
		this.BonusMakeDate = fDate.getDate( aLOEngBonusPolSchema.getBonusMakeDate());
		this.SDispatchDate = fDate.getDate( aLOEngBonusPolSchema.getSDispatchDate());
		this.ADispatchDate = fDate.getDate( aLOEngBonusPolSchema.getADispatchDate());
		this.DispatchType = aLOEngBonusPolSchema.getDispatchType();
		this.Operator = aLOEngBonusPolSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOEngBonusPolSchema.getMakeDate());
		this.MakeTime = aLOEngBonusPolSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOEngBonusPolSchema.getModifyDate());
		this.ModifyTime = aLOEngBonusPolSchema.getModifyTime();
		this.GetFlag = aLOEngBonusPolSchema.getGetFlag();
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
			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			this.FiscalYear = rs.getInt("FiscalYear");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			this.BonusRate = rs.getDouble("BonusRate");
			this.TerminalBonusRate = rs.getDouble("TerminalBonusRate");
			this.BaseAmnt = rs.getDouble("BaseAmnt");
			this.BonusAmnt = rs.getDouble("BonusAmnt");
			this.AvailableAmnt = rs.getDouble("AvailableAmnt");
			this.BonusMakeDate = rs.getDate("BonusMakeDate");
			this.SDispatchDate = rs.getDate("SDispatchDate");
			this.ADispatchDate = rs.getDate("ADispatchDate");
			if( rs.getString("DispatchType") == null )
				this.DispatchType = null;
			else
				this.DispatchType = rs.getString("DispatchType").trim();

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

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOEngBonusPol表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOEngBonusPolSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOEngBonusPolSchema getSchema()
	{
		LOEngBonusPolSchema aLOEngBonusPolSchema = new LOEngBonusPolSchema();
		aLOEngBonusPolSchema.setSchema(this);
		return aLOEngBonusPolSchema;
	}

	public LOEngBonusPolDB getDB()
	{
		LOEngBonusPolDB aDBOper = new LOEngBonusPolDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOEngBonusPol描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FiscalYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TerminalBonusRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BaseAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BonusAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AvailableAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BonusMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( SDispatchDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ADispatchDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DispatchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOEngBonusPol>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FiscalYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			TerminalBonusRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			BaseAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			BonusAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			AvailableAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			BonusMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			SDispatchDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ADispatchDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			DispatchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOEngBonusPolSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FiscalYear));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusRate));
		}
		if (FCode.equalsIgnoreCase("TerminalBonusRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TerminalBonusRate));
		}
		if (FCode.equalsIgnoreCase("BaseAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BaseAmnt));
		}
		if (FCode.equalsIgnoreCase("BonusAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusAmnt));
		}
		if (FCode.equalsIgnoreCase("AvailableAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvailableAmnt));
		}
		if (FCode.equalsIgnoreCase("BonusMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBonusMakeDate()));
		}
		if (FCode.equalsIgnoreCase("SDispatchDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSDispatchDate()));
		}
		if (FCode.equalsIgnoreCase("ADispatchDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getADispatchDate()));
		}
		if (FCode.equalsIgnoreCase("DispatchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DispatchType));
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
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 1:
				strFieldValue = String.valueOf(FiscalYear);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 4:
				strFieldValue = String.valueOf(BonusRate);
				break;
			case 5:
				strFieldValue = String.valueOf(TerminalBonusRate);
				break;
			case 6:
				strFieldValue = String.valueOf(BaseAmnt);
				break;
			case 7:
				strFieldValue = String.valueOf(BonusAmnt);
				break;
			case 8:
				strFieldValue = String.valueOf(AvailableAmnt);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBonusMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSDispatchDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getADispatchDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(DispatchType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
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

		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("FiscalYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FiscalYear = i;
			}
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("BonusRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("TerminalBonusRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TerminalBonusRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("BaseAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BaseAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BonusAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AvailableAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AvailableAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("BonusMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BonusMakeDate = fDate.getDate( FValue );
			}
			else
				BonusMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("SDispatchDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SDispatchDate = fDate.getDate( FValue );
			}
			else
				SDispatchDate = null;
		}
		if (FCode.equalsIgnoreCase("ADispatchDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ADispatchDate = fDate.getDate( FValue );
			}
			else
				ADispatchDate = null;
		}
		if (FCode.equalsIgnoreCase("DispatchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DispatchType = FValue.trim();
			}
			else
				DispatchType = null;
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
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOEngBonusPolSchema other = (LOEngBonusPolSchema)otherObject;
		return
			PolNo.equals(other.getPolNo())
			&& FiscalYear == other.getFiscalYear()
			&& ContNo.equals(other.getContNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& BonusRate == other.getBonusRate()
			&& TerminalBonusRate == other.getTerminalBonusRate()
			&& BaseAmnt == other.getBaseAmnt()
			&& BonusAmnt == other.getBonusAmnt()
			&& AvailableAmnt == other.getAvailableAmnt()
			&& fDate.getString(BonusMakeDate).equals(other.getBonusMakeDate())
			&& fDate.getString(SDispatchDate).equals(other.getSDispatchDate())
			&& fDate.getString(ADispatchDate).equals(other.getADispatchDate())
			&& DispatchType.equals(other.getDispatchType())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& GetFlag.equals(other.getGetFlag());
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
		if( strFieldName.equals("PolNo") ) {
			return 0;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("BonusRate") ) {
			return 4;
		}
		if( strFieldName.equals("TerminalBonusRate") ) {
			return 5;
		}
		if( strFieldName.equals("BaseAmnt") ) {
			return 6;
		}
		if( strFieldName.equals("BonusAmnt") ) {
			return 7;
		}
		if( strFieldName.equals("AvailableAmnt") ) {
			return 8;
		}
		if( strFieldName.equals("BonusMakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("SDispatchDate") ) {
			return 10;
		}
		if( strFieldName.equals("ADispatchDate") ) {
			return 11;
		}
		if( strFieldName.equals("DispatchType") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("GetFlag") ) {
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
				strFieldName = "PolNo";
				break;
			case 1:
				strFieldName = "FiscalYear";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "GrpContNo";
				break;
			case 4:
				strFieldName = "BonusRate";
				break;
			case 5:
				strFieldName = "TerminalBonusRate";
				break;
			case 6:
				strFieldName = "BaseAmnt";
				break;
			case 7:
				strFieldName = "BonusAmnt";
				break;
			case 8:
				strFieldName = "AvailableAmnt";
				break;
			case 9:
				strFieldName = "BonusMakeDate";
				break;
			case 10:
				strFieldName = "SDispatchDate";
				break;
			case 11:
				strFieldName = "ADispatchDate";
				break;
			case 12:
				strFieldName = "DispatchType";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "GetFlag";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FiscalYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("TerminalBonusRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BaseAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AvailableAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BonusMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SDispatchDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ADispatchDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DispatchType") ) {
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
		if( strFieldName.equals("GetFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
