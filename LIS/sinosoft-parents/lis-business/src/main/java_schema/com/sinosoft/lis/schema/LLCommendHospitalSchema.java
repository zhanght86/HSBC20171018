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
import com.sinosoft.lis.db.LLCommendHospitalDB;

/*
 * <p>ClassName: LLCommendHospitalSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLCommendHospitalSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLCommendHospitalSchema.class);

	// @Field
	/** 咨询通知号码 */
	private String ConsultNo;
	/** 医院代码 */
	private String HospitalCode;
	/** 医院名称 */
	private String HospitalName;
	/** 医院等级 */
	private String HosAtti;
	/** 医院状态 */
	private String HosState;
	/** 定点标志 */
	private String ConFlag;
	/** 残疾鉴定资质标志 */
	private String AppFlag;
	/** 医院地址 */
	private String HosAddr;
	/** 医院电话 */
	private String HosPhone;
	/** 操作员 */
	private String Operator;
	/** 管理机构 */
	private String MngCom;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 15;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLCommendHospitalSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "HospitalCode";

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
		LLCommendHospitalSchema cloned = (LLCommendHospitalSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getConsultNo()
	{
		return ConsultNo;
	}
	public void setConsultNo(String aConsultNo)
	{
		ConsultNo = aConsultNo;
	}
	public String getHospitalCode()
	{
		return HospitalCode;
	}
	public void setHospitalCode(String aHospitalCode)
	{
		HospitalCode = aHospitalCode;
	}
	public String getHospitalName()
	{
		return HospitalName;
	}
	public void setHospitalName(String aHospitalName)
	{
		HospitalName = aHospitalName;
	}
	public String getHosAtti()
	{
		return HosAtti;
	}
	public void setHosAtti(String aHosAtti)
	{
		HosAtti = aHosAtti;
	}
	/**
	* 0有效<p>
	* 1暂停<p>
	* 2终止
	*/
	public String getHosState()
	{
		return HosState;
	}
	public void setHosState(String aHosState)
	{
		HosState = aHosState;
	}
	/**
	* 0定点<p>
	* 1非定点
	*/
	public String getConFlag()
	{
		return ConFlag;
	}
	public void setConFlag(String aConFlag)
	{
		ConFlag = aConFlag;
	}
	/**
	* 0有资质<p>
	* 1无资质
	*/
	public String getAppFlag()
	{
		return AppFlag;
	}
	public void setAppFlag(String aAppFlag)
	{
		AppFlag = aAppFlag;
	}
	public String getHosAddr()
	{
		return HosAddr;
	}
	public void setHosAddr(String aHosAddr)
	{
		HosAddr = aHosAddr;
	}
	public String getHosPhone()
	{
		return HosPhone;
	}
	public void setHosPhone(String aHosPhone)
	{
		HosPhone = aHosPhone;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		MngCom = aMngCom;
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
	* 使用另外一个 LLCommendHospitalSchema 对象给 Schema 赋值
	* @param: aLLCommendHospitalSchema LLCommendHospitalSchema
	**/
	public void setSchema(LLCommendHospitalSchema aLLCommendHospitalSchema)
	{
		this.ConsultNo = aLLCommendHospitalSchema.getConsultNo();
		this.HospitalCode = aLLCommendHospitalSchema.getHospitalCode();
		this.HospitalName = aLLCommendHospitalSchema.getHospitalName();
		this.HosAtti = aLLCommendHospitalSchema.getHosAtti();
		this.HosState = aLLCommendHospitalSchema.getHosState();
		this.ConFlag = aLLCommendHospitalSchema.getConFlag();
		this.AppFlag = aLLCommendHospitalSchema.getAppFlag();
		this.HosAddr = aLLCommendHospitalSchema.getHosAddr();
		this.HosPhone = aLLCommendHospitalSchema.getHosPhone();
		this.Operator = aLLCommendHospitalSchema.getOperator();
		this.MngCom = aLLCommendHospitalSchema.getMngCom();
		this.MakeDate = fDate.getDate( aLLCommendHospitalSchema.getMakeDate());
		this.MakeTime = aLLCommendHospitalSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLCommendHospitalSchema.getModifyDate());
		this.ModifyTime = aLLCommendHospitalSchema.getModifyTime();
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
			if( rs.getString("ConsultNo") == null )
				this.ConsultNo = null;
			else
				this.ConsultNo = rs.getString("ConsultNo").trim();

			if( rs.getString("HospitalCode") == null )
				this.HospitalCode = null;
			else
				this.HospitalCode = rs.getString("HospitalCode").trim();

			if( rs.getString("HospitalName") == null )
				this.HospitalName = null;
			else
				this.HospitalName = rs.getString("HospitalName").trim();

			if( rs.getString("HosAtti") == null )
				this.HosAtti = null;
			else
				this.HosAtti = rs.getString("HosAtti").trim();

			if( rs.getString("HosState") == null )
				this.HosState = null;
			else
				this.HosState = rs.getString("HosState").trim();

			if( rs.getString("ConFlag") == null )
				this.ConFlag = null;
			else
				this.ConFlag = rs.getString("ConFlag").trim();

			if( rs.getString("AppFlag") == null )
				this.AppFlag = null;
			else
				this.AppFlag = rs.getString("AppFlag").trim();

			if( rs.getString("HosAddr") == null )
				this.HosAddr = null;
			else
				this.HosAddr = rs.getString("HosAddr").trim();

			if( rs.getString("HosPhone") == null )
				this.HosPhone = null;
			else
				this.HosPhone = rs.getString("HosPhone").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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
			logger.debug("数据库中的LLCommendHospital表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLCommendHospitalSchema getSchema()
	{
		LLCommendHospitalSchema aLLCommendHospitalSchema = new LLCommendHospitalSchema();
		aLLCommendHospitalSchema.setSchema(this);
		return aLLCommendHospitalSchema;
	}

	public LLCommendHospitalDB getDB()
	{
		LLCommendHospitalDB aDBOper = new LLCommendHospitalDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCommendHospital描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ConsultNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HospitalName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosAtti)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosAddr)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(HosPhone)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLCommendHospital>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ConsultNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			HospitalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			HospitalName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			HosAtti = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			HosState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ConFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AppFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			HosAddr = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HosPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLCommendHospitalSchema";
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
		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConsultNo));
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalCode));
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HospitalName));
		}
		if (FCode.equalsIgnoreCase("HosAtti"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosAtti));
		}
		if (FCode.equalsIgnoreCase("HosState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosState));
		}
		if (FCode.equalsIgnoreCase("ConFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConFlag));
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppFlag));
		}
		if (FCode.equalsIgnoreCase("HosAddr"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosAddr));
		}
		if (FCode.equalsIgnoreCase("HosPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HosPhone));
		}
		if (FCode.equalsIgnoreCase("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
				strFieldValue = StrTool.GBKToUnicode(ConsultNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(HospitalCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(HospitalName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(HosAtti);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(HosState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ConFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AppFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(HosAddr);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HosPhone);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 14:
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

		if (FCode.equalsIgnoreCase("ConsultNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConsultNo = FValue.trim();
			}
			else
				ConsultNo = null;
		}
		if (FCode.equalsIgnoreCase("HospitalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalCode = FValue.trim();
			}
			else
				HospitalCode = null;
		}
		if (FCode.equalsIgnoreCase("HospitalName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HospitalName = FValue.trim();
			}
			else
				HospitalName = null;
		}
		if (FCode.equalsIgnoreCase("HosAtti"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosAtti = FValue.trim();
			}
			else
				HosAtti = null;
		}
		if (FCode.equalsIgnoreCase("HosState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosState = FValue.trim();
			}
			else
				HosState = null;
		}
		if (FCode.equalsIgnoreCase("ConFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConFlag = FValue.trim();
			}
			else
				ConFlag = null;
		}
		if (FCode.equalsIgnoreCase("AppFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppFlag = FValue.trim();
			}
			else
				AppFlag = null;
		}
		if (FCode.equalsIgnoreCase("HosAddr"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosAddr = FValue.trim();
			}
			else
				HosAddr = null;
		}
		if (FCode.equalsIgnoreCase("HosPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HosPhone = FValue.trim();
			}
			else
				HosPhone = null;
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
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MngCom = FValue.trim();
			}
			else
				MngCom = null;
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
		LLCommendHospitalSchema other = (LLCommendHospitalSchema)otherObject;
		return
			ConsultNo.equals(other.getConsultNo())
			&& HospitalCode.equals(other.getHospitalCode())
			&& HospitalName.equals(other.getHospitalName())
			&& HosAtti.equals(other.getHosAtti())
			&& HosState.equals(other.getHosState())
			&& ConFlag.equals(other.getConFlag())
			&& AppFlag.equals(other.getAppFlag())
			&& HosAddr.equals(other.getHosAddr())
			&& HosPhone.equals(other.getHosPhone())
			&& Operator.equals(other.getOperator())
			&& MngCom.equals(other.getMngCom())
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
		if( strFieldName.equals("ConsultNo") ) {
			return 0;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return 1;
		}
		if( strFieldName.equals("HospitalName") ) {
			return 2;
		}
		if( strFieldName.equals("HosAtti") ) {
			return 3;
		}
		if( strFieldName.equals("HosState") ) {
			return 4;
		}
		if( strFieldName.equals("ConFlag") ) {
			return 5;
		}
		if( strFieldName.equals("AppFlag") ) {
			return 6;
		}
		if( strFieldName.equals("HosAddr") ) {
			return 7;
		}
		if( strFieldName.equals("HosPhone") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MngCom") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 14;
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
				strFieldName = "ConsultNo";
				break;
			case 1:
				strFieldName = "HospitalCode";
				break;
			case 2:
				strFieldName = "HospitalName";
				break;
			case 3:
				strFieldName = "HosAtti";
				break;
			case 4:
				strFieldName = "HosState";
				break;
			case 5:
				strFieldName = "ConFlag";
				break;
			case 6:
				strFieldName = "AppFlag";
				break;
			case 7:
				strFieldName = "HosAddr";
				break;
			case 8:
				strFieldName = "HosPhone";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MngCom";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyDate";
				break;
			case 14:
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
		if( strFieldName.equals("ConsultNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HospitalName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosAtti") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosAddr") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HosPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
