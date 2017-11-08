

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
import com.sinosoft.lis.db.RIBsnsBillDetailsBakeDB;

/*
 * <p>ClassName: RIBsnsBillDetailsBakeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBsnsBillDetailsBakeSchema implements Schema, Cloneable
{
	// @Field
	/** 备份序号 */
	private String BackupNo;
	/** 批次号 */
	private String BatchNo;
	/** 账单编号 */
	private String BillNo;
	/** 币别 */
	private String Currency;
	/** 费用编码 */
	private String FeeCode;
	/** 费用名称 */
	private String FeeName;
	/** 金额 */
	private double Summoney;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作人 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 备用字段1 */
	private String StandbyString1;
	/** 备用字段2 */
	private String StandbyString2;
	/** 备用字段3 */
	private String StandbyString3;
	/** 信息项 */
	private String BillItem;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIBsnsBillDetailsBakeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "BackupNo";
		pk[1] = "BatchNo";
		pk[2] = "BillNo";
		pk[3] = "Currency";
		pk[4] = "FeeCode";

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
		RIBsnsBillDetailsBakeSchema cloned = (RIBsnsBillDetailsBakeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBackupNo()
	{
		return BackupNo;
	}
	public void setBackupNo(String aBackupNo)
	{
		BackupNo = aBackupNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getBillNo()
	{
		return BillNo;
	}
	public void setBillNo(String aBillNo)
	{
		BillNo = aBillNo;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}
	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
	}
	public String getFeeName()
	{
		return FeeName;
	}
	public void setFeeName(String aFeeName)
	{
		FeeName = aFeeName;
	}
	/**
	* 如果是账单项类型是01-费用项则记录金额
	*/
	public double getSummoney()
	{
		return Summoney;
	}
	public void setSummoney(double aSummoney)
	{
		Summoney = aSummoney;
	}
	public void setSummoney(String aSummoney)
	{
		if (aSummoney != null && !aSummoney.equals(""))
		{
			Double tDouble = new Double(aSummoney);
			double d = tDouble.doubleValue();
			Summoney = d;
		}
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getStandbyString1()
	{
		return StandbyString1;
	}
	public void setStandbyString1(String aStandbyString1)
	{
		StandbyString1 = aStandbyString1;
	}
	public String getStandbyString2()
	{
		return StandbyString2;
	}
	public void setStandbyString2(String aStandbyString2)
	{
		StandbyString2 = aStandbyString2;
	}
	public String getStandbyString3()
	{
		return StandbyString3;
	}
	public void setStandbyString3(String aStandbyString3)
	{
		StandbyString3 = aStandbyString3;
	}
	/**
	* 如果账单项类型02-信息项则记录信息项
	*/
	public String getBillItem()
	{
		return BillItem;
	}
	public void setBillItem(String aBillItem)
	{
		BillItem = aBillItem;
	}

	/**
	* 使用另外一个 RIBsnsBillDetailsBakeSchema 对象给 Schema 赋值
	* @param: aRIBsnsBillDetailsBakeSchema RIBsnsBillDetailsBakeSchema
	**/
	public void setSchema(RIBsnsBillDetailsBakeSchema aRIBsnsBillDetailsBakeSchema)
	{
		this.BackupNo = aRIBsnsBillDetailsBakeSchema.getBackupNo();
		this.BatchNo = aRIBsnsBillDetailsBakeSchema.getBatchNo();
		this.BillNo = aRIBsnsBillDetailsBakeSchema.getBillNo();
		this.Currency = aRIBsnsBillDetailsBakeSchema.getCurrency();
		this.FeeCode = aRIBsnsBillDetailsBakeSchema.getFeeCode();
		this.FeeName = aRIBsnsBillDetailsBakeSchema.getFeeName();
		this.Summoney = aRIBsnsBillDetailsBakeSchema.getSummoney();
		this.MakeDate = fDate.getDate( aRIBsnsBillDetailsBakeSchema.getMakeDate());
		this.MakeTime = aRIBsnsBillDetailsBakeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aRIBsnsBillDetailsBakeSchema.getModifyDate());
		this.ModifyTime = aRIBsnsBillDetailsBakeSchema.getModifyTime();
		this.Operator = aRIBsnsBillDetailsBakeSchema.getOperator();
		this.ManageCom = aRIBsnsBillDetailsBakeSchema.getManageCom();
		this.StandbyString1 = aRIBsnsBillDetailsBakeSchema.getStandbyString1();
		this.StandbyString2 = aRIBsnsBillDetailsBakeSchema.getStandbyString2();
		this.StandbyString3 = aRIBsnsBillDetailsBakeSchema.getStandbyString3();
		this.BillItem = aRIBsnsBillDetailsBakeSchema.getBillItem();
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
			if( rs.getString("BackupNo") == null )
				this.BackupNo = null;
			else
				this.BackupNo = rs.getString("BackupNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("BillNo") == null )
				this.BillNo = null;
			else
				this.BillNo = rs.getString("BillNo").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("FeeName") == null )
				this.FeeName = null;
			else
				this.FeeName = rs.getString("FeeName").trim();

			this.Summoney = rs.getDouble("Summoney");
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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("StandbyString1") == null )
				this.StandbyString1 = null;
			else
				this.StandbyString1 = rs.getString("StandbyString1").trim();

			if( rs.getString("StandbyString2") == null )
				this.StandbyString2 = null;
			else
				this.StandbyString2 = rs.getString("StandbyString2").trim();

			if( rs.getString("StandbyString3") == null )
				this.StandbyString3 = null;
			else
				this.StandbyString3 = rs.getString("StandbyString3").trim();

			if( rs.getString("BillItem") == null )
				this.BillItem = null;
			else
				this.BillItem = rs.getString("BillItem").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIBsnsBillDetailsBake表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			sqle.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillDetailsBakeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIBsnsBillDetailsBakeSchema getSchema()
	{
		RIBsnsBillDetailsBakeSchema aRIBsnsBillDetailsBakeSchema = new RIBsnsBillDetailsBakeSchema();
		aRIBsnsBillDetailsBakeSchema.setSchema(this);
		return aRIBsnsBillDetailsBakeSchema;
	}

	public RIBsnsBillDetailsBakeDB getDB()
	{
		RIBsnsBillDetailsBakeDB aDBOper = new RIBsnsBillDetailsBakeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillDetailsBake描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BackupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Summoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillItem));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillDetailsBake>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BackupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Summoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			BillItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillDetailsBakeSchema";
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
		if (FCode.equalsIgnoreCase("BackupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BackupNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillNo));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeName));
		}
		if (FCode.equalsIgnoreCase("Summoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Summoney));
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString1));
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString2));
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyString3));
		}
		if (FCode.equalsIgnoreCase("BillItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillItem));
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
				strFieldValue = StrTool.GBKToUnicode(BackupNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FeeName);
				break;
			case 6:
				strFieldValue = String.valueOf(Summoney);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(BillItem);
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

		if (FCode.equalsIgnoreCase("BackupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BackupNo = FValue.trim();
			}
			else
				BackupNo = null;
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatchNo = FValue.trim();
			}
			else
				BatchNo = null;
		}
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillNo = FValue.trim();
			}
			else
				BillNo = null;
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeName = FValue.trim();
			}
			else
				FeeName = null;
		}
		if (FCode.equalsIgnoreCase("Summoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Summoney = d;
			}
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("StandbyString1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString1 = FValue.trim();
			}
			else
				StandbyString1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString2 = FValue.trim();
			}
			else
				StandbyString2 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyString3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyString3 = FValue.trim();
			}
			else
				StandbyString3 = null;
		}
		if (FCode.equalsIgnoreCase("BillItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillItem = FValue.trim();
			}
			else
				BillItem = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIBsnsBillDetailsBakeSchema other = (RIBsnsBillDetailsBakeSchema)otherObject;
		return
			BackupNo.equals(other.getBackupNo())
			&& BatchNo.equals(other.getBatchNo())
			&& BillNo.equals(other.getBillNo())
			&& Currency.equals(other.getCurrency())
			&& FeeCode.equals(other.getFeeCode())
			&& FeeName.equals(other.getFeeName())
			&& Summoney == other.getSummoney()
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3())
			&& BillItem.equals(other.getBillItem());
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
		if( strFieldName.equals("BackupNo") ) {
			return 0;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 1;
		}
		if( strFieldName.equals("BillNo") ) {
			return 2;
		}
		if( strFieldName.equals("Currency") ) {
			return 3;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 4;
		}
		if( strFieldName.equals("FeeName") ) {
			return 5;
		}
		if( strFieldName.equals("Summoney") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return 13;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 14;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 15;
		}
		if( strFieldName.equals("BillItem") ) {
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
				strFieldName = "BackupNo";
				break;
			case 1:
				strFieldName = "BatchNo";
				break;
			case 2:
				strFieldName = "BillNo";
				break;
			case 3:
				strFieldName = "Currency";
				break;
			case 4:
				strFieldName = "FeeCode";
				break;
			case 5:
				strFieldName = "FeeName";
				break;
			case 6:
				strFieldName = "Summoney";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "StandbyString1";
				break;
			case 14:
				strFieldName = "StandbyString2";
				break;
			case 15:
				strFieldName = "StandbyString3";
				break;
			case 16:
				strFieldName = "BillItem";
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
		if( strFieldName.equals("BackupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Summoney") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillItem") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
