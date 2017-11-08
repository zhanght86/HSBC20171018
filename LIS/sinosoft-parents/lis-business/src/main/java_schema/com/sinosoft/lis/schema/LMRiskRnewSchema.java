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
import com.sinosoft.lis.db.LMRiskRnewDB;

/*
 * <p>ClassName: LMRiskRnewSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMRiskRnewSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMRiskRnewSchema.class);

	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVer;
	/** 险种名称 */
	private String RiskName;
	/** 续保类型 */
	private String RnewType;
	/** 宽限期 */
	private int GracePeriod;
	/** 宽限期单位 */
	private String GracePeriodUnit;
	/** 宽限日期计算方式 */
	private String GraceDateCalMode;
	/** 续保最大年龄 */
	private int MaxAge;
	/** 续保限制 */
	private String RnewLmt;
	/** 原保单终止日算法 */
	private int EndDateCalMode;
	/** 续保确认日算法 */
	private int ComfirmDateCalMode;
	/** 保证续保标记 */
	private String AssuRnewFlag;
	/** 保证续保要求的续保次数 */
	private int RnewTimes;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMRiskRnewSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "RiskVer";

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
		LMRiskRnewSchema cloned = (LMRiskRnewSchema)super.clone();
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
	/**
	* Y--生成新单、N--不生成新单
	*/
	public String getRnewType()
	{
		return RnewType;
	}
	public void setRnewType(String aRnewType)
	{
		RnewType = aRnewType;
	}
	public int getGracePeriod()
	{
		return GracePeriod;
	}
	public void setGracePeriod(int aGracePeriod)
	{
		GracePeriod = aGracePeriod;
	}
	public void setGracePeriod(String aGracePeriod)
	{
		if (aGracePeriod != null && !aGracePeriod.equals(""))
		{
			Integer tInteger = new Integer(aGracePeriod);
			int i = tInteger.intValue();
			GracePeriod = i;
		}
	}

	/**
	* Y--年 M--月  D--日
	*/
	public String getGracePeriodUnit()
	{
		return GracePeriodUnit;
	}
	public void setGracePeriodUnit(String aGracePeriodUnit)
	{
		GracePeriodUnit = aGracePeriodUnit;
	}
	/**
	* 0 - 以计算为准、1 - 下月一号、2 - 下年一号
	*/
	public String getGraceDateCalMode()
	{
		return GraceDateCalMode;
	}
	public void setGraceDateCalMode(String aGraceDateCalMode)
	{
		GraceDateCalMode = aGraceDateCalMode;
	}
	public int getMaxAge()
	{
		return MaxAge;
	}
	public void setMaxAge(int aMaxAge)
	{
		MaxAge = aMaxAge;
	}
	public void setMaxAge(String aMaxAge)
	{
		if (aMaxAge != null && !aMaxAge.equals(""))
		{
			Integer tInteger = new Integer(aMaxAge);
			int i = tInteger.intValue();
			MaxAge = i;
		}
	}

	/**
	* N--理赔后不能续保、Y--理赔后能续保
	*/
	public String getRnewLmt()
	{
		return RnewLmt;
	}
	public void setRnewLmt(String aRnewLmt)
	{
		RnewLmt = aRnewLmt;
	}
	/**
	* 续保生效日与原保单终止日之间的天数:<p>
	* 0--表示当天生效，<p>
	* 1--表示次日生效<p>
	* 999--表示可选择，弹出生效日期录入框，但限制录入的生效日期在首交日期与登录日期之间
	*/
	public int getEndDateCalMode()
	{
		return EndDateCalMode;
	}
	public void setEndDateCalMode(int aEndDateCalMode)
	{
		EndDateCalMode = aEndDateCalMode;
	}
	public void setEndDateCalMode(String aEndDateCalMode)
	{
		if (aEndDateCalMode != null && !aEndDateCalMode.equals(""))
		{
			Integer tInteger = new Integer(aEndDateCalMode);
			int i = tInteger.intValue();
			EndDateCalMode = i;
		}
	}

	/**
	* 续保生效日与续保确定日之间的天数:<p>
	* 0--表示当天生效，<p>
	* 1--表示次日生效<p>
	* 999--表示可选择，弹出生效日期录入框，但限制录入的生效日期在首交日期与登录日期之间
	*/
	public int getComfirmDateCalMode()
	{
		return ComfirmDateCalMode;
	}
	public void setComfirmDateCalMode(int aComfirmDateCalMode)
	{
		ComfirmDateCalMode = aComfirmDateCalMode;
	}
	public void setComfirmDateCalMode(String aComfirmDateCalMode)
	{
		if (aComfirmDateCalMode != null && !aComfirmDateCalMode.equals(""))
		{
			Integer tInteger = new Integer(aComfirmDateCalMode);
			int i = tInteger.intValue();
			ComfirmDateCalMode = i;
		}
	}

	/**
	* Y--能保证续保、N--不能保证续保
	*/
	public String getAssuRnewFlag()
	{
		return AssuRnewFlag;
	}
	public void setAssuRnewFlag(String aAssuRnewFlag)
	{
		AssuRnewFlag = aAssuRnewFlag;
	}
	public int getRnewTimes()
	{
		return RnewTimes;
	}
	public void setRnewTimes(int aRnewTimes)
	{
		RnewTimes = aRnewTimes;
	}
	public void setRnewTimes(String aRnewTimes)
	{
		if (aRnewTimes != null && !aRnewTimes.equals(""))
		{
			Integer tInteger = new Integer(aRnewTimes);
			int i = tInteger.intValue();
			RnewTimes = i;
		}
	}


	/**
	* 使用另外一个 LMRiskRnewSchema 对象给 Schema 赋值
	* @param: aLMRiskRnewSchema LMRiskRnewSchema
	**/
	public void setSchema(LMRiskRnewSchema aLMRiskRnewSchema)
	{
		this.RiskCode = aLMRiskRnewSchema.getRiskCode();
		this.RiskVer = aLMRiskRnewSchema.getRiskVer();
		this.RiskName = aLMRiskRnewSchema.getRiskName();
		this.RnewType = aLMRiskRnewSchema.getRnewType();
		this.GracePeriod = aLMRiskRnewSchema.getGracePeriod();
		this.GracePeriodUnit = aLMRiskRnewSchema.getGracePeriodUnit();
		this.GraceDateCalMode = aLMRiskRnewSchema.getGraceDateCalMode();
		this.MaxAge = aLMRiskRnewSchema.getMaxAge();
		this.RnewLmt = aLMRiskRnewSchema.getRnewLmt();
		this.EndDateCalMode = aLMRiskRnewSchema.getEndDateCalMode();
		this.ComfirmDateCalMode = aLMRiskRnewSchema.getComfirmDateCalMode();
		this.AssuRnewFlag = aLMRiskRnewSchema.getAssuRnewFlag();
		this.RnewTimes = aLMRiskRnewSchema.getRnewTimes();
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

			if( rs.getString("RnewType") == null )
				this.RnewType = null;
			else
				this.RnewType = rs.getString("RnewType").trim();

			this.GracePeriod = rs.getInt("GracePeriod");
			if( rs.getString("GracePeriodUnit") == null )
				this.GracePeriodUnit = null;
			else
				this.GracePeriodUnit = rs.getString("GracePeriodUnit").trim();

			if( rs.getString("GraceDateCalMode") == null )
				this.GraceDateCalMode = null;
			else
				this.GraceDateCalMode = rs.getString("GraceDateCalMode").trim();

			this.MaxAge = rs.getInt("MaxAge");
			if( rs.getString("RnewLmt") == null )
				this.RnewLmt = null;
			else
				this.RnewLmt = rs.getString("RnewLmt").trim();

			this.EndDateCalMode = rs.getInt("EndDateCalMode");
			this.ComfirmDateCalMode = rs.getInt("ComfirmDateCalMode");
			if( rs.getString("AssuRnewFlag") == null )
				this.AssuRnewFlag = null;
			else
				this.AssuRnewFlag = rs.getString("AssuRnewFlag").trim();

			this.RnewTimes = rs.getInt("RnewTimes");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMRiskRnew表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRnewSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMRiskRnewSchema getSchema()
	{
		LMRiskRnewSchema aLMRiskRnewSchema = new LMRiskRnewSchema();
		aLMRiskRnewSchema.setSchema(this);
		return aLMRiskRnewSchema;
	}

	public LMRiskRnewDB getDB()
	{
		LMRiskRnewDB aDBOper = new LMRiskRnewDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRnew描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVer)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RnewType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GracePeriod));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GracePeriodUnit)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GraceDateCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RnewLmt)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(EndDateCalMode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ComfirmDateCalMode));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AssuRnewFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RnewTimes));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskRnew>历史记账凭证主表信息</A>表字段
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
			RnewType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			GracePeriod= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			GracePeriodUnit = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			GraceDateCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MaxAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			RnewLmt = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			EndDateCalMode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			ComfirmDateCalMode= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			AssuRnewFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RnewTimes= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMRiskRnewSchema";
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
		if (FCode.equalsIgnoreCase("RnewType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewType));
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriod));
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GracePeriodUnit));
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GraceDateCalMode));
		}
		if (FCode.equalsIgnoreCase("MaxAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAge));
		}
		if (FCode.equalsIgnoreCase("RnewLmt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewLmt));
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EndDateCalMode));
		}
		if (FCode.equalsIgnoreCase("ComfirmDateCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComfirmDateCalMode));
		}
		if (FCode.equalsIgnoreCase("AssuRnewFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssuRnewFlag));
		}
		if (FCode.equalsIgnoreCase("RnewTimes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RnewTimes));
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
				strFieldValue = StrTool.GBKToUnicode(RnewType);
				break;
			case 4:
				strFieldValue = String.valueOf(GracePeriod);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GracePeriodUnit);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(GraceDateCalMode);
				break;
			case 7:
				strFieldValue = String.valueOf(MaxAge);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RnewLmt);
				break;
			case 9:
				strFieldValue = String.valueOf(EndDateCalMode);
				break;
			case 10:
				strFieldValue = String.valueOf(ComfirmDateCalMode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AssuRnewFlag);
				break;
			case 12:
				strFieldValue = String.valueOf(RnewTimes);
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
		if (FCode.equalsIgnoreCase("RnewType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RnewType = FValue.trim();
			}
			else
				RnewType = null;
		}
		if (FCode.equalsIgnoreCase("GracePeriod"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GracePeriod = i;
			}
		}
		if (FCode.equalsIgnoreCase("GracePeriodUnit"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GracePeriodUnit = FValue.trim();
			}
			else
				GracePeriodUnit = null;
		}
		if (FCode.equalsIgnoreCase("GraceDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GraceDateCalMode = FValue.trim();
			}
			else
				GraceDateCalMode = null;
		}
		if (FCode.equalsIgnoreCase("MaxAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MaxAge = i;
			}
		}
		if (FCode.equalsIgnoreCase("RnewLmt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RnewLmt = FValue.trim();
			}
			else
				RnewLmt = null;
		}
		if (FCode.equalsIgnoreCase("EndDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				EndDateCalMode = i;
			}
		}
		if (FCode.equalsIgnoreCase("ComfirmDateCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ComfirmDateCalMode = i;
			}
		}
		if (FCode.equalsIgnoreCase("AssuRnewFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AssuRnewFlag = FValue.trim();
			}
			else
				AssuRnewFlag = null;
		}
		if (FCode.equalsIgnoreCase("RnewTimes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RnewTimes = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskRnewSchema other = (LMRiskRnewSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& RnewType.equals(other.getRnewType())
			&& GracePeriod == other.getGracePeriod()
			&& GracePeriodUnit.equals(other.getGracePeriodUnit())
			&& GraceDateCalMode.equals(other.getGraceDateCalMode())
			&& MaxAge == other.getMaxAge()
			&& RnewLmt.equals(other.getRnewLmt())
			&& EndDateCalMode == other.getEndDateCalMode()
			&& ComfirmDateCalMode == other.getComfirmDateCalMode()
			&& AssuRnewFlag.equals(other.getAssuRnewFlag())
			&& RnewTimes == other.getRnewTimes();
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
		if( strFieldName.equals("RnewType") ) {
			return 3;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return 4;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return 5;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
			return 6;
		}
		if( strFieldName.equals("MaxAge") ) {
			return 7;
		}
		if( strFieldName.equals("RnewLmt") ) {
			return 8;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return 9;
		}
		if( strFieldName.equals("ComfirmDateCalMode") ) {
			return 10;
		}
		if( strFieldName.equals("AssuRnewFlag") ) {
			return 11;
		}
		if( strFieldName.equals("RnewTimes") ) {
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "RiskVer";
				break;
			case 2:
				strFieldName = "RiskName";
				break;
			case 3:
				strFieldName = "RnewType";
				break;
			case 4:
				strFieldName = "GracePeriod";
				break;
			case 5:
				strFieldName = "GracePeriodUnit";
				break;
			case 6:
				strFieldName = "GraceDateCalMode";
				break;
			case 7:
				strFieldName = "MaxAge";
				break;
			case 8:
				strFieldName = "RnewLmt";
				break;
			case 9:
				strFieldName = "EndDateCalMode";
				break;
			case 10:
				strFieldName = "ComfirmDateCalMode";
				break;
			case 11:
				strFieldName = "AssuRnewFlag";
				break;
			case 12:
				strFieldName = "RnewTimes";
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
		if( strFieldName.equals("RnewType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GracePeriod") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("GracePeriodUnit") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GraceDateCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxAge") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RnewLmt") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EndDateCalMode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ComfirmDateCalMode") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AssuRnewFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RnewTimes") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
