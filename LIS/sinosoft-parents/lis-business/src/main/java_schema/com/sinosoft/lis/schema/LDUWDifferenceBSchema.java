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
import com.sinosoft.lis.db.LDUWDifferenceBDB;

/*
 * <p>ClassName: LDUWDifferenceBSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDUWDifferenceBSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWDifferenceBSchema.class);

	// @Field
	/** 差异化类别 */
	private String DiffType;
	/** 流水号 */
	private String SerialNo;
	/** 机构代码 */
	private String ManageCom;
	/** 机构核保类别 */
	private String ManaUWType;
	/** 业务员代码 */
	private String AgentCode;
	/** 业务员核保类别 */
	private String UWClass;
	/** 差异化级别 */
	private String UWLevel;
	/** 操作员 */
	private String Operator;
	/** Makedate */
	private Date MakeDate;
	/** Maketime */
	private String MakeTime;
	/** Makedate2 */
	private Date MakeDate2;
	/** Maketime2 */
	private String MakeTime2;
	/** 备用属性1 */
	private String StandByFlag1;
	/** 备用属性2 */
	private String StandByFlag2;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWDifferenceBSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "DiffType";
		pk[1] = "SerialNo";

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
		LDUWDifferenceBSchema cloned = (LDUWDifferenceBSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 0-地区差异化<p>
	* 1-品质差异化
	*/
	public String getDiffType()
	{
		return DiffType;
	}
	public void setDiffType(String aDiffType)
	{
		DiffType = aDiffType;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	/**
	* DiffType为0时记录机构代码，为1时记录业务员代码
	*/
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getManaUWType()
	{
		return ManaUWType;
	}
	public void setManaUWType(String aManaUWType)
	{
		ManaUWType = aManaUWType;
	}
	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public String getUWClass()
	{
		return UWClass;
	}
	public void setUWClass(String aUWClass)
	{
		UWClass = aUWClass;
	}
	public String getUWLevel()
	{
		return UWLevel;
	}
	public void setUWLevel(String aUWLevel)
	{
		UWLevel = aUWLevel;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/**
	* 该字段为A表的MakeDate
	*/
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

	/**
	* 该字段为A表的MakeTime
	*/
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/**
	* 本条记录生成日期
	*/
	public String getMakeDate2()
	{
		if( MakeDate2 != null )
			return fDate.getString(MakeDate2);
		else
			return null;
	}
	public void setMakeDate2(Date aMakeDate2)
	{
		MakeDate2 = aMakeDate2;
	}
	public void setMakeDate2(String aMakeDate2)
	{
		if (aMakeDate2 != null && !aMakeDate2.equals("") )
		{
			MakeDate2 = fDate.getDate( aMakeDate2 );
		}
		else
			MakeDate2 = null;
	}

	/**
	* 本条记录生成时间
	*/
	public String getMakeTime2()
	{
		return MakeTime2;
	}
	public void setMakeTime2(String aMakeTime2)
	{
		MakeTime2 = aMakeTime2;
	}
	public String getStandByFlag1()
	{
		return StandByFlag1;
	}
	public void setStandByFlag1(String aStandByFlag1)
	{
		StandByFlag1 = aStandByFlag1;
	}
	public String getStandByFlag2()
	{
		return StandByFlag2;
	}
	public void setStandByFlag2(String aStandByFlag2)
	{
		StandByFlag2 = aStandByFlag2;
	}

	/**
	* 使用另外一个 LDUWDifferenceBSchema 对象给 Schema 赋值
	* @param: aLDUWDifferenceBSchema LDUWDifferenceBSchema
	**/
	public void setSchema(LDUWDifferenceBSchema aLDUWDifferenceBSchema)
	{
		this.DiffType = aLDUWDifferenceBSchema.getDiffType();
		this.SerialNo = aLDUWDifferenceBSchema.getSerialNo();
		this.ManageCom = aLDUWDifferenceBSchema.getManageCom();
		this.ManaUWType = aLDUWDifferenceBSchema.getManaUWType();
		this.AgentCode = aLDUWDifferenceBSchema.getAgentCode();
		this.UWClass = aLDUWDifferenceBSchema.getUWClass();
		this.UWLevel = aLDUWDifferenceBSchema.getUWLevel();
		this.Operator = aLDUWDifferenceBSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDUWDifferenceBSchema.getMakeDate());
		this.MakeTime = aLDUWDifferenceBSchema.getMakeTime();
		this.MakeDate2 = fDate.getDate( aLDUWDifferenceBSchema.getMakeDate2());
		this.MakeTime2 = aLDUWDifferenceBSchema.getMakeTime2();
		this.StandByFlag1 = aLDUWDifferenceBSchema.getStandByFlag1();
		this.StandByFlag2 = aLDUWDifferenceBSchema.getStandByFlag2();
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
			if( rs.getString("DiffType") == null )
				this.DiffType = null;
			else
				this.DiffType = rs.getString("DiffType").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ManaUWType") == null )
				this.ManaUWType = null;
			else
				this.ManaUWType = rs.getString("ManaUWType").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("UWClass") == null )
				this.UWClass = null;
			else
				this.UWClass = rs.getString("UWClass").trim();

			if( rs.getString("UWLevel") == null )
				this.UWLevel = null;
			else
				this.UWLevel = rs.getString("UWLevel").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate2 = rs.getDate("MakeDate2");
			if( rs.getString("MakeTime2") == null )
				this.MakeTime2 = null;
			else
				this.MakeTime2 = rs.getString("MakeTime2").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWDifferenceB表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWDifferenceBSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWDifferenceBSchema getSchema()
	{
		LDUWDifferenceBSchema aLDUWDifferenceBSchema = new LDUWDifferenceBSchema();
		aLDUWDifferenceBSchema.setSchema(this);
		return aLDUWDifferenceBSchema;
	}

	public LDUWDifferenceBDB getDB()
	{
		LDUWDifferenceBDB aDBOper = new LDUWDifferenceBDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWDifferenceB描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DiffType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManaUWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate2 ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWDifferenceB>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DiffType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManaUWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UWClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate2 = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWDifferenceBSchema";
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
		if (FCode.equalsIgnoreCase("DiffType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiffType));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ManaUWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManaUWType));
		}
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("UWClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWClass));
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWLevel));
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
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime2));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(DiffType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManaUWType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UWClass);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UWLevel);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate2()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime2);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
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

		if (FCode.equalsIgnoreCase("DiffType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiffType = FValue.trim();
			}
			else
				DiffType = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
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
		if (FCode.equalsIgnoreCase("ManaUWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManaUWType = FValue.trim();
			}
			else
				ManaUWType = null;
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
		if (FCode.equalsIgnoreCase("UWClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWClass = FValue.trim();
			}
			else
				UWClass = null;
		}
		if (FCode.equalsIgnoreCase("UWLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWLevel = FValue.trim();
			}
			else
				UWLevel = null;
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
		if (FCode.equalsIgnoreCase("MakeDate2"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate2 = fDate.getDate( FValue );
			}
			else
				MakeDate2 = null;
		}
		if (FCode.equalsIgnoreCase("MakeTime2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime2 = FValue.trim();
			}
			else
				MakeTime2 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag1 = FValue.trim();
			}
			else
				StandByFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag2 = FValue.trim();
			}
			else
				StandByFlag2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWDifferenceBSchema other = (LDUWDifferenceBSchema)otherObject;
		return
			DiffType.equals(other.getDiffType())
			&& SerialNo.equals(other.getSerialNo())
			&& ManageCom.equals(other.getManageCom())
			&& ManaUWType.equals(other.getManaUWType())
			&& AgentCode.equals(other.getAgentCode())
			&& UWClass.equals(other.getUWClass())
			&& UWLevel.equals(other.getUWLevel())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate2).equals(other.getMakeDate2())
			&& MakeTime2.equals(other.getMakeTime2())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2());
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
		if( strFieldName.equals("DiffType") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 2;
		}
		if( strFieldName.equals("ManaUWType") ) {
			return 3;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 4;
		}
		if( strFieldName.equals("UWClass") ) {
			return 5;
		}
		if( strFieldName.equals("UWLevel") ) {
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
		if( strFieldName.equals("MakeDate2") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return 11;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 12;
		}
		if( strFieldName.equals("StandByFlag2") ) {
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
				strFieldName = "DiffType";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "ManageCom";
				break;
			case 3:
				strFieldName = "ManaUWType";
				break;
			case 4:
				strFieldName = "AgentCode";
				break;
			case 5:
				strFieldName = "UWClass";
				break;
			case 6:
				strFieldName = "UWLevel";
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
				strFieldName = "MakeDate2";
				break;
			case 11:
				strFieldName = "MakeTime2";
				break;
			case 12:
				strFieldName = "StandByFlag1";
				break;
			case 13:
				strFieldName = "StandByFlag2";
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
		if( strFieldName.equals("DiffType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManaUWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWLevel") ) {
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
		if( strFieldName.equals("MakeDate2") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
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
