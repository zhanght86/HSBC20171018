

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
import com.sinosoft.lis.db.LMUWDB;

/*
 * <p>ClassName: LMUWSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMUWSchema implements Schema, Cloneable
{
	// @Field
	/** 核保编码 */
	private String UWCode;
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 关联保单类型 */
	private String RelaPolType;
	/** 核保类型 */
	private String UWType;
	/** 核保顺序号 */
	private int UWOrder;
	/** 算法 */
	private String CalCode;
	/** 其他算法 */
	private String OthCalCode;
	/** 备注 */
	private String Remark;
	/** 核保级别 */
	private String UWGrade;
	/** 核保结果控制 */
	private String UWResult;
	/** 核保通过标记 */
	private String PassFlag;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMUWSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "UWCode";

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
		LMUWSchema cloned = (LMUWSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUWCode()
	{
		return UWCode;
	}
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
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
	/**
	* 承保核保<p>
	* G--分单(Group)<p>
	* I--个单(Individual)<p>
	* <p>
	* <p>
	* 保全核保<p>
	* GC--团单总单(Group Contract)<p>
	* GP-团单险种单<p>
	* GIC--团单下个单总单(Group Individual Contract)<p>
	* GIP--团单下个单险种单(Group Individual Pol)<p>
	* <p>
	* IC--个单总单(Individual Contract)<p>
	* IP--个单下险种单(Individual Pol)
	*/
	public String getRelaPolType()
	{
		return RelaPolType;
	}
	public void setRelaPolType(String aRelaPolType)
	{
		RelaPolType = aRelaPolType;
	}
	/**
	* 1--个单、团单险种的承保核保<p>
	* 11 -- 个单、团单险种承保核保的公共规则<p>
	* 12 －－ 个单、团单人工核保上报核保级别<p>
	* 13 －－ 个单人工核保非标准体核保级别校验<p>
	* 14 －－ 个单人工核保拒保延期级别校验<p>
	* 15 －－ 团体下个人核保规则<p>
	* 16 －－ 团体下个人上报核保级别<p>
	* 19 －－ 个单、团单合同核保规则<p>
	* <p>
	* <p>
	* <p>
	* 2--保全核保<p>
	* 21 -- 保全个单承保核保的公共规则<p>
	* 22 －－ 保全个单人工核保上报核保级别<p>
	* 23 －－ 保全个单人工核保非标准体核保级别校验<p>
	* 24 －－ 保全个单人工核保拒保延期级别校验<p>
	* 25 －－ 保全团体下个人核保规则<p>
	* <p>
	* <p>
	* <p>
	* XX--保全项目<p>
	* <p>
	* 3--续保核保、<p>
	* 4--核赔
	*/
	public String getUWType()
	{
		return UWType;
	}
	public void setUWType(String aUWType)
	{
		UWType = aUWType;
	}
	public int getUWOrder()
	{
		return UWOrder;
	}
	public void setUWOrder(int aUWOrder)
	{
		UWOrder = aUWOrder;
	}
	public void setUWOrder(String aUWOrder)
	{
		if (aUWOrder != null && !aUWOrder.equals(""))
		{
			Integer tInteger = new Integer(aUWOrder);
			int i = tInteger.intValue();
			UWOrder = i;
		}
	}

	public String getCalCode()
	{
		return CalCode;
	}
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	public String getOthCalCode()
	{
		return OthCalCode;
	}
	public void setOthCalCode(String aOthCalCode)
	{
		OthCalCode = aOthCalCode;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/**
	* 核保核赔时有用,分级核保核赔.
	*/
	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}
	/**
	* 对核保核赔,返回上报级别.<p>
	* 对控制描述,返回控制类型:<p>
	* 对 checkfield<p>
	* F(f)  返回值,不显示. 控制附险，定额单等<p>
	* N(n)  显示提示信息,display p_rta1t<p>
	* H(h)  不显示提示信息,不display p_rta1t<p>
	* Y(y)  不合要求才显示,显示提示信息
	*/
	public String getUWResult()
	{
		return UWResult;
	}
	public void setUWResult(String aUWResult)
	{
		UWResult = aUWResult;
	}
	/**
	* 核保可通过标记(“N”-不能核保通过标志)在remark中记录标记, 在外部关联程序修改此标记后,可以由核保员决定是否核保通过
	*/
	public String getPassFlag()
	{
		return PassFlag;
	}
	public void setPassFlag(String aPassFlag)
	{
		PassFlag = aPassFlag;
	}

	/**
	* 使用另外一个 LMUWSchema 对象给 Schema 赋值
	* @param: aLMUWSchema LMUWSchema
	**/
	public void setSchema(LMUWSchema aLMUWSchema)
	{
		this.UWCode = aLMUWSchema.getUWCode();
		this.RiskCode = aLMUWSchema.getRiskCode();
		this.RiskVer = aLMUWSchema.getRiskVer();
		this.RiskName = aLMUWSchema.getRiskName();
		this.RelaPolType = aLMUWSchema.getRelaPolType();
		this.UWType = aLMUWSchema.getUWType();
		this.UWOrder = aLMUWSchema.getUWOrder();
		this.CalCode = aLMUWSchema.getCalCode();
		this.OthCalCode = aLMUWSchema.getOthCalCode();
		this.Remark = aLMUWSchema.getRemark();
		this.UWGrade = aLMUWSchema.getUWGrade();
		this.UWResult = aLMUWSchema.getUWResult();
		this.PassFlag = aLMUWSchema.getPassFlag();
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
			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

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

			if( rs.getString("RelaPolType") == null )
				this.RelaPolType = null;
			else
				this.RelaPolType = rs.getString("RelaPolType").trim();

			if( rs.getString("UWType") == null )
				this.UWType = null;
			else
				this.UWType = rs.getString("UWType").trim();

			this.UWOrder = rs.getInt("UWOrder");
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("OthCalCode") == null )
				this.OthCalCode = null;
			else
				this.OthCalCode = rs.getString("OthCalCode").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("UWResult") == null )
				this.UWResult = null;
			else
				this.UWResult = rs.getString("UWResult").trim();

			if( rs.getString("PassFlag") == null )
				this.PassFlag = null;
			else
				this.PassFlag = rs.getString("PassFlag").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的LMUW表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMUWSchema getSchema()
	{
		LMUWSchema aLMUWSchema = new LMUWSchema();
		aLMUWSchema.setSchema(this);
		return aLMUWSchema;
	}

	public LMUWDB getDB()
	{
		LMUWDB aDBOper = new LMUWDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUW描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UWCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaPolType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(UWOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OthCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWResult)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PassFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUW>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RelaPolType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OthCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWResult = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			PassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWSchema";
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
		if (FCode.equalsIgnoreCase("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
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
		if (FCode.equalsIgnoreCase("RelaPolType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPolType));
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWType));
		}
		if (FCode.equalsIgnoreCase("UWOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOrder));
		}
		if (FCode.equalsIgnoreCase("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthCalCode));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("UWResult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWResult));
		}
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PassFlag));
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
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RelaPolType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UWType);
				break;
			case 6:
				strFieldValue = String.valueOf(UWOrder);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OthCalCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWResult);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(PassFlag);
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

		if (FCode.equalsIgnoreCase("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
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
		if (FCode.equalsIgnoreCase("RelaPolType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaPolType = FValue.trim();
			}
			else
				RelaPolType = null;
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWType = FValue.trim();
			}
			else
				UWType = null;
		}
		if (FCode.equalsIgnoreCase("UWOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UWOrder = i;
			}
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
		if (FCode.equalsIgnoreCase("OthCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OthCalCode = FValue.trim();
			}
			else
				OthCalCode = null;
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
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
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
		if (FCode.equalsIgnoreCase("PassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PassFlag = FValue.trim();
			}
			else
				PassFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMUWSchema other = (LMUWSchema)otherObject;
		return
			UWCode.equals(other.getUWCode())
			&& RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& RelaPolType.equals(other.getRelaPolType())
			&& UWType.equals(other.getUWType())
			&& UWOrder == other.getUWOrder()
			&& CalCode.equals(other.getCalCode())
			&& OthCalCode.equals(other.getOthCalCode())
			&& Remark.equals(other.getRemark())
			&& UWGrade.equals(other.getUWGrade())
			&& UWResult.equals(other.getUWResult())
			&& PassFlag.equals(other.getPassFlag());
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
		if( strFieldName.equals("UWCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 2;
		}
		if( strFieldName.equals("RiskName") ) {
			return 3;
		}
		if( strFieldName.equals("RelaPolType") ) {
			return 4;
		}
		if( strFieldName.equals("UWType") ) {
			return 5;
		}
		if( strFieldName.equals("UWOrder") ) {
			return 6;
		}
		if( strFieldName.equals("CalCode") ) {
			return 7;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
			return 9;
		}
		if( strFieldName.equals("UWGrade") ) {
			return 10;
		}
		if( strFieldName.equals("UWResult") ) {
			return 11;
		}
		if( strFieldName.equals("PassFlag") ) {
			return 12;
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
				strFieldName = "UWCode";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "RiskVer";
				break;
			case 3:
				strFieldName = "RiskName";
				break;
			case 4:
				strFieldName = "RelaPolType";
				break;
			case 5:
				strFieldName = "UWType";
				break;
			case 6:
				strFieldName = "UWOrder";
				break;
			case 7:
				strFieldName = "CalCode";
				break;
			case 8:
				strFieldName = "OthCalCode";
				break;
			case 9:
				strFieldName = "Remark";
				break;
			case 10:
				strFieldName = "UWGrade";
				break;
			case 11:
				strFieldName = "UWResult";
				break;
			case 12:
				strFieldName = "PassFlag";
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
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaPolType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OthCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWResult") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PassFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
