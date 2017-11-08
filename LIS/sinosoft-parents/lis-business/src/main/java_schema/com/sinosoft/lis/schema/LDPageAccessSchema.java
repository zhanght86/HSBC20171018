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
import com.sinosoft.lis.db.LDPageAccessDB;

/*
 * <p>ClassName: LDPageAccessSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDPageAccessSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPageAccessSchema.class);

	// @Field
	/** 执行语句 */
	private String RunScript;
	/** 菜单节点说明 */
	private String NodeDescription;
	/** 菜单节点编码 */
	private String NodeCode;

	public static final int FIELDNUM = 3;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPageAccessSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "RunScript";
		pk[1] = "NodeCode";

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
		LDPageAccessSchema cloned = (LDPageAccessSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 此处为需要执行的语句或命令行或网页连接。
	*/
	public String getRunScript()
	{
		return RunScript;
	}
	public void setRunScript(String aRunScript)
	{
		RunScript = aRunScript;
	}
	public String getNodeDescription()
	{
		return NodeDescription;
	}
	public void setNodeDescription(String aNodeDescription)
	{
		NodeDescription = aNodeDescription;
	}
	public String getNodeCode()
	{
		return NodeCode;
	}
	public void setNodeCode(String aNodeCode)
	{
		NodeCode = aNodeCode;
	}

	/**
	* 使用另外一个 LDPageAccessSchema 对象给 Schema 赋值
	* @param: aLDPageAccessSchema LDPageAccessSchema
	**/
	public void setSchema(LDPageAccessSchema aLDPageAccessSchema)
	{
		this.RunScript = aLDPageAccessSchema.getRunScript();
		this.NodeDescription = aLDPageAccessSchema.getNodeDescription();
		this.NodeCode = aLDPageAccessSchema.getNodeCode();
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
			if( rs.getString("RunScript") == null )
				this.RunScript = null;
			else
				this.RunScript = rs.getString("RunScript").trim();

			if( rs.getString("NodeDescription") == null )
				this.NodeDescription = null;
			else
				this.NodeDescription = rs.getString("NodeDescription").trim();

			if( rs.getString("NodeCode") == null )
				this.NodeCode = null;
			else
				this.NodeCode = rs.getString("NodeCode").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDPageAccess表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPageAccessSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPageAccessSchema getSchema()
	{
		LDPageAccessSchema aLDPageAccessSchema = new LDPageAccessSchema();
		aLDPageAccessSchema.setSchema(this);
		return aLDPageAccessSchema;
	}

	public LDPageAccessDB getDB()
	{
		LDPageAccessDB aDBOper = new LDPageAccessDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPageAccess描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(RunScript)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeCode));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPageAccess>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RunScript = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NodeDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			NodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPageAccessSchema";
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
		if (FCode.equalsIgnoreCase("RunScript"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RunScript));
		}
		if (FCode.equalsIgnoreCase("NodeDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeDescription));
		}
		if (FCode.equalsIgnoreCase("NodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeCode));
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
				strFieldValue = StrTool.GBKToUnicode(RunScript);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NodeDescription);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(NodeCode);
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

		if (FCode.equalsIgnoreCase("RunScript"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RunScript = FValue.trim();
			}
			else
				RunScript = null;
		}
		if (FCode.equalsIgnoreCase("NodeDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeDescription = FValue.trim();
			}
			else
				NodeDescription = null;
		}
		if (FCode.equalsIgnoreCase("NodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeCode = FValue.trim();
			}
			else
				NodeCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDPageAccessSchema other = (LDPageAccessSchema)otherObject;
		return
			RunScript.equals(other.getRunScript())
			&& NodeDescription.equals(other.getNodeDescription())
			&& NodeCode.equals(other.getNodeCode());
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
		if( strFieldName.equals("RunScript") ) {
			return 0;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return 1;
		}
		if( strFieldName.equals("NodeCode") ) {
			return 2;
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
				strFieldName = "RunScript";
				break;
			case 1:
				strFieldName = "NodeDescription";
				break;
			case 2:
				strFieldName = "NodeCode";
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
		if( strFieldName.equals("RunScript") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeCode") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
