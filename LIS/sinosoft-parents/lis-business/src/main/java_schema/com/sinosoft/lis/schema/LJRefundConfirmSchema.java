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
import com.sinosoft.lis.db.LJRefundConfirmDB;

/*
 * <p>ClassName: LJRefundConfirmSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LJRefundConfirmSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LJRefundConfirmSchema.class);
	// @Field
	/** 申请批次号 */
	private String ApplyBatNo;
	/** 其他申请批次号 */
	private String OtherApplyBatNo;
	/** 退费类型 */
	private String RefundType;
	/** 批次状态 */
	private String BatState;
	/** 业务号 */
	private String BussNo;
	/** 子业务号 */
	private String SubBussNo;
	/** 其他业务号 */
	private String OtherBussNo;
	/** 客户号码 */
	private String CustomerNo;
	/** 客户名 */
	private String GrpName;
	/** 证件类型 */
	private String IDType;
	/** 证件号码 */
	private String IDNo;
	/** 业务机构 */
	private String BussCom;
	/** 申请人员 */
	private String AppOperator;
	/** 申请日期 */
	private Date AppDate;
	/** 申请时间 */
	private String AppTime;
	/** 审核人员 */
	private String ConfirmOperator;
	/** 审核日期 */
	private Date ConfirmDate;
	/** 审核时间 */
	private String ConfirmTime;
	/** 审核结论 */
	private String ConfirmConclusion;
	/** 审核结论描述 */
	private String ConfirmDesc;
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

	public static final int FIELDNUM = 28;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LJRefundConfirmSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ApplyBatNo";

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
		LJRefundConfirmSchema cloned = (LJRefundConfirmSchema)super.clone();
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
	public String getOtherApplyBatNo()
	{
		return OtherApplyBatNo;
	}
	public void setOtherApplyBatNo(String aOtherApplyBatNo)
	{
		if(aOtherApplyBatNo!=null && aOtherApplyBatNo.length()>20)
			throw new IllegalArgumentException("其他申请批次号OtherApplyBatNo值"+aOtherApplyBatNo+"的长度"+aOtherApplyBatNo.length()+"大于最大值20");
		OtherApplyBatNo = aOtherApplyBatNo;
	}
	public String getRefundType()
	{
		return RefundType;
	}
	public void setRefundType(String aRefundType)
	{
		if(aRefundType!=null && aRefundType.length()>2)
			throw new IllegalArgumentException("退费类型RefundType值"+aRefundType+"的长度"+aRefundType.length()+"大于最大值2");
		RefundType = aRefundType;
	}
	/**
	* 0-已申请待确认，1-待确认，2-确认成功，3-银行信息修改，4-撤销
	*/
	public String getBatState()
	{
		return BatState;
	}
	public void setBatState(String aBatState)
	{
		if(aBatState!=null && aBatState.length()>2)
			throw new IllegalArgumentException("批次状态BatState值"+aBatState+"的长度"+aBatState.length()+"大于最大值2");
		BatState = aBatState;
	}
	public String getBussNo()
	{
		return BussNo;
	}
	public void setBussNo(String aBussNo)
	{
		if(aBussNo!=null && aBussNo.length()>20)
			throw new IllegalArgumentException("业务号BussNo值"+aBussNo+"的长度"+aBussNo.length()+"大于最大值20");
		BussNo = aBussNo;
	}
	public String getSubBussNo()
	{
		return SubBussNo;
	}
	public void setSubBussNo(String aSubBussNo)
	{
		if(aSubBussNo!=null && aSubBussNo.length()>20)
			throw new IllegalArgumentException("子业务号SubBussNo值"+aSubBussNo+"的长度"+aSubBussNo.length()+"大于最大值20");
		SubBussNo = aSubBussNo;
	}
	public String getOtherBussNo()
	{
		return OtherBussNo;
	}
	public void setOtherBussNo(String aOtherBussNo)
	{
		if(aOtherBussNo!=null && aOtherBussNo.length()>20)
			throw new IllegalArgumentException("其他业务号OtherBussNo值"+aOtherBussNo+"的长度"+aOtherBussNo.length()+"大于最大值20");
		OtherBussNo = aOtherBussNo;
	}
	public String getCustomerNo()
	{
		return CustomerNo;
	}
	public void setCustomerNo(String aCustomerNo)
	{
		if(aCustomerNo!=null && aCustomerNo.length()>20)
			throw new IllegalArgumentException("客户号码CustomerNo值"+aCustomerNo+"的长度"+aCustomerNo.length()+"大于最大值20");
		CustomerNo = aCustomerNo;
	}
	public String getGrpName()
	{
		return GrpName;
	}
	public void setGrpName(String aGrpName)
	{
		if(aGrpName!=null && aGrpName.length()>300)
			throw new IllegalArgumentException("客户名GrpName值"+aGrpName+"的长度"+aGrpName.length()+"大于最大值300");
		GrpName = aGrpName;
	}
	public String getIDType()
	{
		return IDType;
	}
	public void setIDType(String aIDType)
	{
		if(aIDType!=null && aIDType.length()>4)
			throw new IllegalArgumentException("证件类型IDType值"+aIDType+"的长度"+aIDType.length()+"大于最大值4");
		IDType = aIDType;
	}
	public String getIDNo()
	{
		return IDNo;
	}
	public void setIDNo(String aIDNo)
	{
		if(aIDNo!=null && aIDNo.length()>50)
			throw new IllegalArgumentException("证件号码IDNo值"+aIDNo+"的长度"+aIDNo.length()+"大于最大值50");
		IDNo = aIDNo;
	}
	public String getBussCom()
	{
		return BussCom;
	}
	public void setBussCom(String aBussCom)
	{
		if(aBussCom!=null && aBussCom.length()>20)
			throw new IllegalArgumentException("业务机构BussCom值"+aBussCom+"的长度"+aBussCom.length()+"大于最大值20");
		BussCom = aBussCom;
	}
	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		if(aAppOperator!=null && aAppOperator.length()>30)
			throw new IllegalArgumentException("申请人员AppOperator值"+aAppOperator+"的长度"+aAppOperator.length()+"大于最大值30");
		AppOperator = aAppOperator;
	}
	public String getAppDate()
	{
		if( AppDate != null )
			return fDate.getString(AppDate);
		else
			return null;
	}
	public void setAppDate(Date aAppDate)
	{
		AppDate = aAppDate;
	}
	public void setAppDate(String aAppDate)
	{
		if (aAppDate != null && !aAppDate.equals("") )
		{
			AppDate = fDate.getDate( aAppDate );
		}
		else
			AppDate = null;
	}

	public String getAppTime()
	{
		return AppTime;
	}
	public void setAppTime(String aAppTime)
	{
		if(aAppTime!=null && aAppTime.length()>8)
			throw new IllegalArgumentException("申请时间AppTime值"+aAppTime+"的长度"+aAppTime.length()+"大于最大值8");
		AppTime = aAppTime;
	}
	public String getConfirmOperator()
	{
		return ConfirmOperator;
	}
	public void setConfirmOperator(String aConfirmOperator)
	{
		if(aConfirmOperator!=null && aConfirmOperator.length()>30)
			throw new IllegalArgumentException("审核人员ConfirmOperator值"+aConfirmOperator+"的长度"+aConfirmOperator.length()+"大于最大值30");
		ConfirmOperator = aConfirmOperator;
	}
	public String getConfirmDate()
	{
		if( ConfirmDate != null )
			return fDate.getString(ConfirmDate);
		else
			return null;
	}
	public void setConfirmDate(Date aConfirmDate)
	{
		ConfirmDate = aConfirmDate;
	}
	public void setConfirmDate(String aConfirmDate)
	{
		if (aConfirmDate != null && !aConfirmDate.equals("") )
		{
			ConfirmDate = fDate.getDate( aConfirmDate );
		}
		else
			ConfirmDate = null;
	}

	public String getConfirmTime()
	{
		return ConfirmTime;
	}
	public void setConfirmTime(String aConfirmTime)
	{
		if(aConfirmTime!=null && aConfirmTime.length()>8)
			throw new IllegalArgumentException("审核时间ConfirmTime值"+aConfirmTime+"的长度"+aConfirmTime.length()+"大于最大值8");
		ConfirmTime = aConfirmTime;
	}
	public String getConfirmConclusion()
	{
		return ConfirmConclusion;
	}
	public void setConfirmConclusion(String aConfirmConclusion)
	{
		if(aConfirmConclusion!=null && aConfirmConclusion.length()>2)
			throw new IllegalArgumentException("审核结论ConfirmConclusion值"+aConfirmConclusion+"的长度"+aConfirmConclusion.length()+"大于最大值2");
		ConfirmConclusion = aConfirmConclusion;
	}
	public String getConfirmDesc()
	{
		return ConfirmDesc;
	}
	public void setConfirmDesc(String aConfirmDesc)
	{
		if(aConfirmDesc!=null && aConfirmDesc.length()>3000)
			throw new IllegalArgumentException("审核结论描述ConfirmDesc值"+aConfirmDesc+"的长度"+aConfirmDesc.length()+"大于最大值3000");
		ConfirmDesc = aConfirmDesc;
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
	* 使用另外一个 LJRefundConfirmSchema 对象给 Schema 赋值
	* @param: aLJRefundConfirmSchema LJRefundConfirmSchema
	**/
	public void setSchema(LJRefundConfirmSchema aLJRefundConfirmSchema)
	{
		this.ApplyBatNo = aLJRefundConfirmSchema.getApplyBatNo();
		this.OtherApplyBatNo = aLJRefundConfirmSchema.getOtherApplyBatNo();
		this.RefundType = aLJRefundConfirmSchema.getRefundType();
		this.BatState = aLJRefundConfirmSchema.getBatState();
		this.BussNo = aLJRefundConfirmSchema.getBussNo();
		this.SubBussNo = aLJRefundConfirmSchema.getSubBussNo();
		this.OtherBussNo = aLJRefundConfirmSchema.getOtherBussNo();
		this.CustomerNo = aLJRefundConfirmSchema.getCustomerNo();
		this.GrpName = aLJRefundConfirmSchema.getGrpName();
		this.IDType = aLJRefundConfirmSchema.getIDType();
		this.IDNo = aLJRefundConfirmSchema.getIDNo();
		this.BussCom = aLJRefundConfirmSchema.getBussCom();
		this.AppOperator = aLJRefundConfirmSchema.getAppOperator();
		this.AppDate = fDate.getDate( aLJRefundConfirmSchema.getAppDate());
		this.AppTime = aLJRefundConfirmSchema.getAppTime();
		this.ConfirmOperator = aLJRefundConfirmSchema.getConfirmOperator();
		this.ConfirmDate = fDate.getDate( aLJRefundConfirmSchema.getConfirmDate());
		this.ConfirmTime = aLJRefundConfirmSchema.getConfirmTime();
		this.ConfirmConclusion = aLJRefundConfirmSchema.getConfirmConclusion();
		this.ConfirmDesc = aLJRefundConfirmSchema.getConfirmDesc();
		this.ManageCom = aLJRefundConfirmSchema.getManageCom();
		this.ComCode = aLJRefundConfirmSchema.getComCode();
		this.MakeOperator = aLJRefundConfirmSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLJRefundConfirmSchema.getMakeDate());
		this.MakeTime = aLJRefundConfirmSchema.getMakeTime();
		this.ModifyOperator = aLJRefundConfirmSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLJRefundConfirmSchema.getModifyDate());
		this.ModifyTime = aLJRefundConfirmSchema.getModifyTime();
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

			if( rs.getString("OtherApplyBatNo") == null )
				this.OtherApplyBatNo = null;
			else
				this.OtherApplyBatNo = rs.getString("OtherApplyBatNo").trim();

			if( rs.getString("RefundType") == null )
				this.RefundType = null;
			else
				this.RefundType = rs.getString("RefundType").trim();

			if( rs.getString("BatState") == null )
				this.BatState = null;
			else
				this.BatState = rs.getString("BatState").trim();

			if( rs.getString("BussNo") == null )
				this.BussNo = null;
			else
				this.BussNo = rs.getString("BussNo").trim();

			if( rs.getString("SubBussNo") == null )
				this.SubBussNo = null;
			else
				this.SubBussNo = rs.getString("SubBussNo").trim();

			if( rs.getString("OtherBussNo") == null )
				this.OtherBussNo = null;
			else
				this.OtherBussNo = rs.getString("OtherBussNo").trim();

			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("IDType") == null )
				this.IDType = null;
			else
				this.IDType = rs.getString("IDType").trim();

			if( rs.getString("IDNo") == null )
				this.IDNo = null;
			else
				this.IDNo = rs.getString("IDNo").trim();

			if( rs.getString("BussCom") == null )
				this.BussCom = null;
			else
				this.BussCom = rs.getString("BussCom").trim();

			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			this.AppDate = rs.getDate("AppDate");
			if( rs.getString("AppTime") == null )
				this.AppTime = null;
			else
				this.AppTime = rs.getString("AppTime").trim();

			if( rs.getString("ConfirmOperator") == null )
				this.ConfirmOperator = null;
			else
				this.ConfirmOperator = rs.getString("ConfirmOperator").trim();

			this.ConfirmDate = rs.getDate("ConfirmDate");
			if( rs.getString("ConfirmTime") == null )
				this.ConfirmTime = null;
			else
				this.ConfirmTime = rs.getString("ConfirmTime").trim();

			if( rs.getString("ConfirmConclusion") == null )
				this.ConfirmConclusion = null;
			else
				this.ConfirmConclusion = rs.getString("ConfirmConclusion").trim();

			if( rs.getString("ConfirmDesc") == null )
				this.ConfirmDesc = null;
			else
				this.ConfirmDesc = rs.getString("ConfirmDesc").trim();

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
			logger.debug("数据库中的LJRefundConfirm表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJRefundConfirmSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LJRefundConfirmSchema getSchema()
	{
		LJRefundConfirmSchema aLJRefundConfirmSchema = new LJRefundConfirmSchema();
		aLJRefundConfirmSchema.setSchema(this);
		return aLJRefundConfirmSchema;
	}

	public LJRefundConfirmDB getDB()
	{
		LJRefundConfirmDB aDBOper = new LJRefundConfirmDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJRefundConfirm描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherApplyBatNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RefundType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BatState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SubBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(OtherBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CustomerNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(IDNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(BussCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( AppDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ConfirmDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmConclusion)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConfirmDesc)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLJRefundConfirm>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OtherApplyBatNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RefundType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BatState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			BussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			SubBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			OtherBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			IDType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			IDNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			BussCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AppDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14,SysConst.PACKAGESPILTER));
			AppTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ConfirmOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ConfirmDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			ConfirmTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ConfirmConclusion = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ConfirmDesc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJRefundConfirmSchema";
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
		if (FCode.equalsIgnoreCase("OtherApplyBatNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherApplyBatNo));
		}
		if (FCode.equalsIgnoreCase("RefundType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RefundType));
		}
		if (FCode.equalsIgnoreCase("BatState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BatState));
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussNo));
		}
		if (FCode.equalsIgnoreCase("SubBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubBussNo));
		}
		if (FCode.equalsIgnoreCase("OtherBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherBussNo));
		}
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDType));
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(IDNo));
		}
		if (FCode.equalsIgnoreCase("BussCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BussCom));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmOperator));
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmTime));
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmConclusion));
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConfirmDesc));
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
				strFieldValue = StrTool.GBKToUnicode(OtherApplyBatNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RefundType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BatState);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(BussNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(SubBussNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(OtherBussNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(IDType);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(IDNo);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(BussCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getAppDate()));
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(AppTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ConfirmOperator);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConfirmDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ConfirmTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(ConfirmConclusion);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(ConfirmDesc);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 27:
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
		if (FCode.equalsIgnoreCase("OtherApplyBatNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherApplyBatNo = FValue.trim();
			}
			else
				OtherApplyBatNo = null;
		}
		if (FCode.equalsIgnoreCase("RefundType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RefundType = FValue.trim();
			}
			else
				RefundType = null;
		}
		if (FCode.equalsIgnoreCase("BatState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BatState = FValue.trim();
			}
			else
				BatState = null;
		}
		if (FCode.equalsIgnoreCase("BussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussNo = FValue.trim();
			}
			else
				BussNo = null;
		}
		if (FCode.equalsIgnoreCase("SubBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubBussNo = FValue.trim();
			}
			else
				SubBussNo = null;
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
		if (FCode.equalsIgnoreCase("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equalsIgnoreCase("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equalsIgnoreCase("IDType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDType = FValue.trim();
			}
			else
				IDType = null;
		}
		if (FCode.equalsIgnoreCase("IDNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				IDNo = FValue.trim();
			}
			else
				IDNo = null;
		}
		if (FCode.equalsIgnoreCase("BussCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BussCom = FValue.trim();
			}
			else
				BussCom = null;
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppOperator = FValue.trim();
			}
			else
				AppOperator = null;
		}
		if (FCode.equalsIgnoreCase("AppDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				AppDate = fDate.getDate( FValue );
			}
			else
				AppDate = null;
		}
		if (FCode.equalsIgnoreCase("AppTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppTime = FValue.trim();
			}
			else
				AppTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmOperator = FValue.trim();
			}
			else
				ConfirmOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConfirmDate = fDate.getDate( FValue );
			}
			else
				ConfirmDate = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmTime = FValue.trim();
			}
			else
				ConfirmTime = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmConclusion"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmConclusion = FValue.trim();
			}
			else
				ConfirmConclusion = null;
		}
		if (FCode.equalsIgnoreCase("ConfirmDesc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConfirmDesc = FValue.trim();
			}
			else
				ConfirmDesc = null;
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
		LJRefundConfirmSchema other = (LJRefundConfirmSchema)otherObject;
		return
			ApplyBatNo.equals(other.getApplyBatNo())
			&& OtherApplyBatNo.equals(other.getOtherApplyBatNo())
			&& RefundType.equals(other.getRefundType())
			&& BatState.equals(other.getBatState())
			&& BussNo.equals(other.getBussNo())
			&& SubBussNo.equals(other.getSubBussNo())
			&& OtherBussNo.equals(other.getOtherBussNo())
			&& CustomerNo.equals(other.getCustomerNo())
			&& GrpName.equals(other.getGrpName())
			&& IDType.equals(other.getIDType())
			&& IDNo.equals(other.getIDNo())
			&& BussCom.equals(other.getBussCom())
			&& AppOperator.equals(other.getAppOperator())
			&& fDate.getString(AppDate).equals(other.getAppDate())
			&& AppTime.equals(other.getAppTime())
			&& ConfirmOperator.equals(other.getConfirmOperator())
			&& fDate.getString(ConfirmDate).equals(other.getConfirmDate())
			&& ConfirmTime.equals(other.getConfirmTime())
			&& ConfirmConclusion.equals(other.getConfirmConclusion())
			&& ConfirmDesc.equals(other.getConfirmDesc())
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
		if( strFieldName.equals("OtherApplyBatNo") ) {
			return 1;
		}
		if( strFieldName.equals("RefundType") ) {
			return 2;
		}
		if( strFieldName.equals("BatState") ) {
			return 3;
		}
		if( strFieldName.equals("BussNo") ) {
			return 4;
		}
		if( strFieldName.equals("SubBussNo") ) {
			return 5;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return 6;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return 7;
		}
		if( strFieldName.equals("GrpName") ) {
			return 8;
		}
		if( strFieldName.equals("IDType") ) {
			return 9;
		}
		if( strFieldName.equals("IDNo") ) {
			return 10;
		}
		if( strFieldName.equals("BussCom") ) {
			return 11;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 12;
		}
		if( strFieldName.equals("AppDate") ) {
			return 13;
		}
		if( strFieldName.equals("AppTime") ) {
			return 14;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return 15;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return 16;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return 17;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return 18;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
			return 19;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 20;
		}
		if( strFieldName.equals("ComCode") ) {
			return 21;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
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
				strFieldName = "OtherApplyBatNo";
				break;
			case 2:
				strFieldName = "RefundType";
				break;
			case 3:
				strFieldName = "BatState";
				break;
			case 4:
				strFieldName = "BussNo";
				break;
			case 5:
				strFieldName = "SubBussNo";
				break;
			case 6:
				strFieldName = "OtherBussNo";
				break;
			case 7:
				strFieldName = "CustomerNo";
				break;
			case 8:
				strFieldName = "GrpName";
				break;
			case 9:
				strFieldName = "IDType";
				break;
			case 10:
				strFieldName = "IDNo";
				break;
			case 11:
				strFieldName = "BussCom";
				break;
			case 12:
				strFieldName = "AppOperator";
				break;
			case 13:
				strFieldName = "AppDate";
				break;
			case 14:
				strFieldName = "AppTime";
				break;
			case 15:
				strFieldName = "ConfirmOperator";
				break;
			case 16:
				strFieldName = "ConfirmDate";
				break;
			case 17:
				strFieldName = "ConfirmTime";
				break;
			case 18:
				strFieldName = "ConfirmConclusion";
				break;
			case 19:
				strFieldName = "ConfirmDesc";
				break;
			case 20:
				strFieldName = "ManageCom";
				break;
			case 21:
				strFieldName = "ComCode";
				break;
			case 22:
				strFieldName = "MakeOperator";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyOperator";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
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
		if( strFieldName.equals("OtherApplyBatNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RefundType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BatState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("IDNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BussCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("AppTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConfirmTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmConclusion") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConfirmDesc") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 14:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 26:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
