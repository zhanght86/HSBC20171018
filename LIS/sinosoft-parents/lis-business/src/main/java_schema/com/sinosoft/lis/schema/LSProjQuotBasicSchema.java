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
import com.sinosoft.lis.db.LSProjQuotBasicDB;

/*
 * <p>ClassName: LSProjQuotBasicSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSProjQuotBasicSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSProjQuotBasicSchema.class);
	// @Field
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 项目名称 */
	private String ProjName;
	/** 目标客户 */
	private String TargetCust;
	/** 被保险人数量 */
	private int NumPeople;
	/** 业务规模 */
	private double PrePrem;
	/** 预估赔付率 */
	private double PreLossRatio;
	/** 合作方 */
	private String Partner;
	/** 有效止期 */
	private Date ExpiryDate;
	/** 产品类型 */
	private String ProdType;
	/** 是否为统括单 */
	private String BlanketFlag;
	/** 是否为弹性询价 */
	private String ElasticFlag;
	/** 项目说明 */
	private String ProjDesc;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
	/** 批次状态 */
	private String QuotState;
	/** 归档原因 */
	private String FileReason;
	/** 归档描述 */
	private String FileDesc;
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

	public static final int FIELDNUM = 27;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSProjQuotBasicSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "QuotNo";
		pk[1] = "QuotBatNo";

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
		LSProjQuotBasicSchema cloned = (LSProjQuotBasicSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getQuotNo()
	{
		return QuotNo;
	}
	public void setQuotNo(String aQuotNo)
	{
		if(aQuotNo!=null && aQuotNo.length()>20)
			throw new IllegalArgumentException("询价号QuotNo值"+aQuotNo+"的长度"+aQuotNo.length()+"大于最大值20");
		QuotNo = aQuotNo;
	}
	public int getQuotBatNo()
	{
		return QuotBatNo;
	}
	public void setQuotBatNo(int aQuotBatNo)
	{
		QuotBatNo = aQuotBatNo;
	}
	public void setQuotBatNo(String aQuotBatNo)
	{
		if (aQuotBatNo != null && !aQuotBatNo.equals(""))
		{
			Integer tInteger = new Integer(aQuotBatNo);
			int i = tInteger.intValue();
			QuotBatNo = i;
		}
	}

	public String getProjName()
	{
		return ProjName;
	}
	public void setProjName(String aProjName)
	{
		if(aProjName!=null && aProjName.length()>120)
			throw new IllegalArgumentException("项目名称ProjName值"+aProjName+"的长度"+aProjName.length()+"大于最大值120");
		ProjName = aProjName;
	}
	public String getTargetCust()
	{
		return TargetCust;
	}
	public void setTargetCust(String aTargetCust)
	{
		if(aTargetCust!=null && aTargetCust.length()>20)
			throw new IllegalArgumentException("目标客户TargetCust值"+aTargetCust+"的长度"+aTargetCust.length()+"大于最大值20");
		TargetCust = aTargetCust;
	}
	public int getNumPeople()
	{
		return NumPeople;
	}
	public void setNumPeople(int aNumPeople)
	{
		NumPeople = aNumPeople;
	}
	public void setNumPeople(String aNumPeople)
	{
		if (aNumPeople != null && !aNumPeople.equals(""))
		{
			Integer tInteger = new Integer(aNumPeople);
			int i = tInteger.intValue();
			NumPeople = i;
		}
	}

	public double getPrePrem()
	{
		return PrePrem;
	}
	public void setPrePrem(double aPrePrem)
	{
		PrePrem = aPrePrem;
	}
	public void setPrePrem(String aPrePrem)
	{
		if (aPrePrem != null && !aPrePrem.equals(""))
		{
			Double tDouble = new Double(aPrePrem);
			double d = tDouble.doubleValue();
			PrePrem = d;
		}
	}

	public double getPreLossRatio()
	{
		return PreLossRatio;
	}
	public void setPreLossRatio(double aPreLossRatio)
	{
		PreLossRatio = aPreLossRatio;
	}
	public void setPreLossRatio(String aPreLossRatio)
	{
		if (aPreLossRatio != null && !aPreLossRatio.equals(""))
		{
			Double tDouble = new Double(aPreLossRatio);
			double d = tDouble.doubleValue();
			PreLossRatio = d;
		}
	}

	public String getPartner()
	{
		return Partner;
	}
	public void setPartner(String aPartner)
	{
		if(aPartner!=null && aPartner.length()>200)
			throw new IllegalArgumentException("合作方Partner值"+aPartner+"的长度"+aPartner.length()+"大于最大值200");
		Partner = aPartner;
	}
	public String getExpiryDate()
	{
		if( ExpiryDate != null )
			return fDate.getString(ExpiryDate);
		else
			return null;
	}
	public void setExpiryDate(Date aExpiryDate)
	{
		ExpiryDate = aExpiryDate;
	}
	public void setExpiryDate(String aExpiryDate)
	{
		if (aExpiryDate != null && !aExpiryDate.equals("") )
		{
			ExpiryDate = fDate.getDate( aExpiryDate );
		}
		else
			ExpiryDate = null;
	}

	public String getProdType()
	{
		return ProdType;
	}
	public void setProdType(String aProdType)
	{
		if(aProdType!=null && aProdType.length()>2)
			throw new IllegalArgumentException("产品类型ProdType值"+aProdType+"的长度"+aProdType.length()+"大于最大值2");
		ProdType = aProdType;
	}
	public String getBlanketFlag()
	{
		return BlanketFlag;
	}
	public void setBlanketFlag(String aBlanketFlag)
	{
		if(aBlanketFlag!=null && aBlanketFlag.length()>1)
			throw new IllegalArgumentException("是否为统括单BlanketFlag值"+aBlanketFlag+"的长度"+aBlanketFlag.length()+"大于最大值1");
		BlanketFlag = aBlanketFlag;
	}
	public String getElasticFlag()
	{
		return ElasticFlag;
	}
	public void setElasticFlag(String aElasticFlag)
	{
		if(aElasticFlag!=null && aElasticFlag.length()>2)
			throw new IllegalArgumentException("是否为弹性询价ElasticFlag值"+aElasticFlag+"的长度"+aElasticFlag.length()+"大于最大值2");
		ElasticFlag = aElasticFlag;
	}
	public String getProjDesc()
	{
		return ProjDesc;
	}
	public void setProjDesc(String aProjDesc)
	{
		if(aProjDesc!=null && aProjDesc.length()>3000)
			throw new IllegalArgumentException("项目说明ProjDesc值"+aProjDesc+"的长度"+aProjDesc.length()+"大于最大值3000");
		ProjDesc = aProjDesc;
	}
	public String getSegment1()
	{
		return Segment1;
	}
	public void setSegment1(String aSegment1)
	{
		if(aSegment1!=null && aSegment1.length()>20)
			throw new IllegalArgumentException("备用字段1Segment1值"+aSegment1+"的长度"+aSegment1.length()+"大于最大值20");
		Segment1 = aSegment1;
	}
	public String getSegment2()
	{
		return Segment2;
	}
	public void setSegment2(String aSegment2)
	{
		if(aSegment2!=null && aSegment2.length()>20)
			throw new IllegalArgumentException("备用字段2Segment2值"+aSegment2+"的长度"+aSegment2.length()+"大于最大值20");
		Segment2 = aSegment2;
	}
	public String getSegment3()
	{
		return Segment3;
	}
	public void setSegment3(String aSegment3)
	{
		if(aSegment3!=null && aSegment3.length()>200)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值200");
		Segment3 = aSegment3;
	}
	/**
	* 00-询价在途，01-审批通过，02-审批未通过，03-归档，04-询价撤回
	*/
	public String getQuotState()
	{
		return QuotState;
	}
	public void setQuotState(String aQuotState)
	{
		if(aQuotState!=null && aQuotState.length()>2)
			throw new IllegalArgumentException("批次状态QuotState值"+aQuotState+"的长度"+aQuotState.length()+"大于最大值2");
		QuotState = aQuotState;
	}
	public String getFileReason()
	{
		return FileReason;
	}
	public void setFileReason(String aFileReason)
	{
		if(aFileReason!=null && aFileReason.length()>2)
			throw new IllegalArgumentException("归档原因FileReason值"+aFileReason+"的长度"+aFileReason.length()+"大于最大值2");
		FileReason = aFileReason;
	}
	public String getFileDesc()
	{
		return FileDesc;
	}
	public void setFileDesc(String aFileDesc)
	{
		if(aFileDesc!=null && aFileDesc.length()>3000)
			throw new IllegalArgumentException("归档描述FileDesc值"+aFileDesc+"的长度"+aFileDesc.length()+"大于最大值3000");
		FileDesc = aFileDesc;
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
	* 使用另外一个 LSProjQuotBasicSchema 对象给 Schema 赋值
	* @param: aLSProjQuotBasicSchema LSProjQuotBasicSchema
	**/
	public void setSchema(LSProjQuotBasicSchema aLSProjQuotBasicSchema)
	{
		this.QuotNo = aLSProjQuotBasicSchema.getQuotNo();
		this.QuotBatNo = aLSProjQuotBasicSchema.getQuotBatNo();
		this.ProjName = aLSProjQuotBasicSchema.getProjName();
		this.TargetCust = aLSProjQuotBasicSchema.getTargetCust();
		this.NumPeople = aLSProjQuotBasicSchema.getNumPeople();
		this.PrePrem = aLSProjQuotBasicSchema.getPrePrem();
		this.PreLossRatio = aLSProjQuotBasicSchema.getPreLossRatio();
		this.Partner = aLSProjQuotBasicSchema.getPartner();
		this.ExpiryDate = fDate.getDate( aLSProjQuotBasicSchema.getExpiryDate());
		this.ProdType = aLSProjQuotBasicSchema.getProdType();
		this.BlanketFlag = aLSProjQuotBasicSchema.getBlanketFlag();
		this.ElasticFlag = aLSProjQuotBasicSchema.getElasticFlag();
		this.ProjDesc = aLSProjQuotBasicSchema.getProjDesc();
		this.Segment1 = aLSProjQuotBasicSchema.getSegment1();
		this.Segment2 = aLSProjQuotBasicSchema.getSegment2();
		this.Segment3 = aLSProjQuotBasicSchema.getSegment3();
		this.QuotState = aLSProjQuotBasicSchema.getQuotState();
		this.FileReason = aLSProjQuotBasicSchema.getFileReason();
		this.FileDesc = aLSProjQuotBasicSchema.getFileDesc();
		this.ManageCom = aLSProjQuotBasicSchema.getManageCom();
		this.ComCode = aLSProjQuotBasicSchema.getComCode();
		this.MakeOperator = aLSProjQuotBasicSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSProjQuotBasicSchema.getMakeDate());
		this.MakeTime = aLSProjQuotBasicSchema.getMakeTime();
		this.ModifyOperator = aLSProjQuotBasicSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSProjQuotBasicSchema.getModifyDate());
		this.ModifyTime = aLSProjQuotBasicSchema.getModifyTime();
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
			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("ProjName") == null )
				this.ProjName = null;
			else
				this.ProjName = rs.getString("ProjName").trim();

			if( rs.getString("TargetCust") == null )
				this.TargetCust = null;
			else
				this.TargetCust = rs.getString("TargetCust").trim();

			this.NumPeople = rs.getInt("NumPeople");
			this.PrePrem = rs.getDouble("PrePrem");
			this.PreLossRatio = rs.getDouble("PreLossRatio");
			if( rs.getString("Partner") == null )
				this.Partner = null;
			else
				this.Partner = rs.getString("Partner").trim();

			this.ExpiryDate = rs.getDate("ExpiryDate");
			if( rs.getString("ProdType") == null )
				this.ProdType = null;
			else
				this.ProdType = rs.getString("ProdType").trim();

			if( rs.getString("BlanketFlag") == null )
				this.BlanketFlag = null;
			else
				this.BlanketFlag = rs.getString("BlanketFlag").trim();

			if( rs.getString("ElasticFlag") == null )
				this.ElasticFlag = null;
			else
				this.ElasticFlag = rs.getString("ElasticFlag").trim();

			if( rs.getString("ProjDesc") == null )
				this.ProjDesc = null;
			else
				this.ProjDesc = rs.getString("ProjDesc").trim();

			if( rs.getString("Segment1") == null )
				this.Segment1 = null;
			else
				this.Segment1 = rs.getString("Segment1").trim();

			if( rs.getString("Segment2") == null )
				this.Segment2 = null;
			else
				this.Segment2 = rs.getString("Segment2").trim();

			if( rs.getString("Segment3") == null )
				this.Segment3 = null;
			else
				this.Segment3 = rs.getString("Segment3").trim();

			if( rs.getString("QuotState") == null )
				this.QuotState = null;
			else
				this.QuotState = rs.getString("QuotState").trim();

			if( rs.getString("FileReason") == null )
				this.FileReason = null;
			else
				this.FileReason = rs.getString("FileReason").trim();

			if( rs.getString("FileDesc") == null )
				this.FileDesc = null;
			else
				this.FileDesc = rs.getString("FileDesc").trim();

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
			logger.debug("数据库中的LSProjQuotBasic表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSProjQuotBasicSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSProjQuotBasicSchema getSchema()
	{
		LSProjQuotBasicSchema aLSProjQuotBasicSchema = new LSProjQuotBasicSchema();
		aLSProjQuotBasicSchema.setSchema(this);
		return aLSProjQuotBasicSchema;
	}

	public LSProjQuotBasicDB getDB()
	{
		LSProjQuotBasicDB aDBOper = new LSProjQuotBasicDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSProjQuotBasic描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProjName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TargetCust)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NumPeople));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PrePrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PreLossRatio));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Partner)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExpiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProdType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BlanketFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ElasticFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProjDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuotState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FileDesc)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSProjQuotBasic>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ProjName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TargetCust = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NumPeople= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			PrePrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			PreLossRatio = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Partner = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ExpiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ProdType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BlanketFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ElasticFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ProjDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			QuotState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			FileReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			FileDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSProjQuotBasicSchema";
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
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("ProjName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProjName));
		}
		if (FCode.equalsIgnoreCase("TargetCust"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TargetCust));
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NumPeople));
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrePrem));
		}
		if (FCode.equalsIgnoreCase("PreLossRatio"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreLossRatio));
		}
		if (FCode.equalsIgnoreCase("Partner"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Partner));
		}
		if (FCode.equalsIgnoreCase("ExpiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExpiryDate()));
		}
		if (FCode.equalsIgnoreCase("ProdType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProdType));
		}
		if (FCode.equalsIgnoreCase("BlanketFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BlanketFlag));
		}
		if (FCode.equalsIgnoreCase("ElasticFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ElasticFlag));
		}
		if (FCode.equalsIgnoreCase("ProjDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProjDesc));
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment1));
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment2));
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Segment3));
		}
		if (FCode.equalsIgnoreCase("QuotState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotState));
		}
		if (FCode.equalsIgnoreCase("FileReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileReason));
		}
		if (FCode.equalsIgnoreCase("FileDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FileDesc));
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
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 1:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProjName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TargetCust);
				break;
			case 4:
				strFieldValue = String.valueOf(NumPeople);
				break;
			case 5:
				strFieldValue = String.valueOf(PrePrem);
				break;
			case 6:
				strFieldValue = String.valueOf(PreLossRatio);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Partner);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExpiryDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ProdType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BlanketFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ElasticFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ProjDesc);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(QuotState);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(FileReason);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(FileDesc);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
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

		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotNo = FValue.trim();
			}
			else
				QuotNo = null;
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				QuotBatNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ProjName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProjName = FValue.trim();
			}
			else
				ProjName = null;
		}
		if (FCode.equalsIgnoreCase("TargetCust"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TargetCust = FValue.trim();
			}
			else
				TargetCust = null;
		}
		if (FCode.equalsIgnoreCase("NumPeople"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NumPeople = i;
			}
		}
		if (FCode.equalsIgnoreCase("PrePrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PrePrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("PreLossRatio"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PreLossRatio = d;
			}
		}
		if (FCode.equalsIgnoreCase("Partner"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Partner = FValue.trim();
			}
			else
				Partner = null;
		}
		if (FCode.equalsIgnoreCase("ExpiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExpiryDate = fDate.getDate( FValue );
			}
			else
				ExpiryDate = null;
		}
		if (FCode.equalsIgnoreCase("ProdType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProdType = FValue.trim();
			}
			else
				ProdType = null;
		}
		if (FCode.equalsIgnoreCase("BlanketFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BlanketFlag = FValue.trim();
			}
			else
				BlanketFlag = null;
		}
		if (FCode.equalsIgnoreCase("ElasticFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ElasticFlag = FValue.trim();
			}
			else
				ElasticFlag = null;
		}
		if (FCode.equalsIgnoreCase("ProjDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProjDesc = FValue.trim();
			}
			else
				ProjDesc = null;
		}
		if (FCode.equalsIgnoreCase("Segment1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment1 = FValue.trim();
			}
			else
				Segment1 = null;
		}
		if (FCode.equalsIgnoreCase("Segment2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment2 = FValue.trim();
			}
			else
				Segment2 = null;
		}
		if (FCode.equalsIgnoreCase("Segment3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Segment3 = FValue.trim();
			}
			else
				Segment3 = null;
		}
		if (FCode.equalsIgnoreCase("QuotState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuotState = FValue.trim();
			}
			else
				QuotState = null;
		}
		if (FCode.equalsIgnoreCase("FileReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileReason = FValue.trim();
			}
			else
				FileReason = null;
		}
		if (FCode.equalsIgnoreCase("FileDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FileDesc = FValue.trim();
			}
			else
				FileDesc = null;
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
		LSProjQuotBasicSchema other = (LSProjQuotBasicSchema)otherObject;
		return
			QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& ProjName.equals(other.getProjName())
			&& TargetCust.equals(other.getTargetCust())
			&& NumPeople == other.getNumPeople()
			&& PrePrem == other.getPrePrem()
			&& PreLossRatio == other.getPreLossRatio()
			&& Partner.equals(other.getPartner())
			&& fDate.getString(ExpiryDate).equals(other.getExpiryDate())
			&& ProdType.equals(other.getProdType())
			&& BlanketFlag.equals(other.getBlanketFlag())
			&& ElasticFlag.equals(other.getElasticFlag())
			&& ProjDesc.equals(other.getProjDesc())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
			&& QuotState.equals(other.getQuotState())
			&& FileReason.equals(other.getFileReason())
			&& FileDesc.equals(other.getFileDesc())
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
		if( strFieldName.equals("QuotNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProjName") ) {
			return 2;
		}
		if( strFieldName.equals("TargetCust") ) {
			return 3;
		}
		if( strFieldName.equals("NumPeople") ) {
			return 4;
		}
		if( strFieldName.equals("PrePrem") ) {
			return 5;
		}
		if( strFieldName.equals("PreLossRatio") ) {
			return 6;
		}
		if( strFieldName.equals("Partner") ) {
			return 7;
		}
		if( strFieldName.equals("ExpiryDate") ) {
			return 8;
		}
		if( strFieldName.equals("ProdType") ) {
			return 9;
		}
		if( strFieldName.equals("BlanketFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ElasticFlag") ) {
			return 11;
		}
		if( strFieldName.equals("ProjDesc") ) {
			return 12;
		}
		if( strFieldName.equals("Segment1") ) {
			return 13;
		}
		if( strFieldName.equals("Segment2") ) {
			return 14;
		}
		if( strFieldName.equals("Segment3") ) {
			return 15;
		}
		if( strFieldName.equals("QuotState") ) {
			return 16;
		}
		if( strFieldName.equals("FileReason") ) {
			return 17;
		}
		if( strFieldName.equals("FileDesc") ) {
			return 18;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 19;
		}
		if( strFieldName.equals("ComCode") ) {
			return 20;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
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
				strFieldName = "QuotNo";
				break;
			case 1:
				strFieldName = "QuotBatNo";
				break;
			case 2:
				strFieldName = "ProjName";
				break;
			case 3:
				strFieldName = "TargetCust";
				break;
			case 4:
				strFieldName = "NumPeople";
				break;
			case 5:
				strFieldName = "PrePrem";
				break;
			case 6:
				strFieldName = "PreLossRatio";
				break;
			case 7:
				strFieldName = "Partner";
				break;
			case 8:
				strFieldName = "ExpiryDate";
				break;
			case 9:
				strFieldName = "ProdType";
				break;
			case 10:
				strFieldName = "BlanketFlag";
				break;
			case 11:
				strFieldName = "ElasticFlag";
				break;
			case 12:
				strFieldName = "ProjDesc";
				break;
			case 13:
				strFieldName = "Segment1";
				break;
			case 14:
				strFieldName = "Segment2";
				break;
			case 15:
				strFieldName = "Segment3";
				break;
			case 16:
				strFieldName = "QuotState";
				break;
			case 17:
				strFieldName = "FileReason";
				break;
			case 18:
				strFieldName = "FileDesc";
				break;
			case 19:
				strFieldName = "ManageCom";
				break;
			case 20:
				strFieldName = "ComCode";
				break;
			case 21:
				strFieldName = "MakeOperator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyOperator";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
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
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ProjName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TargetCust") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NumPeople") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PrePrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("PreLossRatio") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Partner") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ProdType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BlanketFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ElasticFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProjDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Segment3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FileDesc") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
