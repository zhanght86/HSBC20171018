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
import com.sinosoft.lis.db.PD_LMRiskFileDB;

/*
 * <p>ClassName: PD_LMRiskFileSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品定义平台
 * @CreateDate：2011-11-11
 */
public class PD_LMRiskFileSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMRiskFileSchema.class);

	// @Field
	/** Id */
	private String ID;
	/** 险种代码 */
	private String RiskCode;
	/** 详细分类 */
	private String Tittle;
	/** 描述 */
	private String Content;
	/** 相关性 */
	private String Relation;
	/** 文件路径 */
	private String filePath;
	/** 语言 */
	private String Language;
	/** 备注 */
	private String Bak;
	/** 新增日期 */
	private Date MakeDate;
	/** 新增时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public PD_LMRiskFileSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ID";
		pk[1] = "RiskCode";

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
                PD_LMRiskFileSchema cloned = (PD_LMRiskFileSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getID()
	{
		return ID;
	}
	public void setID(String aID)
	{
		ID = aID;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	public String getTittle()
	{
		return Tittle;
	}
	public void setTittle(String aTittle)
	{
		Tittle = aTittle;
	}
	public String getContent()
	{
		return Content;
	}
	public void setContent(String aContent)
	{
		Content = aContent;
	}
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		Relation = aRelation;
	}
	public String getfilePath()
	{
		return filePath;
	}
	public void setfilePath(String afilePath)
	{
		filePath = afilePath;
	}
	public String getLanguage()
	{
		return Language;
	}
	public void setLanguage(String aLanguage)
	{
		Language = aLanguage;
	}
	public String getBak()
	{
		return Bak;
	}
	public void setBak(String aBak)
	{
		Bak = aBak;
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
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 PD_LMRiskFileSchema 对象给 Schema 赋值
	* @param: aPD_LMRiskFileSchema PD_LMRiskFileSchema
	**/
	public void setSchema(PD_LMRiskFileSchema aPD_LMRiskFileSchema)
	{
		this.ID = aPD_LMRiskFileSchema.getID();
		this.RiskCode = aPD_LMRiskFileSchema.getRiskCode();
		this.Tittle = aPD_LMRiskFileSchema.getTittle();
		this.Content = aPD_LMRiskFileSchema.getContent();
		this.Relation = aPD_LMRiskFileSchema.getRelation();
		this.filePath = aPD_LMRiskFileSchema.getfilePath();
		this.Language = aPD_LMRiskFileSchema.getLanguage();
		this.Bak = aPD_LMRiskFileSchema.getBak();
		this.MakeDate = fDate.getDate( aPD_LMRiskFileSchema.getMakeDate());
		this.MakeTime = aPD_LMRiskFileSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMRiskFileSchema.getModifyDate());
		this.ModifyTime = aPD_LMRiskFileSchema.getModifyTime();
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
			if( rs.getString("ID") == null )
				this.ID = null;
			else
				this.ID = rs.getString("ID").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("Tittle") == null )
				this.Tittle = null;
			else
				this.Tittle = rs.getString("Tittle").trim();

			if( rs.getString("Content") == null )
				this.Content = null;
			else
				this.Content = rs.getString("Content").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

			if( rs.getString("filePath") == null )
				this.filePath = null;
			else
				this.filePath = rs.getString("filePath").trim();

			if( rs.getString("Language") == null )
				this.Language = null;
			else
				this.Language = rs.getString("Language").trim();

			if( rs.getString("Bak") == null )
				this.Bak = null;
			else
				this.Bak = rs.getString("Bak").trim();

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
			logger.debug("数据库中的PD_LMRiskFile表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFileSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMRiskFileSchema getSchema()
	{
		PD_LMRiskFileSchema aPD_LMRiskFileSchema = new PD_LMRiskFileSchema();
		aPD_LMRiskFileSchema.setSchema(this);
		return aPD_LMRiskFileSchema;
	}

	public PD_LMRiskFileDB getDB()
	{
		PD_LMRiskFileDB aDBOper = new PD_LMRiskFileDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskFile描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(ID)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Tittle)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Content)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Relation)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(filePath)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Language)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(Bak)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMRiskFile>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Tittle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Content = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			filePath = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Language = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Bak = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMRiskFileSchema";
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
		if (FCode.equalsIgnoreCase("ID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ID));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("Tittle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Tittle));
		}
		if (FCode.equalsIgnoreCase("Content"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Content));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
		}
		if (FCode.equalsIgnoreCase("filePath"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(filePath));
		}
		if (FCode.equalsIgnoreCase("Language"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Language));
		}
		if (FCode.equalsIgnoreCase("Bak"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak));
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
				strFieldValue = StrTool.GBKToUnicode(ID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Tittle);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Content);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Relation);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(filePath);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Language);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Bak);
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

		if (FCode.equalsIgnoreCase("ID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ID = FValue.trim();
			}
			else
				ID = null;
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("Tittle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Tittle = FValue.trim();
			}
			else
				Tittle = null;
		}
		if (FCode.equalsIgnoreCase("Content"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Content = FValue.trim();
			}
			else
				Content = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		if (FCode.equalsIgnoreCase("filePath"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				filePath = FValue.trim();
			}
			else
				filePath = null;
		}
		if (FCode.equalsIgnoreCase("Language"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Language = FValue.trim();
			}
			else
				Language = null;
		}
		if (FCode.equalsIgnoreCase("Bak"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak = FValue.trim();
			}
			else
				Bak = null;
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
		PD_LMRiskFileSchema other = (PD_LMRiskFileSchema)otherObject;
		return
			ID.equals(other.getID())
			&& RiskCode.equals(other.getRiskCode())
			&& Tittle.equals(other.getTittle())
			&& Content.equals(other.getContent())
			&& Relation.equals(other.getRelation())
			&& filePath.equals(other.getfilePath())
			&& Language.equals(other.getLanguage())
			&& Bak.equals(other.getBak())
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
		if( strFieldName.equals("ID") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("Tittle") ) {
			return 2;
		}
		if( strFieldName.equals("Content") ) {
			return 3;
		}
		if( strFieldName.equals("Relation") ) {
			return 4;
		}
		if( strFieldName.equals("filePath") ) {
			return 5;
		}
		if( strFieldName.equals("Language") ) {
			return 6;
		}
		if( strFieldName.equals("Bak") ) {
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
				strFieldName = "ID";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "Tittle";
				break;
			case 3:
				strFieldName = "Content";
				break;
			case 4:
				strFieldName = "Relation";
				break;
			case 5:
				strFieldName = "filePath";
				break;
			case 6:
				strFieldName = "Language";
				break;
			case 7:
				strFieldName = "Bak";
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
		if( strFieldName.equals("ID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Tittle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Content") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("filePath") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Language") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak") ) {
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
