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
import com.sinosoft.lis.db.AskPriceRadioDB;

/*
 * <p>ClassName: AskPriceRadioSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: Quotation System
 * @CreateDate：2010-09-04
 */
public class AskPriceRadioSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(AskPriceRadioSchema.class);

	// @Field
	/** 询价编码 */
	private String AskPriceNo;
	/** 询价版本 */
	private String AskNo;
	/** 险种编码 */
	private String RiskCode;
	/** 代理手续费比例 */
	private double ChargesRatio;
	/** 备注 */
	private String Remarks;
	/** 属性1 */
	private String Attribute1;
	/** 属性2 */
	private String Attribute2;
	/** 属性3 */
	private Date Attribute3;
	/** 属性4 */
	private Date Attribute4;
	/** 属性5 */
	private double Attribute5;
	/** 属性6 */
	private double Attribute6;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 手续费比例 */
	private double RewardRatio;
	/** 代理人编码 */
	private String AgentCode;
	/** 代理人编码1 */
	private String AgentCode1;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AskPriceRadioSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "AskPriceNo";
		pk[1] = "AskNo";
		pk[2] = "RiskCode";

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
                AskPriceRadioSchema cloned = (AskPriceRadioSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAskPriceNo()
	{
		return AskPriceNo;
	}
	public void setAskPriceNo(String aAskPriceNo)
	{
		AskPriceNo = aAskPriceNo;
	}
	public String getAskNo()
	{
		return AskNo;
	}
	public void setAskNo(String aAskNo)
	{
		AskNo = aAskNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public double getChargesRatio()
	{
		return ChargesRatio;
	}
	public void setChargesRatio(double aChargesRatio)
	{
		ChargesRatio = aChargesRatio;
	}
	public void setChargesRatio(String aChargesRatio)
	{
		if (aChargesRatio != null && !aChargesRatio.equals(""))
		{
			Double tDouble = new Double(aChargesRatio);
			double d = tDouble.doubleValue();
			ChargesRatio = d;
		}
	}

	public String getRemarks()
	{
		return Remarks;
	}
	public void setRemarks(String aRemarks)
	{
		Remarks = aRemarks;
	}
	public String getAttribute1()
	{
		return Attribute1;
	}
	public void setAttribute1(String aAttribute1)
	{
		Attribute1 = aAttribute1;
	}
	public String getAttribute2()
	{
		return Attribute2;
	}
	public void setAttribute2(String aAttribute2)
	{
		Attribute2 = aAttribute2;
	}
	public String getAttribute3()
	{
		if( Attribute3 != null )
			return fDate.getString(Attribute3);
		else
			return null;
	}
	public void setAttribute3(Date aAttribute3)
	{
		Attribute3 = aAttribute3;
	}
	public void setAttribute3(String aAttribute3)
	{
		if (aAttribute3 != null && !aAttribute3.equals("") )
		{
			Attribute3 = fDate.getDate( aAttribute3 );
		}
		else
			Attribute3 = null;
	}

	public String getAttribute4()
	{
		if( Attribute4 != null )
			return fDate.getString(Attribute4);
		else
			return null;
	}
	public void setAttribute4(Date aAttribute4)
	{
		Attribute4 = aAttribute4;
	}
	public void setAttribute4(String aAttribute4)
	{
		if (aAttribute4 != null && !aAttribute4.equals("") )
		{
			Attribute4 = fDate.getDate( aAttribute4 );
		}
		else
			Attribute4 = null;
	}

	public double getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(double aAttribute5)
	{
		Attribute5 = aAttribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		if (aAttribute5 != null && !aAttribute5.equals(""))
		{
			Double tDouble = new Double(aAttribute5);
			double d = tDouble.doubleValue();
			Attribute5 = d;
		}
	}

	public double getAttribute6()
	{
		return Attribute6;
	}
	public void setAttribute6(double aAttribute6)
	{
		Attribute6 = aAttribute6;
	}
	public void setAttribute6(String aAttribute6)
	{
		if (aAttribute6 != null && !aAttribute6.equals(""))
		{
			Double tDouble = new Double(aAttribute6);
			double d = tDouble.doubleValue();
			Attribute6 = d;
		}
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
	public double getRewardRatio()
	{
		return RewardRatio;
	}
	public void setRewardRatio(double aRewardRatio)
	{
		RewardRatio = aRewardRatio;
	}
	public void setRewardRatio(String aRewardRatio)
	{
		if (aRewardRatio != null && !aRewardRatio.equals(""))
		{
			Double tDouble = new Double(aRewardRatio);
			double d = tDouble.doubleValue();
			RewardRatio = d;
		}
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentCode1()
	{
		return AgentCode1;
	}
	public void setAgentCode1(String aAgentCode1)
	{
		AgentCode1 = aAgentCode1;
	}

	/**
	* 使用另外一个 AskPriceRadioSchema 对象给 Schema 赋值
	* @param: aAskPriceRadioSchema AskPriceRadioSchema
	**/
	public void setSchema(AskPriceRadioSchema aAskPriceRadioSchema)
	{
		this.AskPriceNo = aAskPriceRadioSchema.getAskPriceNo();
		this.AskNo = aAskPriceRadioSchema.getAskNo();
		this.RiskCode = aAskPriceRadioSchema.getRiskCode();
		this.ChargesRatio = aAskPriceRadioSchema.getChargesRatio();
		this.Remarks = aAskPriceRadioSchema.getRemarks();
		this.Attribute1 = aAskPriceRadioSchema.getAttribute1();
		this.Attribute2 = aAskPriceRadioSchema.getAttribute2();
		this.Attribute3 = fDate.getDate( aAskPriceRadioSchema.getAttribute3());
		this.Attribute4 = fDate.getDate( aAskPriceRadioSchema.getAttribute4());
		this.Attribute5 = aAskPriceRadioSchema.getAttribute5();
		this.Attribute6 = aAskPriceRadioSchema.getAttribute6();
		this.Operator = aAskPriceRadioSchema.getOperator();
		this.MakeDate = fDate.getDate( aAskPriceRadioSchema.getMakeDate());
		this.MakeTime = aAskPriceRadioSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aAskPriceRadioSchema.getModifyDate());
		this.ModifyTime = aAskPriceRadioSchema.getModifyTime();
		this.RewardRatio = aAskPriceRadioSchema.getRewardRatio();
		this.AgentCode = aAskPriceRadioSchema.getAgentCode();
		this.AgentCode1 = aAskPriceRadioSchema.getAgentCode1();
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
			if( rs.getString("AskPriceNo") == null )
				this.AskPriceNo = null;
			else
				this.AskPriceNo = rs.getString("AskPriceNo").trim();

			if( rs.getString("AskNo") == null )
				this.AskNo = null;
			else
				this.AskNo = rs.getString("AskNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			this.ChargesRatio = rs.getDouble("ChargesRatio");
			if( rs.getString("Remarks") == null )
				this.Remarks = null;
			else
				this.Remarks = rs.getString("Remarks").trim();

			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("Attribute2") == null )
				this.Attribute2 = null;
			else
				this.Attribute2 = rs.getString("Attribute2").trim();

			this.Attribute3 = rs.getDate("Attribute3");
			this.Attribute4 = rs.getDate("Attribute4");
			this.Attribute5 = rs.getDouble("Attribute5");
			this.Attribute6 = rs.getDouble("Attribute6");
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

			this.RewardRatio = rs.getDouble("RewardRatio");
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentCode1") == null )
				this.AgentCode1 = null;
			else
				this.AgentCode1 = rs.getString("AgentCode1").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的AskPriceRadio表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AskPriceRadioSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public AskPriceRadioSchema getSchema()
	{
		AskPriceRadioSchema aAskPriceRadioSchema = new AskPriceRadioSchema();
		aAskPriceRadioSchema.setSchema(this);
		return aAskPriceRadioSchema;
	}

	public AskPriceRadioDB getDB()
	{
		AskPriceRadioDB aDBOper = new AskPriceRadioDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAskPriceRadio描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(AskPriceNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AskNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ChargesRatio));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remarks)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( Attribute3 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( Attribute4 ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(Attribute5));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(Attribute6));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(RewardRatio));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AgentCode1));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAskPriceRadio>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AskPriceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ChargesRatio = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			Remarks = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Attribute2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Attribute3 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			Attribute4 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Attribute5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			Attribute6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			RewardRatio = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentCode1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AskPriceRadioSchema";
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
		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskPriceNo));
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AskNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ChargesRatio"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargesRatio));
		}
		if (FCode.equalsIgnoreCase("Remarks"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remarks));
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute1));
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute2));
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAttribute3()));
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAttribute4()));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute6));
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
		if (FCode.equalsIgnoreCase("RewardRatio"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RewardRatio));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode1));
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
				strFieldValue = StrTool.GBKToUnicode(AskPriceNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AskNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = String.valueOf(ChargesRatio);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Remarks);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Attribute2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAttribute3()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAttribute4()));
				break;
			case 9:
				strFieldValue = String.valueOf(Attribute5);
				break;
			case 10:
				strFieldValue = String.valueOf(Attribute6);
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
			case 16:
				strFieldValue = String.valueOf(RewardRatio);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentCode1);
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

		if (FCode.equalsIgnoreCase("AskPriceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskPriceNo = FValue.trim();
			}
			else
				AskPriceNo = null;
		}
		if (FCode.equalsIgnoreCase("AskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AskNo = FValue.trim();
			}
			else
				AskNo = null;
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
		if (FCode.equalsIgnoreCase("ChargesRatio"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChargesRatio = d;
			}
		}
		if (FCode.equalsIgnoreCase("Remarks"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remarks = FValue.trim();
			}
			else
				Remarks = null;
		}
		if (FCode.equalsIgnoreCase("Attribute1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute1 = FValue.trim();
			}
			else
				Attribute1 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute2 = FValue.trim();
			}
			else
				Attribute2 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute3"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Attribute3 = fDate.getDate( FValue );
			}
			else
				Attribute3 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				Attribute4 = fDate.getDate( FValue );
			}
			else
				Attribute4 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Attribute5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Attribute6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Attribute6 = d;
			}
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
		if (FCode.equalsIgnoreCase("RewardRatio"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RewardRatio = d;
			}
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode1 = FValue.trim();
			}
			else
				AgentCode1 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		AskPriceRadioSchema other = (AskPriceRadioSchema)otherObject;
		return
			AskPriceNo.equals(other.getAskPriceNo())
			&& AskNo.equals(other.getAskNo())
			&& RiskCode.equals(other.getRiskCode())
			&& ChargesRatio == other.getChargesRatio()
			&& Remarks.equals(other.getRemarks())
			&& Attribute1.equals(other.getAttribute1())
			&& Attribute2.equals(other.getAttribute2())
			&& fDate.getString(Attribute3).equals(other.getAttribute3())
			&& fDate.getString(Attribute4).equals(other.getAttribute4())
			&& Attribute5 == other.getAttribute5()
			&& Attribute6 == other.getAttribute6()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& RewardRatio == other.getRewardRatio()
			&& AgentCode.equals(other.getAgentCode())
			&& AgentCode1.equals(other.getAgentCode1());
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
		if( strFieldName.equals("AskPriceNo") ) {
			return 0;
		}
		if( strFieldName.equals("AskNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("ChargesRatio") ) {
			return 3;
		}
		if( strFieldName.equals("Remarks") ) {
			return 4;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 5;
		}
		if( strFieldName.equals("Attribute2") ) {
			return 6;
		}
		if( strFieldName.equals("Attribute3") ) {
			return 7;
		}
		if( strFieldName.equals("Attribute4") ) {
			return 8;
		}
		if( strFieldName.equals("Attribute5") ) {
			return 9;
		}
		if( strFieldName.equals("Attribute6") ) {
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
		if( strFieldName.equals("RewardRatio") ) {
			return 16;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 17;
		}
		if( strFieldName.equals("AgentCode1") ) {
			return 18;
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
				strFieldName = "AskPriceNo";
				break;
			case 1:
				strFieldName = "AskNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "ChargesRatio";
				break;
			case 4:
				strFieldName = "Remarks";
				break;
			case 5:
				strFieldName = "Attribute1";
				break;
			case 6:
				strFieldName = "Attribute2";
				break;
			case 7:
				strFieldName = "Attribute3";
				break;
			case 8:
				strFieldName = "Attribute4";
				break;
			case 9:
				strFieldName = "Attribute5";
				break;
			case 10:
				strFieldName = "Attribute6";
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
			case 16:
				strFieldName = "RewardRatio";
				break;
			case 17:
				strFieldName = "AgentCode";
				break;
			case 18:
				strFieldName = "AgentCode1";
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
		if( strFieldName.equals("AskPriceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargesRatio") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remarks") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute3") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Attribute4") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Attribute5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Attribute6") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("RewardRatio") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode1") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
