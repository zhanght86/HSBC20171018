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
import com.sinosoft.lis.db.LRRuleDataDB;

/*
 * <p>ClassName: LRRuleDataSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 */
public class LRRuleDataSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LRRuleDataSchema.class);
	// @Field
	/** 模板号 */
	private String Id;
	/** 版本号 */
	private int Version;
	/** 创建人 */
	private String Creator;
	/** 入机时间 */
	private String MakeTime;
	/** 入机日期 */
	private Date MakeDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 数据表列名 */
	private String ColumnNames;
	/** 数据表列类型 */
	private String ColumnTypes;
	/** Ruledatasql */
	private String RuleDataSQL;
	/** 规则数据 */
	private InputStream RuleData;

	public static final int FIELDNUM = 11;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LRRuleDataSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "Id";

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
		LRRuleDataSchema cloned = (LRRuleDataSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getId()
	{
		return Id;
	}
	public void setId(String aId)
	{
		if(aId!=null && aId.length()>20)
			throw new IllegalArgumentException("模板号Id值"+aId+"的长度"+aId.length()+"大于最大值20");
		Id = aId;
	}
	public int getVersion()
	{
		return Version;
	}
	public void setVersion(int aVersion)
	{
		Version = aVersion;
	}
	public void setVersion(String aVersion)
	{
		if (aVersion != null && !aVersion.equals(""))
		{
			Integer tInteger = new Integer(aVersion);
			int i = tInteger.intValue();
			Version = i;
		}
	}

	public String getCreator()
	{
		return Creator;
	}
	public void setCreator(String aCreator)
	{
		if(aCreator!=null && aCreator.length()>80)
			throw new IllegalArgumentException("创建人Creator值"+aCreator+"的长度"+aCreator.length()+"大于最大值80");
		Creator = aCreator;
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
	/**
	* 纪录产生该纪录的操作员。
	*/
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
	* 纪录最后一次修改的日期。
	*/
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

	public String getColumnNames()
	{
		return ColumnNames;
	}
	public void setColumnNames(String aColumnNames)
	{
		if(aColumnNames!=null && aColumnNames.length()>1000)
			throw new IllegalArgumentException("数据表列名ColumnNames值"+aColumnNames+"的长度"+aColumnNames.length()+"大于最大值1000");
		ColumnNames = aColumnNames;
	}
	public String getColumnTypes()
	{
		return ColumnTypes;
	}
	public void setColumnTypes(String aColumnTypes)
	{
		if(aColumnTypes!=null && aColumnTypes.length()>1000)
			throw new IllegalArgumentException("数据表列类型ColumnTypes值"+aColumnTypes+"的长度"+aColumnTypes.length()+"大于最大值1000");
		ColumnTypes = aColumnTypes;
	}
	/**
	* 列出所有的参数  和  运算符号<p>
	* 用来进行界面展���<p>
	* <p>
	* A.a>;B.b=
	*/
	public String getRuleDataSQL()
	{
		return RuleDataSQL;
	}
	public void setRuleDataSQL(String aRuleDataSQL)
	{
		RuleDataSQL = aRuleDataSQL;
	}
	public InputStream getRuleData()
	{
		return RuleData;
	}
	public void setRuleData(InputStream aRuleData)
	{
		RuleData = aRuleData;
	}
	public void setRuleData(String aRuleData)
	{
	}


	/**
	* 使用另外一个 LRRuleDataSchema 对象给 Schema 赋值
	* @param: aLRRuleDataSchema LRRuleDataSchema
	**/
	public void setSchema(LRRuleDataSchema aLRRuleDataSchema)
	{
		this.Id = aLRRuleDataSchema.getId();
		this.Version = aLRRuleDataSchema.getVersion();
		this.Creator = aLRRuleDataSchema.getCreator();
		this.MakeTime = aLRRuleDataSchema.getMakeTime();
		this.MakeDate = fDate.getDate( aLRRuleDataSchema.getMakeDate());
		this.ModifyTime = aLRRuleDataSchema.getModifyTime();
		this.ModifyDate = fDate.getDate( aLRRuleDataSchema.getModifyDate());
		this.ColumnNames = aLRRuleDataSchema.getColumnNames();
		this.ColumnTypes = aLRRuleDataSchema.getColumnTypes();
		this.RuleDataSQL = aLRRuleDataSchema.getRuleDataSQL();
		this.RuleData = aLRRuleDataSchema.getRuleData();
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
			if( rs.getString("Id") == null )
				this.Id = null;
			else
				this.Id = rs.getString("Id").trim();

			this.Version = rs.getInt("Version");
			if( rs.getString("Creator") == null )
				this.Creator = null;
			else
				this.Creator = rs.getString("Creator").trim();

			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ColumnNames") == null )
				this.ColumnNames = null;
			else
				this.ColumnNames = rs.getString("ColumnNames").trim();

			if( rs.getString("ColumnTypes") == null )
				this.ColumnTypes = null;
			else
				this.ColumnTypes = rs.getString("ColumnTypes").trim();

			if( rs.getString("RuleDataSQL") == null )
				this.RuleDataSQL = null;
			else
				this.RuleDataSQL = rs.getString("RuleDataSQL").trim();

			this.RuleData = rs.getBinaryStream("RuleData");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LRRuleData表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRuleDataSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LRRuleDataSchema getSchema()
	{
		LRRuleDataSchema aLRRuleDataSchema = new LRRuleDataSchema();
		aLRRuleDataSchema.setSchema(this);
		return aLRRuleDataSchema;
	}

	public LRRuleDataDB getDB()
	{
		LRRuleDataDB aDBOper = new LRRuleDataDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRuleData描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Id)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Version));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Creator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColumnNames)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ColumnTypes)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDataSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( 1 );
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLRRuleData>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Id = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			Version= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			Creator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			ColumnNames = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ColumnTypes = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RuleDataSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LRRuleDataSchema";
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
		if (FCode.equalsIgnoreCase("Id"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Id));
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Version));
		}
		if (FCode.equalsIgnoreCase("Creator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Creator));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equalsIgnoreCase("ColumnNames"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnNames));
		}
		if (FCode.equalsIgnoreCase("ColumnTypes"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ColumnTypes));
		}
		if (FCode.equalsIgnoreCase("RuleDataSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDataSQL));
		}
		if (FCode.equalsIgnoreCase("RuleData"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleData));
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
				strFieldValue = StrTool.GBKToUnicode(Id);
				break;
			case 1:
				strFieldValue = String.valueOf(Version);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Creator);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ColumnNames);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ColumnTypes);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RuleDataSQL);
				break;
			case 10:
				strFieldValue = String.valueOf(RuleData);
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

		if (FCode.equalsIgnoreCase("Id"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Id = FValue.trim();
			}
			else
				Id = null;
		}
		if (FCode.equalsIgnoreCase("Version"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Version = i;
			}
		}
		if (FCode.equalsIgnoreCase("Creator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Creator = FValue.trim();
			}
			else
				Creator = null;
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
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
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
		if (FCode.equalsIgnoreCase("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equalsIgnoreCase("ColumnNames"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColumnNames = FValue.trim();
			}
			else
				ColumnNames = null;
		}
		if (FCode.equalsIgnoreCase("ColumnTypes"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ColumnTypes = FValue.trim();
			}
			else
				ColumnTypes = null;
		}
		if (FCode.equalsIgnoreCase("RuleDataSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDataSQL = FValue.trim();
			}
			else
				RuleDataSQL = null;
		}
		if (FCode.equalsIgnoreCase("RuleData"))
		{
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LRRuleDataSchema other = (LRRuleDataSchema)otherObject;
		return
			Id.equals(other.getId())
			&& Version == other.getVersion()
			&& Creator.equals(other.getCreator())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ColumnNames.equals(other.getColumnNames())
			&& ColumnTypes.equals(other.getColumnTypes())
			&& RuleDataSQL.equals(other.getRuleDataSQL())
			;
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
		if( strFieldName.equals("Id") ) {
			return 0;
		}
		if( strFieldName.equals("Version") ) {
			return 1;
		}
		if( strFieldName.equals("Creator") ) {
			return 2;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 3;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 4;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 5;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 6;
		}
		if( strFieldName.equals("ColumnNames") ) {
			return 7;
		}
		if( strFieldName.equals("ColumnTypes") ) {
			return 8;
		}
		if( strFieldName.equals("RuleDataSQL") ) {
			return 9;
		}
		if( strFieldName.equals("RuleData") ) {
			return 10;
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
				strFieldName = "Id";
				break;
			case 1:
				strFieldName = "Version";
				break;
			case 2:
				strFieldName = "Creator";
				break;
			case 3:
				strFieldName = "MakeTime";
				break;
			case 4:
				strFieldName = "MakeDate";
				break;
			case 5:
				strFieldName = "ModifyTime";
				break;
			case 6:
				strFieldName = "ModifyDate";
				break;
			case 7:
				strFieldName = "ColumnNames";
				break;
			case 8:
				strFieldName = "ColumnTypes";
				break;
			case 9:
				strFieldName = "RuleDataSQL";
				break;
			case 10:
				strFieldName = "RuleData";
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
		if( strFieldName.equals("Id") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Version") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Creator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ColumnNames") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ColumnTypes") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDataSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleData") ) {
			return Schema.TYPE_BLOB;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_BLOB;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
