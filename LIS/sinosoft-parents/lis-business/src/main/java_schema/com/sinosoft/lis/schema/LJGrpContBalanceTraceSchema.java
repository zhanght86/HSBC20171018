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
import com.sinosoft.lis.db.LJGrpContBalanceTraceDB;

/*
 * <p>ClassName: LJGrpContBalanceTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJGrpContBalanceTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJGrpContBalanceTraceSchema.class);
	// @Field
	/** 流水号 */
	private String SerialNo;
	/** 子流水号 */
	private int SerialSubNo;
	/** 其他业务类型 */
	private String OtherBussType;
	/** 其他业务号码 */
	private String OtherBussNo;
	/** 金额 */
	private double Money;
	/** 状态标识 */
	private String StateFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 公司代码 */
	private String ComCode;
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

	public static final int FIELDNUM = 14;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJGrpContBalanceTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "SerialNo";
		pk[1] = "SerialSubNo";

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
		LJGrpContBalanceTraceSchema cloned = (LJGrpContBalanceTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("流水号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	public int getSerialSubNo()
	{
		return SerialSubNo;
	}
	public void setSerialSubNo(int aSerialSubNo)
	{
		SerialSubNo = aSerialSubNo;
	}
	public void setSerialSubNo(String aSerialSubNo)
	{
		if (aSerialSubNo != null && !aSerialSubNo.equals(""))
		{
			Integer tInteger = new Integer(aSerialSubNo);
			int i = tInteger.intValue();
			SerialSubNo = i;
		}
	}

	public String getOtherBussType()
	{
		return OtherBussType;
	}
	public void setOtherBussType(String aOtherBussType)
	{
		if(aOtherBussType!=null && aOtherBussType.length()>2)
			throw new IllegalArgumentException("其他业务类型OtherBussType值"+aOtherBussType+"的长度"+aOtherBussType.length()+"大于最大值2");
		OtherBussType = aOtherBussType;
	}
	public String getOtherBussNo()
	{
		return OtherBussNo;
	}
	public void setOtherBussNo(String aOtherBussNo)
	{
		if(aOtherBussNo!=null && aOtherBussNo.length()>20)
			throw new IllegalArgumentException("其他业务号码OtherBussNo值"+aOtherBussNo+"的长度"+aOtherBussNo.length()+"大于最大值20");
		OtherBussNo = aOtherBussNo;
	}
	public double getMoney()
	{
		return Money;
	}
	public void setMoney(double aMoney)
	{
		Money = aMoney;
	}
	public void setMoney(String aMoney)
	{
		if (aMoney != null && !aMoney.equals(""))
		{
			Double tDouble = new Double(aMoney);
			double d = tDouble.doubleValue();
			Money = d;
		}
	}

	/**
	* 0-未生效，1-生效，2-无效，3-失效
	*/
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
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>20)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值20");
		ManageCom = aManageCom;
	}
	public String getComCode()
	{
		return ComCode;
	}
	public void setComCode(String aComCode)
	{
		if(aComCode!=null && aComCode.length()>20)
			throw new IllegalArgumentException("公司代码ComCode值"+aComCode+"的长度"+aComCode.length()+"大于最大值20");
		ComCode = aComCode;
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
	* 使用另外一个 LJGrpContBalanceTraceSchema 对象给 Schema 赋值
	* @param: aLJGrpContBalanceTraceSchema LJGrpContBalanceTraceSchema
	**/
	public void setSchema(LJGrpContBalanceTraceSchema aLJGrpContBalanceTraceSchema)
	{
		this.SerialNo = aLJGrpContBalanceTraceSchema.getSerialNo();
		this.SerialSubNo = aLJGrpContBalanceTraceSchema.getSerialSubNo();
		this.OtherBussType = aLJGrpContBalanceTraceSchema.getOtherBussType();
		this.OtherBussNo = aLJGrpContBalanceTraceSchema.getOtherBussNo();
		this.Money = aLJGrpContBalanceTraceSchema.getMoney();
		this.StateFlag = aLJGrpContBalanceTraceSchema.getStateFlag();
		this.ManageCom = aLJGrpContBalanceTraceSchema.getManageCom();
		this.ComCode = aLJGrpContBalanceTraceSchema.getComCode();
		this.MakeOperator = aLJGrpContBalanceTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJGrpContBalanceTraceSchema.getMakeDate());
		this.MakeTime = aLJGrpContBalanceTraceSchema.getMakeTime();
		this.ModifyOperator = aLJGrpContBalanceTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJGrpContBalanceTraceSchema.getModifyDate());
		this.ModifyTime = aLJGrpContBalanceTraceSchema.getModifyTime();
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
			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			this.SerialSubNo = rs.getInt("SerialSubNo");
			if( rs.getString("OtherBussType") == null )
				this.OtherBussType = null;
			else
				this.OtherBussType = rs.getString("OtherBussType").trim();

			if( rs.getString("OtherBussNo") == null )
				this.OtherBussNo = null;
			else
				this.OtherBussNo = rs.getString("OtherBussNo").trim();

			this.Money = rs.getDouble("Money");
			if( rs.getString("StateFlag") == null )
				this.StateFlag = null;
			else
				this.StateFlag = rs.getString("StateFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

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
			logger.debug("数据库中的LJGrpContBalanceTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJGrpContBalanceTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJGrpContBalanceTraceSchema getSchema()
	{
		LJGrpContBalanceTraceSchema aLJGrpContBalanceTraceSchema = new LJGrpContBalanceTraceSchema();
		aLJGrpContBalanceTraceSchema.setSchema(this);
		return aLJGrpContBalanceTraceSchema;
	}

	public LJGrpContBalanceTraceDB getDB()
	{
		LJGrpContBalanceTraceDB aDBOper = new LJGrpContBalanceTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJGrpContBalanceTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialSubNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherBussType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Money));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(StateFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJGrpContBalanceTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialSubNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			OtherBussType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OtherBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Money = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).doubleValue();
			StateFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJGrpContBalanceTraceSchema";
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
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("SerialSubNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialSubNo));
		}
		if (FCode.equalsIgnoreCase("OtherBussType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherBussType));
		}
		if (FCode.equalsIgnoreCase("OtherBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherBussNo));
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Money));
		}
		if (FCode.equalsIgnoreCase("StateFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StateFlag));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
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
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 1:
				strFieldValue = String.valueOf(SerialSubNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(OtherBussType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(OtherBussNo);
				break;
			case 4:
				strFieldValue = String.valueOf(Money);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(StateFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialSubNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				SerialSubNo = i;
			}
		}
		if (FCode.equalsIgnoreCase("OtherBussType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherBussType = FValue.trim();
			}
			else
				OtherBussType = null;
		}
		if (FCode.equalsIgnoreCase("OtherBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherBussNo = FValue.trim();
			}
			else
				OtherBussNo = null;
		}
		if (FCode.equalsIgnoreCase("Money"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Money = d;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
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
		LJGrpContBalanceTraceSchema other = (LJGrpContBalanceTraceSchema)otherObject;
		return
			SerialNo.equals(other.getSerialNo())
			&& SerialSubNo == other.getSerialSubNo()
			&& OtherBussType.equals(other.getOtherBussType())
			&& OtherBussNo.equals(other.getOtherBussNo())
			&& Money == other.getMoney()
			&& StateFlag.equals(other.getStateFlag())
			&& ManageCom.equals(other.getManageCom())
			&& ComCode.equals(other.getComCode())
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
		if( strFieldName.equals("SerialNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialSubNo") ) {
			return 1;
		}
		if( strFieldName.equals("OtherBussType") ) {
			return 2;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return 3;
		}
		if( strFieldName.equals("Money") ) {
			return 4;
		}
		if( strFieldName.equals("StateFlag") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("ComCode") ) {
			return 7;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 8;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 9;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				strFieldName = "SerialNo";
				break;
			case 1:
				strFieldName = "SerialSubNo";
				break;
			case 2:
				strFieldName = "OtherBussType";
				break;
			case 3:
				strFieldName = "OtherBussNo";
				break;
			case 4:
				strFieldName = "Money";
				break;
			case 5:
				strFieldName = "StateFlag";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "ComCode";
				break;
			case 8:
				strFieldName = "MakeOperator";
				break;
			case 9:
				strFieldName = "MakeDate";
				break;
			case 10:
				strFieldName = "MakeTime";
				break;
			case 11:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialSubNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherBussType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Money") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("StateFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
