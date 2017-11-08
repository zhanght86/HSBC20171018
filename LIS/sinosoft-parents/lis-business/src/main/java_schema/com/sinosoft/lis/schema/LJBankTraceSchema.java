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
import com.sinosoft.lis.db.LJBankTraceDB;

/*
 * <p>ClassName: LJBankTraceSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJBankTraceSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJBankTraceSchema.class);
	// @Field
	/** 申请批次号 */
	private String ApplyBatNo;
	/** 流水号 */
	private int SerialNo;
	/** 目标客户开户行 */
	private String TarHeadBankCode;
	/** 目标银行编码 */
	private String TarBankCode;
	/** 目标所在省编码 */
	private String TarBankProvince;
	/** 目标所在市编码 */
	private String TarBankCity;
	/** 目标银行帐号 */
	private String TarBankAccNo;
	/** 目标银行帐户名 */
	private String TarAccName;
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

	public static final int FIELDNUM = 16;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJBankTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ApplyBatNo";
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
		LJBankTraceSchema cloned = (LJBankTraceSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getApplyBatNo()
	{
		return ApplyBatNo;
	}
	public void setApplyBatNo(String aApplyBatNo)
	{
		if(aApplyBatNo!=null && aApplyBatNo.length()>20)
			throw new IllegalArgumentException("申请批次号ApplyBatNo值"+aApplyBatNo+"的长度"+aApplyBatNo.length()+"大于最大值20");
		ApplyBatNo = aApplyBatNo;
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

	public String getTarHeadBankCode()
	{
		return TarHeadBankCode;
	}
	public void setTarHeadBankCode(String aTarHeadBankCode)
	{
		if(aTarHeadBankCode!=null && aTarHeadBankCode.length()>30)
			throw new IllegalArgumentException("目标客户开户行TarHeadBankCode值"+aTarHeadBankCode+"的长度"+aTarHeadBankCode.length()+"大于最大值30");
		TarHeadBankCode = aTarHeadBankCode;
	}
	public String getTarBankCode()
	{
		return TarBankCode;
	}
	public void setTarBankCode(String aTarBankCode)
	{
		if(aTarBankCode!=null && aTarBankCode.length()>30)
			throw new IllegalArgumentException("目标银行编码TarBankCode值"+aTarBankCode+"的长度"+aTarBankCode.length()+"大于最大值30");
		TarBankCode = aTarBankCode;
	}
	public String getTarBankProvince()
	{
		return TarBankProvince;
	}
	public void setTarBankProvince(String aTarBankProvince)
	{
		if(aTarBankProvince!=null && aTarBankProvince.length()>30)
			throw new IllegalArgumentException("目标所在省编码TarBankProvince值"+aTarBankProvince+"的长度"+aTarBankProvince.length()+"大于最大值30");
		TarBankProvince = aTarBankProvince;
	}
	public String getTarBankCity()
	{
		return TarBankCity;
	}
	public void setTarBankCity(String aTarBankCity)
	{
		if(aTarBankCity!=null && aTarBankCity.length()>30)
			throw new IllegalArgumentException("目标所在市编码TarBankCity值"+aTarBankCity+"的长度"+aTarBankCity.length()+"大于最大值30");
		TarBankCity = aTarBankCity;
	}
	public String getTarBankAccNo()
	{
		return TarBankAccNo;
	}
	public void setTarBankAccNo(String aTarBankAccNo)
	{
		if(aTarBankAccNo!=null && aTarBankAccNo.length()>30)
			throw new IllegalArgumentException("目标银行帐号TarBankAccNo值"+aTarBankAccNo+"的长度"+aTarBankAccNo.length()+"大于最大值30");
		TarBankAccNo = aTarBankAccNo;
	}
	public String getTarAccName()
	{
		return TarAccName;
	}
	public void setTarAccName(String aTarAccName)
	{
		if(aTarAccName!=null && aTarAccName.length()>200)
			throw new IllegalArgumentException("目标银行帐户名TarAccName值"+aTarAccName+"的长度"+aTarAccName.length()+"大于最大值200");
		TarAccName = aTarAccName;
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
	* 使用另外一个 LJBankTraceSchema 对象给 Schema 赋值
	* @param: aLJBankTraceSchema LJBankTraceSchema
	**/
	public void setSchema(LJBankTraceSchema aLJBankTraceSchema)
	{
		this.ApplyBatNo = aLJBankTraceSchema.getApplyBatNo();
		this.SerialNo = aLJBankTraceSchema.getSerialNo();
		this.TarHeadBankCode = aLJBankTraceSchema.getTarHeadBankCode();
		this.TarBankCode = aLJBankTraceSchema.getTarBankCode();
		this.TarBankProvince = aLJBankTraceSchema.getTarBankProvince();
		this.TarBankCity = aLJBankTraceSchema.getTarBankCity();
		this.TarBankAccNo = aLJBankTraceSchema.getTarBankAccNo();
		this.TarAccName = aLJBankTraceSchema.getTarAccName();
		this.ManageCom = aLJBankTraceSchema.getManageCom();
		this.ComCode = aLJBankTraceSchema.getComCode();
		this.MakeOperator = aLJBankTraceSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJBankTraceSchema.getMakeDate());
		this.MakeTime = aLJBankTraceSchema.getMakeTime();
		this.ModifyOperator = aLJBankTraceSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJBankTraceSchema.getModifyDate());
		this.ModifyTime = aLJBankTraceSchema.getModifyTime();
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
			if( rs.getString("ApplyBatNo") == null )
				this.ApplyBatNo = null;
			else
				this.ApplyBatNo = rs.getString("ApplyBatNo").trim();

			this.SerialNo = rs.getInt("SerialNo");
			if( rs.getString("TarHeadBankCode") == null )
				this.TarHeadBankCode = null;
			else
				this.TarHeadBankCode = rs.getString("TarHeadBankCode").trim();

			if( rs.getString("TarBankCode") == null )
				this.TarBankCode = null;
			else
				this.TarBankCode = rs.getString("TarBankCode").trim();

			if( rs.getString("TarBankProvince") == null )
				this.TarBankProvince = null;
			else
				this.TarBankProvince = rs.getString("TarBankProvince").trim();

			if( rs.getString("TarBankCity") == null )
				this.TarBankCity = null;
			else
				this.TarBankCity = rs.getString("TarBankCity").trim();

			if( rs.getString("TarBankAccNo") == null )
				this.TarBankAccNo = null;
			else
				this.TarBankAccNo = rs.getString("TarBankAccNo").trim();

			if( rs.getString("TarAccName") == null )
				this.TarAccName = null;
			else
				this.TarAccName = rs.getString("TarAccName").trim();

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
			logger.debug("数据库中的LJBankTrace表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJBankTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJBankTraceSchema getSchema()
	{
		LJBankTraceSchema aLJBankTraceSchema = new LJBankTraceSchema();
		aLJBankTraceSchema.setSchema(this);
		return aLJBankTraceSchema;
	}

	public LJBankTraceDB getDB()
	{
		LJBankTraceDB aDBOper = new LJBankTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJBankTrace描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SerialNo));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarHeadBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankProvince)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankCity)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarBankAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TarAccName)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJBankTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			SerialNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			TarHeadBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TarBankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TarBankProvince = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TarBankCity = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TarBankAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			TarAccName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
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
			tError.moduleName = "LJBankTraceSchema";
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
		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApplyBatNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("TarHeadBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarHeadBankCode));
		}
		if (FCode.equalsIgnoreCase("TarBankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankCode));
		}
		if (FCode.equalsIgnoreCase("TarBankProvince"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankProvince));
		}
		if (FCode.equalsIgnoreCase("TarBankCity"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankCity));
		}
		if (FCode.equalsIgnoreCase("TarBankAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarBankAccNo));
		}
		if (FCode.equalsIgnoreCase("TarAccName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TarAccName));
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
				strFieldValue = StrTool.GBKToUnicode(ApplyBatNo);
				break;
			case 1:
				strFieldValue = String.valueOf(SerialNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(TarHeadBankCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TarBankCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TarBankProvince);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TarBankCity);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TarBankAccNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(TarAccName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
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

		if (FCode.equalsIgnoreCase("ApplyBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApplyBatNo = FValue.trim();
			}
			else
				ApplyBatNo = null;
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
		if (FCode.equalsIgnoreCase("TarHeadBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarHeadBankCode = FValue.trim();
			}
			else
				TarHeadBankCode = null;
		}
		if (FCode.equalsIgnoreCase("TarBankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankCode = FValue.trim();
			}
			else
				TarBankCode = null;
		}
		if (FCode.equalsIgnoreCase("TarBankProvince"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankProvince = FValue.trim();
			}
			else
				TarBankProvince = null;
		}
		if (FCode.equalsIgnoreCase("TarBankCity"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankCity = FValue.trim();
			}
			else
				TarBankCity = null;
		}
		if (FCode.equalsIgnoreCase("TarBankAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarBankAccNo = FValue.trim();
			}
			else
				TarBankAccNo = null;
		}
		if (FCode.equalsIgnoreCase("TarAccName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TarAccName = FValue.trim();
			}
			else
				TarAccName = null;
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
		LJBankTraceSchema other = (LJBankTraceSchema)otherObject;
		return
			ApplyBatNo.equals(other.getApplyBatNo())
			&& SerialNo == other.getSerialNo()
			&& TarHeadBankCode.equals(other.getTarHeadBankCode())
			&& TarBankCode.equals(other.getTarBankCode())
			&& TarBankProvince.equals(other.getTarBankProvince())
			&& TarBankCity.equals(other.getTarBankCity())
			&& TarBankAccNo.equals(other.getTarBankAccNo())
			&& TarAccName.equals(other.getTarAccName())
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return 0;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 1;
		}
		if( strFieldName.equals("TarHeadBankCode") ) {
			return 2;
		}
		if( strFieldName.equals("TarBankCode") ) {
			return 3;
		}
		if( strFieldName.equals("TarBankProvince") ) {
			return 4;
		}
		if( strFieldName.equals("TarBankCity") ) {
			return 5;
		}
		if( strFieldName.equals("TarBankAccNo") ) {
			return 6;
		}
		if( strFieldName.equals("TarAccName") ) {
			return 7;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 8;
		}
		if( strFieldName.equals("ComCode") ) {
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
				strFieldName = "ApplyBatNo";
				break;
			case 1:
				strFieldName = "SerialNo";
				break;
			case 2:
				strFieldName = "TarHeadBankCode";
				break;
			case 3:
				strFieldName = "TarBankCode";
				break;
			case 4:
				strFieldName = "TarBankProvince";
				break;
			case 5:
				strFieldName = "TarBankCity";
				break;
			case 6:
				strFieldName = "TarBankAccNo";
				break;
			case 7:
				strFieldName = "TarAccName";
				break;
			case 8:
				strFieldName = "ManageCom";
				break;
			case 9:
				strFieldName = "ComCode";
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
		if( strFieldName.equals("ApplyBatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TarHeadBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankProvince") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankCity") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarBankAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TarAccName") ) {
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
