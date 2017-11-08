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
import com.sinosoft.lis.db.LCHospitalQualityRecordDB;

/*
 * <p>ClassName: LCHospitalQualityRecordSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCHospitalQualityRecordSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCHospitalQualityRecordSchema.class);

	// @Field
	/** 体检医院编码 */
	private String HospitCode;
	/** 合同号 */
	private String ContNo;
	/** 品质记录 */
	private String QualityFlag;
	/** 备注信息 */
	private String Remark;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 备用属性1 */
	private String StandByFlag1;
	/** 备用属性2 */
	private String StandByFlag2;
	/** 体检医院品质 */
	private String HospitalQuality;
	/** 体检医院管理岗品质 */
	private String ManagerQuality;
	/** 陪检内勤品质 */
	private String InsideQuality;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCHospitalQualityRecordSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "HospitCode";
		pk[1] = "ContNo";

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
		LCHospitalQualityRecordSchema cloned = (LCHospitalQualityRecordSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getHospitCode()
	{
		return HospitCode;
	}
	public void setHospitCode(String aHospitCode)
	{
		HospitCode = aHospitCode;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getQualityFlag()
	{
		return QualityFlag;
	}
	public void setQualityFlag(String aQualityFlag)
	{
		QualityFlag = aQualityFlag;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
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
	public String getHospitalQuality()
	{
		return HospitalQuality;
	}
	public void setHospitalQuality(String aHospitalQuality)
	{
		HospitalQuality = aHospitalQuality;
	}
	public String getManagerQuality()
	{
		return ManagerQuality;
	}
	public void setManagerQuality(String aManagerQuality)
	{
		ManagerQuality = aManagerQuality;
	}
	public String getInsideQuality()
	{
		return InsideQuality;
	}
	public void setInsideQuality(String aInsideQuality)
	{
		InsideQuality = aInsideQuality;
	}

	/**
	* 使用另外一个 LCHospitalQualityRecordSchema 对象给 Schema 赋值
	* @param: aLCHospitalQualityRecordSchema LCHospitalQualityRecordSchema
	**/
	public void setSchema(LCHospitalQualityRecordSchema aLCHospitalQualityRecordSchema)
	{
		this.HospitCode = aLCHospitalQualityRecordSchema.getHospitCode();
		this.ContNo = aLCHospitalQualityRecordSchema.getContNo();
		this.QualityFlag = aLCHospitalQualityRecordSchema.getQualityFlag();
		this.Remark = aLCHospitalQualityRecordSchema.getRemark();
		this.Operator = aLCHospitalQualityRecordSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCHospitalQualityRecordSchema.getMakeDate());
		this.MakeTime = aLCHospitalQualityRecordSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCHospitalQualityRecordSchema.getModifyDate());
		this.ModifyTime = aLCHospitalQualityRecordSchema.getModifyTime();
		this.StandByFlag1 = aLCHospitalQualityRecordSchema.getStandByFlag1();
		this.StandByFlag2 = aLCHospitalQualityRecordSchema.getStandByFlag2();
		this.HospitalQuality = aLCHospitalQualityRecordSchema.getHospitalQuality();
		this.ManagerQuality = aLCHospitalQualityRecordSchema.getManagerQuality();
		this.InsideQuality = aLCHospitalQualityRecordSchema.getInsideQuality();
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
			if( rs.getString("HospitCode") == null )
				this.HospitCode = null;
			else
				this.HospitCode = rs.getString("HospitCode").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("QualityFlag") == null )
				this.QualityFlag = null;
			else
				this.QualityFlag = rs.getString("QualityFlag").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

			if( rs.getString("HospitalQuality") == null )
				this.HospitalQuality = null;
			else
				this.HospitalQuality = rs.getString("HospitalQuality").trim();

			if( rs.getString("ManagerQuality") == null )
				this.ManagerQuality = null;
			else
				this.ManagerQuality = rs.getString("ManagerQuality").trim();

			if( rs.getString("InsideQuality") == null )
				this.InsideQuality = null;
			else
				this.InsideQuality = rs.getString("InsideQuality").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCHospitalQualityRecord表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCHospitalQualityRecordSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCHospitalQualityRecordSchema getSchema()
	{
		LCHospitalQualityRecordSchema aLCHospitalQualityRecordSchema = new LCHospitalQualityRecordSchema();
		aLCHospitalQualityRecordSchema.setSchema(this);
		return aLCHospitalQualityRecordSchema;
	}

	public LCHospitalQualityRecordDB getDB()
	{
		LCHospitalQualityRecordDB aDBOper = new LCHospitalQualityRecordDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCHospitalQualityRecord描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(HospitCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(QualityFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalQuality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManagerQuality)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsideQuality));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCHospitalQualityRecord>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			HospitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			QualityFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			HospitalQuality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManagerQuality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsideQuality = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCHospitalQualityRecordSchema";
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
		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitCode));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("QualityFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QualityFlag));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
		}
		if (FCode.equalsIgnoreCase("HospitalQuality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalQuality));
		}
		if (FCode.equalsIgnoreCase("ManagerQuality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManagerQuality));
		}
		if (FCode.equalsIgnoreCase("InsideQuality"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsideQuality));
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
				strFieldValue = StrTool.GBKToUnicode(HospitCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(QualityFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(HospitalQuality);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManagerQuality);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsideQuality);
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

		if (FCode.equalsIgnoreCase("HospitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitCode = FValue.trim();
			}
			else
				HospitCode = null;
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("QualityFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QualityFlag = FValue.trim();
			}
			else
				QualityFlag = null;
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
		if (FCode.equalsIgnoreCase("HospitalQuality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalQuality = FValue.trim();
			}
			else
				HospitalQuality = null;
		}
		if (FCode.equalsIgnoreCase("ManagerQuality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManagerQuality = FValue.trim();
			}
			else
				ManagerQuality = null;
		}
		if (FCode.equalsIgnoreCase("InsideQuality"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsideQuality = FValue.trim();
			}
			else
				InsideQuality = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCHospitalQualityRecordSchema other = (LCHospitalQualityRecordSchema)otherObject;
		return
			HospitCode.equals(other.getHospitCode())
			&& ContNo.equals(other.getContNo())
			&& QualityFlag.equals(other.getQualityFlag())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
			&& HospitalQuality.equals(other.getHospitalQuality())
			&& ManagerQuality.equals(other.getManagerQuality())
			&& InsideQuality.equals(other.getInsideQuality());
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
		if( strFieldName.equals("HospitCode") ) {
			return 0;
		}
		if( strFieldName.equals("ContNo") ) {
			return 1;
		}
		if( strFieldName.equals("QualityFlag") ) {
			return 2;
		}
		if( strFieldName.equals("Remark") ) {
			return 3;
		}
		if( strFieldName.equals("Operator") ) {
			return 4;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 5;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 7;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 8;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 9;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 10;
		}
		if( strFieldName.equals("HospitalQuality") ) {
			return 11;
		}
		if( strFieldName.equals("ManagerQuality") ) {
			return 12;
		}
		if( strFieldName.equals("InsideQuality") ) {
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
				strFieldName = "HospitCode";
				break;
			case 1:
				strFieldName = "ContNo";
				break;
			case 2:
				strFieldName = "QualityFlag";
				break;
			case 3:
				strFieldName = "Remark";
				break;
			case 4:
				strFieldName = "Operator";
				break;
			case 5:
				strFieldName = "MakeDate";
				break;
			case 6:
				strFieldName = "MakeTime";
				break;
			case 7:
				strFieldName = "ModifyDate";
				break;
			case 8:
				strFieldName = "ModifyTime";
				break;
			case 9:
				strFieldName = "StandByFlag1";
				break;
			case 10:
				strFieldName = "StandByFlag2";
				break;
			case 11:
				strFieldName = "HospitalQuality";
				break;
			case 12:
				strFieldName = "ManagerQuality";
				break;
			case 13:
				strFieldName = "InsideQuality";
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
		if( strFieldName.equals("HospitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QualityFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalQuality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManagerQuality") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsideQuality") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
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
