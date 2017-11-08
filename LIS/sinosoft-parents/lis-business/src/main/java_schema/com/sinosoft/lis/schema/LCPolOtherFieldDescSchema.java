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
import com.sinosoft.lis.db.LCPolOtherFieldDescDB;

/*
 * <p>ClassName: LCPolOtherFieldDescSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCPolOtherFieldDescSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPolOtherFieldDescSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任编码 */
	private String DutyCode;
	/** 顺序号 */
	private int FieldOrder;
	/** 源表名 */
	private String SourTableName;
	/** 源字段 */
	private String SourFieldName;
	/** 源字段中文名 */
	private String SourFieldCName;
	/** 目标表名 */
	private String DestTableName;
	/** 目标字段 */
	private String DestFieldName;
	/** 目标字段中文名 */
	private String DestFieldCName;
	/** 是否参与计算 */
	private String CalcuFlag;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPolOtherFieldDescSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "FieldOrder";

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
		LCPolOtherFieldDescSchema cloned = (LCPolOtherFieldDescSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public int getFieldOrder()
	{
		return FieldOrder;
	}
	public void setFieldOrder(int aFieldOrder)
	{
		FieldOrder = aFieldOrder;
	}
	public void setFieldOrder(String aFieldOrder)
	{
		if (aFieldOrder != null && !aFieldOrder.equals(""))
		{
			Integer tInteger = new Integer(aFieldOrder);
			int i = tInteger.intValue();
			FieldOrder = i;
		}
	}

	public String getSourTableName()
	{
		return SourTableName;
	}
	public void setSourTableName(String aSourTableName)
	{
		SourTableName = aSourTableName;
	}
	public String getSourFieldName()
	{
		return SourFieldName;
	}
	public void setSourFieldName(String aSourFieldName)
	{
		SourFieldName = aSourFieldName;
	}
	public String getSourFieldCName()
	{
		return SourFieldCName;
	}
	public void setSourFieldCName(String aSourFieldCName)
	{
		SourFieldCName = aSourFieldCName;
	}
	public String getDestTableName()
	{
		return DestTableName;
	}
	public void setDestTableName(String aDestTableName)
	{
		DestTableName = aDestTableName;
	}
	public String getDestFieldName()
	{
		return DestFieldName;
	}
	public void setDestFieldName(String aDestFieldName)
	{
		DestFieldName = aDestFieldName;
	}
	public String getDestFieldCName()
	{
		return DestFieldCName;
	}
	public void setDestFieldCName(String aDestFieldCName)
	{
		DestFieldCName = aDestFieldCName;
	}
	public String getCalcuFlag()
	{
		return CalcuFlag;
	}
	public void setCalcuFlag(String aCalcuFlag)
	{
		CalcuFlag = aCalcuFlag;
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
	* 使用另外一个 LCPolOtherFieldDescSchema 对象给 Schema 赋值
	* @param: aLCPolOtherFieldDescSchema LCPolOtherFieldDescSchema
	**/
	public void setSchema(LCPolOtherFieldDescSchema aLCPolOtherFieldDescSchema)
	{
		this.RiskCode = aLCPolOtherFieldDescSchema.getRiskCode();
		this.DutyCode = aLCPolOtherFieldDescSchema.getDutyCode();
		this.FieldOrder = aLCPolOtherFieldDescSchema.getFieldOrder();
		this.SourTableName = aLCPolOtherFieldDescSchema.getSourTableName();
		this.SourFieldName = aLCPolOtherFieldDescSchema.getSourFieldName();
		this.SourFieldCName = aLCPolOtherFieldDescSchema.getSourFieldCName();
		this.DestTableName = aLCPolOtherFieldDescSchema.getDestTableName();
		this.DestFieldName = aLCPolOtherFieldDescSchema.getDestFieldName();
		this.DestFieldCName = aLCPolOtherFieldDescSchema.getDestFieldCName();
		this.CalcuFlag = aLCPolOtherFieldDescSchema.getCalcuFlag();
		this.Operator = aLCPolOtherFieldDescSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCPolOtherFieldDescSchema.getMakeDate());
		this.MakeTime = aLCPolOtherFieldDescSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCPolOtherFieldDescSchema.getModifyDate());
		this.ModifyTime = aLCPolOtherFieldDescSchema.getModifyTime();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("DutyCode") == null )
				this.DutyCode = null;
			else
				this.DutyCode = rs.getString("DutyCode").trim();

			this.FieldOrder = rs.getInt("FieldOrder");
			if( rs.getString("SourTableName") == null )
				this.SourTableName = null;
			else
				this.SourTableName = rs.getString("SourTableName").trim();

			if( rs.getString("SourFieldName") == null )
				this.SourFieldName = null;
			else
				this.SourFieldName = rs.getString("SourFieldName").trim();

			if( rs.getString("SourFieldCName") == null )
				this.SourFieldCName = null;
			else
				this.SourFieldCName = rs.getString("SourFieldCName").trim();

			if( rs.getString("DestTableName") == null )
				this.DestTableName = null;
			else
				this.DestTableName = rs.getString("DestTableName").trim();

			if( rs.getString("DestFieldName") == null )
				this.DestFieldName = null;
			else
				this.DestFieldName = rs.getString("DestFieldName").trim();

			if( rs.getString("DestFieldCName") == null )
				this.DestFieldCName = null;
			else
				this.DestFieldCName = rs.getString("DestFieldCName").trim();

			if( rs.getString("CalcuFlag") == null )
				this.CalcuFlag = null;
			else
				this.CalcuFlag = rs.getString("CalcuFlag").trim();

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
			logger.debug("数据库中的LCPolOtherFieldDesc表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolOtherFieldDescSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPolOtherFieldDescSchema getSchema()
	{
		LCPolOtherFieldDescSchema aLCPolOtherFieldDescSchema = new LCPolOtherFieldDescSchema();
		aLCPolOtherFieldDescSchema.setSchema(this);
		return aLCPolOtherFieldDescSchema;
	}

	public LCPolOtherFieldDescDB getDB()
	{
		LCPolOtherFieldDescDB aDBOper = new LCPolOtherFieldDescDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolOtherFieldDesc描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FieldOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourFieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourFieldCName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestTableName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestFieldName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DestFieldCName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalcuFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPolOtherFieldDesc>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FieldOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			SourTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SourFieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SourFieldCName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			DestTableName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DestFieldName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			DestFieldCName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CalcuFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPolOtherFieldDescSchema";
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("DutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DutyCode));
		}
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldOrder));
		}
		if (FCode.equalsIgnoreCase("SourTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourTableName));
		}
		if (FCode.equalsIgnoreCase("SourFieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourFieldName));
		}
		if (FCode.equalsIgnoreCase("SourFieldCName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourFieldCName));
		}
		if (FCode.equalsIgnoreCase("DestTableName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestTableName));
		}
		if (FCode.equalsIgnoreCase("DestFieldName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestFieldName));
		}
		if (FCode.equalsIgnoreCase("DestFieldCName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestFieldCName));
		}
		if (FCode.equalsIgnoreCase("CalcuFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalcuFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(DutyCode);
				break;
			case 2:
				strFieldValue = String.valueOf(FieldOrder);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SourTableName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SourFieldName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SourFieldCName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(DestTableName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DestFieldName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(DestFieldCName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(CalcuFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
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
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FieldOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("SourTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourTableName = FValue.trim();
			}
			else
				SourTableName = null;
		}
		if (FCode.equalsIgnoreCase("SourFieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourFieldName = FValue.trim();
			}
			else
				SourFieldName = null;
		}
		if (FCode.equalsIgnoreCase("SourFieldCName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourFieldCName = FValue.trim();
			}
			else
				SourFieldCName = null;
		}
		if (FCode.equalsIgnoreCase("DestTableName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestTableName = FValue.trim();
			}
			else
				DestTableName = null;
		}
		if (FCode.equalsIgnoreCase("DestFieldName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestFieldName = FValue.trim();
			}
			else
				DestFieldName = null;
		}
		if (FCode.equalsIgnoreCase("DestFieldCName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DestFieldCName = FValue.trim();
			}
			else
				DestFieldCName = null;
		}
		if (FCode.equalsIgnoreCase("CalcuFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalcuFlag = FValue.trim();
			}
			else
				CalcuFlag = null;
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
		LCPolOtherFieldDescSchema other = (LCPolOtherFieldDescSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& FieldOrder == other.getFieldOrder()
			&& SourTableName.equals(other.getSourTableName())
			&& SourFieldName.equals(other.getSourFieldName())
			&& SourFieldCName.equals(other.getSourFieldCName())
			&& DestTableName.equals(other.getDestTableName())
			&& DestFieldName.equals(other.getDestFieldName())
			&& DestFieldCName.equals(other.getDestFieldCName())
			&& CalcuFlag.equals(other.getCalcuFlag())
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("DutyCode") ) {
			return 1;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return 2;
		}
		if( strFieldName.equals("SourTableName") ) {
			return 3;
		}
		if( strFieldName.equals("SourFieldName") ) {
			return 4;
		}
		if( strFieldName.equals("SourFieldCName") ) {
			return 5;
		}
		if( strFieldName.equals("DestTableName") ) {
			return 6;
		}
		if( strFieldName.equals("DestFieldName") ) {
			return 7;
		}
		if( strFieldName.equals("DestFieldCName") ) {
			return 8;
		}
		if( strFieldName.equals("CalcuFlag") ) {
			return 9;
		}
		if( strFieldName.equals("Operator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "DutyCode";
				break;
			case 2:
				strFieldName = "FieldOrder";
				break;
			case 3:
				strFieldName = "SourTableName";
				break;
			case 4:
				strFieldName = "SourFieldName";
				break;
			case 5:
				strFieldName = "SourFieldCName";
				break;
			case 6:
				strFieldName = "DestTableName";
				break;
			case 7:
				strFieldName = "DestFieldName";
				break;
			case 8:
				strFieldName = "DestFieldCName";
				break;
			case 9:
				strFieldName = "CalcuFlag";
				break;
			case 10:
				strFieldName = "Operator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("SourTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourFieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourFieldCName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestTableName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestFieldName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestFieldCName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalcuFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
