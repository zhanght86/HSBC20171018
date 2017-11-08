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
import com.sinosoft.lis.db.LFXMLCollDB;
import org.apache.log4j.Logger;

/*
 * <p>ClassName: LFXMLCollSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: msreport
 */
public class LFXMLCollSchema implements Schema, Cloneable
{
	private static Logger logger = Logger.getLogger(LFXMLCollSchema.class);

	// @Field
	/** 保监会机构编码 */
	private String ComCodeISC;
	/** 内部科目编码 */
	private String ItemCode;
	/** 类型 */
	private String RepType;
	/** 统计年 */
	private int StatYear;
	/** 统计月 */
	private int StatMon;
	/** 上级内部科目编码 */
	private String UpItemCode;
	/** 上级机构编码 */
	private String ParentComCodeISC;
	/** 层级 */
	private int Layer;
	/** 统计值 */
	private double StatValue;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFXMLCollSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ComCodeISC";
		pk[1] = "ItemCode";
		pk[2] = "RepType";
		pk[3] = "StatYear";
		pk[4] = "StatMon";

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
		LFXMLCollSchema cloned = (LFXMLCollSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getComCodeISC()
	{
		return ComCodeISC;
	}
	public void setComCodeISC(String aComCodeISC)
	{
		if(aComCodeISC!=null && aComCodeISC.length()>6)
			throw new IllegalArgumentException("保监会机构编码ComCodeISC值"+aComCodeISC+"的长度"+aComCodeISC.length()+"大于最大值6");
		ComCodeISC = aComCodeISC;
	}
	public String getItemCode()
	{
		return ItemCode;
	}
	public void setItemCode(String aItemCode)
	{
		if(aItemCode!=null && aItemCode.length()>8)
			throw new IllegalArgumentException("内部科目编码ItemCode值"+aItemCode+"的长度"+aItemCode.length()+"大于最大值8");
		ItemCode = aItemCode;
	}
	/**
	* 1-快报<p>
	* 2-月报<p>
	* 3-季报<p>
	* 4-半年报<p>
	* 5-年报
	*/
	public String getRepType()
	{
		return RepType;
	}
	public void setRepType(String aRepType)
	{
		if(aRepType!=null && aRepType.length()>1)
			throw new IllegalArgumentException("类型RepType值"+aRepType+"的长度"+aRepType.length()+"大于最大值1");
		RepType = aRepType;
	}
	public int getStatYear()
	{
		return StatYear;
	}
	public void setStatYear(int aStatYear)
	{
		StatYear = aStatYear;
	}
	public void setStatYear(String aStatYear)
	{
		if (aStatYear != null && !aStatYear.equals(""))
		{
			Integer tInteger = new Integer(aStatYear);
			int i = tInteger.intValue();
			StatYear = i;
		}
	}

	public int getStatMon()
	{
		return StatMon;
	}
	public void setStatMon(int aStatMon)
	{
		StatMon = aStatMon;
	}
	public void setStatMon(String aStatMon)
	{
		if (aStatMon != null && !aStatMon.equals(""))
		{
			Integer tInteger = new Integer(aStatMon);
			int i = tInteger.intValue();
			StatMon = i;
		}
	}

	public String getUpItemCode()
	{
		return UpItemCode;
	}
	public void setUpItemCode(String aUpItemCode)
	{
		if(aUpItemCode!=null && aUpItemCode.length()>8)
			throw new IllegalArgumentException("上级内部科目编码UpItemCode值"+aUpItemCode+"的长度"+aUpItemCode.length()+"大于最大值8");
		UpItemCode = aUpItemCode;
	}
	/**
	* 该机构的上级机构。如果没有上级机构，则机构编码为______，因为000000已经被赋予特定含义。
	*/
	public String getParentComCodeISC()
	{
		return ParentComCodeISC;
	}
	public void setParentComCodeISC(String aParentComCodeISC)
	{
		if(aParentComCodeISC!=null && aParentComCodeISC.length()>6)
			throw new IllegalArgumentException("上级机构编码ParentComCodeISC值"+aParentComCodeISC+"的长度"+aParentComCodeISC.length()+"大于最大值6");
		ParentComCodeISC = aParentComCodeISC;
	}
	/**
	* 层级校验描述
	*/
	public int getLayer()
	{
		return Layer;
	}
	public void setLayer(int aLayer)
	{
		Layer = aLayer;
	}
	public void setLayer(String aLayer)
	{
		if (aLayer != null && !aLayer.equals(""))
		{
			Integer tInteger = new Integer(aLayer);
			int i = tInteger.intValue();
			Layer = i;
		}
	}

	public double getStatValue()
	{
		return StatValue;
	}
	public void setStatValue(double aStatValue)
	{
		StatValue = aStatValue;
	}
	public void setStatValue(String aStatValue)
	{
		if (aStatValue != null && !aStatValue.equals(""))
		{
			Double tDouble = new Double(aStatValue);
			double d = tDouble.doubleValue();
			StatValue = d;
		}
	}

	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>200)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值200");
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LFXMLCollSchema 对象给 Schema 赋值
	* @param: aLFXMLCollSchema LFXMLCollSchema
	**/
	public void setSchema(LFXMLCollSchema aLFXMLCollSchema)
	{
		this.ComCodeISC = aLFXMLCollSchema.getComCodeISC();
		this.ItemCode = aLFXMLCollSchema.getItemCode();
		this.RepType = aLFXMLCollSchema.getRepType();
		this.StatYear = aLFXMLCollSchema.getStatYear();
		this.StatMon = aLFXMLCollSchema.getStatMon();
		this.UpItemCode = aLFXMLCollSchema.getUpItemCode();
		this.ParentComCodeISC = aLFXMLCollSchema.getParentComCodeISC();
		this.Layer = aLFXMLCollSchema.getLayer();
		this.StatValue = aLFXMLCollSchema.getStatValue();
		this.Remark = aLFXMLCollSchema.getRemark();
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
			if( rs.getString("ComCodeISC") == null )
				this.ComCodeISC = null;
			else
				this.ComCodeISC = rs.getString("ComCodeISC").trim();

			if( rs.getString("ItemCode") == null )
				this.ItemCode = null;
			else
				this.ItemCode = rs.getString("ItemCode").trim();

			if( rs.getString("RepType") == null )
				this.RepType = null;
			else
				this.RepType = rs.getString("RepType").trim();

			this.StatYear = rs.getInt("StatYear");
			this.StatMon = rs.getInt("StatMon");
			if( rs.getString("UpItemCode") == null )
				this.UpItemCode = null;
			else
				this.UpItemCode = rs.getString("UpItemCode").trim();

			if( rs.getString("ParentComCodeISC") == null )
				this.ParentComCodeISC = null;
			else
				this.ParentComCodeISC = rs.getString("ParentComCodeISC").trim();

			this.Layer = rs.getInt("Layer");
			this.StatValue = rs.getDouble("StatValue");
			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFXMLColl表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFXMLCollSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFXMLCollSchema getSchema()
	{
		LFXMLCollSchema aLFXMLCollSchema = new LFXMLCollSchema();
		aLFXMLCollSchema.setSchema(this);
		return aLFXMLCollSchema;
	}

	public LFXMLCollDB getDB()
	{
		LFXMLCollDB aDBOper = new LFXMLCollDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFXMLColl描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ComCodeISC)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RepType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StatYear));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StatMon));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpItemCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParentComCodeISC)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Layer));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(StatValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFXMLColl>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComCodeISC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RepType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			StatYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			StatMon= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			UpItemCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ParentComCodeISC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Layer= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			StatValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFXMLCollSchema";
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
		if (FCode.equalsIgnoreCase("ComCodeISC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCodeISC));
		}
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCode));
		}
		if (FCode.equalsIgnoreCase("RepType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RepType));
		}
		if (FCode.equalsIgnoreCase("StatYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatYear));
		}
		if (FCode.equalsIgnoreCase("StatMon"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatMon));
		}
		if (FCode.equalsIgnoreCase("UpItemCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpItemCode));
		}
		if (FCode.equalsIgnoreCase("ParentComCodeISC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentComCodeISC));
		}
		if (FCode.equalsIgnoreCase("Layer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Layer));
		}
		if (FCode.equalsIgnoreCase("StatValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StatValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
				strFieldValue = StrTool.GBKToUnicode(ComCodeISC);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ItemCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RepType);
				break;
			case 3:
				strFieldValue = String.valueOf(StatYear);
				break;
			case 4:
				strFieldValue = String.valueOf(StatMon);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UpItemCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ParentComCodeISC);
				break;
			case 7:
				strFieldValue = String.valueOf(Layer);
				break;
			case 8:
				strFieldValue = String.valueOf(StatValue);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Remark);
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

		if (FCode.equalsIgnoreCase("ComCodeISC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCodeISC = FValue.trim();
			}
			else
				ComCodeISC = null;
		}
		if (FCode.equalsIgnoreCase("ItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCode = FValue.trim();
			}
			else
				ItemCode = null;
		}
		if (FCode.equalsIgnoreCase("RepType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RepType = FValue.trim();
			}
			else
				RepType = null;
		}
		if (FCode.equalsIgnoreCase("StatYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StatYear = i;
			}
		}
		if (FCode.equalsIgnoreCase("StatMon"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				StatMon = i;
			}
		}
		if (FCode.equalsIgnoreCase("UpItemCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpItemCode = FValue.trim();
			}
			else
				UpItemCode = null;
		}
		if (FCode.equalsIgnoreCase("ParentComCodeISC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParentComCodeISC = FValue.trim();
			}
			else
				ParentComCodeISC = null;
		}
		if (FCode.equalsIgnoreCase("Layer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Layer = i;
			}
		}
		if (FCode.equalsIgnoreCase("StatValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				StatValue = d;
			}
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFXMLCollSchema other = (LFXMLCollSchema)otherObject;
		return
			ComCodeISC.equals(other.getComCodeISC())
			&& ItemCode.equals(other.getItemCode())
			&& RepType.equals(other.getRepType())
			&& StatYear == other.getStatYear()
			&& StatMon == other.getStatMon()
			&& UpItemCode.equals(other.getUpItemCode())
			&& ParentComCodeISC.equals(other.getParentComCodeISC())
			&& Layer == other.getLayer()
			&& StatValue == other.getStatValue()
			&& Remark.equals(other.getRemark());
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
		if( strFieldName.equals("ComCodeISC") ) {
			return 0;
		}
		if( strFieldName.equals("ItemCode") ) {
			return 1;
		}
		if( strFieldName.equals("RepType") ) {
			return 2;
		}
		if( strFieldName.equals("StatYear") ) {
			return 3;
		}
		if( strFieldName.equals("StatMon") ) {
			return 4;
		}
		if( strFieldName.equals("UpItemCode") ) {
			return 5;
		}
		if( strFieldName.equals("ParentComCodeISC") ) {
			return 6;
		}
		if( strFieldName.equals("Layer") ) {
			return 7;
		}
		if( strFieldName.equals("StatValue") ) {
			return 8;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "ComCodeISC";
				break;
			case 1:
				strFieldName = "ItemCode";
				break;
			case 2:
				strFieldName = "RepType";
				break;
			case 3:
				strFieldName = "StatYear";
				break;
			case 4:
				strFieldName = "StatMon";
				break;
			case 5:
				strFieldName = "UpItemCode";
				break;
			case 6:
				strFieldName = "ParentComCodeISC";
				break;
			case 7:
				strFieldName = "Layer";
				break;
			case 8:
				strFieldName = "StatValue";
				break;
			case 9:
				strFieldName = "Remark";
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
		if( strFieldName.equals("ComCodeISC") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RepType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StatYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StatMon") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("UpItemCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParentComCodeISC") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Layer") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("StatValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Remark") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
