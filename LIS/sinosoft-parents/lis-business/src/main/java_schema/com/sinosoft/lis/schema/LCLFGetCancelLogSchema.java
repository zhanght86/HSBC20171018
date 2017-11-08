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
import com.sinosoft.lis.db.LCLFGetCancelLogDB;

/*
 * <p>ClassName: LCLFGetCancelLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保全核心
 */
public class LCLFGetCancelLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCLFGetCancelLogSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 保单号 */
	private String PolNo;
	/** 给付代码 */
	private String GetDutyCode;
	/** 原领取日期 */
	private Date OldGetDate;
	/** 原领取金额 */
	private double OldGetMoney;
	/** 原操作员 */
	private String OldOperator;
	/** 原数据入机日期 */
	private Date OldMakeDate;
	/** 原数据入机时间 */
	private String OldMakeTime;
	/** 管理机构 */
	private String ManageCom;
	/** 删除者 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 原因描述 */
	private String Remark;
	/** 合同号 */
	private String ContNo;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCLFGetCancelLogSchema()
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
		LCLFGetCancelLogSchema cloned = (LCLFGetCancelLogSchema)super.clone();
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
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		PolNo = aPolNo;
	}
	public String getGetDutyCode()
	{
		return GetDutyCode;
	}
	public void setGetDutyCode(String aGetDutyCode)
	{
		GetDutyCode = aGetDutyCode;
	}
	public String getOldGetDate()
	{
		if( OldGetDate != null )
			return fDate.getString(OldGetDate);
		else
			return null;
	}
	public void setOldGetDate(Date aOldGetDate)
	{
		OldGetDate = aOldGetDate;
	}
	public void setOldGetDate(String aOldGetDate)
	{
		if (aOldGetDate != null && !aOldGetDate.equals("") )
		{
			OldGetDate = fDate.getDate( aOldGetDate );
		}
		else
			OldGetDate = null;
	}

	public double getOldGetMoney()
	{
		return OldGetMoney;
	}
	public void setOldGetMoney(double aOldGetMoney)
	{
		OldGetMoney = aOldGetMoney;
	}
	public void setOldGetMoney(String aOldGetMoney)
	{
		if (aOldGetMoney != null && !aOldGetMoney.equals(""))
		{
			Double tDouble = new Double(aOldGetMoney);
			double d = tDouble.doubleValue();
			OldGetMoney = d;
		}
	}

	public String getOldOperator()
	{
		return OldOperator;
	}
	public void setOldOperator(String aOldOperator)
	{
		OldOperator = aOldOperator;
	}
	public String getOldMakeDate()
	{
		if( OldMakeDate != null )
			return fDate.getString(OldMakeDate);
		else
			return null;
	}
	public void setOldMakeDate(Date aOldMakeDate)
	{
		OldMakeDate = aOldMakeDate;
	}
	public void setOldMakeDate(String aOldMakeDate)
	{
		if (aOldMakeDate != null && !aOldMakeDate.equals("") )
		{
			OldMakeDate = fDate.getDate( aOldMakeDate );
		}
		else
			OldMakeDate = null;
	}

	public String getOldMakeTime()
	{
		return OldMakeTime;
	}
	public void setOldMakeTime(String aOldMakeTime)
	{
		OldMakeTime = aOldMakeTime;
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
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}

	/**
	* 使用另外一个 LCLFGetCancelLogSchema 对象给 Schema 赋值
	* @param: aLCLFGetCancelLogSchema LCLFGetCancelLogSchema
	**/
	public void setSchema(LCLFGetCancelLogSchema aLCLFGetCancelLogSchema)
	{
		this.SerialNo = aLCLFGetCancelLogSchema.getSerialNo();
		this.PolNo = aLCLFGetCancelLogSchema.getPolNo();
		this.GetDutyCode = aLCLFGetCancelLogSchema.getGetDutyCode();
		this.OldGetDate = fDate.getDate( aLCLFGetCancelLogSchema.getOldGetDate());
		this.OldGetMoney = aLCLFGetCancelLogSchema.getOldGetMoney();
		this.OldOperator = aLCLFGetCancelLogSchema.getOldOperator();
		this.OldMakeDate = fDate.getDate( aLCLFGetCancelLogSchema.getOldMakeDate());
		this.OldMakeTime = aLCLFGetCancelLogSchema.getOldMakeTime();
		this.ManageCom = aLCLFGetCancelLogSchema.getManageCom();
		this.Operator = aLCLFGetCancelLogSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCLFGetCancelLogSchema.getMakeDate());
		this.MakeTime = aLCLFGetCancelLogSchema.getMakeTime();
		this.Remark = aLCLFGetCancelLogSchema.getRemark();
		this.ContNo = aLCLFGetCancelLogSchema.getContNo();
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

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

			if( rs.getString("GetDutyCode") == null )
				this.GetDutyCode = null;
			else
				this.GetDutyCode = rs.getString("GetDutyCode").trim();

			this.OldGetDate = rs.getDate("OldGetDate");
			this.OldGetMoney = rs.getDouble("OldGetMoney");
			if( rs.getString("OldOperator") == null )
				this.OldOperator = null;
			else
				this.OldOperator = rs.getString("OldOperator").trim();

			this.OldMakeDate = rs.getDate("OldMakeDate");
			if( rs.getString("OldMakeTime") == null )
				this.OldMakeTime = null;
			else
				this.OldMakeTime = rs.getString("OldMakeTime").trim();

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

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCLFGetCancelLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCLFGetCancelLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCLFGetCancelLogSchema getSchema()
	{
		LCLFGetCancelLogSchema aLCLFGetCancelLogSchema = new LCLFGetCancelLogSchema();
		aLCLFGetCancelLogSchema.setSchema(this);
		return aLCLFGetCancelLogSchema;
	}

	public LCLFGetCancelLogDB getDB()
	{
		LCLFGetCancelLogDB aDBOper = new LCLFGetCancelLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCLFGetCancelLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetDutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OldGetDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OldGetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OldMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCLFGetCancelLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GetDutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OldGetDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4,SysConst.PACKAGESPILTER));
			OldGetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			OldOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OldMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			OldMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCLFGetCancelLogSchema";
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
		}
		if (FCode.equalsIgnoreCase("GetDutyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetDutyCode));
		}
		if (FCode.equalsIgnoreCase("OldGetDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOldGetDate()));
		}
		if (FCode.equalsIgnoreCase("OldGetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldGetMoney));
		}
		if (FCode.equalsIgnoreCase("OldOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldOperator));
		}
		if (FCode.equalsIgnoreCase("OldMakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getOldMakeDate()));
		}
		if (FCode.equalsIgnoreCase("OldMakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OldMakeTime));
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
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
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GetDutyCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOldGetDate()));
				break;
			case 4:
				strFieldValue = String.valueOf(OldGetMoney);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OldOperator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOldMakeDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OldMakeTime);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
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
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("OldGetDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OldGetDate = fDate.getDate( FValue );
			}
			else
				OldGetDate = null;
		}
		if (FCode.equalsIgnoreCase("OldGetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OldGetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("OldOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldOperator = FValue.trim();
			}
			else
				OldOperator = null;
		}
		if (FCode.equalsIgnoreCase("OldMakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				OldMakeDate = fDate.getDate( FValue );
			}
			else
				OldMakeDate = null;
		}
		if (FCode.equalsIgnoreCase("OldMakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OldMakeTime = FValue.trim();
			}
			else
				OldMakeTime = null;
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
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCLFGetCancelLogSchema other = (LCLFGetCancelLogSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& PolNo.equals(other.getPolNo())
			&& GetDutyCode.equals(other.getGetDutyCode())
			&& fDate.getString(OldGetDate).equals(other.getOldGetDate())
			&& OldGetMoney == other.getOldGetMoney()
			&& OldOperator.equals(other.getOldOperator())
			&& fDate.getString(OldMakeDate).equals(other.getOldMakeDate())
			&& OldMakeTime.equals(other.getOldMakeTime())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Remark.equals(other.getRemark())
			&& ContNo.equals(other.getContNo());
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
		if( strFieldName.equals("PolNo") ) {
			return 1;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return 2;
		}
		if( strFieldName.equals("OldGetDate") ) {
			return 3;
		}
		if( strFieldName.equals("OldGetMoney") ) {
			return 4;
		}
		if( strFieldName.equals("OldOperator") ) {
			return 5;
		}
		if( strFieldName.equals("OldMakeDate") ) {
			return 6;
		}
		if( strFieldName.equals("OldMakeTime") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("Remark") ) {
			return 12;
		}
		if( strFieldName.equals("ContNo") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "PolNo";
				break;
			case 2:
				strFieldName = "GetDutyCode";
				break;
			case 3:
				strFieldName = "OldGetDate";
				break;
			case 4:
				strFieldName = "OldGetMoney";
				break;
			case 5:
				strFieldName = "OldOperator";
				break;
			case 6:
				strFieldName = "OldMakeDate";
				break;
			case 7:
				strFieldName = "OldMakeTime";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "Remark";
				break;
			case 13:
				strFieldName = "ContNo";
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
		if( strFieldName.equals("PolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetDutyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldGetDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OldGetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OldOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OldMakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("OldMakeTime") ) {
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
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContNo") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
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
