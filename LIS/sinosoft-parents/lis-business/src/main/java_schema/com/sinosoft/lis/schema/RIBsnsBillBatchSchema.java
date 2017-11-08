

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
import com.sinosoft.lis.db.RIBsnsBillBatchDB;

/*
 * <p>ClassName: RIBsnsBillBatchSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBsnsBillBatchSchema implements Schema, Cloneable
{
	// @Field
	/** 批次号 */
	private String BatchNo;
	/** 账单编号 */
	private String BillNo;
	/** 币别 */
	private String Currency;
	/** 再保公司 */
	private String IncomeCompanyNo;
	/** 账单状态 */
	private String State;
	/** 合同编码 */
	private String RIContNo;
	/** 账单期次 */
	private String BillTimes;
	/** 起始日期 */
	private Date StartDate;
	/** 终止日期 */
	private Date EndDate;
	/** 审核人 */
	private String UWOperator;
	/** 审核意见 */
	private String Idea;
	/** 操作人 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;
	/** 创建日期 */
	private Date MakeDate;
	/** 创建时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 备用字段1 */
	private String StandbyString1;
	/** 备用字段2 */
	private String StandbyString2;
	/** 备用字段3 */
	private String StandbyString3;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIBsnsBillBatchSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "BatchNo";
		pk[1] = "BillNo";
		pk[2] = "Currency";

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
		RIBsnsBillBatchSchema cloned = (RIBsnsBillBatchSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getIncomeCompanyNo()
	{
		return IncomeCompanyNo;
	}
	public void setIncomeCompanyNo(String aIncomeCompanyNo)
	{
		IncomeCompanyNo = aIncomeCompanyNo;
	}
	/**
	* 01   账单调整<p>
	* 02   账单审核<p>
	* 03   审核确认<p>
	* 04   账单结算
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	/**
	* 月度账单：记录为201101/201102/201103/../201112<p>
	* 季度账单：记录为201101/201102/201103/201104
	*/
	public String getBillTimes()
	{
		return BillTimes;
	}
	public void setBillTimes(String aBillTimes)
	{
		BillTimes = aBillTimes;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
	}
	public String getIdea()
	{
		return Idea;
	}
	public void setIdea(String aIdea)
	{
		Idea = aIdea;
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
	* 使用另外一个 RIBsnsBillBatchSchema 对象给 Schema 赋值
	* @param: aRIBsnsBillBatchSchema RIBsnsBillBatchSchema
	**/
	public void setSchema(RIBsnsBillBatchSchema aRIBsnsBillBatchSchema)
	{
		this.BatchNo = aRIBsnsBillBatchSchema.getBatchNo();
		this.BillNo = aRIBsnsBillBatchSchema.getBillNo();
		this.Currency = aRIBsnsBillBatchSchema.getCurrency();
		this.IncomeCompanyNo = aRIBsnsBillBatchSchema.getIncomeCompanyNo();
		this.State = aRIBsnsBillBatchSchema.getState();
		this.RIContNo = aRIBsnsBillBatchSchema.getRIContNo();
		this.BillTimes = aRIBsnsBillBatchSchema.getBillTimes();
		this.StartDate = fDate.getDate( aRIBsnsBillBatchSchema.getStartDate());
		this.EndDate = fDate.getDate( aRIBsnsBillBatchSchema.getEndDate());
		this.UWOperator = aRIBsnsBillBatchSchema.getUWOperator();
		this.Idea = aRIBsnsBillBatchSchema.getIdea();
		this.Operator = aRIBsnsBillBatchSchema.getOperator();
		this.ManageCom = aRIBsnsBillBatchSchema.getManageCom();
		this.MakeDate = fDate.getDate( aRIBsnsBillBatchSchema.getMakeDate());
		this.MakeTime = aRIBsnsBillBatchSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aRIBsnsBillBatchSchema.getModifyDate());
		this.ModifyTime = aRIBsnsBillBatchSchema.getModifyTime();
		this.StandbyString1 = aRIBsnsBillBatchSchema.getStandbyString1();
		this.StandbyString2 = aRIBsnsBillBatchSchema.getStandbyString2();
		this.StandbyString3 = aRIBsnsBillBatchSchema.getStandbyString3();
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

			if( rs.getString("IncomeCompanyNo") == null )
				this.IncomeCompanyNo = null;
			else
				this.IncomeCompanyNo = rs.getString("IncomeCompanyNo").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("BillTimes") == null )
				this.BillTimes = null;
			else
				this.BillTimes = rs.getString("BillTimes").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			if( rs.getString("Idea") == null )
				this.Idea = null;
			else
				this.Idea = rs.getString("Idea").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIBsnsBillBatch表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIBsnsBillBatchSchema getSchema()
	{
		RIBsnsBillBatchSchema aRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();
		aRIBsnsBillBatchSchema.setSchema(this);
		return aRIBsnsBillBatchSchema;
	}

	public RIBsnsBillBatchDB getDB()
	{
		RIBsnsBillBatchDB aDBOper = new RIBsnsBillBatchDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillBatch描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IncomeCompanyNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillTimes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Idea)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyString3));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillBatch>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			IncomeCompanyNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BillTimes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Idea = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyString1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			StandbyString2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			StandbyString3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillBatchSchema";
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
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IncomeCompanyNo));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("BillTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillTimes));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("Idea"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Idea));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(IncomeCompanyNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BillTimes);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Idea);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyString1);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(StandbyString2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(StandbyString3);
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
		if (FCode.equalsIgnoreCase("IncomeCompanyNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IncomeCompanyNo = FValue.trim();
			}
			else
				IncomeCompanyNo = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("BillTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillTimes = FValue.trim();
			}
			else
				BillTimes = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
		}
		if (FCode.equalsIgnoreCase("Idea"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Idea = FValue.trim();
			}
			else
				Idea = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIBsnsBillBatchSchema other = (RIBsnsBillBatchSchema)otherObject;
		return
			BatchNo.equals(other.getBatchNo())
			&& BillNo.equals(other.getBillNo())
			&& Currency.equals(other.getCurrency())
			&& IncomeCompanyNo.equals(other.getIncomeCompanyNo())
			&& State.equals(other.getState())
			&& RIContNo.equals(other.getRIContNo())
			&& BillTimes.equals(other.getBillTimes())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& UWOperator.equals(other.getUWOperator())
			&& Idea.equals(other.getIdea())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StandbyString1.equals(other.getStandbyString1())
			&& StandbyString2.equals(other.getStandbyString2())
			&& StandbyString3.equals(other.getStandbyString3());
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
		if( strFieldName.equals("BatchNo") ) {
			return 0;
		}
		if( strFieldName.equals("BillNo") ) {
			return 1;
		}
		if( strFieldName.equals("Currency") ) {
			return 2;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return 3;
		}
		if( strFieldName.equals("State") ) {
			return 4;
		}
		if( strFieldName.equals("RIContNo") ) {
			return 5;
		}
		if( strFieldName.equals("BillTimes") ) {
			return 6;
		}
		if( strFieldName.equals("StartDate") ) {
			return 7;
		}
		if( strFieldName.equals("EndDate") ) {
			return 8;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 9;
		}
		if( strFieldName.equals("Idea") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("StandbyString1") ) {
			return 17;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return 18;
		}
		if( strFieldName.equals("StandbyString3") ) {
			return 19;
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
				strFieldName = "BatchNo";
				break;
			case 1:
				strFieldName = "BillNo";
				break;
			case 2:
				strFieldName = "Currency";
				break;
			case 3:
				strFieldName = "IncomeCompanyNo";
				break;
			case 4:
				strFieldName = "State";
				break;
			case 5:
				strFieldName = "RIContNo";
				break;
			case 6:
				strFieldName = "BillTimes";
				break;
			case 7:
				strFieldName = "StartDate";
				break;
			case 8:
				strFieldName = "EndDate";
				break;
			case 9:
				strFieldName = "UWOperator";
				break;
			case 10:
				strFieldName = "Idea";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "ManageCom";
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
			case 17:
				strFieldName = "StandbyString1";
				break;
			case 18:
				strFieldName = "StandbyString2";
				break;
			case 19:
				strFieldName = "StandbyString3";
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
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IncomeCompanyNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillTimes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Idea") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
		if( strFieldName.equals("StandbyString1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyString3") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
