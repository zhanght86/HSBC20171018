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
import com.sinosoft.lis.db.ActiveStatusDB;

/*
 * <p>ClassName: ActiveStatusSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 兼业系统
 */
public class ActiveStatusSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ActiveStatusSchema.class);

	// @Field
	/** 卡单号 */
	private String CardNo;
	/** 卡单类型 */
	private String CertifyCode;
	/** 卡单验证码 */
	private String PassWord;
	/** 结算号 */
	private String BalanceNo;
	/** 激活方式 */
	private String ActiveMod;
	/** 生效日期计算方式 */
	private String CvaliDateCalMod;
	/** 生效日算法编码 */
	private String CalCode;
	/** 生效日计算要素 */
	private String CalFactor;
	/** 生效日计算要素值 */
	private String CalFactorValue;
	/** 生效日期 */
	private Date CvaliDate;
	/** 状态类型 */
	private String StatusType;
	/** 激活日期 */
	private Date ActiveDate;
	/** 激活时间 */
	private String ActiveTime;
	/** 备用字段1 */
	private String StandByFlag1;
	/** 备用字段2 */
	private String StandByFlag2;
	/** 操作员 */
	private String Operator;
	/** 操作日期 */
	private Date MakeDate;
	/** 操作时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ActiveStatusSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CardNo";
		pk[1] = "CertifyCode";

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
		ActiveStatusSchema cloned = (ActiveStatusSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCardNo()
	{
		return CardNo;
	}
	public void setCardNo(String aCardNo)
	{
		CardNo = aCardNo;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getPassWord()
	{
		return PassWord;
	}
	public void setPassWord(String aPassWord)
	{
		PassWord = aPassWord;
	}
	public String getBalanceNo()
	{
		return BalanceNo;
	}
	public void setBalanceNo(String aBalanceNo)
	{
		BalanceNo = aBalanceNo;
	}
	/**
	* 0-----网上激活<p>
	* 1-----电话激活<p>
	* 2-----其它
	*/
	public String getActiveMod()
	{
		return ActiveMod;
	}
	public void setActiveMod(String aActiveMod)
	{
		ActiveMod = aActiveMod;
	}
	/**
	* 0-----激活日期次日 <默认><p>
	* 1-----下月1号<p>
	* 2-----指定日期 <指定日期不能在激活日期之前><p>
	* 3-----立即生效 <激活日期当天><p>
	* 4-----根据算法计算生效日期
	*/
	public String getCvaliDateCalMod()
	{
		return CvaliDateCalMod;
	}
	public void setCvaliDateCalMod(String aCvaliDateCalMod)
	{
		CvaliDateCalMod = aCvaliDateCalMod;
	}
	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getCalFactor()
	{
		return CalFactor;
	}
	public void setCalFactor(String aCalFactor)
	{
		CalFactor = aCalFactor;
	}
	public String getCalFactorValue()
	{
		return CalFactorValue;
	}
	public void setCalFactorValue(String aCalFactorValue)
	{
		CalFactorValue = aCalFactorValue;
	}
	public String getCvaliDate()
	{
		if( CvaliDate != null )
			return fDate.getString(CvaliDate);
		else
			return null;
	}
	public void setCvaliDate(Date aCvaliDate)
	{
		CvaliDate = aCvaliDate;
	}
	public void setCvaliDate(String aCvaliDate)
	{
		if (aCvaliDate != null && !aCvaliDate.equals("") )
		{
			CvaliDate = fDate.getDate( aCvaliDate );
		}
		else
			CvaliDate = null;
	}

	/**
	* 1---已激活<p>
	* 0---未激活
	*/
	public String getStatusType()
	{
		return StatusType;
	}
	public void setStatusType(String aStatusType)
	{
		StatusType = aStatusType;
	}
	public String getActiveDate()
	{
		if( ActiveDate != null )
			return fDate.getString(ActiveDate);
		else
			return null;
	}
	public void setActiveDate(Date aActiveDate)
	{
		ActiveDate = aActiveDate;
	}
	public void setActiveDate(String aActiveDate)
	{
		if (aActiveDate != null && !aActiveDate.equals("") )
		{
			ActiveDate = fDate.getDate( aActiveDate );
		}
		else
			ActiveDate = null;
	}

	public String getActiveTime()
	{
		return ActiveTime;
	}
	public void setActiveTime(String aActiveTime)
	{
		ActiveTime = aActiveTime;
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
	* 使用另外一个 ActiveStatusSchema 对象给 Schema 赋值
	* @param: aActiveStatusSchema ActiveStatusSchema
	**/
	public void setSchema(ActiveStatusSchema aActiveStatusSchema)
	{
		this.CardNo = aActiveStatusSchema.getCardNo();
		this.CertifyCode = aActiveStatusSchema.getCertifyCode();
		this.PassWord = aActiveStatusSchema.getPassWord();
		this.BalanceNo = aActiveStatusSchema.getBalanceNo();
		this.ActiveMod = aActiveStatusSchema.getActiveMod();
		this.CvaliDateCalMod = aActiveStatusSchema.getCvaliDateCalMod();
		this.CalCode = aActiveStatusSchema.getCalCode();
		this.CalFactor = aActiveStatusSchema.getCalFactor();
		this.CalFactorValue = aActiveStatusSchema.getCalFactorValue();
		this.CvaliDate = fDate.getDate( aActiveStatusSchema.getCvaliDate());
		this.StatusType = aActiveStatusSchema.getStatusType();
		this.ActiveDate = fDate.getDate( aActiveStatusSchema.getActiveDate());
		this.ActiveTime = aActiveStatusSchema.getActiveTime();
		this.StandByFlag1 = aActiveStatusSchema.getStandByFlag1();
		this.StandByFlag2 = aActiveStatusSchema.getStandByFlag2();
		this.Operator = aActiveStatusSchema.getOperator();
		this.MakeDate = fDate.getDate( aActiveStatusSchema.getMakeDate());
		this.MakeTime = aActiveStatusSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aActiveStatusSchema.getModifyDate());
		this.ModifyTime = aActiveStatusSchema.getModifyTime();
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
			if( rs.getString("CardNo") == null )
				this.CardNo = null;
			else
				this.CardNo = rs.getString("CardNo").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("PassWord") == null )
				this.PassWord = null;
			else
				this.PassWord = rs.getString("PassWord").trim();

			if( rs.getString("BalanceNo") == null )
				this.BalanceNo = null;
			else
				this.BalanceNo = rs.getString("BalanceNo").trim();

			if( rs.getString("ActiveMod") == null )
				this.ActiveMod = null;
			else
				this.ActiveMod = rs.getString("ActiveMod").trim();

			if( rs.getString("CvaliDateCalMod") == null )
				this.CvaliDateCalMod = null;
			else
				this.CvaliDateCalMod = rs.getString("CvaliDateCalMod").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("CalFactor") == null )
				this.CalFactor = null;
			else
				this.CalFactor = rs.getString("CalFactor").trim();

			if( rs.getString("CalFactorValue") == null )
				this.CalFactorValue = null;
			else
				this.CalFactorValue = rs.getString("CalFactorValue").trim();

			this.CvaliDate = rs.getDate("CvaliDate");
			if( rs.getString("StatusType") == null )
				this.StatusType = null;
			else
				this.StatusType = rs.getString("StatusType").trim();

			this.ActiveDate = rs.getDate("ActiveDate");
			if( rs.getString("ActiveTime") == null )
				this.ActiveTime = null;
			else
				this.ActiveTime = rs.getString("ActiveTime").trim();

			if( rs.getString("StandByFlag1") == null )
				this.StandByFlag1 = null;
			else
				this.StandByFlag1 = rs.getString("StandByFlag1").trim();

			if( rs.getString("StandByFlag2") == null )
				this.StandByFlag2 = null;
			else
				this.StandByFlag2 = rs.getString("StandByFlag2").trim();

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
			logger.debug("数据库中的ActiveStatus表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActiveStatusSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ActiveStatusSchema getSchema()
	{
		ActiveStatusSchema aActiveStatusSchema = new ActiveStatusSchema();
		aActiveStatusSchema.setSchema(this);
		return aActiveStatusSchema;
	}

	public ActiveStatusDB getDB()
	{
		ActiveStatusDB aDBOper = new ActiveStatusDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActiveStatus描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CardNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PassWord)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActiveMod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CvaliDateCalMod)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactor)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalFactorValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CvaliDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StatusType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ActiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActiveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandByFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpActiveStatus>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PassWord = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BalanceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ActiveMod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CvaliDateCalMod = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CalFactor = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CalFactorValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CvaliDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			StatusType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ActiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ActiveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			StandByFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			StandByFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ActiveStatusSchema";
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
		if (FCode.equalsIgnoreCase("CardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardNo));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("PassWord"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassWord));
		}
		if (FCode.equalsIgnoreCase("BalanceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceNo));
		}
		if (FCode.equalsIgnoreCase("ActiveMod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActiveMod));
		}
		if (FCode.equalsIgnoreCase("CvaliDateCalMod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CvaliDateCalMod));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("CalFactor"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactor));
		}
		if (FCode.equalsIgnoreCase("CalFactorValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFactorValue));
		}
		if (FCode.equalsIgnoreCase("CvaliDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCvaliDate()));
		}
		if (FCode.equalsIgnoreCase("StatusType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatusType));
		}
		if (FCode.equalsIgnoreCase("ActiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getActiveDate()));
		}
		if (FCode.equalsIgnoreCase("ActiveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActiveTime));
		}
		if (FCode.equalsIgnoreCase("StandByFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag1));
		}
		if (FCode.equalsIgnoreCase("StandByFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(CardNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PassWord);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BalanceNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ActiveMod);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CvaliDateCalMod);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalFactor);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CalFactorValue);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCvaliDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StatusType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getActiveDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ActiveTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag1);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag2);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("CardNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardNo = FValue.trim();
			}
			else
				CardNo = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("PassWord"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassWord = FValue.trim();
			}
			else
				PassWord = null;
		}
		if (FCode.equalsIgnoreCase("BalanceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceNo = FValue.trim();
			}
			else
				BalanceNo = null;
		}
		if (FCode.equalsIgnoreCase("ActiveMod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActiveMod = FValue.trim();
			}
			else
				ActiveMod = null;
		}
		if (FCode.equalsIgnoreCase("CvaliDateCalMod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CvaliDateCalMod = FValue.trim();
			}
			else
				CvaliDateCalMod = null;
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equalsIgnoreCase("CalFactor"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactor = FValue.trim();
			}
			else
				CalFactor = null;
		}
		if (FCode.equalsIgnoreCase("CalFactorValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFactorValue = FValue.trim();
			}
			else
				CalFactorValue = null;
		}
		if (FCode.equalsIgnoreCase("CvaliDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CvaliDate = fDate.getDate( FValue );
			}
			else
				CvaliDate = null;
		}
		if (FCode.equalsIgnoreCase("StatusType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StatusType = FValue.trim();
			}
			else
				StatusType = null;
		}
		if (FCode.equalsIgnoreCase("ActiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ActiveDate = fDate.getDate( FValue );
			}
			else
				ActiveDate = null;
		}
		if (FCode.equalsIgnoreCase("ActiveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActiveTime = FValue.trim();
			}
			else
				ActiveTime = null;
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
		ActiveStatusSchema other = (ActiveStatusSchema)otherObject;
		return
			CardNo.equals(other.getCardNo())
			&& CertifyCode.equals(other.getCertifyCode())
			&& PassWord.equals(other.getPassWord())
			&& BalanceNo.equals(other.getBalanceNo())
			&& ActiveMod.equals(other.getActiveMod())
			&& CvaliDateCalMod.equals(other.getCvaliDateCalMod())
			&& CalCode.equals(other.getCalCode())
			&& CalFactor.equals(other.getCalFactor())
			&& CalFactorValue.equals(other.getCalFactorValue())
			&& fDate.getString(CvaliDate).equals(other.getCvaliDate())
			&& StatusType.equals(other.getStatusType())
			&& fDate.getString(ActiveDate).equals(other.getActiveDate())
			&& ActiveTime.equals(other.getActiveTime())
			&& StandByFlag1.equals(other.getStandByFlag1())
			&& StandByFlag2.equals(other.getStandByFlag2())
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
		if( strFieldName.equals("CardNo") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 1;
		}
		if( strFieldName.equals("PassWord") ) {
			return 2;
		}
		if( strFieldName.equals("BalanceNo") ) {
			return 3;
		}
		if( strFieldName.equals("ActiveMod") ) {
			return 4;
		}
		if( strFieldName.equals("CvaliDateCalMod") ) {
			return 5;
		}
		if( strFieldName.equals("CalCode") ) {
			return 6;
		}
		if( strFieldName.equals("CalFactor") ) {
			return 7;
		}
		if( strFieldName.equals("CalFactorValue") ) {
			return 8;
		}
		if( strFieldName.equals("CvaliDate") ) {
			return 9;
		}
		if( strFieldName.equals("StatusType") ) {
			return 10;
		}
		if( strFieldName.equals("ActiveDate") ) {
			return 11;
		}
		if( strFieldName.equals("ActiveTime") ) {
			return 12;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return 13;
		}
		if( strFieldName.equals("StandByFlag2") ) {
			return 14;
		}
		if( strFieldName.equals("Operator") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "CardNo";
				break;
			case 1:
				strFieldName = "CertifyCode";
				break;
			case 2:
				strFieldName = "PassWord";
				break;
			case 3:
				strFieldName = "BalanceNo";
				break;
			case 4:
				strFieldName = "ActiveMod";
				break;
			case 5:
				strFieldName = "CvaliDateCalMod";
				break;
			case 6:
				strFieldName = "CalCode";
				break;
			case 7:
				strFieldName = "CalFactor";
				break;
			case 8:
				strFieldName = "CalFactorValue";
				break;
			case 9:
				strFieldName = "CvaliDate";
				break;
			case 10:
				strFieldName = "StatusType";
				break;
			case 11:
				strFieldName = "ActiveDate";
				break;
			case 12:
				strFieldName = "ActiveTime";
				break;
			case 13:
				strFieldName = "StandByFlag1";
				break;
			case 14:
				strFieldName = "StandByFlag2";
				break;
			case 15:
				strFieldName = "Operator";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("CardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassWord") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActiveMod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CvaliDateCalMod") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactor") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFactorValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CvaliDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("StatusType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ActiveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag2") ) {
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
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
