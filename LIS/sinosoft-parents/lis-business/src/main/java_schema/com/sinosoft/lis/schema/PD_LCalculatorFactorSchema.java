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
import com.sinosoft.lis.db.PD_LCalculatorFactorDB;

/*
 * <p>ClassName: PD_LCalculatorFactorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 累加器
 */
public class PD_LCalculatorFactorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LCalculatorFactorSchema.class);
	// @Field
	/** 累加器编号 */
	private String CalculatorCode;
	/** 明细流水号 */
	private String SerialNo;
	/** 产品代码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 给付责任代码 */
	private String GetDutyCode;
	/** 给付责任类型 */
	private String GetDutyKind;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 备注1 */
	private String Remark1;
	/** 备注2 */
	private String Remark2;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LCalculatorFactorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CalculatorCode";
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
		PD_LCalculatorFactorSchema cloned = (PD_LCalculatorFactorSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCalculatorCode()
	{
		return CalculatorCode;
	}
	public void setCalculatorCode(String aCalculatorCode)
	{
		if(aCalculatorCode!=null && aCalculatorCode.length()>20)
			throw new IllegalArgumentException("累加器编号CalculatorCode值"+aCalculatorCode+"的长度"+aCalculatorCode.length()+"大于最大值20");
		CalculatorCode = aCalculatorCode;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("明细流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		if(aRiskCode!=null && aRiskCode.length()>15)
			throw new IllegalArgumentException("产品代码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值15");
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		if(aDutyCode!=null && aDutyCode.length()>15)
			throw new IllegalArgumentException("责任代码DutyCode值"+aDutyCode+"的长度"+aDutyCode.length()+"大于最大值15");
		DutyCode = aDutyCode;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		if(aGetDutyCode!=null && aGetDutyCode.length()>15)
			throw new IllegalArgumentException("给付责任代码GetDutyCode值"+aGetDutyCode+"的长度"+aGetDutyCode.length()+"大于最大值15");
		GetDutyCode = aGetDutyCode;
	}
	public String getGetDutyKind()
	{
		return GetDutyKind;
	}
	public void setGetDutyKind(String aGetDutyKind)
	{
		if(aGetDutyKind!=null && aGetDutyKind.length()>15)
			throw new IllegalArgumentException("给付责任类型GetDutyKind值"+aGetDutyKind+"的长度"+aGetDutyKind.length()+"大于最大值15");
		GetDutyKind = aGetDutyKind;
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
		MakeTime = aMakeTime;
	}
	public String getRemark1()
	{
		return Remark1;
	}
	public void setRemark1(String aRemark1)
	{
		if(aRemark1!=null && aRemark1.length()>100)
			throw new IllegalArgumentException("备注1Remark1值"+aRemark1+"的长度"+aRemark1.length()+"大于最大值100");
		Remark1 = aRemark1;
	}
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		if(aRemark2!=null && aRemark2.length()>100)
			throw new IllegalArgumentException("备注2Remark2值"+aRemark2+"的长度"+aRemark2.length()+"大于最大值100");
		Remark2 = aRemark2;
	}

	/**
	* 使用另外一个 PD_LCalculatorFactorSchema 对象给 Schema 赋值
	* @param: aPD_LCalculatorFactorSchema PD_LCalculatorFactorSchema
	**/
	public void setSchema(PD_LCalculatorFactorSchema aPD_LCalculatorFactorSchema)
	{
		this.CalculatorCode = aPD_LCalculatorFactorSchema.getCalculatorCode();
		this.SerialNo = aPD_LCalculatorFactorSchema.getSerialNo();
		this.RiskCode = aPD_LCalculatorFactorSchema.getRiskCode();
		this.DutyCode = aPD_LCalculatorFactorSchema.getDutyCode();
		this.GetDutyCode = aPD_LCalculatorFactorSchema.getGetDutyCode();
		this.GetDutyKind = aPD_LCalculatorFactorSchema.getGetDutyKind();
		this.ModifyDate = fDate.getDate( aPD_LCalculatorFactorSchema.getModifyDate());
		this.ModifyTime = aPD_LCalculatorFactorSchema.getModifyTime();
		this.Operator = aPD_LCalculatorFactorSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LCalculatorFactorSchema.getMakeDate());
		this.MakeTime = aPD_LCalculatorFactorSchema.getMakeTime();
		this.Remark1 = aPD_LCalculatorFactorSchema.getRemark1();
		this.Remark2 = aPD_LCalculatorFactorSchema.getRemark2();
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
			if( rs.getString("CalculatorCode") == null )
				this.CalculatorCode = null;
			else
				this.CalculatorCode = rs.getString("CalculatorCode").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			if( rs.getString("GetDutyKind") == null )
				this.GetDutyKind = null;
			else
				this.GetDutyKind = rs.getString("GetDutyKind").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("Remark1") == null )
				this.Remark1 = null;
			else
				this.Remark1 = rs.getString("Remark1").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的PD_LCalculatorFactor表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorFactorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LCalculatorFactorSchema getSchema()
	{
		PD_LCalculatorFactorSchema aPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
		aPD_LCalculatorFactorSchema.setSchema(this);
		return aPD_LCalculatorFactorSchema;
	}

	public PD_LCalculatorFactorDB getDB()
	{
		PD_LCalculatorFactorDB aDBOper = new PD_LCalculatorFactorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorFactor描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CalculatorCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LCalculatorFactor>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalculatorCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GetDutyKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Remark1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LCalculatorFactorSchema";
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
		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalculatorCode));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyKind));
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark1));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
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
				strFieldValue = StrTool.GBKToUnicode(CalculatorCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GetDutyKind);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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
				strFieldValue = StrTool.GBKToUnicode(Remark1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
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

		if (FCode.equalsIgnoreCase("CalculatorCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalculatorCode = FValue.trim();
			}
			else
				CalculatorCode = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DutyCode = FValue.trim();
			}
			else
				DutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyCode = FValue.trim();
			}
			else
				GetDutyCode = null;
		}
		if (FCode.equalsIgnoreCase("GetDutyKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetDutyKind = FValue.trim();
			}
			else
				GetDutyKind = null;
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
		if (FCode.equalsIgnoreCase("Remark1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark1 = FValue.trim();
			}
			else
				Remark1 = null;
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LCalculatorFactorSchema other = (PD_LCalculatorFactorSchema)otherObject;
		return
			CalculatorCode.equals(other.getCalculatorCode())
			&& SerialNo.equals(other.getSerialNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& GetDutyKind.equals(other.getGetDutyKind())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Remark1.equals(other.getRemark1())
			&& Remark2.equals(other.getRemark2());
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
		if( strFieldName.equals("CalculatorCode") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 3;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 4;
		}
		if( strFieldName.equals("GetDutyKind") ) {
			return 5;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 6;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
		if( strFieldName.equals("Remark1") ) {
			return 11;
		}
		if( strFieldName.equals("Remark2") ) {
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
				strFieldName = "CalculatorCode";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "DutyCode";
				break;
			case 4:
				strFieldName = "GetDutyCode";
				break;
			case 5:
				strFieldName = "GetDutyKind";
				break;
			case 6:
				strFieldName = "ModifyDate";
				break;
			case 7:
				strFieldName = "ModifyTime";
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
				strFieldName = "Remark1";
				break;
			case 12:
				strFieldName = "Remark2";
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
		if( strFieldName.equals("CalculatorCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyKind") ) {
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
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
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