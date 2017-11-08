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
import com.sinosoft.lis.db.LLCaseDrugDB;

/*
 * <p>ClassName: LLCaseDrugSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LLCaseDrugSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCaseDrugSchema.class);
	// @Field
	/** 序号 */
	private String CDSerialNo;
	/** 分案号 */
	private String CaseNo;
	/** 账单序号 */
	private String MainFeeNo;
	/** 药品分类（中西药） */
	private String DrugClass;
	/** 药品代码 */
	private String DrugCode;
	/** 药品名称 */
	private String DrugName;
	/** 金额 */
	private double FeeMoney;
	/** 处理标记 */
	private String AvliFlag;
	/** 处理原因代码 */
	private String AvliFlagCode;
	/** 处理原因 */
	private String AvliReason;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCaseDrugSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "CDSerialNo";

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
		LLCaseDrugSchema cloned = (LLCaseDrugSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCDSerialNo()
	{
		return CDSerialNo;
	}
	public void setCDSerialNo(String aCDSerialNo)
	{
		if(aCDSerialNo!=null && aCDSerialNo.length()>20)
			throw new IllegalArgumentException("序号CDSerialNo值"+aCDSerialNo+"的长度"+aCDSerialNo.length()+"大于最大值20");
		CDSerialNo = aCDSerialNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	public String getMainFeeNo()
	{
		return MainFeeNo;
	}
	public void setMainFeeNo(String aMainFeeNo)
	{
		if(aMainFeeNo!=null && aMainFeeNo.length()>20)
			throw new IllegalArgumentException("账单序号MainFeeNo值"+aMainFeeNo+"的长度"+aMainFeeNo.length()+"大于最大值20");
		MainFeeNo = aMainFeeNo;
	}
	/**
	* 西药—M，中成药—C
	*/
	public String getDrugClass()
	{
		return DrugClass;
	}
	public void setDrugClass(String aDrugClass)
	{
		if(aDrugClass!=null && aDrugClass.length()>2)
			throw new IllegalArgumentException("药品分类（中西药）DrugClass值"+aDrugClass+"的长度"+aDrugClass.length()+"大于最大值2");
		DrugClass = aDrugClass;
	}
	public String getDrugCode()
	{
		return DrugCode;
	}
	public void setDrugCode(String aDrugCode)
	{
		if(aDrugCode!=null && aDrugCode.length()>20)
			throw new IllegalArgumentException("药品代码DrugCode值"+aDrugCode+"的长度"+aDrugCode.length()+"大于最大值20");
		DrugCode = aDrugCode;
	}
	public String getDrugName()
	{
		return DrugName;
	}
	public void setDrugName(String aDrugName)
	{
		if(aDrugName!=null && aDrugName.length()>100)
			throw new IllegalArgumentException("药品名称DrugName值"+aDrugName+"的长度"+aDrugName.length()+"大于最大值100");
		DrugName = aDrugName;
	}
	public double getFeeMoney()
	{
		return FeeMoney;
	}
	public void setFeeMoney(double aFeeMoney)
	{
		FeeMoney = aFeeMoney;
	}
	public void setFeeMoney(String aFeeMoney)
	{
		if (aFeeMoney != null && !aFeeMoney.equals(""))
		{
			Double tDouble = new Double(aFeeMoney);
			double d = tDouble.doubleValue();
			FeeMoney = d;
		}
	}

	/**
	* 0 赔付 1 社保类 2 自费类 3 不合理类
	*/
	public String getAvliFlag()
	{
		return AvliFlag;
	}
	public void setAvliFlag(String aAvliFlag)
	{
		if(aAvliFlag!=null && aAvliFlag.length()>1)
			throw new IllegalArgumentException("处理标记AvliFlag值"+aAvliFlag+"的长度"+aAvliFlag.length()+"大于最大值1");
		AvliFlag = aAvliFlag;
	}
	public String getAvliFlagCode()
	{
		return AvliFlagCode;
	}
	public void setAvliFlagCode(String aAvliFlagCode)
	{
		if(aAvliFlagCode!=null && aAvliFlagCode.length()>6)
			throw new IllegalArgumentException("处理原因代码AvliFlagCode值"+aAvliFlagCode+"的长度"+aAvliFlagCode.length()+"大于最大值6");
		AvliFlagCode = aAvliFlagCode;
	}
	public String getAvliReason()
	{
		return AvliReason;
	}
	public void setAvliReason(String aAvliReason)
	{
		if(aAvliReason!=null && aAvliReason.length()>100)
			throw new IllegalArgumentException("处理原因AvliReason值"+aAvliReason+"的长度"+aAvliReason.length()+"大于最大值100");
		AvliReason = aAvliReason;
	}

	/**
	* 使用另外一个 LLCaseDrugSchema 对象给 Schema 赋值
	* @param: aLLCaseDrugSchema LLCaseDrugSchema
	**/
	public void setSchema(LLCaseDrugSchema aLLCaseDrugSchema)
	{
		this.CDSerialNo = aLLCaseDrugSchema.getCDSerialNo();
		this.CaseNo = aLLCaseDrugSchema.getCaseNo();
		this.MainFeeNo = aLLCaseDrugSchema.getMainFeeNo();
		this.DrugClass = aLLCaseDrugSchema.getDrugClass();
		this.DrugCode = aLLCaseDrugSchema.getDrugCode();
		this.DrugName = aLLCaseDrugSchema.getDrugName();
		this.FeeMoney = aLLCaseDrugSchema.getFeeMoney();
		this.AvliFlag = aLLCaseDrugSchema.getAvliFlag();
		this.AvliFlagCode = aLLCaseDrugSchema.getAvliFlagCode();
		this.AvliReason = aLLCaseDrugSchema.getAvliReason();
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
			if( rs.getString("CDSerialNo") == null )
				this.CDSerialNo = null;
			else
				this.CDSerialNo = rs.getString("CDSerialNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("MainFeeNo") == null )
				this.MainFeeNo = null;
			else
				this.MainFeeNo = rs.getString("MainFeeNo").trim();

			if( rs.getString("DrugClass") == null )
				this.DrugClass = null;
			else
				this.DrugClass = rs.getString("DrugClass").trim();

			if( rs.getString("DrugCode") == null )
				this.DrugCode = null;
			else
				this.DrugCode = rs.getString("DrugCode").trim();

			if( rs.getString("DrugName") == null )
				this.DrugName = null;
			else
				this.DrugName = rs.getString("DrugName").trim();

			this.FeeMoney = rs.getDouble("FeeMoney");
			if( rs.getString("AvliFlag") == null )
				this.AvliFlag = null;
			else
				this.AvliFlag = rs.getString("AvliFlag").trim();

			if( rs.getString("AvliFlagCode") == null )
				this.AvliFlagCode = null;
			else
				this.AvliFlagCode = rs.getString("AvliFlagCode").trim();

			if( rs.getString("AvliReason") == null )
				this.AvliReason = null;
			else
				this.AvliReason = rs.getString("AvliReason").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLCaseDrug表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseDrugSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCaseDrugSchema getSchema()
	{
		LLCaseDrugSchema aLLCaseDrugSchema = new LLCaseDrugSchema();
		aLLCaseDrugSchema.setSchema(this);
		return aLLCaseDrugSchema;
	}

	public LLCaseDrugDB getDB()
	{
		LLCaseDrugDB aDBOper = new LLCaseDrugDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseDrug描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(CDSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainFeeNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrugClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrugCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DrugName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FeeMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvliFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvliFlagCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AvliReason));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCaseDrug>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CDSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			MainFeeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			DrugClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DrugCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DrugName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FeeMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			AvliFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AvliFlagCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AvliReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCaseDrugSchema";
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
		if (FCode.equalsIgnoreCase("CDSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CDSerialNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainFeeNo));
		}
		if (FCode.equalsIgnoreCase("DrugClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrugClass));
		}
		if (FCode.equalsIgnoreCase("DrugCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrugCode));
		}
		if (FCode.equalsIgnoreCase("DrugName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DrugName));
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeMoney));
		}
		if (FCode.equalsIgnoreCase("AvliFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvliFlag));
		}
		if (FCode.equalsIgnoreCase("AvliFlagCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvliFlagCode));
		}
		if (FCode.equalsIgnoreCase("AvliReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AvliReason));
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
				strFieldValue = StrTool.GBKToUnicode(CDSerialNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(MainFeeNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(DrugClass);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DrugCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DrugName);
				break;
			case 6:
				strFieldValue = String.valueOf(FeeMoney);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AvliFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AvliFlagCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AvliReason);
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

		if (FCode.equalsIgnoreCase("CDSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CDSerialNo = FValue.trim();
			}
			else
				CDSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
		}
		if (FCode.equalsIgnoreCase("MainFeeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainFeeNo = FValue.trim();
			}
			else
				MainFeeNo = null;
		}
		if (FCode.equalsIgnoreCase("DrugClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrugClass = FValue.trim();
			}
			else
				DrugClass = null;
		}
		if (FCode.equalsIgnoreCase("DrugCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrugCode = FValue.trim();
			}
			else
				DrugCode = null;
		}
		if (FCode.equalsIgnoreCase("DrugName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DrugName = FValue.trim();
			}
			else
				DrugName = null;
		}
		if (FCode.equalsIgnoreCase("FeeMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				FeeMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("AvliFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvliFlag = FValue.trim();
			}
			else
				AvliFlag = null;
		}
		if (FCode.equalsIgnoreCase("AvliFlagCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvliFlagCode = FValue.trim();
			}
			else
				AvliFlagCode = null;
		}
		if (FCode.equalsIgnoreCase("AvliReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AvliReason = FValue.trim();
			}
			else
				AvliReason = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLCaseDrugSchema other = (LLCaseDrugSchema)otherObject;
		return
			CDSerialNo.equals(other.getCDSerialNo())
			&& CaseNo.equals(other.getCaseNo())
			&& MainFeeNo.equals(other.getMainFeeNo())
			&& DrugClass.equals(other.getDrugClass())
			&& DrugCode.equals(other.getDrugCode())
			&& DrugName.equals(other.getDrugName())
			&& FeeMoney == other.getFeeMoney()
			&& AvliFlag.equals(other.getAvliFlag())
			&& AvliFlagCode.equals(other.getAvliFlagCode())
			&& AvliReason.equals(other.getAvliReason());
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
		if( strFieldName.equals("CDSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("MainFeeNo") ) {
			return 2;
		}
		if( strFieldName.equals("DrugClass") ) {
			return 3;
		}
		if( strFieldName.equals("DrugCode") ) {
			return 4;
		}
		if( strFieldName.equals("DrugName") ) {
			return 5;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return 6;
		}
		if( strFieldName.equals("AvliFlag") ) {
			return 7;
		}
		if( strFieldName.equals("AvliFlagCode") ) {
			return 8;
		}
		if( strFieldName.equals("AvliReason") ) {
			return 9;
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
				strFieldName = "CDSerialNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "MainFeeNo";
				break;
			case 3:
				strFieldName = "DrugClass";
				break;
			case 4:
				strFieldName = "DrugCode";
				break;
			case 5:
				strFieldName = "DrugName";
				break;
			case 6:
				strFieldName = "FeeMoney";
				break;
			case 7:
				strFieldName = "AvliFlag";
				break;
			case 8:
				strFieldName = "AvliFlagCode";
				break;
			case 9:
				strFieldName = "AvliReason";
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
		if( strFieldName.equals("CDSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainFeeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrugClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrugCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DrugName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AvliFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvliFlagCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AvliReason") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
