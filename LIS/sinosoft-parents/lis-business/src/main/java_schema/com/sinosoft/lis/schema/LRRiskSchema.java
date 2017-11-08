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
import com.sinosoft.lis.db.LRRiskDB;

/*
 * <p>ClassName: LRRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LRRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRRiskSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种分类 */
	private String RiskSort;
	/** 险种计算分类 */
	private String RiskCalSort;
	/** 再保险公司 */
	private String ReinsureCom;
	/** 再保项目 */
	private String ReinsurItem;
	/** 自留额上限 */
	private double RetainLine;
	/** 分出额 */
	private double CessionLimit;
	/** 分保方式 */
	private String CessionMode;
	/** 额外死亡率 */
	private double ExMorRate;
	/** 自留额计算比例 */
	private double RetentRate;
	/** 佣金率 */
	private double CommsionRate;
	/** 分保费率算法 */
	private String CessPremMode;
	/** 风险保额算法 */
	private String RiskAmntCalCode;
	/** 分保保费算法 */
	private String CessPremCalCode;
	/** 分保手续费算法 */
	private String CessCommCalCode2;
	/** 理赔摊回金额算法 */
	private String ReturnPayCalCode;
	/** 是否临分算法 */
	private String NegoCalCode;
	/** 团体内个人保额上限 */
	private double GrpPerAmtLimit;
	/** 团体内人均保额上限 */
	private double GrpAverAmntLimit;
	/** 责任代码 */
	private String DutyCode;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "RiskCode";
		pk[1] = "RiskSort";
		pk[2] = "RiskCalSort";
		pk[3] = "ReinsureCom";
		pk[4] = "ReinsurItem";
		pk[5] = "DutyCode";

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
		LRRiskSchema cloned = (LRRiskSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 法定分保:<p>
	* 1-一年期意外<p>
	* 2-极短期意外险<p>
	* 3-航意险<p>
	* 4-短期健康险<p>
	* <p>
	* 商业分保<p>
	* 1-传统寿险<p>
	* 2-分红寿险<p>
	* 3-重大疾病保险<p>
	* 4-短期健康险<p>
	* 5-意外险<p>
	* 6-团体寿险<p>
	* 7-其它<p>
	* 8-团体意外险<p>
	* 9-团体健康险<p>
	* a-团体商业补充医疗保险
	*/
	public String getRiskSort()
	{
		return RiskSort;
	}
	public void setRiskSort(String aRiskSort)
	{
		RiskSort = aRiskSort;
	}
	/**
	* 法定分保:<p>
	* 1-一年期意外<p>
	* 2-极短期意外险<p>
	* 3-航意险<p>
	* 4-短期健康险<p>
	* <p>
	* 商业分保<p>
	* 1-个人寿险<p>
	* 2-个人重大疾病<p>
	* 3-个人意外伤害<p>
	* 4-个人健康险<p>
	* 5-团险<p>
	* 6-附加险<p>
	* 7-其它
	*/
	public String getRiskCalSort()
	{
		return RiskCalSort;
	}
	public void setRiskCalSort(String aRiskCalSort)
	{
		RiskCalSort = aRiskCalSort;
	}
	public String getReinsureCom()
	{
		return ReinsureCom;
	}
	public void setReinsureCom(String aReinsureCom)
	{
		ReinsureCom = aReinsureCom;
	}
	/**
	* L--法定分保<p>
	* C--商业分保
	*/
	public String getReinsurItem()
	{
		return ReinsurItem;
	}
	public void setReinsurItem(String aReinsurItem)
	{
		ReinsurItem = aReinsurItem;
	}
	/**
	* null-无上限
	*/
	public double getRetainLine()
	{
		return RetainLine;
	}
	public void setRetainLine(double aRetainLine)
	{
		RetainLine = aRetainLine;
	}
	public void setRetainLine(String aRetainLine)
	{
		if (aRetainLine != null && !aRetainLine.equals(""))
		{
			Double tDouble = new Double(aRetainLine);
			double d = tDouble.doubleValue();
			RetainLine = d;
		}
	}

	/**
	* Y--是 N--否
	*/
	public double getCessionLimit()
	{
		return CessionLimit;
	}
	public void setCessionLimit(double aCessionLimit)
	{
		CessionLimit = aCessionLimit;
	}
	public void setCessionLimit(String aCessionLimit)
	{
		if (aCessionLimit != null && !aCessionLimit.equals(""))
		{
			Double tDouble = new Double(aCessionLimit);
			double d = tDouble.doubleValue();
			CessionLimit = d;
		}
	}

	/**
	* S--溢额<p>
	* Q--成数<p>
	* M--溢额＋成数
	*/
	public String getCessionMode()
	{
		return CessionMode;
	}
	public void setCessionMode(String aCessionMode)
	{
		CessionMode = aCessionMode;
	}
	public double getExMorRate()
	{
		return ExMorRate;
	}
	public void setExMorRate(double aExMorRate)
	{
		ExMorRate = aExMorRate;
	}
	public void setExMorRate(String aExMorRate)
	{
		if (aExMorRate != null && !aExMorRate.equals(""))
		{
			Double tDouble = new Double(aExMorRate);
			double d = tDouble.doubleValue();
			ExMorRate = d;
		}
	}

	public double getRetentRate()
	{
		return RetentRate;
	}
	public void setRetentRate(double aRetentRate)
	{
		RetentRate = aRetentRate;
	}
	public void setRetentRate(String aRetentRate)
	{
		if (aRetentRate != null && !aRetentRate.equals(""))
		{
			Double tDouble = new Double(aRetentRate);
			double d = tDouble.doubleValue();
			RetentRate = d;
		}
	}

	public double getCommsionRate()
	{
		return CommsionRate;
	}
	public void setCommsionRate(double aCommsionRate)
	{
		CommsionRate = aCommsionRate;
	}
	public void setCommsionRate(String aCommsionRate)
	{
		if (aCommsionRate != null && !aCommsionRate.equals(""))
		{
			Double tDouble = new Double(aCommsionRate);
			double d = tDouble.doubleValue();
			CommsionRate = d;
		}
	}

	/**
	* 000000- 原始年交保费<p>
	* 其它-   再保公司提供分保费率
	*/
	public String getCessPremMode()
	{
		return CessPremMode;
	}
	public void setCessPremMode(String aCessPremMode)
	{
		CessPremMode = aCessPremMode;
	}
	public String getRiskAmntCalCode()
	{
		return RiskAmntCalCode;
	}
	public void setRiskAmntCalCode(String aRiskAmntCalCode)
	{
		RiskAmntCalCode = aRiskAmntCalCode;
	}
	public String getCessPremCalCode()
	{
		return CessPremCalCode;
	}
	public void setCessPremCalCode(String aCessPremCalCode)
	{
		CessPremCalCode = aCessPremCalCode;
	}
	public String getCessCommCalCode2()
	{
		return CessCommCalCode2;
	}
	public void setCessCommCalCode2(String aCessCommCalCode2)
	{
		CessCommCalCode2 = aCessCommCalCode2;
	}
	public String getReturnPayCalCode()
	{
		return ReturnPayCalCode;
	}
	public void setReturnPayCalCode(String aReturnPayCalCode)
	{
		ReturnPayCalCode = aReturnPayCalCode;
	}
	public String getNegoCalCode()
	{
		return NegoCalCode;
	}
	public void setNegoCalCode(String aNegoCalCode)
	{
		NegoCalCode = aNegoCalCode;
	}
	public double getGrpPerAmtLimit()
	{
		return GrpPerAmtLimit;
	}
	public void setGrpPerAmtLimit(double aGrpPerAmtLimit)
	{
		GrpPerAmtLimit = aGrpPerAmtLimit;
	}
	public void setGrpPerAmtLimit(String aGrpPerAmtLimit)
	{
		if (aGrpPerAmtLimit != null && !aGrpPerAmtLimit.equals(""))
		{
			Double tDouble = new Double(aGrpPerAmtLimit);
			double d = tDouble.doubleValue();
			GrpPerAmtLimit = d;
		}
	}

	public double getGrpAverAmntLimit()
	{
		return GrpAverAmntLimit;
	}
	public void setGrpAverAmntLimit(double aGrpAverAmntLimit)
	{
		GrpAverAmntLimit = aGrpAverAmntLimit;
	}
	public void setGrpAverAmntLimit(String aGrpAverAmntLimit)
	{
		if (aGrpAverAmntLimit != null && !aGrpAverAmntLimit.equals(""))
		{
			Double tDouble = new Double(aGrpAverAmntLimit);
			double d = tDouble.doubleValue();
			GrpAverAmntLimit = d;
		}
	}

	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}

	/**
	* 使用另外一个 LRRiskSchema 对象给 Schema 赋值
	* @param: aLRRiskSchema LRRiskSchema
	**/
	public void setSchema(LRRiskSchema aLRRiskSchema)
	{
		this.RiskCode = aLRRiskSchema.getRiskCode();
		this.RiskSort = aLRRiskSchema.getRiskSort();
		this.RiskCalSort = aLRRiskSchema.getRiskCalSort();
		this.ReinsureCom = aLRRiskSchema.getReinsureCom();
		this.ReinsurItem = aLRRiskSchema.getReinsurItem();
		this.RetainLine = aLRRiskSchema.getRetainLine();
		this.CessionLimit = aLRRiskSchema.getCessionLimit();
		this.CessionMode = aLRRiskSchema.getCessionMode();
		this.ExMorRate = aLRRiskSchema.getExMorRate();
		this.RetentRate = aLRRiskSchema.getRetentRate();
		this.CommsionRate = aLRRiskSchema.getCommsionRate();
		this.CessPremMode = aLRRiskSchema.getCessPremMode();
		this.RiskAmntCalCode = aLRRiskSchema.getRiskAmntCalCode();
		this.CessPremCalCode = aLRRiskSchema.getCessPremCalCode();
		this.CessCommCalCode2 = aLRRiskSchema.getCessCommCalCode2();
		this.ReturnPayCalCode = aLRRiskSchema.getReturnPayCalCode();
		this.NegoCalCode = aLRRiskSchema.getNegoCalCode();
		this.GrpPerAmtLimit = aLRRiskSchema.getGrpPerAmtLimit();
		this.GrpAverAmntLimit = aLRRiskSchema.getGrpAverAmntLimit();
		this.DutyCode = aLRRiskSchema.getDutyCode();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskSort") == null )
				this.RiskSort = null;
			else
				this.RiskSort = rs.getString("RiskSort").trim();

			if( rs.getString("RiskCalSort") == null )
				this.RiskCalSort = null;
			else
				this.RiskCalSort = rs.getString("RiskCalSort").trim();

			if( rs.getString("ReinsureCom") == null )
				this.ReinsureCom = null;
			else
				this.ReinsureCom = rs.getString("ReinsureCom").trim();

			if( rs.getString("ReinsurItem") == null )
				this.ReinsurItem = null;
			else
				this.ReinsurItem = rs.getString("ReinsurItem").trim();

			this.RetainLine = rs.getDouble("RetainLine");
			this.CessionLimit = rs.getDouble("CessionLimit");
			if( rs.getString("CessionMode") == null )
				this.CessionMode = null;
			else
				this.CessionMode = rs.getString("CessionMode").trim();

			this.ExMorRate = rs.getDouble("ExMorRate");
			this.RetentRate = rs.getDouble("RetentRate");
			this.CommsionRate = rs.getDouble("CommsionRate");
			if( rs.getString("CessPremMode") == null )
				this.CessPremMode = null;
			else
				this.CessPremMode = rs.getString("CessPremMode").trim();

			if( rs.getString("RiskAmntCalCode") == null )
				this.RiskAmntCalCode = null;
			else
				this.RiskAmntCalCode = rs.getString("RiskAmntCalCode").trim();

			if( rs.getString("CessPremCalCode") == null )
				this.CessPremCalCode = null;
			else
				this.CessPremCalCode = rs.getString("CessPremCalCode").trim();

			if( rs.getString("CessCommCalCode2") == null )
				this.CessCommCalCode2 = null;
			else
				this.CessCommCalCode2 = rs.getString("CessCommCalCode2").trim();

			if( rs.getString("ReturnPayCalCode") == null )
				this.ReturnPayCalCode = null;
			else
				this.ReturnPayCalCode = rs.getString("ReturnPayCalCode").trim();

			if( rs.getString("NegoCalCode") == null )
				this.NegoCalCode = null;
			else
				this.NegoCalCode = rs.getString("NegoCalCode").trim();

			this.GrpPerAmtLimit = rs.getDouble("GrpPerAmtLimit");
			this.GrpAverAmntLimit = rs.getDouble("GrpAverAmntLimit");
			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRRiskSchema getSchema()
	{
		LRRiskSchema aLRRiskSchema = new LRRiskSchema();
		aLRRiskSchema.setSchema(this);
		return aLRRiskSchema;
	}

	public LRRiskDB getDB()
	{
		LRRiskDB aDBOper = new LRRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCalSort)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsureCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReinsurItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetainLine));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CessionLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CessionMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExMorRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetentRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CommsionRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CessPremMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAmntCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CessPremCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CessCommCalCode2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReturnPayCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NegoCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GrpPerAmtLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GrpAverAmntLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRisk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCalSort = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReinsureCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReinsurItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RetainLine = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			CessionLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			CessionMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ExMorRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			RetentRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			CommsionRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			CessPremMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RiskAmntCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CessPremCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CessCommCalCode2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ReturnPayCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			NegoCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			GrpPerAmtLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			GrpAverAmntLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRiskSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskSort));
		}
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCalSort));
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsureCom));
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReinsurItem));
		}
		if (FCode.equalsIgnoreCase("RetainLine"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetainLine));
		}
		if (FCode.equalsIgnoreCase("CessionLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionLimit));
		}
		if (FCode.equalsIgnoreCase("CessionMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessionMode));
		}
		if (FCode.equalsIgnoreCase("ExMorRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExMorRate));
		}
		if (FCode.equalsIgnoreCase("RetentRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetentRate));
		}
		if (FCode.equalsIgnoreCase("CommsionRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommsionRate));
		}
		if (FCode.equalsIgnoreCase("CessPremMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessPremMode));
		}
		if (FCode.equalsIgnoreCase("RiskAmntCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAmntCalCode));
		}
		if (FCode.equalsIgnoreCase("CessPremCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessPremCalCode));
		}
		if (FCode.equalsIgnoreCase("CessCommCalCode2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CessCommCalCode2));
		}
		if (FCode.equalsIgnoreCase("ReturnPayCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnPayCalCode));
		}
		if (FCode.equalsIgnoreCase("NegoCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NegoCalCode));
		}
		if (FCode.equalsIgnoreCase("GrpPerAmtLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPerAmtLimit));
		}
		if (FCode.equalsIgnoreCase("GrpAverAmntLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpAverAmntLimit));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskSort);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCalSort);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReinsureCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReinsurItem);
				break;
			case 5:
				strFieldValue = String.valueOf(RetainLine);
				break;
			case 6:
				strFieldValue = String.valueOf(CessionLimit);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CessionMode);
				break;
			case 8:
				strFieldValue = String.valueOf(ExMorRate);
				break;
			case 9:
				strFieldValue = String.valueOf(RetentRate);
				break;
			case 10:
				strFieldValue = String.valueOf(CommsionRate);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CessPremMode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RiskAmntCalCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CessPremCalCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CessCommCalCode2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ReturnPayCalCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(NegoCalCode);
				break;
			case 17:
				strFieldValue = String.valueOf(GrpPerAmtLimit);
				break;
			case 18:
				strFieldValue = String.valueOf(GrpAverAmntLimit);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
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

		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("RiskSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskSort = FValue.trim();
			}
			else
				RiskSort = null;
		}
		if (FCode.equalsIgnoreCase("RiskCalSort"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCalSort = FValue.trim();
			}
			else
				RiskCalSort = null;
		}
		if (FCode.equalsIgnoreCase("ReinsureCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsureCom = FValue.trim();
			}
			else
				ReinsureCom = null;
		}
		if (FCode.equalsIgnoreCase("ReinsurItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReinsurItem = FValue.trim();
			}
			else
				ReinsurItem = null;
		}
		if (FCode.equalsIgnoreCase("RetainLine"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RetainLine = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CessionLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessionMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CessionMode = FValue.trim();
			}
			else
				CessionMode = null;
		}
		if (FCode.equalsIgnoreCase("ExMorRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExMorRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("RetentRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RetentRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CommsionRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CommsionRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("CessPremMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CessPremMode = FValue.trim();
			}
			else
				CessPremMode = null;
		}
		if (FCode.equalsIgnoreCase("RiskAmntCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAmntCalCode = FValue.trim();
			}
			else
				RiskAmntCalCode = null;
		}
		if (FCode.equalsIgnoreCase("CessPremCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CessPremCalCode = FValue.trim();
			}
			else
				CessPremCalCode = null;
		}
		if (FCode.equalsIgnoreCase("CessCommCalCode2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CessCommCalCode2 = FValue.trim();
			}
			else
				CessCommCalCode2 = null;
		}
		if (FCode.equalsIgnoreCase("ReturnPayCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnPayCalCode = FValue.trim();
			}
			else
				ReturnPayCalCode = null;
		}
		if (FCode.equalsIgnoreCase("NegoCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NegoCalCode = FValue.trim();
			}
			else
				NegoCalCode = null;
		}
		if (FCode.equalsIgnoreCase("GrpPerAmtLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GrpPerAmtLimit = d;
			}
		}
		if (FCode.equalsIgnoreCase("GrpAverAmntLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GrpAverAmntLimit = d;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRRiskSchema other = (LRRiskSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskSort.equals(other.getRiskSort())
			&& RiskCalSort.equals(other.getRiskCalSort())
			&& ReinsureCom.equals(other.getReinsureCom())
			&& ReinsurItem.equals(other.getReinsurItem())
			&& RetainLine == other.getRetainLine()
			&& CessionLimit == other.getCessionLimit()
			&& CessionMode.equals(other.getCessionMode())
			&& ExMorRate == other.getExMorRate()
			&& RetentRate == other.getRetentRate()
			&& CommsionRate == other.getCommsionRate()
			&& CessPremMode.equals(other.getCessPremMode())
			&& RiskAmntCalCode.equals(other.getRiskAmntCalCode())
			&& CessPremCalCode.equals(other.getCessPremCalCode())
			&& CessCommCalCode2.equals(other.getCessCommCalCode2())
			&& ReturnPayCalCode.equals(other.getReturnPayCalCode())
			&& NegoCalCode.equals(other.getNegoCalCode())
			&& GrpPerAmtLimit == other.getGrpPerAmtLimit()
			&& GrpAverAmntLimit == other.getGrpAverAmntLimit()
			&& DutyCode.equals(other.getDutyCode());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskSort") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return 2;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return 3;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return 4;
		}
		if( strFieldName.equals("RetainLine") ) {
			return 5;
		}
		if( strFieldName.equals("CessionLimit") ) {
			return 6;
		}
		if( strFieldName.equals("CessionMode") ) {
			return 7;
		}
		if( strFieldName.equals("ExMorRate") ) {
			return 8;
		}
		if( strFieldName.equals("RetentRate") ) {
			return 9;
		}
		if( strFieldName.equals("CommsionRate") ) {
			return 10;
		}
		if( strFieldName.equals("CessPremMode") ) {
			return 11;
		}
		if( strFieldName.equals("RiskAmntCalCode") ) {
			return 12;
		}
		if( strFieldName.equals("CessPremCalCode") ) {
			return 13;
		}
		if( strFieldName.equals("CessCommCalCode2") ) {
			return 14;
		}
		if( strFieldName.equals("ReturnPayCalCode") ) {
			return 15;
		}
		if( strFieldName.equals("NegoCalCode") ) {
			return 16;
		}
		if( strFieldName.equals("GrpPerAmtLimit") ) {
			return 17;
		}
		if( strFieldName.equals("GrpAverAmntLimit") ) {
			return 18;
		}
		if( strFieldName.equals("DutyCode") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskSort";
				break;
			case 2:
				strFieldName = "RiskCalSort";
				break;
			case 3:
				strFieldName = "ReinsureCom";
				break;
			case 4:
				strFieldName = "ReinsurItem";
				break;
			case 5:
				strFieldName = "RetainLine";
				break;
			case 6:
				strFieldName = "CessionLimit";
				break;
			case 7:
				strFieldName = "CessionMode";
				break;
			case 8:
				strFieldName = "ExMorRate";
				break;
			case 9:
				strFieldName = "RetentRate";
				break;
			case 10:
				strFieldName = "CommsionRate";
				break;
			case 11:
				strFieldName = "CessPremMode";
				break;
			case 12:
				strFieldName = "RiskAmntCalCode";
				break;
			case 13:
				strFieldName = "CessPremCalCode";
				break;
			case 14:
				strFieldName = "CessCommCalCode2";
				break;
			case 15:
				strFieldName = "ReturnPayCalCode";
				break;
			case 16:
				strFieldName = "NegoCalCode";
				break;
			case 17:
				strFieldName = "GrpPerAmtLimit";
				break;
			case 18:
				strFieldName = "GrpAverAmntLimit";
				break;
			case 19:
				strFieldName = "DutyCode";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCalSort") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsureCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReinsurItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RetainLine") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessionMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExMorRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RetentRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CommsionRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CessPremMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAmntCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CessPremCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CessCommCalCode2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnPayCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NegoCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPerAmtLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GrpAverAmntLimit") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("DutyCode") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
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
