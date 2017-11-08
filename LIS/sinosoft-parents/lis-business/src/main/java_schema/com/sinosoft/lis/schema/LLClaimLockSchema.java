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
import com.sinosoft.lis.db.LLClaimLockDB;

/*
 * <p>ClassName: LLClaimLockSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Physical Data Model 1
 */
public class LLClaimLockSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LLClaimLockSchema.class);
	// @Field
	/** 理赔锁定号 */
	private String ClaimLockNo;
	/** 团体保单号 */
	private String GrpContNo;
	/** 个人保单号 */
	private String ContNo;
	/** 被保险人客户号 */
	private String InsuredNo;
	/** 锁定原因 */
	private String LockReason;
	/** 锁定业务号 */
	private String LockBussNo;
	/** 锁定日期 */
	private Date LockDate;
	/** 解锁方式 */
	private String UnLockMode;
	/** 解锁业务号 */
	private String UnLockBussNo;
	/** 解锁日期 */
	private Date UnLockDate;
	/** 锁定状态 */
	private String LockState;
	/** 锁定流程状态 */
	private String LockFlowState;
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

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LLClaimLockSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ClaimLockNo";

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
		LLClaimLockSchema cloned = (LLClaimLockSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getClaimLockNo()
	{
		return ClaimLockNo;
	}
	public void setClaimLockNo(String aClaimLockNo)
	{
		if(aClaimLockNo!=null && aClaimLockNo.length()>20)
			throw new IllegalArgumentException("理赔锁定号ClaimLockNo值"+aClaimLockNo+"的长度"+aClaimLockNo.length()+"大于最大值20");
		ClaimLockNo = aClaimLockNo;
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
	public String getContNo()
	{
		return ContNo;
	}
	public void setContNo(String aContNo)
	{
		if(aContNo!=null && aContNo.length()>20)
			throw new IllegalArgumentException("个人保单号ContNo值"+aContNo+"的长度"+aContNo.length()+"大于最大值20");
		ContNo = aContNo;
	}
	public String getInsuredNo()
	{
		return InsuredNo;
	}
	public void setInsuredNo(String aInsuredNo)
	{
		if(aInsuredNo!=null && aInsuredNo.length()>20)
			throw new IllegalArgumentException("被保险人客户号InsuredNo值"+aInsuredNo+"的长度"+aInsuredNo.length()+"大于最大值20");
		InsuredNo = aInsuredNo;
	}
	/**
	* ZT-减人，IR-换人
	*/
	public String getLockReason()
	{
		return LockReason;
	}
	public void setLockReason(String aLockReason)
	{
		if(aLockReason!=null && aLockReason.length()>6)
			throw new IllegalArgumentException("锁定原因LockReason值"+aLockReason+"的长度"+aLockReason.length()+"大于最大值6");
		LockReason = aLockReason;
	}
	public String getLockBussNo()
	{
		return LockBussNo;
	}
	public void setLockBussNo(String aLockBussNo)
	{
		if(aLockBussNo!=null && aLockBussNo.length()>20)
			throw new IllegalArgumentException("锁定业务号LockBussNo值"+aLockBussNo+"的长度"+aLockBussNo.length()+"大于最大值20");
		LockBussNo = aLockBussNo;
	}
	public String getLockDate()
	{
		if( LockDate != null )
			return fDate.getString(LockDate);
		else
			return null;
	}
	public void setLockDate(Date aLockDate)
	{
		LockDate = aLockDate;
	}
	public void setLockDate(String aLockDate)
	{
		if (aLockDate != null && !aLockDate.equals("") )
		{
			LockDate = fDate.getDate( aLockDate );
		}
		else
			LockDate = null;
	}

	/**
	* 0-手动，1-保全
	*/
	public String getUnLockMode()
	{
		return UnLockMode;
	}
	public void setUnLockMode(String aUnLockMode)
	{
		if(aUnLockMode!=null && aUnLockMode.length()>6)
			throw new IllegalArgumentException("解锁方式UnLockMode值"+aUnLockMode+"的长度"+aUnLockMode.length()+"大于最大值6");
		UnLockMode = aUnLockMode;
	}
	public String getUnLockBussNo()
	{
		return UnLockBussNo;
	}
	public void setUnLockBussNo(String aUnLockBussNo)
	{
		if(aUnLockBussNo!=null && aUnLockBussNo.length()>20)
			throw new IllegalArgumentException("解锁业务号UnLockBussNo值"+aUnLockBussNo+"的长度"+aUnLockBussNo.length()+"大于最大值20");
		UnLockBussNo = aUnLockBussNo;
	}
	public String getUnLockDate()
	{
		if( UnLockDate != null )
			return fDate.getString(UnLockDate);
		else
			return null;
	}
	public void setUnLockDate(Date aUnLockDate)
	{
		UnLockDate = aUnLockDate;
	}
	public void setUnLockDate(String aUnLockDate)
	{
		if (aUnLockDate != null && !aUnLockDate.equals("") )
		{
			UnLockDate = fDate.getDate( aUnLockDate );
		}
		else
			UnLockDate = null;
	}

	/**
	* 0-已解锁，1-已锁定
	*/
	public String getLockState()
	{
		return LockState;
	}
	public void setLockState(String aLockState)
	{
		if(aLockState!=null && aLockState.length()>1)
			throw new IllegalArgumentException("锁定状态LockState值"+aLockState+"的长度"+aLockState.length()+"大于最大值1");
		LockState = aLockState;
	}
	/**
	* 0-未申请，1-已申请，2-已确认
	*/
	public String getLockFlowState()
	{
		return LockFlowState;
	}
	public void setLockFlowState(String aLockFlowState)
	{
		if(aLockFlowState!=null && aLockFlowState.length()>1)
			throw new IllegalArgumentException("锁定流程状态LockFlowState值"+aLockFlowState+"的长度"+aLockFlowState.length()+"大于最大值1");
		LockFlowState = aLockFlowState;
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
	* 使用另外一个 LLClaimLockSchema 对象给 Schema 赋值
	* @param: aLLClaimLockSchema LLClaimLockSchema
	**/
	public void setSchema(LLClaimLockSchema aLLClaimLockSchema)
	{
		this.ClaimLockNo = aLLClaimLockSchema.getClaimLockNo();
		this.GrpContNo = aLLClaimLockSchema.getGrpContNo();
		this.ContNo = aLLClaimLockSchema.getContNo();
		this.InsuredNo = aLLClaimLockSchema.getInsuredNo();
		this.LockReason = aLLClaimLockSchema.getLockReason();
		this.LockBussNo = aLLClaimLockSchema.getLockBussNo();
		this.LockDate = fDate.getDate( aLLClaimLockSchema.getLockDate());
		this.UnLockMode = aLLClaimLockSchema.getUnLockMode();
		this.UnLockBussNo = aLLClaimLockSchema.getUnLockBussNo();
		this.UnLockDate = fDate.getDate( aLLClaimLockSchema.getUnLockDate());
		this.LockState = aLLClaimLockSchema.getLockState();
		this.LockFlowState = aLLClaimLockSchema.getLockFlowState();
		this.ManageCom = aLLClaimLockSchema.getManageCom();
		this.ComCode = aLLClaimLockSchema.getComCode();
		this.MakeOperator = aLLClaimLockSchema.getMakeOperator();
		this.MakeDate = fDate.getDate( aLLClaimLockSchema.getMakeDate());
		this.MakeTime = aLLClaimLockSchema.getMakeTime();
		this.ModifyOperator = aLLClaimLockSchema.getModifyOperator();
		this.ModifyDate = fDate.getDate( aLLClaimLockSchema.getModifyDate());
		this.ModifyTime = aLLClaimLockSchema.getModifyTime();
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
			if( rs.getString("ClaimLockNo") == null )
				this.ClaimLockNo = null;
			else
				this.ClaimLockNo = rs.getString("ClaimLockNo").trim();

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

			if( rs.getString("LockReason") == null )
				this.LockReason = null;
			else
				this.LockReason = rs.getString("LockReason").trim();

			if( rs.getString("LockBussNo") == null )
				this.LockBussNo = null;
			else
				this.LockBussNo = rs.getString("LockBussNo").trim();

			this.LockDate = rs.getDate("LockDate");
			if( rs.getString("UnLockMode") == null )
				this.UnLockMode = null;
			else
				this.UnLockMode = rs.getString("UnLockMode").trim();

			if( rs.getString("UnLockBussNo") == null )
				this.UnLockBussNo = null;
			else
				this.UnLockBussNo = rs.getString("UnLockBussNo").trim();

			this.UnLockDate = rs.getDate("UnLockDate");
			if( rs.getString("LockState") == null )
				this.LockState = null;
			else
				this.LockState = rs.getString("LockState").trim();

			if( rs.getString("LockFlowState") == null )
				this.LockFlowState = null;
			else
				this.LockFlowState = rs.getString("LockFlowState").trim();

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
			logger.debug("数据库中的LLClaimLock表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimLockSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LLClaimLockSchema getSchema()
	{
		LLClaimLockSchema aLLClaimLockSchema = new LLClaimLockSchema();
		aLLClaimLockSchema.setSchema(this);
		return aLLClaimLockSchema;
	}

	public LLClaimLockDB getDB()
	{
		LLClaimLockDB aDBOper = new LLClaimLockDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimLock描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ClaimLockNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(GrpContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(InsuredNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockReason)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( LockDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnLockMode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UnLockBussNo)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UnLockDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(LockFlowState)); strReturn.append(SysConst.PACKAGESPILTER);
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLLClaimLock>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ClaimLockNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			GrpContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			InsuredNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			LockReason = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			LockBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			LockDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7,SysConst.PACKAGESPILTER));
			UnLockMode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UnLockBussNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UnLockDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			LockState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			LockFlowState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			ModifyOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimLockSchema";
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
		if (FCode.equalsIgnoreCase("ClaimLockNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimLockNo));
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
		if (FCode.equalsIgnoreCase("LockReason"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockReason));
		}
		if (FCode.equalsIgnoreCase("LockBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockBussNo));
		}
		if (FCode.equalsIgnoreCase("LockDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getLockDate()));
		}
		if (FCode.equalsIgnoreCase("UnLockMode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnLockMode));
		}
		if (FCode.equalsIgnoreCase("UnLockBussNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnLockBussNo));
		}
		if (FCode.equalsIgnoreCase("UnLockDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUnLockDate()));
		}
		if (FCode.equalsIgnoreCase("LockState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockState));
		}
		if (FCode.equalsIgnoreCase("LockFlowState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LockFlowState));
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
				strFieldValue = StrTool.GBKToUnicode(ClaimLockNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(GrpContNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(InsuredNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(LockReason);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(LockBussNo);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getLockDate()));
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UnLockMode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UnLockBussNo);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUnLockDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(LockState);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(LockFlowState);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(MakeOperator);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(ModifyOperator);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 19:
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

		if (FCode.equalsIgnoreCase("ClaimLockNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimLockNo = FValue.trim();
			}
			else
				ClaimLockNo = null;
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
		if (FCode.equalsIgnoreCase("LockReason"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockReason = FValue.trim();
			}
			else
				LockReason = null;
		}
		if (FCode.equalsIgnoreCase("LockBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockBussNo = FValue.trim();
			}
			else
				LockBussNo = null;
		}
		if (FCode.equalsIgnoreCase("LockDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				LockDate = fDate.getDate( FValue );
			}
			else
				LockDate = null;
		}
		if (FCode.equalsIgnoreCase("UnLockMode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnLockMode = FValue.trim();
			}
			else
				UnLockMode = null;
		}
		if (FCode.equalsIgnoreCase("UnLockBussNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnLockBussNo = FValue.trim();
			}
			else
				UnLockBussNo = null;
		}
		if (FCode.equalsIgnoreCase("UnLockDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UnLockDate = fDate.getDate( FValue );
			}
			else
				UnLockDate = null;
		}
		if (FCode.equalsIgnoreCase("LockState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockState = FValue.trim();
			}
			else
				LockState = null;
		}
		if (FCode.equalsIgnoreCase("LockFlowState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				LockFlowState = FValue.trim();
			}
			else
				LockFlowState = null;
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
		LLClaimLockSchema other = (LLClaimLockSchema)otherObject;
		return
			ClaimLockNo.equals(other.getClaimLockNo())
			&& GrpContNo.equals(other.getGrpContNo())
			&& ContNo.equals(other.getContNo())
			&& InsuredNo.equals(other.getInsuredNo())
			&& LockReason.equals(other.getLockReason())
			&& LockBussNo.equals(other.getLockBussNo())
			&& fDate.getString(LockDate).equals(other.getLockDate())
			&& UnLockMode.equals(other.getUnLockMode())
			&& UnLockBussNo.equals(other.getUnLockBussNo())
			&& fDate.getString(UnLockDate).equals(other.getUnLockDate())
			&& LockState.equals(other.getLockState())
			&& LockFlowState.equals(other.getLockFlowState())
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
		if( strFieldName.equals("ClaimLockNo") ) {
			return 0;
		}
		if( strFieldName.equals("GrpContNo") ) {
			return 1;
		}
		if( strFieldName.equals("ContNo") ) {
			return 2;
		}
		if( strFieldName.equals("InsuredNo") ) {
			return 3;
		}
		if( strFieldName.equals("LockReason") ) {
			return 4;
		}
		if( strFieldName.equals("LockBussNo") ) {
			return 5;
		}
		if( strFieldName.equals("LockDate") ) {
			return 6;
		}
		if( strFieldName.equals("UnLockMode") ) {
			return 7;
		}
		if( strFieldName.equals("UnLockBussNo") ) {
			return 8;
		}
		if( strFieldName.equals("UnLockDate") ) {
			return 9;
		}
		if( strFieldName.equals("LockState") ) {
			return 10;
		}
		if( strFieldName.equals("LockFlowState") ) {
			return 11;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 12;
		}
		if( strFieldName.equals("ComCode") ) {
			return 13;
		}
		if( strFieldName.equals("MakeOperator") ) {
			return 14;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 15;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyOperator") ) {
			return 17;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 19;
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
				strFieldName = "ClaimLockNo";
				break;
			case 1:
				strFieldName = "GrpContNo";
				break;
			case 2:
				strFieldName = "ContNo";
				break;
			case 3:
				strFieldName = "InsuredNo";
				break;
			case 4:
				strFieldName = "LockReason";
				break;
			case 5:
				strFieldName = "LockBussNo";
				break;
			case 6:
				strFieldName = "LockDate";
				break;
			case 7:
				strFieldName = "UnLockMode";
				break;
			case 8:
				strFieldName = "UnLockBussNo";
				break;
			case 9:
				strFieldName = "UnLockDate";
				break;
			case 10:
				strFieldName = "LockState";
				break;
			case 11:
				strFieldName = "LockFlowState";
				break;
			case 12:
				strFieldName = "ManageCom";
				break;
			case 13:
				strFieldName = "ComCode";
				break;
			case 14:
				strFieldName = "MakeOperator";
				break;
			case 15:
				strFieldName = "MakeDate";
				break;
			case 16:
				strFieldName = "MakeTime";
				break;
			case 17:
				strFieldName = "ModifyOperator";
				break;
			case 18:
				strFieldName = "ModifyDate";
				break;
			case 19:
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
		if( strFieldName.equals("ClaimLockNo") ) {
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
		if( strFieldName.equals("LockReason") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UnLockMode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnLockBussNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnLockDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("LockState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("LockFlowState") ) {
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
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
