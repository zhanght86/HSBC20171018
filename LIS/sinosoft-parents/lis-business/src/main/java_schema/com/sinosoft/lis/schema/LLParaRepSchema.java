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
import com.sinosoft.lis.db.LLParaRepDB;

/*
 * <p>ClassName: LLParaRepSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLParaRepSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLParaRepSchema.class);

	// @Field
	/** 报表编号 */
	private String RepID;
	/** 报表名称 */
	private String RepName;
	/** 报表类型 */
	private String RepType;
	/** 开始日期 */
	private String StartDay;
	/** 终止日期 */
	private String EndDay;
	/** 排序序号 */
	private int SortID;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLParaRepSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RepID";

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
		LLParaRepSchema cloned = (LLParaRepSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRepID()
	{
		return RepID;
	}
	public void setRepID(String aRepID)
	{
		RepID = aRepID;
	}
	public String getRepName()
	{
		return RepName;
	}
	public void setRepName(String aRepName)
	{
		RepName = aRepName;
	}
	/**
	* 12--年报表<p>
	* 6--半年报表<p>
	* 3--季度报表<p>
	* 1--月报表<p>
	* 0--不定时间
	*/
	public String getRepType()
	{
		return RepType;
	}
	public void setRepType(String aRepType)
	{
		RepType = aRepType;
	}
	public String getStartDay()
	{
		return StartDay;
	}
	public void setStartDay(String aStartDay)
	{
		StartDay = aStartDay;
	}
	public String getEndDay()
	{
		return EndDay;
	}
	public void setEndDay(String aEndDay)
	{
		EndDay = aEndDay;
	}
	public int getSortID()
	{
		return SortID;
	}
	public void setSortID(int aSortID)
	{
		SortID = aSortID;
	}
	public void setSortID(String aSortID)
	{
		if (aSortID != null && !aSortID.equals(""))
		{
			Integer tInteger = new Integer(aSortID);
			int i = tInteger.intValue();
			SortID = i;
		}
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
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	* 使用另外一个 LLParaRepSchema 对象给 Schema 赋值
	* @param: aLLParaRepSchema LLParaRepSchema
	**/
	public void setSchema(LLParaRepSchema aLLParaRepSchema)
	{
		this.RepID = aLLParaRepSchema.getRepID();
		this.RepName = aLLParaRepSchema.getRepName();
		this.RepType = aLLParaRepSchema.getRepType();
		this.StartDay = aLLParaRepSchema.getStartDay();
		this.EndDay = aLLParaRepSchema.getEndDay();
		this.SortID = aLLParaRepSchema.getSortID();
		this.MakeDate = fDate.getDate( aLLParaRepSchema.getMakeDate());
		this.MakeTime = aLLParaRepSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLParaRepSchema.getModifyDate());
		this.ModifyTime = aLLParaRepSchema.getModifyTime();
		this.Operator = aLLParaRepSchema.getOperator();
		this.Remark = aLLParaRepSchema.getRemark();
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
			if( rs.getString("RepID") == null )
				this.RepID = null;
			else
				this.RepID = rs.getString("RepID").trim();

			if( rs.getString("RepName") == null )
				this.RepName = null;
			else
				this.RepName = rs.getString("RepName").trim();

			if( rs.getString("RepType") == null )
				this.RepType = null;
			else
				this.RepType = rs.getString("RepType").trim();

			if( rs.getString("StartDay") == null )
				this.StartDay = null;
			else
				this.StartDay = rs.getString("StartDay").trim();

			if( rs.getString("EndDay") == null )
				this.EndDay = null;
			else
				this.EndDay = rs.getString("EndDay").trim();

			this.SortID = rs.getInt("SortID");
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

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLParaRep表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaRepSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLParaRepSchema getSchema()
	{
		LLParaRepSchema aLLParaRepSchema = new LLParaRepSchema();
		aLLParaRepSchema.setSchema(this);
		return aLLParaRepSchema;
	}

	public LLParaRepDB getDB()
	{
		LLParaRepDB aDBOper = new LLParaRepDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaRep描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RepID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RepName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RepType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StartDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EndDay)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SortID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLParaRep>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RepID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RepName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RepType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StartDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			EndDay = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SortID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLParaRepSchema";
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
		if (FCode.equalsIgnoreCase("RepID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepID));
		}
		if (FCode.equalsIgnoreCase("RepName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepName));
		}
		if (FCode.equalsIgnoreCase("RepType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepType));
		}
		if (FCode.equalsIgnoreCase("StartDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StartDay));
		}
		if (FCode.equalsIgnoreCase("EndDay"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDay));
		}
		if (FCode.equalsIgnoreCase("SortID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SortID));
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(RepID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RepName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RepType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(StartDay);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(EndDay);
				break;
			case 5:
				strFieldValue = String.valueOf(SortID);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
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

		if (FCode.equalsIgnoreCase("RepID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepID = FValue.trim();
			}
			else
				RepID = null;
		}
		if (FCode.equalsIgnoreCase("RepName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepName = FValue.trim();
			}
			else
				RepName = null;
		}
		if (FCode.equalsIgnoreCase("RepType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepType = FValue.trim();
			}
			else
				RepType = null;
		}
		if (FCode.equalsIgnoreCase("StartDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StartDay = FValue.trim();
			}
			else
				StartDay = null;
		}
		if (FCode.equalsIgnoreCase("EndDay"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EndDay = FValue.trim();
			}
			else
				EndDay = null;
		}
		if (FCode.equalsIgnoreCase("SortID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SortID = i;
			}
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
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		LLParaRepSchema other = (LLParaRepSchema)otherObject;
		return
			RepID.equals(other.getRepID())
			&& RepName.equals(other.getRepName())
			&& RepType.equals(other.getRepType())
			&& StartDay.equals(other.getStartDay())
			&& EndDay.equals(other.getEndDay())
			&& SortID == other.getSortID()
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("RepID") ) {
			return 0;
		}
		if( strFieldName.equals("RepName") ) {
			return 1;
		}
		if( strFieldName.equals("RepType") ) {
			return 2;
		}
		if( strFieldName.equals("StartDay") ) {
			return 3;
		}
		if( strFieldName.equals("EndDay") ) {
			return 4;
		}
		if( strFieldName.equals("SortID") ) {
			return 5;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "RepID";
				break;
			case 1:
				strFieldName = "RepName";
				break;
			case 2:
				strFieldName = "RepType";
				break;
			case 3:
				strFieldName = "StartDay";
				break;
			case 4:
				strFieldName = "EndDay";
				break;
			case 5:
				strFieldName = "SortID";
				break;
			case 6:
				strFieldName = "MakeDate";
				break;
			case 7:
				strFieldName = "MakeTime";
				break;
			case 8:
				strFieldName = "ModifyDate";
				break;
			case 9:
				strFieldName = "ModifyTime";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
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
		if( strFieldName.equals("RepID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RepName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RepType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDay") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SortID") ) {
			return Schema.TYPE_INT;
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
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
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
