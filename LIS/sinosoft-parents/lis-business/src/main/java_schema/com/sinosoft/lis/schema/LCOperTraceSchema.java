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
import com.sinosoft.lis.db.LCOperTraceDB;

/*
 * <p>ClassName: LCOperTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCOperTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCOperTraceSchema.class);

	// @Field
	/** 流水号 */
	private String SeriNo;
	/** 类型 */
	private String Type;
	/** 子类型 */
	private String SubType;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 操作动作描述 */
	private String OperDesc;
	/** 提示信息 */
	private String Msg;
	/** 状态标记 */
	private String StateFlag;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String ManageCom;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCOperTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SeriNo";
		pk[1] = "Type";

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
		LCOperTraceSchema cloned = (LCOperTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSeriNo()
	{
		return SeriNo;
	}
	public void setSeriNo(String aSeriNo)
	{
		SeriNo = aSeriNo;
	}
	/**
	* 记录操作的大类，譬如：承保，续期
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
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
	/**
	* 对执行的动作做简单的描述
	*/
	public String getOperDesc()
	{
		return OperDesc;
	}
	public void setOperDesc(String aOperDesc)
	{
		OperDesc = aOperDesc;
	}
	/**
	* 对对动作的执行情况说明
	*/
	public String getMsg()
	{
		return Msg;
	}
	public void setMsg(String aMsg)
	{
		Msg = aMsg;
	}
	/**
	* 暂列如下：<p>
	* 0－正常<p>
	* 1－过程错误，需要解决
	*/
	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		StateFlag = aStateFlag;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	* 使用另外一个 LCOperTraceSchema 对象给 Schema 赋值
	* @param: aLCOperTraceSchema LCOperTraceSchema
	**/
	public void setSchema(LCOperTraceSchema aLCOperTraceSchema)
	{
		this.SeriNo = aLCOperTraceSchema.getSeriNo();
		this.Type = aLCOperTraceSchema.getType();
		this.SubType = aLCOperTraceSchema.getSubType();
		this.MakeDate = fDate.getDate( aLCOperTraceSchema.getMakeDate());
		this.MakeTime = aLCOperTraceSchema.getMakeTime();
		this.OperDesc = aLCOperTraceSchema.getOperDesc();
		this.Msg = aLCOperTraceSchema.getMsg();
		this.StateFlag = aLCOperTraceSchema.getStateFlag();
		this.Operator = aLCOperTraceSchema.getOperator();
		this.ManageCom = aLCOperTraceSchema.getManageCom();
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
			if( rs.getString("SeriNo") == null )
				this.SeriNo = null;
			else
				this.SeriNo = rs.getString("SeriNo").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("OperDesc") == null )
				this.OperDesc = null;
			else
				this.OperDesc = rs.getString("OperDesc").trim();

			if( rs.getString("Msg") == null )
				this.Msg = null;
			else
				this.Msg = rs.getString("Msg").trim();

			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCOperTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCOperTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCOperTraceSchema getSchema()
	{
		LCOperTraceSchema aLCOperTraceSchema = new LCOperTraceSchema();
		aLCOperTraceSchema.setSchema(this);
		return aLCOperTraceSchema;
	}

	public LCOperTraceDB getDB()
	{
		LCOperTraceDB aDBOper = new LCOperTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCOperTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SeriNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperDesc)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Msg)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCOperTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SeriNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OperDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Msg = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCOperTraceSchema";
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
		if (FCode.equalsIgnoreCase("SeriNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SeriNo));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("OperDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperDesc));
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Msg));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(SeriNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OperDesc);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Msg);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equalsIgnoreCase("SeriNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SeriNo = FValue.trim();
			}
			else
				SeriNo = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
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
		if (FCode.equalsIgnoreCase("OperDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperDesc = FValue.trim();
			}
			else
				OperDesc = null;
		}
		if (FCode.equalsIgnoreCase("Msg"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Msg = FValue.trim();
			}
			else
				Msg = null;
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCOperTraceSchema other = (LCOperTraceSchema)otherObject;
		return
			SeriNo.equals(other.getSeriNo())
			&& Type.equals(other.getType())
			&& SubType.equals(other.getSubType())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& OperDesc.equals(other.getOperDesc())
			&& Msg.equals(other.getMsg())
			&& StateFlag.equals(other.getStateFlag())
			&& Operator.equals(other.getOperator())
			&& ManageCom.equals(other.getManageCom());
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
		if( strFieldName.equals("SeriNo") ) {
			return 0;
		}
		if( strFieldName.equals("Type") ) {
			return 1;
		}
		if( strFieldName.equals("SubType") ) {
			return 2;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 3;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 4;
		}
		if( strFieldName.equals("OperDesc") ) {
			return 5;
		}
		if( strFieldName.equals("Msg") ) {
			return 6;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 7;
		}
		if( strFieldName.equals("Operator") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
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
				strFieldName = "SeriNo";
				break;
			case 1:
				strFieldName = "Type";
				break;
			case 2:
				strFieldName = "SubType";
				break;
			case 3:
				strFieldName = "MakeDate";
				break;
			case 4:
				strFieldName = "MakeTime";
				break;
			case 5:
				strFieldName = "OperDesc";
				break;
			case 6:
				strFieldName = "Msg";
				break;
			case 7:
				strFieldName = "StateFlag";
				break;
			case 8:
				strFieldName = "Operator";
				break;
			case 9:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("SeriNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperDesc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Msg") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
