

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
import com.sinosoft.lis.db.RIBsnsBillRelaDB;

/*
 * <p>ClassName: RIBsnsBillRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 再保系统模型
 */
public class RIBsnsBillRelaSchema implements Schema, Cloneable
{
	// @Field
	/** 账单编码 */
	private String BillNo;
	/** 算法序号 */
	private int CalOrder;
	/** 费用编码 */
	private String FeeCode;
	/** 费用名称 */
	private String FeeName;
	/** 再保公司 */
	private String ReComCode;
	/** 借/贷 */
	private String DebCre;
	/** 明细科目 */
	private String Details;
	/** 科目名称 */
	private String DetailsName;
	/** 录入类型 */
	private String InputType;
	/** 账单项类型 */
	private String BillItemType;
	/** 计算类型 */
	private String ItemCalType;
	/** 固定数字值 */
	private double DoubleValue;
	/** 计算处理类 */
	private String CalClass;
	/** 计算sql算法 */
	private String CalSQL;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RIBsnsBillRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "BillNo";
		pk[1] = "CalOrder";

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
		RIBsnsBillRelaSchema cloned = (RIBsnsBillRelaSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBillNo()
	{
		return BillNo;
	}
	public void setBillNo(String aBillNo)
	{
		BillNo = aBillNo;
	}
	public int getCalOrder()
	{
		return CalOrder;
	}
	public void setCalOrder(int aCalOrder)
	{
		CalOrder = aCalOrder;
	}
	public void setCalOrder(String aCalOrder)
	{
		if (aCalOrder != null && !aCalOrder.equals(""))
		{
			Integer tInteger = new Integer(aCalOrder);
			int i = tInteger.intValue();
			CalOrder = i;
		}
	}

	public String getFeeCode()
	{
		return FeeCode;
	}
	public void setFeeCode(String aFeeCode)
	{
		FeeCode = aFeeCode;
	}
	public String getFeeName()
	{
		return FeeName;
	}
	public void setFeeName(String aFeeName)
	{
		FeeName = aFeeName;
	}
	public String getReComCode()
	{
		return ReComCode;
	}
	public void setReComCode(String aReComCode)
	{
		ReComCode = aReComCode;
	}
	public String getDebCre()
	{
		return DebCre;
	}
	public void setDebCre(String aDebCre)
	{
		DebCre = aDebCre;
	}
	/**
	* 01  期初准备金<p>
	* <p>
	* 02  再保保费<p>
	* 03  准备金利息<p>
	* <p>
	* 04  返还给经纪中介的佣金和摊回税收<p>
	* <p>
	* 05  摊回佣金<p>
	* 06  <p>
	* 07  期末准备金<p>
	* <p>
	* 08  分保余额
	*/
	public String getDetails()
	{
		return Details;
	}
	public void setDetails(String aDetails)
	{
		Details = aDetails;
	}
	public String getDetailsName()
	{
		return DetailsName;
	}
	public void setDetailsName(String aDetailsName)
	{
		DetailsName = aDetailsName;
	}
	/**
	* 01：系统计算<p>
	* <p>
	* 02：手工录入
	*/
	public String getInputType()
	{
		return InputType;
	}
	public void setInputType(String aInputType)
	{
		InputType = aInputType;
	}
	/**
	* 01-费用项<p>
	* <p>
	* 02-信息项
	*/
	public String getBillItemType()
	{
		return BillItemType;
	}
	public void setBillItemType(String aBillItemType)
	{
		BillItemType = aBillItemType;
	}
	/**
	* 00-不需计算<p>
	* 01-固定数值,<p>
	* 02-Sql计算,<p>
	* 03-类计算
	*/
	public String getItemCalType()
	{
		return ItemCalType;
	}
	public void setItemCalType(String aItemCalType)
	{
		ItemCalType = aItemCalType;
	}
	public double getDoubleValue()
	{
		return DoubleValue;
	}
	public void setDoubleValue(double aDoubleValue)
	{
		DoubleValue = aDoubleValue;
	}
	public void setDoubleValue(String aDoubleValue)
	{
		if (aDoubleValue != null && !aDoubleValue.equals(""))
		{
			Double tDouble = new Double(aDoubleValue);
			double d = tDouble.doubleValue();
			DoubleValue = d;
		}
	}

	public String getCalClass()
	{
		return CalClass;
	}
	public void setCalClass(String aCalClass)
	{
		CalClass = aCalClass;
	}
	public String getCalSQL()
	{
		return CalSQL;
	}
	public void setCalSQL(String aCalSQL)
	{
		CalSQL = aCalSQL;
	}

	/**
	* 使用另外一个 RIBsnsBillRelaSchema 对象给 Schema 赋值
	* @param: aRIBsnsBillRelaSchema RIBsnsBillRelaSchema
	**/
	public void setSchema(RIBsnsBillRelaSchema aRIBsnsBillRelaSchema)
	{
		this.BillNo = aRIBsnsBillRelaSchema.getBillNo();
		this.CalOrder = aRIBsnsBillRelaSchema.getCalOrder();
		this.FeeCode = aRIBsnsBillRelaSchema.getFeeCode();
		this.FeeName = aRIBsnsBillRelaSchema.getFeeName();
		this.ReComCode = aRIBsnsBillRelaSchema.getReComCode();
		this.DebCre = aRIBsnsBillRelaSchema.getDebCre();
		this.Details = aRIBsnsBillRelaSchema.getDetails();
		this.DetailsName = aRIBsnsBillRelaSchema.getDetailsName();
		this.InputType = aRIBsnsBillRelaSchema.getInputType();
		this.BillItemType = aRIBsnsBillRelaSchema.getBillItemType();
		this.ItemCalType = aRIBsnsBillRelaSchema.getItemCalType();
		this.DoubleValue = aRIBsnsBillRelaSchema.getDoubleValue();
		this.CalClass = aRIBsnsBillRelaSchema.getCalClass();
		this.CalSQL = aRIBsnsBillRelaSchema.getCalSQL();
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
			if( rs.getString("BillNo") == null )
				this.BillNo = null;
			else
				this.BillNo = rs.getString("BillNo").trim();

			this.CalOrder = rs.getInt("CalOrder");
			if( rs.getString("FeeCode") == null )
				this.FeeCode = null;
			else
				this.FeeCode = rs.getString("FeeCode").trim();

			if( rs.getString("FeeName") == null )
				this.FeeName = null;
			else
				this.FeeName = rs.getString("FeeName").trim();

			if( rs.getString("ReComCode") == null )
				this.ReComCode = null;
			else
				this.ReComCode = rs.getString("ReComCode").trim();

			if( rs.getString("DebCre") == null )
				this.DebCre = null;
			else
				this.DebCre = rs.getString("DebCre").trim();

			if( rs.getString("Details") == null )
				this.Details = null;
			else
				this.Details = rs.getString("Details").trim();

			if( rs.getString("DetailsName") == null )
				this.DetailsName = null;
			else
				this.DetailsName = rs.getString("DetailsName").trim();

			if( rs.getString("InputType") == null )
				this.InputType = null;
			else
				this.InputType = rs.getString("InputType").trim();

			if( rs.getString("BillItemType") == null )
				this.BillItemType = null;
			else
				this.BillItemType = rs.getString("BillItemType").trim();

			if( rs.getString("ItemCalType") == null )
				this.ItemCalType = null;
			else
				this.ItemCalType = rs.getString("ItemCalType").trim();

			this.DoubleValue = rs.getDouble("DoubleValue");
			if( rs.getString("CalClass") == null )
				this.CalClass = null;
			else
				this.CalClass = rs.getString("CalClass").trim();

			if( rs.getString("CalSQL") == null )
				this.CalSQL = null;
			else
				this.CalSQL = rs.getString("CalSQL").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RIBsnsBillRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RIBsnsBillRelaSchema getSchema()
	{
		RIBsnsBillRelaSchema aRIBsnsBillRelaSchema = new RIBsnsBillRelaSchema();
		aRIBsnsBillRelaSchema.setSchema(this);
		return aRIBsnsBillRelaSchema;
	}

	public RIBsnsBillRelaDB getDB()
	{
		RIBsnsBillRelaDB aDBOper = new RIBsnsBillRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BillNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CalOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DebCre)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Details)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DetailsName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InputType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BillItemType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ItemCalType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(DoubleValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CalSQL));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRIBsnsBillRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BillNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CalOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			FeeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ReComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			DebCre = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Details = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			DetailsName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			InputType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BillItemType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ItemCalType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			DoubleValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			CalClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CalSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RIBsnsBillRelaSchema";
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
		if (FCode.equalsIgnoreCase("BillNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillNo));
		}
		if (FCode.equalsIgnoreCase("CalOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalOrder));
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeCode));
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeName));
		}
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReComCode));
		}
		if (FCode.equalsIgnoreCase("DebCre"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DebCre));
		}
		if (FCode.equalsIgnoreCase("Details"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Details));
		}
		if (FCode.equalsIgnoreCase("DetailsName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DetailsName));
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InputType));
		}
		if (FCode.equalsIgnoreCase("BillItemType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BillItemType));
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ItemCalType));
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DoubleValue));
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalClass));
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalSQL));
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
				strFieldValue = StrTool.GBKToUnicode(BillNo);
				break;
			case 1:
				strFieldValue = String.valueOf(CalOrder);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FeeCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ReComCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(DebCre);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Details);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(DetailsName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(InputType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BillItemType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ItemCalType);
				break;
			case 11:
				strFieldValue = String.valueOf(DoubleValue);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CalClass);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CalSQL);
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

		if (FCode.equalsIgnoreCase("BillNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillNo = FValue.trim();
			}
			else
				BillNo = null;
		}
		if (FCode.equalsIgnoreCase("CalOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CalOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeCode = FValue.trim();
			}
			else
				FeeCode = null;
		}
		if (FCode.equalsIgnoreCase("FeeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeName = FValue.trim();
			}
			else
				FeeName = null;
		}
		if (FCode.equalsIgnoreCase("ReComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReComCode = FValue.trim();
			}
			else
				ReComCode = null;
		}
		if (FCode.equalsIgnoreCase("DebCre"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DebCre = FValue.trim();
			}
			else
				DebCre = null;
		}
		if (FCode.equalsIgnoreCase("Details"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Details = FValue.trim();
			}
			else
				Details = null;
		}
		if (FCode.equalsIgnoreCase("DetailsName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DetailsName = FValue.trim();
			}
			else
				DetailsName = null;
		}
		if (FCode.equalsIgnoreCase("InputType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InputType = FValue.trim();
			}
			else
				InputType = null;
		}
		if (FCode.equalsIgnoreCase("BillItemType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BillItemType = FValue.trim();
			}
			else
				BillItemType = null;
		}
		if (FCode.equalsIgnoreCase("ItemCalType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ItemCalType = FValue.trim();
			}
			else
				ItemCalType = null;
		}
		if (FCode.equalsIgnoreCase("DoubleValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				DoubleValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("CalClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalClass = FValue.trim();
			}
			else
				CalClass = null;
		}
		if (FCode.equalsIgnoreCase("CalSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalSQL = FValue.trim();
			}
			else
				CalSQL = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RIBsnsBillRelaSchema other = (RIBsnsBillRelaSchema)otherObject;
		return
			BillNo.equals(other.getBillNo())
			&& CalOrder == other.getCalOrder()
			&& FeeCode.equals(other.getFeeCode())
			&& FeeName.equals(other.getFeeName())
			&& ReComCode.equals(other.getReComCode())
			&& DebCre.equals(other.getDebCre())
			&& Details.equals(other.getDetails())
			&& DetailsName.equals(other.getDetailsName())
			&& InputType.equals(other.getInputType())
			&& BillItemType.equals(other.getBillItemType())
			&& ItemCalType.equals(other.getItemCalType())
			&& DoubleValue == other.getDoubleValue()
			&& CalClass.equals(other.getCalClass())
			&& CalSQL.equals(other.getCalSQL());
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
		if( strFieldName.equals("BillNo") ) {
			return 0;
		}
		if( strFieldName.equals("CalOrder") ) {
			return 1;
		}
		if( strFieldName.equals("FeeCode") ) {
			return 2;
		}
		if( strFieldName.equals("FeeName") ) {
			return 3;
		}
		if( strFieldName.equals("ReComCode") ) {
			return 4;
		}
		if( strFieldName.equals("DebCre") ) {
			return 5;
		}
		if( strFieldName.equals("Details") ) {
			return 6;
		}
		if( strFieldName.equals("DetailsName") ) {
			return 7;
		}
		if( strFieldName.equals("InputType") ) {
			return 8;
		}
		if( strFieldName.equals("BillItemType") ) {
			return 9;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return 10;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return 11;
		}
		if( strFieldName.equals("CalClass") ) {
			return 12;
		}
		if( strFieldName.equals("CalSQL") ) {
			return 13;
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
				strFieldName = "BillNo";
				break;
			case 1:
				strFieldName = "CalOrder";
				break;
			case 2:
				strFieldName = "FeeCode";
				break;
			case 3:
				strFieldName = "FeeName";
				break;
			case 4:
				strFieldName = "ReComCode";
				break;
			case 5:
				strFieldName = "DebCre";
				break;
			case 6:
				strFieldName = "Details";
				break;
			case 7:
				strFieldName = "DetailsName";
				break;
			case 8:
				strFieldName = "InputType";
				break;
			case 9:
				strFieldName = "BillItemType";
				break;
			case 10:
				strFieldName = "ItemCalType";
				break;
			case 11:
				strFieldName = "DoubleValue";
				break;
			case 12:
				strFieldName = "CalClass";
				break;
			case 13:
				strFieldName = "CalSQL";
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
		if( strFieldName.equals("BillNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DebCre") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Details") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DetailsName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InputType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BillItemType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ItemCalType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DoubleValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CalClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalSQL") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
