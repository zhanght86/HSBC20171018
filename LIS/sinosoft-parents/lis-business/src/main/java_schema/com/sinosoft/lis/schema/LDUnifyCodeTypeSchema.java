/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.io.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LDUnifyCodeTypeDB;
import org.apache.log4j.Logger;

/*
 * <p>ClassName: LDUnifyCodeTypeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2011-12-30
 */
public class LDUnifyCodeTypeSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(LDUnifyCodeTypeSchema.class);

	// @Field
	/** 系统编码 */
	private String SysCode;
	/** 编码类型 */
	private String CodeType;
	/** 编码类型名称 */
	private String CodeTypeName;
	/** 状态 */
	private String State;
	/** 可维护标志 */
	private String MaintainFlag;
	/** 编码类型描述 */
	private String CodeTypeDescription;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次操作日期 */
	private Date ModifyDate;
	/** 最后一次操作时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUnifyCodeTypeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SysCode";
		pk[1] = "CodeType";

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
                LDUnifyCodeTypeSchema cloned = (LDUnifyCodeTypeSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSysCode()
	{
		return SysCode;
	}
	public void setSysCode(String aSysCode)
	{
		SysCode = aSysCode;
	}
	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	public String getCodeTypeName()
	{
		return CodeTypeName;
	}
	public void setCodeTypeName(String aCodeTypeName)
	{
		CodeTypeName = aCodeTypeName;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getMaintainFlag()
	{
		return MaintainFlag;
	}
	public void setMaintainFlag(String aMaintainFlag)
	{
		MaintainFlag = aMaintainFlag;
	}
	public String getCodeTypeDescription()
	{
		return CodeTypeDescription;
	}
	public void setCodeTypeDescription(String aCodeTypeDescription)
	{
		CodeTypeDescription = aCodeTypeDescription;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 使用另外一个 LDUnifyCodeTypeSchema 对象给 Schema 赋值
	* @param: aLDUnifyCodeTypeSchema LDUnifyCodeTypeSchema
	**/
	public void setSchema(LDUnifyCodeTypeSchema aLDUnifyCodeTypeSchema)
	{
		this.SysCode = aLDUnifyCodeTypeSchema.getSysCode();
		this.CodeType = aLDUnifyCodeTypeSchema.getCodeType();
		this.CodeTypeName = aLDUnifyCodeTypeSchema.getCodeTypeName();
		this.State = aLDUnifyCodeTypeSchema.getState();
		this.MaintainFlag = aLDUnifyCodeTypeSchema.getMaintainFlag();
		this.CodeTypeDescription = aLDUnifyCodeTypeSchema.getCodeTypeDescription();
		this.ManageCom = aLDUnifyCodeTypeSchema.getManageCom();
		this.Operator = aLDUnifyCodeTypeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDUnifyCodeTypeSchema.getMakeDate());
		this.MakeTime = aLDUnifyCodeTypeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDUnifyCodeTypeSchema.getModifyDate());
		this.ModifyTime = aLDUnifyCodeTypeSchema.getModifyTime();
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
			if( rs.getString("SysCode") == null )
				this.SysCode = null;
			else
				this.SysCode = rs.getString("SysCode").trim();

			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("CodeTypeName") == null )
				this.CodeTypeName = null;
			else
				this.CodeTypeName = rs.getString("CodeTypeName").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("MaintainFlag") == null )
				this.MaintainFlag = null;
			else
				this.MaintainFlag = rs.getString("MaintainFlag").trim();

			if( rs.getString("CodeTypeDescription") == null )
				this.CodeTypeDescription = null;
			else
				this.CodeTypeDescription = rs.getString("CodeTypeDescription").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			logger.debug("数据库中的LDUnifyCodeType表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUnifyCodeTypeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUnifyCodeTypeSchema getSchema()
	{
		LDUnifyCodeTypeSchema aLDUnifyCodeTypeSchema = new LDUnifyCodeTypeSchema();
		aLDUnifyCodeTypeSchema.setSchema(this);
		return aLDUnifyCodeTypeSchema;
	}

	public LDUnifyCodeTypeDB getDB()
	{
		LDUnifyCodeTypeDB aDBOper = new LDUnifyCodeTypeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUnifyCodeType描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(SysCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CodeTypeName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MaintainFlag)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(CodeTypeDescription)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUnifyCodeType>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SysCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeTypeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MaintainFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CodeTypeDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "LDUnifyCodeTypeSchema";
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
		if (FCode.equalsIgnoreCase("SysCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysCode));
		}
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("CodeTypeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeTypeName));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("MaintainFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaintainFlag));
		}
		if (FCode.equalsIgnoreCase("CodeTypeDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeTypeDescription));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = StrTool.GBKToUnicode(SysCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeTypeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(MaintainFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CodeTypeDescription);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equalsIgnoreCase("SysCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysCode = FValue.trim();
			}
			else
				SysCode = null;
		}
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("CodeTypeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeTypeName = FValue.trim();
			}
			else
				CodeTypeName = null;
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
		if (FCode.equalsIgnoreCase("MaintainFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaintainFlag = FValue.trim();
			}
			else
				MaintainFlag = null;
		}
		if (FCode.equalsIgnoreCase("CodeTypeDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeTypeDescription = FValue.trim();
			}
			else
				CodeTypeDescription = null;
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
		LDUnifyCodeTypeSchema other = (LDUnifyCodeTypeSchema)otherObject;
		return
			SysCode.equals(other.getSysCode())
			&& CodeType.equals(other.getCodeType())
			&& CodeTypeName.equals(other.getCodeTypeName())
			&& State.equals(other.getState())
			&& MaintainFlag.equals(other.getMaintainFlag())
			&& CodeTypeDescription.equals(other.getCodeTypeDescription())
			&& ManageCom.equals(other.getManageCom())
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
		if( strFieldName.equals("SysCode") ) {
			return 0;
		}
		if( strFieldName.equals("CodeType") ) {
			return 1;
		}
		if( strFieldName.equals("CodeTypeName") ) {
			return 2;
		}
		if( strFieldName.equals("State") ) {
			return 3;
		}
		if( strFieldName.equals("MaintainFlag") ) {
			return 4;
		}
		if( strFieldName.equals("CodeTypeDescription") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				strFieldName = "SysCode";
				break;
			case 1:
				strFieldName = "CodeType";
				break;
			case 2:
				strFieldName = "CodeTypeName";
				break;
			case 3:
				strFieldName = "State";
				break;
			case 4:
				strFieldName = "MaintainFlag";
				break;
			case 5:
				strFieldName = "CodeTypeDescription";
				break;
			case 6:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("SysCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeTypeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaintainFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeTypeDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
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
