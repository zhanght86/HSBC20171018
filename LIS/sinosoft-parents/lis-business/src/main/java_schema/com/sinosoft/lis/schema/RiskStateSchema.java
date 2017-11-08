

/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RiskStateDB;

/**
 * <p>ClassName: RiskStateSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PHYSICAL_DATA_MODEL_1
 * @CreateDate：2010-11-05
 */
public class RiskStateSchema implements Schema, Cloneable
{
	// @Field
	/** 浜у搧浠ｇ爜 */
	private String RiskNo;
	/** 鑺傜偣鍙? */
	private String NodeCode;
	/** 鐖惰妭鐐瑰彿 */
	private String ParentNodeCode;
	/** 鑺傜偣鍚嶇о */
	private String NodeName;
	/** 閾炬帴url */
	private String NodeUrl;
	/** 鑺傜偣椤哄簭 */
	private int NodeOrder;
	/** 鑺傜偣鐘舵?? */
	private String NodeState;

	public static final int FIELDNUM = 7;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RiskStateSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[3];
		pk[0] = "RiskNo";
		pk[1] = "NodeCode";
		pk[2] = "ParentNodeCode";

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
                RiskStateSchema cloned = (RiskStateSchema)super.clone();
                cloned.mErrors = (CErrors) mErrors.clone();
                return cloned;
            }

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getRiskNo()
	{
		return RiskNo;
	}
	public void setRiskNo(String aRiskNo)
	{
		RiskNo = aRiskNo;
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
	public String getNodeName()
	{
		return NodeName;
	}
	public void setNodeName(String aNodeName)
	{
		NodeName = aNodeName;
	}
	public String getNodeUrl()
	{
		return NodeUrl;
	}
	public void setNodeUrl(String aNodeUrl)
	{
		NodeUrl = aNodeUrl;
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

	public String getNodeState()
	{
		return NodeState;
	}
	public void setNodeState(String aNodeState)
	{
		NodeState = aNodeState;
	}

	/**
	* 使用另外一个 RiskStateSchema 对象给 Schema 赋值
	* @param: aRiskStateSchema RiskStateSchema
	**/
	public void setSchema(RiskStateSchema aRiskStateSchema)
	{
		this.RiskNo = aRiskStateSchema.getRiskNo();
		this.NodeCode = aRiskStateSchema.getNodeCode();
		this.ParentNodeCode = aRiskStateSchema.getParentNodeCode();
		this.NodeName = aRiskStateSchema.getNodeName();
		this.NodeUrl = aRiskStateSchema.getNodeUrl();
		this.NodeOrder = aRiskStateSchema.getNodeOrder();
		this.NodeState = aRiskStateSchema.getNodeState();
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
			if( rs.getString("RiskNo") == null )
				this.RiskNo = null;
			else
				this.RiskNo = rs.getString("RiskNo").trim();

			if( rs.getString("NodeCode") == null )
				this.NodeCode = null;
			else
				this.NodeCode = rs.getString("NodeCode").trim();

			if( rs.getString("ParentNodeCode") == null )
				this.ParentNodeCode = null;
			else
				this.ParentNodeCode = rs.getString("ParentNodeCode").trim();

			if( rs.getString("NodeName") == null )
				this.NodeName = null;
			else
				this.NodeName = rs.getString("NodeName").trim();

			if( rs.getString("NodeUrl") == null )
				this.NodeUrl = null;
			else
				this.NodeUrl = rs.getString("NodeUrl").trim();

			this.NodeOrder = rs.getInt("NodeOrder");
			if( rs.getString("NodeState") == null )
				this.NodeState = null;
			else
				this.NodeState = rs.getString("NodeState").trim();

		}
		catch(SQLException sqle)
		{
			System.out.println("数据库中的RiskState表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RiskStateSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public RiskStateSchema getSchema()
	{
		RiskStateSchema aRiskStateSchema = new RiskStateSchema();
		aRiskStateSchema.setSchema(this);
		return aRiskStateSchema;
	}

	public RiskStateDB getDB()
	{
		RiskStateDB aDBOper = new RiskStateDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRiskState描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
strReturn.append(StrTool.cTrim(RiskNo)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(ParentNodeCode)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NodeName)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NodeUrl)); strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append( ChgData.chgData(NodeOrder));strReturn.append(SysConst.PACKAGESPILTER);
strReturn.append(StrTool.cTrim(NodeState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRiskState>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			NodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ParentNodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			NodeUrl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NodeOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).intValue();
			NodeState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RiskStateSchema";
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
		if (FCode.equalsIgnoreCase("RiskNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskNo));
		}
		if (FCode.equalsIgnoreCase("NodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeCode));
		}
		if (FCode.equalsIgnoreCase("ParentNodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentNodeCode));
		}
		if (FCode.equalsIgnoreCase("NodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeName));
		}
		if (FCode.equalsIgnoreCase("NodeUrl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeUrl));
		}
		if (FCode.equalsIgnoreCase("NodeOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeOrder));
		}
		if (FCode.equalsIgnoreCase("NodeState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeState));
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
				strFieldValue = StrTool.GBKToUnicode(RiskNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(NodeCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ParentNodeCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(NodeUrl);
				break;
			case 5:
				strFieldValue = String.valueOf(NodeOrder);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(NodeState);
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

		if (FCode.equalsIgnoreCase("RiskNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskNo = FValue.trim();
			}
			else
				RiskNo = null;
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
		if (FCode.equalsIgnoreCase("ParentNodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParentNodeCode = FValue.trim();
			}
			else
				ParentNodeCode = null;
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
		if (FCode.equalsIgnoreCase("NodeUrl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeUrl = FValue.trim();
			}
			else
				NodeUrl = null;
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
		if (FCode.equalsIgnoreCase("NodeState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeState = FValue.trim();
			}
			else
				NodeState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RiskStateSchema other = (RiskStateSchema)otherObject;
		return
			RiskNo.equals(other.getRiskNo())
			&& NodeCode.equals(other.getNodeCode())
			&& ParentNodeCode.equals(other.getParentNodeCode())
			&& NodeName.equals(other.getNodeName())
			&& NodeUrl.equals(other.getNodeUrl())
			&& NodeOrder == other.getNodeOrder()
			&& NodeState.equals(other.getNodeState());
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
		if( strFieldName.equals("RiskNo") ) {
			return 0;
		}
		if( strFieldName.equals("NodeCode") ) {
			return 1;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return 2;
		}
		if( strFieldName.equals("NodeName") ) {
			return 3;
		}
		if( strFieldName.equals("NodeUrl") ) {
			return 4;
		}
		if( strFieldName.equals("NodeOrder") ) {
			return 5;
		}
		if( strFieldName.equals("NodeState") ) {
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
				strFieldName = "RiskNo";
				break;
			case 1:
				strFieldName = "NodeCode";
				break;
			case 2:
				strFieldName = "ParentNodeCode";
				break;
			case 3:
				strFieldName = "NodeName";
				break;
			case 4:
				strFieldName = "NodeUrl";
				break;
			case 5:
				strFieldName = "NodeOrder";
				break;
			case 6:
				strFieldName = "NodeState";
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
		if( strFieldName.equals("RiskNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeUrl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NodeState") ) {
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
				nFieldType = Schema.TYPE_INT;
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
