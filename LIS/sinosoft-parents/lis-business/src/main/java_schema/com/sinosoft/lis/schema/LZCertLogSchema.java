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
import com.sinosoft.lis.db.LZCertLogDB;

/*
 * <p>ClassName: LZCertLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCertLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCertLogSchema.class);

	// @Field
	/** 日志流水号 */
	private String LogSeq;
	/** 回收清算单号 */
	private String TakeBackNo;
	/** 单证编码 */
	private String CertifyCode;
	/** 发放机构 */
	private String SendOutCom;
	/** 接收机构 */
	private String ReceiveCom;
	/** 起始号 */
	private String StartNo;
	/** 终止号 */
	private String EndNo;
	/** 数量 */
	private int SumCount;
	/** 日志内容 */
	private String LogContent;

	public static final int FIELDNUM = 9;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCertLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogSeq";

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
		LZCertLogSchema cloned = (LZCertLogSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getLogSeq()
	{
		return LogSeq;
	}
	public void setLogSeq(String aLogSeq)
	{
		LogSeq = aLogSeq;
	}
	public String getTakeBackNo()
	{
		return TakeBackNo;
	}
	public void setTakeBackNo(String aTakeBackNo)
	{
		TakeBackNo = aTakeBackNo;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getSendOutCom()
	{
		return SendOutCom;
	}
	public void setSendOutCom(String aSendOutCom)
	{
		SendOutCom = aSendOutCom;
	}
	public String getReceiveCom()
	{
		return ReceiveCom;
	}
	public void setReceiveCom(String aReceiveCom)
	{
		ReceiveCom = aReceiveCom;
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
	public int getSumCount()
	{
		return SumCount;
	}
	public void setSumCount(int aSumCount)
	{
		SumCount = aSumCount;
	}
	public void setSumCount(String aSumCount)
	{
		if (aSumCount != null && !aSumCount.equals(""))
		{
			Integer tInteger = new Integer(aSumCount);
			int i = tInteger.intValue();
			SumCount = i;
		}
	}

	public String getLogContent()
	{
		return LogContent;
	}
	public void setLogContent(String aLogContent)
	{
		LogContent = aLogContent;
	}

	/**
	* 使用另外一个 LZCertLogSchema 对象给 Schema 赋值
	* @param: aLZCertLogSchema LZCertLogSchema
	**/
	public void setSchema(LZCertLogSchema aLZCertLogSchema)
	{
		this.LogSeq = aLZCertLogSchema.getLogSeq();
		this.TakeBackNo = aLZCertLogSchema.getTakeBackNo();
		this.CertifyCode = aLZCertLogSchema.getCertifyCode();
		this.SendOutCom = aLZCertLogSchema.getSendOutCom();
		this.ReceiveCom = aLZCertLogSchema.getReceiveCom();
		this.StartNo = aLZCertLogSchema.getStartNo();
		this.EndNo = aLZCertLogSchema.getEndNo();
		this.SumCount = aLZCertLogSchema.getSumCount();
		this.LogContent = aLZCertLogSchema.getLogContent();
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
			if( rs.getString("LogSeq") == null )
				this.LogSeq = null;
			else
				this.LogSeq = rs.getString("LogSeq").trim();

			if( rs.getString("TakeBackNo") == null )
				this.TakeBackNo = null;
			else
				this.TakeBackNo = rs.getString("TakeBackNo").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("SendOutCom") == null )
				this.SendOutCom = null;
			else
				this.SendOutCom = rs.getString("SendOutCom").trim();

			if( rs.getString("ReceiveCom") == null )
				this.ReceiveCom = null;
			else
				this.ReceiveCom = rs.getString("ReceiveCom").trim();

			if( rs.getString("StartNo") == null )
				this.StartNo = null;
			else
				this.StartNo = rs.getString("StartNo").trim();

			if( rs.getString("EndNo") == null )
				this.EndNo = null;
			else
				this.EndNo = rs.getString("EndNo").trim();

			this.SumCount = rs.getInt("SumCount");
			if( rs.getString("LogContent") == null )
				this.LogContent = null;
			else
				this.LogContent = rs.getString("LogContent").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCertLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCertLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCertLogSchema getSchema()
	{
		LZCertLogSchema aLZCertLogSchema = new LZCertLogSchema();
		aLZCertLogSchema.setSchema(this);
		return aLZCertLogSchema;
	}

	public LZCertLogDB getDB()
	{
		LZCertLogDB aDBOper = new LZCertLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCertLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(LogSeq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TakeBackNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SendOutCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReceiveCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SumCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LogContent));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCertLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogSeq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TakeBackNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SendOutCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReceiveCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			StartNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			EndNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SumCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			LogContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCertLogSchema";
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
		if (FCode.equalsIgnoreCase("LogSeq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogSeq));
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TakeBackNo));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SendOutCom));
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReceiveCom));
		}
		if (FCode.equalsIgnoreCase("StartNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartNo));
		}
		if (FCode.equalsIgnoreCase("EndNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndNo));
		}
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SumCount));
		}
		if (FCode.equalsIgnoreCase("LogContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogContent));
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
				strFieldValue = StrTool.GBKToUnicode(LogSeq);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TakeBackNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SendOutCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReceiveCom);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StartNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(EndNo);
				break;
			case 7:
				strFieldValue = String.valueOf(SumCount);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(LogContent);
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

		if (FCode.equalsIgnoreCase("LogSeq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogSeq = FValue.trim();
			}
			else
				LogSeq = null;
		}
		if (FCode.equalsIgnoreCase("TakeBackNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TakeBackNo = FValue.trim();
			}
			else
				TakeBackNo = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("SendOutCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SendOutCom = FValue.trim();
			}
			else
				SendOutCom = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReceiveCom = FValue.trim();
			}
			else
				ReceiveCom = null;
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
		if (FCode.equalsIgnoreCase("SumCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SumCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("LogContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LogContent = FValue.trim();
			}
			else
				LogContent = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCertLogSchema other = (LZCertLogSchema)otherObject;
		return
			LogSeq.equals(other.getLogSeq())
			&& TakeBackNo.equals(other.getTakeBackNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& SendOutCom.equals(other.getSendOutCom())
			&& ReceiveCom.equals(other.getReceiveCom())
			&& StartNo.equals(other.getStartNo())
			&& EndNo.equals(other.getEndNo())
			&& SumCount == other.getSumCount()
			&& LogContent.equals(other.getLogContent());
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
		if( strFieldName.equals("LogSeq") ) {
			return 0;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return 1;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 2;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return 3;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return 4;
		}
		if( strFieldName.equals("StartNo") ) {
			return 5;
		}
		if( strFieldName.equals("EndNo") ) {
			return 6;
		}
		if( strFieldName.equals("SumCount") ) {
			return 7;
		}
		if( strFieldName.equals("LogContent") ) {
			return 8;
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
				strFieldName = "LogSeq";
				break;
			case 1:
				strFieldName = "TakeBackNo";
				break;
			case 2:
				strFieldName = "CertifyCode";
				break;
			case 3:
				strFieldName = "SendOutCom";
				break;
			case 4:
				strFieldName = "ReceiveCom";
				break;
			case 5:
				strFieldName = "StartNo";
				break;
			case 6:
				strFieldName = "EndNo";
				break;
			case 7:
				strFieldName = "SumCount";
				break;
			case 8:
				strFieldName = "LogContent";
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
		if( strFieldName.equals("LogSeq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TakeBackNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SendOutCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReceiveCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SumCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("LogContent") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
