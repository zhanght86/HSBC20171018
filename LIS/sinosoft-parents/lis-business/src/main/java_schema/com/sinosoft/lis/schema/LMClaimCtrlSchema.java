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
import com.sinosoft.lis.db.LMClaimCtrlDB;

/*
 * <p>ClassName: LMClaimCtrlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMClaimCtrlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMClaimCtrlSchema.class);

	// @Field
	/** 理赔控制编号 */
	private String ClaimCtrlCode;
	/** 理赔控制名称 */
	private String ClaimCtrlName;
	/** 类型 */
	private String ClaimCtrlType;
	/** 理赔控制描述 */
	private String ClaimEngineDesc;
	/** 有效期间 */
	private String PeriodFlag;
	/** 自定义期间标记 */
	private String DefPeriodFlag;
	/** 个人家庭标记 */
	private String FamilyFlag;
	/** 保单有效期/内外 */
	private String InsPeriodFlag;
	/** 赔付期间控制上限 */
	private int ClmPeriodMAX;
	/** 赔付期间上限单位 */
	private String ClmPeriodMAXFlag;
	/** 赔付期间上限控制计算参考 */
	private String ClmPeriodMAXCtrl;
	/** 赔付期间下限控制 */
	private int ClmPeriodMIN;
	/** 赔付期间下限控制单位 */
	private String ClmPeriodMINFlag;
	/** 赔付期间下限控制计算参考 */
	private String ClmPeriodMINCtrl;
	/** 理赔控制计算sql */
	private String CalCode;
	/** 理赔控制处理值类型 */
	private String CalResultType;
	/** 理赔控制默认值 */
	private double DefaultValue;
	/** 理赔控制计算方式 */
	private String CalCtrlFlag;
	/** 理赔费用控制计算sql */
	private String FeeCalCode;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMClaimCtrlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClaimCtrlCode";

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
		LMClaimCtrlSchema cloned = (LMClaimCtrlSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClaimCtrlCode()
	{
		return ClaimCtrlCode;
	}
	public void setClaimCtrlCode(String aClaimCtrlCode)
	{
		ClaimCtrlCode = aClaimCtrlCode;
	}
	public String getClaimCtrlName()
	{
		return ClaimCtrlName;
	}
	public void setClaimCtrlName(String aClaimCtrlName)
	{
		ClaimCtrlName = aClaimCtrlName;
	}
	/**
	* 1.扣减(免赔额) 2.给付比例(投保人,被保险人共付或者赔付比例) 3.金额险种(保额上限) 4.服务限制(天数等) 5.补贴金额
	*/
	public String getClaimCtrlType()
	{
		return ClaimCtrlType;
	}
	public void setClaimCtrlType(String aClaimCtrlType)
	{
		ClaimCtrlType = aClaimCtrlType;
	}
	public String getClaimEngineDesc()
	{
		return ClaimEngineDesc;
	}
	public void setClaimEngineDesc(String aClaimEngineDesc)
	{
		ClaimEngineDesc = aClaimEngineDesc;
	}
	/**
	* 1.保单年度 2.日历年度 3.保险期间 4.每月 5.每天 6.每次赔案 7 每次事故
	*/
	public String getPeriodFlag()
	{
		return PeriodFlag;
	}
	public void setPeriodFlag(String aPeriodFlag)
	{
		PeriodFlag = aPeriodFlag;
	}
	/**
	* 用来定义在一定有效期间内根据时间段的不同,赔付值不同的情况.决定ClmDefPeriod表的记录是否产生作用
	*/
	public String getDefPeriodFlag()
	{
		return DefPeriodFlag;
	}
	public void setDefPeriodFlag(String aDefPeriodFlag)
	{
		DefPeriodFlag = aDefPeriodFlag;
	}
	/**
	* 是否共用保额
	*/
	public String getFamilyFlag()
	{
		return FamilyFlag;
	}
	public void setFamilyFlag(String aFamilyFlag)
	{
		FamilyFlag = aFamilyFlag;
	}
	/**
	* 该累加器用于保险期间内还是保险期间外赔付标记 1.保险期间内 2.包括期外
	*/
	public String getInsPeriodFlag()
	{
		return InsPeriodFlag;
	}
	public void setInsPeriodFlag(String aInsPeriodFlag)
	{
		InsPeriodFlag = aInsPeriodFlag;
	}
	public int getClmPeriodMAX()
	{
		return ClmPeriodMAX;
	}
	public void setClmPeriodMAX(int aClmPeriodMAX)
	{
		ClmPeriodMAX = aClmPeriodMAX;
	}
	public void setClmPeriodMAX(String aClmPeriodMAX)
	{
		if (aClmPeriodMAX != null && !aClmPeriodMAX.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodMAX);
			int i = tInteger.intValue();
			ClmPeriodMAX = i;
		}
	}

	/**
	* Y--年 M--月 D--日 A--年龄
	*/
	public String getClmPeriodMAXFlag()
	{
		return ClmPeriodMAXFlag;
	}
	public void setClmPeriodMAXFlag(String aClmPeriodMAXFlag)
	{
		ClmPeriodMAXFlag = aClmPeriodMAXFlag;
	}
	/**
	* S--起保日期对应日 B--出生日期对应日 C—出险日期
	*/
	public String getClmPeriodMAXCtrl()
	{
		return ClmPeriodMAXCtrl;
	}
	public void setClmPeriodMAXCtrl(String aClmPeriodMAXCtrl)
	{
		ClmPeriodMAXCtrl = aClmPeriodMAXCtrl;
	}
	public int getClmPeriodMIN()
	{
		return ClmPeriodMIN;
	}
	public void setClmPeriodMIN(int aClmPeriodMIN)
	{
		ClmPeriodMIN = aClmPeriodMIN;
	}
	public void setClmPeriodMIN(String aClmPeriodMIN)
	{
		if (aClmPeriodMIN != null && !aClmPeriodMIN.equals(""))
		{
			Integer tInteger = new Integer(aClmPeriodMIN);
			int i = tInteger.intValue();
			ClmPeriodMIN = i;
		}
	}

	/**
	* Y--年 M--月 D--日 A--年龄
	*/
	public String getClmPeriodMINFlag()
	{
		return ClmPeriodMINFlag;
	}
	public void setClmPeriodMINFlag(String aClmPeriodMINFlag)
	{
		ClmPeriodMINFlag = aClmPeriodMINFlag;
	}
	/**
	* S--起保日期对应日 B--出生日期对应日 C—出险日期
	*/
	public String getClmPeriodMINCtrl()
	{
		return ClmPeriodMINCtrl;
	}
	public void setClmPeriodMINCtrl(String aClmPeriodMINCtrl)
	{
		ClmPeriodMINCtrl = aClmPeriodMINCtrl;
	}
	/**
	* 实例化累加器时计算公式
	*/
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/**
	* 1.比例 2.金额
	*/
	public String getCalResultType()
	{
		return CalResultType;
	}
	public void setCalResultType(String aCalResultType)
	{
		CalResultType = aCalResultType;
	}
	public double getDefaultValue()
	{
		return DefaultValue;
	}
	public void setDefaultValue(double aDefaultValue)
	{
		DefaultValue = aDefaultValue;
	}
	public void setDefaultValue(String aDefaultValue)
	{
		if (aDefaultValue != null && !aDefaultValue.equals(""))
		{
			Double tDouble = new Double(aDefaultValue);
			double d = tDouble.doubleValue();
			DefaultValue = d;
		}
	}

	/**
	* 1.取描述默认值 2.实例化时取默认计算要素计算 3.实例化时取约定计算要素计算
	*/
	public String getCalCtrlFlag()
	{
		return CalCtrlFlag;
	}
	public void setCalCtrlFlag(String aCalCtrlFlag)
	{
		CalCtrlFlag = aCalCtrlFlag;
	}
	public String getFeeCalCode()
	{
		return FeeCalCode;
	}
	public void setFeeCalCode(String aFeeCalCode)
	{
		FeeCalCode = aFeeCalCode;
	}

	/**
	* 使用另外一个 LMClaimCtrlSchema 对象给 Schema 赋值
	* @param: aLMClaimCtrlSchema LMClaimCtrlSchema
	**/
	public void setSchema(LMClaimCtrlSchema aLMClaimCtrlSchema)
	{
		this.ClaimCtrlCode = aLMClaimCtrlSchema.getClaimCtrlCode();
		this.ClaimCtrlName = aLMClaimCtrlSchema.getClaimCtrlName();
		this.ClaimCtrlType = aLMClaimCtrlSchema.getClaimCtrlType();
		this.ClaimEngineDesc = aLMClaimCtrlSchema.getClaimEngineDesc();
		this.PeriodFlag = aLMClaimCtrlSchema.getPeriodFlag();
		this.DefPeriodFlag = aLMClaimCtrlSchema.getDefPeriodFlag();
		this.FamilyFlag = aLMClaimCtrlSchema.getFamilyFlag();
		this.InsPeriodFlag = aLMClaimCtrlSchema.getInsPeriodFlag();
		this.ClmPeriodMAX = aLMClaimCtrlSchema.getClmPeriodMAX();
		this.ClmPeriodMAXFlag = aLMClaimCtrlSchema.getClmPeriodMAXFlag();
		this.ClmPeriodMAXCtrl = aLMClaimCtrlSchema.getClmPeriodMAXCtrl();
		this.ClmPeriodMIN = aLMClaimCtrlSchema.getClmPeriodMIN();
		this.ClmPeriodMINFlag = aLMClaimCtrlSchema.getClmPeriodMINFlag();
		this.ClmPeriodMINCtrl = aLMClaimCtrlSchema.getClmPeriodMINCtrl();
		this.CalCode = aLMClaimCtrlSchema.getCalCode();
		this.CalResultType = aLMClaimCtrlSchema.getCalResultType();
		this.DefaultValue = aLMClaimCtrlSchema.getDefaultValue();
		this.CalCtrlFlag = aLMClaimCtrlSchema.getCalCtrlFlag();
		this.FeeCalCode = aLMClaimCtrlSchema.getFeeCalCode();
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
			if( rs.getString("ClaimCtrlCode") == null )
				this.ClaimCtrlCode = null;
			else
				this.ClaimCtrlCode = rs.getString("ClaimCtrlCode").trim();

			if( rs.getString("ClaimCtrlName") == null )
				this.ClaimCtrlName = null;
			else
				this.ClaimCtrlName = rs.getString("ClaimCtrlName").trim();

			if( rs.getString("ClaimCtrlType") == null )
				this.ClaimCtrlType = null;
			else
				this.ClaimCtrlType = rs.getString("ClaimCtrlType").trim();

			if( rs.getString("ClaimEngineDesc") == null )
				this.ClaimEngineDesc = null;
			else
				this.ClaimEngineDesc = rs.getString("ClaimEngineDesc").trim();

			if( rs.getString("PeriodFlag") == null )
				this.PeriodFlag = null;
			else
				this.PeriodFlag = rs.getString("PeriodFlag").trim();

			if( rs.getString("DefPeriodFlag") == null )
				this.DefPeriodFlag = null;
			else
				this.DefPeriodFlag = rs.getString("DefPeriodFlag").trim();

			if( rs.getString("FamilyFlag") == null )
				this.FamilyFlag = null;
			else
				this.FamilyFlag = rs.getString("FamilyFlag").trim();

			if( rs.getString("InsPeriodFlag") == null )
				this.InsPeriodFlag = null;
			else
				this.InsPeriodFlag = rs.getString("InsPeriodFlag").trim();

			this.ClmPeriodMAX = rs.getInt("ClmPeriodMAX");
			if( rs.getString("ClmPeriodMAXFlag") == null )
				this.ClmPeriodMAXFlag = null;
			else
				this.ClmPeriodMAXFlag = rs.getString("ClmPeriodMAXFlag").trim();

			if( rs.getString("ClmPeriodMAXCtrl") == null )
				this.ClmPeriodMAXCtrl = null;
			else
				this.ClmPeriodMAXCtrl = rs.getString("ClmPeriodMAXCtrl").trim();

			this.ClmPeriodMIN = rs.getInt("ClmPeriodMIN");
			if( rs.getString("ClmPeriodMINFlag") == null )
				this.ClmPeriodMINFlag = null;
			else
				this.ClmPeriodMINFlag = rs.getString("ClmPeriodMINFlag").trim();

			if( rs.getString("ClmPeriodMINCtrl") == null )
				this.ClmPeriodMINCtrl = null;
			else
				this.ClmPeriodMINCtrl = rs.getString("ClmPeriodMINCtrl").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalResultType") == null )
				this.CalResultType = null;
			else
				this.CalResultType = rs.getString("CalResultType").trim();

			this.DefaultValue = rs.getDouble("DefaultValue");
			if( rs.getString("CalCtrlFlag") == null )
				this.CalCtrlFlag = null;
			else
				this.CalCtrlFlag = rs.getString("CalCtrlFlag").trim();

			if( rs.getString("FeeCalCode") == null )
				this.FeeCalCode = null;
			else
				this.FeeCalCode = rs.getString("FeeCalCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMClaimCtrl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMClaimCtrlSchema getSchema()
	{
		LMClaimCtrlSchema aLMClaimCtrlSchema = new LMClaimCtrlSchema();
		aLMClaimCtrlSchema.setSchema(this);
		return aLMClaimCtrlSchema;
	}

	public LMClaimCtrlDB getDB()
	{
		LMClaimCtrlDB aDBOper = new LMClaimCtrlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimCtrlCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimCtrlName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimCtrlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClaimEngineDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DefPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FamilyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsPeriodFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodMAX));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMAXFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMAXCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClmPeriodMIN));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMINFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmPeriodMINCtrl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalResultType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DefaultValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCtrlFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCalCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMClaimCtrl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimCtrlCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ClaimCtrlName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ClaimCtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClaimEngineDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DefPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FamilyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsPeriodFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ClmPeriodMAX= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodMAXFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ClmPeriodMAXCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ClmPeriodMIN= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			ClmPeriodMINFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ClmPeriodMINCtrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CalResultType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DefaultValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			CalCtrlFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			FeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMClaimCtrlSchema";
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
		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlCode));
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlName));
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimCtrlType));
		}
		if (FCode.equalsIgnoreCase("ClaimEngineDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimEngineDesc));
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PeriodFlag));
		}
		if (FCode.equalsIgnoreCase("DefPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("FamilyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FamilyFlag));
		}
		if (FCode.equalsIgnoreCase("InsPeriodFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsPeriodFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAX"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAX));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAXFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMAXCtrl));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMIN"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMIN));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMINFlag));
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINCtrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmPeriodMINCtrl));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalResultType));
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefaultValue));
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCtrlFlag));
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCalCode));
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
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ClaimCtrlType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClaimEngineDesc);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PeriodFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DefPeriodFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FamilyFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsPeriodFlag);
				break;
			case 8:
				strFieldValue = String.valueOf(ClmPeriodMAX);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMAXFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMAXCtrl);
				break;
			case 11:
				strFieldValue = String.valueOf(ClmPeriodMIN);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMINFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ClmPeriodMINCtrl);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CalResultType);
				break;
			case 16:
				strFieldValue = String.valueOf(DefaultValue);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CalCtrlFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(FeeCalCode);
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

		if (FCode.equalsIgnoreCase("ClaimCtrlCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlCode = FValue.trim();
			}
			else
				ClaimCtrlCode = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlName = FValue.trim();
			}
			else
				ClaimCtrlName = null;
		}
		if (FCode.equalsIgnoreCase("ClaimCtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimCtrlType = FValue.trim();
			}
			else
				ClaimCtrlType = null;
		}
		if (FCode.equalsIgnoreCase("ClaimEngineDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimEngineDesc = FValue.trim();
			}
			else
				ClaimEngineDesc = null;
		}
		if (FCode.equalsIgnoreCase("PeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PeriodFlag = FValue.trim();
			}
			else
				PeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("DefPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefPeriodFlag = FValue.trim();
			}
			else
				DefPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("FamilyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FamilyFlag = FValue.trim();
			}
			else
				FamilyFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsPeriodFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsPeriodFlag = FValue.trim();
			}
			else
				InsPeriodFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAX"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodMAX = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMAXFlag = FValue.trim();
			}
			else
				ClmPeriodMAXFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMAXCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMAXCtrl = FValue.trim();
			}
			else
				ClmPeriodMAXCtrl = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMIN"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClmPeriodMIN = i;
			}
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMINFlag = FValue.trim();
			}
			else
				ClmPeriodMINFlag = null;
		}
		if (FCode.equalsIgnoreCase("ClmPeriodMINCtrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmPeriodMINCtrl = FValue.trim();
			}
			else
				ClmPeriodMINCtrl = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalResultType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalResultType = FValue.trim();
			}
			else
				CalResultType = null;
		}
		if (FCode.equalsIgnoreCase("DefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DefaultValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalCtrlFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCtrlFlag = FValue.trim();
			}
			else
				CalCtrlFlag = null;
		}
		if (FCode.equalsIgnoreCase("FeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCalCode = FValue.trim();
			}
			else
				FeeCalCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMClaimCtrlSchema other = (LMClaimCtrlSchema)otherObject;
		return
			ClaimCtrlCode.equals(other.getClaimCtrlCode())
			&& ClaimCtrlName.equals(other.getClaimCtrlName())
			&& ClaimCtrlType.equals(other.getClaimCtrlType())
			&& ClaimEngineDesc.equals(other.getClaimEngineDesc())
			&& PeriodFlag.equals(other.getPeriodFlag())
			&& DefPeriodFlag.equals(other.getDefPeriodFlag())
			&& FamilyFlag.equals(other.getFamilyFlag())
			&& InsPeriodFlag.equals(other.getInsPeriodFlag())
			&& ClmPeriodMAX == other.getClmPeriodMAX()
			&& ClmPeriodMAXFlag.equals(other.getClmPeriodMAXFlag())
			&& ClmPeriodMAXCtrl.equals(other.getClmPeriodMAXCtrl())
			&& ClmPeriodMIN == other.getClmPeriodMIN()
			&& ClmPeriodMINFlag.equals(other.getClmPeriodMINFlag())
			&& ClmPeriodMINCtrl.equals(other.getClmPeriodMINCtrl())
			&& CalCode.equals(other.getCalCode())
			&& CalResultType.equals(other.getCalResultType())
			&& DefaultValue == other.getDefaultValue()
			&& CalCtrlFlag.equals(other.getCalCtrlFlag())
			&& FeeCalCode.equals(other.getFeeCalCode());
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return 0;
		}
		if( strFieldName.equals("ClaimCtrlName") ) {
			return 1;
		}
		if( strFieldName.equals("ClaimCtrlType") ) {
			return 2;
		}
		if( strFieldName.equals("ClaimEngineDesc") ) {
			return 3;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return 4;
		}
		if( strFieldName.equals("DefPeriodFlag") ) {
			return 5;
		}
		if( strFieldName.equals("FamilyFlag") ) {
			return 6;
		}
		if( strFieldName.equals("InsPeriodFlag") ) {
			return 7;
		}
		if( strFieldName.equals("ClmPeriodMAX") ) {
			return 8;
		}
		if( strFieldName.equals("ClmPeriodMAXFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ClmPeriodMAXCtrl") ) {
			return 10;
		}
		if( strFieldName.equals("ClmPeriodMIN") ) {
			return 11;
		}
		if( strFieldName.equals("ClmPeriodMINFlag") ) {
			return 12;
		}
		if( strFieldName.equals("ClmPeriodMINCtrl") ) {
			return 13;
		}
		if( strFieldName.equals("CalCode") ) {
			return 14;
		}
		if( strFieldName.equals("CalResultType") ) {
			return 15;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return 16;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
			return 17;
		}
		if( strFieldName.equals("FeeCalCode") ) {
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
				strFieldName = "ClaimCtrlCode";
				break;
			case 1:
				strFieldName = "ClaimCtrlName";
				break;
			case 2:
				strFieldName = "ClaimCtrlType";
				break;
			case 3:
				strFieldName = "ClaimEngineDesc";
				break;
			case 4:
				strFieldName = "PeriodFlag";
				break;
			case 5:
				strFieldName = "DefPeriodFlag";
				break;
			case 6:
				strFieldName = "FamilyFlag";
				break;
			case 7:
				strFieldName = "InsPeriodFlag";
				break;
			case 8:
				strFieldName = "ClmPeriodMAX";
				break;
			case 9:
				strFieldName = "ClmPeriodMAXFlag";
				break;
			case 10:
				strFieldName = "ClmPeriodMAXCtrl";
				break;
			case 11:
				strFieldName = "ClmPeriodMIN";
				break;
			case 12:
				strFieldName = "ClmPeriodMINFlag";
				break;
			case 13:
				strFieldName = "ClmPeriodMINCtrl";
				break;
			case 14:
				strFieldName = "CalCode";
				break;
			case 15:
				strFieldName = "CalResultType";
				break;
			case 16:
				strFieldName = "DefaultValue";
				break;
			case 17:
				strFieldName = "CalCtrlFlag";
				break;
			case 18:
				strFieldName = "FeeCalCode";
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
		if( strFieldName.equals("ClaimCtrlCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCtrlName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimCtrlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimEngineDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FamilyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsPeriodFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMAX") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodMAXFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMAXCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMIN") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ClmPeriodMINFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmPeriodMINCtrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalResultType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DefaultValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalCtrlFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCalCode") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
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
