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
import com.sinosoft.lis.db.LDUWAddFeeDB;

/*
 * <p>ClassName: LDUWAddFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LDUWAddFeeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWAddFeeSchema.class);

	// @Field
	/** 主要疾病类目编码 */
	private String ICDCode;
	/** 主要疾病类目 */
	private String ICDName;
	/** 疾病严重程度 */
	private String DiseasDegree;
	/** 性别 */
	private String Sex;
	/** 年龄 */
	private int Age;
	/** 险种分类 */
	private String RiskKind;
	/** 是否做手术 */
	private String OPSFlag;
	/** 核保结论 */
	private String UWResult;
	/** 手术编码 */
	private String OPSNo;
	/** 疾病大分类编码 */
	private String DiseasBTypeCode;
	/** 疾病大分类名称 */
	private String DiseasType;
	/** 疾病小分类编码 */
	private String DiseasSTypeCode;
	/** 疾病小分类名称 */
	private String DiseasSType;
	/** 风险等级 */
	private String RiskGrade;
	/** 手术名称 */
	private String OPSName;
	/** 复发期 */
	private String Relapse;
	/** 疾病文本 */
	private String DisText;
	/** 妊娠相关 */
	private String GestationRela;
	/** 状态 */
	private String State;
	/** 观察期 */
	private String ObservedTime;
	/** 可保性判断原则 */
	private String JudgeStandard;
	/** 年份 */
	private String Year;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWAddFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "ICDCode";
		pk[1] = "DiseasDegree";
		pk[2] = "Sex";
		pk[3] = "Age";
		pk[4] = "RiskKind";
		pk[5] = "OPSFlag";

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
		LDUWAddFeeSchema cloned = (LDUWAddFeeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getICDCode()
	{
		return ICDCode;
	}
	public void setICDCode(String aICDCode)
	{
		ICDCode = aICDCode;
	}
	public String getICDName()
	{
		return ICDName;
	}
	public void setICDName(String aICDName)
	{
		ICDName = aICDName;
	}
	public String getDiseasDegree()
	{
		return DiseasDegree;
	}
	public void setDiseasDegree(String aDiseasDegree)
	{
		DiseasDegree = aDiseasDegree;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	public int getAge()
	{
		return Age;
	}
	public void setAge(int aAge)
	{
		Age = aAge;
	}
	public void setAge(String aAge)
	{
		if (aAge != null && !aAge.equals(""))
		{
			Integer tInteger = new Integer(aAge);
			int i = tInteger.intValue();
			Age = i;
		}
	}

	public String getRiskKind()
	{
		return RiskKind;
	}
	public void setRiskKind(String aRiskKind)
	{
		RiskKind = aRiskKind;
	}
	/**
	* 0--否<p>
	* 1--是
	*/
	public String getOPSFlag()
	{
		return OPSFlag;
	}
	public void setOPSFlag(String aOPSFlag)
	{
		OPSFlag = aOPSFlag;
	}
	public String getUWResult()
	{
		return UWResult;
	}
	public void setUWResult(String aUWResult)
	{
		UWResult = aUWResult;
	}
	public String getOPSNo()
	{
		return OPSNo;
	}
	public void setOPSNo(String aOPSNo)
	{
		OPSNo = aOPSNo;
	}
	public String getDiseasBTypeCode()
	{
		return DiseasBTypeCode;
	}
	public void setDiseasBTypeCode(String aDiseasBTypeCode)
	{
		DiseasBTypeCode = aDiseasBTypeCode;
	}
	public String getDiseasType()
	{
		return DiseasType;
	}
	public void setDiseasType(String aDiseasType)
	{
		DiseasType = aDiseasType;
	}
	public String getDiseasSTypeCode()
	{
		return DiseasSTypeCode;
	}
	public void setDiseasSTypeCode(String aDiseasSTypeCode)
	{
		DiseasSTypeCode = aDiseasSTypeCode;
	}
	public String getDiseasSType()
	{
		return DiseasSType;
	}
	public void setDiseasSType(String aDiseasSType)
	{
		DiseasSType = aDiseasSType;
	}
	public String getRiskGrade()
	{
		return RiskGrade;
	}
	public void setRiskGrade(String aRiskGrade)
	{
		RiskGrade = aRiskGrade;
	}
	public String getOPSName()
	{
		return OPSName;
	}
	public void setOPSName(String aOPSName)
	{
		OPSName = aOPSName;
	}
	public String getRelapse()
	{
		return Relapse;
	}
	public void setRelapse(String aRelapse)
	{
		Relapse = aRelapse;
	}
	public String getDisText()
	{
		return DisText;
	}
	public void setDisText(String aDisText)
	{
		DisText = aDisText;
	}
	public String getGestationRela()
	{
		return GestationRela;
	}
	public void setGestationRela(String aGestationRela)
	{
		GestationRela = aGestationRela;
	}
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		State = aState;
	}
	public String getObservedTime()
	{
		return ObservedTime;
	}
	public void setObservedTime(String aObservedTime)
	{
		ObservedTime = aObservedTime;
	}
	public String getJudgeStandard()
	{
		return JudgeStandard;
	}
	public void setJudgeStandard(String aJudgeStandard)
	{
		JudgeStandard = aJudgeStandard;
	}
	public String getYear()
	{
		return Year;
	}
	public void setYear(String aYear)
	{
		Year = aYear;
	}

	/**
	* 使用另外一个 LDUWAddFeeSchema 对象给 Schema 赋值
	* @param: aLDUWAddFeeSchema LDUWAddFeeSchema
	**/
	public void setSchema(LDUWAddFeeSchema aLDUWAddFeeSchema)
	{
		this.ICDCode = aLDUWAddFeeSchema.getICDCode();
		this.ICDName = aLDUWAddFeeSchema.getICDName();
		this.DiseasDegree = aLDUWAddFeeSchema.getDiseasDegree();
		this.Sex = aLDUWAddFeeSchema.getSex();
		this.Age = aLDUWAddFeeSchema.getAge();
		this.RiskKind = aLDUWAddFeeSchema.getRiskKind();
		this.OPSFlag = aLDUWAddFeeSchema.getOPSFlag();
		this.UWResult = aLDUWAddFeeSchema.getUWResult();
		this.OPSNo = aLDUWAddFeeSchema.getOPSNo();
		this.DiseasBTypeCode = aLDUWAddFeeSchema.getDiseasBTypeCode();
		this.DiseasType = aLDUWAddFeeSchema.getDiseasType();
		this.DiseasSTypeCode = aLDUWAddFeeSchema.getDiseasSTypeCode();
		this.DiseasSType = aLDUWAddFeeSchema.getDiseasSType();
		this.RiskGrade = aLDUWAddFeeSchema.getRiskGrade();
		this.OPSName = aLDUWAddFeeSchema.getOPSName();
		this.Relapse = aLDUWAddFeeSchema.getRelapse();
		this.DisText = aLDUWAddFeeSchema.getDisText();
		this.GestationRela = aLDUWAddFeeSchema.getGestationRela();
		this.State = aLDUWAddFeeSchema.getState();
		this.ObservedTime = aLDUWAddFeeSchema.getObservedTime();
		this.JudgeStandard = aLDUWAddFeeSchema.getJudgeStandard();
		this.Year = aLDUWAddFeeSchema.getYear();
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
			if( rs.getString("ICDCode") == null )
				this.ICDCode = null;
			else
				this.ICDCode = rs.getString("ICDCode").trim();

			if( rs.getString("ICDName") == null )
				this.ICDName = null;
			else
				this.ICDName = rs.getString("ICDName").trim();

			if( rs.getString("DiseasDegree") == null )
				this.DiseasDegree = null;
			else
				this.DiseasDegree = rs.getString("DiseasDegree").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Age = rs.getInt("Age");
			if( rs.getString("RiskKind") == null )
				this.RiskKind = null;
			else
				this.RiskKind = rs.getString("RiskKind").trim();

			if( rs.getString("OPSFlag") == null )
				this.OPSFlag = null;
			else
				this.OPSFlag = rs.getString("OPSFlag").trim();

			if( rs.getString("UWResult") == null )
				this.UWResult = null;
			else
				this.UWResult = rs.getString("UWResult").trim();

			if( rs.getString("OPSNo") == null )
				this.OPSNo = null;
			else
				this.OPSNo = rs.getString("OPSNo").trim();

			if( rs.getString("DiseasBTypeCode") == null )
				this.DiseasBTypeCode = null;
			else
				this.DiseasBTypeCode = rs.getString("DiseasBTypeCode").trim();

			if( rs.getString("DiseasType") == null )
				this.DiseasType = null;
			else
				this.DiseasType = rs.getString("DiseasType").trim();

			if( rs.getString("DiseasSTypeCode") == null )
				this.DiseasSTypeCode = null;
			else
				this.DiseasSTypeCode = rs.getString("DiseasSTypeCode").trim();

			if( rs.getString("DiseasSType") == null )
				this.DiseasSType = null;
			else
				this.DiseasSType = rs.getString("DiseasSType").trim();

			if( rs.getString("RiskGrade") == null )
				this.RiskGrade = null;
			else
				this.RiskGrade = rs.getString("RiskGrade").trim();

			if( rs.getString("OPSName") == null )
				this.OPSName = null;
			else
				this.OPSName = rs.getString("OPSName").trim();

			if( rs.getString("Relapse") == null )
				this.Relapse = null;
			else
				this.Relapse = rs.getString("Relapse").trim();

			if( rs.getString("DisText") == null )
				this.DisText = null;
			else
				this.DisText = rs.getString("DisText").trim();

			if( rs.getString("GestationRela") == null )
				this.GestationRela = null;
			else
				this.GestationRela = rs.getString("GestationRela").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("ObservedTime") == null )
				this.ObservedTime = null;
			else
				this.ObservedTime = rs.getString("ObservedTime").trim();

			if( rs.getString("JudgeStandard") == null )
				this.JudgeStandard = null;
			else
				this.JudgeStandard = rs.getString("JudgeStandard").trim();

			if( rs.getString("Year") == null )
				this.Year = null;
			else
				this.Year = rs.getString("Year").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWAddFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWAddFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWAddFeeSchema getSchema()
	{
		LDUWAddFeeSchema aLDUWAddFeeSchema = new LDUWAddFeeSchema();
		aLDUWAddFeeSchema.setSchema(this);
		return aLDUWAddFeeSchema;
	}

	public LDUWAddFeeDB getDB()
	{
		LDUWAddFeeDB aDBOper = new LDUWAddFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWAddFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ICDCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ICDName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseasDegree)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Age));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPSFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPSNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseasBTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseasType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseasSTypeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DiseasSType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OPSName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relapse)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisText)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GestationRela)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ObservedTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(JudgeStandard)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Year));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWAddFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ICDCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ICDName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			DiseasDegree = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Age= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			RiskKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OPSFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UWResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OPSNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			DiseasBTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			DiseasType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DiseasSTypeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			DiseasSType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RiskGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			OPSName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Relapse = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			DisText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			GestationRela = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ObservedTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			JudgeStandard = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Year = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWAddFeeSchema";
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
		if (FCode.equalsIgnoreCase("ICDCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDCode));
		}
		if (FCode.equalsIgnoreCase("ICDName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ICDName));
		}
		if (FCode.equalsIgnoreCase("DiseasDegree"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseasDegree));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Age));
		}
		if (FCode.equalsIgnoreCase("RiskKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskKind));
		}
		if (FCode.equalsIgnoreCase("OPSFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPSFlag));
		}
		if (FCode.equalsIgnoreCase("UWResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWResult));
		}
		if (FCode.equalsIgnoreCase("OPSNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPSNo));
		}
		if (FCode.equalsIgnoreCase("DiseasBTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseasBTypeCode));
		}
		if (FCode.equalsIgnoreCase("DiseasType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseasType));
		}
		if (FCode.equalsIgnoreCase("DiseasSTypeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseasSTypeCode));
		}
		if (FCode.equalsIgnoreCase("DiseasSType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DiseasSType));
		}
		if (FCode.equalsIgnoreCase("RiskGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskGrade));
		}
		if (FCode.equalsIgnoreCase("OPSName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPSName));
		}
		if (FCode.equalsIgnoreCase("Relapse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relapse));
		}
		if (FCode.equalsIgnoreCase("DisText"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisText));
		}
		if (FCode.equalsIgnoreCase("GestationRela"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GestationRela));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("ObservedTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObservedTime));
		}
		if (FCode.equalsIgnoreCase("JudgeStandard"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgeStandard));
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Year));
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
				strFieldValue = StrTool.GBKToUnicode(ICDCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ICDName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(DiseasDegree);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 4:
				strFieldValue = String.valueOf(Age);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskKind);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OPSFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UWResult);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OPSNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(DiseasBTypeCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(DiseasType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(DiseasSTypeCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(DiseasSType);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RiskGrade);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(OPSName);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Relapse);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(DisText);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(GestationRela);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ObservedTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(JudgeStandard);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Year);
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

		if (FCode.equalsIgnoreCase("ICDCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDCode = FValue.trim();
			}
			else
				ICDCode = null;
		}
		if (FCode.equalsIgnoreCase("ICDName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ICDName = FValue.trim();
			}
			else
				ICDName = null;
		}
		if (FCode.equalsIgnoreCase("DiseasDegree"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseasDegree = FValue.trim();
			}
			else
				DiseasDegree = null;
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Age = i;
			}
		}
		if (FCode.equalsIgnoreCase("RiskKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskKind = FValue.trim();
			}
			else
				RiskKind = null;
		}
		if (FCode.equalsIgnoreCase("OPSFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPSFlag = FValue.trim();
			}
			else
				OPSFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWResult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWResult = FValue.trim();
			}
			else
				UWResult = null;
		}
		if (FCode.equalsIgnoreCase("OPSNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPSNo = FValue.trim();
			}
			else
				OPSNo = null;
		}
		if (FCode.equalsIgnoreCase("DiseasBTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseasBTypeCode = FValue.trim();
			}
			else
				DiseasBTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("DiseasType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseasType = FValue.trim();
			}
			else
				DiseasType = null;
		}
		if (FCode.equalsIgnoreCase("DiseasSTypeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseasSTypeCode = FValue.trim();
			}
			else
				DiseasSTypeCode = null;
		}
		if (FCode.equalsIgnoreCase("DiseasSType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DiseasSType = FValue.trim();
			}
			else
				DiseasSType = null;
		}
		if (FCode.equalsIgnoreCase("RiskGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskGrade = FValue.trim();
			}
			else
				RiskGrade = null;
		}
		if (FCode.equalsIgnoreCase("OPSName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPSName = FValue.trim();
			}
			else
				OPSName = null;
		}
		if (FCode.equalsIgnoreCase("Relapse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relapse = FValue.trim();
			}
			else
				Relapse = null;
		}
		if (FCode.equalsIgnoreCase("DisText"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisText = FValue.trim();
			}
			else
				DisText = null;
		}
		if (FCode.equalsIgnoreCase("GestationRela"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GestationRela = FValue.trim();
			}
			else
				GestationRela = null;
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
		if (FCode.equalsIgnoreCase("ObservedTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ObservedTime = FValue.trim();
			}
			else
				ObservedTime = null;
		}
		if (FCode.equalsIgnoreCase("JudgeStandard"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JudgeStandard = FValue.trim();
			}
			else
				JudgeStandard = null;
		}
		if (FCode.equalsIgnoreCase("Year"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Year = FValue.trim();
			}
			else
				Year = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWAddFeeSchema other = (LDUWAddFeeSchema)otherObject;
		return
			ICDCode.equals(other.getICDCode())
			&& ICDName.equals(other.getICDName())
			&& DiseasDegree.equals(other.getDiseasDegree())
			&& Sex.equals(other.getSex())
			&& Age == other.getAge()
			&& RiskKind.equals(other.getRiskKind())
			&& OPSFlag.equals(other.getOPSFlag())
			&& UWResult.equals(other.getUWResult())
			&& OPSNo.equals(other.getOPSNo())
			&& DiseasBTypeCode.equals(other.getDiseasBTypeCode())
			&& DiseasType.equals(other.getDiseasType())
			&& DiseasSTypeCode.equals(other.getDiseasSTypeCode())
			&& DiseasSType.equals(other.getDiseasSType())
			&& RiskGrade.equals(other.getRiskGrade())
			&& OPSName.equals(other.getOPSName())
			&& Relapse.equals(other.getRelapse())
			&& DisText.equals(other.getDisText())
			&& GestationRela.equals(other.getGestationRela())
			&& State.equals(other.getState())
			&& ObservedTime.equals(other.getObservedTime())
			&& JudgeStandard.equals(other.getJudgeStandard())
			&& Year.equals(other.getYear());
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
		if( strFieldName.equals("ICDCode") ) {
			return 0;
		}
		if( strFieldName.equals("ICDName") ) {
			return 1;
		}
		if( strFieldName.equals("DiseasDegree") ) {
			return 2;
		}
		if( strFieldName.equals("Sex") ) {
			return 3;
		}
		if( strFieldName.equals("Age") ) {
			return 4;
		}
		if( strFieldName.equals("RiskKind") ) {
			return 5;
		}
		if( strFieldName.equals("OPSFlag") ) {
			return 6;
		}
		if( strFieldName.equals("UWResult") ) {
			return 7;
		}
		if( strFieldName.equals("OPSNo") ) {
			return 8;
		}
		if( strFieldName.equals("DiseasBTypeCode") ) {
			return 9;
		}
		if( strFieldName.equals("DiseasType") ) {
			return 10;
		}
		if( strFieldName.equals("DiseasSTypeCode") ) {
			return 11;
		}
		if( strFieldName.equals("DiseasSType") ) {
			return 12;
		}
		if( strFieldName.equals("RiskGrade") ) {
			return 13;
		}
		if( strFieldName.equals("OPSName") ) {
			return 14;
		}
		if( strFieldName.equals("Relapse") ) {
			return 15;
		}
		if( strFieldName.equals("DisText") ) {
			return 16;
		}
		if( strFieldName.equals("GestationRela") ) {
			return 17;
		}
		if( strFieldName.equals("State") ) {
			return 18;
		}
		if( strFieldName.equals("ObservedTime") ) {
			return 19;
		}
		if( strFieldName.equals("JudgeStandard") ) {
			return 20;
		}
		if( strFieldName.equals("Year") ) {
			return 21;
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
				strFieldName = "ICDCode";
				break;
			case 1:
				strFieldName = "ICDName";
				break;
			case 2:
				strFieldName = "DiseasDegree";
				break;
			case 3:
				strFieldName = "Sex";
				break;
			case 4:
				strFieldName = "Age";
				break;
			case 5:
				strFieldName = "RiskKind";
				break;
			case 6:
				strFieldName = "OPSFlag";
				break;
			case 7:
				strFieldName = "UWResult";
				break;
			case 8:
				strFieldName = "OPSNo";
				break;
			case 9:
				strFieldName = "DiseasBTypeCode";
				break;
			case 10:
				strFieldName = "DiseasType";
				break;
			case 11:
				strFieldName = "DiseasSTypeCode";
				break;
			case 12:
				strFieldName = "DiseasSType";
				break;
			case 13:
				strFieldName = "RiskGrade";
				break;
			case 14:
				strFieldName = "OPSName";
				break;
			case 15:
				strFieldName = "Relapse";
				break;
			case 16:
				strFieldName = "DisText";
				break;
			case 17:
				strFieldName = "GestationRela";
				break;
			case 18:
				strFieldName = "State";
				break;
			case 19:
				strFieldName = "ObservedTime";
				break;
			case 20:
				strFieldName = "JudgeStandard";
				break;
			case 21:
				strFieldName = "Year";
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
		if( strFieldName.equals("ICDCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ICDName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseasDegree") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Age") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RiskKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPSFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPSNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseasBTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseasType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseasSTypeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiseasSType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPSName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relapse") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisText") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GestationRela") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObservedTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgeStandard") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Year") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
