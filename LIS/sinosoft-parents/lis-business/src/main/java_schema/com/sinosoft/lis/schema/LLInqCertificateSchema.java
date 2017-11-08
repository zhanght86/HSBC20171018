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
import com.sinosoft.lis.db.LLInqCertificateDB;

/*
 * <p>ClassName: LLInqCertificateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLInqCertificateSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLInqCertificateSchema.class);

	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 调查序号 */
	private String InqNo;
	/** 过程序号 */
	private String CouNo;
	/** 单证序号 */
	private String CerNo;
	/** 单证类型 */
	private String CerType;
	/** 单证名称 */
	private String CerName;
	/** 原件标志 */
	private String OriFlag;
	/** 张数 */
	private int CerCount;
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
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLInqCertificateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "InqNo";
		pk[2] = "CouNo";
		pk[3] = "CerNo";

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
		LLInqCertificateSchema cloned = (LLInqCertificateSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		ClmNo = aClmNo;
	}
	public String getInqNo()
	{
		return InqNo;
	}
	public void setInqNo(String aInqNo)
	{
		InqNo = aInqNo;
	}
	public String getCouNo()
	{
		return CouNo;
	}
	public void setCouNo(String aCouNo)
	{
		CouNo = aCouNo;
	}
	public String getCerNo()
	{
		return CerNo;
	}
	public void setCerNo(String aCerNo)
	{
		CerNo = aCerNo;
	}
	public String getCerType()
	{
		return CerType;
	}
	public void setCerType(String aCerType)
	{
		CerType = aCerType;
	}
	public String getCerName()
	{
		return CerName;
	}
	public void setCerName(String aCerName)
	{
		CerName = aCerName;
	}
	/**
	* [不用]
	*/
	public String getOriFlag()
	{
		return OriFlag;
	}
	public void setOriFlag(String aOriFlag)
	{
		OriFlag = aOriFlag;
	}
	public int getCerCount()
	{
		return CerCount;
	}
	public void setCerCount(int aCerCount)
	{
		CerCount = aCerCount;
	}
	public void setCerCount(String aCerCount)
	{
		if (aCerCount != null && !aCerCount.equals(""))
		{
			Integer tInteger = new Integer(aCerCount);
			int i = tInteger.intValue();
			CerCount = i;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LLInqCertificateSchema 对象给 Schema 赋值
	* @param: aLLInqCertificateSchema LLInqCertificateSchema
	**/
	public void setSchema(LLInqCertificateSchema aLLInqCertificateSchema)
	{
		this.ClmNo = aLLInqCertificateSchema.getClmNo();
		this.InqNo = aLLInqCertificateSchema.getInqNo();
		this.CouNo = aLLInqCertificateSchema.getCouNo();
		this.CerNo = aLLInqCertificateSchema.getCerNo();
		this.CerType = aLLInqCertificateSchema.getCerType();
		this.CerName = aLLInqCertificateSchema.getCerName();
		this.OriFlag = aLLInqCertificateSchema.getOriFlag();
		this.CerCount = aLLInqCertificateSchema.getCerCount();
		this.Operator = aLLInqCertificateSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLInqCertificateSchema.getMakeDate());
		this.MakeTime = aLLInqCertificateSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLInqCertificateSchema.getModifyDate());
		this.ModifyTime = aLLInqCertificateSchema.getModifyTime();
		this.Remark = aLLInqCertificateSchema.getRemark();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("InqNo") == null )
				this.InqNo = null;
			else
				this.InqNo = rs.getString("InqNo").trim();

			if( rs.getString("CouNo") == null )
				this.CouNo = null;
			else
				this.CouNo = rs.getString("CouNo").trim();

			if( rs.getString("CerNo") == null )
				this.CerNo = null;
			else
				this.CerNo = rs.getString("CerNo").trim();

			if( rs.getString("CerType") == null )
				this.CerType = null;
			else
				this.CerType = rs.getString("CerType").trim();

			if( rs.getString("CerName") == null )
				this.CerName = null;
			else
				this.CerName = rs.getString("CerName").trim();

			if( rs.getString("OriFlag") == null )
				this.OriFlag = null;
			else
				this.OriFlag = rs.getString("OriFlag").trim();

			this.CerCount = rs.getInt("CerCount");
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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLInqCertificate表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqCertificateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLInqCertificateSchema getSchema()
	{
		LLInqCertificateSchema aLLInqCertificateSchema = new LLInqCertificateSchema();
		aLLInqCertificateSchema.setSchema(this);
		return aLLInqCertificateSchema;
	}

	public LLInqCertificateDB getDB()
	{
		LLInqCertificateDB aDBOper = new LLInqCertificateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqCertificate描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InqNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CouNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CerType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OriFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CerCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLInqCertificate>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			InqNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CouNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CerType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OriFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CerCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLInqCertificateSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InqNo));
		}
		if (FCode.equalsIgnoreCase("CouNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CouNo));
		}
		if (FCode.equalsIgnoreCase("CerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CerNo));
		}
		if (FCode.equalsIgnoreCase("CerType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CerType));
		}
		if (FCode.equalsIgnoreCase("CerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CerName));
		}
		if (FCode.equalsIgnoreCase("OriFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OriFlag));
		}
		if (FCode.equalsIgnoreCase("CerCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CerCount));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(InqNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CouNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CerType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CerName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OriFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(CerCount);
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
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("InqNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InqNo = FValue.trim();
			}
			else
				InqNo = null;
		}
		if (FCode.equalsIgnoreCase("CouNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CouNo = FValue.trim();
			}
			else
				CouNo = null;
		}
		if (FCode.equalsIgnoreCase("CerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CerNo = FValue.trim();
			}
			else
				CerNo = null;
		}
		if (FCode.equalsIgnoreCase("CerType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CerType = FValue.trim();
			}
			else
				CerType = null;
		}
		if (FCode.equalsIgnoreCase("CerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CerName = FValue.trim();
			}
			else
				CerName = null;
		}
		if (FCode.equalsIgnoreCase("OriFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OriFlag = FValue.trim();
			}
			else
				OriFlag = null;
		}
		if (FCode.equalsIgnoreCase("CerCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CerCount = i;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLInqCertificateSchema other = (LLInqCertificateSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& InqNo.equals(other.getInqNo())
			&& CouNo.equals(other.getCouNo())
			&& CerNo.equals(other.getCerNo())
			&& CerType.equals(other.getCerType())
			&& CerName.equals(other.getCerName())
			&& OriFlag.equals(other.getOriFlag())
			&& CerCount == other.getCerCount()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("InqNo") ) {
			return 1;
		}
		if( strFieldName.equals("CouNo") ) {
			return 2;
		}
		if( strFieldName.equals("CerNo") ) {
			return 3;
		}
		if( strFieldName.equals("CerType") ) {
			return 4;
		}
		if( strFieldName.equals("CerName") ) {
			return 5;
		}
		if( strFieldName.equals("OriFlag") ) {
			return 6;
		}
		if( strFieldName.equals("CerCount") ) {
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
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "InqNo";
				break;
			case 2:
				strFieldName = "CouNo";
				break;
			case 3:
				strFieldName = "CerNo";
				break;
			case 4:
				strFieldName = "CerType";
				break;
			case 5:
				strFieldName = "CerName";
				break;
			case 6:
				strFieldName = "OriFlag";
				break;
			case 7:
				strFieldName = "CerCount";
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
			case 13:
				strFieldName = "Remark";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InqNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CouNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CerType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OriFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CerCount") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
