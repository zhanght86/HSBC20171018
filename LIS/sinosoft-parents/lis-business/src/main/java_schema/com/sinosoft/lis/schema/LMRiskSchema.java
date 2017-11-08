

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
import com.sinosoft.lis.db.LMRiskDB;

/*
 * <p>ClassName: LMRiskSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskSchema implements Schema, Cloneable
{
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 险种简称 */
	private String RiskShortName;
	/** 险种英文名称 */
	private String RiskEnName;
	/** 险种英文简称 */
	private String RiskEnShortName;
	/** 选择责任标记 */
	private String ChoDutyFlag;
	/** 续期收费标记 */
	private String CPayFlag;
	/** 生存给付标记 */
	private String GetFlag;
	/** 保全标记 */
	private String EdorFlag;
	/** 续保标记 */
	private String RnewFlag;
	/** 核保标记 */
	private String UWFlag;
	/** 分保标记 */
	private String RinsFlag;
	/** 保险帐户标记 */
	private String InsuAccFlag;
	/** 预定利率 */
	private double DestRate;
	/** 原险种编码 */
	private String OrigRiskCode;
	/** 子版本号 */
	private String SubRiskVer;
	/** 险种统计名称 */
	private String RiskStatName;

	public static final int FIELDNUM = 18;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

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
		LMRiskSchema cloned = (LMRiskSchema)super.clone();
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
	public String getRiskVer()
	{
		return RiskVer;
	}
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	public String getRiskName()
	{
		return RiskName;
	}
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	public String getRiskShortName()
	{
		return RiskShortName;
	}
	public void setRiskShortName(String aRiskShortName)
	{
		RiskShortName = aRiskShortName;
	}
	public String getRiskEnName()
	{
		return RiskEnName;
	}
	public void setRiskEnName(String aRiskEnName)
	{
		RiskEnName = aRiskEnName;
	}
	public String getRiskEnShortName()
	{
		return RiskEnShortName;
	}
	public void setRiskEnShortName(String aRiskEnShortName)
	{
		RiskEnShortName = aRiskEnShortName;
	}
	/**
	* Y--是 N--否
	*/
	public String getChoDutyFlag()
	{
		return ChoDutyFlag;
	}
	public void setChoDutyFlag(String aChoDutyFlag)
	{
		ChoDutyFlag = aChoDutyFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getCPayFlag()
	{
		return CPayFlag;
	}
	public void setCPayFlag(String aCPayFlag)
	{
		CPayFlag = aCPayFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getGetFlag()
	{
		return GetFlag;
	}
	public void setGetFlag(String aGetFlag)
	{
		GetFlag = aGetFlag;
	}
	/**
	* Y--是、N--否
	*/
	public String getEdorFlag()
	{
		return EdorFlag;
	}
	public void setEdorFlag(String aEdorFlag)
	{
		EdorFlag = aEdorFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getRnewFlag()
	{
		return RnewFlag;
	}
	public void setRnewFlag(String aRnewFlag)
	{
		RnewFlag = aRnewFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	/**
	* Y--是 N--否
	*/
	public String getRinsFlag()
	{
		return RinsFlag;
	}
	public void setRinsFlag(String aRinsFlag)
	{
		RinsFlag = aRinsFlag;
	}
	/**
	* Y--是<p>
	* N--否
	*/
	public String getInsuAccFlag()
	{
		return InsuAccFlag;
	}
	public void setInsuAccFlag(String aInsuAccFlag)
	{
		InsuAccFlag = aInsuAccFlag;
	}
	/**
	* 该险种的预定利率,利差返还时用到
	*/
	public double getDestRate()
	{
		return DestRate;
	}
	public void setDestRate(double aDestRate)
	{
		DestRate = aDestRate;
	}
	public void setDestRate(String aDestRate)
	{
		if (aDestRate != null && !aDestRate.equals(""))
		{
			Double tDouble = new Double(aDestRate);
			double d = tDouble.doubleValue();
			DestRate = d;
		}
	}

	public String getOrigRiskCode()
	{
		return OrigRiskCode;
	}
	public void setOrigRiskCode(String aOrigRiskCode)
	{
		OrigRiskCode = aOrigRiskCode;
	}
	public String getSubRiskVer()
	{
		return SubRiskVer;
	}
	public void setSubRiskVer(String aSubRiskVer)
	{
		SubRiskVer = aSubRiskVer;
	}
	/**
	* 统计时显示的名称
	*/
	public String getRiskStatName()
	{
		return RiskStatName;
	}
	public void setRiskStatName(String aRiskStatName)
	{
		RiskStatName = aRiskStatName;
	}

	/**
	* 使用另外一个 LMRiskSchema 对象给 Schema 赋值
	* @param: aLMRiskSchema LMRiskSchema
	**/
	public void setSchema(LMRiskSchema aLMRiskSchema)
	{
		this.RiskCode = aLMRiskSchema.getRiskCode();
		this.RiskVer = aLMRiskSchema.getRiskVer();
		this.RiskName = aLMRiskSchema.getRiskName();
		this.RiskShortName = aLMRiskSchema.getRiskShortName();
		this.RiskEnName = aLMRiskSchema.getRiskEnName();
		this.RiskEnShortName = aLMRiskSchema.getRiskEnShortName();
		this.ChoDutyFlag = aLMRiskSchema.getChoDutyFlag();
		this.CPayFlag = aLMRiskSchema.getCPayFlag();
		this.GetFlag = aLMRiskSchema.getGetFlag();
		this.EdorFlag = aLMRiskSchema.getEdorFlag();
		this.RnewFlag = aLMRiskSchema.getRnewFlag();
		this.UWFlag = aLMRiskSchema.getUWFlag();
		this.RinsFlag = aLMRiskSchema.getRinsFlag();
		this.InsuAccFlag = aLMRiskSchema.getInsuAccFlag();
		this.DestRate = aLMRiskSchema.getDestRate();
		this.OrigRiskCode = aLMRiskSchema.getOrigRiskCode();
		this.SubRiskVer = aLMRiskSchema.getSubRiskVer();
		this.RiskStatName = aLMRiskSchema.getRiskStatName();
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

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("RiskShortName") == null )
				this.RiskShortName = null;
			else
				this.RiskShortName = rs.getString("RiskShortName").trim();

			if( rs.getString("RiskEnName") == null )
				this.RiskEnName = null;
			else
				this.RiskEnName = rs.getString("RiskEnName").trim();

			if( rs.getString("RiskEnShortName") == null )
				this.RiskEnShortName = null;
			else
				this.RiskEnShortName = rs.getString("RiskEnShortName").trim();

			if( rs.getString("ChoDutyFlag") == null )
				this.ChoDutyFlag = null;
			else
				this.ChoDutyFlag = rs.getString("ChoDutyFlag").trim();

			if( rs.getString("CPayFlag") == null )
				this.CPayFlag = null;
			else
				this.CPayFlag = rs.getString("CPayFlag").trim();

			if( rs.getString("GetFlag") == null )
				this.GetFlag = null;
			else
				this.GetFlag = rs.getString("GetFlag").trim();

			if( rs.getString("EdorFlag") == null )
				this.EdorFlag = null;
			else
				this.EdorFlag = rs.getString("EdorFlag").trim();

			if( rs.getString("RnewFlag") == null )
				this.RnewFlag = null;
			else
				this.RnewFlag = rs.getString("RnewFlag").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("RinsFlag") == null )
				this.RinsFlag = null;
			else
				this.RinsFlag = rs.getString("RinsFlag").trim();

			if( rs.getString("InsuAccFlag") == null )
				this.InsuAccFlag = null;
			else
				this.InsuAccFlag = rs.getString("InsuAccFlag").trim();

			this.DestRate = rs.getDouble("DestRate");
			if( rs.getString("OrigRiskCode") == null )
				this.OrigRiskCode = null;
			else
				this.OrigRiskCode = rs.getString("OrigRiskCode").trim();

			if( rs.getString("SubRiskVer") == null )
				this.SubRiskVer = null;
			else
				this.SubRiskVer = rs.getString("SubRiskVer").trim();

			if( rs.getString("RiskStatName") == null )
				this.RiskStatName = null;
			else
				this.RiskStatName = rs.getString("RiskStatName").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMRisk表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskSchema getSchema()
	{
		LMRiskSchema aLMRiskSchema = new LMRiskSchema();
		aLMRiskSchema.setSchema(this);
		return aLMRiskSchema;
	}

	public LMRiskDB getDB()
	{
		LMRiskDB aDBOper = new LMRiskDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRisk描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskEnName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskEnShortName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChoDutyFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CPayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GetFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RnewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RinsFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DestRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OrigRiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubRiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskStatName));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRisk>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RiskEnName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskEnShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ChoDutyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CPayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GetFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			EdorFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RnewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RinsFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			InsuAccFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DestRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			OrigRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			SubRiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			RiskStatName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskSchema";
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equalsIgnoreCase("RiskShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskShortName));
		}
		if (FCode.equalsIgnoreCase("RiskEnName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskEnName));
		}
		if (FCode.equalsIgnoreCase("RiskEnShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskEnShortName));
		}
		if (FCode.equalsIgnoreCase("ChoDutyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChoDutyFlag));
		}
		if (FCode.equalsIgnoreCase("CPayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CPayFlag));
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetFlag));
		}
		if (FCode.equalsIgnoreCase("EdorFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorFlag));
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewFlag));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("RinsFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RinsFlag));
		}
		if (FCode.equalsIgnoreCase("InsuAccFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccFlag));
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DestRate));
		}
		if (FCode.equalsIgnoreCase("OrigRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OrigRiskCode));
		}
		if (FCode.equalsIgnoreCase("SubRiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskVer));
		}
		if (FCode.equalsIgnoreCase("RiskStatName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskStatName));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskShortName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RiskEnName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskEnShortName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ChoDutyFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CPayFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GetFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(EdorFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RnewFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RinsFlag);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(InsuAccFlag);
				break;
			case 14:
				strFieldValue = String.valueOf(DestRate);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(OrigRiskCode);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SubRiskVer);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(RiskStatName);
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
		if (FCode.equalsIgnoreCase("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equalsIgnoreCase("RiskShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskShortName = FValue.trim();
			}
			else
				RiskShortName = null;
		}
		if (FCode.equalsIgnoreCase("RiskEnName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskEnName = FValue.trim();
			}
			else
				RiskEnName = null;
		}
		if (FCode.equalsIgnoreCase("RiskEnShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskEnShortName = FValue.trim();
			}
			else
				RiskEnShortName = null;
		}
		if (FCode.equalsIgnoreCase("ChoDutyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChoDutyFlag = FValue.trim();
			}
			else
				ChoDutyFlag = null;
		}
		if (FCode.equalsIgnoreCase("CPayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CPayFlag = FValue.trim();
			}
			else
				CPayFlag = null;
		}
		if (FCode.equalsIgnoreCase("GetFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GetFlag = FValue.trim();
			}
			else
				GetFlag = null;
		}
		if (FCode.equalsIgnoreCase("EdorFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorFlag = FValue.trim();
			}
			else
				EdorFlag = null;
		}
		if (FCode.equalsIgnoreCase("RnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RnewFlag = FValue.trim();
			}
			else
				RnewFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("RinsFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RinsFlag = FValue.trim();
			}
			else
				RinsFlag = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccFlag = FValue.trim();
			}
			else
				InsuAccFlag = null;
		}
		if (FCode.equalsIgnoreCase("DestRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DestRate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OrigRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OrigRiskCode = FValue.trim();
			}
			else
				OrigRiskCode = null;
		}
		if (FCode.equalsIgnoreCase("SubRiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskVer = FValue.trim();
			}
			else
				SubRiskVer = null;
		}
		if (FCode.equalsIgnoreCase("RiskStatName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskStatName = FValue.trim();
			}
			else
				RiskStatName = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskSchema other = (LMRiskSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& RiskShortName.equals(other.getRiskShortName())
			&& RiskEnName.equals(other.getRiskEnName())
			&& RiskEnShortName.equals(other.getRiskEnShortName())
			&& ChoDutyFlag.equals(other.getChoDutyFlag())
			&& CPayFlag.equals(other.getCPayFlag())
			&& GetFlag.equals(other.getGetFlag())
			&& EdorFlag.equals(other.getEdorFlag())
			&& RnewFlag.equals(other.getRnewFlag())
			&& UWFlag.equals(other.getUWFlag())
			&& RinsFlag.equals(other.getRinsFlag())
			&& InsuAccFlag.equals(other.getInsuAccFlag())
			&& DestRate == other.getDestRate()
			&& OrigRiskCode.equals(other.getOrigRiskCode())
			&& SubRiskVer.equals(other.getSubRiskVer())
			&& RiskStatName.equals(other.getRiskStatName());
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
		if( strFieldName.equals("RiskVer") ) {
			return 1;
		}
		if( strFieldName.equals("RiskName") ) {
			return 2;
		}
		if( strFieldName.equals("RiskShortName") ) {
			return 3;
		}
		if( strFieldName.equals("RiskEnName") ) {
			return 4;
		}
		if( strFieldName.equals("RiskEnShortName") ) {
			return 5;
		}
		if( strFieldName.equals("ChoDutyFlag") ) {
			return 6;
		}
		if( strFieldName.equals("CPayFlag") ) {
			return 7;
		}
		if( strFieldName.equals("GetFlag") ) {
			return 8;
		}
		if( strFieldName.equals("EdorFlag") ) {
			return 9;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return 10;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 11;
		}
		if( strFieldName.equals("RinsFlag") ) {
			return 12;
		}
		if( strFieldName.equals("InsuAccFlag") ) {
			return 13;
		}
		if( strFieldName.equals("DestRate") ) {
			return 14;
		}
		if( strFieldName.equals("OrigRiskCode") ) {
			return 15;
		}
		if( strFieldName.equals("SubRiskVer") ) {
			return 16;
		}
		if( strFieldName.equals("RiskStatName") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "RiskShortName";
				break;
			case 4:
				strFieldName = "RiskEnName";
				break;
			case 5:
				strFieldName = "RiskEnShortName";
				break;
			case 6:
				strFieldName = "ChoDutyFlag";
				break;
			case 7:
				strFieldName = "CPayFlag";
				break;
			case 8:
				strFieldName = "GetFlag";
				break;
			case 9:
				strFieldName = "EdorFlag";
				break;
			case 10:
				strFieldName = "RnewFlag";
				break;
			case 11:
				strFieldName = "UWFlag";
				break;
			case 12:
				strFieldName = "RinsFlag";
				break;
			case 13:
				strFieldName = "InsuAccFlag";
				break;
			case 14:
				strFieldName = "DestRate";
				break;
			case 15:
				strFieldName = "OrigRiskCode";
				break;
			case 16:
				strFieldName = "SubRiskVer";
				break;
			case 17:
				strFieldName = "RiskStatName";
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
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskEnName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskEnShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChoDutyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CPayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GetFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RinsFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DestRate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OrigRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskStatName") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
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
