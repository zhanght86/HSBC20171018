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
import com.sinosoft.lis.db.LBPEdorItemDB;

/*
 * <p>ClassName: LBPEdorItemSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 泰康未整理PDM
 */
public class LBPEdorItemSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LBPEdorItemSchema.class);
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
	/** 合同号码 */
	private String ContNo;
	/** 被保人客户号码 */
	private String InsuredNo;
	/** 保单险种号码 */
	private String PolNo;
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
	/** Standbyflag1 */
	private String Standbyflag1;
	/** Standbyflag2 */
	private String standbyflag2;
	/** Standbyflag3 */
	private String standbyflag3;
	/** 批改算法 */
	private String EdorTypeCal;

	public static final int FIELDNUM = 37;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LBPEdorItemSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[6];
		pk[0] = "EdorAcceptNo";
		pk[1] = "EdorNo";
		pk[2] = "EdorType";
		pk[3] = "ContNo";
		pk[4] = "InsuredNo";
		pk[5] = "PolNo";

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
		LBPEdorItemSchema cloned = (LBPEdorItemSchema)super.clone();
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
		if(aEdorAcceptNo!=null && aEdorAcceptNo.length()>20)
			throw new IllegalArgumentException("保全受理号EdorAcceptNo值"+aEdorAcceptNo+"的长度"+aEdorAcceptNo.length()+"大于最大值20");
		EdorAcceptNo = aEdorAcceptNo;
	}
	public String getEdorNo()
	{
		return EdorNo;
	}
	public void setEdorNo(String aEdorNo)
	{
		if(aEdorNo!=null && aEdorNo.length()>20)
			throw new IllegalArgumentException("批单号EdorNo值"+aEdorNo+"的长度"+aEdorNo.length()+"大于最大值20");
		EdorNo = aEdorNo;
	}
	public String getEdorAppNo()
	{
		return EdorAppNo;
	}
	public void setEdorAppNo(String aEdorAppNo)
	{
		if(aEdorAppNo!=null && aEdorAppNo.length()>20)
			throw new IllegalArgumentException("批改申请号EdorAppNo值"+aEdorAppNo+"的长度"+aEdorAppNo.length()+"大于最大值20");
		EdorAppNo = aEdorAppNo;
	}
	public String getEdorType()
	{
		return EdorType;
	}
	public void setEdorType(String aEdorType)
	{
		if(aEdorType!=null && aEdorType.length()>2)
			throw new IllegalArgumentException("批改类型EdorType值"+aEdorType+"的长度"+aEdorType.length()+"大于最大值2");
		EdorType = aEdorType;
	}
	/**
	* 1－仅显示保单 2－显示保单，被保人 3－显示保单，被保人，险种
	*/
	public String getDisplayType()
	{
		return DisplayType;
	}
	public void setDisplayType(String aDisplayType)
	{
		if(aDisplayType!=null && aDisplayType.length()>2)
			throw new IllegalArgumentException("批改类型显示级别DisplayType值"+aDisplayType+"的长度"+aDisplayType.length()+"大于最大值2");
		DisplayType = aDisplayType;
	}
	public String getGrpContNo()
	{
		return GrpContNo;
	}
	public void setGrpContNo(String aGrpContNo)
	{
		if(aGrpContNo!=null && aGrpContNo.length()>20)
			throw new IllegalArgumentException("集体合同号码GrpContNo值"+aGrpContNo+"的长度"+aGrpContNo.length()+"大于最大值20");
		GrpContNo = aGrpContNo;
	}
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("合同号码ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>24)
			throw new IllegalArgumentException("被保人客户号码InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值24");
		InsuredNo = aInsuredNo;
	}
	public String getPolNo()
	{
		return PolNo;
	}
	public void setPolNo(String aPolNo)
	{
		if(aPolNo!=null && aPolNo.length()>20)
			throw new IllegalArgumentException("保单险种号码PolNo值"+aPolNo+"的长度"+aPolNo.length()+"大于最大值20");
		PolNo = aPolNo;
	}
	public String getManageCom()
	{
		return ManageCom;
	}
	public void setManageCom(String aManageCom)
	{
		if(aManageCom!=null && aManageCom.length()>10)
			throw new IllegalArgumentException("管理机构ManageCom值"+aManageCom+"的长度"+aManageCom.length()+"大于最大值10");
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
	* 0 确认生效 1 录入完成 2 申请确认 3 等待录入 4 逾期终止 5 复核修改 6 确认未生效 7 保全撤销 8 核保终止
	*/
	public String getEdorState()
	{
		return EdorState;
	}
	public void setEdorState(String aEdorState)
	{
		if(aEdorState!=null && aEdorState.length()>1)
			throw new IllegalArgumentException("批改状态EdorState值"+aEdorState+"的长度"+aEdorState.length()+"大于最大值1");
		EdorState = aEdorState;
	}
	/**
	* 1 拒保 2 延期 3 条件承保 4 通融 5 自动 6 待上级 7 问题件 8 核保通知书 9 正常 a 撤单 b 保险计划变更 z 核保订正
	*/
	public String getUWFlag()
	{
		return UWFlag;
	}
	public void setUWFlag(String aUWFlag)
	{
		if(aUWFlag!=null && aUWFlag.length()>1)
			throw new IllegalArgumentException("核保状态UWFlag值"+aUWFlag+"的长度"+aUWFlag.length()+"大于最大值1");
		UWFlag = aUWFlag;
	}
	public String getUWOperator()
	{
		return UWOperator;
	}
	public void setUWOperator(String aUWOperator)
	{
		if(aUWOperator!=null && aUWOperator.length()>10)
			throw new IllegalArgumentException("核保人UWOperator值"+aUWOperator+"的长度"+aUWOperator.length()+"大于最大值10");
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
		if(aUWTime!=null && aUWTime.length()>8)
			throw new IllegalArgumentException("核保完成时间UWTime值"+aUWTime+"的长度"+aUWTime.length()+"大于最大值8");
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
		if(aOperator!=null && aOperator.length()>10)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值10");
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
	* 1：客户提出申请 2：保险公司内部申请 3：其他保全变更导致 4：其他
	*/
	public String getAppReason()
	{
		return AppReason;
	}
	public void setAppReason(String aAppReason)
	{
		if(aAppReason!=null && aAppReason.length()>2)
			throw new IllegalArgumentException("保全申请原因AppReason值"+aAppReason+"的长度"+aAppReason.length()+"大于最大值2");
		AppReason = aAppReason;
	}
	public String getEdorReasonCode()
	{
		return EdorReasonCode;
	}
	public void setEdorReasonCode(String aEdorReasonCode)
	{
		if(aEdorReasonCode!=null && aEdorReasonCode.length()>6)
			throw new IllegalArgumentException("保全变更原因编码EdorReasonCode值"+aEdorReasonCode+"的长度"+aEdorReasonCode.length()+"大于最大值6");
		EdorReasonCode = aEdorReasonCode;
	}
	public String getEdorReason()
	{
		return EdorReason;
	}
	public void setEdorReason(String aEdorReason)
	{
		if(aEdorReason!=null && aEdorReason.length()>200)
			throw new IllegalArgumentException("保全变更原因EdorReason值"+aEdorReason+"的长度"+aEdorReason.length()+"大于最大值200");
		EdorReason = aEdorReason;
	}
	public String getApproveFlag()
	{
		return ApproveFlag;
	}
	public void setApproveFlag(String aApproveFlag)
	{
		if(aApproveFlag!=null && aApproveFlag.length()>1)
			throw new IllegalArgumentException("复核状态ApproveFlag值"+aApproveFlag+"的长度"+aApproveFlag.length()+"大于最大值1");
		ApproveFlag = aApproveFlag;
	}
	public String getApproveOperator()
	{
		return ApproveOperator;
	}
	public void setApproveOperator(String aApproveOperator)
	{
		if(aApproveOperator!=null && aApproveOperator.length()>10)
			throw new IllegalArgumentException("复核人ApproveOperator值"+aApproveOperator+"的长度"+aApproveOperator.length()+"大于最大值10");
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
		if(aApproveTime!=null && aApproveTime.length()>8)
			throw new IllegalArgumentException("复核时间ApproveTime值"+aApproveTime+"的长度"+aApproveTime.length()+"大于最大值8");
		ApproveTime = aApproveTime;
	}
	public String getStandbyflag1()
	{
		return Standbyflag1;
	}
	public void setStandbyflag1(String aStandbyflag1)
	{
		if(aStandbyflag1!=null && aStandbyflag1.length()>10)
			throw new IllegalArgumentException("Standbyflag1Standbyflag1值"+aStandbyflag1+"的长度"+aStandbyflag1.length()+"大于最大值10");
		Standbyflag1 = aStandbyflag1;
	}
	public String getstandbyflag2()
	{
		return standbyflag2;
	}
	public void setstandbyflag2(String astandbyflag2)
	{
		if(astandbyflag2!=null && astandbyflag2.length()>10)
			throw new IllegalArgumentException("Standbyflag2standbyflag2值"+astandbyflag2+"的长度"+astandbyflag2.length()+"大于最大值10");
		standbyflag2 = astandbyflag2;
	}
	public String getstandbyflag3()
	{
		return standbyflag3;
	}
	public void setstandbyflag3(String astandbyflag3)
	{
		if(astandbyflag3!=null && astandbyflag3.length()>10)
			throw new IllegalArgumentException("Standbyflag3standbyflag3值"+astandbyflag3+"的长度"+astandbyflag3.length()+"大于最大值10");
		standbyflag3 = astandbyflag3;
	}
	public String getEdorTypeCal()
	{
		return EdorTypeCal;
	}
	public void setEdorTypeCal(String aEdorTypeCal)
	{
		if(aEdorTypeCal!=null && aEdorTypeCal.length()>3)
			throw new IllegalArgumentException("批改算法EdorTypeCal值"+aEdorTypeCal+"的长度"+aEdorTypeCal.length()+"大于最大值3");
		EdorTypeCal = aEdorTypeCal;
	}

	/**
	* 使用另外一个 LBPEdorItemSchema 对象给 Schema 赋值
	* @param: aLBPEdorItemSchema LBPEdorItemSchema
	**/
	public void setSchema(LBPEdorItemSchema aLBPEdorItemSchema)
	{
		this.EdorAcceptNo = aLBPEdorItemSchema.getEdorAcceptNo();
		this.EdorNo = aLBPEdorItemSchema.getEdorNo();
		this.EdorAppNo = aLBPEdorItemSchema.getEdorAppNo();
		this.EdorType = aLBPEdorItemSchema.getEdorType();
		this.DisplayType = aLBPEdorItemSchema.getDisplayType();
		this.GrpContNo = aLBPEdorItemSchema.getGrpContNo();
		this.ContNo = aLBPEdorItemSchema.getContNo();
		this.InsuredNo = aLBPEdorItemSchema.getInsuredNo();
		this.PolNo = aLBPEdorItemSchema.getPolNo();
		this.ManageCom = aLBPEdorItemSchema.getManageCom();
		this.EdorValiDate = fDate.getDate( aLBPEdorItemSchema.getEdorValiDate());
		this.EdorAppDate = fDate.getDate( aLBPEdorItemSchema.getEdorAppDate());
		this.EdorState = aLBPEdorItemSchema.getEdorState();
		this.UWFlag = aLBPEdorItemSchema.getUWFlag();
		this.UWOperator = aLBPEdorItemSchema.getUWOperator();
		this.UWDate = fDate.getDate( aLBPEdorItemSchema.getUWDate());
		this.UWTime = aLBPEdorItemSchema.getUWTime();
		this.ChgPrem = aLBPEdorItemSchema.getChgPrem();
		this.ChgAmnt = aLBPEdorItemSchema.getChgAmnt();
		this.GetMoney = aLBPEdorItemSchema.getGetMoney();
		this.GetInterest = aLBPEdorItemSchema.getGetInterest();
		this.Operator = aLBPEdorItemSchema.getOperator();
		this.MakeDate = fDate.getDate( aLBPEdorItemSchema.getMakeDate());
		this.MakeTime = aLBPEdorItemSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLBPEdorItemSchema.getModifyDate());
		this.ModifyTime = aLBPEdorItemSchema.getModifyTime();
		this.AppReason = aLBPEdorItemSchema.getAppReason();
		this.EdorReasonCode = aLBPEdorItemSchema.getEdorReasonCode();
		this.EdorReason = aLBPEdorItemSchema.getEdorReason();
		this.ApproveFlag = aLBPEdorItemSchema.getApproveFlag();
		this.ApproveOperator = aLBPEdorItemSchema.getApproveOperator();
		this.ApproveDate = fDate.getDate( aLBPEdorItemSchema.getApproveDate());
		this.ApproveTime = aLBPEdorItemSchema.getApproveTime();
		this.Standbyflag1 = aLBPEdorItemSchema.getStandbyflag1();
		this.standbyflag2 = aLBPEdorItemSchema.getstandbyflag2();
		this.standbyflag3 = aLBPEdorItemSchema.getstandbyflag3();
		this.EdorTypeCal = aLBPEdorItemSchema.getEdorTypeCal();
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

			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("InsuredNo") == null )
				this.InsuredNo = null;
			else
				this.InsuredNo = rs.getString("InsuredNo").trim();

			if( rs.getString("PolNo") == null )
				this.PolNo = null;
			else
				this.PolNo = rs.getString("PolNo").trim();

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

			if( rs.getString("Standbyflag1") == null )
				this.Standbyflag1 = null;
			else
				this.Standbyflag1 = rs.getString("Standbyflag1").trim();

			if( rs.getString("standbyflag2") == null )
				this.standbyflag2 = null;
			else
				this.standbyflag2 = rs.getString("standbyflag2").trim();

			if( rs.getString("standbyflag3") == null )
				this.standbyflag3 = null;
			else
				this.standbyflag3 = rs.getString("standbyflag3").trim();

			if( rs.getString("EdorTypeCal") == null )
				this.EdorTypeCal = null;
			else
				this.EdorTypeCal = rs.getString("EdorTypeCal").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LBPEdorItem表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPEdorItemSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LBPEdorItemSchema getSchema()
	{
		LBPEdorItemSchema aLBPEdorItemSchema = new LBPEdorItemSchema();
		aLBPEdorItemSchema.setSchema(this);
		return aLBPEdorItemSchema;
	}

	public LBPEdorItemDB getDB()
	{
		LBPEdorItemDB aDBOper = new LBPEdorItemDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPEdorItem描述/A>表字段
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
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PolNo)); strReturn.append(SysConst.PACKAGESPILTER);
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
		strReturn.append(StrTool.cTrim(AppReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorReasonCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveFlag)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ApproveDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ApproveTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Standbyflag1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(standbyflag2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(standbyflag3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(EdorTypeCal));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPEdorItem>历史记账凭证主表信息</A>表字段
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
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			PolNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			EdorValiDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			EdorAppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12,SysConst.PACKAGESPILTER));
			EdorState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			UWOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			UWDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			UWTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ChgPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).doubleValue();
			ChgAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,19,SysConst.PACKAGESPILTER))).doubleValue();
			GetMoney = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			GetInterest = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AppReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			EdorReasonCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			EdorReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			ApproveFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			ApproveOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			ApproveDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32,SysConst.PACKAGESPILTER));
			ApproveTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			Standbyflag1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			standbyflag2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			standbyflag3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			EdorTypeCal = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LBPEdorItemSchema";
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InsuredNo));
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PolNo));
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Standbyflag1));
		}
		if (FCode.equalsIgnoreCase("standbyflag2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(standbyflag2));
		}
		if (FCode.equalsIgnoreCase("standbyflag3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(standbyflag3));
		}
		if (FCode.equalsIgnoreCase("EdorTypeCal"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorTypeCal));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(PolNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorValiDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEdorAppDate()));
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(EdorState);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(UWOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUWDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(UWTime);
				break;
			case 17:
				strFieldValue = String.valueOf(ChgPrem);
				break;
			case 18:
				strFieldValue = String.valueOf(ChgAmnt);
				break;
			case 19:
				strFieldValue = String.valueOf(GetMoney);
				break;
			case 20:
				strFieldValue = String.valueOf(GetInterest);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AppReason);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(EdorReasonCode);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(EdorReason);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(ApproveFlag);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(ApproveOperator);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getApproveDate()));
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(ApproveTime);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(Standbyflag1);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(standbyflag2);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(standbyflag3);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(EdorTypeCal);
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
		if (FCode.equalsIgnoreCase("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equalsIgnoreCase("InsuredNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InsuredNo = FValue.trim();
			}
			else
				InsuredNo = null;
		}
		if (FCode.equalsIgnoreCase("PolNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PolNo = FValue.trim();
			}
			else
				PolNo = null;
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
		if (FCode.equalsIgnoreCase("Standbyflag1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Standbyflag1 = FValue.trim();
			}
			else
				Standbyflag1 = null;
		}
		if (FCode.equalsIgnoreCase("standbyflag2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				standbyflag2 = FValue.trim();
			}
			else
				standbyflag2 = null;
		}
		if (FCode.equalsIgnoreCase("standbyflag3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				standbyflag3 = FValue.trim();
			}
			else
				standbyflag3 = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LBPEdorItemSchema other = (LBPEdorItemSchema)otherObject;
		return
			EdorAcceptNo.equals(other.getEdorAcceptNo())
			&& EdorNo.equals(other.getEdorNo())
			&& EdorAppNo.equals(other.getEdorAppNo())
			&& EdorType.equals(other.getEdorType())
			&& DisplayType.equals(other.getDisplayType())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& PolNo.equals(other.getPolNo())
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
			&& AppReason.equals(other.getAppReason())
			&& EdorReasonCode.equals(other.getEdorReasonCode())
			&& EdorReason.equals(other.getEdorReason())
			&& ApproveFlag.equals(other.getApproveFlag())
			&& ApproveOperator.equals(other.getApproveOperator())
			&& fDate.getString(ApproveDate).equals(other.getApproveDate())
			&& ApproveTime.equals(other.getApproveTime())
			&& Standbyflag1.equals(other.getStandbyflag1())
			&& standbyflag2.equals(other.getstandbyflag2())
			&& standbyflag3.equals(other.getstandbyflag3())
			&& EdorTypeCal.equals(other.getEdorTypeCal());
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
		if( strFieldName.equals("ContNo") ) {
			return 6;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 7;
		}
		if( strFieldName.equals("PolNo") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
		}
		if( strFieldName.equals("EdorValiDate") ) {
			return 10;
		}
		if( strFieldName.equals("EdorAppDate") ) {
			return 11;
		}
		if( strFieldName.equals("EdorState") ) {
			return 12;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 13;
		}
		if( strFieldName.equals("UWOperator") ) {
			return 14;
		}
		if( strFieldName.equals("UWDate") ) {
			return 15;
		}
		if( strFieldName.equals("UWTime") ) {
			return 16;
		}
		if( strFieldName.equals("ChgPrem") ) {
			return 17;
		}
		if( strFieldName.equals("ChgAmnt") ) {
			return 18;
		}
		if( strFieldName.equals("GetMoney") ) {
			return 19;
		}
		if( strFieldName.equals("GetInterest") ) {
			return 20;
		}
		if( strFieldName.equals("Operator") ) {
			return 21;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 22;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 23;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 25;
		}
		if( strFieldName.equals("AppReason") ) {
			return 26;
		}
		if( strFieldName.equals("EdorReasonCode") ) {
			return 27;
		}
		if( strFieldName.equals("EdorReason") ) {
			return 28;
		}
		if( strFieldName.equals("ApproveFlag") ) {
			return 29;
		}
		if( strFieldName.equals("ApproveOperator") ) {
			return 30;
		}
		if( strFieldName.equals("ApproveDate") ) {
			return 31;
		}
		if( strFieldName.equals("ApproveTime") ) {
			return 32;
		}
		if( strFieldName.equals("Standbyflag1") ) {
			return 33;
		}
		if( strFieldName.equals("standbyflag2") ) {
			return 34;
		}
		if( strFieldName.equals("standbyflag3") ) {
			return 35;
		}
		if( strFieldName.equals("EdorTypeCal") ) {
			return 36;
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
				strFieldName = "ContNo";
				break;
			case 7:
				strFieldName = "InsuredNo";
				break;
			case 8:
				strFieldName = "PolNo";
				break;
			case 9:
				strFieldName = "ManageCom";
				break;
			case 10:
				strFieldName = "EdorValiDate";
				break;
			case 11:
				strFieldName = "EdorAppDate";
				break;
			case 12:
				strFieldName = "EdorState";
				break;
			case 13:
				strFieldName = "UWFlag";
				break;
			case 14:
				strFieldName = "UWOperator";
				break;
			case 15:
				strFieldName = "UWDate";
				break;
			case 16:
				strFieldName = "UWTime";
				break;
			case 17:
				strFieldName = "ChgPrem";
				break;
			case 18:
				strFieldName = "ChgAmnt";
				break;
			case 19:
				strFieldName = "GetMoney";
				break;
			case 20:
				strFieldName = "GetInterest";
				break;
			case 21:
				strFieldName = "Operator";
				break;
			case 22:
				strFieldName = "MakeDate";
				break;
			case 23:
				strFieldName = "MakeTime";
				break;
			case 24:
				strFieldName = "ModifyDate";
				break;
			case 25:
				strFieldName = "ModifyTime";
				break;
			case 26:
				strFieldName = "AppReason";
				break;
			case 27:
				strFieldName = "EdorReasonCode";
				break;
			case 28:
				strFieldName = "EdorReason";
				break;
			case 29:
				strFieldName = "ApproveFlag";
				break;
			case 30:
				strFieldName = "ApproveOperator";
				break;
			case 31:
				strFieldName = "ApproveDate";
				break;
			case 32:
				strFieldName = "ApproveTime";
				break;
			case 33:
				strFieldName = "Standbyflag1";
				break;
			case 34:
				strFieldName = "standbyflag2";
				break;
			case 35:
				strFieldName = "standbyflag3";
				break;
			case 36:
				strFieldName = "EdorTypeCal";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PolNo") ) {
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
		if( strFieldName.equals("Standbyflag1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("standbyflag2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("standbyflag3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorTypeCal") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
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
			case 24:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
