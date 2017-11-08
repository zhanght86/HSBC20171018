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
import com.sinosoft.lis.db.LXbalanceSubDB;

/*
 * <p>ClassName: LXbalanceSubSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 兼业系统
 */
public class LXbalanceSubSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LXbalanceSubSchema.class);

	// @Field
	/** 结算号 */
	private String BalanceNo;
	/** 卡单类型 */
	private String CardType;
	/** 起始号 */
	private String StartNo;
	/** 终止号 */
	private String EndNo;
	/** 数量 */
	private int Count;
	/** 单价 */
	private double UnitPrice;
	/** 是否协议标志 */
	private String ProFlag;
	/** 浮动费率 */
	private double FloatRate;
	/** 代理人编码 */
	private String AgentCode;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构 */
	private String AgentCom;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;
	/** 备用属性字段3 */
	private String StandbyFlag3;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LXbalanceSubSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BalanceNo";
		pk[1] = "CardType";
		pk[2] = "StartNo";

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
		LXbalanceSubSchema cloned = (LXbalanceSubSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBalanceNo()
	{
		return BalanceNo;
	}
	public void setBalanceNo(String aBalanceNo)
	{
		BalanceNo = aBalanceNo;
	}
	public String getCardType()
	{
		return CardType;
	}
	public void setCardType(String aCardType)
	{
		CardType = aCardType;
	}
	public String getStartNo()
	{
		return StartNo;
	}
	public void setStartNo(String aStartNo)
	{
		StartNo = aStartNo;
	}
	public String getEndNo()
	{
		return EndNo;
	}
	public void setEndNo(String aEndNo)
	{
		EndNo = aEndNo;
	}
	public int getCount()
	{
		return Count;
	}
	public void setCount(int aCount)
	{
		Count = aCount;
	}
	public void setCount(String aCount)
	{
		if (aCount != null && !aCount.equals(""))
		{
			Integer tInteger = new Integer(aCount);
			int i = tInteger.intValue();
			Count = i;
		}
	}

	public double getUnitPrice()
	{
		return UnitPrice;
	}
	public void setUnitPrice(double aUnitPrice)
	{
		UnitPrice = aUnitPrice;
	}
	public void setUnitPrice(String aUnitPrice)
	{
		if (aUnitPrice != null && !aUnitPrice.equals(""))
		{
			Double tDouble = new Double(aUnitPrice);
			double d = tDouble.doubleValue();
			UnitPrice = d;
		}
	}

	/**
	* 0--否<p>
	* 1--是
	*/
	public String getProFlag()
	{
		return ProFlag;
	}
	public void setProFlag(String aProFlag)
	{
		ProFlag = aProFlag;
	}
	public double getFloatRate()
	{
		return FloatRate;
	}
	public void setFloatRate(double aFloatRate)
	{
		FloatRate = aFloatRate;
	}
	public void setFloatRate(String aFloatRate)
	{
		if (aFloatRate != null && !aFloatRate.equals(""))
		{
			Double tDouble = new Double(aFloatRate);
			double d = tDouble.doubleValue();
			FloatRate = d;
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}
	public String getStandbyFlag3()
	{
		return StandbyFlag3;
	}
	public void setStandbyFlag3(String aStandbyFlag3)
	{
		StandbyFlag3 = aStandbyFlag3;
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
	* 使用另外一个 LXbalanceSubSchema 对象给 Schema 赋值
	* @param: aLXbalanceSubSchema LXbalanceSubSchema
	**/
	public void setSchema(LXbalanceSubSchema aLXbalanceSubSchema)
	{
		this.BalanceNo = aLXbalanceSubSchema.getBalanceNo();
		this.CardType = aLXbalanceSubSchema.getCardType();
		this.StartNo = aLXbalanceSubSchema.getStartNo();
		this.EndNo = aLXbalanceSubSchema.getEndNo();
		this.Count = aLXbalanceSubSchema.getCount();
		this.UnitPrice = aLXbalanceSubSchema.getUnitPrice();
		this.ProFlag = aLXbalanceSubSchema.getProFlag();
		this.FloatRate = aLXbalanceSubSchema.getFloatRate();
		this.AgentCode = aLXbalanceSubSchema.getAgentCode();
		this.ManageCom = aLXbalanceSubSchema.getManageCom();
		this.AgentCom = aLXbalanceSubSchema.getAgentCom();
		this.StandbyFlag1 = aLXbalanceSubSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLXbalanceSubSchema.getStandbyFlag2();
		this.StandbyFlag3 = aLXbalanceSubSchema.getStandbyFlag3();
		this.Operator = aLXbalanceSubSchema.getOperator();
		this.MakeDate = fDate.getDate( aLXbalanceSubSchema.getMakeDate());
		this.MakeTime = aLXbalanceSubSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLXbalanceSubSchema.getModifyDate());
		this.ModifyTime = aLXbalanceSubSchema.getModifyTime();
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
			if( rs.getString("BalanceNo") == null )
				this.BalanceNo = null;
			else
				this.BalanceNo = rs.getString("BalanceNo").trim();

			if( rs.getString("CardType") == null )
				this.CardType = null;
			else
				this.CardType = rs.getString("CardType").trim();

			if( rs.getString("StartNo") == null )
				this.StartNo = null;
			else
				this.StartNo = rs.getString("StartNo").trim();

			if( rs.getString("EndNo") == null )
				this.EndNo = null;
			else
				this.EndNo = rs.getString("EndNo").trim();

			this.Count = rs.getInt("Count");
			this.UnitPrice = rs.getDouble("UnitPrice");
			if( rs.getString("ProFlag") == null )
				this.ProFlag = null;
			else
				this.ProFlag = rs.getString("ProFlag").trim();

			this.FloatRate = rs.getDouble("FloatRate");
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

			if( rs.getString("StandbyFlag3") == null )
				this.StandbyFlag3 = null;
			else
				this.StandbyFlag3 = rs.getString("StandbyFlag3").trim();

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
			logger.debug("数据库中的LXbalanceSub表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LXbalanceSubSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LXbalanceSubSchema getSchema()
	{
		LXbalanceSubSchema aLXbalanceSubSchema = new LXbalanceSubSchema();
		aLXbalanceSubSchema.setSchema(this);
		return aLXbalanceSubSchema;
	}

	public LXbalanceSubDB getDB()
	{
		LXbalanceSubDB aDBOper = new LXbalanceSubDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLXbalanceSub描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BalanceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Count));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UnitPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FloatRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLXbalanceSub>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BalanceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CardType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			StartNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EndNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Count= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			UnitPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			ProFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FloatRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyFlag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LXbalanceSubSchema";
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
		if (FCode.equalsIgnoreCase("BalanceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceNo));
		}
		if (FCode.equalsIgnoreCase("CardType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardType));
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartNo));
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndNo));
		}
		if (FCode.equalsIgnoreCase("Count"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Count));
		}
		if (FCode.equalsIgnoreCase("UnitPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitPrice));
		}
		if (FCode.equalsIgnoreCase("ProFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProFlag));
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FloatRate));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag3));
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
				strFieldValue = StrTool.GBKToUnicode(BalanceNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CardType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(StartNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EndNo);
				break;
			case 4:
				strFieldValue = String.valueOf(Count);
				break;
			case 5:
				strFieldValue = String.valueOf(UnitPrice);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ProFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(FloatRate);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag3);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
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

		if (FCode.equalsIgnoreCase("BalanceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceNo = FValue.trim();
			}
			else
				BalanceNo = null;
		}
		if (FCode.equalsIgnoreCase("CardType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardType = FValue.trim();
			}
			else
				CardType = null;
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartNo = FValue.trim();
			}
			else
				StartNo = null;
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndNo = FValue.trim();
			}
			else
				EndNo = null;
		}
		if (FCode.equalsIgnoreCase("Count"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Count = i;
			}
		}
		if (FCode.equalsIgnoreCase("UnitPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				UnitPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("ProFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProFlag = FValue.trim();
			}
			else
				ProFlag = null;
		}
		if (FCode.equalsIgnoreCase("FloatRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FloatRate = d;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag3 = FValue.trim();
			}
			else
				StandbyFlag3 = null;
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
		LXbalanceSubSchema other = (LXbalanceSubSchema)otherObject;
		return
			BalanceNo.equals(other.getBalanceNo())
			&& CardType.equals(other.getCardType())
			&& StartNo.equals(other.getStartNo())
			&& EndNo.equals(other.getEndNo())
			&& Count == other.getCount()
			&& UnitPrice == other.getUnitPrice()
			&& ProFlag.equals(other.getProFlag())
			&& FloatRate == other.getFloatRate()
			&& AgentCode.equals(other.getAgentCode())
			&& ManageCom.equals(other.getManageCom())
			&& AgentCom.equals(other.getAgentCom())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& StandbyFlag3.equals(other.getStandbyFlag3())
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
		if( strFieldName.equals("BalanceNo") ) {
			return 0;
		}
		if( strFieldName.equals("CardType") ) {
			return 1;
		}
		if( strFieldName.equals("StartNo") ) {
			return 2;
		}
		if( strFieldName.equals("EndNo") ) {
			return 3;
		}
		if( strFieldName.equals("Count") ) {
			return 4;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return 5;
		}
		if( strFieldName.equals("ProFlag") ) {
			return 6;
		}
		if( strFieldName.equals("FloatRate") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 10;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 11;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
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
				strFieldName = "BalanceNo";
				break;
			case 1:
				strFieldName = "CardType";
				break;
			case 2:
				strFieldName = "StartNo";
				break;
			case 3:
				strFieldName = "EndNo";
				break;
			case 4:
				strFieldName = "Count";
				break;
			case 5:
				strFieldName = "UnitPrice";
				break;
			case 6:
				strFieldName = "ProFlag";
				break;
			case 7:
				strFieldName = "FloatRate";
				break;
			case 8:
				strFieldName = "AgentCode";
				break;
			case 9:
				strFieldName = "ManageCom";
				break;
			case 10:
				strFieldName = "AgentCom";
				break;
			case 11:
				strFieldName = "StandbyFlag1";
				break;
			case 12:
				strFieldName = "StandbyFlag2";
				break;
			case 13:
				strFieldName = "StandbyFlag3";
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
		if( strFieldName.equals("BalanceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Count") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("UnitPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ProFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FloatRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag3") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
