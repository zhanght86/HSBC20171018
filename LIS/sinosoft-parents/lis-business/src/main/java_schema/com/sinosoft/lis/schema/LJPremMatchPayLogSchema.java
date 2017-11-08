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
import com.sinosoft.lis.db.LJPremMatchPayLogDB;

/*
 * <p>ClassName: LJPremMatchPayLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchPayLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPremMatchPayLogSchema.class);
	// @Field
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 匹配节点 */
	private String MatchNode;
	/** 业务类型 */
	private String BussType;
	/** 主业务号码 */
	private String MainBussNo;
	/** 子业务号码 */
	private String SubBussNo;
	/** 投保单位编码 */
	private String AppntNo;
	/** 投保单位名称 */
	private String GrpName;
	/** 共保公司编码 */
	private String Insurancecom;
	/** 共保公司名称 */
	private String InsurancecomName;
	/** 业务日期 */
	private Date BussDate;
	/** 应收金额 */
	private double DuePayMoney;
	/** 本次溢交金额 */
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

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPremMatchPayLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "MatchSerialNo";
		pk[1] = "MatchNode";
		pk[2] = "BussType";
		pk[3] = "MainBussNo";
		pk[4] = "SubBussNo";

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
		LJPremMatchPayLogSchema cloned = (LJPremMatchPayLogSchema)super.clone();
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
	public String getMatchNode()
	{
		return MatchNode;
	}
	public void setMatchNode(String aMatchNode)
	{
		if(aMatchNode!=null && aMatchNode.length()>2)
			throw new IllegalArgumentException("匹配节点MatchNode值"+aMatchNode+"的长度"+aMatchNode.length()+"大于最大值2");
		MatchNode = aMatchNode;
	}
	/**
	* 01-新契约，02-续期，03-保全，04-定期结算，05-理赔，06-溢交退费
	*/
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
	public String getMainBussNo()
	{
		return MainBussNo;
	}
	public void setMainBussNo(String aMainBussNo)
	{
		if(aMainBussNo!=null && aMainBussNo.length()>20)
			throw new IllegalArgumentException("主业务号码MainBussNo值"+aMainBussNo+"的长度"+aMainBussNo.length()+"大于最大值20");
		MainBussNo = aMainBussNo;
	}
	public String getSubBussNo()
	{
		return SubBussNo;
	}
	public void setSubBussNo(String aSubBussNo)
	{
		if(aSubBussNo!=null && aSubBussNo.length()>20)
			throw new IllegalArgumentException("子业务号码SubBussNo值"+aSubBussNo+"的长度"+aSubBussNo.length()+"大于最大值20");
		SubBussNo = aSubBussNo;
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
	public String getInsurancecom()
	{
		return Insurancecom;
	}
	public void setInsurancecom(String aInsurancecom)
	{
		if(aInsurancecom!=null && aInsurancecom.length()>30)
			throw new IllegalArgumentException("共保公司编码Insurancecom值"+aInsurancecom+"的长度"+aInsurancecom.length()+"大于最大值30");
		Insurancecom = aInsurancecom;
	}
	public String getInsurancecomName()
	{
		return InsurancecomName;
	}
	public void setInsurancecomName(String aInsurancecomName)
	{
		if(aInsurancecomName!=null && aInsurancecomName.length()>200)
			throw new IllegalArgumentException("共保公司名称InsurancecomName值"+aInsurancecomName+"的长度"+aInsurancecomName.length()+"大于最大值200");
		InsurancecomName = aInsurancecomName;
	}
	public String getBussDate()
	{
		if( BussDate != null )
			return fDate.getString(BussDate);
		else
			return null;
	}
	public void setBussDate(Date aBussDate)
	{
		BussDate = aBussDate;
	}
	public void setBussDate(String aBussDate)
	{
		if (aBussDate != null && !aBussDate.equals("") )
		{
			BussDate = fDate.getDate( aBussDate );
		}
		else
			BussDate = null;
	}

	public double getDuePayMoney()
	{
		return DuePayMoney;
	}
	public void setDuePayMoney(double aDuePayMoney)
	{
		DuePayMoney = aDuePayMoney;
	}
	public void setDuePayMoney(String aDuePayMoney)
	{
		if (aDuePayMoney != null && !aDuePayMoney.equals(""))
		{
			Double tDouble = new Double(aDuePayMoney);
			double d = tDouble.doubleValue();
			DuePayMoney = d;
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
	* 使用另外一个 LJPremMatchPayLogSchema 对象给 Schema 赋值
	* @param: aLJPremMatchPayLogSchema LJPremMatchPayLogSchema
	**/
	public void setSchema(LJPremMatchPayLogSchema aLJPremMatchPayLogSchema)
	{
		this.MatchSerialNo = aLJPremMatchPayLogSchema.getMatchSerialNo();
		this.MatchNode = aLJPremMatchPayLogSchema.getMatchNode();
		this.BussType = aLJPremMatchPayLogSchema.getBussType();
		this.MainBussNo = aLJPremMatchPayLogSchema.getMainBussNo();
		this.SubBussNo = aLJPremMatchPayLogSchema.getSubBussNo();
		this.AppntNo = aLJPremMatchPayLogSchema.getAppntNo();
		this.GrpName = aLJPremMatchPayLogSchema.getGrpName();
		this.Insurancecom = aLJPremMatchPayLogSchema.getInsurancecom();
		this.InsurancecomName = aLJPremMatchPayLogSchema.getInsurancecomName();
		this.BussDate = fDate.getDate( aLJPremMatchPayLogSchema.getBussDate());
		this.DuePayMoney = aLJPremMatchPayLogSchema.getDuePayMoney();
		this.CurOutPayMoney = aLJPremMatchPayLogSchema.getCurOutPayMoney();
		this.ManageCom = aLJPremMatchPayLogSchema.getManageCom();
		this.ComCode = aLJPremMatchPayLogSchema.getComCode();
		this.MakeOperator = aLJPremMatchPayLogSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJPremMatchPayLogSchema.getMakeDate());
		this.MakeTime = aLJPremMatchPayLogSchema.getMakeTime();
		this.ModifyOperator = aLJPremMatchPayLogSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJPremMatchPayLogSchema.getModifyDate());
		this.ModifyTime = aLJPremMatchPayLogSchema.getModifyTime();
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

			if( rs.getString("MatchNode") == null )
				this.MatchNode = null;
			else
				this.MatchNode = rs.getString("MatchNode").trim();

			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("MainBussNo") == null )
				this.MainBussNo = null;
			else
				this.MainBussNo = rs.getString("MainBussNo").trim();

			if( rs.getString("SubBussNo") == null )
				this.SubBussNo = null;
			else
				this.SubBussNo = rs.getString("SubBussNo").trim();

			if( rs.getString("AppntNo") == null )
				this.AppntNo = null;
			else
				this.AppntNo = rs.getString("AppntNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("Insurancecom") == null )
				this.Insurancecom = null;
			else
				this.Insurancecom = rs.getString("Insurancecom").trim();

			if( rs.getString("InsurancecomName") == null )
				this.InsurancecomName = null;
			else
				this.InsurancecomName = rs.getString("InsurancecomName").trim();

			this.BussDate = rs.getDate("BussDate");
			this.DuePayMoney = rs.getDouble("DuePayMoney");
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
			logger.debug("数据库中的LJPremMatchPayLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchPayLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPremMatchPayLogSchema getSchema()
	{
		LJPremMatchPayLogSchema aLJPremMatchPayLogSchema = new LJPremMatchPayLogSchema();
		aLJPremMatchPayLogSchema.setSchema(this);
		return aLJPremMatchPayLogSchema;
	}

	public LJPremMatchPayLogDB getDB()
	{
		LJPremMatchPayLogDB aDBOper = new LJPremMatchPayLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchPayLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MatchNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insurancecom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsurancecomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BussDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DuePayMoney));strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchPayLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MatchNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MainBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SubBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppntNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Insurancecom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InsurancecomName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BussDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			DuePayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			CurOutPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
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
			tError.moduleName = "LJPremMatchPayLogSchema";
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
		if (FCode.equalsIgnoreCase("MatchNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchNode));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("MainBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainBussNo));
		}
		if (FCode.equalsIgnoreCase("SubBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubBussNo));
		}
		if (FCode.equalsIgnoreCase("AppntNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("Insurancecom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insurancecom));
		}
		if (FCode.equalsIgnoreCase("InsurancecomName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsurancecomName));
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
		}
		if (FCode.equalsIgnoreCase("DuePayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DuePayMoney));
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
				strFieldValue = StrTool.GBKToUnicode(MatchNode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MainBussNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SubBussNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppntNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Insurancecom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InsurancecomName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBussDate()));
				break;
			case 10:
				strFieldValue = String.valueOf(DuePayMoney);
				break;
			case 11:
				strFieldValue = String.valueOf(CurOutPayMoney);
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

		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("MatchNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchNode = FValue.trim();
			}
			else
				MatchNode = null;
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
		if (FCode.equalsIgnoreCase("MainBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainBussNo = FValue.trim();
			}
			else
				MainBussNo = null;
		}
		if (FCode.equalsIgnoreCase("SubBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubBussNo = FValue.trim();
			}
			else
				SubBussNo = null;
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
		if (FCode.equalsIgnoreCase("Insurancecom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Insurancecom = FValue.trim();
			}
			else
				Insurancecom = null;
		}
		if (FCode.equalsIgnoreCase("InsurancecomName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsurancecomName = FValue.trim();
			}
			else
				InsurancecomName = null;
		}
		if (FCode.equalsIgnoreCase("BussDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BussDate = fDate.getDate( FValue );
			}
			else
				BussDate = null;
		}
		if (FCode.equalsIgnoreCase("DuePayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DuePayMoney = d;
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
		LJPremMatchPayLogSchema other = (LJPremMatchPayLogSchema)otherObject;
		return
			MatchSerialNo.equals(other.getMatchSerialNo())
			&& MatchNode.equals(other.getMatchNode())
			&& BussType.equals(other.getBussType())
			&& MainBussNo.equals(other.getMainBussNo())
			&& SubBussNo.equals(other.getSubBussNo())
			&& AppntNo.equals(other.getAppntNo())
			&& GrpName.equals(other.getGrpName())
			&& Insurancecom.equals(other.getInsurancecom())
			&& InsurancecomName.equals(other.getInsurancecomName())
			&& fDate.getString(BussDate).equals(other.getBussDate())
			&& DuePayMoney == other.getDuePayMoney()
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
		if( strFieldName.equals("MatchNode") ) {
			return 1;
		}
		if( strFieldName.equals("BussType") ) {
			return 2;
		}
		if( strFieldName.equals("MainBussNo") ) {
			return 3;
		}
		if( strFieldName.equals("SubBussNo") ) {
			return 4;
		}
		if( strFieldName.equals("AppntNo") ) {
			return 5;
		}
		if( strFieldName.equals("GrpName") ) {
			return 6;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return 7;
		}
		if( strFieldName.equals("InsurancecomName") ) {
			return 8;
		}
		if( strFieldName.equals("BussDate") ) {
			return 9;
		}
		if( strFieldName.equals("DuePayMoney") ) {
			return 10;
		}
		if( strFieldName.equals("CurOutPayMoney") ) {
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
				strFieldName = "MatchSerialNo";
				break;
			case 1:
				strFieldName = "MatchNode";
				break;
			case 2:
				strFieldName = "BussType";
				break;
			case 3:
				strFieldName = "MainBussNo";
				break;
			case 4:
				strFieldName = "SubBussNo";
				break;
			case 5:
				strFieldName = "AppntNo";
				break;
			case 6:
				strFieldName = "GrpName";
				break;
			case 7:
				strFieldName = "Insurancecom";
				break;
			case 8:
				strFieldName = "InsurancecomName";
				break;
			case 9:
				strFieldName = "BussDate";
				break;
			case 10:
				strFieldName = "DuePayMoney";
				break;
			case 11:
				strFieldName = "CurOutPayMoney";
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MatchNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Insurancecom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsurancecomName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DuePayMoney") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
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
