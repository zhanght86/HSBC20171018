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
import com.sinosoft.lis.db.LLUWSpecSubDB;

/*
 * <p>ClassName: LLUWSpecSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLUWSpecSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLUWSpecSubSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 批次号 */
	private String BatNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 合同号码 */
	private String ContNo;
	/** 投保单号码 */
	private String ProposalContNo;
	/** 打印流水号 */
	private String PrtSeq;
	/** 流水号 */
	private String SerialNo;
	/** 批单号码 */
	private String EndorsementNo;
	/** 特约类型 */
	private String SpecType;
	/** 特约编码 */
	private String SpecCode;
	/** 特约内容 */
	private String SpecContent;
	/** 打印标记 */
	private String PrtFlag;
	/** 备份类型 */
	private String BackupType;
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
	/** 客户号 */
	private String CustomerNo;
	/** 特约原因 */
	private String SpecReason;
	/** 下发标记 */
	private String NeedPrint;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLUWSpecSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "BatNo";
		pk[2] = "ProposalContNo";
		pk[3] = "SerialNo";

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
		LLUWSpecSubSchema cloned = (LLUWSpecSubSchema)super.clone();
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
		ClmNo = aClmNo;
	}
	public String getBatNo()
	{
		return BatNo;
	}
	public void setBatNo(String aBatNo)
	{
		BatNo = aBatNo;
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
	public String getPrtSeq()
	{
		return PrtSeq;
	}
	public void setPrtSeq(String aPrtSeq)
	{
		PrtSeq = aPrtSeq;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getEndorsementNo()
	{
		return EndorsementNo;
	}
	public void setEndorsementNo(String aEndorsementNo)
	{
		EndorsementNo = aEndorsementNo;
	}
	public String getSpecType()
	{
		return SpecType;
	}
	public void setSpecType(String aSpecType)
	{
		SpecType = aSpecType;
	}
	public String getSpecCode()
	{
		return SpecCode;
	}
	public void setSpecCode(String aSpecCode)
	{
		SpecCode = aSpecCode;
	}
	public String getSpecContent()
	{
		return SpecContent;
	}
	public void setSpecContent(String aSpecContent)
	{
		SpecContent = aSpecContent;
	}
	/**
	* 0 --与保单同打<p>
	* 1 --分别打印
	*/
	public String getPrtFlag()
	{
		return PrtFlag;
	}
	public void setPrtFlag(String aPrtFlag)
	{
		PrtFlag = aPrtFlag;
	}
	public String getBackupType()
	{
		return BackupType;
	}
	public void setBackupType(String aBackupType)
	{
		BackupType = aBackupType;
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
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getSpecReason()
	{
		return SpecReason;
	}
	public void setSpecReason(String aSpecReason)
	{
		SpecReason = aSpecReason;
	}
	public String getNeedPrint()
	{
		return NeedPrint;
	}
	public void setNeedPrint(String aNeedPrint)
	{
		NeedPrint = aNeedPrint;
	}

	/**
	* 使用另外一个 LLUWSpecSubSchema 对象给 Schema 赋值
	* @param: aLLUWSpecSubSchema LLUWSpecSubSchema
	**/
	public void setSchema(LLUWSpecSubSchema aLLUWSpecSubSchema)
	{
		this.ClmNo = aLLUWSpecSubSchema.getClmNo();
		this.BatNo = aLLUWSpecSubSchema.getBatNo();
		this.GrpContNo = aLLUWSpecSubSchema.getGrpContNo();
		this.ContNo = aLLUWSpecSubSchema.getContNo();
		this.ProposalContNo = aLLUWSpecSubSchema.getProposalContNo();
		this.PrtSeq = aLLUWSpecSubSchema.getPrtSeq();
		this.SerialNo = aLLUWSpecSubSchema.getSerialNo();
		this.EndorsementNo = aLLUWSpecSubSchema.getEndorsementNo();
		this.SpecType = aLLUWSpecSubSchema.getSpecType();
		this.SpecCode = aLLUWSpecSubSchema.getSpecCode();
		this.SpecContent = aLLUWSpecSubSchema.getSpecContent();
		this.PrtFlag = aLLUWSpecSubSchema.getPrtFlag();
		this.BackupType = aLLUWSpecSubSchema.getBackupType();
		this.Operator = aLLUWSpecSubSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLUWSpecSubSchema.getMakeDate());
		this.MakeTime = aLLUWSpecSubSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLUWSpecSubSchema.getModifyDate());
		this.ModifyTime = aLLUWSpecSubSchema.getModifyTime();
		this.CustomerNo = aLLUWSpecSubSchema.getCustomerNo();
		this.SpecReason = aLLUWSpecSubSchema.getSpecReason();
		this.NeedPrint = aLLUWSpecSubSchema.getNeedPrint();
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

			if( rs.getString("BatNo") == null )
				this.BatNo = null;
			else
				this.BatNo = rs.getString("BatNo").trim();

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

			if( rs.getString("PrtSeq") == null )
				this.PrtSeq = null;
			else
				this.PrtSeq = rs.getString("PrtSeq").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("EndorsementNo") == null )
				this.EndorsementNo = null;
			else
				this.EndorsementNo = rs.getString("EndorsementNo").trim();

			if( rs.getString("SpecType") == null )
				this.SpecType = null;
			else
				this.SpecType = rs.getString("SpecType").trim();

			if( rs.getString("SpecCode") == null )
				this.SpecCode = null;
			else
				this.SpecCode = rs.getString("SpecCode").trim();

			if( rs.getString("SpecContent") == null )
				this.SpecContent = null;
			else
				this.SpecContent = rs.getString("SpecContent").trim();

			if( rs.getString("PrtFlag") == null )
				this.PrtFlag = null;
			else
				this.PrtFlag = rs.getString("PrtFlag").trim();

			if( rs.getString("BackupType") == null )
				this.BackupType = null;
			else
				this.BackupType = rs.getString("BackupType").trim();

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

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("SpecReason") == null )
				this.SpecReason = null;
			else
				this.SpecReason = rs.getString("SpecReason").trim();

			if( rs.getString("NeedPrint") == null )
				this.NeedPrint = null;
			else
				this.NeedPrint = rs.getString("NeedPrint").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLUWSpecSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWSpecSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLUWSpecSubSchema getSchema()
	{
		LLUWSpecSubSchema aLLUWSpecSubSchema = new LLUWSpecSubSchema();
		aLLUWSpecSubSchema.setSchema(this);
		return aLLUWSpecSubSchema;
	}

	public LLUWSpecSubDB getDB()
	{
		LLUWSpecSubDB aDBOper = new LLUWSpecSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWSpecSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndorsementNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BackupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpecReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedPrint));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLUWSpecSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EndorsementNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			SpecType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			SpecCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SpecContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			PrtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BackupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			SpecReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			NeedPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLUWSpecSubSchema";
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
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatNo));
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
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtSeq));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("EndorsementNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndorsementNo));
		}
		if (FCode.equalsIgnoreCase("SpecType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecType));
		}
		if (FCode.equalsIgnoreCase("SpecCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecCode));
		}
		if (FCode.equalsIgnoreCase("SpecContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecContent));
		}
		if (FCode.equalsIgnoreCase("PrtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtFlag));
		}
		if (FCode.equalsIgnoreCase("BackupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackupType));
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("SpecReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpecReason));
		}
		if (FCode.equalsIgnoreCase("NeedPrint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedPrint));
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
				strFieldValue = StrTool.GBKToUnicode(BatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtSeq);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(EndorsementNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(SpecType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SpecCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SpecContent);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(PrtFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BackupType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(SpecReason);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(NeedPrint);
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
		if (FCode.equalsIgnoreCase("BatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatNo = FValue.trim();
			}
			else
				BatNo = null;
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
		if (FCode.equalsIgnoreCase("PrtSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtSeq = FValue.trim();
			}
			else
				PrtSeq = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("EndorsementNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndorsementNo = FValue.trim();
			}
			else
				EndorsementNo = null;
		}
		if (FCode.equalsIgnoreCase("SpecType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecType = FValue.trim();
			}
			else
				SpecType = null;
		}
		if (FCode.equalsIgnoreCase("SpecCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecCode = FValue.trim();
			}
			else
				SpecCode = null;
		}
		if (FCode.equalsIgnoreCase("SpecContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecContent = FValue.trim();
			}
			else
				SpecContent = null;
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
		if (FCode.equalsIgnoreCase("BackupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackupType = FValue.trim();
			}
			else
				BackupType = null;
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("SpecReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpecReason = FValue.trim();
			}
			else
				SpecReason = null;
		}
		if (FCode.equalsIgnoreCase("NeedPrint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedPrint = FValue.trim();
			}
			else
				NeedPrint = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLUWSpecSubSchema other = (LLUWSpecSubSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& BatNo.equals(other.getBatNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& PrtSeq.equals(other.getPrtSeq())
			&& SerialNo.equals(other.getSerialNo())
			&& EndorsementNo.equals(other.getEndorsementNo())
			&& SpecType.equals(other.getSpecType())
			&& SpecCode.equals(other.getSpecCode())
			&& SpecContent.equals(other.getSpecContent())
			&& PrtFlag.equals(other.getPrtFlag())
			&& BackupType.equals(other.getBackupType())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& CustomerNo.equals(other.getCustomerNo())
			&& SpecReason.equals(other.getSpecReason())
			&& NeedPrint.equals(other.getNeedPrint());
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
		if( strFieldName.equals("BatNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ContNo") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 4;
		}
		if( strFieldName.equals("PrtSeq") ) {
			return 5;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 6;
		}
		if( strFieldName.equals("EndorsementNo") ) {
			return 7;
		}
		if( strFieldName.equals("SpecType") ) {
			return 8;
		}
		if( strFieldName.equals("SpecCode") ) {
			return 9;
		}
		if( strFieldName.equals("SpecContent") ) {
			return 10;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return 11;
		}
		if( strFieldName.equals("BackupType") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 18;
		}
		if( strFieldName.equals("SpecReason") ) {
			return 19;
		}
		if( strFieldName.equals("NeedPrint") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "BatNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ContNo";
				break;
			case 4:
				strFieldName = "ProposalContNo";
				break;
			case 5:
				strFieldName = "PrtSeq";
				break;
			case 6:
				strFieldName = "SerialNo";
				break;
			case 7:
				strFieldName = "EndorsementNo";
				break;
			case 8:
				strFieldName = "SpecType";
				break;
			case 9:
				strFieldName = "SpecCode";
				break;
			case 10:
				strFieldName = "SpecContent";
				break;
			case 11:
				strFieldName = "PrtFlag";
				break;
			case 12:
				strFieldName = "BackupType";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "CustomerNo";
				break;
			case 19:
				strFieldName = "SpecReason";
				break;
			case 20:
				strFieldName = "NeedPrint";
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
		if( strFieldName.equals("BatNo") ) {
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
		if( strFieldName.equals("PrtSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndorsementNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecContent") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BackupType") ) {
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpecReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedPrint") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
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
