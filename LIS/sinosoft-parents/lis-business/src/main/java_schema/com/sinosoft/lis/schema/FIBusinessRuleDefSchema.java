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
import com.sinosoft.lis.db.FIBusinessRuleDefDB;

/*
 * <p>ClassName: FIBusinessRuleDefSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_2
 */
public class FIBusinessRuleDefSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(FIBusinessRuleDefSchema.class);
	// @Field
	/** 版本编号 */
	private String VersionNo;
	/** 校验规则编码 */
	private String RuleID;
	/** 校验规则名称 */
	private String RuleName;
	/** 校验处理方式 */
	private String RuleDealMode;
	/** 处理类名称 */
	private String RuleDealClass;
	/** 处理文件名称 */
	private String RuleFileName;
	/** 校验规则的sql */
	private String RuleDealSQL;
	/** 校验规则状态 */
	private String RuleState;
	/** 校验返回数据定义 */
	private String RuleReturnDataDef;
	/** 上一个结点 */
	private String LastNode;
	/** 下一个结点 */
	private String NextNode;
	/** 调用节点 */
	private String CallPointID;
	/** Databaseid */
	private String DataBaseID;

	public static final int FIELDNUM = 13;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public FIBusinessRuleDefSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "VersionNo";
		pk[1] = "RuleID";

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
		FIBusinessRuleDefSchema cloned = (FIBusinessRuleDefSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getVersionNo()
	{
		return VersionNo;
	}
	public void setVersionNo(String aVersionNo)
	{
		if(aVersionNo!=null && aVersionNo.length()>20)
			throw new IllegalArgumentException("版本编号VersionNo值"+aVersionNo+"的长度"+aVersionNo.length()+"大于最大值20");
		VersionNo = aVersionNo;
	}
	/**
	* 编码规范为<p>
	* 0000001的方式递增<p>
	* 规则采用<p>
	* RULEID 的方式创建
	*/
	public String getRuleID()
	{
		return RuleID;
	}
	public void setRuleID(String aRuleID)
	{
		if(aRuleID!=null && aRuleID.length()>10)
			throw new IllegalArgumentException("校验规则编码RuleID值"+aRuleID+"的长度"+aRuleID.length()+"大于最大值10");
		RuleID = aRuleID;
	}
	/**
	* 中文说明 用于错误信息组织
	*/
	public String getRuleName()
	{
		return RuleName;
	}
	public void setRuleName(String aRuleName)
	{
		if(aRuleName!=null && aRuleName.length()>100)
			throw new IllegalArgumentException("校验规则名称RuleName值"+aRuleName+"的长度"+aRuleName.length()+"大于最大值100");
		RuleName = aRuleName;
	}
	/**
	* 1、类处理   <p>
	* 2、SQL处理   <p>
	* 3、文件校验规则处理
	*/
	public String getRuleDealMode()
	{
		return RuleDealMode;
	}
	public void setRuleDealMode(String aRuleDealMode)
	{
		if(aRuleDealMode!=null && aRuleDealMode.length()>1)
			throw new IllegalArgumentException("校验处理方式RuleDealMode值"+aRuleDealMode+"的长度"+aRuleDealMode.length()+"大于最大值1");
		RuleDealMode = aRuleDealMode;
	}
	public String getRuleDealClass()
	{
		return RuleDealClass;
	}
	public void setRuleDealClass(String aRuleDealClass)
	{
		if(aRuleDealClass!=null && aRuleDealClass.length()>100)
			throw new IllegalArgumentException("处理类名称RuleDealClass值"+aRuleDealClass+"的长度"+aRuleDealClass.length()+"大于最大值100");
		RuleDealClass = aRuleDealClass;
	}
	public String getRuleFileName()
	{
		return RuleFileName;
	}
	public void setRuleFileName(String aRuleFileName)
	{
		if(aRuleFileName!=null && aRuleFileName.length()>100)
			throw new IllegalArgumentException("处理文件名称RuleFileName值"+aRuleFileName+"的长度"+aRuleFileName.length()+"大于最大值100");
		RuleFileName = aRuleFileName;
	}
	public String getRuleDealSQL()
	{
		return RuleDealSQL;
	}
	public void setRuleDealSQL(String aRuleDealSQL)
	{
		if(aRuleDealSQL!=null && aRuleDealSQL.length()>4000)
			throw new IllegalArgumentException("校验规则的sqlRuleDealSQL值"+aRuleDealSQL+"的长度"+aRuleDealSQL.length()+"大于最大值4000");
		RuleDealSQL = aRuleDealSQL;
	}
	/**
	* 1 表示该规则启用<p>
	* 0 表示该规则不启用
	*/
	public String getRuleState()
	{
		return RuleState;
	}
	public void setRuleState(String aRuleState)
	{
		if(aRuleState!=null && aRuleState.length()>1)
			throw new IllegalArgumentException("校验规则状态RuleState值"+aRuleState+"的长度"+aRuleState.length()+"大于最大值1");
		RuleState = aRuleState;
	}
	/**
	* 返回不符合的数据集合<p>
	* 包含单条数据或者多条<p>
	* 如果是多个 中间用","分开
	*/
	public String getRuleReturnDataDef()
	{
		return RuleReturnDataDef;
	}
	public void setRuleReturnDataDef(String aRuleReturnDataDef)
	{
		if(aRuleReturnDataDef!=null && aRuleReturnDataDef.length()>4000)
			throw new IllegalArgumentException("校验返回数据定义RuleReturnDataDef值"+aRuleReturnDataDef+"的长度"+aRuleReturnDataDef.length()+"大于最大值4000");
		RuleReturnDataDef = aRuleReturnDataDef;
	}
	/**
	* 如果是开始就赋值 <p>
	* Root [根节点]不区分大小写<p>
	* 表示最开始的结点
	*/
	public String getLastNode()
	{
		return LastNode;
	}
	public void setLastNode(String aLastNode)
	{
		if(aLastNode!=null && aLastNode.length()>100)
			throw new IllegalArgumentException("上一个结点LastNode值"+aLastNode+"的长度"+aLastNode.length()+"大于最大值100");
		LastNode = aLastNode;
	}
	/**
	* 为Leaf 表示结束<p>
	* 默认就为该值
	*/
	public String getNextNode()
	{
		return NextNode;
	}
	public void setNextNode(String aNextNode)
	{
		if(aNextNode!=null && aNextNode.length()>100)
			throw new IllegalArgumentException("下一个结点NextNode值"+aNextNode+"的长度"+aNextNode.length()+"大于最大值100");
		NextNode = aNextNode;
	}
	/**
	* 1 采集<p>
	* 2 提取凭证<p>
	* 3 汇总凭证<p>
	* 4 待定 <p>
	* ......
	*/
	public String getCallPointID()
	{
		return CallPointID;
	}
	public void setCallPointID(String aCallPointID)
	{
		if(aCallPointID!=null && aCallPointID.length()>10)
			throw new IllegalArgumentException("调用节点CallPointID值"+aCallPointID+"的长度"+aCallPointID.length()+"大于最大值10");
		CallPointID = aCallPointID;
	}
	public String getDataBaseID()
	{
		return DataBaseID;
	}
	public void setDataBaseID(String aDataBaseID)
	{
		if(aDataBaseID!=null && aDataBaseID.length()>20)
			throw new IllegalArgumentException("DatabaseidDataBaseID值"+aDataBaseID+"的长度"+aDataBaseID.length()+"大于最大值20");
		DataBaseID = aDataBaseID;
	}

	/**
	* 使用另外一个 FIBusinessRuleDefSchema 对象给 Schema 赋值
	* @param: aFIBusinessRuleDefSchema FIBusinessRuleDefSchema
	**/
	public void setSchema(FIBusinessRuleDefSchema aFIBusinessRuleDefSchema)
	{
		this.VersionNo = aFIBusinessRuleDefSchema.getVersionNo();
		this.RuleID = aFIBusinessRuleDefSchema.getRuleID();
		this.RuleName = aFIBusinessRuleDefSchema.getRuleName();
		this.RuleDealMode = aFIBusinessRuleDefSchema.getRuleDealMode();
		this.RuleDealClass = aFIBusinessRuleDefSchema.getRuleDealClass();
		this.RuleFileName = aFIBusinessRuleDefSchema.getRuleFileName();
		this.RuleDealSQL = aFIBusinessRuleDefSchema.getRuleDealSQL();
		this.RuleState = aFIBusinessRuleDefSchema.getRuleState();
		this.RuleReturnDataDef = aFIBusinessRuleDefSchema.getRuleReturnDataDef();
		this.LastNode = aFIBusinessRuleDefSchema.getLastNode();
		this.NextNode = aFIBusinessRuleDefSchema.getNextNode();
		this.CallPointID = aFIBusinessRuleDefSchema.getCallPointID();
		this.DataBaseID = aFIBusinessRuleDefSchema.getDataBaseID();
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
			if( rs.getString("VersionNo") == null )
				this.VersionNo = null;
			else
				this.VersionNo = rs.getString("VersionNo").trim();

			if( rs.getString("RuleID") == null )
				this.RuleID = null;
			else
				this.RuleID = rs.getString("RuleID").trim();

			if( rs.getString("RuleName") == null )
				this.RuleName = null;
			else
				this.RuleName = rs.getString("RuleName").trim();

			if( rs.getString("RuleDealMode") == null )
				this.RuleDealMode = null;
			else
				this.RuleDealMode = rs.getString("RuleDealMode").trim();

			if( rs.getString("RuleDealClass") == null )
				this.RuleDealClass = null;
			else
				this.RuleDealClass = rs.getString("RuleDealClass").trim();

			if( rs.getString("RuleFileName") == null )
				this.RuleFileName = null;
			else
				this.RuleFileName = rs.getString("RuleFileName").trim();

			if( rs.getString("RuleDealSQL") == null )
				this.RuleDealSQL = null;
			else
				this.RuleDealSQL = rs.getString("RuleDealSQL").trim();

			if( rs.getString("RuleState") == null )
				this.RuleState = null;
			else
				this.RuleState = rs.getString("RuleState").trim();

			if( rs.getString("RuleReturnDataDef") == null )
				this.RuleReturnDataDef = null;
			else
				this.RuleReturnDataDef = rs.getString("RuleReturnDataDef").trim();

			if( rs.getString("LastNode") == null )
				this.LastNode = null;
			else
				this.LastNode = rs.getString("LastNode").trim();

			if( rs.getString("NextNode") == null )
				this.NextNode = null;
			else
				this.NextNode = rs.getString("NextNode").trim();

			if( rs.getString("CallPointID") == null )
				this.CallPointID = null;
			else
				this.CallPointID = rs.getString("CallPointID").trim();

			if( rs.getString("DataBaseID") == null )
				this.DataBaseID = null;
			else
				this.DataBaseID = rs.getString("DataBaseID").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的FIBusinessRuleDef表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIBusinessRuleDefSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public FIBusinessRuleDefSchema getSchema()
	{
		FIBusinessRuleDefSchema aFIBusinessRuleDefSchema = new FIBusinessRuleDefSchema();
		aFIBusinessRuleDefSchema.setSchema(this);
		return aFIBusinessRuleDefSchema;
	}

	public FIBusinessRuleDefDB getDB()
	{
		FIBusinessRuleDefDB aDBOper = new FIBusinessRuleDefDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIBusinessRuleDef描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(VersionNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDealMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDealClass)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleFileName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleDealSQL)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RuleReturnDataDef)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LastNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(NextNode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CallPointID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DataBaseID));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpFIBusinessRuleDef>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			VersionNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RuleID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RuleName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RuleDealMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			RuleDealClass = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RuleFileName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RuleDealSQL = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RuleState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			RuleReturnDataDef = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			LastNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			NextNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CallPointID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			DataBaseID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIBusinessRuleDefSchema";
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
		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VersionNo));
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleID));
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleName));
		}
		if (FCode.equalsIgnoreCase("RuleDealMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealMode));
		}
		if (FCode.equalsIgnoreCase("RuleDealClass"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealClass));
		}
		if (FCode.equalsIgnoreCase("RuleFileName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleFileName));
		}
		if (FCode.equalsIgnoreCase("RuleDealSQL"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleDealSQL));
		}
		if (FCode.equalsIgnoreCase("RuleState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleState));
		}
		if (FCode.equalsIgnoreCase("RuleReturnDataDef"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RuleReturnDataDef));
		}
		if (FCode.equalsIgnoreCase("LastNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LastNode));
		}
		if (FCode.equalsIgnoreCase("NextNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NextNode));
		}
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CallPointID));
		}
		if (FCode.equalsIgnoreCase("DataBaseID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DataBaseID));
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
				strFieldValue = StrTool.GBKToUnicode(VersionNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RuleID);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RuleName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RuleDealMode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(RuleDealClass);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RuleFileName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RuleDealSQL);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RuleState);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(RuleReturnDataDef);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(LastNode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(NextNode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CallPointID);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(DataBaseID);
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

		if (FCode.equalsIgnoreCase("VersionNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VersionNo = FValue.trim();
			}
			else
				VersionNo = null;
		}
		if (FCode.equalsIgnoreCase("RuleID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleID = FValue.trim();
			}
			else
				RuleID = null;
		}
		if (FCode.equalsIgnoreCase("RuleName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleName = FValue.trim();
			}
			else
				RuleName = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealMode = FValue.trim();
			}
			else
				RuleDealMode = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealClass"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealClass = FValue.trim();
			}
			else
				RuleDealClass = null;
		}
		if (FCode.equalsIgnoreCase("RuleFileName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleFileName = FValue.trim();
			}
			else
				RuleFileName = null;
		}
		if (FCode.equalsIgnoreCase("RuleDealSQL"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleDealSQL = FValue.trim();
			}
			else
				RuleDealSQL = null;
		}
		if (FCode.equalsIgnoreCase("RuleState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleState = FValue.trim();
			}
			else
				RuleState = null;
		}
		if (FCode.equalsIgnoreCase("RuleReturnDataDef"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RuleReturnDataDef = FValue.trim();
			}
			else
				RuleReturnDataDef = null;
		}
		if (FCode.equalsIgnoreCase("LastNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LastNode = FValue.trim();
			}
			else
				LastNode = null;
		}
		if (FCode.equalsIgnoreCase("NextNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NextNode = FValue.trim();
			}
			else
				NextNode = null;
		}
		if (FCode.equalsIgnoreCase("CallPointID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CallPointID = FValue.trim();
			}
			else
				CallPointID = null;
		}
		if (FCode.equalsIgnoreCase("DataBaseID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DataBaseID = FValue.trim();
			}
			else
				DataBaseID = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		FIBusinessRuleDefSchema other = (FIBusinessRuleDefSchema)otherObject;
		return
			VersionNo.equals(other.getVersionNo())
			&& RuleID.equals(other.getRuleID())
			&& RuleName.equals(other.getRuleName())
			&& RuleDealMode.equals(other.getRuleDealMode())
			&& RuleDealClass.equals(other.getRuleDealClass())
			&& RuleFileName.equals(other.getRuleFileName())
			&& RuleDealSQL.equals(other.getRuleDealSQL())
			&& RuleState.equals(other.getRuleState())
			&& RuleReturnDataDef.equals(other.getRuleReturnDataDef())
			&& LastNode.equals(other.getLastNode())
			&& NextNode.equals(other.getNextNode())
			&& CallPointID.equals(other.getCallPointID())
			&& DataBaseID.equals(other.getDataBaseID());
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
		if( strFieldName.equals("VersionNo") ) {
			return 0;
		}
		if( strFieldName.equals("RuleID") ) {
			return 1;
		}
		if( strFieldName.equals("RuleName") ) {
			return 2;
		}
		if( strFieldName.equals("RuleDealMode") ) {
			return 3;
		}
		if( strFieldName.equals("RuleDealClass") ) {
			return 4;
		}
		if( strFieldName.equals("RuleFileName") ) {
			return 5;
		}
		if( strFieldName.equals("RuleDealSQL") ) {
			return 6;
		}
		if( strFieldName.equals("RuleState") ) {
			return 7;
		}
		if( strFieldName.equals("RuleReturnDataDef") ) {
			return 8;
		}
		if( strFieldName.equals("LastNode") ) {
			return 9;
		}
		if( strFieldName.equals("NextNode") ) {
			return 10;
		}
		if( strFieldName.equals("CallPointID") ) {
			return 11;
		}
		if( strFieldName.equals("DataBaseID") ) {
			return 12;
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
				strFieldName = "VersionNo";
				break;
			case 1:
				strFieldName = "RuleID";
				break;
			case 2:
				strFieldName = "RuleName";
				break;
			case 3:
				strFieldName = "RuleDealMode";
				break;
			case 4:
				strFieldName = "RuleDealClass";
				break;
			case 5:
				strFieldName = "RuleFileName";
				break;
			case 6:
				strFieldName = "RuleDealSQL";
				break;
			case 7:
				strFieldName = "RuleState";
				break;
			case 8:
				strFieldName = "RuleReturnDataDef";
				break;
			case 9:
				strFieldName = "LastNode";
				break;
			case 10:
				strFieldName = "NextNode";
				break;
			case 11:
				strFieldName = "CallPointID";
				break;
			case 12:
				strFieldName = "DataBaseID";
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
		if( strFieldName.equals("VersionNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealClass") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleFileName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleDealSQL") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RuleReturnDataDef") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LastNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NextNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CallPointID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DataBaseID") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
