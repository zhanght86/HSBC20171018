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
import com.sinosoft.lis.db.LJPremMatchDetailDB;

/*
 * <p>ClassName: LJPremMatchDetailSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJPremMatchDetailSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJPremMatchDetailSchema.class);
	// @Field
	/** 保费匹配流水号 */
	private String MatchSerialNo;
	/** 子流水号 */
	private int SubNo;
	/** 保费来源 */
	private String FeeDataSource;
	/** 保费来源号码 */
	private String FeeSourceNo;
	/** 应收业务类型 */
	private String PayBussType;
	/** 应收主业务号 */
	private String PayMainBussNo;
	/** 应收业务号码 */
	private String PaySubBussNo;
	/** 实收金额 */
	private double PayMoney;
	/** 实际溢缴金额 */
	private double OutPayMoney;
	/** 状态标识 */
	private String StateFlag;
	/** 入机操作员 */
	private String MakeOperator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改操作员 */
	private String ModifyOperator;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJPremMatchDetailSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "MatchSerialNo";
		pk[1] = "SubNo";

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
		LJPremMatchDetailSchema cloned = (LJPremMatchDetailSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getMatchSerialNo()
	{
		return MatchSerialNo;
	}
	public void setMatchSerialNo(String aMatchSerialNo)
	{
		if(aMatchSerialNo!=null && aMatchSerialNo.length()>20)
			throw new IllegalArgumentException("保费匹配流水号MatchSerialNo值"+aMatchSerialNo+"的长度"+aMatchSerialNo.length()+"大于最大值20");
		MatchSerialNo = aMatchSerialNo;
	}
	public int getSubNo()
	{
		return SubNo;
	}
	public void setSubNo(int aSubNo)
	{
		SubNo = aSubNo;
	}
	public void setSubNo(String aSubNo)
	{
		if (aSubNo != null && !aSubNo.equals(""))
		{
			Integer tInteger = new Integer(aSubNo);
			int i = tInteger.intValue();
			SubNo = i;
		}
	}

	/**
	* 0-收付费系统，1-保单溢交保费
	*/
	public String getFeeDataSource()
	{
		return FeeDataSource;
	}
	public void setFeeDataSource(String aFeeDataSource)
	{
		if(aFeeDataSource!=null && aFeeDataSource.length()>2)
			throw new IllegalArgumentException("保费来源FeeDataSource值"+aFeeDataSource+"的长度"+aFeeDataSource.length()+"大于最大值2");
		FeeDataSource = aFeeDataSource;
	}
	/**
	* 根据数据来源确定：收付费-TempFeeNo，溢交保费-GrpContNo
	*/
	public String getFeeSourceNo()
	{
		return FeeSourceNo;
	}
	public void setFeeSourceNo(String aFeeSourceNo)
	{
		if(aFeeSourceNo!=null && aFeeSourceNo.length()>20)
			throw new IllegalArgumentException("保费来源号码FeeSourceNo值"+aFeeSourceNo+"的长度"+aFeeSourceNo.length()+"大于最大值20");
		FeeSourceNo = aFeeSourceNo;
	}
	/**
	* 01-新契约，02-续期，03-保全，04-定期结算，05-理赔，06-溢交退费
	*/
	public String getPayBussType()
	{
		return PayBussType;
	}
	public void setPayBussType(String aPayBussType)
	{
		if(aPayBussType!=null && aPayBussType.length()>2)
			throw new IllegalArgumentException("应收业务类型PayBussType值"+aPayBussType+"的长度"+aPayBussType.length()+"大于最大值2");
		PayBussType = aPayBussType;
	}
	public String getPayMainBussNo()
	{
		return PayMainBussNo;
	}
	public void setPayMainBussNo(String aPayMainBussNo)
	{
		if(aPayMainBussNo!=null && aPayMainBussNo.length()>20)
			throw new IllegalArgumentException("应收主业务号PayMainBussNo值"+aPayMainBussNo+"的长度"+aPayMainBussNo.length()+"大于最大值20");
		PayMainBussNo = aPayMainBussNo;
	}
	public String getPaySubBussNo()
	{
		return PaySubBussNo;
	}
	public void setPaySubBussNo(String aPaySubBussNo)
	{
		if(aPaySubBussNo!=null && aPaySubBussNo.length()>20)
			throw new IllegalArgumentException("应收业务号码PaySubBussNo值"+aPaySubBussNo+"的长度"+aPaySubBussNo.length()+"大于最大值20");
		PaySubBussNo = aPaySubBussNo;
	}
	public double getPayMoney()
	{
		return PayMoney;
	}
	public void setPayMoney(double aPayMoney)
	{
		PayMoney = aPayMoney;
	}
	public void setPayMoney(String aPayMoney)
	{
		if (aPayMoney != null && !aPayMoney.equals(""))
		{
			Double tDouble = new Double(aPayMoney);
			double d = tDouble.doubleValue();
			PayMoney = d;
		}
	}

	public double getOutPayMoney()
	{
		return OutPayMoney;
	}
	public void setOutPayMoney(double aOutPayMoney)
	{
		OutPayMoney = aOutPayMoney;
	}
	public void setOutPayMoney(String aOutPayMoney)
	{
		if (aOutPayMoney != null && !aOutPayMoney.equals(""))
		{
			Double tDouble = new Double(aOutPayMoney);
			double d = tDouble.doubleValue();
			OutPayMoney = d;
		}
	}

	public String getStateFlag()
	{
		return StateFlag;
	}
	public void setStateFlag(String aStateFlag)
	{
		if(aStateFlag!=null && aStateFlag.length()>2)
			throw new IllegalArgumentException("状态标识StateFlag值"+aStateFlag+"的长度"+aStateFlag.length()+"大于最大值2");
		StateFlag = aStateFlag;
	}
	public String getMakeOperator()
	{
		return MakeOperator;
	}
	public void setMakeOperator(String aMakeOperator)
	{
		if(aMakeOperator!=null && aMakeOperator.length()>30)
			throw new IllegalArgumentException("入机操作员MakeOperator值"+aMakeOperator+"的长度"+aMakeOperator.length()+"大于最大值30");
		MakeOperator = aMakeOperator;
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
	public String getModifyOperator()
	{
		return ModifyOperator;
	}
	public void setModifyOperator(String aModifyOperator)
	{
		if(aModifyOperator!=null && aModifyOperator.length()>30)
			throw new IllegalArgumentException("最后一次修改操作员ModifyOperator值"+aModifyOperator+"的长度"+aModifyOperator.length()+"大于最大值30");
		ModifyOperator = aModifyOperator;
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
	* 使用另外一个 LJPremMatchDetailSchema 对象给 Schema 赋值
	* @param: aLJPremMatchDetailSchema LJPremMatchDetailSchema
	**/
	public void setSchema(LJPremMatchDetailSchema aLJPremMatchDetailSchema)
	{
		this.MatchSerialNo = aLJPremMatchDetailSchema.getMatchSerialNo();
		this.SubNo = aLJPremMatchDetailSchema.getSubNo();
		this.FeeDataSource = aLJPremMatchDetailSchema.getFeeDataSource();
		this.FeeSourceNo = aLJPremMatchDetailSchema.getFeeSourceNo();
		this.PayBussType = aLJPremMatchDetailSchema.getPayBussType();
		this.PayMainBussNo = aLJPremMatchDetailSchema.getPayMainBussNo();
		this.PaySubBussNo = aLJPremMatchDetailSchema.getPaySubBussNo();
		this.PayMoney = aLJPremMatchDetailSchema.getPayMoney();
		this.OutPayMoney = aLJPremMatchDetailSchema.getOutPayMoney();
		this.StateFlag = aLJPremMatchDetailSchema.getStateFlag();
		this.MakeOperator = aLJPremMatchDetailSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJPremMatchDetailSchema.getMakeDate());
		this.MakeTime = aLJPremMatchDetailSchema.getMakeTime();
		this.ModifyOperator = aLJPremMatchDetailSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJPremMatchDetailSchema.getModifyDate());
		this.ModifyTime = aLJPremMatchDetailSchema.getModifyTime();
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
			if( rs.getString("MatchSerialNo") == null )
				this.MatchSerialNo = null;
			else
				this.MatchSerialNo = rs.getString("MatchSerialNo").trim();

			this.SubNo = rs.getInt("SubNo");
			if( rs.getString("FeeDataSource") == null )
				this.FeeDataSource = null;
			else
				this.FeeDataSource = rs.getString("FeeDataSource").trim();

			if( rs.getString("FeeSourceNo") == null )
				this.FeeSourceNo = null;
			else
				this.FeeSourceNo = rs.getString("FeeSourceNo").trim();

			if( rs.getString("PayBussType") == null )
				this.PayBussType = null;
			else
				this.PayBussType = rs.getString("PayBussType").trim();

			if( rs.getString("PayMainBussNo") == null )
				this.PayMainBussNo = null;
			else
				this.PayMainBussNo = rs.getString("PayMainBussNo").trim();

			if( rs.getString("PaySubBussNo") == null )
				this.PaySubBussNo = null;
			else
				this.PaySubBussNo = rs.getString("PaySubBussNo").trim();

			this.PayMoney = rs.getDouble("PayMoney");
			this.OutPayMoney = rs.getDouble("OutPayMoney");
			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			if( rs.getString("MakeOperator") == null )
				this.MakeOperator = null;
			else
				this.MakeOperator = rs.getString("MakeOperator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LJPremMatchDetail表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchDetailSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJPremMatchDetailSchema getSchema()
	{
		LJPremMatchDetailSchema aLJPremMatchDetailSchema = new LJPremMatchDetailSchema();
		aLJPremMatchDetailSchema.setSchema(this);
		return aLJPremMatchDetailSchema;
	}

	public LJPremMatchDetailDB getDB()
	{
		LJPremMatchDetailDB aDBOper = new LJPremMatchDetailDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchDetail描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(MatchSerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SubNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeDataSource)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeSourceNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayBussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayMainBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PaySubBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(PayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OutPayMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJPremMatchDetail>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MatchSerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SubNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			FeeDataSource = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			FeeSourceNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayBussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PayMainBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			PaySubBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			PayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).doubleValue();
			OutPayMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).doubleValue();
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJPremMatchDetailSchema";
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
		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MatchSerialNo));
		}
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubNo));
		}
		if (FCode.equalsIgnoreCase("FeeDataSource"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeDataSource));
		}
		if (FCode.equalsIgnoreCase("FeeSourceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeSourceNo));
		}
		if (FCode.equalsIgnoreCase("PayBussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayBussType));
		}
		if (FCode.equalsIgnoreCase("PayMainBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMainBussNo));
		}
		if (FCode.equalsIgnoreCase("PaySubBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PaySubBussNo));
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayMoney));
		}
		if (FCode.equalsIgnoreCase("OutPayMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutPayMoney));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeOperator));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(MatchSerialNo);
				break;
			case 1:
				strFieldValue = String.valueOf(SubNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FeeDataSource);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(FeeSourceNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayBussType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PayMainBussNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(PaySubBussNo);
				break;
			case 7:
				strFieldValue = String.valueOf(PayMoney);
				break;
			case 8:
				strFieldValue = String.valueOf(OutPayMoney);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("MatchSerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MatchSerialNo = FValue.trim();
			}
			else
				MatchSerialNo = null;
		}
		if (FCode.equalsIgnoreCase("SubNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SubNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("FeeDataSource"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeDataSource = FValue.trim();
			}
			else
				FeeDataSource = null;
		}
		if (FCode.equalsIgnoreCase("FeeSourceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeSourceNo = FValue.trim();
			}
			else
				FeeSourceNo = null;
		}
		if (FCode.equalsIgnoreCase("PayBussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayBussType = FValue.trim();
			}
			else
				PayBussType = null;
		}
		if (FCode.equalsIgnoreCase("PayMainBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayMainBussNo = FValue.trim();
			}
			else
				PayMainBussNo = null;
		}
		if (FCode.equalsIgnoreCase("PaySubBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PaySubBussNo = FValue.trim();
			}
			else
				PaySubBussNo = null;
		}
		if (FCode.equalsIgnoreCase("PayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("OutPayMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OutPayMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StateFlag = FValue.trim();
			}
			else
				StateFlag = null;
		}
		if (FCode.equalsIgnoreCase("MakeOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeOperator = FValue.trim();
			}
			else
				MakeOperator = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
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
		LJPremMatchDetailSchema other = (LJPremMatchDetailSchema)otherObject;
		return
			MatchSerialNo.equals(other.getMatchSerialNo())
			&& SubNo == other.getSubNo()
			&& FeeDataSource.equals(other.getFeeDataSource())
			&& FeeSourceNo.equals(other.getFeeSourceNo())
			&& PayBussType.equals(other.getPayBussType())
			&& PayMainBussNo.equals(other.getPayMainBussNo())
			&& PaySubBussNo.equals(other.getPaySubBussNo())
			&& PayMoney == other.getPayMoney()
			&& OutPayMoney == other.getOutPayMoney()
			&& StateFlag.equals(other.getStateFlag())
			&& MakeOperator.equals(other.getMakeOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyOperator.equals(other.getModifyOperator())
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("SubNo") ) {
			return 1;
		}
		if( strFieldName.equals("FeeDataSource") ) {
			return 2;
		}
		if( strFieldName.equals("FeeSourceNo") ) {
			return 3;
		}
		if( strFieldName.equals("PayBussType") ) {
			return 4;
		}
		if( strFieldName.equals("PayMainBussNo") ) {
			return 5;
		}
		if( strFieldName.equals("PaySubBussNo") ) {
			return 6;
		}
		if( strFieldName.equals("PayMoney") ) {
			return 7;
		}
		if( strFieldName.equals("OutPayMoney") ) {
			return 8;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 9;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 10;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 11;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 12;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "MatchSerialNo";
				break;
			case 1:
				strFieldName = "SubNo";
				break;
			case 2:
				strFieldName = "FeeDataSource";
				break;
			case 3:
				strFieldName = "FeeSourceNo";
				break;
			case 4:
				strFieldName = "PayBussType";
				break;
			case 5:
				strFieldName = "PayMainBussNo";
				break;
			case 6:
				strFieldName = "PaySubBussNo";
				break;
			case 7:
				strFieldName = "PayMoney";
				break;
			case 8:
				strFieldName = "OutPayMoney";
				break;
			case 9:
				strFieldName = "StateFlag";
				break;
			case 10:
				strFieldName = "MakeOperator";
				break;
			case 11:
				strFieldName = "MakeDate";
				break;
			case 12:
				strFieldName = "MakeTime";
				break;
			case 13:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("MatchSerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("FeeDataSource") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeSourceNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayBussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMainBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PaySubBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("OutPayMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
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
