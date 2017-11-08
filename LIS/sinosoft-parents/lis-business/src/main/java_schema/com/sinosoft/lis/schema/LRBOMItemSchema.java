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
import com.sinosoft.lis.db.LRBOMItemDB;

/*
 * <p>ClassName: LRBOMItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 规则引擎
 */
public class LRBOMItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRBOMItemSchema.class);

	// @Field
	/** 词条英文名 */
	private String Name;
	/** Bom */
	private String BOMName;
	/** 词条中文名 */
	private String CNName;
	/** 连接词 */
	private String Connector;
	/** 是否层次型数据 */
	private String IsHierarchical;
	/** 是否基础词条 */
	private String IsBase;
	/** 基础数据取值类型 */
	private String SourceType;
	/** 基础数据取值来源 */
	private String Source;
	/** 词条数据类型 */
	private String CommandType;
	/** 词条预校验 */
	private String PreCheck;
	/** 有效性 */
	private String Valid;
	/** 词条描述 */
	private String Description;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRBOMItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Name";
		pk[1] = "BOMName";

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
		LRBOMItemSchema cloned = (LRBOMItemSchema)super.clone();
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
	public String getBOMName()
	{
		return BOMName;
	}
	public void setBOMName(String aBOMName)
	{
		BOMName = aBOMName;
	}
	public String getCNName()
	{
		return CNName;
	}
	public void setCNName(String aCNName)
	{
		CNName = aCNName;
	}
	public String getConnector()
	{
		return Connector;
	}
	public void setConnector(String aConnector)
	{
		Connector = aConnector;
	}
	/**
	* 0----否，正常数据结构<p>
	* 1----是，层次型数据结构
	*/
	public String getIsHierarchical()
	{
		return IsHierarchical;
	}
	public void setIsHierarchical(String aIsHierarchical)
	{
		IsHierarchical = aIsHierarchical;
	}
	/**
	* 0———基础词条，基础数据，定义界面时需要设值<p>
	* 1——业务系统传入数据，定义界面不需要设值
	*/
	public String getIsBase()
	{
		return IsBase;
	}
	public void setIsBase(String aIsBase)
	{
		IsBase = aIsBase;
	}
	/**
	* SQL<p>
	* 或者<p>
	* java
	*/
	public String getSourceType()
	{
		return SourceType;
	}
	public void setSourceType(String aSourceType)
	{
		SourceType = aSourceType;
	}
	/**
	* SQL或者java
	*/
	public String getSource()
	{
		return Source;
	}
	public void setSource(String aSource)
	{
		Source = aSource;
	}
	/**
	* String-字符型<p>
	* Number-数字型<p>
	* Date-日期型 yyyy-mm-dd<p>
	* Time-时间型 HH24:MI:SS<p>
	* Boolean-布尔值：1代表真；0代表假
	*/
	public String getCommandType()
	{
		return CommandType;
	}
	public void setCommandType(String aCommandType)
	{
		CommandType = aCommandType;
	}
	/**
	* 生成的java对象中，对对象初始值的预校验
	*/
	public String getPreCheck()
	{
		return PreCheck;
	}
	public void setPreCheck(String aPreCheck)
	{
		PreCheck = aPreCheck;
	}
	public String getValid()
	{
		return Valid;
	}
	public void setValid(String aValid)
	{
		Valid = aValid;
	}
	public String getDescription()
	{
		return Description;
	}
	public void setDescription(String aDescription)
	{
		Description = aDescription;
	}

	/**
	* 使用另外一个 LRBOMItemSchema 对象给 Schema 赋值
	* @param: aLRBOMItemSchema LRBOMItemSchema
	**/
	public void setSchema(LRBOMItemSchema aLRBOMItemSchema)
	{
		this.Name = aLRBOMItemSchema.getName();
		this.BOMName = aLRBOMItemSchema.getBOMName();
		this.CNName = aLRBOMItemSchema.getCNName();
		this.Connector = aLRBOMItemSchema.getConnector();
		this.IsHierarchical = aLRBOMItemSchema.getIsHierarchical();
		this.IsBase = aLRBOMItemSchema.getIsBase();
		this.SourceType = aLRBOMItemSchema.getSourceType();
		this.Source = aLRBOMItemSchema.getSource();
		this.CommandType = aLRBOMItemSchema.getCommandType();
		this.PreCheck = aLRBOMItemSchema.getPreCheck();
		this.Valid = aLRBOMItemSchema.getValid();
		this.Description = aLRBOMItemSchema.getDescription();
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

			if( rs.getString("BOMName") == null )
				this.BOMName = null;
			else
				this.BOMName = rs.getString("BOMName").trim();

			if( rs.getString("CNName") == null )
				this.CNName = null;
			else
				this.CNName = rs.getString("CNName").trim();

			if( rs.getString("Connector") == null )
				this.Connector = null;
			else
				this.Connector = rs.getString("Connector").trim();

			if( rs.getString("IsHierarchical") == null )
				this.IsHierarchical = null;
			else
				this.IsHierarchical = rs.getString("IsHierarchical").trim();

			if( rs.getString("IsBase") == null )
				this.IsBase = null;
			else
				this.IsBase = rs.getString("IsBase").trim();

			if( rs.getString("SourceType") == null )
				this.SourceType = null;
			else
				this.SourceType = rs.getString("SourceType").trim();

			if( rs.getString("Source") == null )
				this.Source = null;
			else
				this.Source = rs.getString("Source").trim();

			if( rs.getString("CommandType") == null )
				this.CommandType = null;
			else
				this.CommandType = rs.getString("CommandType").trim();

			if( rs.getString("PreCheck") == null )
				this.PreCheck = null;
			else
				this.PreCheck = rs.getString("PreCheck").trim();

			if( rs.getString("Valid") == null )
				this.Valid = null;
			else
				this.Valid = rs.getString("Valid").trim();

			if( rs.getString("Description") == null )
				this.Description = null;
			else
				this.Description = rs.getString("Description").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRBOMItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRBOMItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRBOMItemSchema getSchema()
	{
		LRBOMItemSchema aLRBOMItemSchema = new LRBOMItemSchema();
		aLRBOMItemSchema.setSchema(this);
		return aLRBOMItemSchema;
	}

	public LRBOMItemDB getDB()
	{
		LRBOMItemDB aDBOper = new LRBOMItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRBOMItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BOMName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CNName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Connector)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsHierarchical)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsBase)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SourceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Source)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CommandType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PreCheck)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Valid)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Description));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRBOMItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BOMName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CNName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Connector = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			IsHierarchical = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			IsBase = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			SourceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Source = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			CommandType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PreCheck = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Valid = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Description = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRBOMItemSchema";
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
		if (FCode.equalsIgnoreCase("BOMName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BOMName));
		}
		if (FCode.equalsIgnoreCase("CNName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CNName));
		}
		if (FCode.equalsIgnoreCase("Connector"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Connector));
		}
		if (FCode.equalsIgnoreCase("IsHierarchical"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsHierarchical));
		}
		if (FCode.equalsIgnoreCase("IsBase"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsBase));
		}
		if (FCode.equalsIgnoreCase("SourceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SourceType));
		}
		if (FCode.equalsIgnoreCase("Source"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Source));
		}
		if (FCode.equalsIgnoreCase("CommandType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CommandType));
		}
		if (FCode.equalsIgnoreCase("PreCheck"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PreCheck));
		}
		if (FCode.equalsIgnoreCase("Valid"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Valid));
		}
		if (FCode.equalsIgnoreCase("Description"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Description));
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
				strFieldValue = StrTool.GBKToUnicode(BOMName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CNName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Connector);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IsHierarchical);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(IsBase);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(SourceType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Source);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(CommandType);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PreCheck);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Valid);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Description);
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
		if (FCode.equalsIgnoreCase("BOMName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BOMName = FValue.trim();
			}
			else
				BOMName = null;
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
		if (FCode.equalsIgnoreCase("Connector"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Connector = FValue.trim();
			}
			else
				Connector = null;
		}
		if (FCode.equalsIgnoreCase("IsHierarchical"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsHierarchical = FValue.trim();
			}
			else
				IsHierarchical = null;
		}
		if (FCode.equalsIgnoreCase("IsBase"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsBase = FValue.trim();
			}
			else
				IsBase = null;
		}
		if (FCode.equalsIgnoreCase("SourceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SourceType = FValue.trim();
			}
			else
				SourceType = null;
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
		if (FCode.equalsIgnoreCase("CommandType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CommandType = FValue.trim();
			}
			else
				CommandType = null;
		}
		if (FCode.equalsIgnoreCase("PreCheck"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PreCheck = FValue.trim();
			}
			else
				PreCheck = null;
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
		if (FCode.equalsIgnoreCase("Description"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Description = FValue.trim();
			}
			else
				Description = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRBOMItemSchema other = (LRBOMItemSchema)otherObject;
		return
			Name.equals(other.getName())
			&& BOMName.equals(other.getBOMName())
			&& CNName.equals(other.getCNName())
			&& Connector.equals(other.getConnector())
			&& IsHierarchical.equals(other.getIsHierarchical())
			&& IsBase.equals(other.getIsBase())
			&& SourceType.equals(other.getSourceType())
			&& Source.equals(other.getSource())
			&& CommandType.equals(other.getCommandType())
			&& PreCheck.equals(other.getPreCheck())
			&& Valid.equals(other.getValid())
			&& Description.equals(other.getDescription());
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
		if( strFieldName.equals("BOMName") ) {
			return 1;
		}
		if( strFieldName.equals("CNName") ) {
			return 2;
		}
		if( strFieldName.equals("Connector") ) {
			return 3;
		}
		if( strFieldName.equals("IsHierarchical") ) {
			return 4;
		}
		if( strFieldName.equals("IsBase") ) {
			return 5;
		}
		if( strFieldName.equals("SourceType") ) {
			return 6;
		}
		if( strFieldName.equals("Source") ) {
			return 7;
		}
		if( strFieldName.equals("CommandType") ) {
			return 8;
		}
		if( strFieldName.equals("PreCheck") ) {
			return 9;
		}
		if( strFieldName.equals("Valid") ) {
			return 10;
		}
		if( strFieldName.equals("Description") ) {
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
				strFieldName = "Name";
				break;
			case 1:
				strFieldName = "BOMName";
				break;
			case 2:
				strFieldName = "CNName";
				break;
			case 3:
				strFieldName = "Connector";
				break;
			case 4:
				strFieldName = "IsHierarchical";
				break;
			case 5:
				strFieldName = "IsBase";
				break;
			case 6:
				strFieldName = "SourceType";
				break;
			case 7:
				strFieldName = "Source";
				break;
			case 8:
				strFieldName = "CommandType";
				break;
			case 9:
				strFieldName = "PreCheck";
				break;
			case 10:
				strFieldName = "Valid";
				break;
			case 11:
				strFieldName = "Description";
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
		if( strFieldName.equals("BOMName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CNName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Connector") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsHierarchical") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IsBase") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SourceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Source") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CommandType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PreCheck") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Valid") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Description") ) {
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
