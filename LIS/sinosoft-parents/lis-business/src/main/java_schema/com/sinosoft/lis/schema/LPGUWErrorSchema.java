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
import com.sinosoft.lis.db.LPGUWErrorDB;

/*
 * <p>ClassName: LPGUWErrorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全流程
 */
public class LPGUWErrorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPGUWErrorSchema.class);

	// @Field
	/** 批单号 */
	private String EdorNo;
	/** 批改类型 */
	private String EdorType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 总单投保单号码 */
	private String ProposalGrpContNo;
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体投保单险种号码 */
	private String GrpProposalNo;
	/** 流水号 */
	private String SerialNo;
	/** 核保次数 */
	private int UWNo;
	/** 单位编码 */
	private String GrpNo;
	/** 单位名称 */
	private String Name;
	/** 管理机构 */
	private String ManageCom;
	/** 核保规则编码 */
	private String UWRuleCode;
	/** 核保出错信息 */
	private String UWError;
	/** 当前值 */
	private String CurrValue;
	/** 核保可通过标记（分保） */
	private String UWPassFlag;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 核保级别 */
	private String UWGrade;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPGUWErrorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "EdorNo";
		pk[1] = "EdorType";
		pk[2] = "GrpPolNo";
		pk[3] = "SerialNo";
		pk[4] = "UWNo";

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
		LPGUWErrorSchema cloned = (LPGUWErrorSchema)super.clone();
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
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
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
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpProposalNo()
	{
		return GrpProposalNo;
	}
	public void setGrpProposalNo(String aGrpProposalNo)
	{
		GrpProposalNo = aGrpProposalNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
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

	public String getGrpNo()
	{
		return GrpNo;
	}
	public void setGrpNo(String aGrpNo)
	{
		GrpNo = aGrpNo;
	}
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getUWRuleCode()
	{
		return UWRuleCode;
	}
	public void setUWRuleCode(String aUWRuleCode)
	{
		UWRuleCode = aUWRuleCode;
	}
	public String getUWError()
	{
		return UWError;
	}
	public void setUWError(String aUWError)
	{
		UWError = aUWError;
	}
	public String getCurrValue()
	{
		return CurrValue;
	}
	public void setCurrValue(String aCurrValue)
	{
		CurrValue = aCurrValue;
	}
	public String getUWPassFlag()
	{
		return UWPassFlag;
	}
	public void setUWPassFlag(String aUWPassFlag)
	{
		UWPassFlag = aUWPassFlag;
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
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}

	/**
	* 使用另外一个 LPGUWErrorSchema 对象给 Schema 赋值
	* @param: aLPGUWErrorSchema LPGUWErrorSchema
	**/
	public void setSchema(LPGUWErrorSchema aLPGUWErrorSchema)
	{
		this.EdorNo = aLPGUWErrorSchema.getEdorNo();
		this.EdorType = aLPGUWErrorSchema.getEdorType();
		this.GrpContNo = aLPGUWErrorSchema.getGrpContNo();
		this.ProposalGrpContNo = aLPGUWErrorSchema.getProposalGrpContNo();
		this.GrpPolNo = aLPGUWErrorSchema.getGrpPolNo();
		this.GrpProposalNo = aLPGUWErrorSchema.getGrpProposalNo();
		this.SerialNo = aLPGUWErrorSchema.getSerialNo();
		this.UWNo = aLPGUWErrorSchema.getUWNo();
		this.GrpNo = aLPGUWErrorSchema.getGrpNo();
		this.Name = aLPGUWErrorSchema.getName();
		this.ManageCom = aLPGUWErrorSchema.getManageCom();
		this.UWRuleCode = aLPGUWErrorSchema.getUWRuleCode();
		this.UWError = aLPGUWErrorSchema.getUWError();
		this.CurrValue = aLPGUWErrorSchema.getCurrValue();
		this.UWPassFlag = aLPGUWErrorSchema.getUWPassFlag();
		this.ModifyDate = fDate.getDate( aLPGUWErrorSchema.getModifyDate());
		this.ModifyTime = aLPGUWErrorSchema.getModifyTime();
		this.UWGrade = aLPGUWErrorSchema.getUWGrade();
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

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ProposalGrpContNo") == null )
				this.ProposalGrpContNo = null;
			else
				this.ProposalGrpContNo = rs.getString("ProposalGrpContNo").trim();

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpProposalNo") == null )
				this.GrpProposalNo = null;
			else
				this.GrpProposalNo = rs.getString("GrpProposalNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.UWNo = rs.getInt("UWNo");
			if( rs.getString("GrpNo") == null )
				this.GrpNo = null;
			else
				this.GrpNo = rs.getString("GrpNo").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("UWRuleCode") == null )
				this.UWRuleCode = null;
			else
				this.UWRuleCode = rs.getString("UWRuleCode").trim();

			if( rs.getString("UWError") == null )
				this.UWError = null;
			else
				this.UWError = rs.getString("UWError").trim();

			if( rs.getString("CurrValue") == null )
				this.CurrValue = null;
			else
				this.CurrValue = rs.getString("CurrValue").trim();

			if( rs.getString("UWPassFlag") == null )
				this.UWPassFlag = null;
			else
				this.UWPassFlag = rs.getString("UWPassFlag").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LPGUWError表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGUWErrorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPGUWErrorSchema getSchema()
	{
		LPGUWErrorSchema aLPGUWErrorSchema = new LPGUWErrorSchema();
		aLPGUWErrorSchema.setSchema(this);
		return aLPGUWErrorSchema;
	}

	public LPGUWErrorDB getDB()
	{
		LPGUWErrorDB aDBOper = new LPGUWErrorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGUWError描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProposalGrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpProposalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWError)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CurrValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPGUWError>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ProposalGrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			GrpNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWError = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CurrValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			UWPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPGUWErrorSchema";
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalGrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpProposalNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWNo));
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpNo));
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("UWRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWRuleCode));
		}
		if (FCode.equalsIgnoreCase("UWError"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWError));
		}
		if (FCode.equalsIgnoreCase("CurrValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrValue));
		}
		if (FCode.equalsIgnoreCase("UWPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPassFlag));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
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
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ProposalGrpContNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpProposalNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 7:
				strFieldValue = String.valueOf(UWNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWRuleCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UWError);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CurrValue);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(UWPassFlag);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
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
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
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
		if (FCode.equalsIgnoreCase("ProposalGrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalGrpContNo = FValue.trim();
			}
			else
				ProposalGrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpProposalNo = FValue.trim();
			}
			else
				GrpProposalNo = null;
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
		if (FCode.equalsIgnoreCase("UWNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("GrpNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpNo = FValue.trim();
			}
			else
				GrpNo = null;
		}
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("UWRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWRuleCode = FValue.trim();
			}
			else
				UWRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("UWError"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWError = FValue.trim();
			}
			else
				UWError = null;
		}
		if (FCode.equalsIgnoreCase("CurrValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrValue = FValue.trim();
			}
			else
				CurrValue = null;
		}
		if (FCode.equalsIgnoreCase("UWPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPassFlag = FValue.trim();
			}
			else
				UWPassFlag = null;
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
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LPGUWErrorSchema other = (LPGUWErrorSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& EdorType.equals(other.getEdorType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ProposalGrpContNo.equals(other.getProposalGrpContNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& GrpProposalNo.equals(other.getGrpProposalNo())
			&& SerialNo.equals(other.getSerialNo())
			&& UWNo == other.getUWNo()
			&& GrpNo.equals(other.getGrpNo())
			&& Name.equals(other.getName())
			&& ManageCom.equals(other.getManageCom())
			&& UWRuleCode.equals(other.getUWRuleCode())
			&& UWError.equals(other.getUWError())
			&& CurrValue.equals(other.getCurrValue())
			&& UWPassFlag.equals(other.getUWPassFlag())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& UWGrade.equals(other.getUWGrade());
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
		if( strFieldName.equals("EdorType") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return 3;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return 4;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return 5;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 6;
		}
		if( strFieldName.equals("UWNo") ) {
			return 7;
		}
		if( strFieldName.equals("GrpNo") ) {
			return 8;
		}
		if( strFieldName.equals("Name") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("UWRuleCode") ) {
			return 11;
		}
		if( strFieldName.equals("UWError") ) {
			return 12;
		}
		if( strFieldName.equals("CurrValue") ) {
			return 13;
		}
		if( strFieldName.equals("UWPassFlag") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 16;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 17;
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
				strFieldName = "EdorType";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "ProposalGrpContNo";
				break;
			case 4:
				strFieldName = "GrpPolNo";
				break;
			case 5:
				strFieldName = "GrpProposalNo";
				break;
			case 6:
				strFieldName = "SerialNo";
				break;
			case 7:
				strFieldName = "UWNo";
				break;
			case 8:
				strFieldName = "GrpNo";
				break;
			case 9:
				strFieldName = "Name";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "UWRuleCode";
				break;
			case 12:
				strFieldName = "UWError";
				break;
			case 13:
				strFieldName = "CurrValue";
				break;
			case 14:
				strFieldName = "UWPassFlag";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
				strFieldName = "ModifyTime";
				break;
			case 17:
				strFieldName = "UWGrade";
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
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalGrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GrpNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWError") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CurrValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
