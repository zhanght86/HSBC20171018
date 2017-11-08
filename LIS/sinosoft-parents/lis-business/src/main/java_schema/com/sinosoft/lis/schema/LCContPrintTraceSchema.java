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
import com.sinosoft.lis.db.LCContPrintTraceDB;

/*
 * <p>ClassName: LCContPrintTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保单打印表
 */
public class LCContPrintTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContPrintTraceSchema.class);

	// @Field
	/** 保单号码 */
	private String ContNo;
	/** 投保单号 */
	private String PrtNo;
	/** 打印次数 */
	private String BatchNo;
	/** 管理机构 */
	private String ManageCom;
	/** 是否计费 */
	private String NeedPay;
	/** 原因 */
	private String Reason;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 第一次打印日期 */
	private Date FMakeDate;
	/** 第一次打印时间 */
	private String FMakeTime;
	/** 重打申请标记 */
	private String RePrintAppFlag;
	/** 保单类型 */
	private String ContType;
	/** 重打标记 */
	private String RePrintFlag;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContPrintTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "ContNo";
		pk[1] = "PrtNo";
		pk[2] = "BatchNo";

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
		LCContPrintTraceSchema cloned = (LCContPrintTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getPrtNo()
	{
		return PrtNo;
	}
	public void setPrtNo(String aPrtNo)
	{
		PrtNo = aPrtNo;
	}
	public String getBatchNo()
	{
		return BatchNo;
	}
	public void setBatchNo(String aBatchNo)
	{
		BatchNo = aBatchNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/**
	* 0----计费<p>
	* 1----不计费
	*/
	public String getNeedPay()
	{
		return NeedPay;
	}
	public void setNeedPay(String aNeedPay)
	{
		NeedPay = aNeedPay;
	}
	/**
	* 1-打印机故障<p>
	* 2-操作失误<p>
	* 3-数据错误<p>
	* 4-遗失或毁损<p>
	* 5-其它
	*/
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		Reason = aReason;
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
	public String getFMakeDate()
	{
		if( FMakeDate != null )
			return fDate.getString(FMakeDate);
		else
			return null;
	}
	public void setFMakeDate(Date aFMakeDate)
	{
		FMakeDate = aFMakeDate;
	}
	public void setFMakeDate(String aFMakeDate)
	{
		if (aFMakeDate != null && !aFMakeDate.equals("") )
		{
			FMakeDate = fDate.getDate( aFMakeDate );
		}
		else
			FMakeDate = null;
	}

	public String getFMakeTime()
	{
		return FMakeTime;
	}
	public void setFMakeTime(String aFMakeTime)
	{
		FMakeTime = aFMakeTime;
	}
	public String getRePrintAppFlag()
	{
		return RePrintAppFlag;
	}
	public void setRePrintAppFlag(String aRePrintAppFlag)
	{
		RePrintAppFlag = aRePrintAppFlag;
	}
	public String getContType()
	{
		return ContType;
	}
	public void setContType(String aContType)
	{
		ContType = aContType;
	}
	public String getRePrintFlag()
	{
		return RePrintFlag;
	}
	public void setRePrintFlag(String aRePrintFlag)
	{
		RePrintFlag = aRePrintFlag;
	}

	/**
	* 使用另外一个 LCContPrintTraceSchema 对象给 Schema 赋值
	* @param: aLCContPrintTraceSchema LCContPrintTraceSchema
	**/
	public void setSchema(LCContPrintTraceSchema aLCContPrintTraceSchema)
	{
		this.ContNo = aLCContPrintTraceSchema.getContNo();
		this.PrtNo = aLCContPrintTraceSchema.getPrtNo();
		this.BatchNo = aLCContPrintTraceSchema.getBatchNo();
		this.ManageCom = aLCContPrintTraceSchema.getManageCom();
		this.NeedPay = aLCContPrintTraceSchema.getNeedPay();
		this.Reason = aLCContPrintTraceSchema.getReason();
		this.Operator = aLCContPrintTraceSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCContPrintTraceSchema.getMakeDate());
		this.MakeTime = aLCContPrintTraceSchema.getMakeTime();
		this.FMakeDate = fDate.getDate( aLCContPrintTraceSchema.getFMakeDate());
		this.FMakeTime = aLCContPrintTraceSchema.getFMakeTime();
		this.RePrintAppFlag = aLCContPrintTraceSchema.getRePrintAppFlag();
		this.ContType = aLCContPrintTraceSchema.getContType();
		this.RePrintFlag = aLCContPrintTraceSchema.getRePrintFlag();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("PrtNo") == null )
				this.PrtNo = null;
			else
				this.PrtNo = rs.getString("PrtNo").trim();

			if( rs.getString("BatchNo") == null )
				this.BatchNo = null;
			else
				this.BatchNo = rs.getString("BatchNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("NeedPay") == null )
				this.NeedPay = null;
			else
				this.NeedPay = rs.getString("NeedPay").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.FMakeDate = rs.getDate("FMakeDate");
			if( rs.getString("FMakeTime") == null )
				this.FMakeTime = null;
			else
				this.FMakeTime = rs.getString("FMakeTime").trim();

			if( rs.getString("RePrintAppFlag") == null )
				this.RePrintAppFlag = null;
			else
				this.RePrintAppFlag = rs.getString("RePrintAppFlag").trim();

			if( rs.getString("ContType") == null )
				this.ContType = null;
			else
				this.ContType = rs.getString("ContType").trim();

			if( rs.getString("RePrintFlag") == null )
				this.RePrintFlag = null;
			else
				this.RePrintFlag = rs.getString("RePrintFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCContPrintTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPrintTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContPrintTraceSchema getSchema()
	{
		LCContPrintTraceSchema aLCContPrintTraceSchema = new LCContPrintTraceSchema();
		aLCContPrintTraceSchema.setSchema(this);
		return aLCContPrintTraceSchema;
	}

	public LCContPrintTraceDB getDB()
	{
		LCContPrintTraceDB aDBOper = new LCContPrintTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPrintTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatchNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NeedPay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( FMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RePrintAppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RePrintFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContPrintTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PrtNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BatchNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NeedPay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			FMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			FMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RePrintAppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ContType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RePrintFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContPrintTraceSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtNo));
		}
		if (FCode.equalsIgnoreCase("BatchNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatchNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("NeedPay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NeedPay));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
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
		if (FCode.equalsIgnoreCase("FMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getFMakeDate()));
		}
		if (FCode.equalsIgnoreCase("FMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FMakeTime));
		}
		if (FCode.equalsIgnoreCase("RePrintAppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RePrintAppFlag));
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContType));
		}
		if (FCode.equalsIgnoreCase("RePrintFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RePrintFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PrtNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BatchNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NeedPay);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getFMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FMakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RePrintAppFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ContType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RePrintFlag);
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

		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("PrtNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtNo = FValue.trim();
			}
			else
				PrtNo = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("NeedPay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NeedPay = FValue.trim();
			}
			else
				NeedPay = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
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
		if (FCode.equalsIgnoreCase("FMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				FMakeDate = fDate.getDate( FValue );
			}
			else
				FMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("FMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FMakeTime = FValue.trim();
			}
			else
				FMakeTime = null;
		}
		if (FCode.equalsIgnoreCase("RePrintAppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RePrintAppFlag = FValue.trim();
			}
			else
				RePrintAppFlag = null;
		}
		if (FCode.equalsIgnoreCase("ContType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContType = FValue.trim();
			}
			else
				ContType = null;
		}
		if (FCode.equalsIgnoreCase("RePrintFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RePrintFlag = FValue.trim();
			}
			else
				RePrintFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCContPrintTraceSchema other = (LCContPrintTraceSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& PrtNo.equals(other.getPrtNo())
			&& BatchNo.equals(other.getBatchNo())
			&& ManageCom.equals(other.getManageCom())
			&& NeedPay.equals(other.getNeedPay())
			&& Reason.equals(other.getReason())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(FMakeDate).equals(other.getFMakeDate())
			&& FMakeTime.equals(other.getFMakeTime())
			&& RePrintAppFlag.equals(other.getRePrintAppFlag())
			&& ContType.equals(other.getContType())
			&& RePrintFlag.equals(other.getRePrintFlag());
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("PrtNo") ) {
			return 1;
		}
		if( strFieldName.equals("BatchNo") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("NeedPay") ) {
			return 4;
		}
		if( strFieldName.equals("Reason") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("FMakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("FMakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("RePrintAppFlag") ) {
			return 11;
		}
		if( strFieldName.equals("ContType") ) {
			return 12;
		}
		if( strFieldName.equals("RePrintFlag") ) {
			return 13;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "PrtNo";
				break;
			case 2:
				strFieldName = "BatchNo";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "NeedPay";
				break;
			case 5:
				strFieldName = "Reason";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "FMakeDate";
				break;
			case 10:
				strFieldName = "FMakeTime";
				break;
			case 11:
				strFieldName = "RePrintAppFlag";
				break;
			case 12:
				strFieldName = "ContType";
				break;
			case 13:
				strFieldName = "RePrintFlag";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatchNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NeedPay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
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
		if( strFieldName.equals("FMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("FMakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RePrintAppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RePrintFlag") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
