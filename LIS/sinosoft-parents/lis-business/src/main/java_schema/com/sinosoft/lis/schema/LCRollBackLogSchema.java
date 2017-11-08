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
import com.sinosoft.lis.db.LCRollBackLogDB;

/*
 * <p>ClassName: LCRollBackLogSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 保费续期及中介
 */
public class LCRollBackLogSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCRollBackLogSchema.class);

	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 保单号 */
	private String PolNo;
	/** 集体保单号码 */
	private String GrpPolNo;
	/** 其它号码 */
	private String OtherNo;
	/** 其它号码类型 */
	private String OtherNoType;
	/** 回滚类型 */
	private String Type;
	/** 回滚金额 */
	private double Money;
	/** 原操作员 */
	private String OldOperator;
	/** 原数据入机日期 */
	private Date OldMakeDate;
	/** 原数据入机时间 */
	private String OldMakeTime;
	/** 管理机构 */
	private String ManageCom;
	/** 回滚操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 原因描述 */
	private String Remark;
	/** 集体合同号 */
	private String GrpContNo;
	/** 备用属性字段1 */
	private String StandbyFlag1;
	/** 备用属性字段2 */
	private String StandbyFlag2;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCRollBackLogSchema()
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
		LCRollBackLogSchema cloned = (LCRollBackLogSchema)super.clone();
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
	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	/**
	* 合同号分为：<p>
	* 个单合同号<p>
	* 集体合同号
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/**
	* 0 ---表示生存领取对应的合同号<p>
	* 1 ---表示生存领取对应的集体保单号<p>
	* 2 ---表示生存领取对应的个人保单号<p>
	* 3 ---表示批改号<p>
	* 4 ---暂交费退费,对应暂交费退费表的给付通知书号<p>
	* 5 ---表示赔付应收表中的给付通知书号（就是赔案号）<p>
	* 6 ---表示其他退费的给付通知书号码(对应号码添个人保单号）<p>
	* 7 ---表示红利对应的个人保单号。<p>
	* 8 ---表示其他退费的给付通知书号码(对应号码填团体保单号）
	*/
	public String getOtherNoType()
	{
		return OtherNoType;
	}
	public void setOtherNoType(String aOtherNoType)
	{
		OtherNoType = aOtherNoType;
	}
	/**
	* 第一类：<p>
	* 1：一年期寿险<p>
	*    一年期意外险<p>
	*    一年期意外附加险<p>
	*    长期寿险<p>
	*    长期意外险<p>
	*    长期意外附加险<p>
	* <p>
	* <p>
	* 第二类：<p>
	* 2：极短期意外险
	*/
	public String getType()
	{
		return Type;
	}
	public void setType(String aType)
	{
		Type = aType;
	}
	public double getMoney()
	{
		return Money;
	}
	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}
	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = tDouble.doubleValue();
			Money = d;
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
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}

	/**
	* 使用另外一个 LCRollBackLogSchema 对象给 Schema 赋值
	* @param: aLCRollBackLogSchema LCRollBackLogSchema
	**/
	public void setSchema(LCRollBackLogSchema aLCRollBackLogSchema)
	{
		this.SerialNo = aLCRollBackLogSchema.getSerialNo();
		this.PolNo = aLCRollBackLogSchema.getPolNo();
		this.GrpPolNo = aLCRollBackLogSchema.getGrpPolNo();
		this.OtherNo = aLCRollBackLogSchema.getOtherNo();
		this.OtherNoType = aLCRollBackLogSchema.getOtherNoType();
		this.Type = aLCRollBackLogSchema.getType();
		this.Money = aLCRollBackLogSchema.getMoney();
		this.OldOperator = aLCRollBackLogSchema.getOldOperator();
		this.OldMakeDate = fDate.getDate( aLCRollBackLogSchema.getOldMakeDate());
		this.OldMakeTime = aLCRollBackLogSchema.getOldMakeTime();
		this.ManageCom = aLCRollBackLogSchema.getManageCom();
		this.Operator = aLCRollBackLogSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCRollBackLogSchema.getMakeDate());
		this.MakeTime = aLCRollBackLogSchema.getMakeTime();
		this.Remark = aLCRollBackLogSchema.getRemark();
		this.GrpContNo = aLCRollBackLogSchema.getGrpContNo();
		this.StandbyFlag1 = aLCRollBackLogSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLCRollBackLogSchema.getStandbyFlag2();
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

			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("OtherNoType") == null )
				this.OtherNoType = null;
			else
				this.OtherNoType = rs.getString("OtherNoType").trim();

			if( rs.getString("Type") == null )
				this.Type = null;
			else
				this.Type = rs.getString("Type").trim();

			this.Money = rs.getDouble("Money");
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

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCRollBackLog表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCRollBackLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCRollBackLogSchema getSchema()
	{
		LCRollBackLogSchema aLCRollBackLogSchema = new LCRollBackLogSchema();
		aLCRollBackLogSchema.setSchema(this);
		return aLCRollBackLogSchema;
	}

	public LCRollBackLogDB getDB()
	{
		LCRollBackLogDB aDBOper = new LCRollBackLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCRollBackLog描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNoType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Type)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( OldMakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OldMakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCRollBackLog>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			OtherNoType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			OldOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OldMakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			OldMakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCRollBackLogSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNoType));
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(OtherNoType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Type);
				break;
			case 6:
				strFieldValue = String.valueOf(Money);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OldOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getOldMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(OldMakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equalsIgnoreCase("OtherNoType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNoType = FValue.trim();
			}
			else
				OtherNoType = null;
		}
		if (FCode.equalsIgnoreCase("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Type = FValue.trim();
			}
			else
				Type = null;
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Money = d;
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCRollBackLogSchema other = (LCRollBackLogSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& PolNo.equals(other.getPolNo())
			&& GrpPolNo.equals(other.getGrpPolNo())
			&& OtherNo.equals(other.getOtherNo())
			&& OtherNoType.equals(other.getOtherNoType())
			&& Type.equals(other.getType())
			&& Money == other.getMoney()
			&& OldOperator.equals(other.getOldOperator())
			&& fDate.getString(OldMakeDate).equals(other.getOldMakeDate())
			&& OldMakeTime.equals(other.getOldMakeTime())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& Remark.equals(other.getRemark())
			&& GrpContNo.equals(other.getGrpContNo())
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2());
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 2;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 3;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return 4;
		}
		if( strFieldName.equals("Type") ) {
			return 5;
		}
		if( strFieldName.equals("Money") ) {
			return 6;
		}
		if( strFieldName.equals("OldOperator") ) {
			return 7;
		}
		if( strFieldName.equals("OldMakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("OldMakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("Remark") ) {
			return 14;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 15;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 16;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 17;
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
				strFieldName = "GrpPolNo";
				break;
			case 3:
				strFieldName = "OtherNo";
				break;
			case 4:
				strFieldName = "OtherNoType";
				break;
			case 5:
				strFieldName = "Type";
				break;
			case 6:
				strFieldName = "Money";
				break;
			case 7:
				strFieldName = "OldOperator";
				break;
			case 8:
				strFieldName = "OldMakeDate";
				break;
			case 9:
				strFieldName = "OldMakeTime";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "Remark";
				break;
			case 15:
				strFieldName = "GrpContNo";
				break;
			case 16:
				strFieldName = "StandbyFlag1";
				break;
			case 17:
				strFieldName = "StandbyFlag2";
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNoType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Money") ) {
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
