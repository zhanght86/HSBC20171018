

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
import com.sinosoft.lis.db.RIDataModifyLogDB;

/*
 * <p>ClassName: RIDataModifyLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIDataModifyLogSchema implements Schema, Cloneable
{
	// @Field
	/** 序列号 */
	private String SerialNo;
	/** 再保合同号码 */
	private String RIContNo;
	/** 分出责任编号 */
	private String AccumulateDefNo;
	/** 被保险人客户号码 */
	private String InsuredNo;
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 字段代码 */
	private String ClumnCode;
	/** 字段名称 */
	private String ClumnName;
	/** 原始数据 */
	private String ClumnOriginalValue;
	/** 修改数据 */
	private String ClumnModifyValue;
	/** 操作员 */
	private String Operator;
	/** 入机时间 */
	private String MakeTime;
	/** 管理机构 */
	private String ManageCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIDataModifyLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SerialNo";

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
		RIDataModifyLogSchema cloned = (RIDataModifyLogSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public String getRIContNo()
	{
		return RIContNo;
	}
	public void setRIContNo(String aRIContNo)
	{
		RIContNo = aRIContNo;
	}
	public String getAccumulateDefNo()
	{
		return AccumulateDefNo;
	}
	public void setAccumulateDefNo(String aAccumulateDefNo)
	{
		AccumulateDefNo = aAccumulateDefNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		InsuredNo = aInsuredNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getDutyCode()
	{
		return DutyCode;
	}
	public void setDutyCode(String aDutyCode)
	{
		DutyCode = aDutyCode;
	}
	public String getClumnCode()
	{
		return ClumnCode;
	}
	public void setClumnCode(String aClumnCode)
	{
		ClumnCode = aClumnCode;
	}
	public String getClumnName()
	{
		return ClumnName;
	}
	public void setClumnName(String aClumnName)
	{
		ClumnName = aClumnName;
	}
	public String getClumnOriginalValue()
	{
		return ClumnOriginalValue;
	}
	public void setClumnOriginalValue(String aClumnOriginalValue)
	{
		ClumnOriginalValue = aClumnOriginalValue;
	}
	/**
	* 01-固定值 02-区间值 03-分段类加
	*/
	public String getClumnModifyValue()
	{
		return ClumnModifyValue;
	}
	public void setClumnModifyValue(String aClumnModifyValue)
	{
		ClumnModifyValue = aClumnModifyValue;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMakeTime()
	{
		return MakeTime;
	}
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
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
	* 使用另外一个 RIDataModifyLogSchema 对象给 Schema 赋值
	* @param: aRIDataModifyLogSchema RIDataModifyLogSchema
	**/
	public void setSchema(RIDataModifyLogSchema aRIDataModifyLogSchema)
	{
		this.SerialNo = aRIDataModifyLogSchema.getSerialNo();
		this.RIContNo = aRIDataModifyLogSchema.getRIContNo();
		this.AccumulateDefNo = aRIDataModifyLogSchema.getAccumulateDefNo();
		this.InsuredNo = aRIDataModifyLogSchema.getInsuredNo();
		this.RiskCode = aRIDataModifyLogSchema.getRiskCode();
		this.DutyCode = aRIDataModifyLogSchema.getDutyCode();
		this.ClumnCode = aRIDataModifyLogSchema.getClumnCode();
		this.ClumnName = aRIDataModifyLogSchema.getClumnName();
		this.ClumnOriginalValue = aRIDataModifyLogSchema.getClumnOriginalValue();
		this.ClumnModifyValue = aRIDataModifyLogSchema.getClumnModifyValue();
		this.Operator = aRIDataModifyLogSchema.getOperator();
		this.MakeTime = aRIDataModifyLogSchema.getMakeTime();
		this.ManageCom = aRIDataModifyLogSchema.getManageCom();
		this.MakeDate = fDate.getDate( aRIDataModifyLogSchema.getMakeDate());
		this.ModifyDate = fDate.getDate( aRIDataModifyLogSchema.getModifyDate());
		this.ModifyTime = aRIDataModifyLogSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RIContNo") == null )
				this.RIContNo = null;
			else
				this.RIContNo = rs.getString("RIContNo").trim();

			if( rs.getString("AccumulateDefNo") == null )
				this.AccumulateDefNo = null;
			else
				this.AccumulateDefNo = rs.getString("AccumulateDefNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			if( rs.getString("ClumnCode") == null )
				this.ClumnCode = null;
			else
				this.ClumnCode = rs.getString("ClumnCode").trim();

			if( rs.getString("ClumnName") == null )
				this.ClumnName = null;
			else
				this.ClumnName = rs.getString("ClumnName").trim();

			if( rs.getString("ClumnOriginalValue") == null )
				this.ClumnOriginalValue = null;
			else
				this.ClumnOriginalValue = rs.getString("ClumnOriginalValue").trim();

			if( rs.getString("ClumnModifyValue") == null )
				this.ClumnModifyValue = null;
			else
				this.ClumnModifyValue = rs.getString("ClumnModifyValue").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.MakeDate = rs.getDate("MakeDate");
			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIDataModifyLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIDataModifyLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIDataModifyLogSchema getSchema()
	{
		RIDataModifyLogSchema aRIDataModifyLogSchema = new RIDataModifyLogSchema();
		aRIDataModifyLogSchema.setSchema(this);
		return aRIDataModifyLogSchema;
	}

	public RIDataModifyLogDB getDB()
	{
		RIDataModifyLogDB aDBOper = new RIDataModifyLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIDataModifyLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RIContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccumulateDefNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClumnCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClumnName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClumnOriginalValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ClumnModifyValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIDataModifyLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RIContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AccumulateDefNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ClumnCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClumnName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ClumnOriginalValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ClumnModifyValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIDataModifyLogSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RIContNo));
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccumulateDefNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("ClumnCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClumnCode));
		}
		if (FCode.equalsIgnoreCase("ClumnName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClumnName));
		}
		if (FCode.equalsIgnoreCase("ClumnOriginalValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClumnOriginalValue));
		}
		if (FCode.equalsIgnoreCase("ClumnModifyValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClumnModifyValue));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RIContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AccumulateDefNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ClumnCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClumnName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ClumnOriginalValue);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ClumnModifyValue);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("RIContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RIContNo = FValue.trim();
			}
			else
				RIContNo = null;
		}
		if (FCode.equalsIgnoreCase("AccumulateDefNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccumulateDefNo = FValue.trim();
			}
			else
				AccumulateDefNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
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
		if (FCode.equalsIgnoreCase("ClumnCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClumnCode = FValue.trim();
			}
			else
				ClumnCode = null;
		}
		if (FCode.equalsIgnoreCase("ClumnName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClumnName = FValue.trim();
			}
			else
				ClumnName = null;
		}
		if (FCode.equalsIgnoreCase("ClumnOriginalValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClumnOriginalValue = FValue.trim();
			}
			else
				ClumnOriginalValue = null;
		}
		if (FCode.equalsIgnoreCase("ClumnModifyValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClumnModifyValue = FValue.trim();
			}
			else
				ClumnModifyValue = null;
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
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
		RIDataModifyLogSchema other = (RIDataModifyLogSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& RIContNo.equals(other.getRIContNo())
			&& AccumulateDefNo.equals(other.getAccumulateDefNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& ClumnCode.equals(other.getClumnCode())
			&& ClumnName.equals(other.getClumnName())
			&& ClumnOriginalValue.equals(other.getClumnOriginalValue())
			&& ClumnModifyValue.equals(other.getClumnModifyValue())
			&& Operator.equals(other.getOperator())
			&& MakeTime.equals(other.getMakeTime())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("RIContNo") ) {
			return 1;
		}
		if( strFieldName.equals("AccumulateDefNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 3;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 4;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 5;
		}
		if( strFieldName.equals("ClumnCode") ) {
			return 6;
		}
		if( strFieldName.equals("ClumnName") ) {
			return 7;
		}
		if( strFieldName.equals("ClumnOriginalValue") ) {
			return 8;
		}
		if( strFieldName.equals("ClumnModifyValue") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "RIContNo";
				break;
			case 2:
				strFieldName = "AccumulateDefNo";
				break;
			case 3:
				strFieldName = "InsuredNo";
				break;
			case 4:
				strFieldName = "RiskCode";
				break;
			case 5:
				strFieldName = "DutyCode";
				break;
			case 6:
				strFieldName = "ClumnCode";
				break;
			case 7:
				strFieldName = "ClumnName";
				break;
			case 8:
				strFieldName = "ClumnOriginalValue";
				break;
			case 9:
				strFieldName = "ClumnModifyValue";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "MakeDate";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RIContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccumulateDefNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClumnCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClumnName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClumnOriginalValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClumnModifyValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
