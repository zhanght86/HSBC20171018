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
import com.sinosoft.lis.db.LDRiskRuleBDB;

/*
 * <p>ClassName: LDRiskRuleBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDRiskRuleBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDRiskRuleBSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 险种编码 */
	private String RiskCode;
	/** 销售渠道 */
	private String SaleChnl;
	/** 机构组编码 */
	private String ComGroup;
	/** 投保起期 */
	private Date StartPolApplyDate;
	/** 投保止期 */
	private Date EndPolApplyDate;
	/** 扫描起始日期 */
	private Date StartScanDate;
	/** 扫描起始时间 */
	private String StartScanTime;
	/** 扫描终止日期 */
	private Date EndScanDate;
	/** 扫描终止时间 */
	private String EndScanTime;
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
	/** 备份日期 */
	private Date BakDate;
	/** 备份时间 */
	private String BakTime;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDRiskRuleBSchema()
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
		LDRiskRuleBSchema cloned = (LDRiskRuleBSchema)super.clone();
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
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		SaleChnl = aSaleChnl;
	}
	public String getComGroup()
	{
		return ComGroup;
	}
	public void setComGroup(String aComGroup)
	{
		ComGroup = aComGroup;
	}
	public String getStartPolApplyDate()
	{
		if( StartPolApplyDate != null )
			return fDate.getString(StartPolApplyDate);
		else
			return null;
	}
	public void setStartPolApplyDate(Date aStartPolApplyDate)
	{
		StartPolApplyDate = aStartPolApplyDate;
	}
	public void setStartPolApplyDate(String aStartPolApplyDate)
	{
		if (aStartPolApplyDate != null && !aStartPolApplyDate.equals("") )
		{
			StartPolApplyDate = fDate.getDate( aStartPolApplyDate );
		}
		else
			StartPolApplyDate = null;
	}

	public String getEndPolApplyDate()
	{
		if( EndPolApplyDate != null )
			return fDate.getString(EndPolApplyDate);
		else
			return null;
	}
	public void setEndPolApplyDate(Date aEndPolApplyDate)
	{
		EndPolApplyDate = aEndPolApplyDate;
	}
	public void setEndPolApplyDate(String aEndPolApplyDate)
	{
		if (aEndPolApplyDate != null && !aEndPolApplyDate.equals("") )
		{
			EndPolApplyDate = fDate.getDate( aEndPolApplyDate );
		}
		else
			EndPolApplyDate = null;
	}

	public String getStartScanDate()
	{
		if( StartScanDate != null )
			return fDate.getString(StartScanDate);
		else
			return null;
	}
	public void setStartScanDate(Date aStartScanDate)
	{
		StartScanDate = aStartScanDate;
	}
	public void setStartScanDate(String aStartScanDate)
	{
		if (aStartScanDate != null && !aStartScanDate.equals("") )
		{
			StartScanDate = fDate.getDate( aStartScanDate );
		}
		else
			StartScanDate = null;
	}

	public String getStartScanTime()
	{
		return StartScanTime;
	}
	public void setStartScanTime(String aStartScanTime)
	{
		StartScanTime = aStartScanTime;
	}
	public String getEndScanDate()
	{
		if( EndScanDate != null )
			return fDate.getString(EndScanDate);
		else
			return null;
	}
	public void setEndScanDate(Date aEndScanDate)
	{
		EndScanDate = aEndScanDate;
	}
	public void setEndScanDate(String aEndScanDate)
	{
		if (aEndScanDate != null && !aEndScanDate.equals("") )
		{
			EndScanDate = fDate.getDate( aEndScanDate );
		}
		else
			EndScanDate = null;
	}

	public String getEndScanTime()
	{
		return EndScanTime;
	}
	public void setEndScanTime(String aEndScanTime)
	{
		EndScanTime = aEndScanTime;
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
	public String getBakDate()
	{
		if( BakDate != null )
			return fDate.getString(BakDate);
		else
			return null;
	}
	public void setBakDate(Date aBakDate)
	{
		BakDate = aBakDate;
	}
	public void setBakDate(String aBakDate)
	{
		if (aBakDate != null && !aBakDate.equals("") )
		{
			BakDate = fDate.getDate( aBakDate );
		}
		else
			BakDate = null;
	}

	public String getBakTime()
	{
		return BakTime;
	}
	public void setBakTime(String aBakTime)
	{
		BakTime = aBakTime;
	}

	/**
	* 使用另外一个 LDRiskRuleBSchema 对象给 Schema 赋值
	* @param: aLDRiskRuleBSchema LDRiskRuleBSchema
	**/
	public void setSchema(LDRiskRuleBSchema aLDRiskRuleBSchema)
	{
		this.SerialNo = aLDRiskRuleBSchema.getSerialNo();
		this.RiskCode = aLDRiskRuleBSchema.getRiskCode();
		this.SaleChnl = aLDRiskRuleBSchema.getSaleChnl();
		this.ComGroup = aLDRiskRuleBSchema.getComGroup();
		this.StartPolApplyDate = fDate.getDate( aLDRiskRuleBSchema.getStartPolApplyDate());
		this.EndPolApplyDate = fDate.getDate( aLDRiskRuleBSchema.getEndPolApplyDate());
		this.StartScanDate = fDate.getDate( aLDRiskRuleBSchema.getStartScanDate());
		this.StartScanTime = aLDRiskRuleBSchema.getStartScanTime();
		this.EndScanDate = fDate.getDate( aLDRiskRuleBSchema.getEndScanDate());
		this.EndScanTime = aLDRiskRuleBSchema.getEndScanTime();
		this.Operator = aLDRiskRuleBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDRiskRuleBSchema.getMakeDate());
		this.MakeTime = aLDRiskRuleBSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDRiskRuleBSchema.getModifyDate());
		this.ModifyTime = aLDRiskRuleBSchema.getModifyTime();
		this.BakDate = fDate.getDate( aLDRiskRuleBSchema.getBakDate());
		this.BakTime = aLDRiskRuleBSchema.getBakTime();
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

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			if( rs.getString("ComGroup") == null )
				this.ComGroup = null;
			else
				this.ComGroup = rs.getString("ComGroup").trim();

			this.StartPolApplyDate = rs.getDate("StartPolApplyDate");
			this.EndPolApplyDate = rs.getDate("EndPolApplyDate");
			this.StartScanDate = rs.getDate("StartScanDate");
			if( rs.getString("StartScanTime") == null )
				this.StartScanTime = null;
			else
				this.StartScanTime = rs.getString("StartScanTime").trim();

			this.EndScanDate = rs.getDate("EndScanDate");
			if( rs.getString("EndScanTime") == null )
				this.EndScanTime = null;
			else
				this.EndScanTime = rs.getString("EndScanTime").trim();

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

			this.BakDate = rs.getDate("BakDate");
			if( rs.getString("BakTime") == null )
				this.BakTime = null;
			else
				this.BakTime = rs.getString("BakTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDRiskRuleB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDRiskRuleBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDRiskRuleBSchema getSchema()
	{
		LDRiskRuleBSchema aLDRiskRuleBSchema = new LDRiskRuleBSchema();
		aLDRiskRuleBSchema.setSchema(this);
		return aLDRiskRuleBSchema;
	}

	public LDRiskRuleBDB getDB()
	{
		LDRiskRuleBDB aDBOper = new LDRiskRuleBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDRiskRuleB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartPolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndPolApplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartScanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartScanTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndScanDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndScanTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BakDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BakTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDRiskRuleB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ComGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartPolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			EndPolApplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			StartScanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			StartScanTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			EndScanDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			EndScanTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			BakDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			BakTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDRiskRuleBSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("ComGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComGroup));
		}
		if (FCode.equalsIgnoreCase("StartPolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("EndPolApplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndPolApplyDate()));
		}
		if (FCode.equalsIgnoreCase("StartScanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartScanDate()));
		}
		if (FCode.equalsIgnoreCase("StartScanTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartScanTime));
		}
		if (FCode.equalsIgnoreCase("EndScanDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndScanDate()));
		}
		if (FCode.equalsIgnoreCase("EndScanTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndScanTime));
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
		if (FCode.equalsIgnoreCase("BakDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBakDate()));
		}
		if (FCode.equalsIgnoreCase("BakTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BakTime));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ComGroup);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartPolApplyDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndPolApplyDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartScanDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StartScanTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndScanDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(EndScanTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBakDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BakTime);
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("ComGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComGroup = FValue.trim();
			}
			else
				ComGroup = null;
		}
		if (FCode.equalsIgnoreCase("StartPolApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartPolApplyDate = fDate.getDate( FValue );
			}
			else
				StartPolApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("EndPolApplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndPolApplyDate = fDate.getDate( FValue );
			}
			else
				EndPolApplyDate = null;
		}
		if (FCode.equalsIgnoreCase("StartScanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartScanDate = fDate.getDate( FValue );
			}
			else
				StartScanDate = null;
		}
		if (FCode.equalsIgnoreCase("StartScanTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartScanTime = FValue.trim();
			}
			else
				StartScanTime = null;
		}
		if (FCode.equalsIgnoreCase("EndScanDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndScanDate = fDate.getDate( FValue );
			}
			else
				EndScanDate = null;
		}
		if (FCode.equalsIgnoreCase("EndScanTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndScanTime = FValue.trim();
			}
			else
				EndScanTime = null;
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
		if (FCode.equalsIgnoreCase("BakDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BakDate = fDate.getDate( FValue );
			}
			else
				BakDate = null;
		}
		if (FCode.equalsIgnoreCase("BakTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BakTime = FValue.trim();
			}
			else
				BakTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDRiskRuleBSchema other = (LDRiskRuleBSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& RiskCode.equals(other.getRiskCode())
			&& SaleChnl.equals(other.getSaleChnl())
			&& ComGroup.equals(other.getComGroup())
			&& fDate.getString(StartPolApplyDate).equals(other.getStartPolApplyDate())
			&& fDate.getString(EndPolApplyDate).equals(other.getEndPolApplyDate())
			&& fDate.getString(StartScanDate).equals(other.getStartScanDate())
			&& StartScanTime.equals(other.getStartScanTime())
			&& fDate.getString(EndScanDate).equals(other.getEndScanDate())
			&& EndScanTime.equals(other.getEndScanTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(BakDate).equals(other.getBakDate())
			&& BakTime.equals(other.getBakTime());
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
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 2;
		}
		if( strFieldName.equals("ComGroup") ) {
			return 3;
		}
		if( strFieldName.equals("StartPolApplyDate") ) {
			return 4;
		}
		if( strFieldName.equals("EndPolApplyDate") ) {
			return 5;
		}
		if( strFieldName.equals("StartScanDate") ) {
			return 6;
		}
		if( strFieldName.equals("StartScanTime") ) {
			return 7;
		}
		if( strFieldName.equals("EndScanDate") ) {
			return 8;
		}
		if( strFieldName.equals("EndScanTime") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
		}
		if( strFieldName.equals("BakDate") ) {
			return 15;
		}
		if( strFieldName.equals("BakTime") ) {
			return 16;
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
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "SaleChnl";
				break;
			case 3:
				strFieldName = "ComGroup";
				break;
			case 4:
				strFieldName = "StartPolApplyDate";
				break;
			case 5:
				strFieldName = "EndPolApplyDate";
				break;
			case 6:
				strFieldName = "StartScanDate";
				break;
			case 7:
				strFieldName = "StartScanTime";
				break;
			case 8:
				strFieldName = "EndScanDate";
				break;
			case 9:
				strFieldName = "EndScanTime";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
				strFieldName = "ModifyTime";
				break;
			case 15:
				strFieldName = "BakDate";
				break;
			case 16:
				strFieldName = "BakTime";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartPolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndPolApplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartScanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartScanTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndScanDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndScanTime") ) {
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
		if( strFieldName.equals("BakDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BakTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
