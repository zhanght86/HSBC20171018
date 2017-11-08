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
import com.sinosoft.lis.db.LKBalanceDetailDB;

/*
 * <p>ClassName: LKBalanceDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 银保通
 */
public class LKBalanceDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LKBalanceDetailSchema.class);

	// @Field
	/** 交易流水号 */
	private String TransrNo;
	/** 银行代码 */
	private String BankCode;
	/** 银行地区代码 */
	private String BankZoneCode;
	/** 银行网点代码 */
	private String BrNo;
	/** 柜员代码 */
	private String TellerNo;
	/** 处理标志 */
	private String FuncFlag;
	/** 单证号码 */
	private String CardNo;
	/** 投保人姓名 */
	private String AppntName;
	/** 交易日期 */
	private Date TranDate;
	/** 交易金额 */
	private double TranAmnt;
	/** 确认标志 */
	private String ConfirmFlag;
	/** 备用1 */
	private String Temp1;
	/** 备用2 */
	private String Temp2;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKBalanceDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "TransrNo";
		pk[1] = "BankCode";
		pk[2] = "BankZoneCode";
		pk[3] = "BrNo";

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
		LKBalanceDetailSchema cloned = (LKBalanceDetailSchema)super.clone();
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
	* 承保交易流水号
	*/
	public String getTransrNo()
	{
		return TransrNo;
	}
	public void setTransrNo(String aTransrNo)
	{
		TransrNo = aTransrNo;
	}
	public String getBankCode()
	{
		return BankCode;
	}
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	public String getBankZoneCode()
	{
		return BankZoneCode;
	}
	public void setBankZoneCode(String aBankZoneCode)
	{
		BankZoneCode = aBankZoneCode;
	}
	public String getBrNo()
	{
		return BrNo;
	}
	public void setBrNo(String aBrNo)
	{
		BrNo = aBrNo;
	}
	public String getTellerNo()
	{
		return TellerNo;
	}
	public void setTellerNo(String aTellerNo)
	{
		TellerNo = aTellerNo;
	}
	public String getFuncFlag()
	{
		return FuncFlag;
	}
	public void setFuncFlag(String aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	public String getCardNo()
	{
		return CardNo;
	}
	public void setCardNo(String aCardNo)
	{
		CardNo = aCardNo;
	}
	/**
	* 通过此字段与交易日志表中的reportno关联，可以找到对应的对账交易日志
	*/
	public String getAppntName()
	{
		return AppntName;
	}
	public void setAppntName(String aAppntName)
	{
		AppntName = aAppntName;
	}
	public String getTranDate()
	{
		if( TranDate != null )
			return fDate.getString(TranDate);
		else
			return null;
	}
	public void setTranDate(Date aTranDate)
	{
		TranDate = aTranDate;
	}
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals("") )
		{
			TranDate = fDate.getDate( aTranDate );
		}
		else
			TranDate = null;
	}

	public double getTranAmnt()
	{
		return TranAmnt;
	}
	public void setTranAmnt(double aTranAmnt)
	{
		TranAmnt = aTranAmnt;
	}
	public void setTranAmnt(String aTranAmnt)
	{
		if (aTranAmnt != null && !aTranAmnt.equals(""))
		{
			Double tDouble = new Double(aTranAmnt);
			double d = tDouble.doubleValue();
			TranAmnt = d;
		}
	}

	/**
	* 1 -- 包含在银行发送的对账明细文件中的记录<p>
	* 0 -- 不包含在银行发送的对账明细文件中的记录
	*/
	public String getConfirmFlag()
	{
		return ConfirmFlag;
	}
	public void setConfirmFlag(String aConfirmFlag)
	{
		ConfirmFlag = aConfirmFlag;
	}
	/**
	* 0 -- 对账失败的记录<p>
	* 1 -- 银行传来的未对账的交易<p>
	* 2 -- 系统中成功，而银行失败的交易
	*/
	public String getTemp1()
	{
		return Temp1;
	}
	public void setTemp1(String aTemp1)
	{
		Temp1 = aTemp1;
	}
	/**
	* 对账失败，此字段记录失败原因
	*/
	public String getTemp2()
	{
		return Temp2;
	}
	public void setTemp2(String aTemp2)
	{
		Temp2 = aTemp2;
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
	* 使用另外一个 LKBalanceDetailSchema 对象给 Schema 赋值
	* @param: aLKBalanceDetailSchema LKBalanceDetailSchema
	**/
	public void setSchema(LKBalanceDetailSchema aLKBalanceDetailSchema)
	{
		this.TransrNo = aLKBalanceDetailSchema.getTransrNo();
		this.BankCode = aLKBalanceDetailSchema.getBankCode();
		this.BankZoneCode = aLKBalanceDetailSchema.getBankZoneCode();
		this.BrNo = aLKBalanceDetailSchema.getBrNo();
		this.TellerNo = aLKBalanceDetailSchema.getTellerNo();
		this.FuncFlag = aLKBalanceDetailSchema.getFuncFlag();
		this.CardNo = aLKBalanceDetailSchema.getCardNo();
		this.AppntName = aLKBalanceDetailSchema.getAppntName();
		this.TranDate = fDate.getDate( aLKBalanceDetailSchema.getTranDate());
		this.TranAmnt = aLKBalanceDetailSchema.getTranAmnt();
		this.ConfirmFlag = aLKBalanceDetailSchema.getConfirmFlag();
		this.Temp1 = aLKBalanceDetailSchema.getTemp1();
		this.Temp2 = aLKBalanceDetailSchema.getTemp2();
		this.MakeDate = fDate.getDate( aLKBalanceDetailSchema.getMakeDate());
		this.MakeTime = aLKBalanceDetailSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLKBalanceDetailSchema.getModifyDate());
		this.ModifyTime = aLKBalanceDetailSchema.getModifyTime();
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
			if( rs.getString("TransrNo") == null )
				this.TransrNo = null;
			else
				this.TransrNo = rs.getString("TransrNo").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankZoneCode") == null )
				this.BankZoneCode = null;
			else
				this.BankZoneCode = rs.getString("BankZoneCode").trim();

			if( rs.getString("BrNo") == null )
				this.BrNo = null;
			else
				this.BrNo = rs.getString("BrNo").trim();

			if( rs.getString("TellerNo") == null )
				this.TellerNo = null;
			else
				this.TellerNo = rs.getString("TellerNo").trim();

			if( rs.getString("FuncFlag") == null )
				this.FuncFlag = null;
			else
				this.FuncFlag = rs.getString("FuncFlag").trim();

			if( rs.getString("CardNo") == null )
				this.CardNo = null;
			else
				this.CardNo = rs.getString("CardNo").trim();

			if( rs.getString("AppntName") == null )
				this.AppntName = null;
			else
				this.AppntName = rs.getString("AppntName").trim();

			this.TranDate = rs.getDate("TranDate");
			this.TranAmnt = rs.getDouble("TranAmnt");
			if( rs.getString("ConfirmFlag") == null )
				this.ConfirmFlag = null;
			else
				this.ConfirmFlag = rs.getString("ConfirmFlag").trim();

			if( rs.getString("Temp1") == null )
				this.Temp1 = null;
			else
				this.Temp1 = rs.getString("Temp1").trim();

			if( rs.getString("Temp2") == null )
				this.Temp2 = null;
			else
				this.Temp2 = rs.getString("Temp2").trim();

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
			logger.debug("数据库中的LKBalanceDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKBalanceDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LKBalanceDetailSchema getSchema()
	{
		LKBalanceDetailSchema aLKBalanceDetailSchema = new LKBalanceDetailSchema();
		aLKBalanceDetailSchema.setSchema(this);
		return aLKBalanceDetailSchema;
	}

	public LKBalanceDetailDB getDB()
	{
		LKBalanceDetailDB aDBOper = new LKBalanceDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKBalanceDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TransrNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BankZoneCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BrNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TellerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FuncFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CardNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppntName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TranDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TranAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Temp1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Temp2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKBalanceDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransrNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BankZoneCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BrNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FuncFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AppntName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TranDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			TranAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).doubleValue();
			ConfirmFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Temp1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Temp2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKBalanceDetailSchema";
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
		if (FCode.equalsIgnoreCase("TransrNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransrNo));
		}
		if (FCode.equalsIgnoreCase("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equalsIgnoreCase("BankZoneCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankZoneCode));
		}
		if (FCode.equalsIgnoreCase("BrNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BrNo));
		}
		if (FCode.equalsIgnoreCase("TellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerNo));
		}
		if (FCode.equalsIgnoreCase("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equalsIgnoreCase("CardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardNo));
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppntName));
		}
		if (FCode.equalsIgnoreCase("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
		}
		if (FCode.equalsIgnoreCase("TranAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranAmnt));
		}
		if (FCode.equalsIgnoreCase("ConfirmFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmFlag));
		}
		if (FCode.equalsIgnoreCase("Temp1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp1));
		}
		if (FCode.equalsIgnoreCase("Temp2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Temp2));
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
				strFieldValue = StrTool.GBKToUnicode(TransrNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BankZoneCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BrNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TellerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FuncFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CardNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AppntName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTranDate()));
				break;
			case 9:
				strFieldValue = String.valueOf(TranAmnt);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ConfirmFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Temp1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Temp2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 16:
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

		if (FCode.equalsIgnoreCase("TransrNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransrNo = FValue.trim();
			}
			else
				TransrNo = null;
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
		if (FCode.equalsIgnoreCase("BankZoneCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankZoneCode = FValue.trim();
			}
			else
				BankZoneCode = null;
		}
		if (FCode.equalsIgnoreCase("BrNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BrNo = FValue.trim();
			}
			else
				BrNo = null;
		}
		if (FCode.equalsIgnoreCase("TellerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerNo = FValue.trim();
			}
			else
				TellerNo = null;
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
		if (FCode.equalsIgnoreCase("CardNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardNo = FValue.trim();
			}
			else
				CardNo = null;
		}
		if (FCode.equalsIgnoreCase("AppntName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppntName = FValue.trim();
			}
			else
				AppntName = null;
		}
		if (FCode.equalsIgnoreCase("TranDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TranDate = fDate.getDate( FValue );
			}
			else
				TranDate = null;
		}
		if (FCode.equalsIgnoreCase("TranAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				TranAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("ConfirmFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmFlag = FValue.trim();
			}
			else
				ConfirmFlag = null;
		}
		if (FCode.equalsIgnoreCase("Temp1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp1 = FValue.trim();
			}
			else
				Temp1 = null;
		}
		if (FCode.equalsIgnoreCase("Temp2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Temp2 = FValue.trim();
			}
			else
				Temp2 = null;
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
		LKBalanceDetailSchema other = (LKBalanceDetailSchema)otherObject;
		return
			TransrNo.equals(other.getTransrNo())
			&& BankCode.equals(other.getBankCode())
			&& BankZoneCode.equals(other.getBankZoneCode())
			&& BrNo.equals(other.getBrNo())
			&& TellerNo.equals(other.getTellerNo())
			&& FuncFlag.equals(other.getFuncFlag())
			&& CardNo.equals(other.getCardNo())
			&& AppntName.equals(other.getAppntName())
			&& fDate.getString(TranDate).equals(other.getTranDate())
			&& TranAmnt == other.getTranAmnt()
			&& ConfirmFlag.equals(other.getConfirmFlag())
			&& Temp1.equals(other.getTemp1())
			&& Temp2.equals(other.getTemp2())
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
		if( strFieldName.equals("TransrNo") ) {
			return 0;
		}
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("BankZoneCode") ) {
			return 2;
		}
		if( strFieldName.equals("BrNo") ) {
			return 3;
		}
		if( strFieldName.equals("TellerNo") ) {
			return 4;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 5;
		}
		if( strFieldName.equals("CardNo") ) {
			return 6;
		}
		if( strFieldName.equals("AppntName") ) {
			return 7;
		}
		if( strFieldName.equals("TranDate") ) {
			return 8;
		}
		if( strFieldName.equals("TranAmnt") ) {
			return 9;
		}
		if( strFieldName.equals("ConfirmFlag") ) {
			return 10;
		}
		if( strFieldName.equals("Temp1") ) {
			return 11;
		}
		if( strFieldName.equals("Temp2") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "TransrNo";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "BankZoneCode";
				break;
			case 3:
				strFieldName = "BrNo";
				break;
			case 4:
				strFieldName = "TellerNo";
				break;
			case 5:
				strFieldName = "FuncFlag";
				break;
			case 6:
				strFieldName = "CardNo";
				break;
			case 7:
				strFieldName = "AppntName";
				break;
			case 8:
				strFieldName = "TranDate";
				break;
			case 9:
				strFieldName = "TranAmnt";
				break;
			case 10:
				strFieldName = "ConfirmFlag";
				break;
			case 11:
				strFieldName = "Temp1";
				break;
			case 12:
				strFieldName = "Temp2";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "MakeTime";
				break;
			case 15:
				strFieldName = "ModifyDate";
				break;
			case 16:
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
		if( strFieldName.equals("TransrNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankZoneCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BrNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppntName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("TranAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ConfirmFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Temp2") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
