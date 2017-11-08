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
import com.sinosoft.lis.db.LDUWGradePersonDB;

/*
 * <p>ClassName: LDUWGradePersonSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWGradePersonSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWGradePersonSchema.class);

	// @Field
	/** 核保级别 */
	private String UWPopedom;
	/** 机构权限 */
	private String UWCom;
	/** 险类 */
	private String UWKind;
	/** 是否标准体 */
	private String StandFlag;
	/** 是否体检件 */
	private String HealthFlag;
	/** 是否延期拒保 */
	private String DelayFlag;
	/** 业务规模 */
	private String BusiRange;
	/** 保额上限 */
	private double MaxAmnt;
	/** 疾病保额上限 */
	private double MaxIllAmnt;
	/** 死亡保额上限 */
	private double MaxDieAmnt;
	/** 其它保额上限 */
	private double OthMaxAmnt;
	/** 保费上限 */
	private double MaxPrem;
	/** 费率 */
	private double Rate;
	/** 其它费率 */
	private double OthRate;
	/** 是否可调管理费 */
	private String Flag;
	/** 是否可调整责任 */
	private String Flag1;
	/** 加费评点 */
	private double AddPoint;
	/** 核保业务类别 */
	private String UWType;
	/** 核保级别名称 */
	private String UWPopedomName;
	/** 险种编码 */
	private String RiskCode;
	/** 份数 */
	private double Mult;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWGradePersonSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "UWPopedom";
		pk[1] = "RiskCode";

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
		LDUWGradePersonSchema cloned = (LDUWGradePersonSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		UWPopedom = aUWPopedom;
	}
	/**
	* 定义总公司分公司权限
	*/
	public String getUWCom()
	{
		return UWCom;
	}
	public void setUWCom(String aUWCom)
	{
		UWCom = aUWCom;
	}
	/**
	* 定义险种分类权限
	*/
	public String getUWKind()
	{
		return UWKind;
	}
	public void setUWKind(String aUWKind)
	{
		UWKind = aUWKind;
	}
	/**
	* 0 －－ 标准体<p>
	* 1 －－ 非标准体<p>
	* 9 －－ 该字段无关
	*/
	public String getStandFlag()
	{
		return StandFlag;
	}
	public void setStandFlag(String aStandFlag)
	{
		StandFlag = aStandFlag;
	}
	/**
	* 0 －－ 体检件<p>
	* 1 －－ 非体检件<p>
	* 9 －－ 该字段无关
	*/
	public String getHealthFlag()
	{
		return HealthFlag;
	}
	public void setHealthFlag(String aHealthFlag)
	{
		HealthFlag = aHealthFlag;
	}
	/**
	* 0 －－ 拒保相关<p>
	* 9 －－ 拒保不相关
	*/
	public String getDelayFlag()
	{
		return DelayFlag;
	}
	public void setDelayFlag(String aDelayFlag)
	{
		DelayFlag = aDelayFlag;
	}
	/**
	* A-八人以下<p>
	* B－小型团体<p>
	* C-中型团体<p>
	* D-大型团体<p>
	* E-超大型团体
	*/
	public String getBusiRange()
	{
		return BusiRange;
	}
	public void setBusiRange(String aBusiRange)
	{
		BusiRange = aBusiRange;
	}
	public double getMaxAmnt()
	{
		return MaxAmnt;
	}
	public void setMaxAmnt(double aMaxAmnt)
	{
		MaxAmnt = aMaxAmnt;
	}
	public void setMaxAmnt(String aMaxAmnt)
	{
		if (aMaxAmnt != null && !aMaxAmnt.equals(""))
		{
			Double tDouble = new Double(aMaxAmnt);
			double d = tDouble.doubleValue();
			MaxAmnt = d;
		}
	}

	public double getMaxIllAmnt()
	{
		return MaxIllAmnt;
	}
	public void setMaxIllAmnt(double aMaxIllAmnt)
	{
		MaxIllAmnt = aMaxIllAmnt;
	}
	public void setMaxIllAmnt(String aMaxIllAmnt)
	{
		if (aMaxIllAmnt != null && !aMaxIllAmnt.equals(""))
		{
			Double tDouble = new Double(aMaxIllAmnt);
			double d = tDouble.doubleValue();
			MaxIllAmnt = d;
		}
	}

	public double getMaxDieAmnt()
	{
		return MaxDieAmnt;
	}
	public void setMaxDieAmnt(double aMaxDieAmnt)
	{
		MaxDieAmnt = aMaxDieAmnt;
	}
	public void setMaxDieAmnt(String aMaxDieAmnt)
	{
		if (aMaxDieAmnt != null && !aMaxDieAmnt.equals(""))
		{
			Double tDouble = new Double(aMaxDieAmnt);
			double d = tDouble.doubleValue();
			MaxDieAmnt = d;
		}
	}

	public double getOthMaxAmnt()
	{
		return OthMaxAmnt;
	}
	public void setOthMaxAmnt(double aOthMaxAmnt)
	{
		OthMaxAmnt = aOthMaxAmnt;
	}
	public void setOthMaxAmnt(String aOthMaxAmnt)
	{
		if (aOthMaxAmnt != null && !aOthMaxAmnt.equals(""))
		{
			Double tDouble = new Double(aOthMaxAmnt);
			double d = tDouble.doubleValue();
			OthMaxAmnt = d;
		}
	}

	public double getMaxPrem()
	{
		return MaxPrem;
	}
	public void setMaxPrem(double aMaxPrem)
	{
		MaxPrem = aMaxPrem;
	}
	public void setMaxPrem(String aMaxPrem)
	{
		if (aMaxPrem != null && !aMaxPrem.equals(""))
		{
			Double tDouble = new Double(aMaxPrem);
			double d = tDouble.doubleValue();
			MaxPrem = d;
		}
	}

	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}

	public double getOthRate()
	{
		return OthRate;
	}
	public void setOthRate(double aOthRate)
	{
		OthRate = aOthRate;
	}
	public void setOthRate(String aOthRate)
	{
		if (aOthRate != null && !aOthRate.equals(""))
		{
			Double tDouble = new Double(aOthRate);
			double d = tDouble.doubleValue();
			OthRate = d;
		}
	}

	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String aFlag)
	{
		Flag = aFlag;
	}
	public String getFlag1()
	{
		return Flag1;
	}
	public void setFlag1(String aFlag1)
	{
		Flag1 = aFlag1;
	}
	public double getAddPoint()
	{
		return AddPoint;
	}
	public void setAddPoint(double aAddPoint)
	{
		AddPoint = aAddPoint;
	}
	public void setAddPoint(String aAddPoint)
	{
		if (aAddPoint != null && !aAddPoint.equals(""))
		{
			Double tDouble = new Double(aAddPoint);
			double d = tDouble.doubleValue();
			AddPoint = d;
		}
	}

	/**
	* 1-个险<p>
	* 2-团险
	*/
	public String getUWType()
	{
		return UWType;
	}
	public void setUWType(String aUWType)
	{
		UWType = aUWType;
	}
	public String getUWPopedomName()
	{
		return UWPopedomName;
	}
	public void setUWPopedomName(String aUWPopedomName)
	{
		UWPopedomName = aUWPopedomName;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public double getMult()
	{
		return Mult;
	}
	public void setMult(double aMult)
	{
		Mult = aMult;
	}
	public void setMult(String aMult)
	{
		if (aMult != null && !aMult.equals(""))
		{
			Double tDouble = new Double(aMult);
			double d = tDouble.doubleValue();
			Mult = d;
		}
	}


	/**
	* 使用另外一个 LDUWGradePersonSchema 对象给 Schema 赋值
	* @param: aLDUWGradePersonSchema LDUWGradePersonSchema
	**/
	public void setSchema(LDUWGradePersonSchema aLDUWGradePersonSchema)
	{
		this.UWPopedom = aLDUWGradePersonSchema.getUWPopedom();
		this.UWCom = aLDUWGradePersonSchema.getUWCom();
		this.UWKind = aLDUWGradePersonSchema.getUWKind();
		this.StandFlag = aLDUWGradePersonSchema.getStandFlag();
		this.HealthFlag = aLDUWGradePersonSchema.getHealthFlag();
		this.DelayFlag = aLDUWGradePersonSchema.getDelayFlag();
		this.BusiRange = aLDUWGradePersonSchema.getBusiRange();
		this.MaxAmnt = aLDUWGradePersonSchema.getMaxAmnt();
		this.MaxIllAmnt = aLDUWGradePersonSchema.getMaxIllAmnt();
		this.MaxDieAmnt = aLDUWGradePersonSchema.getMaxDieAmnt();
		this.OthMaxAmnt = aLDUWGradePersonSchema.getOthMaxAmnt();
		this.MaxPrem = aLDUWGradePersonSchema.getMaxPrem();
		this.Rate = aLDUWGradePersonSchema.getRate();
		this.OthRate = aLDUWGradePersonSchema.getOthRate();
		this.Flag = aLDUWGradePersonSchema.getFlag();
		this.Flag1 = aLDUWGradePersonSchema.getFlag1();
		this.AddPoint = aLDUWGradePersonSchema.getAddPoint();
		this.UWType = aLDUWGradePersonSchema.getUWType();
		this.UWPopedomName = aLDUWGradePersonSchema.getUWPopedomName();
		this.RiskCode = aLDUWGradePersonSchema.getRiskCode();
		this.Mult = aLDUWGradePersonSchema.getMult();
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
			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

			if( rs.getString("UWCom") == null )
				this.UWCom = null;
			else
				this.UWCom = rs.getString("UWCom").trim();

			if( rs.getString("UWKind") == null )
				this.UWKind = null;
			else
				this.UWKind = rs.getString("UWKind").trim();

			if( rs.getString("StandFlag") == null )
				this.StandFlag = null;
			else
				this.StandFlag = rs.getString("StandFlag").trim();

			if( rs.getString("HealthFlag") == null )
				this.HealthFlag = null;
			else
				this.HealthFlag = rs.getString("HealthFlag").trim();

			if( rs.getString("DelayFlag") == null )
				this.DelayFlag = null;
			else
				this.DelayFlag = rs.getString("DelayFlag").trim();

			if( rs.getString("BusiRange") == null )
				this.BusiRange = null;
			else
				this.BusiRange = rs.getString("BusiRange").trim();

			this.MaxAmnt = rs.getDouble("MaxAmnt");
			this.MaxIllAmnt = rs.getDouble("MaxIllAmnt");
			this.MaxDieAmnt = rs.getDouble("MaxDieAmnt");
			this.OthMaxAmnt = rs.getDouble("OthMaxAmnt");
			this.MaxPrem = rs.getDouble("MaxPrem");
			this.Rate = rs.getDouble("Rate");
			this.OthRate = rs.getDouble("OthRate");
			if( rs.getString("Flag") == null )
				this.Flag = null;
			else
				this.Flag = rs.getString("Flag").trim();

			if( rs.getString("Flag1") == null )
				this.Flag1 = null;
			else
				this.Flag1 = rs.getString("Flag1").trim();

			this.AddPoint = rs.getDouble("AddPoint");
			if( rs.getString("UWType") == null )
				this.UWType = null;
			else
				this.UWType = rs.getString("UWType").trim();

			if( rs.getString("UWPopedomName") == null )
				this.UWPopedomName = null;
			else
				this.UWPopedomName = rs.getString("UWPopedomName").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.Mult = rs.getDouble("Mult");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWGradePerson表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGradePersonSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWGradePersonSchema getSchema()
	{
		LDUWGradePersonSchema aLDUWGradePersonSchema = new LDUWGradePersonSchema();
		aLDUWGradePersonSchema.setSchema(this);
		return aLDUWGradePersonSchema;
	}

	public LDUWGradePersonDB getDB()
	{
		LDUWGradePersonDB aDBOper = new LDUWGradePersonDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGradePerson描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HealthFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DelayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BusiRange)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxIllAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxDieAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthMaxAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Flag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddPoint));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Mult));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGradePerson>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UWCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UWKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StandFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			HealthFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DelayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BusiRange = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MaxAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			MaxIllAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			MaxDieAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			OthMaxAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			MaxPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			OthRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Flag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AddPoint = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			UWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			UWPopedomName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Mult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGradePersonSchema";
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
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
		}
		if (FCode.equalsIgnoreCase("UWCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCom));
		}
		if (FCode.equalsIgnoreCase("UWKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWKind));
		}
		if (FCode.equalsIgnoreCase("StandFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandFlag));
		}
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HealthFlag));
		}
		if (FCode.equalsIgnoreCase("DelayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DelayFlag));
		}
		if (FCode.equalsIgnoreCase("BusiRange"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiRange));
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAmnt));
		}
		if (FCode.equalsIgnoreCase("MaxIllAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxIllAmnt));
		}
		if (FCode.equalsIgnoreCase("MaxDieAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxDieAmnt));
		}
		if (FCode.equalsIgnoreCase("OthMaxAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthMaxAmnt));
		}
		if (FCode.equalsIgnoreCase("MaxPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxPrem));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("OthRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthRate));
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag));
		}
		if (FCode.equalsIgnoreCase("Flag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Flag1));
		}
		if (FCode.equalsIgnoreCase("AddPoint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPoint));
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWType));
		}
		if (FCode.equalsIgnoreCase("UWPopedomName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedomName));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mult));
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
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UWCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(UWKind);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(StandFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(HealthFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DelayFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BusiRange);
				break;
			case 7:
				strFieldValue = String.valueOf(MaxAmnt);
				break;
			case 8:
				strFieldValue = String.valueOf(MaxIllAmnt);
				break;
			case 9:
				strFieldValue = String.valueOf(MaxDieAmnt);
				break;
			case 10:
				strFieldValue = String.valueOf(OthMaxAmnt);
				break;
			case 11:
				strFieldValue = String.valueOf(MaxPrem);
				break;
			case 12:
				strFieldValue = String.valueOf(Rate);
				break;
			case 13:
				strFieldValue = String.valueOf(OthRate);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Flag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Flag1);
				break;
			case 16:
				strFieldValue = String.valueOf(AddPoint);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UWType);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(UWPopedomName);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 20:
				strFieldValue = String.valueOf(Mult);
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

		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedom = FValue.trim();
			}
			else
				UWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCom = FValue.trim();
			}
			else
				UWCom = null;
		}
		if (FCode.equalsIgnoreCase("UWKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWKind = FValue.trim();
			}
			else
				UWKind = null;
		}
		if (FCode.equalsIgnoreCase("StandFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandFlag = FValue.trim();
			}
			else
				StandFlag = null;
		}
		if (FCode.equalsIgnoreCase("HealthFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HealthFlag = FValue.trim();
			}
			else
				HealthFlag = null;
		}
		if (FCode.equalsIgnoreCase("DelayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DelayFlag = FValue.trim();
			}
			else
				DelayFlag = null;
		}
		if (FCode.equalsIgnoreCase("BusiRange"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiRange = FValue.trim();
			}
			else
				BusiRange = null;
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxIllAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxIllAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxDieAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxDieAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthMaxAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthMaxAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag = FValue.trim();
			}
			else
				Flag = null;
		}
		if (FCode.equalsIgnoreCase("Flag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Flag1 = FValue.trim();
			}
			else
				Flag1 = null;
		}
		if (FCode.equalsIgnoreCase("AddPoint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPoint = d;
			}
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWType = FValue.trim();
			}
			else
				UWType = null;
		}
		if (FCode.equalsIgnoreCase("UWPopedomName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedomName = FValue.trim();
			}
			else
				UWPopedomName = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("Mult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Mult = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWGradePersonSchema other = (LDUWGradePersonSchema)otherObject;
		return
			UWPopedom.equals(other.getUWPopedom())
			&& UWCom.equals(other.getUWCom())
			&& UWKind.equals(other.getUWKind())
			&& StandFlag.equals(other.getStandFlag())
			&& HealthFlag.equals(other.getHealthFlag())
			&& DelayFlag.equals(other.getDelayFlag())
			&& BusiRange.equals(other.getBusiRange())
			&& MaxAmnt == other.getMaxAmnt()
			&& MaxIllAmnt == other.getMaxIllAmnt()
			&& MaxDieAmnt == other.getMaxDieAmnt()
			&& OthMaxAmnt == other.getOthMaxAmnt()
			&& MaxPrem == other.getMaxPrem()
			&& Rate == other.getRate()
			&& OthRate == other.getOthRate()
			&& Flag.equals(other.getFlag())
			&& Flag1.equals(other.getFlag1())
			&& AddPoint == other.getAddPoint()
			&& UWType.equals(other.getUWType())
			&& UWPopedomName.equals(other.getUWPopedomName())
			&& RiskCode.equals(other.getRiskCode())
			&& Mult == other.getMult();
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
		if( strFieldName.equals("UWPopedom") ) {
			return 0;
		}
		if( strFieldName.equals("UWCom") ) {
			return 1;
		}
		if( strFieldName.equals("UWKind") ) {
			return 2;
		}
		if( strFieldName.equals("StandFlag") ) {
			return 3;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return 4;
		}
		if( strFieldName.equals("DelayFlag") ) {
			return 5;
		}
		if( strFieldName.equals("BusiRange") ) {
			return 6;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return 7;
		}
		if( strFieldName.equals("MaxIllAmnt") ) {
			return 8;
		}
		if( strFieldName.equals("MaxDieAmnt") ) {
			return 9;
		}
		if( strFieldName.equals("OthMaxAmnt") ) {
			return 10;
		}
		if( strFieldName.equals("MaxPrem") ) {
			return 11;
		}
		if( strFieldName.equals("Rate") ) {
			return 12;
		}
		if( strFieldName.equals("OthRate") ) {
			return 13;
		}
		if( strFieldName.equals("Flag") ) {
			return 14;
		}
		if( strFieldName.equals("Flag1") ) {
			return 15;
		}
		if( strFieldName.equals("AddPoint") ) {
			return 16;
		}
		if( strFieldName.equals("UWType") ) {
			return 17;
		}
		if( strFieldName.equals("UWPopedomName") ) {
			return 18;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 19;
		}
		if( strFieldName.equals("Mult") ) {
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
				strFieldName = "UWPopedom";
				break;
			case 1:
				strFieldName = "UWCom";
				break;
			case 2:
				strFieldName = "UWKind";
				break;
			case 3:
				strFieldName = "StandFlag";
				break;
			case 4:
				strFieldName = "HealthFlag";
				break;
			case 5:
				strFieldName = "DelayFlag";
				break;
			case 6:
				strFieldName = "BusiRange";
				break;
			case 7:
				strFieldName = "MaxAmnt";
				break;
			case 8:
				strFieldName = "MaxIllAmnt";
				break;
			case 9:
				strFieldName = "MaxDieAmnt";
				break;
			case 10:
				strFieldName = "OthMaxAmnt";
				break;
			case 11:
				strFieldName = "MaxPrem";
				break;
			case 12:
				strFieldName = "Rate";
				break;
			case 13:
				strFieldName = "OthRate";
				break;
			case 14:
				strFieldName = "Flag";
				break;
			case 15:
				strFieldName = "Flag1";
				break;
			case 16:
				strFieldName = "AddPoint";
				break;
			case 17:
				strFieldName = "UWType";
				break;
			case 18:
				strFieldName = "UWPopedomName";
				break;
			case 19:
				strFieldName = "RiskCode";
				break;
			case 20:
				strFieldName = "Mult";
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
		if( strFieldName.equals("UWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HealthFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DelayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BusiRange") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxIllAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxDieAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthMaxAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Flag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPoint") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedomName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mult") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
