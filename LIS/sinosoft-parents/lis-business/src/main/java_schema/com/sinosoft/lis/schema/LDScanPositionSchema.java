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
import com.sinosoft.lis.db.LDScanPositionDB;

/*
 * <p>ClassName: LDScanPositionSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务未整理模型
 */
public class LDScanPositionSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDScanPositionSchema.class);

	// @Field
	/** 单证类型 */
	private String SubType;
	/** 元素代码 */
	private String ObjCode;
	/** 影像件索引 */
	private int PicIndex;
	/** 横坐标1 */
	private int x1;
	/** 纵坐标1 */
	private int y1;
	/** 横坐标2 */
	private int x2;
	/** 纵坐标2 */
	private int y2;
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

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDScanPositionSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SubType";
		pk[1] = "ObjCode";

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
		LDScanPositionSchema cloned = (LDScanPositionSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		if(aSubType!=null && aSubType.length()>8)
			throw new IllegalArgumentException("单证类型SubType值"+aSubType+"的长度"+aSubType.length()+"大于最大值8");
		SubType = aSubType;
	}
	public String getObjCode()
	{
		return ObjCode;
	}
	public void setObjCode(String aObjCode)
	{
		if(aObjCode!=null && aObjCode.length()>50)
			throw new IllegalArgumentException("元素代码ObjCode值"+aObjCode+"的长度"+aObjCode.length()+"大于最大值50");
		ObjCode = aObjCode;
	}
	public int getPicIndex()
	{
		return PicIndex;
	}
	public void setPicIndex(int aPicIndex)
	{
		PicIndex = aPicIndex;
	}
	public void setPicIndex(String aPicIndex)
	{
		if (aPicIndex != null && !aPicIndex.equals(""))
		{
			Integer tInteger = new Integer(aPicIndex);
			int i = tInteger.intValue();
			PicIndex = i;
		}
	}

	public int getx1()
	{
		return x1;
	}
	public void setx1(int ax1)
	{
		x1 = ax1;
	}
	public void setx1(String ax1)
	{
		if (ax1 != null && !ax1.equals(""))
		{
			Integer tInteger = new Integer(ax1);
			int i = tInteger.intValue();
			x1 = i;
		}
	}

	public int gety1()
	{
		return y1;
	}
	public void sety1(int ay1)
	{
		y1 = ay1;
	}
	public void sety1(String ay1)
	{
		if (ay1 != null && !ay1.equals(""))
		{
			Integer tInteger = new Integer(ay1);
			int i = tInteger.intValue();
			y1 = i;
		}
	}

	public int getx2()
	{
		return x2;
	}
	public void setx2(int ax2)
	{
		x2 = ax2;
	}
	public void setx2(String ax2)
	{
		if (ax2 != null && !ax2.equals(""))
		{
			Integer tInteger = new Integer(ax2);
			int i = tInteger.intValue();
			x2 = i;
		}
	}

	public int gety2()
	{
		return y2;
	}
	public void sety2(int ay2)
	{
		y2 = ay2;
	}
	public void sety2(String ay2)
	{
		if (ay2 != null && !ay2.equals(""))
		{
			Integer tInteger = new Integer(ay2);
			int i = tInteger.intValue();
			y2 = i;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDScanPositionSchema 对象给 Schema 赋值
	* @param: aLDScanPositionSchema LDScanPositionSchema
	**/
	public void setSchema(LDScanPositionSchema aLDScanPositionSchema)
	{
		this.SubType = aLDScanPositionSchema.getSubType();
		this.ObjCode = aLDScanPositionSchema.getObjCode();
		this.PicIndex = aLDScanPositionSchema.getPicIndex();
		this.x1 = aLDScanPositionSchema.getx1();
		this.y1 = aLDScanPositionSchema.gety1();
		this.x2 = aLDScanPositionSchema.getx2();
		this.y2 = aLDScanPositionSchema.gety2();
		this.Operator = aLDScanPositionSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDScanPositionSchema.getMakeDate());
		this.MakeTime = aLDScanPositionSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDScanPositionSchema.getModifyDate());
		this.ModifyTime = aLDScanPositionSchema.getModifyTime();
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
			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("ObjCode") == null )
				this.ObjCode = null;
			else
				this.ObjCode = rs.getString("ObjCode").trim();

			this.PicIndex = rs.getInt("PicIndex");
			this.x1 = rs.getInt("x1");
			this.y1 = rs.getInt("y1");
			this.x2 = rs.getInt("x2");
			this.y2 = rs.getInt("y2");
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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDScanPosition表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDScanPositionSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDScanPositionSchema getSchema()
	{
		LDScanPositionSchema aLDScanPositionSchema = new LDScanPositionSchema();
		aLDScanPositionSchema.setSchema(this);
		return aLDScanPositionSchema;
	}

	public LDScanPositionDB getDB()
	{
		LDScanPositionDB aDBOper = new LDScanPositionDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDScanPosition描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ObjCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PicIndex));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(x1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(y1));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(x2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(y2));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDScanPosition>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ObjCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PicIndex= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,3,SysConst.PACKAGESPILTER))).intValue();
			x1= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			y1= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			x2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			y2= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDScanPositionSchema";
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
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("ObjCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ObjCode));
		}
		if (FCode.equalsIgnoreCase("PicIndex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PicIndex));
		}
		if (FCode.equalsIgnoreCase("x1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(x1));
		}
		if (FCode.equalsIgnoreCase("y1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(y1));
		}
		if (FCode.equalsIgnoreCase("x2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(x2));
		}
		if (FCode.equalsIgnoreCase("y2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(y2));
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
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ObjCode);
				break;
			case 2:
				strFieldValue = String.valueOf(PicIndex);
				break;
			case 3:
				strFieldValue = String.valueOf(x1);
				break;
			case 4:
				strFieldValue = String.valueOf(y1);
				break;
			case 5:
				strFieldValue = String.valueOf(x2);
				break;
			case 6:
				strFieldValue = String.valueOf(y2);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("ObjCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ObjCode = FValue.trim();
			}
			else
				ObjCode = null;
		}
		if (FCode.equalsIgnoreCase("PicIndex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				PicIndex = i;
			}
		}
		if (FCode.equalsIgnoreCase("x1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				x1 = i;
			}
		}
		if (FCode.equalsIgnoreCase("y1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				y1 = i;
			}
		}
		if (FCode.equalsIgnoreCase("x2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				x2 = i;
			}
		}
		if (FCode.equalsIgnoreCase("y2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				y2 = i;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDScanPositionSchema other = (LDScanPositionSchema)otherObject;
		return
			SubType.equals(other.getSubType())
			&& ObjCode.equals(other.getObjCode())
			&& PicIndex == other.getPicIndex()
			&& x1 == other.getx1()
			&& y1 == other.gety1()
			&& x2 == other.getx2()
			&& y2 == other.gety2()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("SubType") ) {
			return 0;
		}
		if( strFieldName.equals("ObjCode") ) {
			return 1;
		}
		if( strFieldName.equals("PicIndex") ) {
			return 2;
		}
		if( strFieldName.equals("x1") ) {
			return 3;
		}
		if( strFieldName.equals("y1") ) {
			return 4;
		}
		if( strFieldName.equals("x2") ) {
			return 5;
		}
		if( strFieldName.equals("y2") ) {
			return 6;
		}
		if( strFieldName.equals("Operator") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "SubType";
				break;
			case 1:
				strFieldName = "ObjCode";
				break;
			case 2:
				strFieldName = "PicIndex";
				break;
			case 3:
				strFieldName = "x1";
				break;
			case 4:
				strFieldName = "y1";
				break;
			case 5:
				strFieldName = "x2";
				break;
			case 6:
				strFieldName = "y2";
				break;
			case 7:
				strFieldName = "Operator";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ObjCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PicIndex") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("x1") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("y1") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("x2") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("y2") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_INT;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
