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
import com.sinosoft.lis.db.LQClaimRiskDB;

/*
 * <p>ClassName: LQClaimRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-09-09
 */
public class LQClaimRiskSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LQClaimRiskSchema.class);

	// @Field
	/** 询价编码 */
	private String AskPriceNo;
	/** 报价版本号 */
	private String AskNo;
	/** Serialno */
	private String SerialNo;
	/** Year */
	private String Year;
	/** Startdate */
	private Date StartDate;
	/** Enddate */
	private Date EndDate;
	/** Claimmonth */
	private int ClaimMonth;
	/** Ibnr */
	private double IBNR;
	/** Claimpaid */
	private double ClaimPaid;
	/** Claimproduct */
	private String ClaimProduct;
	/** Ibnrvalue */
	private double IBNRValue;
	/** Remark */
	private String Remark;
	/** Attribute1 */
	private String Attribute1;
	/** Attribute2 */
	private String Attribute2;
	/** Attribute3 */
	private String Attribute3;
	/** Attribute4 */
	private String Attribute4;
	/** Attribute5 */
	private String Attribute5;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LQClaimRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "AskPriceNo";
		pk[1] = "AskNo";
		pk[2] = "SerialNo";
		pk[3] = "ClaimProduct";

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
                LQClaimRiskSchema cloned = (LQClaimRiskSchema)super.clone();
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
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getYear()
	{
		return Year;
	}
	public void setYear(String aYear)
	{
		Year = aYear;
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

	public int getClaimMonth()
	{
		return ClaimMonth;
	}
	public void setClaimMonth(int aClaimMonth)
	{
		ClaimMonth = aClaimMonth;
	}
	public void setClaimMonth(String aClaimMonth)
	{
		if (aClaimMonth != null && !aClaimMonth.equals(""))
		{
			Integer tInteger = new Integer(aClaimMonth);
			int i = tInteger.intValue();
			ClaimMonth = i;
		}
	}

	public double getIBNR()
	{
		return IBNR;
	}
	public void setIBNR(double aIBNR)
	{
		IBNR = aIBNR;
	}
	public void setIBNR(String aIBNR)
	{
		if (aIBNR != null && !aIBNR.equals(""))
		{
			Double tDouble = new Double(aIBNR);
			double d = tDouble.doubleValue();
			IBNR = d;
		}
	}

	public double getClaimPaid()
	{
		return ClaimPaid;
	}
	public void setClaimPaid(double aClaimPaid)
	{
		ClaimPaid = aClaimPaid;
	}
	public void setClaimPaid(String aClaimPaid)
	{
		if (aClaimPaid != null && !aClaimPaid.equals(""))
		{
			Double tDouble = new Double(aClaimPaid);
			double d = tDouble.doubleValue();
			ClaimPaid = d;
		}
	}

	public String getClaimProduct()
	{
		return ClaimProduct;
	}
	public void setClaimProduct(String aClaimProduct)
	{
		ClaimProduct = aClaimProduct;
	}
	public double getIBNRValue()
	{
		return IBNRValue;
	}
	public void setIBNRValue(double aIBNRValue)
	{
		IBNRValue = aIBNRValue;
	}
	public void setIBNRValue(String aIBNRValue)
	{
		if (aIBNRValue != null && !aIBNRValue.equals(""))
		{
			Double tDouble = new Double(aIBNRValue);
			double d = tDouble.doubleValue();
			IBNRValue = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
		return Attribute3;
	}
	public void setAttribute3(String aAttribute3)
	{
		Attribute3 = aAttribute3;
	}
	public String getAttribute4()
	{
		return Attribute4;
	}
	public void setAttribute4(String aAttribute4)
	{
		Attribute4 = aAttribute4;
	}
	public String getAttribute5()
	{
		return Attribute5;
	}
	public void setAttribute5(String aAttribute5)
	{
		Attribute5 = aAttribute5;
	}

	/**
	* 使用另外一个 LQClaimRiskSchema 对象给 Schema 赋值
	* @param: aLQClaimRiskSchema LQClaimRiskSchema
	**/
	public void setSchema(LQClaimRiskSchema aLQClaimRiskSchema)
	{
		this.AskPriceNo = aLQClaimRiskSchema.getAskPriceNo();
		this.AskNo = aLQClaimRiskSchema.getAskNo();
		this.SerialNo = aLQClaimRiskSchema.getSerialNo();
		this.Year = aLQClaimRiskSchema.getYear();
		this.StartDate = fDate.getDate( aLQClaimRiskSchema.getStartDate());
		this.EndDate = fDate.getDate( aLQClaimRiskSchema.getEndDate());
		this.ClaimMonth = aLQClaimRiskSchema.getClaimMonth();
		this.IBNR = aLQClaimRiskSchema.getIBNR();
		this.ClaimPaid = aLQClaimRiskSchema.getClaimPaid();
		this.ClaimProduct = aLQClaimRiskSchema.getClaimProduct();
		this.IBNRValue = aLQClaimRiskSchema.getIBNRValue();
		this.Remark = aLQClaimRiskSchema.getRemark();
		this.Attribute1 = aLQClaimRiskSchema.getAttribute1();
		this.Attribute2 = aLQClaimRiskSchema.getAttribute2();
		this.Attribute3 = aLQClaimRiskSchema.getAttribute3();
		this.Attribute4 = aLQClaimRiskSchema.getAttribute4();
		this.Attribute5 = aLQClaimRiskSchema.getAttribute5();
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

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("Year") == null )
				this.Year = null;
			else
				this.Year = rs.getString("Year").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.ClaimMonth = rs.getInt("ClaimMonth");
			this.IBNR = rs.getDouble("IBNR");
			this.ClaimPaid = rs.getDouble("ClaimPaid");
			if( rs.getString("ClaimProduct") == null )
				this.ClaimProduct = null;
			else
				this.ClaimProduct = rs.getString("ClaimProduct").trim();

			this.IBNRValue = rs.getDouble("IBNRValue");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Attribute1") == null )
				this.Attribute1 = null;
			else
				this.Attribute1 = rs.getString("Attribute1").trim();

			if( rs.getString("Attribute2") == null )
				this.Attribute2 = null;
			else
				this.Attribute2 = rs.getString("Attribute2").trim();

			if( rs.getString("Attribute3") == null )
				this.Attribute3 = null;
			else
				this.Attribute3 = rs.getString("Attribute3").trim();

			if( rs.getString("Attribute4") == null )
				this.Attribute4 = null;
			else
				this.Attribute4 = rs.getString("Attribute4").trim();

			if( rs.getString("Attribute5") == null )
				this.Attribute5 = null;
			else
				this.Attribute5 = rs.getString("Attribute5").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LQClaimRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQClaimRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LQClaimRiskSchema getSchema()
	{
		LQClaimRiskSchema aLQClaimRiskSchema = new LQClaimRiskSchema();
		aLQClaimRiskSchema.setSchema(this);
		return aLQClaimRiskSchema;
	}

	public LQClaimRiskDB getDB()
	{
		LQClaimRiskDB aDBOper = new LQClaimRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQClaimRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(AskPriceNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(AskNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Year)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ClaimMonth));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(IBNR));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(ClaimPaid));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ClaimProduct)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(IBNRValue));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute3)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute4)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Attribute5));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLQClaimRisk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AskPriceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			ClaimMonth= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			IBNR = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimPaid = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			ClaimProduct = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IBNRValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Attribute1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Attribute2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Attribute3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Attribute4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Attribute5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LQClaimRiskSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("ClaimMonth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimMonth));
		}
		if (FCode.equalsIgnoreCase("IBNR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IBNR));
		}
		if (FCode.equalsIgnoreCase("ClaimPaid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimPaid));
		}
		if (FCode.equalsIgnoreCase("ClaimProduct"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimProduct));
		}
		if (FCode.equalsIgnoreCase("IBNRValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IBNRValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute3));
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute4));
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Attribute5));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Year);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 6:
				strFieldValue = String.valueOf(ClaimMonth);
				break;
			case 7:
				strFieldValue = String.valueOf(IBNR);
				break;
			case 8:
				strFieldValue = String.valueOf(ClaimPaid);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ClaimProduct);
				break;
			case 10:
				strFieldValue = String.valueOf(IBNRValue);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Attribute1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Attribute2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Attribute3);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Attribute4);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Attribute5);
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Year = FValue.trim();
			}
			else
				Year = null;
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
		if (FCode.equalsIgnoreCase("ClaimMonth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ClaimMonth = i;
			}
		}
		if (FCode.equalsIgnoreCase("IBNR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				IBNR = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimPaid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ClaimPaid = d;
			}
		}
		if (FCode.equalsIgnoreCase("ClaimProduct"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimProduct = FValue.trim();
			}
			else
				ClaimProduct = null;
		}
		if (FCode.equalsIgnoreCase("IBNRValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				IBNRValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
			if( FValue != null && !FValue.equals(""))
			{
				Attribute3 = FValue.trim();
			}
			else
				Attribute3 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute4 = FValue.trim();
			}
			else
				Attribute4 = null;
		}
		if (FCode.equalsIgnoreCase("Attribute5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Attribute5 = FValue.trim();
			}
			else
				Attribute5 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LQClaimRiskSchema other = (LQClaimRiskSchema)otherObject;
		return
			AskPriceNo.equals(other.getAskPriceNo())
			&& AskNo.equals(other.getAskNo())
			&& SerialNo.equals(other.getSerialNo())
			&& Year.equals(other.getYear())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& ClaimMonth == other.getClaimMonth()
			&& IBNR == other.getIBNR()
			&& ClaimPaid == other.getClaimPaid()
			&& ClaimProduct.equals(other.getClaimProduct())
			&& IBNRValue == other.getIBNRValue()
			&& Remark.equals(other.getRemark())
			&& Attribute1.equals(other.getAttribute1())
			&& Attribute2.equals(other.getAttribute2())
			&& Attribute3.equals(other.getAttribute3())
			&& Attribute4.equals(other.getAttribute4())
			&& Attribute5.equals(other.getAttribute5());
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
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("Year") ) {
			return 3;
		}
		if( strFieldName.equals("StartDate") ) {
			return 4;
		}
		if( strFieldName.equals("EndDate") ) {
			return 5;
		}
		if( strFieldName.equals("ClaimMonth") ) {
			return 6;
		}
		if( strFieldName.equals("IBNR") ) {
			return 7;
		}
		if( strFieldName.equals("ClaimPaid") ) {
			return 8;
		}
		if( strFieldName.equals("ClaimProduct") ) {
			return 9;
		}
		if( strFieldName.equals("IBNRValue") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
			return 11;
		}
		if( strFieldName.equals("Attribute1") ) {
			return 12;
		}
		if( strFieldName.equals("Attribute2") ) {
			return 13;
		}
		if( strFieldName.equals("Attribute3") ) {
			return 14;
		}
		if( strFieldName.equals("Attribute4") ) {
			return 15;
		}
		if( strFieldName.equals("Attribute5") ) {
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
				strFieldName = "AskPriceNo";
				break;
			case 1:
				strFieldName = "AskNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "Year";
				break;
			case 4:
				strFieldName = "StartDate";
				break;
			case 5:
				strFieldName = "EndDate";
				break;
			case 6:
				strFieldName = "ClaimMonth";
				break;
			case 7:
				strFieldName = "IBNR";
				break;
			case 8:
				strFieldName = "ClaimPaid";
				break;
			case 9:
				strFieldName = "ClaimProduct";
				break;
			case 10:
				strFieldName = "IBNRValue";
				break;
			case 11:
				strFieldName = "Remark";
				break;
			case 12:
				strFieldName = "Attribute1";
				break;
			case 13:
				strFieldName = "Attribute2";
				break;
			case 14:
				strFieldName = "Attribute3";
				break;
			case 15:
				strFieldName = "Attribute4";
				break;
			case 16:
				strFieldName = "Attribute5";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Year") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ClaimMonth") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IBNR") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimPaid") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ClaimProduct") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IBNRValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Attribute5") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DOUBLE;
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
