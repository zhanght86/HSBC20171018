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
import com.sinosoft.lis.db.LCContPlanParamDB;

/*
 * <p>ClassName: LCContPlanParamSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCContPlanParamSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContPlanParamSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 集体投保单号码 */
	private String ProposalGrpContNo;
	/** 主险险种编码 */
	private String MainRiskCode;
	/** 主险险种版本 */
	private String MainRiskVersion;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 保险计划名称 */
	private String ContPlanName;
	/** 要素类型 */
	private String FactoryType;
	/** 要素类型对应号码 */
	private String OtherNo;
	/** 计算编码 */
	private String FactoryCode;
	/** 计算子编码 */
	private int FactorySubCode;
	/** 计算名称 */
	private String FactoryName;
	/** 内部流水号 */
	private String InerSerialNo;
	/** 参数名 */
	private String ParamName;
	/** 参数值 */
	private String Param;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 计划类别 */
	private String PlanType;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContPlanParamSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[11];
		pk[0] = "ProposalGrpContNo";
		pk[1] = "MainRiskCode";
		pk[2] = "RiskCode";
		pk[3] = "ContPlanCode";
		pk[4] = "FactoryType";
		pk[5] = "OtherNo";
		pk[6] = "FactoryCode";
		pk[7] = "FactorySubCode";
		pk[8] = "InerSerialNo";
		pk[9] = "ParamName";
		pk[10] = "PlanType";

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
		LCContPlanParamSchema cloned = (LCContPlanParamSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	public String getMainRiskCode()
	{
		return MainRiskCode;
	}
	public void setMainRiskCode(String aMainRiskCode)
	{
		MainRiskCode = aMainRiskCode;
	}
	public String getMainRiskVersion()
	{
		return MainRiskVersion;
	}
	public void setMainRiskVersion(String aMainRiskVersion)
	{
		MainRiskVersion = aMainRiskVersion;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	/**
	* 00-默认值
	*/
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		ContPlanCode = aContPlanCode;
	}
	public String getContPlanName()
	{
		return ContPlanName;
	}
	public void setContPlanName(String aContPlanName)
	{
		ContPlanName = aContPlanName;
	}
	/**
	* 对应LMFactoryMode表中的Type字段
	*/
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	public String getFactoryCode()
	{
		return FactoryCode;
	}
	public void setFactoryCode(String aFactoryCode)
	{
		FactoryCode = aFactoryCode;
	}
	public int getFactorySubCode()
	{
		return FactorySubCode;
	}
	public void setFactorySubCode(int aFactorySubCode)
	{
		FactorySubCode = aFactorySubCode;
	}
	public void setFactorySubCode(String aFactorySubCode)
	{
		if (aFactorySubCode != null && !aFactorySubCode.equals(""))
		{
			Integer tInteger = new Integer(aFactorySubCode);
			int i = tInteger.intValue();
			FactorySubCode = i;
		}
	}

	public String getFactoryName()
	{
		return FactoryName;
	}
	public void setFactoryName(String aFactoryName)
	{
		FactoryName = aFactoryName;
	}
	public String getInerSerialNo()
	{
		return InerSerialNo;
	}
	public void setInerSerialNo(String aInerSerialNo)
	{
		InerSerialNo = aInerSerialNo;
	}
	public String getParamName()
	{
		return ParamName;
	}
	public void setParamName(String aParamName)
	{
		ParamName = aParamName;
	}
	public String getParam()
	{
		return Param;
	}
	public void setParam(String aParam)
	{
		Param = aParam;
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
		ModifyTime = aModifyTime;
	}
	/**
	* 0-固定计划；<p>
	* 1-非固定计划<p>
	* 3-险种默认值
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}

	/**
	* 使用另外一个 LCContPlanParamSchema 对象给 Schema 赋值
	* @param: aLCContPlanParamSchema LCContPlanParamSchema
	**/
	public void setSchema(LCContPlanParamSchema aLCContPlanParamSchema)
	{
		this.GrpContNo = aLCContPlanParamSchema.getGrpContNo();
		this.ProposalGrpContNo = aLCContPlanParamSchema.getProposalGrpContNo();
		this.MainRiskCode = aLCContPlanParamSchema.getMainRiskCode();
		this.MainRiskVersion = aLCContPlanParamSchema.getMainRiskVersion();
		this.RiskCode = aLCContPlanParamSchema.getRiskCode();
		this.RiskVersion = aLCContPlanParamSchema.getRiskVersion();
		this.ContPlanCode = aLCContPlanParamSchema.getContPlanCode();
		this.ContPlanName = aLCContPlanParamSchema.getContPlanName();
		this.FactoryType = aLCContPlanParamSchema.getFactoryType();
		this.OtherNo = aLCContPlanParamSchema.getOtherNo();
		this.FactoryCode = aLCContPlanParamSchema.getFactoryCode();
		this.FactorySubCode = aLCContPlanParamSchema.getFactorySubCode();
		this.FactoryName = aLCContPlanParamSchema.getFactoryName();
		this.InerSerialNo = aLCContPlanParamSchema.getInerSerialNo();
		this.ParamName = aLCContPlanParamSchema.getParamName();
		this.Param = aLCContPlanParamSchema.getParam();
		this.MakeDate = fDate.getDate( aLCContPlanParamSchema.getMakeDate());
		this.MakeTime = aLCContPlanParamSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCContPlanParamSchema.getModifyDate());
		this.ModifyTime = aLCContPlanParamSchema.getModifyTime();
		this.PlanType = aLCContPlanParamSchema.getPlanType();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("MainRiskCode") == null )
				this.MainRiskCode = null;
			else
				this.MainRiskCode = rs.getString("MainRiskCode").trim();

			if( rs.getString("MainRiskVersion") == null )
				this.MainRiskVersion = null;
			else
				this.MainRiskVersion = rs.getString("MainRiskVersion").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("ContPlanName") == null )
				this.ContPlanName = null;
			else
				this.ContPlanName = rs.getString("ContPlanName").trim();

			if( rs.getString("FactoryType") == null )
				this.FactoryType = null;
			else
				this.FactoryType = rs.getString("FactoryType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("FactoryCode") == null )
				this.FactoryCode = null;
			else
				this.FactoryCode = rs.getString("FactoryCode").trim();

			this.FactorySubCode = rs.getInt("FactorySubCode");
			if( rs.getString("FactoryName") == null )
				this.FactoryName = null;
			else
				this.FactoryName = rs.getString("FactoryName").trim();

			if( rs.getString("InerSerialNo") == null )
				this.InerSerialNo = null;
			else
				this.InerSerialNo = rs.getString("InerSerialNo").trim();

			if( rs.getString("ParamName") == null )
				this.ParamName = null;
			else
				this.ParamName = rs.getString("ParamName").trim();

			if( rs.getString("Param") == null )
				this.Param = null;
			else
				this.Param = rs.getString("Param").trim();

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

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCContPlanParam表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanParamSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContPlanParamSchema getSchema()
	{
		LCContPlanParamSchema aLCContPlanParamSchema = new LCContPlanParamSchema();
		aLCContPlanParamSchema.setSchema(this);
		return aLCContPlanParamSchema;
	}

	public LCContPlanParamDB getDB()
	{
		LCContPlanParamDB aDBOper = new LCContPlanParamDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanParam描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainRiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorySubCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InerSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParamName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Param)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPlanParam>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MainRiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ContPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FactoryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			FactoryCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			FactorySubCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			FactoryName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InerSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ParamName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Param = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPlanParamSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("MainRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskCode));
		}
		if (FCode.equalsIgnoreCase("MainRiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskVersion));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanName));
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryCode));
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubCode));
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryName));
		}
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InerSerialNo));
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParamName));
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Param));
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
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MainRiskVersion);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ContPlanName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(FactoryType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FactoryCode);
				break;
			case 11:
				strFieldValue = String.valueOf(FactorySubCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(FactoryName);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InerSerialNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ParamName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Param);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("MainRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskCode = FValue.trim();
			}
			else
				MainRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("MainRiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskVersion = FValue.trim();
			}
			else
				MainRiskVersion = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanName = FValue.trim();
			}
			else
				ContPlanName = null;
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryType = FValue.trim();
			}
			else
				FactoryType = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryCode = FValue.trim();
			}
			else
				FactoryCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FactorySubCode = i;
			}
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryName = FValue.trim();
			}
			else
				FactoryName = null;
		}
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InerSerialNo = FValue.trim();
			}
			else
				InerSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("ParamName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParamName = FValue.trim();
			}
			else
				ParamName = null;
		}
		if (FCode.equalsIgnoreCase("Param"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Param = FValue.trim();
			}
			else
				Param = null;
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
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContPlanParamSchema other = (LCContPlanParamSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& MainRiskCode.equals(other.getMainRiskCode())
			&& MainRiskVersion.equals(other.getMainRiskVersion())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& ContPlanCode.equals(other.getContPlanCode())
			&& ContPlanName.equals(other.getContPlanName())
			&& FactoryType.equals(other.getFactoryType())
			&& OtherNo.equals(other.getOtherNo())
			&& FactoryCode.equals(other.getFactoryCode())
			&& FactorySubCode == other.getFactorySubCode()
			&& FactoryName.equals(other.getFactoryName())
			&& InerSerialNo.equals(other.getInerSerialNo())
			&& ParamName.equals(other.getParamName())
			&& Param.equals(other.getParam())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PlanType.equals(other.getPlanType());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("MainRiskVersion") ) {
			return 3;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 4;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 5;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return 6;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return 7;
		}
		if( strFieldName.equals("FactoryType") ) {
			return 8;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 9;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return 10;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return 11;
		}
		if( strFieldName.equals("FactoryName") ) {
			return 12;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return 13;
		}
		if( strFieldName.equals("ParamName") ) {
			return 14;
		}
		if( strFieldName.equals("Param") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
		}
		if( strFieldName.equals("PlanType") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "ProposalGrpContNo";
				break;
			case 2:
				strFieldName = "MainRiskCode";
				break;
			case 3:
				strFieldName = "MainRiskVersion";
				break;
			case 4:
				strFieldName = "RiskCode";
				break;
			case 5:
				strFieldName = "RiskVersion";
				break;
			case 6:
				strFieldName = "ContPlanCode";
				break;
			case 7:
				strFieldName = "ContPlanName";
				break;
			case 8:
				strFieldName = "FactoryType";
				break;
			case 9:
				strFieldName = "OtherNo";
				break;
			case 10:
				strFieldName = "FactoryCode";
				break;
			case 11:
				strFieldName = "FactorySubCode";
				break;
			case 12:
				strFieldName = "FactoryName";
				break;
			case 13:
				strFieldName = "InerSerialNo";
				break;
			case 14:
				strFieldName = "ParamName";
				break;
			case 15:
				strFieldName = "Param";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
				strFieldName = "ModifyTime";
				break;
			case 20:
				strFieldName = "PlanType";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FactoryName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParamName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Param") ) {
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
		if( strFieldName.equals("PlanType") ) {
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
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
