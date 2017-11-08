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
import com.sinosoft.lis.db.LCContCustomerRelaInfoDB;

/*
 * <p>ClassName: LCContCustomerRelaInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCContCustomerRelaInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCContCustomerRelaInfoSchema.class);

	// @Field
	/** 合同号 */
	private String ContNo;
	/** 相关被保人 */
	private String RelaCustomerNo;
	/** 相关被保人类别 */
	private String InsuredKind;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户类别 */
	private String CustomerKind;
	/** 客户与相关被保人关系 */
	private String RelationToInsured;
	/** 客户婚否 */
	private String Marriage;
	/** 客户性别 */
	private String Sex;
	/** 客户年龄 */
	private int Age;
	/** 相关被保人婚否 */
	private String RelaMarriage;
	/** 相关被保人性别 */
	private String RelaSex;
	/** 相关被保人年龄 */
	private int RelaAge;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private Date ModifyDate;
	/** 修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCContCustomerRelaInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ContNo";
		pk[1] = "RelaCustomerNo";
		pk[2] = "InsuredKind";
		pk[3] = "CustomerNo";
		pk[4] = "RelationToInsured";

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
		LCContCustomerRelaInfoSchema cloned = (LCContCustomerRelaInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	public String getRelaCustomerNo()
	{
		return RelaCustomerNo;
	}
	public void setRelaCustomerNo(String aRelaCustomerNo)
	{
		RelaCustomerNo = aRelaCustomerNo;
	}
	/**
	* 0－第一被保人<p>
	* 1－
	*/
	public String getInsuredKind()
	{
		return InsuredKind;
	}
	public void setInsuredKind(String aInsuredKind)
	{
		InsuredKind = aInsuredKind;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	public String getCustomerKind()
	{
		return CustomerKind;
	}
	public void setCustomerKind(String aCustomerKind)
	{
		CustomerKind = aCustomerKind;
	}
	public String getRelationToInsured()
	{
		return RelationToInsured;
	}
	public void setRelationToInsured(String aRelationToInsured)
	{
		RelationToInsured = aRelationToInsured;
	}
	public String getMarriage()
	{
		return Marriage;
	}
	public void setMarriage(String aMarriage)
	{
		Marriage = aMarriage;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String aSex)
	{
		Sex = aSex;
	}
	public int getAge()
	{
		return Age;
	}
	public void setAge(int aAge)
	{
		Age = aAge;
	}
	public void setAge(String aAge)
	{
		if (aAge != null && !aAge.equals(""))
		{
			Integer tInteger = new Integer(aAge);
			int i = tInteger.intValue();
			Age = i;
		}
	}

	public String getRelaMarriage()
	{
		return RelaMarriage;
	}
	public void setRelaMarriage(String aRelaMarriage)
	{
		RelaMarriage = aRelaMarriage;
	}
	public String getRelaSex()
	{
		return RelaSex;
	}
	public void setRelaSex(String aRelaSex)
	{
		RelaSex = aRelaSex;
	}
	public int getRelaAge()
	{
		return RelaAge;
	}
	public void setRelaAge(int aRelaAge)
	{
		RelaAge = aRelaAge;
	}
	public void setRelaAge(String aRelaAge)
	{
		if (aRelaAge != null && !aRelaAge.equals(""))
		{
			Integer tInteger = new Integer(aRelaAge);
			int i = tInteger.intValue();
			RelaAge = i;
		}
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
	* 使用另外一个 LCContCustomerRelaInfoSchema 对象给 Schema 赋值
	* @param: aLCContCustomerRelaInfoSchema LCContCustomerRelaInfoSchema
	**/
	public void setSchema(LCContCustomerRelaInfoSchema aLCContCustomerRelaInfoSchema)
	{
		this.ContNo = aLCContCustomerRelaInfoSchema.getContNo();
		this.RelaCustomerNo = aLCContCustomerRelaInfoSchema.getRelaCustomerNo();
		this.InsuredKind = aLCContCustomerRelaInfoSchema.getInsuredKind();
		this.CustomerNo = aLCContCustomerRelaInfoSchema.getCustomerNo();
		this.CustomerKind = aLCContCustomerRelaInfoSchema.getCustomerKind();
		this.RelationToInsured = aLCContCustomerRelaInfoSchema.getRelationToInsured();
		this.Marriage = aLCContCustomerRelaInfoSchema.getMarriage();
		this.Sex = aLCContCustomerRelaInfoSchema.getSex();
		this.Age = aLCContCustomerRelaInfoSchema.getAge();
		this.RelaMarriage = aLCContCustomerRelaInfoSchema.getRelaMarriage();
		this.RelaSex = aLCContCustomerRelaInfoSchema.getRelaSex();
		this.RelaAge = aLCContCustomerRelaInfoSchema.getRelaAge();
		this.MakeDate = fDate.getDate( aLCContCustomerRelaInfoSchema.getMakeDate());
		this.MakeTime = aLCContCustomerRelaInfoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCContCustomerRelaInfoSchema.getModifyDate());
		this.ModifyTime = aLCContCustomerRelaInfoSchema.getModifyTime();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("RelaCustomerNo") == null )
				this.RelaCustomerNo = null;
			else
				this.RelaCustomerNo = rs.getString("RelaCustomerNo").trim();

			if( rs.getString("InsuredKind") == null )
				this.InsuredKind = null;
			else
				this.InsuredKind = rs.getString("InsuredKind").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerKind") == null )
				this.CustomerKind = null;
			else
				this.CustomerKind = rs.getString("CustomerKind").trim();

			if( rs.getString("RelationToInsured") == null )
				this.RelationToInsured = null;
			else
				this.RelationToInsured = rs.getString("RelationToInsured").trim();

			if( rs.getString("Marriage") == null )
				this.Marriage = null;
			else
				this.Marriage = rs.getString("Marriage").trim();

			if( rs.getString("Sex") == null )
				this.Sex = null;
			else
				this.Sex = rs.getString("Sex").trim();

			this.Age = rs.getInt("Age");
			if( rs.getString("RelaMarriage") == null )
				this.RelaMarriage = null;
			else
				this.RelaMarriage = rs.getString("RelaMarriage").trim();

			if( rs.getString("RelaSex") == null )
				this.RelaSex = null;
			else
				this.RelaSex = rs.getString("RelaSex").trim();

			this.RelaAge = rs.getInt("RelaAge");
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
			logger.debug("数据库中的LCContCustomerRelaInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContCustomerRelaInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCContCustomerRelaInfoSchema getSchema()
	{
		LCContCustomerRelaInfoSchema aLCContCustomerRelaInfoSchema = new LCContCustomerRelaInfoSchema();
		aLCContCustomerRelaInfoSchema.setSchema(this);
		return aLCContCustomerRelaInfoSchema;
	}

	public LCContCustomerRelaInfoDB getDB()
	{
		LCContCustomerRelaInfoDB aDBOper = new LCContCustomerRelaInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContCustomerRelaInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaCustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelationToInsured)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Marriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Sex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Age));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaMarriage)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RelaAge));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCContCustomerRelaInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RelaCustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			InsuredKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RelationToInsured = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Marriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Sex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Age= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			RelaMarriage = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RelaSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RelaAge= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCContCustomerRelaInfoSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("RelaCustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaCustomerNo));
		}
		if (FCode.equalsIgnoreCase("InsuredKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredKind));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerKind));
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelationToInsured));
		}
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Marriage));
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Sex));
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Age));
		}
		if (FCode.equalsIgnoreCase("RelaMarriage"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaMarriage));
		}
		if (FCode.equalsIgnoreCase("RelaSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaSex));
		}
		if (FCode.equalsIgnoreCase("RelaAge"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaAge));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RelaCustomerNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(InsuredKind);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerKind);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RelationToInsured);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Marriage);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Sex);
				break;
			case 8:
				strFieldValue = String.valueOf(Age);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(RelaMarriage);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RelaSex);
				break;
			case 11:
				strFieldValue = String.valueOf(RelaAge);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 15:
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

		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("RelaCustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaCustomerNo = FValue.trim();
			}
			else
				RelaCustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredKind = FValue.trim();
			}
			else
				InsuredKind = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerKind = FValue.trim();
			}
			else
				CustomerKind = null;
		}
		if (FCode.equalsIgnoreCase("RelationToInsured"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelationToInsured = FValue.trim();
			}
			else
				RelationToInsured = null;
		}
		if (FCode.equalsIgnoreCase("Marriage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Marriage = FValue.trim();
			}
			else
				Marriage = null;
		}
		if (FCode.equalsIgnoreCase("Sex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Sex = FValue.trim();
			}
			else
				Sex = null;
		}
		if (FCode.equalsIgnoreCase("Age"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Age = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaMarriage"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaMarriage = FValue.trim();
			}
			else
				RelaMarriage = null;
		}
		if (FCode.equalsIgnoreCase("RelaSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaSex = FValue.trim();
			}
			else
				RelaSex = null;
		}
		if (FCode.equalsIgnoreCase("RelaAge"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RelaAge = i;
			}
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
		LCContCustomerRelaInfoSchema other = (LCContCustomerRelaInfoSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& RelaCustomerNo.equals(other.getRelaCustomerNo())
			&& InsuredKind.equals(other.getInsuredKind())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerKind.equals(other.getCustomerKind())
			&& RelationToInsured.equals(other.getRelationToInsured())
			&& Marriage.equals(other.getMarriage())
			&& Sex.equals(other.getSex())
			&& Age == other.getAge()
			&& RelaMarriage.equals(other.getRelaMarriage())
			&& RelaSex.equals(other.getRelaSex())
			&& RelaAge == other.getRelaAge()
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("RelaCustomerNo") ) {
			return 1;
		}
		if( strFieldName.equals("InsuredKind") ) {
			return 2;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerKind") ) {
			return 4;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return 5;
		}
		if( strFieldName.equals("Marriage") ) {
			return 6;
		}
		if( strFieldName.equals("Sex") ) {
			return 7;
		}
		if( strFieldName.equals("Age") ) {
			return 8;
		}
		if( strFieldName.equals("RelaMarriage") ) {
			return 9;
		}
		if( strFieldName.equals("RelaSex") ) {
			return 10;
		}
		if( strFieldName.equals("RelaAge") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "RelaCustomerNo";
				break;
			case 2:
				strFieldName = "InsuredKind";
				break;
			case 3:
				strFieldName = "CustomerNo";
				break;
			case 4:
				strFieldName = "CustomerKind";
				break;
			case 5:
				strFieldName = "RelationToInsured";
				break;
			case 6:
				strFieldName = "Marriage";
				break;
			case 7:
				strFieldName = "Sex";
				break;
			case 8:
				strFieldName = "Age";
				break;
			case 9:
				strFieldName = "RelaMarriage";
				break;
			case 10:
				strFieldName = "RelaSex";
				break;
			case 11:
				strFieldName = "RelaAge";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaCustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelationToInsured") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Marriage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Sex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Age") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaMarriage") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaAge") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
