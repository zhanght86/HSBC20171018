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
import com.sinosoft.lis.db.LIItemElemDB;

/*
 * <p>ClassName: LIItemElemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LIItemElemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LIItemElemSchema.class);

	// @Field
	/** 科目类型 */
	private String AccType;
	/** 科目代码 */
	private String AccCode;
	/** 科目名称 */
	private String AccName;
	/** 借贷方 */
	private String PayWayFlag;
	/** 金额 */
	private String AccSumFlag;
	/** 成本中心 */
	private String DepartmentFlag;
	/** 销售渠道 */
	private String SellWayFlag;
	/** 性质1 */
	private String RiskKind1Flag;
	/** 性质2 */
	private String RiskKind2Flag;
	/** 险种 */
	private String RiskCodeFlag;
	/** 首续期 */
	private String PayKindFlag;
	/** 缴费期限 */
	private String PayPrdFlag;
	/** 保单号 */
	private String ContNoFlag;
	/** 分保公司 */
	private String BankCodeFlag;
	/** 代理机构 */
	private String AgenComFlag;
	/** 签报号 */
	private String AmneNoFlag;
	/** 团体对方单位 */
	private String GropNameFlag;
	/** 标准保费 */
	private String StandPremFlag;
	/** 摘要 */
	private String AbstrFlag;
	/** 公司代码 */
	private String ComCodeFlag;
	/** 现金流量 */
	private String AccUseFlag;
	/** 收付款方式 */
	private String PayModeFlag;
	/** 支票号 */
	private String CheckNoFlag;
	/** 回退标志 */
	private String ROLLBACKFLAG;

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LIItemElemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "AccCode";

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
		LIItemElemSchema cloned = (LIItemElemSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	public String getAccCode()
	{
		return AccCode;
	}
	public void setAccCode(String aAccCode)
	{
		AccCode = aAccCode;
	}
	public String getAccName()
	{
		return AccName;
	}
	public void setAccName(String aAccName)
	{
		AccName = aAccName;
	}
	public String getPayWayFlag()
	{
		return PayWayFlag;
	}
	public void setPayWayFlag(String aPayWayFlag)
	{
		PayWayFlag = aPayWayFlag;
	}
	public String getAccSumFlag()
	{
		return AccSumFlag;
	}
	public void setAccSumFlag(String aAccSumFlag)
	{
		AccSumFlag = aAccSumFlag;
	}
	public String getDepartmentFlag()
	{
		return DepartmentFlag;
	}
	public void setDepartmentFlag(String aDepartmentFlag)
	{
		DepartmentFlag = aDepartmentFlag;
	}
	public String getSellWayFlag()
	{
		return SellWayFlag;
	}
	public void setSellWayFlag(String aSellWayFlag)
	{
		SellWayFlag = aSellWayFlag;
	}
	public String getRiskKind1Flag()
	{
		return RiskKind1Flag;
	}
	public void setRiskKind1Flag(String aRiskKind1Flag)
	{
		RiskKind1Flag = aRiskKind1Flag;
	}
	public String getRiskKind2Flag()
	{
		return RiskKind2Flag;
	}
	public void setRiskKind2Flag(String aRiskKind2Flag)
	{
		RiskKind2Flag = aRiskKind2Flag;
	}
	public String getRiskCodeFlag()
	{
		return RiskCodeFlag;
	}
	public void setRiskCodeFlag(String aRiskCodeFlag)
	{
		RiskCodeFlag = aRiskCodeFlag;
	}
	public String getPayKindFlag()
	{
		return PayKindFlag;
	}
	public void setPayKindFlag(String aPayKindFlag)
	{
		PayKindFlag = aPayKindFlag;
	}
	public String getPayPrdFlag()
	{
		return PayPrdFlag;
	}
	public void setPayPrdFlag(String aPayPrdFlag)
	{
		PayPrdFlag = aPayPrdFlag;
	}
	public String getContNoFlag()
	{
		return ContNoFlag;
	}
	public void setContNoFlag(String aContNoFlag)
	{
		ContNoFlag = aContNoFlag;
	}
	public String getBankCodeFlag()
	{
		return BankCodeFlag;
	}
	public void setBankCodeFlag(String aBankCodeFlag)
	{
		BankCodeFlag = aBankCodeFlag;
	}
	public String getAgenComFlag()
	{
		return AgenComFlag;
	}
	public void setAgenComFlag(String aAgenComFlag)
	{
		AgenComFlag = aAgenComFlag;
	}
	public String getAmneNoFlag()
	{
		return AmneNoFlag;
	}
	public void setAmneNoFlag(String aAmneNoFlag)
	{
		AmneNoFlag = aAmneNoFlag;
	}
	public String getGropNameFlag()
	{
		return GropNameFlag;
	}
	public void setGropNameFlag(String aGropNameFlag)
	{
		GropNameFlag = aGropNameFlag;
	}
	public String getStandPremFlag()
	{
		return StandPremFlag;
	}
	public void setStandPremFlag(String aStandPremFlag)
	{
		StandPremFlag = aStandPremFlag;
	}
	public String getAbstrFlag()
	{
		return AbstrFlag;
	}
	public void setAbstrFlag(String aAbstrFlag)
	{
		AbstrFlag = aAbstrFlag;
	}
	public String getComCodeFlag()
	{
		return ComCodeFlag;
	}
	public void setComCodeFlag(String aComCodeFlag)
	{
		ComCodeFlag = aComCodeFlag;
	}
	public String getAccUseFlag()
	{
		return AccUseFlag;
	}
	public void setAccUseFlag(String aAccUseFlag)
	{
		AccUseFlag = aAccUseFlag;
	}
	public String getPayModeFlag()
	{
		return PayModeFlag;
	}
	public void setPayModeFlag(String aPayModeFlag)
	{
		PayModeFlag = aPayModeFlag;
	}
	public String getCheckNoFlag()
	{
		return CheckNoFlag;
	}
	public void setCheckNoFlag(String aCheckNoFlag)
	{
		CheckNoFlag = aCheckNoFlag;
	}
	public String getROLLBACKFLAG()
	{
		return ROLLBACKFLAG;
	}
	public void setROLLBACKFLAG(String aROLLBACKFLAG)
	{
		ROLLBACKFLAG = aROLLBACKFLAG;
	}

	/**
	* 使用另外一个 LIItemElemSchema 对象给 Schema 赋值
	* @param: aLIItemElemSchema LIItemElemSchema
	**/
	public void setSchema(LIItemElemSchema aLIItemElemSchema)
	{
		this.AccType = aLIItemElemSchema.getAccType();
		this.AccCode = aLIItemElemSchema.getAccCode();
		this.AccName = aLIItemElemSchema.getAccName();
		this.PayWayFlag = aLIItemElemSchema.getPayWayFlag();
		this.AccSumFlag = aLIItemElemSchema.getAccSumFlag();
		this.DepartmentFlag = aLIItemElemSchema.getDepartmentFlag();
		this.SellWayFlag = aLIItemElemSchema.getSellWayFlag();
		this.RiskKind1Flag = aLIItemElemSchema.getRiskKind1Flag();
		this.RiskKind2Flag = aLIItemElemSchema.getRiskKind2Flag();
		this.RiskCodeFlag = aLIItemElemSchema.getRiskCodeFlag();
		this.PayKindFlag = aLIItemElemSchema.getPayKindFlag();
		this.PayPrdFlag = aLIItemElemSchema.getPayPrdFlag();
		this.ContNoFlag = aLIItemElemSchema.getContNoFlag();
		this.BankCodeFlag = aLIItemElemSchema.getBankCodeFlag();
		this.AgenComFlag = aLIItemElemSchema.getAgenComFlag();
		this.AmneNoFlag = aLIItemElemSchema.getAmneNoFlag();
		this.GropNameFlag = aLIItemElemSchema.getGropNameFlag();
		this.StandPremFlag = aLIItemElemSchema.getStandPremFlag();
		this.AbstrFlag = aLIItemElemSchema.getAbstrFlag();
		this.ComCodeFlag = aLIItemElemSchema.getComCodeFlag();
		this.AccUseFlag = aLIItemElemSchema.getAccUseFlag();
		this.PayModeFlag = aLIItemElemSchema.getPayModeFlag();
		this.CheckNoFlag = aLIItemElemSchema.getCheckNoFlag();
		this.ROLLBACKFLAG = aLIItemElemSchema.getROLLBACKFLAG();
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
			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("AccCode") == null )
				this.AccCode = null;
			else
				this.AccCode = rs.getString("AccCode").trim();

			if( rs.getString("AccName") == null )
				this.AccName = null;
			else
				this.AccName = rs.getString("AccName").trim();

			if( rs.getString("PayWayFlag") == null )
				this.PayWayFlag = null;
			else
				this.PayWayFlag = rs.getString("PayWayFlag").trim();

			if( rs.getString("AccSumFlag") == null )
				this.AccSumFlag = null;
			else
				this.AccSumFlag = rs.getString("AccSumFlag").trim();

			if( rs.getString("DepartmentFlag") == null )
				this.DepartmentFlag = null;
			else
				this.DepartmentFlag = rs.getString("DepartmentFlag").trim();

			if( rs.getString("SellWayFlag") == null )
				this.SellWayFlag = null;
			else
				this.SellWayFlag = rs.getString("SellWayFlag").trim();

			if( rs.getString("RiskKind1Flag") == null )
				this.RiskKind1Flag = null;
			else
				this.RiskKind1Flag = rs.getString("RiskKind1Flag").trim();

			if( rs.getString("RiskKind2Flag") == null )
				this.RiskKind2Flag = null;
			else
				this.RiskKind2Flag = rs.getString("RiskKind2Flag").trim();

			if( rs.getString("RiskCodeFlag") == null )
				this.RiskCodeFlag = null;
			else
				this.RiskCodeFlag = rs.getString("RiskCodeFlag").trim();

			if( rs.getString("PayKindFlag") == null )
				this.PayKindFlag = null;
			else
				this.PayKindFlag = rs.getString("PayKindFlag").trim();

			if( rs.getString("PayPrdFlag") == null )
				this.PayPrdFlag = null;
			else
				this.PayPrdFlag = rs.getString("PayPrdFlag").trim();

			if( rs.getString("ContNoFlag") == null )
				this.ContNoFlag = null;
			else
				this.ContNoFlag = rs.getString("ContNoFlag").trim();

			if( rs.getString("BankCodeFlag") == null )
				this.BankCodeFlag = null;
			else
				this.BankCodeFlag = rs.getString("BankCodeFlag").trim();

			if( rs.getString("AgenComFlag") == null )
				this.AgenComFlag = null;
			else
				this.AgenComFlag = rs.getString("AgenComFlag").trim();

			if( rs.getString("AmneNoFlag") == null )
				this.AmneNoFlag = null;
			else
				this.AmneNoFlag = rs.getString("AmneNoFlag").trim();

			if( rs.getString("GropNameFlag") == null )
				this.GropNameFlag = null;
			else
				this.GropNameFlag = rs.getString("GropNameFlag").trim();

			if( rs.getString("StandPremFlag") == null )
				this.StandPremFlag = null;
			else
				this.StandPremFlag = rs.getString("StandPremFlag").trim();

			if( rs.getString("AbstrFlag") == null )
				this.AbstrFlag = null;
			else
				this.AbstrFlag = rs.getString("AbstrFlag").trim();

			if( rs.getString("ComCodeFlag") == null )
				this.ComCodeFlag = null;
			else
				this.ComCodeFlag = rs.getString("ComCodeFlag").trim();

			if( rs.getString("AccUseFlag") == null )
				this.AccUseFlag = null;
			else
				this.AccUseFlag = rs.getString("AccUseFlag").trim();

			if( rs.getString("PayModeFlag") == null )
				this.PayModeFlag = null;
			else
				this.PayModeFlag = rs.getString("PayModeFlag").trim();

			if( rs.getString("CheckNoFlag") == null )
				this.CheckNoFlag = null;
			else
				this.CheckNoFlag = rs.getString("CheckNoFlag").trim();

			if( rs.getString("ROLLBACKFLAG") == null )
				this.ROLLBACKFLAG = null;
			else
				this.ROLLBACKFLAG = rs.getString("ROLLBACKFLAG").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LIItemElem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIItemElemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LIItemElemSchema getSchema()
	{
		LIItemElemSchema aLIItemElemSchema = new LIItemElemSchema();
		aLIItemElemSchema.setSchema(this);
		return aLIItemElemSchema;
	}

	public LIItemElemDB getDB()
	{
		LIItemElemDB aDBOper = new LIItemElemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIItemElem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccSumFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DepartmentFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SellWayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskKind1Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskKind2Flag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCodeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayKindFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPrdFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNoFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCodeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgenComFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AmneNoFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GropNameFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandPremFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AbstrFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCodeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccUseFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayModeFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CheckNoFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ROLLBACKFLAG));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLIItemElem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PayWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AccSumFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DepartmentFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SellWayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskKind1Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RiskKind2Flag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RiskCodeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			PayKindFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PayPrdFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ContNoFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			BankCodeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			AgenComFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			AmneNoFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			GropNameFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AbstrFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ComCodeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			AccUseFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PayModeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			CheckNoFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ROLLBACKFLAG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LIItemElemSchema";
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
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccCode));
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccName));
		}
		if (FCode.equalsIgnoreCase("PayWayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayWayFlag));
		}
		if (FCode.equalsIgnoreCase("AccSumFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccSumFlag));
		}
		if (FCode.equalsIgnoreCase("DepartmentFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DepartmentFlag));
		}
		if (FCode.equalsIgnoreCase("SellWayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellWayFlag));
		}
		if (FCode.equalsIgnoreCase("RiskKind1Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskKind1Flag));
		}
		if (FCode.equalsIgnoreCase("RiskKind2Flag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskKind2Flag));
		}
		if (FCode.equalsIgnoreCase("RiskCodeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCodeFlag));
		}
		if (FCode.equalsIgnoreCase("PayKindFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayKindFlag));
		}
		if (FCode.equalsIgnoreCase("PayPrdFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPrdFlag));
		}
		if (FCode.equalsIgnoreCase("ContNoFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNoFlag));
		}
		if (FCode.equalsIgnoreCase("BankCodeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCodeFlag));
		}
		if (FCode.equalsIgnoreCase("AgenComFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgenComFlag));
		}
		if (FCode.equalsIgnoreCase("AmneNoFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AmneNoFlag));
		}
		if (FCode.equalsIgnoreCase("GropNameFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GropNameFlag));
		}
		if (FCode.equalsIgnoreCase("StandPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPremFlag));
		}
		if (FCode.equalsIgnoreCase("AbstrFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AbstrFlag));
		}
		if (FCode.equalsIgnoreCase("ComCodeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCodeFlag));
		}
		if (FCode.equalsIgnoreCase("AccUseFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccUseFlag));
		}
		if (FCode.equalsIgnoreCase("PayModeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayModeFlag));
		}
		if (FCode.equalsIgnoreCase("CheckNoFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CheckNoFlag));
		}
		if (FCode.equalsIgnoreCase("ROLLBACKFLAG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ROLLBACKFLAG));
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
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AccCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PayWayFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AccSumFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DepartmentFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SellWayFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskKind1Flag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RiskKind2Flag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RiskCodeFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(PayKindFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PayPrdFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ContNoFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(BankCodeFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AgenComFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(AmneNoFlag);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(GropNameFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandPremFlag);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AbstrFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ComCodeFlag);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(AccUseFlag);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PayModeFlag);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(CheckNoFlag);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ROLLBACKFLAG);
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

		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("AccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccCode = FValue.trim();
			}
			else
				AccCode = null;
		}
		if (FCode.equalsIgnoreCase("AccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccName = FValue.trim();
			}
			else
				AccName = null;
		}
		if (FCode.equalsIgnoreCase("PayWayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayWayFlag = FValue.trim();
			}
			else
				PayWayFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccSumFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccSumFlag = FValue.trim();
			}
			else
				AccSumFlag = null;
		}
		if (FCode.equalsIgnoreCase("DepartmentFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DepartmentFlag = FValue.trim();
			}
			else
				DepartmentFlag = null;
		}
		if (FCode.equalsIgnoreCase("SellWayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellWayFlag = FValue.trim();
			}
			else
				SellWayFlag = null;
		}
		if (FCode.equalsIgnoreCase("RiskKind1Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskKind1Flag = FValue.trim();
			}
			else
				RiskKind1Flag = null;
		}
		if (FCode.equalsIgnoreCase("RiskKind2Flag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskKind2Flag = FValue.trim();
			}
			else
				RiskKind2Flag = null;
		}
		if (FCode.equalsIgnoreCase("RiskCodeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCodeFlag = FValue.trim();
			}
			else
				RiskCodeFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayKindFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayKindFlag = FValue.trim();
			}
			else
				PayKindFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayPrdFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPrdFlag = FValue.trim();
			}
			else
				PayPrdFlag = null;
		}
		if (FCode.equalsIgnoreCase("ContNoFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNoFlag = FValue.trim();
			}
			else
				ContNoFlag = null;
		}
		if (FCode.equalsIgnoreCase("BankCodeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCodeFlag = FValue.trim();
			}
			else
				BankCodeFlag = null;
		}
		if (FCode.equalsIgnoreCase("AgenComFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgenComFlag = FValue.trim();
			}
			else
				AgenComFlag = null;
		}
		if (FCode.equalsIgnoreCase("AmneNoFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AmneNoFlag = FValue.trim();
			}
			else
				AmneNoFlag = null;
		}
		if (FCode.equalsIgnoreCase("GropNameFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GropNameFlag = FValue.trim();
			}
			else
				GropNameFlag = null;
		}
		if (FCode.equalsIgnoreCase("StandPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandPremFlag = FValue.trim();
			}
			else
				StandPremFlag = null;
		}
		if (FCode.equalsIgnoreCase("AbstrFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AbstrFlag = FValue.trim();
			}
			else
				AbstrFlag = null;
		}
		if (FCode.equalsIgnoreCase("ComCodeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCodeFlag = FValue.trim();
			}
			else
				ComCodeFlag = null;
		}
		if (FCode.equalsIgnoreCase("AccUseFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccUseFlag = FValue.trim();
			}
			else
				AccUseFlag = null;
		}
		if (FCode.equalsIgnoreCase("PayModeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayModeFlag = FValue.trim();
			}
			else
				PayModeFlag = null;
		}
		if (FCode.equalsIgnoreCase("CheckNoFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CheckNoFlag = FValue.trim();
			}
			else
				CheckNoFlag = null;
		}
		if (FCode.equalsIgnoreCase("ROLLBACKFLAG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ROLLBACKFLAG = FValue.trim();
			}
			else
				ROLLBACKFLAG = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LIItemElemSchema other = (LIItemElemSchema)otherObject;
		return
			AccType.equals(other.getAccType())
			&& AccCode.equals(other.getAccCode())
			&& AccName.equals(other.getAccName())
			&& PayWayFlag.equals(other.getPayWayFlag())
			&& AccSumFlag.equals(other.getAccSumFlag())
			&& DepartmentFlag.equals(other.getDepartmentFlag())
			&& SellWayFlag.equals(other.getSellWayFlag())
			&& RiskKind1Flag.equals(other.getRiskKind1Flag())
			&& RiskKind2Flag.equals(other.getRiskKind2Flag())
			&& RiskCodeFlag.equals(other.getRiskCodeFlag())
			&& PayKindFlag.equals(other.getPayKindFlag())
			&& PayPrdFlag.equals(other.getPayPrdFlag())
			&& ContNoFlag.equals(other.getContNoFlag())
			&& BankCodeFlag.equals(other.getBankCodeFlag())
			&& AgenComFlag.equals(other.getAgenComFlag())
			&& AmneNoFlag.equals(other.getAmneNoFlag())
			&& GropNameFlag.equals(other.getGropNameFlag())
			&& StandPremFlag.equals(other.getStandPremFlag())
			&& AbstrFlag.equals(other.getAbstrFlag())
			&& ComCodeFlag.equals(other.getComCodeFlag())
			&& AccUseFlag.equals(other.getAccUseFlag())
			&& PayModeFlag.equals(other.getPayModeFlag())
			&& CheckNoFlag.equals(other.getCheckNoFlag())
			&& ROLLBACKFLAG.equals(other.getROLLBACKFLAG());
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
		if( strFieldName.equals("AccType") ) {
			return 0;
		}
		if( strFieldName.equals("AccCode") ) {
			return 1;
		}
		if( strFieldName.equals("AccName") ) {
			return 2;
		}
		if( strFieldName.equals("PayWayFlag") ) {
			return 3;
		}
		if( strFieldName.equals("AccSumFlag") ) {
			return 4;
		}
		if( strFieldName.equals("DepartmentFlag") ) {
			return 5;
		}
		if( strFieldName.equals("SellWayFlag") ) {
			return 6;
		}
		if( strFieldName.equals("RiskKind1Flag") ) {
			return 7;
		}
		if( strFieldName.equals("RiskKind2Flag") ) {
			return 8;
		}
		if( strFieldName.equals("RiskCodeFlag") ) {
			return 9;
		}
		if( strFieldName.equals("PayKindFlag") ) {
			return 10;
		}
		if( strFieldName.equals("PayPrdFlag") ) {
			return 11;
		}
		if( strFieldName.equals("ContNoFlag") ) {
			return 12;
		}
		if( strFieldName.equals("BankCodeFlag") ) {
			return 13;
		}
		if( strFieldName.equals("AgenComFlag") ) {
			return 14;
		}
		if( strFieldName.equals("AmneNoFlag") ) {
			return 15;
		}
		if( strFieldName.equals("GropNameFlag") ) {
			return 16;
		}
		if( strFieldName.equals("StandPremFlag") ) {
			return 17;
		}
		if( strFieldName.equals("AbstrFlag") ) {
			return 18;
		}
		if( strFieldName.equals("ComCodeFlag") ) {
			return 19;
		}
		if( strFieldName.equals("AccUseFlag") ) {
			return 20;
		}
		if( strFieldName.equals("PayModeFlag") ) {
			return 21;
		}
		if( strFieldName.equals("CheckNoFlag") ) {
			return 22;
		}
		if( strFieldName.equals("ROLLBACKFLAG") ) {
			return 23;
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
				strFieldName = "AccType";
				break;
			case 1:
				strFieldName = "AccCode";
				break;
			case 2:
				strFieldName = "AccName";
				break;
			case 3:
				strFieldName = "PayWayFlag";
				break;
			case 4:
				strFieldName = "AccSumFlag";
				break;
			case 5:
				strFieldName = "DepartmentFlag";
				break;
			case 6:
				strFieldName = "SellWayFlag";
				break;
			case 7:
				strFieldName = "RiskKind1Flag";
				break;
			case 8:
				strFieldName = "RiskKind2Flag";
				break;
			case 9:
				strFieldName = "RiskCodeFlag";
				break;
			case 10:
				strFieldName = "PayKindFlag";
				break;
			case 11:
				strFieldName = "PayPrdFlag";
				break;
			case 12:
				strFieldName = "ContNoFlag";
				break;
			case 13:
				strFieldName = "BankCodeFlag";
				break;
			case 14:
				strFieldName = "AgenComFlag";
				break;
			case 15:
				strFieldName = "AmneNoFlag";
				break;
			case 16:
				strFieldName = "GropNameFlag";
				break;
			case 17:
				strFieldName = "StandPremFlag";
				break;
			case 18:
				strFieldName = "AbstrFlag";
				break;
			case 19:
				strFieldName = "ComCodeFlag";
				break;
			case 20:
				strFieldName = "AccUseFlag";
				break;
			case 21:
				strFieldName = "PayModeFlag";
				break;
			case 22:
				strFieldName = "CheckNoFlag";
				break;
			case 23:
				strFieldName = "ROLLBACKFLAG";
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
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccSumFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DepartmentFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SellWayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskKind1Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskKind2Flag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCodeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayKindFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPrdFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNoFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCodeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgenComFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AmneNoFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GropNameFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AbstrFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCodeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccUseFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayModeFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CheckNoFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ROLLBACKFLAG") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
