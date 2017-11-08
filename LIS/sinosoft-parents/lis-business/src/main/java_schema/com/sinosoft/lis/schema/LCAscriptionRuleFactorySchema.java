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
import com.sinosoft.lis.db.LCAscriptionRuleFactoryDB;

/*
 * <p>ClassName: LCAscriptionRuleFactorySchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保核心
 */
public class LCAscriptionRuleFactorySchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCAscriptionRuleFactorySchema.class);

	// @Field
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 归属规则编码 */
	private String AscriptionRuleCode;
	/** 归属规则名称 */
	private String AscriptionRuleName;
	/** 要素类型 */
	private String FactoryType;
	/** 要素类型对应号码 */
	private String OtherNo;
	/** 计算编码 */
	private String FactoryCode;
	/** 计算子编码 */
	private int FactorySubCode;
	/** 计算名称 */
	private String FactoryName;
	/** 内部流水号 */
	private String InerSerialNo;
	/** 计算sql */
	private String CalSql;
	/** 参数 */
	private String Params;
	/** 计算备注 */
	private String CalRemark;
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
	/** 属性 */
	private String AscriptType;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAscriptionRuleFactorySchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[7];
		pk[0] = "GrpPolNo";
		pk[1] = "AscriptionRuleCode";
		pk[2] = "FactoryType";
		pk[3] = "OtherNo";
		pk[4] = "FactoryCode";
		pk[5] = "FactorySubCode";
		pk[6] = "InerSerialNo";

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
		LCAscriptionRuleFactorySchema cloned = (LCAscriptionRuleFactorySchema)super.clone();
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
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 00-默认值
	*/
	public String getAscriptionRuleCode()
	{
		return AscriptionRuleCode;
	}
	public void setAscriptionRuleCode(String aAscriptionRuleCode)
	{
		AscriptionRuleCode = aAscriptionRuleCode;
	}
	public String getAscriptionRuleName()
	{
		return AscriptionRuleName;
	}
	public void setAscriptionRuleName(String aAscriptionRuleName)
	{
		AscriptionRuleName = aAscriptionRuleName;
	}
	/**
	* 对应LMFactoryMode表中的Type字段<p>
	* 对应保费项
	*/
	public String getFactoryType()
	{
		return FactoryType;
	}
	public void setFactoryType(String aFactoryType)
	{
		FactoryType = aFactoryType;
	}
	/**
	* 缴费项目编码
	*/
	public String getOtherNo()
	{
		return OtherNo;
	}
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	public String getFactoryCode()
	{
		return FactoryCode;
	}
	public void setFactoryCode(String aFactoryCode)
	{
		FactoryCode = aFactoryCode;
	}
	public int getFactorySubCode()
	{
		return FactorySubCode;
	}
	public void setFactorySubCode(int aFactorySubCode)
	{
		FactorySubCode = aFactorySubCode;
	}
	public void setFactorySubCode(String aFactorySubCode)
	{
		if (aFactorySubCode != null && !aFactorySubCode.equals(""))
		{
			Integer tInteger = new Integer(aFactorySubCode);
			int i = tInteger.intValue();
			FactorySubCode = i;
		}
	}

	public String getFactoryName()
	{
		return FactoryName;
	}
	public void setFactoryName(String aFactoryName)
	{
		FactoryName = aFactoryName;
	}
	public String getInerSerialNo()
	{
		return InerSerialNo;
	}
	public void setInerSerialNo(String aInerSerialNo)
	{
		InerSerialNo = aInerSerialNo;
	}
	/**
	* 参数个数
	*/
	public String getCalSql()
	{
		return CalSql;
	}
	public void setCalSql(String aCalSql)
	{
		CalSql = aCalSql;
	}
	public String getParams()
	{
		return Params;
	}
	public void setParams(String aParams)
	{
		Params = aParams;
	}
	/**
	* 参数个数
	*/
	public String getCalRemark()
	{
		return CalRemark;
	}
	public void setCalRemark(String aCalRemark)
	{
		CalRemark = aCalRemark;
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
	* 1|正常|^2|全残|^3|身故|^4|离职
	*/
	public String getAscriptType()
	{
		return AscriptType;
	}
	public void setAscriptType(String aAscriptType)
	{
		AscriptType = aAscriptType;
	}

	/**
	* 使用另外一个 LCAscriptionRuleFactorySchema 对象给 Schema 赋值
	* @param: aLCAscriptionRuleFactorySchema LCAscriptionRuleFactorySchema
	**/
	public void setSchema(LCAscriptionRuleFactorySchema aLCAscriptionRuleFactorySchema)
	{
		this.GrpPolNo = aLCAscriptionRuleFactorySchema.getGrpPolNo();
		this.GrpContNo = aLCAscriptionRuleFactorySchema.getGrpContNo();
		this.RiskCode = aLCAscriptionRuleFactorySchema.getRiskCode();
		this.AscriptionRuleCode = aLCAscriptionRuleFactorySchema.getAscriptionRuleCode();
		this.AscriptionRuleName = aLCAscriptionRuleFactorySchema.getAscriptionRuleName();
		this.FactoryType = aLCAscriptionRuleFactorySchema.getFactoryType();
		this.OtherNo = aLCAscriptionRuleFactorySchema.getOtherNo();
		this.FactoryCode = aLCAscriptionRuleFactorySchema.getFactoryCode();
		this.FactorySubCode = aLCAscriptionRuleFactorySchema.getFactorySubCode();
		this.FactoryName = aLCAscriptionRuleFactorySchema.getFactoryName();
		this.InerSerialNo = aLCAscriptionRuleFactorySchema.getInerSerialNo();
		this.CalSql = aLCAscriptionRuleFactorySchema.getCalSql();
		this.Params = aLCAscriptionRuleFactorySchema.getParams();
		this.CalRemark = aLCAscriptionRuleFactorySchema.getCalRemark();
		this.Operator = aLCAscriptionRuleFactorySchema.getOperator();
		this.MakeDate = fDate.getDate( aLCAscriptionRuleFactorySchema.getMakeDate());
		this.MakeTime = aLCAscriptionRuleFactorySchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCAscriptionRuleFactorySchema.getModifyDate());
		this.ModifyTime = aLCAscriptionRuleFactorySchema.getModifyTime();
		this.AscriptType = aLCAscriptionRuleFactorySchema.getAscriptType();
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

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AscriptionRuleCode") == null )
				this.AscriptionRuleCode = null;
			else
				this.AscriptionRuleCode = rs.getString("AscriptionRuleCode").trim();

			if( rs.getString("AscriptionRuleName") == null )
				this.AscriptionRuleName = null;
			else
				this.AscriptionRuleName = rs.getString("AscriptionRuleName").trim();

			if( rs.getString("FactoryType") == null )
				this.FactoryType = null;
			else
				this.FactoryType = rs.getString("FactoryType").trim();

			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			if( rs.getString("FactoryCode") == null )
				this.FactoryCode = null;
			else
				this.FactoryCode = rs.getString("FactoryCode").trim();

			this.FactorySubCode = rs.getInt("FactorySubCode");
			if( rs.getString("FactoryName") == null )
				this.FactoryName = null;
			else
				this.FactoryName = rs.getString("FactoryName").trim();

			if( rs.getString("InerSerialNo") == null )
				this.InerSerialNo = null;
			else
				this.InerSerialNo = rs.getString("InerSerialNo").trim();

			if( rs.getString("CalSql") == null )
				this.CalSql = null;
			else
				this.CalSql = rs.getString("CalSql").trim();

			if( rs.getString("Params") == null )
				this.Params = null;
			else
				this.Params = rs.getString("Params").trim();

			if( rs.getString("CalRemark") == null )
				this.CalRemark = null;
			else
				this.CalRemark = rs.getString("CalRemark").trim();

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

			if( rs.getString("AscriptType") == null )
				this.AscriptType = null;
			else
				this.AscriptType = rs.getString("AscriptType").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCAscriptionRuleFactory表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactorySchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCAscriptionRuleFactorySchema getSchema()
	{
		LCAscriptionRuleFactorySchema aLCAscriptionRuleFactorySchema = new LCAscriptionRuleFactorySchema();
		aLCAscriptionRuleFactorySchema.setSchema(this);
		return aLCAscriptionRuleFactorySchema;
	}

	public LCAscriptionRuleFactoryDB getDB()
	{
		LCAscriptionRuleFactoryDB aDBOper = new LCAscriptionRuleFactoryDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAscriptionRuleFactory描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionRuleCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptionRuleName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FactorySubCode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FactoryName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InerSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSql)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Params)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AscriptType));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAscriptionRuleFactory>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AscriptionRuleCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AscriptionRuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			FactoryType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			FactoryCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			FactorySubCode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			FactoryName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			InerSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CalSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Params = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CalRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			AscriptType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAscriptionRuleFactorySchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionRuleCode));
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptionRuleName));
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryType));
		}
		if (FCode.equalsIgnoreCase("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryCode));
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactorySubCode));
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FactoryName));
		}
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InerSerialNo));
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSql));
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Params));
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalRemark));
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
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AscriptType));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AscriptionRuleCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AscriptionRuleName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(FactoryType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(FactoryCode);
				break;
			case 8:
				strFieldValue = String.valueOf(FactorySubCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(FactoryName);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(InerSerialNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CalSql);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Params);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CalRemark);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(AscriptType);
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
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
		if (FCode.equalsIgnoreCase("AscriptionRuleCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionRuleCode = FValue.trim();
			}
			else
				AscriptionRuleCode = null;
		}
		if (FCode.equalsIgnoreCase("AscriptionRuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptionRuleName = FValue.trim();
			}
			else
				AscriptionRuleName = null;
		}
		if (FCode.equalsIgnoreCase("FactoryType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryType = FValue.trim();
			}
			else
				FactoryType = null;
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
		if (FCode.equalsIgnoreCase("FactoryCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryCode = FValue.trim();
			}
			else
				FactoryCode = null;
		}
		if (FCode.equalsIgnoreCase("FactorySubCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FactorySubCode = i;
			}
		}
		if (FCode.equalsIgnoreCase("FactoryName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FactoryName = FValue.trim();
			}
			else
				FactoryName = null;
		}
		if (FCode.equalsIgnoreCase("InerSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InerSerialNo = FValue.trim();
			}
			else
				InerSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("CalSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSql = FValue.trim();
			}
			else
				CalSql = null;
		}
		if (FCode.equalsIgnoreCase("Params"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Params = FValue.trim();
			}
			else
				Params = null;
		}
		if (FCode.equalsIgnoreCase("CalRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalRemark = FValue.trim();
			}
			else
				CalRemark = null;
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
		if (FCode.equalsIgnoreCase("AscriptType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AscriptType = FValue.trim();
			}
			else
				AscriptType = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCAscriptionRuleFactorySchema other = (LCAscriptionRuleFactorySchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AscriptionRuleCode.equals(other.getAscriptionRuleCode())
			&& AscriptionRuleName.equals(other.getAscriptionRuleName())
			&& FactoryType.equals(other.getFactoryType())
			&& OtherNo.equals(other.getOtherNo())
			&& FactoryCode.equals(other.getFactoryCode())
			&& FactorySubCode == other.getFactorySubCode()
			&& FactoryName.equals(other.getFactoryName())
			&& InerSerialNo.equals(other.getInerSerialNo())
			&& CalSql.equals(other.getCalSql())
			&& Params.equals(other.getParams())
			&& CalRemark.equals(other.getCalRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& AscriptType.equals(other.getAscriptType());
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
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return 3;
		}
		if( strFieldName.equals("AscriptionRuleName") ) {
			return 4;
		}
		if( strFieldName.equals("FactoryType") ) {
			return 5;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 6;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return 7;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return 8;
		}
		if( strFieldName.equals("FactoryName") ) {
			return 9;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return 10;
		}
		if( strFieldName.equals("CalSql") ) {
			return 11;
		}
		if( strFieldName.equals("Params") ) {
			return 12;
		}
		if( strFieldName.equals("CalRemark") ) {
			return 13;
		}
		if( strFieldName.equals("Operator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 18;
		}
		if( strFieldName.equals("AscriptType") ) {
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "AscriptionRuleCode";
				break;
			case 4:
				strFieldName = "AscriptionRuleName";
				break;
			case 5:
				strFieldName = "FactoryType";
				break;
			case 6:
				strFieldName = "OtherNo";
				break;
			case 7:
				strFieldName = "FactoryCode";
				break;
			case 8:
				strFieldName = "FactorySubCode";
				break;
			case 9:
				strFieldName = "FactoryName";
				break;
			case 10:
				strFieldName = "InerSerialNo";
				break;
			case 11:
				strFieldName = "CalSql";
				break;
			case 12:
				strFieldName = "Params";
				break;
			case 13:
				strFieldName = "CalRemark";
				break;
			case 14:
				strFieldName = "Operator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyDate";
				break;
			case 18:
				strFieldName = "ModifyTime";
				break;
			case 19:
				strFieldName = "AscriptType";
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionRuleCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AscriptionRuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactoryCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FactorySubCode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FactoryName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InerSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Params") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalRemark") ) {
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
		if( strFieldName.equals("AscriptType") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
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
