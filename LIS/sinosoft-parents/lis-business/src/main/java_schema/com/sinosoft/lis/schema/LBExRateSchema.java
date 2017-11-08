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
import com.sinosoft.lis.db.LBExRateDB;

/*
 * <p>ClassName: LBExRateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造基础表
 */
public class LBExRateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBExRateSchema.class);

	// @Field
	/** 外汇币种代码 */
	private String CurrCode;
	/** 外币数额单位 */
	private int Per;
	/** 本币币种代码 */
	private String DestCode;
	/** 现汇买入价 */
	private double ExchBid;
	/** 现汇卖出价 */
	private double ExchOffer;
	/** 现钞买入价 */
	private double CashBid;
	/** 现钞卖出价 */
	private double CashOffer;
	/** 中间价 */
	private double Middle;
	/** 启用日期 */
	private Date StartDate;
	/** 启用时间 */
	private String StartTime;
	/** 停用日期 */
	private Date EndDate;
	/** 停用时间 */
	private String EndTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBExRateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "CurrCode";
		pk[1] = "Per";
		pk[2] = "DestCode";
		pk[3] = "StartDate";
		pk[4] = "StartTime";

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
		LBExRateSchema cloned = (LBExRateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCurrCode()
	{
		return CurrCode;
	}
	public void setCurrCode(String aCurrCode)
	{
		CurrCode = aCurrCode;
	}
	public int getPer()
	{
		return Per;
	}
	public void setPer(int aPer)
	{
		Per = aPer;
	}
	public void setPer(String aPer)
	{
		if (aPer != null && !aPer.equals(""))
		{
			Integer tInteger = new Integer(aPer);
			int i = tInteger.intValue();
			Per = i;
		}
	}

	public String getDestCode()
	{
		return DestCode;
	}
	public void setDestCode(String aDestCode)
	{
		DestCode = aDestCode;
	}
	public double getExchBid()
	{
		return ExchBid;
	}
	public void setExchBid(double aExchBid)
	{
		ExchBid = aExchBid;
	}
	public void setExchBid(String aExchBid)
	{
		if (aExchBid != null && !aExchBid.equals(""))
		{
			Double tDouble = new Double(aExchBid);
			double d = tDouble.doubleValue();
			ExchBid = d;
		}
	}

	public double getExchOffer()
	{
		return ExchOffer;
	}
	public void setExchOffer(double aExchOffer)
	{
		ExchOffer = aExchOffer;
	}
	public void setExchOffer(String aExchOffer)
	{
		if (aExchOffer != null && !aExchOffer.equals(""))
		{
			Double tDouble = new Double(aExchOffer);
			double d = tDouble.doubleValue();
			ExchOffer = d;
		}
	}

	public double getCashBid()
	{
		return CashBid;
	}
	public void setCashBid(double aCashBid)
	{
		CashBid = aCashBid;
	}
	public void setCashBid(String aCashBid)
	{
		if (aCashBid != null && !aCashBid.equals(""))
		{
			Double tDouble = new Double(aCashBid);
			double d = tDouble.doubleValue();
			CashBid = d;
		}
	}

	public double getCashOffer()
	{
		return CashOffer;
	}
	public void setCashOffer(double aCashOffer)
	{
		CashOffer = aCashOffer;
	}
	public void setCashOffer(String aCashOffer)
	{
		if (aCashOffer != null && !aCashOffer.equals(""))
		{
			Double tDouble = new Double(aCashOffer);
			double d = tDouble.doubleValue();
			CashOffer = d;
		}
	}

	public double getMiddle()
	{
		return Middle;
	}
	public void setMiddle(double aMiddle)
	{
		Middle = aMiddle;
	}
	public void setMiddle(String aMiddle)
	{
		if (aMiddle != null && !aMiddle.equals(""))
		{
			Double tDouble = new Double(aMiddle);
			double d = tDouble.doubleValue();
			Middle = d;
		}
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

	public String getStartTime()
	{
		return StartTime;
	}
	public void setStartTime(String aStartTime)
	{
		StartTime = aStartTime;
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

	public String getEndTime()
	{
		return EndTime;
	}
	public void setEndTime(String aEndTime)
	{
		EndTime = aEndTime;
	}

	/**
	* 使用另外一个 LBExRateSchema 对象给 Schema 赋值
	* @param: aLBExRateSchema LBExRateSchema
	**/
	public void setSchema(LBExRateSchema aLBExRateSchema)
	{
		this.CurrCode = aLBExRateSchema.getCurrCode();
		this.Per = aLBExRateSchema.getPer();
		this.DestCode = aLBExRateSchema.getDestCode();
		this.ExchBid = aLBExRateSchema.getExchBid();
		this.ExchOffer = aLBExRateSchema.getExchOffer();
		this.CashBid = aLBExRateSchema.getCashBid();
		this.CashOffer = aLBExRateSchema.getCashOffer();
		this.Middle = aLBExRateSchema.getMiddle();
		this.StartDate = fDate.getDate( aLBExRateSchema.getStartDate());
		this.StartTime = aLBExRateSchema.getStartTime();
		this.EndDate = fDate.getDate( aLBExRateSchema.getEndDate());
		this.EndTime = aLBExRateSchema.getEndTime();
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
			if( rs.getString("CurrCode") == null )
				this.CurrCode = null;
			else
				this.CurrCode = rs.getString("CurrCode").trim();

			this.Per = rs.getInt("Per");
			if( rs.getString("DestCode") == null )
				this.DestCode = null;
			else
				this.DestCode = rs.getString("DestCode").trim();

			this.ExchBid = rs.getDouble("ExchBid");
			this.ExchOffer = rs.getDouble("ExchOffer");
			this.CashBid = rs.getDouble("CashBid");
			this.CashOffer = rs.getDouble("CashOffer");
			this.Middle = rs.getDouble("Middle");
			this.StartDate = rs.getDate("StartDate");
			if( rs.getString("StartTime") == null )
				this.StartTime = null;
			else
				this.StartTime = rs.getString("StartTime").trim();

			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("EndTime") == null )
				this.EndTime = null;
			else
				this.EndTime = rs.getString("EndTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBExRate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBExRateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBExRateSchema getSchema()
	{
		LBExRateSchema aLBExRateSchema = new LBExRateSchema();
		aLBExRateSchema.setSchema(this);
		return aLBExRateSchema;
	}

	public LBExRateDB getDB()
	{
		LBExRateDB aDBOper = new LBExRateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBExRate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CurrCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Per));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExchBid));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ExchOffer));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CashBid));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CashOffer));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Middle));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBExRate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CurrCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Per= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			DestCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ExchBid = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			ExchOffer = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			CashBid = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			CashOffer = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Middle = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			StartTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EndTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBExRateSchema";
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
		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CurrCode));
		}
		if (FCode.equalsIgnoreCase("Per"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Per));
		}
		if (FCode.equalsIgnoreCase("DestCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestCode));
		}
		if (FCode.equalsIgnoreCase("ExchBid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExchBid));
		}
		if (FCode.equalsIgnoreCase("ExchOffer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExchOffer));
		}
		if (FCode.equalsIgnoreCase("CashBid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashBid));
		}
		if (FCode.equalsIgnoreCase("CashOffer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CashOffer));
		}
		if (FCode.equalsIgnoreCase("Middle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Middle));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("StartTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartTime));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("EndTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndTime));
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
				strFieldValue = StrTool.GBKToUnicode(CurrCode);
				break;
			case 1:
				strFieldValue = String.valueOf(Per);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DestCode);
				break;
			case 3:
				strFieldValue = String.valueOf(ExchBid);
				break;
			case 4:
				strFieldValue = String.valueOf(ExchOffer);
				break;
			case 5:
				strFieldValue = String.valueOf(CashBid);
				break;
			case 6:
				strFieldValue = String.valueOf(CashOffer);
				break;
			case 7:
				strFieldValue = String.valueOf(Middle);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StartTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(EndTime);
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

		if (FCode.equalsIgnoreCase("CurrCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CurrCode = FValue.trim();
			}
			else
				CurrCode = null;
		}
		if (FCode.equalsIgnoreCase("Per"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Per = i;
			}
		}
		if (FCode.equalsIgnoreCase("DestCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestCode = FValue.trim();
			}
			else
				DestCode = null;
		}
		if (FCode.equalsIgnoreCase("ExchBid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExchBid = d;
			}
		}
		if (FCode.equalsIgnoreCase("ExchOffer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ExchOffer = d;
			}
		}
		if (FCode.equalsIgnoreCase("CashBid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CashBid = d;
			}
		}
		if (FCode.equalsIgnoreCase("CashOffer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CashOffer = d;
			}
		}
		if (FCode.equalsIgnoreCase("Middle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Middle = d;
			}
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
		if (FCode.equalsIgnoreCase("StartTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartTime = FValue.trim();
			}
			else
				StartTime = null;
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
		if (FCode.equalsIgnoreCase("EndTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndTime = FValue.trim();
			}
			else
				EndTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBExRateSchema other = (LBExRateSchema)otherObject;
		return
			CurrCode.equals(other.getCurrCode())
			&& Per == other.getPer()
			&& DestCode.equals(other.getDestCode())
			&& ExchBid == other.getExchBid()
			&& ExchOffer == other.getExchOffer()
			&& CashBid == other.getCashBid()
			&& CashOffer == other.getCashOffer()
			&& Middle == other.getMiddle()
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& StartTime.equals(other.getStartTime())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& EndTime.equals(other.getEndTime());
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
		if( strFieldName.equals("CurrCode") ) {
			return 0;
		}
		if( strFieldName.equals("Per") ) {
			return 1;
		}
		if( strFieldName.equals("DestCode") ) {
			return 2;
		}
		if( strFieldName.equals("ExchBid") ) {
			return 3;
		}
		if( strFieldName.equals("ExchOffer") ) {
			return 4;
		}
		if( strFieldName.equals("CashBid") ) {
			return 5;
		}
		if( strFieldName.equals("CashOffer") ) {
			return 6;
		}
		if( strFieldName.equals("Middle") ) {
			return 7;
		}
		if( strFieldName.equals("StartDate") ) {
			return 8;
		}
		if( strFieldName.equals("StartTime") ) {
			return 9;
		}
		if( strFieldName.equals("EndDate") ) {
			return 10;
		}
		if( strFieldName.equals("EndTime") ) {
			return 11;
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
				strFieldName = "CurrCode";
				break;
			case 1:
				strFieldName = "Per";
				break;
			case 2:
				strFieldName = "DestCode";
				break;
			case 3:
				strFieldName = "ExchBid";
				break;
			case 4:
				strFieldName = "ExchOffer";
				break;
			case 5:
				strFieldName = "CashBid";
				break;
			case 6:
				strFieldName = "CashOffer";
				break;
			case 7:
				strFieldName = "Middle";
				break;
			case 8:
				strFieldName = "StartDate";
				break;
			case 9:
				strFieldName = "StartTime";
				break;
			case 10:
				strFieldName = "EndDate";
				break;
			case 11:
				strFieldName = "EndTime";
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
		if( strFieldName.equals("CurrCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Per") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("DestCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExchBid") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ExchOffer") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CashBid") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CashOffer") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Middle") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StartTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndTime") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
