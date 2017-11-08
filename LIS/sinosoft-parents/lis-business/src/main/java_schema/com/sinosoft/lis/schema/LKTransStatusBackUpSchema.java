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
import com.sinosoft.lis.db.LKTransStatusBackUpDB;

/*
 * <p>ClassName: LKTransStatusBackUpSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class LKTransStatusBackUpSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LKTransStatusBackUpSchema.class);

	// @Field
	/** 业务流水号 */
	private String TransCode;
	/** 报文编号 */
	private String ReportNo;
	/** 银行代码 */
	private String BankCode;
	/** 银行机构代码 */
	private String BankBranch;
	/** 银行网点代码 */
	private String BankNode;
	/** 银行操作员代码 */
	private String BankOperator;
	/** 交易流水号(银行) */
	private String TransNo;
	/** 处理标志 */
	private String FuncFlag;
	/** 交易日期 */
	private Date TransDate;
	/** 交易时间 */
	private String TransTime;
	/** 区站(管理机构) */
	private String ManageCom;
	/** 险种代码 */
	private String RiskCode;
	/** 投保单号 */
	private String ProposalNo;
	/** 印刷号 */
	private String PrtNo;
	/** 保单号 */
	private String PolNo;
	/** 批单号 */
	private String EdorNo;
	/** 收据号 */
	private String TempFeeNo;
	/** 交易金额 */
	private double TransAmnt;
	/** 银行信用卡号 */
	private String BankAcc;
	/** 返还码 */
	private String RCode;
	/** 交易状态 */
	private String TransStatus;
	/** 业务状态 */
	private String Status;
	/** 描述 */
	private String Descr;
	/** 备用字段 */
	private String Temp;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 28;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKTransStatusBackUpSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[0];

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
		LKTransStatusBackUpSchema cloned = (LKTransStatusBackUpSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTransCode()
	{
		return TransCode;
	}
	public void setTransCode(String aTransCode)
	{
		TransCode = aTransCode;
	}
	public String getReportNo()
	{
		return ReportNo;
	}
	public void setReportNo(String aReportNo)
	{
		ReportNo = aReportNo;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankBranch()
	{
		return BankBranch;
	}
	public void setBankBranch(String aBankBranch)
	{
		BankBranch = aBankBranch;
	}
	public String getBankNode()
	{
		return BankNode;
	}
	public void setBankNode(String aBankNode)
	{
		BankNode = aBankNode;
	}
	public String getBankOperator()
	{
		return BankOperator;
	}
	public void setBankOperator(String aBankOperator)
	{
		BankOperator = aBankOperator;
	}
	public String getTransNo()
	{
		return TransNo;
	}
	public void setTransNo(String aTransNo)
	{
		TransNo = aTransNo;
	}
	public String getFuncFlag()
	{
		return FuncFlag;
	}
	public void setFuncFlag(String aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	public String getTransDate()
	{
		if( TransDate != null )
			return fDate.getString(TransDate);
		else
			return null;
	}
	public void setTransDate(Date aTransDate)
	{
		TransDate = aTransDate;
	}
	public void setTransDate(String aTransDate)
	{
		if (aTransDate != null && !aTransDate.equals("") )
		{
			TransDate = fDate.getDate( aTransDate );
		}
		else
			TransDate = null;
	}

	public String getTransTime()
	{
		return TransTime;
	}
	public void setTransTime(String aTransTime)
	{
		TransTime = aTransTime;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getProposalNo()
	{
		return ProposalNo;
	}
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getTempFeeNo()
	{
		return TempFeeNo;
	}
	public void setTempFeeNo(String aTempFeeNo)
	{
		TempFeeNo = aTempFeeNo;
	}
	public double getTransAmnt()
	{
		return TransAmnt;
	}
	public void setTransAmnt(double aTransAmnt)
	{
		TransAmnt = aTransAmnt;
	}
	public void setTransAmnt(String aTransAmnt)
	{
		if (aTransAmnt != null && !aTransAmnt.equals(""))
		{
			Double tDouble = new Double(aTransAmnt);
			double d = tDouble.doubleValue();
			TransAmnt = d;
		}
	}

	public String getBankAcc()
	{
		return BankAcc;
	}
	public void setBankAcc(String aBankAcc)
	{
		BankAcc = aBankAcc;
	}
	public String getRCode()
	{
		return RCode;
	}
	public void setRCode(String aRCode)
	{
		RCode = aRCode;
	}
	public String getTransStatus()
	{
		return TransStatus;
	}
	public void setTransStatus(String aTransStatus)
	{
		TransStatus = aTransStatus;
	}
	public String getStatus()
	{
		return Status;
	}
	public void setStatus(String aStatus)
	{
		Status = aStatus;
	}
	public String getDescr()
	{
		return Descr;
	}
	public void setDescr(String aDescr)
	{
		Descr = aDescr;
	}
	public String getTemp()
	{
		return Temp;
	}
	public void setTemp(String aTemp)
	{
		Temp = aTemp;
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
	* 使用另外一个 LKTransStatusBackUpSchema 对象给 Schema 赋值
	* @param: aLKTransStatusBackUpSchema LKTransStatusBackUpSchema
	**/
	public void setSchema(LKTransStatusBackUpSchema aLKTransStatusBackUpSchema)
	{
		this.TransCode = aLKTransStatusBackUpSchema.getTransCode();
		this.ReportNo = aLKTransStatusBackUpSchema.getReportNo();
		this.BankCode = aLKTransStatusBackUpSchema.getBankCode();
		this.BankBranch = aLKTransStatusBackUpSchema.getBankBranch();
		this.BankNode = aLKTransStatusBackUpSchema.getBankNode();
		this.BankOperator = aLKTransStatusBackUpSchema.getBankOperator();
		this.TransNo = aLKTransStatusBackUpSchema.getTransNo();
		this.FuncFlag = aLKTransStatusBackUpSchema.getFuncFlag();
		this.TransDate = fDate.getDate( aLKTransStatusBackUpSchema.getTransDate());
		this.TransTime = aLKTransStatusBackUpSchema.getTransTime();
		this.ManageCom = aLKTransStatusBackUpSchema.getManageCom();
		this.RiskCode = aLKTransStatusBackUpSchema.getRiskCode();
		this.ProposalNo = aLKTransStatusBackUpSchema.getProposalNo();
		this.PrtNo = aLKTransStatusBackUpSchema.getPrtNo();
		this.PolNo = aLKTransStatusBackUpSchema.getPolNo();
		this.EdorNo = aLKTransStatusBackUpSchema.getEdorNo();
		this.TempFeeNo = aLKTransStatusBackUpSchema.getTempFeeNo();
		this.TransAmnt = aLKTransStatusBackUpSchema.getTransAmnt();
		this.BankAcc = aLKTransStatusBackUpSchema.getBankAcc();
		this.RCode = aLKTransStatusBackUpSchema.getRCode();
		this.TransStatus = aLKTransStatusBackUpSchema.getTransStatus();
		this.Status = aLKTransStatusBackUpSchema.getStatus();
		this.Descr = aLKTransStatusBackUpSchema.getDescr();
		this.Temp = aLKTransStatusBackUpSchema.getTemp();
		this.MakeDate = fDate.getDate( aLKTransStatusBackUpSchema.getMakeDate());
		this.MakeTime = aLKTransStatusBackUpSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLKTransStatusBackUpSchema.getModifyDate());
		this.ModifyTime = aLKTransStatusBackUpSchema.getModifyTime();
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
			if( rs.getString("TransCode") == null )
				this.TransCode = null;
			else
				this.TransCode = rs.getString("TransCode").trim();

			if( rs.getString("ReportNo") == null )
				this.ReportNo = null;
			else
				this.ReportNo = rs.getString("ReportNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankBranch") == null )
				this.BankBranch = null;
			else
				this.BankBranch = rs.getString("BankBranch").trim();

			if( rs.getString("BankNode") == null )
				this.BankNode = null;
			else
				this.BankNode = rs.getString("BankNode").trim();

			if( rs.getString("BankOperator") == null )
				this.BankOperator = null;
			else
				this.BankOperator = rs.getString("BankOperator").trim();

			if( rs.getString("TransNo") == null )
				this.TransNo = null;
			else
				this.TransNo = rs.getString("TransNo").trim();

			if( rs.getString("FuncFlag") == null )
				this.FuncFlag = null;
			else
				this.FuncFlag = rs.getString("FuncFlag").trim();

			this.TransDate = rs.getDate("TransDate");
			if( rs.getString("TransTime") == null )
				this.TransTime = null;
			else
				this.TransTime = rs.getString("TransTime").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("TempFeeNo") == null )
				this.TempFeeNo = null;
			else
				this.TempFeeNo = rs.getString("TempFeeNo").trim();

			this.TransAmnt = rs.getDouble("TransAmnt");
			if( rs.getString("BankAcc") == null )
				this.BankAcc = null;
			else
				this.BankAcc = rs.getString("BankAcc").trim();

			if( rs.getString("RCode") == null )
				this.RCode = null;
			else
				this.RCode = rs.getString("RCode").trim();

			if( rs.getString("TransStatus") == null )
				this.TransStatus = null;
			else
				this.TransStatus = rs.getString("TransStatus").trim();

			if( rs.getString("Status") == null )
				this.Status = null;
			else
				this.Status = rs.getString("Status").trim();

			if( rs.getString("Descr") == null )
				this.Descr = null;
			else
				this.Descr = rs.getString("Descr").trim();

			if( rs.getString("Temp") == null )
				this.Temp = null;
			else
				this.Temp = rs.getString("Temp").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LKTransStatusBackUp表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKTransStatusBackUpSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LKTransStatusBackUpSchema getSchema()
	{
		LKTransStatusBackUpSchema aLKTransStatusBackUpSchema = new LKTransStatusBackUpSchema();
		aLKTransStatusBackUpSchema.setSchema(this);
		return aLKTransStatusBackUpSchema;
	}

	public LKTransStatusBackUpDB getDB()
	{
		LKTransStatusBackUpDB aDBOper = new LKTransStatusBackUpDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTransStatusBackUp描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TransCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReportNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankBranch)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FuncFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TempFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TransAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankAcc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransStatus)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Status)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Descr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Temp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTransStatusBackUp>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ReportNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BankBranch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BankNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BankOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TransNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FuncFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TransDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			TransTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			TempFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			TransAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			BankAcc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			RCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			TransStatus = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Status = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Descr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Temp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKTransStatusBackUpSchema";
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
		if (FCode.equalsIgnoreCase("TransCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransCode));
		}
		if (FCode.equalsIgnoreCase("ReportNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReportNo));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankBranch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankBranch));
		}
		if (FCode.equalsIgnoreCase("BankNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankNode));
		}
		if (FCode.equalsIgnoreCase("BankOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankOperator));
		}
		if (FCode.equalsIgnoreCase("TransNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransNo));
		}
		if (FCode.equalsIgnoreCase("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransTime));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TempFeeNo));
		}
		if (FCode.equalsIgnoreCase("TransAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransAmnt));
		}
		if (FCode.equalsIgnoreCase("BankAcc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAcc));
		}
		if (FCode.equalsIgnoreCase("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equalsIgnoreCase("TransStatus"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransStatus));
		}
		if (FCode.equalsIgnoreCase("Status"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Status));
		}
		if (FCode.equalsIgnoreCase("Descr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Descr));
		}
		if (FCode.equalsIgnoreCase("Temp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp));
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
				strFieldValue = StrTool.GBKToUnicode(TransCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ReportNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BankBranch);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BankNode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BankOperator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TransNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FuncFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(TransTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(TempFeeNo);
				break;
			case 17:
				strFieldValue = String.valueOf(TransAmnt);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(BankAcc);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(RCode);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(TransStatus);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Status);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Descr);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Temp);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
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

		if (FCode.equalsIgnoreCase("TransCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransCode = FValue.trim();
			}
			else
				TransCode = null;
		}
		if (FCode.equalsIgnoreCase("ReportNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReportNo = FValue.trim();
			}
			else
				ReportNo = null;
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equalsIgnoreCase("BankBranch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankBranch = FValue.trim();
			}
			else
				BankBranch = null;
		}
		if (FCode.equalsIgnoreCase("BankNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankNode = FValue.trim();
			}
			else
				BankNode = null;
		}
		if (FCode.equalsIgnoreCase("BankOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankOperator = FValue.trim();
			}
			else
				BankOperator = null;
		}
		if (FCode.equalsIgnoreCase("TransNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransNo = FValue.trim();
			}
			else
				TransNo = null;
		}
		if (FCode.equalsIgnoreCase("FuncFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FuncFlag = FValue.trim();
			}
			else
				FuncFlag = null;
		}
		if (FCode.equalsIgnoreCase("TransDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransDate = fDate.getDate( FValue );
			}
			else
				TransDate = null;
		}
		if (FCode.equalsIgnoreCase("TransTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransTime = FValue.trim();
			}
			else
				TransTime = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("TempFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TempFeeNo = FValue.trim();
			}
			else
				TempFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("TransAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TransAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("BankAcc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAcc = FValue.trim();
			}
			else
				BankAcc = null;
		}
		if (FCode.equalsIgnoreCase("RCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCode = FValue.trim();
			}
			else
				RCode = null;
		}
		if (FCode.equalsIgnoreCase("TransStatus"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransStatus = FValue.trim();
			}
			else
				TransStatus = null;
		}
		if (FCode.equalsIgnoreCase("Status"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Status = FValue.trim();
			}
			else
				Status = null;
		}
		if (FCode.equalsIgnoreCase("Descr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Descr = FValue.trim();
			}
			else
				Descr = null;
		}
		if (FCode.equalsIgnoreCase("Temp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp = FValue.trim();
			}
			else
				Temp = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKTransStatusBackUpSchema other = (LKTransStatusBackUpSchema)otherObject;
		return
			TransCode.equals(other.getTransCode())
			&& ReportNo.equals(other.getReportNo())
			&& BankCode.equals(other.getBankCode())
			&& BankBranch.equals(other.getBankBranch())
			&& BankNode.equals(other.getBankNode())
			&& BankOperator.equals(other.getBankOperator())
			&& TransNo.equals(other.getTransNo())
			&& FuncFlag.equals(other.getFuncFlag())
			&& fDate.getString(TransDate).equals(other.getTransDate())
			&& TransTime.equals(other.getTransTime())
			&& ManageCom.equals(other.getManageCom())
			&& RiskCode.equals(other.getRiskCode())
			&& ProposalNo.equals(other.getProposalNo())
			&& PrtNo.equals(other.getPrtNo())
			&& PolNo.equals(other.getPolNo())
			&& EdorNo.equals(other.getEdorNo())
			&& TempFeeNo.equals(other.getTempFeeNo())
			&& TransAmnt == other.getTransAmnt()
			&& BankAcc.equals(other.getBankAcc())
			&& RCode.equals(other.getRCode())
			&& TransStatus.equals(other.getTransStatus())
			&& Status.equals(other.getStatus())
			&& Descr.equals(other.getDescr())
			&& Temp.equals(other.getTemp())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
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
		if( strFieldName.equals("TransCode") ) {
			return 0;
		}
		if( strFieldName.equals("ReportNo") ) {
			return 1;
		}
		if( strFieldName.equals("BankCode") ) {
			return 2;
		}
		if( strFieldName.equals("BankBranch") ) {
			return 3;
		}
		if( strFieldName.equals("BankNode") ) {
			return 4;
		}
		if( strFieldName.equals("BankOperator") ) {
			return 5;
		}
		if( strFieldName.equals("TransNo") ) {
			return 6;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 7;
		}
		if( strFieldName.equals("TransDate") ) {
			return 8;
		}
		if( strFieldName.equals("TransTime") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 11;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 12;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 13;
		}
		if( strFieldName.equals("PolNo") ) {
			return 14;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 15;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return 16;
		}
		if( strFieldName.equals("TransAmnt") ) {
			return 17;
		}
		if( strFieldName.equals("BankAcc") ) {
			return 18;
		}
		if( strFieldName.equals("RCode") ) {
			return 19;
		}
		if( strFieldName.equals("TransStatus") ) {
			return 20;
		}
		if( strFieldName.equals("Status") ) {
			return 21;
		}
		if( strFieldName.equals("Descr") ) {
			return 22;
		}
		if( strFieldName.equals("Temp") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
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
				strFieldName = "TransCode";
				break;
			case 1:
				strFieldName = "ReportNo";
				break;
			case 2:
				strFieldName = "BankCode";
				break;
			case 3:
				strFieldName = "BankBranch";
				break;
			case 4:
				strFieldName = "BankNode";
				break;
			case 5:
				strFieldName = "BankOperator";
				break;
			case 6:
				strFieldName = "TransNo";
				break;
			case 7:
				strFieldName = "FuncFlag";
				break;
			case 8:
				strFieldName = "TransDate";
				break;
			case 9:
				strFieldName = "TransTime";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "RiskCode";
				break;
			case 12:
				strFieldName = "ProposalNo";
				break;
			case 13:
				strFieldName = "PrtNo";
				break;
			case 14:
				strFieldName = "PolNo";
				break;
			case 15:
				strFieldName = "EdorNo";
				break;
			case 16:
				strFieldName = "TempFeeNo";
				break;
			case 17:
				strFieldName = "TransAmnt";
				break;
			case 18:
				strFieldName = "BankAcc";
				break;
			case 19:
				strFieldName = "RCode";
				break;
			case 20:
				strFieldName = "TransStatus";
				break;
			case 21:
				strFieldName = "Status";
				break;
			case 22:
				strFieldName = "Descr";
				break;
			case 23:
				strFieldName = "Temp";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
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
		if( strFieldName.equals("TransCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReportNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankBranch") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TransTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TempFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BankAcc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransStatus") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Status") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Descr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 24:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
