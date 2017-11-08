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
import com.sinosoft.lis.db.LMEdorZTDB;

/*
 * <p>ClassName: LMEdorZTSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新险种定义
 */
public class LMEdorZTSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMEdorZTSchema.class);
	// @Field
	/** 险种编码 */
	private String RiskCode;
	/** 险种版本 */
	private String RiskVersion;
	/** 期交是否允许通融退保 */
	private String CycFlag;
	/** 期交退保控制范围类型 */
	private String CycType;
	/** 期交退保控制范围,起点 */
	private int CycStart;
	/** 期交退保控制范围,终点 */
	private int CycEnd;
	/** 趸交是否允许通融退保 */
	private String OnePayFlag;
	/** 趸交退保控制范围类型 */
	private String OnePayType;
	/** 趸交退保控制范围起点 */
	private int OnePayStart;
	/** 趸交退保控制范围终点 */
	private int OnePayEnd;
	/** 补/退费财务类型 */
	private String FeeFinaType;
	/** 加费回退标志 */
	private String AddFeeBackFlag;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMEdorZTSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RiskCode";
		pk[1] = "RiskVersion";

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
		LMEdorZTSchema cloned = (LMEdorZTSchema)super.clone();
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
		if(aRiskCode!=null && aRiskCode.length()>8)
			throw new IllegalArgumentException("险种编码RiskCode值"+aRiskCode+"的长度"+aRiskCode.length()+"大于最大值8");
		RiskCode = aRiskCode;
	}
	public String getRiskVersion()
	{
		return RiskVersion;
	}
	public void setRiskVersion(String aRiskVersion)
	{
		if(aRiskVersion!=null && aRiskVersion.length()>8)
			throw new IllegalArgumentException("险种版本RiskVersion值"+aRiskVersion+"的长度"+aRiskVersion.length()+"大于最大值8");
		RiskVersion = aRiskVersion;
	}
	public String getCycFlag()
	{
		return CycFlag;
	}
	public void setCycFlag(String aCycFlag)
	{
		if(aCycFlag!=null && aCycFlag.length()>1)
			throw new IllegalArgumentException("期交是否允许通融退保CycFlag值"+aCycFlag+"的长度"+aCycFlag.length()+"大于最大值1");
		CycFlag = aCycFlag;
	}
	/**
	* year<p>
	* month<p>
	* day
	*/
	public String getCycType()
	{
		return CycType;
	}
	public void setCycType(String aCycType)
	{
		if(aCycType!=null && aCycType.length()>5)
			throw new IllegalArgumentException("期交退保控制范围类型CycType值"+aCycType+"的长度"+aCycType.length()+"大于最大值5");
		CycType = aCycType;
	}
	public int getCycStart()
	{
		return CycStart;
	}
	public void setCycStart(int aCycStart)
	{
		CycStart = aCycStart;
	}
	public void setCycStart(String aCycStart)
	{
		if (aCycStart != null && !aCycStart.equals(""))
		{
			Integer tInteger = new Integer(aCycStart);
			int i = tInteger.intValue();
			CycStart = i;
		}
	}

	public int getCycEnd()
	{
		return CycEnd;
	}
	public void setCycEnd(int aCycEnd)
	{
		CycEnd = aCycEnd;
	}
	public void setCycEnd(String aCycEnd)
	{
		if (aCycEnd != null && !aCycEnd.equals(""))
		{
			Integer tInteger = new Integer(aCycEnd);
			int i = tInteger.intValue();
			CycEnd = i;
		}
	}

	public String getOnePayFlag()
	{
		return OnePayFlag;
	}
	public void setOnePayFlag(String aOnePayFlag)
	{
		if(aOnePayFlag!=null && aOnePayFlag.length()>1)
			throw new IllegalArgumentException("趸交是否允许通融退保OnePayFlag值"+aOnePayFlag+"的长度"+aOnePayFlag.length()+"大于最大值1");
		OnePayFlag = aOnePayFlag;
	}
	/**
	* Y －－year<p>
	* M －－month<p>
	* D －－day
	*/
	public String getOnePayType()
	{
		return OnePayType;
	}
	public void setOnePayType(String aOnePayType)
	{
		if(aOnePayType!=null && aOnePayType.length()>5)
			throw new IllegalArgumentException("趸交退保控制范围类型OnePayType值"+aOnePayType+"的长度"+aOnePayType.length()+"大于最大值5");
		OnePayType = aOnePayType;
	}
	public int getOnePayStart()
	{
		return OnePayStart;
	}
	public void setOnePayStart(int aOnePayStart)
	{
		OnePayStart = aOnePayStart;
	}
	public void setOnePayStart(String aOnePayStart)
	{
		if (aOnePayStart != null && !aOnePayStart.equals(""))
		{
			Integer tInteger = new Integer(aOnePayStart);
			int i = tInteger.intValue();
			OnePayStart = i;
		}
	}

	public int getOnePayEnd()
	{
		return OnePayEnd;
	}
	public void setOnePayEnd(int aOnePayEnd)
	{
		OnePayEnd = aOnePayEnd;
	}
	public void setOnePayEnd(String aOnePayEnd)
	{
		if (aOnePayEnd != null && !aOnePayEnd.equals(""))
		{
			Integer tInteger = new Integer(aOnePayEnd);
			int i = tInteger.intValue();
			OnePayEnd = i;
		}
	}

	/**
	* 对应以前的tf_je_type<p>
	* 该字段根据不同的退费类型填写。（可能根据不同公司的要求，编写不同的财务类型）<p>
	* 现有的类型有：<p>
	* TB －－ 表示正常的退保。<p>
	* TF －－ 表示正常退保导致的退费。<p>
	* 对于这两种类型，需要根据LMEdorZT的FeeFinaType字段的描述来填写。<p>
	* 意外伤害险和一年期（以及一年期内）健康保险的退保金都按照TF来计算。
	*/
	public String getFeeFinaType()
	{
		return FeeFinaType;
	}
	public void setFeeFinaType(String aFeeFinaType)
	{
		if(aFeeFinaType!=null && aFeeFinaType.length()>6)
			throw new IllegalArgumentException("补/退费财务类型FeeFinaType值"+aFeeFinaType+"的长度"+aFeeFinaType.length()+"大于最大值6");
		FeeFinaType = aFeeFinaType;
	}
	public String getAddFeeBackFlag()
	{
		return AddFeeBackFlag;
	}
	public void setAddFeeBackFlag(String aAddFeeBackFlag)
	{
		if(aAddFeeBackFlag!=null && aAddFeeBackFlag.length()>2)
			throw new IllegalArgumentException("加费回退标志AddFeeBackFlag值"+aAddFeeBackFlag+"的长度"+aAddFeeBackFlag.length()+"大于最大值2");
		AddFeeBackFlag = aAddFeeBackFlag;
	}

	/**
	* 使用另外一个 LMEdorZTSchema 对象给 Schema 赋值
	* @param: aLMEdorZTSchema LMEdorZTSchema
	**/
	public void setSchema(LMEdorZTSchema aLMEdorZTSchema)
	{
		this.RiskCode = aLMEdorZTSchema.getRiskCode();
		this.RiskVersion = aLMEdorZTSchema.getRiskVersion();
		this.CycFlag = aLMEdorZTSchema.getCycFlag();
		this.CycType = aLMEdorZTSchema.getCycType();
		this.CycStart = aLMEdorZTSchema.getCycStart();
		this.CycEnd = aLMEdorZTSchema.getCycEnd();
		this.OnePayFlag = aLMEdorZTSchema.getOnePayFlag();
		this.OnePayType = aLMEdorZTSchema.getOnePayType();
		this.OnePayStart = aLMEdorZTSchema.getOnePayStart();
		this.OnePayEnd = aLMEdorZTSchema.getOnePayEnd();
		this.FeeFinaType = aLMEdorZTSchema.getFeeFinaType();
		this.AddFeeBackFlag = aLMEdorZTSchema.getAddFeeBackFlag();
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

			if( rs.getString("RiskVersion") == null )
				this.RiskVersion = null;
			else
				this.RiskVersion = rs.getString("RiskVersion").trim();

			if( rs.getString("CycFlag") == null )
				this.CycFlag = null;
			else
				this.CycFlag = rs.getString("CycFlag").trim();

			if( rs.getString("CycType") == null )
				this.CycType = null;
			else
				this.CycType = rs.getString("CycType").trim();

			this.CycStart = rs.getInt("CycStart");
			this.CycEnd = rs.getInt("CycEnd");
			if( rs.getString("OnePayFlag") == null )
				this.OnePayFlag = null;
			else
				this.OnePayFlag = rs.getString("OnePayFlag").trim();

			if( rs.getString("OnePayType") == null )
				this.OnePayType = null;
			else
				this.OnePayType = rs.getString("OnePayType").trim();

			this.OnePayStart = rs.getInt("OnePayStart");
			this.OnePayEnd = rs.getInt("OnePayEnd");
			if( rs.getString("FeeFinaType") == null )
				this.FeeFinaType = null;
			else
				this.FeeFinaType = rs.getString("FeeFinaType").trim();

			if( rs.getString("AddFeeBackFlag") == null )
				this.AddFeeBackFlag = null;
			else
				this.AddFeeBackFlag = rs.getString("AddFeeBackFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMEdorZT表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorZTSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMEdorZTSchema getSchema()
	{
		LMEdorZTSchema aLMEdorZTSchema = new LMEdorZTSchema();
		aLMEdorZTSchema.setSchema(this);
		return aLMEdorZTSchema;
	}

	public LMEdorZTDB getDB()
	{
		LMEdorZTDB aDBOper = new LMEdorZTDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorZT描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskVersion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CycType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CycStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CycEnd));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OnePayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnePayStart));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OnePayEnd));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeFinaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AddFeeBackFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMEdorZT>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskVersion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CycFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CycType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CycStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			CycEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			OnePayFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OnePayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OnePayStart= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			OnePayEnd= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			FeeFinaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AddFeeBackFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMEdorZTSchema";
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVersion));
		}
		if (FCode.equalsIgnoreCase("CycFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycFlag));
		}
		if (FCode.equalsIgnoreCase("CycType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycType));
		}
		if (FCode.equalsIgnoreCase("CycStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycStart));
		}
		if (FCode.equalsIgnoreCase("CycEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CycEnd));
		}
		if (FCode.equalsIgnoreCase("OnePayFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayFlag));
		}
		if (FCode.equalsIgnoreCase("OnePayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayType));
		}
		if (FCode.equalsIgnoreCase("OnePayStart"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayStart));
		}
		if (FCode.equalsIgnoreCase("OnePayEnd"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OnePayEnd));
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeFinaType));
		}
		if (FCode.equalsIgnoreCase("AddFeeBackFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddFeeBackFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskVersion);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CycFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CycType);
				break;
			case 4:
				strFieldValue = String.valueOf(CycStart);
				break;
			case 5:
				strFieldValue = String.valueOf(CycEnd);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OnePayFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OnePayType);
				break;
			case 8:
				strFieldValue = String.valueOf(OnePayStart);
				break;
			case 9:
				strFieldValue = String.valueOf(OnePayEnd);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(FeeFinaType);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AddFeeBackFlag);
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
		if (FCode.equalsIgnoreCase("RiskVersion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVersion = FValue.trim();
			}
			else
				RiskVersion = null;
		}
		if (FCode.equalsIgnoreCase("CycFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycFlag = FValue.trim();
			}
			else
				CycFlag = null;
		}
		if (FCode.equalsIgnoreCase("CycType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CycType = FValue.trim();
			}
			else
				CycType = null;
		}
		if (FCode.equalsIgnoreCase("CycStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CycStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("CycEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CycEnd = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnePayFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayFlag = FValue.trim();
			}
			else
				OnePayFlag = null;
		}
		if (FCode.equalsIgnoreCase("OnePayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OnePayType = FValue.trim();
			}
			else
				OnePayType = null;
		}
		if (FCode.equalsIgnoreCase("OnePayStart"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnePayStart = i;
			}
		}
		if (FCode.equalsIgnoreCase("OnePayEnd"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OnePayEnd = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeFinaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeFinaType = FValue.trim();
			}
			else
				FeeFinaType = null;
		}
		if (FCode.equalsIgnoreCase("AddFeeBackFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddFeeBackFlag = FValue.trim();
			}
			else
				AddFeeBackFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMEdorZTSchema other = (LMEdorZTSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& RiskVersion.equals(other.getRiskVersion())
			&& CycFlag.equals(other.getCycFlag())
			&& CycType.equals(other.getCycType())
			&& CycStart == other.getCycStart()
			&& CycEnd == other.getCycEnd()
			&& OnePayFlag.equals(other.getOnePayFlag())
			&& OnePayType.equals(other.getOnePayType())
			&& OnePayStart == other.getOnePayStart()
			&& OnePayEnd == other.getOnePayEnd()
			&& FeeFinaType.equals(other.getFeeFinaType())
			&& AddFeeBackFlag.equals(other.getAddFeeBackFlag());
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
		if( strFieldName.equals("RiskVersion") ) {
			return 1;
		}
		if( strFieldName.equals("CycFlag") ) {
			return 2;
		}
		if( strFieldName.equals("CycType") ) {
			return 3;
		}
		if( strFieldName.equals("CycStart") ) {
			return 4;
		}
		if( strFieldName.equals("CycEnd") ) {
			return 5;
		}
		if( strFieldName.equals("OnePayFlag") ) {
			return 6;
		}
		if( strFieldName.equals("OnePayType") ) {
			return 7;
		}
		if( strFieldName.equals("OnePayStart") ) {
			return 8;
		}
		if( strFieldName.equals("OnePayEnd") ) {
			return 9;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return 10;
		}
		if( strFieldName.equals("AddFeeBackFlag") ) {
			return 11;
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
				strFieldName = "RiskVersion";
				break;
			case 2:
				strFieldName = "CycFlag";
				break;
			case 3:
				strFieldName = "CycType";
				break;
			case 4:
				strFieldName = "CycStart";
				break;
			case 5:
				strFieldName = "CycEnd";
				break;
			case 6:
				strFieldName = "OnePayFlag";
				break;
			case 7:
				strFieldName = "OnePayType";
				break;
			case 8:
				strFieldName = "OnePayStart";
				break;
			case 9:
				strFieldName = "OnePayEnd";
				break;
			case 10:
				strFieldName = "FeeFinaType";
				break;
			case 11:
				strFieldName = "AddFeeBackFlag";
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
		if( strFieldName.equals("RiskVersion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CycStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CycEnd") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnePayFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OnePayStart") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OnePayEnd") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeFinaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddFeeBackFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
