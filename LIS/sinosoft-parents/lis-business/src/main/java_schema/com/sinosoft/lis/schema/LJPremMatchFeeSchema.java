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
import com.sinosoft.lis.db.LJPremMatchFeeDB;

/*
 * <p>ClassName: LJPremMatchFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPremMatchFeeSchema.class);
	// @Field
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 保费来源 */
	private String DataSource;
	/** 来源号码 */
	private String SourceNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 业务类型 */
	private String BussType;
	/** 业务号码 */
	private String BussNo;
	/** 客户开户行编码 */
	private String CustBankCode;
	/** 客户账号 */
	private String CustBankAccNo;
	/** 客户账户名 */
	private String CustAccName;
	/** 投保单位编码 */
	private String AppntNo;
	/** 投保单位名称 */
	private String GrpName;
	/** 收费银行编码 */
	private String InBankCode;
	/** 收费账号 */
	private String InBankAccNo;
	/** 到账日期 */
	private Date EnterAccDate;
	/** 金额 */
	private double Money;
	/** 本次溢缴金额 */
	private double CurOutPayMoney;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPremMatchFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "MatchSerialNo";
		pk[1] = "DataSource";
		pk[2] = "SourceNo";

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
		LJPremMatchFeeSchema cloned = (LJPremMatchFeeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMatchSerialNo()
	{
		return MatchSerialNo;
	}
	public void setMatchSerialNo(String aMatchSerialNo)
	{
		if(aMatchSerialNo!=null && aMatchSerialNo.length()>20)
			throw new IllegalArgumentException("保费匹配流水号MatchSerialNo值"+aMatchSerialNo+"的长度"+aMatchSerialNo.length()+"大于最大值20");
		MatchSerialNo = aMatchSerialNo;
	}
	/**
	* 00-财务收费，01-保单溢缴
	*/
	public String getDataSource()
	{
		return DataSource;
	}
	public void setDataSource(String aDataSource)
	{
		if(aDataSource!=null && aDataSource.length()>2)
			throw new IllegalArgumentException("保费来源DataSource值"+aDataSource+"的长度"+aDataSource.length()+"大于最大值2");
		DataSource = aDataSource;
	}
	/**
	* 根据数据来源确定：收付费-TempFeeNo，溢交保费-GrpContNo
	*/
	public String getSourceNo()
	{
		return SourceNo;
	}
	public void setSourceNo(String aSourceNo)
	{
		if(aSourceNo!=null && aSourceNo.length()>20)
			throw new IllegalArgumentException("来源号码SourceNo值"+aSourceNo+"的长度"+aSourceNo.length()+"大于最大值20");
		SourceNo = aSourceNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		if(aBussType!=null && aBussType.length()>2)
			throw new IllegalArgumentException("业务类型BussType值"+aBussType+"的长度"+aBussType.length()+"大于最大值2");
		BussType = aBussType;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号码BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getCustBankCode()
	{
		return CustBankCode;
	}
	public void setCustBankCode(String aCustBankCode)
	{
		if(aCustBankCode!=null && aCustBankCode.length()>30)
			throw new IllegalArgumentException("客户开户行编码CustBankCode值"+aCustBankCode+"的长度"+aCustBankCode.length()+"大于最大值30");
		CustBankCode = aCustBankCode;
	}
	public String getCustBankAccNo()
	{
		return CustBankAccNo;
	}
	public void setCustBankAccNo(String aCustBankAccNo)
	{
		if(aCustBankAccNo!=null && aCustBankAccNo.length()>30)
			throw new IllegalArgumentException("客户账号CustBankAccNo值"+aCustBankAccNo+"的长度"+aCustBankAccNo.length()+"大于最大值30");
		CustBankAccNo = aCustBankAccNo;
	}
	public String getCustAccName()
	{
		return CustAccName;
	}
	public void setCustAccName(String aCustAccName)
	{
		if(aCustAccName!=null && aCustAccName.length()>200)
			throw new IllegalArgumentException("客户账户名CustAccName值"+aCustAccName+"的长度"+aCustAccName.length()+"大于最大值200");
		CustAccName = aCustAccName;
	}
	public String getAppntNo()
	{
		return AppntNo;
	}
	public void setAppntNo(String aAppntNo)
	{
		if(aAppntNo!=null && aAppntNo.length()>20)
			throw new IllegalArgumentException("投保单位编码AppntNo值"+aAppntNo+"的长度"+aAppntNo.length()+"大于最大值20");
		AppntNo = aAppntNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>200)
			throw new IllegalArgumentException("投保单位名称GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值200");
		GrpName = aGrpName;
	}
	public String getInBankCode()
	{
		return InBankCode;
	}
	public void setInBankCode(String aInBankCode)
	{
		if(aInBankCode!=null && aInBankCode.length()>30)
			throw new IllegalArgumentException("收费银行编码InBankCode值"+aInBankCode+"的长度"+aInBankCode.length()+"大于最大值30");
		InBankCode = aInBankCode;
	}
	public String getInBankAccNo()
	{
		return InBankAccNo;
	}
	public void setInBankAccNo(String aInBankAccNo)
	{
		if(aInBankAccNo!=null && aInBankAccNo.length()>30)
			throw new IllegalArgumentException("收费账号InBankAccNo值"+aInBankAccNo+"的长度"+aInBankAccNo.length()+"大于最大值30");
		InBankAccNo = aInBankAccNo;
	}
	public String getEnterAccDate()
	{
		if( EnterAccDate != null )
			return fDate.getString(EnterAccDate);
		else
			return null;
	}
	public void setEnterAccDate(Date aEnterAccDate)
	{
		EnterAccDate = aEnterAccDate;
	}
	public void setEnterAccDate(String aEnterAccDate)
	{
		if (aEnterAccDate != null && !aEnterAccDate.equals("") )
		{
			EnterAccDate = fDate.getDate( aEnterAccDate );
		}
		else
			EnterAccDate = null;
	}

	public double getMoney()
	{
		return Money;
	}
	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}
	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = tDouble.doubleValue();
			Money = d;
		}
	}

	public double getCurOutPayMoney()
	{
		return CurOutPayMoney;
	}
	public void setCurOutPayMoney(double aCurOutPayMoney)
	{
		CurOutPayMoney = aCurOutPayMoney;
	}
	public void setCurOutPayMoney(String aCurOutPayMoney)
	{
		if (aCurOutPayMoney != null && !aCurOutPayMoney.equals(""))
		{
			Double tDouble = new Double(aCurOutPayMoney);
			double d = tDouble.doubleValue();
			CurOutPayMoney = d;
		}
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
	* 使用另外一个 LJPremMatchFeeSchema 对象给 Schema 赋值
	* @param: aLJPremMatchFeeSchema LJPremMatchFeeSchema
	**/
	public void setSchema(LJPremMatchFeeSchema aLJPremMatchFeeSchema)
	{
		this.MatchSerialNo = aLJPremMatchFeeSchema.getMatchSerialNo();
		this.DataSource = aLJPremMatchFeeSchema.getDataSource();
		this.SourceNo = aLJPremMatchFeeSchema.getSourceNo();
		this.GrpContNo = aLJPremMatchFeeSchema.getGrpContNo();
		this.BussType = aLJPremMatchFeeSchema.getBussType();
		this.BussNo = aLJPremMatchFeeSchema.getBussNo();
		this.CustBankCode = aLJPremMatchFeeSchema.getCustBankCode();
		this.CustBankAccNo = aLJPremMatchFeeSchema.getCustBankAccNo();
		this.CustAccName = aLJPremMatchFeeSchema.getCustAccName();
		this.AppntNo = aLJPremMatchFeeSchema.getAppntNo();
		this.GrpName = aLJPremMatchFeeSchema.getGrpName();
		this.InBankCode = aLJPremMatchFeeSchema.getInBankCode();
		this.InBankAccNo = aLJPremMatchFeeSchema.getInBankAccNo();
		this.EnterAccDate = fDate.getDate( aLJPremMatchFeeSchema.getEnterAccDate());
		this.Money = aLJPremMatchFeeSchema.getMoney();
		this.CurOutPayMoney = aLJPremMatchFeeSchema.getCurOutPayMoney();
		this.ManageCom = aLJPremMatchFeeSchema.getManageCom();
		this.ComCode = aLJPremMatchFeeSchema.getComCode();
		this.MakeOperator = aLJPremMatchFeeSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJPremMatchFeeSchema.getMakeDate());
		this.MakeTime = aLJPremMatchFeeSchema.getMakeTime();
		this.ModifyOperator = aLJPremMatchFeeSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJPremMatchFeeSchema.getModifyDate());
		this.ModifyTime = aLJPremMatchFeeSchema.getModifyTime();
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
			if( rs.getString("MatchSerialNo") == null )
				this.MatchSerialNo = null;
			else
				this.MatchSerialNo = rs.getString("MatchSerialNo").trim();

			if( rs.getString("DataSource") == null )
				this.DataSource = null;
			else
				this.DataSource = rs.getString("DataSource").trim();

			if( rs.getString("SourceNo") == null )
				this.SourceNo = null;
			else
				this.SourceNo = rs.getString("SourceNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("CustBankCode") == null )
				this.CustBankCode = null;
			else
				this.CustBankCode = rs.getString("CustBankCode").trim();

			if( rs.getString("CustBankAccNo") == null )
				this.CustBankAccNo = null;
			else
				this.CustBankAccNo = rs.getString("CustBankAccNo").trim();

			if( rs.getString("CustAccName") == null )
				this.CustAccName = null;
			else
				this.CustAccName = rs.getString("CustAccName").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("InBankCode") == null )
				this.InBankCode = null;
			else
				this.InBankCode = rs.getString("InBankCode").trim();

			if( rs.getString("InBankAccNo") == null )
				this.InBankAccNo = null;
			else
				this.InBankAccNo = rs.getString("InBankAccNo").trim();

			this.EnterAccDate = rs.getDate("EnterAccDate");
			this.Money = rs.getDouble("Money");
			this.CurOutPayMoney = rs.getDouble("CurOutPayMoney");
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
			logger.debug("数据库中的LJPremMatchFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPremMatchFeeSchema getSchema()
	{
		LJPremMatchFeeSchema aLJPremMatchFeeSchema = new LJPremMatchFeeSchema();
		aLJPremMatchFeeSchema.setSchema(this);
		return aLJPremMatchFeeSchema;
	}

	public LJPremMatchFeeDB getDB()
	{
		LJPremMatchFeeDB aDBOper = new LJPremMatchFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DataSource)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustAccName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EnterAccDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CurOutPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DataSource = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SourceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CustBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CustAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			InBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			InBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			EnterAccDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			CurOutPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchFeeSchema";
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
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchSerialNo));
		}
		if (FCode.equalsIgnoreCase("DataSource"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataSource));
		}
		if (FCode.equalsIgnoreCase("SourceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankCode));
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustBankAccNo));
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustAccName));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankCode));
		}
		if (FCode.equalsIgnoreCase("InBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InBankAccNo));
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("CurOutPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurOutPayMoney));
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
				strFieldValue = StrTool.GBKToUnicode(MatchSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DataSource);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SourceNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustBankCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CustBankAccNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CustAccName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(InBankCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(InBankAccNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEnterAccDate()));
				break;
			case 14:
				strFieldValue = String.valueOf(Money);
				break;
			case 15:
				strFieldValue = String.valueOf(CurOutPayMoney);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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

		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("DataSource"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataSource = FValue.trim();
			}
			else
				DataSource = null;
		}
		if (FCode.equalsIgnoreCase("SourceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceNo = FValue.trim();
			}
			else
				SourceNo = null;
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
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("CustBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankCode = FValue.trim();
			}
			else
				CustBankCode = null;
		}
		if (FCode.equalsIgnoreCase("CustBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustBankAccNo = FValue.trim();
			}
			else
				CustBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("CustAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustAccName = FValue.trim();
			}
			else
				CustAccName = null;
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntNo = FValue.trim();
			}
			else
				AppntNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equalsIgnoreCase("InBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankCode = FValue.trim();
			}
			else
				InBankCode = null;
		}
		if (FCode.equalsIgnoreCase("InBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InBankAccNo = FValue.trim();
			}
			else
				InBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("EnterAccDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EnterAccDate = fDate.getDate( FValue );
			}
			else
				EnterAccDate = null;
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Money = d;
			}
		}
		if (FCode.equalsIgnoreCase("CurOutPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CurOutPayMoney = d;
			}
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
		LJPremMatchFeeSchema other = (LJPremMatchFeeSchema)otherObject;
		return
			MatchSerialNo.equals(other.getMatchSerialNo())
			&& DataSource.equals(other.getDataSource())
			&& SourceNo.equals(other.getSourceNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& BussType.equals(other.getBussType())
			&& BussNo.equals(other.getBussNo())
			&& CustBankCode.equals(other.getCustBankCode())
			&& CustBankAccNo.equals(other.getCustBankAccNo())
			&& CustAccName.equals(other.getCustAccName())
			&& AppntNo.equals(other.getAppntNo())
			&& GrpName.equals(other.getGrpName())
			&& InBankCode.equals(other.getInBankCode())
			&& InBankAccNo.equals(other.getInBankAccNo())
			&& fDate.getString(EnterAccDate).equals(other.getEnterAccDate())
			&& Money == other.getMoney()
			&& CurOutPayMoney == other.getCurOutPayMoney()
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("DataSource") ) {
			return 1;
		}
		if( strFieldName.equals("SourceNo") ) {
			return 2;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("BussType") ) {
			return 4;
		}
		if( strFieldName.equals("BussNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return 6;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return 7;
		}
		if( strFieldName.equals("CustAccName") ) {
			return 8;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 9;
		}
		if( strFieldName.equals("GrpName") ) {
			return 10;
		}
		if( strFieldName.equals("InBankCode") ) {
			return 11;
		}
		if( strFieldName.equals("InBankAccNo") ) {
			return 12;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return 13;
		}
		if( strFieldName.equals("Money") ) {
			return 14;
		}
		if( strFieldName.equals("CurOutPayMoney") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("ComCode") ) {
			return 17;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "MatchSerialNo";
				break;
			case 1:
				strFieldName = "DataSource";
				break;
			case 2:
				strFieldName = "SourceNo";
				break;
			case 3:
				strFieldName = "GrpContNo";
				break;
			case 4:
				strFieldName = "BussType";
				break;
			case 5:
				strFieldName = "BussNo";
				break;
			case 6:
				strFieldName = "CustBankCode";
				break;
			case 7:
				strFieldName = "CustBankAccNo";
				break;
			case 8:
				strFieldName = "CustAccName";
				break;
			case 9:
				strFieldName = "AppntNo";
				break;
			case 10:
				strFieldName = "GrpName";
				break;
			case 11:
				strFieldName = "InBankCode";
				break;
			case 12:
				strFieldName = "InBankAccNo";
				break;
			case 13:
				strFieldName = "EnterAccDate";
				break;
			case 14:
				strFieldName = "Money";
				break;
			case 15:
				strFieldName = "CurOutPayMoney";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "ComCode";
				break;
			case 18:
				strFieldName = "MakeOperator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyOperator";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataSource") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustAccName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EnterAccDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Money") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CurOutPayMoney") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
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
