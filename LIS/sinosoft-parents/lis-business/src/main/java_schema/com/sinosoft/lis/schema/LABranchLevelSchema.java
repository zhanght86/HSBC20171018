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
import com.sinosoft.lis.db.LABranchLevelDB;

/*
 * <p>ClassName: LABranchLevelSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LABranchLevelSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LABranchLevelSchema.class);

	// @Field
	/** 机构级别编码 */
	private String BranchLevelCode;
	/** 机构级别名称 */
	private String BranchLevelName;
	/** 机构级别分类 */
	private String BranchLevelType;
	/** 机构级别的id */
	private int BranchLevelID;
	/** 展业类型 */
	private String BranchType;
	/** 机构属性 */
	private String BranchProperty;
	/** 机构对应属性(agentkind) */
	private String AgentKind;
	/** 直辖属性 */
	private String SubjectProperty;
	/** 机构对应属性1 */
	private String BranchLevelProperty1;
	/** 机构对应属性2 */
	private String BranchLevelProperty2;
	/** 机构对应属性3 */
	private String BranchLevelProperty3;
	/** 机构对应属性4 */
	private String BranchLevelProperty4;
	/** 机构对应属性5 */
	private String BranchLevelProperty5;
	/** 备注 */
	private String Noti;

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LABranchLevelSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BranchLevelCode";

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
		LABranchLevelSchema cloned = (LABranchLevelSchema)super.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getBranchLevelCode()
	{
		return BranchLevelCode;
	}
	public void setBranchLevelCode(String aBranchLevelCode)
	{
		BranchLevelCode = aBranchLevelCode;
	}
	public String getBranchLevelName()
	{
		return BranchLevelName;
	}
	public void setBranchLevelName(String aBranchLevelName)
	{
		BranchLevelName = aBranchLevelName;
	}
	/**
	* 0－默认。
	*/
	public String getBranchLevelType()
	{
		return BranchLevelType;
	}
	public void setBranchLevelType(String aBranchLevelType)
	{
		BranchLevelType = aBranchLevelType;
	}
	/**
	* （职级属性2，相当于机构级别的内部编码 1,2,3....）
	*/
	public int getBranchLevelID()
	{
		return BranchLevelID;
	}
	public void setBranchLevelID(int aBranchLevelID)
	{
		BranchLevelID = aBranchLevelID;
	}
	public void setBranchLevelID(String aBranchLevelID)
	{
		if (aBranchLevelID != null && !aBranchLevelID.equals(""))
		{
			Integer tInteger = new Integer(aBranchLevelID);
			int i = tInteger.intValue();
			BranchLevelID = i;
		}
	}

	/**
	* 展业类型(1-个人营销，2-团险，3－银行保险，9－其他)
	*/
	public String getBranchType()
	{
		return BranchType;
	}
	public void setBranchType(String aBranchType)
	{
		BranchType = aBranchType;
	}
	/**
	* （上门，电话，其他）；
	*/
	public String getBranchProperty()
	{
		return BranchProperty;
	}
	public void setBranchProperty(String aBranchProperty)
	{
		BranchProperty = aBranchProperty;
	}
	public String getAgentKind()
	{
		return AgentKind;
	}
	public void setAgentKind(String aAgentKind)
	{
		AgentKind = aAgentKind;
	}
	public String getSubjectProperty()
	{
		return SubjectProperty;
	}
	public void setSubjectProperty(String aSubjectProperty)
	{
		SubjectProperty = aSubjectProperty;
	}
	/**
	* 机构中实际是否有增员
	*/
	public String getBranchLevelProperty1()
	{
		return BranchLevelProperty1;
	}
	public void setBranchLevelProperty1(String aBranchLevelProperty1)
	{
		BranchLevelProperty1 = aBranchLevelProperty1;
	}
	/**
	* 展业机构编码长度定义
	*/
	public String getBranchLevelProperty2()
	{
		return BranchLevelProperty2;
	}
	public void setBranchLevelProperty2(String aBranchLevelProperty2)
	{
		BranchLevelProperty2 = aBranchLevelProperty2;
	}
	public String getBranchLevelProperty3()
	{
		return BranchLevelProperty3;
	}
	public void setBranchLevelProperty3(String aBranchLevelProperty3)
	{
		BranchLevelProperty3 = aBranchLevelProperty3;
	}
	public String getBranchLevelProperty4()
	{
		return BranchLevelProperty4;
	}
	public void setBranchLevelProperty4(String aBranchLevelProperty4)
	{
		BranchLevelProperty4 = aBranchLevelProperty4;
	}
	public String getBranchLevelProperty5()
	{
		return BranchLevelProperty5;
	}
	public void setBranchLevelProperty5(String aBranchLevelProperty5)
	{
		BranchLevelProperty5 = aBranchLevelProperty5;
	}
	public String getNoti()
	{
		return Noti;
	}
	public void setNoti(String aNoti)
	{
		Noti = aNoti;
	}

	/**
	* 使用另外一个 LABranchLevelSchema 对象给 Schema 赋值
	* @param: aLABranchLevelSchema LABranchLevelSchema
	**/
	public void setSchema(LABranchLevelSchema aLABranchLevelSchema)
	{
		this.BranchLevelCode = aLABranchLevelSchema.getBranchLevelCode();
		this.BranchLevelName = aLABranchLevelSchema.getBranchLevelName();
		this.BranchLevelType = aLABranchLevelSchema.getBranchLevelType();
		this.BranchLevelID = aLABranchLevelSchema.getBranchLevelID();
		this.BranchType = aLABranchLevelSchema.getBranchType();
		this.BranchProperty = aLABranchLevelSchema.getBranchProperty();
		this.AgentKind = aLABranchLevelSchema.getAgentKind();
		this.SubjectProperty = aLABranchLevelSchema.getSubjectProperty();
		this.BranchLevelProperty1 = aLABranchLevelSchema.getBranchLevelProperty1();
		this.BranchLevelProperty2 = aLABranchLevelSchema.getBranchLevelProperty2();
		this.BranchLevelProperty3 = aLABranchLevelSchema.getBranchLevelProperty3();
		this.BranchLevelProperty4 = aLABranchLevelSchema.getBranchLevelProperty4();
		this.BranchLevelProperty5 = aLABranchLevelSchema.getBranchLevelProperty5();
		this.Noti = aLABranchLevelSchema.getNoti();
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
			if( rs.getString("BranchLevelCode") == null )
				this.BranchLevelCode = null;
			else
				this.BranchLevelCode = rs.getString("BranchLevelCode").trim();

			if( rs.getString("BranchLevelName") == null )
				this.BranchLevelName = null;
			else
				this.BranchLevelName = rs.getString("BranchLevelName").trim();

			if( rs.getString("BranchLevelType") == null )
				this.BranchLevelType = null;
			else
				this.BranchLevelType = rs.getString("BranchLevelType").trim();

			this.BranchLevelID = rs.getInt("BranchLevelID");
			if( rs.getString("BranchType") == null )
				this.BranchType = null;
			else
				this.BranchType = rs.getString("BranchType").trim();

			if( rs.getString("BranchProperty") == null )
				this.BranchProperty = null;
			else
				this.BranchProperty = rs.getString("BranchProperty").trim();

			if( rs.getString("AgentKind") == null )
				this.AgentKind = null;
			else
				this.AgentKind = rs.getString("AgentKind").trim();

			if( rs.getString("SubjectProperty") == null )
				this.SubjectProperty = null;
			else
				this.SubjectProperty = rs.getString("SubjectProperty").trim();

			if( rs.getString("BranchLevelProperty1") == null )
				this.BranchLevelProperty1 = null;
			else
				this.BranchLevelProperty1 = rs.getString("BranchLevelProperty1").trim();

			if( rs.getString("BranchLevelProperty2") == null )
				this.BranchLevelProperty2 = null;
			else
				this.BranchLevelProperty2 = rs.getString("BranchLevelProperty2").trim();

			if( rs.getString("BranchLevelProperty3") == null )
				this.BranchLevelProperty3 = null;
			else
				this.BranchLevelProperty3 = rs.getString("BranchLevelProperty3").trim();

			if( rs.getString("BranchLevelProperty4") == null )
				this.BranchLevelProperty4 = null;
			else
				this.BranchLevelProperty4 = rs.getString("BranchLevelProperty4").trim();

			if( rs.getString("BranchLevelProperty5") == null )
				this.BranchLevelProperty5 = null;
			else
				this.BranchLevelProperty5 = rs.getString("BranchLevelProperty5").trim();

			if( rs.getString("Noti") == null )
				this.Noti = null;
			else
				this.Noti = rs.getString("Noti").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LABranchLevel表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABranchLevelSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LABranchLevelSchema getSchema()
	{
		LABranchLevelSchema aLABranchLevelSchema = new LABranchLevelSchema();
		aLABranchLevelSchema.setSchema(this);
		return aLABranchLevelSchema;
	}

	public LABranchLevelDB getDB()
	{
		LABranchLevelDB aDBOper = new LABranchLevelDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLABranchLevel描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BranchLevelCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BranchLevelID));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchProperty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AgentKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubjectProperty)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelProperty1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelProperty2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelProperty3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelProperty4)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BranchLevelProperty5)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Noti));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLABranchLevel>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BranchLevelCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BranchLevelName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BranchLevelType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BranchLevelID= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			BranchType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BranchProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			SubjectProperty = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BranchLevelProperty1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			BranchLevelProperty2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BranchLevelProperty3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BranchLevelProperty4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			BranchLevelProperty5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Noti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LABranchLevelSchema";
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
		if (FCode.equalsIgnoreCase("BranchLevelCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelCode));
		}
		if (FCode.equalsIgnoreCase("BranchLevelName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelName));
		}
		if (FCode.equalsIgnoreCase("BranchLevelType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelType));
		}
		if (FCode.equalsIgnoreCase("BranchLevelID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelID));
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchType));
		}
		if (FCode.equalsIgnoreCase("BranchProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchProperty));
		}
		if (FCode.equalsIgnoreCase("AgentKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentKind));
		}
		if (FCode.equalsIgnoreCase("SubjectProperty"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubjectProperty));
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelProperty1));
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelProperty2));
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelProperty3));
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelProperty4));
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BranchLevelProperty5));
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Noti));
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
				strFieldValue = StrTool.GBKToUnicode(BranchLevelCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelType);
				break;
			case 3:
				strFieldValue = String.valueOf(BranchLevelID);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BranchType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BranchProperty);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentKind);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(SubjectProperty);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelProperty1);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelProperty2);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelProperty3);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelProperty4);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(BranchLevelProperty5);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Noti);
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

		if (FCode.equalsIgnoreCase("BranchLevelCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelCode = FValue.trim();
			}
			else
				BranchLevelCode = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelName = FValue.trim();
			}
			else
				BranchLevelName = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelType = FValue.trim();
			}
			else
				BranchLevelType = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				BranchLevelID = i;
			}
		}
		if (FCode.equalsIgnoreCase("BranchType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchType = FValue.trim();
			}
			else
				BranchType = null;
		}
		if (FCode.equalsIgnoreCase("BranchProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchProperty = FValue.trim();
			}
			else
				BranchProperty = null;
		}
		if (FCode.equalsIgnoreCase("AgentKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentKind = FValue.trim();
			}
			else
				AgentKind = null;
		}
		if (FCode.equalsIgnoreCase("SubjectProperty"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubjectProperty = FValue.trim();
			}
			else
				SubjectProperty = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelProperty1 = FValue.trim();
			}
			else
				BranchLevelProperty1 = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelProperty2 = FValue.trim();
			}
			else
				BranchLevelProperty2 = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelProperty3 = FValue.trim();
			}
			else
				BranchLevelProperty3 = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelProperty4 = FValue.trim();
			}
			else
				BranchLevelProperty4 = null;
		}
		if (FCode.equalsIgnoreCase("BranchLevelProperty5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BranchLevelProperty5 = FValue.trim();
			}
			else
				BranchLevelProperty5 = null;
		}
		if (FCode.equalsIgnoreCase("Noti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Noti = FValue.trim();
			}
			else
				Noti = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LABranchLevelSchema other = (LABranchLevelSchema)otherObject;
		return
			BranchLevelCode.equals(other.getBranchLevelCode())
			&& BranchLevelName.equals(other.getBranchLevelName())
			&& BranchLevelType.equals(other.getBranchLevelType())
			&& BranchLevelID == other.getBranchLevelID()
			&& BranchType.equals(other.getBranchType())
			&& BranchProperty.equals(other.getBranchProperty())
			&& AgentKind.equals(other.getAgentKind())
			&& SubjectProperty.equals(other.getSubjectProperty())
			&& BranchLevelProperty1.equals(other.getBranchLevelProperty1())
			&& BranchLevelProperty2.equals(other.getBranchLevelProperty2())
			&& BranchLevelProperty3.equals(other.getBranchLevelProperty3())
			&& BranchLevelProperty4.equals(other.getBranchLevelProperty4())
			&& BranchLevelProperty5.equals(other.getBranchLevelProperty5())
			&& Noti.equals(other.getNoti());
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
		if( strFieldName.equals("BranchLevelCode") ) {
			return 0;
		}
		if( strFieldName.equals("BranchLevelName") ) {
			return 1;
		}
		if( strFieldName.equals("BranchLevelType") ) {
			return 2;
		}
		if( strFieldName.equals("BranchLevelID") ) {
			return 3;
		}
		if( strFieldName.equals("BranchType") ) {
			return 4;
		}
		if( strFieldName.equals("BranchProperty") ) {
			return 5;
		}
		if( strFieldName.equals("AgentKind") ) {
			return 6;
		}
		if( strFieldName.equals("SubjectProperty") ) {
			return 7;
		}
		if( strFieldName.equals("BranchLevelProperty1") ) {
			return 8;
		}
		if( strFieldName.equals("BranchLevelProperty2") ) {
			return 9;
		}
		if( strFieldName.equals("BranchLevelProperty3") ) {
			return 10;
		}
		if( strFieldName.equals("BranchLevelProperty4") ) {
			return 11;
		}
		if( strFieldName.equals("BranchLevelProperty5") ) {
			return 12;
		}
		if( strFieldName.equals("Noti") ) {
			return 13;
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
				strFieldName = "BranchLevelCode";
				break;
			case 1:
				strFieldName = "BranchLevelName";
				break;
			case 2:
				strFieldName = "BranchLevelType";
				break;
			case 3:
				strFieldName = "BranchLevelID";
				break;
			case 4:
				strFieldName = "BranchType";
				break;
			case 5:
				strFieldName = "BranchProperty";
				break;
			case 6:
				strFieldName = "AgentKind";
				break;
			case 7:
				strFieldName = "SubjectProperty";
				break;
			case 8:
				strFieldName = "BranchLevelProperty1";
				break;
			case 9:
				strFieldName = "BranchLevelProperty2";
				break;
			case 10:
				strFieldName = "BranchLevelProperty3";
				break;
			case 11:
				strFieldName = "BranchLevelProperty4";
				break;
			case 12:
				strFieldName = "BranchLevelProperty5";
				break;
			case 13:
				strFieldName = "Noti";
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
		if( strFieldName.equals("BranchLevelCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelID") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("BranchType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchProperty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubjectProperty") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelProperty1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelProperty2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelProperty3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelProperty4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BranchLevelProperty5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Noti") ) {
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
				nFieldType = Schema.TYPE_INT;
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
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
