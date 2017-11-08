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
import com.sinosoft.lis.db.LLClaimDutyKindDB;

/*
 * <p>ClassName: LLClaimDutyKindSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLClaimDutyKindSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimDutyKindSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 账单金额 */
	private double TabFeeMoney;
	/** 理算金额 */
	private double ClaimMoney;
	/** 核算赔付金额 */
	private double StandPay;
	/** 社保给付 */
	private double SocPay;
	/** 第三方给付 */
	private double OthPay;
	/** 核赔赔付金额 */
	private double RealPay;
	/** 备注 */
	private String Remark;
	/** 拒赔金额 */
	private double DeclinePay;
	/** 自费金额 */
	private double SelfAmnt;
	/** 币别 */
	private String Currency;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimDutyKindSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ClmNo";
		pk[1] = "GetDutyKind";

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
		LLClaimDutyKindSchema cloned = (LLClaimDutyKindSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	/**
	* 100  意外医疗<p>
	* 101  意外伤残<p>
	* 102  意外死亡<p>
	* 103  意外高残<p>
	* 104  意外大病<p>
	* 105  意外特种疾病<p>
	* 106  意外失业失能<p>
	* 109  意外豁免<p>
	* 200  疾病医疗<p>
	* 201  疾病伤残<p>
	* 202  疾病死亡<p>
	* 203  疾病高残<p>
	* 204  疾病大病<p>
	* 205  疾病特种疾病<p>
	* 206  疾病失业失能<p>
	* 209  疾病豁免
	*/
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>6)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值6");
		GetDutyKind = aGetDutyKind;
	}
	/**
	* 医疗帐单录入原始金额
	*/
	public double getTabFeeMoney()
	{
		return TabFeeMoney;
	}
	public void setTabFeeMoney(double aTabFeeMoney)
	{
		TabFeeMoney = aTabFeeMoney;
	}
	public void setTabFeeMoney(String aTabFeeMoney)
	{
		if (aTabFeeMoney != null && !aTabFeeMoney.equals(""))
		{
			Double tDouble = new Double(aTabFeeMoney);
			double d = tDouble.doubleValue();
			TabFeeMoney = d;
		}
	}

	/**
	* 为帐单金额,和核算赔付金额的最小值<p>
	* 加上<p>
	* 医疗类特殊附加险(类似225)
	*/
	public double getClaimMoney()
	{
		return ClaimMoney;
	}
	public void setClaimMoney(double aClaimMoney)
	{
		ClaimMoney = aClaimMoney;
	}
	public void setClaimMoney(String aClaimMoney)
	{
		if (aClaimMoney != null && !aClaimMoney.equals(""))
		{
			Double tDouble = new Double(aClaimMoney);
			double d = tDouble.doubleValue();
			ClaimMoney = d;
		}
	}

	/**
	* 计算出来的金额,各个保项的汇总金额
	*/
	public double getStandPay()
	{
		return StandPay;
	}
	public void setStandPay(double aStandPay)
	{
		StandPay = aStandPay;
	}
	public void setStandPay(String aStandPay)
	{
		if (aStandPay != null && !aStandPay.equals(""))
		{
			Double tDouble = new Double(aStandPay);
			double d = tDouble.doubleValue();
			StandPay = d;
		}
	}

	public double getSocPay()
	{
		return SocPay;
	}
	public void setSocPay(double aSocPay)
	{
		SocPay = aSocPay;
	}
	public void setSocPay(String aSocPay)
	{
		if (aSocPay != null && !aSocPay.equals(""))
		{
			Double tDouble = new Double(aSocPay);
			double d = tDouble.doubleValue();
			SocPay = d;
		}
	}

	public double getOthPay()
	{
		return OthPay;
	}
	public void setOthPay(double aOthPay)
	{
		OthPay = aOthPay;
	}
	public void setOthPay(String aOthPay)
	{
		if (aOthPay != null && !aOthPay.equals(""))
		{
			Double tDouble = new Double(aOthPay);
			double d = tDouble.doubleValue();
			OthPay = d;
		}
	}

	/**
	* 最后确定赔付的金额<p>
	* 理算金额 - 社保给付 - 第三方给付
	*/
	public double getRealPay()
	{
		return RealPay;
	}
	public void setRealPay(double aRealPay)
	{
		RealPay = aRealPay;
	}
	public void setRealPay(String aRealPay)
	{
		if (aRealPay != null && !aRealPay.equals(""))
		{
			Double tDouble = new Double(aRealPay);
			double d = tDouble.doubleValue();
			RealPay = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>1000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值1000");
		Remark = aRemark;
	}
	public double getDeclinePay()
	{
		return DeclinePay;
	}
	public void setDeclinePay(double aDeclinePay)
	{
		DeclinePay = aDeclinePay;
	}
	public void setDeclinePay(String aDeclinePay)
	{
		if (aDeclinePay != null && !aDeclinePay.equals(""))
		{
			Double tDouble = new Double(aDeclinePay);
			double d = tDouble.doubleValue();
			DeclinePay = d;
		}
	}

	public double getSelfAmnt()
	{
		return SelfAmnt;
	}
	public void setSelfAmnt(double aSelfAmnt)
	{
		SelfAmnt = aSelfAmnt;
	}
	public void setSelfAmnt(String aSelfAmnt)
	{
		if (aSelfAmnt != null && !aSelfAmnt.equals(""))
		{
			Double tDouble = new Double(aSelfAmnt);
			double d = tDouble.doubleValue();
			SelfAmnt = d;
		}
	}

	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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

	/**
	* 使用另外一个 LLClaimDutyKindSchema 对象给 Schema 赋值
	* @param: aLLClaimDutyKindSchema LLClaimDutyKindSchema
	**/
	public void setSchema(LLClaimDutyKindSchema aLLClaimDutyKindSchema)
	{
		this.ClmNo = aLLClaimDutyKindSchema.getClmNo();
		this.GetDutyKind = aLLClaimDutyKindSchema.getGetDutyKind();
		this.TabFeeMoney = aLLClaimDutyKindSchema.getTabFeeMoney();
		this.ClaimMoney = aLLClaimDutyKindSchema.getClaimMoney();
		this.StandPay = aLLClaimDutyKindSchema.getStandPay();
		this.SocPay = aLLClaimDutyKindSchema.getSocPay();
		this.OthPay = aLLClaimDutyKindSchema.getOthPay();
		this.RealPay = aLLClaimDutyKindSchema.getRealPay();
		this.Remark = aLLClaimDutyKindSchema.getRemark();
		this.DeclinePay = aLLClaimDutyKindSchema.getDeclinePay();
		this.SelfAmnt = aLLClaimDutyKindSchema.getSelfAmnt();
		this.Currency = aLLClaimDutyKindSchema.getCurrency();
		this.ManageCom = aLLClaimDutyKindSchema.getManageCom();
		this.ComCode = aLLClaimDutyKindSchema.getComCode();
		this.MakeOperator = aLLClaimDutyKindSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLClaimDutyKindSchema.getMakeDate());
		this.MakeTime = aLLClaimDutyKindSchema.getMakeTime();
		this.ModifyOperator = aLLClaimDutyKindSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLClaimDutyKindSchema.getModifyDate());
		this.ModifyTime = aLLClaimDutyKindSchema.getModifyTime();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			this.TabFeeMoney = rs.getDouble("TabFeeMoney");
			this.ClaimMoney = rs.getDouble("ClaimMoney");
			this.StandPay = rs.getDouble("StandPay");
			this.SocPay = rs.getDouble("SocPay");
			this.OthPay = rs.getDouble("OthPay");
			this.RealPay = rs.getDouble("RealPay");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			this.DeclinePay = rs.getDouble("DeclinePay");
			this.SelfAmnt = rs.getDouble("SelfAmnt");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLClaimDutyKind表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDutyKindSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimDutyKindSchema getSchema()
	{
		LLClaimDutyKindSchema aLLClaimDutyKindSchema = new LLClaimDutyKindSchema();
		aLLClaimDutyKindSchema.setSchema(this);
		return aLLClaimDutyKindSchema;
	}

	public LLClaimDutyKindDB getDB()
	{
		LLClaimDutyKindDB aDBOper = new LLClaimDutyKindDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimDutyKind描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TabFeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ClaimMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SocPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DeclinePay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SelfAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimDutyKind>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TabFeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			SocPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			OthPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DeclinePay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			SelfAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimDutyKindSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TabFeeMoney));
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimMoney));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SocPay));
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthPay));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DeclinePay));
		}
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfAmnt));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 2:
				strFieldValue = String.valueOf(TabFeeMoney);
				break;
			case 3:
				strFieldValue = String.valueOf(ClaimMoney);
				break;
			case 4:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 5:
				strFieldValue = String.valueOf(SocPay);
				break;
			case 6:
				strFieldValue = String.valueOf(OthPay);
				break;
			case 7:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 9:
				strFieldValue = String.valueOf(DeclinePay);
				break;
			case 10:
				strFieldValue = String.valueOf(SelfAmnt);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("TabFeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TabFeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("SocPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SocPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equalsIgnoreCase("DeclinePay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DeclinePay = d;
			}
		}
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SelfAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LLClaimDutyKindSchema other = (LLClaimDutyKindSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& TabFeeMoney == other.getTabFeeMoney()
			&& ClaimMoney == other.getClaimMoney()
			&& StandPay == other.getStandPay()
			&& SocPay == other.getSocPay()
			&& OthPay == other.getOthPay()
			&& RealPay == other.getRealPay()
			&& Remark.equals(other.getRemark())
			&& DeclinePay == other.getDeclinePay()
			&& SelfAmnt == other.getSelfAmnt()
			&& Currency.equals(other.getCurrency())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 1;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return 2;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return 3;
		}
		if( strFieldName.equals("StandPay") ) {
			return 4;
		}
		if( strFieldName.equals("SocPay") ) {
			return 5;
		}
		if( strFieldName.equals("OthPay") ) {
			return 6;
		}
		if( strFieldName.equals("RealPay") ) {
			return 7;
		}
		if( strFieldName.equals("Remark") ) {
			return 8;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return 9;
		}
		if( strFieldName.equals("SelfAmnt") ) {
			return 10;
		}
		if( strFieldName.equals("Currency") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("ComCode") ) {
			return 13;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "GetDutyKind";
				break;
			case 2:
				strFieldName = "TabFeeMoney";
				break;
			case 3:
				strFieldName = "ClaimMoney";
				break;
			case 4:
				strFieldName = "StandPay";
				break;
			case 5:
				strFieldName = "SocPay";
				break;
			case 6:
				strFieldName = "OthPay";
				break;
			case 7:
				strFieldName = "RealPay";
				break;
			case 8:
				strFieldName = "Remark";
				break;
			case 9:
				strFieldName = "DeclinePay";
				break;
			case 10:
				strFieldName = "SelfAmnt";
				break;
			case 11:
				strFieldName = "Currency";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "ComCode";
				break;
			case 14:
				strFieldName = "MakeOperator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TabFeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SocPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DeclinePay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("SelfAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
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
