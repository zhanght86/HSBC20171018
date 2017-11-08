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
import com.sinosoft.lis.db.LZCardPlanDB;

/*
 * <p>ClassName: LZCardPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证管理
 */
public class LZCardPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LZCardPlanSchema.class);

	// @Field
	/** 计划标识 */
	private String PlanID;
	/** 单证编码 */
	private String CertifyCode;
	/** 计划类型 */
	private String PlanType;
	/** 申请数量 */
	private int AppCount;
	/** 申请人 */
	private String AppOperator;
	/** 申请机构 */
	private String AppCom;
	/** 调整数量 */
	private int ConCount;
	/** 调整人 */
	private String ConOperator;
	/** 调整机构 */
	private String ConCom;
	/** 批复数量 */
	private int RetCount;
	/** 批复人 */
	private String RetOperator;
	/** 批复机构 */
	private String RetCom;
	/** 批复情况 */
	private String RetState;
	/** 库存分配数量 */
	private int AssignCount;
	/** 关联计划 */
	private String RelaPlan;
	/** 关联印刷 */
	private String RelaPrint;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 更新日期 */
	private Date UpdateDate;
	/** 更新时间 */
	private String UpdateTime;
	/** 计划状态 */
	private String PlanState;

	public static final int FIELDNUM = 21;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LZCardPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "PlanID";

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
		LZCardPlanSchema cloned = (LZCardPlanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	public String getPlanID()
	{
		return PlanID;
	}
	public void setPlanID(String aPlanID)
	{
		PlanID = aPlanID;
	}
	public String getCertifyCode()
	{
		return CertifyCode;
	}
	public void setCertifyCode(String aCertifyCode)
	{
		CertifyCode = aCertifyCode;
	}
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		PlanType = aPlanType;
	}
	public int getAppCount()
	{
		return AppCount;
	}
	public void setAppCount(int aAppCount)
	{
		AppCount = aAppCount;
	}
	public void setAppCount(String aAppCount)
	{
		if (aAppCount != null && !aAppCount.equals(""))
		{
			Integer tInteger = new Integer(aAppCount);
			int i = tInteger.intValue();
			AppCount = i;
		}
	}

	public String getAppOperator()
	{
		return AppOperator;
	}
	public void setAppOperator(String aAppOperator)
	{
		AppOperator = aAppOperator;
	}
	public String getAppCom()
	{
		return AppCom;
	}
	public void setAppCom(String aAppCom)
	{
		AppCom = aAppCom;
	}
	/**
	* 分公司对支公司和分公司自己申请的计划作汇总时产生<p>
	* 总公司不需要汇总分公司计划，所以无此数据
	*/
	public int getConCount()
	{
		return ConCount;
	}
	public void setConCount(int aConCount)
	{
		ConCount = aConCount;
	}
	public void setConCount(String aConCount)
	{
		if (aConCount != null && !aConCount.equals(""))
		{
			Integer tInteger = new Integer(aConCount);
			int i = tInteger.intValue();
			ConCount = i;
		}
	}

	public String getConOperator()
	{
		return ConOperator;
	}
	public void setConOperator(String aConOperator)
	{
		ConOperator = aConOperator;
	}
	public String getConCom()
	{
		return ConCom;
	}
	public void setConCom(String aConCom)
	{
		ConCom = aConCom;
	}
	public int getRetCount()
	{
		return RetCount;
	}
	public void setRetCount(int aRetCount)
	{
		RetCount = aRetCount;
	}
	public void setRetCount(String aRetCount)
	{
		if (aRetCount != null && !aRetCount.equals(""))
		{
			Integer tInteger = new Integer(aRetCount);
			int i = tInteger.intValue();
			RetCount = i;
		}
	}

	public String getRetOperator()
	{
		return RetOperator;
	}
	public void setRetOperator(String aRetOperator)
	{
		RetOperator = aRetOperator;
	}
	public String getRetCom()
	{
		return RetCom;
	}
	public void setRetCom(String aRetCom)
	{
		RetCom = aRetCom;
	}
	/**
	* 在PlanState为“批复状态”时，批复情况才有意义。<p>
	* Y -- 批复通过<p>
	* N -- 批复不通过
	*/
	public String getRetState()
	{
		return RetState;
	}
	public void setRetState(String aRetState)
	{
		RetState = aRetState;
	}
	/**
	* 库存分配的时候产生：<p>
	* 总公司在印刷计划汇总之前先把库存分配给分公司，<p>
	* 然后用计划批复数量—库存分配量=应该印刷的数量
	*/
	public int getAssignCount()
	{
		return AssignCount;
	}
	public void setAssignCount(int aAssignCount)
	{
		AssignCount = aAssignCount;
	}
	public void setAssignCount(String aAssignCount)
	{
		if (aAssignCount != null && !aAssignCount.equals(""))
		{
			Integer tInteger = new Integer(aAssignCount);
			int i = tInteger.intValue();
			AssignCount = i;
		}
	}

	public String getRelaPlan()
	{
		return RelaPlan;
	}
	public void setRelaPlan(String aRelaPlan)
	{
		RelaPlan = aRelaPlan;
	}
	public String getRelaPrint()
	{
		return RelaPrint;
	}
	public void setRelaPrint(String aRelaPrint)
	{
		RelaPrint = aRelaPrint;
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
	public String getUpdateDate()
	{
		if( UpdateDate != null )
			return fDate.getString(UpdateDate);
		else
			return null;
	}
	public void setUpdateDate(Date aUpdateDate)
	{
		UpdateDate = aUpdateDate;
	}
	public void setUpdateDate(String aUpdateDate)
	{
		if (aUpdateDate != null && !aUpdateDate.equals("") )
		{
			UpdateDate = fDate.getDate( aUpdateDate );
		}
		else
			UpdateDate = null;
	}

	public String getUpdateTime()
	{
		return UpdateTime;
	}
	public void setUpdateTime(String aUpdateTime)
	{
		UpdateTime = aUpdateTime;
	}
	/**
	* 申请状态　A(pply)<p>
	* 确认状态　C(onfirm)<p>
	* <p>
	* 汇总状态  P[ack]<p>
	* 汇总提交状态  S[ubmit]<p>
	* <p>
	* 回复状态　R(eturn)
	*/
	public String getPlanState()
	{
		return PlanState;
	}
	public void setPlanState(String aPlanState)
	{
		PlanState = aPlanState;
	}

	/**
	* 使用另外一个 LZCardPlanSchema 对象给 Schema 赋值
	* @param: aLZCardPlanSchema LZCardPlanSchema
	**/
	public void setSchema(LZCardPlanSchema aLZCardPlanSchema)
	{
		this.PlanID = aLZCardPlanSchema.getPlanID();
		this.CertifyCode = aLZCardPlanSchema.getCertifyCode();
		this.PlanType = aLZCardPlanSchema.getPlanType();
		this.AppCount = aLZCardPlanSchema.getAppCount();
		this.AppOperator = aLZCardPlanSchema.getAppOperator();
		this.AppCom = aLZCardPlanSchema.getAppCom();
		this.ConCount = aLZCardPlanSchema.getConCount();
		this.ConOperator = aLZCardPlanSchema.getConOperator();
		this.ConCom = aLZCardPlanSchema.getConCom();
		this.RetCount = aLZCardPlanSchema.getRetCount();
		this.RetOperator = aLZCardPlanSchema.getRetOperator();
		this.RetCom = aLZCardPlanSchema.getRetCom();
		this.RetState = aLZCardPlanSchema.getRetState();
		this.AssignCount = aLZCardPlanSchema.getAssignCount();
		this.RelaPlan = aLZCardPlanSchema.getRelaPlan();
		this.RelaPrint = aLZCardPlanSchema.getRelaPrint();
		this.MakeDate = fDate.getDate( aLZCardPlanSchema.getMakeDate());
		this.MakeTime = aLZCardPlanSchema.getMakeTime();
		this.UpdateDate = fDate.getDate( aLZCardPlanSchema.getUpdateDate());
		this.UpdateTime = aLZCardPlanSchema.getUpdateTime();
		this.PlanState = aLZCardPlanSchema.getPlanState();
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
			if( rs.getString("PlanID") == null )
				this.PlanID = null;
			else
				this.PlanID = rs.getString("PlanID").trim();

			if( rs.getString("CertifyCode") == null )
				this.CertifyCode = null;
			else
				this.CertifyCode = rs.getString("CertifyCode").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			this.AppCount = rs.getInt("AppCount");
			if( rs.getString("AppOperator") == null )
				this.AppOperator = null;
			else
				this.AppOperator = rs.getString("AppOperator").trim();

			if( rs.getString("AppCom") == null )
				this.AppCom = null;
			else
				this.AppCom = rs.getString("AppCom").trim();

			this.ConCount = rs.getInt("ConCount");
			if( rs.getString("ConOperator") == null )
				this.ConOperator = null;
			else
				this.ConOperator = rs.getString("ConOperator").trim();

			if( rs.getString("ConCom") == null )
				this.ConCom = null;
			else
				this.ConCom = rs.getString("ConCom").trim();

			this.RetCount = rs.getInt("RetCount");
			if( rs.getString("RetOperator") == null )
				this.RetOperator = null;
			else
				this.RetOperator = rs.getString("RetOperator").trim();

			if( rs.getString("RetCom") == null )
				this.RetCom = null;
			else
				this.RetCom = rs.getString("RetCom").trim();

			if( rs.getString("RetState") == null )
				this.RetState = null;
			else
				this.RetState = rs.getString("RetState").trim();

			this.AssignCount = rs.getInt("AssignCount");
			if( rs.getString("RelaPlan") == null )
				this.RelaPlan = null;
			else
				this.RelaPlan = rs.getString("RelaPlan").trim();

			if( rs.getString("RelaPrint") == null )
				this.RelaPrint = null;
			else
				this.RelaPrint = rs.getString("RelaPrint").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.UpdateDate = rs.getDate("UpdateDate");
			if( rs.getString("UpdateTime") == null )
				this.UpdateTime = null;
			else
				this.UpdateTime = rs.getString("UpdateTime").trim();

			if( rs.getString("PlanState") == null )
				this.PlanState = null;
			else
				this.PlanState = rs.getString("PlanState").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LZCardPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LZCardPlanSchema getSchema()
	{
		LZCardPlanSchema aLZCardPlanSchema = new LZCardPlanSchema();
		aLZCardPlanSchema.setSchema(this);
		return aLZCardPlanSchema;
	}

	public LZCardPlanDB getDB()
	{
		LZCardPlanDB aDBOper = new LZCardPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(PlanID)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(CertifyCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AppCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(AppCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(ConCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ConCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(RetCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RetOperator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RetCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RetState)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(AssignCount));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaPlan)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(RelaPrint)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( UpdateDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(UpdateTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanState));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLZCardPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			PlanID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CertifyCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AppCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			AppOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AppCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ConCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			ConOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			ConCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			RetCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			RetOperator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			RetCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RetState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			AssignCount= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			RelaPlan = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			RelaPrint = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			UpdateDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			UpdateTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PlanState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LZCardPlanSchema";
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
		if (FCode.equalsIgnoreCase("PlanID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanID));
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyCode));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("AppCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppCount));
		}
		if (FCode.equalsIgnoreCase("AppOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppOperator));
		}
		if (FCode.equalsIgnoreCase("AppCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AppCom));
		}
		if (FCode.equalsIgnoreCase("ConCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConCount));
		}
		if (FCode.equalsIgnoreCase("ConOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConOperator));
		}
		if (FCode.equalsIgnoreCase("ConCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConCom));
		}
		if (FCode.equalsIgnoreCase("RetCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetCount));
		}
		if (FCode.equalsIgnoreCase("RetOperator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetOperator));
		}
		if (FCode.equalsIgnoreCase("RetCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetCom));
		}
		if (FCode.equalsIgnoreCase("RetState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RetState));
		}
		if (FCode.equalsIgnoreCase("AssignCount"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AssignCount));
		}
		if (FCode.equalsIgnoreCase("RelaPlan"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPlan));
		}
		if (FCode.equalsIgnoreCase("RelaPrint"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RelaPrint));
		}
		if (FCode.equalsIgnoreCase("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equalsIgnoreCase("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equalsIgnoreCase("UpdateDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getUpdateDate()));
		}
		if (FCode.equalsIgnoreCase("UpdateTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpdateTime));
		}
		if (FCode.equalsIgnoreCase("PlanState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanState));
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
				strFieldValue = StrTool.GBKToUnicode(PlanID);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CertifyCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 3:
				strFieldValue = String.valueOf(AppCount);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AppOperator);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AppCom);
				break;
			case 6:
				strFieldValue = String.valueOf(ConCount);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ConOperator);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(ConCom);
				break;
			case 9:
				strFieldValue = String.valueOf(RetCount);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RetOperator);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(RetCom);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RetState);
				break;
			case 13:
				strFieldValue = String.valueOf(AssignCount);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(RelaPlan);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(RelaPrint);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getUpdateDate()));
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(UpdateTime);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PlanState);
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

		if (FCode.equalsIgnoreCase("PlanID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanID = FValue.trim();
			}
			else
				PlanID = null;
		}
		if (FCode.equalsIgnoreCase("CertifyCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyCode = FValue.trim();
			}
			else
				CertifyCode = null;
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanType = FValue.trim();
			}
			else
				PlanType = null;
		}
		if (FCode.equalsIgnoreCase("AppCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AppCount = i;
			}
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
		if (FCode.equalsIgnoreCase("AppCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AppCom = FValue.trim();
			}
			else
				AppCom = null;
		}
		if (FCode.equalsIgnoreCase("ConCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ConCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("ConOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConOperator = FValue.trim();
			}
			else
				ConOperator = null;
		}
		if (FCode.equalsIgnoreCase("ConCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConCom = FValue.trim();
			}
			else
				ConCom = null;
		}
		if (FCode.equalsIgnoreCase("RetCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RetCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("RetOperator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RetOperator = FValue.trim();
			}
			else
				RetOperator = null;
		}
		if (FCode.equalsIgnoreCase("RetCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RetCom = FValue.trim();
			}
			else
				RetCom = null;
		}
		if (FCode.equalsIgnoreCase("RetState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RetState = FValue.trim();
			}
			else
				RetState = null;
		}
		if (FCode.equalsIgnoreCase("AssignCount"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AssignCount = i;
			}
		}
		if (FCode.equalsIgnoreCase("RelaPlan"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaPlan = FValue.trim();
			}
			else
				RelaPlan = null;
		}
		if (FCode.equalsIgnoreCase("RelaPrint"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RelaPrint = FValue.trim();
			}
			else
				RelaPrint = null;
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
		if (FCode.equalsIgnoreCase("UpdateDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				UpdateDate = fDate.getDate( FValue );
			}
			else
				UpdateDate = null;
		}
		if (FCode.equalsIgnoreCase("UpdateTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpdateTime = FValue.trim();
			}
			else
				UpdateTime = null;
		}
		if (FCode.equalsIgnoreCase("PlanState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanState = FValue.trim();
			}
			else
				PlanState = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LZCardPlanSchema other = (LZCardPlanSchema)otherObject;
		return
			PlanID.equals(other.getPlanID())
			&& CertifyCode.equals(other.getCertifyCode())
			&& PlanType.equals(other.getPlanType())
			&& AppCount == other.getAppCount()
			&& AppOperator.equals(other.getAppOperator())
			&& AppCom.equals(other.getAppCom())
			&& ConCount == other.getConCount()
			&& ConOperator.equals(other.getConOperator())
			&& ConCom.equals(other.getConCom())
			&& RetCount == other.getRetCount()
			&& RetOperator.equals(other.getRetOperator())
			&& RetCom.equals(other.getRetCom())
			&& RetState.equals(other.getRetState())
			&& AssignCount == other.getAssignCount()
			&& RelaPlan.equals(other.getRelaPlan())
			&& RelaPrint.equals(other.getRelaPrint())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(UpdateDate).equals(other.getUpdateDate())
			&& UpdateTime.equals(other.getUpdateTime())
			&& PlanState.equals(other.getPlanState());
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
		if( strFieldName.equals("PlanID") ) {
			return 0;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return 1;
		}
		if( strFieldName.equals("PlanType") ) {
			return 2;
		}
		if( strFieldName.equals("AppCount") ) {
			return 3;
		}
		if( strFieldName.equals("AppOperator") ) {
			return 4;
		}
		if( strFieldName.equals("AppCom") ) {
			return 5;
		}
		if( strFieldName.equals("ConCount") ) {
			return 6;
		}
		if( strFieldName.equals("ConOperator") ) {
			return 7;
		}
		if( strFieldName.equals("ConCom") ) {
			return 8;
		}
		if( strFieldName.equals("RetCount") ) {
			return 9;
		}
		if( strFieldName.equals("RetOperator") ) {
			return 10;
		}
		if( strFieldName.equals("RetCom") ) {
			return 11;
		}
		if( strFieldName.equals("RetState") ) {
			return 12;
		}
		if( strFieldName.equals("AssignCount") ) {
			return 13;
		}
		if( strFieldName.equals("RelaPlan") ) {
			return 14;
		}
		if( strFieldName.equals("RelaPrint") ) {
			return 15;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 16;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 17;
		}
		if( strFieldName.equals("UpdateDate") ) {
			return 18;
		}
		if( strFieldName.equals("UpdateTime") ) {
			return 19;
		}
		if( strFieldName.equals("PlanState") ) {
			return 20;
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
				strFieldName = "PlanID";
				break;
			case 1:
				strFieldName = "CertifyCode";
				break;
			case 2:
				strFieldName = "PlanType";
				break;
			case 3:
				strFieldName = "AppCount";
				break;
			case 4:
				strFieldName = "AppOperator";
				break;
			case 5:
				strFieldName = "AppCom";
				break;
			case 6:
				strFieldName = "ConCount";
				break;
			case 7:
				strFieldName = "ConOperator";
				break;
			case 8:
				strFieldName = "ConCom";
				break;
			case 9:
				strFieldName = "RetCount";
				break;
			case 10:
				strFieldName = "RetOperator";
				break;
			case 11:
				strFieldName = "RetCom";
				break;
			case 12:
				strFieldName = "RetState";
				break;
			case 13:
				strFieldName = "AssignCount";
				break;
			case 14:
				strFieldName = "RelaPlan";
				break;
			case 15:
				strFieldName = "RelaPrint";
				break;
			case 16:
				strFieldName = "MakeDate";
				break;
			case 17:
				strFieldName = "MakeTime";
				break;
			case 18:
				strFieldName = "UpdateDate";
				break;
			case 19:
				strFieldName = "UpdateTime";
				break;
			case 20:
				strFieldName = "PlanState";
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
		if( strFieldName.equals("PlanID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CertifyCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AppOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AppCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ConOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RetCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RetOperator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RetCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RetState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AssignCount") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("RelaPlan") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RelaPrint") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpdateDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("UpdateTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanState") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}

}
