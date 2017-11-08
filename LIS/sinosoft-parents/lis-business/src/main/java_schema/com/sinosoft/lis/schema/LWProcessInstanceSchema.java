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
import com.sinosoft.lis.db.LWProcessInstanceDB;

/*
 * <p>ClassName: LWProcessInstanceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 工作流模型
 */
public class LWProcessInstanceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LWProcessInstanceSchema.class);

	// @Field
	/** 转移id */
	private String TransitionID;
	/** 过程id */
	private String ProcessID;
	/** 转移起点 */
	private String TransitionStart;
	/** 转移终点 */
	private String TransitionEnd;
	/** 转移条件 */
	private String TransitionCond;
	/** 转移条件类型 */
	private String TransitionCondT;
	/** 转移时方式 */
	private String TransitionModel;
	/** 起点类型 */
	private String StartType;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LWProcessInstanceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "TransitionID";
		pk[1] = "ProcessID";

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
		LWProcessInstanceSchema cloned = (LWProcessInstanceSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getTransitionID()
	{
		return TransitionID;
	}
	public void setTransitionID(String aTransitionID)
	{
		TransitionID = aTransitionID;
	}
	public String getProcessID()
	{
		return ProcessID;
	}
	public void setProcessID(String aProcessID)
	{
		ProcessID = aProcessID;
	}
	public String getTransitionStart()
	{
		return TransitionStart;
	}
	public void setTransitionStart(String aTransitionStart)
	{
		TransitionStart = aTransitionStart;
	}
	public String getTransitionEnd()
	{
		return TransitionEnd;
	}
	public void setTransitionEnd(String aTransitionEnd)
	{
		TransitionEnd = aTransitionEnd;
	}
	public String getTransitionCond()
	{
		return TransitionCond;
	}
	public void setTransitionCond(String aTransitionCond)
	{
		TransitionCond = aTransitionCond;
	}
	/**
	* 0 -- 默认，表示条件为Where子句。<p>
	* 1 -- 表示转移条件为一个特殊的类。
	*/
	public String getTransitionCondT()
	{
		return TransitionCondT;
	}
	public void setTransitionCondT(String aTransitionCondT)
	{
		TransitionCondT = aTransitionCondT;
	}
	/**
	* 0 表示同一起点的终点间关系为同时执行<p>
	* 1 表示同一起点的终点间关系为允许选择其中的一个执行。(默认值)
	*/
	public String getTransitionModel()
	{
		return TransitionModel;
	}
	public void setTransitionModel(String aTransitionModel)
	{
		TransitionModel = aTransitionModel;
	}
	/**
	* 0 -- 默认根节点<p>
	* 1 -- 根节点，但是为非默认根节点<p>
	* 2 -- 非根节点
	*/
	public String getStartType()
	{
		return StartType;
	}
	public void setStartType(String aStartType)
	{
		StartType = aStartType;
	}

	/**
	* 使用另外一个 LWProcessInstanceSchema 对象给 Schema 赋值
	* @param: aLWProcessInstanceSchema LWProcessInstanceSchema
	**/
	public void setSchema(LWProcessInstanceSchema aLWProcessInstanceSchema)
	{
		this.TransitionID = aLWProcessInstanceSchema.getTransitionID();
		this.ProcessID = aLWProcessInstanceSchema.getProcessID();
		this.TransitionStart = aLWProcessInstanceSchema.getTransitionStart();
		this.TransitionEnd = aLWProcessInstanceSchema.getTransitionEnd();
		this.TransitionCond = aLWProcessInstanceSchema.getTransitionCond();
		this.TransitionCondT = aLWProcessInstanceSchema.getTransitionCondT();
		this.TransitionModel = aLWProcessInstanceSchema.getTransitionModel();
		this.StartType = aLWProcessInstanceSchema.getStartType();
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
			if( rs.getString("TransitionID") == null )
				this.TransitionID = null;
			else
				this.TransitionID = rs.getString("TransitionID").trim();

			if( rs.getString("ProcessID") == null )
				this.ProcessID = null;
			else
				this.ProcessID = rs.getString("ProcessID").trim();

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

			if( rs.getString("TransitionCondT") == null )
				this.TransitionCondT = null;
			else
				this.TransitionCondT = rs.getString("TransitionCondT").trim();

			if( rs.getString("TransitionModel") == null )
				this.TransitionModel = null;
			else
				this.TransitionModel = rs.getString("TransitionModel").trim();

			if( rs.getString("StartType") == null )
				this.StartType = null;
			else
				this.StartType = rs.getString("StartType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LWProcessInstance表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessInstanceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LWProcessInstanceSchema getSchema()
	{
		LWProcessInstanceSchema aLWProcessInstanceSchema = new LWProcessInstanceSchema();
		aLWProcessInstanceSchema.setSchema(this);
		return aLWProcessInstanceSchema;
	}

	public LWProcessInstanceDB getDB()
	{
		LWProcessInstanceDB aDBOper = new LWProcessInstanceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessInstance描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(TransitionID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ProcessID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionStart)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionEnd)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionCondT)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransitionModel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLWProcessInstance>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TransitionID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ProcessID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			TransitionStart = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TransitionEnd = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TransitionCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TransitionCondT = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TransitionModel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StartType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LWProcessInstanceSchema";
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
		if (FCode.equalsIgnoreCase("TransitionID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionID));
		}
		if (FCode.equalsIgnoreCase("ProcessID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProcessID));
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
		if (FCode.equalsIgnoreCase("TransitionCondT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionCondT));
		}
		if (FCode.equalsIgnoreCase("TransitionModel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransitionModel));
		}
		if (FCode.equalsIgnoreCase("StartType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartType));
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
				strFieldValue = StrTool.GBKToUnicode(TransitionID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ProcessID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TransitionStart);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TransitionEnd);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TransitionCond);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TransitionCondT);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TransitionModel);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StartType);
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

		if (FCode.equalsIgnoreCase("TransitionID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionID = FValue.trim();
			}
			else
				TransitionID = null;
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
		if (FCode.equalsIgnoreCase("TransitionCondT"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionCondT = FValue.trim();
			}
			else
				TransitionCondT = null;
		}
		if (FCode.equalsIgnoreCase("TransitionModel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransitionModel = FValue.trim();
			}
			else
				TransitionModel = null;
		}
		if (FCode.equalsIgnoreCase("StartType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartType = FValue.trim();
			}
			else
				StartType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LWProcessInstanceSchema other = (LWProcessInstanceSchema)otherObject;
		return
			TransitionID.equals(other.getTransitionID())
			&& ProcessID.equals(other.getProcessID())
			&& TransitionStart.equals(other.getTransitionStart())
			&& TransitionEnd.equals(other.getTransitionEnd())
			&& TransitionCond.equals(other.getTransitionCond())
			&& TransitionCondT.equals(other.getTransitionCondT())
			&& TransitionModel.equals(other.getTransitionModel())
			&& StartType.equals(other.getStartType());
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
		if( strFieldName.equals("TransitionID") ) {
			return 0;
		}
		if( strFieldName.equals("ProcessID") ) {
			return 1;
		}
		if( strFieldName.equals("TransitionStart") ) {
			return 2;
		}
		if( strFieldName.equals("TransitionEnd") ) {
			return 3;
		}
		if( strFieldName.equals("TransitionCond") ) {
			return 4;
		}
		if( strFieldName.equals("TransitionCondT") ) {
			return 5;
		}
		if( strFieldName.equals("TransitionModel") ) {
			return 6;
		}
		if( strFieldName.equals("StartType") ) {
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
				strFieldName = "TransitionID";
				break;
			case 1:
				strFieldName = "ProcessID";
				break;
			case 2:
				strFieldName = "TransitionStart";
				break;
			case 3:
				strFieldName = "TransitionEnd";
				break;
			case 4:
				strFieldName = "TransitionCond";
				break;
			case 5:
				strFieldName = "TransitionCondT";
				break;
			case 6:
				strFieldName = "TransitionModel";
				break;
			case 7:
				strFieldName = "StartType";
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
		if( strFieldName.equals("TransitionID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProcessID") ) {
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
		if( strFieldName.equals("TransitionCondT") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransitionModel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartType") ) {
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
