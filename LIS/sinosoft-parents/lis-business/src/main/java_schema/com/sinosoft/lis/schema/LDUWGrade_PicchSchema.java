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
import com.sinosoft.lis.db.LDUWGrade_PicchDB;

/*
 * <p>ClassName: LDUWGrade_PicchSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWGrade_PicchSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWGrade_PicchSchema.class);

	// @Field
	/** 核保师级别 */
	private String UWGrade;
	/** 核保师级别名称 */
	private String UWGradeName;
	/** 业务类型 */
	private String ContFlag;
	/** 保险金额 */
	private double Amnt;
	/** 加费比例 */
	private double AddPrem;
	/** 拒保金额 */
	private double Refuse;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;

	public static final int FIELDNUM = 8;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWGrade_PicchSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "UWGrade";
		pk[1] = "ContFlag";

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
		LDUWGrade_PicchSchema cloned = (LDUWGrade_PicchSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUWGrade()
	{
		return UWGrade;
	}
	public void setUWGrade(String aUWGrade)
	{
		UWGrade = aUWGrade;
	}
	public String getUWGradeName()
	{
		return UWGradeName;
	}
	public void setUWGradeName(String aUWGradeName)
	{
		UWGradeName = aUWGradeName;
	}
	public String getContFlag()
	{
		return ContFlag;
	}
	public void setContFlag(String aContFlag)
	{
		ContFlag = aContFlag;
	}
	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public double getAddPrem()
	{
		return AddPrem;
	}
	public void setAddPrem(double aAddPrem)
	{
		AddPrem = aAddPrem;
	}
	public void setAddPrem(String aAddPrem)
	{
		if (aAddPrem != null && !aAddPrem.equals(""))
		{
			Double tDouble = new Double(aAddPrem);
			double d = tDouble.doubleValue();
			AddPrem = d;
		}
	}

	public double getRefuse()
	{
		return Refuse;
	}
	public void setRefuse(double aRefuse)
	{
		Refuse = aRefuse;
	}
	public void setRefuse(String aRefuse)
	{
		if (aRefuse != null && !aRefuse.equals(""))
		{
			Double tDouble = new Double(aRefuse);
			double d = tDouble.doubleValue();
			Refuse = d;
		}
	}

	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
	}

	/**
	* 使用另外一个 LDUWGrade_PicchSchema 对象给 Schema 赋值
	* @param: aLDUWGrade_PicchSchema LDUWGrade_PicchSchema
	**/
	public void setSchema(LDUWGrade_PicchSchema aLDUWGrade_PicchSchema)
	{
		this.UWGrade = aLDUWGrade_PicchSchema.getUWGrade();
		this.UWGradeName = aLDUWGrade_PicchSchema.getUWGradeName();
		this.ContFlag = aLDUWGrade_PicchSchema.getContFlag();
		this.Amnt = aLDUWGrade_PicchSchema.getAmnt();
		this.AddPrem = aLDUWGrade_PicchSchema.getAddPrem();
		this.Refuse = aLDUWGrade_PicchSchema.getRefuse();
		this.StandbyFlag1 = aLDUWGrade_PicchSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLDUWGrade_PicchSchema.getStandbyFlag2();
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
			if( rs.getString("UWGrade") == null )
				this.UWGrade = null;
			else
				this.UWGrade = rs.getString("UWGrade").trim();

			if( rs.getString("UWGradeName") == null )
				this.UWGradeName = null;
			else
				this.UWGradeName = rs.getString("UWGradeName").trim();

			if( rs.getString("ContFlag") == null )
				this.ContFlag = null;
			else
				this.ContFlag = rs.getString("ContFlag").trim();

			this.Amnt = rs.getDouble("Amnt");
			this.AddPrem = rs.getDouble("AddPrem");
			this.Refuse = rs.getDouble("Refuse");
			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWGrade_Picch表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGrade_PicchSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWGrade_PicchSchema getSchema()
	{
		LDUWGrade_PicchSchema aLDUWGrade_PicchSchema = new LDUWGrade_PicchSchema();
		aLDUWGrade_PicchSchema.setSchema(this);
		return aLDUWGrade_PicchSchema;
	}

	public LDUWGrade_PicchDB getDB()
	{
		LDUWGrade_PicchDB aDBOper = new LDUWGrade_PicchDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGrade_Picch描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UWGrade)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWGradeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Refuse));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGrade_Picch>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UWGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UWGradeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			AddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			Refuse = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGrade_PicchSchema";
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
		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGrade));
		}
		if (FCode.equalsIgnoreCase("UWGradeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWGradeName));
		}
		if (FCode.equalsIgnoreCase("ContFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContFlag));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("AddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPrem));
		}
		if (FCode.equalsIgnoreCase("Refuse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Refuse));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(UWGrade);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UWGradeName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContFlag);
				break;
			case 3:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 4:
				strFieldValue = String.valueOf(AddPrem);
				break;
			case 5:
				strFieldValue = String.valueOf(Refuse);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
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

		if (FCode.equalsIgnoreCase("UWGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGrade = FValue.trim();
			}
			else
				UWGrade = null;
		}
		if (FCode.equalsIgnoreCase("UWGradeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWGradeName = FValue.trim();
			}
			else
				UWGradeName = null;
		}
		if (FCode.equalsIgnoreCase("ContFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContFlag = FValue.trim();
			}
			else
				ContFlag = null;
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AddPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Refuse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Refuse = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWGrade_PicchSchema other = (LDUWGrade_PicchSchema)otherObject;
		return
			UWGrade.equals(other.getUWGrade())
			&& UWGradeName.equals(other.getUWGradeName())
			&& ContFlag.equals(other.getContFlag())
			&& Amnt == other.getAmnt()
			&& AddPrem == other.getAddPrem()
			&& Refuse == other.getRefuse()
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2());
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
		if( strFieldName.equals("UWGrade") ) {
			return 0;
		}
		if( strFieldName.equals("UWGradeName") ) {
			return 1;
		}
		if( strFieldName.equals("ContFlag") ) {
			return 2;
		}
		if( strFieldName.equals("Amnt") ) {
			return 3;
		}
		if( strFieldName.equals("AddPrem") ) {
			return 4;
		}
		if( strFieldName.equals("Refuse") ) {
			return 5;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 6;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 7;
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
				strFieldName = "UWGrade";
				break;
			case 1:
				strFieldName = "UWGradeName";
				break;
			case 2:
				strFieldName = "ContFlag";
				break;
			case 3:
				strFieldName = "Amnt";
				break;
			case 4:
				strFieldName = "AddPrem";
				break;
			case 5:
				strFieldName = "Refuse";
				break;
			case 6:
				strFieldName = "StandbyFlag1";
				break;
			case 7:
				strFieldName = "StandbyFlag2";
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
		if( strFieldName.equals("UWGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWGradeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Refuse") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 4:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 5:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
