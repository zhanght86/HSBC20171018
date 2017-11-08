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
import com.sinosoft.lis.db.NACheckAscriptDB;

/*
 * <p>ClassName: NACheckAscriptSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class NACheckAscriptSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(NACheckAscriptSchema.class);

	// @Field
	/** 批改流水号 */
	private String EdorNo;
	/** 考核年月 */
	private String IndexCalNo;
	/** 被归属代理人 */
	private String AgentCode;
	/** 原职级 */
	private String AgentGrade;
	/** 建议职级 */
	private String AgentGrade1;
	/** 类型 */
	private String RelaType;
	/** 相关代理人 */
	private String RAgentCode;
	/** 机构级别 */
	private String tLevel;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 机构编码 */
	private String AgentGroup;
	/** 状态 */
	private String State;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public NACheckAscriptSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "EdorNo";

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
		NACheckAscriptSchema cloned = (NACheckAscriptSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getIndexCalNo()
	{
		return IndexCalNo;
	}
	public void setIndexCalNo(String aIndexCalNo)
	{
		IndexCalNo = aIndexCalNo;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getAgentGrade()
	{
		return AgentGrade;
	}
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	public String getAgentGrade1()
	{
		return AgentGrade1;
	}
	public void setAgentGrade1(String aAgentGrade1)
	{
		AgentGrade1 = aAgentGrade1;
	}
	/**
	* 01--晋升归属时其上级主管(即育成人)；<p>
	* <p>
	* 02--晋升归属带走人员；<p>
	* <p>
	* 03--降级归属目标机构；<p>
	* <p>
	* 04--降级归属要回归人员；
	*/
	public String getRelaType()
	{
		return RelaType;
	}
	public void setRelaType(String aRelaType)
	{
		RelaType = aRelaType;
	}
	/**
	* 当RelaType为01时：晋升归属时其上级主管(即育成人)；<p>
	* <p>
	* 当RelaType为02时：晋升归属带走人员；<p>
	* <p>
	* 当RelaType为03时：降级归属目标机构主管(不一定存在)；<p>
	* <p>
	* 当RelaType为04时：降级归属要回归人员；
	*/
	public String getRAgentCode()
	{
		return RAgentCode;
	}
	public void setRAgentCode(String aRAgentCode)
	{
		RAgentCode = aRAgentCode;
	}
	/**
	* 00--组员<p>
	* 01--组<p>
	* <p>
	* 02--部<p>
	* <p>
	* 03--区<p>
	* <p>
	* 04--督导
	*/
	public String gettLevel()
	{
		return tLevel;
	}
	public void settLevel(String atLevel)
	{
		tLevel = atLevel;
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
	/**
	* 当RelaType为01时：晋升归属时其上级主管(即育成人)所在机构；<p>
	* 当RelaType为02时：晋升归属带走人员所在机构；<p>
	* 当RelaType为03时：降级归属目标机构；<p>
	* <p>
	* 当RelaType为04时：降级归属要回归人员所在机构；
	*/
	public String getAgentGroup()
	{
		return AgentGroup;
	}
	public void setAgentGroup(String aAgentGroup)
	{
		AgentGroup = aAgentGroup;
	}
	/**
	* 0--组织归属前保留信息；<p>
	* 1--组织归属后核对正确设置为1；
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}

	/**
	* 使用另外一个 NACheckAscriptSchema 对象给 Schema 赋值
	* @param: aNACheckAscriptSchema NACheckAscriptSchema
	**/
	public void setSchema(NACheckAscriptSchema aNACheckAscriptSchema)
	{
		this.EdorNo = aNACheckAscriptSchema.getEdorNo();
		this.IndexCalNo = aNACheckAscriptSchema.getIndexCalNo();
		this.AgentCode = aNACheckAscriptSchema.getAgentCode();
		this.AgentGrade = aNACheckAscriptSchema.getAgentGrade();
		this.AgentGrade1 = aNACheckAscriptSchema.getAgentGrade1();
		this.RelaType = aNACheckAscriptSchema.getRelaType();
		this.RAgentCode = aNACheckAscriptSchema.getRAgentCode();
		this.tLevel = aNACheckAscriptSchema.gettLevel();
		this.Operator = aNACheckAscriptSchema.getOperator();
		this.MakeDate = fDate.getDate( aNACheckAscriptSchema.getMakeDate());
		this.MakeTime = aNACheckAscriptSchema.getMakeTime();
		this.AgentGroup = aNACheckAscriptSchema.getAgentGroup();
		this.State = aNACheckAscriptSchema.getState();
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
			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("IndexCalNo") == null )
				this.IndexCalNo = null;
			else
				this.IndexCalNo = rs.getString("IndexCalNo").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("AgentGrade1") == null )
				this.AgentGrade1 = null;
			else
				this.AgentGrade1 = rs.getString("AgentGrade1").trim();

			if( rs.getString("RelaType") == null )
				this.RelaType = null;
			else
				this.RelaType = rs.getString("RelaType").trim();

			if( rs.getString("RAgentCode") == null )
				this.RAgentCode = null;
			else
				this.RAgentCode = rs.getString("RAgentCode").trim();

			if( rs.getString("tLevel") == null )
				this.tLevel = null;
			else
				this.tLevel = rs.getString("tLevel").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("AgentGroup") == null )
				this.AgentGroup = null;
			else
				this.AgentGroup = rs.getString("AgentGroup").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的NACheckAscript表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NACheckAscriptSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public NACheckAscriptSchema getSchema()
	{
		NACheckAscriptSchema aNACheckAscriptSchema = new NACheckAscriptSchema();
		aNACheckAscriptSchema.setSchema(this);
		return aNACheckAscriptSchema;
	}

	public NACheckAscriptDB getDB()
	{
		NACheckAscriptDB aDBOper = new NACheckAscriptDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNACheckAscript描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IndexCalNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGrade1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RAgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(tLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentGroup)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNACheckAscript>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			IndexCalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentGrade1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RAgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			tLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentGroup = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NACheckAscriptSchema";
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
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IndexCalNo));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade1));
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaType));
		}
		if (FCode.equalsIgnoreCase("RAgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RAgentCode));
		}
		if (FCode.equalsIgnoreCase("tLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(tLevel));
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGroup));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(IndexCalNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RelaType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RAgentCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(tLevel);
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
				strFieldValue = StrTool.GBKToUnicode(AgentGroup);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(State);
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

		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("IndexCalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IndexCalNo = FValue.trim();
			}
			else
				IndexCalNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
		}
		if (FCode.equalsIgnoreCase("AgentGrade1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade1 = FValue.trim();
			}
			else
				AgentGrade1 = null;
		}
		if (FCode.equalsIgnoreCase("RelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaType = FValue.trim();
			}
			else
				RelaType = null;
		}
		if (FCode.equalsIgnoreCase("RAgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RAgentCode = FValue.trim();
			}
			else
				RAgentCode = null;
		}
		if (FCode.equalsIgnoreCase("tLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				tLevel = FValue.trim();
			}
			else
				tLevel = null;
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
		if (FCode.equalsIgnoreCase("AgentGroup"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGroup = FValue.trim();
			}
			else
				AgentGroup = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		NACheckAscriptSchema other = (NACheckAscriptSchema)otherObject;
		return
			EdorNo.equals(other.getEdorNo())
			&& IndexCalNo.equals(other.getIndexCalNo())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentGrade.equals(other.getAgentGrade())
			&& AgentGrade1.equals(other.getAgentGrade1())
			&& RelaType.equals(other.getRelaType())
			&& RAgentCode.equals(other.getRAgentCode())
			&& tLevel.equals(other.gettLevel())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& AgentGroup.equals(other.getAgentGroup())
			&& State.equals(other.getState());
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
		if( strFieldName.equals("EdorNo") ) {
			return 0;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return 1;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 2;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 3;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return 4;
		}
		if( strFieldName.equals("RelaType") ) {
			return 5;
		}
		if( strFieldName.equals("RAgentCode") ) {
			return 6;
		}
		if( strFieldName.equals("tLevel") ) {
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
		if( strFieldName.equals("AgentGroup") ) {
			return 11;
		}
		if( strFieldName.equals("State") ) {
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
				strFieldName = "EdorNo";
				break;
			case 1:
				strFieldName = "IndexCalNo";
				break;
			case 2:
				strFieldName = "AgentCode";
				break;
			case 3:
				strFieldName = "AgentGrade";
				break;
			case 4:
				strFieldName = "AgentGrade1";
				break;
			case 5:
				strFieldName = "RelaType";
				break;
			case 6:
				strFieldName = "RAgentCode";
				break;
			case 7:
				strFieldName = "tLevel";
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
				strFieldName = "AgentGroup";
				break;
			case 12:
				strFieldName = "State";
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
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IndexCalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RAgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("tLevel") ) {
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
		if( strFieldName.equals("AgentGroup") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_STRING;
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
