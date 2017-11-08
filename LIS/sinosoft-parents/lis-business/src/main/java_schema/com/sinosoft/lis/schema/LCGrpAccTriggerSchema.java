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
import com.sinosoft.lis.db.LCGrpAccTriggerDB;

/*
 * <p>ClassName: LCGrpAccTriggerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 寿险业务系统未整理模型
 */
public class LCGrpAccTriggerSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LCGrpAccTriggerSchema.class);

	// @Field
	/** 集体保单险种号码 */
	private String GrpPolNo;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 险种编码 */
	private String RiskCode;
	/** 账户范围 */
	private String AccType;
	/** 交费计划编码 */
	private String PayPlanCode;
	/** 保险帐户号码 */
	private String InsuAccNo;
	/** 险种帐户交费名 */
	private String RiskAccPayName;
	/** 账户变动类型 */
	private String ChgType;
	/** 账户变动业务类型 */
	private String ChgOperationType;
	/** 账户触发顺序 */
	private int TriggerOrder;
	/** 触发动作 */
	private String Action;
	/** 触发条件 */
	private String ActionCond;
	/** 处理对象 */
	private String ActionObject;
	/** 处理计算方法 */
	private String ActionCalMode;
	/** 计算类型 */
	private String ActionCalModeType;
	/** 计算公式 */
	private String ActionCalCode;
	/** 固定值 */
	private double Value;
	/** 比较值 */
	private double CompareValue;
	/** 归属对象 */
	private String ToObject;
	/** 归属对象定义 */
	private String ToObjectType;
	/** 目标保单险种号码 */
	private String ToPolNo;
	/** 目标责任编码 */
	private String ToDutycode;
	/** 目标交费计划编码 */
	private String ToPayPlanCode;
	/** 目标保险帐户号码 */
	private String ToInsuAccNo;
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

	public static final int FIELDNUM = 29;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCGrpAccTriggerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "GrpPolNo";
		pk[1] = "PayPlanCode";
		pk[2] = "InsuAccNo";
		pk[3] = "ChgType";
		pk[4] = "ChgOperationType";
		pk[5] = "TriggerOrder";

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
		LCGrpAccTriggerSchema cloned = (LCGrpAccTriggerSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getGrpPolNo()
	{
		return GrpPolNo;
	}
	public void setGrpPolNo(String aGrpPolNo)
	{
		GrpPolNo = aGrpPolNo;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getRiskCode()
	{
		return RiskCode;
	}
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/**
	* 00-账户<p>
	* 01-子账户
	*/
	public String getAccType()
	{
		return AccType;
	}
	public void setAccType(String aAccType)
	{
		AccType = aAccType;
	}
	/**
	* 按照账户进行处理交费计划编码为全零。
	*/
	public String getPayPlanCode()
	{
		return PayPlanCode;
	}
	public void setPayPlanCode(String aPayPlanCode)
	{
		PayPlanCode = aPayPlanCode;
	}
	public String getInsuAccNo()
	{
		return InsuAccNo;
	}
	public void setInsuAccNo(String aInsuAccNo)
	{
		InsuAccNo = aInsuAccNo;
	}
	/**
	* 对应于险种账户分类的名称
	*/
	public String getRiskAccPayName()
	{
		return RiskAccPayName;
	}
	public void setRiskAccPayName(String aRiskAccPayName)
	{
		RiskAccPayName = aRiskAccPayName;
	}
	/**
	* 0-账户增加<p>
	* 1-账户减少
	*/
	public String getChgType()
	{
		return ChgType;
	}
	public void setChgType(String aChgType)
	{
		ChgType = aChgType;
	}
	/**
	* 01-结息<p>
	* 02-分红<p>
	* 03-金额转入<p>
	* <p>
	* 11-金额转出<p>
	* 12-管理费
	*/
	public String getChgOperationType()
	{
		return ChgOperationType;
	}
	public void setChgOperationType(String aChgOperationType)
	{
		ChgOperationType = aChgOperationType;
	}
	public int getTriggerOrder()
	{
		return TriggerOrder;
	}
	public void setTriggerOrder(int aTriggerOrder)
	{
		TriggerOrder = aTriggerOrder;
	}
	public void setTriggerOrder(String aTriggerOrder)
	{
		if (aTriggerOrder != null && !aTriggerOrder.equals(""))
		{
			Integer tInteger = new Integer(aTriggerOrder);
			int i = tInteger.intValue();
			TriggerOrder = i;
		}
	}

	/**
	* 01-转移<p>
	* 02-其他变动（待扩展）
	*/
	public String getAction()
	{
		return Action;
	}
	public void setAction(String aAction)
	{
		Action = aAction;
	}
	/**
	* 待定义
	*/
	public String getActionCond()
	{
		return ActionCond;
	}
	public void setActionCond(String aActionCond)
	{
		ActionCond = aActionCond;
	}
	/**
	* 00-自身<p>
	* 01-账户余额<p>
	* 02-其他<p>
	* 。。。
	*/
	public String getActionObject()
	{
		return ActionObject;
	}
	public void setActionObject(String aActionObject)
	{
		ActionObject = aActionObject;
	}
	/**
	* 01-固定值（内扣）<p>
	* 02-固定比例 （内扣）<p>
	* 03-固定值（外缴）<p>
	* 04-固定比例 （外缴）<p>
	* 05-Min(固定值与比例结合)<p>
	* 06-Max(固定值和比例结合）
	*/
	public String getActionCalMode()
	{
		return ActionCalMode;
	}
	public void setActionCalMode(String aActionCalMode)
	{
		ActionCalMode = aActionCalMode;
	}
	/**
	* 0-直接取值<p>
	* 1-sql描述
	*/
	public String getActionCalModeType()
	{
		return ActionCalModeType;
	}
	public void setActionCalModeType(String aActionCalModeType)
	{
		ActionCalModeType = aActionCalModeType;
	}
	public String getActionCalCode()
	{
		return ActionCalCode;
	}
	public void setActionCalCode(String aActionCalCode)
	{
		ActionCalCode = aActionCalCode;
	}
	public double getValue()
	{
		return Value;
	}
	public void setValue(double aValue)
	{
		Value = aValue;
	}
	public void setValue(String aValue)
	{
		if (aValue != null && !aValue.equals(""))
		{
			Double tDouble = new Double(aValue);
			double d = tDouble.doubleValue();
			Value = d;
		}
	}

	public double getCompareValue()
	{
		return CompareValue;
	}
	public void setCompareValue(double aCompareValue)
	{
		CompareValue = aCompareValue;
	}
	public void setCompareValue(String aCompareValue)
	{
		if (aCompareValue != null && !aCompareValue.equals(""))
		{
			Double tDouble = new Double(aCompareValue);
			double d = tDouble.doubleValue();
			CompareValue = d;
		}
	}

	/**
	* 00-自身<p>
	* 01-规定账户<p>
	* 。。。
	*/
	public String getToObject()
	{
		return ToObject;
	}
	public void setToObject(String aToObject)
	{
		ToObject = aToObject;
	}
	/**
	* 00-抵缴保费：个人帐户产生的分红回到原缴费帐户，与本金混合。<p>
	* 01-归属个人帐户：个人缴费帐户产生的分红进入个人帐户下的个人红利帐户。<p>
	* 02-归属企业帐户：个人缴费帐户产生的分红进入企业帐户下的企业红利帐户
	*/
	public String getToObjectType()
	{
		return ToObjectType;
	}
	public void setToObjectType(String aToObjectType)
	{
		ToObjectType = aToObjectType;
	}
	public String getToPolNo()
	{
		return ToPolNo;
	}
	public void setToPolNo(String aToPolNo)
	{
		ToPolNo = aToPolNo;
	}
	public String getToDutycode()
	{
		return ToDutycode;
	}
	public void setToDutycode(String aToDutycode)
	{
		ToDutycode = aToDutycode;
	}
	public String getToPayPlanCode()
	{
		return ToPayPlanCode;
	}
	public void setToPayPlanCode(String aToPayPlanCode)
	{
		ToPayPlanCode = aToPayPlanCode;
	}
	public String getToInsuAccNo()
	{
		return ToInsuAccNo;
	}
	public void setToInsuAccNo(String aToInsuAccNo)
	{
		ToInsuAccNo = aToInsuAccNo;
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
	* 使用另外一个 LCGrpAccTriggerSchema 对象给 Schema 赋值
	* @param: aLCGrpAccTriggerSchema LCGrpAccTriggerSchema
	**/
	public void setSchema(LCGrpAccTriggerSchema aLCGrpAccTriggerSchema)
	{
		this.GrpPolNo = aLCGrpAccTriggerSchema.getGrpPolNo();
		this.GrpContNo = aLCGrpAccTriggerSchema.getGrpContNo();
		this.RiskCode = aLCGrpAccTriggerSchema.getRiskCode();
		this.AccType = aLCGrpAccTriggerSchema.getAccType();
		this.PayPlanCode = aLCGrpAccTriggerSchema.getPayPlanCode();
		this.InsuAccNo = aLCGrpAccTriggerSchema.getInsuAccNo();
		this.RiskAccPayName = aLCGrpAccTriggerSchema.getRiskAccPayName();
		this.ChgType = aLCGrpAccTriggerSchema.getChgType();
		this.ChgOperationType = aLCGrpAccTriggerSchema.getChgOperationType();
		this.TriggerOrder = aLCGrpAccTriggerSchema.getTriggerOrder();
		this.Action = aLCGrpAccTriggerSchema.getAction();
		this.ActionCond = aLCGrpAccTriggerSchema.getActionCond();
		this.ActionObject = aLCGrpAccTriggerSchema.getActionObject();
		this.ActionCalMode = aLCGrpAccTriggerSchema.getActionCalMode();
		this.ActionCalModeType = aLCGrpAccTriggerSchema.getActionCalModeType();
		this.ActionCalCode = aLCGrpAccTriggerSchema.getActionCalCode();
		this.Value = aLCGrpAccTriggerSchema.getValue();
		this.CompareValue = aLCGrpAccTriggerSchema.getCompareValue();
		this.ToObject = aLCGrpAccTriggerSchema.getToObject();
		this.ToObjectType = aLCGrpAccTriggerSchema.getToObjectType();
		this.ToPolNo = aLCGrpAccTriggerSchema.getToPolNo();
		this.ToDutycode = aLCGrpAccTriggerSchema.getToDutycode();
		this.ToPayPlanCode = aLCGrpAccTriggerSchema.getToPayPlanCode();
		this.ToInsuAccNo = aLCGrpAccTriggerSchema.getToInsuAccNo();
		this.Operator = aLCGrpAccTriggerSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCGrpAccTriggerSchema.getMakeDate());
		this.MakeTime = aLCGrpAccTriggerSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCGrpAccTriggerSchema.getModifyDate());
		this.ModifyTime = aLCGrpAccTriggerSchema.getModifyTime();
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
			if( rs.getString("GrpPolNo") == null )
				this.GrpPolNo = null;
			else
				this.GrpPolNo = rs.getString("GrpPolNo").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("AccType") == null )
				this.AccType = null;
			else
				this.AccType = rs.getString("AccType").trim();

			if( rs.getString("PayPlanCode") == null )
				this.PayPlanCode = null;
			else
				this.PayPlanCode = rs.getString("PayPlanCode").trim();

			if( rs.getString("InsuAccNo") == null )
				this.InsuAccNo = null;
			else
				this.InsuAccNo = rs.getString("InsuAccNo").trim();

			if( rs.getString("RiskAccPayName") == null )
				this.RiskAccPayName = null;
			else
				this.RiskAccPayName = rs.getString("RiskAccPayName").trim();

			if( rs.getString("ChgType") == null )
				this.ChgType = null;
			else
				this.ChgType = rs.getString("ChgType").trim();

			if( rs.getString("ChgOperationType") == null )
				this.ChgOperationType = null;
			else
				this.ChgOperationType = rs.getString("ChgOperationType").trim();

			this.TriggerOrder = rs.getInt("TriggerOrder");
			if( rs.getString("Action") == null )
				this.Action = null;
			else
				this.Action = rs.getString("Action").trim();

			if( rs.getString("ActionCond") == null )
				this.ActionCond = null;
			else
				this.ActionCond = rs.getString("ActionCond").trim();

			if( rs.getString("ActionObject") == null )
				this.ActionObject = null;
			else
				this.ActionObject = rs.getString("ActionObject").trim();

			if( rs.getString("ActionCalMode") == null )
				this.ActionCalMode = null;
			else
				this.ActionCalMode = rs.getString("ActionCalMode").trim();

			if( rs.getString("ActionCalModeType") == null )
				this.ActionCalModeType = null;
			else
				this.ActionCalModeType = rs.getString("ActionCalModeType").trim();

			if( rs.getString("ActionCalCode") == null )
				this.ActionCalCode = null;
			else
				this.ActionCalCode = rs.getString("ActionCalCode").trim();

			this.Value = rs.getDouble("Value");
			this.CompareValue = rs.getDouble("CompareValue");
			if( rs.getString("ToObject") == null )
				this.ToObject = null;
			else
				this.ToObject = rs.getString("ToObject").trim();

			if( rs.getString("ToObjectType") == null )
				this.ToObjectType = null;
			else
				this.ToObjectType = rs.getString("ToObjectType").trim();

			if( rs.getString("ToPolNo") == null )
				this.ToPolNo = null;
			else
				this.ToPolNo = rs.getString("ToPolNo").trim();

			if( rs.getString("ToDutycode") == null )
				this.ToDutycode = null;
			else
				this.ToDutycode = rs.getString("ToDutycode").trim();

			if( rs.getString("ToPayPlanCode") == null )
				this.ToPayPlanCode = null;
			else
				this.ToPayPlanCode = rs.getString("ToPayPlanCode").trim();

			if( rs.getString("ToInsuAccNo") == null )
				this.ToInsuAccNo = null;
			else
				this.ToInsuAccNo = rs.getString("ToInsuAccNo").trim();

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
			logger.debug("数据库中的LCGrpAccTrigger表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAccTriggerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LCGrpAccTriggerSchema getSchema()
	{
		LCGrpAccTriggerSchema aLCGrpAccTriggerSchema = new LCGrpAccTriggerSchema();
		aLCGrpAccTriggerSchema.setSchema(this);
		return aLCGrpAccTriggerSchema;
	}

	public LCGrpAccTriggerDB getDB()
	{
		LCGrpAccTriggerDB aDBOper = new LCGrpAccTriggerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpAccTrigger描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(GrpPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AccType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RiskAccPayName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ChgOperationType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(TriggerOrder));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Action)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCond)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionObject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalModeType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ActionCalCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Value));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(CompareValue));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToObject)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToObjectType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToPolNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToDutycode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToPayPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ToInsuAccNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCGrpAccTrigger>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			GrpPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AccType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			InsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RiskAccPayName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ChgType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ChgOperationType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			TriggerOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			Action = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ActionCond = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ActionObject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ActionCalMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ActionCalModeType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ActionCalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Value = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			CompareValue = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			ToObject = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ToObjectType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ToPolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ToDutycode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			ToPayPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			ToInsuAccNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCGrpAccTriggerSchema";
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
		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpPolNo));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccType));
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PayPlanCode));
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuAccNo));
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskAccPayName));
		}
		if (FCode.equalsIgnoreCase("ChgType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgType));
		}
		if (FCode.equalsIgnoreCase("ChgOperationType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgOperationType));
		}
		if (FCode.equalsIgnoreCase("TriggerOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TriggerOrder));
		}
		if (FCode.equalsIgnoreCase("Action"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Action));
		}
		if (FCode.equalsIgnoreCase("ActionCond"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCond));
		}
		if (FCode.equalsIgnoreCase("ActionObject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionObject));
		}
		if (FCode.equalsIgnoreCase("ActionCalMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalMode));
		}
		if (FCode.equalsIgnoreCase("ActionCalModeType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalModeType));
		}
		if (FCode.equalsIgnoreCase("ActionCalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ActionCalCode));
		}
		if (FCode.equalsIgnoreCase("Value"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Value));
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompareValue));
		}
		if (FCode.equalsIgnoreCase("ToObject"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToObject));
		}
		if (FCode.equalsIgnoreCase("ToObjectType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToObjectType));
		}
		if (FCode.equalsIgnoreCase("ToPolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToPolNo));
		}
		if (FCode.equalsIgnoreCase("ToDutycode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToDutycode));
		}
		if (FCode.equalsIgnoreCase("ToPayPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToPayPlanCode));
		}
		if (FCode.equalsIgnoreCase("ToInsuAccNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ToInsuAccNo));
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
				strFieldValue = StrTool.GBKToUnicode(GrpPolNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AccType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PayPlanCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(InsuAccNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RiskAccPayName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ChgType);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ChgOperationType);
				break;
			case 9:
				strFieldValue = String.valueOf(TriggerOrder);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Action);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ActionCond);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ActionObject);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ActionCalMode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ActionCalModeType);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ActionCalCode);
				break;
			case 16:
				strFieldValue = String.valueOf(Value);
				break;
			case 17:
				strFieldValue = String.valueOf(CompareValue);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ToObject);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ToObjectType);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ToPolNo);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ToDutycode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ToPayPlanCode);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(ToInsuAccNo);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 28:
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

		if (FCode.equalsIgnoreCase("GrpPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpPolNo = FValue.trim();
			}
			else
				GrpPolNo = null;
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
		if (FCode.equalsIgnoreCase("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equalsIgnoreCase("AccType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccType = FValue.trim();
			}
			else
				AccType = null;
		}
		if (FCode.equalsIgnoreCase("PayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PayPlanCode = FValue.trim();
			}
			else
				PayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("InsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuAccNo = FValue.trim();
			}
			else
				InsuAccNo = null;
		}
		if (FCode.equalsIgnoreCase("RiskAccPayName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskAccPayName = FValue.trim();
			}
			else
				RiskAccPayName = null;
		}
		if (FCode.equalsIgnoreCase("ChgType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgType = FValue.trim();
			}
			else
				ChgType = null;
		}
		if (FCode.equalsIgnoreCase("ChgOperationType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChgOperationType = FValue.trim();
			}
			else
				ChgOperationType = null;
		}
		if (FCode.equalsIgnoreCase("TriggerOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TriggerOrder = i;
			}
		}
		if (FCode.equalsIgnoreCase("Action"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Action = FValue.trim();
			}
			else
				Action = null;
		}
		if (FCode.equalsIgnoreCase("ActionCond"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCond = FValue.trim();
			}
			else
				ActionCond = null;
		}
		if (FCode.equalsIgnoreCase("ActionObject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionObject = FValue.trim();
			}
			else
				ActionObject = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalMode = FValue.trim();
			}
			else
				ActionCalMode = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalModeType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalModeType = FValue.trim();
			}
			else
				ActionCalModeType = null;
		}
		if (FCode.equalsIgnoreCase("ActionCalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ActionCalCode = FValue.trim();
			}
			else
				ActionCalCode = null;
		}
		if (FCode.equalsIgnoreCase("Value"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				Value = d;
			}
		}
		if (FCode.equalsIgnoreCase("CompareValue"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				CompareValue = d;
			}
		}
		if (FCode.equalsIgnoreCase("ToObject"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToObject = FValue.trim();
			}
			else
				ToObject = null;
		}
		if (FCode.equalsIgnoreCase("ToObjectType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToObjectType = FValue.trim();
			}
			else
				ToObjectType = null;
		}
		if (FCode.equalsIgnoreCase("ToPolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToPolNo = FValue.trim();
			}
			else
				ToPolNo = null;
		}
		if (FCode.equalsIgnoreCase("ToDutycode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToDutycode = FValue.trim();
			}
			else
				ToDutycode = null;
		}
		if (FCode.equalsIgnoreCase("ToPayPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToPayPlanCode = FValue.trim();
			}
			else
				ToPayPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("ToInsuAccNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ToInsuAccNo = FValue.trim();
			}
			else
				ToInsuAccNo = null;
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
		LCGrpAccTriggerSchema other = (LCGrpAccTriggerSchema)otherObject;
		return
			GrpPolNo.equals(other.getGrpPolNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& RiskCode.equals(other.getRiskCode())
			&& AccType.equals(other.getAccType())
			&& PayPlanCode.equals(other.getPayPlanCode())
			&& InsuAccNo.equals(other.getInsuAccNo())
			&& RiskAccPayName.equals(other.getRiskAccPayName())
			&& ChgType.equals(other.getChgType())
			&& ChgOperationType.equals(other.getChgOperationType())
			&& TriggerOrder == other.getTriggerOrder()
			&& Action.equals(other.getAction())
			&& ActionCond.equals(other.getActionCond())
			&& ActionObject.equals(other.getActionObject())
			&& ActionCalMode.equals(other.getActionCalMode())
			&& ActionCalModeType.equals(other.getActionCalModeType())
			&& ActionCalCode.equals(other.getActionCalCode())
			&& Value == other.getValue()
			&& CompareValue == other.getCompareValue()
			&& ToObject.equals(other.getToObject())
			&& ToObjectType.equals(other.getToObjectType())
			&& ToPolNo.equals(other.getToPolNo())
			&& ToDutycode.equals(other.getToDutycode())
			&& ToPayPlanCode.equals(other.getToPayPlanCode())
			&& ToInsuAccNo.equals(other.getToInsuAccNo())
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
		if( strFieldName.equals("GrpPolNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 2;
		}
		if( strFieldName.equals("AccType") ) {
			return 3;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return 4;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return 5;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return 6;
		}
		if( strFieldName.equals("ChgType") ) {
			return 7;
		}
		if( strFieldName.equals("ChgOperationType") ) {
			return 8;
		}
		if( strFieldName.equals("TriggerOrder") ) {
			return 9;
		}
		if( strFieldName.equals("Action") ) {
			return 10;
		}
		if( strFieldName.equals("ActionCond") ) {
			return 11;
		}
		if( strFieldName.equals("ActionObject") ) {
			return 12;
		}
		if( strFieldName.equals("ActionCalMode") ) {
			return 13;
		}
		if( strFieldName.equals("ActionCalModeType") ) {
			return 14;
		}
		if( strFieldName.equals("ActionCalCode") ) {
			return 15;
		}
		if( strFieldName.equals("Value") ) {
			return 16;
		}
		if( strFieldName.equals("CompareValue") ) {
			return 17;
		}
		if( strFieldName.equals("ToObject") ) {
			return 18;
		}
		if( strFieldName.equals("ToObjectType") ) {
			return 19;
		}
		if( strFieldName.equals("ToPolNo") ) {
			return 20;
		}
		if( strFieldName.equals("ToDutycode") ) {
			return 21;
		}
		if( strFieldName.equals("ToPayPlanCode") ) {
			return 22;
		}
		if( strFieldName.equals("ToInsuAccNo") ) {
			return 23;
		}
		if( strFieldName.equals("Operator") ) {
			return 24;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 25;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 27;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 28;
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
				strFieldName = "GrpPolNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "RiskCode";
				break;
			case 3:
				strFieldName = "AccType";
				break;
			case 4:
				strFieldName = "PayPlanCode";
				break;
			case 5:
				strFieldName = "InsuAccNo";
				break;
			case 6:
				strFieldName = "RiskAccPayName";
				break;
			case 7:
				strFieldName = "ChgType";
				break;
			case 8:
				strFieldName = "ChgOperationType";
				break;
			case 9:
				strFieldName = "TriggerOrder";
				break;
			case 10:
				strFieldName = "Action";
				break;
			case 11:
				strFieldName = "ActionCond";
				break;
			case 12:
				strFieldName = "ActionObject";
				break;
			case 13:
				strFieldName = "ActionCalMode";
				break;
			case 14:
				strFieldName = "ActionCalModeType";
				break;
			case 15:
				strFieldName = "ActionCalCode";
				break;
			case 16:
				strFieldName = "Value";
				break;
			case 17:
				strFieldName = "CompareValue";
				break;
			case 18:
				strFieldName = "ToObject";
				break;
			case 19:
				strFieldName = "ToObjectType";
				break;
			case 20:
				strFieldName = "ToPolNo";
				break;
			case 21:
				strFieldName = "ToDutycode";
				break;
			case 22:
				strFieldName = "ToPayPlanCode";
				break;
			case 23:
				strFieldName = "ToInsuAccNo";
				break;
			case 24:
				strFieldName = "Operator";
				break;
			case 25:
				strFieldName = "MakeDate";
				break;
			case 26:
				strFieldName = "MakeTime";
				break;
			case 27:
				strFieldName = "ModifyDate";
				break;
			case 28:
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
		if( strFieldName.equals("GrpPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuAccNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskAccPayName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgOperationType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TriggerOrder") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Action") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCond") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionObject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalModeType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ActionCalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Value") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("CompareValue") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ToObject") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToObjectType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToPolNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToDutycode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToPayPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ToInsuAccNo") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
