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
import com.sinosoft.lis.db.LWConditionDB;

/*
 * <p>ClassName: LWConditionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWConditionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWConditionSchema.class);
	// @Field
	/** 业务类型 */
	private String BusiType;
	/** 转移起点 */
	private String TransitionStart;
	/** 转移终点 */
	private String TransitionEnd;
	/** 可能转向条件 */
	private String TransitionCond;
	/** 条件描述 */
	private String CondDesc;
	/** 转移条件类型 */
	private String TransitionCondT;
	/** 过程id */
	private String ProcessID;
	/** 版本控制 */
	private String Version;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWConditionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "BusiType";
		pk[1] = "TransitionStart";
		pk[2] = "TransitionEnd";
		pk[3] = "ProcessID";
		pk[4] = "Version";

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
		LWConditionSchema cloned = (LWConditionSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBusiType()
	{
		return BusiType;
	}
	public void setBusiType(String aBusiType)
	{
		if(aBusiType!=null && aBusiType.length()>4)
			throw new IllegalArgumentException("业务类型BusiType值"+aBusiType+"的长度"+aBusiType.length()+"大于最大值4");
		BusiType = aBusiType;
	}
	public String getTransitionStart()
	{
		return TransitionStart;
	}
	public void setTransitionStart(String aTransitionStart)
	{
		if(aTransitionStart!=null && aTransitionStart.length()>10)
			throw new IllegalArgumentException("转移起点TransitionStart值"+aTransitionStart+"的长度"+aTransitionStart.length()+"大于最大值10");
		TransitionStart = aTransitionStart;
	}
	public String getTransitionEnd()
	{
		return TransitionEnd;
	}
	public void setTransitionEnd(String aTransitionEnd)
	{
		if(aTransitionEnd!=null && aTransitionEnd.length()>10)
			throw new IllegalArgumentException("转移终点TransitionEnd值"+aTransitionEnd+"的长度"+aTransitionEnd.length()+"大于最大值10");
		TransitionEnd = aTransitionEnd;
	}
	public String getTransitionCond()
	{
		return TransitionCond;
	}
	public void setTransitionCond(String aTransitionCond)
	{
		if(aTransitionCond!=null && aTransitionCond.length()>1000)
			throw new IllegalArgumentException("可能转向条件TransitionCond值"+aTransitionCond+"的长度"+aTransitionCond.length()+"大于最大值1000");
		TransitionCond = aTransitionCond;
	}
	public String getCondDesc()
	{
		return CondDesc;
	}
	public void setCondDesc(String aCondDesc)
	{
		if(aCondDesc!=null && aCondDesc.length()>1000)
			throw new IllegalArgumentException("条件描述CondDesc值"+aCondDesc+"的长度"+aCondDesc.length()+"大于最大值1000");
		CondDesc = aCondDesc;
	}
	/**
	* 0 -- 默认，表示条件为Where子句。<p>
	* 2 --表示转移条件为一个固定的值。
	*/
	public String getTransitionCondT()
	{
		return TransitionCondT;
	}
	public void setTransitionCondT(String aTransitionCondT)
	{
		if(aTransitionCondT!=null && aTransitionCondT.length()>1)
			throw new IllegalArgumentException("转移条件类型TransitionCondT值"+aTransitionCondT+"的长度"+aTransitionCondT.length()+"大于最大值1");
		TransitionCondT = aTransitionCondT;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		if(aProcessID!=null && aProcessID.length()>10)
			throw new IllegalArgumentException("过程idProcessID值"+aProcessID+"的长度"+aProcessID.length()+"大于最大值10");
		ProcessID = aProcessID;
	}
	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String aVersion)
	{
		if(aVersion!=null && aVersion.length()>20)
			throw new IllegalArgumentException("版本控制Version值"+aVersion+"的长度"+aVersion.length()+"大于最大值20");
		Version = aVersion;
	}

	/**
	* 使用另外一个 LWConditionSchema 对象给 Schema 赋值
	* @param: aLWConditionSchema LWConditionSchema
	**/
	public void setSchema(LWConditionSchema aLWConditionSchema)
	{
		this.BusiType = aLWConditionSchema.getBusiType();
		this.TransitionStart = aLWConditionSchema.getTransitionStart();
		this.TransitionEnd = aLWConditionSchema.getTransitionEnd();
		this.TransitionCond = aLWConditionSchema.getTransitionCond();
		this.CondDesc = aLWConditionSchema.getCondDesc();
		this.TransitionCondT = aLWConditionSchema.getTransitionCondT();
		this.ProcessID = aLWConditionSchema.getProcessID();
		this.Version = aLWConditionSchema.getVersion();
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
			if( rs.getString("BusiType") == null )
				this.BusiType = null;
			else
				this.BusiType = rs.getString("BusiType").trim();

			if( rs.getString("TransitionStart") == null )
				this.TransitionStart = null;
			else
				this.TransitionStart = rs.getString("TransitionStart").trim();

			if( rs.getString("TransitionEnd") == null )
				this.TransitionEnd = null;
			else
				this.TransitionEnd = rs.getString("TransitionEnd").trim();

			if( rs.getString("TransitionCond") == null )
				this.TransitionCond = null;
			else
				this.TransitionCond = rs.getString("TransitionCond").trim();

			if( rs.getString("CondDesc") == null )
				this.CondDesc = null;
			else
				this.CondDesc = rs.getString("CondDesc").trim();

			if( rs.getString("TransitionCondT") == null )
				this.TransitionCondT = null;
			else
				this.TransitionCondT = rs.getString("TransitionCondT").trim();

			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

			if( rs.getString("Version") == null )
				this.Version = null;
			else
				this.Version = rs.getString("Version").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWCondition表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWConditionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWConditionSchema getSchema()
	{
		LWConditionSchema aLWConditionSchema = new LWConditionSchema();
		aLWConditionSchema.setSchema(this);
		return aLWConditionSchema;
	}

	public LWConditionDB getDB()
	{
		LWConditionDB aDBOper = new LWConditionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWCondition描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BusiType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionStart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CondDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionCondT)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Version));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWCondition>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BusiType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TransitionStart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TransitionEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TransitionCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CondDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TransitionCondT = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWConditionSchema";
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
		if (FCode.equalsIgnoreCase("BusiType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BusiType));
		}
		if (FCode.equalsIgnoreCase("TransitionStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionStart));
		}
		if (FCode.equalsIgnoreCase("TransitionEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionEnd));
		}
		if (FCode.equalsIgnoreCase("TransitionCond"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionCond));
		}
		if (FCode.equalsIgnoreCase("CondDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CondDesc));
		}
		if (FCode.equalsIgnoreCase("TransitionCondT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionCondT));
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
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
				strFieldValue = StrTool.GBKToUnicode(BusiType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TransitionStart);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TransitionEnd);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TransitionCond);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CondDesc);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TransitionCondT);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Version);
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

		if (FCode.equalsIgnoreCase("BusiType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BusiType = FValue.trim();
			}
			else
				BusiType = null;
		}
		if (FCode.equalsIgnoreCase("TransitionStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionStart = FValue.trim();
			}
			else
				TransitionStart = null;
		}
		if (FCode.equalsIgnoreCase("TransitionEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionEnd = FValue.trim();
			}
			else
				TransitionEnd = null;
		}
		if (FCode.equalsIgnoreCase("TransitionCond"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionCond = FValue.trim();
			}
			else
				TransitionCond = null;
		}
		if (FCode.equalsIgnoreCase("CondDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CondDesc = FValue.trim();
			}
			else
				CondDesc = null;
		}
		if (FCode.equalsIgnoreCase("TransitionCondT"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionCondT = FValue.trim();
			}
			else
				TransitionCondT = null;
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProcessID = FValue.trim();
			}
			else
				ProcessID = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Version = FValue.trim();
			}
			else
				Version = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWConditionSchema other = (LWConditionSchema)otherObject;
		return
			BusiType.equals(other.getBusiType())
			&& TransitionStart.equals(other.getTransitionStart())
			&& TransitionEnd.equals(other.getTransitionEnd())
			&& TransitionCond.equals(other.getTransitionCond())
			&& CondDesc.equals(other.getCondDesc())
			&& TransitionCondT.equals(other.getTransitionCondT())
			&& ProcessID.equals(other.getProcessID())
			&& Version.equals(other.getVersion());
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
		if( strFieldName.equals("BusiType") ) {
			return 0;
		}
		if( strFieldName.equals("TransitionStart") ) {
			return 1;
		}
		if( strFieldName.equals("TransitionEnd") ) {
			return 2;
		}
		if( strFieldName.equals("TransitionCond") ) {
			return 3;
		}
		if( strFieldName.equals("CondDesc") ) {
			return 4;
		}
		if( strFieldName.equals("TransitionCondT") ) {
			return 5;
		}
		if( strFieldName.equals("ProcessID") ) {
			return 6;
		}
		if( strFieldName.equals("Version") ) {
			return 7;
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
				strFieldName = "BusiType";
				break;
			case 1:
				strFieldName = "TransitionStart";
				break;
			case 2:
				strFieldName = "TransitionEnd";
				break;
			case 3:
				strFieldName = "TransitionCond";
				break;
			case 4:
				strFieldName = "CondDesc";
				break;
			case 5:
				strFieldName = "TransitionCondT";
				break;
			case 6:
				strFieldName = "ProcessID";
				break;
			case 7:
				strFieldName = "Version";
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
		if( strFieldName.equals("BusiType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransitionStart") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransitionEnd") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransitionCond") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CondDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransitionCondT") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
