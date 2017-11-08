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
import com.sinosoft.lis.db.LCPrintAgentDB;

/*
 * <p>ClassName: LCPrintAgentSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 新单管理
 */
public class LCPrintAgentSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPrintAgentSchema.class);
	// @Field
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 中介机构编码 */
	private String AgentCom;
	/** 中介机构名称 */
	private String AgentComName;
	/** 其他描述 */
	private String OtherDesc;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPrintAgentSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpPropNo";
		pk[1] = "AgentCom";

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
		LCPrintAgentSchema cloned = (LCPrintAgentSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
	}
	public String getAgentCom()
	{
		return AgentCom;
	}
	public void setAgentCom(String aAgentCom)
	{
		if(aAgentCom!=null && aAgentCom.length()>20)
			throw new IllegalArgumentException("中介机构编码AgentCom值"+aAgentCom+"的长度"+aAgentCom.length()+"大于最大值20");
		AgentCom = aAgentCom;
	}
	public String getAgentComName()
	{
		return AgentComName;
	}
	public void setAgentComName(String aAgentComName)
	{
		if(aAgentComName!=null && aAgentComName.length()>200)
			throw new IllegalArgumentException("中介机构名称AgentComName值"+aAgentComName+"的长度"+aAgentComName.length()+"大于最大值200");
		AgentComName = aAgentComName;
	}
	public String getOtherDesc()
	{
		return OtherDesc;
	}
	public void setOtherDesc(String aOtherDesc)
	{
		if(aOtherDesc!=null && aOtherDesc.length()>60)
			throw new IllegalArgumentException("其他描述OtherDesc值"+aOtherDesc+"的长度"+aOtherDesc.length()+"大于最大值60");
		OtherDesc = aOtherDesc;
	}

	/**
	* 使用另外一个 LCPrintAgentSchema 对象给 Schema 赋值
	* @param: aLCPrintAgentSchema LCPrintAgentSchema
	**/
	public void setSchema(LCPrintAgentSchema aLCPrintAgentSchema)
	{
		this.GrpPropNo = aLCPrintAgentSchema.getGrpPropNo();
		this.AgentCom = aLCPrintAgentSchema.getAgentCom();
		this.AgentComName = aLCPrintAgentSchema.getAgentComName();
		this.OtherDesc = aLCPrintAgentSchema.getOtherDesc();
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
			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("OtherDesc") == null )
				this.OtherDesc = null;
			else
				this.OtherDesc = rs.getString("OtherDesc").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LCPrintAgent表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAgentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPrintAgentSchema getSchema()
	{
		LCPrintAgentSchema aLCPrintAgentSchema = new LCPrintAgentSchema();
		aLCPrintAgentSchema.setSchema(this);
		return aLCPrintAgentSchema;
	}

	public LCPrintAgentDB getDB()
	{
		LCPrintAgentDB aDBOper = new LCPrintAgentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPrintAgent描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentComName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherDesc));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPrintAgent>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPrintAgentSchema";
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
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equalsIgnoreCase("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherDesc));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherDesc);
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

		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equalsIgnoreCase("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
		}
		if (FCode.equalsIgnoreCase("OtherDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherDesc = FValue.trim();
			}
			else
				OtherDesc = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCPrintAgentSchema other = (LCPrintAgentSchema)otherObject;
		return
			GrpPropNo.equals(other.getGrpPropNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentComName.equals(other.getAgentComName())
			&& OtherDesc.equals(other.getOtherDesc());
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
		if( strFieldName.equals("GrpPropNo") ) {
			return 0;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 1;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 2;
		}
		if( strFieldName.equals("OtherDesc") ) {
			return 3;
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
				strFieldName = "GrpPropNo";
				break;
			case 1:
				strFieldName = "AgentCom";
				break;
			case 2:
				strFieldName = "AgentComName";
				break;
			case 3:
				strFieldName = "OtherDesc";
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
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherDesc") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
