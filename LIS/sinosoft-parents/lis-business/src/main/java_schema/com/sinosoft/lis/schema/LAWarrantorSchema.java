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
import com.sinosoft.lis.db.LAWarrantorDB;

/*
 * <p>ClassName: LAWarrantorSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 代理人管理
 */
public class LAWarrantorSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LAWarrantorSchema.class);

	// @Field
	/** 代理人编码 */
	private String AgentCode;
	/** 序号 */
	private int SerialNo;
	/** 担保人名称 */
	private String CautionerName;
	/** 担保人性别 */
	private String CautionerSex;
	/** 担保人身份证 */
	private String CautionerID;
	/** 担保人出生日 */
	private Date CautionerBirthday;
	/** 担保人单位 */
	private String CautionerCom;
	/** 家庭地址编码 */
	private String HomeAddressCode;
	/** 家庭地址 */
	private String HomeAddress;
	/** 邮政编码 */
	private String ZipCode;
	/** 电话 */
	private String Phone;
	/** 关系 */
	private String Relation;
	/** 担保日期 */
	private Date CautionData;
	/** 操作员代码 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 手机 */
	private String Mobile;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LAWarrantorSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "AgentCode";
		pk[1] = "SerialNo";

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
		LAWarrantorSchema cloned = (LAWarrantorSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getAgentCode()
	{
		return AgentCode;
	}
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	public int getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(int aSerialNo)
	{
		SerialNo = aSerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if (aSerialNo != null && !aSerialNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialNo);
			int i = tInteger.intValue();
			SerialNo = i;
		}
	}

	public String getCautionerName()
	{
		return CautionerName;
	}
	public void setCautionerName(String aCautionerName)
	{
		CautionerName = aCautionerName;
	}
	public String getCautionerSex()
	{
		return CautionerSex;
	}
	public void setCautionerSex(String aCautionerSex)
	{
		CautionerSex = aCautionerSex;
	}
	public String getCautionerID()
	{
		return CautionerID;
	}
	public void setCautionerID(String aCautionerID)
	{
		CautionerID = aCautionerID;
	}
	public String getCautionerBirthday()
	{
		if( CautionerBirthday != null )
			return fDate.getString(CautionerBirthday);
		else
			return null;
	}
	public void setCautionerBirthday(Date aCautionerBirthday)
	{
		CautionerBirthday = aCautionerBirthday;
	}
	public void setCautionerBirthday(String aCautionerBirthday)
	{
		if (aCautionerBirthday != null && !aCautionerBirthday.equals("") )
		{
			CautionerBirthday = fDate.getDate( aCautionerBirthday );
		}
		else
			CautionerBirthday = null;
	}

	public String getCautionerCom()
	{
		return CautionerCom;
	}
	public void setCautionerCom(String aCautionerCom)
	{
		CautionerCom = aCautionerCom;
	}
	public String getHomeAddressCode()
	{
		return HomeAddressCode;
	}
	public void setHomeAddressCode(String aHomeAddressCode)
	{
		HomeAddressCode = aHomeAddressCode;
	}
	public String getHomeAddress()
	{
		return HomeAddress;
	}
	public void setHomeAddress(String aHomeAddress)
	{
		HomeAddress = aHomeAddress;
	}
	public String getZipCode()
	{
		return ZipCode;
	}
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	public String getPhone()
	{
		return Phone;
	}
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	public String getRelation()
	{
		return Relation;
	}
	public void setRelation(String aRelation)
	{
		Relation = aRelation;
	}
	public String getCautionData()
	{
		if( CautionData != null )
			return fDate.getString(CautionData);
		else
			return null;
	}
	public void setCautionData(Date aCautionData)
	{
		CautionData = aCautionData;
	}
	public void setCautionData(String aCautionData)
	{
		if (aCautionData != null && !aCautionData.equals("") )
		{
			CautionData = fDate.getDate( aCautionData );
		}
		else
			CautionData = null;
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
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
	public String getMobile()
	{
		return Mobile;
	}
	public void setMobile(String aMobile)
	{
		Mobile = aMobile;
	}

	/**
	* 使用另外一个 LAWarrantorSchema 对象给 Schema 赋值
	* @param: aLAWarrantorSchema LAWarrantorSchema
	**/
	public void setSchema(LAWarrantorSchema aLAWarrantorSchema)
	{
		this.AgentCode = aLAWarrantorSchema.getAgentCode();
		this.SerialNo = aLAWarrantorSchema.getSerialNo();
		this.CautionerName = aLAWarrantorSchema.getCautionerName();
		this.CautionerSex = aLAWarrantorSchema.getCautionerSex();
		this.CautionerID = aLAWarrantorSchema.getCautionerID();
		this.CautionerBirthday = fDate.getDate( aLAWarrantorSchema.getCautionerBirthday());
		this.CautionerCom = aLAWarrantorSchema.getCautionerCom();
		this.HomeAddressCode = aLAWarrantorSchema.getHomeAddressCode();
		this.HomeAddress = aLAWarrantorSchema.getHomeAddress();
		this.ZipCode = aLAWarrantorSchema.getZipCode();
		this.Phone = aLAWarrantorSchema.getPhone();
		this.Relation = aLAWarrantorSchema.getRelation();
		this.CautionData = fDate.getDate( aLAWarrantorSchema.getCautionData());
		this.Operator = aLAWarrantorSchema.getOperator();
		this.MakeDate = fDate.getDate( aLAWarrantorSchema.getMakeDate());
		this.MakeTime = aLAWarrantorSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLAWarrantorSchema.getModifyDate());
		this.ModifyTime = aLAWarrantorSchema.getModifyTime();
		this.Mobile = aLAWarrantorSchema.getMobile();
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
			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("CautionerName") == null )
				this.CautionerName = null;
			else
				this.CautionerName = rs.getString("CautionerName").trim();

			if( rs.getString("CautionerSex") == null )
				this.CautionerSex = null;
			else
				this.CautionerSex = rs.getString("CautionerSex").trim();

			if( rs.getString("CautionerID") == null )
				this.CautionerID = null;
			else
				this.CautionerID = rs.getString("CautionerID").trim();

			this.CautionerBirthday = rs.getDate("CautionerBirthday");
			if( rs.getString("CautionerCom") == null )
				this.CautionerCom = null;
			else
				this.CautionerCom = rs.getString("CautionerCom").trim();

			if( rs.getString("HomeAddressCode") == null )
				this.HomeAddressCode = null;
			else
				this.HomeAddressCode = rs.getString("HomeAddressCode").trim();

			if( rs.getString("HomeAddress") == null )
				this.HomeAddress = null;
			else
				this.HomeAddress = rs.getString("HomeAddress").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Relation") == null )
				this.Relation = null;
			else
				this.Relation = rs.getString("Relation").trim();

			this.CautionData = rs.getDate("CautionData");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

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

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LAWarrantor表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWarrantorSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LAWarrantorSchema getSchema()
	{
		LAWarrantorSchema aLAWarrantorSchema = new LAWarrantorSchema();
		aLAWarrantorSchema.setSchema(this);
		return aLAWarrantorSchema;
	}

	public LAWarrantorDB getDB()
	{
		LAWarrantorDB aDBOper = new LAWarrantorDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWarrantor描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(AgentCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerSex)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CautionerBirthday ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CautionerCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddressCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HomeAddress)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ZipCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Phone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Relation)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CautionData ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Mobile));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLAWarrantor>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			CautionerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CautionerSex = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CautionerID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CautionerBirthday = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6,SysConst.PACKAGESPILTER));
			CautionerCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			HomeAddressCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Relation = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CautionData = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LAWarrantorSchema";
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
		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CautionerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerName));
		}
		if (FCode.equalsIgnoreCase("CautionerSex"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerSex));
		}
		if (FCode.equalsIgnoreCase("CautionerID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerID));
		}
		if (FCode.equalsIgnoreCase("CautionerBirthday"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCautionerBirthday()));
		}
		if (FCode.equalsIgnoreCase("CautionerCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CautionerCom));
		}
		if (FCode.equalsIgnoreCase("HomeAddressCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddressCode));
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddress));
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Relation));
		}
		if (FCode.equalsIgnoreCase("CautionData"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCautionData()));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
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
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 1:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CautionerName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CautionerSex);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CautionerID);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCautionerBirthday()));
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(CautionerCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(HomeAddressCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Relation);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCautionData()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
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

		if (FCode.equalsIgnoreCase("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("CautionerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerName = FValue.trim();
			}
			else
				CautionerName = null;
		}
		if (FCode.equalsIgnoreCase("CautionerSex"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerSex = FValue.trim();
			}
			else
				CautionerSex = null;
		}
		if (FCode.equalsIgnoreCase("CautionerID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerID = FValue.trim();
			}
			else
				CautionerID = null;
		}
		if (FCode.equalsIgnoreCase("CautionerBirthday"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CautionerBirthday = fDate.getDate( FValue );
			}
			else
				CautionerBirthday = null;
		}
		if (FCode.equalsIgnoreCase("CautionerCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CautionerCom = FValue.trim();
			}
			else
				CautionerCom = null;
		}
		if (FCode.equalsIgnoreCase("HomeAddressCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeAddressCode = FValue.trim();
			}
			else
				HomeAddressCode = null;
		}
		if (FCode.equalsIgnoreCase("HomeAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeAddress = FValue.trim();
			}
			else
				HomeAddress = null;
		}
		if (FCode.equalsIgnoreCase("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equalsIgnoreCase("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equalsIgnoreCase("Relation"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Relation = FValue.trim();
			}
			else
				Relation = null;
		}
		if (FCode.equalsIgnoreCase("CautionData"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CautionData = fDate.getDate( FValue );
			}
			else
				CautionData = null;
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
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
		if (FCode.equalsIgnoreCase("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LAWarrantorSchema other = (LAWarrantorSchema)otherObject;
		return
			AgentCode.equals(other.getAgentCode())
			&& SerialNo == other.getSerialNo()
			&& CautionerName.equals(other.getCautionerName())
			&& CautionerSex.equals(other.getCautionerSex())
			&& CautionerID.equals(other.getCautionerID())
			&& fDate.getString(CautionerBirthday).equals(other.getCautionerBirthday())
			&& CautionerCom.equals(other.getCautionerCom())
			&& HomeAddressCode.equals(other.getHomeAddressCode())
			&& HomeAddress.equals(other.getHomeAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Relation.equals(other.getRelation())
			&& fDate.getString(CautionData).equals(other.getCautionData())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Mobile.equals(other.getMobile());
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
		if( strFieldName.equals("AgentCode") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("CautionerName") ) {
			return 2;
		}
		if( strFieldName.equals("CautionerSex") ) {
			return 3;
		}
		if( strFieldName.equals("CautionerID") ) {
			return 4;
		}
		if( strFieldName.equals("CautionerBirthday") ) {
			return 5;
		}
		if( strFieldName.equals("CautionerCom") ) {
			return 6;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return 7;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 8;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 9;
		}
		if( strFieldName.equals("Phone") ) {
			return 10;
		}
		if( strFieldName.equals("Relation") ) {
			return 11;
		}
		if( strFieldName.equals("CautionData") ) {
			return 12;
		}
		if( strFieldName.equals("Operator") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("Mobile") ) {
			return 18;
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
				strFieldName = "AgentCode";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "CautionerName";
				break;
			case 3:
				strFieldName = "CautionerSex";
				break;
			case 4:
				strFieldName = "CautionerID";
				break;
			case 5:
				strFieldName = "CautionerBirthday";
				break;
			case 6:
				strFieldName = "CautionerCom";
				break;
			case 7:
				strFieldName = "HomeAddressCode";
				break;
			case 8:
				strFieldName = "HomeAddress";
				break;
			case 9:
				strFieldName = "ZipCode";
				break;
			case 10:
				strFieldName = "Phone";
				break;
			case 11:
				strFieldName = "Relation";
				break;
			case 12:
				strFieldName = "CautionData";
				break;
			case 13:
				strFieldName = "Operator";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
				strFieldName = "Mobile";
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
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CautionerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerSex") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionerBirthday") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CautionerCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddressCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Relation") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CautionData") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Operator") ) {
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
		if( strFieldName.equals("Mobile") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
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
			case 16:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
