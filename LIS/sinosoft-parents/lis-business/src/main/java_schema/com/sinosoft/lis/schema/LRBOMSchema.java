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
import com.sinosoft.lis.db.LRBOMDB;

/*
 * <p>ClassName: LRBOMSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎
 */
public class LRBOMSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRBOMSchema.class);

	// @Field
	/** Bom英文名 */
	private String Name;
	/** Bom的中文名 */
	private String CNName;
	/** 父bom */
	private String FBOM;
	/** 本bom字段 */
	private String LocalItem;
	/** 父bom字段 */
	private String FatherItem;
	/** Bom层次 */
	private String BOMLevel;
	/** 业务模块 */
	private String Business;
	/** Bom描述信息 */
	private String Discription;
	/** Bom对应的class名称 */
	private String Source;
	/** 有效性 */
	private String Valid;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRBOMSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Name";

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
		LRBOMSchema cloned = (LRBOMSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		Name = aName;
	}
	public String getCNName()
	{
		return CNName;
	}
	public void setCNName(String aCNName)
	{
		CNName = aCNName;
	}
	/**
	* 0——此词条是一个BOM<p>
	* 1——此词条是叶子词条
	*/
	public String getFBOM()
	{
		return FBOM;
	}
	public void setFBOM(String aFBOM)
	{
		FBOM = aFBOM;
	}
	public String getLocalItem()
	{
		return LocalItem;
	}
	public void setLocalItem(String aLocalItem)
	{
		LocalItem = aLocalItem;
	}
	public String getFatherItem()
	{
		return FatherItem;
	}
	public void setFatherItem(String aFatherItem)
	{
		FatherItem = aFatherItem;
	}
	public String getBOMLevel()
	{
		return BOMLevel;
	}
	public void setBOMLevel(String aBOMLevel)
	{
		BOMLevel = aBOMLevel;
	}
	/**
	* 0——公共<p>
	* 1-契约自核规则<p>
	* 2-保全自核规则,<p>
	* 3-理赔自核规则,<p>
	* 4- 续期续保核保规则,<p>
	* 5-销售管理模块
	*/
	public String getBusiness()
	{
		return Business;
	}
	public void setBusiness(String aBusiness)
	{
		Business = aBusiness;
	}
	public String getDiscription()
	{
		return Discription;
	}
	public void setDiscription(String aDiscription)
	{
		Discription = aDiscription;
	}
	public String getSource()
	{
		return Source;
	}
	public void setSource(String aSource)
	{
		Source = aSource;
	}
	public String getValid()
	{
		return Valid;
	}
	public void setValid(String aValid)
	{
		Valid = aValid;
	}

	/**
	* 使用另外一个 LRBOMSchema 对象给 Schema 赋值
	* @param: aLRBOMSchema LRBOMSchema
	**/
	public void setSchema(LRBOMSchema aLRBOMSchema)
	{
		this.Name = aLRBOMSchema.getName();
		this.CNName = aLRBOMSchema.getCNName();
		this.FBOM = aLRBOMSchema.getFBOM();
		this.LocalItem = aLRBOMSchema.getLocalItem();
		this.FatherItem = aLRBOMSchema.getFatherItem();
		this.BOMLevel = aLRBOMSchema.getBOMLevel();
		this.Business = aLRBOMSchema.getBusiness();
		this.Discription = aLRBOMSchema.getDiscription();
		this.Source = aLRBOMSchema.getSource();
		this.Valid = aLRBOMSchema.getValid();
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
			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("CNName") == null )
				this.CNName = null;
			else
				this.CNName = rs.getString("CNName").trim();

			if( rs.getString("FBOM") == null )
				this.FBOM = null;
			else
				this.FBOM = rs.getString("FBOM").trim();

			if( rs.getString("LocalItem") == null )
				this.LocalItem = null;
			else
				this.LocalItem = rs.getString("LocalItem").trim();

			if( rs.getString("FatherItem") == null )
				this.FatherItem = null;
			else
				this.FatherItem = rs.getString("FatherItem").trim();

			if( rs.getString("BOMLevel") == null )
				this.BOMLevel = null;
			else
				this.BOMLevel = rs.getString("BOMLevel").trim();

			if( rs.getString("Business") == null )
				this.Business = null;
			else
				this.Business = rs.getString("Business").trim();

			if( rs.getString("Discription") == null )
				this.Discription = null;
			else
				this.Discription = rs.getString("Discription").trim();

			if( rs.getString("Source") == null )
				this.Source = null;
			else
				this.Source = rs.getString("Source").trim();

			if( rs.getString("Valid") == null )
				this.Valid = null;
			else
				this.Valid = rs.getString("Valid").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRBOM表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRBOMSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRBOMSchema getSchema()
	{
		LRBOMSchema aLRBOMSchema = new LRBOMSchema();
		aLRBOMSchema.setSchema(this);
		return aLRBOMSchema;
	}

	public LRBOMDB getDB()
	{
		LRBOMDB aDBOper = new LRBOMDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRBOM描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CNName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FBOM)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LocalItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FatherItem)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BOMLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Business)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Discription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Source)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Valid));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRBOM>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CNName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FBOM = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			LocalItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FatherItem = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BOMLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Business = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Discription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Source = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Valid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRBOMSchema";
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("CNName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CNName));
		}
		if (FCode.equalsIgnoreCase("FBOM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FBOM));
		}
		if (FCode.equalsIgnoreCase("LocalItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LocalItem));
		}
		if (FCode.equalsIgnoreCase("FatherItem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FatherItem));
		}
		if (FCode.equalsIgnoreCase("BOMLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BOMLevel));
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Business));
		}
		if (FCode.equalsIgnoreCase("Discription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Discription));
		}
		if (FCode.equalsIgnoreCase("Source"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Source));
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Valid));
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
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CNName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FBOM);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(LocalItem);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FatherItem);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BOMLevel);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Business);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Discription);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Source);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Valid);
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

		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equalsIgnoreCase("CNName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CNName = FValue.trim();
			}
			else
				CNName = null;
		}
		if (FCode.equalsIgnoreCase("FBOM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FBOM = FValue.trim();
			}
			else
				FBOM = null;
		}
		if (FCode.equalsIgnoreCase("LocalItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LocalItem = FValue.trim();
			}
			else
				LocalItem = null;
		}
		if (FCode.equalsIgnoreCase("FatherItem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FatherItem = FValue.trim();
			}
			else
				FatherItem = null;
		}
		if (FCode.equalsIgnoreCase("BOMLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BOMLevel = FValue.trim();
			}
			else
				BOMLevel = null;
		}
		if (FCode.equalsIgnoreCase("Business"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Business = FValue.trim();
			}
			else
				Business = null;
		}
		if (FCode.equalsIgnoreCase("Discription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Discription = FValue.trim();
			}
			else
				Discription = null;
		}
		if (FCode.equalsIgnoreCase("Source"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Source = FValue.trim();
			}
			else
				Source = null;
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Valid = FValue.trim();
			}
			else
				Valid = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRBOMSchema other = (LRBOMSchema)otherObject;
		return
			Name.equals(other.getName())
			&& CNName.equals(other.getCNName())
			&& FBOM.equals(other.getFBOM())
			&& LocalItem.equals(other.getLocalItem())
			&& FatherItem.equals(other.getFatherItem())
			&& BOMLevel.equals(other.getBOMLevel())
			&& Business.equals(other.getBusiness())
			&& Discription.equals(other.getDiscription())
			&& Source.equals(other.getSource())
			&& Valid.equals(other.getValid());
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
		if( strFieldName.equals("Name") ) {
			return 0;
		}
		if( strFieldName.equals("CNName") ) {
			return 1;
		}
		if( strFieldName.equals("FBOM") ) {
			return 2;
		}
		if( strFieldName.equals("LocalItem") ) {
			return 3;
		}
		if( strFieldName.equals("FatherItem") ) {
			return 4;
		}
		if( strFieldName.equals("BOMLevel") ) {
			return 5;
		}
		if( strFieldName.equals("Business") ) {
			return 6;
		}
		if( strFieldName.equals("Discription") ) {
			return 7;
		}
		if( strFieldName.equals("Source") ) {
			return 8;
		}
		if( strFieldName.equals("Valid") ) {
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
				strFieldName = "Name";
				break;
			case 1:
				strFieldName = "CNName";
				break;
			case 2:
				strFieldName = "FBOM";
				break;
			case 3:
				strFieldName = "LocalItem";
				break;
			case 4:
				strFieldName = "FatherItem";
				break;
			case 5:
				strFieldName = "BOMLevel";
				break;
			case 6:
				strFieldName = "Business";
				break;
			case 7:
				strFieldName = "Discription";
				break;
			case 8:
				strFieldName = "Source";
				break;
			case 9:
				strFieldName = "Valid";
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
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CNName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FBOM") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LocalItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FatherItem") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BOMLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Business") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Discription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Source") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Valid") ) {
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
