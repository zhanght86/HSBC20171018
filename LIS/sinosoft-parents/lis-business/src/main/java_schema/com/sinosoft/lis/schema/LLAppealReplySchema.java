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
import com.sinosoft.lis.db.LLAppealReplyDB;

/*
 * <p>ClassName: LLAppealReplySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLAppealReplySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLAppealReplySchema.class);
	// @Field
	/** 回复人 */
	private String ReplyerNo;
	/** 回复时间 */
	private Date ReplyDate;
	/** 管理机构 */
	private String MngCom;
	/** 处理结论 */
	private String DealResult;
	/** 处理依据 */
	private String DealReson;

	public static final int FIELDNUM = 5;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLAppealReplySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[0];

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
		LLAppealReplySchema cloned = (LLAppealReplySchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getReplyerNo()
	{
		return ReplyerNo;
	}
	public void setReplyerNo(String aReplyerNo)
	{
		if(aReplyerNo!=null && aReplyerNo.length()>6)
			throw new IllegalArgumentException("回复人ReplyerNo值"+aReplyerNo+"的长度"+aReplyerNo.length()+"大于最大值6");
		ReplyerNo = aReplyerNo;
	}
	public String getReplyDate()
	{
		if( ReplyDate != null )
			return fDate.getString(ReplyDate);
		else
			return null;
	}
	public void setReplyDate(Date aReplyDate)
	{
		ReplyDate = aReplyDate;
	}
	public void setReplyDate(String aReplyDate)
	{
		if (aReplyDate != null && !aReplyDate.equals("") )
		{
			ReplyDate = fDate.getDate( aReplyDate );
		}
		else
			ReplyDate = null;
	}

	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>6)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值6");
		MngCom = aMngCom;
	}
	public String getDealResult()
	{
		return DealResult;
	}
	public void setDealResult(String aDealResult)
	{
		if(aDealResult!=null && aDealResult.length()>2)
			throw new IllegalArgumentException("处理结论DealResult值"+aDealResult+"的长度"+aDealResult.length()+"大于最大值2");
		DealResult = aDealResult;
	}
	public String getDealReson()
	{
		return DealReson;
	}
	public void setDealReson(String aDealReson)
	{
		if(aDealReson!=null && aDealReson.length()>2)
			throw new IllegalArgumentException("处理依据DealReson值"+aDealReson+"的长度"+aDealReson.length()+"大于最大值2");
		DealReson = aDealReson;
	}

	/**
	* 使用另外一个 LLAppealReplySchema 对象给 Schema 赋值
	* @param: aLLAppealReplySchema LLAppealReplySchema
	**/
	public void setSchema(LLAppealReplySchema aLLAppealReplySchema)
	{
		this.ReplyerNo = aLLAppealReplySchema.getReplyerNo();
		this.ReplyDate = fDate.getDate( aLLAppealReplySchema.getReplyDate());
		this.MngCom = aLLAppealReplySchema.getMngCom();
		this.DealResult = aLLAppealReplySchema.getDealResult();
		this.DealReson = aLLAppealReplySchema.getDealReson();
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
			if( rs.getString("ReplyerNo") == null )
				this.ReplyerNo = null;
			else
				this.ReplyerNo = rs.getString("ReplyerNo").trim();

			this.ReplyDate = rs.getDate("ReplyDate");
			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

			if( rs.getString("DealResult") == null )
				this.DealResult = null;
			else
				this.DealResult = rs.getString("DealResult").trim();

			if( rs.getString("DealReson") == null )
				this.DealReson = null;
			else
				this.DealReson = rs.getString("DealReson").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLAppealReply表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealReplySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLAppealReplySchema getSchema()
	{
		LLAppealReplySchema aLLAppealReplySchema = new LLAppealReplySchema();
		aLLAppealReplySchema.setSchema(this);
		return aLLAppealReplySchema;
	}

	public LLAppealReplyDB getDB()
	{
		LLAppealReplyDB aDBOper = new LLAppealReplyDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppealReply描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ReplyerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReplyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DealReson));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLAppealReply>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ReplyerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ReplyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2,SysConst.PACKAGESPILTER));
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DealResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DealReson = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLAppealReplySchema";
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
		if (FCode.equalsIgnoreCase("ReplyerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReplyerNo));
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
		}
		if (FCode.equalsIgnoreCase("DealResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealResult));
		}
		if (FCode.equalsIgnoreCase("DealReson"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DealReson));
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
				strFieldValue = StrTool.GBKToUnicode(ReplyerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReplyDate()));
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DealResult);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DealReson);
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

		if (FCode.equalsIgnoreCase("ReplyerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReplyerNo = FValue.trim();
			}
			else
				ReplyerNo = null;
		}
		if (FCode.equalsIgnoreCase("ReplyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReplyDate = fDate.getDate( FValue );
			}
			else
				ReplyDate = null;
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
		}
		if (FCode.equalsIgnoreCase("DealResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealResult = FValue.trim();
			}
			else
				DealResult = null;
		}
		if (FCode.equalsIgnoreCase("DealReson"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DealReson = FValue.trim();
			}
			else
				DealReson = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLAppealReplySchema other = (LLAppealReplySchema)otherObject;
		return
			ReplyerNo.equals(other.getReplyerNo())
			&& fDate.getString(ReplyDate).equals(other.getReplyDate())
			&& MngCom.equals(other.getMngCom())
			&& DealResult.equals(other.getDealResult())
			&& DealReson.equals(other.getDealReson());
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
		if( strFieldName.equals("ReplyerNo") ) {
			return 0;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return 1;
		}
		if( strFieldName.equals("MngCom") ) {
			return 2;
		}
		if( strFieldName.equals("DealResult") ) {
			return 3;
		}
		if( strFieldName.equals("DealReson") ) {
			return 4;
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
				strFieldName = "ReplyerNo";
				break;
			case 1:
				strFieldName = "ReplyDate";
				break;
			case 2:
				strFieldName = "MngCom";
				break;
			case 3:
				strFieldName = "DealResult";
				break;
			case 4:
				strFieldName = "DealReson";
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
		if( strFieldName.equals("ReplyerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReplyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DealReson") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
