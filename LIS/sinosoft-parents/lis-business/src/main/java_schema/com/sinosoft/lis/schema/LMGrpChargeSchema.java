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
import com.sinosoft.lis.db.LMGrpChargeDB;

/*
 * <p>ClassName: LMGrpChargeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LMGrpChargeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMGrpChargeSchema.class);

	// @Field
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 起始交费次数 */
	private int PayCountFrom;
	/** 终止交费次数 */
	private int PayCountTo;
	/** 手续费类型 */
	private String ChargeType;
	/** 手续费比率 */
	private double ChargeRate;
	/** 对公帐号 */
	private String ChargeAccount;
	/** 参数1 */
	private double Parm1;
	/** 参数2 */
	private double Parm2;
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
	/** 备注 */
	private String Noti;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMGrpChargeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "GrpPolNo";
		pk[1] = "PayCountFrom";
		pk[2] = "PayCountTo";
		pk[3] = "ChargeType";

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
		LMGrpChargeSchema cloned = (LMGrpChargeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public int getPayCountFrom()
	{
		return PayCountFrom;
	}
	public void setPayCountFrom(int aPayCountFrom)
	{
		PayCountFrom = aPayCountFrom;
	}
	public void setPayCountFrom(String aPayCountFrom)
	{
		if (aPayCountFrom != null && !aPayCountFrom.equals(""))
		{
			Integer tInteger = new Integer(aPayCountFrom);
			int i = tInteger.intValue();
			PayCountFrom = i;
		}
	}

	public int getPayCountTo()
	{
		return PayCountTo;
	}
	public void setPayCountTo(int aPayCountTo)
	{
		PayCountTo = aPayCountTo;
	}
	public void setPayCountTo(String aPayCountTo)
	{
		if (aPayCountTo != null && !aPayCountTo.equals(""))
		{
			Integer tInteger = new Integer(aPayCountTo);
			int i = tInteger.intValue();
			PayCountTo = i;
		}
	}

	/**
	* 11-                     正常手续费<p>
	* <p>
	* 12-                     报销手续费
	*/
	public String getChargeType()
	{
		return ChargeType;
	}
	public void setChargeType(String aChargeType)
	{
		ChargeType = aChargeType;
	}
	public double getChargeRate()
	{
		return ChargeRate;
	}
	public void setChargeRate(double aChargeRate)
	{
		ChargeRate = aChargeRate;
	}
	public void setChargeRate(String aChargeRate)
	{
		if (aChargeRate != null && !aChargeRate.equals(""))
		{
			Double tDouble = new Double(aChargeRate);
			double d = tDouble.doubleValue();
			ChargeRate = d;
		}
	}

	public String getChargeAccount()
	{
		return ChargeAccount;
	}
	public void setChargeAccount(String aChargeAccount)
	{
		ChargeAccount = aChargeAccount;
	}
	public double getParm1()
	{
		return Parm1;
	}
	public void setParm1(double aParm1)
	{
		Parm1 = aParm1;
	}
	public void setParm1(String aParm1)
	{
		if (aParm1 != null && !aParm1.equals(""))
		{
			Double tDouble = new Double(aParm1);
			double d = tDouble.doubleValue();
			Parm1 = d;
		}
	}

	public double getParm2()
	{
		return Parm2;
	}
	public void setParm2(double aParm2)
	{
		Parm2 = aParm2;
	}
	public void setParm2(String aParm2)
	{
		if (aParm2 != null && !aParm2.equals(""))
		{
			Double tDouble = new Double(aParm2);
			double d = tDouble.doubleValue();
			Parm2 = d;
		}
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
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}

	/**
	* 使用另外一个 LMGrpChargeSchema 对象给 Schema 赋值
	* @param: aLMGrpChargeSchema LMGrpChargeSchema
	**/
	public void setSchema(LMGrpChargeSchema aLMGrpChargeSchema)
	{
		this.GrpPolNo = aLMGrpChargeSchema.getGrpPolNo();
		this.PayCountFrom = aLMGrpChargeSchema.getPayCountFrom();
		this.PayCountTo = aLMGrpChargeSchema.getPayCountTo();
		this.ChargeType = aLMGrpChargeSchema.getChargeType();
		this.ChargeRate = aLMGrpChargeSchema.getChargeRate();
		this.ChargeAccount = aLMGrpChargeSchema.getChargeAccount();
		this.Parm1 = aLMGrpChargeSchema.getParm1();
		this.Parm2 = aLMGrpChargeSchema.getParm2();
		this.Operator = aLMGrpChargeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMGrpChargeSchema.getMakeDate());
		this.MakeTime = aLMGrpChargeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLMGrpChargeSchema.getModifyDate());
		this.ModifyTime = aLMGrpChargeSchema.getModifyTime();
		this.Noti = aLMGrpChargeSchema.getNoti();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			this.PayCountFrom = rs.getInt("PayCountFrom");
			this.PayCountTo = rs.getInt("PayCountTo");
			if( rs.getString("ChargeType") == null )
				this.ChargeType = null;
			else
				this.ChargeType = rs.getString("ChargeType").trim();

			this.ChargeRate = rs.getDouble("ChargeRate");
			if( rs.getString("ChargeAccount") == null )
				this.ChargeAccount = null;
			else
				this.ChargeAccount = rs.getString("ChargeAccount").trim();

			this.Parm1 = rs.getDouble("Parm1");
			this.Parm2 = rs.getDouble("Parm2");
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

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMGrpCharge表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMGrpChargeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMGrpChargeSchema getSchema()
	{
		LMGrpChargeSchema aLMGrpChargeSchema = new LMGrpChargeSchema();
		aLMGrpChargeSchema.setSchema(this);
		return aLMGrpChargeSchema;
	}

	public LMGrpChargeDB getDB()
	{
		LMGrpChargeDB aDBOper = new LMGrpChargeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMGrpCharge描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCountFrom));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayCountTo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChargeRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChargeAccount)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Parm1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Parm2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMGrpCharge>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PayCountFrom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PayCountTo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			ChargeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChargeRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			ChargeAccount = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Parm1 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Parm2 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMGrpChargeSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("PayCountFrom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCountFrom));
		}
		if (FCode.equalsIgnoreCase("PayCountTo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayCountTo));
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeType));
		}
		if (FCode.equalsIgnoreCase("ChargeRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeRate));
		}
		if (FCode.equalsIgnoreCase("ChargeAccount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChargeAccount));
		}
		if (FCode.equalsIgnoreCase("Parm1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Parm1));
		}
		if (FCode.equalsIgnoreCase("Parm2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Parm2));
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
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = String.valueOf(PayCountFrom);
				break;
			case 2:
				strFieldValue = String.valueOf(PayCountTo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ChargeType);
				break;
			case 4:
				strFieldValue = String.valueOf(ChargeRate);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ChargeAccount);
				break;
			case 6:
				strFieldValue = String.valueOf(Parm1);
				break;
			case 7:
				strFieldValue = String.valueOf(Parm2);
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
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Noti);
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

		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("PayCountFrom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCountFrom = i;
			}
		}
		if (FCode.equalsIgnoreCase("PayCountTo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PayCountTo = i;
			}
		}
		if (FCode.equalsIgnoreCase("ChargeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeType = FValue.trim();
			}
			else
				ChargeType = null;
		}
		if (FCode.equalsIgnoreCase("ChargeRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChargeRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChargeAccount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChargeAccount = FValue.trim();
			}
			else
				ChargeAccount = null;
		}
		if (FCode.equalsIgnoreCase("Parm1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Parm1 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Parm2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Parm2 = d;
			}
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
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMGrpChargeSchema other = (LMGrpChargeSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& PayCountFrom == other.getPayCountFrom()
			&& PayCountTo == other.getPayCountTo()
			&& ChargeType.equals(other.getChargeType())
			&& ChargeRate == other.getChargeRate()
			&& ChargeAccount.equals(other.getChargeAccount())
			&& Parm1 == other.getParm1()
			&& Parm2 == other.getParm2()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Noti.equals(other.getNoti());
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("PayCountFrom") ) {
			return 1;
		}
		if( strFieldName.equals("PayCountTo") ) {
			return 2;
		}
		if( strFieldName.equals("ChargeType") ) {
			return 3;
		}
		if( strFieldName.equals("ChargeRate") ) {
			return 4;
		}
		if( strFieldName.equals("ChargeAccount") ) {
			return 5;
		}
		if( strFieldName.equals("Parm1") ) {
			return 6;
		}
		if( strFieldName.equals("Parm2") ) {
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
		if( strFieldName.equals("ModifyDate") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 12;
		}
		if( strFieldName.equals("Noti") ) {
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "PayCountFrom";
				break;
			case 2:
				strFieldName = "PayCountTo";
				break;
			case 3:
				strFieldName = "ChargeType";
				break;
			case 4:
				strFieldName = "ChargeRate";
				break;
			case 5:
				strFieldName = "ChargeAccount";
				break;
			case 6:
				strFieldName = "Parm1";
				break;
			case 7:
				strFieldName = "Parm2";
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
				strFieldName = "ModifyDate";
				break;
			case 12:
				strFieldName = "ModifyTime";
				break;
			case 13:
				strFieldName = "Noti";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayCountFrom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PayCountTo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ChargeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChargeRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChargeAccount") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Parm1") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Parm2") ) {
			return Schema.TYPE_DOUBLE;
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
		if( strFieldName.equals("Noti") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
