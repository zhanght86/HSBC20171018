

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
import com.sinosoft.lis.db.PD_LMDutyPayAddFeeDB;

/*
 * <p>ClassName: PD_LMDutyPayAddFeeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 产品定义平台_PDM
 */
public class PD_LMDutyPayAddFeeSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 责任代码 */
	private String DutyCode;
	/** 加费类型 */
	private String AddFeeType;
	/** 加费对象 */
	private String AddFeeObject;
	/** 加费算法 */
	private String AddFeeCalCode;
	/** 加费评点最大值 */
	private double AddPointLimit;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String Standbyflag2;
	/** Standbyflag3 */
	private int Standbyflag3;
	/** Standbyflag4 */
	private int Standbyflag4;
	/** Standbyflag5 */
	private double Standbyflag5;
	/** Standbyflag6 */
	private double Standbyflag6;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMDutyPayAddFeeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "RiskCode";
		pk[1] = "DutyCode";
		pk[2] = "AddFeeType";
		pk[3] = "AddFeeObject";

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
		PD_LMDutyPayAddFeeSchema cloned = (PD_LMDutyPayAddFeeSchema)super.clone();
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
	/**
	* 00 默认 01 首期健康 02 首期职业 03 复效健康 04 复效职业*
	*/
	public String getAddFeeType()
	{
		return AddFeeType;
	}
	public void setAddFeeType(String aAddFeeType)
	{
		AddFeeType = aAddFeeType;
	}
	/**
	* 00 默认 01投保人 02单一被保险人 03多被保险人*
	*/
	public String getAddFeeObject()
	{
		return AddFeeObject;
	}
	public void setAddFeeObject(String aAddFeeObject)
	{
		AddFeeObject = aAddFeeObject;
	}
	/**
	* 描述加费公式的算法*
	*/
	public String getAddFeeCalCode()
	{
		return AddFeeCalCode;
	}
	public void setAddFeeCalCode(String aAddFeeCalCode)
	{
		AddFeeCalCode = aAddFeeCalCode;
	}
	public double getAddPointLimit()
	{
		return AddPointLimit;
	}
	public void setAddPointLimit(double aAddPointLimit)
	{
		AddPointLimit = aAddPointLimit;
	}
	public void setAddPointLimit(String aAddPointLimit)
	{
		if (aAddPointLimit != null && !aAddPointLimit.equals(""))
		{
			Double tDouble = new Double(aAddPointLimit);
			double d = tDouble.doubleValue();
			AddPointLimit = d;
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
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		Standbyflag1 = aStandbyflag1;
	}
	public String getStandbyflag2()
	{
		return Standbyflag2;
	}
	public void setStandbyflag2(String aStandbyflag2)
	{
		Standbyflag2 = aStandbyflag2;
	}
	public int getStandbyflag3()
	{
		return Standbyflag3;
	}
	public void setStandbyflag3(int aStandbyflag3)
	{
		Standbyflag3 = aStandbyflag3;
	}
	public void setStandbyflag3(String aStandbyflag3)
	{
		if (aStandbyflag3 != null && !aStandbyflag3.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag3);
			int i = tInteger.intValue();
			Standbyflag3 = i;
		}
	}

	public int getStandbyflag4()
	{
		return Standbyflag4;
	}
	public void setStandbyflag4(int aStandbyflag4)
	{
		Standbyflag4 = aStandbyflag4;
	}
	public void setStandbyflag4(String aStandbyflag4)
	{
		if (aStandbyflag4 != null && !aStandbyflag4.equals(""))
		{
			Integer tInteger = new Integer(aStandbyflag4);
			int i = tInteger.intValue();
			Standbyflag4 = i;
		}
	}

	public double getStandbyflag5()
	{
		return Standbyflag5;
	}
	public void setStandbyflag5(double aStandbyflag5)
	{
		Standbyflag5 = aStandbyflag5;
	}
	public void setStandbyflag5(String aStandbyflag5)
	{
		if (aStandbyflag5 != null && !aStandbyflag5.equals(""))
		{
			Double tDouble = new Double(aStandbyflag5);
			double d = tDouble.doubleValue();
			Standbyflag5 = d;
		}
	}

	public double getStandbyflag6()
	{
		return Standbyflag6;
	}
	public void setStandbyflag6(double aStandbyflag6)
	{
		Standbyflag6 = aStandbyflag6;
	}
	public void setStandbyflag6(String aStandbyflag6)
	{
		if (aStandbyflag6 != null && !aStandbyflag6.equals(""))
		{
			Double tDouble = new Double(aStandbyflag6);
			double d = tDouble.doubleValue();
			Standbyflag6 = d;
		}
	}


	/**
	* 使用另外一个 PD_LMDutyPayAddFeeSchema 对象给 Schema 赋值
	* @param: aPD_LMDutyPayAddFeeSchema PD_LMDutyPayAddFeeSchema
	**/
	public void setSchema(PD_LMDutyPayAddFeeSchema aPD_LMDutyPayAddFeeSchema)
	{
		this.RiskCode = aPD_LMDutyPayAddFeeSchema.getRiskCode();
		this.DutyCode = aPD_LMDutyPayAddFeeSchema.getDutyCode();
		this.AddFeeType = aPD_LMDutyPayAddFeeSchema.getAddFeeType();
		this.AddFeeObject = aPD_LMDutyPayAddFeeSchema.getAddFeeObject();
		this.AddFeeCalCode = aPD_LMDutyPayAddFeeSchema.getAddFeeCalCode();
		this.AddPointLimit = aPD_LMDutyPayAddFeeSchema.getAddPointLimit();
		this.Operator = aPD_LMDutyPayAddFeeSchema.getOperator();
		this.MakeDate = fDate.getDate( aPD_LMDutyPayAddFeeSchema.getMakeDate());
		this.MakeTime = aPD_LMDutyPayAddFeeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMDutyPayAddFeeSchema.getModifyDate());
		this.ModifyTime = aPD_LMDutyPayAddFeeSchema.getModifyTime();
		this.Standbyflag1 = aPD_LMDutyPayAddFeeSchema.getStandbyflag1();
		this.Standbyflag2 = aPD_LMDutyPayAddFeeSchema.getStandbyflag2();
		this.Standbyflag3 = aPD_LMDutyPayAddFeeSchema.getStandbyflag3();
		this.Standbyflag4 = aPD_LMDutyPayAddFeeSchema.getStandbyflag4();
		this.Standbyflag5 = aPD_LMDutyPayAddFeeSchema.getStandbyflag5();
		this.Standbyflag6 = aPD_LMDutyPayAddFeeSchema.getStandbyflag6();
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

			if( rs.getString("AddFeeType") == null )
				this.AddFeeType = null;
			else
				this.AddFeeType = rs.getString("AddFeeType").trim();

			if( rs.getString("AddFeeObject") == null )
				this.AddFeeObject = null;
			else
				this.AddFeeObject = rs.getString("AddFeeObject").trim();

			if( rs.getString("AddFeeCalCode") == null )
				this.AddFeeCalCode = null;
			else
				this.AddFeeCalCode = rs.getString("AddFeeCalCode").trim();

			this.AddPointLimit = rs.getDouble("AddPointLimit");
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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("Standbyflag2") == null )
				this.Standbyflag2 = null;
			else
				this.Standbyflag2 = rs.getString("Standbyflag2").trim();

			this.Standbyflag3 = rs.getInt("Standbyflag3");
			this.Standbyflag4 = rs.getInt("Standbyflag4");
			this.Standbyflag5 = rs.getDouble("Standbyflag5");
			this.Standbyflag6 = rs.getDouble("Standbyflag6");
		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的PD_LMDutyPayAddFee表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPayAddFeeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMDutyPayAddFeeSchema getSchema()
	{
		PD_LMDutyPayAddFeeSchema aPD_LMDutyPayAddFeeSchema = new PD_LMDutyPayAddFeeSchema();
		aPD_LMDutyPayAddFeeSchema.setSchema(this);
		return aPD_LMDutyPayAddFeeSchema;
	}

	public PD_LMDutyPayAddFeeDB getDB()
	{
		PD_LMDutyPayAddFeeDB aDBOper = new PD_LMDutyPayAddFeeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyPayAddFee描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DutyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeObject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddPointLimit));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag4));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag5));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Standbyflag6));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMDutyPayAddFee>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			DutyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AddFeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AddFeeObject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AddFeeCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AddPointLimit = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Standbyflag3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag4= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Standbyflag5 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			Standbyflag6 = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMDutyPayAddFeeSchema";
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
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeType));
		}
		if (FCode.equalsIgnoreCase("AddFeeObject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeObject));
		}
		if (FCode.equalsIgnoreCase("AddFeeCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeCalCode));
		}
		if (FCode.equalsIgnoreCase("AddPointLimit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPointLimit));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag2));
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag3));
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag4));
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag5));
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag6));
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
				strFieldValue = StrTool.GBKToUnicode(AddFeeType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AddFeeObject);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AddFeeCalCode);
				break;
			case 5:
				strFieldValue = String.valueOf(AddPointLimit);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag2);
				break;
			case 13:
				strFieldValue = String.valueOf(Standbyflag3);
				break;
			case 14:
				strFieldValue = String.valueOf(Standbyflag4);
				break;
			case 15:
				strFieldValue = String.valueOf(Standbyflag5);
				break;
			case 16:
				strFieldValue = String.valueOf(Standbyflag6);
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
		if (FCode.equalsIgnoreCase("AddFeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeType = FValue.trim();
			}
			else
				AddFeeType = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeObject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeObject = FValue.trim();
			}
			else
				AddFeeObject = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeCalCode = FValue.trim();
			}
			else
				AddFeeCalCode = null;
		}
		if (FCode.equalsIgnoreCase("AddPointLimit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPointLimit = d;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag2 = FValue.trim();
			}
			else
				Standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("Standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Standbyflag4 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag5 = d;
			}
		}
		if (FCode.equalsIgnoreCase("Standbyflag6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Standbyflag6 = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		PD_LMDutyPayAddFeeSchema other = (PD_LMDutyPayAddFeeSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& DutyCode.equals(other.getDutyCode())
			&& AddFeeType.equals(other.getAddFeeType())
			&& AddFeeObject.equals(other.getAddFeeObject())
			&& AddFeeCalCode.equals(other.getAddFeeCalCode())
			&& AddPointLimit == other.getAddPointLimit()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& Standbyflag2.equals(other.getStandbyflag2())
			&& Standbyflag3 == other.getStandbyflag3()
			&& Standbyflag4 == other.getStandbyflag4()
			&& Standbyflag5 == other.getStandbyflag5()
			&& Standbyflag6 == other.getStandbyflag6();
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
		if( strFieldName.equals("AddFeeType") ) {
			return 2;
		}
		if( strFieldName.equals("AddFeeObject") ) {
			return 3;
		}
		if( strFieldName.equals("AddFeeCalCode") ) {
			return 4;
		}
		if( strFieldName.equals("AddPointLimit") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 11;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return 12;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return 13;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return 14;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return 15;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return 16;
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
				strFieldName = "AddFeeType";
				break;
			case 3:
				strFieldName = "AddFeeObject";
				break;
			case 4:
				strFieldName = "AddFeeCalCode";
				break;
			case 5:
				strFieldName = "AddPointLimit";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "Standbyflag1";
				break;
			case 12:
				strFieldName = "Standbyflag2";
				break;
			case 13:
				strFieldName = "Standbyflag3";
				break;
			case 14:
				strFieldName = "Standbyflag4";
				break;
			case 15:
				strFieldName = "Standbyflag5";
				break;
			case 16:
				strFieldName = "Standbyflag6";
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
		if( strFieldName.equals("AddFeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeObject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPointLimit") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Standbyflag3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag4") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Standbyflag5") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Standbyflag6") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_DATE;
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
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
