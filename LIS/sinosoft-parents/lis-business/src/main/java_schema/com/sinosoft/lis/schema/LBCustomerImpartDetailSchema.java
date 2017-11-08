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
import com.sinosoft.lis.db.LBCustomerImpartDetailDB;

/*
 * <p>ClassName: LBCustomerImpartDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LBCustomerImpartDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBCustomerImpartDetailSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 总单投保单号码 */
	private String ProposalContNo;
	/** 印刷号码 */
	private String PrtNo;
	/** 告知编码 */
	private String ImpartCode;
	/** 告知版别 */
	private String ImpartVer;
	/** 内部流水号 */
	private String SubSerialNo;
	/** 告知内容 */
	private String ImpartDetailContent;
	/** 疾病内容 */
	private String DiseaseContent;
	/** 开始时间 */
	private Date StartDate;
	/** 结束时间 */
	private Date EndDate;
	/** 证明人 */
	private String Prover;
	/** 目前状况 */
	private String CurrCondition;
	/** 能否证明 */
	private String IsProved;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户号码类型 */
	private String CustomerNoType;
	/** 是否参与核保核赔标志 */
	private String UWClaimFlg;
	/** 打印标志 */
	private String PrtFlag;
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
	/** 批次号 */
	private int PatchNo;

	public static final int FIELDNUM = 25;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBCustomerImpartDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[9];
		pk[0] = "EdorNo";
		pk[1] = "GrpContNo";
		pk[2] = "ProposalContNo";
		pk[3] = "ImpartCode";
		pk[4] = "ImpartVer";
		pk[5] = "SubSerialNo";
		pk[6] = "CustomerNo";
		pk[7] = "CustomerNoType";
		pk[8] = "PatchNo";

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
		LBCustomerImpartDetailSchema cloned = (LBCustomerImpartDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/**
	* 第3,4位的意义如下：<p>
	* 11：个人印刷号码<p>
	* 12：集体印刷号码<p>
	* 15：银行险印刷号码
	*/
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getImpartCode()
	{
		return ImpartCode;
	}
	public void setImpartCode(String aImpartCode)
	{
		ImpartCode = aImpartCode;
	}
	public String getImpartVer()
	{
		return ImpartVer;
	}
	public void setImpartVer(String aImpartVer)
	{
		ImpartVer = aImpartVer;
	}
	public String getSubSerialNo()
	{
		return SubSerialNo;
	}
	public void setSubSerialNo(String aSubSerialNo)
	{
		SubSerialNo = aSubSerialNo;
	}
	public String getImpartDetailContent()
	{
		return ImpartDetailContent;
	}
	public void setImpartDetailContent(String aImpartDetailContent)
	{
		ImpartDetailContent = aImpartDetailContent;
	}
	public String getDiseaseContent()
	{
		return DiseaseContent;
	}
	public void setDiseaseContent(String aDiseaseContent)
	{
		DiseaseContent = aDiseaseContent;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getProver()
	{
		return Prover;
	}
	public void setProver(String aProver)
	{
		Prover = aProver;
	}
	public String getCurrCondition()
	{
		return CurrCondition;
	}
	public void setCurrCondition(String aCurrCondition)
	{
		CurrCondition = aCurrCondition;
	}
	/**
	* 0-不能够证明<p>
	* 1－能够证明
	*/
	public String getIsProved()
	{
		return IsProved;
	}
	public void setIsProved(String aIsProved)
	{
		IsProved = aIsProved;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/**
	* 0 --客户号码为投保人<p>
	* I --客户号码为被保人<p>
	* 3 －客户号码为受益人
	*/
	public String getCustomerNoType()
	{
		return CustomerNoType;
	}
	public void setCustomerNoType(String aCustomerNoType)
	{
		CustomerNoType = aCustomerNoType;
	}
	public String getUWClaimFlg()
	{
		return UWClaimFlg;
	}
	public void setUWClaimFlg(String aUWClaimFlg)
	{
		UWClaimFlg = aUWClaimFlg;
	}
	public String getPrtFlag()
	{
		return PrtFlag;
	}
	public void setPrtFlag(String aPrtFlag)
	{
		PrtFlag = aPrtFlag;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
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
	public int getPatchNo()
	{
		return PatchNo;
	}
	public void setPatchNo(int aPatchNo)
	{
		PatchNo = aPatchNo;
	}
	public void setPatchNo(String aPatchNo)
	{
		if (aPatchNo != null && !aPatchNo.equals(""))
		{
			Integer tInteger = new Integer(aPatchNo);
			int i = tInteger.intValue();
			PatchNo = i;
		}
	}


	/**
	* 使用另外一个 LBCustomerImpartDetailSchema 对象给 Schema 赋值
	* @param: aLBCustomerImpartDetailSchema LBCustomerImpartDetailSchema
	**/
	public void setSchema(LBCustomerImpartDetailSchema aLBCustomerImpartDetailSchema)
	{
		this.EdorNo = aLBCustomerImpartDetailSchema.getEdorNo();
		this.GrpContNo = aLBCustomerImpartDetailSchema.getGrpContNo();
		this.ContNo = aLBCustomerImpartDetailSchema.getContNo();
		this.ProposalContNo = aLBCustomerImpartDetailSchema.getProposalContNo();
		this.PrtNo = aLBCustomerImpartDetailSchema.getPrtNo();
		this.ImpartCode = aLBCustomerImpartDetailSchema.getImpartCode();
		this.ImpartVer = aLBCustomerImpartDetailSchema.getImpartVer();
		this.SubSerialNo = aLBCustomerImpartDetailSchema.getSubSerialNo();
		this.ImpartDetailContent = aLBCustomerImpartDetailSchema.getImpartDetailContent();
		this.DiseaseContent = aLBCustomerImpartDetailSchema.getDiseaseContent();
		this.StartDate = fDate.getDate( aLBCustomerImpartDetailSchema.getStartDate());
		this.EndDate = fDate.getDate( aLBCustomerImpartDetailSchema.getEndDate());
		this.Prover = aLBCustomerImpartDetailSchema.getProver();
		this.CurrCondition = aLBCustomerImpartDetailSchema.getCurrCondition();
		this.IsProved = aLBCustomerImpartDetailSchema.getIsProved();
		this.CustomerNo = aLBCustomerImpartDetailSchema.getCustomerNo();
		this.CustomerNoType = aLBCustomerImpartDetailSchema.getCustomerNoType();
		this.UWClaimFlg = aLBCustomerImpartDetailSchema.getUWClaimFlg();
		this.PrtFlag = aLBCustomerImpartDetailSchema.getPrtFlag();
		this.Operator = aLBCustomerImpartDetailSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBCustomerImpartDetailSchema.getMakeDate());
		this.MakeTime = aLBCustomerImpartDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBCustomerImpartDetailSchema.getModifyDate());
		this.ModifyTime = aLBCustomerImpartDetailSchema.getModifyTime();
		this.PatchNo = aLBCustomerImpartDetailSchema.getPatchNo();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("ImpartCode") == null )
				this.ImpartCode = null;
			else
				this.ImpartCode = rs.getString("ImpartCode").trim();

			if( rs.getString("ImpartVer") == null )
				this.ImpartVer = null;
			else
				this.ImpartVer = rs.getString("ImpartVer").trim();

			if( rs.getString("SubSerialNo") == null )
				this.SubSerialNo = null;
			else
				this.SubSerialNo = rs.getString("SubSerialNo").trim();

			if( rs.getString("ImpartDetailContent") == null )
				this.ImpartDetailContent = null;
			else
				this.ImpartDetailContent = rs.getString("ImpartDetailContent").trim();

			if( rs.getString("DiseaseContent") == null )
				this.DiseaseContent = null;
			else
				this.DiseaseContent = rs.getString("DiseaseContent").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("Prover") == null )
				this.Prover = null;
			else
				this.Prover = rs.getString("Prover").trim();

			if( rs.getString("CurrCondition") == null )
				this.CurrCondition = null;
			else
				this.CurrCondition = rs.getString("CurrCondition").trim();

			if( rs.getString("IsProved") == null )
				this.IsProved = null;
			else
				this.IsProved = rs.getString("IsProved").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerNoType") == null )
				this.CustomerNoType = null;
			else
				this.CustomerNoType = rs.getString("CustomerNoType").trim();

			if( rs.getString("UWClaimFlg") == null )
				this.UWClaimFlg = null;
			else
				this.UWClaimFlg = rs.getString("UWClaimFlg").trim();

			if( rs.getString("PrtFlag") == null )
				this.PrtFlag = null;
			else
				this.PrtFlag = rs.getString("PrtFlag").trim();

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

			this.PatchNo = rs.getInt("PatchNo");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBCustomerImpartDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBCustomerImpartDetailSchema getSchema()
	{
		LBCustomerImpartDetailSchema aLBCustomerImpartDetailSchema = new LBCustomerImpartDetailSchema();
		aLBCustomerImpartDetailSchema.setSchema(this);
		return aLBCustomerImpartDetailSchema;
	}

	public LBCustomerImpartDetailDB getDB()
	{
		LBCustomerImpartDetailDB aDBOper = new LBCustomerImpartDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCustomerImpartDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartDetailContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseaseContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Prover)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrCondition)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsProved)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWClaimFlg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PatchNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBCustomerImpartDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ImpartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ImpartVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SubSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ImpartDetailContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DiseaseContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			Prover = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CurrCondition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			IsProved = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CustomerNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UWClaimFlg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PrtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PatchNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBCustomerImpartDetailSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartCode));
		}
		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartVer));
		}
		if (FCode.equalsIgnoreCase("SubSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubSerialNo));
		}
		if (FCode.equalsIgnoreCase("ImpartDetailContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartDetailContent));
		}
		if (FCode.equalsIgnoreCase("DiseaseContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseaseContent));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("Prover"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Prover));
		}
		if (FCode.equalsIgnoreCase("CurrCondition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrCondition));
		}
		if (FCode.equalsIgnoreCase("IsProved"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsProved));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNoType));
		}
		if (FCode.equalsIgnoreCase("UWClaimFlg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWClaimFlg));
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtFlag));
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
		if (FCode.equalsIgnoreCase("PatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchNo));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ImpartCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ImpartVer);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SubSerialNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ImpartDetailContent);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DiseaseContent);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Prover);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CurrCondition);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(IsProved);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CustomerNoType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UWClaimFlg);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PrtFlag);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 24:
				strFieldValue = String.valueOf(PatchNo);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalContNo = FValue.trim();
			}
			else
				ProposalContNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
		}
		if (FCode.equalsIgnoreCase("ImpartCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartCode = FValue.trim();
			}
			else
				ImpartCode = null;
		}
		if (FCode.equalsIgnoreCase("ImpartVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartVer = FValue.trim();
			}
			else
				ImpartVer = null;
		}
		if (FCode.equalsIgnoreCase("SubSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubSerialNo = FValue.trim();
			}
			else
				SubSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("ImpartDetailContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartDetailContent = FValue.trim();
			}
			else
				ImpartDetailContent = null;
		}
		if (FCode.equalsIgnoreCase("DiseaseContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseaseContent = FValue.trim();
			}
			else
				DiseaseContent = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("Prover"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Prover = FValue.trim();
			}
			else
				Prover = null;
		}
		if (FCode.equalsIgnoreCase("CurrCondition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrCondition = FValue.trim();
			}
			else
				CurrCondition = null;
		}
		if (FCode.equalsIgnoreCase("IsProved"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsProved = FValue.trim();
			}
			else
				IsProved = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNoType = FValue.trim();
			}
			else
				CustomerNoType = null;
		}
		if (FCode.equalsIgnoreCase("UWClaimFlg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWClaimFlg = FValue.trim();
			}
			else
				UWClaimFlg = null;
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtFlag = FValue.trim();
			}
			else
				PrtFlag = null;
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
		if (FCode.equalsIgnoreCase("PatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PatchNo = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBCustomerImpartDetailSchema other = (LBCustomerImpartDetailSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ImpartCode.equals(other.getImpartCode())
			&& ImpartVer.equals(other.getImpartVer())
			&& SubSerialNo.equals(other.getSubSerialNo())
			&& ImpartDetailContent.equals(other.getImpartDetailContent())
			&& DiseaseContent.equals(other.getDiseaseContent())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& Prover.equals(other.getProver())
			&& CurrCondition.equals(other.getCurrCondition())
			&& IsProved.equals(other.getIsProved())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerNoType.equals(other.getCustomerNoType())
			&& UWClaimFlg.equals(other.getUWClaimFlg())
			&& PrtFlag.equals(other.getPrtFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PatchNo == other.getPatchNo();
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 3;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 4;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return 5;
		}
		if( strFieldName.equals("ImpartVer") ) {
			return 6;
		}
		if( strFieldName.equals("SubSerialNo") ) {
			return 7;
		}
		if( strFieldName.equals("ImpartDetailContent") ) {
			return 8;
		}
		if( strFieldName.equals("DiseaseContent") ) {
			return 9;
		}
		if( strFieldName.equals("StartDate") ) {
			return 10;
		}
		if( strFieldName.equals("EndDate") ) {
			return 11;
		}
		if( strFieldName.equals("Prover") ) {
			return 12;
		}
		if( strFieldName.equals("CurrCondition") ) {
			return 13;
		}
		if( strFieldName.equals("IsProved") ) {
			return 14;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 15;
		}
		if( strFieldName.equals("CustomerNoType") ) {
			return 16;
		}
		if( strFieldName.equals("UWClaimFlg") ) {
			return 17;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return 18;
		}
		if( strFieldName.equals("Operator") ) {
			return 19;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 20;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
		}
		if( strFieldName.equals("PatchNo") ) {
			return 24;
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "ProposalContNo";
				break;
			case 4:
				strFieldName = "PrtNo";
				break;
			case 5:
				strFieldName = "ImpartCode";
				break;
			case 6:
				strFieldName = "ImpartVer";
				break;
			case 7:
				strFieldName = "SubSerialNo";
				break;
			case 8:
				strFieldName = "ImpartDetailContent";
				break;
			case 9:
				strFieldName = "DiseaseContent";
				break;
			case 10:
				strFieldName = "StartDate";
				break;
			case 11:
				strFieldName = "EndDate";
				break;
			case 12:
				strFieldName = "Prover";
				break;
			case 13:
				strFieldName = "CurrCondition";
				break;
			case 14:
				strFieldName = "IsProved";
				break;
			case 15:
				strFieldName = "CustomerNo";
				break;
			case 16:
				strFieldName = "CustomerNoType";
				break;
			case 17:
				strFieldName = "UWClaimFlg";
				break;
			case 18:
				strFieldName = "PrtFlag";
				break;
			case 19:
				strFieldName = "Operator";
				break;
			case 20:
				strFieldName = "MakeDate";
				break;
			case 21:
				strFieldName = "MakeTime";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
				strFieldName = "ModifyTime";
				break;
			case 24:
				strFieldName = "PatchNo";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartDetailContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseaseContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Prover") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrCondition") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsProved") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWClaimFlg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtFlag") ) {
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
		if( strFieldName.equals("PatchNo") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
