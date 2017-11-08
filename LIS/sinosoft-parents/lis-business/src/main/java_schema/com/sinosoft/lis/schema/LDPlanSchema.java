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
import com.sinosoft.lis.db.LDPlanDB;

/*
 * <p>ClassName: LDPlanSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 基础信息
 */
public class LDPlanSchema implements Schema, Cloneable
{
private static Logger logger = Logger.getLogger(LDPlanSchema.class);
	// @Field
	/** 保险计划编码 */
	private String ContPlanCode;
	/** 保险计划名称 */
	private String ContPlanName;
	/** 计划类别 */
	private String PlanType;
	/** 计划规则 */
	private String PlanRule;
	/** 计划规则sql */
	private String PlanSql;
	/** 备注 */
	private String Remark;
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
	/** 可投保人数 */
	private int Peoples3;
	/** 备注2 */
	private String Remark2;
	/** 管理机构 */
	private String ManageCom;
	/** 销售渠道 */
	private String SaleChnl;
	/** 销售起期 */
	private Date StartDate;
	/** 销售止期 */
	private Date EndDate;
	/** 状态 */
	private String State;
	/** 计划层级2 */
	private String PlanKind2;
	/** 计划层级3 */
	private String Plankind3;
	/** 计划层级1 */
	private String PlanKind1;
	/** 产品组合标记 */
	private String PortfolioFlag;

