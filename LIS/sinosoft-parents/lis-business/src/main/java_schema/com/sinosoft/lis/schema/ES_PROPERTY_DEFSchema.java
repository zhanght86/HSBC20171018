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
import com.sinosoft.lis.db.ES_PROPERTY_DEFDB;

/*
 * <p>ClassName: ES_PROPERTY_DEFSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: bug
 */
public class ES_PROPERTY_DEFSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(ES_PROPERTY_DEFSchema.class);
	// @Field
	/** 定义编码 */
	private String DefCode;
	/** 映射字段 */
	private String PropField;
	/** 属性代码 */
	private String PropCode;
	/** 属性名称 */
	private String PropName;
	/** 字段序号 */
	private int FieldOrder;
	/** 业务类型编码 */
	private String BussType;
	/** 单证细类编码 */
	private String SubType;
	/** 单证版本 */
	private String Version;
	/** 控件类型 */
	private String CtrlType;
	/** 控件上边距 */
	private int CtrlTop;
	/** 控件左距 */
	private int CtrlLeft;
	/** 控件高度 */
	private int CtrlHeight;
	/** 控件宽度 */
	private int CtrlWidth;
	/** 控件可见性 */
	private String CtrlVisible;
	/** 控件标题 */
	private String CtrlTitle;
	/** 控件所在窗体 */
	private String CtrlForm;
	/** 下拉选项 */
	private String ListCodeType;
	/** 默认值 */
	private String CtrlDefaultValue;
	/** 备注 */
	private String Remark;
	/** 有效标记 */
	private String ValidFlag;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public ES_PROPERTY_DEFSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "DefCode";
		pk[1] = "PropField";
		pk[2] = "BussType";
		pk[3] = "SubType";

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
		ES_PROPERTY_DEFSchema cloned = (ES_PROPERTY_DEFSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getDefCode()
	{
		return DefCode;
	}
	public void setDefCode(String aDefCode)
	{
		DefCode = aDefCode;
	}
	public String getPropField()
	{
		return PropField;
	}
	public void setPropField(String aPropField)
	{
		PropField = aPropField;
	}
	public String getPropCode()
	{
		return PropCode;
	}
	public void setPropCode(String aPropCode)
	{
		PropCode = aPropCode;
	}
	public String getPropName()
	{
		return PropName;
	}
	public void setPropName(String aPropName)
	{
		PropName = aPropName;
	}
	public int getFieldOrder()
	{
		return FieldOrder;
	}
	public void setFieldOrder(int aFieldOrder)
	{
		FieldOrder = aFieldOrder;
	}
	public void setFieldOrder(String aFieldOrder)
	{
		if (aFieldOrder != null && !aFieldOrder.equals(""))
		{
			Integer tInteger = new Integer(aFieldOrder);
			int i = tInteger.intValue();
			FieldOrder = i;
		}
	}

	public String getBussType()
	{
		return BussType;
	}
	public void setBussType(String aBussType)
	{
		BussType = aBussType;
	}
	public String getSubType()
	{
		return SubType;
	}
	public void setSubType(String aSubType)
	{
		SubType = aSubType;
	}
	public String getVersion()
	{
		return Version;
	}
	public void setVersion(String aVersion)
	{
		Version = aVersion;
	}
	public String getCtrlType()
	{
		return CtrlType;
	}
	public void setCtrlType(String aCtrlType)
	{
		CtrlType = aCtrlType;
	}
	public int getCtrlTop()
	{
		return CtrlTop;
	}
	public void setCtrlTop(int aCtrlTop)
	{
		CtrlTop = aCtrlTop;
	}
	public void setCtrlTop(String aCtrlTop)
	{
		if (aCtrlTop != null && !aCtrlTop.equals(""))
		{
			Integer tInteger = new Integer(aCtrlTop);
			int i = tInteger.intValue();
			CtrlTop = i;
		}
	}

	public int getCtrlLeft()
	{
		return CtrlLeft;
	}
	public void setCtrlLeft(int aCtrlLeft)
	{
		CtrlLeft = aCtrlLeft;
	}
	public void setCtrlLeft(String aCtrlLeft)
	{
		if (aCtrlLeft != null && !aCtrlLeft.equals(""))
		{
			Integer tInteger = new Integer(aCtrlLeft);
			int i = tInteger.intValue();
			CtrlLeft = i;
		}
	}

	public int getCtrlHeight()
	{
		return CtrlHeight;
	}
	public void setCtrlHeight(int aCtrlHeight)
	{
		CtrlHeight = aCtrlHeight;
	}
	public void setCtrlHeight(String aCtrlHeight)
	{
		if (aCtrlHeight != null && !aCtrlHeight.equals(""))
		{
			Integer tInteger = new Integer(aCtrlHeight);
			int i = tInteger.intValue();
			CtrlHeight = i;
		}
	}

	public int getCtrlWidth()
	{
		return CtrlWidth;
	}
	public void setCtrlWidth(int aCtrlWidth)
	{
		CtrlWidth = aCtrlWidth;
	}
	public void setCtrlWidth(String aCtrlWidth)
	{
		if (aCtrlWidth != null && !aCtrlWidth.equals(""))
		{
			Integer tInteger = new Integer(aCtrlWidth);
			int i = tInteger.intValue();
			CtrlWidth = i;
		}
	}

	public String getCtrlVisible()
	{
		return CtrlVisible;
	}
	public void setCtrlVisible(String aCtrlVisible)
	{
		CtrlVisible = aCtrlVisible;
	}
	public String getCtrlTitle()
	{
		return CtrlTitle;
	}
	public void setCtrlTitle(String aCtrlTitle)
	{
		CtrlTitle = aCtrlTitle;
	}
	public String getCtrlForm()
	{
		return CtrlForm;
	}
	public void setCtrlForm(String aCtrlForm)
	{
		CtrlForm = aCtrlForm;
	}
	/**
	* 与LDCode结合，类似CodeSelect。描述LDCode表中的CodeType。
	*/
	public String getListCodeType()
	{
		return ListCodeType;
	}
	public void setListCodeType(String aListCodeType)
	{
		ListCodeType = aListCodeType;
	}
	public String getCtrlDefaultValue()
	{
		return CtrlDefaultValue;
	}
	public void setCtrlDefaultValue(String aCtrlDefaultValue)
	{
		CtrlDefaultValue = aCtrlDefaultValue;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	public String getValidFlag()
	{
		return ValidFlag;
	}
	public void setValidFlag(String aValidFlag)
	{
		ValidFlag = aValidFlag;
	}

	/**
	* 使用另外一个 ES_PROPERTY_DEFSchema 对象给 Schema 赋值
	* @param: aES_PROPERTY_DEFSchema ES_PROPERTY_DEFSchema
	**/
	public void setSchema(ES_PROPERTY_DEFSchema aES_PROPERTY_DEFSchema)
	{
		this.DefCode = aES_PROPERTY_DEFSchema.getDefCode();
		this.PropField = aES_PROPERTY_DEFSchema.getPropField();
		this.PropCode = aES_PROPERTY_DEFSchema.getPropCode();
		this.PropName = aES_PROPERTY_DEFSchema.getPropName();
		this.FieldOrder = aES_PROPERTY_DEFSchema.getFieldOrder();
		this.BussType = aES_PROPERTY_DEFSchema.getBussType();
		this.SubType = aES_PROPERTY_DEFSchema.getSubType();
		this.Version = aES_PROPERTY_DEFSchema.getVersion();
		this.CtrlType = aES_PROPERTY_DEFSchema.getCtrlType();
		this.CtrlTop = aES_PROPERTY_DEFSchema.getCtrlTop();
		this.CtrlLeft = aES_PROPERTY_DEFSchema.getCtrlLeft();
		this.CtrlHeight = aES_PROPERTY_DEFSchema.getCtrlHeight();
		this.CtrlWidth = aES_PROPERTY_DEFSchema.getCtrlWidth();
		this.CtrlVisible = aES_PROPERTY_DEFSchema.getCtrlVisible();
		this.CtrlTitle = aES_PROPERTY_DEFSchema.getCtrlTitle();
		this.CtrlForm = aES_PROPERTY_DEFSchema.getCtrlForm();
		this.ListCodeType = aES_PROPERTY_DEFSchema.getListCodeType();
		this.CtrlDefaultValue = aES_PROPERTY_DEFSchema.getCtrlDefaultValue();
		this.Remark = aES_PROPERTY_DEFSchema.getRemark();
		this.ValidFlag = aES_PROPERTY_DEFSchema.getValidFlag();
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
			if( rs.getString("DefCode") == null )
				this.DefCode = null;
			else
				this.DefCode = rs.getString("DefCode").trim();

			if( rs.getString("PropField") == null )
				this.PropField = null;
			else
				this.PropField = rs.getString("PropField").trim();

			if( rs.getString("PropCode") == null )
				this.PropCode = null;
			else
				this.PropCode = rs.getString("PropCode").trim();

			if( rs.getString("PropName") == null )
				this.PropName = null;
			else
				this.PropName = rs.getString("PropName").trim();

			this.FieldOrder = rs.getInt("FieldOrder");
			if( rs.getString("BussType") == null )
				this.BussType = null;
			else
				this.BussType = rs.getString("BussType").trim();

			if( rs.getString("SubType") == null )
				this.SubType = null;
			else
				this.SubType = rs.getString("SubType").trim();

			if( rs.getString("Version") == null )
				this.Version = null;
			else
				this.Version = rs.getString("Version").trim();

			if( rs.getString("CtrlType") == null )
				this.CtrlType = null;
			else
				this.CtrlType = rs.getString("CtrlType").trim();

			this.CtrlTop = rs.getInt("CtrlTop");
			this.CtrlLeft = rs.getInt("CtrlLeft");
			this.CtrlHeight = rs.getInt("CtrlHeight");
			this.CtrlWidth = rs.getInt("CtrlWidth");
			if( rs.getString("CtrlVisible") == null )
				this.CtrlVisible = null;
			else
				this.CtrlVisible = rs.getString("CtrlVisible").trim();

			if( rs.getString("CtrlTitle") == null )
				this.CtrlTitle = null;
			else
				this.CtrlTitle = rs.getString("CtrlTitle").trim();

			if( rs.getString("CtrlForm") == null )
				this.CtrlForm = null;
			else
				this.CtrlForm = rs.getString("CtrlForm").trim();

			if( rs.getString("ListCodeType") == null )
				this.ListCodeType = null;
			else
				this.ListCodeType = rs.getString("ListCodeType").trim();

			if( rs.getString("CtrlDefaultValue") == null )
				this.CtrlDefaultValue = null;
			else
				this.CtrlDefaultValue = rs.getString("CtrlDefaultValue").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("ValidFlag") == null )
				this.ValidFlag = null;
			else
				this.ValidFlag = rs.getString("ValidFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的ES_PROPERTY_DEF表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_PROPERTY_DEFSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public ES_PROPERTY_DEFSchema getSchema()
	{
		ES_PROPERTY_DEFSchema aES_PROPERTY_DEFSchema = new ES_PROPERTY_DEFSchema();
		aES_PROPERTY_DEFSchema.setSchema(this);
		return aES_PROPERTY_DEFSchema;
	}

	public ES_PROPERTY_DEFDB getDB()
	{
		ES_PROPERTY_DEFDB aDBOper = new ES_PROPERTY_DEFDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_PROPERTY_DEF描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(DefCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropField)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PropName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(FieldOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Version)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlTop));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlLeft));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlHeight));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CtrlWidth));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlVisible)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlTitle)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlForm)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ListCodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CtrlDefaultValue)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ValidFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpES_PROPERTY_DEF>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			DefCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			PropField = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PropCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PropName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FieldOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			BussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SubType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Version = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CtrlType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			CtrlTop= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			CtrlLeft= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			CtrlHeight= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			CtrlWidth= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			CtrlVisible = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CtrlTitle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			CtrlForm = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ListCodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			CtrlDefaultValue = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ValidFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ES_PROPERTY_DEFSchema";
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
		if (FCode.equalsIgnoreCase("DefCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DefCode));
		}
		if (FCode.equalsIgnoreCase("PropField"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropField));
		}
		if (FCode.equalsIgnoreCase("PropCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropCode));
		}
		if (FCode.equalsIgnoreCase("PropName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PropName));
		}
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FieldOrder));
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussType));
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubType));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlType));
		}
		if (FCode.equalsIgnoreCase("CtrlTop"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlTop));
		}
		if (FCode.equalsIgnoreCase("CtrlLeft"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlLeft));
		}
		if (FCode.equalsIgnoreCase("CtrlHeight"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlHeight));
		}
		if (FCode.equalsIgnoreCase("CtrlWidth"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlWidth));
		}
		if (FCode.equalsIgnoreCase("CtrlVisible"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlVisible));
		}
		if (FCode.equalsIgnoreCase("CtrlTitle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlTitle));
		}
		if (FCode.equalsIgnoreCase("CtrlForm"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlForm));
		}
		if (FCode.equalsIgnoreCase("ListCodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ListCodeType));
		}
		if (FCode.equalsIgnoreCase("CtrlDefaultValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CtrlDefaultValue));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ValidFlag));
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
				strFieldValue = StrTool.GBKToUnicode(DefCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(PropField);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PropCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PropName);
				break;
			case 4:
				strFieldValue = String.valueOf(FieldOrder);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BussType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SubType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Version);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CtrlType);
				break;
			case 9:
				strFieldValue = String.valueOf(CtrlTop);
				break;
			case 10:
				strFieldValue = String.valueOf(CtrlLeft);
				break;
			case 11:
				strFieldValue = String.valueOf(CtrlHeight);
				break;
			case 12:
				strFieldValue = String.valueOf(CtrlWidth);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CtrlVisible);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CtrlTitle);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(CtrlForm);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ListCodeType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(CtrlDefaultValue);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ValidFlag);
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

		if (FCode.equalsIgnoreCase("DefCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DefCode = FValue.trim();
			}
			else
				DefCode = null;
		}
		if (FCode.equalsIgnoreCase("PropField"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropField = FValue.trim();
			}
			else
				PropField = null;
		}
		if (FCode.equalsIgnoreCase("PropCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropCode = FValue.trim();
			}
			else
				PropCode = null;
		}
		if (FCode.equalsIgnoreCase("PropName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PropName = FValue.trim();
			}
			else
				PropName = null;
		}
		if (FCode.equalsIgnoreCase("FieldOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FieldOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("BussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussType = FValue.trim();
			}
			else
				BussType = null;
		}
		if (FCode.equalsIgnoreCase("SubType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubType = FValue.trim();
			}
			else
				SubType = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Version = FValue.trim();
			}
			else
				Version = null;
		}
		if (FCode.equalsIgnoreCase("CtrlType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlType = FValue.trim();
			}
			else
				CtrlType = null;
		}
		if (FCode.equalsIgnoreCase("CtrlTop"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlTop = i;
			}
		}
		if (FCode.equalsIgnoreCase("CtrlLeft"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlLeft = i;
			}
		}
		if (FCode.equalsIgnoreCase("CtrlHeight"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlHeight = i;
			}
		}
		if (FCode.equalsIgnoreCase("CtrlWidth"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				CtrlWidth = i;
			}
		}
		if (FCode.equalsIgnoreCase("CtrlVisible"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlVisible = FValue.trim();
			}
			else
				CtrlVisible = null;
		}
		if (FCode.equalsIgnoreCase("CtrlTitle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlTitle = FValue.trim();
			}
			else
				CtrlTitle = null;
		}
		if (FCode.equalsIgnoreCase("CtrlForm"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlForm = FValue.trim();
			}
			else
				CtrlForm = null;
		}
		if (FCode.equalsIgnoreCase("ListCodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ListCodeType = FValue.trim();
			}
			else
				ListCodeType = null;
		}
		if (FCode.equalsIgnoreCase("CtrlDefaultValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CtrlDefaultValue = FValue.trim();
			}
			else
				CtrlDefaultValue = null;
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
		if (FCode.equalsIgnoreCase("ValidFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ValidFlag = FValue.trim();
			}
			else
				ValidFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ES_PROPERTY_DEFSchema other = (ES_PROPERTY_DEFSchema)otherObject;
		return
			DefCode.equals(other.getDefCode())
			&& PropField.equals(other.getPropField())
			&& PropCode.equals(other.getPropCode())
			&& PropName.equals(other.getPropName())
			&& FieldOrder == other.getFieldOrder()
			&& BussType.equals(other.getBussType())
			&& SubType.equals(other.getSubType())
			&& Version.equals(other.getVersion())
			&& CtrlType.equals(other.getCtrlType())
			&& CtrlTop == other.getCtrlTop()
			&& CtrlLeft == other.getCtrlLeft()
			&& CtrlHeight == other.getCtrlHeight()
			&& CtrlWidth == other.getCtrlWidth()
			&& CtrlVisible.equals(other.getCtrlVisible())
			&& CtrlTitle.equals(other.getCtrlTitle())
			&& CtrlForm.equals(other.getCtrlForm())
			&& ListCodeType.equals(other.getListCodeType())
			&& CtrlDefaultValue.equals(other.getCtrlDefaultValue())
			&& Remark.equals(other.getRemark())
			&& ValidFlag.equals(other.getValidFlag());
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
		if( strFieldName.equals("DefCode") ) {
			return 0;
		}
		if( strFieldName.equals("PropField") ) {
			return 1;
		}
		if( strFieldName.equals("PropCode") ) {
			return 2;
		}
		if( strFieldName.equals("PropName") ) {
			return 3;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return 4;
		}
		if( strFieldName.equals("BussType") ) {
			return 5;
		}
		if( strFieldName.equals("SubType") ) {
			return 6;
		}
		if( strFieldName.equals("Version") ) {
			return 7;
		}
		if( strFieldName.equals("CtrlType") ) {
			return 8;
		}
		if( strFieldName.equals("CtrlTop") ) {
			return 9;
		}
		if( strFieldName.equals("CtrlLeft") ) {
			return 10;
		}
		if( strFieldName.equals("CtrlHeight") ) {
			return 11;
		}
		if( strFieldName.equals("CtrlWidth") ) {
			return 12;
		}
		if( strFieldName.equals("CtrlVisible") ) {
			return 13;
		}
		if( strFieldName.equals("CtrlTitle") ) {
			return 14;
		}
		if( strFieldName.equals("CtrlForm") ) {
			return 15;
		}
		if( strFieldName.equals("ListCodeType") ) {
			return 16;
		}
		if( strFieldName.equals("CtrlDefaultValue") ) {
			return 17;
		}
		if( strFieldName.equals("Remark") ) {
			return 18;
		}
		if( strFieldName.equals("ValidFlag") ) {
			return 19;
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
				strFieldName = "DefCode";
				break;
			case 1:
				strFieldName = "PropField";
				break;
			case 2:
				strFieldName = "PropCode";
				break;
			case 3:
				strFieldName = "PropName";
				break;
			case 4:
				strFieldName = "FieldOrder";
				break;
			case 5:
				strFieldName = "BussType";
				break;
			case 6:
				strFieldName = "SubType";
				break;
			case 7:
				strFieldName = "Version";
				break;
			case 8:
				strFieldName = "CtrlType";
				break;
			case 9:
				strFieldName = "CtrlTop";
				break;
			case 10:
				strFieldName = "CtrlLeft";
				break;
			case 11:
				strFieldName = "CtrlHeight";
				break;
			case 12:
				strFieldName = "CtrlWidth";
				break;
			case 13:
				strFieldName = "CtrlVisible";
				break;
			case 14:
				strFieldName = "CtrlTitle";
				break;
			case 15:
				strFieldName = "CtrlForm";
				break;
			case 16:
				strFieldName = "ListCodeType";
				break;
			case 17:
				strFieldName = "CtrlDefaultValue";
				break;
			case 18:
				strFieldName = "Remark";
				break;
			case 19:
				strFieldName = "ValidFlag";
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
		if( strFieldName.equals("DefCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropField") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PropName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FieldOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlTop") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CtrlLeft") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CtrlHeight") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CtrlWidth") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CtrlVisible") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlTitle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlForm") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ListCodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CtrlDefaultValue") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ValidFlag") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
