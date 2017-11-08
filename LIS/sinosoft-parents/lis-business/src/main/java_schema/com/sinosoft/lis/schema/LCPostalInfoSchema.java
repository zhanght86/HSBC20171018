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
import com.sinosoft.lis.db.LCPostalInfoDB;

/*
 * <p>ClassName: LCPostalInfoSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LCPostalInfoSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCPostalInfoSchema.class);
	// @Field
	/** 团体保单号 */
	private String GrpContNo;
	/** 团体投保单号 */
	private String GrpPropNo;
	/** 流水号 */
	private String SerialNo;
	/** 登记结论 */
	private String RegisterPassFlag;
	/** 不合格原因 */
	private String Reason;
	/** 交接类型 */
	private String TransferType;
	/** 快递公司名称 */
	private String ExpressCorpName;
	/** 快递单号 */
	private String ExpressNo;
	/** 交接日期 */
	private Date TransferDate;
	/** 登记人 */
	private String Register;
	/** 快递登记人 */
	private String ExpressRegister;
	/** 快递登记日期 */
	private Date ExpressDate;
	/** 接收日期 */
	private Date ReceiveDate;
	/** 客户签收日期 */
	private Date GetPolDate;
	/** 客户接收日期 */
	private Date CustomerGetPolDate;
	/** 回执回销操作日期 */
	private Date InputDate;
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

	public static final int FIELDNUM = 24;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCPostalInfoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "GrpContNo";
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
		LCPostalInfoSchema cloned = (LCPostalInfoSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
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
	public String getGrpPropNo()
	{
		return GrpPropNo;
	}
	public void setGrpPropNo(String aGrpPropNo)
	{
		if(aGrpPropNo!=null && aGrpPropNo.length()>20)
			throw new IllegalArgumentException("团体投保单号GrpPropNo值"+aGrpPropNo+"的长度"+aGrpPropNo.length()+"大于最大值20");
		GrpPropNo = aGrpPropNo;
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
	public String getRegisterPassFlag()
	{
		return RegisterPassFlag;
	}
	public void setRegisterPassFlag(String aRegisterPassFlag)
	{
		if(aRegisterPassFlag!=null && aRegisterPassFlag.length()>2)
			throw new IllegalArgumentException("登记结论RegisterPassFlag值"+aRegisterPassFlag+"的长度"+aRegisterPassFlag.length()+"大于最大值2");
		RegisterPassFlag = aRegisterPassFlag;
	}
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		if(aReason!=null && aReason.length()>1000)
			throw new IllegalArgumentException("不合格原因Reason值"+aReason+"的长度"+aReason.length()+"大于最大值1000");
		Reason = aReason;
	}
	/**
	* 01- 当??交接<p>
	* 02- 快递交接
	*/
	public String getTransferType()
	{
		return TransferType;
	}
	public void setTransferType(String aTransferType)
	{
		if(aTransferType!=null && aTransferType.length()>2)
			throw new IllegalArgumentException("交接类型TransferType值"+aTransferType+"的长度"+aTransferType.length()+"大于最大值2");
		TransferType = aTransferType;
	}
	public String getExpressCorpName()
	{
		return ExpressCorpName;
	}
	public void setExpressCorpName(String aExpressCorpName)
	{
		if(aExpressCorpName!=null && aExpressCorpName.length()>100)
			throw new IllegalArgumentException("快递公司名称ExpressCorpName值"+aExpressCorpName+"的长度"+aExpressCorpName.length()+"大于最大值100");
		ExpressCorpName = aExpressCorpName;
	}
	public String getExpressNo()
	{
		return ExpressNo;
	}
	public void setExpressNo(String aExpressNo)
	{
		if(aExpressNo!=null && aExpressNo.length()>50)
			throw new IllegalArgumentException("快递单号ExpressNo值"+aExpressNo+"的长度"+aExpressNo.length()+"大于最大值50");
		ExpressNo = aExpressNo;
	}
	public String getTransferDate()
	{
		if( TransferDate != null )
			return fDate.getString(TransferDate);
		else
			return null;
	}
	public void setTransferDate(Date aTransferDate)
	{
		TransferDate = aTransferDate;
	}
	public void setTransferDate(String aTransferDate)
	{
		if (aTransferDate != null && !aTransferDate.equals("") )
		{
			TransferDate = fDate.getDate( aTransferDate );
		}
		else
			TransferDate = null;
	}

	public String getRegister()
	{
		return Register;
	}
	public void setRegister(String aRegister)
	{
		if(aRegister!=null && aRegister.length()>30)
			throw new IllegalArgumentException("登记人Register值"+aRegister+"的长度"+aRegister.length()+"大于最大值30");
		Register = aRegister;
	}
	public String getExpressRegister()
	{
		return ExpressRegister;
	}
	public void setExpressRegister(String aExpressRegister)
	{
		if(aExpressRegister!=null && aExpressRegister.length()>30)
			throw new IllegalArgumentException("快递登记人ExpressRegister值"+aExpressRegister+"的长度"+aExpressRegister.length()+"大于最大值30");
		ExpressRegister = aExpressRegister;
	}
	public String getExpressDate()
	{
		if( ExpressDate != null )
			return fDate.getString(ExpressDate);
		else
			return null;
	}
	public void setExpressDate(Date aExpressDate)
	{
		ExpressDate = aExpressDate;
	}
	public void setExpressDate(String aExpressDate)
	{
		if (aExpressDate != null && !aExpressDate.equals("") )
		{
			ExpressDate = fDate.getDate( aExpressDate );
		}
		else
			ExpressDate = null;
	}

	public String getReceiveDate()
	{
		if( ReceiveDate != null )
			return fDate.getString(ReceiveDate);
		else
			return null;
	}
	public void setReceiveDate(Date aReceiveDate)
	{
		ReceiveDate = aReceiveDate;
	}
	public void setReceiveDate(String aReceiveDate)
	{
		if (aReceiveDate != null && !aReceiveDate.equals("") )
		{
			ReceiveDate = fDate.getDate( aReceiveDate );
		}
		else
			ReceiveDate = null;
	}

	public String getGetPolDate()
	{
		if( GetPolDate != null )
			return fDate.getString(GetPolDate);
		else
			return null;
	}
	public void setGetPolDate(Date aGetPolDate)
	{
		GetPolDate = aGetPolDate;
	}
	public void setGetPolDate(String aGetPolDate)
	{
		if (aGetPolDate != null && !aGetPolDate.equals("") )
		{
			GetPolDate = fDate.getDate( aGetPolDate );
		}
		else
			GetPolDate = null;
	}

	public String getCustomerGetPolDate()
	{
		if( CustomerGetPolDate != null )
			return fDate.getString(CustomerGetPolDate);
		else
			return null;
	}
	public void setCustomerGetPolDate(Date aCustomerGetPolDate)
	{
		CustomerGetPolDate = aCustomerGetPolDate;
	}
	public void setCustomerGetPolDate(String aCustomerGetPolDate)
	{
		if (aCustomerGetPolDate != null && !aCustomerGetPolDate.equals("") )
		{
			CustomerGetPolDate = fDate.getDate( aCustomerGetPolDate );
		}
		else
			CustomerGetPolDate = null;
	}

	public String getInputDate()
	{
		if( InputDate != null )
			return fDate.getString(InputDate);
		else
			return null;
	}
	public void setInputDate(Date aInputDate)
	{
		InputDate = aInputDate;
	}
	public void setInputDate(String aInputDate)
	{
		if (aInputDate != null && !aInputDate.equals("") )
		{
			InputDate = fDate.getDate( aInputDate );
		}
		else
			InputDate = null;
	}

	/**
	* 承保管理机构
	*/
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
	/**
	* 承保公司代码
	*/
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
	* 使用另外一个 LCPostalInfoSchema 对象给 Schema 赋值
	* @param: aLCPostalInfoSchema LCPostalInfoSchema
	**/
	public void setSchema(LCPostalInfoSchema aLCPostalInfoSchema)
	{
		this.GrpContNo = aLCPostalInfoSchema.getGrpContNo();
		this.GrpPropNo = aLCPostalInfoSchema.getGrpPropNo();
		this.SerialNo = aLCPostalInfoSchema.getSerialNo();
		this.RegisterPassFlag = aLCPostalInfoSchema.getRegisterPassFlag();
		this.Reason = aLCPostalInfoSchema.getReason();
		this.TransferType = aLCPostalInfoSchema.getTransferType();
		this.ExpressCorpName = aLCPostalInfoSchema.getExpressCorpName();
		this.ExpressNo = aLCPostalInfoSchema.getExpressNo();
		this.TransferDate = fDate.getDate( aLCPostalInfoSchema.getTransferDate());
		this.Register = aLCPostalInfoSchema.getRegister();
		this.ExpressRegister = aLCPostalInfoSchema.getExpressRegister();
		this.ExpressDate = fDate.getDate( aLCPostalInfoSchema.getExpressDate());
		this.ReceiveDate = fDate.getDate( aLCPostalInfoSchema.getReceiveDate());
		this.GetPolDate = fDate.getDate( aLCPostalInfoSchema.getGetPolDate());
		this.CustomerGetPolDate = fDate.getDate( aLCPostalInfoSchema.getCustomerGetPolDate());
		this.InputDate = fDate.getDate( aLCPostalInfoSchema.getInputDate());
		this.ManageCom = aLCPostalInfoSchema.getManageCom();
		this.ComCode = aLCPostalInfoSchema.getComCode();
		this.MakeOperator = aLCPostalInfoSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLCPostalInfoSchema.getMakeDate());
		this.MakeTime = aLCPostalInfoSchema.getMakeTime();
		this.ModifyOperator = aLCPostalInfoSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLCPostalInfoSchema.getModifyDate());
		this.ModifyTime = aLCPostalInfoSchema.getModifyTime();
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
			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("GrpPropNo") == null )
				this.GrpPropNo = null;
			else
				this.GrpPropNo = rs.getString("GrpPropNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("RegisterPassFlag") == null )
				this.RegisterPassFlag = null;
			else
				this.RegisterPassFlag = rs.getString("RegisterPassFlag").trim();

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("TransferType") == null )
				this.TransferType = null;
			else
				this.TransferType = rs.getString("TransferType").trim();

			if( rs.getString("ExpressCorpName") == null )
				this.ExpressCorpName = null;
			else
				this.ExpressCorpName = rs.getString("ExpressCorpName").trim();

			if( rs.getString("ExpressNo") == null )
				this.ExpressNo = null;
			else
				this.ExpressNo = rs.getString("ExpressNo").trim();

			this.TransferDate = rs.getDate("TransferDate");
			if( rs.getString("Register") == null )
				this.Register = null;
			else
				this.Register = rs.getString("Register").trim();

			if( rs.getString("ExpressRegister") == null )
				this.ExpressRegister = null;
			else
				this.ExpressRegister = rs.getString("ExpressRegister").trim();

			this.ExpressDate = rs.getDate("ExpressDate");
			this.ReceiveDate = rs.getDate("ReceiveDate");
			this.GetPolDate = rs.getDate("GetPolDate");
			this.CustomerGetPolDate = rs.getDate("CustomerGetPolDate");
			this.InputDate = rs.getDate("InputDate");
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
			logger.debug("数据库中的LCPostalInfo表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPostalInfoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCPostalInfoSchema getSchema()
	{
		LCPostalInfoSchema aLCPostalInfoSchema = new LCPostalInfoSchema();
		aLCPostalInfoSchema.setSchema(this);
		return aLCPostalInfoSchema;
	}

	public LCPostalInfoDB getDB()
	{
		LCPostalInfoDB aDBOper = new LCPostalInfoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPostalInfo描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpPropNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RegisterPassFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(TransferType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpressCorpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpressNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( TransferDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Register)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ExpressRegister)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ExpressDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ReceiveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( GetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( CustomerGetPolDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( InputDate ))); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCPostalInfo>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpPropNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RegisterPassFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TransferType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ExpressCorpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ExpressNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TransferDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			Register = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ExpressRegister = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ExpressDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			ReceiveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			GetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			CustomerGetPolDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			InputDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCPostalInfoSchema";
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
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPropNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("RegisterPassFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RegisterPassFlag));
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("TransferType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TransferType));
		}
		if (FCode.equalsIgnoreCase("ExpressCorpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpressCorpName));
		}
		if (FCode.equalsIgnoreCase("ExpressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpressNo));
		}
		if (FCode.equalsIgnoreCase("TransferDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getTransferDate()));
		}
		if (FCode.equalsIgnoreCase("Register"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Register));
		}
		if (FCode.equalsIgnoreCase("ExpressRegister"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ExpressRegister));
		}
		if (FCode.equalsIgnoreCase("ExpressDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getExpressDate()));
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("CustomerGetPolDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getCustomerGetPolDate()));
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
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
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpPropNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RegisterPassFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TransferType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ExpressCorpName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ExpressNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getTransferDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Register);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ExpressRegister);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getExpressDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getReceiveDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getGetPolDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getCustomerGetPolDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getInputDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 23:
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

		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpContNo = FValue.trim();
			}
			else
				GrpContNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpPropNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPropNo = FValue.trim();
			}
			else
				GrpPropNo = null;
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SerialNo = FValue.trim();
			}
			else
				SerialNo = null;
		}
		if (FCode.equalsIgnoreCase("RegisterPassFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RegisterPassFlag = FValue.trim();
			}
			else
				RegisterPassFlag = null;
		}
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("TransferType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TransferType = FValue.trim();
			}
			else
				TransferType = null;
		}
		if (FCode.equalsIgnoreCase("ExpressCorpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpressCorpName = FValue.trim();
			}
			else
				ExpressCorpName = null;
		}
		if (FCode.equalsIgnoreCase("ExpressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpressNo = FValue.trim();
			}
			else
				ExpressNo = null;
		}
		if (FCode.equalsIgnoreCase("TransferDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				TransferDate = fDate.getDate( FValue );
			}
			else
				TransferDate = null;
		}
		if (FCode.equalsIgnoreCase("Register"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Register = FValue.trim();
			}
			else
				Register = null;
		}
		if (FCode.equalsIgnoreCase("ExpressRegister"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ExpressRegister = FValue.trim();
			}
			else
				ExpressRegister = null;
		}
		if (FCode.equalsIgnoreCase("ExpressDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ExpressDate = fDate.getDate( FValue );
			}
			else
				ExpressDate = null;
		}
		if (FCode.equalsIgnoreCase("ReceiveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ReceiveDate = fDate.getDate( FValue );
			}
			else
				ReceiveDate = null;
		}
		if (FCode.equalsIgnoreCase("GetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				GetPolDate = fDate.getDate( FValue );
			}
			else
				GetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("CustomerGetPolDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				CustomerGetPolDate = fDate.getDate( FValue );
			}
			else
				CustomerGetPolDate = null;
		}
		if (FCode.equalsIgnoreCase("InputDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				InputDate = fDate.getDate( FValue );
			}
			else
				InputDate = null;
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
		LCPostalInfoSchema other = (LCPostalInfoSchema)otherObject;
		return
			GrpContNo.equals(other.getGrpContNo())
			&& GrpPropNo.equals(other.getGrpPropNo())
			&& SerialNo.equals(other.getSerialNo())
			&& RegisterPassFlag.equals(other.getRegisterPassFlag())
			&& Reason.equals(other.getReason())
			&& TransferType.equals(other.getTransferType())
			&& ExpressCorpName.equals(other.getExpressCorpName())
			&& ExpressNo.equals(other.getExpressNo())
			&& fDate.getString(TransferDate).equals(other.getTransferDate())
			&& Register.equals(other.getRegister())
			&& ExpressRegister.equals(other.getExpressRegister())
			&& fDate.getString(ExpressDate).equals(other.getExpressDate())
			&& fDate.getString(ReceiveDate).equals(other.getReceiveDate())
			&& fDate.getString(GetPolDate).equals(other.getGetPolDate())
			&& fDate.getString(CustomerGetPolDate).equals(other.getCustomerGetPolDate())
			&& fDate.getString(InputDate).equals(other.getInputDate())
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
		if( strFieldName.equals("GrpContNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("RegisterPassFlag") ) {
			return 3;
		}
		if( strFieldName.equals("Reason") ) {
			return 4;
		}
		if( strFieldName.equals("TransferType") ) {
			return 5;
		}
		if( strFieldName.equals("ExpressCorpName") ) {
			return 6;
		}
		if( strFieldName.equals("ExpressNo") ) {
			return 7;
		}
		if( strFieldName.equals("TransferDate") ) {
			return 8;
		}
		if( strFieldName.equals("Register") ) {
			return 9;
		}
		if( strFieldName.equals("ExpressRegister") ) {
			return 10;
		}
		if( strFieldName.equals("ExpressDate") ) {
			return 11;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return 12;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return 13;
		}
		if( strFieldName.equals("CustomerGetPolDate") ) {
			return 14;
		}
		if( strFieldName.equals("InputDate") ) {
			return 15;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 16;
		}
		if( strFieldName.equals("ComCode") ) {
			return 17;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 23;
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
				strFieldName = "GrpContNo";
				break;
			case 1:
				strFieldName = "GrpPropNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "RegisterPassFlag";
				break;
			case 4:
				strFieldName = "Reason";
				break;
			case 5:
				strFieldName = "TransferType";
				break;
			case 6:
				strFieldName = "ExpressCorpName";
				break;
			case 7:
				strFieldName = "ExpressNo";
				break;
			case 8:
				strFieldName = "TransferDate";
				break;
			case 9:
				strFieldName = "Register";
				break;
			case 10:
				strFieldName = "ExpressRegister";
				break;
			case 11:
				strFieldName = "ExpressDate";
				break;
			case 12:
				strFieldName = "ReceiveDate";
				break;
			case 13:
				strFieldName = "GetPolDate";
				break;
			case 14:
				strFieldName = "CustomerGetPolDate";
				break;
			case 15:
				strFieldName = "InputDate";
				break;
			case 16:
				strFieldName = "ManageCom";
				break;
			case 17:
				strFieldName = "ComCode";
				break;
			case 18:
				strFieldName = "MakeOperator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyOperator";
				break;
			case 22:
				strFieldName = "ModifyDate";
				break;
			case 23:
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
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpPropNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RegisterPassFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransferType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpressCorpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpressNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TransferDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("Register") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpressRegister") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ExpressDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ReceiveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("GetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CustomerGetPolDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("InputDate") ) {
			return Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
