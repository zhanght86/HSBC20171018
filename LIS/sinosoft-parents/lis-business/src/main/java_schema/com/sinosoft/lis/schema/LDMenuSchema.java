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
import com.sinosoft.lis.db.LDMenuDB;

/*
 * <p>ClassName: LDMenuSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDMenuSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDMenuSchema.class);

	// @Field
	/** 菜单节点编码 */
	private String NodeCode;
	/** 父菜单节点编码 */
	private String ParentNodeCode;
	/** 菜单节点层次 */
	private String NodeLevel;
	/** 节点名称 */
	private String NodeName;
	/** 子节点个数 */
	private String ChildFlag;
	/** 节点热健 */
	private String NodeKey;
	/** 执行语句 */
	private String RunScript;
	/** 菜单节点说明 */
	private String NodeDescription;
	/** 节点标志 */
	private String NodeSign;
	/** 节点顺序号 */
	private int NodeOrder;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMenuSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "NodeCode";

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
		LDMenuSchema cloned = (LDMenuSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getNodeCode()
	{
		return NodeCode;
	}
	public void setNodeCode(String aNodeCode)
	{
		NodeCode = aNodeCode;
	}
	public String getParentNodeCode()
	{
		return ParentNodeCode;
	}
	public void setParentNodeCode(String aParentNodeCode)
	{
		ParentNodeCode = aParentNodeCode;
	}
	public String getNodeLevel()
	{
		return NodeLevel;
	}
	public void setNodeLevel(String aNodeLevel)
	{
		NodeLevel = aNodeLevel;
	}
	public String getNodeName()
	{
		return NodeName;
	}
	public void setNodeName(String aNodeName)
	{
		NodeName = aNodeName;
	}
	public String getChildFlag()
	{
		return ChildFlag;
	}
	public void setChildFlag(String aChildFlag)
	{
		ChildFlag = aChildFlag;
	}
	public String getNodeKey()
	{
		return NodeKey;
	}
	public void setNodeKey(String aNodeKey)
	{
		NodeKey = aNodeKey;
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
	public String getNodeSign()
	{
		return NodeSign;
	}
	public void setNodeSign(String aNodeSign)
	{
		NodeSign = aNodeSign;
	}
	public int getNodeOrder()
	{
		return NodeOrder;
	}
	public void setNodeOrder(int aNodeOrder)
	{
		NodeOrder = aNodeOrder;
	}
	public void setNodeOrder(String aNodeOrder)
	{
		if (aNodeOrder != null && !aNodeOrder.equals(""))
		{
			Integer tInteger = new Integer(aNodeOrder);
			int i = tInteger.intValue();
			NodeOrder = i;
		}
	}


	/**
	* 使用另外一个 LDMenuSchema 对象给 Schema 赋值
	* @param: aLDMenuSchema LDMenuSchema
	**/
	public void setSchema(LDMenuSchema aLDMenuSchema)
	{
		this.NodeCode = aLDMenuSchema.getNodeCode();
		this.ParentNodeCode = aLDMenuSchema.getParentNodeCode();
		this.NodeLevel = aLDMenuSchema.getNodeLevel();
		this.NodeName = aLDMenuSchema.getNodeName();
		this.ChildFlag = aLDMenuSchema.getChildFlag();
		this.NodeKey = aLDMenuSchema.getNodeKey();
		this.RunScript = aLDMenuSchema.getRunScript();
		this.NodeDescription = aLDMenuSchema.getNodeDescription();
		this.NodeSign = aLDMenuSchema.getNodeSign();
		this.NodeOrder = aLDMenuSchema.getNodeOrder();
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
			if( rs.getString("NodeCode") == null )
				this.NodeCode = null;
			else
				this.NodeCode = rs.getString("NodeCode").trim();

			if( rs.getString("ParentNodeCode") == null )
				this.ParentNodeCode = null;
			else
				this.ParentNodeCode = rs.getString("ParentNodeCode").trim();

			if( rs.getString("NodeLevel") == null )
				this.NodeLevel = null;
			else
				this.NodeLevel = rs.getString("NodeLevel").trim();

			if( rs.getString("NodeName") == null )
				this.NodeName = null;
			else
				this.NodeName = rs.getString("NodeName").trim();

			if( rs.getString("ChildFlag") == null )
				this.ChildFlag = null;
			else
				this.ChildFlag = rs.getString("ChildFlag").trim();

			if( rs.getString("NodeKey") == null )
				this.NodeKey = null;
			else
				this.NodeKey = rs.getString("NodeKey").trim();

			if( rs.getString("RunScript") == null )
				this.RunScript = null;
			else
				this.RunScript = rs.getString("RunScript").trim();

			if( rs.getString("NodeDescription") == null )
				this.NodeDescription = null;
			else
				this.NodeDescription = rs.getString("NodeDescription").trim();

			if( rs.getString("NodeSign") == null )
				this.NodeSign = null;
			else
				this.NodeSign = rs.getString("NodeSign").trim();

			this.NodeOrder = rs.getInt("NodeOrder");
		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDMenu表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDMenuSchema getSchema()
	{
		LDMenuSchema aLDMenuSchema = new LDMenuSchema();
		aLDMenuSchema.setSchema(this);
		return aLDMenuSchema;
	}

	public LDMenuDB getDB()
	{
		LDMenuDB aDBOper = new LDMenuDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenu描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(NodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ParentNodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeLevel)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChildFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeKey)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RunScript)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeDescription)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NodeSign)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(NodeOrder));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenu>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ParentNodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			NodeLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChildFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NodeKey = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RunScript = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NodeDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			NodeSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			NodeOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuSchema";
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
		if (FCode.equalsIgnoreCase("NodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeCode));
		}
		if (FCode.equalsIgnoreCase("ParentNodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentNodeCode));
		}
		if (FCode.equalsIgnoreCase("NodeLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeLevel));
		}
		if (FCode.equalsIgnoreCase("NodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeName));
		}
		if (FCode.equalsIgnoreCase("ChildFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChildFlag));
		}
		if (FCode.equalsIgnoreCase("NodeKey"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeKey));
		}
		if (FCode.equalsIgnoreCase("RunScript"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RunScript));
		}
		if (FCode.equalsIgnoreCase("NodeDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeDescription));
		}
		if (FCode.equalsIgnoreCase("NodeSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeSign));
		}
		if (FCode.equalsIgnoreCase("NodeOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeOrder));
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
				strFieldValue = StrTool.GBKToUnicode(NodeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ParentNodeCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(NodeLevel);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChildFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NodeKey);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RunScript);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NodeDescription);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(NodeSign);
				break;
			case 9:
				strFieldValue = String.valueOf(NodeOrder);
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

		if (FCode.equalsIgnoreCase("NodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeCode = FValue.trim();
			}
			else
				NodeCode = null;
		}
		if (FCode.equalsIgnoreCase("ParentNodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParentNodeCode = FValue.trim();
			}
			else
				ParentNodeCode = null;
		}
		if (FCode.equalsIgnoreCase("NodeLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeLevel = FValue.trim();
			}
			else
				NodeLevel = null;
		}
		if (FCode.equalsIgnoreCase("NodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeName = FValue.trim();
			}
			else
				NodeName = null;
		}
		if (FCode.equalsIgnoreCase("ChildFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChildFlag = FValue.trim();
			}
			else
				ChildFlag = null;
		}
		if (FCode.equalsIgnoreCase("NodeKey"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeKey = FValue.trim();
			}
			else
				NodeKey = null;
		}
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
		if (FCode.equalsIgnoreCase("NodeSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeSign = FValue.trim();
			}
			else
				NodeSign = null;
		}
		if (FCode.equalsIgnoreCase("NodeOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NodeOrder = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMenuSchema other = (LDMenuSchema)otherObject;
		return
			NodeCode.equals(other.getNodeCode())
			&& ParentNodeCode.equals(other.getParentNodeCode())
			&& NodeLevel.equals(other.getNodeLevel())
			&& NodeName.equals(other.getNodeName())
			&& ChildFlag.equals(other.getChildFlag())
			&& NodeKey.equals(other.getNodeKey())
			&& RunScript.equals(other.getRunScript())
			&& NodeDescription.equals(other.getNodeDescription())
			&& NodeSign.equals(other.getNodeSign())
			&& NodeOrder == other.getNodeOrder();
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
		if( strFieldName.equals("NodeCode") ) {
			return 0;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return 1;
		}
		if( strFieldName.equals("NodeLevel") ) {
			return 2;
		}
		if( strFieldName.equals("NodeName") ) {
			return 3;
		}
		if( strFieldName.equals("ChildFlag") ) {
			return 4;
		}
		if( strFieldName.equals("NodeKey") ) {
			return 5;
		}
		if( strFieldName.equals("RunScript") ) {
			return 6;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return 7;
		}
		if( strFieldName.equals("NodeSign") ) {
			return 8;
		}
		if( strFieldName.equals("NodeOrder") ) {
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
				strFieldName = "NodeCode";
				break;
			case 1:
				strFieldName = "ParentNodeCode";
				break;
			case 2:
				strFieldName = "NodeLevel";
				break;
			case 3:
				strFieldName = "NodeName";
				break;
			case 4:
				strFieldName = "ChildFlag";
				break;
			case 5:
				strFieldName = "NodeKey";
				break;
			case 6:
				strFieldName = "RunScript";
				break;
			case 7:
				strFieldName = "NodeDescription";
				break;
			case 8:
				strFieldName = "NodeSign";
				break;
			case 9:
				strFieldName = "NodeOrder";
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
		if( strFieldName.equals("NodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChildFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeKey") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RunScript") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeSign") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