	public static final int FIELDNUM = 22;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDPlanSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContPlanCode";
		pk[1] = "PlanType";

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
		LDPlanSchema cloned = (LDPlanSchema)super.clone();
		cloned.fDate = (FDate) fDate.clone();
		cloned.mErrors = (CErrors) mErrors.clone();
		return cloned;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/**
	* 00-默认值
	*/
	public String getContPlanCode()
	{
		return ContPlanCode;
	}
	public void setContPlanCode(String aContPlanCode)
	{
		if(aContPlanCode!=null && aContPlanCode.length()>20)
			throw new IllegalArgumentException("保险计划编码ContPlanCode值"+aContPlanCode+"的长度"+aContPlanCode.length()+"大于最大值20");
		ContPlanCode = aContPlanCode;
	}
	public String getContPlanName()
	{
		return ContPlanName;
	}
	public void setContPlanName(String aContPlanName)
	{
		if(aContPlanName!=null && aContPlanName.length()>120)
			throw new IllegalArgumentException("保险计划名称ContPlanName值"+aContPlanName+"的长度"+aContPlanName.length()+"大于最大值120");
		ContPlanName = aContPlanName;
	}
	/**
	* 0-固定计划；<p>
	* 1-非固定计划<p>
	* 3-险种默认值<p>
	* 4-套餐计划
	*/
	public String getPlanType()
	{
		return PlanType;
	}
	public void setPlanType(String aPlanType)
	{
		if(aPlanType!=null && aPlanType.length()>1)
			throw new IllegalArgumentException("计划类别PlanType值"+aPlanType+"的长度"+aPlanType.length()+"大于最大值1");
		PlanType = aPlanType;
	}
	public String getPlanRule()
	{
		return PlanRule;
	}
	public void setPlanRule(String aPlanRule)
	{
		if(aPlanRule!=null && aPlanRule.length()>100)
			throw new IllegalArgumentException("计划规则PlanRule值"+aPlanRule+"的长度"+aPlanRule.length()+"大于最大值100");
		PlanRule = aPlanRule;
	}
	public String getPlanSql()
	{
		return PlanSql;
	}
	public void setPlanSql(String aPlanSql)
	{
		if(aPlanSql!=null && aPlanSql.length()>500)
			throw new IllegalArgumentException("计划规则sqlPlanSql值"+aPlanSql+"的长度"+aPlanSql.length()+"大于最大值500");
		PlanSql = aPlanSql;
	}
	/**
	* 参数个数
	*/
	public String getRemark()
	{
		return Remark;
	}
	public void setRemark(String aRemark)
	{
		if(aRemark!=null && aRemark.length()>2000)
			throw new IllegalArgumentException("备注Remark值"+aRemark+"的长度"+aRemark.length()+"大于最大值2000");
		Remark = aRemark;
	}
	public String getOperator()
	{
		return Operator;
	}
	public void setOperator(String aOperator)
	{
		if(aOperator!=null && aOperator.length()>80)
			throw new IllegalArgumentException("操作员Operator值"+aOperator+"的长度"+aOperator.length()+"大于最大值80");
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
	public int getPeoples3()
	{
		return Peoples3;
	}
	public void setPeoples3(int aPeoples3)
	{
		Peoples3 = aPeoples3;
	}
	public void setPeoples3(String aPeoples3)
	{
		if (aPeoples3 != null && !aPeoples3.equals(""))
		{
			Integer tInteger = new Integer(aPeoples3);
			int i = tInteger.intValue();
			Peoples3 = i;
		}
	}

	/**
	* 参数个数
	*/
	public String getRemark2()
	{
		return Remark2;
	}
	public void setRemark2(String aRemark2)
	{
		if(aRemark2!=null && aRemark2.length()>2000)
			throw new IllegalArgumentException("备注2Remark2值"+aRemark2+"的长度"+aRemark2.length()+"大于最大值2000");
		Remark2 = aRemark2;
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
	/**
	* 1-个人营销,2-团险直销,3-银行代理
	*/
	public String getSaleChnl()
	{
		return SaleChnl;
	}
	public void setSaleChnl(String aSaleChnl)
	{
		if(aSaleChnl!=null && aSaleChnl.length()>2)
			throw new IllegalArgumentException("销售渠道SaleChnl值"+aSaleChnl+"的长度"+aSaleChnl.length()+"大于最大值2");
		SaleChnl = aSaleChnl;
	}
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/**
	* 用以存储产品组合所处状态<p>
	* <p>
	* 0-申请中（产品组合保存时指定）<p>
	* 1-待审核<p>
	* 2-审核中<p>
	* 3-待审批<p>
	* 4-审批中<p>
	* <p>
	* 5-审核不通过<p>
	* 7-审批不通过<p>
	* <p>
	* 9-完成
	*/
	public String getState()
	{
		return State;
	}
	public void setState(String aState)
	{
		if(aState!=null && aState.length()>1)
			throw new IllegalArgumentException("状态State值"+aState+"的长度"+aState.length()+"大于最大值1");
		State = aState;
	}
	public String getPlanKind2()
	{
		return PlanKind2;
	}
	public void setPlanKind2(String aPlanKind2)
	{
		if(aPlanKind2!=null && aPlanKind2.length()>100)
			throw new IllegalArgumentException("计划层级2PlanKind2值"+aPlanKind2+"的长度"+aPlanKind2.length()+"大于最大值100");
		PlanKind2 = aPlanKind2;
	}
	public String getPlankind3()
	{
		return Plankind3;
	}
	public void setPlankind3(String aPlankind3)
	{
		if(aPlankind3!=null && aPlankind3.length()>100)
			throw new IllegalArgumentException("计划层级3Plankind3值"+aPlankind3+"的长度"+aPlankind3.length()+"大于最大值100");
		Plankind3 = aPlankind3;
	}
	public String getPlanKind1()
	{
		return PlanKind1;
	}
	public void setPlanKind1(String aPlanKind1)
	{
		if(aPlanKind1!=null && aPlanKind1.length()>100)
			throw new IllegalArgumentException("计划层级1PlanKind1值"+aPlanKind1+"的长度"+aPlanKind1.length()+"大于最大值100");
		PlanKind1 = aPlanKind1;
	}
	/**
	* 1-个险产品组合<p>
	* 其它为保险套餐
	*/
	public String getPortfolioFlag()
	{
		return PortfolioFlag;
	}
	public void setPortfolioFlag(String aPortfolioFlag)
	{
		if(aPortfolioFlag!=null && aPortfolioFlag.length()>1)
			throw new IllegalArgumentException("产品组合标记PortfolioFlag值"+aPortfolioFlag+"的长度"+aPortfolioFlag.length()+"大于最大值1");
		PortfolioFlag = aPortfolioFlag;
	}

	/**
	* 使用另外一个 LDPlanSchema 对象给 Schema 赋值
	* @param: aLDPlanSchema LDPlanSchema
	**/
	public void setSchema(LDPlanSchema aLDPlanSchema)
	{
		this.ContPlanCode = aLDPlanSchema.getContPlanCode();
		this.ContPlanName = aLDPlanSchema.getContPlanName();
		this.PlanType = aLDPlanSchema.getPlanType();
		this.PlanRule = aLDPlanSchema.getPlanRule();
		this.PlanSql = aLDPlanSchema.getPlanSql();
		this.Remark = aLDPlanSchema.getRemark();
		this.Operator = aLDPlanSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDPlanSchema.getMakeDate());
		this.MakeTime = aLDPlanSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLDPlanSchema.getModifyDate());
		this.ModifyTime = aLDPlanSchema.getModifyTime();
		this.Peoples3 = aLDPlanSchema.getPeoples3();
		this.Remark2 = aLDPlanSchema.getRemark2();
		this.ManageCom = aLDPlanSchema.getManageCom();
		this.SaleChnl = aLDPlanSchema.getSaleChnl();
		this.StartDate = fDate.getDate( aLDPlanSchema.getStartDate());
		this.EndDate = fDate.getDate( aLDPlanSchema.getEndDate());
		this.State = aLDPlanSchema.getState();
		this.PlanKind2 = aLDPlanSchema.getPlanKind2();
		this.Plankind3 = aLDPlanSchema.getPlankind3();
		this.PlanKind1 = aLDPlanSchema.getPlanKind1();
		this.PortfolioFlag = aLDPlanSchema.getPortfolioFlag();
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
			if( rs.getString("ContPlanCode") == null )
				this.ContPlanCode = null;
			else
				this.ContPlanCode = rs.getString("ContPlanCode").trim();

			if( rs.getString("ContPlanName") == null )
				this.ContPlanName = null;
			else
				this.ContPlanName = rs.getString("ContPlanName").trim();

			if( rs.getString("PlanType") == null )
				this.PlanType = null;
			else
				this.PlanType = rs.getString("PlanType").trim();

			if( rs.getString("PlanRule") == null )
				this.PlanRule = null;
			else
				this.PlanRule = rs.getString("PlanRule").trim();

			if( rs.getString("PlanSql") == null )
				this.PlanSql = null;
			else
				this.PlanSql = rs.getString("PlanSql").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

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

			this.Peoples3 = rs.getInt("Peoples3");
			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("SaleChnl") == null )
				this.SaleChnl = null;
			else
				this.SaleChnl = rs.getString("SaleChnl").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

