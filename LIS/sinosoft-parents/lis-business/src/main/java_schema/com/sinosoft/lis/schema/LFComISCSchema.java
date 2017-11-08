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
import com.sinosoft.lis.db.LFComISCDB;

/*
 * <p>ClassName: LFComISCSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LFComISCSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LFComISCSchema.class);
	// @Field
	/** 保监会机构编码 */
	private String ComCodeISC;
	/** 机构名称 */
	private String Name;
	/** 上级机构编码 */
	private String ParentComCodeISC;
	/** 机构层次 */
	private int ComLevel;
	/** 是否是叶子结点 */
	private String IsLeaf;
	/** 是否上报标志 */
	private String OutputFlag;

	public static final int FIELDNUM = 6;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LFComISCSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ComCodeISC";

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
		LFComISCSchema cloned = (LFComISCSchema)super.clone();
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
	public String getName()
	{
		return Name;
	}
	public void setName(String aName)
	{
		if(aName!=null && aName.length()>60)
			throw new IllegalArgumentException("机构名称Name值"+aName+"的长度"+aName.length()+"大于最大值60");
		Name = aName;
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
	* 记录该机构是属于哪级机构，总公司级别为1<p>
	* 分公司级别为2，地市公司级别为3。
	*/
	public int getComLevel()
	{
		return ComLevel;
	}
	public void setComLevel(int aComLevel)
	{
		ComLevel = aComLevel;
	}
	public void setComLevel(String aComLevel)
	{
		if (aComLevel != null && !aComLevel.equals(""))
		{
			Integer tInteger = new Integer(aComLevel);
			int i = tInteger.intValue();
			ComLevel = i;
		}
	}

	/**
	* 0--不是<p>
	* 1--是
	*/
	public String getIsLeaf()
	{
		return IsLeaf;
	}
	public void setIsLeaf(String aIsLeaf)
	{
		if(aIsLeaf!=null && aIsLeaf.length()>1)
			throw new IllegalArgumentException("是否是叶子结点IsLeaf值"+aIsLeaf+"的长度"+aIsLeaf.length()+"大于最大值1");
		IsLeaf = aIsLeaf;
	}
	public String getOutputFlag()
	{
		return OutputFlag;
	}
	public void setOutputFlag(String aOutputFlag)
	{
		if(aOutputFlag!=null && aOutputFlag.length()>1)
			throw new IllegalArgumentException("是否上报标志OutputFlag值"+aOutputFlag+"的长度"+aOutputFlag.length()+"大于最大值1");
		OutputFlag = aOutputFlag;
	}

	/**
	* 使用另外一个 LFComISCSchema 对象给 Schema 赋值
	* @param: aLFComISCSchema LFComISCSchema
	**/
	public void setSchema(LFComISCSchema aLFComISCSchema)
	{
		this.ComCodeISC = aLFComISCSchema.getComCodeISC();
		this.Name = aLFComISCSchema.getName();
		this.ParentComCodeISC = aLFComISCSchema.getParentComCodeISC();
		this.ComLevel = aLFComISCSchema.getComLevel();
		this.IsLeaf = aLFComISCSchema.getIsLeaf();
		this.OutputFlag = aLFComISCSchema.getOutputFlag();
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

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ParentComCodeISC") == null )
				this.ParentComCodeISC = null;
			else
				this.ParentComCodeISC = rs.getString("ParentComCodeISC").trim();

			this.ComLevel = rs.getInt("ComLevel");
			if( rs.getString("IsLeaf") == null )
				this.IsLeaf = null;
			else
				this.IsLeaf = rs.getString("IsLeaf").trim();

			if( rs.getString("OutputFlag") == null )
				this.OutputFlag = null;
			else
				this.OutputFlag = rs.getString("OutputFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LFComISC表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFComISCSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LFComISCSchema getSchema()
	{
		LFComISCSchema aLFComISCSchema = new LFComISCSchema();
		aLFComISCSchema.setSchema(this);
		return aLFComISCSchema;
	}

	public LFComISCDB getDB()
	{
		LFComISCDB aDBOper = new LFComISCDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFComISC描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ComCodeISC)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Name)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParentComCodeISC)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ComLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IsLeaf)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OutputFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLFComISC>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComCodeISC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ParentComCodeISC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ComLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			IsLeaf = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			OutputFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LFComISCSchema";
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equalsIgnoreCase("ParentComCodeISC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentComCodeISC));
		}
		if (FCode.equalsIgnoreCase("ComLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComLevel));
		}
		if (FCode.equalsIgnoreCase("IsLeaf"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IsLeaf));
		}
		if (FCode.equalsIgnoreCase("OutputFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutputFlag));
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
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParentComCodeISC);
				break;
			case 3:
				strFieldValue = String.valueOf(ComLevel);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(IsLeaf);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OutputFlag);
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
		if (FCode.equalsIgnoreCase("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
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
		if (FCode.equalsIgnoreCase("ComLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ComLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("IsLeaf"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IsLeaf = FValue.trim();
			}
			else
				IsLeaf = null;
		}
		if (FCode.equalsIgnoreCase("OutputFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutputFlag = FValue.trim();
			}
			else
				OutputFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LFComISCSchema other = (LFComISCSchema)otherObject;
		return
			ComCodeISC.equals(other.getComCodeISC())
			&& Name.equals(other.getName())
			&& ParentComCodeISC.equals(other.getParentComCodeISC())
			&& ComLevel == other.getComLevel()
			&& IsLeaf.equals(other.getIsLeaf())
			&& OutputFlag.equals(other.getOutputFlag());
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
		if( strFieldName.equals("Name") ) {
			return 1;
		}
		if( strFieldName.equals("ParentComCodeISC") ) {
			return 2;
		}
		if( strFieldName.equals("ComLevel") ) {
			return 3;
		}
		if( strFieldName.equals("IsLeaf") ) {
			return 4;
		}
		if( strFieldName.equals("OutputFlag") ) {
			return 5;
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
				strFieldName = "Name";
				break;
			case 2:
				strFieldName = "ParentComCodeISC";
				break;
			case 3:
				strFieldName = "ComLevel";
				break;
			case 4:
				strFieldName = "IsLeaf";
				break;
			case 5:
				strFieldName = "OutputFlag";
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
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParentComCodeISC") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("IsLeaf") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutputFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
