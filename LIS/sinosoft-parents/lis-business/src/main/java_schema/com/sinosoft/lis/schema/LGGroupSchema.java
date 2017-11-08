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
import com.sinosoft.lis.db.LGGroupDB;

/*
 * <p>ClassName: LGGroupSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统缺失模型
 */
public class LGGroupSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LGGroupSchema.class);

	// @Field
	/** Groupno */
	private String GroupNo;
	/** Groupname */
	private String GroupName;
	/** Groupinfo */
	private String GroupInfo;
	/** Supergroupno */
	private String SuperGroupNo;
	/** Worktypeno */
	private String WorkTypeNo;
	/** Operator */
	private String Operator;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Modifydate */
	private Date ModifyDate;
	/** Modifytime */
	private String ModifyTime;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LGGroupSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GroupNo";

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
		LGGroupSchema cloned = (LGGroupSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGroupNo()
	{
		return GroupNo;
	}
	public void setGroupNo(String aGroupNo)
	{
		GroupNo = aGroupNo;
	}
	public String getGroupName()
	{
		return GroupName;
	}
	public void setGroupName(String aGroupName)
	{
		GroupName = aGroupName;
	}
	public String getGroupInfo()
	{
		return GroupInfo;
	}
	public void setGroupInfo(String aGroupInfo)
	{
		GroupInfo = aGroupInfo;
	}
	public String getSuperGroupNo()
	{
		return SuperGroupNo;
	}
	public void setSuperGroupNo(String aSuperGroupNo)
	{
		SuperGroupNo = aSuperGroupNo;
	}
	public String getWorkTypeNo()
	{
		return WorkTypeNo;
	}
	public void setWorkTypeNo(String aWorkTypeNo)
	{
		WorkTypeNo = aWorkTypeNo;
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
	* 使用另外一个 LGGroupSchema 对象给 Schema 赋值
	* @param: aLGGroupSchema LGGroupSchema
	**/
	public void setSchema(LGGroupSchema aLGGroupSchema)
	{
		this.GroupNo = aLGGroupSchema.getGroupNo();
		this.GroupName = aLGGroupSchema.getGroupName();
		this.GroupInfo = aLGGroupSchema.getGroupInfo();
		this.SuperGroupNo = aLGGroupSchema.getSuperGroupNo();
		this.WorkTypeNo = aLGGroupSchema.getWorkTypeNo();
		this.Operator = aLGGroupSchema.getOperator();
		this.MakeDate = fDate.getDate( aLGGroupSchema.getMakeDate());
		this.MakeTime = aLGGroupSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLGGroupSchema.getModifyDate());
		this.ModifyTime = aLGGroupSchema.getModifyTime();
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
			if( rs.getString("GroupNo") == null )
				this.GroupNo = null;
			else
				this.GroupNo = rs.getString("GroupNo").trim();

			if( rs.getString("GroupName") == null )
				this.GroupName = null;
			else
				this.GroupName = rs.getString("GroupName").trim();

			if( rs.getString("GroupInfo") == null )
				this.GroupInfo = null;
			else
				this.GroupInfo = rs.getString("GroupInfo").trim();

			if( rs.getString("SuperGroupNo") == null )
				this.SuperGroupNo = null;
			else
				this.SuperGroupNo = rs.getString("SuperGroupNo").trim();

			if( rs.getString("WorkTypeNo") == null )
				this.WorkTypeNo = null;
			else
				this.WorkTypeNo = rs.getString("WorkTypeNo").trim();

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
			logger.debug("数据库中的LGGroup表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGGroupSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LGGroupSchema getSchema()
	{
		LGGroupSchema aLGGroupSchema = new LGGroupSchema();
		aLGGroupSchema.setSchema(this);
		return aLGGroupSchema;
	}

	public LGGroupDB getDB()
	{
		LGGroupDB aDBOper = new LGGroupDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGGroup描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GroupName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GroupInfo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SuperGroupNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(WorkTypeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGGroup>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GroupName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GroupInfo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SuperGroupNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			WorkTypeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGGroupSchema";
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
		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupNo));
		}
		if (FCode.equalsIgnoreCase("GroupName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupName));
		}
		if (FCode.equalsIgnoreCase("GroupInfo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupInfo));
		}
		if (FCode.equalsIgnoreCase("SuperGroupNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuperGroupNo));
		}
		if (FCode.equalsIgnoreCase("WorkTypeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkTypeNo));
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
				strFieldValue = StrTool.GBKToUnicode(GroupNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GroupName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GroupInfo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SuperGroupNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(WorkTypeNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("GroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupNo = FValue.trim();
			}
			else
				GroupNo = null;
		}
		if (FCode.equalsIgnoreCase("GroupName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupName = FValue.trim();
			}
			else
				GroupName = null;
		}
		if (FCode.equalsIgnoreCase("GroupInfo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupInfo = FValue.trim();
			}
			else
				GroupInfo = null;
		}
		if (FCode.equalsIgnoreCase("SuperGroupNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SuperGroupNo = FValue.trim();
			}
			else
				SuperGroupNo = null;
		}
		if (FCode.equalsIgnoreCase("WorkTypeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkTypeNo = FValue.trim();
			}
			else
				WorkTypeNo = null;
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
		LGGroupSchema other = (LGGroupSchema)otherObject;
		return
			GroupNo.equals(other.getGroupNo())
			&& GroupName.equals(other.getGroupName())
			&& GroupInfo.equals(other.getGroupInfo())
			&& SuperGroupNo.equals(other.getSuperGroupNo())
			&& WorkTypeNo.equals(other.getWorkTypeNo())
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
		if( strFieldName.equals("GroupNo") ) {
			return 0;
		}
		if( strFieldName.equals("GroupName") ) {
			return 1;
		}
		if( strFieldName.equals("GroupInfo") ) {
			return 2;
		}
		if( strFieldName.equals("SuperGroupNo") ) {
			return 3;
		}
		if( strFieldName.equals("WorkTypeNo") ) {
			return 4;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "GroupNo";
				break;
			case 1:
				strFieldName = "GroupName";
				break;
			case 2:
				strFieldName = "GroupInfo";
				break;
			case 3:
				strFieldName = "SuperGroupNo";
				break;
			case 4:
				strFieldName = "WorkTypeNo";
				break;
			case 5:
				strFieldName = "Operator";
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
		if( strFieldName.equals("GroupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GroupName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GroupInfo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SuperGroupNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WorkTypeNo") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
