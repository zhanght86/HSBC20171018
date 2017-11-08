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
import com.sinosoft.lis.db.LSQuotRequestDB;

/*
 * <p>ClassName: LSQuotRequestSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LSQuotRequestSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LSQuotRequestSchema.class);
	// @Field
	/** 序号 */
	private String SerialNo;
	/** 询价号 */
	private String QuotNo;
	/** 询价批次号 */
	private int QuotBatNo;
	/** 业务需求分类 */
	private String RequestType;
	/** 其他分类描述 */
	private String OtherTypeDesc;
	/** 系统方案编码 */
	private String SysPlanCode;
	/** 方案编码 */
	private String PlanCode;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 业务需求内容 */
	private String RequestDesc;
	/** 核保意见(中支) */
	private String SubUWOpinion;
	/** 核保意见(分公司) */
	private String BranchUWOpinion;
	/** 备用字段1 */
	private String Segment1;
	/** 备用字段2 */
	private String Segment2;
	/** 备用字段3 */
	private String Segment3;
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

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LSQuotRequestSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		LSQuotRequestSchema cloned = (LSQuotRequestSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>10)
			throw new IllegalArgumentException("序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值10");
		SerialNo = aSerialNo;
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

	public String getRequestType()
	{
		return RequestType;
	}
	public void setRequestType(String aRequestType)
	{
		if(aRequestType!=null && aRequestType.length()>2)
			throw new IllegalArgumentException("业务需求分类RequestType值"+aRequestType+"的长度"+aRequestType.length()+"大于最大值2");
		RequestType = aRequestType;
	}
	public String getOtherTypeDesc()
	{
		return OtherTypeDesc;
	}
	public void setOtherTypeDesc(String aOtherTypeDesc)
	{
		if(aOtherTypeDesc!=null && aOtherTypeDesc.length()>30)
			throw new IllegalArgumentException("其他分类描述OtherTypeDesc值"+aOtherTypeDesc+"的长度"+aOtherTypeDesc.length()+"大于最大值30");
		OtherTypeDesc = aOtherTypeDesc;
	}
	public String getSysPlanCode()
	{
		return SysPlanCode;
	}
	public void setSysPlanCode(String aSysPlanCode)
	{
		if(aSysPlanCode!=null && aSysPlanCode.length()>10)
			throw new IllegalArgumentException("系统方案编码SysPlanCode值"+aSysPlanCode+"的长度"+aSysPlanCode.length()+"大于最大值10");
		SysPlanCode = aSysPlanCode;
	}
	/**
	* 一般询价：000(固定)<p>
	* 项目询价：正常方案编码
	*/
	public String getPlanCode()
	{
		return PlanCode;
	}
	public void setPlanCode(String aPlanCode)
	{
		if(aPlanCode!=null && aPlanCode.length()>10)
			throw new IllegalArgumentException("方案编码PlanCode值"+aPlanCode+"的长度"+aPlanCode.length()+"大于最大值10");
		PlanCode = aPlanCode;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>20)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值20");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>20)
			throw new IllegalArgumentException("责任编码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值20");
		DutyCode = aDutyCode;
	}
	public String getRequestDesc()
	{
		return RequestDesc;
	}
	public void setRequestDesc(String aRequestDesc)
	{
		if(aRequestDesc!=null && aRequestDesc.length()>3000)
			throw new IllegalArgumentException("业务需求内容RequestDesc值"+aRequestDesc+"的长度"+aRequestDesc.length()+"大于最大值3000");
		RequestDesc = aRequestDesc;
	}
	public String getSubUWOpinion()
	{
		return SubUWOpinion;
	}
	public void setSubUWOpinion(String aSubUWOpinion)
	{
		if(aSubUWOpinion!=null && aSubUWOpinion.length()>3000)
			throw new IllegalArgumentException("核保意见(中支)SubUWOpinion值"+aSubUWOpinion+"的长度"+aSubUWOpinion.length()+"大于最大值3000");
		SubUWOpinion = aSubUWOpinion;
	}
	public String getBranchUWOpinion()
	{
		return BranchUWOpinion;
	}
	public void setBranchUWOpinion(String aBranchUWOpinion)
	{
		if(aBranchUWOpinion!=null && aBranchUWOpinion.length()>3000)
			throw new IllegalArgumentException("核保意见(分公司)BranchUWOpinion值"+aBranchUWOpinion+"的长度"+aBranchUWOpinion.length()+"大于最大值3000");
		BranchUWOpinion = aBranchUWOpinion;
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
		if(aSegment3!=null && aSegment3.length()>20)
			throw new IllegalArgumentException("备用字段3Segment3值"+aSegment3+"的长度"+aSegment3.length()+"大于最大值20");
		Segment3 = aSegment3;
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
	* 使用另外一个 LSQuotRequestSchema 对象给 Schema 赋值
	* @param: aLSQuotRequestSchema LSQuotRequestSchema
	**/
	public void setSchema(LSQuotRequestSchema aLSQuotRequestSchema)
	{
		this.SerialNo = aLSQuotRequestSchema.getSerialNo();
		this.QuotNo = aLSQuotRequestSchema.getQuotNo();
		this.QuotBatNo = aLSQuotRequestSchema.getQuotBatNo();
		this.RequestType = aLSQuotRequestSchema.getRequestType();
		this.OtherTypeDesc = aLSQuotRequestSchema.getOtherTypeDesc();
		this.SysPlanCode = aLSQuotRequestSchema.getSysPlanCode();
		this.PlanCode = aLSQuotRequestSchema.getPlanCode();
		this.RiskCode = aLSQuotRequestSchema.getRiskCode();
		this.DutyCode = aLSQuotRequestSchema.getDutyCode();
		this.RequestDesc = aLSQuotRequestSchema.getRequestDesc();
		this.SubUWOpinion = aLSQuotRequestSchema.getSubUWOpinion();
		this.BranchUWOpinion = aLSQuotRequestSchema.getBranchUWOpinion();
		this.Segment1 = aLSQuotRequestSchema.getSegment1();
		this.Segment2 = aLSQuotRequestSchema.getSegment2();
		this.Segment3 = aLSQuotRequestSchema.getSegment3();
		this.MakeOperator = aLSQuotRequestSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLSQuotRequestSchema.getMakeDate());
		this.MakeTime = aLSQuotRequestSchema.getMakeTime();
		this.ModifyOperator = aLSQuotRequestSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLSQuotRequestSchema.getModifyDate());
		this.ModifyTime = aLSQuotRequestSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("QuotNo") == null )
				this.QuotNo = null;
			else
				this.QuotNo = rs.getString("QuotNo").trim();

			this.QuotBatNo = rs.getInt("QuotBatNo");
			if( rs.getString("RequestType") == null )
				this.RequestType = null;
			else
				this.RequestType = rs.getString("RequestType").trim();

			if( rs.getString("OtherTypeDesc") == null )
				this.OtherTypeDesc = null;
			else
				this.OtherTypeDesc = rs.getString("OtherTypeDesc").trim();

			if( rs.getString("SysPlanCode") == null )
				this.SysPlanCode = null;
			else
				this.SysPlanCode = rs.getString("SysPlanCode").trim();

			if( rs.getString("PlanCode") == null )
				this.PlanCode = null;
			else
				this.PlanCode = rs.getString("PlanCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("RequestDesc") == null )
				this.RequestDesc = null;
			else
				this.RequestDesc = rs.getString("RequestDesc").trim();

			if( rs.getString("SubUWOpinion") == null )
				this.SubUWOpinion = null;
			else
				this.SubUWOpinion = rs.getString("SubUWOpinion").trim();

			if( rs.getString("BranchUWOpinion") == null )
				this.BranchUWOpinion = null;
			else
				this.BranchUWOpinion = rs.getString("BranchUWOpinion").trim();

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
			logger.debug("数据库中的LSQuotRequest表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotRequestSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LSQuotRequestSchema getSchema()
	{
		LSQuotRequestSchema aLSQuotRequestSchema = new LSQuotRequestSchema();
		aLSQuotRequestSchema.setSchema(this);
		return aLSQuotRequestSchema;
	}

	public LSQuotRequestDB getDB()
	{
		LSQuotRequestDB aDBOper = new LSQuotRequestDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotRequest描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QuotNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(QuotBatNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RequestType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherTypeDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SysPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RequestDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubUWOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchUWOpinion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Segment3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLSQuotRequest>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			QuotNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			QuotBatNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			RequestType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherTypeDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SysPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RequestDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SubUWOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BranchUWOpinion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Segment1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Segment2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Segment3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LSQuotRequestSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("QuotNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotNo));
		}
		if (FCode.equalsIgnoreCase("QuotBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuotBatNo));
		}
		if (FCode.equalsIgnoreCase("RequestType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RequestType));
		}
		if (FCode.equalsIgnoreCase("OtherTypeDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherTypeDesc));
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysPlanCode));
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("RequestDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RequestDesc));
		}
		if (FCode.equalsIgnoreCase("SubUWOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubUWOpinion));
		}
		if (FCode.equalsIgnoreCase("BranchUWOpinion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchUWOpinion));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(QuotNo);
				break;
			case 2:
				strFieldValue = String.valueOf(QuotBatNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RequestType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherTypeDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SysPlanCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RequestDesc);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SubUWOpinion);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BranchUWOpinion);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Segment1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Segment2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Segment3);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
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
		if (FCode.equalsIgnoreCase("RequestType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RequestType = FValue.trim();
			}
			else
				RequestType = null;
		}
		if (FCode.equalsIgnoreCase("OtherTypeDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherTypeDesc = FValue.trim();
			}
			else
				OtherTypeDesc = null;
		}
		if (FCode.equalsIgnoreCase("SysPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysPlanCode = FValue.trim();
			}
			else
				SysPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanCode = FValue.trim();
			}
			else
				PlanCode = null;
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
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("RequestDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RequestDesc = FValue.trim();
			}
			else
				RequestDesc = null;
		}
		if (FCode.equalsIgnoreCase("SubUWOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubUWOpinion = FValue.trim();
			}
			else
				SubUWOpinion = null;
		}
		if (FCode.equalsIgnoreCase("BranchUWOpinion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchUWOpinion = FValue.trim();
			}
			else
				BranchUWOpinion = null;
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
		LSQuotRequestSchema other = (LSQuotRequestSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& QuotNo.equals(other.getQuotNo())
			&& QuotBatNo == other.getQuotBatNo()
			&& RequestType.equals(other.getRequestType())
			&& OtherTypeDesc.equals(other.getOtherTypeDesc())
			&& SysPlanCode.equals(other.getSysPlanCode())
			&& PlanCode.equals(other.getPlanCode())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& RequestDesc.equals(other.getRequestDesc())
			&& SubUWOpinion.equals(other.getSubUWOpinion())
			&& BranchUWOpinion.equals(other.getBranchUWOpinion())
			&& Segment1.equals(other.getSegment1())
			&& Segment2.equals(other.getSegment2())
			&& Segment3.equals(other.getSegment3())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("QuotNo") ) {
			return 1;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return 2;
		}
		if( strFieldName.equals("RequestType") ) {
			return 3;
		}
		if( strFieldName.equals("OtherTypeDesc") ) {
			return 4;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return 5;
		}
		if( strFieldName.equals("PlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 7;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 8;
		}
		if( strFieldName.equals("RequestDesc") ) {
			return 9;
		}
		if( strFieldName.equals("SubUWOpinion") ) {
			return 10;
		}
		if( strFieldName.equals("BranchUWOpinion") ) {
			return 11;
		}
		if( strFieldName.equals("Segment1") ) {
			return 12;
		}
		if( strFieldName.equals("Segment2") ) {
			return 13;
		}
		if( strFieldName.equals("Segment3") ) {
			return 14;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "QuotNo";
				break;
			case 2:
				strFieldName = "QuotBatNo";
				break;
			case 3:
				strFieldName = "RequestType";
				break;
			case 4:
				strFieldName = "OtherTypeDesc";
				break;
			case 5:
				strFieldName = "SysPlanCode";
				break;
			case 6:
				strFieldName = "PlanCode";
				break;
			case 7:
				strFieldName = "RiskCode";
				break;
			case 8:
				strFieldName = "DutyCode";
				break;
			case 9:
				strFieldName = "RequestDesc";
				break;
			case 10:
				strFieldName = "SubUWOpinion";
				break;
			case 11:
				strFieldName = "BranchUWOpinion";
				break;
			case 12:
				strFieldName = "Segment1";
				break;
			case 13:
				strFieldName = "Segment2";
				break;
			case 14:
				strFieldName = "Segment3";
				break;
			case 15:
				strFieldName = "MakeOperator";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyOperator";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuotBatNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RequestType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherTypeDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RequestDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubUWOpinion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchUWOpinion") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
