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
import com.sinosoft.lis.db.LLOperationDB;

/*
 * <p>ClassName: LLOperationSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 理赔管理
 */
public class LLOperationSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLOperationSchema.class);
	// @Field
	/** 赔案号 */
	private String ClmNo;
	/** 分案号 */
	private String CaseNo;
	/** 序号 */
	private String SerialNo;
	/** 事故号 */
	private String CaseRelaNo;
	/** 出险人客户号 */
	private String CustomerNo;
	/** 出险人名称 */
	private String CustomerName;
	/** 手术类型 */
	private String OperationType;
	/** 手术代码 */
	private String OperationCode;
	/** 手术名称 */
	private String OperationName;
	/** 手术档次 */
	private int OpLevel;
	/** 手术等级 */
	private int OpGrade;
	/** 手术费用 */
	private double OpFee;
	/** 服务机构代码 */
	private String UnitNo;
	/** 服务机构名称 */
	private String UnitName;
	/** 确诊日期 */
	private Date DiagnoseDate;
	/** 调整金额 */
	private double AdjSum;
	/** 调整原因 */
	private String AdjReason;
	/** 调整备注 */
	private String AdjRemark;
	/** 主切口标志 */
	private String MainOp;
	/** 管理机构 */
	private String MngCom;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 自费金额 */
	private double SelfAmnt;
	/** 币别 */
	private String Currency;
	/** 手术类型2 */
	private String FeeType;
	/** 公司代码 */
	private String ComCode;
	/** 最后一次修改操作员 */
	private String ModifyOperator;

	public static final int FIELDNUM = 30;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLOperationSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "ClmNo";
		pk[1] = "CaseNo";
		pk[2] = "SerialNo";
		pk[3] = "CustomerNo";

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
		LLOperationSchema cloned = (LLOperationSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClmNo()
	{
		return ClmNo;
	}
	public void setClmNo(String aClmNo)
	{
		if(aClmNo!=null && aClmNo.length()>20)
			throw new IllegalArgumentException("赔案号ClmNo值"+aClmNo+"的长度"+aClmNo.length()+"大于最大值20");
		ClmNo = aClmNo;
	}
	public String getCaseNo()
	{
		return CaseNo;
	}
	public void setCaseNo(String aCaseNo)
	{
		if(aCaseNo!=null && aCaseNo.length()>20)
			throw new IllegalArgumentException("分案号CaseNo值"+aCaseNo+"的长度"+aCaseNo.length()+"大于最大值20");
		CaseNo = aCaseNo;
	}
	public String getSerialNo()
	{
		return SerialNo;
	}
	public void setSerialNo(String aSerialNo)
	{
		if(aSerialNo!=null && aSerialNo.length()>20)
			throw new IllegalArgumentException("序号SerialNo值"+aSerialNo+"的长度"+aSerialNo.length()+"大于最大值20");
		SerialNo = aSerialNo;
	}
	/**
	* 受理事故号
	*/
	public String getCaseRelaNo()
	{
		return CaseRelaNo;
	}
	public void setCaseRelaNo(String aCaseRelaNo)
	{
		if(aCaseRelaNo!=null && aCaseRelaNo.length()>20)
			throw new IllegalArgumentException("事故号CaseRelaNo值"+aCaseRelaNo+"的长度"+aCaseRelaNo.length()+"大于最大值20");
		CaseRelaNo = aCaseRelaNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>24)
			throw new IllegalArgumentException("出险人客户号CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值24");
		CustomerNo = aCustomerNo;
	}
	public String getCustomerName()
	{
		return CustomerName;
	}
	public void setCustomerName(String aCustomerName)
	{
		if(aCustomerName!=null && aCustomerName.length()>40)
			throw new IllegalArgumentException("出险人名称CustomerName值"+aCustomerName+"的长度"+aCustomerName.length()+"大于最大值40");
		CustomerName = aCustomerName;
	}
	/**
	* D--手术<p>
	* E--疾病
	*/
	public String getOperationType()
	{
		return OperationType;
	}
	public void setOperationType(String aOperationType)
	{
		if(aOperationType!=null && aOperationType.length()>20)
			throw new IllegalArgumentException("手术类型OperationType值"+aOperationType+"的长度"+aOperationType.length()+"大于最大值20");
		OperationType = aOperationType;
	}
	public String getOperationCode()
	{
		return OperationCode;
	}
	public void setOperationCode(String aOperationCode)
	{
		if(aOperationCode!=null && aOperationCode.length()>20)
			throw new IllegalArgumentException("手术代码OperationCode值"+aOperationCode+"的长度"+aOperationCode.length()+"大于最大值20");
		OperationCode = aOperationCode;
	}
	public String getOperationName()
	{
		return OperationName;
	}
	public void setOperationName(String aOperationName)
	{
		if(aOperationName!=null && aOperationName.length()>200)
			throw new IllegalArgumentException("手术名称OperationName值"+aOperationName+"的长度"+aOperationName.length()+"大于最大值200");
		OperationName = aOperationName;
	}
	/**
	* [不用]
	*/
	public int getOpLevel()
	{
		return OpLevel;
	}
	public void setOpLevel(int aOpLevel)
	{
		OpLevel = aOpLevel;
	}
	public void setOpLevel(String aOpLevel)
	{
		if (aOpLevel != null && !aOpLevel.equals(""))
		{
			Integer tInteger = new Integer(aOpLevel);
			int i = tInteger.intValue();
			OpLevel = i;
		}
	}

	/**
	* [不用]
	*/
	public int getOpGrade()
	{
		return OpGrade;
	}
	public void setOpGrade(int aOpGrade)
	{
		OpGrade = aOpGrade;
	}
	public void setOpGrade(String aOpGrade)
	{
		if (aOpGrade != null && !aOpGrade.equals(""))
		{
			Integer tInteger = new Integer(aOpGrade);
			int i = tInteger.intValue();
			OpGrade = i;
		}
	}

	public double getOpFee()
	{
		return OpFee;
	}
	public void setOpFee(double aOpFee)
	{
		OpFee = aOpFee;
	}
	public void setOpFee(String aOpFee)
	{
		if (aOpFee != null && !aOpFee.equals(""))
		{
			Double tDouble = new Double(aOpFee);
			double d = tDouble.doubleValue();
			OpFee = d;
		}
	}

	public String getUnitNo()
	{
		return UnitNo;
	}
	public void setUnitNo(String aUnitNo)
	{
		if(aUnitNo!=null && aUnitNo.length()>20)
			throw new IllegalArgumentException("服务机构代码UnitNo值"+aUnitNo+"的长度"+aUnitNo.length()+"大于最大值20");
		UnitNo = aUnitNo;
	}
	public String getUnitName()
	{
		return UnitName;
	}
	public void setUnitName(String aUnitName)
	{
		if(aUnitName!=null && aUnitName.length()>80)
			throw new IllegalArgumentException("服务机构名称UnitName值"+aUnitName+"的长度"+aUnitName.length()+"大于最大值80");
		UnitName = aUnitName;
	}
	public String getDiagnoseDate()
	{
		if( DiagnoseDate != null )
			return fDate.getString(DiagnoseDate);
		else
			return null;
	}
	public void setDiagnoseDate(Date aDiagnoseDate)
	{
		DiagnoseDate = aDiagnoseDate;
	}
	public void setDiagnoseDate(String aDiagnoseDate)
	{
		if (aDiagnoseDate != null && !aDiagnoseDate.equals("") )
		{
			DiagnoseDate = fDate.getDate( aDiagnoseDate );
		}
		else
			DiagnoseDate = null;
	}

	/**
	* 参与计算的金额，已经扣除了责任外的金额
	*/
	public double getAdjSum()
	{
		return AdjSum;
	}
	public void setAdjSum(double aAdjSum)
	{
		AdjSum = aAdjSum;
	}
	public void setAdjSum(String aAdjSum)
	{
		if (aAdjSum != null && !aAdjSum.equals(""))
		{
			Double tDouble = new Double(aAdjSum);
			double d = tDouble.doubleValue();
			AdjSum = d;
		}
	}

	public String getAdjReason()
	{
		return AdjReason;
	}
	public void setAdjReason(String aAdjReason)
	{
		if(aAdjReason!=null && aAdjReason.length()>6)
			throw new IllegalArgumentException("调整原因AdjReason值"+aAdjReason+"的长度"+aAdjReason.length()+"大于最大值6");
		AdjReason = aAdjReason;
	}
	public String getAdjRemark()
	{
		return AdjRemark;
	}
	public void setAdjRemark(String aAdjRemark)
	{
		if(aAdjRemark!=null && aAdjRemark.length()>1000)
			throw new IllegalArgumentException("调整备注AdjRemark值"+aAdjRemark+"的长度"+aAdjRemark.length()+"大于最大值1000");
		AdjRemark = aAdjRemark;
	}
	/**
	* 0 主要切口<p>
	* 1 非主要切口
	*/
	public String getMainOp()
	{
		return MainOp;
	}
	public void setMainOp(String aMainOp)
	{
		if(aMainOp!=null && aMainOp.length()>1)
			throw new IllegalArgumentException("主切口标志MainOp值"+aMainOp+"的长度"+aMainOp.length()+"大于最大值1");
		MainOp = aMainOp;
	}
	public String getMngCom()
	{
		return MngCom;
	}
	public void setMngCom(String aMngCom)
	{
		if(aMngCom!=null && aMngCom.length()>10)
			throw new IllegalArgumentException("管理机构MngCom值"+aMngCom+"的长度"+aMngCom.length()+"大于最大值10");
		MngCom = aMngCom;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>20)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值20");
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
	public double getSelfAmnt()
	{
		return SelfAmnt;
	}
	public void setSelfAmnt(double aSelfAmnt)
	{
		SelfAmnt = aSelfAmnt;
	}
	public void setSelfAmnt(String aSelfAmnt)
	{
		if (aSelfAmnt != null && !aSelfAmnt.equals(""))
		{
			Double tDouble = new Double(aSelfAmnt);
			double d = tDouble.doubleValue();
			SelfAmnt = d;
		}
	}

	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		if(aCurrency!=null && aCurrency.length()>3)
			throw new IllegalArgumentException("币别Currency值"+aCurrency+"的长度"+aCurrency.length()+"大于最大值3");
		Currency = aCurrency;
	}
	/**
	* D--手术<p>
	* E--疾病
	*/
	public String getFeeType()
	{
		return FeeType;
	}
	public void setFeeType(String aFeeType)
	{
		if(aFeeType!=null && aFeeType.length()>20)
			throw new IllegalArgumentException("手术类型2FeeType值"+aFeeType+"的长度"+aFeeType.length()+"大于最大值20");
		FeeType = aFeeType;
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

	/**
	* 使用另外一个 LLOperationSchema 对象给 Schema 赋值
	* @param: aLLOperationSchema LLOperationSchema
	**/
	public void setSchema(LLOperationSchema aLLOperationSchema)
	{
		this.ClmNo = aLLOperationSchema.getClmNo();
		this.CaseNo = aLLOperationSchema.getCaseNo();
		this.SerialNo = aLLOperationSchema.getSerialNo();
		this.CaseRelaNo = aLLOperationSchema.getCaseRelaNo();
		this.CustomerNo = aLLOperationSchema.getCustomerNo();
		this.CustomerName = aLLOperationSchema.getCustomerName();
		this.OperationType = aLLOperationSchema.getOperationType();
		this.OperationCode = aLLOperationSchema.getOperationCode();
		this.OperationName = aLLOperationSchema.getOperationName();
		this.OpLevel = aLLOperationSchema.getOpLevel();
		this.OpGrade = aLLOperationSchema.getOpGrade();
		this.OpFee = aLLOperationSchema.getOpFee();
		this.UnitNo = aLLOperationSchema.getUnitNo();
		this.UnitName = aLLOperationSchema.getUnitName();
		this.DiagnoseDate = fDate.getDate( aLLOperationSchema.getDiagnoseDate());
		this.AdjSum = aLLOperationSchema.getAdjSum();
		this.AdjReason = aLLOperationSchema.getAdjReason();
		this.AdjRemark = aLLOperationSchema.getAdjRemark();
		this.MainOp = aLLOperationSchema.getMainOp();
		this.MngCom = aLLOperationSchema.getMngCom();
		this.Operator = aLLOperationSchema.getOperator();
		this.MakeDate = fDate.getDate( aLLOperationSchema.getMakeDate());
		this.MakeTime = aLLOperationSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLLOperationSchema.getModifyDate());
		this.ModifyTime = aLLOperationSchema.getModifyTime();
		this.SelfAmnt = aLLOperationSchema.getSelfAmnt();
		this.Currency = aLLOperationSchema.getCurrency();
		this.FeeType = aLLOperationSchema.getFeeType();
		this.ComCode = aLLOperationSchema.getComCode();
		this.ModifyOperator = aLLOperationSchema.getModifyOperator();
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
			if( rs.getString("ClmNo") == null )
				this.ClmNo = null;
			else
				this.ClmNo = rs.getString("ClmNo").trim();

			if( rs.getString("CaseNo") == null )
				this.CaseNo = null;
			else
				this.CaseNo = rs.getString("CaseNo").trim();

			if( rs.getString("SerialNo") == null )
				this.SerialNo = null;
			else
				this.SerialNo = rs.getString("SerialNo").trim();

			if( rs.getString("CaseRelaNo") == null )
				this.CaseRelaNo = null;
			else
				this.CaseRelaNo = rs.getString("CaseRelaNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("CustomerName") == null )
				this.CustomerName = null;
			else
				this.CustomerName = rs.getString("CustomerName").trim();

			if( rs.getString("OperationType") == null )
				this.OperationType = null;
			else
				this.OperationType = rs.getString("OperationType").trim();

			if( rs.getString("OperationCode") == null )
				this.OperationCode = null;
			else
				this.OperationCode = rs.getString("OperationCode").trim();

			if( rs.getString("OperationName") == null )
				this.OperationName = null;
			else
				this.OperationName = rs.getString("OperationName").trim();

			this.OpLevel = rs.getInt("OpLevel");
			this.OpGrade = rs.getInt("OpGrade");
			this.OpFee = rs.getDouble("OpFee");
			if( rs.getString("UnitNo") == null )
				this.UnitNo = null;
			else
				this.UnitNo = rs.getString("UnitNo").trim();

			if( rs.getString("UnitName") == null )
				this.UnitName = null;
			else
				this.UnitName = rs.getString("UnitName").trim();

			this.DiagnoseDate = rs.getDate("DiagnoseDate");
			this.AdjSum = rs.getDouble("AdjSum");
			if( rs.getString("AdjReason") == null )
				this.AdjReason = null;
			else
				this.AdjReason = rs.getString("AdjReason").trim();

			if( rs.getString("AdjRemark") == null )
				this.AdjRemark = null;
			else
				this.AdjRemark = rs.getString("AdjRemark").trim();

			if( rs.getString("MainOp") == null )
				this.MainOp = null;
			else
				this.MainOp = rs.getString("MainOp").trim();

			if( rs.getString("MngCom") == null )
				this.MngCom = null;
			else
				this.MngCom = rs.getString("MngCom").trim();

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

			this.SelfAmnt = rs.getDouble("SelfAmnt");
			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

			if( rs.getString("FeeType") == null )
				this.FeeType = null;
			else
				this.FeeType = rs.getString("FeeType").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("ModifyOperator") == null )
				this.ModifyOperator = null;
			else
				this.ModifyOperator = rs.getString("ModifyOperator").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LLOperation表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLOperationSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLOperationSchema getSchema()
	{
		LLOperationSchema aLLOperationSchema = new LLOperationSchema();
		aLLOperationSchema.setSchema(this);
		return aLLOperationSchema;
	}

	public LLOperationDB getDB()
	{
		LLOperationDB aDBOper = new LLOperationDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLOperation描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClmNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SerialNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CaseRelaNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OperationName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OpLevel));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OpGrade));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(OpFee));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnitNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnitName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( DiagnoseDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AdjSum));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AdjRemark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MainOp)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MngCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(SelfAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(FeeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ComCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyOperator));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLOperation>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClmNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CaseNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SerialNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			CaseRelaNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			CustomerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			OperationCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OperationName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			OpLevel= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			OpGrade= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			OpFee = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).doubleValue();
			UnitNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			UnitName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			DiagnoseDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			AdjSum = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			AdjReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AdjRemark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			MainOp = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MngCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			SelfAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			FeeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLOperationSchema";
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
		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClmNo));
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseNo));
		}
		if (FCode.equalsIgnoreCase("SerialNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SerialNo));
		}
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CaseRelaNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerName));
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperationType));
		}
		if (FCode.equalsIgnoreCase("OperationCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperationCode));
		}
		if (FCode.equalsIgnoreCase("OperationName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperationName));
		}
		if (FCode.equalsIgnoreCase("OpLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OpLevel));
		}
		if (FCode.equalsIgnoreCase("OpGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OpGrade));
		}
		if (FCode.equalsIgnoreCase("OpFee"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OpFee));
		}
		if (FCode.equalsIgnoreCase("UnitNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitNo));
		}
		if (FCode.equalsIgnoreCase("UnitName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitName));
		}
		if (FCode.equalsIgnoreCase("DiagnoseDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getDiagnoseDate()));
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjSum));
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjReason));
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AdjRemark));
		}
		if (FCode.equalsIgnoreCase("MainOp"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainOp));
		}
		if (FCode.equalsIgnoreCase("MngCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MngCom));
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
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SelfAmnt));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FeeType));
		}
		if (FCode.equalsIgnoreCase("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyOperator));
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
				strFieldValue = StrTool.GBKToUnicode(ClmNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CaseNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SerialNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(CaseRelaNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(CustomerName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OperationType);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(OperationCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OperationName);
				break;
			case 9:
				strFieldValue = String.valueOf(OpLevel);
				break;
			case 10:
				strFieldValue = String.valueOf(OpGrade);
				break;
			case 11:
				strFieldValue = String.valueOf(OpFee);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(UnitNo);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UnitName);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getDiagnoseDate()));
				break;
			case 15:
				strFieldValue = String.valueOf(AdjSum);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(AdjReason);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AdjRemark);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MainOp);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MngCom);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 25:
				strFieldValue = String.valueOf(SelfAmnt);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(Currency);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(FeeType);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
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

		if (FCode.equalsIgnoreCase("ClmNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClmNo = FValue.trim();
			}
			else
				ClmNo = null;
		}
		if (FCode.equalsIgnoreCase("CaseNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseNo = FValue.trim();
			}
			else
				CaseNo = null;
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
		if (FCode.equalsIgnoreCase("CaseRelaNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CaseRelaNo = FValue.trim();
			}
			else
				CaseRelaNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("CustomerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerName = FValue.trim();
			}
			else
				CustomerName = null;
		}
		if (FCode.equalsIgnoreCase("OperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperationType = FValue.trim();
			}
			else
				OperationType = null;
		}
		if (FCode.equalsIgnoreCase("OperationCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperationCode = FValue.trim();
			}
			else
				OperationCode = null;
		}
		if (FCode.equalsIgnoreCase("OperationName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OperationName = FValue.trim();
			}
			else
				OperationName = null;
		}
		if (FCode.equalsIgnoreCase("OpLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OpLevel = i;
			}
		}
		if (FCode.equalsIgnoreCase("OpGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OpGrade = i;
			}
		}
		if (FCode.equalsIgnoreCase("OpFee"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				OpFee = d;
			}
		}
		if (FCode.equalsIgnoreCase("UnitNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitNo = FValue.trim();
			}
			else
				UnitNo = null;
		}
		if (FCode.equalsIgnoreCase("UnitName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitName = FValue.trim();
			}
			else
				UnitName = null;
		}
		if (FCode.equalsIgnoreCase("DiagnoseDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				DiagnoseDate = fDate.getDate( FValue );
			}
			else
				DiagnoseDate = null;
		}
		if (FCode.equalsIgnoreCase("AdjSum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AdjSum = d;
			}
		}
		if (FCode.equalsIgnoreCase("AdjReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjReason = FValue.trim();
			}
			else
				AdjReason = null;
		}
		if (FCode.equalsIgnoreCase("AdjRemark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AdjRemark = FValue.trim();
			}
			else
				AdjRemark = null;
		}
		if (FCode.equalsIgnoreCase("MainOp"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainOp = FValue.trim();
			}
			else
				MainOp = null;
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
		if (FCode.equalsIgnoreCase("SelfAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				SelfAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Currency = FValue.trim();
			}
			else
				Currency = null;
		}
		if (FCode.equalsIgnoreCase("FeeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FeeType = FValue.trim();
			}
			else
				FeeType = null;
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
		if (FCode.equalsIgnoreCase("ModifyOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyOperator = FValue.trim();
			}
			else
				ModifyOperator = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LLOperationSchema other = (LLOperationSchema)otherObject;
		return
			ClmNo.equals(other.getClmNo())
			&& CaseNo.equals(other.getCaseNo())
			&& SerialNo.equals(other.getSerialNo())
			&& CaseRelaNo.equals(other.getCaseRelaNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& CustomerName.equals(other.getCustomerName())
			&& OperationType.equals(other.getOperationType())
			&& OperationCode.equals(other.getOperationCode())
			&& OperationName.equals(other.getOperationName())
			&& OpLevel == other.getOpLevel()
			&& OpGrade == other.getOpGrade()
			&& OpFee == other.getOpFee()
			&& UnitNo.equals(other.getUnitNo())
			&& UnitName.equals(other.getUnitName())
			&& fDate.getString(DiagnoseDate).equals(other.getDiagnoseDate())
			&& AdjSum == other.getAdjSum()
			&& AdjReason.equals(other.getAdjReason())
			&& AdjRemark.equals(other.getAdjRemark())
			&& MainOp.equals(other.getMainOp())
			&& MngCom.equals(other.getMngCom())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& SelfAmnt == other.getSelfAmnt()
			&& Currency.equals(other.getCurrency())
			&& FeeType.equals(other.getFeeType())
			&& ComCode.equals(other.getComCode())
			&& ModifyOperator.equals(other.getModifyOperator());
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
		if( strFieldName.equals("ClmNo") ) {
			return 0;
		}
		if( strFieldName.equals("CaseNo") ) {
			return 1;
		}
		if( strFieldName.equals("SerialNo") ) {
			return 2;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return 3;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 4;
		}
		if( strFieldName.equals("CustomerName") ) {
			return 5;
		}
		if( strFieldName.equals("OperationType") ) {
			return 6;
		}
		if( strFieldName.equals("OperationCode") ) {
			return 7;
		}
		if( strFieldName.equals("OperationName") ) {
			return 8;
		}
		if( strFieldName.equals("OpLevel") ) {
			return 9;
		}
		if( strFieldName.equals("OpGrade") ) {
			return 10;
		}
		if( strFieldName.equals("OpFee") ) {
			return 11;
		}
		if( strFieldName.equals("UnitNo") ) {
			return 12;
		}
		if( strFieldName.equals("UnitName") ) {
			return 13;
		}
		if( strFieldName.equals("DiagnoseDate") ) {
			return 14;
		}
		if( strFieldName.equals("AdjSum") ) {
			return 15;
		}
		if( strFieldName.equals("AdjReason") ) {
			return 16;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return 17;
		}
		if( strFieldName.equals("MainOp") ) {
			return 18;
		}
		if( strFieldName.equals("MngCom") ) {
			return 19;
		}
		if( strFieldName.equals("Operator") ) {
			return 20;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 21;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 22;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 24;
		}
		if( strFieldName.equals("SelfAmnt") ) {
			return 25;
		}
		if( strFieldName.equals("Currency") ) {
			return 26;
		}
		if( strFieldName.equals("FeeType") ) {
			return 27;
		}
		if( strFieldName.equals("ComCode") ) {
			return 28;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 29;
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
				strFieldName = "ClmNo";
				break;
			case 1:
				strFieldName = "CaseNo";
				break;
			case 2:
				strFieldName = "SerialNo";
				break;
			case 3:
				strFieldName = "CaseRelaNo";
				break;
			case 4:
				strFieldName = "CustomerNo";
				break;
			case 5:
				strFieldName = "CustomerName";
				break;
			case 6:
				strFieldName = "OperationType";
				break;
			case 7:
				strFieldName = "OperationCode";
				break;
			case 8:
				strFieldName = "OperationName";
				break;
			case 9:
				strFieldName = "OpLevel";
				break;
			case 10:
				strFieldName = "OpGrade";
				break;
			case 11:
				strFieldName = "OpFee";
				break;
			case 12:
				strFieldName = "UnitNo";
				break;
			case 13:
				strFieldName = "UnitName";
				break;
			case 14:
				strFieldName = "DiagnoseDate";
				break;
			case 15:
				strFieldName = "AdjSum";
				break;
			case 16:
				strFieldName = "AdjReason";
				break;
			case 17:
				strFieldName = "AdjRemark";
				break;
			case 18:
				strFieldName = "MainOp";
				break;
			case 19:
				strFieldName = "MngCom";
				break;
			case 20:
				strFieldName = "Operator";
				break;
			case 21:
				strFieldName = "MakeDate";
				break;
			case 22:
				strFieldName = "MakeTime";
				break;
			case 23:
				strFieldName = "ModifyDate";
				break;
			case 24:
				strFieldName = "ModifyTime";
				break;
			case 25:
				strFieldName = "SelfAmnt";
				break;
			case 26:
				strFieldName = "Currency";
				break;
			case 27:
				strFieldName = "FeeType";
				break;
			case 28:
				strFieldName = "ComCode";
				break;
			case 29:
				strFieldName = "ModifyOperator";
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
		if( strFieldName.equals("ClmNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SerialNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CaseRelaNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperationCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperationName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OpLevel") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OpGrade") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OpFee") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("UnitNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DiagnoseDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AdjSum") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AdjReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AdjRemark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainOp") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MngCom") ) {
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
		if( strFieldName.equals("SelfAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("Currency") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FeeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyOperator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_INT;
				break;
			case 11:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
