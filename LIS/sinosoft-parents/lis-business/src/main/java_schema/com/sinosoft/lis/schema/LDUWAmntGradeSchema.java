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
import com.sinosoft.lis.db.LDUWAmntGradeDB;

/*
 * <p>ClassName: LDUWAmntGradeSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 权限管理
 */
public class LDUWAmntGradeSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDUWAmntGradeSchema.class);
	// @Field
	/** 核保师编码 */
	private String UserCode;
	/** 核保师权限级别 */
	private String UWPopedom;
	/** 核保师级别名称 */
	private String UWPopedomName;
	/** 业务类型 */
	private String UWType;
	/** 险类 */
	private String UWKind;
	/** 保额上限 */
	private double MaxAmnt;
	/** 保费上限 */
	private double MaxPrem;
	/** 费率 */
	private double Rate;
	/** 其它费率 */
	private double OthRate;
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

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUWAmntGradeSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "UserCode";
		pk[1] = "UWPopedom";
		pk[2] = "UWType";
		pk[3] = "UWKind";

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
		LDUWAmntGradeSchema cloned = (LDUWAmntGradeSchema)super.clone();
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
		if(aUserCode!=null && aUserCode.length()>10)
			throw new IllegalArgumentException("核保师编码UserCode值"+aUserCode+"的长度"+aUserCode.length()+"大于最大值10");
		UserCode = aUserCode;
	}
	public String getUWPopedom()
	{
		return UWPopedom;
	}
	public void setUWPopedom(String aUWPopedom)
	{
		if(aUWPopedom!=null && aUWPopedom.length()>10)
			throw new IllegalArgumentException("核保师权限级别UWPopedom值"+aUWPopedom+"的长度"+aUWPopedom.length()+"大于最大值10");
		UWPopedom = aUWPopedom;
	}
	public String getUWPopedomName()
	{
		return UWPopedomName;
	}
	public void setUWPopedomName(String aUWPopedomName)
	{
		if(aUWPopedomName!=null && aUWPopedomName.length()>200)
			throw new IllegalArgumentException("核保师级别名称UWPopedomName值"+aUWPopedomName+"的长度"+aUWPopedomName.length()+"大于最大值200");
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
		if(aUWType!=null && aUWType.length()>1)
			throw new IllegalArgumentException("业务类型UWType值"+aUWType+"的长度"+aUWType.length()+"大于最大值1");
		UWType = aUWType;
	}
	/**
	* 定义险种分类权限
	*/
	public String getUWKind()
	{
		return UWKind;
	}
	public void setUWKind(String aUWKind)
	{
		if(aUWKind!=null && aUWKind.length()>8)
			throw new IllegalArgumentException("险类UWKind值"+aUWKind+"的长度"+aUWKind.length()+"大于最大值8");
		UWKind = aUWKind;
	}
	public double getMaxAmnt()
	{
		return MaxAmnt;
	}
	public void setMaxAmnt(double aMaxAmnt)
	{
		MaxAmnt = aMaxAmnt;
	}
	public void setMaxAmnt(String aMaxAmnt)
	{
		if (aMaxAmnt != null && !aMaxAmnt.equals(""))
		{
			Double tDouble = new Double(aMaxAmnt);
			double d = tDouble.doubleValue();
			MaxAmnt = d;
		}
	}

	public double getMaxPrem()
	{
		return MaxPrem;
	}
	public void setMaxPrem(double aMaxPrem)
	{
		MaxPrem = aMaxPrem;
	}
	public void setMaxPrem(String aMaxPrem)
	{
		if (aMaxPrem != null && !aMaxPrem.equals(""))
		{
			Double tDouble = new Double(aMaxPrem);
			double d = tDouble.doubleValue();
			MaxPrem = d;
		}
	}

	public double getRate()
	{
		return Rate;
	}
	public void setRate(double aRate)
	{
		Rate = aRate;
	}
	public void setRate(String aRate)
	{
		if (aRate != null && !aRate.equals(""))
		{
			Double tDouble = new Double(aRate);
			double d = tDouble.doubleValue();
			Rate = d;
		}
	}

	public double getOthRate()
	{
		return OthRate;
	}
	public void setOthRate(double aOthRate)
	{
		OthRate = aOthRate;
	}
	public void setOthRate(String aOthRate)
	{
		if (aOthRate != null && !aOthRate.equals(""))
		{
			Double tDouble = new Double(aOthRate);
			double d = tDouble.doubleValue();
			OthRate = d;
		}
	}

	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员代码Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
		if(aMakeTime!=null && aMakeTime.length()>8)
			throw new IllegalArgumentException("入机时间MakeTime值"+aMakeTime+"的长度"+aMakeTime.length()+"大于最大值8");
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
		if(aModifyTime!=null && aModifyTime.length()>8)
			throw new IllegalArgumentException("最后一次修改时间ModifyTime值"+aModifyTime+"的长度"+aModifyTime.length()+"大于最大值8");
		ModifyTime = aModifyTime;
	}

	/**
	* 使用另外一个 LDUWAmntGradeSchema 对象给 Schema 赋值
	* @param: aLDUWAmntGradeSchema LDUWAmntGradeSchema
	**/
	public void setSchema(LDUWAmntGradeSchema aLDUWAmntGradeSchema)
	{
		this.UserCode = aLDUWAmntGradeSchema.getUserCode();
		this.UWPopedom = aLDUWAmntGradeSchema.getUWPopedom();
		this.UWPopedomName = aLDUWAmntGradeSchema.getUWPopedomName();
		this.UWType = aLDUWAmntGradeSchema.getUWType();
		this.UWKind = aLDUWAmntGradeSchema.getUWKind();
		this.MaxAmnt = aLDUWAmntGradeSchema.getMaxAmnt();
		this.MaxPrem = aLDUWAmntGradeSchema.getMaxPrem();
		this.Rate = aLDUWAmntGradeSchema.getRate();
		this.OthRate = aLDUWAmntGradeSchema.getOthRate();
		this.Operator = aLDUWAmntGradeSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDUWAmntGradeSchema.getMakeDate());
		this.MakeTime = aLDUWAmntGradeSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDUWAmntGradeSchema.getModifyDate());
		this.ModifyTime = aLDUWAmntGradeSchema.getModifyTime();
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

			if( rs.getString("UWKind") == null )
				this.UWKind = null;
			else
				this.UWKind = rs.getString("UWKind").trim();

			this.MaxAmnt = rs.getDouble("MaxAmnt");
			this.MaxPrem = rs.getDouble("MaxPrem");
			this.Rate = rs.getDouble("Rate");
			this.OthRate = rs.getDouble("OthRate");
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
			logger.debug("数据库中的LDUWAmntGrade表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWAmntGradeSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDUWAmntGradeSchema getSchema()
	{
		LDUWAmntGradeSchema aLDUWAmntGradeSchema = new LDUWAmntGradeSchema();
		aLDUWAmntGradeSchema.setSchema(this);
		return aLDUWAmntGradeSchema;
	}

	public LDUWAmntGradeDB getDB()
	{
		LDUWAmntGradeDB aDBOper = new LDUWAmntGradeDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWAmntGrade描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(UserCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWPopedomName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWKind)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(MaxPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Rate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OthRate));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUWAmntGrade>历史记账凭证主表信息</A>表字段
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
			UWKind = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			MaxAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,6,SysConst.PACKAGESPILTER))).doubleValue();
			MaxPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).doubleValue();
			Rate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			OthRate = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUWAmntGradeSchema";
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
		if (FCode.equalsIgnoreCase("UWKind"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWKind));
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxAmnt));
		}
		if (FCode.equalsIgnoreCase("MaxPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MaxPrem));
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Rate));
		}
		if (FCode.equalsIgnoreCase("OthRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OthRate));
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
				strFieldValue = StrTool.GBKToUnicode(UWKind);
				break;
			case 5:
				strFieldValue = String.valueOf(MaxAmnt);
				break;
			case 6:
				strFieldValue = String.valueOf(MaxPrem);
				break;
			case 7:
				strFieldValue = String.valueOf(Rate);
				break;
			case 8:
				strFieldValue = String.valueOf(OthRate);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 13:
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
		if (FCode.equalsIgnoreCase("UWKind"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWKind = FValue.trim();
			}
			else
				UWKind = null;
		}
		if (FCode.equalsIgnoreCase("MaxAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("MaxPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				MaxPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("Rate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Rate = d;
			}
		}
		if (FCode.equalsIgnoreCase("OthRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OthRate = d;
			}
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
		LDUWAmntGradeSchema other = (LDUWAmntGradeSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UWPopedom.equals(other.getUWPopedom())
			&& UWPopedomName.equals(other.getUWPopedomName())
			&& UWType.equals(other.getUWType())
			&& UWKind.equals(other.getUWKind())
			&& MaxAmnt == other.getMaxAmnt()
			&& MaxPrem == other.getMaxPrem()
			&& Rate == other.getRate()
			&& OthRate == other.getOthRate()
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
		if( strFieldName.equals("UWKind") ) {
			return 4;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return 5;
		}
		if( strFieldName.equals("MaxPrem") ) {
			return 6;
		}
		if( strFieldName.equals("Rate") ) {
			return 7;
		}
		if( strFieldName.equals("OthRate") ) {
			return 8;
		}
		if( strFieldName.equals("Operator") ) {
			return 9;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 10;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 11;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "UWKind";
				break;
			case 5:
				strFieldName = "MaxAmnt";
				break;
			case 6:
				strFieldName = "MaxPrem";
				break;
			case 7:
				strFieldName = "Rate";
				break;
			case 8:
				strFieldName = "OthRate";
				break;
			case 9:
				strFieldName = "Operator";
				break;
			case 10:
				strFieldName = "MakeDate";
				break;
			case 11:
				strFieldName = "MakeTime";
				break;
			case 12:
				strFieldName = "ModifyDate";
				break;
			case 13:
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
		if( strFieldName.equals("UWKind") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MaxAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("MaxPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Rate") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OthRate") ) {
			return Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
