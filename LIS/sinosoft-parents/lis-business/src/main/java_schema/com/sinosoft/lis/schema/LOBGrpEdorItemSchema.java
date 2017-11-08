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
import com.sinosoft.lis.db.LOBGrpEdorItemDB;

/*
 * <p>ClassName: LOBGrpEdorItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 多币种改造业务表
 */
public class LOBGrpEdorItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LOBGrpEdorItemSchema.class);

	// @Field
	/** 保全受理号 */
	private String EdorAcceptNo;
	/** 批单号 */
	private String EdorNo;
	/** 批改申请号 */
	private String EdorAppNo;
	/** 批改类型 */
	private String EdorType;
	/** 批改类型显示级别 */
	private String DisplayType;
	/** 集体合同号码 */
	private String GrpContNo;
	/** 管理机构 */
	private String ManageCom;
	/** 批改生效日期 */
	private Date EdorValiDate;
	/** 批改申请日期 */
	private Date EdorAppDate;
	/** 批改状态 */
	private String EdorState;
	/** 核保状态 */
	private String UWFlag;
	/** 核保人 */
	private String UWOperator;
	/** 核保完成日期 */
	private Date UWDate;
	/** 核保完成时间 */
	private String UWTime;
	/** 变动的保费 */
	private double ChgPrem;
	/** 变动的保额 */
	private double ChgAmnt;
	/** 补/退费金额 */
	private double GetMoney;
	/** 补/退费利息 */
	private double GetInterest;
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
	/** 撤销原因 */
	private String Reason;
	/** 原因编码 */
	private String ReasonCode;
	/** 保全申请原因 */
	private String AppReason;
	/** 保全变更原因编码 */
	private String EdorReasonCode;
	/** 保全变更原因 */
	private String EdorReason;
	/** 复核状态 */
	private String ApproveFlag;
	/** 复核人 */
	private String ApproveOperator;
	/** 复核日期 */
	private Date ApproveDate;
	/** 复核时间 */
	private String ApproveTime;
	/** 批改算法 */
	private String EdorTypeCal;
	/** 币别 */
	private String Currency;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LOBGrpEdorItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[4];
		pk[0] = "EdorAcceptNo";
		pk[1] = "EdorNo";
		pk[2] = "EdorType";
		pk[3] = "GrpContNo";

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
		LOBGrpEdorItemSchema cloned = (LOBGrpEdorItemSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getEdorAcceptNo()
	{
		return EdorAcceptNo;
	}
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		EdorAcceptNo = aEdorAcceptNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		EdorNo = aEdorNo;
	}
	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		EdorAppNo = aEdorAppNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		EdorType = aEdorType;
	}
	/**
	* 1－仅显示保单<p>
	* 2－显示保单，被保人<p>
	* 3－显示保单，被保人，险种（险种描述对应未增加，暂时允许空）
	*/
	public String getDisplayType()
	{
		return DisplayType;
	}
	public void setDisplayType(String aDisplayType)
	{
		DisplayType = aDisplayType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		GrpContNo = aGrpContNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	public String getEdorValiDate()
	{
		if( EdorValiDate != null )
			return fDate.getString(EdorValiDate);
		else
			return null;
	}
	public void setEdorValiDate(Date aEdorValiDate)
	{
		EdorValiDate = aEdorValiDate;
	}
	public void setEdorValiDate(String aEdorValiDate)
	{
		if (aEdorValiDate != null && !aEdorValiDate.equals("") )
		{
			EdorValiDate = fDate.getDate( aEdorValiDate );
		}
		else
			EdorValiDate = null;
	}

	public String getEdorAppDate()
	{
		if( EdorAppDate != null )
			return fDate.getString(EdorAppDate);
		else
			return null;
	}
	public void setEdorAppDate(Date aEdorAppDate)
	{
		EdorAppDate = aEdorAppDate;
	}
	public void setEdorAppDate(String aEdorAppDate)
	{
		if (aEdorAppDate != null && !aEdorAppDate.equals("") )
		{
			EdorAppDate = fDate.getDate( aEdorAppDate );
		}
		else
			EdorAppDate = null;
	}

	/**
	* 批改状态(0-批改确认,1-批改申请)
	*/
	public String getEdorState()
	{
		return EdorState;
	}
	public void setEdorState(String aEdorState)
	{
		EdorState = aEdorState;
	}
	/**
	* 1 拒保<p>
	* 2 延期<p>
	* 3 条件承保<p>
	* 4 通融<p>
	* 5 自动<p>
	* 6 待上级<p>
	* 7 问题件<p>
	* 8 核保通知书<p>
	* 9 正常<p>
	* a 撤单<p>
	* b 保险计划变更<p>
	* z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		UWOperator = aUWOperator;
	}
	public String getUWDate()
	{
		if( UWDate != null )
			return fDate.getString(UWDate);
		else
			return null;
	}
	public void setUWDate(Date aUWDate)
	{
		UWDate = aUWDate;
	}
	public void setUWDate(String aUWDate)
	{
		if (aUWDate != null && !aUWDate.equals("") )
		{
			UWDate = fDate.getDate( aUWDate );
		}
		else
			UWDate = null;
	}

	public String getUWTime()
	{
		return UWTime;
	}
	public void setUWTime(String aUWTime)
	{
		UWTime = aUWTime;
	}
	public double getChgPrem()
	{
		return ChgPrem;
	}
	public void setChgPrem(double aChgPrem)
	{
		ChgPrem = aChgPrem;
	}
	public void setChgPrem(String aChgPrem)
	{
		if (aChgPrem != null && !aChgPrem.equals(""))
		{
			Double tDouble = new Double(aChgPrem);
			double d = tDouble.doubleValue();
			ChgPrem = d;
		}
	}

	public double getChgAmnt()
	{
		return ChgAmnt;
	}
	public void setChgAmnt(double aChgAmnt)
	{
		ChgAmnt = aChgAmnt;
	}
	public void setChgAmnt(String aChgAmnt)
	{
		if (aChgAmnt != null && !aChgAmnt.equals(""))
		{
			Double tDouble = new Double(aChgAmnt);
			double d = tDouble.doubleValue();
			ChgAmnt = d;
		}
	}

	public double getGetMoney()
	{
		return GetMoney;
	}
	public void setGetMoney(double aGetMoney)
	{
		GetMoney = aGetMoney;
	}
	public void setGetMoney(String aGetMoney)
	{
		if (aGetMoney != null && !aGetMoney.equals(""))
		{
			Double tDouble = new Double(aGetMoney);
			double d = tDouble.doubleValue();
			GetMoney = d;
		}
	}

	public double getGetInterest()
	{
		return GetInterest;
	}
	public void setGetInterest(double aGetInterest)
	{
		GetInterest = aGetInterest;
	}
	public void setGetInterest(String aGetInterest)
	{
		if (aGetInterest != null && !aGetInterest.equals(""))
		{
			Double tDouble = new Double(aGetInterest);
			double d = tDouble.doubleValue();
			GetInterest = d;
		}
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
	public String getReason()
	{
		return Reason;
	}
	public void setReason(String aReason)
	{
		Reason = aReason;
	}
	public String getReasonCode()
	{
		return ReasonCode;
	}
	public void setReasonCode(String aReasonCode)
	{
		ReasonCode = aReasonCode;
	}
	/**
	* 1：客户提出申请<p>
	* 2：其他保全变更导致<p>
	* 3：保险公司内部申请<p>
	* 4：其他
	*/
	public String getAppReason()
	{
		return AppReason;
	}
	public void setAppReason(String aAppReason)
	{
		AppReason = aAppReason;
	}
	public String getEdorReasonCode()
	{
		return EdorReasonCode;
	}
	public void setEdorReasonCode(String aEdorReasonCode)
	{
		EdorReasonCode = aEdorReasonCode;
	}
	public String getEdorReason()
	{
		return EdorReason;
	}
	public void setEdorReason(String aEdorReason)
	{
		EdorReason = aEdorReason;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		ApproveFlag = aApproveFlag;
	}
	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		ApproveOperator = aApproveOperator;
	}
	public String getApproveDate()
	{
		if( ApproveDate != null )
			return fDate.getString(ApproveDate);
		else
			return null;
	}
	public void setApproveDate(Date aApproveDate)
	{
		ApproveDate = aApproveDate;
	}
	public void setApproveDate(String aApproveDate)
	{
		if (aApproveDate != null && !aApproveDate.equals("") )
		{
			ApproveDate = fDate.getDate( aApproveDate );
		}
		else
			ApproveDate = null;
	}

	public String getApproveTime()
	{
		return ApproveTime;
	}
	public void setApproveTime(String aApproveTime)
	{
		ApproveTime = aApproveTime;
	}
	public String getEdorTypeCal()
	{
		return EdorTypeCal;
	}
	public void setEdorTypeCal(String aEdorTypeCal)
	{
		EdorTypeCal = aEdorTypeCal;
	}
	public String getCurrency()
	{
		return Currency;
	}
	public void setCurrency(String aCurrency)
	{
		Currency = aCurrency;
	}

	/**
	* 使用另外一个 LOBGrpEdorItemSchema 对象给 Schema 赋值
	* @param: aLOBGrpEdorItemSchema LOBGrpEdorItemSchema
	**/
	public void setSchema(LOBGrpEdorItemSchema aLOBGrpEdorItemSchema)
	{
		this.EdorAcceptNo = aLOBGrpEdorItemSchema.getEdorAcceptNo();
		this.EdorNo = aLOBGrpEdorItemSchema.getEdorNo();
		this.EdorAppNo = aLOBGrpEdorItemSchema.getEdorAppNo();
		this.EdorType = aLOBGrpEdorItemSchema.getEdorType();
		this.DisplayType = aLOBGrpEdorItemSchema.getDisplayType();
		this.GrpContNo = aLOBGrpEdorItemSchema.getGrpContNo();
		this.ManageCom = aLOBGrpEdorItemSchema.getManageCom();
		this.EdorValiDate = fDate.getDate( aLOBGrpEdorItemSchema.getEdorValiDate());
		this.EdorAppDate = fDate.getDate( aLOBGrpEdorItemSchema.getEdorAppDate());
		this.EdorState = aLOBGrpEdorItemSchema.getEdorState();
		this.UWFlag = aLOBGrpEdorItemSchema.getUWFlag();
		this.UWOperator = aLOBGrpEdorItemSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLOBGrpEdorItemSchema.getUWDate());
		this.UWTime = aLOBGrpEdorItemSchema.getUWTime();
		this.ChgPrem = aLOBGrpEdorItemSchema.getChgPrem();
		this.ChgAmnt = aLOBGrpEdorItemSchema.getChgAmnt();
		this.GetMoney = aLOBGrpEdorItemSchema.getGetMoney();
		this.GetInterest = aLOBGrpEdorItemSchema.getGetInterest();
		this.Operator = aLOBGrpEdorItemSchema.getOperator();
		this.MakeDate = fDate.getDate( aLOBGrpEdorItemSchema.getMakeDate());
		this.MakeTime = aLOBGrpEdorItemSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLOBGrpEdorItemSchema.getModifyDate());
		this.ModifyTime = aLOBGrpEdorItemSchema.getModifyTime();
		this.Reason = aLOBGrpEdorItemSchema.getReason();
		this.ReasonCode = aLOBGrpEdorItemSchema.getReasonCode();
		this.AppReason = aLOBGrpEdorItemSchema.getAppReason();
		this.EdorReasonCode = aLOBGrpEdorItemSchema.getEdorReasonCode();
		this.EdorReason = aLOBGrpEdorItemSchema.getEdorReason();
		this.ApproveFlag = aLOBGrpEdorItemSchema.getApproveFlag();
		this.ApproveOperator = aLOBGrpEdorItemSchema.getApproveOperator();
		this.ApproveDate = fDate.getDate( aLOBGrpEdorItemSchema.getApproveDate());
		this.ApproveTime = aLOBGrpEdorItemSchema.getApproveTime();
		this.EdorTypeCal = aLOBGrpEdorItemSchema.getEdorTypeCal();
		this.Currency = aLOBGrpEdorItemSchema.getCurrency();
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
			if( rs.getString("EdorAcceptNo") == null )
				this.EdorAcceptNo = null;
			else
				this.EdorAcceptNo = rs.getString("EdorAcceptNo").trim();

			if( rs.getString("EdorNo") == null )
				this.EdorNo = null;
			else
				this.EdorNo = rs.getString("EdorNo").trim();

			if( rs.getString("EdorAppNo") == null )
				this.EdorAppNo = null;
			else
				this.EdorAppNo = rs.getString("EdorAppNo").trim();

			if( rs.getString("EdorType") == null )
				this.EdorType = null;
			else
				this.EdorType = rs.getString("EdorType").trim();

			if( rs.getString("DisplayType") == null )
				this.DisplayType = null;
			else
				this.DisplayType = rs.getString("DisplayType").trim();

			if( rs.getString("GrpContNo") == null )
				this.GrpContNo = null;
			else
				this.GrpContNo = rs.getString("GrpContNo").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			this.EdorValiDate = rs.getDate("EdorValiDate");
			this.EdorAppDate = rs.getDate("EdorAppDate");
			if( rs.getString("EdorState") == null )
				this.EdorState = null;
			else
				this.EdorState = rs.getString("EdorState").trim();

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWOperator") == null )
				this.UWOperator = null;
			else
				this.UWOperator = rs.getString("UWOperator").trim();

			this.UWDate = rs.getDate("UWDate");
			if( rs.getString("UWTime") == null )
				this.UWTime = null;
			else
				this.UWTime = rs.getString("UWTime").trim();

			this.ChgPrem = rs.getDouble("ChgPrem");
			this.ChgAmnt = rs.getDouble("ChgAmnt");
			this.GetMoney = rs.getDouble("GetMoney");
			this.GetInterest = rs.getDouble("GetInterest");
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

			if( rs.getString("Reason") == null )
				this.Reason = null;
			else
				this.Reason = rs.getString("Reason").trim();

			if( rs.getString("ReasonCode") == null )
				this.ReasonCode = null;
			else
				this.ReasonCode = rs.getString("ReasonCode").trim();

			if( rs.getString("AppReason") == null )
				this.AppReason = null;
			else
				this.AppReason = rs.getString("AppReason").trim();

			if( rs.getString("EdorReasonCode") == null )
				this.EdorReasonCode = null;
			else
				this.EdorReasonCode = rs.getString("EdorReasonCode").trim();

			if( rs.getString("EdorReason") == null )
				this.EdorReason = null;
			else
				this.EdorReason = rs.getString("EdorReason").trim();

			if( rs.getString("ApproveFlag") == null )
				this.ApproveFlag = null;
			else
				this.ApproveFlag = rs.getString("ApproveFlag").trim();

			if( rs.getString("ApproveOperator") == null )
				this.ApproveOperator = null;
			else
				this.ApproveOperator = rs.getString("ApproveOperator").trim();

			this.ApproveDate = rs.getDate("ApproveDate");
			if( rs.getString("ApproveTime") == null )
				this.ApproveTime = null;
			else
				this.ApproveTime = rs.getString("ApproveTime").trim();

			if( rs.getString("EdorTypeCal") == null )
				this.EdorTypeCal = null;
			else
				this.EdorTypeCal = rs.getString("EdorTypeCal").trim();

			if( rs.getString("Currency") == null )
				this.Currency = null;
			else
				this.Currency = rs.getString("Currency").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LOBGrpEdorItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LOBGrpEdorItemSchema getSchema()
	{
		LOBGrpEdorItemSchema aLOBGrpEdorItemSchema = new LOBGrpEdorItemSchema();
		aLOBGrpEdorItemSchema.setSchema(this);
		return aLOBGrpEdorItemSchema;
	}

	public LOBGrpEdorItemDB getDB()
	{
		LOBGrpEdorItemDB aDBOper = new LOBGrpEdorItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBGrpEdorItem描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(EdorAcceptNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorAppNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(DisplayType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorValiDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EdorAppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UWDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UWTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgPrem));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ChgAmnt));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetMoney));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(GetInterest));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Reason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorTypeCal)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Currency));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLOBGrpEdorItem>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			EdorAcceptNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			EdorNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			EdorAppNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			EdorType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			DisplayType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			EdorValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EdorAppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			EdorState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ChgPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).doubleValue();
			ChgAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).doubleValue();
			GetInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			Reason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			AppReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			EdorReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			EdorReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			EdorTypeCal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Currency = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LOBGrpEdorItemSchema";
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
		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAcceptNo));
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorNo));
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorAppNo));
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorType));
		}
		if (FCode.equalsIgnoreCase("DisplayType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(DisplayType));
		}
		if (FCode.equalsIgnoreCase("GrpContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpContNo));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorState));
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWOperator));
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWTime));
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgPrem));
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChgAmnt));
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetMoney));
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GetInterest));
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
		if (FCode.equalsIgnoreCase("Reason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Reason));
		}
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReasonCode));
		}
		if (FCode.equalsIgnoreCase("AppReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppReason));
		}
		if (FCode.equalsIgnoreCase("EdorReasonCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorReasonCode));
		}
		if (FCode.equalsIgnoreCase("EdorReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorReason));
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveFlag));
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveOperator));
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ApproveTime));
		}
		if (FCode.equalsIgnoreCase("EdorTypeCal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorTypeCal));
		}
		if (FCode.equalsIgnoreCase("Currency"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Currency));
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
				strFieldValue = StrTool.GBKToUnicode(EdorAcceptNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(EdorNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(EdorAppNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(EdorType);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(DisplayType);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(EdorState);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 14:
				strFieldValue = String.valueOf(ChgPrem);
				break;
			case 15:
				strFieldValue = String.valueOf(ChgAmnt);
				break;
			case 16:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 17:
				strFieldValue = String.valueOf(GetInterest);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(Reason);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(ReasonCode);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(AppReason);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(EdorReasonCode);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(EdorReason);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(EdorTypeCal);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Currency);
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

		if (FCode.equalsIgnoreCase("EdorAcceptNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAcceptNo = FValue.trim();
			}
			else
				EdorAcceptNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorNo = FValue.trim();
			}
			else
				EdorNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorAppNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorAppNo = FValue.trim();
			}
			else
				EdorAppNo = null;
		}
		if (FCode.equalsIgnoreCase("EdorType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorType = FValue.trim();
			}
			else
				EdorType = null;
		}
		if (FCode.equalsIgnoreCase("DisplayType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				DisplayType = FValue.trim();
			}
			else
				DisplayType = null;
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
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equalsIgnoreCase("EdorValiDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorValiDate = fDate.getDate( FValue );
			}
			else
				EdorValiDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorAppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EdorAppDate = fDate.getDate( FValue );
			}
			else
				EdorAppDate = null;
		}
		if (FCode.equalsIgnoreCase("EdorState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorState = FValue.trim();
			}
			else
				EdorState = null;
		}
		if (FCode.equalsIgnoreCase("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equalsIgnoreCase("UWOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWOperator = FValue.trim();
			}
			else
				UWOperator = null;
		}
		if (FCode.equalsIgnoreCase("UWDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UWDate = fDate.getDate( FValue );
			}
			else
				UWDate = null;
		}
		if (FCode.equalsIgnoreCase("UWTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWTime = FValue.trim();
			}
			else
				UWTime = null;
		}
		if (FCode.equalsIgnoreCase("ChgPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgPrem = d;
			}
		}
		if (FCode.equalsIgnoreCase("ChgAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				ChgAmnt = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetMoney"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetMoney = d;
			}
		}
		if (FCode.equalsIgnoreCase("GetInterest"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				GetInterest = d;
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
		if (FCode.equalsIgnoreCase("Reason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Reason = FValue.trim();
			}
			else
				Reason = null;
		}
		if (FCode.equalsIgnoreCase("ReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReasonCode = FValue.trim();
			}
			else
				ReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("AppReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppReason = FValue.trim();
			}
			else
				AppReason = null;
		}
		if (FCode.equalsIgnoreCase("EdorReasonCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorReasonCode = FValue.trim();
			}
			else
				EdorReasonCode = null;
		}
		if (FCode.equalsIgnoreCase("EdorReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorReason = FValue.trim();
			}
			else
				EdorReason = null;
		}
		if (FCode.equalsIgnoreCase("ApproveFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveFlag = FValue.trim();
			}
			else
				ApproveFlag = null;
		}
		if (FCode.equalsIgnoreCase("ApproveOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveOperator = FValue.trim();
			}
			else
				ApproveOperator = null;
		}
		if (FCode.equalsIgnoreCase("ApproveDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ApproveDate = fDate.getDate( FValue );
			}
			else
				ApproveDate = null;
		}
		if (FCode.equalsIgnoreCase("ApproveTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ApproveTime = FValue.trim();
			}
			else
				ApproveTime = null;
		}
		if (FCode.equalsIgnoreCase("EdorTypeCal"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorTypeCal = FValue.trim();
			}
			else
				EdorTypeCal = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LOBGrpEdorItemSchema other = (LOBGrpEdorItemSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& EdorType.equals(other.getEdorType())
			&& DisplayType.equals(other.getDisplayType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ManageCom.equals(other.getManageCom())
			&& fDate.getString(EdorValiDate).equals(other.getEdorValiDate())
			&& fDate.getString(EdorAppDate).equals(other.getEdorAppDate())
			&& EdorState.equals(other.getEdorState())
			&& UWFlag.equals(other.getUWFlag())
			&& UWOperator.equals(other.getUWOperator())
			&& fDate.getString(UWDate).equals(other.getUWDate())
			&& UWTime.equals(other.getUWTime())
			&& ChgPrem == other.getChgPrem()
			&& ChgAmnt == other.getChgAmnt()
			&& GetMoney == other.getGetMoney()
			&& GetInterest == other.getGetInterest()
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Reason.equals(other.getReason())
			&& ReasonCode.equals(other.getReasonCode())
			&& AppReason.equals(other.getAppReason())
			&& EdorReasonCode.equals(other.getEdorReasonCode())
			&& EdorReason.equals(other.getEdorReason())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveOperator.equals(other.getApproveOperator())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& EdorTypeCal.equals(other.getEdorTypeCal())
			&& Currency.equals(other.getCurrency());
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return 0;
		}
		if( strFieldName.equals("EdorNo") ) {
			return 1;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return 2;
		}
		if( strFieldName.equals("EdorType") ) {
			return 3;
		}
		if( strFieldName.equals("DisplayType") ) {
			return 4;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return 7;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return 8;
		}
		if( strFieldName.equals("EdorState") ) {
			return 9;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 10;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 11;
		}
		if( strFieldName.equals("UWDate") ) {
			return 12;
		}
		if( strFieldName.equals("UWTime") ) {
			return 13;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return 14;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return 15;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 16;
		}
		if( strFieldName.equals("GetInterest") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 19;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 20;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 21;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 22;
		}
		if( strFieldName.equals("Reason") ) {
			return 23;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return 24;
		}
		if( strFieldName.equals("AppReason") ) {
			return 25;
		}
		if( strFieldName.equals("EdorReasonCode") ) {
			return 26;
		}
		if( strFieldName.equals("EdorReason") ) {
			return 27;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 28;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return 29;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 30;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 31;
		}
		if( strFieldName.equals("EdorTypeCal") ) {
			return 32;
		}
		if( strFieldName.equals("Currency") ) {
			return 33;
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
				strFieldName = "EdorAcceptNo";
				break;
			case 1:
				strFieldName = "EdorNo";
				break;
			case 2:
				strFieldName = "EdorAppNo";
				break;
			case 3:
				strFieldName = "EdorType";
				break;
			case 4:
				strFieldName = "DisplayType";
				break;
			case 5:
				strFieldName = "GrpContNo";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "EdorValiDate";
				break;
			case 8:
				strFieldName = "EdorAppDate";
				break;
			case 9:
				strFieldName = "EdorState";
				break;
			case 10:
				strFieldName = "UWFlag";
				break;
			case 11:
				strFieldName = "UWOperator";
				break;
			case 12:
				strFieldName = "UWDate";
				break;
			case 13:
				strFieldName = "UWTime";
				break;
			case 14:
				strFieldName = "ChgPrem";
				break;
			case 15:
				strFieldName = "ChgAmnt";
				break;
			case 16:
				strFieldName = "GetMoney";
				break;
			case 17:
				strFieldName = "GetInterest";
				break;
			case 18:
				strFieldName = "Operator";
				break;
			case 19:
				strFieldName = "MakeDate";
				break;
			case 20:
				strFieldName = "MakeTime";
				break;
			case 21:
				strFieldName = "ModifyDate";
				break;
			case 22:
				strFieldName = "ModifyTime";
				break;
			case 23:
				strFieldName = "Reason";
				break;
			case 24:
				strFieldName = "ReasonCode";
				break;
			case 25:
				strFieldName = "AppReason";
				break;
			case 26:
				strFieldName = "EdorReasonCode";
				break;
			case 27:
				strFieldName = "EdorReason";
				break;
			case 28:
				strFieldName = "ApproveFlag";
				break;
			case 29:
				strFieldName = "ApproveOperator";
				break;
			case 30:
				strFieldName = "ApproveDate";
				break;
			case 31:
				strFieldName = "ApproveTime";
				break;
			case 32:
				strFieldName = "EdorTypeCal";
				break;
			case 33:
				strFieldName = "Currency";
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
		if( strFieldName.equals("EdorAcceptNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorAppNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("DisplayType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EdorState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UWTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetMoney") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("GetInterest") ) {
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
		if( strFieldName.equals("Reason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorReasonCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorTypeCal") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Currency") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DOUBLE;
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
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
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
