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
import com.sinosoft.lis.db.LPLiveInquiryDB;

/*
 * <p>ClassName: LPLiveInquirySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LPLiveInquirySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LPLiveInquirySchema.class);

	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 生调次数 */
	private int InquiryTimes;
	/** 生调日期 */
	private Date InquiryDate;
	/** 生调结果 */
	private String InquiryResult;
	/** 死亡日期 */
	private Date DiedDate;
	/** 死亡原因 */
	private String DiedReason;
	/** 生调员 */
	private String InquiryOperator;
	/** 备注 */
	private String Remark;
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

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LPLiveInquirySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CustomerNo";
		pk[1] = "InquiryTimes";

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
		LPLiveInquirySchema cloned = (LPLiveInquirySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public int getInquiryTimes()
	{
		return InquiryTimes;
	}
	public void setInquiryTimes(int aInquiryTimes)
	{
		InquiryTimes = aInquiryTimes;
	}
	public void setInquiryTimes(String aInquiryTimes)
	{
		if (aInquiryTimes != null && !aInquiryTimes.equals(""))
		{
			Integer tInteger = new Integer(aInquiryTimes);
			int i = tInteger.intValue();
			InquiryTimes = i;
		}
	}

	public String getInquiryDate()
	{
		if( InquiryDate != null )
			return fDate.getString(InquiryDate);
		else
			return null;
	}
	public void setInquiryDate(Date aInquiryDate)
	{
		InquiryDate = aInquiryDate;
	}
	public void setInquiryDate(String aInquiryDate)
	{
		if (aInquiryDate != null && !aInquiryDate.equals("") )
		{
			InquiryDate = fDate.getDate( aInquiryDate );
		}
		else
			InquiryDate = null;
	}

	/**
	* 0 正常<p>
	* 1 死亡
	*/
	public String getInquiryResult()
	{
		return InquiryResult;
	}
	public void setInquiryResult(String aInquiryResult)
	{
		InquiryResult = aInquiryResult;
	}
	public String getDiedDate()
	{
		if( DiedDate != null )
			return fDate.getString(DiedDate);
		else
			return null;
	}
	public void setDiedDate(Date aDiedDate)
	{
		DiedDate = aDiedDate;
	}
	public void setDiedDate(String aDiedDate)
	{
		if (aDiedDate != null && !aDiedDate.equals("") )
		{
			DiedDate = fDate.getDate( aDiedDate );
		}
		else
			DiedDate = null;
	}

	public String getDiedReason()
	{
		return DiedReason;
	}
	public void setDiedReason(String aDiedReason)
	{
		DiedReason = aDiedReason;
	}
	public String getInquiryOperator()
	{
		return InquiryOperator;
	}
	public void setInquiryOperator(String aInquiryOperator)
	{
		InquiryOperator = aInquiryOperator;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	* 使用另外一个 LPLiveInquirySchema 对象给 Schema 赋值
	* @param: aLPLiveInquirySchema LPLiveInquirySchema
	**/
	public void setSchema(LPLiveInquirySchema aLPLiveInquirySchema)
	{
		this.CustomerNo = aLPLiveInquirySchema.getCustomerNo();
		this.InquiryTimes = aLPLiveInquirySchema.getInquiryTimes();
		this.InquiryDate = fDate.getDate( aLPLiveInquirySchema.getInquiryDate());
		this.InquiryResult = aLPLiveInquirySchema.getInquiryResult();
		this.DiedDate = fDate.getDate( aLPLiveInquirySchema.getDiedDate());
		this.DiedReason = aLPLiveInquirySchema.getDiedReason();
		this.InquiryOperator = aLPLiveInquirySchema.getInquiryOperator();
		this.Remark = aLPLiveInquirySchema.getRemark();
		this.Operator = aLPLiveInquirySchema.getOperator();
		this.MakeDate = fDate.getDate( aLPLiveInquirySchema.getMakeDate());
		this.MakeTime = aLPLiveInquirySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLPLiveInquirySchema.getModifyDate());
		this.ModifyTime = aLPLiveInquirySchema.getModifyTime();
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
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			this.InquiryTimes = rs.getInt("InquiryTimes");
			this.InquiryDate = rs.getDate("InquiryDate");
			if( rs.getString("InquiryResult") == null )
				this.InquiryResult = null;
			else
				this.InquiryResult = rs.getString("InquiryResult").trim();

			this.DiedDate = rs.getDate("DiedDate");
			if( rs.getString("DiedReason") == null )
				this.DiedReason = null;
			else
				this.DiedReason = rs.getString("DiedReason").trim();

			if( rs.getString("InquiryOperator") == null )
				this.InquiryOperator = null;
			else
				this.InquiryOperator = rs.getString("InquiryOperator").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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
			logger.debug("数据库中的LPLiveInquiry表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPLiveInquirySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LPLiveInquirySchema getSchema()
	{
		LPLiveInquirySchema aLPLiveInquirySchema = new LPLiveInquirySchema();
		aLPLiveInquirySchema.setSchema(this);
		return aLPLiveInquirySchema;
	}

	public LPLiveInquiryDB getDB()
	{
		LPLiveInquiryDB aDBOper = new LPLiveInquiryDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPLiveInquiry描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(InquiryTimes));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InquiryDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InquiryResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DiedDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiedReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InquiryOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLPLiveInquiry>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InquiryTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			InquiryDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3,SysConst.PACKAGESPILTER));
			InquiryResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DiedDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			DiedReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			InquiryOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPLiveInquirySchema";
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("InquiryTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InquiryTimes));
		}
		if (FCode.equalsIgnoreCase("InquiryDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInquiryDate()));
		}
		if (FCode.equalsIgnoreCase("InquiryResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InquiryResult));
		}
		if (FCode.equalsIgnoreCase("DiedDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDiedDate()));
		}
		if (FCode.equalsIgnoreCase("DiedReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiedReason));
		}
		if (FCode.equalsIgnoreCase("InquiryOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InquiryOperator));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = String.valueOf(InquiryTimes);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInquiryDate()));
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InquiryResult);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDiedDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DiedReason);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(InquiryOperator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
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

		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("InquiryTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				InquiryTimes = i;
			}
		}
		if (FCode.equalsIgnoreCase("InquiryDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InquiryDate = fDate.getDate( FValue );
			}
			else
				InquiryDate = null;
		}
		if (FCode.equalsIgnoreCase("InquiryResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InquiryResult = FValue.trim();
			}
			else
				InquiryResult = null;
		}
		if (FCode.equalsIgnoreCase("DiedDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DiedDate = fDate.getDate( FValue );
			}
			else
				DiedDate = null;
		}
		if (FCode.equalsIgnoreCase("DiedReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiedReason = FValue.trim();
			}
			else
				DiedReason = null;
		}
		if (FCode.equalsIgnoreCase("InquiryOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InquiryOperator = FValue.trim();
			}
			else
				InquiryOperator = null;
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
		LPLiveInquirySchema other = (LPLiveInquirySchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& InquiryTimes == other.getInquiryTimes()
			&& fDate.getString(InquiryDate).equals(other.getInquiryDate())
			&& InquiryResult.equals(other.getInquiryResult())
			&& fDate.getString(DiedDate).equals(other.getDiedDate())
			&& DiedReason.equals(other.getDiedReason())
			&& InquiryOperator.equals(other.getInquiryOperator())
			&& Remark.equals(other.getRemark())
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("InquiryTimes") ) {
			return 1;
		}
		if( strFieldName.equals("InquiryDate") ) {
			return 2;
		}
		if( strFieldName.equals("InquiryResult") ) {
			return 3;
		}
		if( strFieldName.equals("DiedDate") ) {
			return 4;
		}
		if( strFieldName.equals("DiedReason") ) {
			return 5;
		}
		if( strFieldName.equals("InquiryOperator") ) {
			return 6;
		}
		if( strFieldName.equals("Remark") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "InquiryTimes";
				break;
			case 2:
				strFieldName = "InquiryDate";
				break;
			case 3:
				strFieldName = "InquiryResult";
				break;
			case 4:
				strFieldName = "DiedDate";
				break;
			case 5:
				strFieldName = "DiedReason";
				break;
			case 6:
				strFieldName = "InquiryOperator";
				break;
			case 7:
				strFieldName = "Remark";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyDate";
				break;
			case 12:
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InquiryTimes") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("InquiryDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InquiryResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiedDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("DiedReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InquiryOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
