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
import com.sinosoft.lis.db.LBPOCustomerImpartDB;

/*
 * <p>ClassName: LBPOCustomerImpartSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 内部外包流程
 */
public class LBPOCustomerImpartSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPOCustomerImpartSchema.class);

	// @Field
	/** 集体合同号码 */
	private String GrpContNo;
	/** 录入次数 */
	private int InputNo;
	/** 添空号 */
	private String FillNo;
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
	/** 告知内容 */
	private String ImpartContent;
	/** 告知参数展现模版 */
	private String ImpartParamModle;
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
	private String MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private String ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 批次号 */
	private int PatchNo;
	/** 第一被保人 */
	private String Insured1;
	/** 第二被保人 */
	private String Insured2;
	/** 第三被保人 */
	private String insured3;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPOCustomerImpartSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "GrpContNo";
		pk[1] = "InputNo";
		pk[2] = "FillNo";
		pk[3] = "ProposalContNo";

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
		LBPOCustomerImpartSchema cloned = (LBPOCustomerImpartSchema)super.clone();
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
	public int getInputNo()
	{
		return InputNo;
	}
	public void setInputNo(int aInputNo)
	{
		InputNo = aInputNo;
	}
	public void setInputNo(String aInputNo)
	{
		if (aInputNo != null && !aInputNo.equals(""))
		{
			Integer tInteger = new Integer(aInputNo);
			int i = tInteger.intValue();
			InputNo = i;
		}
	}

	public String getFillNo()
	{
		return FillNo;
	}
	public void setFillNo(String aFillNo)
	{
		FillNo = aFillNo;
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
	* 同投保单号
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
	public String getImpartContent()
	{
		return ImpartContent;
	}
	public void setImpartContent(String aImpartContent)
	{
		ImpartContent = aImpartContent;
	}
	public String getImpartParamModle()
	{
		return ImpartParamModle;
	}
	public void setImpartParamModle(String aImpartParamModle)
	{
		ImpartParamModle = aImpartParamModle;
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
	* 1 --客户号码为被保人<p>
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
	/**
	* 备用
	*/
	public String getUWClaimFlg()
	{
		return UWClaimFlg;
	}
	public void setUWClaimFlg(String aUWClaimFlg)
	{
		UWClaimFlg = aUWClaimFlg;
	}
	/**
	* 备用
	*/
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
		return MakeDate;
	}
	public void setMakeDate(String aMakeDate)
	{
		MakeDate = aMakeDate;
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
		return ModifyDate;
	}
	public void setModifyDate(String aModifyDate)
	{
		ModifyDate = aModifyDate;
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
	* 1————是，2————否
	*/
	public String getInsured1()
	{
		return Insured1;
	}
	public void setInsured1(String aInsured1)
	{
		Insured1 = aInsured1;
	}
	/**
	* 1————是，2————否
	*/
	public String getInsured2()
	{
		return Insured2;
	}
	public void setInsured2(String aInsured2)
	{
		Insured2 = aInsured2;
	}
	/**
	* 1————是，2————否
	*/
	public String getinsured3()
	{
		return insured3;
	}
	public void setinsured3(String ainsured3)
	{
		insured3 = ainsured3;
	}

	/**
	* 使用另外一个 LBPOCustomerImpartSchema 对象给 Schema 赋值
	* @param: aLBPOCustomerImpartSchema LBPOCustomerImpartSchema
	**/
	public void setSchema(LBPOCustomerImpartSchema aLBPOCustomerImpartSchema)
	{
		this.GrpContNo = aLBPOCustomerImpartSchema.getGrpContNo();
		this.InputNo = aLBPOCustomerImpartSchema.getInputNo();
		this.FillNo = aLBPOCustomerImpartSchema.getFillNo();
		this.ContNo = aLBPOCustomerImpartSchema.getContNo();
		this.ProposalContNo = aLBPOCustomerImpartSchema.getProposalContNo();
		this.PrtNo = aLBPOCustomerImpartSchema.getPrtNo();
		this.ImpartCode = aLBPOCustomerImpartSchema.getImpartCode();
		this.ImpartVer = aLBPOCustomerImpartSchema.getImpartVer();
		this.ImpartContent = aLBPOCustomerImpartSchema.getImpartContent();
		this.ImpartParamModle = aLBPOCustomerImpartSchema.getImpartParamModle();
		this.CustomerNo = aLBPOCustomerImpartSchema.getCustomerNo();
		this.CustomerNoType = aLBPOCustomerImpartSchema.getCustomerNoType();
		this.UWClaimFlg = aLBPOCustomerImpartSchema.getUWClaimFlg();
		this.PrtFlag = aLBPOCustomerImpartSchema.getPrtFlag();
		this.Operator = aLBPOCustomerImpartSchema.getOperator();
		this.MakeDate = aLBPOCustomerImpartSchema.getMakeDate();
		this.MakeTime = aLBPOCustomerImpartSchema.getMakeTime();
		this.ModifyDate = aLBPOCustomerImpartSchema.getModifyDate();
		this.ModifyTime = aLBPOCustomerImpartSchema.getModifyTime();
		this.PatchNo = aLBPOCustomerImpartSchema.getPatchNo();
		this.Insured1 = aLBPOCustomerImpartSchema.getInsured1();
		this.Insured2 = aLBPOCustomerImpartSchema.getInsured2();
		this.insured3 = aLBPOCustomerImpartSchema.getinsured3();
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

			this.InputNo = rs.getInt("InputNo");
			if( rs.getString("FillNo") == null )
				this.FillNo = null;
			else
				this.FillNo = rs.getString("FillNo").trim();

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

			if( rs.getString("ImpartContent") == null )
				this.ImpartContent = null;
			else
				this.ImpartContent = rs.getString("ImpartContent").trim();

			if( rs.getString("ImpartParamModle") == null )
				this.ImpartParamModle = null;
			else
				this.ImpartParamModle = rs.getString("ImpartParamModle").trim();

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

			if( rs.getString("MakeDate") == null )
				this.MakeDate = null;
			else
				this.MakeDate = rs.getString("MakeDate").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyDate") == null )
				this.ModifyDate = null;
			else
				this.ModifyDate = rs.getString("ModifyDate").trim();

			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.PatchNo = rs.getInt("PatchNo");
			if( rs.getString("Insured1") == null )
				this.Insured1 = null;
			else
				this.Insured1 = rs.getString("Insured1").trim();

			if( rs.getString("Insured2") == null )
				this.Insured2 = null;
			else
				this.Insured2 = rs.getString("Insured2").trim();

			if( rs.getString("insured3") == null )
				this.insured3 = null;
			else
				this.insured3 = rs.getString("insured3").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPOCustomerImpart表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOCustomerImpartSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPOCustomerImpartSchema getSchema()
	{
		LBPOCustomerImpartSchema aLBPOCustomerImpartSchema = new LBPOCustomerImpartSchema();
		aLBPOCustomerImpartSchema.setSchema(this);
		return aLBPOCustomerImpartSchema;
	}

	public LBPOCustomerImpartDB getDB()
	{
		LBPOCustomerImpartDB aDBOper = new LBPOCustomerImpartDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOCustomerImpart描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InputNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImpartParamModle)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWClaimFlg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyDate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PatchNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insured1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Insured2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(insured3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPOCustomerImpart>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InputNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			FillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ImpartCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ImpartVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ImpartContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ImpartParamModle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CustomerNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWClaimFlg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PrtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PatchNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).intValue();
			Insured1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Insured2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			insured3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPOCustomerImpartSchema";
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
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputNo));
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FillNo));
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
		if (FCode.equalsIgnoreCase("ImpartContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartContent));
		}
		if (FCode.equalsIgnoreCase("ImpartParamModle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImpartParamModle));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("PatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PatchNo));
		}
		if (FCode.equalsIgnoreCase("Insured1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insured1));
		}
		if (FCode.equalsIgnoreCase("Insured2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Insured2));
		}
		if (FCode.equalsIgnoreCase("insured3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(insured3));
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
				strFieldValue = String.valueOf(InputNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FillNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ImpartCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ImpartVer);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ImpartContent);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ImpartParamModle);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CustomerNoType);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UWClaimFlg);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PrtFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeDate);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyDate);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = String.valueOf(PatchNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Insured1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Insured2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(insured3);
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
		if (FCode.equalsIgnoreCase("InputNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InputNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FillNo = FValue.trim();
			}
			else
				FillNo = null;
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
		if (FCode.equalsIgnoreCase("ImpartContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartContent = FValue.trim();
			}
			else
				ImpartContent = null;
		}
		if (FCode.equalsIgnoreCase("ImpartParamModle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImpartParamModle = FValue.trim();
			}
			else
				ImpartParamModle = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				MakeDate = FValue.trim();
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
			if( FValue != null && !FValue.equals(""))
			{
				ModifyDate = FValue.trim();
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
		if (FCode.equalsIgnoreCase("Insured1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Insured1 = FValue.trim();
			}
			else
				Insured1 = null;
		}
		if (FCode.equalsIgnoreCase("Insured2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Insured2 = FValue.trim();
			}
			else
				Insured2 = null;
		}
		if (FCode.equalsIgnoreCase("insured3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				insured3 = FValue.trim();
			}
			else
				insured3 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPOCustomerImpartSchema other = (LBPOCustomerImpartSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& InputNo == other.getInputNo()
			&& FillNo.equals(other.getFillNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& ImpartCode.equals(other.getImpartCode())
			&& ImpartVer.equals(other.getImpartVer())
			&& ImpartContent.equals(other.getImpartContent())
			&& ImpartParamModle.equals(other.getImpartParamModle())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerNoType.equals(other.getCustomerNoType())
			&& UWClaimFlg.equals(other.getUWClaimFlg())
			&& PrtFlag.equals(other.getPrtFlag())
			&& Operator.equals(other.getOperator())
			&& MakeDate.equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate.equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& PatchNo == other.getPatchNo()
			&& Insured1.equals(other.getInsured1())
			&& Insured2.equals(other.getInsured2())
			&& insured3.equals(other.getinsured3());
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
		if( strFieldName.equals("InputNo") ) {
			return 1;
		}
		if( strFieldName.equals("FillNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 5;
		}
		if( strFieldName.equals("ImpartCode") ) {
			return 6;
		}
		if( strFieldName.equals("ImpartVer") ) {
			return 7;
		}
		if( strFieldName.equals("ImpartContent") ) {
			return 8;
		}
		if( strFieldName.equals("ImpartParamModle") ) {
			return 9;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 10;
		}
		if( strFieldName.equals("CustomerNoType") ) {
			return 11;
		}
		if( strFieldName.equals("UWClaimFlg") ) {
			return 12;
		}
		if( strFieldName.equals("PrtFlag") ) {
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
		if( strFieldName.equals("PatchNo") ) {
			return 19;
		}
		if( strFieldName.equals("Insured1") ) {
			return 20;
		}
		if( strFieldName.equals("Insured2") ) {
			return 21;
		}
		if( strFieldName.equals("insured3") ) {
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "InputNo";
				break;
			case 2:
				strFieldName = "FillNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "PrtNo";
				break;
			case 6:
				strFieldName = "ImpartCode";
				break;
			case 7:
				strFieldName = "ImpartVer";
				break;
			case 8:
				strFieldName = "ImpartContent";
				break;
			case 9:
				strFieldName = "ImpartParamModle";
				break;
			case 10:
				strFieldName = "CustomerNo";
				break;
			case 11:
				strFieldName = "CustomerNoType";
				break;
			case 12:
				strFieldName = "UWClaimFlg";
				break;
			case 13:
				strFieldName = "PrtFlag";
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
				strFieldName = "PatchNo";
				break;
			case 20:
				strFieldName = "Insured1";
				break;
			case 21:
				strFieldName = "Insured2";
				break;
			case 22:
				strFieldName = "insured3";
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
		if( strFieldName.equals("InputNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FillNo") ) {
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
		if( strFieldName.equals("ImpartContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImpartParamModle") ) {
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
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PatchNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Insured1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Insured2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("insured3") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
