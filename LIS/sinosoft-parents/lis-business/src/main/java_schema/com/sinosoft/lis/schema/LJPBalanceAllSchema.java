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
import com.sinosoft.lis.db.LJPBalanceAllDB;

/*
 * <p>ClassName: LJPBalanceAllSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPBalanceAllSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPBalanceAllSchema.class);
	// @Field
	/** 定期结算汇总号码 */
	private String BalanceAllNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 本次定期结算方式 */
	private String BalanceType;
	/** 本次定期结算金额合计 */
	private double BalanceAllSum;
	/** 定期结算操作员 */
	private String BalanceAllOperator;
	/** 定期结算收付类型 */
	private String BalanceAllType;
	/** 定期结算收付状态 */
	private String BalanceAllState;
	/** 本次定期结算日期 */
	private Date BalanceDate;
	/** 本次定期结算时间 */
	private String BalanceTime;
	/** 打印状态 */
	private String PrintState;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPBalanceAllSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "BalanceAllNo";

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
		LJPBalanceAllSchema cloned = (LJPBalanceAllSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public double getBalanceAllSum()
	{
		return BalanceAllSum;
	}
	public void setBalanceAllSum(double aBalanceAllSum)
	{
		BalanceAllSum = aBalanceAllSum;
	}
	public void setBalanceAllSum(String aBalanceAllSum)
	{
		if (aBalanceAllSum != null && !aBalanceAllSum.equals(""))
		{
			Double tDouble = new Double(aBalanceAllSum);
			double d = tDouble.doubleValue();
			BalanceAllSum = d;
		}
	}

	public String getBalanceAllOperator()
	{
		return BalanceAllOperator;
	}
	public void setBalanceAllOperator(String aBalanceAllOperator)
	{
		if(aBalanceAllOperator!=null && aBalanceAllOperator.length()>30)
			throw new IllegalArgumentException("定期结算操作员BalanceAllOperator值"+aBalanceAllOperator+"的长度"+aBalanceAllOperator.length()+"大于最大值30");
		BalanceAllOperator = aBalanceAllOperator;
	}
	/**
	* 0 －收付抵消<p>
	* 1 －收费<p>
	* 2 －付费
	*/
	public String getBalanceAllType()
	{
		return BalanceAllType;
	}
	public void setBalanceAllType(String aBalanceAllType)
	{
		if(aBalanceAllType!=null && aBalanceAllType.length()>2)
			throw new IllegalArgumentException("定期结算收付类型BalanceAllType值"+aBalanceAllType+"的长度"+aBalanceAllType.length()+"大于最大值2");
		BalanceAllType = aBalanceAllType;
	}
	/**
	* 0 －收付费未处理完<p>
	* 1 －收付费已处理完
	*/
	public String getBalanceAllState()
	{
		return BalanceAllState;
	}
	public void setBalanceAllState(String aBalanceAllState)
	{
		if(aBalanceAllState!=null && aBalanceAllState.length()>2)
			throw new IllegalArgumentException("定期结算收付状态BalanceAllState值"+aBalanceAllState+"的长度"+aBalanceAllState.length()+"大于最大值2");
		BalanceAllState = aBalanceAllState;
	}
	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理日期
	*/
	public String getBalanceDate()
	{
		if( BalanceDate != null )
			return fDate.getString(BalanceDate);
		else
			return null;
	}
	public void setBalanceDate(Date aBalanceDate)
	{
		BalanceDate = aBalanceDate;
	}
	public void setBalanceDate(String aBalanceDate)
	{
		if (aBalanceDate != null && !aBalanceDate.equals("") )
		{
			BalanceDate = fDate.getDate( aBalanceDate );
		}
		else
			BalanceDate = null;
	}

	/**
	* 到达定期结算周期或到达金额上限或手动定期结算的处理时间
	*/
	public String getBalanceTime()
	{
		return BalanceTime;
	}
	public void setBalanceTime(String aBalanceTime)
	{
		if(aBalanceTime!=null && aBalanceTime.length()>8)
			throw new IllegalArgumentException("本次定期结算时间BalanceTime值"+aBalanceTime+"的长度"+aBalanceTime.length()+"大于最大值8");
		BalanceTime = aBalanceTime;
	}
	public String getPrintState()
	{
		return PrintState;
	}
	public void setPrintState(String aPrintState)
	{
		if(aPrintState!=null && aPrintState.length()>8)
			throw new IllegalArgumentException("打印状态PrintState值"+aPrintState+"的长度"+aPrintState.length()+"大于最大值8");
		PrintState = aPrintState;
	}

	/**
	* 使用另外一个 LJPBalanceAllSchema 对象给 Schema 赋值
	* @param: aLJPBalanceAllSchema LJPBalanceAllSchema
	**/
	public void setSchema(LJPBalanceAllSchema aLJPBalanceAllSchema)
	{
		this.BalanceAllNo = aLJPBalanceAllSchema.getBalanceAllNo();
		this.GrpContNo = aLJPBalanceAllSchema.getGrpContNo();
		this.BalanceType = aLJPBalanceAllSchema.getBalanceType();
		this.BalanceAllSum = aLJPBalanceAllSchema.getBalanceAllSum();
		this.BalanceAllOperator = aLJPBalanceAllSchema.getBalanceAllOperator();
		this.BalanceAllType = aLJPBalanceAllSchema.getBalanceAllType();
		this.BalanceAllState = aLJPBalanceAllSchema.getBalanceAllState();
		this.BalanceDate = fDate.getDate( aLJPBalanceAllSchema.getBalanceDate());
		this.BalanceTime = aLJPBalanceAllSchema.getBalanceTime();
		this.PrintState = aLJPBalanceAllSchema.getPrintState();
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

			this.BalanceAllSum = rs.getDouble("BalanceAllSum");
			if( rs.getString("BalanceAllOperator") == null )
				this.BalanceAllOperator = null;
			else
				this.BalanceAllOperator = rs.getString("BalanceAllOperator").trim();

			if( rs.getString("BalanceAllType") == null )
				this.BalanceAllType = null;
			else
				this.BalanceAllType = rs.getString("BalanceAllType").trim();

			if( rs.getString("BalanceAllState") == null )
				this.BalanceAllState = null;
			else
				this.BalanceAllState = rs.getString("BalanceAllState").trim();

			this.BalanceDate = rs.getDate("BalanceDate");
			if( rs.getString("BalanceTime") == null )
				this.BalanceTime = null;
			else
				this.BalanceTime = rs.getString("BalanceTime").trim();

			if( rs.getString("PrintState") == null )
				this.PrintState = null;
			else
				this.PrintState = rs.getString("PrintState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJPBalanceAll表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPBalanceAllSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPBalanceAllSchema getSchema()
	{
		LJPBalanceAllSchema aLJPBalanceAllSchema = new LJPBalanceAllSchema();
		aLJPBalanceAllSchema.setSchema(this);
		return aLJPBalanceAllSchema;
	}

	public LJPBalanceAllDB getDB()
	{
		LJPBalanceAllDB aDBOper = new LJPBalanceAllDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPBalanceAll描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(BalanceAllNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(BalanceAllSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceAllState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( BalanceDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BalanceTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PrintState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPBalanceAll>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			BalanceAllNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BalanceType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BalanceAllSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).doubleValue();
			BalanceAllOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			BalanceAllType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			BalanceAllState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			BalanceDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			BalanceTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PrintState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPBalanceAllSchema";
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
		if (FCode.equalsIgnoreCase("BalanceAllSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllSum));
		}
		if (FCode.equalsIgnoreCase("BalanceAllOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllOperator));
		}
		if (FCode.equalsIgnoreCase("BalanceAllType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllType));
		}
		if (FCode.equalsIgnoreCase("BalanceAllState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceAllState));
		}
		if (FCode.equalsIgnoreCase("BalanceDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getBalanceDate()));
		}
		if (FCode.equalsIgnoreCase("BalanceTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BalanceTime));
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PrintState));
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
				strFieldValue = StrTool.GBKToUnicode(BalanceAllNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BalanceType);
				break;
			case 3:
				strFieldValue = String.valueOf(BalanceAllSum);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllOperator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(BalanceAllState);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getBalanceDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(BalanceTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PrintState);
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
		if (FCode.equalsIgnoreCase("BalanceAllSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				BalanceAllSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("BalanceAllOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllOperator = FValue.trim();
			}
			else
				BalanceAllOperator = null;
		}
		if (FCode.equalsIgnoreCase("BalanceAllType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllType = FValue.trim();
			}
			else
				BalanceAllType = null;
		}
		if (FCode.equalsIgnoreCase("BalanceAllState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceAllState = FValue.trim();
			}
			else
				BalanceAllState = null;
		}
		if (FCode.equalsIgnoreCase("BalanceDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				BalanceDate = fDate.getDate( FValue );
			}
			else
				BalanceDate = null;
		}
		if (FCode.equalsIgnoreCase("BalanceTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BalanceTime = FValue.trim();
			}
			else
				BalanceTime = null;
		}
		if (FCode.equalsIgnoreCase("PrintState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PrintState = FValue.trim();
			}
			else
				PrintState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LJPBalanceAllSchema other = (LJPBalanceAllSchema)otherObject;
		return
			BalanceAllNo.equals(other.getBalanceAllNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& BalanceType.equals(other.getBalanceType())
			&& BalanceAllSum == other.getBalanceAllSum()
			&& BalanceAllOperator.equals(other.getBalanceAllOperator())
			&& BalanceAllType.equals(other.getBalanceAllType())
			&& BalanceAllState.equals(other.getBalanceAllState())
			&& fDate.getString(BalanceDate).equals(other.getBalanceDate())
			&& BalanceTime.equals(other.getBalanceTime())
			&& PrintState.equals(other.getPrintState());
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
		if( strFieldName.equals("BalanceAllNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("BalanceType") ) {
			return 2;
		}
		if( strFieldName.equals("BalanceAllSum") ) {
			return 3;
		}
		if( strFieldName.equals("BalanceAllOperator") ) {
			return 4;
		}
		if( strFieldName.equals("BalanceAllType") ) {
			return 5;
		}
		if( strFieldName.equals("BalanceAllState") ) {
			return 6;
		}
		if( strFieldName.equals("BalanceDate") ) {
			return 7;
		}
		if( strFieldName.equals("BalanceTime") ) {
			return 8;
		}
		if( strFieldName.equals("PrintState") ) {
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
				strFieldName = "BalanceAllNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "BalanceType";
				break;
			case 3:
				strFieldName = "BalanceAllSum";
				break;
			case 4:
				strFieldName = "BalanceAllOperator";
				break;
			case 5:
				strFieldName = "BalanceAllType";
				break;
			case 6:
				strFieldName = "BalanceAllState";
				break;
			case 7:
				strFieldName = "BalanceDate";
				break;
			case 8:
				strFieldName = "BalanceTime";
				break;
			case 9:
				strFieldName = "PrintState";
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
		if( strFieldName.equals("BalanceAllNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BalanceAllOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceAllState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BalanceDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("BalanceTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PrintState") ) {
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
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
