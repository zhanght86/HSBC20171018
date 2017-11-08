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
import com.sinosoft.lis.db.LMBonusGroupDB;

/*
 * <p>ClassName: LMBonusGroupSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 分红管理
 */
public class LMBonusGroupSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LMBonusGroupSchema.class);

	// @Field
	/** 红利分配组号 */
	private int GroupID;
	/** 红利分配组类型 */
	private String BonusGroupType;
	/** 红利计算的限制条件 */
	private String BonusCondition;
	/** 说明 */
	private String Remark;

	public static final int FIELDNUM = 4;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMBonusGroupSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "GroupID";

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
		LMBonusGroupSchema cloned = (LMBonusGroupSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 每个分配的组都有一个组号
	*/
	public int getGroupID()
	{
		return GroupID;
	}
	public void setGroupID(int aGroupID)
	{
		GroupID = aGroupID;
	}
	public void setGroupID(String aGroupID)
	{
		if (aGroupID != null && !aGroupID.equals(""))
		{
			Integer tInteger = new Integer(aGroupID);
			int i = tInteger.intValue();
			GroupID = i;
		}
	}

	/**
	* 1 －－ 对所有的可分配红利的险种进行分红<p>
	* 2 －－ 某些销售渠道的红利<p>
	* 3 －－ 某些险种的红利<p>
	* 4 －－ 某些团体保单的红利（主要针对某个团体的大单，通过协议来进行红利分配的情况。）<p>
	* <p>
	* 该字段仅仅是描述作用，没有太多的实际意义。
	*/
	public String getBonusGroupType()
	{
		return BonusGroupType;
	}
	public void setBonusGroupType(String aBonusGroupType)
	{
		BonusGroupType = aBonusGroupType;
	}
	/**
	* 在这里定义一个Where子句，描述该红利分配组需要什么样的条件限制。<p>
	* （整个Where都需要描述，程序中只要简单的拼接起来就可以了。）<p>
	* 通过该限制条件来描述该红利分配组的需要取的有效保单。
	*/
	public String getBonusCondition()
	{
		return BonusCondition;
	}
	public void setBonusCondition(String aBonusCondition)
	{
		BonusCondition = aBonusCondition;
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
	* 使用另外一个 LMBonusGroupSchema 对象给 Schema 赋值
	* @param: aLMBonusGroupSchema LMBonusGroupSchema
	**/
	public void setSchema(LMBonusGroupSchema aLMBonusGroupSchema)
	{
		this.GroupID = aLMBonusGroupSchema.getGroupID();
		this.BonusGroupType = aLMBonusGroupSchema.getBonusGroupType();
		this.BonusCondition = aLMBonusGroupSchema.getBonusCondition();
		this.Remark = aLMBonusGroupSchema.getRemark();
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
			this.GroupID = rs.getInt("GroupID");
			if( rs.getString("BonusGroupType") == null )
				this.BonusGroupType = null;
			else
				this.BonusGroupType = rs.getString("BonusGroupType").trim();

			if( rs.getString("BonusCondition") == null )
				this.BonusCondition = null;
			else
				this.BonusCondition = rs.getString("BonusCondition").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LMBonusGroup表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMBonusGroupSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LMBonusGroupSchema getSchema()
	{
		LMBonusGroupSchema aLMBonusGroupSchema = new LMBonusGroupSchema();
		aLMBonusGroupSchema.setSchema(this);
		return aLMBonusGroupSchema;
	}

	public LMBonusGroupDB getDB()
	{
		LMBonusGroupDB aDBOper = new LMBonusGroupDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMBonusGroup描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append( ChgData.chgData(GroupID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusGroupType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BonusCondition)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMBonusGroup>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GroupID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			BonusGroupType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BonusCondition = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMBonusGroupSchema";
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
		if (FCode.equalsIgnoreCase("GroupID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupID));
		}
		if (FCode.equalsIgnoreCase("BonusGroupType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusGroupType));
		}
		if (FCode.equalsIgnoreCase("BonusCondition"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BonusCondition));
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
				strFieldValue = String.valueOf(GroupID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BonusGroupType);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BonusCondition);
				break;
			case 3:
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

		if (FCode.equalsIgnoreCase("GroupID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				GroupID = i;
			}
		}
		if (FCode.equalsIgnoreCase("BonusGroupType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusGroupType = FValue.trim();
			}
			else
				BonusGroupType = null;
		}
		if (FCode.equalsIgnoreCase("BonusCondition"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BonusCondition = FValue.trim();
			}
			else
				BonusCondition = null;
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
		LMBonusGroupSchema other = (LMBonusGroupSchema)otherObject;
		return
			GroupID == other.getGroupID()
			&& BonusGroupType.equals(other.getBonusGroupType())
			&& BonusCondition.equals(other.getBonusCondition())
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
		if( strFieldName.equals("GroupID") ) {
			return 0;
		}
		if( strFieldName.equals("BonusGroupType") ) {
			return 1;
		}
		if( strFieldName.equals("BonusCondition") ) {
			return 2;
		}
		if( strFieldName.equals("Remark") ) {
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
				strFieldName = "GroupID";
				break;
			case 1:
				strFieldName = "BonusGroupType";
				break;
			case 2:
				strFieldName = "BonusCondition";
				break;
			case 3:
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
		if( strFieldName.equals("GroupID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BonusGroupType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BonusCondition") ) {
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
				nFieldType = Schema.TYPE_INT;
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
