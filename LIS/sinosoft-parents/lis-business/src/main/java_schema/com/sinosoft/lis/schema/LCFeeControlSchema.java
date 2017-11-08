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
import com.sinosoft.lis.db.LCFeeControlDB;

/*
 * <p>ClassName: LCFeeControlSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LCFeeControlSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCFeeControlSchema.class);
	// @Field
	/** 集体险种号 */
	private String GrpPolno;
	/** 集体合同号 */
	private String GrpContno;
	/** 交费收据号 */
	private String PayNo;
	/** 给付通知书号码 */
	private String GetNoticeNo;
	/** 次数 */
	private int CountNo;
	/** 险种编码 */
	private String RiskCode;
	/** 手续费比例 */
	private double FeeRate;
	/** 手续费 */
	private double Fee;
	/** 领取人 */
	private String Drawer;
	/** 领取人id */
	private String DrawerID;
	/** 投保单位名称 */
	private String AppntName;
	/** 备注 */
	private String Remark;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;
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
	/** 支付类型 */
	private String PayType;
	/** 支付金额 */
	private double PayMoney;
	/** 备用数字1 */
	private double number1;
	/** 备用字符1 */
	private String string1;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCFeeControlSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "GrpPolno";
		pk[1] = "PayNo";
		pk[2] = "GetNoticeNo";
		pk[3] = "CountNo";

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
		LCFeeControlSchema cloned = (LCFeeControlSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 集体险种号
	*/
	public String getGrpPolno()
	{
		return GrpPolno;
	}
	public void setGrpPolno(String aGrpPolno)
	{
		if(aGrpPolno!=null && aGrpPolno.length()>20)
			throw new IllegalArgumentException("集体险种号GrpPolno值"+aGrpPolno+"的长度"+aGrpPolno.length()+"大于最大值20");
		GrpPolno = aGrpPolno;
	}
	/**
	* 集体合同号
	*/
	public String getGrpContno()
	{
		return GrpContno;
	}
	public void setGrpContno(String aGrpContno)
	{
		if(aGrpContno!=null && aGrpContno.length()>20)
			throw new IllegalArgumentException("集体合同号GrpContno值"+aGrpContno+"的长度"+aGrpContno.length()+"大于最大值20");
		GrpContno = aGrpContno;
	}
	/**
	* 交费收据号 签单前写GRPCONTNO
	*/
	public String getPayNo()
	{
		return PayNo;
	}
	public void setPayNo(String aPayNo)
	{
		if(aPayNo!=null && aPayNo.length()>20)
			throw new IllegalArgumentException("交费收据号PayNo值"+aPayNo+"的长度"+aPayNo.length()+"大于最大值20");
		PayNo = aPayNo;
	}
	/**
	* 一个领取人生成一个号码
	*/
	public String getGetNoticeNo()
	{
		return GetNoticeNo;
	}
	public void setGetNoticeNo(String aGetNoticeNo)
	{
		if(aGetNoticeNo!=null && aGetNoticeNo.length()>20)
			throw new IllegalArgumentException("给付通知书号码GetNoticeNo值"+aGetNoticeNo+"的长度"+aGetNoticeNo.length()+"大于最大值20");
		GetNoticeNo = aGetNoticeNo;
	}
	/**
	* 交手续费次数
	*/
	public int getCountNo()
	{
		return CountNo;
	}
	public void setCountNo(int aCountNo)
	{
		CountNo = aCountNo;
	}
	public void setCountNo(String aCountNo)
	{
		if (aCountNo != null && !aCountNo.equals(""))
		{
			Integer tInteger = new Integer(aCountNo);
			int i = tInteger.intValue();
			CountNo = i;
		}
	}

	/**
	* 险种编码
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>10)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值10");
		RiskCode = aRiskCode;
	}
	/**
	* 手续费比例
	*/
	public double getFeeRate()
	{
		return FeeRate;
	}
	public void setFeeRate(double aFeeRate)
	{
		FeeRate = aFeeRate;
	}
	public void setFeeRate(String aFeeRate)
	{
		if (aFeeRate != null && !aFeeRate.equals(""))
		{
			Double tDouble = new Double(aFeeRate);
			double d = tDouble.doubleValue();
			FeeRate = d;
		}
	}

	/**
	* 手续费
	*/
	public double getFee()
	{
		return Fee;
	}
	public void setFee(double aFee)
	{
		Fee = aFee;
	}
	public void setFee(String aFee)
	{
		if (aFee != null && !aFee.equals(""))
		{
			Double tDouble = new Double(aFee);
			double d = tDouble.doubleValue();
			Fee = d;
		}
	}

	public String getDrawer()
	{
		return Drawer;
	}
	public void setDrawer(String aDrawer)
	{
		if(aDrawer!=null && aDrawer.length()>100)
			throw new IllegalArgumentException("领取人Drawer值"+aDrawer+"的长度"+aDrawer.length()+"大于最大值100");
		Drawer = aDrawer;
	}
	public String getDrawerID()
	{
		return DrawerID;
	}
	public void setDrawerID(String aDrawerID)
	{
		if(aDrawerID!=null && aDrawerID.length()>20)
			throw new IllegalArgumentException("领取人idDrawerID值"+aDrawerID+"的长度"+aDrawerID.length()+"大于最大值20");
		DrawerID = aDrawerID;
	}
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		if(aAppntName!=null && aAppntName.length()>100)
			throw new IllegalArgumentException("投保单位名称AppntName值"+aAppntName+"的长度"+aAppntName.length()+"大于最大值100");
		AppntName = aAppntName;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>500)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值500");
		Remark = aRemark;
	}
	/**
	* 当等于1时代表已经生成应付，2时代表已经生成实付
	*/
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		if(aStandbyFlag1!=null && aStandbyFlag1.length()>10)
			throw new IllegalArgumentException("备用字段1StandbyFlag1值"+aStandbyFlag1+"的长度"+aStandbyFlag1.length()+"大于最大值10");
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		if(aStandbyFlag2!=null && aStandbyFlag2.length()>10)
			throw new IllegalArgumentException("备用字段2StandbyFlag2值"+aStandbyFlag2+"的长度"+aStandbyFlag2.length()+"大于最大值10");
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getPayType()
	{
		return PayType;
	}
	public void setPayType(String aPayType)
	{
		if(aPayType!=null && aPayType.length()>1)
			throw new IllegalArgumentException("支付类型PayType值"+aPayType+"的长度"+aPayType.length()+"大于最大值1");
		PayType = aPayType;
	}
	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
	}

	public double getnumber1()
	{
		return number1;
	}
	public void setnumber1(double anumber1)
	{
		number1 = anumber1;
	}
	public void setnumber1(String anumber1)
	{
		if (anumber1 != null && !anumber1.equals(""))
		{
			Double tDouble = new Double(anumber1);
			double d = tDouble.doubleValue();
			number1 = d;
		}
	}

	public String getstring1()
	{
		return string1;
	}
	public void setstring1(String astring1)
	{
		if(astring1!=null && astring1.length()>20)
			throw new IllegalArgumentException("备用字符1string1值"+astring1+"的长度"+astring1.length()+"大于最大值20");
		string1 = astring1;
	}

	/**
	* 使用另外一个 LCFeeControlSchema 对象给 Schema 赋值
	* @param: aLCFeeControlSchema LCFeeControlSchema
	**/
	public void setSchema(LCFeeControlSchema aLCFeeControlSchema)
	{
		this.GrpPolno = aLCFeeControlSchema.getGrpPolno();
		this.GrpContno = aLCFeeControlSchema.getGrpContno();
		this.PayNo = aLCFeeControlSchema.getPayNo();
		this.GetNoticeNo = aLCFeeControlSchema.getGetNoticeNo();
		this.CountNo = aLCFeeControlSchema.getCountNo();
		this.RiskCode = aLCFeeControlSchema.getRiskCode();
		this.FeeRate = aLCFeeControlSchema.getFeeRate();
		this.Fee = aLCFeeControlSchema.getFee();
		this.Drawer = aLCFeeControlSchema.getDrawer();
		this.DrawerID = aLCFeeControlSchema.getDrawerID();
		this.AppntName = aLCFeeControlSchema.getAppntName();
		this.Remark = aLCFeeControlSchema.getRemark();
		this.StandbyFlag1 = aLCFeeControlSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCFeeControlSchema.getStandbyFlag2();
		this.Operator = aLCFeeControlSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCFeeControlSchema.getMakeDate());
		this.MakeTime = aLCFeeControlSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCFeeControlSchema.getModifyDate());
		this.ModifyTime = aLCFeeControlSchema.getModifyTime();
		this.PayType = aLCFeeControlSchema.getPayType();
		this.PayMoney = aLCFeeControlSchema.getPayMoney();
		this.number1 = aLCFeeControlSchema.getnumber1();
		this.string1 = aLCFeeControlSchema.getstring1();
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
			if( rs.getString("GrpPolno") == null )
				this.GrpPolno = null;
			else
				this.GrpPolno = rs.getString("GrpPolno").trim();

			if( rs.getString("GrpContno") == null )
				this.GrpContno = null;
			else
				this.GrpContno = rs.getString("GrpContno").trim();

			if( rs.getString("PayNo") == null )
				this.PayNo = null;
			else
				this.PayNo = rs.getString("PayNo").trim();

			if( rs.getString("GetNoticeNo") == null )
				this.GetNoticeNo = null;
			else
				this.GetNoticeNo = rs.getString("GetNoticeNo").trim();

			this.CountNo = rs.getInt("CountNo");
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.FeeRate = rs.getDouble("FeeRate");
			this.Fee = rs.getDouble("Fee");
			if( rs.getString("Drawer") == null )
				this.Drawer = null;
			else
				this.Drawer = rs.getString("Drawer").trim();

			if( rs.getString("DrawerID") == null )
				this.DrawerID = null;
			else
				this.DrawerID = rs.getString("DrawerID").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

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

			if( rs.getString("PayType") == null )
				this.PayType = null;
			else
				this.PayType = rs.getString("PayType").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			this.number1 = rs.getDouble("number1");
			if( rs.getString("string1") == null )
				this.string1 = null;
			else
				this.string1 = rs.getString("string1").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCFeeControl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCFeeControlSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCFeeControlSchema getSchema()
	{
		LCFeeControlSchema aLCFeeControlSchema = new LCFeeControlSchema();
		aLCFeeControlSchema.setSchema(this);
		return aLCFeeControlSchema;
	}

	public LCFeeControlDB getDB()
	{
		LCFeeControlDB aDBOper = new LCFeeControlDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCFeeControl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContno)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetNoticeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CountNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Fee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Drawer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrawerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(number1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(string1));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCFeeControl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContno = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PayNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GetNoticeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CountNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Fee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			Drawer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DrawerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			number1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			string1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCFeeControlSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolno));
		}
		if (FCode.equalsIgnoreCase("GrpContno"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContno));
		}
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayNo));
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetNoticeNo));
		}
		if (FCode.equalsIgnoreCase("CountNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CountNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeRate));
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fee));
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Drawer));
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrawerID));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
		if (FCode.equalsIgnoreCase("PayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayType));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("number1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(number1));
		}
		if (FCode.equalsIgnoreCase("string1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(string1));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolno);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContno);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PayNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GetNoticeNo);
				break;
			case 4:
				strFieldValue = String.valueOf(CountNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = String.valueOf(FeeRate);
				break;
			case 7:
				strFieldValue = String.valueOf(Fee);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Drawer);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DrawerID);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PayType);
				break;
			case 20:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 21:
				strFieldValue = String.valueOf(number1);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(string1);
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

		if (FCode.equalsIgnoreCase("GrpPolno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolno = FValue.trim();
			}
			else
				GrpPolno = null;
		}
		if (FCode.equalsIgnoreCase("GrpContno"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContno = FValue.trim();
			}
			else
				GrpContno = null;
		}
		if (FCode.equalsIgnoreCase("PayNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayNo = FValue.trim();
			}
			else
				PayNo = null;
		}
		if (FCode.equalsIgnoreCase("GetNoticeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetNoticeNo = FValue.trim();
			}
			else
				GetNoticeNo = null;
		}
		if (FCode.equalsIgnoreCase("CountNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CountNo = i;
			}
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
		if (FCode.equalsIgnoreCase("FeeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("Fee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Fee = d;
			}
		}
		if (FCode.equalsIgnoreCase("Drawer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Drawer = FValue.trim();
			}
			else
				Drawer = null;
		}
		if (FCode.equalsIgnoreCase("DrawerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrawerID = FValue.trim();
			}
			else
				DrawerID = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
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
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
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
		if (FCode.equalsIgnoreCase("PayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayType = FValue.trim();
			}
			else
				PayType = null;
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("number1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				number1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("string1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				string1 = FValue.trim();
			}
			else
				string1 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCFeeControlSchema other = (LCFeeControlSchema)otherObject;
		return
			GrpPolno.equals(other.getGrpPolno())
			&& GrpContno.equals(other.getGrpContno())
			&& PayNo.equals(other.getPayNo())
			&& GetNoticeNo.equals(other.getGetNoticeNo())
			&& CountNo == other.getCountNo()
			&& RiskCode.equals(other.getRiskCode())
			&& FeeRate == other.getFeeRate()
			&& Fee == other.getFee()
			&& Drawer.equals(other.getDrawer())
			&& DrawerID.equals(other.getDrawerID())
			&& AppntName.equals(other.getAppntName())
			&& Remark.equals(other.getRemark())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PayType.equals(other.getPayType())
			&& PayMoney == other.getPayMoney()
			&& number1 == other.getnumber1()
			&& string1.equals(other.getstring1());
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
		if( strFieldName.equals("GrpPolno") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContno") ) {
			return 1;
		}
		if( strFieldName.equals("PayNo") ) {
			return 2;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return 3;
		}
		if( strFieldName.equals("CountNo") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("FeeRate") ) {
			return 6;
		}
		if( strFieldName.equals("Fee") ) {
			return 7;
		}
		if( strFieldName.equals("Drawer") ) {
			return 8;
		}
		if( strFieldName.equals("DrawerID") ) {
			return 9;
		}
		if( strFieldName.equals("AppntName") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
			return 11;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("PayType") ) {
			return 19;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 20;
		}
		if( strFieldName.equals("number1") ) {
			return 21;
		}
		if( strFieldName.equals("string1") ) {
			return 22;
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
				strFieldName = "GrpPolno";
				break;
			case 1:
				strFieldName = "GrpContno";
				break;
			case 2:
				strFieldName = "PayNo";
				break;
			case 3:
				strFieldName = "GetNoticeNo";
				break;
			case 4:
				strFieldName = "CountNo";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "FeeRate";
				break;
			case 7:
				strFieldName = "Fee";
				break;
			case 8:
				strFieldName = "Drawer";
				break;
			case 9:
				strFieldName = "DrawerID";
				break;
			case 10:
				strFieldName = "AppntName";
				break;
			case 11:
				strFieldName = "Remark";
				break;
			case 12:
				strFieldName = "StandbyFlag1";
				break;
			case 13:
				strFieldName = "StandbyFlag2";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "PayType";
				break;
			case 20:
				strFieldName = "PayMoney";
				break;
			case 21:
				strFieldName = "number1";
				break;
			case 22:
				strFieldName = "string1";
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
		if( strFieldName.equals("GrpPolno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContno") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetNoticeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CountNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Fee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Drawer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrawerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
		if( strFieldName.equals("PayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("number1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("string1") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
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
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
