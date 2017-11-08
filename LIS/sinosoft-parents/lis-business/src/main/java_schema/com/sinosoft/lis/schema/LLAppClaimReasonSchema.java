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
import com.sinosoft.lis.db.LLAppClaimReasonDB;

/*
 * <p>ClassName: LLAppClaimReasonSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLAppClaimReasonSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAppClaimReasonSchema.class);
	// @Field
	/** 赔案号 */
	private String CaseNo;
	/** 申请登记号 */
	private String RgtNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 理赔类型 */
	private String ReasonCode;
	/** 类型 */
	private String ReasonType;
	/** 原因 */
	private String Reason;
	/** 材料齐备日期 */
	private Date AffixGetDate;
	/** 管理机构 */
	private String MngCom;
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
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAppClaimReasonSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "CaseNo";
		pk[1] = "RgtNo";
		pk[2] = "CustomerNo";
		pk[3] = "ReasonCode";

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
		LLAppClaimReasonSchema cloned = (LLAppClaimReasonSchema)super.clone();
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
	* 默认为是赔案号，暂时不用该字段，但保持原来设计
	*/
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("赔案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	/**
	* 如果是团体，可以理解为:团体申请受理号
	*/
	public String getRgtNo()
	{
		return RgtNo;
	}
	public void setRgtNo(String aRgtNo)
	{
		if(aRgtNo!=null && aRgtNo.length()>20)
			throw new IllegalArgumentException("申请登记号RgtNo值"+aRgtNo+"的长度"+aRgtNo.length()+"大于最大值20");
		RgtNo = aRgtNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	/**
	* 100  意外医疗<p>
	* 101  意外伤残<p>
	* 102  意外死亡<p>
	* 103  意外高残<p>
	* 104  意外大病<p>
	* 105  意外特种疾病<p>
	* 106  意外失业失能<p>
	* 109  意外豁免<p>
	* 200  疾病医疗<p>
	* 201  疾病伤残<p>
	* 202  疾病死亡<p>
	* 203  疾病高残<p>
	* 204  疾病大病<p>
	* 205  疾病特种疾病<p>
	* 206  疾病失业失能<p>
	* 209  疾病豁免
	*/
	public String getReasonCode()
	{
		return ReasonCode;
	}
	public void setReasonCode(String aReasonCode)
	{
		if(aReasonCode!=null && aReasonCode.length()>6)
			throw new IllegalArgumentException("理赔类型ReasonCode值"+aReasonCode+"的长度"+aReasonCode.length()+"大于最大值6");
		ReasonCode = aReasonCode;
	}
	/**
	* 【不用】<p>
	* 0 申请<p>
	* 1 申诉
	*/
	public String getReasonType()
	{
		return ReasonType;
	}
	public void setReasonType(String aReasonType)
	{
		if(aReasonType!=null && aReasonType.length()>6)
			throw new IllegalArgumentException("类型ReasonType值"+aReasonType+"的长度"+aReasonType.length()+"大于最大值6");
		ReasonType = aReasonType;
	}
	/**
	* 【不用】
	*/
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>200)
			throw new IllegalArgumentException("原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值200");
		Reason = aReason;
	}
	/**
	* 【不用】
	*/
	public String getAffixGetDate()
	{
		if( AffixGetDate != null )
			return fDate.getString(AffixGetDate);
		else
			return null;
	}
	public void setAffixGetDate(Date aAffixGetDate)
	{
		AffixGetDate = aAffixGetDate;
	}
	public void setAffixGetDate(String aAffixGetDate)
	{
		if (aAffixGetDate != null && !aAffixGetDate.equals("") )
		{
			AffixGetDate = fDate.getDate( aAffixGetDate );
		}
		else
			AffixGetDate = null;
	}

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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

	/**
	* 使用另外一个 LLAppClaimReasonSchema 对象给 Schema 赋值
	* @param: aLLAppClaimReasonSchema LLAppClaimReasonSchema
	**/
	public void setSchema(LLAppClaimReasonSchema aLLAppClaimReasonSchema)
	{
		this.CaseNo = aLLAppClaimReasonSchema.getCaseNo();
		this.RgtNo = aLLAppClaimReasonSchema.getRgtNo();
		this.CustomerNo = aLLAppClaimReasonSchema.getCustomerNo();
		this.ReasonCode = aLLAppClaimReasonSchema.getReasonCode();
		this.ReasonType = aLLAppClaimReasonSchema.getReasonType();
		this.Reason = aLLAppClaimReasonSchema.getReason();
		this.AffixGetDate = fDate.getDate( aLLAppClaimReasonSchema.getAffixGetDate());
		this.MngCom = aLLAppClaimReasonSchema.getMngCom();
		this.Operator = aLLAppClaimReasonSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLAppClaimReasonSchema.getMakeDate());
		this.MakeTime = aLLAppClaimReasonSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLAppClaimReasonSchema.getModifyDate());
		this.ModifyTime = aLLAppClaimReasonSchema.getModifyTime();
		this.ComCode = aLLAppClaimReasonSchema.getComCode();
		this.ModifyOperator = aLLAppClaimReasonSchema.getModifyOperator();
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
			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("RgtNo") == null )
				this.RgtNo = null;
			else
				this.RgtNo = rs.getString("RgtNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("ReasonCode") == null )
				this.ReasonCode = null;
			else
				this.ReasonCode = rs.getString("ReasonCode").trim();

			if( rs.getString("ReasonType") == null )
				this.ReasonType = null;
			else
				this.ReasonType = rs.getString("ReasonType").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			this.AffixGetDate = rs.getDate("AffixGetDate");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAppClaimReason表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppClaimReasonSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAppClaimReasonSchema getSchema()
	{
		LLAppClaimReasonSchema aLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
		aLLAppClaimReasonSchema.setSchema(this);
		return aLLAppClaimReasonSchema;
	}

	public LLAppClaimReasonDB getDB()
	{
		LLAppClaimReasonDB aDBOper = new LLAppClaimReasonDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppClaimReason描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RgtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AffixGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppClaimReason>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RgtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReasonType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AffixGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppClaimReasonSchema";
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
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RgtNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonCode));
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonType));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("AffixGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAffixGetDate()));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RgtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ReasonCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReasonType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAffixGetDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("RgtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RgtNo = FValue.trim();
			}
			else
				RgtNo = null;
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
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonCode = FValue.trim();
			}
			else
				ReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("ReasonType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonType = FValue.trim();
			}
			else
				ReasonType = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("AffixGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AffixGetDate = fDate.getDate( FValue );
			}
			else
				AffixGetDate = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAppClaimReasonSchema other = (LLAppClaimReasonSchema)otherObject;
		return
			CaseNo.equals(other.getCaseNo())
			&& RgtNo.equals(other.getRgtNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& ReasonCode.equals(other.getReasonCode())
			&& ReasonType.equals(other.getReasonType())
			&& Reason.equals(other.getReason())
			&& fDate.getString(AffixGetDate).equals(other.getAffixGetDate())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("CaseNo") ) {
			return 0;
		}
		if( strFieldName.equals("RgtNo") ) {
			return 1;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 2;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return 3;
		}
		if( strFieldName.equals("ReasonType") ) {
			return 4;
		}
		if( strFieldName.equals("Reason") ) {
			return 5;
		}
		if( strFieldName.equals("AffixGetDate") ) {
			return 6;
		}
		if( strFieldName.equals("MngCom") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("ComCode") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 14;
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
				strFieldName = "CaseNo";
				break;
			case 1:
				strFieldName = "RgtNo";
				break;
			case 2:
				strFieldName = "CustomerNo";
				break;
			case 3:
				strFieldName = "ReasonCode";
				break;
			case 4:
				strFieldName = "ReasonType";
				break;
			case 5:
				strFieldName = "Reason";
				break;
			case 6:
				strFieldName = "AffixGetDate";
				break;
			case 7:
				strFieldName = "MngCom";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "ComCode";
				break;
			case 14:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RgtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AffixGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
