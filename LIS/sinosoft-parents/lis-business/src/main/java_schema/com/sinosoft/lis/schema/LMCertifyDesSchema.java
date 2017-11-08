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
import com.sinosoft.lis.db.LMCertifyDesDB;

/*
 * <p>ClassName: LMCertifyDesSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LMCertifyDesSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMCertifyDesSchema.class);

	// @Field
	/** 单证编码 */
	private String CertifyCode;
	/** 单证名称 */
	private String CertifyName;
	/** 附标号 */
	private String SubCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 业务类型 */
	private String CertifyClass;
	/** 是否有价单证 */
	private String HavePrice;
	/** 业务单证类型 */
	private String CertifyClass2;
	/** 重要级别 */
	private String ImportantLevel;
	/** 是否是有号单证 */
	private String HaveNumber;
	/** 单证号码长度 */
	private int CertifyLength;
	/** 是否回收标志 */
	private String TackBackFlag;
	/** 单价 */
	private double CertifyPrice;
	/** 单位 */
	private String Unit;
	/** 印刷最后号码 */
	private String MaxPrintNo;
	/** 发放时拆分 */
	private String SplitOnSend;
	/** 是否限量领用 */
	private String HaveLimit;
	/** 个人代理人最大领用数 */
	private int MaxUnit1;
	/** 非个人代理人最大领用数 */
	private int MaxUnit2;
	/** 是否有有效使用期 */
	private String HaveValidate;
	/** 个人代理人有效使用期 */
	private int Validate1;
	/** 非个人代理人有效使用期 */
	private int Validate2;
	/** 状态 */
	private String State;
	/** 注释 */
	private String Note;
	/** 管理机构 */
	private String ManageCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 业务员状态校验标志 */
	private String JugAgtFlag;

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMCertifyDesSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CertifyCode";

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
		LMCertifyDesSchema cloned = (LMCertifyDesSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getCertifyName()
	{
		return CertifyName;
	}
	public void setCertifyName(String aCertifyName)
	{
		CertifyName = aCertifyName;
	}
	/**
	* 对于普通单证，该字段为空，对于定额单证，<p>
	* #该字段指定额单证的附标
	*/
	public String getSubCode()
	{
		return SubCode;
	}
	public void setSubCode(String aSubCode)
	{
		SubCode = aSubCode;
	}
	/**
	* 险种（只对定额单证有效，对于普通单证，设为000）
	*/
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		RiskVersion = aRiskVersion;
	}
	/**
	* #“D” 定额单<p>
	* #“S” 系统单证<p>
	* #“P” 普通单证
	*/
	public String getCertifyClass()
	{
		return CertifyClass;
	}
	public void setCertifyClass(String aCertifyClass)
	{
		CertifyClass = aCertifyClass;
	}
	/**
	* Y-有价单证<p>
	* N-非有价单证
	*/
	public String getHavePrice()
	{
		return HavePrice;
	}
	public void setHavePrice(String aHavePrice)
	{
		HavePrice = aHavePrice;
	}
	/**
	* 0-承保类、1-保全类、2-理赔类、3-财务类、<p>
	* 4-条款、5-产品说明书、6-其他
	*/
	public String getCertifyClass2()
	{
		return CertifyClass2;
	}
	public void setCertifyClass2(String aCertifyClass2)
	{
		CertifyClass2 = aCertifyClass2;
	}
	/**
	* 重要级别（未用）
	*/
	public String getImportantLevel()
	{
		return ImportantLevel;
	}
	public void setImportantLevel(String aImportantLevel)
	{
		ImportantLevel = aImportantLevel;
	}
	/**
	* Y表示是有号单证；<p>
	* N表示是无号单证
	*/
	public String getHaveNumber()
	{
		return HaveNumber;
	}
	public void setHaveNumber(String aHaveNumber)
	{
		HaveNumber = aHaveNumber;
	}
	public int getCertifyLength()
	{
		return CertifyLength;
	}
	public void setCertifyLength(int aCertifyLength)
	{
		CertifyLength = aCertifyLength;
	}
	public void setCertifyLength(String aCertifyLength)
	{
		if (aCertifyLength != null && !aCertifyLength.equals(""))
		{
			Integer tInteger = new Integer(aCertifyLength);
			int i = tInteger.intValue();
			CertifyLength = i;
		}
	}

	/**
	* Y 需要回收<p>
	* N 不需要回收
	*/
	public String getTackBackFlag()
	{
		return TackBackFlag;
	}
	public void setTackBackFlag(String aTackBackFlag)
	{
		TackBackFlag = aTackBackFlag;
	}
	public double getCertifyPrice()
	{
		return CertifyPrice;
	}
	public void setCertifyPrice(double aCertifyPrice)
	{
		CertifyPrice = aCertifyPrice;
	}
	public void setCertifyPrice(String aCertifyPrice)
	{
		if (aCertifyPrice != null && !aCertifyPrice.equals(""))
		{
			Double tDouble = new Double(aCertifyPrice);
			double d = tDouble.doubleValue();
			CertifyPrice = d;
		}
	}

	public String getUnit()
	{
		return Unit;
	}
	public void setUnit(String aUnit)
	{
		Unit = aUnit;
	}
	public String getMaxPrintNo()
	{
		return MaxPrintNo;
	}
	public void setMaxPrintNo(String aMaxPrintNo)
	{
		MaxPrintNo = aMaxPrintNo;
	}
	public String getSplitOnSend()
	{
		return SplitOnSend;
	}
	public void setSplitOnSend(String aSplitOnSend)
	{
		SplitOnSend = aSplitOnSend;
	}
	/**
	* Y 限量领用<p>
	* N 不限量领用
	*/
	public String getHaveLimit()
	{
		return HaveLimit;
	}
	public void setHaveLimit(String aHaveLimit)
	{
		HaveLimit = aHaveLimit;
	}
	public int getMaxUnit1()
	{
		return MaxUnit1;
	}
	public void setMaxUnit1(int aMaxUnit1)
	{
		MaxUnit1 = aMaxUnit1;
	}
	public void setMaxUnit1(String aMaxUnit1)
	{
		if (aMaxUnit1 != null && !aMaxUnit1.equals(""))
		{
			Integer tInteger = new Integer(aMaxUnit1);
			int i = tInteger.intValue();
			MaxUnit1 = i;
		}
	}

	public int getMaxUnit2()
	{
		return MaxUnit2;
	}
	public void setMaxUnit2(int aMaxUnit2)
	{
		MaxUnit2 = aMaxUnit2;
	}
	public void setMaxUnit2(String aMaxUnit2)
	{
		if (aMaxUnit2 != null && !aMaxUnit2.equals(""))
		{
			Integer tInteger = new Integer(aMaxUnit2);
			int i = tInteger.intValue();
			MaxUnit2 = i;
		}
	}

	/**
	* Y  有有效使用期限<p>
	* N 无有效使用期限
	*/
	public String getHaveValidate()
	{
		return HaveValidate;
	}
	public void setHaveValidate(String aHaveValidate)
	{
		HaveValidate = aHaveValidate;
	}
	/**
	* 单位为天
	*/
	public int getValidate1()
	{
		return Validate1;
	}
	public void setValidate1(int aValidate1)
	{
		Validate1 = aValidate1;
	}
	public void setValidate1(String aValidate1)
	{
		if (aValidate1 != null && !aValidate1.equals(""))
		{
			Integer tInteger = new Integer(aValidate1);
			int i = tInteger.intValue();
			Validate1 = i;
		}
	}

	/**
	* 单位为天
	*/
	public int getValidate2()
	{
		return Validate2;
	}
	public void setValidate2(int aValidate2)
	{
		Validate2 = aValidate2;
	}
	public void setValidate2(String aValidate2)
	{
		if (aValidate2 != null && !aValidate2.equals(""))
		{
			Integer tInteger = new Integer(aValidate2);
			int i = tInteger.intValue();
			Validate2 = i;
		}
	}

	/**
	* 0-启用<p>
	* 1-停用
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	/**
	* 单证的具体注释
	*/
	public String getNote()
	{
		return Note;
	}
	public void setNote(String aNote)
	{
		Note = aNote;
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
	/**
	* 0 null-发放\回收都做校验<p>
	* 1-发放作校验,回收不作校验<p>
	* 2-发放不作校验,回收作校验<p>
	* 3-发放/回收都不做校验
	*/
	public String getJugAgtFlag()
	{
		return JugAgtFlag;
	}
	public void setJugAgtFlag(String aJugAgtFlag)
	{
		JugAgtFlag = aJugAgtFlag;
	}

	/**
	* 使用另外一个 LMCertifyDesSchema 对象给 Schema 赋值
	* @param: aLMCertifyDesSchema LMCertifyDesSchema
	**/
	public void setSchema(LMCertifyDesSchema aLMCertifyDesSchema)
	{
		this.CertifyCode = aLMCertifyDesSchema.getCertifyCode();
		this.CertifyName = aLMCertifyDesSchema.getCertifyName();
		this.SubCode = aLMCertifyDesSchema.getSubCode();
		this.RiskCode = aLMCertifyDesSchema.getRiskCode();
		this.RiskVersion = aLMCertifyDesSchema.getRiskVersion();
		this.CertifyClass = aLMCertifyDesSchema.getCertifyClass();
		this.HavePrice = aLMCertifyDesSchema.getHavePrice();
		this.CertifyClass2 = aLMCertifyDesSchema.getCertifyClass2();
		this.ImportantLevel = aLMCertifyDesSchema.getImportantLevel();
		this.HaveNumber = aLMCertifyDesSchema.getHaveNumber();
		this.CertifyLength = aLMCertifyDesSchema.getCertifyLength();
		this.TackBackFlag = aLMCertifyDesSchema.getTackBackFlag();
		this.CertifyPrice = aLMCertifyDesSchema.getCertifyPrice();
		this.Unit = aLMCertifyDesSchema.getUnit();
		this.MaxPrintNo = aLMCertifyDesSchema.getMaxPrintNo();
		this.SplitOnSend = aLMCertifyDesSchema.getSplitOnSend();
		this.HaveLimit = aLMCertifyDesSchema.getHaveLimit();
		this.MaxUnit1 = aLMCertifyDesSchema.getMaxUnit1();
		this.MaxUnit2 = aLMCertifyDesSchema.getMaxUnit2();
		this.HaveValidate = aLMCertifyDesSchema.getHaveValidate();
		this.Validate1 = aLMCertifyDesSchema.getValidate1();
		this.Validate2 = aLMCertifyDesSchema.getValidate2();
		this.State = aLMCertifyDesSchema.getState();
		this.Note = aLMCertifyDesSchema.getNote();
		this.ManageCom = aLMCertifyDesSchema.getManageCom();
		this.Operator = aLMCertifyDesSchema.getOperator();
		this.MakeDate = fDate.getDate( aLMCertifyDesSchema.getMakeDate());
		this.MakeTime = aLMCertifyDesSchema.getMakeTime();
		this.JugAgtFlag = aLMCertifyDesSchema.getJugAgtFlag();
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
			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("CertifyName") == null )
				this.CertifyName = null;
			else
				this.CertifyName = rs.getString("CertifyName").trim();

			if( rs.getString("SubCode") == null )
				this.SubCode = null;
			else
				this.SubCode = rs.getString("SubCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("CertifyClass") == null )
				this.CertifyClass = null;
			else
				this.CertifyClass = rs.getString("CertifyClass").trim();

			if( rs.getString("HavePrice") == null )
				this.HavePrice = null;
			else
				this.HavePrice = rs.getString("HavePrice").trim();

			if( rs.getString("CertifyClass2") == null )
				this.CertifyClass2 = null;
			else
				this.CertifyClass2 = rs.getString("CertifyClass2").trim();

			if( rs.getString("ImportantLevel") == null )
				this.ImportantLevel = null;
			else
				this.ImportantLevel = rs.getString("ImportantLevel").trim();

			if( rs.getString("HaveNumber") == null )
				this.HaveNumber = null;
			else
				this.HaveNumber = rs.getString("HaveNumber").trim();

			this.CertifyLength = rs.getInt("CertifyLength");
			if( rs.getString("TackBackFlag") == null )
				this.TackBackFlag = null;
			else
				this.TackBackFlag = rs.getString("TackBackFlag").trim();

			this.CertifyPrice = rs.getDouble("CertifyPrice");
			if( rs.getString("Unit") == null )
				this.Unit = null;
			else
				this.Unit = rs.getString("Unit").trim();

			if( rs.getString("MaxPrintNo") == null )
				this.MaxPrintNo = null;
			else
				this.MaxPrintNo = rs.getString("MaxPrintNo").trim();

			if( rs.getString("SplitOnSend") == null )
				this.SplitOnSend = null;
			else
				this.SplitOnSend = rs.getString("SplitOnSend").trim();

			if( rs.getString("HaveLimit") == null )
				this.HaveLimit = null;
			else
				this.HaveLimit = rs.getString("HaveLimit").trim();

			this.MaxUnit1 = rs.getInt("MaxUnit1");
			this.MaxUnit2 = rs.getInt("MaxUnit2");
			if( rs.getString("HaveValidate") == null )
				this.HaveValidate = null;
			else
				this.HaveValidate = rs.getString("HaveValidate").trim();

			this.Validate1 = rs.getInt("Validate1");
			this.Validate2 = rs.getInt("Validate2");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("Note") == null )
				this.Note = null;
			else
				this.Note = rs.getString("Note").trim();

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

			if( rs.getString("JugAgtFlag") == null )
				this.JugAgtFlag = null;
			else
				this.JugAgtFlag = rs.getString("JugAgtFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMCertifyDes表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCertifyDesSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMCertifyDesSchema getSchema()
	{
		LMCertifyDesSchema aLMCertifyDesSchema = new LMCertifyDesSchema();
		aLMCertifyDesSchema.setSchema(this);
		return aLMCertifyDesSchema;
	}

	public LMCertifyDesDB getDB()
	{
		LMCertifyDesDB aDBOper = new LMCertifyDesDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCertifyDes描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HavePrice)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyClass2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ImportantLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HaveNumber)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CertifyLength));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TackBackFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CertifyPrice));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Unit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MaxPrintNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SplitOnSend)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HaveLimit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxUnit1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxUnit2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HaveValidate)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Validate1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Validate2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Note)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JugAgtFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCertifyDes>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SubCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CertifyClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			HavePrice = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CertifyClass2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ImportantLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HaveNumber = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CertifyLength= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			TackBackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CertifyPrice = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).doubleValue();
			Unit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MaxPrintNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			SplitOnSend = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			HaveLimit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MaxUnit1= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			MaxUnit2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).intValue();
			HaveValidate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Validate1= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).intValue();
			Validate2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).intValue();
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Note = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			JugAgtFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMCertifyDesSchema";
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
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("CertifyName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyName));
		}
		if (FCode.equalsIgnoreCase("SubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubCode));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("CertifyClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyClass));
		}
		if (FCode.equalsIgnoreCase("HavePrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HavePrice));
		}
		if (FCode.equalsIgnoreCase("CertifyClass2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyClass2));
		}
		if (FCode.equalsIgnoreCase("ImportantLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ImportantLevel));
		}
		if (FCode.equalsIgnoreCase("HaveNumber"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HaveNumber));
		}
		if (FCode.equalsIgnoreCase("CertifyLength"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyLength));
		}
		if (FCode.equalsIgnoreCase("TackBackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TackBackFlag));
		}
		if (FCode.equalsIgnoreCase("CertifyPrice"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyPrice));
		}
		if (FCode.equalsIgnoreCase("Unit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Unit));
		}
		if (FCode.equalsIgnoreCase("MaxPrintNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxPrintNo));
		}
		if (FCode.equalsIgnoreCase("SplitOnSend"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SplitOnSend));
		}
		if (FCode.equalsIgnoreCase("HaveLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HaveLimit));
		}
		if (FCode.equalsIgnoreCase("MaxUnit1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxUnit1));
		}
		if (FCode.equalsIgnoreCase("MaxUnit2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxUnit2));
		}
		if (FCode.equalsIgnoreCase("HaveValidate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HaveValidate));
		}
		if (FCode.equalsIgnoreCase("Validate1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Validate1));
		}
		if (FCode.equalsIgnoreCase("Validate2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Validate2));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("Note"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Note));
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
		if (FCode.equalsIgnoreCase("JugAgtFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JugAgtFlag));
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
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SubCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CertifyClass);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(HavePrice);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CertifyClass2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ImportantLevel);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HaveNumber);
				break;
			case 10:
				strFieldValue = String.valueOf(CertifyLength);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(TackBackFlag);
				break;
			case 12:
				strFieldValue = String.valueOf(CertifyPrice);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Unit);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MaxPrintNo);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(SplitOnSend);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(HaveLimit);
				break;
			case 17:
				strFieldValue = String.valueOf(MaxUnit1);
				break;
			case 18:
				strFieldValue = String.valueOf(MaxUnit2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(HaveValidate);
				break;
			case 20:
				strFieldValue = String.valueOf(Validate1);
				break;
			case 21:
				strFieldValue = String.valueOf(Validate2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Note);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(JugAgtFlag);
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

		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("CertifyName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyName = FValue.trim();
			}
			else
				CertifyName = null;
		}
		if (FCode.equalsIgnoreCase("SubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubCode = FValue.trim();
			}
			else
				SubCode = null;
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("CertifyClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyClass = FValue.trim();
			}
			else
				CertifyClass = null;
		}
		if (FCode.equalsIgnoreCase("HavePrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HavePrice = FValue.trim();
			}
			else
				HavePrice = null;
		}
		if (FCode.equalsIgnoreCase("CertifyClass2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyClass2 = FValue.trim();
			}
			else
				CertifyClass2 = null;
		}
		if (FCode.equalsIgnoreCase("ImportantLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ImportantLevel = FValue.trim();
			}
			else
				ImportantLevel = null;
		}
		if (FCode.equalsIgnoreCase("HaveNumber"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HaveNumber = FValue.trim();
			}
			else
				HaveNumber = null;
		}
		if (FCode.equalsIgnoreCase("CertifyLength"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CertifyLength = i;
			}
		}
		if (FCode.equalsIgnoreCase("TackBackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TackBackFlag = FValue.trim();
			}
			else
				TackBackFlag = null;
		}
		if (FCode.equalsIgnoreCase("CertifyPrice"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CertifyPrice = d;
			}
		}
		if (FCode.equalsIgnoreCase("Unit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Unit = FValue.trim();
			}
			else
				Unit = null;
		}
		if (FCode.equalsIgnoreCase("MaxPrintNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MaxPrintNo = FValue.trim();
			}
			else
				MaxPrintNo = null;
		}
		if (FCode.equalsIgnoreCase("SplitOnSend"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SplitOnSend = FValue.trim();
			}
			else
				SplitOnSend = null;
		}
		if (FCode.equalsIgnoreCase("HaveLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HaveLimit = FValue.trim();
			}
			else
				HaveLimit = null;
		}
		if (FCode.equalsIgnoreCase("MaxUnit1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxUnit1 = i;
			}
		}
		if (FCode.equalsIgnoreCase("MaxUnit2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxUnit2 = i;
			}
		}
		if (FCode.equalsIgnoreCase("HaveValidate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HaveValidate = FValue.trim();
			}
			else
				HaveValidate = null;
		}
		if (FCode.equalsIgnoreCase("Validate1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Validate1 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Validate2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Validate2 = i;
			}
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
		if (FCode.equalsIgnoreCase("Note"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Note = FValue.trim();
			}
			else
				Note = null;
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
		if (FCode.equalsIgnoreCase("JugAgtFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JugAgtFlag = FValue.trim();
			}
			else
				JugAgtFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMCertifyDesSchema other = (LMCertifyDesSchema)otherObject;
		return
			CertifyCode.equals(other.getCertifyCode())
			&& CertifyName.equals(other.getCertifyName())
			&& SubCode.equals(other.getSubCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& CertifyClass.equals(other.getCertifyClass())
			&& HavePrice.equals(other.getHavePrice())
			&& CertifyClass2.equals(other.getCertifyClass2())
			&& ImportantLevel.equals(other.getImportantLevel())
			&& HaveNumber.equals(other.getHaveNumber())
			&& CertifyLength == other.getCertifyLength()
			&& TackBackFlag.equals(other.getTackBackFlag())
			&& CertifyPrice == other.getCertifyPrice()
			&& Unit.equals(other.getUnit())
			&& MaxPrintNo.equals(other.getMaxPrintNo())
			&& SplitOnSend.equals(other.getSplitOnSend())
			&& HaveLimit.equals(other.getHaveLimit())
			&& MaxUnit1 == other.getMaxUnit1()
			&& MaxUnit2 == other.getMaxUnit2()
			&& HaveValidate.equals(other.getHaveValidate())
			&& Validate1 == other.getValidate1()
			&& Validate2 == other.getValidate2()
			&& State.equals(other.getState())
			&& Note.equals(other.getNote())
			&& ManageCom.equals(other.getManageCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& JugAgtFlag.equals(other.getJugAgtFlag());
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
		if( strFieldName.equals("CertifyCode") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyName") ) {
			return 1;
		}
		if( strFieldName.equals("SubCode") ) {
			return 2;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 3;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return 4;
		}
		if( strFieldName.equals("CertifyClass") ) {
			return 5;
		}
		if( strFieldName.equals("HavePrice") ) {
			return 6;
		}
		if( strFieldName.equals("CertifyClass2") ) {
			return 7;
		}
		if( strFieldName.equals("ImportantLevel") ) {
			return 8;
		}
		if( strFieldName.equals("HaveNumber") ) {
			return 9;
		}
		if( strFieldName.equals("CertifyLength") ) {
			return 10;
		}
		if( strFieldName.equals("TackBackFlag") ) {
			return 11;
		}
		if( strFieldName.equals("CertifyPrice") ) {
			return 12;
		}
		if( strFieldName.equals("Unit") ) {
			return 13;
		}
		if( strFieldName.equals("MaxPrintNo") ) {
			return 14;
		}
		if( strFieldName.equals("SplitOnSend") ) {
			return 15;
		}
		if( strFieldName.equals("HaveLimit") ) {
			return 16;
		}
		if( strFieldName.equals("MaxUnit1") ) {
			return 17;
		}
		if( strFieldName.equals("MaxUnit2") ) {
			return 18;
		}
		if( strFieldName.equals("HaveValidate") ) {
			return 19;
		}
		if( strFieldName.equals("Validate1") ) {
			return 20;
		}
		if( strFieldName.equals("Validate2") ) {
			return 21;
		}
		if( strFieldName.equals("State") ) {
			return 22;
		}
		if( strFieldName.equals("Note") ) {
			return 23;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 24;
		}
		if( strFieldName.equals("Operator") ) {
			return 25;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 26;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 27;
		}
		if( strFieldName.equals("JugAgtFlag") ) {
			return 28;
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
				strFieldName = "CertifyCode";
				break;
			case 1:
				strFieldName = "CertifyName";
				break;
			case 2:
				strFieldName = "SubCode";
				break;
			case 3:
				strFieldName = "RiskCode";
				break;
			case 4:
				strFieldName = "RiskVersion";
				break;
			case 5:
				strFieldName = "CertifyClass";
				break;
			case 6:
				strFieldName = "HavePrice";
				break;
			case 7:
				strFieldName = "CertifyClass2";
				break;
			case 8:
				strFieldName = "ImportantLevel";
				break;
			case 9:
				strFieldName = "HaveNumber";
				break;
			case 10:
				strFieldName = "CertifyLength";
				break;
			case 11:
				strFieldName = "TackBackFlag";
				break;
			case 12:
				strFieldName = "CertifyPrice";
				break;
			case 13:
				strFieldName = "Unit";
				break;
			case 14:
				strFieldName = "MaxPrintNo";
				break;
			case 15:
				strFieldName = "SplitOnSend";
				break;
			case 16:
				strFieldName = "HaveLimit";
				break;
			case 17:
				strFieldName = "MaxUnit1";
				break;
			case 18:
				strFieldName = "MaxUnit2";
				break;
			case 19:
				strFieldName = "HaveValidate";
				break;
			case 20:
				strFieldName = "Validate1";
				break;
			case 21:
				strFieldName = "Validate2";
				break;
			case 22:
				strFieldName = "State";
				break;
			case 23:
				strFieldName = "Note";
				break;
			case 24:
				strFieldName = "ManageCom";
				break;
			case 25:
				strFieldName = "Operator";
				break;
			case 26:
				strFieldName = "MakeDate";
				break;
			case 27:
				strFieldName = "MakeTime";
				break;
			case 28:
				strFieldName = "JugAgtFlag";
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
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HavePrice") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyClass2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ImportantLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HaveNumber") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyLength") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TackBackFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyPrice") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Unit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxPrintNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SplitOnSend") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HaveLimit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxUnit1") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MaxUnit2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("HaveValidate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Validate1") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Validate2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Note") ) {
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
		if( strFieldName.equals("JugAgtFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_INT;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_INT;
				break;
			case 21:
				nFieldType = Schema.TYPE_INT;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
