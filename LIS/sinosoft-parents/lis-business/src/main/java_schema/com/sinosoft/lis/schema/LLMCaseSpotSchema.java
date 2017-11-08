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
import com.sinosoft.lis.db.LLMCaseSpotDB;

/*
 * <p>ClassName: LLMCaseSpotSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLMCaseSpotSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLMCaseSpotSchema.class);
	// @Field
	/** 抽检机构 */
	private String SpotMngCom;
	/** 抽检人 */
	private String Spoter;
	/** 被抽检机构 */
	private String SpotedMngCom;
	/** 被抽检人级别 */
	private String SpotedPopedom;
	/** 抽检比较符 */
	private String SpotOper;
	/** 抽检频率 */
	private String SpotFreq;
	/** 抽检比例 */
	private double SpotRate;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLMCaseSpotSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "SpotMngCom";
		pk[1] = "SpotedMngCom";
		pk[2] = "SpotedPopedom";
		pk[3] = "SpotOper";

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
		LLMCaseSpotSchema cloned = (LLMCaseSpotSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSpotMngCom()
	{
		return SpotMngCom;
	}
	public void setSpotMngCom(String aSpotMngCom)
	{
		if(aSpotMngCom!=null && aSpotMngCom.length()>10)
			throw new IllegalArgumentException("抽检机构SpotMngCom值"+aSpotMngCom+"的长度"+aSpotMngCom.length()+"大于最大值10");
		SpotMngCom = aSpotMngCom;
	}
	public String getSpoter()
	{
		return Spoter;
	}
	public void setSpoter(String aSpoter)
	{
		if(aSpoter!=null && aSpoter.length()>10)
			throw new IllegalArgumentException("抽检人Spoter值"+aSpoter+"的长度"+aSpoter.length()+"大于最大值10");
		Spoter = aSpoter;
	}
	public String getSpotedMngCom()
	{
		return SpotedMngCom;
	}
	public void setSpotedMngCom(String aSpotedMngCom)
	{
		if(aSpotedMngCom!=null && aSpotedMngCom.length()>10)
			throw new IllegalArgumentException("被抽检机构SpotedMngCom值"+aSpotedMngCom+"的长度"+aSpotedMngCom.length()+"大于最大值10");
		SpotedMngCom = aSpotedMngCom;
	}
	public String getSpotedPopedom()
	{
		return SpotedPopedom;
	}
	public void setSpotedPopedom(String aSpotedPopedom)
	{
		if(aSpotedPopedom!=null && aSpotedPopedom.length()>40)
			throw new IllegalArgumentException("被抽检人级别SpotedPopedom值"+aSpotedPopedom+"的长度"+aSpotedPopedom.length()+"大于最大值40");
		SpotedPopedom = aSpotedPopedom;
	}
	/**
	* 0 = 1 <= 2 包含
	*/
	public String getSpotOper()
	{
		return SpotOper;
	}
	public void setSpotOper(String aSpotOper)
	{
		if(aSpotOper!=null && aSpotOper.length()>1)
			throw new IllegalArgumentException("抽检比较符SpotOper值"+aSpotOper+"的长度"+aSpotOper.length()+"大于最大值1");
		SpotOper = aSpotOper;
	}
	/**
	* 0 临时指定 1 每日
	*/
	public String getSpotFreq()
	{
		return SpotFreq;
	}
	public void setSpotFreq(String aSpotFreq)
	{
		if(aSpotFreq!=null && aSpotFreq.length()>1)
			throw new IllegalArgumentException("抽检频率SpotFreq值"+aSpotFreq+"的长度"+aSpotFreq.length()+"大于最大值1");
		SpotFreq = aSpotFreq;
	}
	public double getSpotRate()
	{
		return SpotRate;
	}
	public void setSpotRate(double aSpotRate)
	{
		SpotRate = aSpotRate;
	}
	public void setSpotRate(String aSpotRate)
	{
		if (aSpotRate != null && !aSpotRate.equals(""))
		{
			Double tDouble = new Double(aSpotRate);
			double d = tDouble.doubleValue();
			SpotRate = d;
		}
	}


	/**
	* 使用另外一个 LLMCaseSpotSchema 对象给 Schema 赋值
	* @param: aLLMCaseSpotSchema LLMCaseSpotSchema
	**/
	public void setSchema(LLMCaseSpotSchema aLLMCaseSpotSchema)
	{
		this.SpotMngCom = aLLMCaseSpotSchema.getSpotMngCom();
		this.Spoter = aLLMCaseSpotSchema.getSpoter();
		this.SpotedMngCom = aLLMCaseSpotSchema.getSpotedMngCom();
		this.SpotedPopedom = aLLMCaseSpotSchema.getSpotedPopedom();
		this.SpotOper = aLLMCaseSpotSchema.getSpotOper();
		this.SpotFreq = aLLMCaseSpotSchema.getSpotFreq();
		this.SpotRate = aLLMCaseSpotSchema.getSpotRate();
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
			if( rs.getString("SpotMngCom") == null )
				this.SpotMngCom = null;
			else
				this.SpotMngCom = rs.getString("SpotMngCom").trim();

			if( rs.getString("Spoter") == null )
				this.Spoter = null;
			else
				this.Spoter = rs.getString("Spoter").trim();

			if( rs.getString("SpotedMngCom") == null )
				this.SpotedMngCom = null;
			else
				this.SpotedMngCom = rs.getString("SpotedMngCom").trim();

			if( rs.getString("SpotedPopedom") == null )
				this.SpotedPopedom = null;
			else
				this.SpotedPopedom = rs.getString("SpotedPopedom").trim();

			if( rs.getString("SpotOper") == null )
				this.SpotOper = null;
			else
				this.SpotOper = rs.getString("SpotOper").trim();

			if( rs.getString("SpotFreq") == null )
				this.SpotFreq = null;
			else
				this.SpotFreq = rs.getString("SpotFreq").trim();

			this.SpotRate = rs.getDouble("SpotRate");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLMCaseSpot表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMCaseSpotSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLMCaseSpotSchema getSchema()
	{
		LLMCaseSpotSchema aLLMCaseSpotSchema = new LLMCaseSpotSchema();
		aLLMCaseSpotSchema.setSchema(this);
		return aLLMCaseSpotSchema;
	}

	public LLMCaseSpotDB getDB()
	{
		LLMCaseSpotDB aDBOper = new LLMCaseSpotDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMCaseSpot描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SpotMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Spoter)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotedMngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotedPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotOper)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SpotFreq)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SpotRate));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLMCaseSpot>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SpotMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Spoter = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SpotedMngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			SpotedPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SpotOper = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SpotFreq = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SpotRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLMCaseSpotSchema";
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
		if (FCode.equalsIgnoreCase("SpotMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotMngCom));
		}
		if (FCode.equalsIgnoreCase("Spoter"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Spoter));
		}
		if (FCode.equalsIgnoreCase("SpotedMngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotedMngCom));
		}
		if (FCode.equalsIgnoreCase("SpotedPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotedPopedom));
		}
		if (FCode.equalsIgnoreCase("SpotOper"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotOper));
		}
		if (FCode.equalsIgnoreCase("SpotFreq"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotFreq));
		}
		if (FCode.equalsIgnoreCase("SpotRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SpotRate));
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
				strFieldValue = StrTool.GBKToUnicode(SpotMngCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(Spoter);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SpotedMngCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(SpotedPopedom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SpotOper);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SpotFreq);
				break;
			case 6:
				strFieldValue = String.valueOf(SpotRate);
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

		if (FCode.equalsIgnoreCase("SpotMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotMngCom = FValue.trim();
			}
			else
				SpotMngCom = null;
		}
		if (FCode.equalsIgnoreCase("Spoter"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Spoter = FValue.trim();
			}
			else
				Spoter = null;
		}
		if (FCode.equalsIgnoreCase("SpotedMngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotedMngCom = FValue.trim();
			}
			else
				SpotedMngCom = null;
		}
		if (FCode.equalsIgnoreCase("SpotedPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotedPopedom = FValue.trim();
			}
			else
				SpotedPopedom = null;
		}
		if (FCode.equalsIgnoreCase("SpotOper"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotOper = FValue.trim();
			}
			else
				SpotOper = null;
		}
		if (FCode.equalsIgnoreCase("SpotFreq"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SpotFreq = FValue.trim();
			}
			else
				SpotFreq = null;
		}
		if (FCode.equalsIgnoreCase("SpotRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SpotRate = d;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLMCaseSpotSchema other = (LLMCaseSpotSchema)otherObject;
		return
			SpotMngCom.equals(other.getSpotMngCom())
			&& Spoter.equals(other.getSpoter())
			&& SpotedMngCom.equals(other.getSpotedMngCom())
			&& SpotedPopedom.equals(other.getSpotedPopedom())
			&& SpotOper.equals(other.getSpotOper())
			&& SpotFreq.equals(other.getSpotFreq())
			&& SpotRate == other.getSpotRate();
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
		if( strFieldName.equals("SpotMngCom") ) {
			return 0;
		}
		if( strFieldName.equals("Spoter") ) {
			return 1;
		}
		if( strFieldName.equals("SpotedMngCom") ) {
			return 2;
		}
		if( strFieldName.equals("SpotedPopedom") ) {
			return 3;
		}
		if( strFieldName.equals("SpotOper") ) {
			return 4;
		}
		if( strFieldName.equals("SpotFreq") ) {
			return 5;
		}
		if( strFieldName.equals("SpotRate") ) {
			return 6;
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
				strFieldName = "SpotMngCom";
				break;
			case 1:
				strFieldName = "Spoter";
				break;
			case 2:
				strFieldName = "SpotedMngCom";
				break;
			case 3:
				strFieldName = "SpotedPopedom";
				break;
			case 4:
				strFieldName = "SpotOper";
				break;
			case 5:
				strFieldName = "SpotFreq";
				break;
			case 6:
				strFieldName = "SpotRate";
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
		if( strFieldName.equals("SpotMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Spoter") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotedMngCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotedPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotOper") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotFreq") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SpotRate") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
