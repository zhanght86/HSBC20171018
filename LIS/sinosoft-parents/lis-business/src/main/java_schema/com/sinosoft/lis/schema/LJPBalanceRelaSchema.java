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
import com.sinosoft.lis.db.LJPBalanceRelaDB;

/*
 * <p>ClassName: LJPBalanceRelaSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPBalanceRelaSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPBalanceRelaSchema.class);
	// @Field
	/** 定期结算关联号码 */
	private String BalanceRelaNo;
	/** 定期结算汇总号码 */
	private String BalanceAllNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 本次定期结算方式 */
	private String BalanceType;
	/** 定期结算关联金额合计 */
	private double BalanceRelaSum;
	/** 定期结算关联操作员 */
	private String BalanceRelaOperator;
	/** 定期结算关联收付类型 */
	private String BalanceRelaType;
	/** 定期结算关联收付状态 */
	private String BalanceRelaState;
	/** 定期结算关联日期 */
	private Date BalanceRelaDate;
	/** 定期结算关联时间 */
	private String BalanceRelaTime;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPBalanceRelaSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BalanceRelaNo";

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
		LJPBalanceRelaSchema cloned = (LJPBalanceRelaSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 财务人员根据需要选择某些保全定期结算业务进行关联
	*/
	public String getBalanceRelaNo()
	{
		return BalanceRelaNo;
	}
	public void setBalanceRelaNo(String aBalanceRelaNo)
	{
		if(aBalanceRelaNo!=null && aBalanceRelaNo.length()>20)
			throw new IllegalArgumentException("定期结算关联号码BalanceRelaNo值"+aBalanceRelaNo+"的长度"+aBalanceRelaNo.length()+"大于最大值20");
		BalanceRelaNo = aBalanceRelaNo;
	}
	public String getBalanceAllNo()
	{
		return BalanceAllNo;
	}
	public void setBalanceAllNo(String aBalanceAllNo)
	{
		if(aBalanceAllNo!=null && aBalanceAllNo.length()>20)
			throw new IllegalArgumentException("定期结算汇总号码BalanceAllNo值"+aBalanceAllNo+"的长度"+aBalanceAllNo.length()+"大于最大值20");
		BalanceAllNo = aBalanceAllNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("团体保单号GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	/**
	* 定期结算方式：<p>
	* -1 －未定期结算(在本表存储中无该方式)<p>
	* 0 －到达结算周期<p>
	* 1 －到达金额上限<p>
	* 2 －手动定期结算
	*/
	public String getBalanceType()
	{
		return BalanceType;
	}
	public void setBalanceType(String aBalanceType)
	{
		if(aBalanceType!=null && aBalanceType.length()>2)
			throw new IllegalArgumentException("本次定期结算方式BalanceType值"+aBalanceType+"的长度"+aBalanceType.length()+"大于最大值2");
		BalanceType = aBalanceType;
	}
	public double getBalanceRelaSum()
	{
		return BalanceRelaSum;
	}
	public void setBalanceRelaSum(double aBalanceRelaSum)
	{
		BalanceRelaSum = aBalanceRelaSum;
	}
	public void setBalanceRelaSum(String aBalanceRelaSum)
	{
		if (aBalanceRelaSum != null && !aBalanceRelaSum.equals(""))
		{
			Double tDouble = new Double(aBalanceRelaSum);
			double d = tDouble.doubleValue();
			BalanceRelaSum = d;
		}
	}

	public String getBalanceRelaOperator()
	{
		return BalanceRelaOperator;
	}
	public void setBalanceRelaOperator(String aBalanceRelaOperator)
	{
		if(aBalanceRelaOperator!=null && aBalanceRelaOperator.length()>30)
			throw new IllegalArgumentException("定期结算关联操作员BalanceRelaOperator值"+aBalanceRelaOperator+"的长度"+aBalanceRelaOperator.length()+"大于最大值30");
		BalanceRelaOperator = aBalanceRelaOperator;
	}
	/**
	* 0 －收付抵消<p>
	* 1 －收费<p>
	* 2 －付费
	*/
	public String getBalanceRelaType()
	{
		return BalanceRelaType;
	}
	public void setBalanceRelaType(String aBalanceRelaType)
	{
		if(aBalanceRelaType!=null && aBalanceRelaType.length()>2)
			throw new IllegalArgumentException("定期结算关联收付类型BalanceRelaType值"+aBalanceRelaType+"的长度"+aBalanceRelaType.length()+"大于最大值2");
		BalanceRelaType = aBalanceRelaType;
	}
	/**
	* 0 －未进行收付费<p>
	* 1 －已进行收付费
	*/
	public String getBalanceRelaState()
	{
		return BalanceRelaState;
	}
	public void setBalanceRelaState(String aBalanceRelaState)
	{
		if(aBalanceRelaState!=null && aBalanceRelaState.length()>2)
			throw new IllegalArgumentException("定期结算关联收付状态BalanceRelaState值"+aBalanceRelaState+"的长度"+aBalanceRelaState.length()+"大于最大值2");
		BalanceRelaState = aBalanceRelaState;
	}
	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理日期
	*/
	public String getBalanceRelaDate()
	{
		if( BalanceRelaDate != null )
			return fDate.getString(BalanceRelaDate);
		else
			return null;
	}
	public void setBalanceRelaDate(Date aBalanceRelaDate)
	{
		BalanceRelaDate = aBalanceRelaDate;
	}
	public void setBalanceRelaDate(String aBalanceRelaDate)
	{
		if (aBalanceRelaDate != null && !aBalanceRelaDate.equals("") )
		{
			BalanceRelaDate = fDate.getDate( aBalanceRelaDate );
		}
		else
			BalanceRelaDate = null;
	}

	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理时间
	*/
	public String getBalanceRelaTime()
	{
		return BalanceRelaTime;
	}
	public void setBalanceRelaTime(String aBalanceRelaTime)
	{
		if(aBalanceRelaTime!=null && aBalanceRelaTime.length()>8)
			throw new IllegalArgumentException("定期结算关联时间BalanceRelaTime值"+aBalanceRelaTime+"的长度"+aBalanceRelaTime.length()+"大于最大值8");
		BalanceRelaTime = aBalanceRelaTime;
	}

	/**
	* 使用另外一个 LJPBalanceRelaSchema 对象给 Schema 赋值
	* @param: aLJPBalanceRelaSchema LJPBalanceRelaSchema
	**/
	public void setSchema(LJPBalanceRelaSchema aLJPBalanceRelaSchema)
	{
		this.BalanceRelaNo = aLJPBalanceRelaSchema.getBalanceRelaNo();
		this.BalanceAllNo = aLJPBalanceRelaSchema.getBalanceAllNo();
		this.GrpContNo = aLJPBalanceRelaSchema.getGrpContNo();
		this.BalanceType = aLJPBalanceRelaSchema.getBalanceType();
		this.BalanceRelaSum = aLJPBalanceRelaSchema.getBalanceRelaSum();
		this.BalanceRelaOperator = aLJPBalanceRelaSchema.getBalanceRelaOperator();
		this.BalanceRelaType = aLJPBalanceRelaSchema.getBalanceRelaType();
		this.BalanceRelaState = aLJPBalanceRelaSchema.getBalanceRelaState();
		this.BalanceRelaDate = fDate.getDate( aLJPBalanceRelaSchema.getBalanceRelaDate());
		this.BalanceRelaTime = aLJPBalanceRelaSchema.getBalanceRelaTime();
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
			if( rs.getString("BalanceRelaNo") == null )
				this.BalanceRelaNo = null;
			else
				this.BalanceRelaNo = rs.getString("BalanceRelaNo").trim();

			if( rs.getString("BalanceAllNo") == null )
				this.BalanceAllNo = null;
			else
				this.BalanceAllNo = rs.getString("BalanceAllNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("BalanceType") == null )
				this.BalanceType = null;
			else
				this.BalanceType = rs.getString("BalanceType").trim();

			this.BalanceRelaSum = rs.getDouble("BalanceRelaSum");
			if( rs.getString("BalanceRelaOperator") == null )
				this.BalanceRelaOperator = null;
			else
				this.BalanceRelaOperator = rs.getString("BalanceRelaOperator").trim();

			if( rs.getString("BalanceRelaType") == null )
				this.BalanceRelaType = null;
			else
				this.BalanceRelaType = rs.getString("BalanceRelaType").trim();

			if( rs.getString("BalanceRelaState") == null )
				this.BalanceRelaState = null;
			else
				this.BalanceRelaState = rs.getString("BalanceRelaState").trim();

			this.BalanceRelaDate = rs.getDate("BalanceRelaDate");
			if( rs.getString("BalanceRelaTime") == null )
				this.BalanceRelaTime = null;
			else
				this.BalanceRelaTime = rs.getString("BalanceRelaTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJPBalanceRela表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPBalanceRelaSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPBalanceRelaSchema getSchema()
	{
		LJPBalanceRelaSchema aLJPBalanceRelaSchema = new LJPBalanceRelaSchema();
		aLJPBalanceRelaSchema.setSchema(this);
		return aLJPBalanceRelaSchema;
	}

	public LJPBalanceRelaDB getDB()
	{
		LJPBalanceRelaDB aDBOper = new LJPBalanceRelaDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPBalanceRela描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BalanceRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BalanceRelaSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalanceRelaDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceRelaTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPBalanceRela>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BalanceRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BalanceAllNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BalanceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BalanceRelaSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			BalanceRelaOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BalanceRelaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BalanceRelaState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			BalanceRelaDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			BalanceRelaTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPBalanceRelaSchema";
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
		if (FCode.equalsIgnoreCase("BalanceRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaNo));
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("BalanceType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceType));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaSum));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaOperator));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaType));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaState));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalanceRelaDate()));
		}
		if (FCode.equalsIgnoreCase("BalanceRelaTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceRelaTime));
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
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BalanceType);
				break;
			case 4:
				strFieldValue = String.valueOf(BalanceRelaSum);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaOperator);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaState);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalanceRelaDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(BalanceRelaTime);
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

		if (FCode.equalsIgnoreCase("BalanceRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaNo = FValue.trim();
			}
			else
				BalanceRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("BalanceAllNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllNo = FValue.trim();
			}
			else
				BalanceAllNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("BalanceType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceType = FValue.trim();
			}
			else
				BalanceType = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BalanceRelaSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("BalanceRelaOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaOperator = FValue.trim();
			}
			else
				BalanceRelaOperator = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaType = FValue.trim();
			}
			else
				BalanceRelaType = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaState = FValue.trim();
			}
			else
				BalanceRelaState = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalanceRelaDate = fDate.getDate( FValue );
			}
			else
				BalanceRelaDate = null;
		}
		if (FCode.equalsIgnoreCase("BalanceRelaTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceRelaTime = FValue.trim();
			}
			else
				BalanceRelaTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJPBalanceRelaSchema other = (LJPBalanceRelaSchema)otherObject;
		return
			BalanceRelaNo.equals(other.getBalanceRelaNo())
			&& BalanceAllNo.equals(other.getBalanceAllNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& BalanceType.equals(other.getBalanceType())
			&& BalanceRelaSum == other.getBalanceRelaSum()
			&& BalanceRelaOperator.equals(other.getBalanceRelaOperator())
			&& BalanceRelaType.equals(other.getBalanceRelaType())
			&& BalanceRelaState.equals(other.getBalanceRelaState())
			&& fDate.getString(BalanceRelaDate).equals(other.getBalanceRelaDate())
			&& BalanceRelaTime.equals(other.getBalanceRelaTime());
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
		if( strFieldName.equals("BalanceRelaNo") ) {
			return 0;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return 1;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 2;
		}
		if( strFieldName.equals("BalanceType") ) {
			return 3;
		}
		if( strFieldName.equals("BalanceRelaSum") ) {
			return 4;
		}
		if( strFieldName.equals("BalanceRelaOperator") ) {
			return 5;
		}
		if( strFieldName.equals("BalanceRelaType") ) {
			return 6;
		}
		if( strFieldName.equals("BalanceRelaState") ) {
			return 7;
		}
		if( strFieldName.equals("BalanceRelaDate") ) {
			return 8;
		}
		if( strFieldName.equals("BalanceRelaTime") ) {
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
				strFieldName = "BalanceRelaNo";
				break;
			case 1:
				strFieldName = "BalanceAllNo";
				break;
			case 2:
				strFieldName = "GrpContNo";
				break;
			case 3:
				strFieldName = "BalanceType";
				break;
			case 4:
				strFieldName = "BalanceRelaSum";
				break;
			case 5:
				strFieldName = "BalanceRelaOperator";
				break;
			case 6:
				strFieldName = "BalanceRelaType";
				break;
			case 7:
				strFieldName = "BalanceRelaState";
				break;
			case 8:
				strFieldName = "BalanceRelaDate";
				break;
			case 9:
				strFieldName = "BalanceRelaTime";
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
		if( strFieldName.equals("BalanceRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalanceRelaOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceRelaDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalanceRelaTime") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
