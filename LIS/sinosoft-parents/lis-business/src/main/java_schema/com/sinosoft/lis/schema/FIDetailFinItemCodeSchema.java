/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sinosoft.lis.db.FIDetailFinItemCodeDB;
import com.sinosoft.utility.*;

/**
 * <p>ClassName: FIDetailFinItemCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 财务接口最新版
 * @CreateDate：2008-12-09
 */
public class FIDetailFinItemCodeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIDetailFinItemCodeSchema.class);

	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 科目编号 */
	private String FinItemID;
	/** 判断条件续号 */
	private String JudgementNo;
	/** 层级条件组合数值 */
	private String LevelConditionValue;
	/** 层级科目代码 */
	private String LevelCode;
	/** 下级科目判断条件 */
	private String NextJudgementNo;
	/** 条件组合数值描述 */
	private String ReMark;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIDetailFinItemCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "VersionNo";
		pk[1] = "FinItemID";
		pk[2] = "JudgementNo";
		pk[3] = "LevelConditionValue";

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
                FIDetailFinItemCodeSchema cloned = (FIDetailFinItemCodeSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getVersionNo()
	{
		return VersionNo;
	}
	public void setVersionNo(String aVersionNo)
	{
		VersionNo = aVersionNo;
	}
	public String getFinItemID()
	{
		return FinItemID;
	}
	public void setFinItemID(String aFinItemID)
	{
		FinItemID = aFinItemID;
	}
	public String getJudgementNo()
	{
		return JudgementNo;
	}
	public void setJudgementNo(String aJudgementNo)
	{
		JudgementNo = aJudgementNo;
	}
	public String getLevelConditionValue()
	{
		return LevelConditionValue;
	}
	public void setLevelConditionValue(String aLevelConditionValue)
	{
		LevelConditionValue = aLevelConditionValue;
	}
	public String getLevelCode()
	{
		return LevelCode;
	}
	public void setLevelCode(String aLevelCode)
	{
		LevelCode = aLevelCode;
	}
	public String getNextJudgementNo()
	{
		return NextJudgementNo;
	}
	public void setNextJudgementNo(String aNextJudgementNo)
	{
		NextJudgementNo = aNextJudgementNo;
	}
	public String getReMark()
	{
		return ReMark;
	}
	public void setReMark(String aReMark)
	{
		ReMark = aReMark;
	}

	/**
	* 使用另外一个 FIDetailFinItemCodeSchema 对象给 Schema 赋值
	* @param: aFIDetailFinItemCodeSchema FIDetailFinItemCodeSchema
	**/
	public void setSchema(FIDetailFinItemCodeSchema aFIDetailFinItemCodeSchema)
	{
		this.VersionNo = aFIDetailFinItemCodeSchema.getVersionNo();
		this.FinItemID = aFIDetailFinItemCodeSchema.getFinItemID();
		this.JudgementNo = aFIDetailFinItemCodeSchema.getJudgementNo();
		this.LevelConditionValue = aFIDetailFinItemCodeSchema.getLevelConditionValue();
		this.LevelCode = aFIDetailFinItemCodeSchema.getLevelCode();
		this.NextJudgementNo = aFIDetailFinItemCodeSchema.getNextJudgementNo();
		this.ReMark = aFIDetailFinItemCodeSchema.getReMark();
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
			if( rs.getString("VersionNo") == null )
				this.VersionNo = null;
			else
				this.VersionNo = rs.getString("VersionNo").trim();

			if( rs.getString("FinItemID") == null )
				this.FinItemID = null;
			else
				this.FinItemID = rs.getString("FinItemID").trim();

			if( rs.getString("JudgementNo") == null )
				this.JudgementNo = null;
			else
				this.JudgementNo = rs.getString("JudgementNo").trim();

			if( rs.getString("LevelConditionValue") == null )
				this.LevelConditionValue = null;
			else
				this.LevelConditionValue = rs.getString("LevelConditionValue").trim();

			if( rs.getString("LevelCode") == null )
				this.LevelCode = null;
			else
				this.LevelCode = rs.getString("LevelCode").trim();

			if( rs.getString("NextJudgementNo") == null )
				this.NextJudgementNo = null;
			else
				this.NextJudgementNo = rs.getString("NextJudgementNo").trim();

			if( rs.getString("ReMark") == null )
				this.ReMark = null;
			else
				this.ReMark = rs.getString("ReMark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIDetailFinItemCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIDetailFinItemCodeSchema getSchema()
	{
		FIDetailFinItemCodeSchema aFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();
		aFIDetailFinItemCodeSchema.setSchema(this);
		return aFIDetailFinItemCodeSchema;
	}

	public FIDetailFinItemCodeDB getDB()
	{
		FIDetailFinItemCodeDB aDBOper = new FIDetailFinItemCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDetailFinItemCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(FinItemID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(JudgementNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LevelConditionValue)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(LevelCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NextJudgementNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ReMark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIDetailFinItemCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			FinItemID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			JudgementNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LevelConditionValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LevelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NextJudgementNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ReMark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeSchema";
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
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionNo));
		}
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FinItemID));
		}
		if (FCode.equalsIgnoreCase("JudgementNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(JudgementNo));
		}
		if (FCode.equalsIgnoreCase("LevelConditionValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LevelConditionValue));
		}
		if (FCode.equalsIgnoreCase("LevelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LevelCode));
		}
		if (FCode.equalsIgnoreCase("NextJudgementNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NextJudgementNo));
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReMark));
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
				strFieldValue = StrTool.GBKToUnicode(VersionNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(FinItemID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(JudgementNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LevelConditionValue);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LevelCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NextJudgementNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ReMark);
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

		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionNo = FValue.trim();
			}
			else
				VersionNo = null;
		}
		if (FCode.equalsIgnoreCase("FinItemID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FinItemID = FValue.trim();
			}
			else
				FinItemID = null;
		}
		if (FCode.equalsIgnoreCase("JudgementNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				JudgementNo = FValue.trim();
			}
			else
				JudgementNo = null;
		}
		if (FCode.equalsIgnoreCase("LevelConditionValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LevelConditionValue = FValue.trim();
			}
			else
				LevelConditionValue = null;
		}
		if (FCode.equalsIgnoreCase("LevelCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LevelCode = FValue.trim();
			}
			else
				LevelCode = null;
		}
		if (FCode.equalsIgnoreCase("NextJudgementNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NextJudgementNo = FValue.trim();
			}
			else
				NextJudgementNo = null;
		}
		if (FCode.equalsIgnoreCase("ReMark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReMark = FValue.trim();
			}
			else
				ReMark = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIDetailFinItemCodeSchema other = (FIDetailFinItemCodeSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& FinItemID.equals(other.getFinItemID())
			&& JudgementNo.equals(other.getJudgementNo())
			&& LevelConditionValue.equals(other.getLevelConditionValue())
			&& LevelCode.equals(other.getLevelCode())
			&& NextJudgementNo.equals(other.getNextJudgementNo())
			&& ReMark.equals(other.getReMark());
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
		if( strFieldName.equals("VersionNo") ) {
			return 0;
		}
		if( strFieldName.equals("FinItemID") ) {
			return 1;
		}
		if( strFieldName.equals("JudgementNo") ) {
			return 2;
		}
		if( strFieldName.equals("LevelConditionValue") ) {
			return 3;
		}
		if( strFieldName.equals("LevelCode") ) {
			return 4;
		}
		if( strFieldName.equals("NextJudgementNo") ) {
			return 5;
		}
		if( strFieldName.equals("ReMark") ) {
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "FinItemID";
				break;
			case 2:
				strFieldName = "JudgementNo";
				break;
			case 3:
				strFieldName = "LevelConditionValue";
				break;
			case 4:
				strFieldName = "LevelCode";
				break;
			case 5:
				strFieldName = "NextJudgementNo";
				break;
			case 6:
				strFieldName = "ReMark";
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
		if( strFieldName.equals("VersionNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FinItemID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("JudgementNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LevelConditionValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LevelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NextJudgementNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReMark") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
