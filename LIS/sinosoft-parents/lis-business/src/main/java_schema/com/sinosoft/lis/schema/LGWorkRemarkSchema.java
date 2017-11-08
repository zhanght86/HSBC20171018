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
import com.sinosoft.lis.db.LGWorkRemarkDB;

/*
 * <p>ClassName: LGWorkRemarkSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统缺失模型
 */
public class LGWorkRemarkSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LGWorkRemarkSchema.class);

	// @Field
	/** Workno */
	private String WorkNo;
	/** Nodeno */
	private String NodeNo;
	/** Remarkno */
	private String RemarkNo;
	/** Remarktypeno */
	private String RemarkTypeNo;
	/** Remarkdate */
	private Date RemarkDate;
	/** Remarktime */
	private String RemarkTime;
	/** Remarkcontent */
	private String RemarkContent;
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

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LGWorkRemarkSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "WorkNo";
		pk[1] = "NodeNo";
		pk[2] = "RemarkNo";

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
		LGWorkRemarkSchema cloned = (LGWorkRemarkSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getWorkNo()
	{
		return WorkNo;
	}
	public void setWorkNo(String aWorkNo)
	{
		WorkNo = aWorkNo;
	}
	public String getNodeNo()
	{
		return NodeNo;
	}
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
	}
	public String getRemarkNo()
	{
		return RemarkNo;
	}
	public void setRemarkNo(String aRemarkNo)
	{
		RemarkNo = aRemarkNo;
	}
	public String getRemarkTypeNo()
	{
		return RemarkTypeNo;
	}
	public void setRemarkTypeNo(String aRemarkTypeNo)
	{
		RemarkTypeNo = aRemarkTypeNo;
	}
	public String getRemarkDate()
	{
		if( RemarkDate != null )
			return fDate.getString(RemarkDate);
		else
			return null;
	}
	public void setRemarkDate(Date aRemarkDate)
	{
		RemarkDate = aRemarkDate;
	}
	public void setRemarkDate(String aRemarkDate)
	{
		if (aRemarkDate != null && !aRemarkDate.equals("") )
		{
			RemarkDate = fDate.getDate( aRemarkDate );
		}
		else
			RemarkDate = null;
	}

	public String getRemarkTime()
	{
		return RemarkTime;
	}
	public void setRemarkTime(String aRemarkTime)
	{
		RemarkTime = aRemarkTime;
	}
	public String getRemarkContent()
	{
		return RemarkContent;
	}
	public void setRemarkContent(String aRemarkContent)
	{
		RemarkContent = aRemarkContent;
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
	* 使用另外一个 LGWorkRemarkSchema 对象给 Schema 赋值
	* @param: aLGWorkRemarkSchema LGWorkRemarkSchema
	**/
	public void setSchema(LGWorkRemarkSchema aLGWorkRemarkSchema)
	{
		this.WorkNo = aLGWorkRemarkSchema.getWorkNo();
		this.NodeNo = aLGWorkRemarkSchema.getNodeNo();
		this.RemarkNo = aLGWorkRemarkSchema.getRemarkNo();
		this.RemarkTypeNo = aLGWorkRemarkSchema.getRemarkTypeNo();
		this.RemarkDate = fDate.getDate( aLGWorkRemarkSchema.getRemarkDate());
		this.RemarkTime = aLGWorkRemarkSchema.getRemarkTime();
		this.RemarkContent = aLGWorkRemarkSchema.getRemarkContent();
		this.Operator = aLGWorkRemarkSchema.getOperator();
		this.MakeDate = fDate.getDate( aLGWorkRemarkSchema.getMakeDate());
		this.MakeTime = aLGWorkRemarkSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLGWorkRemarkSchema.getModifyDate());
		this.ModifyTime = aLGWorkRemarkSchema.getModifyTime();
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
			if( rs.getString("WorkNo") == null )
				this.WorkNo = null;
			else
				this.WorkNo = rs.getString("WorkNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("RemarkNo") == null )
				this.RemarkNo = null;
			else
				this.RemarkNo = rs.getString("RemarkNo").trim();

			if( rs.getString("RemarkTypeNo") == null )
				this.RemarkTypeNo = null;
			else
				this.RemarkTypeNo = rs.getString("RemarkTypeNo").trim();

			this.RemarkDate = rs.getDate("RemarkDate");
			if( rs.getString("RemarkTime") == null )
				this.RemarkTime = null;
			else
				this.RemarkTime = rs.getString("RemarkTime").trim();

			if( rs.getString("RemarkContent") == null )
				this.RemarkContent = null;
			else
				this.RemarkContent = rs.getString("RemarkContent").trim();

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
			logger.debug("数据库中的LGWorkRemark表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGWorkRemarkSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LGWorkRemarkSchema getSchema()
	{
		LGWorkRemarkSchema aLGWorkRemarkSchema = new LGWorkRemarkSchema();
		aLGWorkRemarkSchema.setSchema(this);
		return aLGWorkRemarkSchema;
	}

	public LGWorkRemarkDB getDB()
	{
		LGWorkRemarkDB aDBOper = new LGWorkRemarkDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGWorkRemark描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(WorkNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RemarkNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RemarkTypeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( RemarkDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RemarkTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RemarkContent)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLGWorkRemark>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			WorkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RemarkNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RemarkTypeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RemarkDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			RemarkTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RemarkContent = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LGWorkRemarkSchema";
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
		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WorkNo));
		}
		if (FCode.equalsIgnoreCase("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equalsIgnoreCase("RemarkNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RemarkNo));
		}
		if (FCode.equalsIgnoreCase("RemarkTypeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RemarkTypeNo));
		}
		if (FCode.equalsIgnoreCase("RemarkDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getRemarkDate()));
		}
		if (FCode.equalsIgnoreCase("RemarkTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RemarkTime));
		}
		if (FCode.equalsIgnoreCase("RemarkContent"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RemarkContent));
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
				strFieldValue = StrTool.GBKToUnicode(WorkNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RemarkNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RemarkTypeNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getRemarkDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RemarkTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RemarkContent);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
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

		if (FCode.equalsIgnoreCase("WorkNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WorkNo = FValue.trim();
			}
			else
				WorkNo = null;
		}
		if (FCode.equalsIgnoreCase("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
		}
		if (FCode.equalsIgnoreCase("RemarkNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RemarkNo = FValue.trim();
			}
			else
				RemarkNo = null;
		}
		if (FCode.equalsIgnoreCase("RemarkTypeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RemarkTypeNo = FValue.trim();
			}
			else
				RemarkTypeNo = null;
		}
		if (FCode.equalsIgnoreCase("RemarkDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				RemarkDate = fDate.getDate( FValue );
			}
			else
				RemarkDate = null;
		}
		if (FCode.equalsIgnoreCase("RemarkTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RemarkTime = FValue.trim();
			}
			else
				RemarkTime = null;
		}
		if (FCode.equalsIgnoreCase("RemarkContent"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RemarkContent = FValue.trim();
			}
			else
				RemarkContent = null;
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
		LGWorkRemarkSchema other = (LGWorkRemarkSchema)otherObject;
		return
			WorkNo.equals(other.getWorkNo())
			&& NodeNo.equals(other.getNodeNo())
			&& RemarkNo.equals(other.getRemarkNo())
			&& RemarkTypeNo.equals(other.getRemarkTypeNo())
			&& fDate.getString(RemarkDate).equals(other.getRemarkDate())
			&& RemarkTime.equals(other.getRemarkTime())
			&& RemarkContent.equals(other.getRemarkContent())
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
		if( strFieldName.equals("WorkNo") ) {
			return 0;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 1;
		}
		if( strFieldName.equals("RemarkNo") ) {
			return 2;
		}
		if( strFieldName.equals("RemarkTypeNo") ) {
			return 3;
		}
		if( strFieldName.equals("RemarkDate") ) {
			return 4;
		}
		if( strFieldName.equals("RemarkTime") ) {
			return 5;
		}
		if( strFieldName.equals("RemarkContent") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "WorkNo";
				break;
			case 1:
				strFieldName = "NodeNo";
				break;
			case 2:
				strFieldName = "RemarkNo";
				break;
			case 3:
				strFieldName = "RemarkTypeNo";
				break;
			case 4:
				strFieldName = "RemarkDate";
				break;
			case 5:
				strFieldName = "RemarkTime";
				break;
			case 6:
				strFieldName = "RemarkContent";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
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
		if( strFieldName.equals("WorkNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RemarkNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RemarkTypeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RemarkDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("RemarkTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RemarkContent") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
