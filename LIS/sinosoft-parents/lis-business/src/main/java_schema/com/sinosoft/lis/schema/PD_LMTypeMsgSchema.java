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
import com.sinosoft.lis.db.PD_LMTypeMsgDB;

/*
 * <p>ClassName: PD_LMTypeMsgSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 中银产品定义平台
 * @CreateDate：2011-11-11
 */
public class PD_LMTypeMsgSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(PD_LMTypeMsgSchema.class);

	// @Field
	/** Id */
	private String id;
	/** 大类 */
	private String type;
	/** 详细分类 */
	private String tittle;
	/** 描述 */
	private String content;
	/** 相关性 */
	private String relation;
	/** 文件夹名称 */
	private String fileName;
	/** 备注1 */
	private String bak1;
	/** 备注2 */
	private String bak2;
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
	public PD_LMTypeMsgSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "id";

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
                PD_LMTypeMsgSchema cloned = (PD_LMTypeMsgSchema)super.clone();
                cloned.fDate = (FDate) fDate.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getid()
	{
		return id;
	}
	public void setid(String aid)
	{
		id = aid;
	}
	public String gettype()
	{
		return type;
	}
	public void settype(String atype)
	{
		type = atype;
	}
	public String gettittle()
	{
		return tittle;
	}
	public void settittle(String atittle)
	{
		tittle = atittle;
	}
	public String getcontent()
	{
		return content;
	}
	public void setcontent(String acontent)
	{
		content = acontent;
	}
	public String getrelation()
	{
		return relation;
	}
	public void setrelation(String arelation)
	{
		relation = arelation;
	}
	public String getfileName()
	{
		return fileName;
	}
	public void setfileName(String afileName)
	{
		fileName = afileName;
	}
	public String getbak1()
	{
		return bak1;
	}
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	public String getbak2()
	{
		return bak2;
	}
	public void setbak2(String abak2)
	{
		bak2 = abak2;
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
	* 使用另外一个 PD_LMTypeMsgSchema 对象给 Schema 赋值
	* @param: aPD_LMTypeMsgSchema PD_LMTypeMsgSchema
	**/
	public void setSchema(PD_LMTypeMsgSchema aPD_LMTypeMsgSchema)
	{
		this.id = aPD_LMTypeMsgSchema.getid();
		this.type = aPD_LMTypeMsgSchema.gettype();
		this.tittle = aPD_LMTypeMsgSchema.gettittle();
		this.content = aPD_LMTypeMsgSchema.getcontent();
		this.relation = aPD_LMTypeMsgSchema.getrelation();
		this.fileName = aPD_LMTypeMsgSchema.getfileName();
		this.bak1 = aPD_LMTypeMsgSchema.getbak1();
		this.bak2 = aPD_LMTypeMsgSchema.getbak2();
		this.MakeDate = fDate.getDate( aPD_LMTypeMsgSchema.getMakeDate());
		this.MakeTime = aPD_LMTypeMsgSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aPD_LMTypeMsgSchema.getModifyDate());
		this.ModifyTime = aPD_LMTypeMsgSchema.getModifyTime();
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
			if( rs.getString("id") == null )
				this.id = null;
			else
				this.id = rs.getString("id").trim();

			if( rs.getString("type") == null )
				this.type = null;
			else
				this.type = rs.getString("type").trim();

			if( rs.getString("tittle") == null )
				this.tittle = null;
			else
				this.tittle = rs.getString("tittle").trim();

			if( rs.getString("content") == null )
				this.content = null;
			else
				this.content = rs.getString("content").trim();

			if( rs.getString("relation") == null )
				this.relation = null;
			else
				this.relation = rs.getString("relation").trim();

			if( rs.getString("fileName") == null )
				this.fileName = null;
			else
				this.fileName = rs.getString("fileName").trim();

			if( rs.getString("bak1") == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString("bak1").trim();

			if( rs.getString("bak2") == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString("bak2").trim();

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
			logger.debug("数据库中的PD_LMTypeMsg表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMTypeMsgSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public PD_LMTypeMsgSchema getSchema()
	{
		PD_LMTypeMsgSchema aPD_LMTypeMsgSchema = new PD_LMTypeMsgSchema();
		aPD_LMTypeMsgSchema.setSchema(this);
		return aPD_LMTypeMsgSchema;
	}

	public PD_LMTypeMsgDB getDB()
	{
		PD_LMTypeMsgDB aDBOper = new PD_LMTypeMsgDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMTypeMsg描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(id)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(type)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(tittle)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(content)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(relation)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fileName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak1)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(bak2)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpPD_LMTypeMsg>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			id = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			type = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			tittle = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			content = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			fileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PD_LMTypeMsgSchema";
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
		if (FCode.equalsIgnoreCase("id"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(id));
		}
		if (FCode.equalsIgnoreCase("type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(type));
		}
		if (FCode.equalsIgnoreCase("tittle"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(tittle));
		}
		if (FCode.equalsIgnoreCase("content"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(content));
		}
		if (FCode.equalsIgnoreCase("relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(relation));
		}
		if (FCode.equalsIgnoreCase("fileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(fileName));
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak1));
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak2));
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
				strFieldValue = StrTool.GBKToUnicode(id);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(tittle);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(content);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(relation);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(fileName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(bak2);
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

		if (FCode.equalsIgnoreCase("id"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				id = FValue.trim();
			}
			else
				id = null;
		}
		if (FCode.equalsIgnoreCase("type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				type = FValue.trim();
			}
			else
				type = null;
		}
		if (FCode.equalsIgnoreCase("tittle"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				tittle = FValue.trim();
			}
			else
				tittle = null;
		}
		if (FCode.equalsIgnoreCase("content"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				content = FValue.trim();
			}
			else
				content = null;
		}
		if (FCode.equalsIgnoreCase("relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				relation = FValue.trim();
			}
			else
				relation = null;
		}
		if (FCode.equalsIgnoreCase("fileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				fileName = FValue.trim();
			}
			else
				fileName = null;
		}
		if (FCode.equalsIgnoreCase("bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak1 = FValue.trim();
			}
			else
				bak1 = null;
		}
		if (FCode.equalsIgnoreCase("bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak2 = FValue.trim();
			}
			else
				bak2 = null;
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
		PD_LMTypeMsgSchema other = (PD_LMTypeMsgSchema)otherObject;
		return
			id.equals(other.getid())
			&& type.equals(other.gettype())
			&& tittle.equals(other.gettittle())
			&& content.equals(other.getcontent())
			&& relation.equals(other.getrelation())
			&& fileName.equals(other.getfileName())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
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
		if( strFieldName.equals("id") ) {
			return 0;
		}
		if( strFieldName.equals("type") ) {
			return 1;
		}
		if( strFieldName.equals("tittle") ) {
			return 2;
		}
		if( strFieldName.equals("content") ) {
			return 3;
		}
		if( strFieldName.equals("relation") ) {
			return 4;
		}
		if( strFieldName.equals("fileName") ) {
			return 5;
		}
		if( strFieldName.equals("bak1") ) {
			return 6;
		}
		if( strFieldName.equals("bak2") ) {
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
				strFieldName = "id";
				break;
			case 1:
				strFieldName = "type";
				break;
			case 2:
				strFieldName = "tittle";
				break;
			case 3:
				strFieldName = "content";
				break;
			case 4:
				strFieldName = "relation";
				break;
			case 5:
				strFieldName = "fileName";
				break;
			case 6:
				strFieldName = "bak1";
				break;
			case 7:
				strFieldName = "bak2";
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
		if( strFieldName.equals("id") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("type") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("tittle") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("content") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("fileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak2") ) {
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
