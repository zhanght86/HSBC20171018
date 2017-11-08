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
import com.sinosoft.lis.db.LMRiskPayDB;

/*
 * <p>ClassName: LMRiskPaySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskPaySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskPaySchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 催缴标记 */
	private String UrgePayFlag;
	/** 手续费类型 */
	private String ChargeType;
	/** 分解缴费间隔 */
	private String CutPayIntv;
	/** 免交涉及加费 */
	private String PayAvoidType;
	/** 手续费与保费关系 */
	private String ChargeAndPrem;
	/** 结算日类型 */
	private String BalaDateType;
	/** 免交标记 */
	private String PayAvoidFlag;
	/** 缴费与复效关系 */
	private String PayAndRevEffe;
	/** 缴费宽限期 */
	private int GracePeriod;
	/** 宽限期单位 */
	private String GracePeriodUnit;
	/** 宽限日期计算方式 */
	private String GraceDateCalMode;
	/** 宽限期算法 */
	private String GraceCalCode;
	/** 逾期处理方式 */
	private String OverdueDeal;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskPaySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "RiskVer";

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
		LMRiskPaySchema cloned = (LMRiskPaySchema)super.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/**
	* Y--催付、N--不催付
	*/
	public String getUrgePayFlag()
	{
		return UrgePayFlag;
	}
	public void setUrgePayFlag(String aUrgePayFlag)
	{
		UrgePayFlag = aUrgePayFlag;
	}
	/**
	* 1-假手续费 0 - 真手续费
	*/
	public String getChargeType()
	{
		return ChargeType;
	}
	public void setChargeType(String aChargeType)
	{
		ChargeType = aChargeType;
	}
	/**
	* N - 不可分解 (交费间隔不同计算保费方法不同)、Y - 可分解 (交费与间隔成倍数关系)
	*/
	public String getCutPayIntv()
	{
		return CutPayIntv;
	}
	public void setCutPayIntv(String aCutPayIntv)
	{
		CutPayIntv = aCutPayIntv;
	}
	/**
	* N-不涉及、Y-涉及加费(没用)
	*/
	public String getPayAvoidType()
	{
		return PayAvoidType;
	}
	public void setPayAvoidType(String aPayAvoidType)
	{
		PayAvoidType = aPayAvoidType;
	}
	/**
	* N-手续费不能冲减保费 Y-手续费可以冲减保费
	*/
	public String getChargeAndPrem()
	{
		return ChargeAndPrem;
	}
	public void setChargeAndPrem(String aChargeAndPrem)
	{
		ChargeAndPrem = aChargeAndPrem;
	}
	/**
	* 1-结算日为会计年度末 2-结算日为起保日期 （针对基金险）其他为操作日期
	*/
	public String getBalaDateType()
	{
		return BalaDateType;
	}
	public void setBalaDateType(String aBalaDateType)
	{
		BalaDateType = aBalaDateType;
	}
	/**
	* Y--能、N--不能
	*/
	public String getPayAvoidFlag()
	{
		return PayAvoidFlag;
	}
	public void setPayAvoidFlag(String aPayAvoidFlag)
	{
		PayAvoidFlag = aPayAvoidFlag;
	}
	/**
	* N 失效后不能交费必须作复效批改、Y 失效后可以交费不必作复效批改
	*/
	public String getPayAndRevEffe()
	{
		return PayAndRevEffe;
	}
	public void setPayAndRevEffe(String aPayAndRevEffe)
	{
		PayAndRevEffe = aPayAndRevEffe;
	}
	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* Y--年 M--月 D--日
	*/
	public String getGracePeriodUnit()
	{
		return GracePeriodUnit;
	}
	public void setGracePeriodUnit(String aGracePeriodUnit)
	{
		GracePeriodUnit = aGracePeriodUnit;
	}
	/**
	* 0 - 以计算为准、1 - 下月一号、2 - 下年一号
	*/
	public String getGraceDateCalMode()
	{
		return GraceDateCalMode;
	}
	public void setGraceDateCalMode(String aGraceDateCalMode)
	{
		GraceDateCalMode = aGraceDateCalMode;
	}
	public String getGraceCalCode()
	{
		return GraceCalCode;
	}
	public void setGraceCalCode(String aGraceCalCode)
	{
		GraceCalCode = aGraceCalCode;
	}
	/**
	* L: 失效（Lapse） H：缓缴（Premiem Holiday） P: 缴清（Paid-up） R: 减额缴清（RPU） E: 展期（ETI） N: 不失效（Non-Lapse）
	*/
	public String getOverdueDeal()
	{
		return OverdueDeal;
	}
	public void setOverdueDeal(String aOverdueDeal)
	{
		OverdueDeal = aOverdueDeal;
	}

	/**
	* 使用另外一个 LMRiskPaySchema 对象给 Schema 赋值
	* @param: aLMRiskPaySchema LMRiskPaySchema
	**/
	public void setSchema(LMRiskPaySchema aLMRiskPaySchema)
	{
		this.RiskCode = aLMRiskPaySchema.getRiskCode();
		this.RiskVer = aLMRiskPaySchema.getRiskVer();
		this.RiskName = aLMRiskPaySchema.getRiskName();
		this.UrgePayFlag = aLMRiskPaySchema.getUrgePayFlag();
		this.ChargeType = aLMRiskPaySchema.getChargeType();
		this.CutPayIntv = aLMRiskPaySchema.getCutPayIntv();
		this.PayAvoidType = aLMRiskPaySchema.getPayAvoidType();
		this.ChargeAndPrem = aLMRiskPaySchema.getChargeAndPrem();
		this.BalaDateType = aLMRiskPaySchema.getBalaDateType();
		this.PayAvoidFlag = aLMRiskPaySchema.getPayAvoidFlag();
		this.PayAndRevEffe = aLMRiskPaySchema.getPayAndRevEffe();
		this.GracePeriod = aLMRiskPaySchema.getGracePeriod();
		this.GracePeriodUnit = aLMRiskPaySchema.getGracePeriodUnit();
		this.GraceDateCalMode = aLMRiskPaySchema.getGraceDateCalMode();
		this.GraceCalCode = aLMRiskPaySchema.getGraceCalCode();
		this.OverdueDeal = aLMRiskPaySchema.getOverdueDeal();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("UrgePayFlag") == null )
				this.UrgePayFlag = null;
			else
				this.UrgePayFlag = rs.getString("UrgePayFlag").trim();

			if( rs.getString("ChargeType") == null )
				this.ChargeType = null;
			else
				this.ChargeType = rs.getString("ChargeType").trim();

			if( rs.getString("CutPayIntv") == null )
				this.CutPayIntv = null;
			else
				this.CutPayIntv = rs.getString("CutPayIntv").trim();

			if( rs.getString("PayAvoidType") == null )
				this.PayAvoidType = null;
			else
				this.PayAvoidType = rs.getString("PayAvoidType").trim();

			if( rs.getString("ChargeAndPrem") == null )
				this.ChargeAndPrem = null;
			else
				this.ChargeAndPrem = rs.getString("ChargeAndPrem").trim();

			if( rs.getString("BalaDateType") == null )
				this.BalaDateType = null;
			else
				this.BalaDateType = rs.getString("BalaDateType").trim();

			if( rs.getString("PayAvoidFlag") == null )
				this.PayAvoidFlag = null;
			else
				this.PayAvoidFlag = rs.getString("PayAvoidFlag").trim();

			if( rs.getString("PayAndRevEffe") == null )
				this.PayAndRevEffe = null;
			else
				this.PayAndRevEffe = rs.getString("PayAndRevEffe").trim();

			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("GracePeriodUnit") == null )
				this.GracePeriodUnit = null;
			else
				this.GracePeriodUnit = rs.getString("GracePeriodUnit").trim();

			if( rs.getString("GraceDateCalMode") == null )
				this.GraceDateCalMode = null;
			else
				this.GraceDateCalMode = rs.getString("GraceDateCalMode").trim();

			if( rs.getString("GraceCalCode") == null )
				this.GraceCalCode = null;
			else
				this.GraceCalCode = rs.getString("GraceCalCode").trim();

			if( rs.getString("OverdueDeal") == null )
				this.OverdueDeal = null;
			else
				this.OverdueDeal = rs.getString("OverdueDeal").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskPay表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPaySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskPaySchema getSchema()
	{
		LMRiskPaySchema aLMRiskPaySchema = new LMRiskPaySchema();
		aLMRiskPaySchema.setSchema(this);
		return aLMRiskPaySchema;
	}

	public LMRiskPayDB getDB()
	{
		LMRiskPayDB aDBOper = new LMRiskPayDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskPay描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UrgePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CutPayIntv)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAvoidType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeAndPrem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalaDateType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAvoidFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayAndRevEffe)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GracePeriodUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GraceDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GraceCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OverdueDeal));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskPay>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UrgePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChargeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CutPayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PayAvoidType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ChargeAndPrem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BalaDateType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PayAvoidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PayAndRevEffe = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			GracePeriodUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			GraceDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			GraceCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			OverdueDeal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskPaySchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UrgePayFlag));
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeType));
		}
		if (FCode.equalsIgnoreCase("CutPayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CutPayIntv));
		}
		if (FCode.equalsIgnoreCase("PayAvoidType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAvoidType));
		}
		if (FCode.equalsIgnoreCase("ChargeAndPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeAndPrem));
		}
		if (FCode.equalsIgnoreCase("BalaDateType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalaDateType));
		}
		if (FCode.equalsIgnoreCase("PayAvoidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAvoidFlag));
		}
		if (FCode.equalsIgnoreCase("PayAndRevEffe"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayAndRevEffe));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriodUnit));
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraceDateCalMode));
		}
		if (FCode.equalsIgnoreCase("GraceCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraceCalCode));
		}
		if (FCode.equalsIgnoreCase("OverdueDeal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OverdueDeal));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UrgePayFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChargeType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CutPayIntv);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PayAvoidType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ChargeAndPrem);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BalaDateType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PayAvoidFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayAndRevEffe);
				break;
			case 11:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(GracePeriodUnit);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(GraceDateCalMode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(GraceCalCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(OverdueDeal);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("UrgePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UrgePayFlag = FValue.trim();
			}
			else
				UrgePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeType = FValue.trim();
			}
			else
				ChargeType = null;
		}
		if (FCode.equalsIgnoreCase("CutPayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CutPayIntv = FValue.trim();
			}
			else
				CutPayIntv = null;
		}
		if (FCode.equalsIgnoreCase("PayAvoidType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAvoidType = FValue.trim();
			}
			else
				PayAvoidType = null;
		}
		if (FCode.equalsIgnoreCase("ChargeAndPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeAndPrem = FValue.trim();
			}
			else
				ChargeAndPrem = null;
		}
		if (FCode.equalsIgnoreCase("BalaDateType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalaDateType = FValue.trim();
			}
			else
				BalaDateType = null;
		}
		if (FCode.equalsIgnoreCase("PayAvoidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAvoidFlag = FValue.trim();
			}
			else
				PayAvoidFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayAndRevEffe"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayAndRevEffe = FValue.trim();
			}
			else
				PayAndRevEffe = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GracePeriodUnit = FValue.trim();
			}
			else
				GracePeriodUnit = null;
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraceDateCalMode = FValue.trim();
			}
			else
				GraceDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("GraceCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraceCalCode = FValue.trim();
			}
			else
				GraceCalCode = null;
		}
		if (FCode.equalsIgnoreCase("OverdueDeal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OverdueDeal = FValue.trim();
			}
			else
				OverdueDeal = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskPaySchema other = (LMRiskPaySchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& UrgePayFlag.equals(other.getUrgePayFlag())
			&& ChargeType.equals(other.getChargeType())
			&& CutPayIntv.equals(other.getCutPayIntv())
			&& PayAvoidType.equals(other.getPayAvoidType())
			&& ChargeAndPrem.equals(other.getChargeAndPrem())
			&& BalaDateType.equals(other.getBalaDateType())
			&& PayAvoidFlag.equals(other.getPayAvoidFlag())
			&& PayAndRevEffe.equals(other.getPayAndRevEffe())
			&& GracePeriod == other.getGracePeriod()
			&& GracePeriodUnit.equals(other.getGracePeriodUnit())
			&& GraceDateCalMode.equals(other.getGraceDateCalMode())
			&& GraceCalCode.equals(other.getGraceCalCode())
			&& OverdueDeal.equals(other.getOverdueDeal());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return 3;
		}
		if( strFieldName.equals("ChargeType") ) {
			return 4;
		}
		if( strFieldName.equals("CutPayIntv") ) {
			return 5;
		}
		if( strFieldName.equals("PayAvoidType") ) {
			return 6;
		}
		if( strFieldName.equals("ChargeAndPrem") ) {
			return 7;
		}
		if( strFieldName.equals("BalaDateType") ) {
			return 8;
		}
		if( strFieldName.equals("PayAvoidFlag") ) {
			return 9;
		}
		if( strFieldName.equals("PayAndRevEffe") ) {
			return 10;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 11;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return 12;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
			return 13;
		}
		if( strFieldName.equals("GraceCalCode") ) {
			return 14;
		}
		if( strFieldName.equals("OverdueDeal") ) {
			return 15;
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
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "UrgePayFlag";
				break;
			case 4:
				strFieldName = "ChargeType";
				break;
			case 5:
				strFieldName = "CutPayIntv";
				break;
			case 6:
				strFieldName = "PayAvoidType";
				break;
			case 7:
				strFieldName = "ChargeAndPrem";
				break;
			case 8:
				strFieldName = "BalaDateType";
				break;
			case 9:
				strFieldName = "PayAvoidFlag";
				break;
			case 10:
				strFieldName = "PayAndRevEffe";
				break;
			case 11:
				strFieldName = "GracePeriod";
				break;
			case 12:
				strFieldName = "GracePeriodUnit";
				break;
			case 13:
				strFieldName = "GraceDateCalMode";
				break;
			case 14:
				strFieldName = "GraceCalCode";
				break;
			case 15:
				strFieldName = "OverdueDeal";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UrgePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CutPayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAvoidType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargeAndPrem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalaDateType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAvoidFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayAndRevEffe") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GraceCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OverdueDeal") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