			if( rs.getString("PlanKind2") == null )
				this.PlanKind2 = null;
			else
				this.PlanKind2 = rs.getString("PlanKind2").trim();

			if( rs.getString("Plankind3") == null )
				this.Plankind3 = null;
			else
				this.Plankind3 = rs.getString("Plankind3").trim();

			if( rs.getString("PlanKind1") == null )
				this.PlanKind1 = null;
			else
				this.PlanKind1 = rs.getString("PlanKind1").trim();

			if( rs.getString("PortfolioFlag") == null )
				this.PortfolioFlag = null;
			else
				this.PortfolioFlag = rs.getString("PortfolioFlag").trim();

		}
		catch(SQLException sqle)
		{
			logger.debug("数据库中的LDPlan表字段个数和Schema中的字段个数不一致，或者执行db.executeQuery查询时没有使用select * from tables");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);
			return false;
		}
		return true;
	}

	public LDPlanSchema getSchema()
	{
		LDPlanSchema aLDPlanSchema = new LDPlanSchema();
		aLDPlanSchema.setSchema(this);
		return aLDPlanSchema;
	}

	public LDPlanDB getDB()
	{
		LDPlanDB aDBOper = new LDPlanDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlan描述/A>表字段
	* @return: String 返回打包后字符串
	**/
	public String encode()
	{
		StringBuffer strReturn = new StringBuffer(256);
		strReturn.append(StrTool.cTrim(ContPlanCode)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ContPlanName)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanType)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanRule)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanSql)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Operator)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( MakeDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(MakeTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( ModifyDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ModifyTime)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append( ChgData.chgData(Peoples3));strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Remark2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(ManageCom)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(SaleChnl)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( StartDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(fDate.getString( EndDate ))); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(State)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanKind2)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(Plankind3)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PlanKind1)); strReturn.append(SysConst.PACKAGESPILTER);
		strReturn.append(StrTool.cTrim(PortfolioFlag));
		return strReturn.toString();
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDPlan>历史记账凭证主表信息</A>表字段
	* @param: strMessage String 包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContPlanCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ContPlanName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			PlanType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			PlanRule = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			PlanSql = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Peoples3= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,12,SysConst.PACKAGESPILTER))).intValue();
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			SaleChnl = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17,SysConst.PACKAGESPILTER));
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			PlanKind2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Plankind3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			PlanKind1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			PortfolioFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDPlanSchema";
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
		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanCode));
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContPlanName));
		}
		if (FCode.equalsIgnoreCase("PlanType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanType));
		}
		if (FCode.equalsIgnoreCase("PlanRule"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanRule));
		}
		if (FCode.equalsIgnoreCase("PlanSql"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanSql));
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
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
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Peoples3));
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
		}
		if (FCode.equalsIgnoreCase("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleChnl));
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equalsIgnoreCase("PlanKind2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanKind2));
		}
		if (FCode.equalsIgnoreCase("Plankind3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Plankind3));
		}
		if (FCode.equalsIgnoreCase("PlanKind1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PlanKind1));
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PortfolioFlag));
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
				strFieldValue = StrTool.GBKToUnicode(ContPlanCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ContPlanName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PlanType);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(PlanRule);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(PlanSql);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = String.valueOf(Peoples3);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(SaleChnl);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(PlanKind2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Plankind3);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(PlanKind1);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(PortfolioFlag);
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

		if (FCode.equalsIgnoreCase("ContPlanCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanCode = FValue.trim();
			}
			else
				ContPlanCode = null;
		}
		if (FCode.equalsIgnoreCase("ContPlanName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContPlanName = FValue.trim();
			}
			else
				ContPlanName = null;
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
		if (FCode.equalsIgnoreCase("PlanRule"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanRule = FValue.trim();
			}
			else
				PlanRule = null;
		}
		if (FCode.equalsIgnoreCase("PlanSql"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanSql = FValue.trim();
			}
			else
				PlanSql = null;
		}
		if (FCode.equalsIgnoreCase("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
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
		if (FCode.equalsIgnoreCase("Peoples3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Peoples3 = i;
			}
		}
		if (FCode.equalsIgnoreCase("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
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
		if (FCode.equalsIgnoreCase("SaleChnl"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleChnl = FValue.trim();
			}
			else
				SaleChnl = null;
		}
		if (FCode.equalsIgnoreCase("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equalsIgnoreCase("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equalsIgnoreCase("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
		}
		if (FCode.equalsIgnoreCase("PlanKind2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanKind2 = FValue.trim();
			}
			else
				PlanKind2 = null;
		}
		if (FCode.equalsIgnoreCase("Plankind3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Plankind3 = FValue.trim();
			}
			else
				Plankind3 = null;
		}
		if (FCode.equalsIgnoreCase("PlanKind1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PlanKind1 = FValue.trim();
			}
			else
				PlanKind1 = null;
		}
		if (FCode.equalsIgnoreCase("PortfolioFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PortfolioFlag = FValue.trim();
			}
			else
				PortfolioFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDPlanSchema other = (LDPlanSchema)otherObject;
		return
			ContPlanCode.equals(other.getContPlanCode())
			&& ContPlanName.equals(other.getContPlanName())
			&& PlanType.equals(other.getPlanType())
			&& PlanRule.equals(other.getPlanRule())
			&& PlanSql.equals(other.getPlanSql())
			&& Remark.equals(other.getRemark())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& Peoples3 == other.getPeoples3()
			&& Remark2.equals(other.getRemark2())
			&& ManageCom.equals(other.getManageCom())
			&& SaleChnl.equals(other.getSaleChnl())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& State.equals(other.getState())
			&& PlanKind2.equals(other.getPlanKind2())
			&& Plankind3.equals(other.getPlankind3())
			&& PlanKind1.equals(other.getPlanKind1())
			&& PortfolioFlag.equals(other.getPortfolioFlag());
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
		if( strFieldName.equals("ContPlanCode") ) {
			return 0;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return 1;
		}
		if( strFieldName.equals("PlanType") ) {
			return 2;
		}
		if( strFieldName.equals("PlanRule") ) {
			return 3;
		}
		if( strFieldName.equals("PlanSql") ) {
			return 4;
		}
		if( strFieldName.equals("Remark") ) {
			return 5;
		}
		if( strFieldName.equals("Operator") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("Peoples3") ) {
			return 11;
		}
		if( strFieldName.equals("Remark2") ) {
			return 12;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 13;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return 14;
		}
		if( strFieldName.equals("StartDate") ) {
			return 15;
		}
		if( strFieldName.equals("EndDate") ) {
			return 16;
		}
		if( strFieldName.equals("State") ) {
			return 17;
		}
		if( strFieldName.equals("PlanKind2") ) {
			return 18;
		}
		if( strFieldName.equals("Plankind3") ) {
			return 19;
		}
		if( strFieldName.equals("PlanKind1") ) {
			return 20;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
			return 21;
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
				strFieldName = "ContPlanCode";
				break;
			case 1:
				strFieldName = "ContPlanName";
				break;
			case 2:
				strFieldName = "PlanType";
				break;
			case 3:
				strFieldName = "PlanRule";
				break;
			case 4:
				strFieldName = "PlanSql";
				break;
			case 5:
				strFieldName = "Remark";
				break;
			case 6:
				strFieldName = "Operator";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "Peoples3";
				break;
			case 12:
				strFieldName = "Remark2";
				break;
			case 13:
				strFieldName = "ManageCom";
				break;
			case 14:
				strFieldName = "SaleChnl";
				break;
			case 15:
				strFieldName = "StartDate";
				break;
			case 16:
				strFieldName = "EndDate";
				break;
			case 17:
				strFieldName = "State";
				break;
			case 18:
				strFieldName = "PlanKind2";
				break;
			case 19:
				strFieldName = "Plankind3";
				break;
			case 20:
				strFieldName = "PlanKind1";
				break;
			case 21:
				strFieldName = "PortfolioFlag";
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
		if( strFieldName.equals("ContPlanCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ContPlanName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanRule") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanSql") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
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
		if( strFieldName.equals("Peoples3") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Remark2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SaleChnl") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanKind2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Plankind3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PlanKind1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PortfolioFlag") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_INT;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
