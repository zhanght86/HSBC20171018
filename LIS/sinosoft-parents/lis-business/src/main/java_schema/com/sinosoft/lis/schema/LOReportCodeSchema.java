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
import com.sinosoft.lis.db.LOReportCodeDB;

/*
 * <p>ClassName: LOReportCodeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LOReportCodeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOReportCodeSchema.class);

	// @Field
	/** 编码 */
	private String Code;
	/** 打印类型 */
	private String CodeType;
	/** 编码名称 */
	private String CodeName;
	/** 对应处理 */
	private String PrtBL;
	/** 对应模版 */
	private String PrtVTS;
	/** 统计层级 */
	private String PrtArray;
	/** 备注 */
	private String Remark;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOReportCodeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "Code";
		pk[1] = "CodeType";

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
		LOReportCodeSchema cloned = (LOReportCodeSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getCode()
	{
		return Code;
	}
	public void setCode(String aCode)
	{
		Code = aCode;
	}
	/**
	* 0 -- 文件输出<p>
	* 1 -- XML输出
	*/
	public String getCodeType()
	{
		return CodeType;
	}
	public void setCodeType(String aCodeType)
	{
		CodeType = aCodeType;
	}
	public String getCodeName()
	{
		return CodeName;
	}
	public void setCodeName(String aCodeName)
	{
		CodeName = aCodeName;
	}
	public String getPrtBL()
	{
		return PrtBL;
	}
	public void setPrtBL(String aPrtBL)
	{
		PrtBL = aPrtBL;
	}
	public String getPrtVTS()
	{
		return PrtVTS;
	}
	public void setPrtVTS(String aPrtVTS)
	{
		PrtVTS = aPrtVTS;
	}
	/**
	* 报表统计到分公司还是中支等，这样可以在程序中循环时判断循环2、3、4级机构。<p>
	* <p>
	* ^1|总公司 ^2|分公司 ^3|中支公司 ^4|支公司 ^5|区 ^6|部 ^7|组 ^8|业务员
	*/
	public String getPrtArray()
	{
		return PrtArray;
	}
	public void setPrtArray(String aPrtArray)
	{
		PrtArray = aPrtArray;
	}
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}

	/**
	* 使用另外一个 LOReportCodeSchema 对象给 Schema 赋值
	* @param: aLOReportCodeSchema LOReportCodeSchema
	**/
	public void setSchema(LOReportCodeSchema aLOReportCodeSchema)
	{
		this.Code = aLOReportCodeSchema.getCode();
		this.CodeType = aLOReportCodeSchema.getCodeType();
		this.CodeName = aLOReportCodeSchema.getCodeName();
		this.PrtBL = aLOReportCodeSchema.getPrtBL();
		this.PrtVTS = aLOReportCodeSchema.getPrtVTS();
		this.PrtArray = aLOReportCodeSchema.getPrtArray();
		this.Remark = aLOReportCodeSchema.getRemark();
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
			if( rs.getString("Code") == null )
				this.Code = null;
			else
				this.Code = rs.getString("Code").trim();

			if( rs.getString("CodeType") == null )
				this.CodeType = null;
			else
				this.CodeType = rs.getString("CodeType").trim();

			if( rs.getString("CodeName") == null )
				this.CodeName = null;
			else
				this.CodeName = rs.getString("CodeName").trim();

			if( rs.getString("PrtBL") == null )
				this.PrtBL = null;
			else
				this.PrtBL = rs.getString("PrtBL").trim();

			if( rs.getString("PrtVTS") == null )
				this.PrtVTS = null;
			else
				this.PrtVTS = rs.getString("PrtVTS").trim();

			if( rs.getString("PrtArray") == null )
				this.PrtArray = null;
			else
				this.PrtArray = rs.getString("PrtArray").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOReportCode表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReportCodeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOReportCodeSchema getSchema()
	{
		LOReportCodeSchema aLOReportCodeSchema = new LOReportCodeSchema();
		aLOReportCodeSchema.setSchema(this);
		return aLOReportCodeSchema;
	}

	public LOReportCodeDB getDB()
	{
		LOReportCodeDB aDBOper = new LOReportCodeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReportCode描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(Code)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtBL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtVTS)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrtArray)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOReportCode>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			Code = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CodeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PrtBL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PrtVTS = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PrtArray = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOReportCodeSchema";
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
		if (FCode.equalsIgnoreCase("Code"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Code));
		}
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeType));
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CodeName));
		}
		if (FCode.equalsIgnoreCase("PrtBL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtBL));
		}
		if (FCode.equalsIgnoreCase("PrtVTS"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtVTS));
		}
		if (FCode.equalsIgnoreCase("PrtArray"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrtArray));
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
				strFieldValue = StrTool.GBKToUnicode(Code);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CodeType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CodeName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PrtBL);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PrtVTS);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PrtArray);
				break;
			case 6:
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

		if (FCode.equalsIgnoreCase("Code"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Code = FValue.trim();
			}
			else
				Code = null;
		}
		if (FCode.equalsIgnoreCase("CodeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeType = FValue.trim();
			}
			else
				CodeType = null;
		}
		if (FCode.equalsIgnoreCase("CodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CodeName = FValue.trim();
			}
			else
				CodeName = null;
		}
		if (FCode.equalsIgnoreCase("PrtBL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtBL = FValue.trim();
			}
			else
				PrtBL = null;
		}
		if (FCode.equalsIgnoreCase("PrtVTS"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtVTS = FValue.trim();
			}
			else
				PrtVTS = null;
		}
		if (FCode.equalsIgnoreCase("PrtArray"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrtArray = FValue.trim();
			}
			else
				PrtArray = null;
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
		LOReportCodeSchema other = (LOReportCodeSchema)otherObject;
		return
			Code.equals(other.getCode())
			&& CodeType.equals(other.getCodeType())
			&& CodeName.equals(other.getCodeName())
			&& PrtBL.equals(other.getPrtBL())
			&& PrtVTS.equals(other.getPrtVTS())
			&& PrtArray.equals(other.getPrtArray())
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
		if( strFieldName.equals("Code") ) {
			return 0;
		}
		if( strFieldName.equals("CodeType") ) {
			return 1;
		}
		if( strFieldName.equals("CodeName") ) {
			return 2;
		}
		if( strFieldName.equals("PrtBL") ) {
			return 3;
		}
		if( strFieldName.equals("PrtVTS") ) {
			return 4;
		}
		if( strFieldName.equals("PrtArray") ) {
			return 5;
		}
		if( strFieldName.equals("Remark") ) {
			return 6;
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
				strFieldName = "Code";
				break;
			case 1:
				strFieldName = "CodeType";
				break;
			case 2:
				strFieldName = "CodeName";
				break;
			case 3:
				strFieldName = "PrtBL";
				break;
			case 4:
				strFieldName = "PrtVTS";
				break;
			case 5:
				strFieldName = "PrtArray";
				break;
			case 6:
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
		if( strFieldName.equals("Code") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtBL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtVTS") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrtArray") ) {
			return Schema.TYPE_STRING;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
