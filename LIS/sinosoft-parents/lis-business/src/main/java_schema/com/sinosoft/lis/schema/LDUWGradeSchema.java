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
import com.sinosoft.lis.db.LDUWGradeDB;

/*
 * <p>ClassName: LDUWGradeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWGradeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWGradeSchema.class);

	// @Field
	/** 核保师编码 */
	private String UserCode;
	/** 核保师权限级别 */
	private String UWPopedom;
	/** 核保师级别名称 */
	private String UWPopedomName;
	/** 业务类型 */
	private String UWType;
	/** 核保结论代码 */
	private String UWState;
	/** 核保结论名称 */
	private String UWStateName;
	/** 保险金额 */
	private double Amnt;
	/** 加费比例 */
	private double AddPrem;
	/** 拒保金额 */
	private double Refuse;
	/** 备用字段1 */
	private String StandbyFlag1;
	/** 备用字段2 */
	private String StandbyFlag2;
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

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWGradeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "UserCode";
		pk[1] = "UWPopedom";
		pk[2] = "UWType";
		pk[3] = "UWState";

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
		LDUWGradeSchema cloned = (LDUWGradeSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getUserCode()
	{
		return UserCode;
	}
	public void setUserCode(String aUserCode)
	{
		UserCode = aUserCode;
	}
	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		UWPopedom = aUWPopedom;
	}
	public String getUWPopedomName()
	{
		return UWPopedomName;
	}
	public void setUWPopedomName(String aUWPopedomName)
	{
		UWPopedomName = aUWPopedomName;
	}
	/**
	* 1-个险<p>
	* 2-团险
	*/
	public String getUWType()
	{
		return UWType;
	}
	public void setUWType(String aUWType)
	{
		UWType = aUWType;
	}
	public String getUWState()
	{
		return UWState;
	}
	public void setUWState(String aUWState)
	{
		UWState = aUWState;
	}
	public String getUWStateName()
	{
		return UWStateName;
	}
	public void setUWStateName(String aUWStateName)
	{
		UWStateName = aUWStateName;
	}
	public double getAmnt()
	{
		return Amnt;
	}
	public void setAmnt(double aAmnt)
	{
		Amnt = aAmnt;
	}
	public void setAmnt(String aAmnt)
	{
		if (aAmnt != null && !aAmnt.equals(""))
		{
			Double tDouble = new Double(aAmnt);
			double d = tDouble.doubleValue();
			Amnt = d;
		}
	}

	public double getAddPrem()
	{
		return AddPrem;
	}
	public void setAddPrem(double aAddPrem)
	{
		AddPrem = aAddPrem;
	}
	public void setAddPrem(String aAddPrem)
	{
		if (aAddPrem != null && !aAddPrem.equals(""))
		{
			Double tDouble = new Double(aAddPrem);
			double d = tDouble.doubleValue();
			AddPrem = d;
		}
	}

	public double getRefuse()
	{
		return Refuse;
	}
	public void setRefuse(double aRefuse)
	{
		Refuse = aRefuse;
	}
	public void setRefuse(String aRefuse)
	{
		if (aRefuse != null && !aRefuse.equals(""))
		{
			Double tDouble = new Double(aRefuse);
			double d = tDouble.doubleValue();
			Refuse = d;
		}
	}

	public String getStandbyFlag1()
	{
		return StandbyFlag1;
	}
	public void setStandbyFlag1(String aStandbyFlag1)
	{
		StandbyFlag1 = aStandbyFlag1;
	}
	public String getStandbyFlag2()
	{
		return StandbyFlag2;
	}
	public void setStandbyFlag2(String aStandbyFlag2)
	{
		StandbyFlag2 = aStandbyFlag2;
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

	/**
	* 使用另外一个 LDUWGradeSchema 对象给 Schema 赋值
	* @param: aLDUWGradeSchema LDUWGradeSchema
	**/
	public void setSchema(LDUWGradeSchema aLDUWGradeSchema)
	{
		this.UserCode = aLDUWGradeSchema.getUserCode();
		this.UWPopedom = aLDUWGradeSchema.getUWPopedom();
		this.UWPopedomName = aLDUWGradeSchema.getUWPopedomName();
		this.UWType = aLDUWGradeSchema.getUWType();
		this.UWState = aLDUWGradeSchema.getUWState();
		this.UWStateName = aLDUWGradeSchema.getUWStateName();
		this.Amnt = aLDUWGradeSchema.getAmnt();
		this.AddPrem = aLDUWGradeSchema.getAddPrem();
		this.Refuse = aLDUWGradeSchema.getRefuse();
		this.StandbyFlag1 = aLDUWGradeSchema.getStandbyFlag1();
		this.StandbyFlag2 = aLDUWGradeSchema.getStandbyFlag2();
		this.Operator = aLDUWGradeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDUWGradeSchema.getMakeDate());
		this.MakeTime = aLDUWGradeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDUWGradeSchema.getModifyDate());
		this.ModifyTime = aLDUWGradeSchema.getModifyTime();
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

			if( rs.getString("UWPopedomName") == null )
				this.UWPopedomName = null;
			else
				this.UWPopedomName = rs.getString("UWPopedomName").trim();

			if( rs.getString("UWType") == null )
				this.UWType = null;
			else
				this.UWType = rs.getString("UWType").trim();

			if( rs.getString("UWState") == null )
				this.UWState = null;
			else
				this.UWState = rs.getString("UWState").trim();

			if( rs.getString("UWStateName") == null )
				this.UWStateName = null;
			else
				this.UWStateName = rs.getString("UWStateName").trim();

			this.Amnt = rs.getDouble("Amnt");
			this.AddPrem = rs.getDouble("AddPrem");
			this.Refuse = rs.getDouble("Refuse");
			if( rs.getString("StandbyFlag1") == null )
				this.StandbyFlag1 = null;
			else
				this.StandbyFlag1 = rs.getString("StandbyFlag1").trim();

			if( rs.getString("StandbyFlag2") == null )
				this.StandbyFlag2 = null;
			else
				this.StandbyFlag2 = rs.getString("StandbyFlag2").trim();

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

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDUWGrade表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGradeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWGradeSchema getSchema()
	{
		LDUWGradeSchema aLDUWGradeSchema = new LDUWGradeSchema();
		aLDUWGradeSchema.setSchema(this);
		return aLDUWGradeSchema;
	}

	public LDUWGradeDB getDB()
	{
		LDUWGradeDB aDBOper = new LDUWGradeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGrade描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWStateName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Amnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AddPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Refuse));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StandbyFlag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWGrade>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			UWPopedomName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			UWType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UWState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UWStateName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Amnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			AddPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			Refuse = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			StandbyFlag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			StandbyFlag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWGradeSchema";
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
		if (FCode.equalsIgnoreCase("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
		}
		if (FCode.equalsIgnoreCase("UWPopedomName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedomName));
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWType));
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWState));
		}
		if (FCode.equalsIgnoreCase("UWStateName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWStateName));
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Amnt));
		}
		if (FCode.equalsIgnoreCase("AddPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPrem));
		}
		if (FCode.equalsIgnoreCase("Refuse"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Refuse));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag1));
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandbyFlag2));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(UWPopedomName);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(UWType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(UWState);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UWStateName);
				break;
			case 6:
				strFieldValue = String.valueOf(Amnt);
				break;
			case 7:
				strFieldValue = String.valueOf(AddPrem);
				break;
			case 8:
				strFieldValue = String.valueOf(Refuse);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(StandbyFlag2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equalsIgnoreCase("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equalsIgnoreCase("UWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedom = FValue.trim();
			}
			else
				UWPopedom = null;
		}
		if (FCode.equalsIgnoreCase("UWPopedomName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedomName = FValue.trim();
			}
			else
				UWPopedomName = null;
		}
		if (FCode.equalsIgnoreCase("UWType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWType = FValue.trim();
			}
			else
				UWType = null;
		}
		if (FCode.equalsIgnoreCase("UWState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWState = FValue.trim();
			}
			else
				UWState = null;
		}
		if (FCode.equalsIgnoreCase("UWStateName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWStateName = FValue.trim();
			}
			else
				UWStateName = null;
		}
		if (FCode.equalsIgnoreCase("Amnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Amnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("AddPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AddPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Refuse"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Refuse = d;
			}
		}
		if (FCode.equalsIgnoreCase("StandbyFlag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag1 = FValue.trim();
			}
			else
				StandbyFlag1 = null;
		}
		if (FCode.equalsIgnoreCase("StandbyFlag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandbyFlag2 = FValue.trim();
			}
			else
				StandbyFlag2 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUWGradeSchema other = (LDUWGradeSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UWPopedom.equals(other.getUWPopedom())
			&& UWPopedomName.equals(other.getUWPopedomName())
			&& UWType.equals(other.getUWType())
			&& UWState.equals(other.getUWState())
			&& UWStateName.equals(other.getUWStateName())
			&& Amnt == other.getAmnt()
			&& AddPrem == other.getAddPrem()
			&& Refuse == other.getRefuse()
			&& StandbyFlag1.equals(other.getStandbyFlag1())
			&& StandbyFlag2.equals(other.getStandbyFlag2())
			&& Operator.equals(other.getOperator())
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return 1;
		}
		if( strFieldName.equals("UWPopedomName") ) {
			return 2;
		}
		if( strFieldName.equals("UWType") ) {
			return 3;
		}
		if( strFieldName.equals("UWState") ) {
			return 4;
		}
		if( strFieldName.equals("UWStateName") ) {
			return 5;
		}
		if( strFieldName.equals("Amnt") ) {
			return 6;
		}
		if( strFieldName.equals("AddPrem") ) {
			return 7;
		}
		if( strFieldName.equals("Refuse") ) {
			return 8;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return 9;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "UWPopedom";
				break;
			case 2:
				strFieldName = "UWPopedomName";
				break;
			case 3:
				strFieldName = "UWType";
				break;
			case 4:
				strFieldName = "UWState";
				break;
			case 5:
				strFieldName = "UWStateName";
				break;
			case 6:
				strFieldName = "Amnt";
				break;
			case 7:
				strFieldName = "AddPrem";
				break;
			case 8:
				strFieldName = "Refuse";
				break;
			case 9:
				strFieldName = "StandbyFlag1";
				break;
			case 10:
				strFieldName = "StandbyFlag2";
				break;
			case 11:
				strFieldName = "Operator";
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedomName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWStateName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Amnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AddPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Refuse") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StandbyFlag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandbyFlag2") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 7:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DOUBLE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
