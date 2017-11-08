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
import com.sinosoft.lis.db.ActuContClaimDB;

/*
 * <p>ClassName: ActuContClaimSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class ActuContClaimSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ActuContClaimSchema.class);

	// @Field
	/** 流水号 */
	private int WatNum;
	/** 保单号 */
	private String ContNo;
	/** 险种代码 */
	private String RiskCode;
	/** 赔案号 */
	private String ClmNo;
	/** 主、附险标记 */
	private String MainFlag;
	/** 出险人序号 */
	private String CustomerNo;
	/** 出险人姓名 */
	private String CustomerName;
	/** 出险人身份证号 */
	private String IDNo;
	/** 事故发生日 */
	private Date AccidentDate;
	/** 报案日 */
	private Date RptDate;
	/** 结案日 */
	private Date EndCaseDate;
	/** 结案状态 */
	private String ClmState;
	/** 给付金额 */
	private double RealPay;
	/** 预估金额 */
	private double StandPay;
	/** 险种对应的保额 */
	private double RiakToAmnt;
	/** 出险责任类型 */
	private String GetDutyKind;
	/** 出险原因1 */
	private String AccResult1;
	/** 出险原因2 */
	private String AccResult2;
	/** 出险原因3 */
	private String AccResult3;
	/** 分案号 */
	private String CaseNo;
	/** 事故受理号 */
	private String CaseRelaNo;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ActuContClaimSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "WatNum";

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
		ActuContClaimSchema cloned = (ActuContClaimSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public int getWatNum()
	{
		return WatNum;
	}
	public void setWatNum(int aWatNum)
	{
		WatNum = aWatNum;
	}
	public void setWatNum(String aWatNum)
	{
		if (aWatNum != null && !aWatNum.equals(""))
		{
			Integer tInteger = new Integer(aWatNum);
			int i = tInteger.intValue();
			WatNum = i;
		}
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getMainFlag()
	{
		return MainFlag;
	}
	public void setMainFlag(String aMainFlag)
	{
		MainFlag = aMainFlag;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		CustomerName = aCustomerName;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		IDNo = aIDNo;
	}
	public String getAccidentDate()
	{
		if( AccidentDate != null )
			return fDate.getString(AccidentDate);
		else
			return null;
	}
	public void setAccidentDate(Date aAccidentDate)
	{
		AccidentDate = aAccidentDate;
	}
	public void setAccidentDate(String aAccidentDate)
	{
		if (aAccidentDate != null && !aAccidentDate.equals("") )
		{
			AccidentDate = fDate.getDate( aAccidentDate );
		}
		else
			AccidentDate = null;
	}

	public String getRptDate()
	{
		if( RptDate != null )
			return fDate.getString(RptDate);
		else
			return null;
	}
	public void setRptDate(Date aRptDate)
	{
		RptDate = aRptDate;
	}
	public void setRptDate(String aRptDate)
	{
		if (aRptDate != null && !aRptDate.equals("") )
		{
			RptDate = fDate.getDate( aRptDate );
		}
		else
			RptDate = null;
	}

	public String getEndCaseDate()
	{
		if( EndCaseDate != null )
			return fDate.getString(EndCaseDate);
		else
			return null;
	}
	public void setEndCaseDate(Date aEndCaseDate)
	{
		EndCaseDate = aEndCaseDate;
	}
	public void setEndCaseDate(String aEndCaseDate)
	{
		if (aEndCaseDate != null && !aEndCaseDate.equals("") )
		{
			EndCaseDate = fDate.getDate( aEndCaseDate );
		}
		else
			EndCaseDate = null;
	}

	public String getClmState()
	{
		return ClmState;
	}
	public void setClmState(String aClmState)
	{
		ClmState = aClmState;
	}
	public double getRealPay()
	{
		return RealPay;
	}
	public void setRealPay(double aRealPay)
	{
		RealPay = aRealPay;
	}
	public void setRealPay(String aRealPay)
	{
		if (aRealPay != null && !aRealPay.equals(""))
		{
			Double tDouble = new Double(aRealPay);
			double d = tDouble.doubleValue();
			RealPay = d;
		}
	}

	public double getStandPay()
	{
		return StandPay;
	}
	public void setStandPay(double aStandPay)
	{
		StandPay = aStandPay;
	}
	public void setStandPay(String aStandPay)
	{
		if (aStandPay != null && !aStandPay.equals(""))
		{
			Double tDouble = new Double(aStandPay);
			double d = tDouble.doubleValue();
			StandPay = d;
		}
	}

	public double getRiakToAmnt()
	{
		return RiakToAmnt;
	}
	public void setRiakToAmnt(double aRiakToAmnt)
	{
		RiakToAmnt = aRiakToAmnt;
	}
	public void setRiakToAmnt(String aRiakToAmnt)
	{
		if (aRiakToAmnt != null && !aRiakToAmnt.equals(""))
		{
			Double tDouble = new Double(aRiakToAmnt);
			double d = tDouble.doubleValue();
			RiakToAmnt = d;
		}
	}

	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		GetDutyKind = aGetDutyKind;
	}
	public String getAccResult1()
	{
		return AccResult1;
	}
	public void setAccResult1(String aAccResult1)
	{
		AccResult1 = aAccResult1;
	}
	public String getAccResult2()
	{
		return AccResult2;
	}
	public void setAccResult2(String aAccResult2)
	{
		AccResult2 = aAccResult2;
	}
	public String getAccResult3()
	{
		return AccResult3;
	}
	public void setAccResult3(String aAccResult3)
	{
		AccResult3 = aAccResult3;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		CaseNo = aCaseNo;
	}
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		CaseRelaNo = aCaseRelaNo;
	}

	/**
	* 使用另外一个 ActuContClaimSchema 对象给 Schema 赋值
	* @param: aActuContClaimSchema ActuContClaimSchema
	**/
	public void setSchema(ActuContClaimSchema aActuContClaimSchema)
	{
		this.WatNum = aActuContClaimSchema.getWatNum();
		this.ContNo = aActuContClaimSchema.getContNo();
		this.RiskCode = aActuContClaimSchema.getRiskCode();
		this.ClmNo = aActuContClaimSchema.getClmNo();
		this.MainFlag = aActuContClaimSchema.getMainFlag();
		this.CustomerNo = aActuContClaimSchema.getCustomerNo();
		this.CustomerName = aActuContClaimSchema.getCustomerName();
		this.IDNo = aActuContClaimSchema.getIDNo();
		this.AccidentDate = fDate.getDate( aActuContClaimSchema.getAccidentDate());
		this.RptDate = fDate.getDate( aActuContClaimSchema.getRptDate());
		this.EndCaseDate = fDate.getDate( aActuContClaimSchema.getEndCaseDate());
		this.ClmState = aActuContClaimSchema.getClmState();
		this.RealPay = aActuContClaimSchema.getRealPay();
		this.StandPay = aActuContClaimSchema.getStandPay();
		this.RiakToAmnt = aActuContClaimSchema.getRiakToAmnt();
		this.GetDutyKind = aActuContClaimSchema.getGetDutyKind();
		this.AccResult1 = aActuContClaimSchema.getAccResult1();
		this.AccResult2 = aActuContClaimSchema.getAccResult2();
		this.AccResult3 = aActuContClaimSchema.getAccResult3();
		this.CaseNo = aActuContClaimSchema.getCaseNo();
		this.CaseRelaNo = aActuContClaimSchema.getCaseRelaNo();
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
			this.WatNum = rs.getInt("WatNum");
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("MainFlag") == null )
				this.MainFlag = null;
			else
				this.MainFlag = rs.getString("MainFlag").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			this.AccidentDate = rs.getDate("AccidentDate");
			this.RptDate = rs.getDate("RptDate");
			this.EndCaseDate = rs.getDate("EndCaseDate");
			if( rs.getString("ClmState") == null )
				this.ClmState = null;
			else
				this.ClmState = rs.getString("ClmState").trim();

			this.RealPay = rs.getDouble("RealPay");
			this.StandPay = rs.getDouble("StandPay");
			this.RiakToAmnt = rs.getDouble("RiakToAmnt");
			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			if( rs.getString("AccResult1") == null )
				this.AccResult1 = null;
			else
				this.AccResult1 = rs.getString("AccResult1").trim();

			if( rs.getString("AccResult2") == null )
				this.AccResult2 = null;
			else
				this.AccResult2 = rs.getString("AccResult2").trim();

			if( rs.getString("AccResult3") == null )
				this.AccResult3 = null;
			else
				this.AccResult3 = rs.getString("AccResult3").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ActuContClaim表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActuContClaimSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ActuContClaimSchema getSchema()
	{
		ActuContClaimSchema aActuContClaimSchema = new ActuContClaimSchema();
		aActuContClaimSchema.setSchema(this);
		return aActuContClaimSchema;
	}

	public ActuContClaimDB getDB()
	{
		ActuContClaimDB aDBOper = new ActuContClaimDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActuContClaim描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(WatNum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AccidentDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RptDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndCaseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClmState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RealPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StandPay));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RiakToAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccResult3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActuContClaim>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			WatNum= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MainFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccidentDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			RptDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			EndCaseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ClmState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RealPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			StandPay = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).doubleValue();
			RiakToAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			AccResult1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AccResult2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AccResult3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActuContClaimSchema";
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
		if (FCode.equalsIgnoreCase("WatNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WatNum));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFlag));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
		}
		if (FCode.equalsIgnoreCase("RptDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRptDate()));
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
		}
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmState));
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RealPay));
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandPay));
		}
		if (FCode.equalsIgnoreCase("RiakToAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiakToAmnt));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult1));
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult2));
		}
		if (FCode.equalsIgnoreCase("AccResult3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccResult3));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
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
				strFieldValue = String.valueOf(WatNum);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MainFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAccidentDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRptDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndCaseDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ClmState);
				break;
			case 12:
				strFieldValue = String.valueOf(RealPay);
				break;
			case 13:
				strFieldValue = String.valueOf(StandPay);
				break;
			case 14:
				strFieldValue = String.valueOf(RiakToAmnt);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AccResult1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AccResult2);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AccResult3);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
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

		if (FCode.equalsIgnoreCase("WatNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WatNum = i;
			}
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("MainFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFlag = FValue.trim();
			}
			else
				MainFlag = null;
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
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("AccidentDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AccidentDate = fDate.getDate( FValue );
			}
			else
				AccidentDate = null;
		}
		if (FCode.equalsIgnoreCase("RptDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RptDate = fDate.getDate( FValue );
			}
			else
				RptDate = null;
		}
		if (FCode.equalsIgnoreCase("EndCaseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndCaseDate = fDate.getDate( FValue );
			}
			else
				EndCaseDate = null;
		}
		if (FCode.equalsIgnoreCase("ClmState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmState = FValue.trim();
			}
			else
				ClmState = null;
		}
		if (FCode.equalsIgnoreCase("RealPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RealPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StandPay = d;
			}
		}
		if (FCode.equalsIgnoreCase("RiakToAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				RiakToAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
		}
		if (FCode.equalsIgnoreCase("AccResult1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult1 = FValue.trim();
			}
			else
				AccResult1 = null;
		}
		if (FCode.equalsIgnoreCase("AccResult2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult2 = FValue.trim();
			}
			else
				AccResult2 = null;
		}
		if (FCode.equalsIgnoreCase("AccResult3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccResult3 = FValue.trim();
			}
			else
				AccResult3 = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ActuContClaimSchema other = (ActuContClaimSchema)otherObject;
		return
			WatNum == other.getWatNum()
			&& ContNo.equals(other.getContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& ClmNo.equals(other.getClmNo())
			&& MainFlag.equals(other.getMainFlag())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& IDNo.equals(other.getIDNo())
			&& fDate.getString(AccidentDate).equals(other.getAccidentDate())
			&& fDate.getString(RptDate).equals(other.getRptDate())
			&& fDate.getString(EndCaseDate).equals(other.getEndCaseDate())
			&& ClmState.equals(other.getClmState())
			&& RealPay == other.getRealPay()
			&& StandPay == other.getStandPay()
			&& RiakToAmnt == other.getRiakToAmnt()
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& AccResult1.equals(other.getAccResult1())
			&& AccResult2.equals(other.getAccResult2())
			&& AccResult3.equals(other.getAccResult3())
			&& CaseNo.equals(other.getCaseNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo());
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
		if( strFieldName.equals("WatNum") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("ClmNo") ) {
			return 3;
		}
		if( strFieldName.equals("MainFlag") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 5;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 6;
		}
		if( strFieldName.equals("IDNo") ) {
			return 7;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return 8;
		}
		if( strFieldName.equals("RptDate") ) {
			return 9;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return 10;
		}
		if( strFieldName.equals("ClmState") ) {
			return 11;
		}
		if( strFieldName.equals("RealPay") ) {
			return 12;
		}
		if( strFieldName.equals("StandPay") ) {
			return 13;
		}
		if( strFieldName.equals("RiakToAmnt") ) {
			return 14;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 15;
		}
		if( strFieldName.equals("AccResult1") ) {
			return 16;
		}
		if( strFieldName.equals("AccResult2") ) {
			return 17;
		}
		if( strFieldName.equals("AccResult3") ) {
			return 18;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 19;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
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
				strFieldName = "WatNum";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "ClmNo";
				break;
			case 4:
				strFieldName = "MainFlag";
				break;
			case 5:
				strFieldName = "CustomerNo";
				break;
			case 6:
				strFieldName = "CustomerName";
				break;
			case 7:
				strFieldName = "IDNo";
				break;
			case 8:
				strFieldName = "AccidentDate";
				break;
			case 9:
				strFieldName = "RptDate";
				break;
			case 10:
				strFieldName = "EndCaseDate";
				break;
			case 11:
				strFieldName = "ClmState";
				break;
			case 12:
				strFieldName = "RealPay";
				break;
			case 13:
				strFieldName = "StandPay";
				break;
			case 14:
				strFieldName = "RiakToAmnt";
				break;
			case 15:
				strFieldName = "GetDutyKind";
				break;
			case 16:
				strFieldName = "AccResult1";
				break;
			case 17:
				strFieldName = "AccResult2";
				break;
			case 18:
				strFieldName = "AccResult3";
				break;
			case 19:
				strFieldName = "CaseNo";
				break;
			case 20:
				strFieldName = "CaseRelaNo";
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
		if( strFieldName.equals("WatNum") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccidentDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RptDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndCaseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ClmState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RealPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandPay") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("RiakToAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccResult3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
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
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
