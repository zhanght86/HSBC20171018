

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RIAnwserIdeaDB;

/*
 * <p>ClassName: RIAnwserIdeaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIAnwserIdeaSchema implements Schema, Cloneable
{
	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 总单投保单号码 */
	private String ProposalGrpContNo;
	/** 投保单号码 */
	private String ProposalContNo;
	/** 审核类型 */
	private String AuditType;
	/** 审核主码 */
	private String AuditCode;
	/** 审核附码 */
	private String AuditAffixCode;
	/** 再保审核次数 */
	private int UWNo;
	/** 上载附件路径 */
	private String AdjunctPath;
	/** 再保操作员 */
	private String ReInsurer;
	/** 再保意见 */
	private String UWIdea;
	/** 管理机构 */
	private String ManageCom;
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

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIAnwserIdeaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "UWNo";

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
		RIAnwserIdeaSchema cloned = (RIAnwserIdeaSchema)super.clone();
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
		SerialNo = aSerialNo;
	}
	public String getProposalGrpContNo()
	{
		return ProposalGrpContNo;
	}
	public void setProposalGrpContNo(String aProposalGrpContNo)
	{
		ProposalGrpContNo = aProposalGrpContNo;
	}
	/**
	* 团单-000000<p>
	* 个单-ProposalContNo，或可以唯一标识个人保单的号码组合
	*/
	public String getProposalContNo()
	{
		return ProposalContNo;
	}
	public void setProposalContNo(String aProposalContNo)
	{
		ProposalContNo = aProposalContNo;
	}
	/**
	* 01-新单数据 02-续期数据 03-保全数据 04-理赔数据
	*/
	public String getAuditType()
	{
		return AuditType;
	}
	public void setAuditType(String aAuditType)
	{
		AuditType = aAuditType;
	}
	public String getAuditCode()
	{
		return AuditCode;
	}
	public void setAuditCode(String aAuditCode)
	{
		AuditCode = aAuditCode;
	}
	public String getAuditAffixCode()
	{
		return AuditAffixCode;
	}
	public void setAuditAffixCode(String aAuditAffixCode)
	{
		AuditAffixCode = aAuditAffixCode;
	}
	/**
	* 险种发送给再保部门的次数。<p>
	* <p>
	* （再保是由核保部门发起）
	*/
	public int getUWNo()
	{
		return UWNo;
	}
	public void setUWNo(int aUWNo)
	{
		UWNo = aUWNo;
	}
	public void setUWNo(String aUWNo)
	{
		if (aUWNo != null && !aUWNo.equals(""))
		{
			Integer tInteger = new Integer(aUWNo);
			int i = tInteger.intValue();
			UWNo = i;
		}
	}

	public String getAdjunctPath()
	{
		return AdjunctPath;
	}
	public void setAdjunctPath(String aAdjunctPath)
	{
		AdjunctPath = aAdjunctPath;
	}
	public String getReInsurer()
	{
		return ReInsurer;
	}
	public void setReInsurer(String aReInsurer)
	{
		ReInsurer = aReInsurer;
	}
	public String getUWIdea()
	{
		return UWIdea;
	}
	public void setUWIdea(String aUWIdea)
	{
		UWIdea = aUWIdea;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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

	/**
	* 使用另外一个 RIAnwserIdeaSchema 对象给 Schema 赋值
	* @param: aRIAnwserIdeaSchema RIAnwserIdeaSchema
	**/
	public void setSchema(RIAnwserIdeaSchema aRIAnwserIdeaSchema)
	{
		this.SerialNo = aRIAnwserIdeaSchema.getSerialNo();
		this.ProposalGrpContNo = aRIAnwserIdeaSchema.getProposalGrpContNo();
		this.ProposalContNo = aRIAnwserIdeaSchema.getProposalContNo();
		this.AuditType = aRIAnwserIdeaSchema.getAuditType();
		this.AuditCode = aRIAnwserIdeaSchema.getAuditCode();
		this.AuditAffixCode = aRIAnwserIdeaSchema.getAuditAffixCode();
		this.UWNo = aRIAnwserIdeaSchema.getUWNo();
		this.AdjunctPath = aRIAnwserIdeaSchema.getAdjunctPath();
		this.ReInsurer = aRIAnwserIdeaSchema.getReInsurer();
		this.UWIdea = aRIAnwserIdeaSchema.getUWIdea();
		this.ManageCom = aRIAnwserIdeaSchema.getManageCom();
		this.Operator = aRIAnwserIdeaSchema.getOperator();
		this.MakeDate = fDate.getDate( aRIAnwserIdeaSchema.getMakeDate());
		this.MakeTime = aRIAnwserIdeaSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aRIAnwserIdeaSchema.getModifyDate());
		this.ModifyTime = aRIAnwserIdeaSchema.getModifyTime();
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

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("ProposalContNo") == null )
				this.ProposalContNo = null;
			else
				this.ProposalContNo = rs.getString("ProposalContNo").trim();

			if( rs.getString("AuditType") == null )
				this.AuditType = null;
			else
				this.AuditType = rs.getString("AuditType").trim();

			if( rs.getString("AuditCode") == null )
				this.AuditCode = null;
			else
				this.AuditCode = rs.getString("AuditCode").trim();

			if( rs.getString("AuditAffixCode") == null )
				this.AuditAffixCode = null;
			else
				this.AuditAffixCode = rs.getString("AuditAffixCode").trim();

			this.UWNo = rs.getInt("UWNo");
			if( rs.getString("AdjunctPath") == null )
				this.AdjunctPath = null;
			else
				this.AdjunctPath = rs.getString("AdjunctPath").trim();

			if( rs.getString("ReInsurer") == null )
				this.ReInsurer = null;
			else
				this.ReInsurer = rs.getString("ReInsurer").trim();

			if( rs.getString("UWIdea") == null )
				this.UWIdea = null;
			else
				this.UWIdea = rs.getString("UWIdea").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIAnwserIdea表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAnwserIdeaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIAnwserIdeaSchema getSchema()
	{
		RIAnwserIdeaSchema aRIAnwserIdeaSchema = new RIAnwserIdeaSchema();
		aRIAnwserIdeaSchema.setSchema(this);
		return aRIAnwserIdeaSchema;
	}

	public RIAnwserIdeaDB getDB()
	{
		RIAnwserIdeaDB aDBOper = new RIAnwserIdeaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAnwserIdea描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AuditAffixCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjunctPath)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReInsurer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWIdea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIAnwserIdea>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ProposalContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AuditType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AuditCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AuditAffixCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			AdjunctPath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ReInsurer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UWIdea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIAnwserIdeaSchema";
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalContNo));
		}
		if (FCode.equalsIgnoreCase("AuditType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditType));
		}
		if (FCode.equalsIgnoreCase("AuditCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditCode));
		}
		if (FCode.equalsIgnoreCase("AuditAffixCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AuditAffixCode));
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWNo));
		}
		if (FCode.equalsIgnoreCase("AdjunctPath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjunctPath));
		}
		if (FCode.equalsIgnoreCase("ReInsurer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReInsurer));
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWIdea));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ProposalContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AuditType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AuditCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AuditAffixCode);
				break;
			case 6:
				strFieldValue = String.valueOf(UWNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AdjunctPath);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ReInsurer);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UWIdea);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
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
		if (FCode.equalsIgnoreCase("AuditType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditType = FValue.trim();
			}
			else
				AuditType = null;
		}
		if (FCode.equalsIgnoreCase("AuditCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditCode = FValue.trim();
			}
			else
				AuditCode = null;
		}
		if (FCode.equalsIgnoreCase("AuditAffixCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AuditAffixCode = FValue.trim();
			}
			else
				AuditAffixCode = null;
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("AdjunctPath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjunctPath = FValue.trim();
			}
			else
				AdjunctPath = null;
		}
		if (FCode.equalsIgnoreCase("ReInsurer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReInsurer = FValue.trim();
			}
			else
				ReInsurer = null;
		}
		if (FCode.equalsIgnoreCase("UWIdea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWIdea = FValue.trim();
			}
			else
				UWIdea = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIAnwserIdeaSchema other = (RIAnwserIdeaSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& ProposalContNo.equals(other.getProposalContNo())
			&& AuditType.equals(other.getAuditType())
			&& AuditCode.equals(other.getAuditCode())
			&& AuditAffixCode.equals(other.getAuditAffixCode())
			&& UWNo == other.getUWNo()
			&& AdjunctPath.equals(other.getAdjunctPath())
			&& ReInsurer.equals(other.getReInsurer())
			&& UWIdea.equals(other.getUWIdea())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return 2;
		}
		if( strFieldName.equals("AuditType") ) {
			return 3;
		}
		if( strFieldName.equals("AuditCode") ) {
			return 4;
		}
		if( strFieldName.equals("AuditAffixCode") ) {
			return 5;
		}
		if( strFieldName.equals("UWNo") ) {
			return 6;
		}
		if( strFieldName.equals("AdjunctPath") ) {
			return 7;
		}
		if( strFieldName.equals("ReInsurer") ) {
			return 8;
		}
		if( strFieldName.equals("UWIdea") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
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
				strFieldName = "ProposalGrpContNo";
				break;
			case 2:
				strFieldName = "ProposalContNo";
				break;
			case 3:
				strFieldName = "AuditType";
				break;
			case 4:
				strFieldName = "AuditCode";
				break;
			case 5:
				strFieldName = "AuditAffixCode";
				break;
			case 6:
				strFieldName = "UWNo";
				break;
			case 7:
				strFieldName = "AdjunctPath";
				break;
			case 8:
				strFieldName = "ReInsurer";
				break;
			case 9:
				strFieldName = "UWIdea";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
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
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AuditAffixCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AdjunctPath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReInsurer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWIdea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
